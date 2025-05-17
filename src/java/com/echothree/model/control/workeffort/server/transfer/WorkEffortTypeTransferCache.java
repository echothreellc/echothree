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

import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.workeffort.common.WorkEffortOptions;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTypeTransfer;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class WorkEffortTypeTransferCache
        extends BaseWorkEffortTransferCache<WorkEffortType, WorkEffortTypeTransfer> {
    
    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    WorkRequirementControl workRequirementControl = Session.getModelController(WorkRequirementControl.class);
    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
    boolean includeWorkRequirementTypes;
    boolean includeWorkEffortScopes;
    
    /** Creates a new instance of WorkEffortTypeTransferCache */
    public WorkEffortTypeTransferCache(UserVisit userVisit, WorkEffortControl workEffortControl) {
        super(userVisit, workEffortControl);

        var options = session.getOptions();
        if(options != null) {
            includeWorkRequirementTypes = options.contains(WorkEffortOptions.WorkEffortTypeIncludeWorkRequirementTypes);
            includeWorkEffortScopes = options.contains(WorkEffortOptions.WorkEffortTypeIncludeWorkEffortScopes);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public WorkEffortTypeTransfer getWorkEffortTypeTransfer(WorkEffortType workEffortType) {
        var workEffortTypeTransfer = get(workEffortType);
        
        if(workEffortTypeTransfer == null) {
            var workEffortTypeDetail = workEffortType.getLastDetail();
            var workEffortTypeName = workEffortTypeDetail.getWorkEffortTypeName();
            var entityTypeTransfer = entityTypeControl.getEntityTypeTransfer(userVisit, workEffortTypeDetail.getEntityType());
            var workEffortSequence = workEffortTypeDetail.getWorkEffortSequence();
            var workEffortSequenceTransfer = workEffortSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, workEffortSequence);
            var unformattedScheduledTime = workEffortTypeDetail.getScheduledTime();
            var scheduledTime = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedScheduledTime);
            var unformattedEstimatedTimeAllowed = workEffortTypeDetail.getEstimatedTimeAllowed();
            var estimatedTimeAllowed = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedEstimatedTimeAllowed);
            var unformattedMaximumTimeAllowed = workEffortTypeDetail.getMaximumTimeAllowed();
            var maximumTimeAllowed = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedMaximumTimeAllowed);
            var sortOrder = workEffortTypeDetail.getSortOrder();
            var description = workEffortControl.getBestWorkEffortTypeDescription(workEffortType, getLanguage());
            
            workEffortTypeTransfer = new WorkEffortTypeTransfer(workEffortTypeName, entityTypeTransfer, workEffortSequenceTransfer, unformattedScheduledTime,
                    scheduledTime, unformattedEstimatedTimeAllowed, estimatedTimeAllowed, unformattedMaximumTimeAllowed, maximumTimeAllowed, sortOrder,
                    description);
            put(workEffortType, workEffortTypeTransfer);
        }

        if(includeWorkRequirementTypes) {
            workEffortTypeTransfer.setWorkRequirementTypes(new ListWrapper<>(workRequirementControl.getWorkRequirementTypeTransfers(userVisit, workEffortType)));
        }

        if(includeWorkEffortScopes) {
            workEffortTypeTransfer.setWorkEffortScopes(new ListWrapper<>(workEffortControl.getWorkEffortScopeTransfers(userVisit, workEffortType)));
        }
        
        return workEffortTypeTransfer;
    }
    
}
