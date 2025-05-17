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

import com.echothree.control.user.contactlist.common.form.CreateContactListForm;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.contactlist.common.workflow.PartyContactListStatusConstants;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
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

public class CreateContactListCommand
        extends BaseSimpleCommand<CreateContactListForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateContactListCommand */
    public CreateContactListCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var contactListName = form.getContactListName();
        var contactList = contactListControl.getContactListByName(contactListName);

        if(contactList == null) {
            var contactListGroupName = form.getContactListGroupName();
            var contactListGroup = contactListControl.getContactListGroupByName(contactListGroupName);

            if(contactListGroup != null) {
                var contactListTypeName = form.getContactListTypeName();
                var contactListType = contactListControl.getContactListTypeByName(contactListTypeName);

                if(contactListType != null) {
                    var contactListFrequencyName = form.getContactListFrequencyName();
                    var contactListFrequency = contactListFrequencyName == null ? null : contactListControl.getContactListFrequencyByName(contactListFrequencyName);

                    if(contactListFrequencyName == null || contactListFrequency != null) {
                        var workflowControl = Session.getModelController(WorkflowControl.class);
                        var defaultPartyContactListStatusChoice = form.getDefaultPartyContactListStatusChoice();
                        var workflow = workflowControl.getWorkflowByName(PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS);
                        var defaultPartyContactListStatus = workflowControl.getWorkflowEntranceByName(workflow, defaultPartyContactListStatusChoice);

                        if(defaultPartyContactListStatus != null) {
                            var partyPK = getPartyPK();
                            var isDefault = Boolean.valueOf(form.getIsDefault());
                            var sortOrder = Integer.valueOf(form.getSortOrder());
                            var description = form.getDescription();

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
