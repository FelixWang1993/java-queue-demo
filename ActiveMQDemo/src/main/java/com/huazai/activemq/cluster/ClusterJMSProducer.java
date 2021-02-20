package com.huazai.activemq.cluster;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author pyh
 * @date 2020/12/23 23:21
 */
public class ClusterJMSProducer {
    // ActiveMQ服务地址
    public static final String ACTIVEMQ_URL = "failover:(tcp://192.168.64.129:61616,tcp://192.168.64.129:61617,tcp://192.168.64.129:61618)";
    // 消息队列名称
    public static final String QUEUE_NAME = "queue-cluster";

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
        // 5.创建消息的生产者
        MessageProducer producer = session.createProducer(queue);
        // 6.通过消息生产者生产6条消息发送MQ队列
        for (int i = 0; i < 3; i++) {
            // 7.创建消息
            TextMessage textMessage = session.createTextMessage("msg" + i + ":hello world");
            // 8.将消息发送到MQ
            producer.send(textMessage);
        }
        // 9.关闭资源
        producer.close();
        session.close();
        connection.close();
        System.out.println("finish");
    }

}
