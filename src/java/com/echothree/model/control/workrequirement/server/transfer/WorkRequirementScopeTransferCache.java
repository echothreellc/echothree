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

import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workrequirement.common.WorkRequirementOptions;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementScopeTransfer;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementScope;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class WorkRequirementScopeTransferCache
        extends BaseWorkRequirementTransferCache<WorkRequirementScope, WorkRequirementScopeTransfer> {
    
    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    WorkEffortControl workEffortControl = Session.getModelController(WorkEffortControl.class);
    boolean includeWorkRequirements;
    
    /** Creates a new instance of WorkRequirementScopeTransferCache */
    public WorkRequirementScopeTransferCache(UserVisit userVisit, WorkRequirementControl workRequirementControl) {
        super(userVisit, workRequirementControl);

        var options = session.getOptions();
        if(options != null) {
            includeWorkRequirements = options.contains(WorkRequirementOptions.WorkRequirementScopeIncludeWorkRequirements);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public WorkRequirementScopeTransfer getWorkRequirementScopeTransfer(WorkRequirementScope workRequirementScope) {
        var workRequirementScopeTransfer = get(workRequirementScope);
        
        if(workRequirementScopeTransfer == null) {
            var workRequirementScopeDetail = workRequirementScope.getLastDetail();
            var workEffortScopeTransfer = workEffortControl.getWorkEffortScopeTransfer(userVisit, workRequirementScopeDetail.getWorkEffortScope());
            var workRequirementTypeTransfer = workRequirementControl.getWorkRequirementTypeTransfer(userVisit, workRequirementScopeDetail.getWorkRequirementType());
            var workRequirementSequence = workRequirementScopeDetail.getWorkRequirementSequence();
            var workRequirementSequenceTransfer = workRequirementSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, workRequirementSequence);
            var workTimeSequence = workRequirementScopeDetail.getWorkTimeSequence();
            var workTimeSequenceTransfer = workTimeSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, workTimeSequence);
            var workAssignmentSelector = workRequirementScopeDetail.getWorkAssignmentSelector();
            var workAssignmentSelectorTransfer = workAssignmentSelector == null? null: selectorControl.getSelectorTransfer(userVisit, workAssignmentSelector);
            var timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
            var estimatedTimeAllowed = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, workRequirementScopeDetail.getEstimatedTimeAllowed());
            var maximumTimeAllowed = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, workRequirementScopeDetail.getMaximumTimeAllowed());
            
            workRequirementScopeTransfer = new WorkRequirementScopeTransfer(workEffortScopeTransfer, workRequirementTypeTransfer,
                    workRequirementSequenceTransfer, workTimeSequenceTransfer, workAssignmentSelectorTransfer, estimatedTimeAllowed,
                    maximumTimeAllowed);
            put(userVisit, workRequirementScope, workRequirementScopeTransfer);

            if(includeWorkRequirements) {
                workRequirementScopeTransfer.setWorkRequirements(new ListWrapper<>(workRequirementControl.getWorkRequirementTransfersByWorkRequirementScope(userVisit, workRequirementScope)));
            }
        }
        
        return workRequirementScopeTransfer;
    }
    
}
