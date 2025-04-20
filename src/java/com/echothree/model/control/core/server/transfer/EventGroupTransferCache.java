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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.EventGroupTransfer;
import com.echothree.model.control.core.common.workflow.EventGroupStatusConstants;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EventGroup;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EventGroupTransferCache
        extends BaseCoreTransferCache<EventGroup, EventGroupTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of EventGroupTransferCache */
    public EventGroupTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    public EventGroupTransfer getEventGroupTransfer(EventGroup eventGroup) {
        var eventGroupTransfer = get(eventGroup);
        
        if(eventGroupTransfer == null) {
            var eventGroupDetail = eventGroup.getLastDetail();
            var eventGroupName = eventGroupDetail.getEventGroupName();

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(eventGroup.getPrimaryKey());
            var eventGroupStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    EventGroupStatusConstants.Workflow_EVENT_GROUP_STATUS, entityInstance);
            
            eventGroupTransfer = new EventGroupTransfer(eventGroupName, eventGroupStatusTransfer);
            put(eventGroup, eventGroupTransfer);
        }
        
        return eventGroupTransfer;
    }
    
}
