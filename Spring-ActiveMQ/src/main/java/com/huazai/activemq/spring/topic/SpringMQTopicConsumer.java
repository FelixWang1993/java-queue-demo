package com.huazai.activemq.spring.topic;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * @author pyh
 * @date 2021/01/11 23:40
 */
@Service
public class SpringMQTopicConsumer {
	private final JmsTemplate jmsTemplate;

	public SpringMQTopicConsumer(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		SpringMQTopicConsumer springMQConsumer = applicationContext.getBean(SpringMQTopicConsumer.class);
		JmsTemplate jmsTemplate = springMQConsumer.jmsTemplate;
		// applicationContext.xml的默认目的地配置为队列，所以此处需要用代码修改默认目的地
		jmsTemplate.setDefaultDestination(((Destination) applicationContext.getBean("destinationTopic")));
//		jmsTemplate.setDestinationResolver((session, s, b) -> session.createTopic("spring-test-topic"));
		while (true) {
			String returnValue = (String) jmsTemplate .receiveAndConvert();
			if ("exit-".equals(returnValue)) {
				System.exit( -1);
			}
			System.out.println("****消费者收到的消息:   " + returnValue);
		}
	}
}

