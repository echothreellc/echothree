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

import com.echothree.control.user.filter.common.form.CreateFilterStepElementForm;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
import javax.enterprise.context.Dependent;

@Dependent
public class CreateFilterStepElementCommand
        extends BaseSimpleCommand<CreateFilterStepElementForm> {

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
                new FieldDefinition("FilterStepElementName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of CreateFilterStepElementCommand */
    public CreateFilterStepElementCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKindName = form.getFilterKindName();
        var filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            var filterTypeName = form.getFilterTypeName();
            var filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);
            
            if(filterType != null) {
                var filterName = form.getFilterName();
                var filter = filterControl.getFilterByName(filterType, filterName);
                
                if(filter != null) {
                    var filterStepName = form.getFilterStepName();
                    var filterStep = filterControl.getFilterStepByName(filter, filterStepName);
                    
                    if(filterStep != null) {
                        var filterStepElementName = form.getFilterStepElementName();
                        var filterStepElement = filterControl.getFilterStepElementByName(filterStep, filterStepElementName);
                        
                        if(filterStepElement == null) {
                            var filterItemSelectorName = form.getFilterItemSelectorName();
                            Selector filterItemSelector = null;
                            
                            if(filterItemSelectorName != null) {
                                var selectorControl = Session.getModelController(SelectorControl.class);
                                var selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.ITEM.name());
                                
                                if(selectorKind != null) {
                                    var selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.FILTER.name());
                                    
                                    if(selectorType != null) {
                                        filterItemSelector = selectorControl.getSelectorByName(selectorType, filterItemSelectorName);
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorTypes.FILTER.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.ITEM.name());
                                }
                            }
                            
                            if(filterItemSelectorName == null || filterItemSelector != null) {
                                var filterAdjustmentName = form.getFilterAdjustmentName();
                                var filterAdjustment = filterAdjustmentName == null? null: filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);
                                
                                if(filterAdjustmentName == null || filterAdjustment != null) {
                                    var description = form.getDescription();
                                    var partyPK = getPartyPK();
                                    
                                    filterStepElement = filterControl.createFilterStepElement(filterStep, filterStepElementName,
                                            filterItemSelector, filterAdjustment, partyPK);
                                    
                                    if(description != null) {
                                        filterControl.createFilterStepElementDescription(filterStepElement, getPreferredLanguage(),
                                                description, partyPK);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownFilterAdjustmentName.name(), filterAdjustmentName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownFilterItemSelectorName.name(), filterItemSelectorName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateFilterStepElementName.name(), filterStepElementName);
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
        
        return null;
    }
    
}
