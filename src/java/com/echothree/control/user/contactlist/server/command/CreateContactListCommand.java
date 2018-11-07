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

package com.echothree.control.user.contactlist.server.command;

import com.echothree.control.user.contactlist.common.form.CreateContactListForm;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.contactlist.common.workflow.PartyContactListStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequency;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.contactlist.server.entity.ContactListType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
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

public class CreateContactListCommand
        extends BaseSimpleCommand<CreateContactListForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ContactList.name(), SecurityRoles.Create.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListFrequencyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultPartyContactListStatusChoice", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateContactListCommand */
    public CreateContactListCommand(UserVisitPK userVisitPK, CreateContactListForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        ContactListControl contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        String contactListName = form.getContactListName();
        ContactList contactList = contactListControl.getContactListByName(contactListName);

        if(contactList == null) {
            String contactListGroupName = form.getContactListGroupName();
            ContactListGroup contactListGroup = contactListControl.getContactListGroupByName(contactListGroupName);

            if(contactListGroup != null) {
                String contactListTypeName = form.getContactListTypeName();
                ContactListType contactListType = contactListControl.getContactListTypeByName(contactListTypeName);

                if(contactListType != null) {
                    String contactListFrequencyName = form.getContactListFrequencyName();
                    ContactListFrequency contactListFrequency = contactListFrequencyName == null ? null : contactListControl.getContactListFrequencyByName(contactListFrequencyName);

                    if(contactListFrequencyName == null || contactListFrequency != null) {
                        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                        String defaultPartyContactListStatusChoice = form.getDefaultPartyContactListStatusChoice();
                        Workflow workflow = workflowControl.getWorkflowByName(PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS);
                        WorkflowEntrance defaultPartyContactListStatus = workflowControl.getWorkflowEntranceByName(workflow, defaultPartyContactListStatusChoice);

                        if(defaultPartyContactListStatus != null) {
                            PartyPK partyPK = getPartyPK();
                            Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                            Integer sortOrder = Integer.valueOf(form.getSortOrder());
                            String description = form.getDescription();

                            contactList = contactListControl.createContactList(contactListName, contactListGroup, contactListType, contactListFrequency,
                                    defaultPartyContactListStatus, isDefault, sortOrder, partyPK);

                            if(description != null) {
                                contactListControl.createContactListDescription(contactList, getPreferredLanguage(), description, partyPK);
                            }
                        } else {
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
        } else {
            addExecutionError(ExecutionErrors.DuplicateContactListName.name(), contactListName);
        }

        return null;
    }
    
}
