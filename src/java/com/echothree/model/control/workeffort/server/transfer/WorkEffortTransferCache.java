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

package com.echothree.model.control.workeffort.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.workeffort.common.WorkEffortOptions;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTransfer;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffort;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class WorkEffortTransferCache
        extends BaseWorkEffortTransferCache<WorkEffort, WorkEffortTransfer> {
    
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    WorkRequirementControl workRequirementControl = Session.getModelController(WorkRequirementControl.class);
    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
    boolean includeWorkRequirements;
    
    /** Creates a new instance of WorkEffortTransferCache */
    public WorkEffortTransferCache(WorkEffortControl workEffortControl) {
        super(workEffortControl);

        var options = session.getOptions();
        if(options != null) {
            includeWorkRequirements = options.contains(WorkEffortOptions.WorkEffortIncludeWorkRequirements);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public WorkEffortTransfer getWorkEffortTransfer(UserVisit userVisit, WorkEffort workEffort) {
        var workEffortTransfer = get(workEffort);
        
        if(workEffortTransfer == null) {
            var workEffortDetail = workEffort.getLastDetail();
            var workEffortName = workEffortDetail.getWorkEffortName();
            var owningEntityInstance = entityInstanceControl.getEntityInstanceTransfer(userVisit, workEffortDetail.getOwningEntityInstance(), false, false, false, false);
            var workEffortScope = workEffortControl.getWorkEffortScopeTransfer(userVisit, workEffortDetail.getWorkEffortScope());
            var unformattedScheduledTime = workEffortDetail.getScheduledTime();
            var scheduledTime = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedScheduledTime);
            var unformattedScheduledStartTime = workEffortDetail.getScheduledStartTime();
            var scheduledStartTime = formatTypicalDateTime(userVisit, unformattedScheduledStartTime);
            var unformattedScheduledEndTime = workEffortDetail.getScheduledEndTime();
            var scheduledEndTime = formatTypicalDateTime(userVisit, unformattedScheduledEndTime);
            var unformattedEstimatedTimeAllowed = workEffortDetail.getEstimatedTimeAllowed();
            var estimatedTimeAllowed = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedEstimatedTimeAllowed);
            var unformattedMaximumTimeAllowed = workEffortDetail.getMaximumTimeAllowed();
            var maximumTimeAllowed = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedMaximumTimeAllowed);

            workEffortTransfer = new WorkEffortTransfer(workEffortName, owningEntityInstance, workEffortScope, unformattedScheduledTime, scheduledTime,
                    unformattedScheduledStartTime, scheduledStartTime, unformattedScheduledEndTime, scheduledEndTime, unformattedEstimatedTimeAllowed,
                    estimatedTimeAllowed, unformattedMaximumTimeAllowed, maximumTimeAllowed);
            put(userVisit, workEffort, workEffortTransfer);

            if(includeWorkRequirements) {
                workEffortTransfer.setWorkRequirements(new ListWrapper<>(workRequirementControl.getWorkRequirementTransfersByWorkEffort(userVisit, workEffort)));
            }
        }
        
        return workEffortTransfer;
    }
    
}
