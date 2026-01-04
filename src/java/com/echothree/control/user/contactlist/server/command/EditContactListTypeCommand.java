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

import com.echothree.control.user.contactlist.common.edit.ContactListEditFactory;
import com.echothree.control.user.contactlist.common.edit.ContactListTypeEdit;
import com.echothree.control.user.contactlist.common.form.EditContactListTypeForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditContactListTypeResult;
import com.echothree.control.user.contactlist.common.spec.ContactListTypeSpec;
import com.echothree.model.control.chain.common.ChainConstants;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.contactlist.server.entity.ContactListType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditContactListTypeCommand
        extends BaseAbstractEditCommand<ContactListTypeSpec, ContactListTypeEdit, EditContactListTypeResult, ContactListType, ContactListType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactListType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
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

    /** Creates a new instance of EditContactListTypeCommand */
    public EditContactListTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditContactListTypeResult getResult() {
        return ContactListResultFactory.getEditContactListTypeResult();
    }

    @Override
    public ContactListTypeEdit getEdit() {
        return ContactListEditFactory.getContactListTypeEdit();
    }

    @Override
    public ContactListType getEntity(EditContactListTypeResult result) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        ContactListType contactListType;
        var contactListTypeName = spec.getContactListTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            contactListType = contactListControl.getContactListTypeByName(contactListTypeName);
        } else { // EditMode.UPDATE
            contactListType = contactListControl.getContactListTypeByNameForUpdate(contactListTypeName);
        }

        if(contactListType == null) {
            addExecutionError(ExecutionErrors.UnknownContactListTypeName.name(), contactListTypeName);
        }

        return contactListType;
    }

    @Override
    public ContactListType getLockEntity(ContactListType contactListType) {
        return contactListType;
    }

    @Override
    public void fillInResult(EditContactListTypeResult result, ContactListType contactListType) {
        var contactListControl = Session.getModelController(ContactListControl.class);

        result.setContactListType(contactListControl.getContactListTypeTransfer(getUserVisit(), contactListType));
    }

    @Override
    public void doLock(ContactListTypeEdit edit, ContactListType contactListType) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var contactListTypeDescription = contactListControl.getContactListTypeDescription(contactListType, getPreferredLanguage());
        var contactListTypeDetail = contactListType.getLastDetail();
        var confirmationRequestChain = contactListTypeDetail.getConfirmationRequestChain();
        var subscribeChain = contactListTypeDetail.getSubscribeChain();
        var unsubscribeChain = contactListTypeDetail.getUnsubscribeChain();

        edit.setContactListTypeName(contactListTypeDetail.getContactListTypeName());
        edit.setConfirmationRequestChainName(confirmationRequestChain == null ? null : confirmationRequestChain.getLastDetail().getChainName());
        edit.setSubscribeChainName(subscribeChain == null ? null : subscribeChain.getLastDetail().getChainName());
        edit.setUnsubscribeChainName(unsubscribeChain == null ? null : unsubscribeChain.getLastDetail().getChainName());
        edit.setUsedForSolicitation(contactListTypeDetail.getUsedForSolicitation().toString());
        edit.setIsDefault(contactListTypeDetail.getIsDefault().toString());
        edit.setSortOrder(contactListTypeDetail.getSortOrder().toString());

        if(contactListTypeDescription != null) {
            edit.setDescription(contactListTypeDescription.getDescription());
        }
    }

    Chain confirmationRequestChain;
    Chain subscribeChain;
    Chain unsubscribeChain;

    @Override
    public void canUpdate(ContactListType contactListType) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var contactListTypeName = edit.getContactListTypeName();
        var duplicateContactListType = contactListControl.getContactListTypeByName(contactListTypeName);

        if(duplicateContactListType != null && !contactListType.equals(duplicateContactListType)) {
            addExecutionError(ExecutionErrors.DuplicateContactListTypeName.name(), contactListTypeName);
        } else {
            var chainControl = Session.getModelController(ChainControl.class);
            var chainKind = chainControl.getChainKindByName(ChainConstants.ChainKind_CONTACT_LIST);
            var confirmationRequestChainName = edit.getConfirmationRequestChainName();

            confirmationRequestChain = confirmationRequestChainName == null ? null
                    : chainControl.getChainByName(chainControl.getChainTypeByName(chainKind, ChainConstants.ChainType_CONFIRMATION_REQUEST), confirmationRequestChainName);

            if(confirmationRequestChainName == null || confirmationRequestChain != null) {
                var subscribeChainName = edit.getSubscribeChainName();

                subscribeChain = subscribeChainName == null ? null
                        : chainControl.getChainByName(chainControl.getChainTypeByName(chainKind, ChainConstants.ChainType_SUBSCRIBE), subscribeChainName);

                if(subscribeChainName == null || subscribeChain != null) {
                    var unsubscribeChainName = edit.getUnsubscribeChainName();

                    unsubscribeChain = unsubscribeChainName == null ? null
                            : chainControl.getChainByName(chainControl.getChainTypeByName(chainKind, ChainConstants.ChainType_UNSUBSCRIBE), unsubscribeChainName);

                    if(unsubscribeChainName != null && unsubscribeChain == null) {
                        addExecutionError(ExecutionErrors.UnknownUnsubscribeChainName.name(), unsubscribeChainName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSubscribeChainName.name(), subscribeChainName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownConfirmationRequestChainName.name(), confirmationRequestChainName);
            }
        }
    }

    @Override
    public void doUpdate(ContactListType contactListType) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var partyPK = getPartyPK();
        var contactListTypeDetailValue = contactListControl.getContactListTypeDetailValueForUpdate(contactListType);
        var contactListTypeDescription = contactListControl.getContactListTypeDescriptionForUpdate(contactListType, getPreferredLanguage());
        var description = edit.getDescription();

        contactListTypeDetailValue.setContactListTypeName(edit.getContactListTypeName());
        contactListTypeDetailValue.setConfirmationRequestChainPK(confirmationRequestChain == null ? null : confirmationRequestChain.getPrimaryKey());
        contactListTypeDetailValue.setSubscribeChainPK(subscribeChain == null ? null : subscribeChain.getPrimaryKey());
        contactListTypeDetailValue.setUnsubscribeChainPK(unsubscribeChain == null ? null : unsubscribeChain.getPrimaryKey());
        contactListTypeDetailValue.setUsedForSolicitation(Boolean.valueOf(edit.getUsedForSolicitation()));
        contactListTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        contactListTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        contactListControl.updateContactListTypeFromValue(contactListTypeDetailValue, partyPK);

        if(contactListTypeDescription == null && description != null) {
            contactListControl.createContactListTypeDescription(contactListType, getPreferredLanguage(), description, partyPK);
        } else if(contactListTypeDescription != null && description == null) {
            contactListControl.deleteContactListTypeDescription(contactListTypeDescription, partyPK);
        } else if(contactListTypeDescription != null && description != null) {
            var contactListTypeDescriptionValue = contactListControl.getContactListTypeDescriptionValue(contactListTypeDescription);

            contactListTypeDescriptionValue.setDescription(description);
            contactListControl.updateContactListTypeDescriptionFromValue(contactListTypeDescriptionValue, partyPK);
        }
    }

}
