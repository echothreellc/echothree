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
import com.echothree.control.user.search.common.edit.SearchResultActionTypeEdit;
import com.echothree.control.user.search.common.form.EditSearchResultActionTypeForm;
import com.echothree.control.user.search.common.result.EditSearchResultActionTypeResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchResultActionTypeUniversalSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchResultActionTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchResultActionType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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
public class EditSearchResultActionTypeCommand
        extends BaseAbstractEditCommand<SearchResultActionTypeUniversalSpec, SearchResultActionTypeEdit, EditSearchResultActionTypeResult, SearchResultActionType, SearchResultActionType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchResultActionType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchResultActionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchResultActionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSearchResultActionTypeCommand */
    public EditSearchResultActionTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSearchResultActionTypeResult getResult() {
        return SearchResultFactory.getEditSearchResultActionTypeResult();
    }

    @Override
    public SearchResultActionTypeEdit getEdit() {
        return SearchEditFactory.getSearchResultActionTypeEdit();
    }

    @Override
    public SearchResultActionType getEntity(EditSearchResultActionTypeResult result) {
        return SearchResultActionTypeLogic.getInstance().getSearchResultActionTypeByUniversalSpec(this,
                spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public SearchResultActionType getLockEntity(SearchResultActionType searchResultActionType) {
        return searchResultActionType;
    }

    @Override
    public void fillInResult(EditSearchResultActionTypeResult result, SearchResultActionType searchResultActionType) {
        final var searchControl = Session.getModelController(SearchControl.class);

        result.setSearchResultActionType(searchControl.getSearchResultActionTypeTransfer(getUserVisit(), searchResultActionType));
    }

    @Override
    public void doLock(SearchResultActionTypeEdit edit, SearchResultActionType searchResultActionType) {
        final var searchControl = Session.getModelController(SearchControl.class);
        final var searchResultActionTypeDescription = searchControl.getSearchResultActionTypeDescription(searchResultActionType, getPreferredLanguage());
        final var searchResultActionTypeDetail = searchResultActionType.getLastDetail();

        edit.setSearchResultActionTypeName(searchResultActionTypeDetail.getSearchResultActionTypeName());
        edit.setIsDefault(searchResultActionTypeDetail.getIsDefault().toString());
        edit.setSortOrder(searchResultActionTypeDetail.getSortOrder().toString());

        if(searchResultActionTypeDescription != null) {
            edit.setDescription(searchResultActionTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(SearchResultActionType searchResultActionType) {
        final var searchControl = Session.getModelController(SearchControl.class);
        final var searchResultActionTypeName = edit.getSearchResultActionTypeName();
        final var duplicateSearchResultActionType = searchControl.getSearchResultActionTypeByName(searchResultActionTypeName);

        if(duplicateSearchResultActionType != null && !searchResultActionType.equals(duplicateSearchResultActionType)) {
            addExecutionError(ExecutionErrors.DuplicateSearchResultActionTypeName.name(), searchResultActionTypeName);
        }
    }

    @Override
    public void doUpdate(SearchResultActionType searchResultActionType) {
        final var searchControl = Session.getModelController(SearchControl.class);
        final var partyPK = getPartyPK();
        final var searchResultActionTypeDetailValue = searchControl.getSearchResultActionTypeDetailValueForUpdate(searchResultActionType);
        final var searchResultActionTypeDescription = searchControl.getSearchResultActionTypeDescriptionForUpdate(searchResultActionType, getPreferredLanguage());
        final var description = edit.getDescription();

        searchResultActionTypeDetailValue.setSearchResultActionTypeName(edit.getSearchResultActionTypeName());
        searchResultActionTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        searchResultActionTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        SearchResultActionTypeLogic.getInstance().updateSearchResultActionTypeFromValue(searchResultActionTypeDetailValue, partyPK);

        if(searchResultActionTypeDescription == null && description != null) {
            searchControl.createSearchResultActionTypeDescription(searchResultActionType, getPreferredLanguage(), description, partyPK);
        } else if(searchResultActionTypeDescription != null && description == null) {
            searchControl.deleteSearchResultActionTypeDescription(searchResultActionTypeDescription, partyPK);
        } else if(searchResultActionTypeDescription != null && description != null) {
            var searchResultActionTypeDescriptionValue = searchControl.getSearchResultActionTypeDescriptionValue(searchResultActionTypeDescription);

            searchResultActionTypeDescriptionValue.setDescription(description);
            searchControl.updateSearchResultActionTypeDescriptionFromValue(searchResultActionTypeDescriptionValue, partyPK);
        }
    }
    
}
