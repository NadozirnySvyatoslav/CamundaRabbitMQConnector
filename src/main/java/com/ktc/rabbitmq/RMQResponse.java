package com.ktc.rabbitmq;

import java.util.Map;
import org.camunda.connect.spi.CloseableConnectorResponse;

import org.camunda.connect.impl.AbstractConnectorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RMQResponse extends AbstractConnectorResponse {
  private static final Logger LOGGER = LoggerFactory.getLogger(RMQResponse.class);
  String message;

  public RMQResponse(String message){
    this.message=message;
  }

  @Override
  protected void collectResponseParameters(Map<String, Object> responseParameters) {
    responseParameters.put("message", this.message);
  }
  @Override
  public String toString() {
    return "RQMResponse [message=" + this.message + "]";
  }


}
