package com.csgg.test.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMqProductorTest {
	static String exchange = "source";
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory cf = new ConnectionFactory();
//		cf.setAutomaticRecoveryEnabled(false);
		cf.setAutomaticRecoveryEnabled(true);
		
		Connection cn = cf.newConnection();
		
		Channel cnl = cn.createChannel();
		
		String message = "xxxxx";
		cnl.exchangeDeclare(exchange, "fanout");
		cnl.basicPublish(exchange, "", null, message.getBytes());
		
		cnl.close();
		cn.close();
	}
}
