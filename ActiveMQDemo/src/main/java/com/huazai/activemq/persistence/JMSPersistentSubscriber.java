package com.huazai.activemq.persistence;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.xml.soap.Text;

/**
 * @author pyh
 * @date 2021/1/5 22:00
 */
public class JMSPersistentSubscriber {
    // ActiveMQ服务地址
    public static final String ACTIVEMQ_URL = "tcp://192.168.64.129:61616";
    // 主题名称
    public static final String TOPIC_NAME = "persistent-topic";
    public static void main(String[] args) throws Exception {
        //1.创建连接工厂，按照给定的URL，采用默认的用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂,获得connection,设置connectionID
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("王五");
//        connection.start();
        //3.创建会话session
        //两个参数transacted=事务,acknowledgeMode=确认模式(签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地(具体是队列queue还是主题topic)
        Topic topic = session.createTopic(TOPIC_NAME);
        //5.通过session创建持久化订阅
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "我是王五");
        //6.启动连接
        connection.start();
        //7.接收消息
        Message message;
        do {
            message = topicSubscriber.receive();
            TextMessage textMessage = (TextMessage) message;
            System.out.println(textMessage.getText());
        }while (message != null);
        // 9.关闭资源
        topicSubscriber.close();
        session.close();
        connection.close();
        System.out.println("finish");

        /**
         * 一定要先运行一次消费者,类似于像MQ注册,我订阅了这个主题
         * 然后再运行主题生产者
         * 无论消费着是否在线,都会接收到,在线的立即接收到,不在线的等下次上线把没接收到的接收

         也就是：先启动消费者（A,B），然后启动生产者生产消息。消费者消费之后，关闭消费者B，再生产消息。此时B是离线状态的，A在线，所以A立刻消费了消息。此时启动消费者B，B也可以消费之前的消息。
         */

    }

}
