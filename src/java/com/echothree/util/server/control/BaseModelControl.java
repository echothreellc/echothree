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

package com.echothree.util.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.ThreadSession;
import java.sql.Connection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseModelControl {
    
    protected Session session;
    protected Connection connection;
    private Log log;
    private PartyControl partyControl;
    private WorkflowControl workflowControl;
    
    /** Creates a new instance of BaseModelControl */
    protected BaseModelControl() {
        this.session = ThreadSession.currentSession();
        this.connection = session.getConnection();
    }
    
    public Session getSession() {
        return session;
    }
    
    public Connection getConnection() {
        return connection;
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

    protected PartyControl getPartyControl() {
        if(partyControl == null) {
            partyControl = Session.getModelController(PartyControl.class);
        }
        
        return partyControl;
    }
    
    protected WorkflowControl getWorkflowControl() {
        if(workflowControl == null) {
            workflowControl = Session.getModelController(WorkflowControl.class);
        }

        return workflowControl;
    }
    
    protected EntityInstance getEntityInstanceByBasePK(final BasePK pk) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

        return entityInstanceControl.getEntityInstanceByBasePK(pk);
    }
    
    protected EntityInstance getEntityInstanceByBaseEntity(final BaseEntity baseEntity) {
        return getEntityInstanceByBasePK(baseEntity.getPrimaryKey());
    }

    protected Event sendEvent(final BasePK basePK, final EventTypes eventType, final BasePK relatedBasePK,
            final EventTypes relatedEventType, final BasePK createdByBasePK) {
        var coreControl = Session.getModelController(CoreControl.class);

        return coreControl.sendEvent(basePK, eventType, relatedBasePK, relatedEventType, createdByBasePK);
    }
    
    protected Event sendEvent(final EntityInstance entityInstance, final EventTypes eventType, final BasePK relatedBasePK,
            final EventTypes relatedEventType, final BasePK createdByBasePK) {
        var coreControl = Session.getModelController(CoreControl.class);

        return coreControl.sendEvent(entityInstance, eventType, relatedBasePK, relatedEventType, createdByBasePK);
    }
    
    public Event sendEvent(final EntityInstance entityInstance, final EventTypes eventType, final EntityInstance relatedEntityInstance,
            final EventTypes relatedEventType, final BasePK createdByBasePK) {
        var coreControl = Session.getModelController(CoreControl.class);

        return coreControl.sendEvent(entityInstance, eventType, relatedEntityInstance, relatedEventType, createdByBasePK);
    }
    
}
