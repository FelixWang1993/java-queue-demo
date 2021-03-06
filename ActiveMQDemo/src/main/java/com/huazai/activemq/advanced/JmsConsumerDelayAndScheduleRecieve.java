package com.huazai.activemq.advanced;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.time.LocalDateTime;

/**
 * ActiveMQ高级特性之延迟和定时投递消费者
 * @author pyh
 * @date 2021/1/30 20:54
 */
public class JmsConsumerDelayAndScheduleRecieve {
    // ActiveMQ服务地址
    public static final String ACTIVEMQ_URL = "tcp://192.168.64.129:61616";
    // 消息队列名称，取消息必须和存消息的队列名称一致
    public static final String QUEUE_NAME = "queue-delay-schedule";

    public static void main(String[] args) throws Exception {
        // 1.创建给定ActiveMQ服务连接工厂，使用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2.通过连接工厂，创建连接对象并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 3.创建会话，第一个参数为是否开启事务，第二个参数为签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4.创建目的地（队列或者主题）
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5.创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
        int i = 0;
        while (true) {
            // 接受消息根据生产者发送消息类型强类型转换
            TextMessage message = (TextMessage) consumer.receive();
            if (message != null) {
                String text = message.getText();
                System.out.print("第" + ++i + "次接受延迟消息：");
                System.out.println(text);
                int endSecond = LocalDateTime.now().getSecond();
            } else {
                break;
            }
        }
        consumer.close();
        session.close();
        connection.close();
    }
}
