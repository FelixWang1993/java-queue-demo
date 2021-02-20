package com.huazai.activemq.advanced;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;

import javax.jms.Connection;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * ActiveMQ高级特性之重试机制生产者
 *
 * @author pyh
 * @date 2021/2/1 23:24
 */
public class JmsProduceRedeliveryPolicy {
    // ActiveMQ服务地址
    public static final String ACTIVEMQ_URL = "tcp://192.168.64.129:61616";
    // 消息队列名称
    public static final String QUEUE_NAME = "queue-redelivery-policy";

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
        // 可以用父接口Destination接受
        // Destination queue = session.createQueue(QUEUE_NAME);
        // 5.创建消息的生产者，一定要向上转型为ActiveMQMessageProducer类型才有异步投递功能
        ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer) session.createProducer(queue);
        // 6.通过消息生产者生产消息发送MQ队列
        // 7.创建消息
        TextMessage textMessage = session.createTextMessage("投递消息：" + ":hello world");
        // 8.将消息发送到MQ，并回调判断消息是否发送成功
        activeMQMessageProducer.send(textMessage);
        // 9.关闭资源
        activeMQMessageProducer.close();
        session.close();
        connection.close();
        System.out.println("finish");
    }
}
