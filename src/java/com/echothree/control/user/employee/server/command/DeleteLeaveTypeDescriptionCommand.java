// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.employee.common.form.DeleteLeaveTypeDescriptionForm;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.employee.server.entity.LeaveType;
import com.echothree.model.data.employee.server.entity.LeaveTypeDescription;
import com.echothree.model.data.party.server.entity.Language;
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

public class DeleteLeaveTypeDescriptionCommand
        extends BaseSimpleCommand<DeleteLeaveTypeDescriptionForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LeaveTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of DeleteLeaveTypeDescriptionCommand */
    public DeleteLeaveTypeDescriptionCommand(UserVisitPK userVisitPK, DeleteLeaveTypeDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
        var employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
        String leaveTypeName = form.getLeaveTypeName();
        LeaveType leaveType = employeeControl.getLeaveTypeByName(leaveTypeName);
        
        if(leaveType != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                LeaveTypeDescription leaveTypeDescription = employeeControl.getLeaveTypeDescriptionForUpdate(leaveType, language);
                
                if(leaveTypeDescription != null) {
                    employeeControl.deleteLeaveTypeDescription(leaveTypeDescription, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownLeaveTypeDescription.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownLeaveTypeName.name(), leaveTypeName);
        }
        
        return null;
    }
    
}
