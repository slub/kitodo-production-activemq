package org.kitodo.client;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class KitodoActiveMQClient {

    public static void main(String[] args) {
        try {
            Connection connection = new ActiveMQConnectionFactory(args[0]).createConnection();

            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(args[1]);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create job ticket
            MapMessage message = session.createMapMessage();
            message.setString("id", args[2]);
            message.setString("message", args[3]);

            // Send job ticket
            producer.send(message);

            // Shutdown
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
