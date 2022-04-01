/*
 * (c) Kitodo. Key to digital objects e. V. <contact@kitodo.org>
 *
 * This file is part of the Kitodo project.
 *
 * It is licensed under GNU General Public License version 3 or later.
 *
 * For the full copyright and license information, please read the
 * GPL3-License.txt file that was distributed with this source code.
 */

package org.kitodo.client;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The client produces specific Kitodo messages and sends them to the ActiveMQ.
 */
public class KitodoActiveMQClient {

    private static final Logger logger = LogManager.getLogger(KitodoActiveMQClient.class);

    /**
     * When the client called this main function is automatically executed. The
     * following arguments are required: url of Active MQ, name of destination,
     * queue, id of task, comment that appears on the process when the task is
     * closed
     *
     * @param args
     *            The arguments to run.
     */
    public static void main(String[] args) {
        try {
            String url = args[0], queue = args[1], taskId = args[2], message = args[3];
            Connection connection = new ActiveMQConnectionFactory(url).createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queue);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("id", taskId);
            mapMessage.setString("message", message);

            logger.debug("Send message to url '" + url + "' destination queue '" + queue + "' and task id " + taskId
                    + " and message '" + message + "'");

            producer.send(mapMessage);

            logger.info("Sending of message to close taskId " + taskId + " successful");

            session.close();
            connection.close();
        } catch (JMSException e) {
            logger.error("Exception while building or sending message.", e);
        }
    }

}
