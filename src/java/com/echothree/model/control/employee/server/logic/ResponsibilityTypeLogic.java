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

package com.echothree.model.control.employee.server.logic;

import com.echothree.control.user.employee.common.spec.ResponsibilityTypeDescriptionSpec;
import com.echothree.model.control.employee.common.exception.UnknownResponsibilityTypeDescriptionException;
import com.echothree.model.control.employee.common.exception.UnknownResponsibilityTypeNameException;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.model.data.employee.server.entity.ResponsibilityTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ResponsibilityTypeLogic
        extends BaseLogic {

    @Inject
    EmployeeControl employeeControl;

    @Inject
    LanguageLogic languageLogic;

    protected ResponsibilityTypeLogic() {
        super();
    }
    
    public ResponsibilityType getResponsibilityTypeByName(final ExecutionErrorAccumulator eea, final String responsibilityTypeName,
            final EntityPermission entityPermission) {
        var responsibilityType = employeeControl.getResponsibilityTypeByName(responsibilityTypeName, entityPermission);

        if(responsibilityType == null) {
            handleExecutionError(UnknownResponsibilityTypeNameException.class, eea, ExecutionErrors.UnknownResponsibilityTypeName.name(), responsibilityTypeName);
        }

        return responsibilityType;
    }
    
    public ResponsibilityType getResponsibilityTypeByName(final ExecutionErrorAccumulator eea, final String responsibilityTypeName) {
        return getResponsibilityTypeByName(eea, responsibilityTypeName, EntityPermission.READ_ONLY);
    }

    public ResponsibilityType getResponsibilityTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String responsibilityTypeName) {
        return getResponsibilityTypeByName(eea, responsibilityTypeName, EntityPermission.READ_WRITE);
    }

    public ResponsibilityTypeDescription getResponsibilityTypeDescriptionBySpec(final ExecutionErrorAccumulator eea,
            final ResponsibilityTypeDescriptionSpec spec, final EntityPermission entityPermission) {
        ResponsibilityTypeDescription responsibilityTypeDescription = null;
        var responsibilityTypeName = spec.getResponsibilityTypeName();
        var responsibilityType = getResponsibilityTypeByName(eea, responsibilityTypeName);

        if(!hasExecutionErrors(eea)) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(eea, languageIsoName);

            if(!hasExecutionErrors(eea)) {
                responsibilityTypeDescription = employeeControl.getResponsibilityTypeDescription(responsibilityType, language, entityPermission);

                if(responsibilityTypeDescription == null) {
                    handleExecutionError(UnknownResponsibilityTypeDescriptionException.class, eea,
                            ExecutionErrors.UnknownResponsibilityTypeDescription.name(), responsibilityTypeName, languageIsoName);
                }
            }
        }

        return responsibilityTypeDescription;
    }

}
