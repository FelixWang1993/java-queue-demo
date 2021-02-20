package com.huazai.activemq.spring.queue;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * @author pyh
 * @date 2021/01/11 23:39
 */
@Service
public class SpringMQProducer {
    static final Scanner input = new Scanner(System.in);

    @Autowired
    private final JmsTemplate jmsTemplate;

    @Autowired
    public SpringMQProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQProducer springMQ_producer = applicationContext.getBean(SpringMQProducer.class);
        JmsTemplate jmsTemplate = springMQ_producer.jmsTemplate;
        // 发送消息
        jmsTemplate.send(session -> session.createTextMessage("***Spring和ActiveMQ的整合case....."));
        System.out.println("********send task over");
        while(true) {
            System.out.println("\n请输入要发送的内容");
            final String msgText = input.next();
            // 输入exit推出程序
            if ("exit".equals(msgText)) {
                System.exit(-1);
            }
            if (msgText.startsWith("auto:")) {
                int amount;
                try {
                    amount = Integer.parseInt(msgText.substring(5));
                } catch (NumberFormatException e) {
                    System.out.println("auto指令格式错误，正确的应该是：auto:number");
                    continue;
                }
                System.out.println("请稍等...");
                for (int i = 1; i <= amount; i++) {
                    final int ii = i;
                    jmsTemplate.send(session -> session.createTextMessage(ii + ""));
                }
                System.out.println(msgText + "\t->|\t指令执行完成");
            } else {
                jmsTemplate.send(session -> session.createTextMessage(msgText));
                System.out.println(msgText + "\t->|\t发送完成");
            }
        }
    }
}



