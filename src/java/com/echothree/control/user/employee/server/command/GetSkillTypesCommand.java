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

import com.echothree.control.user.employee.common.form.GetSkillTypesForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.SkillType;
import com.echothree.model.data.employee.server.factory.SkillTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetSkillTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<SkillType, GetSkillTypesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    @Inject
    EmployeeControl employeeControl;

    /** Creates a new instance of GetSkillTypesCommand */
    public GetSkillTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return employeeControl.countSkillTypes();
    }

    @Override
    protected Collection<SkillType> getEntities() {
        return employeeControl.getSkillTypes();
    }

    @Override
    protected BaseResult getResult(Collection<SkillType> entities) {
        var result = EmployeeResultFactory.getGetSkillTypesResult();

        if(entities != null) {
            if(session.hasLimit(SkillTypeFactory.class)) {
                result.setSkillTypeCount(getTotalEntities());
            }

            result.setSkillTypes(employeeControl.getSkillTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
