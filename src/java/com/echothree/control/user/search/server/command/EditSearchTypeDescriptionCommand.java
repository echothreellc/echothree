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
import com.echothree.control.user.search.common.edit.SearchTypeDescriptionEdit;
import com.echothree.control.user.search.common.form.EditSearchTypeDescriptionForm;
import com.echothree.control.user.search.common.result.EditSearchTypeDescriptionResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.SearchTypeDescription;
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

public class EditSearchTypeDescriptionCommand
        extends BaseAbstractEditCommand<SearchTypeDescriptionSpec, SearchTypeDescriptionEdit, EditSearchTypeDescriptionResult, SearchTypeDescription, SearchType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditSearchTypeDescriptionCommand */
    public EditSearchTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSearchTypeDescriptionResult getResult() {
        return SearchResultFactory.getEditSearchTypeDescriptionResult();
    }

    @Override
    public SearchTypeDescriptionEdit getEdit() {
        return SearchEditFactory.getSearchTypeDescriptionEdit();
    }

    @Override
    public SearchTypeDescription getEntity(EditSearchTypeDescriptionResult result) {
        var searchControl = Session.getModelController(SearchControl.class);
        SearchTypeDescription searchTypeDescription = null;
        var searchKindName = spec.getSearchKindName();
        var searchKind = searchControl.getSearchKindByName(searchKindName);

        if(searchKind != null) {
            var searchTypeName = spec.getSearchTypeName();
            var searchType = searchControl.getSearchTypeByName(searchKind, searchTypeName);

            if(searchType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        searchTypeDescription = searchControl.getSearchTypeDescription(searchType, language);
                    } else { // EditMode.UPDATE
                        searchTypeDescription = searchControl.getSearchTypeDescriptionForUpdate(searchType, language);
                    }

                    if(searchTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownSearchTypeDescription.name(), searchKindName, searchTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSearchTypeName.name(), searchKindName, searchTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), searchKindName);
        }

        return searchTypeDescription;
    }

    @Override
    public SearchType getLockEntity(SearchTypeDescription searchTypeDescription) {
        return searchTypeDescription.getSearchType();
    }

    @Override
    public void fillInResult(EditSearchTypeDescriptionResult result, SearchTypeDescription searchTypeDescription) {
        var searchControl = Session.getModelController(SearchControl.class);

        result.setSearchTypeDescription(searchControl.getSearchTypeDescriptionTransfer(getUserVisit(), searchTypeDescription));
    }

    @Override
    public void doLock(SearchTypeDescriptionEdit edit, SearchTypeDescription searchTypeDescription) {
        edit.setDescription(searchTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(SearchTypeDescription searchTypeDescription) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchTypeDescriptionValue = searchControl.getSearchTypeDescriptionValue(searchTypeDescription);

        searchTypeDescriptionValue.setDescription(edit.getDescription());

        searchControl.updateSearchTypeDescriptionFromValue(searchTypeDescriptionValue, getPartyPK());
    }

}
