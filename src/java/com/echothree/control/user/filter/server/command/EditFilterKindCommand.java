// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.control.user.filter.common.edit.FilterKindEdit;
import com.echothree.control.user.filter.common.form.EditFilterKindForm;
import com.echothree.control.user.filter.common.result.EditFilterKindResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterKindSpec;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.FilterKind;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditFilterKindCommand
        extends BaseAbstractEditCommand<FilterKindSpec, FilterKindEdit, EditFilterKindResult, FilterKind, FilterKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterKind.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditFilterKindCommand */
    public EditFilterKindCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditFilterKindResult getResult() {
        return FilterResultFactory.getEditFilterKindResult();
    }

    @Override
    public FilterKindEdit getEdit() {
        return FilterEditFactory.getFilterKindEdit();
    }

    @Override
    public FilterKind getEntity(EditFilterKindResult result) {
        var filterControl = Session.getModelController(FilterControl.class);
        FilterKind filterKind;
        var filterKindName = spec.getFilterKindName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            filterKind = filterControl.getFilterKindByName(filterKindName);
        } else { // EditMode.UPDATE
            filterKind = filterControl.getFilterKindByNameForUpdate(filterKindName);
        }

        if(filterKind == null) {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }

        return filterKind;
    }

    @Override
    public FilterKind getLockEntity(FilterKind filterKind) {
        return filterKind;
    }

    @Override
    public void fillInResult(EditFilterKindResult result, FilterKind filterKind) {
        var filterControl = Session.getModelController(FilterControl.class);

        result.setFilterKind(filterControl.getFilterKindTransfer(getUserVisit(), filterKind));
    }

    @Override
    public void doLock(FilterKindEdit edit, FilterKind filterKind) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKindDescription = filterControl.getFilterKindDescription(filterKind, getPreferredLanguage());
        var filterKindDetail = filterKind.getLastDetail();

        edit.setFilterKindName(filterKindDetail.getFilterKindName());
        edit.setIsDefault(filterKindDetail.getIsDefault().toString());
        edit.setSortOrder(filterKindDetail.getSortOrder().toString());

        if(filterKindDescription != null) {
            edit.setDescription(filterKindDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(FilterKind filterKind) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKindName = edit.getFilterKindName();
        var duplicateFilterKind = filterControl.getFilterKindByName(filterKindName);

        if(duplicateFilterKind != null && !filterKind.equals(duplicateFilterKind)) {
            addExecutionError(ExecutionErrors.DuplicateFilterKindName.name(), filterKindName);
        }
    }

    @Override
    public void doUpdate(FilterKind filterKind) {
        var filterControl = Session.getModelController(FilterControl.class);
        var partyPK = getPartyPK();
        var filterKindDetailValue = filterControl.getFilterKindDetailValueForUpdate(filterKind);
        var filterKindDescription = filterControl.getFilterKindDescriptionForUpdate(filterKind, getPreferredLanguage());
        var description = edit.getDescription();

        filterKindDetailValue.setFilterKindName(edit.getFilterKindName());
        filterKindDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        filterKindDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        filterControl.updateFilterKindFromValue(filterKindDetailValue, partyPK);

        if(filterKindDescription == null && description != null) {
            filterControl.createFilterKindDescription(filterKind, getPreferredLanguage(), description, partyPK);
        } else if(filterKindDescription != null && description == null) {
            filterControl.deleteFilterKindDescription(filterKindDescription, partyPK);
        } else if(filterKindDescription != null && description != null) {
            var filterKindDescriptionValue = filterControl.getFilterKindDescriptionValue(filterKindDescription);

            filterKindDescriptionValue.setDescription(description);
            filterControl.updateFilterKindDescriptionFromValue(filterKindDescriptionValue, partyPK);
        }
    }

}
