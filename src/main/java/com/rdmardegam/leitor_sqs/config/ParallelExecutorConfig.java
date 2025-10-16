package com.rdmardegam.leitor_sqs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ParallelExecutorConfig {

    @Bean(name = "messageExecutor")
    public ThreadPoolTaskExecutor  messageExecutor() {
        // Ja temos 10 threads por defautl, ao adicionar 10 a mais aqui... vamos ter 20 threads em apralelo
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(11); // Inicia com X threads em paralelo para processar
        executor.setMaxPoolSize(11); // Pode expandir ate o max poll e ter no maximo em paralelo
        executor.setQueueCapacity(0); // aguarda na fila, sem necessidade de fazer um novo poll
        //executor.setQueueCapacity(0);
        executor.setThreadNamePrefix("msg-processor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}
