package com.huazai.activemq.springboot.topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author pyh
 * @date 2021/1/17 15:15
 */
@Service
public class TopicConsumerService {

    /**
     * 开启监听器监听主题消息
     *
     * @param textMessage
     * @throws JMSException
     */
    @JmsListener(destination = "${topic.name}")
    public void receive(TextMessage textMessage) throws JMSException {
        String text = textMessage.getText();
        System.out.println("订阅者订阅到的消息：" + text);
    }
}
