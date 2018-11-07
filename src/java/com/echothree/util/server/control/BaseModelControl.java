// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.ThreadSession;
import java.sql.Connection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseModelControl<C
        extends BaseModelControl> {
    
    protected Session session;
    protected Connection connection;
    private Log log;
    private CoreControl coreControl;
    private PartyControl partyControl;
    private WorkflowControl workflowControl;
    
    /** Creates a new instance of BaseModelControl */
    public BaseModelControl() {
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
    
    protected CoreControl getCoreControl() {
        if(coreControl == null) {
            coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        }
        
        return coreControl;
    }
    
    protected PartyControl getPartyControl() {
        if(partyControl == null) {
            partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        }
        
        return partyControl;
    }
    
    protected WorkflowControl getWorkflowControl() {
        if(workflowControl == null) {
            workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        }
        
        return workflowControl;
    }
    
    protected EntityInstance getEntityInstanceByBasePK(BasePK pk) {
        return getCoreControl().getEntityInstanceByBasePK(pk);
    }
    
    protected EntityInstance getEntityInstanceByBaseEntity(BaseEntity baseEntity) {
        return getEntityInstanceByBasePK(baseEntity.getPrimaryKey());
    }
    
    protected Event sendEventUsingNames(BasePK entityInstancePK, String eventTypeName, BasePK relatedPK, String relatedEventTypeName, BasePK createdByPK) {
        return getCoreControl().sendEventUsingNames(entityInstancePK, eventTypeName, relatedPK, relatedEventTypeName, createdByPK);
    }
    
    protected Event sendEventUsingNames(EntityInstance entityInstance, String eventTypeName, BasePK relatedPK, String relatedEventTypeName, BasePK createdByPK) {
        return getCoreControl().sendEventUsingNames(entityInstance, eventTypeName, relatedPK, relatedEventTypeName, createdByPK);
    }
    
    public Event sendEventUsingNames(EntityInstance entityInstance, String eventTypeName, EntityInstance relatedEntityInstance, String relatedEventTypeName,
            BasePK createdByPK) {
        return getCoreControl().sendEventUsingNames(entityInstance, eventTypeName, relatedEntityInstance, relatedEventTypeName, createdByPK);
    }
    
}
