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

import com.echothree.control.user.letter.common.edit.LetterEdit;
import com.echothree.control.user.letter.common.edit.LetterEditFactory;
import com.echothree.control.user.letter.common.form.EditLetterForm;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.control.user.letter.common.spec.LetterSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
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

public class EditLetterCommand
        extends BaseEditCommand<LetterSpec, LetterEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Letter.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LetterName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LetterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LetterSourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditLetterCommand */
    public EditLetterCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var chainControl = Session.getModelController(ChainControl.class);
        var result = LetterResultFactory.getEditLetterResult();
        var chainKindName = spec.getChainKindName();
        var chainKind = chainControl.getChainKindByName(chainKindName);
        
        if(chainKind != null) {
            var chainTypeName = spec.getChainTypeName();
            var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);
            
            if(chainType != null) {
                var letterControl = Session.getModelController(LetterControl.class);
                
                if(editMode.equals(EditMode.LOCK)) {
                    var letterName = spec.getLetterName();
                    var letter = letterControl.getLetterByName(chainType, letterName);
                    
                    if(letter != null) {
                        result.setLetter(letterControl.getLetterTransfer(getUserVisit(), letter));
                        
                        if(lockEntity(letter)) {
                            var letterDescription = letterControl.getLetterDescription(letter, getPreferredLanguage());
                            var edit = LetterEditFactory.getLetterEdit();
                            var letterDetail = letter.getLastDetail();
                            var contactList = letterDetail.getContactList();
                            
                            result.setEdit(edit);
                            edit.setLetterName(letterDetail.getLetterName());
                            edit.setLetterSourceName(letterDetail.getLetterSource().getLastDetail().getLetterSourceName());
                            edit.setContactListName(contactList == null? null: contactList.getLastDetail().getContactListName());
                            edit.setIsDefault(letterDetail.getIsDefault().toString());
                            edit.setSortOrder(letterDetail.getSortOrder().toString());
                            
                            if(letterDescription != null)
                                edit.setDescription(letterDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(letter));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLetterName.name(), letterName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var letterName = spec.getLetterName();
                    var letter = letterControl.getLetterByNameForUpdate(chainType, letterName);
                    
                    if(letter != null) {
                        letterName = edit.getLetterName();
                        var duplicateLetter = letterControl.getLetterByName(chainType, letterName);
                        
                        if(duplicateLetter == null || letter.equals(duplicateLetter)) {
                            var letterSourceName = edit.getLetterSourceName();
                            var letterSource = letterControl.getLetterSourceByName(letterSourceName);
                            
                            if(letterSource != null) {
                                var contactListControl = Session.getModelController(ContactListControl.class);
                                var contactListName = edit.getContactListName();
                                var contactList = contactListName == null? null: contactListControl.getContactListByName(contactListName);
                                
                                if(contactListName == null || contactList != null) {
                                    if(lockEntityForUpdate(letter)) {
                                        try {
                                            var partyPK = getPartyPK();
                                            var letterDetailValue = letterControl.getLetterDetailValueForUpdate(letter);
                                            var letterDescription = letterControl.getLetterDescriptionForUpdate(letter, getPreferredLanguage());
                                            var description = edit.getDescription();
                                            
                                            letterDetailValue.setLetterName(edit.getLetterName());
                                            letterDetailValue.setLetterSourcePK(letterSource.getPrimaryKey());
                                            letterDetailValue.setContactListPK(contactList == null? null: contactList.getPrimaryKey());
                                            letterDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                            letterDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                            
                                            letterControl.updateLetterFromValue(letterDetailValue, partyPK);
                                            
                                            if(letterDescription == null && description != null) {
                                                letterControl.createLetterDescription(letter, getPreferredLanguage(), description, partyPK);
                                            } else if(letterDescription != null && description == null) {
                                                letterControl.deleteLetterDescription(letterDescription, partyPK);
                                            } else if(letterDescription != null && description != null) {
                                                var letterDescriptionValue = letterControl.getLetterDescriptionValue(letterDescription);
                                                
                                                letterDescriptionValue.setDescription(description);
                                                letterControl.updateLetterDescriptionFromValue(letterDescriptionValue, partyPK);
                                            }
                                        } finally {
                                            unlockEntity(letter);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownContactListName.name(), contactListName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownLetterSourceName.name(), letterSourceName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateLetterName.name(), letterName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLetterName.name(), letterName);
                    }
                    
                    if(hasExecutionErrors()) {
                        result.setLetter(letterControl.getLetterTransfer(getUserVisit(), letter));
                        result.setEntityLock(getEntityLockTransfer(letter));
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }
        
        return result;
    }
    
}
