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

package org.kitodo.client.queue;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;

public abstract class TaskQueueMessage {

    public static final String TASK_ID_KEY = "id";
    public static final String MESSAGE_KEY = "message";

    private String taskId;

    private String message;

    TaskQueueMessage(String taskId, String message) {
        this.taskId = taskId;
        this.message = message;
    }


    public MapMessage createMessage( Session session ) throws JMSException {
        MapMessage mapMessage = session.createMapMessage();
        mapMessage.setString(TASK_ID_KEY, taskId);
        mapMessage.setString(MESSAGE_KEY, message);
        return mapMessage;
    }

    public abstract String getQueueName();

    @Override
    public String toString() {
        return TASK_ID_KEY + "='" + taskId + "'," + MESSAGE_KEY + "='" + message + "'";
    }
}
