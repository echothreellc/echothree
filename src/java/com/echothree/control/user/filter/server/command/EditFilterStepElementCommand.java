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

import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.edit.FilterStepElementEdit;
import com.echothree.control.user.filter.common.result.EditFilterStepElementResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterStepElementSpec;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.filter.server.logic.FilterAdjustmentLogic;
import com.echothree.model.control.filter.server.logic.FilterStepElementLogic;
import com.echothree.model.control.filter.server.logic.FilterStepLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.logic.SelectorLogic;
import com.echothree.model.data.filter.server.entity.FilterStepElement;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditFilterStepElementCommand
        extends BaseAbstractEditCommand<FilterStepElementSpec, FilterStepElementEdit, EditFilterStepElementResult, FilterStepElement, FilterStepElement> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Filter.name(), SecurityRoles.FilterStepElement.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterStepElementName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterStepElementName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditFilterStepElementCommand */
    public EditFilterStepElementCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Inject
    FilterControl filterControl;

    @Inject
    FilterStepLogic filterStepLogic;

    @Inject
    FilterStepElementLogic filterStepElementLogic;

    @Inject
    FilterAdjustmentLogic filterAdjustmentLogic;

    @Inject
    SelectorLogic selectorLogic;

    @Override
    protected EditFilterStepElementResult getResult() {
        return FilterResultFactory.getEditFilterStepElementResult();
    }

    @Override
    protected FilterStepElementEdit getEdit() {
        return FilterEditFactory.getFilterStepElementEdit();
    }

    @Override
    protected FilterStepElement getEntity(EditFilterStepElementResult result) {
        var filterKindName = spec.getFilterKindName();
        var filterTypeName = spec.getFilterTypeName();
        var filterName = spec.getFilterName();
        var filterStepName = spec.getFilterStepName();
        var filterStep = filterStepLogic.getFilterStepByName(this, filterKindName, filterTypeName, filterName, filterStepName);
        FilterStepElement filterStepElement = null;

        if(!hasExecutionErrors()) {
            var filterStepElementName = spec.getFilterStepElementName();

            filterStepElement = filterStepElementLogic.getFilterStepElementByName(this, filterStep, filterStepElementName, editModeToEntityPermission(editMode));
        }

        return filterStepElement;
    }

    @Override
    protected FilterStepElement getLockEntity(FilterStepElement filterStepElement) {
        return filterStepElement;
    }

    @Override
    protected void fillInResult(EditFilterStepElementResult result, FilterStepElement filterStepElement) {
        result.setFilterStepElement(filterControl.getFilterStepElementTransfer(getUserVisit(), filterStepElement));
    }

    @Override
    protected void doLock(FilterStepElementEdit edit, FilterStepElement filterStepElement) {
        var filterStepElementDescription = filterControl.getFilterStepElementDescription(filterStepElement, getPreferredLanguage());
        var filterStepElementDetail = filterStepElement.getLastDetail();
        var filterItemSelector = filterStepElementDetail.getFilterItemSelector();

        edit.setFilterStepElementName(filterStepElementDetail.getFilterStepElementName());
        edit.setFilterItemSelectorName(filterItemSelector == null ? null : filterItemSelector.getLastDetail().getSelectorName());
        edit.setFilterAdjustmentName(filterStepElementDetail.getFilterAdjustment().getLastDetail().getFilterAdjustmentName());

        if(filterStepElementDescription != null) {
            edit.setDescription(filterStepElementDescription.getDescription());
        }
    }

    @Override
    protected void canUpdate(FilterStepElement filterStepElement) {
        var filterStep = filterStepElement.getLastDetail().getFilterStep();
        var filterStepElementName = edit.getFilterStepElementName();
        var duplicateFilterStepElement = filterControl.getFilterStepElementByName(filterStep, filterStepElementName);

        if(duplicateFilterStepElement != null && !filterStepElement.equals(duplicateFilterStepElement)) {
            addExecutionError(ExecutionErrors.DuplicateFilterStepElementName.name(), filterStepElementName);
        }
    }

    @Override
    protected void doUpdate(FilterStepElement filterStepElement) {
        var filterItemSelectorName = edit.getFilterItemSelectorName();
        var filterItemSelector = filterItemSelectorName == null ? null : selectorLogic.getSelectorByName(this, SelectorKinds.ITEM.name(), SelectorTypes.FILTER.name(), filterItemSelectorName);

        if(!hasExecutionErrors()) {
            var filterKind = filterStepElement.getLastDetail().getFilterStep().getLastDetail().getFilter().getLastDetail().getFilterType().getLastDetail().getFilterKind();
            var filterAdjustmentName = edit.getFilterAdjustmentName();
            var filterAdjustment = filterAdjustmentLogic.getFilterAdjustmentByName(this, filterKind, filterAdjustmentName);

            if(!hasExecutionErrors()) {
                var partyPK = getPartyPK();
                var filterStepElementDetailValue = filterControl.getFilterStepElementDetailValueForUpdate(filterStepElement);
                var filterStepElementDescription = filterControl.getFilterStepElementDescriptionForUpdate(filterStepElement, getPreferredLanguage());
                var description = edit.getDescription();

                filterStepElementDetailValue.setFilterStepElementName(edit.getFilterStepElementName());
                filterStepElementDetailValue.setFilterItemSelectorPK(filterItemSelector == null ? null : filterItemSelector.getPrimaryKey());
                filterStepElementDetailValue.setFilterAdjustmentPK(filterAdjustment.getPrimaryKey());

                filterControl.updateFilterStepElementFromValue(filterStepElementDetailValue, partyPK);

                if(filterStepElementDescription == null && description != null) {
                    filterControl.createFilterStepElementDescription(filterStepElement, getPreferredLanguage(), description, partyPK);
                } else if(filterStepElementDescription != null && description == null) {
                    filterControl.deleteFilterStepElementDescription(filterStepElementDescription, partyPK);
                } else if(filterStepElementDescription != null && description != null) {
                    var filterStepElementDescriptionValue = filterControl.getFilterStepElementDescriptionValue(filterStepElementDescription);

                    filterStepElementDescriptionValue.setDescription(description);
                    filterControl.updateFilterStepElementDescriptionFromValue(filterStepElementDescriptionValue, partyPK);
                }
            }
        }
    }

}
