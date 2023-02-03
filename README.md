# Kitodo-Production ActiveMQ

This repository provides on the one hand an ActiveMQ image and on the other hand a client to send messages to Kitodo.

## Docker Image

By default, the Docker image is provided with the following ActiveMQ configuration.

https://github.com/slub/kitodo-production-activemq/blob/main/kitodo-activemq.xml

If you want to change this configuration then you can overwrite it with a bind mount and the target `/opt/activemq/conf/activemq.xml`.

With the use of Docker Compose, the call looks like this:

```
        volumes:   
            - type: bind
              source: ./kitodo-activemq.xml
              target: /opt/activemq/conf/activemq.xml
```
 
## Secured usage

Currently we use the ActiveMQ without authentication and authorization. You can find more information about the secured usage on the following page "(Securing access to ActiveMQ)[https://github.com/kitodo/kitodo-production/wiki/Developer_3.x-Getting-started-ActiveMQ]" or in the comments of the kitodo_config.properties.

## Using Client

There is a [client](https://github.com/slub/kitodo-production-activemq/blob/main/client/README.md) in this repository for using ActiveMQ. 

## Further informations

* https://github.com/kitodo/kitodo-production/wiki/Developer_3.x-Active-MQ
