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

package com.echothree.control.user.letter.server.command;

import com.echothree.control.user.letter.common.edit.LetterContactMechanismPurposeEdit;
import com.echothree.control.user.letter.common.edit.LetterEditFactory;
import com.echothree.control.user.letter.common.form.EditLetterContactMechanismPurposeForm;
import com.echothree.control.user.letter.common.result.EditLetterContactMechanismPurposeResult;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.control.user.letter.common.spec.LetterContactMechanismPurposeSpec;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.letter.server.LetterControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.letter.server.entity.LetterContactMechanismPurpose;
import com.echothree.model.data.letter.server.entity.LetterContactMechanismPurposeDetail;
import com.echothree.model.data.letter.server.value.LetterContactMechanismPurposeDetailValue;
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

public class EditLetterContactMechanismPurposeCommand
        extends BaseEditCommand<LetterContactMechanismPurposeSpec, LetterContactMechanismPurposeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                new SecurityRoleDefinition(SecurityRoleGroups.LetterContactMechanismPurpose.name(), SecurityRoles.Edit.name())
                )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LetterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Priority", FieldType.SIGNED_INTEGER, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactMechanismPurposeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditLetterContactMechanismPurposeCommand */
    public EditLetterContactMechanismPurposeCommand(UserVisitPK userVisitPK, EditLetterContactMechanismPurposeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        EditLetterContactMechanismPurposeResult result = LetterResultFactory.getEditLetterContactMechanismPurposeResult();
        String chainKindName = spec.getChainKindName();
        ChainKind chainKind = chainControl.getChainKindByName(chainKindName);
        
        if(chainKind != null) {
            String chainTypeName = spec.getChainTypeName();
            ChainType chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);
            
            if(chainType != null) {
                var letterControl = (LetterControl)Session.getModelController(LetterControl.class);
                String letterName = spec.getLetterName();
                Letter letter = letterControl.getLetterByName(chainType, letterName);
                
                if(letter != null) {
                    Integer priority = Integer.valueOf(spec.getPriority());
                    
                    if(editMode.equals(EditMode.LOCK)) {
                        LetterContactMechanismPurpose letterContactMechanismPurpose = letterControl.getLetterContactMechanismPurpose(letter,
                                priority);
                        
                        if(letterContactMechanismPurpose != null) {
                            result.setLetterContactMechanismPurpose(letterControl.getLetterContactMechanismPurposeTransfer(getUserVisit(),
                                    letterContactMechanismPurpose));
                            
                            if(lockEntity(letter)) {
                                LetterContactMechanismPurposeDetail letterContactMechanismPurposeDetail = letterContactMechanismPurpose.getLastDetail();
                                LetterContactMechanismPurposeEdit edit = LetterEditFactory.getLetterContactMechanismPurposeEdit();
                                
                                result.setEdit(edit);
                                edit.setContactMechanismPurposeName(letterContactMechanismPurposeDetail.getContactMechanismPurpose().getContactMechanismPurposeName());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(letter));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLetterContactMechanismPurpose.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        LetterContactMechanismPurpose letterContactMechanismPurpose = letterControl.getLetterContactMechanismPurposeForUpdate(letter,
                                priority);
                        LetterContactMechanismPurposeDetailValue letterContactMechanismPurposeDetailValue = letterControl.getLetterContactMechanismPurposeDetailValueForUpdate(letterContactMechanismPurpose);
                        
                        if(letterContactMechanismPurposeDetailValue != null) {
                            var contactControl = (ContactControl)Session.getModelController(ContactControl.class);
                            String contactMechanismPurposeName = edit.getContactMechanismPurposeName();
                            ContactMechanismPurpose contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(contactMechanismPurposeName);
                            
                            if(contactMechanismPurpose != null) {
                                String contactMechanismTypeName = contactMechanismPurpose.getContactMechanismType().getContactMechanismTypeName();
                                
                                if(contactMechanismTypeName.equals(ContactMechanismTypes.EMAIL_ADDRESS.name())
                                        || contactMechanismTypeName.equals(ContactMechanismTypes.POSTAL_ADDRESS.name())) {
                                    if(lockEntityForUpdate(letter)) {
                                        try {
                                            letterContactMechanismPurposeDetailValue.setContactMechanismPurposePK(contactMechanismPurpose.getPrimaryKey());
                                            
                                            letterControl.updateLetterContactMechanismPurposeFromValue(letterContactMechanismPurposeDetailValue, getPartyPK());
                                        } finally {
                                            unlockEntity(letter);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.InvalidContactMechanismType.name(), contactMechanismTypeName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownContactMechanismPurposeName.name(), contactMechanismPurposeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLetterContactMechanismPurpose.name());
                        }
                        
                        if(hasExecutionErrors()) {
                            result.setLetterContactMechanismPurpose(letterControl.getLetterContactMechanismPurposeTransfer(getUserVisit(),
                                    letterContactMechanismPurpose));
                            result.setEntityLock(getEntityLockTransfer(letter));
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLetterName.name(), letterName);
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
