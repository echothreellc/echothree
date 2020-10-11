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

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.workeffort.common.WorkEffortOptions;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTransfer;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffort;
import com.echothree.model.data.workeffort.server.entity.WorkEffortDetail;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class WorkEffortTransferCache
        extends BaseWorkEffortTransferCache<WorkEffort, WorkEffortTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    WorkRequirementControl workRequirementControl = (WorkRequirementControl)Session.getModelController(WorkRequirementControl.class);
    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
    boolean includeWorkRequirements;
    
    /** Creates a new instance of WorkEffortTransferCache */
    public WorkEffortTransferCache(UserVisit userVisit, WorkEffortControl workEffortControl) {
        super(userVisit, workEffortControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            includeWorkRequirements = options.contains(WorkEffortOptions.WorkEffortIncludeWorkRequirements);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public WorkEffortTransfer getWorkEffortTransfer(WorkEffort workEffort) {
        WorkEffortTransfer workEffortTransfer = get(workEffort);
        
        if(workEffortTransfer == null) {
            WorkEffortDetail workEffortDetail = workEffort.getLastDetail();
            String workEffortName = workEffortDetail.getWorkEffortName();
            EntityInstanceTransfer owningEntityInstance = coreControl.getEntityInstanceTransfer(userVisit, workEffortDetail.getOwningEntityInstance(), false, false, false, false, false);
            WorkEffortScopeTransfer workEffortScope = workEffortControl.getWorkEffortScopeTransfer(userVisit, workEffortDetail.getWorkEffortScope());
            Long unformattedScheduledTime = workEffortDetail.getScheduledTime();
            String scheduledTime = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedScheduledTime);
            Long unformattedScheduledStartTime = workEffortDetail.getScheduledStartTime();
            String scheduledStartTime = formatTypicalDateTime(unformattedScheduledStartTime);
            Long unformattedScheduledEndTime = workEffortDetail.getScheduledEndTime();
            String scheduledEndTime = formatTypicalDateTime(unformattedScheduledEndTime);
            Long unformattedEstimatedTimeAllowed = workEffortDetail.getEstimatedTimeAllowed();
            String estimatedTimeAllowed = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedEstimatedTimeAllowed);
            Long unformattedMaximumTimeAllowed = workEffortDetail.getMaximumTimeAllowed();
            String maximumTimeAllowed = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedMaximumTimeAllowed);

            workEffortTransfer = new WorkEffortTransfer(workEffortName, owningEntityInstance, workEffortScope, unformattedScheduledTime, scheduledTime,
                    unformattedScheduledStartTime, scheduledStartTime, unformattedScheduledEndTime, scheduledEndTime, unformattedEstimatedTimeAllowed,
                    estimatedTimeAllowed, unformattedMaximumTimeAllowed, maximumTimeAllowed);
            put(workEffort, workEffortTransfer);

            if(includeWorkRequirements) {
                workEffortTransfer.setWorkRequirements(new ListWrapper<>(workRequirementControl.getWorkRequirementTransfersByWorkEffort(userVisit, workEffort)));
            }
        }
        
        return workEffortTransfer;
    }
    
}
