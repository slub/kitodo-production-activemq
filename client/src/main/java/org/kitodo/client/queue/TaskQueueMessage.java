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

    public static final String KEY_TASK_ID = "id";
    public static final String KEY_MESSAGE = "message";

    private String taskId;

    private String message;

    TaskQueueMessage(String taskId, String message) {
        this.taskId = taskId;
        this.message = message;
    }

    public MapMessage createMessage( Session session ) throws JMSException {
        MapMessage mapMessage = session.createMapMessage();
        mapMessage.setString(KEY_TASK_ID, taskId);
        mapMessage.setString(KEY_MESSAGE, message);
        return mapMessage;
    }

    public abstract String getQueueName();

    @Override
    public String toString() {
        return KEY_TASK_ID + "='" + taskId + "'," + KEY_MESSAGE + "='" + message + "'";
    }
}
