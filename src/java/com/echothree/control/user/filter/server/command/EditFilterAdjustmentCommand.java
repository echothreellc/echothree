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

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.edit.FilterAdjustmentEdit;
import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.form.EditFilterAdjustmentForm;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterAdjustmentSpec;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDescription;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDetail;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSource;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.value.FilterAdjustmentDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterAdjustmentDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditFilterAdjustmentCommand
        extends BaseEditCommand<FilterAdjustmentSpec, FilterAdjustmentEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FilterAdjustmentSourceName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of EditFilterAdjustmentCommand */
    public EditFilterAdjustmentCommand(UserVisitPK userVisitPK, EditFilterAdjustmentForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        FilterControl filterControl = (FilterControl)Session.getModelController(FilterControl.class);
        EditFilterAdjustmentResult result = FilterResultFactory.getEditFilterAdjustmentResult();
        String filterKindName = spec.getFilterKindName();
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            if(editMode.equals(EditMode.LOCK)) {
                String filterAdjustmentName = spec.getFilterAdjustmentName();
                FilterAdjustment filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);
                
                if(filterAdjustment != null) {
                    result.setFilterAdjustment(filterControl.getFilterAdjustmentTransfer(getUserVisit(), filterAdjustment));
                    
                    if(lockEntity(filterAdjustment)) {
                        FilterAdjustmentDescription filterAdjustmentDescription = filterControl.getFilterAdjustmentDescription(filterAdjustment, getPreferredLanguage());
                        FilterAdjustmentEdit edit = FilterEditFactory.getFilterAdjustmentEdit();
                        FilterAdjustmentDetail filterAdjustmentDetail = filterAdjustment.getLastDetail();
                        
                        result.setEdit(edit);
                        edit.setFilterAdjustmentName(filterAdjustmentDetail.getFilterAdjustmentName());
                        edit.setFilterAdjustmentSourceName(filterAdjustmentDetail.getFilterAdjustmentSource().getFilterAdjustmentSourceName());
                        edit.setIsDefault(filterAdjustmentDetail.getIsDefault().toString());
                        edit.setSortOrder(filterAdjustmentDetail.getSortOrder().toString());
                        
                        if(filterAdjustmentDescription != null) {
                            edit.setDescription(filterAdjustmentDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(filterAdjustment));
                } else {
                    addExecutionError(ExecutionErrors.UnknownFilterAdjustmentName.name(), filterAdjustmentName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                String filterAdjustmentName = spec.getFilterAdjustmentName();
                FilterAdjustment filterAdjustment = filterControl.getFilterAdjustmentByNameForUpdate(filterKind, filterAdjustmentName);
                
                if(filterAdjustment != null) {
                    filterAdjustmentName = edit.getFilterAdjustmentName();
                    FilterAdjustment duplicateFilterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);
                    
                    if(duplicateFilterAdjustment == null || filterAdjustment.equals(duplicateFilterAdjustment)) {
                        String filterAdjustmentSourceName = edit.getFilterAdjustmentSourceName();
                        FilterAdjustmentSource filterAdjustmentSource = filterControl.getFilterAdjustmentSourceByName(filterAdjustmentSourceName);
                        
                        if(filterAdjustmentSource != null) {
                            if(lockEntityForUpdate(filterAdjustment)) {
                                try {
                                    PartyPK partyPK = getPartyPK();
                                    FilterAdjustmentDetailValue filterAdjustmentDetailValue = filterControl.getFilterAdjustmentDetailValueForUpdate(filterAdjustment);
                                    FilterAdjustmentDescription filterAdjustmentDescription = filterControl.getFilterAdjustmentDescriptionForUpdate(filterAdjustment, getPreferredLanguage());
                                    String description = edit.getDescription();
                                    
                                    filterAdjustmentDetailValue.setFilterAdjustmentName(edit.getFilterAdjustmentName());
                                    filterAdjustmentDetailValue.setFilterAdjustmentSourcePK(filterAdjustmentSource.getPrimaryKey());
                                    filterAdjustmentDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    filterAdjustmentDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    filterControl.updateFilterAdjustmentFromValue(filterAdjustmentDetailValue, partyPK);
                                    
                                    if(filterAdjustmentDescription == null && description != null) {
                                        filterControl.createFilterAdjustmentDescription(filterAdjustment, getPreferredLanguage(), description, partyPK);
                                    } else if(filterAdjustmentDescription != null && description == null) {
                                        filterControl.deleteFilterAdjustmentDescription(filterAdjustmentDescription, partyPK);
                                    } else if(filterAdjustmentDescription != null && description != null) {
                                        FilterAdjustmentDescriptionValue filterAdjustmentDescriptionValue = filterControl.getFilterAdjustmentDescriptionValue(filterAdjustmentDescription);
                                        
                                        filterAdjustmentDescriptionValue.setDescription(description);
                                        filterControl.updateFilterAdjustmentDescriptionFromValue(filterAdjustmentDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(filterAdjustment);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownFilterAdjustmentSourceName.name(), filterAdjustmentSourceName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateFilterAdjustmentName.name(), filterAdjustmentName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownFilterAdjustmentName.name(), filterAdjustmentName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }
        
        return result;
    }
    
}
