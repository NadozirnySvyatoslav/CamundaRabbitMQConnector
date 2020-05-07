package com.ktc.rabbitmq;

import java.util.Arrays;
import java.util.List;
import org.camunda.connect.impl.AbstractRequestInvocation;
import org.camunda.connect.spi.ConnectorRequestInterceptor;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.io.IOException;





public class RMQInvocation extends AbstractRequestInvocation<RMQRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RMQInvocation.class);
    private RMQRequest request;
    private RMQResponse response;
    public RMQInvocation(RMQRequest target, RMQRequest request, List<ConnectorRequestInterceptor> requestInterceptors){
    super(target, request, requestInterceptors);
    this.request=request;
  }

  private String randomString(int size){
    String s="";
    String chars="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    Random rnd=new Random();
    for(int i=0;i<size;i++){
        s=s+chars.charAt(rnd.nextInt( chars.length() ));
    }
    return s;
  }

  @Override
  public Object invokeTarget() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri(request.getUrl());
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    String tmp_queue="camunda."+randomString(32);

    if (request.getSync().matches("true") )  {
        AMQP.BasicProperties props=request.getProperties().builder().replyTo(tmp_queue).build();
        channel.queueDeclare(tmp_queue, false, false, false, null);
        channel.basicPublish(request.getExchange(), 
			request.getRoutingKey(), 
            props,
			request.getMessage().getBytes("UTF-8"));
        LOGGER.debug(" [x] RMQ send Sync "+tmp_queue+" '{}'", request.getMessage());

        final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);
            Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                response.offer(new String(body, "UTF-8"));
            }
        };

        channel.basicConsume(tmp_queue, true, consumer);
        String message=response.take();
        LOGGER.debug(" [x] received Sync "+tmp_queue+" '" + message + "'");
        channel.queueDelete(tmp_queue);
        return new RMQResponse(message);
    }else{
        channel.basicPublish(request.getExchange(), 
			request.getRoutingKey(), 
			request.getProperties(),
			request.getMessage().getBytes("UTF-8"));
        LOGGER.debug(" [x] RMQ send Async '{}'", request.getMessage());
    }
    channel.close();
    connection.close();
    return null;
  }
}
