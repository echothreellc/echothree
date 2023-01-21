// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.control.user.party.common.form.GetPartyRelationshipForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetPartyRelationshipCommand
        extends BaseSimpleCommand<GetPartyRelationshipForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("PartyRelationshipTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FromPartyName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FromRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ToPartyName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ToRoleTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetPartyRelationshipCommand */
    public GetPartyRelationshipCommand(UserVisitPK userVisitPK, GetPartyRelationshipForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = PartyResultFactory.getGetPartyRelationshipResult();
        var partyControl = Session.getModelController(PartyControl.class);
        var partyRelationshipTypeName = form.getPartyRelationshipTypeName();
        var partyRelationshipType = partyControl.getPartyRelationshipTypeByName(partyRelationshipTypeName);

        if(partyRelationshipType != null) {
            var fromPartyName = form.getFromPartyName();
            var fromParty = partyControl.getPartyByName(fromPartyName);

            if(fromParty != null) {
                var fromRoleTypeName = form.getFromRoleTypeName();
                var fromRoleType = partyControl.getRoleTypeByName(fromRoleTypeName);

                if(fromRoleType != null) {
                    var toPartyName = form.getToPartyName();
                    var toParty = partyControl.getPartyByName(toPartyName);

                    if(toParty != null) {
                        var toRoleTypeName = form.getToRoleTypeName();
                        var toRoleType = partyControl.getRoleTypeByName(toRoleTypeName);

                        if(toRoleType != null) {
                            result.setPartyRelationship(partyControl.getPartyRelationshipTransfer(getUserVisit(),
                                    partyControl.getPartyRelationship(partyRelationshipType, fromParty, fromRoleType, toParty, toRoleType)));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownToRoleTypeName.name(), toRoleTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownToPartyName.name(), toPartyName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownFromRoleTypeName.name(), fromRoleTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFromPartyName.name(), fromPartyName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyRelationshipTypeName.name(), partyRelationshipTypeName);
        }

        return result;
    }
    
}
