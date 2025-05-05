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

import com.echothree.control.user.search.common.edit.SearchCheckSpellingActionTypeDescriptionEdit;
import com.echothree.control.user.search.common.edit.SearchEditFactory;
import com.echothree.control.user.search.common.form.EditSearchCheckSpellingActionTypeDescriptionForm;
import com.echothree.control.user.search.common.result.EditSearchCheckSpellingActionTypeDescriptionResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchCheckSpellingActionTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionType;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionTypeDescription;
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

public class EditSearchCheckSpellingActionTypeDescriptionCommand
        extends BaseAbstractEditCommand<SearchCheckSpellingActionTypeDescriptionSpec, SearchCheckSpellingActionTypeDescriptionEdit, EditSearchCheckSpellingActionTypeDescriptionResult, SearchCheckSpellingActionTypeDescription, SearchCheckSpellingActionType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchCheckSpellingActionType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchCheckSpellingActionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSearchCheckSpellingActionTypeDescriptionCommand */
    public EditSearchCheckSpellingActionTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditSearchCheckSpellingActionTypeDescriptionResult getResult() {
        return SearchResultFactory.getEditSearchCheckSpellingActionTypeDescriptionResult();
    }

    @Override
    public SearchCheckSpellingActionTypeDescriptionEdit getEdit() {
        return SearchEditFactory.getSearchCheckSpellingActionTypeDescriptionEdit();
    }

    @Override
    public SearchCheckSpellingActionTypeDescription getEntity(EditSearchCheckSpellingActionTypeDescriptionResult result) {
        var searchControl = Session.getModelController(SearchControl.class);
        SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription = null;
        var searchCheckSpellingActionTypeName = spec.getSearchCheckSpellingActionTypeName();
        var searchCheckSpellingActionType = searchControl.getSearchCheckSpellingActionTypeByName(searchCheckSpellingActionTypeName);

        if(searchCheckSpellingActionType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    searchCheckSpellingActionTypeDescription = searchControl.getSearchCheckSpellingActionTypeDescription(searchCheckSpellingActionType, language);
                } else { // EditMode.UPDATE
                    searchCheckSpellingActionTypeDescription = searchControl.getSearchCheckSpellingActionTypeDescriptionForUpdate(searchCheckSpellingActionType, language);
                }

                if(searchCheckSpellingActionTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownSearchCheckSpellingActionTypeDescription.name(), searchCheckSpellingActionTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchCheckSpellingActionTypeName.name(), searchCheckSpellingActionTypeName);
        }

        return searchCheckSpellingActionTypeDescription;
    }

    @Override
    public SearchCheckSpellingActionType getLockEntity(SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription) {
        return searchCheckSpellingActionTypeDescription.getSearchCheckSpellingActionType();
    }

    @Override
    public void fillInResult(EditSearchCheckSpellingActionTypeDescriptionResult result, SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription) {
        var searchControl = Session.getModelController(SearchControl.class);

        result.setSearchCheckSpellingActionTypeDescription(searchControl.getSearchCheckSpellingActionTypeDescriptionTransfer(getUserVisit(), searchCheckSpellingActionTypeDescription));
    }

    @Override
    public void doLock(SearchCheckSpellingActionTypeDescriptionEdit edit, SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription) {
        edit.setDescription(searchCheckSpellingActionTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchCheckSpellingActionTypeDescriptionValue = searchControl.getSearchCheckSpellingActionTypeDescriptionValue(searchCheckSpellingActionTypeDescription);
        searchCheckSpellingActionTypeDescriptionValue.setDescription(edit.getDescription());

        searchControl.updateSearchCheckSpellingActionTypeDescriptionFromValue(searchCheckSpellingActionTypeDescriptionValue, getPartyPK());
    }
    
}
