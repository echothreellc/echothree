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

import com.echothree.control.user.letter.common.edit.LetterContactMechanismPurposeEdit;
import com.echothree.control.user.letter.common.edit.LetterEditFactory;
import com.echothree.control.user.letter.common.form.EditLetterContactMechanismPurposeForm;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.control.user.letter.common.spec.LetterContactMechanismPurposeSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
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

public class EditLetterContactMechanismPurposeCommand
        extends BaseEditCommand<LetterContactMechanismPurposeSpec, LetterContactMechanismPurposeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
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
    public EditLetterContactMechanismPurposeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var chainControl = Session.getModelController(ChainControl.class);
        var result = LetterResultFactory.getEditLetterContactMechanismPurposeResult();
        var chainKindName = spec.getChainKindName();
        var chainKind = chainControl.getChainKindByName(chainKindName);
        
        if(chainKind != null) {
            var chainTypeName = spec.getChainTypeName();
            var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);
            
            if(chainType != null) {
                var letterControl = Session.getModelController(LetterControl.class);
                var letterName = spec.getLetterName();
                var letter = letterControl.getLetterByName(chainType, letterName);
                
                if(letter != null) {
                    var priority = Integer.valueOf(spec.getPriority());
                    
                    if(editMode.equals(EditMode.LOCK)) {
                        var letterContactMechanismPurpose = letterControl.getLetterContactMechanismPurpose(letter,
                                priority);
                        
                        if(letterContactMechanismPurpose != null) {
                            result.setLetterContactMechanismPurpose(letterControl.getLetterContactMechanismPurposeTransfer(getUserVisit(),
                                    letterContactMechanismPurpose));
                            
                            if(lockEntity(letter)) {
                                var letterContactMechanismPurposeDetail = letterContactMechanismPurpose.getLastDetail();
                                var edit = LetterEditFactory.getLetterContactMechanismPurposeEdit();
                                
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
                        var letterContactMechanismPurpose = letterControl.getLetterContactMechanismPurposeForUpdate(letter,
                                priority);
                        var letterContactMechanismPurposeDetailValue = letterControl.getLetterContactMechanismPurposeDetailValueForUpdate(letterContactMechanismPurpose);
                        
                        if(letterContactMechanismPurposeDetailValue != null) {
                            var contactControl = Session.getModelController(ContactControl.class);
                            var contactMechanismPurposeName = edit.getContactMechanismPurposeName();
                            var contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(contactMechanismPurposeName);
                            
                            if(contactMechanismPurpose != null) {
                                var contactMechanismTypeName = contactMechanismPurpose.getContactMechanismType().getContactMechanismTypeName();
                                
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
