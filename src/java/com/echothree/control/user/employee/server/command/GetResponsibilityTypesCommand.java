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

import com.echothree.control.user.employee.common.form.GetResponsibilityTypesForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.model.data.employee.server.factory.ResponsibilityTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetResponsibilityTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<ResponsibilityType, GetResponsibilityTypesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    @Inject
    EmployeeControl employeeControl;

    /** Creates a new instance of GetResponsibilityTypesCommand */
    public GetResponsibilityTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return employeeControl.countResponsibilityTypes();
    }

    @Override
    protected Collection<ResponsibilityType> getEntities() {
        return employeeControl.getResponsibilityTypes();
    }

    @Override
    protected BaseResult getResult(Collection<ResponsibilityType> entities) {
        var result = EmployeeResultFactory.getGetResponsibilityTypesResult();

        if(entities != null) {
            if(session.hasLimit(ResponsibilityTypeFactory.class)) {
                result.setResponsibilityTypeCount(getTotalEntities());
            }

            result.setResponsibilityTypes(employeeControl.getResponsibilityTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
