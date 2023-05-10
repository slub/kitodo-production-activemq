FROM openjdk:11-jre-slim-bullseye

ARG ACTIVEMQ_VERSION=5.17.4
ARG ACTIVEMQ=apache-activemq-$ACTIVEMQ_VERSION

ENV ACTIVEMQ_HOME=/opt/activemq
ENV ACTIVEMQ_TCP=61616
ENV ACTIVEMQ_AMQP=5672
ENV ACTIVEMQ_STOMP=61613
ENV ACTIVEMQ_MQTT=1883
ENV ACTIVEMQ_WS=61614
ENV ACTIVEMQ_UI=8161

LABEL \
    maintainer="https://slub-dresden.de" \
    org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url="https://github.com/slub/kitodo-production-activemq" \
    org.label-schema.build-date=$BUILD_DATE

RUN apt-get update && \
    apt-get install -y apt-utils curl && \
    apt-get clean

RUN mkdir -p /opt && \
    curl -s -S https://archive.apache.org/dist/activemq/$ACTIVEMQ_VERSION/$ACTIVEMQ-bin.tar.gz -o $ACTIVEMQ-bin.tar.gz && \
    tar -zxvf $ACTIVEMQ-bin.tar.gz -C /opt
  
RUN mv /opt/$ACTIVEMQ $ACTIVEMQ_HOME && \
    adduser --system --group --no-create-home --home $ACTIVEMQ_HOME activemq && \
    chown -R activemq:activemq $ACTIVEMQ_HOME && \
    rm $ACTIVEMQ-bin.tar.gz

COPY ./kitodo-activemq.xml /opt/activemq/conf/activemq.xml

USER activemq

WORKDIR $ACTIVEMQ_HOME

EXPOSE $ACTIVEMQ_TCP $ACTIVEMQ_AMQP $ACTIVEMQ_STOMP $ACTIVEMQ_MQTT $ACTIVEMQ_WS $ACTIVEMQ_UI

CMD ["bash", "-c", "bin/activemq console"]