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

package com.echothree.control.user.contactlist.server.command;

import com.echothree.control.user.contactlist.common.edit.ContactListEdit;
import com.echothree.control.user.contactlist.common.edit.ContactListEditFactory;
import com.echothree.control.user.contactlist.common.form.EditContactListForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditContactListResult;
import com.echothree.control.user.contactlist.common.spec.ContactListSpec;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.contactlist.common.workflow.PartyContactListStatusConstants;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequency;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.contactlist.server.entity.ContactListType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
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
public class EditContactListCommand
        extends BaseAbstractEditCommand<ContactListSpec, ContactListEdit, EditContactListResult, ContactList, ContactList> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactList.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListFrequencyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultPartyContactListStatusChoice", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditContactListCommand */
    public EditContactListCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditContactListResult getResult() {
        return ContactListResultFactory.getEditContactListResult();
    }

    @Override
    public ContactListEdit getEdit() {
        return ContactListEditFactory.getContactListEdit();
    }

    @Override
    public ContactList getEntity(EditContactListResult result) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        ContactList contactList;
        var contactListName = spec.getContactListName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            contactList = contactListControl.getContactListByName(contactListName);
        } else { // EditMode.UPDATE
            contactList = contactListControl.getContactListByNameForUpdate(contactListName);
        }

        if(contactList == null) {
            addExecutionError(ExecutionErrors.UnknownContactListName.name(), contactListName);
        }

        return contactList;
    }

    @Override
    public ContactList getLockEntity(ContactList contactList) {
        return contactList;
    }

    @Override
    public void fillInResult(EditContactListResult result, ContactList contactList) {
        var contactListControl = Session.getModelController(ContactListControl.class);

        result.setContactList(contactListControl.getContactListTransfer(getUserVisit(), contactList));
    }

    ContactListFrequency contactListFrequency;

    @Override
    public void doLock(ContactListEdit edit, ContactList contactList) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var contactListDescription = contactListControl.getContactListDescription(contactList, getPreferredLanguage());
        var contactListDetail = contactList.getLastDetail();

        contactListFrequency = contactListDetail.getContactListFrequency();

        edit.setContactListName(contactListDetail.getContactListName());
        edit.setContactListGroupName(contactListDetail.getContactListGroup().getLastDetail().getContactListGroupName());
        edit.setContactListTypeName(contactListDetail.getContactListType().getLastDetail().getContactListTypeName());
        edit.setContactListFrequencyName(contactListFrequency == null ? null : contactListFrequency.getLastDetail().getContactListFrequencyName());
        edit.setDefaultPartyContactListStatusChoice(contactListDetail.getDefaultPartyContactListStatus().getLastDetail().getWorkflowEntranceName());
        edit.setIsDefault(contactListDetail.getIsDefault().toString());
        edit.setSortOrder(contactListDetail.getSortOrder().toString());

        if(contactListDescription != null) {
            edit.setDescription(contactListDescription.getDescription());
        }
    }

    ContactListGroup contactListGroup;
    ContactListType contactListType;
    WorkflowEntrance defaultPartyContactListStatus;

    @Override
    public void canUpdate(ContactList contactList) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var contactListName = edit.getContactListName();
        var duplicateContactList = contactListControl.getContactListByName(contactListName);

        if(duplicateContactList != null && !contactList.equals(duplicateContactList)) {
            addExecutionError(ExecutionErrors.DuplicateContactListName.name(), contactListName);
        } else {
            var contactListGroupName = edit.getContactListGroupName();

            contactListGroup = contactListControl.getContactListGroupByName(contactListGroupName);

            if(contactListGroup != null) {
                var contactListTypeName = edit.getContactListTypeName();

                contactListType = contactListControl.getContactListTypeByName(contactListTypeName);

                if(contactListType != null) {
                    var contactListFrequencyName = edit.getContactListFrequencyName();

                    contactListFrequency = contactListFrequencyName == null ? null : contactListControl.getContactListFrequencyByName(contactListFrequencyName);

                    if(contactListFrequencyName == null || contactListFrequency != null) {
                        var workflowControl = Session.getModelController(WorkflowControl.class);
                        var defaultPartyContactListStatusChoice = edit.getDefaultPartyContactListStatusChoice();
                        var workflow = workflowControl.getWorkflowByName(PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS);

                        defaultPartyContactListStatus = workflowControl.getWorkflowEntranceByName(workflow, defaultPartyContactListStatusChoice);

                        if(defaultPartyContactListStatus == null) {
                            addExecutionError(ExecutionErrors.UnknownDefaultPartyContactListStatusChoice.name(), PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS,
                                    defaultPartyContactListStatusChoice);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownContactListFrequencyName.name(), contactListFrequencyName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownContactListTypeName.name(), contactListTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactListGroupName.name(), contactListGroupName);
            }
        }
    }

    @Override
    public void doUpdate(ContactList contactList) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var partyPK = getPartyPK();
        var contactListDetailValue = contactListControl.getContactListDetailValueForUpdate(contactList);
        var contactListDescription = contactListControl.getContactListDescriptionForUpdate(contactList, getPreferredLanguage());
        var description = edit.getDescription();

        contactListDetailValue.setContactListName(edit.getContactListName());
        contactListDetailValue.setContactListGroupPK(contactListGroup.getPrimaryKey());
        contactListDetailValue.setContactListTypePK(contactListType.getPrimaryKey());
        contactListDetailValue.setContactListFrequencyPK(contactListFrequency == null ? null : contactListFrequency.getPrimaryKey());
        contactListDetailValue.setDefaultPartyContactListStatusPK(defaultPartyContactListStatus.getPrimaryKey());
        contactListDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        contactListDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        contactListControl.updateContactListFromValue(contactListDetailValue, partyPK);

        if(contactListDescription == null && description != null) {
            contactListControl.createContactListDescription(contactList, getPreferredLanguage(), description, partyPK);
        } else if(contactListDescription != null && description == null) {
            contactListControl.deleteContactListDescription(contactListDescription, partyPK);
        } else if(contactListDescription != null && description != null) {
            var contactListDescriptionValue = contactListControl.getContactListDescriptionValue(contactListDescription);

            contactListDescriptionValue.setDescription(description);
            contactListControl.updateContactListDescriptionFromValue(contactListDescriptionValue, partyPK);
        }
    }

}
