package com.estafet.boostcd.commons.lib.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.estafet.boostcd.commons.lib.properties.PropertyUtils;

public class AbstractTopic {

	private PropertyUtils props = PropertyUtils.instance();

	protected Connection createConnection() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(props.getProperty("jboss.amq.broker.url"));
		return connectionFactory.createConnection(props.getProperty("jboss.amq.broker.user"),
				props.getProperty("jboss.amq.broker.password"));
	}

}
