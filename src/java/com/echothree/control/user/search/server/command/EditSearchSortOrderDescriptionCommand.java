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
import com.echothree.control.user.search.common.edit.SearchSortOrderDescriptionEdit;
import com.echothree.control.user.search.common.form.EditSearchSortOrderDescriptionForm;
import com.echothree.control.user.search.common.result.EditSearchSortOrderDescriptionResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchSortOrderDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchSortOrderDescription;
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

public class EditSearchSortOrderDescriptionCommand
        extends BaseAbstractEditCommand<SearchSortOrderDescriptionSpec, SearchSortOrderDescriptionEdit, EditSearchSortOrderDescriptionResult, SearchSortOrderDescription, SearchSortOrder> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchSortOrder.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchSortOrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditSearchSortOrderDescriptionCommand */
    public EditSearchSortOrderDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSearchSortOrderDescriptionResult getResult() {
        return SearchResultFactory.getEditSearchSortOrderDescriptionResult();
    }

    @Override
    public SearchSortOrderDescriptionEdit getEdit() {
        return SearchEditFactory.getSearchSortOrderDescriptionEdit();
    }

    @Override
    public SearchSortOrderDescription getEntity(EditSearchSortOrderDescriptionResult result) {
        var searchControl = Session.getModelController(SearchControl.class);
        SearchSortOrderDescription searchSortOrderDescription = null;
        var searchKindName = spec.getSearchKindName();
        var searchKind = searchControl.getSearchKindByName(searchKindName);

        if(searchKind != null) {
            var searchSortOrderName = spec.getSearchSortOrderName();
            var searchSortOrder = searchControl.getSearchSortOrderByName(searchKind, searchSortOrderName);

            if(searchSortOrder != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        searchSortOrderDescription = searchControl.getSearchSortOrderDescription(searchSortOrder, language);
                    } else { // EditMode.UPDATE
                        searchSortOrderDescription = searchControl.getSearchSortOrderDescriptionForUpdate(searchSortOrder, language);
                    }

                    if(searchSortOrderDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownSearchSortOrderDescription.name(), searchKindName, searchSortOrderName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSearchSortOrderName.name(), searchKindName, searchSortOrderName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), searchKindName);
        }

        return searchSortOrderDescription;
    }

    @Override
    public SearchSortOrder getLockEntity(SearchSortOrderDescription searchSortOrderDescription) {
        return searchSortOrderDescription.getSearchSortOrder();
    }

    @Override
    public void fillInResult(EditSearchSortOrderDescriptionResult result, SearchSortOrderDescription searchSortOrderDescription) {
        var searchControl = Session.getModelController(SearchControl.class);

        result.setSearchSortOrderDescription(searchControl.getSearchSortOrderDescriptionTransfer(getUserVisit(), searchSortOrderDescription));
    }

    @Override
    public void doLock(SearchSortOrderDescriptionEdit edit, SearchSortOrderDescription searchSortOrderDescription) {
        edit.setDescription(searchSortOrderDescription.getDescription());
    }

    @Override
    public void doUpdate(SearchSortOrderDescription searchSortOrderDescription) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchSortOrderDescriptionValue = searchControl.getSearchSortOrderDescriptionValue(searchSortOrderDescription);

        searchSortOrderDescriptionValue.setDescription(edit.getDescription());

        searchControl.updateSearchSortOrderDescriptionFromValue(searchSortOrderDescriptionValue, getPartyPK());
    }

}
