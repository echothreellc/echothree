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

import com.echothree.control.user.party.common.form.CreatePartyEntityTypeForm;
import com.echothree.model.control.party.server.control.PartyEntityTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CreatePartyEntityTypeCommand
        extends BaseSimpleCommand<CreatePartyEntityTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyEntityType.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("ConfirmDelete", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePartyEntityTypeCommand */
    public CreatePartyEntityTypeCommand(UserVisitPK userVisitPK, CreatePartyEntityTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyName = form.getPartyName();
        var party = partyControl.getPartyByName(partyName);

        if(party != null) {
            var partyType = party.getLastDetail().getPartyType();

            if(partyType.getAllowUserLogins()) {
                var componentVendorName = form.getComponentVendorName();
                var componentVendor = getComponentControl().getComponentVendorByName(componentVendorName);

                if(componentVendor != null) {
                    var entityTypeName = form.getEntityTypeName();
                    var entityType = getEntityTypeControl().getEntityTypeByName(componentVendor, entityTypeName);

                    if(entityType != null) {
                        var partyEntityTypeControl = Session.getModelController(PartyEntityTypeControl.class);
                        var partyEntityType = partyEntityTypeControl.getPartyEntityType(party, entityType);

                        if(partyEntityType == null) {
                            var confirmDelete = Boolean.valueOf(form.getConfirmDelete());

                            partyEntityTypeControl.createPartyEntityType(party, entityType, confirmDelete, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.DuplicatePartyEntityType.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidPartyType.name(), partyType.getPartyTypeName());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return null;
    }
    
}
