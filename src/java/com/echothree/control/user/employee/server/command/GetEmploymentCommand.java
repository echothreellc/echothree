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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.common.form.GetEmploymentForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.Employment;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetEmploymentCommand
        extends BaseSingleEntityCommand<Employment, GetEmploymentForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EmploymentName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    EmployeeControl employeeControl;

    /** Creates a new instance of GetEmploymentCommand */
    public GetEmploymentCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Employment getEntity() {
        var employmentName = form.getEmploymentName();
        var employment = employeeControl.getEmploymentByName(employmentName);

        if(employment != null) {
            sendEvent(employment.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownEmployment.name(), employmentName);
        }

        return employment;
    }

    @Override
    protected BaseResult getResult(Employment employment) {
        var result = EmployeeResultFactory.getGetEmploymentResult();

        if(employment != null) {
            result.setEmployment(employeeControl.getEmploymentTransfer(getUserVisit(), employment));
        }

        return result;
    }
    
}
