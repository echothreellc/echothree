// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.party.common.form.GetPartyRelationshipsForm;
import com.echothree.control.user.party.common.result.GetPartyRelationshipsResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyRelationshipType;
import com.echothree.model.data.party.server.entity.RoleType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetPartyRelationshipsCommand
        extends BaseSimpleCommand<GetPartyRelationshipsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("PartyRelationshipTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FromPartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("FromRoleTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ToPartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ToRoleTypeName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetPartyRelationshipsCommand */
    public GetPartyRelationshipsCommand(UserVisitPK userVisitPK, GetPartyRelationshipsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetPartyRelationshipsResult result = PartyResultFactory.getGetPartyRelationshipsResult();
        String fromPartyName = form.getFromPartyName();
        String fromRoleTypeName = form.getFromRoleTypeName();
        String toPartyName = form.getToPartyName();
        String toRoleTypeName = form.getToRoleTypeName();
        int fromParameterCount = (fromPartyName == null? 0: 1) + (fromRoleTypeName == null? 0: 1);
        int toParameterCount = (toPartyName == null? 0: 1) + (toRoleTypeName == null? 0: 1);
        
        if((fromParameterCount == 2 && toParameterCount == 0) || (fromParameterCount == 0 && toParameterCount == 2)) {
            var partyControl = Session.getModelController(PartyControl.class);
            String partyRelationshipTypeName = form.getPartyRelationshipTypeName();
            PartyRelationshipType partyRelationshipType = partyControl.getPartyRelationshipTypeByName(partyRelationshipTypeName);
            
            if(partyRelationshipType != null) {
                UserVisit userVisit = getUserVisit();
                
                if(fromParameterCount == 2) {
                    Party fromParty = partyControl.getPartyByName(fromPartyName);
                    
                    if(fromParty != null) {
                        RoleType fromRoleType = partyControl.getRoleTypeByName(fromRoleTypeName);
                        
                        result.setFromParty(partyControl.getPartyTransfer(userVisit, fromParty));
                        
                        if(fromRoleType != null) {
                            result.setPartyRelationships(partyControl.getPartyRelationshipTransfersByFromRelationship(userVisit,
                                    partyRelationshipType, fromParty, fromRoleType));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownFromRoleTypeName.name(), fromRoleTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownFromPartyName.name(), fromPartyName);
                    }
                }
                
                if(toParameterCount == 2) {
                    Party toParty = partyControl.getPartyByName(toPartyName);
                    
                    if(toParty != null) {
                        RoleType toRoleType = partyControl.getRoleTypeByName(toRoleTypeName);
                        
                        result.setToParty(partyControl.getPartyTransfer(userVisit, toParty));
                        
                        if(toRoleType != null) {
                            result.setPartyRelationships(partyControl.getPartyRelationshipTransfersByToRelationship(userVisit,
                                    partyRelationshipType, toParty, toRoleType));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownToRoleTypeName.name(), toRoleTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownToPartyName.name(), toPartyName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyRelationshipTypeName.name(), partyRelationshipTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCombination.name());
        }
        
        return result;
    }
    
}
