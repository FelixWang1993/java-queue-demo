package com.huazai.activemq.spring.topic;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.Scanner;

/**
 * @author pyh
 * @date 2021/01/11 23:41
 */
@Service
public class SpringMQTopicProducer {
	static final Scanner input = new Scanner(System.in);

	private final JmsTemplate jmsTemplate;

	public SpringMQTopicProducer(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		SpringMQTopicProducer springMQ_topic_producer = applicationContext.getBean(SpringMQTopicProducer.class);
		//直接调用application.xml里面创建的destinationTopic这个bean设置为目的地就行了
		JmsTemplate jmsTemplate = springMQ_topic_producer.jmsTemplate;
		// applicationContext.xml的默认目的地配置为队列，所以此处需要用代码修改默认目的地
		jmsTemplate.setDefaultDestination(((Destination) applicationContext.getBean("destinationTopic")));
//		jmsTemplate.setDestinationResolver((session, s, b) -> session.createTopic("spring-test-topic"));
		jmsTemplate.send(session -> session.createTextMessage("***Spring和ActiveMQ的整合TopicCase111....."));
		System.out.println("********send task over");
		while(true) {
			System.out.println("\n请输入要发送的内容");
			final String msgText = input.next();
			if ("exit".equals(msgText)) {
				System.exit(-1);
			} else {
				jmsTemplate.send(session -> session.createTextMessage(msgText));
				System.out.println(msgText + "\t->|\t发送完成");
			}
		}
	}
}


