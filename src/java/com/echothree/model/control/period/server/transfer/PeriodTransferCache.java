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

package com.echothree.model.control.period.server.transfer;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.period.common.PeriodConstants;
import com.echothree.model.control.period.common.transfer.PeriodKindTransfer;
import com.echothree.model.control.period.common.transfer.PeriodTransfer;
import com.echothree.model.control.period.common.transfer.PeriodTypeTransfer;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.accounting.common.workflow.FiscalPeriodStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.period.server.entity.PeriodDetail;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PeriodTransferCache
        extends BasePeriodTransferCache<Period, PeriodTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of PeriodTransferCache */
    public PeriodTransferCache(UserVisit userVisit, PeriodControl periodControl) {
        super(userVisit, periodControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PeriodTransfer getPeriodTransfer(Period period) {
        PeriodTransfer periodTransfer = get(period);
        
        if(periodTransfer == null) {
            PeriodDetail periodDetail = period.getLastDetail();
            PeriodKindTransfer periodKindTransfer = periodControl.getPeriodKindTransfer(userVisit, periodDetail.getPeriodKind());
            String periodName = periodDetail.getPeriodName();
            Period parentPeriod = periodDetail.getParentPeriod();
            PeriodTransfer parentPeriodTransfer = parentPeriod == null? null: periodControl.getPeriodTransfer(userVisit, parentPeriod);
            PeriodType periodType = periodDetail.getPeriodType();
            PeriodTypeTransfer periodTypeTransfer = periodType == null? null: periodControl.getPeriodTypeTransfer(userVisit, periodType);
            Long unformattedStartTime = periodDetail.getStartTime();
            String startTime = formatTypicalDateTime(unformattedStartTime);
            Long unformattedEndTime = periodDetail.getEndTime();
            String endTime = formatTypicalDateTime(unformattedEndTime);
            String description = periodControl.getBestPeriodDescription(period, getLanguage());
            WorkflowEntityStatusTransfer status = null;
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(period.getPrimaryKey());
            String periodKindName = periodTypeTransfer.getPeriodKind().getPeriodKindName();
            if(periodKindName.equals(PeriodConstants.PeriodKind_FISCAL)) {
                status = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                        FiscalPeriodStatusConstants.Workflow_FISCAL_PERIOD_STATUS, entityInstance);
            }
            
            periodTransfer = new PeriodTransfer(periodKindTransfer, periodName, parentPeriodTransfer, periodTypeTransfer, unformattedStartTime, startTime, unformattedEndTime,
                    endTime, description, status);
            put(period, periodTransfer);
        }
        
        return periodTransfer;
    }
    
}
