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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.remote.form.GetDivisionChoicesForm;
import com.echothree.control.user.party.remote.result.GetDivisionChoicesResult;
import com.echothree.control.user.party.remote.result.PartyResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyType;
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

public class GetDivisionChoicesCommand
        extends BaseSimpleCommand<GetDivisionChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultDivisionChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetDivisionChoicesCommand */
    public GetDivisionChoicesCommand(UserVisitPK userVisitPK, GetDivisionChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        GetDivisionChoicesResult result = PartyResultFactory.getGetDivisionChoicesResult();
        String companyName = form.getCompanyName();
        String partyName = form.getPartyName();
        int parameterCount = (companyName == null? 0: 1) + (partyName == null? 0: 1);
        
        if(parameterCount == 1) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            Party party = null;
            
            if(companyName != null) {
                PartyCompany partyCompany = partyControl.getPartyCompanyByName(companyName);
                
                if(partyCompany == null) {
                    addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
                } else {
                    party = partyCompany.getParty();
                }
            } else {
                party = partyControl.getPartyByName(partyName);
                
                if(party != null) {
                    PartyType partyType = partyControl.getPartyTypeByName(PartyConstants.PartyType_COMPANY);
                    
                    if(!party.getLastDetail().getPartyType().equals(partyType)) {
                        addExecutionError(ExecutionErrors.InvalidPartyType.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            }
            
            if(!hasExecutionErrors()) {
                String defaultDivisionChoice = form.getDefaultDivisionChoice();
                boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
                
                result.setDivisionChoices(partyControl.getDivisionChoices(party, defaultDivisionChoice, allowNullChoice));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
