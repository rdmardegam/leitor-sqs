package com.rdmardegam.leitor_sqs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdmardegam.leitor_sqs.consumer.SqsMessageConsumer;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class MessageProcessorService {

    private static final Logger log = LoggerFactory.getLogger(MessageProcessorService.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
    public void processMessage(org.springframework.messaging.Message message) {
        try {
            var json = mapper.readTree(message.getPayload().toString());
            var payload = json.get("payload");
            String cardId = payload.get("cardId").asText();
            String pan = payload.get("pan").asText();
            log.info("Processando cardId={} pan={} thread={}", cardId, pan, Thread.currentThread().getName());

            Thread.sleep(3000);

            int x = Integer.parseInt("S");

            log.info("Mensagem processada com sucesso - cardId={}", cardId);
            Acknowledgement.acknowledge(message);

        } catch (Exception e) {
            log.error("Erro ao processar mensagem: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao processar mensagem", e);
        }
    }

}
