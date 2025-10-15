//package com.rdmardegam.leitor_sqs.config;
//
//import com.rdmardegam.leitor_sqs.consumer.SqsMessageConsumer;
//import io.awspring.cloud.sqs.listener.ContainerOptions;
//import io.awspring.cloud.sqs.listener.MessageListenerContainer;
//import io.awspring.cloud.sqs.listener.SqsContainerOptions;
//import io.awspring.cloud.sqs.listener.SqsMessageListenerContainer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import software.amazon.awssdk.services.sqs.SqsAsyncClient;
//
//import java.time.Duration;
//
//@Configuration
//public class SqsConfig {
//
//    @Bean
//    public SqsMessageListenerContainer<String> sqsListenerContainer(
//            SqsAsyncClient sqsAsyncClient,
//            ThreadPoolTaskExecutor sqsTaskExecutor) {
//
//        return SqsMessageListenerContainer
//                .<String>builder()
//                .queueNames("minha-fila-teste")
//                .sqsAsyncClient(sqsAsyncClient)
//                .build();
//    }
//}
//
//
//
////
////import io.awspring.cloud.messaging.listener.SimpleMessageListenerContainer;
////import io.awspring.cloud.messaging.listener.SimpleMessageListenerContainerOptions;
////import io.awspring.cloud.messaging.listener.annotation.SqsListener;
////import io.awspring.cloud.messaging.listener.support.QueueMessageHandlerFactory;
////import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
////
////@Configuration
////public class SqsListenerConfig {
////
////    private final ThreadPoolTaskExecutor sqsExecutor;
////    MessageListenerContainer
////    public SqsListenerContainerConfig(ThreadPoolTaskExecutor sqsExecutor) {
////        this.sqsExecutor = sqsExecutor;
////    }
////
////    @Bean
////    public QueueMessageHandlerFactory   simpleMessageListenerContainer(QueueMessageHandlerFactory queueMessageHandlerFactory) {
////        SimpleMessageListenerContainerOptions options = SimpleMessageListenerContainerOptions.builder()
////                .concurrentConsumers(20)   // máximo de threads
////                .maxNumberOfMessages(10)   // mensagens por poll
////                .taskExecutor(sqsExecutor) // executor customizado
////                .build();
////
////        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(queueMessageHandlerFactory, options);
////        container.setQueueNames("minha-fila-teste");
////        return container;
////    }
////}