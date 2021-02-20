package com.huazai.activemq.advanced;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;

/**
 * ActiveMQ高级特性之异步投递
 *
 * @author pyh
 * @date 2021/1/30 19:51
 */
public class JmsProduceAsyncSend {
    // ActiveMQ服务地址
    public static final String ACTIVEMQ_URL = "tcp://192.168.64.129:61616";
    // 消息队列名称
    public static final String QUEUE_NAME = "queue-async";

    public static void main(String[] args) throws Exception {
        // 1.创建给定ActiveMQ服务连接工厂，使用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 开启异步投递
        activeMQConnectionFactory.setUseAsyncSend(true);
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
        // 6.通过消息生产者生产6条消息发送MQ队列
        for (int i = 0; i < 3; i++) {
            // 7.创建消息
            TextMessage textMessage = session.createTextMessage("异步投递消息" + i + ":hello world");
            // 给消息设置一个唯一的id可以知道哪条消息发送成功，哪条消息发送失败
            textMessage.setJMSMessageID(UUID.randomUUID().toString());
            String msgId = textMessage.getJMSMessageID();
            // 8.将消息发送到MQ，并回调判断消息是否发送成功
            activeMQMessageProducer.send(textMessage, new AsyncCallback() {
                /**
                 * 消息发送成功回调方法
                 */
                @Override
                public void onSuccess() {
                    System.out.println("消息成功发送回调方法，成功消息标识：" + msgId);
                }

                /**
                 * 消息发送失败回调方法
                 *
                 * @param exception
                 */
                @Override
                public void onException(JMSException exception) {
                    System.err.println("消息发送失败回调方法，失败消息标识：" + msgId);
                }
            });
        }
        // 9.关闭资源
        activeMQMessageProducer.close();
        session.close();
        connection.close();
        System.out.println("finish");
    }
}
