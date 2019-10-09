package com.ktc.rabbitmq;

import java.util.Arrays;
import java.util.List;

import org.camunda.connect.impl.AbstractRequestInvocation;
import org.camunda.connect.spi.ConnectorRequestInterceptor;

public class RMQInvocation extends AbstractRequestInvocation<RMQRequest> {

  public RMQInvocation(RMQRequest target, RMQRequest request, List<ConnectorRequestInterceptor> requestInterceptors){
    super(target, request, requestInterceptors);
  }

  @Override
  public Object invokeTarget() throws Exception {
    String message="Hello world!";
    //
    // connect , channel 
    //	
    return message;
  }

}
