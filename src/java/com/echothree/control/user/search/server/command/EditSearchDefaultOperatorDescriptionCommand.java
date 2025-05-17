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

import com.echothree.control.user.search.common.edit.SearchDefaultOperatorDescriptionEdit;
import com.echothree.control.user.search.common.edit.SearchEditFactory;
import com.echothree.control.user.search.common.form.EditSearchDefaultOperatorDescriptionForm;
import com.echothree.control.user.search.common.result.EditSearchDefaultOperatorDescriptionResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchDefaultOperatorDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchDefaultOperatorDescription;
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

public class EditSearchDefaultOperatorDescriptionCommand
        extends BaseAbstractEditCommand<SearchDefaultOperatorDescriptionSpec, SearchDefaultOperatorDescriptionEdit, EditSearchDefaultOperatorDescriptionResult, SearchDefaultOperatorDescription, SearchDefaultOperator> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchDefaultOperator.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchDefaultOperatorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSearchDefaultOperatorDescriptionCommand */
    public EditSearchDefaultOperatorDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditSearchDefaultOperatorDescriptionResult getResult() {
        return SearchResultFactory.getEditSearchDefaultOperatorDescriptionResult();
    }

    @Override
    public SearchDefaultOperatorDescriptionEdit getEdit() {
        return SearchEditFactory.getSearchDefaultOperatorDescriptionEdit();
    }

    @Override
    public SearchDefaultOperatorDescription getEntity(EditSearchDefaultOperatorDescriptionResult result) {
        var searchControl = Session.getModelController(SearchControl.class);
        SearchDefaultOperatorDescription searchDefaultOperatorDescription = null;
        var searchDefaultOperatorName = spec.getSearchDefaultOperatorName();
        var searchDefaultOperator = searchControl.getSearchDefaultOperatorByName(searchDefaultOperatorName);

        if(searchDefaultOperator != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    searchDefaultOperatorDescription = searchControl.getSearchDefaultOperatorDescription(searchDefaultOperator, language);
                } else { // EditMode.UPDATE
                    searchDefaultOperatorDescription = searchControl.getSearchDefaultOperatorDescriptionForUpdate(searchDefaultOperator, language);
                }

                if(searchDefaultOperatorDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownSearchDefaultOperatorDescription.name(), searchDefaultOperatorName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchDefaultOperatorName.name(), searchDefaultOperatorName);
        }

        return searchDefaultOperatorDescription;
    }

    @Override
    public SearchDefaultOperator getLockEntity(SearchDefaultOperatorDescription searchDefaultOperatorDescription) {
        return searchDefaultOperatorDescription.getSearchDefaultOperator();
    }

    @Override
    public void fillInResult(EditSearchDefaultOperatorDescriptionResult result, SearchDefaultOperatorDescription searchDefaultOperatorDescription) {
        var searchControl = Session.getModelController(SearchControl.class);

        result.setSearchDefaultOperatorDescription(searchControl.getSearchDefaultOperatorDescriptionTransfer(getUserVisit(), searchDefaultOperatorDescription));
    }

    @Override
    public void doLock(SearchDefaultOperatorDescriptionEdit edit, SearchDefaultOperatorDescription searchDefaultOperatorDescription) {
        edit.setDescription(searchDefaultOperatorDescription.getDescription());
    }

    @Override
    public void doUpdate(SearchDefaultOperatorDescription searchDefaultOperatorDescription) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchDefaultOperatorDescriptionValue = searchControl.getSearchDefaultOperatorDescriptionValue(searchDefaultOperatorDescription);
        searchDefaultOperatorDescriptionValue.setDescription(edit.getDescription());

        searchControl.updateSearchDefaultOperatorDescriptionFromValue(searchDefaultOperatorDescriptionValue, getPartyPK());
    }
    
}
