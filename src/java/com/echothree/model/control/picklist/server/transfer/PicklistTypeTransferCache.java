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

package com.echothree.model.control.picklist.server.transfer;

import com.echothree.model.control.picklist.common.transfer.PicklistTypeTransfer;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.picklist.server.entity.PicklistType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PicklistTypeTransferCache
        extends BasePicklistTransferCache<PicklistType, PicklistTypeTransfer> {
    
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of PicklistTypeTransferCache */
    public PicklistTypeTransferCache(PicklistControl picklistControl) {
        super(picklistControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PicklistTypeTransfer getPicklistTypeTransfer(UserVisit userVisit, PicklistType picklistType) {
        var picklistTypeTransfer = get(picklistType);
        
        if(picklistTypeTransfer == null) {
            var picklistTypeDetail = picklistType.getLastDetail();
            var picklistTypeName = picklistTypeDetail.getPicklistTypeName();
            var parentPicklistType = picklistTypeDetail.getParentPicklistType();
            var parentPicklistTypeTransfer = parentPicklistType == null ? null : getPicklistTypeTransfer(userVisit, parentPicklistType);
            var picklistSequenceType = picklistTypeDetail.getPicklistSequenceType();
            var picklistSequenceTypeTransfer = picklistSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, picklistSequenceType);
            var picklistWorkflow = picklistTypeDetail.getPicklistWorkflow();
            var picklistWorkflowTransfer = picklistWorkflow == null? null: workflowControl.getWorkflowTransfer(userVisit, picklistWorkflow);
            var picklistWorkflowEntrance = picklistTypeDetail.getPicklistWorkflowEntrance();
            var picklistWorkflowEntranceTransfer = picklistWorkflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, picklistWorkflowEntrance);
            var isDefault = picklistTypeDetail.getIsDefault();
            var sortOrder = picklistTypeDetail.getSortOrder();
            var description = picklistControl.getBestPicklistTypeDescription(picklistType, getLanguage(userVisit));
            
            picklistTypeTransfer = new PicklistTypeTransfer(picklistTypeName, parentPicklistTypeTransfer, picklistSequenceTypeTransfer, picklistWorkflowTransfer,
                    picklistWorkflowEntranceTransfer, isDefault, sortOrder, description);
            put(userVisit, picklistType, picklistTypeTransfer);
        }
        
        return picklistTypeTransfer;
    }
    
}
