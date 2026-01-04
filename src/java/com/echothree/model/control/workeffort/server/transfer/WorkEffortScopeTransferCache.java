// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.workeffort.common.WorkEffortOptions;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WorkEffortScopeTransferCache
        extends BaseWorkEffortTransferCache<WorkEffortScope, WorkEffortScopeTransfer> {
    
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    WorkEffortControl workEffortControl = Session.getModelController(WorkEffortControl.class);
    WorkRequirementControl workRequirementControl = Session.getModelController(WorkRequirementControl.class);

    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);

    boolean includeWorkRequirementScopes;
    boolean includeWorkEfforts;
    
    /** Creates a new instance of WorkEffortScopeTransferCache */
    protected WorkEffortScopeTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            includeWorkRequirementScopes = options.contains(WorkEffortOptions.WorkEffortScopeIncludeWorkRequirementScopes);
            includeWorkEfforts = options.contains(WorkEffortOptions.WorkEffortScopeIncludeWorkEfforts);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public WorkEffortScopeTransfer getWorkEffortScopeTransfer(UserVisit userVisit, WorkEffortScope workEffortScope) {
        var workEffortScopeTransfer = get(workEffortScope);
        
        if(workEffortScopeTransfer == null) {
            var workEffortScopeDetail = workEffortScope.getLastDetail();
            var workEffortTypeTransfer = workEffortControl.getWorkEffortTypeTransfer(userVisit, workEffortScopeDetail.getWorkEffortType());
            var workEffortScopeName = workEffortScopeDetail.getWorkEffortScopeName();
            var workEffortSequence = workEffortScopeDetail.getWorkEffortSequence();
            var workEffortSequenceTransfer = workEffortSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, workEffortSequence);
            var unformattedScheduledTime = workEffortScopeDetail.getScheduledTime();
            var scheduledTime = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedScheduledTime);
            var unformattedEstimatedTimeAllowed = workEffortScopeDetail.getEstimatedTimeAllowed();
            var estimatedTimeAllowed = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedEstimatedTimeAllowed);
            var unformattedMaximumTimeAllowed = workEffortScopeDetail.getMaximumTimeAllowed();
            var maximumTimeAllowed = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedMaximumTimeAllowed);
            var isDefault = workEffortScopeDetail.getIsDefault();
            var sortOrder = workEffortScopeDetail.getSortOrder();
            var description = workEffortControl.getBestWorkEffortScopeDescription(workEffortScope, getLanguage(userVisit));
            
            workEffortScopeTransfer = new WorkEffortScopeTransfer(workEffortTypeTransfer, workEffortScopeName, workEffortSequenceTransfer,
                    unformattedScheduledTime, scheduledTime, unformattedEstimatedTimeAllowed, estimatedTimeAllowed, unformattedMaximumTimeAllowed,
                    maximumTimeAllowed, isDefault, sortOrder, description);
            put(userVisit, workEffortScope, workEffortScopeTransfer);

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
