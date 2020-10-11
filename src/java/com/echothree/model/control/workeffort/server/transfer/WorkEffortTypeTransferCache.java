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

package com.echothree.model.control.workeffort.server.transfer;

import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.workeffort.common.WorkEffortOptions;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTypeTransfer;
import com.echothree.model.control.workeffort.server.WorkEffortControl;
import com.echothree.model.control.workrequirement.server.WorkRequirementControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.model.data.workeffort.server.entity.WorkEffortTypeDetail;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class WorkEffortTypeTransferCache
        extends BaseWorkEffortTransferCache<WorkEffortType, WorkEffortTypeTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    WorkRequirementControl workRequirementControl = (WorkRequirementControl)Session.getModelController(WorkRequirementControl.class);
    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
    boolean includeWorkRequirementTypes;
    boolean includeWorkEffortScopes;
    
    /** Creates a new instance of WorkEffortTypeTransferCache */
    public WorkEffortTypeTransferCache(UserVisit userVisit, WorkEffortControl workEffortControl) {
        super(userVisit, workEffortControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            includeWorkRequirementTypes = options.contains(WorkEffortOptions.WorkEffortTypeIncludeWorkRequirementTypes);
            includeWorkEffortScopes = options.contains(WorkEffortOptions.WorkEffortTypeIncludeWorkEffortScopes);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public WorkEffortTypeTransfer getWorkEffortTypeTransfer(WorkEffortType workEffortType) {
        WorkEffortTypeTransfer workEffortTypeTransfer = get(workEffortType);
        
        if(workEffortTypeTransfer == null) {
            WorkEffortTypeDetail workEffortTypeDetail = workEffortType.getLastDetail();
            String workEffortTypeName = workEffortTypeDetail.getWorkEffortTypeName();
            EntityTypeTransfer entityTypeTransfer = coreControl.getEntityTypeTransfer(userVisit, workEffortTypeDetail.getEntityType());
            Sequence workEffortSequence = workEffortTypeDetail.getWorkEffortSequence();
            SequenceTransfer workEffortSequenceTransfer = workEffortSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, workEffortSequence);
            Long unformattedScheduledTime = workEffortTypeDetail.getScheduledTime();
            String scheduledTime = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedScheduledTime);
            Long unformattedEstimatedTimeAllowed = workEffortTypeDetail.getEstimatedTimeAllowed();
            String estimatedTimeAllowed = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedEstimatedTimeAllowed);
            Long unformattedMaximumTimeAllowed = workEffortTypeDetail.getMaximumTimeAllowed();
            String maximumTimeAllowed = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedMaximumTimeAllowed);
            Integer sortOrder = workEffortTypeDetail.getSortOrder();
            String description = workEffortControl.getBestWorkEffortTypeDescription(workEffortType, getLanguage());
            
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
