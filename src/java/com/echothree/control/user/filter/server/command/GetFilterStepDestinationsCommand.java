// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.filter.common.form.GetFilterStepDestinationsForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.result.GetFilterStepDestinationsResult;
import com.echothree.model.control.filter.common.transfer.FilterStepDestinationTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterStep;
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

public class GetFilterStepDestinationsCommand
        extends BaseSimpleCommand<GetFilterStepDestinationsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Filter.name(), SecurityRoles.FilterStepDestination.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FromFilterStepName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ToFilterStepName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetFilterStepDestinationsCommand */
    public GetFilterStepDestinationsCommand(UserVisitPK userVisitPK, GetFilterStepDestinationsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = Session.getModelController(FilterControl.class);
        GetFilterStepDestinationsResult result = FilterResultFactory.getGetFilterStepDestinationsResult();
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
                    String fromFilterStepName = form.getFromFilterStepName();
                    String toFilterStepName = form.getToFilterStepName();
                    List<FilterStepDestinationTransfer> filterStepDestinationTransfers = null;
                    
                    result.setFilter(filterControl.getFilterTransfer(userVisit, filter));
                    
                    if(fromFilterStepName != null && toFilterStepName == null) {
                        FilterStep fromFilterStep = filterControl.getFilterStepByName(filter, fromFilterStepName);
                        
                        if(fromFilterStep != null) {
                            result.setFromFilterStep(filterControl.getFilterStepTransfer(userVisit, fromFilterStep));
                            filterStepDestinationTransfers = filterControl.getFilterStepDestinationTransfersByFromFilterStep(userVisit, fromFilterStep);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownFromFilterStepName.name(), fromFilterStepName);
                        }
                    } else if(fromFilterStepName == null && toFilterStepName != null) {
                        FilterStep toFilterStep = filterControl.getFilterStepByName(filter, toFilterStepName);
                        
                        if(toFilterStep != null) {
                            result.setToFilterStep(filterControl.getFilterStepTransfer(userVisit, toFilterStep));
                            filterStepDestinationTransfers = filterControl.getFilterStepDestinationTransfersByFromFilterStep(userVisit, toFilterStep);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownToFilterStepName.name(), toFilterStepName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidFilterStepSpecification.name());
                    }
                    
                    result.setFilterStepDestinations(filterStepDestinationTransfers);
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
