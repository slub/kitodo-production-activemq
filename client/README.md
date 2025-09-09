# Kitodo.Production ActiveMQ Client

Client to send messages to the Kitodo ActiveMQ.

## Usage

### FinalizeTaskQueue (Legacy)

Close a specific task and assign additional properties (see https://github.com/kitodo/kitodo-production/wiki/Developer_3.x-Active-MQ#finalizestep-queue)
).

```java
java -jar kitodo-activemq-client-X.jar tcp://localhost:61616?closeAsync=false "FinalizeTaskQueue" TaskID Message
```

### TaskActionQueue

Task actions (https://github.com/kitodo/kitodo-production/wiki/Developer_3.x-Active-MQ#task-action
) make it possible to execute certain behaviors, for example setting the state of a task or adding a comment (see https://github.com/kitodo/kitodo-production/wiki/Developer_3.x-Active-MQ#taskaction-queue
).

```java
java -jar kitodo-activemq-client-X.jar tcp://localhost:61616?closeAsync=false "TaskActionQueue" TaskID Message TaskAction (CorrectionTaskID)
```
