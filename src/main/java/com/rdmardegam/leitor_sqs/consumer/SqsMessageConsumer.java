package com.rdmardegam.leitor_sqs.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;

import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;


@Service
public class SqsMessageConsumer {
    private static final Logger log = LoggerFactory.getLogger(SqsMessageConsumer.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final Executor taskExecutor;

    public SqsMessageConsumer(Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }


    @SqsListener(value = "${app.sqs.queue-name}")
    public void onMessage(org.springframework.messaging.Message message) {
        //processMessage(message);
        //messageExecutor.execute(() -> processMessage(message));
        CompletableFuture.runAsync(() -> processMessage(message), taskExecutor);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
    public void processMessage(org.springframework.messaging.Message message) {
        try {
            var json = mapper.readTree(message.getPayload().toString());
            var payload = json.get("payload");
            String cardId = payload.get("cardId").asText();
            String pan = payload.get("pan").asText();
            log.info("Processando cardId={} pan={} thread={}", cardId, pan, Thread.currentThread().getName());

            Thread.sleep(10000);

            log.info("Mensagem processada com sucesso - cardId={}", cardId);

            Acknowledgement.acknowledge(message);
        } catch (Exception e) {
            log.error("Erro ao processar mensagem: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao processar mensagem", e);
        }
    }
}
