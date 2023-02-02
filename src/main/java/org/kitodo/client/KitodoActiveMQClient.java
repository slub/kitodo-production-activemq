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
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kitodo.client.queue.FinalizeTaskQueueMessage;
import org.kitodo.client.queue.TaskQueueMessage;
import org.kitodo.client.queue.TaskStateQueueMessage;

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

            TaskQueueMessage taskQueue;
            switch (queue) {
                case "FinalizeTaskQueue":
                    taskQueue = new FinalizeTaskQueueMessage(taskId, message);
                    logger.debug("Send message to url '" + url + "' destination queue '" + queue + "' and task id " + taskId
                            + " and message '" + message + "'");
                    break;
                case "TaskStateQueue":
                    String state=args[4];
                    taskQueue = new TaskStateQueueMessage(taskId, message, state);
                    if(args.length == 6) {
                        ((TaskStateQueueMessage) taskQueue).setCorrectionTaskId(args[5]);
                    }
                    break;
                default:
                    return;
            }

            logger.debug("Send message '" + taskQueue + "' to url '" + url + "' destination queue '" + taskQueue.getQueueName() + "'");

            Connection connection = new ActiveMQConnectionFactory(url).createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(taskQueue.getQueueName());
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.send(taskQueue.createMessage(session));

            logger.info("Sending of message for taskId='" + taskId + "' was successful");

            session.close();
            connection.close();

        } catch (JMSException e) {
            logger.error("Exception while building or sending message.", e);
        }
    }

}
