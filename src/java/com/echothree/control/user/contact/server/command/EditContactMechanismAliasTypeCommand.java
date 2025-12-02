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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.edit.ContactEditFactory;
import com.echothree.control.user.contact.common.edit.ContactMechanismAliasTypeEdit;
import com.echothree.control.user.contact.common.form.EditContactMechanismAliasTypeForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.EditContactMechanismAliasTypeResult;
import com.echothree.control.user.contact.common.spec.ContactMechanismAliasTypeSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasType;
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
public class EditContactMechanismAliasTypeCommand
        extends BaseAbstractEditCommand<ContactMechanismAliasTypeSpec, ContactMechanismAliasTypeEdit, EditContactMechanismAliasTypeResult, ContactMechanismAliasType, ContactMechanismAliasType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactMechanismAliasType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactMechanismAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactMechanismAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContactMechanismAliasTypeCommand */
    public EditContactMechanismAliasTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContactMechanismAliasTypeResult getResult() {
        return ContactResultFactory.getEditContactMechanismAliasTypeResult();
    }
    
    @Override
    public ContactMechanismAliasTypeEdit getEdit() {
        return ContactEditFactory.getContactMechanismAliasTypeEdit();
    }
    
    @Override
    public ContactMechanismAliasType getEntity(EditContactMechanismAliasTypeResult result) {
        var contactControl = Session.getModelController(ContactControl.class);
        ContactMechanismAliasType contactMechanismAliasType;
        var contactMechanismAliasTypeName = spec.getContactMechanismAliasTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            contactMechanismAliasType = contactControl.getContactMechanismAliasTypeByName(contactMechanismAliasTypeName);
        } else { // EditMode.UPDATE
            contactMechanismAliasType = contactControl.getContactMechanismAliasTypeByNameForUpdate(contactMechanismAliasTypeName);
        }

        if(contactMechanismAliasType != null) {
            result.setContactMechanismAliasType(contactControl.getContactMechanismAliasTypeTransfer(getUserVisit(), contactMechanismAliasType));
        } else {
            addExecutionError(ExecutionErrors.UnknownContactMechanismAliasTypeName.name(), contactMechanismAliasTypeName);
        }

        return contactMechanismAliasType;
    }
    
    @Override
    public ContactMechanismAliasType getLockEntity(ContactMechanismAliasType contactMechanismAliasType) {
        return contactMechanismAliasType;
    }
    
    @Override
    public void fillInResult(EditContactMechanismAliasTypeResult result, ContactMechanismAliasType contactMechanismAliasType) {
        var contactControl = Session.getModelController(ContactControl.class);
        
        result.setContactMechanismAliasType(contactControl.getContactMechanismAliasTypeTransfer(getUserVisit(), contactMechanismAliasType));
    }
    
    @Override
    public void doLock(ContactMechanismAliasTypeEdit edit, ContactMechanismAliasType contactMechanismAliasType) {
        var contactControl = Session.getModelController(ContactControl.class);
        var contactMechanismAliasTypeDescription = contactControl.getContactMechanismAliasTypeDescription(contactMechanismAliasType, getPreferredLanguage());
        var contactMechanismAliasTypeDetail = contactMechanismAliasType.getLastDetail();

        edit.setContactMechanismAliasTypeName(contactMechanismAliasTypeDetail.getContactMechanismAliasTypeName());
        edit.setIsDefault(contactMechanismAliasTypeDetail.getIsDefault().toString());
        edit.setSortOrder(contactMechanismAliasTypeDetail.getSortOrder().toString());

        if(contactMechanismAliasTypeDescription != null) {
            edit.setDescription(contactMechanismAliasTypeDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(ContactMechanismAliasType contactMechanismAliasType) {
        var contactControl = Session.getModelController(ContactControl.class);
        var contactMechanismAliasTypeName = edit.getContactMechanismAliasTypeName();
        var duplicateContactMechanismAliasType = contactControl.getContactMechanismAliasTypeByName(contactMechanismAliasTypeName);

        if(duplicateContactMechanismAliasType != null && !contactMechanismAliasType.equals(duplicateContactMechanismAliasType)) {
            addExecutionError(ExecutionErrors.DuplicateContactMechanismAliasTypeName.name(), contactMechanismAliasTypeName);
        }
    }
    
    @Override
    public void doUpdate(ContactMechanismAliasType contactMechanismAliasType) {
        var contactControl = Session.getModelController(ContactControl.class);
        var partyPK = getPartyPK();
        var contactMechanismAliasTypeDetailValue = contactControl.getContactMechanismAliasTypeDetailValueForUpdate(contactMechanismAliasType);
        var contactMechanismAliasTypeDescription = contactControl.getContactMechanismAliasTypeDescriptionForUpdate(contactMechanismAliasType, getPreferredLanguage());
        var description = edit.getDescription();

        contactMechanismAliasTypeDetailValue.setContactMechanismAliasTypeName(edit.getContactMechanismAliasTypeName());
        contactMechanismAliasTypeDetailValue.setValidationPattern(edit.getValidationPattern());
        contactMechanismAliasTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        contactMechanismAliasTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        contactControl.updateContactMechanismAliasTypeFromValue(contactMechanismAliasTypeDetailValue, partyPK);

        if(contactMechanismAliasTypeDescription == null && description != null) {
            contactControl.createContactMechanismAliasTypeDescription(contactMechanismAliasType, getPreferredLanguage(), description, partyPK);
        } else if(contactMechanismAliasTypeDescription != null && description == null) {
            contactControl.deleteContactMechanismAliasTypeDescription(contactMechanismAliasTypeDescription, partyPK);
        } else if(contactMechanismAliasTypeDescription != null && description != null) {
            var contactMechanismAliasTypeDescriptionValue = contactControl.getContactMechanismAliasTypeDescriptionValue(contactMechanismAliasTypeDescription);

            contactMechanismAliasTypeDescriptionValue.setDescription(description);
            contactControl.updateContactMechanismAliasTypeDescriptionFromValue(contactMechanismAliasTypeDescriptionValue, partyPK);
        }
    }
    
}
