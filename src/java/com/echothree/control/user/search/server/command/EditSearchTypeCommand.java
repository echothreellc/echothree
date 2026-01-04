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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.edit.SearchEditFactory;
import com.echothree.control.user.search.common.edit.SearchTypeEdit;
import com.echothree.control.user.search.common.form.EditSearchTypeForm;
import com.echothree.control.user.search.common.result.EditSearchTypeResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchType;
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
public class EditSearchTypeCommand
        extends BaseAbstractEditCommand<SearchTypeSpec, SearchTypeEdit, EditSearchTypeResult, SearchType, SearchType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditSearchTypeCommand */
    public EditSearchTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSearchTypeResult getResult() {
        return SearchResultFactory.getEditSearchTypeResult();
    }

    @Override
    public SearchTypeEdit getEdit() {
        return SearchEditFactory.getSearchTypeEdit();
    }

    SearchKind searchKind;

    @Override
    public SearchType getEntity(EditSearchTypeResult result) {
        var searchControl = Session.getModelController(SearchControl.class);
        SearchType searchType = null;
        var searchKindName = spec.getSearchKindName();

        searchKind = searchControl.getSearchKindByName(searchKindName);

        if(searchKind != null) {
            var searchTypeName = spec.getSearchTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                searchType = searchControl.getSearchTypeByName(searchKind, searchTypeName);
            } else { // EditMode.UPDATE
                searchType = searchControl.getSearchTypeByNameForUpdate(searchKind, searchTypeName);
            }

            if(searchType == null) {
                addExecutionError(ExecutionErrors.UnknownSearchTypeName.name(), searchKindName, searchTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), searchKindName);
        }

        return searchType;
    }

    @Override
    public SearchType getLockEntity(SearchType searchType) {
        return searchType;
    }

    @Override
    public void fillInResult(EditSearchTypeResult result, SearchType searchType) {
        var searchControl = Session.getModelController(SearchControl.class);

        result.setSearchType(searchControl.getSearchTypeTransfer(getUserVisit(), searchType));
    }

    @Override
    public void doLock(SearchTypeEdit edit, SearchType searchType) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchTypeDescription = searchControl.getSearchTypeDescription(searchType, getPreferredLanguage());
        var searchTypeDetail = searchType.getLastDetail();

        edit.setSearchTypeName(searchTypeDetail.getSearchTypeName());
        edit.setIsDefault(searchTypeDetail.getIsDefault().toString());
        edit.setSortOrder(searchTypeDetail.getSortOrder().toString());

        if(searchTypeDescription != null) {
            edit.setDescription(searchTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(SearchType searchType) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchKindDetail = searchKind.getLastDetail();
        var searchTypeName = edit.getSearchTypeName();
        var duplicateSearchType = searchControl.getSearchTypeByName(searchKind, searchTypeName);

        if(duplicateSearchType != null && !searchType.equals(duplicateSearchType)) {
            addExecutionError(ExecutionErrors.DuplicateSearchTypeName.name(), searchKindDetail.getSearchKindName(), searchTypeName);
        }
    }

    @Override
    public void doUpdate(SearchType searchType) {
        var searchControl = Session.getModelController(SearchControl.class);
        var partyPK = getPartyPK();
        var searchTypeDetailValue = searchControl.getSearchTypeDetailValueForUpdate(searchType);
        var searchTypeDescription = searchControl.getSearchTypeDescriptionForUpdate(searchType, getPreferredLanguage());
        var description = edit.getDescription();

        searchTypeDetailValue.setSearchTypeName(edit.getSearchTypeName());
        searchTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        searchTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        searchControl.updateSearchTypeFromValue(searchTypeDetailValue, partyPK);

        if(searchTypeDescription == null && description != null) {
            searchControl.createSearchTypeDescription(searchType, getPreferredLanguage(), description, partyPK);
        } else if(searchTypeDescription != null && description == null) {
            searchControl.deleteSearchTypeDescription(searchTypeDescription, partyPK);
        } else if(searchTypeDescription != null && description != null) {
            var searchTypeDescriptionValue = searchControl.getSearchTypeDescriptionValue(searchTypeDescription);

            searchTypeDescriptionValue.setDescription(description);
            searchControl.updateSearchTypeDescriptionFromValue(searchTypeDescriptionValue, partyPK);
        }
    }

}
