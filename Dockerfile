FROM openjdk:11-jre-slim-bullseye

MAINTAINER markus.weigelt@slub-dresden.de

ARG ACTIVEMQ_VERSION=5.17.3
ARG ACTIVEMQ=apache-activemq-$ACTIVEMQ_VERSION
ENV ACTIVEMQ_VERSION_SHA512=8ed682fd8c9910f186cc14872552fcb1b5e8d361466b880c92dbd8a0f3a8fba339047023891e027ece7f57663a271fe47e73358e0290b371e78e5bfe70387468

ENV ACTIVEMQ_HOME /opt/activemq
ENV ACTIVEMQ_TCP=61616
ENV ACTIVEMQ_AMQP=5672
ENV ACTIVEMQ_STOMP=61613
ENV ACTIVEMQ_MQTT=1883
ENV ACTIVEMQ_WS=61614
ENV ACTIVEMQ_UI=8161

LABEL \
    maintainer="https://slub-dresden.de" \
    org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url=$VCS_URL \
    org.label-schema.build-date=$BUILD_DATE

# make apt system functional
RUN apt-get update && \
    apt-get install -y apt-utils curl nano && \
    apt-get clean

RUN set -x && \
    mkdir -p /opt && \
    curl https://archive.apache.org/dist/activemq/$ACTIVEMQ_VERSION/$ACTIVEMQ-bin.tar.gz -o $ACTIVEMQ-bin.tar.gz

# Validate checksum
RUN if [ "$ACTIVEMQ_VERSION_SHA512" != "$(sha512sum $ACTIVEMQ-bin.tar.gz | awk '{print($1)}')" ];\
    then \
        echo "sha512 values doesn't match! exiting."  && \
        exit 1; \
    fi;

RUN tar xzf $ACTIVEMQ-bin.tar.gz -C /opt && \
    ln -s /opt/$ACTIVEMQ $ACTIVEMQ_HOME && \
    adduser --system --group --no-create-home --home $ACTIVEMQ_HOME activemq && \
    chown -R activemq:activemq /opt/$ACTIVEMQ && \
    chown -h activemq:activemq $ACTIVEMQ_HOME && \
	rm $ACTIVEMQ-bin.tar.gz

COPY ./kitodo-activemq.xml /opt/activemq/conf/activemq.xml

USER activemq

WORKDIR $ACTIVEMQ_HOME
EXPOSE $ACTIVEMQ_TCP $ACTIVEMQ_AMQP $ACTIVEMQ_STOMP $ACTIVEMQ_MQTT $ACTIVEMQ_WS $ACTIVEMQ_UI

CMD ["bash", "-c", "bin/activemq console"]
