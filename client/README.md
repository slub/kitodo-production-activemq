# Kitodo.Production ActiveMQ Client

Client to send messages to the Kitodo ActiveMQ. Only tickets can be closed with the existing behavior of the client atm.

## Usage

### FinalizeTaskQueue

```java
java -jar kitodo-activemq-client-X.jar tcp://localhost:61616?closeAsync=false "FinalizeTaskQueue" TaskID Message
```

### TaskActionQueue

```java
java -jar kitodo-activemq-client-X.jar tcp://localhost:61616?closeAsync=false "TaskActionQueue" TaskID Message TaskAction (CorrectionTaskID)
```
