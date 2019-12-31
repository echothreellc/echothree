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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.edit.TermDescriptionEdit;
import com.echothree.control.user.term.common.edit.TermEditFactory;
import com.echothree.control.user.term.common.form.EditTermDescriptionForm;
import com.echothree.control.user.term.common.result.EditTermDescriptionResult;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.control.user.term.common.spec.TermDescriptionSpec;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.term.server.entity.TermDescription;
import com.echothree.model.data.term.server.value.TermDescriptionValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditTermDescriptionCommand
        extends BaseEditCommand<TermDescriptionSpec, TermDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TermName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditTermDescriptionCommand */
    public EditTermDescriptionCommand(UserVisitPK userVisitPK, EditTermDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var termControl = (TermControl)Session.getModelController(TermControl.class);
        EditTermDescriptionResult result = TermResultFactory.getEditTermDescriptionResult();
        String termName = spec.getTermName();
        Term term = termControl.getTermByName(termName);
        
        if(term != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    TermDescription termDescription = termControl.getTermDescription(term, language);
                    
                    if(termDescription != null) {
                        result.setTermDescription(termControl.getTermDescriptionTransfer(getUserVisit(), termDescription));
                        
                        if(lockEntity(term)) {
                            TermDescriptionEdit edit = TermEditFactory.getTermDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(termDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(term));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTermDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    TermDescriptionValue termDescriptionValue = termControl.getTermDescriptionValueForUpdate(term, language);
                    
                    if(termDescriptionValue != null) {
                        if(lockEntityForUpdate(term)) {
                            try {
                                String description = edit.getDescription();
                                
                                termDescriptionValue.setDescription(description);
                                
                                termControl.updateTermDescriptionFromValue(termDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(term);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTermDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTermName.name(), termName);
        }
        
        return result;
    }
    
}
