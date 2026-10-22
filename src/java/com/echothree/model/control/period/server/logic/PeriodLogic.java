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

package com.echothree.model.control.period.server.logic;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.period.common.exception.UnknownPeriodNameException;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.period.server.control.PeriodControl.PeriodSelection;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PeriodLogic
        extends BaseLogic {

    @Inject
    EntityInstanceControl entityInstanceControl;

    @Inject
    PeriodControl periodControl;

    @Inject
    WorkflowControl workflowControl;

    @Inject
    PeriodTypeLogic periodTypeLogic;

    protected PeriodLogic() {
        super();
    }
    
    public Period createPeriod(final PeriodKind periodKind, final String periodName, final Period parentPeriod,
            final PeriodType periodType, final Long startTime, final Long endTime, final PartyPK createdBy) {
        var period = periodControl.createPeriod(periodKind, periodName, parentPeriod, periodType, startTime, endTime, createdBy);
        var periodTypeDetail = periodType.getLastDetail();
        var workflowEntrance = periodTypeDetail.getWorkflowEntrance();
        
        if(workflowEntrance == null) {
            workflowEntrance = periodTypeDetail.getPeriodKind().getLastDetail().getWorkflowEntrance();
        }
        
        if(workflowEntrance != null) {
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(period.getPrimaryKey());

            workflowControl.addEntityToWorkflow(workflowEntrance, entityInstance, null, null, createdBy);
        }
        
        return period;
    }

    public Period getPeriodByName(final ExecutionErrorAccumulator eea, final PeriodKind periodKind,
            final String periodName, final EntityPermission entityPermission) {
        var period = periodControl.getPeriodByName(periodKind, periodName, entityPermission);

        if(period == null) {
            handleExecutionError(UnknownPeriodNameException.class, eea, ExecutionErrors.UnknownPeriodName.name(),
                    periodKind.getLastDetail().getPeriodKindName(), periodName);
        }

        return period;
    }

    public Period getPeriodByName(final ExecutionErrorAccumulator eea, final PeriodKind periodKind, final String periodName) {
        return getPeriodByName(eea, periodKind, periodName, EntityPermission.READ_ONLY);
    }

    public PeriodSelection getPeriodSelection(final ExecutionErrorAccumulator executionErrorAccumulator, final PeriodKind periodKind,
            final String parentPeriodName, final String periodTypeName) {
        Period parentPeriod = null;
        PeriodType periodType = null;

        if(!hasExecutionErrors(executionErrorAccumulator) && parentPeriodName != null) {
            parentPeriod = getPeriodByName(executionErrorAccumulator, periodKind, parentPeriodName);
        }

        if(!hasExecutionErrors(executionErrorAccumulator) && periodTypeName != null) {
            periodType = periodTypeLogic.getPeriodTypeByName(executionErrorAccumulator, periodKind, periodTypeName);
        }

        return hasExecutionErrors(executionErrorAccumulator) ? null : new PeriodSelection(periodKind, parentPeriod, periodType);
    }

}
