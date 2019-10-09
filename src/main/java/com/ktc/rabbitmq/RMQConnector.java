package com.ktc.rabbitmq;


import org.camunda.connect.Connectors;
import org.camunda.connect.spi.Connector;
import org.camunda.connect.impl.AbstractConnector;
import org.camunda.connect.spi.ConnectorResponse;

import java.io.Closeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RMQConnector extends AbstractConnector<RMQRequest,RMQEmptyResponse> {
    public static final String ID = "rabbitmq";
    private final static Logger LOGGER = LoggerFactory.getLogger(RMQConnector.class);

  public RMQConnector() {
    super(ID);
  }

  public RMQRequest createRequest() {
    return new RMQRequest(this);
  }

 @Override
  public ConnectorResponse execute(RMQRequest request) {
    try{
	RMQInvocation invocation=new RMQInvocation(request,request, requestInterceptors);
	invocation.proceed();
    }catch (Exception e) {
	LOGGER.error("RMQ exception: "+ e.getMessage());
	throw new RMQException("RMQ Connector execution error ",e);
    }
    return new RMQEmptyResponse();

  }
}
