package com.ktc.rabbitmq;

public class RMQException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public RMQException(String message) {
    super(message);
  }

  public RMQException(Throwable cause) {
    super(cause);
  }

  public RMQException(String message, Throwable cause) {
    super(message, cause);
  }

}

