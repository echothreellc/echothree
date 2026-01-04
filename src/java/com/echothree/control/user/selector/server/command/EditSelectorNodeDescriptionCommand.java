// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.control.user.selector.common.edit.SelectorEditFactory;
import com.echothree.control.user.selector.common.edit.SelectorNodeDescriptionEdit;
import com.echothree.control.user.selector.common.form.EditSelectorNodeDescriptionForm;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.control.user.selector.common.spec.SelectorNodeDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.selector.server.control.SelectorControl;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditSelectorNodeDescriptionCommand
        extends BaseEditCommand<SelectorNodeDescriptionSpec, SelectorNodeDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SelectorNodeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditSelectorNodeDescriptionCommand */
    public EditSelectorNodeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var result = SelectorResultFactory.getEditSelectorNodeDescriptionResult();
        var selectorKindName = spec.getSelectorKindName();
        var selectorKind = selectorControl.getSelectorKindByName(selectorKindName);
        
        if(selectorKind != null) {
            var selectorTypeName = spec.getSelectorTypeName();
            var selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);
            
            if(selectorType != null) {
                var selectorName = spec.getSelectorName();
                var selector = selectorControl.getSelectorByName(selectorType, selectorName);
                
                if(selector != null) {
                    var selectorNodeName = spec.getSelectorNodeName();
                    var selectorNode = selectorControl.getSelectorNodeByName(selector, selectorNodeName);
                    
                    if(selectorNode != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        var languageIsoName = spec.getLanguageIsoName();
                        var language = partyControl.getLanguageByIsoName(languageIsoName);
                        
                        if(language != null) {
                            if(editMode.equals(EditMode.LOCK)) {
                                var selectorNodeDescription = selectorControl.getSelectorNodeDescription(selectorNode, language);
                                
                                if(selectorNodeDescription != null) {
                                    result.setSelectorNodeDescription(selectorControl.getSelectorNodeDescriptionTransfer(getUserVisit(), selectorNodeDescription));
                                    
                                    if(lockEntity(selectorNode)) {
                                        var edit = SelectorEditFactory.getSelectorNodeDescriptionEdit();
                                        
                                        result.setEdit(edit);
                                        edit.setDescription(selectorNodeDescription.getDescription());
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                    }
                                    
                                    result.setEntityLock(getEntityLockTransfer(selectorNode));
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownSelectorNodeDescription.name());
                                }
                            } else if(editMode.equals(EditMode.UPDATE)) {
                                var selectorNodeDescriptionValue = selectorControl.getSelectorNodeDescriptionValueForUpdate(selectorNode, language);
                                
                                if(selectorNodeDescriptionValue != null) {
                                    if(lockEntityForUpdate(selectorNode)) {
                                        try {
                                            var description = edit.getDescription();
                                            
                                            selectorNodeDescriptionValue.setDescription(description);
                                            
                                            selectorControl.updateSelectorNodeDescriptionFromValue(selectorNodeDescriptionValue, getPartyPK());
                                        } finally {
                                            unlockEntity(selectorNode);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownSelectorNodeDescription.name());
                                }
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorNodeName.name(), selectorNodeName);
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
