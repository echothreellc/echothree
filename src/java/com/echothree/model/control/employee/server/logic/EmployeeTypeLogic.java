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

import com.echothree.control.user.employee.common.spec.EmployeeTypeDescriptionSpec;
import com.echothree.model.control.employee.common.exception.UnknownEmployeeTypeDescriptionException;
import com.echothree.model.control.employee.common.exception.UnknownEmployeeTypeNameException;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.employee.server.entity.EmployeeType;
import com.echothree.model.data.employee.server.entity.EmployeeTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EmployeeTypeLogic
        extends BaseLogic {

    @Inject
    EmployeeControl employeeControl;

    @Inject
    LanguageLogic languageLogic;

    protected EmployeeTypeLogic() {
        super();
    }
    
    public EmployeeType getEmployeeTypeByName(final ExecutionErrorAccumulator eea, final String employeeTypeName,
            final EntityPermission entityPermission) {
        var employeeType = employeeControl.getEmployeeTypeByName(employeeTypeName, entityPermission);

        if(employeeType == null) {
            handleExecutionError(UnknownEmployeeTypeNameException.class, eea, ExecutionErrors.UnknownEmployeeTypeName.name(), employeeTypeName);
        }

        return employeeType;
    }
    
    public EmployeeType getEmployeeTypeByName(final ExecutionErrorAccumulator eea, final String employeeTypeName) {
        return getEmployeeTypeByName(eea, employeeTypeName, EntityPermission.READ_ONLY);
    }

    public EmployeeType getEmployeeTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String employeeTypeName) {
        return getEmployeeTypeByName(eea, employeeTypeName, EntityPermission.READ_WRITE);
    }

    public EmployeeTypeDescription getEmployeeTypeDescriptionBySpec(final ExecutionErrorAccumulator eea,
            final EmployeeTypeDescriptionSpec spec, final EntityPermission entityPermission) {
        EmployeeTypeDescription employeeTypeDescription = null;
        var employeeTypeName = spec.getEmployeeTypeName();
        var employeeType = getEmployeeTypeByName(eea, employeeTypeName);

        if(!hasExecutionErrors(eea)) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(eea, languageIsoName);

            if(!hasExecutionErrors(eea)) {
                employeeTypeDescription = employeeControl.getEmployeeTypeDescription(employeeType, language, entityPermission);

                if(employeeTypeDescription == null) {
                    handleExecutionError(UnknownEmployeeTypeDescriptionException.class, eea, ExecutionErrors.UnknownEmployeeTypeDescription.name(), employeeTypeName, languageIsoName);
                }
            }
        }

        return employeeTypeDescription;
    }

}
