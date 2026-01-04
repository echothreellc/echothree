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

package com.echothree.util.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.Session;
import java.sql.Connection;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseModelControl {

    @Inject
    protected Session session;

    @Inject
    protected EntityInstanceControl entityInstanceControl;

    @Inject
    protected EventControl eventControl;

    @Inject
    protected PartyControl partyControl;

    @Inject
    protected WorkflowControl workflowControl;

    private Log log;

    /** Creates a new instance of BaseModelControl */
    protected BaseModelControl() {
    }

    @PostConstruct
    public void init() {
    }

    protected Log getLog() {
        if(log == null) {
            log = LogFactory.getLog(this.getClass());
        }
        
        return log;
    }
    
    // --------------------------------------------------------------------------------
    //   Utilities
    // --------------------------------------------------------------------------------

    protected EntityInstance getEntityInstanceByBasePK(final BasePK pk) {
        return entityInstanceControl.getEntityInstanceByBasePK(pk);
    }
    
    protected EntityInstance getEntityInstanceByBaseEntity(final BaseEntity baseEntity) {
        return getEntityInstanceByBasePK(baseEntity.getPrimaryKey());
    }

    protected Event sendEvent(final BasePK basePK, final EventTypes eventType, final BasePK relatedBasePK,
            final EventTypes relatedEventType, final BasePK createdByBasePK) {
        return eventControl.sendEvent(basePK, eventType, relatedBasePK, relatedEventType, createdByBasePK);
    }
    
    protected Event sendEvent(final EntityInstance entityInstance, final EventTypes eventType, final BasePK relatedBasePK,
            final EventTypes relatedEventType, final BasePK createdByBasePK) {
        return eventControl.sendEvent(entityInstance, eventType, relatedBasePK, relatedEventType, createdByBasePK);
    }
    
    public Event sendEvent(final EntityInstance entityInstance, final EventTypes eventType, final EntityInstance relatedEntityInstance,
            final EventTypes relatedEventType, final BasePK createdByBasePK) {
        return eventControl.sendEvent(entityInstance, eventType, relatedEntityInstance, relatedEventType, createdByBasePK);
    }
    
}
