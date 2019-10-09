package com.ktc.rabbitmq;

import org.camunda.connect.impl.AbstractConnectorRequest;
import org.camunda.connect.spi.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RMQRequest extends AbstractConnectorRequest<RMQResponse> {
   private final static Logger LOGGER = LoggerFactory.getLogger(RMQConnector.class);

 public RMQRequest(Connector<?> connector) {
    super(connector);
  }

  @Override
  protected boolean isRequestValid() {

     String url = getRequestParameter("url");

    if(url == null ) {
      LOGGER.warn("invalid request: missing parameter 'url' in {}", this);
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "RMQRequest [url=" + getRequestParameter("url")+ "]";
  }
}

