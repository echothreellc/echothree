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

import com.echothree.control.user.filter.common.edit.FilterAdjustmentEdit;
import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterAdjustmentSpec;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.util.common.command.EditMode;
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
public class EditFilterAdjustmentCommand
        extends BaseAbstractEditCommand<FilterAdjustmentSpec, FilterAdjustmentEdit, EditFilterAdjustmentResult, FilterAdjustment, FilterAdjustment> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterAdjustment.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterAdjustmentSourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditFilterAdjustmentCommand */
    public EditFilterAdjustmentCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Inject
    FilterControl filterControl;

    @Override
    public EditFilterAdjustmentResult getResult() {
        return FilterResultFactory.getEditFilterAdjustmentResult();
    }

    @Override
    public FilterAdjustmentEdit getEdit() {
        return FilterEditFactory.getFilterAdjustmentEdit();
    }

    FilterKind filterKind;

    @Override
    public FilterAdjustment getEntity(EditFilterAdjustmentResult result) {
        FilterAdjustment filterAdjustment = null;
        var filterKindName = spec.getFilterKindName();

        filterKind = filterControl.getFilterKindByName(filterKindName);

        if(filterKind != null) {
            var filterAdjustmentName = spec.getFilterAdjustmentName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);
            } else { // EditMode.UPDATE
                filterAdjustment = filterControl.getFilterAdjustmentByNameForUpdate(filterKind, filterAdjustmentName);
            }

            if(filterAdjustment == null) {
                addExecutionError(ExecutionErrors.UnknownFilterAdjustmentName.name(),
                        filterKind.getLastDetail().getFilterKindName(), filterAdjustmentName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }

        return filterAdjustment;
    }

    @Override
    public FilterAdjustment getLockEntity(FilterAdjustment filterAdjustment) {
        return filterAdjustment;
    }

    @Override
    public void fillInResult(EditFilterAdjustmentResult result, FilterAdjustment filterAdjustment) {
        result.setFilterAdjustment(filterControl.getFilterAdjustmentTransfer(getUserVisit(), filterAdjustment));
    }

    @Override
    public void doLock(FilterAdjustmentEdit edit, FilterAdjustment filterAdjustment) {
        var filterAdjustmentDescription = filterControl.getFilterAdjustmentDescription(filterAdjustment, getPreferredLanguage());
        var filterAdjustmentDetail = filterAdjustment.getLastDetail();

        edit.setFilterAdjustmentName(filterAdjustmentDetail.getFilterAdjustmentName());
        edit.setFilterAdjustmentSourceName(filterAdjustmentDetail.getFilterAdjustmentSource().getFilterAdjustmentSourceName());
        edit.setIsDefault(filterAdjustmentDetail.getIsDefault().toString());
        edit.setSortOrder(filterAdjustmentDetail.getSortOrder().toString());

        if(filterAdjustmentDescription != null) {
            edit.setDescription(filterAdjustmentDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(FilterAdjustment filterAdjustment) {
        var filterAdjustmentName = edit.getFilterAdjustmentName();
        var duplicateFilterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);

        if(duplicateFilterAdjustment != null && !filterAdjustment.equals(duplicateFilterAdjustment)) {
            addExecutionError(ExecutionErrors.DuplicateFilterAdjustmentName.name(), filterAdjustmentName);
        } else {
            var filterAdjustmentSourceName = edit.getFilterAdjustmentSourceName();
            var filterAdjustmentSource = filterControl.getFilterAdjustmentSourceByName(filterAdjustmentSourceName);

            if(filterAdjustmentSource == null) {
                addExecutionError(ExecutionErrors.UnknownFilterAdjustmentSourceName.name(), filterAdjustmentSourceName);
            }
        }
    }

    @Override
    public void doUpdate(FilterAdjustment filterAdjustment) {
        var partyPK = getPartyPK();
        var filterAdjustmentDetailValue = filterControl.getFilterAdjustmentDetailValueForUpdate(filterAdjustment);
        var filterAdjustmentDescription = filterControl.getFilterAdjustmentDescriptionForUpdate(filterAdjustment, getPreferredLanguage());
        var filterAdjustmentSourceName = edit.getFilterAdjustmentSourceName();
        var filterAdjustmentSource = filterControl.getFilterAdjustmentSourceByName(filterAdjustmentSourceName);
        var description = edit.getDescription();

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
            var filterAdjustmentDescriptionValue = filterControl.getFilterAdjustmentDescriptionValue(filterAdjustmentDescription);

            filterAdjustmentDescriptionValue.setDescription(description);
            filterControl.updateFilterAdjustmentDescriptionFromValue(filterAdjustmentDescriptionValue, partyPK);
        }
    }

}
