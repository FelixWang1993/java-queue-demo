package com.huazai.activemq.spring.queue;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author pyh
 * @date 2021/01/11 23:39
 */
@Service
public class SpringMQConsumer {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQConsumer springMQConsumer = applicationContext.getBean(SpringMQConsumer.class);
        while(true) {
            // 接受消息
            String returnValue = (String) springMQConsumer.jmsTemplate.receiveAndConvert();
            // 输入exit-退出
            if ("exit-".equals(returnValue)) {
                System.exit(-1);
            }
            System.out.println("****消费者收到的消息: " + returnValue);
        }
    }
}
 
 
