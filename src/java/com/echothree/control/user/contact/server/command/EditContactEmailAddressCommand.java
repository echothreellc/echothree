// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.contact.common.edit.ContactEmailAddressEdit;
import com.echothree.control.user.contact.common.form.EditContactEmailAddressForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.EditContactEmailAddressResult;
import com.echothree.control.user.contact.common.spec.PartyContactMechanismSpec;
import com.echothree.model.control.contact.common.ContactConstants;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.contact.server.entity.ContactEmailAddress;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismDetail;
import com.echothree.model.data.contact.server.value.ContactEmailAddressValue;
import com.echothree.model.data.contact.server.value.ContactMechanismDetailValue;
import com.echothree.model.data.contact.server.value.PartyContactMechanismDetailValue;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.user.server.value.UserLoginValue;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditContactEmailAddressCommand
        extends BaseEditCommand<PartyContactMechanismSpec, ContactEmailAddressEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_CUSTOMER, null),
                new PartyTypeDefinition(PartyConstants.PartyType_VENDOR, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactMechanism.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AllowSolicitation", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("EmailAddress", FieldType.EMAIL_ADDRESS, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditContactEmailAddressCommand */
    public EditContactEmailAddressCommand(UserVisitPK userVisitPK, EditContactEmailAddressForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected SecurityResult security() {
        var securityResult = super.security();

        return securityResult != null ? securityResult : selfOnly(spec);
    }

    @Override
    protected BaseResult execute() {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        EditContactEmailAddressResult result = ContactResultFactory.getEditContactEmailAddressResult();
        String partyName = spec.getPartyName();
        Party party = partyName == null ? getParty() : partyControl.getPartyByName(partyName);
        
        if(party != null) {
            ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
            String contactMechanismName = spec.getContactMechanismName();
            ContactMechanism contactMechanism = contactControl.getContactMechanismByName(contactMechanismName);
            
            if(contactMechanism != null) {
                PartyContactMechanismDetailValue partyContactMechanismDetailValue = contactControl.getPartyContactMechanismDetailValueForUpdate(party, contactMechanism);
                
                if(partyContactMechanismDetailValue != null) {
                    ContactMechanismDetail lastContactMechanismDetail = contactMechanism.getLastDetailForUpdate();
                    String contactMechanismTypeName = lastContactMechanismDetail.getContactMechanismType().getContactMechanismTypeName();
                    
                    result.setContactMechanism(contactControl.getContactMechanismTransfer(getUserVisit(), contactMechanism));
                    
                    if(ContactConstants.ContactMechanismType_EMAIL_ADDRESS.equals(contactMechanismTypeName)) {
                        if(editMode.equals(EditMode.LOCK)) {
                            ContactEmailAddress contactEmailAddress = contactControl.getContactEmailAddress(contactMechanism);
                            
                            if(lockEntity(contactMechanism)) {
                                ContactEmailAddressEdit edit = ContactEditFactory.getContactEmailAddressEdit();
                                
                                result.setEdit(edit);
                                edit.setAllowSolicitation(lastContactMechanismDetail.getAllowSolicitation().toString());
                                edit.setEmailAddress(contactEmailAddress.getEmailAddress());
                                edit.setDescription(partyContactMechanismDetailValue.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(contactMechanism));
                        } else if(editMode.equals(EditMode.ABANDON)) {
                            unlockEntity(contactMechanism);
                        } else if(editMode.equals(EditMode.UPDATE)) {
                            UserControl userControl = getUserControl();
                            ContactMechanismDetailValue lastContactMechanismDetailValue = contactControl.getContactMechanismDetailValue(lastContactMechanismDetail);
                            ContactEmailAddressValue contactEmailAddressValue = contactControl.getContactEmailAddressValueForUpdate(contactMechanism);
                            UserLoginValue userLoginValue = userControl.getUserLoginValueForUpdate(party);
                            String emailAddress = edit.getEmailAddress();
                            boolean updateUserLoginValue = false;
                            
                            if(userLoginValue != null && userLoginValue.getUsername().equals(contactEmailAddressValue.getEmailAddress())) {
                                UserLogin duplicateUserLogin = userControl.getUserLoginByUsername(emailAddress);
                                
                                if(duplicateUserLogin != null && !duplicateUserLogin.getParty().equals(party)) {
                                    addExecutionError(ExecutionErrors.DuplicateUsername.name());
                                } else {
                                    updateUserLoginValue = true;
                                }
                            }
                            
                            if(!hasExecutionErrors()) {
                                if(lockEntityForUpdate(contactMechanism)) {
                                    try {
                                        BasePK updatedBy = getPartyPK();
                                        
                                        if(updateUserLoginValue) {
                                            // TODO: Lock Party?
                                            userLoginValue.setUsername(emailAddress);
                                            userControl.updateUserLoginFromValue(userLoginValue, updatedBy);
                                        }
                                        
                                        lastContactMechanismDetailValue.setAllowSolicitation(Boolean.valueOf(edit.getAllowSolicitation()));
                                        contactEmailAddressValue.setEmailAddress(emailAddress);
                                        partyContactMechanismDetailValue.setDescription(edit.getDescription());
                                        
                                        contactControl.updateContactMechanismFromValue(lastContactMechanismDetailValue, updatedBy);
                                        contactControl.updateContactEmailAddressFromValue(contactEmailAddressValue, updatedBy);
                                        contactControl.updatePartyContactMechanismFromValue(partyContactMechanismDetailValue, updatedBy);
                                    } finally {
                                        unlockEntity(contactMechanism);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                                
                                if(hasExecutionErrors()) {
                                    result.setContactMechanism(contactControl.getContactMechanismTransfer(getUserVisit(), contactMechanism));
                                    result.setEntityLock(getEntityLockTransfer(contactMechanism));
                                }
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidContactMechanismType.name(), contactMechanismTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyContactMechanism.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return result;
    }
    
}

