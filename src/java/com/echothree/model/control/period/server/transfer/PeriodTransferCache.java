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

package com.echothree.model.control.period.server.transfer;

import com.echothree.model.control.accounting.common.workflow.FiscalPeriodStatusConstants;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.period.common.PeriodConstants;
import com.echothree.model.control.period.common.transfer.PeriodTransfer;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PeriodTransferCache
        extends BasePeriodTransferCache<Period, PeriodTransfer> {
    
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of PeriodTransferCache */
    public PeriodTransferCache(PeriodControl periodControl) {
        super(periodControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PeriodTransfer getPeriodTransfer(UserVisit userVisit, Period period) {
        var periodTransfer = get(period);
        
        if(periodTransfer == null) {
            var periodDetail = period.getLastDetail();
            var periodKindTransfer = periodControl.getPeriodKindTransfer(userVisit, periodDetail.getPeriodKind());
            var periodName = periodDetail.getPeriodName();
            var parentPeriod = periodDetail.getParentPeriod();
            var parentPeriodTransfer = parentPeriod == null? null: periodControl.getPeriodTransfer(userVisit, parentPeriod);
            var periodType = periodDetail.getPeriodType();
            var periodTypeTransfer = periodType == null? null: periodControl.getPeriodTypeTransfer(userVisit, periodType);
            var unformattedStartTime = periodDetail.getStartTime();
            var startTime = formatTypicalDateTime(userVisit, unformattedStartTime);
            var unformattedEndTime = periodDetail.getEndTime();
            var endTime = formatTypicalDateTime(userVisit, unformattedEndTime);
            var description = periodControl.getBestPeriodDescription(period, getLanguage(userVisit));
            WorkflowEntityStatusTransfer status = null;

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(period.getPrimaryKey());
            var periodKindName = periodTypeTransfer.getPeriodKind().getPeriodKindName();
            if(periodKindName.equals(PeriodConstants.PeriodKind_FISCAL)) {
                status = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                        FiscalPeriodStatusConstants.Workflow_FISCAL_PERIOD_STATUS, entityInstance);
            }
            
            periodTransfer = new PeriodTransfer(periodKindTransfer, periodName, parentPeriodTransfer, periodTypeTransfer, unformattedStartTime, startTime, unformattedEndTime,
                    endTime, description, status);
            put(userVisit, period, periodTransfer);
        }
        
        return periodTransfer;
    }
    
}
