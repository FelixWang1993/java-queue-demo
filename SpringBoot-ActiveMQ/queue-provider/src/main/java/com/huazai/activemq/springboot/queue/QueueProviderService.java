package com.huazai.activemq.springboot.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Queue;
import java.util.UUID;

@Service
public class QueueProviderService {
    /**
     * 相当于 {@link JmsTemplate}
     */
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    /**
     * 生产消息
     *
     * @throws JMSException
     */
    public void productMessage() throws JMSException {
        // 生产者生产并发送消息，此方法是send方法的加强版
        jmsMessagingTemplate.convertAndSend(queue, "消费者发送消息：" + UUID.randomUUID());
    }

    /**
     * 定时投递消息
     *
     * @throws JMSException
     */
//    @Scheduled(fixedDelay = 3000)
    public void productMessageScheduled() throws JMSException {
        // 生产者生产并发送消息，此方法是send方法的加强版
        jmsMessagingTemplate.convertAndSend(queue, "定时投递消息：hello world " + UUID.randomUUID());
    }
}


