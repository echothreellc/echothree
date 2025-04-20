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

package com.echothree.model.control.period.server.logic;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.util.server.persistence.Session;

public class PeriodLogic {

    private PeriodLogic() {
        super();
    }

    private static class PeriodLogicHolder {
        static PeriodLogic instance = new PeriodLogic();
    }

    public static PeriodLogic getInstance() {
        return PeriodLogicHolder.instance;
    }
    
    public Period createPeriod(final PeriodKind periodKind, final String periodName, final Period parentPeriod,
            final PeriodType periodType, final Long startTime, final Long endTime, final PartyPK createdBy) {
        var periodControl = Session.getModelController(PeriodControl.class);
        var period = periodControl.createPeriod(periodKind, periodName, parentPeriod, periodType, startTime, endTime, createdBy);
        var periodTypeDetail = periodType.getLastDetail();
        var workflowEntrance = periodTypeDetail.getWorkflowEntrance();
        
        if(workflowEntrance == null) {
            workflowEntrance = periodTypeDetail.getPeriodKind().getLastDetail().getWorkflowEntrance();
        }
        
        if(workflowEntrance != null) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(period.getPrimaryKey());

            workflowControl.addEntityToWorkflow(workflowEntrance, entityInstance, null, null, createdBy);
        }
        
        return period;
    }
    
}
