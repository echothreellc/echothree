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
import com.echothree.control.user.search.common.edit.SearchSortDirectionEdit;
import com.echothree.control.user.search.common.form.EditSearchSortDirectionForm;
import com.echothree.control.user.search.common.result.EditSearchSortDirectionResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchSortDirectionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
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
public class EditSearchSortDirectionCommand
        extends BaseAbstractEditCommand<SearchSortDirectionSpec, SearchSortDirectionEdit, EditSearchSortDirectionResult, SearchSortDirection, SearchSortDirection> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchSortDirection.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchSortDirectionName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchSortDirectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSearchSortDirectionCommand */
    public EditSearchSortDirectionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditSearchSortDirectionResult getResult() {
        return SearchResultFactory.getEditSearchSortDirectionResult();
    }

    @Override
    public SearchSortDirectionEdit getEdit() {
        return SearchEditFactory.getSearchSortDirectionEdit();
    }

    @Override
    public SearchSortDirection getEntity(EditSearchSortDirectionResult result) {
        var searchControl = Session.getModelController(SearchControl.class);
        SearchSortDirection searchSortDirection;
        var searchSortDirectionName = spec.getSearchSortDirectionName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            searchSortDirection = searchControl.getSearchSortDirectionByName(searchSortDirectionName);
        } else { // EditMode.UPDATE
            searchSortDirection = searchControl.getSearchSortDirectionByNameForUpdate(searchSortDirectionName);
        }

        if(searchSortDirection == null) {
            addExecutionError(ExecutionErrors.UnknownSearchSortDirectionName.name(), searchSortDirectionName);
        }

        return searchSortDirection;
    }

    @Override
    public SearchSortDirection getLockEntity(SearchSortDirection searchSortDirection) {
        return searchSortDirection;
    }

    @Override
    public void fillInResult(EditSearchSortDirectionResult result, SearchSortDirection searchSortDirection) {
        var searchControl = Session.getModelController(SearchControl.class);

        result.setSearchSortDirection(searchControl.getSearchSortDirectionTransfer(getUserVisit(), searchSortDirection));
    }

    @Override
    public void doLock(SearchSortDirectionEdit edit, SearchSortDirection searchSortDirection) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchSortDirectionDescription = searchControl.getSearchSortDirectionDescription(searchSortDirection, getPreferredLanguage());
        var searchSortDirectionDetail = searchSortDirection.getLastDetail();

        edit.setSearchSortDirectionName(searchSortDirectionDetail.getSearchSortDirectionName());
        edit.setIsDefault(searchSortDirectionDetail.getIsDefault().toString());
        edit.setSortOrder(searchSortDirectionDetail.getSortOrder().toString());

        if(searchSortDirectionDescription != null) {
            edit.setDescription(searchSortDirectionDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(SearchSortDirection searchSortDirection) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchSortDirectionName = edit.getSearchSortDirectionName();
        var duplicateSearchSortDirection = searchControl.getSearchSortDirectionByName(searchSortDirectionName);

        if(duplicateSearchSortDirection != null && !searchSortDirection.equals(duplicateSearchSortDirection)) {
            addExecutionError(ExecutionErrors.DuplicateSearchSortDirectionName.name(), searchSortDirectionName);
        }
    }

    @Override
    public void doUpdate(SearchSortDirection searchSortDirection) {
        var searchControl = Session.getModelController(SearchControl.class);
        var partyPK = getPartyPK();
        var searchSortDirectionDetailValue = searchControl.getSearchSortDirectionDetailValueForUpdate(searchSortDirection);
        var searchSortDirectionDescription = searchControl.getSearchSortDirectionDescriptionForUpdate(searchSortDirection, getPreferredLanguage());
        var description = edit.getDescription();

        searchSortDirectionDetailValue.setSearchSortDirectionName(edit.getSearchSortDirectionName());
        searchSortDirectionDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        searchSortDirectionDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        searchControl.updateSearchSortDirectionFromValue(searchSortDirectionDetailValue, partyPK);

        if(searchSortDirectionDescription == null && description != null) {
            searchControl.createSearchSortDirectionDescription(searchSortDirection, getPreferredLanguage(), description, partyPK);
        } else {
            if(searchSortDirectionDescription != null && description == null) {
                searchControl.deleteSearchSortDirectionDescription(searchSortDirectionDescription, partyPK);
            } else {
                if(searchSortDirectionDescription != null && description != null) {
                    var searchSortDirectionDescriptionValue = searchControl.getSearchSortDirectionDescriptionValue(searchSortDirectionDescription);

                    searchSortDirectionDescriptionValue.setDescription(description);
                    searchControl.updateSearchSortDirectionDescriptionFromValue(searchSortDirectionDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
