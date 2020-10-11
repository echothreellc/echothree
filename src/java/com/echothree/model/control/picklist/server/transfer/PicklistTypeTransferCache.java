// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.picklist.server.transfer;

import com.echothree.model.control.picklist.common.transfer.PicklistTypeTransfer;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.picklist.server.entity.PicklistType;
import com.echothree.model.data.picklist.server.entity.PicklistTypeDetail;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.server.persistence.Session;

public class PicklistTypeTransferCache
        extends BasePicklistTransferCache<PicklistType, PicklistTypeTransfer> {
    
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of PicklistTypeTransferCache */
    public PicklistTypeTransferCache(UserVisit userVisit, PicklistControl picklistControl) {
        super(userVisit, picklistControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PicklistTypeTransfer getPicklistTypeTransfer(PicklistType picklistType) {
        PicklistTypeTransfer picklistTypeTransfer = get(picklistType);
        
        if(picklistTypeTransfer == null) {
            PicklistTypeDetail picklistTypeDetail = picklistType.getLastDetail();
            String picklistTypeName = picklistTypeDetail.getPicklistTypeName();
            PicklistType parentPicklistType = picklistTypeDetail.getParentPicklistType();
            PicklistTypeTransfer parentPicklistTypeTransfer = parentPicklistType == null? null: getPicklistTypeTransfer(parentPicklistType);
            SequenceType picklistSequenceType = picklistTypeDetail.getPicklistSequenceType();
            SequenceTypeTransfer picklistSequenceTypeTransfer = picklistSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, picklistSequenceType);
            Workflow picklistWorkflow = picklistTypeDetail.getPicklistWorkflow();
            WorkflowTransfer picklistWorkflowTransfer = picklistWorkflow == null? null: workflowControl.getWorkflowTransfer(userVisit, picklistWorkflow);
            WorkflowEntrance picklistWorkflowEntrance = picklistTypeDetail.getPicklistWorkflowEntrance();
            WorkflowEntranceTransfer picklistWorkflowEntranceTransfer = picklistWorkflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, picklistWorkflowEntrance);
            Boolean isDefault = picklistTypeDetail.getIsDefault();
            Integer sortOrder = picklistTypeDetail.getSortOrder();
            String description = picklistControl.getBestPicklistTypeDescription(picklistType, getLanguage());
            
            picklistTypeTransfer = new PicklistTypeTransfer(picklistTypeName, parentPicklistTypeTransfer, picklistSequenceTypeTransfer, picklistWorkflowTransfer,
                    picklistWorkflowEntranceTransfer, isDefault, sortOrder, description);
            put(picklistType, picklistTypeTransfer);
        }
        
        return picklistTypeTransfer;
    }
    
}
