package com.huazai.activemq.springboot.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Topic;

/**
 * @author pyh
 * @date 2021/1/16 19:13
 */
@Configuration
public class ActiveConfig {
    @Value("${topic.name}")
    private String topicName;

    @Bean
    public Topic activeTopic() {
        return new ActiveMQTopic(topicName);
    }
}
