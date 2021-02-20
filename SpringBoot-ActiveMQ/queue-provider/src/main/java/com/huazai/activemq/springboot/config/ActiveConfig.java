package com.huazai.activemq.springboot.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * @author pyh
 * @date 2021/1/16 19:13
 */
@Configuration
public class ActiveConfig {
    @Value("${queue.name}")
    private String queueName;

    @Bean
    public Queue activeQueue() {
        return new ActiveMQQueue(queueName);
    }
}
