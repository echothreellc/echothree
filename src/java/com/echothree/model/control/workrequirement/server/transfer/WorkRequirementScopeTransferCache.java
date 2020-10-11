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

package com.echothree.model.control.workrequirement.server.transfer;

import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.model.control.workeffort.server.WorkEffortControl;
import com.echothree.model.control.workrequirement.common.WorkRequirementOptions;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementScopeTransfer;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementTypeTransfer;
import com.echothree.model.control.workrequirement.server.WorkRequirementControl;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementScope;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementScopeDetail;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class WorkRequirementScopeTransferCache
        extends BaseWorkRequirementTransferCache<WorkRequirementScope, WorkRequirementScopeTransfer> {
    
    SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    WorkEffortControl workEffortControl = (WorkEffortControl)Session.getModelController(WorkEffortControl.class);
    boolean includeWorkRequirements;
    
    /** Creates a new instance of WorkRequirementScopeTransferCache */
    public WorkRequirementScopeTransferCache(UserVisit userVisit, WorkRequirementControl workRequirementControl) {
        super(userVisit, workRequirementControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            includeWorkRequirements = options.contains(WorkRequirementOptions.WorkRequirementScopeIncludeWorkRequirements);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public WorkRequirementScopeTransfer getWorkRequirementScopeTransfer(WorkRequirementScope workRequirementScope) {
        WorkRequirementScopeTransfer workRequirementScopeTransfer = get(workRequirementScope);
        
        if(workRequirementScopeTransfer == null) {
            WorkRequirementScopeDetail workRequirementScopeDetail = workRequirementScope.getLastDetail();
            WorkEffortScopeTransfer workEffortScopeTransfer = workEffortControl.getWorkEffortScopeTransfer(userVisit, workRequirementScopeDetail.getWorkEffortScope());
            WorkRequirementTypeTransfer workRequirementTypeTransfer = workRequirementControl.getWorkRequirementTypeTransfer(userVisit, workRequirementScopeDetail.getWorkRequirementType());
            Sequence workRequirementSequence = workRequirementScopeDetail.getWorkRequirementSequence();
            SequenceTransfer workRequirementSequenceTransfer = workRequirementSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, workRequirementSequence);
            Sequence workTimeSequence = workRequirementScopeDetail.getWorkTimeSequence();
            SequenceTransfer workTimeSequenceTransfer = workTimeSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, workTimeSequence);
            Selector workAssignmentSelector = workRequirementScopeDetail.getWorkAssignmentSelector();
            SelectorTransfer workAssignmentSelectorTransfer = workAssignmentSelector == null? null: selectorControl.getSelectorTransfer(userVisit, workAssignmentSelector);
            UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
            String estimatedTimeAllowed = formatUnitOfMeasure(timeUnitOfMeasureKind, workRequirementScopeDetail.getEstimatedTimeAllowed());
            String maximumTimeAllowed = formatUnitOfMeasure(timeUnitOfMeasureKind, workRequirementScopeDetail.getMaximumTimeAllowed());
            
            workRequirementScopeTransfer = new WorkRequirementScopeTransfer(workEffortScopeTransfer, workRequirementTypeTransfer,
                    workRequirementSequenceTransfer, workTimeSequenceTransfer, workAssignmentSelectorTransfer, estimatedTimeAllowed,
                    maximumTimeAllowed);
            put(workRequirementScope, workRequirementScopeTransfer);

            if(includeWorkRequirements) {
                workRequirementScopeTransfer.setWorkRequirements(new ListWrapper<>(workRequirementControl.getWorkRequirementTransfersByWorkRequirementScope(userVisit, workRequirementScope)));
            }
        }
        
        return workRequirementScopeTransfer;
    }
    
}
