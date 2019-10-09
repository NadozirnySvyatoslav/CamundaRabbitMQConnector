package com.ktc.rabbitmq;

import org.camunda.connect.spi.ConnectorProvider;

public class RMQConnectorProvider implements ConnectorProvider{
  public String getConnectorId() {
    return RMQConnector.ID;
  }

  public RMQConnector createConnectorInstance() {
    return new RMQConnector();
  }

}
