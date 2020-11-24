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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.edit.SelectorEdit;
import com.echothree.control.user.selector.common.edit.SelectorEditFactory;
import com.echothree.control.user.selector.common.form.EditSelectorForm;
import com.echothree.control.user.selector.common.result.EditSelectorResult;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.control.user.selector.common.spec.SelectorSpec;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorDescription;
import com.echothree.model.data.selector.server.entity.SelectorDetail;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.selector.server.value.SelectorDescriptionValue;
import com.echothree.model.data.selector.server.value.SelectorDetailValue;
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

public class EditSelectorCommand
        extends BaseEditCommand<SelectorSpec, SelectorEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("InitialSelectorAdjustmentName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("SelectorItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of EditSelectorCommand */
    public EditSelectorCommand(UserVisitPK userVisitPK, EditSelectorForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var selectorControl = Session.getModelController(SelectorControl.class);
        EditSelectorResult result = SelectorResultFactory.getEditSelectorResult();
        String selectorKindName = spec.getSelectorKindName();
        SelectorKind selectorKind = selectorControl.getSelectorKindByName(selectorKindName);
        
        if(selectorKind != null) {
            String selectorTypeName = spec.getSelectorTypeName();
            SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);
            
            if(selectorType != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    String selectorName = spec.getSelectorName();
                    Selector selector = selectorControl.getSelectorByName(selectorType, selectorName);
                    
                    if(selector != null) {
                        result.setSelector(selectorControl.getSelectorTransfer(getUserVisit(), selector));
                        
                        if(lockEntity(selector)) {
                            SelectorDescription selectorDescription = selectorControl.getSelectorDescription(selector, getPreferredLanguage());
                            SelectorEdit edit = SelectorEditFactory.getSelectorEdit();
                            SelectorDetail selectorDetail = selector.getLastDetail();
                            
                            result.setEdit(edit);
                            edit.setSelectorName(selectorDetail.getSelectorName());
                            edit.setIsDefault(selectorDetail.getIsDefault().toString());
                            edit.setSortOrder(selectorDetail.getSortOrder().toString());
                            
                            if(selectorDescription != null) {
                                edit.setDescription(selectorDescription.getDescription());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(selector));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorName.name(), selectorName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    String selectorName = spec.getSelectorName();
                    Selector selector = selectorControl.getSelectorByNameForUpdate(selectorType, selectorName);
                    
                    if(selector != null) {
                        selectorName = edit.getSelectorName();
                        Selector duplicateSelector = selectorControl.getSelectorByName(selectorType, selectorName);
                        
                        if(duplicateSelector == null || selector.equals(duplicateSelector)) {
                            if(lockEntityForUpdate(selector)) {
                                try {
                                    var partyPK = getPartyPK();
                                    SelectorDetailValue selectorDetailValue = selectorControl.getSelectorDetailValueForUpdate(selector);
                                    SelectorDescription selectorDescription = selectorControl.getSelectorDescriptionForUpdate(selector, getPreferredLanguage());
                                    String description = edit.getDescription();
                                    
                                    selectorDetailValue.setSelectorName(edit.getSelectorName());
                                    selectorDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    selectorDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    selectorControl.updateSelectorFromValue(selectorDetailValue, partyPK);
                                    
                                    if(selectorDescription == null && description != null) {
                                        selectorControl.createSelectorDescription(selector, getPreferredLanguage(), description, partyPK);
                                    } else if(selectorDescription != null && description == null) {
                                        selectorControl.deleteSelectorDescription(selectorDescription, partyPK);
                                    } else if(selectorDescription != null && description != null) {
                                        SelectorDescriptionValue selectorDescriptionValue = selectorControl.getSelectorDescriptionValue(selectorDescription);
                                        
                                        selectorDescriptionValue.setDescription(description);
                                        selectorControl.updateSelectorDescriptionFromValue(selectorDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(selector);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateSelectorName.name(), selectorName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorName.name(), selectorName);
                    }
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
