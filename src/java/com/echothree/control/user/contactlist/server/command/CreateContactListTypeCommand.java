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

package com.echothree.control.user.contactlist.server.command;

import com.echothree.control.user.contactlist.common.form.CreateContactListTypeForm;
import com.echothree.model.control.chain.common.ChainConstants;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
import javax.enterprise.context.Dependent;

@Dependent
public class CreateContactListTypeCommand
        extends BaseSimpleCommand<CreateContactListTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ContactListType.name(), SecurityRoles.Create.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ConfirmationRequestChainName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SubscribeChainName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UnsubscribeChainName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UsedForSolicitation", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateContactListTypeCommand */
    public CreateContactListTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var contactListTypeName = form.getContactListTypeName();
        var contactListType = contactListControl.getContactListTypeByName(contactListTypeName);

        if(contactListType == null) {
            var chainControl = Session.getModelController(ChainControl.class);
            var chainKind = chainControl.getChainKindByName(ChainConstants.ChainKind_CONTACT_LIST);
            var confirmationRequestChainName = form.getConfirmationRequestChainName();
            var confirmationRequestChain = confirmationRequestChainName == null ? null
                    : chainControl.getChainByName(chainControl.getChainTypeByName(chainKind, ChainConstants.ChainType_CONFIRMATION_REQUEST), confirmationRequestChainName);

            if(confirmationRequestChainName == null || confirmationRequestChain != null) {
                var subscribeChainName = form.getSubscribeChainName();
                var subscribeChain = subscribeChainName == null ? null
                        : chainControl.getChainByName(chainControl.getChainTypeByName(chainKind, ChainConstants.ChainType_SUBSCRIBE), subscribeChainName);

                if(subscribeChainName == null || subscribeChain != null) {
                    var unsubscribeChainName = form.getUnsubscribeChainName();
                    var unsubscribeChain = unsubscribeChainName == null ? null
                            : chainControl.getChainByName(chainControl.getChainTypeByName(chainKind, ChainConstants.ChainType_UNSUBSCRIBE), unsubscribeChainName);

                    if(unsubscribeChainName == null || unsubscribeChain != null) {
                        var partyPK = getPartyPK();
                        var usedForSolicitation = Boolean.valueOf(form.getUsedForSolicitation());
                        var isDefault = Boolean.valueOf(form.getIsDefault());
                        var sortOrder = Integer.valueOf(form.getSortOrder());
                        var description = form.getDescription();

                        contactListType = contactListControl.createContactListType(contactListTypeName, confirmationRequestChain, subscribeChain,
                                unsubscribeChain, usedForSolicitation, isDefault, sortOrder, partyPK);

                        if(description != null) {
                            contactListControl.createContactListTypeDescription(contactListType, getPreferredLanguage(), description, partyPK);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUnsubscribeChainName.name(), unsubscribeChainName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSubscribeChainName.name(), subscribeChainName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownConfirmationRequestChainName.name(), confirmationRequestChainName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateContactListTypeName.name(), contactListTypeName);
        }
        
        return null;
    }
    
}
