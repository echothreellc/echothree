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

import com.echothree.control.user.filter.common.edit.FilterAdjustmentDescriptionEdit;
import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.form.EditFilterAdjustmentDescriptionForm;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentDescriptionResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterAdjustmentDescriptionSpec;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDescription;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.value.FilterAdjustmentDescriptionValue;
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

public class EditFilterAdjustmentDescriptionCommand
        extends BaseEditCommand<FilterAdjustmentDescriptionSpec, FilterAdjustmentDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of EditFilterAdjustmentDescriptionCommand */
    public EditFilterAdjustmentDescriptionCommand(UserVisitPK userVisitPK, EditFilterAdjustmentDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = (FilterControl)Session.getModelController(FilterControl.class);
        EditFilterAdjustmentDescriptionResult result = FilterResultFactory.getEditFilterAdjustmentDescriptionResult();
        String filterKindName = spec.getFilterKindName();
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            String filterAdjustmentName = spec.getFilterAdjustmentName();
            FilterAdjustment filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);
            
            if(filterAdjustment != null) {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        FilterAdjustmentDescription filterAdjustmentDescription = filterControl.getFilterAdjustmentDescription(filterAdjustment, language);
                        
                        if(filterAdjustmentDescription != null) {
                            result.setFilterAdjustmentDescription(filterControl.getFilterAdjustmentDescriptionTransfer(getUserVisit(), filterAdjustmentDescription));
                            
                            if(lockEntity(filterAdjustment)) {
                                FilterAdjustmentDescriptionEdit edit = FilterEditFactory.getFilterAdjustmentDescriptionEdit();
                                
                                result.setEdit(edit);
                                edit.setDescription(filterAdjustmentDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(filterAdjustment));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownFilterAdjustmentDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        FilterAdjustmentDescriptionValue filterAdjustmentDescriptionValue = filterControl.getFilterAdjustmentDescriptionValueForUpdate(filterAdjustment, language);
                        
                        if(filterAdjustmentDescriptionValue != null) {
                            if(lockEntityForUpdate(filterAdjustment)) {
                                try {
                                    String description = edit.getDescription();
                                    
                                    filterAdjustmentDescriptionValue.setDescription(description);
                                    
                                    filterControl.updateFilterAdjustmentDescriptionFromValue(filterAdjustmentDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(filterAdjustment);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownFilterAdjustmentDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFilterAdjustmentName.name(), filterAdjustmentName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }
        
        return result;
    }
    
}
