// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.letter.common.result.EditLetterResult;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.control.user.letter.common.spec.LetterSpec;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.letter.server.LetterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.letter.server.entity.LetterDescription;
import com.echothree.model.data.letter.server.entity.LetterDetail;
import com.echothree.model.data.letter.server.entity.LetterSource;
import com.echothree.model.data.letter.server.value.LetterDescriptionValue;
import com.echothree.model.data.letter.server.value.LetterDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditLetterCommand */
    public EditLetterCommand(UserVisitPK userVisitPK, EditLetterForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        EditLetterResult result = LetterResultFactory.getEditLetterResult();
        String chainKindName = spec.getChainKindName();
        ChainKind chainKind = chainControl.getChainKindByName(chainKindName);
        
        if(chainKind != null) {
            String chainTypeName = spec.getChainTypeName();
            ChainType chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);
            
            if(chainType != null) {
                var letterControl = (LetterControl)Session.getModelController(LetterControl.class);
                
                if(editMode.equals(EditMode.LOCK)) {
                    String letterName = spec.getLetterName();
                    Letter letter = letterControl.getLetterByName(chainType, letterName);
                    
                    if(letter != null) {
                        result.setLetter(letterControl.getLetterTransfer(getUserVisit(), letter));
                        
                        if(lockEntity(letter)) {
                            LetterDescription letterDescription = letterControl.getLetterDescription(letter, getPreferredLanguage());
                            LetterEdit edit = LetterEditFactory.getLetterEdit();
                            LetterDetail letterDetail = letter.getLastDetail();
                            ContactList contactList = letterDetail.getContactList();
                            
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
                    String letterName = spec.getLetterName();
                    Letter letter = letterControl.getLetterByNameForUpdate(chainType, letterName);
                    
                    if(letter != null) {
                        letterName = edit.getLetterName();
                        Letter duplicateLetter = letterControl.getLetterByName(chainType, letterName);
                        
                        if(duplicateLetter == null || letter.equals(duplicateLetter)) {
                            String letterSourceName = edit.getLetterSourceName();
                            LetterSource letterSource = letterControl.getLetterSourceByName(letterSourceName);
                            
                            if(letterSource != null) {
                                var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
                                String contactListName = edit.getContactListName();
                                ContactList contactList = contactListName == null? null: contactListControl.getContactListByName(contactListName);
                                
                                if(contactListName == null || contactList != null) {
                                    if(lockEntityForUpdate(letter)) {
                                        try {
                                            PartyPK partyPK = getPartyPK();
                                            LetterDetailValue letterDetailValue = letterControl.getLetterDetailValueForUpdate(letter);
                                            LetterDescription letterDescription = letterControl.getLetterDescriptionForUpdate(letter, getPreferredLanguage());
                                            String description = edit.getDescription();
                                            
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
                                                LetterDescriptionValue letterDescriptionValue = letterControl.getLetterDescriptionValue(letterDescription);
                                                
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
