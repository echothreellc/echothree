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

import com.echothree.control.user.period.common.spec.PeriodKindUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.period.common.exception.DuplicatePeriodKindNameException;
import com.echothree.model.control.period.common.exception.UnknownDefaultPeriodKindException;
import com.echothree.model.control.period.common.exception.UnknownPeriodKindNameException;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PeriodKindLogic
        extends BaseLogic {

    protected PeriodKindLogic() {
        super();
    }

    public static PeriodKindLogic getInstance() {
        return CDI.current().select(PeriodKindLogic.class).get();
    }

    public PeriodKind createPeriodKind(final ExecutionErrorAccumulator eea, final String periodKindName,
            final WorkflowEntrance workflowEntrance, final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var periodControl = Session.getModelController(PeriodControl.class);
        var periodKind = periodControl.getPeriodKindByName(periodKindName);

        if(periodKind == null) {
            periodKind = periodControl.createPeriodKind(periodKindName, workflowEntrance, isDefault, sortOrder, createdBy);

            if(description != null) {
                periodControl.createPeriodKindDescription(periodKind, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicatePeriodKindNameException.class, eea, ExecutionErrors.DuplicatePeriodKindName.name(), periodKindName);
        }

        return periodKind;
    }

    public PeriodKind getPeriodKindByName(final ExecutionErrorAccumulator eea, final String periodKindName,
            final EntityPermission entityPermission) {
        var periodControl = Session.getModelController(PeriodControl.class);
        var periodKind = periodControl.getPeriodKindByName(periodKindName, entityPermission);

        if(periodKind == null) {
            handleExecutionError(UnknownPeriodKindNameException.class, eea, ExecutionErrors.UnknownPeriodKindName.name(), periodKindName);
        }

        return periodKind;
    }

    public PeriodKind getPeriodKindByName(final ExecutionErrorAccumulator eea, final String periodKindName) {
        return getPeriodKindByName(eea, periodKindName, EntityPermission.READ_ONLY);
    }

    public PeriodKind getPeriodKindByNameForUpdate(final ExecutionErrorAccumulator eea, final String periodKindName) {
        return getPeriodKindByName(eea, periodKindName, EntityPermission.READ_WRITE);
    }

    public PeriodKind getPeriodKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PeriodKindUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        PeriodKind periodKind = null;
        var periodControl = Session.getModelController(PeriodControl.class);
        var periodKindName = universalSpec.getPeriodKindName();
        var parameterCount = (periodKindName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    periodKind = periodControl.getDefaultPeriodKind(entityPermission);

                    if(periodKind == null) {
                        handleExecutionError(UnknownDefaultPeriodKindException.class, eea, ExecutionErrors.UnknownDefaultPeriodKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(periodKindName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.PeriodKind.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        periodKind = periodControl.getPeriodKindByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    periodKind = getPeriodKindByName(eea, periodKindName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return periodKind;
    }

    public PeriodKind getPeriodKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PeriodKindUniversalSpec universalSpec, boolean allowDefault) {
        return getPeriodKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PeriodKind getPeriodKindByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PeriodKindUniversalSpec universalSpec, boolean allowDefault) {
        return getPeriodKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deletePeriodKind(final ExecutionErrorAccumulator eea, final PeriodKind periodKind,
            final BasePK deletedBy) {
        var periodControl = Session.getModelController(PeriodControl.class);

        periodControl.deletePeriodKind(periodKind, deletedBy);
    }

}
