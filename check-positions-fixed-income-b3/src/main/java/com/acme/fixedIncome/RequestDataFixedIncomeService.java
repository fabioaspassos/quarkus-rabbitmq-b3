package com.acme.fixedIncome;

import com.acme.checkdata.CheckDataMessageRequest;
import com.acme.integration.b3.guia.GuiaResponse;
import com.acme.integration.b3.guia.UpdatedProductClient;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.*;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class RequestDataFixedIncomeService {

    @Channel("get-positions-b3")
    Emitter<RequestDataMessage> checkDateEmitter;
    @Inject
    UpdatedProductClient updatedProductClient;
    @Inject
    CheckDocumentPriorityService checkDocumentPriorityService;

    @Incoming("check-positions-fixedIncome-b3")
    @Blocking
    public CompletionStage<Void> execute (Message<JsonObject> message){
        CheckDataMessageRequest requestDocuments = message.getPayload().mapTo(CheckDataMessageRequest.class);

        GuiaResponse response = updatedProductClient.getUpdatedProduct(
                requestDocuments.getReferenceDate(),
                "FixedIncommePosition",
                requestDocuments.getPage().toString());

        Multi.createFrom()
                .iterable(response.getData().getUpdatedProducts())
                .map( i -> buildRequestDataMessage(i.getDocumentNumber(), requestDocuments.getReferenceDate()))
                .subscribe().with(msg -> checkDateEmitter.send(msg));

        return message.ack();
    }

    private Message<RequestDataMessage> buildRequestDataMessage(String document, String referenceDate){
        RequestDataMessage  requestDataMessage = new RequestDataMessage(document, referenceDate);

        OutgoingRabbitMQMetadata metadata = new OutgoingRabbitMQMetadata.Builder()
                .withTimestamp(ZonedDateTime.now())
                .withRoutingKey("FixedIncommePosition")
                .withPriority(checkDocumentPriorityService.execute(document))
                .build();

        return Message.of(requestDataMessage, Metadata.of(metadata));
    }

}
