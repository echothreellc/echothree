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

import com.echothree.control.user.period.common.spec.PeriodTypeDescriptionSpec;
import com.echothree.control.user.period.common.spec.PeriodTypeSpec;
import com.echothree.control.user.period.common.spec.PeriodTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.period.common.exception.UnknownDefaultPeriodKindException;
import com.echothree.model.control.period.common.exception.UnknownDefaultPeriodTypeException;
import com.echothree.model.control.period.common.exception.UnknownPeriodTypeDescriptionException;
import com.echothree.model.control.period.common.exception.UnknownPeriodTypeNameException;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.model.data.period.server.entity.PeriodTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PeriodTypeLogic
        extends BaseLogic {

    @Inject
    PeriodControl periodControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    PeriodKindLogic periodKindLogic;

    protected PeriodTypeLogic() {
        super();
    }

    public PeriodType getPeriodTypeByName(final ExecutionErrorAccumulator eea, final PeriodKind periodKind, final String periodTypeName,
            final EntityPermission entityPermission) {
        var periodType = periodControl.getPeriodTypeByName(periodKind, periodTypeName, entityPermission);

        if(periodType == null) {
            handleExecutionError(UnknownPeriodTypeNameException.class, eea, ExecutionErrors.UnknownPeriodTypeName.name(),
                    periodKind.getLastDetail().getPeriodKindName(), periodTypeName);
        }

        return periodType;
    }

    public PeriodType getPeriodTypeByName(final ExecutionErrorAccumulator eea, final PeriodKind periodKind, final String periodTypeName) {
        return getPeriodTypeByName(eea, periodKind, periodTypeName, EntityPermission.READ_ONLY);
    }

    public PeriodType getPeriodTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final PeriodKind periodKind, final String periodTypeName) {
        return getPeriodTypeByName(eea, periodKind, periodTypeName, EntityPermission.READ_WRITE);
    }

    public PeriodType getPeriodTypeByName(final ExecutionErrorAccumulator eea, final String periodKindName, final String periodTypeName,
            final EntityPermission entityPermission) {
        var periodKind = periodKindLogic.getPeriodKindByName(eea, periodKindName);
        PeriodType periodType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            periodType = getPeriodTypeByName(eea, periodKind, periodTypeName, entityPermission);
        }

        return periodType;
    }

    public PeriodType getPeriodTypeByName(final ExecutionErrorAccumulator eea, final String periodKindName, final String periodTypeName) {
        return getPeriodTypeByName(eea, periodKindName, periodTypeName, EntityPermission.READ_ONLY);
    }

    public PeriodType getPeriodTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String periodKindName, final String periodTypeName) {
        return getPeriodTypeByName(eea, periodKindName, periodTypeName, EntityPermission.READ_WRITE);
    }

    public PeriodType getPeriodTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final PeriodTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var periodKindName = universalSpec.getPeriodKindName();
        var periodTypeName = universalSpec.getPeriodTypeName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(periodKindName, periodTypeName);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        PeriodType periodType = null;

        if(nameParameterCount <= 2 && possibleEntitySpecs == 0) {
            PeriodKind periodKind = null;

            if(periodKindName == null) {
                if(allowDefault) {
                    periodKind = periodControl.getDefaultPeriodKind();

                    if(periodKind == null) {
                        handleExecutionError(UnknownDefaultPeriodKindException.class, eea, ExecutionErrors.UnknownDefaultPeriodKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                periodKind = periodKindLogic.getPeriodKindByName(eea, periodKindName);
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                if(periodTypeName == null) {
                    if(allowDefault) {
                        periodType = periodControl.getDefaultPeriodType(periodKind, entityPermission);

                        if(periodType == null) {
                            handleExecutionError(UnknownDefaultPeriodTypeException.class, eea, ExecutionErrors.UnknownDefaultPeriodType.name(), periodKind.getLastDetail().getPeriodKindName());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    periodType = getPeriodTypeByName(eea, periodKind, periodTypeName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.PeriodType.name());

            if(eea == null || !eea.hasExecutionErrors()) {
                periodType = periodControl.getPeriodTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return periodType;
    }

    public PeriodType getPeriodTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final PeriodTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getPeriodTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PeriodType getPeriodTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final PeriodTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getPeriodTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public PeriodType getPeriodTypeBySpec(final ExecutionErrorAccumulator eea,
            final PeriodTypeSpec spec, final EntityPermission entityPermission) {
        return getPeriodTypeByName(eea, spec.getPeriodKindName(), spec.getPeriodTypeName(), entityPermission);
    }

    public PeriodTypeDescription getPeriodTypeDescriptionBySpec(final ExecutionErrorAccumulator eea,
            final PeriodTypeDescriptionSpec spec, final EntityPermission entityPermission) {
        PeriodTypeDescription periodTypeDescription = null;
        var periodKindName = spec.getPeriodKindName();
        var periodKind = periodKindLogic.getPeriodKindByName(eea, periodKindName);

        if(!hasExecutionErrors(eea)) {
            var periodTypeName = spec.getPeriodTypeName();
            var periodType = periodControl.getPeriodTypeByName(periodKind, periodTypeName);

            if(periodType != null) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = languageLogic.getLanguageByName(eea, languageIsoName);

                if(!hasExecutionErrors(eea)) {
                    periodTypeDescription = periodControl.getPeriodTypeDescription(periodType, language, entityPermission);

                    if(periodTypeDescription == null) {
                        handleExecutionError(UnknownPeriodTypeDescriptionException.class, eea, ExecutionErrors.UnknownPeriodTypeDescription.name(), periodKindName, periodTypeName, languageIsoName);
                    }
                }
            } else {
                handleExecutionError(UnknownPeriodTypeNameException.class, eea, ExecutionErrors.UnknownPeriodTypeName.name(), periodKindName, periodTypeName);
            }
        }

        return periodTypeDescription;
    }

}
