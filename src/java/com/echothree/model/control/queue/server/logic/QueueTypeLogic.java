// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.model.control.queue.server.logic;

import com.echothree.model.control.queue.common.exception.UnknownQueueTypeNameException;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class QueueTypeLogic
        extends BaseLogic {

    protected QueueTypeLogic() {
        super();
    }

    public static QueueTypeLogic getInstance() {
        return CDI.current().select(QueueTypeLogic.class).get();
    }
    
    public QueueType getQueueTypeByName(final ExecutionErrorAccumulator eea, final String queueTypeName) {
        var queueControl = Session.getModelController(QueueControl.class);
        var queueType = queueControl.getQueueTypeByName(queueTypeName);

        if(queueType == null) {
            handleExecutionError(UnknownQueueTypeNameException.class, eea, ExecutionErrors.UnknownQueueTypeName.name(), queueTypeName);
        }

        return queueType;
    }
    
}
