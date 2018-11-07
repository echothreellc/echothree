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

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.form.DeleteFilterStepDestinationForm;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepDestination;
import com.echothree.model.data.filter.server.entity.FilterType;
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

public class DeleteFilterStepDestinationCommand
        extends BaseSimpleCommand<DeleteFilterStepDestinationForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FromFilterStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ToFilterStepName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteFilterStepDestinationCommand */
    public DeleteFilterStepDestinationCommand(UserVisitPK userVisitPK, DeleteFilterStepDestinationForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        FilterControl filterControl = (FilterControl)Session.getModelController(FilterControl.class);
        String filterKindName = form.getFilterKindName();
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            String filterTypeName = form.getFilterTypeName();
            FilterType filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);
            
            if(filterType != null) {
                String filterName = form.getFilterName();
                Filter filter = filterControl.getFilterByName(filterType, filterName);
                
                if(filter != null) {
                    String fromFilterStepName = form.getFromFilterStepName();
                    FilterStep fromFilterStep = filterControl.getFilterStepByName(filter, fromFilterStepName);
                    
                    if(fromFilterStep != null) {
                        String toFilterStepName = form.getToFilterStepName();
                        FilterStep toFilterStep = filterControl.getFilterStepByName(filter, toFilterStepName);
                        
                        if(toFilterStep != null) {
                            FilterStepDestination filterStepDestination = filterControl.getFilterStepDestinationForUpdate(fromFilterStep, toFilterStep);
                            
                            if(filterStepDestination != null) {
                                filterControl.deleteFilterStepDestination(filterStepDestination, getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.UnknownFilterStepDestination.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownToFilterStepName.name(), toFilterStepName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownFromFilterStepName.name(), fromFilterStepName);
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
        
        return null;
    }
    
}
