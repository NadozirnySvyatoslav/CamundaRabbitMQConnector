# Camunda RabbitMQ Connector



## Test Sending message to RabbitMQ

```
#!/bin/sh

export LIBS=libs/amqp-client-5.7.3.jar:libs/slf4j-api-1.7.26.jar:target/rabbitmq-connector-1.0-SNAPSHOT.jar
export RABBIT_URI=amqp://localhost/%2F
export QUEUE_NAME=test124
java -cp $LIBS com.ktc.rabbitmqConnector.Send RABBIT_URI QUEUE_NAME
```


## Test Receiving message from RabbitMQ

```
#!/bin/sh

export LIBS=libs/amqp-client-5.7.3.jar:libs/slf4j-api-1.7.26.jar:target/rabbitmq-connector-1.0-SNAPSHOT.jar
export RABBIT_URI=amqp://localhost/%2F
export QUEUE_NAME=test124
java -cp $LIBS com.ktc.rabbitmqConnector.Receiver RABBIT_URI QUEUE_NAME
```


