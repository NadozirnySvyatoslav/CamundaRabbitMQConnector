package com.ktc.rabbitmq;

import java.util.Arrays;
import java.util.List;
import org.camunda.connect.impl.AbstractRequestInvocation;
import org.camunda.connect.spi.ConnectorRequestInterceptor;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RMQInvocation extends AbstractRequestInvocation<RMQRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RMQInvocation.class);
    private RMQRequest request;
  public RMQInvocation(RMQRequest target, RMQRequest request, List<ConnectorRequestInterceptor> requestInterceptors){
    super(target, request, requestInterceptors);
    this.request=request;
  }

  @Override
  public Object invokeTarget() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri(request.getUrl());
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.basicPublish(request.getExchange(), 
			request.getRoutingKey(), 
			request.getProperties(),
			request.getMessage().getBytes("UTF-8"));
    LOGGER.debug(" [x] RMQ send '{}'", request.getMessage());
    channel.close();
    connection.close();
    return null;
  }

}
