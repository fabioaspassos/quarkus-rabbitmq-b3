package com.acme.fixedIncome;

import com.acme.checkdata.CheckDataMessageRequest;
import com.acme.integration.b3.guia.GuiaResponse;
import com.acme.integration.b3.guia.UpdatedProductClient;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.rabbitmq.IncomingRabbitMQMetadata;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.*;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class RequestDataFixedIncomeService {

    @Channel("get-positions-b3")
    Emitter<RequestDataMessage> checkDateEmitter;
    @Inject
    UpdatedProductClient updatedProductClient;
    @Inject
    CheckDocumentPriorityService checkDocumentPriorityService;

    //https://smallrye.io/smallrye-reactive-messaging/smallrye-reactive-messaging/3.1/signatures/signatures.html
    @Incoming("check-positions-fixedIncome-b3")
    @Blocking
    public CompletionStage<Void> execute (Message<JsonObject> inMessage){
        CheckDataMessageRequest requestDocuments = inMessage.getPayload().mapTo(CheckDataMessageRequest.class);
        Optional<IncomingRabbitMQMetadata> inMetadata = inMessage.getMetadata(IncomingRabbitMQMetadata.class);

        //A chave para o produto é o mesmo utilizado na RoutingKey
        String productAndRoutingKey = inMetadata.isPresent() ? inMetadata.get().getRoutingKey() : "";

        GuiaResponse response = updatedProductClient.getUpdatedProduct(
                requestDocuments.getReferenceDate(),
                productAndRoutingKey,
                requestDocuments.getPage().toString());

        Multi.createFrom()
                .iterable(response.getData().getUpdatedProducts())
                .map( i -> buildRequestDataMessage(i.getDocumentNumber(), requestDocuments.getReferenceDate(), productAndRoutingKey))
                .subscribe().with(msg -> checkDateEmitter.send(msg));

        return inMessage.ack();
    }

    private Message<RequestDataMessage> buildRequestDataMessage(String document, String referenceDate, String routingKey){
        RequestDataMessage  requestDataMessage = new RequestDataMessage(document, referenceDate);

        OutgoingRabbitMQMetadata metadata = new OutgoingRabbitMQMetadata.Builder()
                .withTimestamp(ZonedDateTime.now())
                .withRoutingKey(routingKey)
                .withPriority(checkDocumentPriorityService.execute(document))
                .build();

        return Message.of(requestDataMessage, Metadata.of(metadata));
    }

}
