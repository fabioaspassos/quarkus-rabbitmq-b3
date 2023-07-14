package com.acme.checkdata;

import com.acme.integration.b3.guia.UpdatedProductClient;
import com.acme.integration.b3.guia.GuiaResponse;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.*;

import java.time.ZonedDateTime;

@ApplicationScoped
public class CheckDataService {

    @Inject
    UpdatedProductClient updatedProductClient;

    @Channel("check-positions")
    Emitter<CheckDataMessageRequest> checkDateEmitter;

    @ConfigProperty(name="products")
    String[] products;


    public void execute(String referenceDate){
        for (String product: products ) {
            GuiaResponse guiaResponse = updatedProductClient.getUpdatedProduct(referenceDate, product, "1");

            if (guiaResponse != null) {
                Integer lastPage = guiaResponse.getLinks().lastPage();
                this.sendNotification(lastPage, referenceDate, product);
            }
        }
    }

    private void sendNotification(Integer qtdeOfPages, String referenceDate, String product){
        OutgoingRabbitMQMetadata metadata = new OutgoingRabbitMQMetadata.Builder()
                .withTimestamp(ZonedDateTime.now())
                .withRoutingKey(product)
                .build();

        for (int i = 1; i <= qtdeOfPages ; i++) {
            checkDateEmitter.send(
                    Message.of(
                            new CheckDataMessageRequest(referenceDate, i),
                            Metadata.of(metadata)));
        }
    }
}
