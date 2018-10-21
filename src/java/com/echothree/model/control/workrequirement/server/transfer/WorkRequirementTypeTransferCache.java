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

package com.echothree.model.control.workrequirement.server.transfer;

import com.echothree.model.control.sequence.remote.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.workeffort.remote.transfer.WorkEffortTypeTransfer;
import com.echothree.model.control.workeffort.server.WorkEffortControl;
import com.echothree.model.control.workflow.remote.transfer.WorkflowStepTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workrequirement.common.WorkRequirementOptions;
import com.echothree.model.control.workrequirement.remote.transfer.WorkRequirementTypeTransfer;
import com.echothree.model.control.workrequirement.server.WorkRequirementControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementType;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementTypeDetail;
import com.echothree.util.remote.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class WorkRequirementTypeTransferCache
        extends BaseWorkRequirementTransferCache<WorkRequirementType, WorkRequirementTypeTransfer> {
    
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    WorkEffortControl workEffortControl = (WorkEffortControl)Session.getModelController(WorkEffortControl.class);
    boolean includeWorkRequirementScopes;
    
    /** Creates a new instance of WorkRequirementTypeTransferCache */
    public WorkRequirementTypeTransferCache(UserVisit userVisit, WorkRequirementControl workRequirementControl) {
        super(userVisit, workRequirementControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            includeWorkRequirementScopes = options.contains(WorkRequirementOptions.WorkRequirementTypeIncludeWorkRequirementScopes);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public WorkRequirementTypeTransfer getWorkRequirementTypeTransfer(WorkRequirementType workRequirementType) {
        WorkRequirementTypeTransfer workRequirementTypeTransfer = get(workRequirementType);
        
        if(workRequirementTypeTransfer == null) {
            WorkRequirementTypeDetail workRequirementTypeDetail = workRequirementType.getLastDetail();
            WorkEffortTypeTransfer workEffortTypeTransfer = workEffortControl.getWorkEffortTypeTransfer(userVisit, workRequirementTypeDetail.getWorkEffortType());
            String workRequirementTypeName = workRequirementTypeDetail.getWorkRequirementTypeName();
            Sequence workRequirementSequence = workRequirementTypeDetail.getWorkRequirementSequence();
            SequenceTransfer workRequirementSequenceTransfer = workRequirementSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, workRequirementSequence);
            WorkflowStep workflowStep = workRequirementTypeDetail.getWorkflowStep();
            WorkflowStepTransfer workflowStepTransfer = workflowStep == null? null: workflowControl.getWorkflowStepTransfer(userVisit, workflowStep);
            UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
            String estimatedTimeAllowed = formatUnitOfMeasure(timeUnitOfMeasureKind, workRequirementTypeDetail.getEstimatedTimeAllowed());
            String maximumTimeAllowed = formatUnitOfMeasure(timeUnitOfMeasureKind, workRequirementTypeDetail.getMaximumTimeAllowed());
            Boolean allowReassignment = workRequirementTypeDetail.getAllowReassignment();
            Integer sortOrder = workRequirementTypeDetail.getSortOrder();
            String description = workRequirementControl.getBestWorkRequirementTypeDescription(workRequirementType, getLanguage());
            
            workRequirementTypeTransfer = new WorkRequirementTypeTransfer(workEffortTypeTransfer, workRequirementTypeName, workRequirementSequenceTransfer,
                    workflowStepTransfer, estimatedTimeAllowed, maximumTimeAllowed, allowReassignment, sortOrder, description);
            put(workRequirementType, workRequirementTypeTransfer);

            if(includeWorkRequirementScopes) {
                workRequirementTypeTransfer.setWorkRequirementScopes(new ListWrapper<>(workRequirementControl.getWorkRequirementScopeTransfersByWorkRequirementType(userVisit, workRequirementType)));
            }
        }
        
        return workRequirementTypeTransfer;
    }
    
}
