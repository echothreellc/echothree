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

import com.echothree.control.user.filter.common.form.CreateFilterAdjustmentForm;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSource;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentType;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateFilterAdjustmentCommand
        extends BaseSimpleCommand<CreateFilterAdjustmentForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterAdjustmentSourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterAdjustmentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateFilterAdjustmentCommand */
    public CreateFilterAdjustmentCommand(UserVisitPK userVisitPK, CreateFilterAdjustmentForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = (FilterControl)Session.getModelController(FilterControl.class);
        String filterKindName = form.getFilterKindName();
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            String filterAdjustmentName = form.getFilterAdjustmentName();
            FilterAdjustment filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);
            
            if(filterAdjustment == null) {
                String filterAdjustmentSourceName = form.getFilterAdjustmentSourceName();
                FilterAdjustmentSource filterAdjustmentSource = filterControl.getFilterAdjustmentSourceByName(filterAdjustmentSourceName);
                
                if(filterAdjustmentSource != null) {
                    String filterAdjustmentTypeName = form.getFilterAdjustmentTypeName();
                    FilterAdjustmentType filterAdjustmentType = filterAdjustmentTypeName == null? null: filterControl.getFilterAdjustmentTypeByName(filterAdjustmentTypeName);
                    
                    if(filterAdjustmentTypeName == null || filterAdjustmentType != null) {
                        PartyPK partyPK = getPartyPK();
                        Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                        Integer sortOrder = Integer.valueOf(form.getSortOrder());
                        String description = form.getDescription();
                        
                        filterAdjustment = filterControl.createFilterAdjustment(filterKind, filterAdjustmentName,
                                filterAdjustmentSource, filterAdjustmentType, isDefault, sortOrder, partyPK);
                        
                        if(description != null) {
                            Language language = getPreferredLanguage();
                            
                            filterControl.createFilterAdjustmentDescription(filterAdjustment, language, description, partyPK);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownFilterAdjustmentTypeName.name(), filterAdjustmentTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownFilterAdjustmentSourceName.name(), filterAdjustmentSourceName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateFilterAdjustmentName.name(), filterAdjustmentName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }
        
        return null;
    }
    
}
