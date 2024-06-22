// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.workeffort.server.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.workeffort.common.WorkEffortOptions;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTypeTransfer;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScopeDetail;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class WorkEffortScopeTransferCache
        extends BaseWorkEffortTransferCache<WorkEffortScope, WorkEffortScopeTransfer> {
    
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    WorkRequirementControl workRequirementControl = Session.getModelController(WorkRequirementControl.class);
    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
    boolean includeWorkRequirementScopes;
    boolean includeWorkEfforts;
    
    /** Creates a new instance of WorkEffortScopeTransferCache */
    public WorkEffortScopeTransferCache(UserVisit userVisit, WorkEffortControl workEffortControl) {
        super(userVisit, workEffortControl);

        var options = session.getOptions();
        if(options != null) {
            includeWorkRequirementScopes = options.contains(WorkEffortOptions.WorkEffortScopeIncludeWorkRequirementScopes);
            includeWorkEfforts = options.contains(WorkEffortOptions.WorkEffortScopeIncludeWorkEfforts);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public WorkEffortScopeTransfer getWorkEffortScopeTransfer(WorkEffortScope workEffortScope) {
        WorkEffortScopeTransfer workEffortScopeTransfer = get(workEffortScope);
        
        if(workEffortScopeTransfer == null) {
            WorkEffortScopeDetail workEffortScopeDetail = workEffortScope.getLastDetail();
            WorkEffortTypeTransfer workEffortTypeTransfer = workEffortControl.getWorkEffortTypeTransfer(userVisit, workEffortScopeDetail.getWorkEffortType());
            String workEffortScopeName = workEffortScopeDetail.getWorkEffortScopeName();
            Sequence workEffortSequence = workEffortScopeDetail.getWorkEffortSequence();
            SequenceTransfer workEffortSequenceTransfer = workEffortSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, workEffortSequence);
            Long unformattedScheduledTime = workEffortScopeDetail.getScheduledTime();
            String scheduledTime = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedScheduledTime);
            Long unformattedEstimatedTimeAllowed = workEffortScopeDetail.getEstimatedTimeAllowed();
            String estimatedTimeAllowed = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedEstimatedTimeAllowed);
            Long unformattedMaximumTimeAllowed = workEffortScopeDetail.getMaximumTimeAllowed();
            String maximumTimeAllowed = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedMaximumTimeAllowed);
            Boolean isDefault = workEffortScopeDetail.getIsDefault();
            Integer sortOrder = workEffortScopeDetail.getSortOrder();
            String description = workEffortControl.getBestWorkEffortScopeDescription(workEffortScope, getLanguage());
            
            workEffortScopeTransfer = new WorkEffortScopeTransfer(workEffortTypeTransfer, workEffortScopeName, workEffortSequenceTransfer,
                    unformattedScheduledTime, scheduledTime, unformattedEstimatedTimeAllowed, estimatedTimeAllowed, unformattedMaximumTimeAllowed,
                    maximumTimeAllowed, isDefault, sortOrder, description);
            put(workEffortScope, workEffortScopeTransfer);

            if(includeWorkRequirementScopes) {
                workEffortScopeTransfer.setWorkRequirementScopes(new ListWrapper<>(workRequirementControl.getWorkRequirementScopeTransfersByWorkEffortScope(userVisit, workEffortScope)));
            }

            if(includeWorkEfforts) {
                workEffortScopeTransfer.setWorkEfforts(new ListWrapper<>(workEffortControl.getWorkEffortTransfersByWorkEffortScope(userVisit, workEffortScope)));
            }
        }
        
        return workEffortScopeTransfer;
    }
    
}
