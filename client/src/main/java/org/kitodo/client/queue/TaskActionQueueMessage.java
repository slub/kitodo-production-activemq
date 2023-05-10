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

public class TaskActionQueueMessage extends TaskQueueMessage {

    public enum TaskAction {
        /**
         * Adds a comment to the task.
         */
        COMMENT,

        /**
         * Lock a task and add an error comment when task status is OPEN or INWORK.
         */
        ERROR_OPEN,

        /**
         * Set task status of locked task to OPEN.
         */
        ERROR_CLOSE,

        /**
         * Set task status of open task to INWORK.
         */
        PROCESS,

        /**
         * Close a task.
         */
        CLOSE
    }

    public static final String KEY_TASK_ACTION = "action";

    public static final String KEY_CORRECTION_TASK_ID = "correctionTaskId";

    private TaskAction taskAction;

    private String correctionTaskId;

    public TaskActionQueueMessage(String taskId, String message, TaskAction taskAction) {
        super(taskId, message);
        this.taskAction = taskAction;
    }

    public MapMessage createMessage(Session session) throws JMSException {
        MapMessage mapMessage = super.createMessage(session);
        mapMessage.setString(KEY_TASK_ACTION, taskAction.name());
        if (nonNull(correctionTaskId)) {
            mapMessage.setString(KEY_CORRECTION_TASK_ID, correctionTaskId);
        }
        return mapMessage;
    }

    @Override
    public String getQueueName() {
        return "KitodoProduction.TaskAction.Queue";
    }

    public void setCorrectionTaskId(String correctionTaskId) {
        this.correctionTaskId = correctionTaskId;
    }

    @Override
    public String toString() {
        String result = super.toString() +  "," + KEY_TASK_ACTION + "='" + taskAction + "'";
        if (nonNull(correctionTaskId)) {
            result += "," + KEY_CORRECTION_TASK_ID + "='" + correctionTaskId + "'";
        }
        return result;
    }
}
