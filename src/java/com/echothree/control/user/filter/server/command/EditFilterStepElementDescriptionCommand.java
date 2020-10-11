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

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.edit.FilterStepElementDescriptionEdit;
import com.echothree.control.user.filter.common.form.EditFilterStepElementDescriptionForm;
import com.echothree.control.user.filter.common.result.EditFilterStepElementDescriptionResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterStepElementDescriptionSpec;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepElement;
import com.echothree.model.data.filter.server.entity.FilterStepElementDescription;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.filter.server.value.FilterStepElementDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditFilterStepElementDescriptionCommand
        extends BaseEditCommand<FilterStepElementDescriptionSpec, FilterStepElementDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FilterStepName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FilterStepElementName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of EditFilterStepElementDescriptionCommand */
    public EditFilterStepElementDescriptionCommand(UserVisitPK userVisitPK, EditFilterStepElementDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = (FilterControl)Session.getModelController(FilterControl.class);
        EditFilterStepElementDescriptionResult result = FilterResultFactory.getEditFilterStepElementDescriptionResult();
        String filterKindName = spec.getFilterKindName();
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            String filterTypeName = spec.getFilterTypeName();
            FilterType filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);
            
            if(filterType != null) {
                String filterName = spec.getFilterName();
                Filter filter = filterControl.getFilterByName(filterType, filterName);
                
                if(filter != null) {
                    String filterStepName = spec.getFilterStepName();
                    FilterStep filterStep = filterControl.getFilterStepByName(filter, filterStepName);
                    
                    if(filterStep != null) {
                        String filterStepElementName = spec.getFilterStepElementName();
                        FilterStepElement filterStepElement = filterControl.getFilterStepElementByName(filterStep, filterStepElementName);
                        
                        if(filterStepElement != null) {
                            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                            String languageIsoName = spec.getLanguageIsoName();
                            Language language = partyControl.getLanguageByIsoName(languageIsoName);
                            
                            if(language != null) {
                                if(editMode.equals(EditMode.LOCK)) {
                                    FilterStepElementDescription filterStepElementDescription = filterControl.getFilterStepElementDescription(filterStepElement, language);
                                    
                                    if(filterStepElementDescription != null) {
                                        result.setFilterStepElementDescription(filterControl.getFilterStepElementDescriptionTransfer(getUserVisit(), filterStepElementDescription));
                                        
                                        if(lockEntity(filterStepElement)) {
                                            FilterStepElementDescriptionEdit edit = FilterEditFactory.getFilterStepElementDescriptionEdit();
                                            
                                            result.setEdit(edit);
                                            edit.setDescription(filterStepElementDescription.getDescription());
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                        }
                                        
                                        result.setEntityLock(getEntityLockTransfer(filterStepElement));
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownFilterStepElementDescription.name());
                                    }
                                } else if(editMode.equals(EditMode.UPDATE)) {
                                    FilterStepElementDescriptionValue filterStepElementDescriptionValue = filterControl.getFilterStepElementDescriptionValueForUpdate(filterStepElement, language);
                                    
                                    if(filterStepElementDescriptionValue != null) {
                                        if(lockEntityForUpdate(filterStepElement)) {
                                            try {
                                                String description = edit.getDescription();
                                                
                                                filterStepElementDescriptionValue.setDescription(description);
                                                
                                                filterControl.updateFilterStepElementDescriptionFromValue(filterStepElementDescriptionValue, getPartyPK());
                                            } finally {
                                                unlockEntity(filterStepElement);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownFilterStepElementDescription.name());
                                    }
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownFilterStepElementName.name(), filterStepElementName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownFilterStepName.name(), filterStepName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownFilterName.name(), filterName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFilterTypeName.name(), filterTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }
        
        return result;
    }
    
}
