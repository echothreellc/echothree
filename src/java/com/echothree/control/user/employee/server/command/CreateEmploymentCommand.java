// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.employee.common.form.CreateEmploymentForm;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.control.PartyControl;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateEmploymentCommand
        extends BaseSimpleCommand<CreateEmploymentForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("StartTime", FieldType.DATE_TIME, true, null, null),
                new FieldDefinition("EndTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("TerminationTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("TerminationReasonName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateEmploymentCommand */
    public CreateEmploymentCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyName = form.getPartyName();
        var party = partyControl.getPartyByName(partyName);

        if(party != null) {
            var companyName = form.getCompanyName();
            var partyCompany = partyControl.getPartyCompanyByName(companyName);

            if(partyCompany != null) {
                var employeeControl = Session.getModelController(EmployeeControl.class);
                var terminationTypeName = form.getTerminationTypeName();
                var terminationType = employeeControl.getTerminationTypeByName(terminationTypeName);

                if(terminationTypeName == null || terminationType != null) {
                    var terminationReasonName = form.getTerminationReasonName();
                    var terminationReason = employeeControl.getTerminationReasonByName(terminationReasonName);

                    if(terminationReasonName == null || terminationReason != null) {
                        var startTime = Long.valueOf(form.getStartTime());
                        var strEndTime = form.getEndTime();
                        var endTime = strEndTime == null ? null : Long.valueOf(strEndTime);

                        employeeControl.createEmployment(party, partyCompany.getParty(), startTime, endTime, terminationType, terminationReason, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTerminationReasonName.name(), terminationReasonName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTerminationTypeName.name(), terminationTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return null;
    }
    
}
