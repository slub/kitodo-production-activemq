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

import static java.util.Objects.nonNull;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;

public class TaskStateQueueMessage extends TaskQueueMessage {

    public static final String STATE_KEY = "state";

    public static final String CORRECTION_TASK_ID_KEY = "correctionTaskId";

    private String state;

    private String correctionTaskId;

    public TaskStateQueueMessage(String taskId, String message, String state) {
        super(taskId, message);
        this.state = state;
    }

    public MapMessage createMessage(Session session) throws JMSException {
        MapMessage mapMessage = super.createMessage(session);
        mapMessage.setString(STATE_KEY, state);
        if (nonNull(correctionTaskId)) {
            mapMessage.setString(CORRECTION_TASK_ID_KEY, correctionTaskId);
        }
        return mapMessage;
    }

    @Override
    public String getQueueName() {
        return "KitodoProduction.TaskState.Queue";
    }

    public void setCorrectionTaskId(String correctionTaskId) {
        this.correctionTaskId = correctionTaskId;
    }

    @Override
    public String toString() {
        String result = super.toString() +  "," + STATE_KEY + "='" + state + "'";
        if (nonNull(correctionTaskId)) {
            result += "," + CORRECTION_TASK_ID_KEY + "='" + correctionTaskId + "'";
        }
        return result;
    }
}
