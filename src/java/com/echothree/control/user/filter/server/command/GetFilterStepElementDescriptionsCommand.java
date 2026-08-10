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

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.form.GetFilterStepElementDescriptionsForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.filter.server.control.FilterKindControl;
import com.echothree.model.control.filter.server.control.FilterTypeControl;
import com.echothree.model.control.filter.server.control.FilterStepControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
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

    @Inject
    FilterControl filterControl;

    @Inject
    FilterKindControl filterKindControl;

    @Inject
    FilterTypeControl filterTypeControl;

    @Inject
    FilterStepControl filterStepControl;

    
    /** Creates a new instance of GetFilterStepElementDescriptionsCommand */
    public GetFilterStepElementDescriptionsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = FilterResultFactory.getGetFilterStepElementDescriptionsResult();
        var filterKindName = form.getFilterKindName();
        var filterKind = filterKindControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            var userVisit = getUserVisit();
            var filterTypeName = form.getFilterTypeName();
            var filterType = filterTypeControl.getFilterTypeByName(filterKind, filterTypeName);
            
            result.setFilterKind(filterKindControl.getFilterKindTransfer(userVisit, filterKind));
            
            if(filterType != null) {
                var filterName = form.getFilterName();
                var filter = filterControl.getFilterByName(filterType, filterName);
                
                result.setFilterType(filterTypeControl.getFilterTypeTransfer(userVisit, filterType));
                
                if(filter != null) {
                    var filterStepName = form.getFilterStepName();
                    var filterStep = filterStepControl.getFilterStepByName(filter, filterStepName);
                    
                    result.setFilter(filterControl.getFilterTransfer(userVisit, filter));
                    
                    if(filterStep != null) {
                        var filterStepElementName = form.getFilterStepElementName();
                        var filterStepElement = filterStepControl.getFilterStepElementByName(filterStep, filterStepElementName);
                        
                        result.setFilterStep(filterStepControl.getFilterStepTransfer(userVisit, filterStep));
                        
                        if(filterStepElement != null) {
                            result.setFilterStepElement(filterStepControl.getFilterStepElementTransfer(userVisit, filterStepElement));
                            result.setFilterStepElementDescriptions(filterStepControl.getFilterStepElementDescriptionTransfers(userVisit, filterStepElement));
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
