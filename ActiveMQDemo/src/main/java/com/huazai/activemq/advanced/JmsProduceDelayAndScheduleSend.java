package com.huazai.activemq.advanced;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;
import java.util.UUID;

/**
 * ActiveMQ高级特性之延迟和定时投递生产者
 *
 * @author pyh
 * @date 2021/1/30 20:25
 */
public class JmsProduceDelayAndScheduleSend {
    // ActiveMQ服务地址
    public static final String ACTIVEMQ_URL = "tcp://192.168.64.129:61616";
    // 消息队列名称
    public static final String QUEUE_NAME = "queue-delay-schedule";
    /**
     * 延迟投递的时间
     */
    private static final long DELAY = 3 * 1000;
    /**
     * 每次投递的时间间隔
     */
    private static final long PERIOD = 4 * 1000;
    /**
     * 投递的次数
     */
    private static final int REPEAT = 5;

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
        // 5.创建消息的生产者，一向上转型为ActiveMQMessageProducer
        ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer) session.createProducer(queue);

        // 6.通过消息生产者生产6条消息发送MQ队列
        for (int i = 0; i < 3; i++) {
            // 7.创建消息
            TextMessage textMessage = session.createTextMessage("延迟投递和定时投递消息" + i + ":hello world\n");
            // 给消息设置一个唯一的id可以知道哪条消息发送成功，哪条消息发送失败
            textMessage.setJMSMessageID(UUID.randomUUID().toString());
            String msgId = textMessage.getJMSMessageID();
            // 延迟投递的时间
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, DELAY);
            // 每次投递的时间间隔
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, PERIOD);
            // 投递的次数
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, REPEAT);
            // 8.将消息发送到MQ，并回调判断消息是否发送成功
            activeMQMessageProducer.send(textMessage);
        }
        // 9.关闭资源
        activeMQMessageProducer.close();
        session.close();
        connection.close();
        System.out.println("finish");
    }
}
