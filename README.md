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


## Camunda Usage

### Implement connector into the server
1. Add to /var/lib/tomcat9/conf/logging.properties
```
com.ktc.rabbitmq.level = FINE
com.ktc.rabbitmq.prefix = rabbitmq.
com.ktc.rabbitmq.directory = ${catalina.base}/logs
com.ktc.rabbitmq.maxDays = 90
```

2. Put  rabbitmq-connector-1.0-SNAPSHOT.jar  to /var/lib/tomcat9/lib 

3. Restart Tomcat server

4. Check for logs in /var/lib/tomcat9/logs/catalina.*

```
09-Oct-2019 07:41:30.341 INFO [main] org.camunda.commons.logging.BaseLogger.logInfo ENGINE-12003 Plugin 'ConnectProcessEnginePlugin' activated on process engine 'default'
09-Oct-2019 07:41:30.345 INFO [main] org.camunda.commons.logging.BaseLogger.logInfo CNCT-01004 Discovered provider for connector id 'rabbitmq' and class 'com.ktc.rabbitmq.RMQConnector': 'com.ktc.rabbitmq.RMQConnectorProvider'
09-Oct-2019 07:41:30.521 INFO [main] org.camunda.commons.logging.BaseLogger.logInfo CNCT-01004 Discovered provider for connector id 'http-connector' and class 'org.camunda.connect.httpclient.impl.HttpConnectorImpl': 'org.camunda.connect.httpclient.impl.HttpConnectorProviderImpl'
09-Oct-2019 07:41:30.525 INFO [main] org.camunda.commons.logging.BaseLogger.logInfo CNCT-01004 Discovered provider for connector id 'soap-http-connector' and class 'org.camunda.connect.httpclient.soap.impl.SoapHttpConnectorImpl': 'org.camunda.connect.httpclient.soap.impl.SoapHttpConnectorProviderImpl'
```

Line "Discovered provider for connector id 'rabbitmq' and class 'com.ktc.rabbitmq.RMQConnector': 'com.ktc.rabbitmq.RMQConnectorProvider'" should be present without errors
Deploy BPMN, might be used from demo/demo.bpmn

![BPMN process](/demo/demo_bpmn.png)

### Test connector


2. Run listener (look Receiving message from RabbitMQ) or RabbitMQ Control Panel

3. Trigger message
```
#!/bin/sh

export headers="-H \"Content-Type: application/json\""
export UUID=$(cat /proc/sys/kernel/random/uuid)
export payload='{"messageName" : "SendToRabbit", "businessKey" : "'$UUID'", "processVariables":{}}'
export url=http://localhost:8080/engine-rest/message
echo [x] $UUID
curl -H 'Content-Type: application/json' $url -d "$payload"
```

4. Check listener and camunda logs
```
09-Oct-2019 08:08:00.571 WARNING [http-nio-8080-exec-1] com.ktc.rabbitmq.RMQRequest.isRequestValid parameter 'exchange' was set to '' RMQRequest [url=amqp://localhost/%2F]
09-Oct-2019 08:31:50.105 FINE [http-nio-8080-exec-1] com.ktc.rabbitmq.RMQInvocation.invokeTarget  [x] RMQ send 'TEST from Camunda'
```

## Configuration of "rabbitmq" Connector

Input parameters for the connector:

url - URI in format amqp://user:pass@host:port/virtualhost, mandatory
exchange - name, default ""
queue - name, mandatory
routingKey - key name
appId - application id
deliveryMode - default=2
contentType -
contentEncoding -
priority - default=1
type -
replyTo -
correlationId -
messageId -
message - data to put in queue, mandatory
