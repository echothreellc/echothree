// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.queue.common.QueueTypes;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.queue.server.entity.QueuedEntity;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class QueuedEntityLogic
        extends BaseLogic {

    protected QueuedEntityLogic() {
        super();
    }

    public static QueuedEntityLogic getInstance() {
        return CDI.current().select(QueuedEntityLogic.class).get();
    }
    
    public QueuedEntity createQueuedEntityUsingNames(final ExecutionErrorAccumulator eea, final String queueTypeName, final EntityInstance entityInstance) {
        var queueType = QueueTypeLogic.getInstance().getQueueTypeByName(eea, QueueTypes.INDEXING.name());
        QueuedEntity queuedEntity = null;
        
        if(!hasExecutionErrors(eea)) {
            var queueControl = Session.getModelController(QueueControl.class);
            
            queueControl.createQueuedEntity(queueType, entityInstance);
        }
        
        return queuedEntity;
    }
    
}
