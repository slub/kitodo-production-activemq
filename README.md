# Kitodo.Production ActiveMQ Client

Client to send messages to the Kitodo ActiveMQ. Only tickets can be closed with the existing behavior of the client atm.

## Usage

```java
java -jar kitodo-activemq-client-X.jar tcp://localhost:61616?closeAsync=false "KitodoProduction.FinalizeStep.Queue" TaskID Message
```

Client is not yet ready for production use.
