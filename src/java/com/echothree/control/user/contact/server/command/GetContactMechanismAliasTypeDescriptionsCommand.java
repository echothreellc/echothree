// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.contact.common.form.GetContactMechanismAliasTypeDescriptionsForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.GetContactMechanismAliasTypeDescriptionsResult;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetContactMechanismAliasTypeDescriptionsCommand
        extends BaseSimpleCommand<GetContactMechanismAliasTypeDescriptionsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactMechanismAliasType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ContactMechanismAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetContactMechanismAliasTypeDescriptionsCommand */
    public GetContactMechanismAliasTypeDescriptionsCommand(UserVisitPK userVisitPK, GetContactMechanismAliasTypeDescriptionsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var contactControl = (ContactControl)Session.getModelController(ContactControl.class);
        GetContactMechanismAliasTypeDescriptionsResult result = ContactResultFactory.getGetContactMechanismAliasTypeDescriptionsResult();
        String contactMechanismAliasTypeName = form.getContactMechanismAliasTypeName();
        ContactMechanismAliasType contactMechanismAliasType = contactControl.getContactMechanismAliasTypeByName(contactMechanismAliasTypeName);
        
        if(contactMechanismAliasType != null) {
            result.setContactMechanismAliasType(contactControl.getContactMechanismAliasTypeTransfer(getUserVisit(), contactMechanismAliasType));
            result.setContactMechanismAliasTypeDescriptions(contactControl.getContactMechanismAliasTypeDescriptionTransfersByContactMechanismAliasType(getUserVisit(), contactMechanismAliasType));
        } else {
            addExecutionError(ExecutionErrors.UnknownContactMechanismAliasTypeName.name(), contactMechanismAliasTypeName);
        }
        
        return result;
    }
    
}
