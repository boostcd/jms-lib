package com.estafet.boostcd.commons.lib.jms;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class TopicConsumer extends AbstractTopic {

	Connection connection;
	MessageConsumer messageConsumer;
	String destination;

	public TopicConsumer(String destination) {
		try {
			this.destination = destination;
			connection = createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(destination);
			messageConsumer = session.createConsumer(topic);
			connection.start();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T consume(Class<T> clazz) {
		try {
			return new ObjectMapper().readValue(((TextMessage) messageConsumer.receive(3000)).getText(), clazz);
		} catch (IOException | JMSException e) {
			throw new RuntimeException(e);
		}
	}
}