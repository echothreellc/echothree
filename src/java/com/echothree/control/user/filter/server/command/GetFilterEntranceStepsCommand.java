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

import com.echothree.control.user.filter.common.form.GetFilterEntranceStepsForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.result.GetFilterEntranceStepsResult;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetFilterEntranceStepsCommand
        extends BaseSimpleCommand<GetFilterEntranceStepsForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetFilterEntranceStepsCommand */
    public GetFilterEntranceStepsCommand(UserVisitPK userVisitPK, GetFilterEntranceStepsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        FilterControl filterControl = (FilterControl)Session.getModelController(FilterControl.class);
        GetFilterEntranceStepsResult result = FilterResultFactory.getGetFilterEntranceStepsResult();
        String filterKindName = form.getFilterKindName();
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            UserVisit userVisit = getUserVisit();
            String filterTypeName = form.getFilterTypeName();
            FilterType filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);
            
            result.setFilterKind(filterControl.getFilterKindTransfer(userVisit, filterKind));
            
            if(filterType != null) {
                String filterName = form.getFilterName();
                Filter filter = filterControl.getFilterByName(filterType, filterName);
                
                result.setFilterType(filterControl.getFilterTypeTransfer(userVisit, filterType));
                
                if(filter != null) {
                    result.setFilter(filterControl.getFilterTransfer(userVisit, filter));
                    result.setFilterEntranceSteps(filterControl.getFilterEntranceStepTransfersByFilter(userVisit, filter));
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
