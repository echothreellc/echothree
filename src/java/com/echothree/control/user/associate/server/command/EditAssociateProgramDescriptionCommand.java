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

package com.echothree.control.user.associate.server.command;

import com.echothree.control.user.associate.remote.edit.AssociateEditFactory;
import com.echothree.control.user.associate.remote.edit.AssociateProgramDescriptionEdit;
import com.echothree.control.user.associate.remote.form.EditAssociateProgramDescriptionForm;
import com.echothree.control.user.associate.remote.result.AssociateResultFactory;
import com.echothree.control.user.associate.remote.result.EditAssociateProgramDescriptionResult;
import com.echothree.control.user.associate.remote.spec.AssociateProgramDescriptionSpec;
import com.echothree.model.control.associate.server.AssociateControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.associate.server.entity.AssociateProgram;
import com.echothree.model.data.associate.server.entity.AssociateProgramDescription;
import com.echothree.model.data.associate.server.value.AssociateProgramDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditAssociateProgramDescriptionCommand
        extends BaseEditCommand<AssociateProgramDescriptionSpec, AssociateProgramDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of EditAssociateProgramDescriptionCommand */
    public EditAssociateProgramDescriptionCommand(UserVisitPK userVisitPK, EditAssociateProgramDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        AssociateControl associateControl = (AssociateControl)Session.getModelController(AssociateControl.class);
        EditAssociateProgramDescriptionResult result = AssociateResultFactory.getEditAssociateProgramDescriptionResult();
        String associateProgramName = spec.getAssociateProgramName();
        AssociateProgram associateProgram = associateControl.getAssociateProgramByName(associateProgramName);
        
        if(associateProgram != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    AssociateProgramDescription associateProgramDescription = associateControl.getAssociateProgramDescription(associateProgram, language);
                    
                    if(associateProgramDescription != null) {
                        result.setAssociateProgramDescription(associateControl.getAssociateProgramDescriptionTransfer(getUserVisit(), associateProgramDescription));
                        
                        if(lockEntity(associateProgram)) {
                            AssociateProgramDescriptionEdit edit = AssociateEditFactory.getAssociateProgramDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(associateProgramDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(associateProgram));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownAssociateProgramDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    AssociateProgramDescriptionValue associateProgramDescriptionValue = associateControl.getAssociateProgramDescriptionValueForUpdate(associateProgram, language);
                    
                    if(associateProgramDescriptionValue != null) {
                        if(lockEntityForUpdate(associateProgram)) {
                            try {
                                String description = edit.getDescription();
                                
                                associateProgramDescriptionValue.setDescription(description);
                                
                                associateControl.updateAssociateProgramDescriptionFromValue(associateProgramDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(associateProgram);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownAssociateProgramDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownAssociateProgramName.name(), associateProgramName);
        }
        
        return result;
    }
    
}
