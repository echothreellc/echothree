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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.edit.SearchEditFactory;
import com.echothree.control.user.search.common.edit.SearchKindEdit;
import com.echothree.control.user.search.common.form.EditSearchKindForm;
import com.echothree.control.user.search.common.result.EditSearchKindResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchKindSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchKind;
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
public class EditSearchKindCommand
        extends BaseAbstractEditCommand<SearchKindSpec, SearchKindEdit, EditSearchKindResult, SearchKind, SearchKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchKind.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchKindName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditSearchKindCommand */
    public EditSearchKindCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSearchKindResult getResult() {
        return SearchResultFactory.getEditSearchKindResult();
    }

    @Override
    public SearchKindEdit getEdit() {
        return SearchEditFactory.getSearchKindEdit();
    }

    @Override
    public SearchKind getEntity(EditSearchKindResult result) {
        var searchControl = Session.getModelController(SearchControl.class);
        SearchKind searchKind;
        var searchKindName = spec.getSearchKindName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            searchKind = searchControl.getSearchKindByName(searchKindName);
        } else { // EditMode.UPDATE
            searchKind = searchControl.getSearchKindByNameForUpdate(searchKindName);
        }

        if(searchKind == null) {
            addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), searchKindName);
        }

        return searchKind;
    }

    @Override
    public SearchKind getLockEntity(SearchKind searchKind) {
        return searchKind;
    }

    @Override
    public void fillInResult(EditSearchKindResult result, SearchKind searchKind) {
        var searchControl = Session.getModelController(SearchControl.class);

        result.setSearchKind(searchControl.getSearchKindTransfer(getUserVisit(), searchKind));
    }

    @Override
    public void doLock(SearchKindEdit edit, SearchKind searchKind) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchKindDescription = searchControl.getSearchKindDescription(searchKind, getPreferredLanguage());
        var searchKindDetail = searchKind.getLastDetail();

        edit.setSearchKindName(searchKindDetail.getSearchKindName());
        edit.setIsDefault(searchKindDetail.getIsDefault().toString());
        edit.setSortOrder(searchKindDetail.getSortOrder().toString());

        if(searchKindDescription != null) {
            edit.setDescription(searchKindDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(SearchKind searchKind) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchKindName = edit.getSearchKindName();
        var duplicateSearchKind = searchControl.getSearchKindByName(searchKindName);

        if(duplicateSearchKind != null && !searchKind.equals(duplicateSearchKind)) {
            addExecutionError(ExecutionErrors.DuplicateSearchKindName.name(), searchKindName);
        }
    }

    @Override
    public void doUpdate(SearchKind searchKind) {
        var searchControl = Session.getModelController(SearchControl.class);
        var partyPK = getPartyPK();
        var searchKindDetailValue = searchControl.getSearchKindDetailValueForUpdate(searchKind);
        var searchKindDescription = searchControl.getSearchKindDescriptionForUpdate(searchKind, getPreferredLanguage());
        var description = edit.getDescription();

        searchKindDetailValue.setSearchKindName(edit.getSearchKindName());
        searchKindDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        searchKindDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        searchControl.updateSearchKindFromValue(searchKindDetailValue, partyPK);

        if(searchKindDescription == null && description != null) {
            searchControl.createSearchKindDescription(searchKind, getPreferredLanguage(), description, partyPK);
        } else if(searchKindDescription != null && description == null) {
            searchControl.deleteSearchKindDescription(searchKindDescription, partyPK);
        } else if(searchKindDescription != null && description != null) {
            var searchKindDescriptionValue = searchControl.getSearchKindDescriptionValue(searchKindDescription);

            searchKindDescriptionValue.setDescription(description);
            searchControl.updateSearchKindDescriptionFromValue(searchKindDescriptionValue, partyPK);
        }
    }

}
