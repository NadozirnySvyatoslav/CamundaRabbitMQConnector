package com.ktc.rabbitmqConnector;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;

import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Send
{

    public static void main( String[] args ) 
	throws java.security.NoSuchAlgorithmException,
	IOException,
	java.util.concurrent.TimeoutException,
	java.net.URISyntaxException,
	java.security.KeyManagementException
    {

    String url=args[0];
    String QUEUE_NAME = args[1];

    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri(url);
    Connection connection = factory.newConnection(); 
    Channel channel = connection.createChannel();
    
    String message = "";
//    channel.queueDeclare(QUEUE_NAME, true, false, false, null);

    BufferedReader in = null;
    try {
        in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
	    message=message.concat(line);
        }
     }
    catch (IOException e) {
        System.err.println("IOException reading System.in " +e.getMessage());
    }
    finally {
        if (in != null) {
            in.close();
        }
    }
    channel.basicPublish("", QUEUE_NAME, new AMQP.BasicProperties.Builder()
               .contentType("application/json")
	       .contentEncoding("UTF-8")
               .deliveryMode(2)
               .priority(1)
	       .type("bpmn-test")
		.appId("yt11")
               .build(), message.getBytes("UTF-8"));
    System.out.println(" [x] Sent:\n" + message +"\n");
    channel.close();
    connection.close();
    }
}
