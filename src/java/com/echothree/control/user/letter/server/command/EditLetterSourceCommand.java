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

package com.echothree.control.user.letter.server.command;

import com.echothree.control.user.letter.common.edit.LetterEditFactory;
import com.echothree.control.user.letter.common.edit.LetterSourceEdit;
import com.echothree.control.user.letter.common.form.EditLetterSourceForm;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.control.user.letter.common.spec.LetterSourceSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditLetterSourceCommand
        extends BaseEditCommand<LetterSourceSpec, LetterSourceEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.LetterSource.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LetterSourceName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LetterSourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EmailAddressContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmailAddressContactMechanismAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmailAddressContactMechanismAlias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PostalAddressContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PostalAddressContactMechanismAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PostalAddressContactMechanismAlias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LetterSourceContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LetterSourceContactMechanismAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LetterSourceContactMechanismAlias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditLetterSourceCommand */
    public EditLetterSourceCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var letterControl = Session.getModelController(LetterControl.class);
        var result = LetterResultFactory.getEditLetterSourceResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var letterSourceName = spec.getLetterSourceName();
            var letterSource = letterControl.getLetterSourceByName(letterSourceName);
            
            if(letterSource != null) {
                result.setLetterSource(letterControl.getLetterSourceTransfer(getUserVisit(), letterSource));
                
                if(lockEntity(letterSource)) {
                    var letterSourceDescription = letterControl.getLetterSourceDescription(letterSource, getPreferredLanguage());
                    var edit = LetterEditFactory.getLetterSourceEdit();
                    var letterSourceDetail = letterSource.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setLetterSourceName(letterSourceDetail.getLetterSourceName());
                    edit.setEmailAddressContactMechanismName(letterSourceDetail.getEmailAddressPartyContactMechanism().getLastDetail().getContactMechanism().getLastDetail().getContactMechanismName());
                    edit.setPostalAddressContactMechanismName(letterSourceDetail.getPostalAddressPartyContactMechanism().getLastDetail().getContactMechanism().getLastDetail().getContactMechanismName());
                    edit.setLetterSourceContactMechanismName(letterSourceDetail.getLetterSourcePartyContactMechanism().getLastDetail().getContactMechanism().getLastDetail().getContactMechanismName());
                    edit.setIsDefault(letterSourceDetail.getIsDefault().toString());
                    edit.setSortOrder(letterSourceDetail.getSortOrder().toString());
                    
                    if(letterSourceDescription != null)
                        edit.setDescription(letterSourceDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(letterSource));
            } else {
                addExecutionError(ExecutionErrors.UnknownLetterSourceName.name(), letterSourceName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var letterSourceName = spec.getLetterSourceName();
            var letterSource = letterControl.getLetterSourceByNameForUpdate(letterSourceName);
            
            if(letterSource != null) {
                letterSourceName = edit.getLetterSourceName();
                var duplicateLetterSource = letterControl.getLetterSourceByName(letterSourceName);
                
                if(duplicateLetterSource == null || letterSource.equals(duplicateLetterSource)) {
                    var contactControl = Session.getModelController(ContactControl.class);
                    var companyParty = letterSource.getLastDetail().getCompanyParty();
                    var letterSourceCommandUtil = LetterSourceCommandUtil.getInstance();
                    var emailAddressPartyContactMechanism = letterSourceCommandUtil.getEmailAddressContactMechanism(this, edit, contactControl,
                            companyParty);
                    
                    if(!hasExecutionErrors()) {
                        var postalAddressPartyContactMechanism = letterSourceCommandUtil.getPostalAddressContactMechanism(this, edit,
                                contactControl, companyParty);
                        
                        if(!hasExecutionErrors()) {
                            var letterSourcePartyContactMechanism = letterSourceCommandUtil.getLetterSourceContactMechanism(this, edit,
                                    contactControl, companyParty);
                            
                            if(!hasExecutionErrors()) {
                                if(lockEntityForUpdate(letterSource)) {
                                    try {
                                        var partyPK = getPartyPK();
                                        var letterSourceDetailValue = letterControl.getLetterSourceDetailValueForUpdate(letterSource);
                                        var letterSourceDescription = letterControl.getLetterSourceDescriptionForUpdate(letterSource, getPreferredLanguage());
                                        var description = edit.getDescription();
                                        
                                        letterSourceDetailValue.setLetterSourceName(edit.getLetterSourceName());
                                        letterSourceDetailValue.setEmailAddressPartyContactMechanismPK(emailAddressPartyContactMechanism.getPrimaryKey());
                                        letterSourceDetailValue.setPostalAddressPartyContactMechanismPK(postalAddressPartyContactMechanism.getPrimaryKey());
                                        letterSourceDetailValue.setLetterSourcePartyContactMechanismPK(letterSourcePartyContactMechanism.getPrimaryKey());
                                        letterSourceDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                        letterSourceDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                        
                                        letterControl.updateLetterSourceFromValue(letterSourceDetailValue, partyPK);
                                        
                                        if(letterSourceDescription == null && description != null) {
                                            letterControl.createLetterSourceDescription(letterSource, getPreferredLanguage(), description, partyPK);
                                        } else if(letterSourceDescription != null && description == null) {
                                            letterControl.deleteLetterSourceDescription(letterSourceDescription, partyPK);
                                        } else if(letterSourceDescription != null && description != null) {
                                            var letterSourceDescriptionValue = letterControl.getLetterSourceDescriptionValue(letterSourceDescription);
                                            
                                            letterSourceDescriptionValue.setDescription(description);
                                            letterControl.updateLetterSourceDescriptionFromValue(letterSourceDescriptionValue, partyPK);
                                        }
                                    } finally {
                                        unlockEntity(letterSource);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            }
                        }
                    }
                    
                } else {
                    addExecutionError(ExecutionErrors.DuplicateLetterSourceName.name(), letterSourceName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLetterSourceName.name(), letterSourceName);
            }
            
            if(hasExecutionErrors()) {
                result.setLetterSource(letterControl.getLetterSourceTransfer(getUserVisit(), letterSource));
                result.setEntityLock(getEntityLockTransfer(letterSource));
            }
        }
        
        return result;
    }
    
}
