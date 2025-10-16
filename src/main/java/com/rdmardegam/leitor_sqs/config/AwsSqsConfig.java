package com.rdmardegam.leitor_sqs.config;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class AwsSqsConfig {

//    @Bean
//    public SqsClient sqsClient() {
//        return SqsClient.builder()
//                .region(Region.of("us-east-1")) // ou região do seu SQS
//                .credentialsProvider(
//                        StaticCredentialsProvider.create(
//                                AwsBasicCredentials.create("test", "test")))
//                .httpClientBuilder(UrlConnectionHttpClient.builder())
//                .endpointOverride(URI.create("http://localhost:4566")) // Seu endpoint (ex: LocalStack)
//                .build();
//    }

    @Bean
    SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {

        return SqsMessageListenerContainerFactory.builder()
                .configure(options -> options.acknowledgementMode(AcknowledgementMode.MANUAL)
                        .maxConcurrentMessages(1) // quantidade de threads
                        .maxMessagesPerPoll(1)  // quantidade de msg recuperada por poll
                )
                //.acknowledgementResultCallback(new AckResultCallback())
                .sqsAsyncClient(sqsAsyncClient)
                .build();
    }

//    static class AckResultCallback implements AcknowledgementResultCallback<Object> {
//
//        @Override
//        public void onSuccess(Collection<Message<Object>> messages) {
//            Logger.info("Ack with success at {}", OffsetDateTime.now());
//        }
//
//        @Override
//        public void onFailure(Collection<Message<Object>> messages, Throwable t) {
//            LOGGER.error("Ack with fail", t);
//        }
//    }
}

