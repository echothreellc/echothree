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

package com.echothree.model.control.workrequirement.server.transfer;

import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workrequirement.common.WorkRequirementOptions;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementTypeTransfer;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementType;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WorkRequirementTypeTransferCache
        extends BaseWorkRequirementTransferCache<WorkRequirementType, WorkRequirementTypeTransfer> {
    
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    WorkEffortControl workEffortControl = Session.getModelController(WorkEffortControl.class);
    WorkRequirementControl workRequirementControl = Session.getModelController(WorkRequirementControl.class);
    boolean includeWorkRequirementScopes;
    
    /** Creates a new instance of WorkRequirementTypeTransferCache */
    protected WorkRequirementTypeTransferCache() {
        var options = session.getOptions();
        if(options != null) {
            includeWorkRequirementScopes = options.contains(WorkRequirementOptions.WorkRequirementTypeIncludeWorkRequirementScopes);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public WorkRequirementTypeTransfer getWorkRequirementTypeTransfer(UserVisit userVisit, WorkRequirementType workRequirementType) {
        var workRequirementTypeTransfer = get(workRequirementType);
        
        if(workRequirementTypeTransfer == null) {
            var workRequirementTypeDetail = workRequirementType.getLastDetail();
            var workEffortTypeTransfer = workEffortControl.getWorkEffortTypeTransfer(userVisit, workRequirementTypeDetail.getWorkEffortType());
            var workRequirementTypeName = workRequirementTypeDetail.getWorkRequirementTypeName();
            var workRequirementSequence = workRequirementTypeDetail.getWorkRequirementSequence();
            var workRequirementSequenceTransfer = workRequirementSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, workRequirementSequence);
            var workflowStep = workRequirementTypeDetail.getWorkflowStep();
            var workflowStepTransfer = workflowStep == null? null: workflowControl.getWorkflowStepTransfer(userVisit, workflowStep);
            var timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
            var estimatedTimeAllowed = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, workRequirementTypeDetail.getEstimatedTimeAllowed());
            var maximumTimeAllowed = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, workRequirementTypeDetail.getMaximumTimeAllowed());
            var allowReassignment = workRequirementTypeDetail.getAllowReassignment();
            var sortOrder = workRequirementTypeDetail.getSortOrder();
            var description = workRequirementControl.getBestWorkRequirementTypeDescription(workRequirementType, getLanguage(userVisit));
            
            workRequirementTypeTransfer = new WorkRequirementTypeTransfer(workEffortTypeTransfer, workRequirementTypeName, workRequirementSequenceTransfer,
                    workflowStepTransfer, estimatedTimeAllowed, maximumTimeAllowed, allowReassignment, sortOrder, description);
            put(userVisit, workRequirementType, workRequirementTypeTransfer);

            if(includeWorkRequirementScopes) {
                workRequirementTypeTransfer.setWorkRequirementScopes(new ListWrapper<>(workRequirementControl.getWorkRequirementScopeTransfersByWorkRequirementType(userVisit, workRequirementType)));
            }
        }
        
        return workRequirementTypeTransfer;
    }
    
}
