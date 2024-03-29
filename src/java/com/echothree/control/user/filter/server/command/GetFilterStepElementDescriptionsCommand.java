// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.filter.common.form.GetFilterStepElementDescriptionsForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.result.GetFilterStepElementDescriptionsResult;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepElement;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetFilterStepElementDescriptionsCommand
        extends BaseSimpleCommand<GetFilterStepElementDescriptionsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Filter.name(), SecurityRoles.FilterStepElement.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterStepElementName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetFilterStepElementDescriptionsCommand */
    public GetFilterStepElementDescriptionsCommand(UserVisitPK userVisitPK, GetFilterStepElementDescriptionsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = Session.getModelController(FilterControl.class);
        GetFilterStepElementDescriptionsResult result = FilterResultFactory.getGetFilterStepElementDescriptionsResult();
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
                    String filterStepName = form.getFilterStepName();
                    FilterStep filterStep = filterControl.getFilterStepByName(filter, filterStepName);
                    
                    result.setFilter(filterControl.getFilterTransfer(userVisit, filter));
                    
                    if(filterStep != null) {
                        String filterStepElementName = form.getFilterStepElementName();
                        FilterStepElement filterStepElement = filterControl.getFilterStepElementByName(filterStep, filterStepElementName);
                        
                        result.setFilterStep(filterControl.getFilterStepTransfer(userVisit, filterStep));
                        
                        if(filterStepElement != null) {
                            result.setFilterStepElement(filterControl.getFilterStepElementTransfer(userVisit, filterStepElement));
                            result.setFilterStepElementDescriptions(filterControl.getFilterStepElementDescriptionTransfers(userVisit, filterStepElement));
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
