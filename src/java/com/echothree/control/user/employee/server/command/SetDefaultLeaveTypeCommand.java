// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.employee.remote.form.SetDefaultLeaveTypeForm;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.data.employee.server.value.LeaveTypeDetailValue;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetDefaultLeaveTypeCommand
        extends BaseSimpleCommand<SetDefaultLeaveTypeForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LeaveTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of SetDefaultLeaveTypeCommand */
    public SetDefaultLeaveTypeCommand(UserVisitPK userVisitPK, SetDefaultLeaveTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
        EmployeeControl employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
        String leaveTypeName = form.getLeaveTypeName();
        LeaveTypeDetailValue leaveTypeDetailValue = employeeControl.getLeaveTypeDetailValueByNameForUpdate(leaveTypeName);
        
        if(leaveTypeDetailValue != null) {
            leaveTypeDetailValue.setIsDefault(Boolean.TRUE);
            employeeControl.updateLeaveTypeFromValue(leaveTypeDetailValue, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownLeaveTypeName.name(), leaveTypeName);
        }
        
        return null;
    }
    
}
