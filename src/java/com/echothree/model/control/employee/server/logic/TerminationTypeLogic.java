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

import com.echothree.control.user.employee.common.spec.TerminationTypeDescriptionSpec;
import com.echothree.model.control.employee.common.exception.UnknownTerminationTypeDescriptionException;
import com.echothree.model.control.employee.common.exception.UnknownTerminationTypeNameException;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.employee.server.entity.TerminationType;
import com.echothree.model.data.employee.server.entity.TerminationTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TerminationTypeLogic
        extends BaseLogic {

    @Inject
    EmployeeControl employeeControl;

    @Inject
    LanguageLogic languageLogic;

    protected TerminationTypeLogic() {
        super();
    }
    
    public TerminationType getTerminationTypeByName(final ExecutionErrorAccumulator eea, final String terminationTypeName,
            final EntityPermission entityPermission) {
        var terminationType = employeeControl.getTerminationTypeByName(terminationTypeName, entityPermission);

        if(terminationType == null) {
            handleExecutionError(UnknownTerminationTypeNameException.class, eea, ExecutionErrors.UnknownTerminationTypeName.name(), terminationTypeName);
        }

        return terminationType;
    }
    
    public TerminationType getTerminationTypeByName(final ExecutionErrorAccumulator eea, final String terminationTypeName) {
        return getTerminationTypeByName(eea, terminationTypeName, EntityPermission.READ_ONLY);
    }

    public TerminationType getTerminationTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String terminationTypeName) {
        return getTerminationTypeByName(eea, terminationTypeName, EntityPermission.READ_WRITE);
    }

    public TerminationTypeDescription getTerminationTypeDescriptionBySpec(final ExecutionErrorAccumulator eea,
            final TerminationTypeDescriptionSpec spec, final EntityPermission entityPermission) {
        TerminationTypeDescription terminationTypeDescription = null;
        var terminationTypeName = spec.getTerminationTypeName();
        var terminationType = getTerminationTypeByName(eea, terminationTypeName);

        if(!hasExecutionErrors(eea)) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(eea, languageIsoName);

            if(!hasExecutionErrors(eea)) {
                terminationTypeDescription = employeeControl.getTerminationTypeDescription(terminationType, language, entityPermission);

                if(terminationTypeDescription == null) {
                    handleExecutionError(UnknownTerminationTypeDescriptionException.class, eea,
                            ExecutionErrors.UnknownTerminationTypeDescription.name(), terminationTypeName, languageIsoName);
                }
            }
        }

        return terminationTypeDescription;
    }

}
