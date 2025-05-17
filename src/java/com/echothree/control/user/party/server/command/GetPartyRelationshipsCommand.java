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

import com.echothree.control.user.party.common.form.GetPartyRelationshipsForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;

public class GetPartyRelationshipsCommand
        extends BaseMultipleEntitiesCommand<PartyRelationship, GetPartyRelationshipsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Party.name(), SecurityRoles.PartyRelationship.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyRelationshipTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FromPartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("FromRoleTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ToPartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ToRoleTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetPartyRelationshipsCommand */
    public GetPartyRelationshipsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    Party fromParty;
    Party toParty;

    @Override
    protected Collection<PartyRelationship> getEntities() {
        var fromPartyName = form.getFromPartyName();
        var fromRoleTypeName = form.getFromRoleTypeName();
        var toPartyName = form.getToPartyName();
        var toRoleTypeName = form.getToRoleTypeName();
        var fromParameterCount = (fromPartyName == null ? 0 : 1) + (fromRoleTypeName == null ? 0 : 1);
        var toParameterCount = (toPartyName == null ? 0 : 1) + (toRoleTypeName == null ? 0 : 1);
        Collection<PartyRelationship> partyRelationships = null;

        if((fromParameterCount == 2 && toParameterCount == 0) || (fromParameterCount == 0 && toParameterCount == 2)) {
            var partyControl = Session.getModelController(PartyControl.class);
            var partyRelationshipTypeName = form.getPartyRelationshipTypeName();
            var partyRelationshipType = partyControl.getPartyRelationshipTypeByName(partyRelationshipTypeName);

            if(partyRelationshipType != null) {
                if(fromParameterCount == 2) {
                    fromParty = partyControl.getPartyByName(fromPartyName);

                    if(fromParty != null) {
                        var fromRoleType = partyControl.getRoleTypeByName(fromRoleTypeName);

                        if(fromRoleType != null) {
                            partyRelationships = partyControl.getPartyRelationshipsByFromRelationship(
                                    partyRelationshipType, fromParty, fromRoleType);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownFromRoleTypeName.name(), fromRoleTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownFromPartyName.name(), fromPartyName);
                    }
                }

                if(toParameterCount == 2) {
                    toParty = partyControl.getPartyByName(toPartyName);

                    if(toParty != null) {
                        var toRoleType = partyControl.getRoleTypeByName(toRoleTypeName);

                        if(toRoleType != null) {
                            partyRelationships = partyControl.getPartyRelationshipsByToRelationship(
                                    partyRelationshipType, toParty, toRoleType);
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

        return partyRelationships;
    }

    @Override
    protected BaseResult getResult(Collection<PartyRelationship> entities) {
        var result = PartyResultFactory.getGetPartyRelationshipsResult();

        if(entities != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var userVisit = getUserVisit();

            if(fromParty != null) {
                result.setFromParty(partyControl.getPartyTransfer(userVisit, fromParty));
            }

            if(toParty != null) {
                result.setToParty(partyControl.getPartyTransfer(userVisit, toParty));
            }

            result.setPartyRelationships(partyControl.getPartyRelationshipTransfers(userVisit,
                    entities));
        }

        return result;
    }

}
