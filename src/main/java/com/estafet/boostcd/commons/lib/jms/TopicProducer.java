package com.estafet.boostcd.commons.lib.jms;

import java.util.UUID;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public abstract class TopicProducer extends AbstractTopic {

	String topicName;
	Connection connection;
	Session session;
	
	public TopicProducer(String topicName) {
		this.topicName = topicName;
	}

	public void sendMessage(String message) {
		try {
			Topic topic = createTopic();
			MessageProducer messageProducer = session.createProducer(topic);
			TextMessage textMessage = session.createTextMessage(message);
			textMessage.setStringProperty("message.event.interaction.reference", UUID.randomUUID().toString());
			messageProducer.send(textMessage);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				connection.close();
				Thread.sleep(3000);
			} catch (JMSException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private Topic createTopic() throws JMSException {
		connection = createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic(topicName);
		return topic;
	}

}