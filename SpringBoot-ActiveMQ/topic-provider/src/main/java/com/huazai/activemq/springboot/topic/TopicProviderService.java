package com.huazai.activemq.springboot.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.Topic;
import java.util.UUID;

/**
 * @author pyh
 * @date 2021/1/17 15:14
 */
@Service
public class TopicProviderService {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic topic;

    /**
     * 定时发布主题消息
     */
    @Scheduled(fixedDelay = 3000)
    public void productTopic() {
        jmsMessagingTemplate.convertAndSend(topic, "发布者发布主题消息：" + UUID.randomUUID());
    }
}
