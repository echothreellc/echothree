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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.DeleteDepartmentForm;
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

public class DeleteDepartmentCommand
        extends BaseSimpleCommand<DeleteDepartmentForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DivisionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DepartmentName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteDepartmentCommand */
    public DeleteDepartmentCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var companyName = form.getCompanyName();
        var partyCompany = partyControl.getPartyCompanyByName(companyName);
        
        if(partyCompany != null) {
            var divisionName = form.getDivisionName();
            var partyCompanyParty = partyCompany.getParty();
            var partyDivision = partyControl.getPartyDivisionByName(partyCompanyParty, divisionName);
            
            if(partyDivision != null) {
                var departmentName = form.getDepartmentName();
                var partyDivisionParty = partyDivision.getParty();
                var partyDepartment = partyControl.getPartyDepartmentByNameForUpdate(partyDivisionParty, departmentName);
                
                if(partyDepartment != null) {
                    getLog().error("unimplemented deleteDepartment called");
                    // TODO: partyControl.deleteParty(partyDepartment.getPartyForUpdate(), getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownDepartmentName.name(), departmentName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownDivisionName.name(), divisionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
        }
        
        return null;
    }
    
}
