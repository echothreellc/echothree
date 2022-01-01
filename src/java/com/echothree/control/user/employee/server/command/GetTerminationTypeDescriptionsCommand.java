// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.employee.common.form.GetTerminationTypeDescriptionsForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.result.GetTerminationTypeDescriptionsResult;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.TerminationType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetTerminationTypeDescriptionsCommand
        extends BaseSimpleCommand<GetTerminationTypeDescriptionsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("TerminationTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetTerminationTypeDescriptionsCommand */
    public GetTerminationTypeDescriptionsCommand(UserVisitPK userVisitPK, GetTerminationTypeDescriptionsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        GetTerminationTypeDescriptionsResult result = EmployeeResultFactory.getGetTerminationTypeDescriptionsResult();
        String terminationTypeName = form.getTerminationTypeName();
        TerminationType terminationType = employeeControl.getTerminationTypeByName(terminationTypeName);
        
        if(terminationType != null) {
            result.setTerminationType(employeeControl.getTerminationTypeTransfer(getUserVisit(), terminationType));
            result.setTerminationTypeDescriptions(employeeControl.getTerminationTypeDescriptionTransfers(getUserVisit(), terminationType));
        } else {
            addExecutionError(ExecutionErrors.UnknownTerminationTypeName.name(), terminationTypeName);
        }
        
        return result;
    }
    
}
