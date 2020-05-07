package com.ktc.rabbitmq;

import org.camunda.connect.impl.AbstractConnectorRequest;
import org.camunda.connect.spi.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rabbitmq.client.*;


public class RMQRequest extends AbstractConnectorRequest<RMQEmptyResponse> {
   private final static Logger LOGGER = LoggerFactory.getLogger(RMQConnector.class);
   private AMQP.BasicProperties properties;

 public RMQRequest(Connector<?> connector) {
    super(connector);
  }

 public AMQP.BasicProperties getProperties() {
    return properties;
  }

 public String getUrl() {
    return getRequestParameter("url");
  }

 public String getSync() {
    return getRequestParameter("sync");
  }

 public String getMessage() {
    return getRequestParameter("message");
  }

 public String getRoutingKey() {
    return getRequestParameter("routingKey");
  }

 public String getExchange() {
    return getRequestParameter("exchange");
  }

 public String getQueue() {
    return getRequestParameter("queue");
  }

 public String getAppId() {
    return getRequestParameter("appId");
  }

 public String getDeliveryMode() {
    return getRequestParameter("deliveryMode");
  }

 public String getPriority() {
    return getRequestParameter("priority");
  }

 public String getContentType() {
    return getRequestParameter("contentType");
  }

 public String getContentEncoding() {
    return getRequestParameter("contentEncoding");
  }

 public String getType() {
    return getRequestParameter("type");
  }
 public String getReplyTo() {
    return getRequestParameter("replyTo");
  }

 public String getCorrelationId() {
    return getRequestParameter("correlationId");
  }
 public String getMessageId() {
    return getRequestParameter("messageId");
  }

 public void setReplyTo(String ReplyTo) {
    setRequestParameter("replyTo",ReplyTo);
  }


  @Override
  protected boolean isRequestValid() {

    AMQP.BasicProperties.Builder prop_builder=new AMQP.BasicProperties.Builder();

    if(getUrl() == null ) {
      LOGGER.warn("parameter 'url' was set to 'amqp:////' {}", this);
      setRequestParameter("url","amqp:////");
    }

    if(getExchange() == null ) {
      setRequestParameter("exchange","");
    }

    if(getQueue() == null ) {
      setRequestParameter("queue","");
    }

    if(getRoutingKey() == null ) {
      setRequestParameter("routingKey","");
    }

    if(getAppId() != null ){
	prop_builder.appId(getAppId());
    }

    if(getContentType() != null ) {
        prop_builder.contentType(getContentType());
    }

    if(getContentEncoding() != null ) {
        prop_builder.contentEncoding(getContentEncoding());
    }

    if(getDeliveryMode() != null ) {
	try{
    	    prop_builder.deliveryMode(Integer.parseInt(getDeliveryMode()));
	}catch(Exception e){
          LOGGER.warn("Error parameter 'deliveryMode' was set to '2' {}", this);
	  setRequestParameter("deliveryMode",2);
	}
    }

    if(getPriority() != null ) {
	try{
    	    prop_builder.priority(Integer.parseInt(getPriority()));
	}catch(Exception e){
          LOGGER.warn("Error parameter 'priority' was set to '1' {}", this);
	  setRequestParameter("priority",1);
	}
    }

    if(getType() != null ) {
        prop_builder.type(getType());
    }

    if(getReplyTo() != null ) {
        prop_builder.replyTo(getReplyTo());
    }

    if(getCorrelationId() != null ) {
        prop_builder.correlationId(getCorrelationId());
    }

    if(getMessageId() != null ) {
        prop_builder.messageId(getMessageId());
    }
    properties=prop_builder.build();
    return true;
  }

  @Override
  public String toString() {
    return "RMQRequest [url=" + getRequestParameter("url")+ "]";
  }
}

