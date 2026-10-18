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
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.period.common.exception.UnknownPeriodTypeDescriptionException;
import com.echothree.model.control.period.common.exception.UnknownPeriodTypeNameException;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.model.data.period.server.entity.PeriodTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PeriodTypeLogic
        extends BaseLogic {

    @Inject
    PeriodControl periodControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    PeriodKindLogic periodKindLogic;

    protected PeriodTypeLogic() {
        super();
    }

    public PeriodType getPeriodTypeBySpec(final ExecutionErrorAccumulator eea,
            final PeriodTypeSpec spec, final EntityPermission entityPermission) {
        PeriodType periodType = null;
        var periodKindName = spec.getPeriodKindName();
        var periodKind = periodKindLogic.getPeriodKindByName(eea, periodKindName);

        if(!hasExecutionErrors(eea)) {
            var periodTypeName = spec.getPeriodTypeName();

            periodType = periodControl.getPeriodTypeByName(periodKind, periodTypeName, entityPermission);

            if(periodType == null) {
                handleExecutionError(UnknownPeriodTypeNameException.class, eea, ExecutionErrors.UnknownPeriodTypeName.name(), periodKindName, periodTypeName);
            }
        }

        return periodType;
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
