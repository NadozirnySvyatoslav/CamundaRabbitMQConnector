package com.ktc.rabbitmqConnector;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.util.concurrent.TimeoutException;

public class Receiver {

    public static void main(String[] argv) throws Exception,TimeoutException {

    if (argv.length != 2) {
	System.out.println("Usage amqp://user:pass@host:port/virtualhost Queue_name");
        System.exit(1);
    }
        String QUEUE_NAME = argv[1];
	String url=argv[0];
        ConnectionFactory factory = new ConnectionFactory();
	factory.setUri(url);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
	    try{
	    channel.close();
	    connection.close();
	    }catch(Exception e){
	    }
        } ;
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }
}