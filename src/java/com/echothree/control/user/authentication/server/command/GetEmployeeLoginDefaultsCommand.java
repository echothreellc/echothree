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

package com.echothree.control.user.authentication.server.command;

import com.echothree.control.user.authentication.common.form.AuthenticationFormFactory;
import com.echothree.control.user.authentication.common.form.GetEmployeeLoginDefaultsForm;
import com.echothree.control.user.authentication.common.result.AuthenticationResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetEmployeeLoginDefaultsCommand
        extends BaseSimpleCommand<GetEmployeeLoginDefaultsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetEmployeeLoginDefaultsCommand */
    public GetEmployeeLoginDefaultsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = AuthenticationResultFactory.getGetEmployeeLoginDefaultsResult();
        var userControl = getUserControl();
        var userSession = userControl.getUserSessionByUserVisit(getUserVisit());
        String username = null;
        String companyName = null;
        
        if(userSession != null) {
            var party = userSession.getParty();
            
            if(party != null) {
                if(party.getLastDetail().getPartyType().getPartyTypeName().equals(PartyTypes.EMPLOYEE.name())) {
                    var userLogin = userControl.getUserLogin(party);
                    var partyRelationship = userSession.getPartyRelationship();
                    
                    username = userLogin.getUsername();
                    
                    if(partyRelationship != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        var fromParty = partyRelationship.getFromParty();
                        var partyCompany = partyControl.getPartyCompany(fromParty);
                        
                        if(partyCompany != null) {
                            companyName = partyCompany.getPartyCompanyName();
                        }
                    }
                }
            }
        }

        var employeeLoginForm = AuthenticationFormFactory.getEmployeeLoginForm();
        employeeLoginForm.setUsername(username);
        employeeLoginForm.setCompanyName(companyName);
        result.setEmployeeLoginForm(employeeLoginForm);
        
        return result;
    }
    
}
