package com.huazai.activemq.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author pyh
 * @date 2020/12/24 22:09
 */
public class JMSConsumerForListener {
    // ActiveMQ服务地址
    public static final String ACTIVEMQ_URL = "tcp://192.168.64.129:61616";
    // 消息队列名称，取消息必须和存消息的队列名称一致
    public static final String QUEUE_NAME = "queue01";

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

        /*
            异步非阻塞式方式监听器(onMessage)
            订阅者或消费者通过创建的消费者对象,给消费者注册消息监听器setMessageListener,
            当消息有消息的时候,系统会自动调用MessageListener类的onMessage方法
            我们只需要在onMessage方法内判断消息类型即可获取消息
         */
        consumer.setMessageListener(message -> {
            if (message != null && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("监听器接受到的消息：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        /*
            由于是异步接受消息，会发生监听器没监听到消息之前程序就已经运行完毕，
            所以通过此行代码阻塞程序等到监听器监听并回调，可在控制台输入任意字符并回车结束程序运行
         */
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
