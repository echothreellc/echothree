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

import com.echothree.control.user.contactlist.common.edit.ContactListContactMechanismPurposeEdit;
import com.echothree.control.user.contactlist.common.edit.ContactListEditFactory;
import com.echothree.control.user.contactlist.common.form.EditContactListContactMechanismPurposeForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditContactListContactMechanismPurposeResult;
import com.echothree.control.user.contactlist.common.spec.ContactListContactMechanismPurposeSpec;
import com.echothree.model.control.contact.server.logic.ContactMechanismPurposeLogic;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurpose;
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
public class EditContactListContactMechanismPurposeCommand
        extends BaseAbstractEditCommand<ContactListContactMechanismPurposeSpec, ContactListContactMechanismPurposeEdit, EditContactListContactMechanismPurposeResult, ContactListContactMechanismPurpose, ContactListContactMechanismPurpose> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactList.name(), SecurityRoles.ContactListContactMechanismPurpose.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactMechanismPurposeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditContactListContactMechanismPurposeCommand */
    public EditContactListContactMechanismPurposeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContactListContactMechanismPurposeResult getResult() {
        return ContactListResultFactory.getEditContactListContactMechanismPurposeResult();
    }

    @Override
    public ContactListContactMechanismPurposeEdit getEdit() {
        return ContactListEditFactory.getContactListContactMechanismPurposeEdit();
    }

    @Override
    public ContactListContactMechanismPurpose getEntity(EditContactListContactMechanismPurposeResult result) {
        ContactListContactMechanismPurpose contactListContactMechanismPurpose = null;
        var contactListName = spec.getContactListName();
        var contactList = ContactListLogic.getInstance().getContactListByName(this, contactListName);
        
        if(!hasExecutionErrors()) {
            var contactMechanismPurposeName = spec.getContactMechanismPurposeName();
            var contactMechanismPurpose = ContactMechanismPurposeLogic.getInstance().getContactMechanismPurposeByName(this, contactMechanismPurposeName);
            
            if(!hasExecutionErrors()) {
                var contactListControl = Session.getModelController(ContactListControl.class);
                
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    contactListContactMechanismPurpose = contactListControl.getContactListContactMechanismPurpose(contactList, contactMechanismPurpose);
                } else { // EditMode.UPDATE
                    contactListContactMechanismPurpose = contactListControl.getContactListContactMechanismPurposeForUpdate(contactList, contactMechanismPurpose);
                }

                if(contactListContactMechanismPurpose == null) {
                    addExecutionError(ExecutionErrors.UnknownContactListContactMechanismPurpose.name(), contactListName, contactMechanismPurposeName);
                }
            }
        }

        return contactListContactMechanismPurpose;
    }

    @Override
    public ContactListContactMechanismPurpose getLockEntity(ContactListContactMechanismPurpose contactListContactMechanismPurpose) {
        return contactListContactMechanismPurpose;
    }

    @Override
    public void fillInResult(EditContactListContactMechanismPurposeResult result, ContactListContactMechanismPurpose contactListContactMechanismPurpose) {
        var contactListControl = Session.getModelController(ContactListControl.class);

        result.setContactListContactMechanismPurpose(contactListControl.getContactListContactMechanismPurposeTransfer(getUserVisit(), contactListContactMechanismPurpose));
    }

    @Override
    public void doLock(ContactListContactMechanismPurposeEdit edit, ContactListContactMechanismPurpose contactListContactMechanismPurpose) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var contactListContactMechanismPurposeDetail = contactListContactMechanismPurpose.getLastDetail();

        edit.setIsDefault(contactListContactMechanismPurposeDetail.getIsDefault().toString());
        edit.setSortOrder(contactListContactMechanismPurposeDetail.getSortOrder().toString());
    }

    @Override
    public void doUpdate(ContactListContactMechanismPurpose contactListContactMechanismPurpose) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var partyPK = getPartyPK();
        var contactListContactMechanismPurposeDetailValue = contactListControl.getContactListContactMechanismPurposeDetailValueForUpdate(contactListContactMechanismPurpose);

        contactListContactMechanismPurposeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        contactListContactMechanismPurposeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        contactListControl.updateContactListContactMechanismPurposeFromValue(contactListContactMechanismPurposeDetailValue, partyPK);
    }
    
}
