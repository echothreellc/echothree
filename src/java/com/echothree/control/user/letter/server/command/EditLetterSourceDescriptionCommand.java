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

package com.echothree.control.user.letter.server.command;

import com.echothree.control.user.letter.common.edit.LetterEditFactory;
import com.echothree.control.user.letter.common.edit.LetterSourceDescriptionEdit;
import com.echothree.control.user.letter.common.form.EditLetterSourceDescriptionForm;
import com.echothree.control.user.letter.common.result.EditLetterSourceDescriptionResult;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.control.user.letter.common.spec.LetterSourceDescriptionSpec;
import com.echothree.model.control.letter.server.LetterControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.letter.server.entity.LetterSource;
import com.echothree.model.data.letter.server.entity.LetterSourceDescription;
import com.echothree.model.data.letter.server.value.LetterSourceDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditLetterSourceDescriptionCommand
        extends BaseEditCommand<LetterSourceDescriptionSpec, LetterSourceDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LetterSource.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LetterSourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditLetterSourceDescriptionCommand */
    public EditLetterSourceDescriptionCommand(UserVisitPK userVisitPK, EditLetterSourceDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        LetterControl letterControl = (LetterControl)Session.getModelController(LetterControl.class);
        EditLetterSourceDescriptionResult result = LetterResultFactory.getEditLetterSourceDescriptionResult();
        String letterSourceName = spec.getLetterSourceName();
        LetterSource letterSource = letterControl.getLetterSourceByName(letterSourceName);
        
        if(letterSource != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    LetterSourceDescription letterSourceDescription = letterControl.getLetterSourceDescription(letterSource, language);
                    
                    if(letterSourceDescription != null) {
                        result.setLetterSourceDescription(letterControl.getLetterSourceDescriptionTransfer(getUserVisit(), letterSourceDescription));
                        
                        if(lockEntity(letterSource)) {
                            LetterSourceDescriptionEdit edit = LetterEditFactory.getLetterSourceDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(letterSourceDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(letterSource));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLetterSourceDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    LetterSourceDescriptionValue letterSourceDescriptionValue = letterControl.getLetterSourceDescriptionValueForUpdate(letterSource, language);
                    
                    if(letterSourceDescriptionValue != null) {
                        if(lockEntityForUpdate(letterSource)) {
                            try {
                                String description = edit.getDescription();
                                
                                letterSourceDescriptionValue.setDescription(description);
                                
                                letterControl.updateLetterSourceDescriptionFromValue(letterSourceDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(letterSource);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLetterSourceDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownLetterSourceName.name(), letterSourceName);
        }
        
        return result;
    }
    
}
