// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.form.GetContactMechanismAliasTypeForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contact.server.logic.ContactMechanismAliasTypeLogic;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetContactMechanismAliasTypeCommand
        extends BaseSingleEntityCommand<ContactMechanismAliasType, GetContactMechanismAliasTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactMechanismAliasType.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ContactMechanismAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }
    
    @Inject
    ContactControl contactControl;
    
    @Inject
    ContactMechanismAliasTypeLogic contactMechanismAliasTypeLogic;
    
    /** Creates a new instance of GetContactMechanismAliasTypeCommand */
    public GetContactMechanismAliasTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected ContactMechanismAliasType getEntity() {
        var contactMechanismAliasType = contactMechanismAliasTypeLogic.getContactMechanismAliasTypeByUniversalSpec(this, form, true);

        if(contactMechanismAliasType != null) {
            sendEvent(contactMechanismAliasType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return contactMechanismAliasType;
    }

    @Override
    protected BaseResult getResult(ContactMechanismAliasType contactMechanismAliasType) {
        var result = ContactResultFactory.getGetContactMechanismAliasTypeResult();

        if(contactMechanismAliasType != null) {
            result.setContactMechanismAliasType(contactControl.getContactMechanismAliasTypeTransfer(getUserVisit(), contactMechanismAliasType));
        }

        return result;
    }
    
}
