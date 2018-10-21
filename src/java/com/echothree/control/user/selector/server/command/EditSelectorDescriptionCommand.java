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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.remote.edit.SelectorDescriptionEdit;
import com.echothree.control.user.selector.remote.edit.SelectorEditFactory;
import com.echothree.control.user.selector.remote.form.EditSelectorDescriptionForm;
import com.echothree.control.user.selector.remote.result.EditSelectorDescriptionResult;
import com.echothree.control.user.selector.remote.result.SelectorResultFactory;
import com.echothree.control.user.selector.remote.spec.SelectorDescriptionSpec;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorDescription;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.selector.server.value.SelectorDescriptionValue;
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

public class EditSelectorDescriptionCommand
        extends BaseEditCommand<SelectorDescriptionSpec, SelectorDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of EditSelectorDescriptionCommand */
    public EditSelectorDescriptionCommand(UserVisitPK userVisitPK, EditSelectorDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        EditSelectorDescriptionResult result = SelectorResultFactory.getEditSelectorDescriptionResult();
        String selectorKindName = spec.getSelectorKindName();
        SelectorKind selectorKind = selectorControl.getSelectorKindByName(selectorKindName);
        
        if(selectorKind != null) {
            String selectorTypeName = spec.getSelectorTypeName();
            SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);
            
            if(selectorType != null) {
                String selectorName = spec.getSelectorName();
                Selector selector = selectorControl.getSelectorByName(selectorType, selectorName);
                
                if(selector != null) {
                    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                    String languageIsoName = spec.getLanguageIsoName();
                    Language language = partyControl.getLanguageByIsoName(languageIsoName);
                    
                    if(language != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            SelectorDescription selectorDescription = selectorControl.getSelectorDescription(selector, language);
                            
                            if(selectorDescription != null) {
                                result.setSelectorDescription(selectorControl.getSelectorDescriptionTransfer(getUserVisit(), selectorDescription));
                                
                                if(lockEntity(selector)) {
                                    SelectorDescriptionEdit edit = SelectorEditFactory.getSelectorDescriptionEdit();
                                    
                                    result.setEdit(edit);
                                    edit.setDescription(selectorDescription.getDescription());
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }
                                
                                result.setEntityLock(getEntityLockTransfer(selector));
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSelectorDescription.name());
                            }
                        } else if(editMode.equals(EditMode.UPDATE)) {
                            SelectorDescriptionValue selectorDescriptionValue = selectorControl.getSelectorDescriptionValueForUpdate(selector, language);
                            
                            if(selectorDescriptionValue != null) {
                                if(lockEntityForUpdate(selector)) {
                                    try {
                                        String description = edit.getDescription();
                                        
                                        selectorDescriptionValue.setDescription(description);
                                        
                                        selectorControl.updateSelectorDescriptionFromValue(selectorDescriptionValue, getPartyPK());
                                    } finally {
                                        unlockEntity(selector);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSelectorDescription.name());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorName.name(), selectorName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), selectorTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
        }
        
        return result;
    }
    
}
