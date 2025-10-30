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
import com.echothree.control.user.search.common.edit.SearchResultActionTypeDescriptionEdit;
import com.echothree.control.user.search.common.form.EditSearchResultActionTypeDescriptionForm;
import com.echothree.control.user.search.common.result.EditSearchResultActionTypeDescriptionResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchResultActionTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchResultActionType;
import com.echothree.model.data.search.server.entity.SearchResultActionTypeDescription;
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
public class EditSearchResultActionTypeDescriptionCommand
        extends BaseAbstractEditCommand<SearchResultActionTypeDescriptionSpec, SearchResultActionTypeDescriptionEdit, EditSearchResultActionTypeDescriptionResult, SearchResultActionTypeDescription, SearchResultActionType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchResultActionType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchResultActionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSearchResultActionTypeDescriptionCommand */
    public EditSearchResultActionTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditSearchResultActionTypeDescriptionResult getResult() {
        return SearchResultFactory.getEditSearchResultActionTypeDescriptionResult();
    }

    @Override
    public SearchResultActionTypeDescriptionEdit getEdit() {
        return SearchEditFactory.getSearchResultActionTypeDescriptionEdit();
    }

    @Override
    public SearchResultActionTypeDescription getEntity(EditSearchResultActionTypeDescriptionResult result) {
        var searchControl = Session.getModelController(SearchControl.class);
        SearchResultActionTypeDescription searchResultActionTypeDescription = null;
        var searchResultActionTypeName = spec.getSearchResultActionTypeName();
        var searchResultActionType = searchControl.getSearchResultActionTypeByName(searchResultActionTypeName);

        if(searchResultActionType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    searchResultActionTypeDescription = searchControl.getSearchResultActionTypeDescription(searchResultActionType, language);
                } else { // EditMode.UPDATE
                    searchResultActionTypeDescription = searchControl.getSearchResultActionTypeDescriptionForUpdate(searchResultActionType, language);
                }

                if(searchResultActionTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownSearchResultActionTypeDescription.name(), searchResultActionTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchResultActionTypeName.name(), searchResultActionTypeName);
        }

        return searchResultActionTypeDescription;
    }

    @Override
    public SearchResultActionType getLockEntity(SearchResultActionTypeDescription searchResultActionTypeDescription) {
        return searchResultActionTypeDescription.getSearchResultActionType();
    }

    @Override
    public void fillInResult(EditSearchResultActionTypeDescriptionResult result, SearchResultActionTypeDescription searchResultActionTypeDescription) {
        var searchControl = Session.getModelController(SearchControl.class);

        result.setSearchResultActionTypeDescription(searchControl.getSearchResultActionTypeDescriptionTransfer(getUserVisit(), searchResultActionTypeDescription));
    }

    @Override
    public void doLock(SearchResultActionTypeDescriptionEdit edit, SearchResultActionTypeDescription searchResultActionTypeDescription) {
        edit.setDescription(searchResultActionTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(SearchResultActionTypeDescription searchResultActionTypeDescription) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchResultActionTypeDescriptionValue = searchControl.getSearchResultActionTypeDescriptionValue(searchResultActionTypeDescription);
        searchResultActionTypeDescriptionValue.setDescription(edit.getDescription());

        searchControl.updateSearchResultActionTypeDescriptionFromValue(searchResultActionTypeDescriptionValue, getPartyPK());
    }
    
}
