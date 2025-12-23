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
import com.echothree.control.user.filter.common.edit.FilterTypeEdit;
import com.echothree.control.user.filter.common.form.EditFilterTypeForm;
import com.echothree.control.user.filter.common.result.EditFilterTypeResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterTypeSpec;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditFilterTypeCommand
        extends BaseAbstractEditCommand<FilterTypeSpec, FilterTypeEdit, EditFilterTypeResult, FilterType, FilterType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditFilterTypeCommand */
    public EditFilterTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditFilterTypeResult getResult() {
        return FilterResultFactory.getEditFilterTypeResult();
    }

    @Override
    public FilterTypeEdit getEdit() {
        return FilterEditFactory.getFilterTypeEdit();
    }

    FilterKind filterKind;

    @Override
    public FilterType getEntity(EditFilterTypeResult result) {
        var filterControl = Session.getModelController(FilterControl.class);
        FilterType filterType = null;
        var filterKindName = spec.getFilterKindName();

        filterKind = filterControl.getFilterKindByName(filterKindName);

        if(filterKind != null) {
            var filterTypeName = spec.getFilterTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);
            } else { // EditMode.UPDATE
                filterType = filterControl.getFilterTypeByNameForUpdate(filterKind, filterTypeName);
            }

            if(filterType == null) {
                addExecutionError(ExecutionErrors.UnknownFilterTypeName.name(), filterKindName, filterTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }

        return filterType;
    }

    @Override
    public FilterType getLockEntity(FilterType filterType) {
        return filterType;
    }

    @Override
    public void fillInResult(EditFilterTypeResult result, FilterType filterType) {
        var filterControl = Session.getModelController(FilterControl.class);

        result.setFilterType(filterControl.getFilterTypeTransfer(getUserVisit(), filterType));
    }

    @Override
    public void doLock(FilterTypeEdit edit, FilterType filterType) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterTypeDescription = filterControl.getFilterTypeDescription(filterType, getPreferredLanguage());
        var filterTypeDetail = filterType.getLastDetail();

        edit.setFilterTypeName(filterTypeDetail.getFilterTypeName());
        edit.setIsDefault(filterTypeDetail.getIsDefault().toString());
        edit.setSortOrder(filterTypeDetail.getSortOrder().toString());

        if(filterTypeDescription != null) {
            edit.setDescription(filterTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(FilterType filterType) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKindDetail = filterKind.getLastDetail();
        var filterTypeName = edit.getFilterTypeName();
        var duplicateFilterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);

        if(duplicateFilterType != null && !filterType.equals(duplicateFilterType)) {
            addExecutionError(ExecutionErrors.DuplicateFilterTypeName.name(), filterKindDetail.getFilterKindName(), filterTypeName);
        }
    }

    @Override
    public void doUpdate(FilterType filterType) {
        var filterControl = Session.getModelController(FilterControl.class);
        var partyPK = getPartyPK();
        var filterTypeDetailValue = filterControl.getFilterTypeDetailValueForUpdate(filterType);
        var filterTypeDescription = filterControl.getFilterTypeDescriptionForUpdate(filterType, getPreferredLanguage());
        var description = edit.getDescription();

        filterTypeDetailValue.setFilterTypeName(edit.getFilterTypeName());
        filterTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        filterTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        filterControl.updateFilterTypeFromValue(filterTypeDetailValue, partyPK);

        if(filterTypeDescription == null && description != null) {
            filterControl.createFilterTypeDescription(filterType, getPreferredLanguage(), description, partyPK);
        } else if(filterTypeDescription != null && description == null) {
            filterControl.deleteFilterTypeDescription(filterTypeDescription, partyPK);
        } else if(filterTypeDescription != null && description != null) {
            var filterTypeDescriptionValue = filterControl.getFilterTypeDescriptionValue(filterTypeDescription);

            filterTypeDescriptionValue.setDescription(description);
            filterControl.updateFilterTypeDescriptionFromValue(filterTypeDescriptionValue, partyPK);
        }
    }

}
