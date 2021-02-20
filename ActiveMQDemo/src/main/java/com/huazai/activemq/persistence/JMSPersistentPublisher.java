package com.huazai.activemq.persistence;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author pyh
 * @date 2021/1/5 21:53
 */
public class JMSPersistentPublisher {
    // ActiveMQ服务地址
    public static final String ACTIVEMQ_URL = "tcp://192.168.64.129:61616";
    // 主题名称
    public static final String TOPIC_NAME = "persistent-topic";

    public static void main(String[] args) throws Exception {
        // 1.创建给定ActiveMQ服务连接工厂，使用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2.通过连接工厂，创建连接对象并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
//        connection.start();
        // 3.创建会话，第一个参数为是否开启事务，第二个参数为签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4.创建目的地（队列或者主题）
        Topic topic = session.createTopic(TOPIC_NAME);
        // 可以用父接口Destination接受
        // Destination topic = session.createQueue(TOPIC_NAME);
        // 5.创建消息的生产者
        MessageProducer producer = session.createProducer(topic);
        // 持久化设置
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        connection.start();
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
