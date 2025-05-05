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
import com.echothree.control.user.search.common.edit.SearchSortDirectionDescriptionEdit;
import com.echothree.control.user.search.common.form.EditSearchSortDirectionDescriptionForm;
import com.echothree.control.user.search.common.result.EditSearchSortDirectionDescriptionResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchSortDirectionDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortDirectionDescription;
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

public class EditSearchSortDirectionDescriptionCommand
        extends BaseAbstractEditCommand<SearchSortDirectionDescriptionSpec, SearchSortDirectionDescriptionEdit, EditSearchSortDirectionDescriptionResult, SearchSortDirectionDescription, SearchSortDirection> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchSortDirection.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchSortDirectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSearchSortDirectionDescriptionCommand */
    public EditSearchSortDirectionDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditSearchSortDirectionDescriptionResult getResult() {
        return SearchResultFactory.getEditSearchSortDirectionDescriptionResult();
    }

    @Override
    public SearchSortDirectionDescriptionEdit getEdit() {
        return SearchEditFactory.getSearchSortDirectionDescriptionEdit();
    }

    @Override
    public SearchSortDirectionDescription getEntity(EditSearchSortDirectionDescriptionResult result) {
        var searchControl = Session.getModelController(SearchControl.class);
        SearchSortDirectionDescription searchSortDirectionDescription = null;
        var searchSortDirectionName = spec.getSearchSortDirectionName();
        var searchSortDirection = searchControl.getSearchSortDirectionByName(searchSortDirectionName);

        if(searchSortDirection != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    searchSortDirectionDescription = searchControl.getSearchSortDirectionDescription(searchSortDirection, language);
                } else { // EditMode.UPDATE
                    searchSortDirectionDescription = searchControl.getSearchSortDirectionDescriptionForUpdate(searchSortDirection, language);
                }

                if(searchSortDirectionDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownSearchSortDirectionDescription.name(), searchSortDirectionName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchSortDirectionName.name(), searchSortDirectionName);
        }

        return searchSortDirectionDescription;
    }

    @Override
    public SearchSortDirection getLockEntity(SearchSortDirectionDescription searchSortDirectionDescription) {
        return searchSortDirectionDescription.getSearchSortDirection();
    }

    @Override
    public void fillInResult(EditSearchSortDirectionDescriptionResult result, SearchSortDirectionDescription searchSortDirectionDescription) {
        var searchControl = Session.getModelController(SearchControl.class);

        result.setSearchSortDirectionDescription(searchControl.getSearchSortDirectionDescriptionTransfer(getUserVisit(), searchSortDirectionDescription));
    }

    @Override
    public void doLock(SearchSortDirectionDescriptionEdit edit, SearchSortDirectionDescription searchSortDirectionDescription) {
        edit.setDescription(searchSortDirectionDescription.getDescription());
    }

    @Override
    public void doUpdate(SearchSortDirectionDescription searchSortDirectionDescription) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchSortDirectionDescriptionValue = searchControl.getSearchSortDirectionDescriptionValue(searchSortDirectionDescription);
        searchSortDirectionDescriptionValue.setDescription(edit.getDescription());

        searchControl.updateSearchSortDirectionDescriptionFromValue(searchSortDirectionDescriptionValue, getPartyPK());
    }
    
}
