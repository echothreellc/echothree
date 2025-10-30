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

import com.echothree.control.user.search.common.edit.SearchDefaultOperatorEdit;
import com.echothree.control.user.search.common.edit.SearchEditFactory;
import com.echothree.control.user.search.common.form.EditSearchDefaultOperatorForm;
import com.echothree.control.user.search.common.result.EditSearchDefaultOperatorResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchDefaultOperatorSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
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
public class EditSearchDefaultOperatorCommand
        extends BaseAbstractEditCommand<SearchDefaultOperatorSpec, SearchDefaultOperatorEdit, EditSearchDefaultOperatorResult, SearchDefaultOperator, SearchDefaultOperator> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchDefaultOperator.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchDefaultOperatorName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchDefaultOperatorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSearchDefaultOperatorCommand */
    public EditSearchDefaultOperatorCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditSearchDefaultOperatorResult getResult() {
        return SearchResultFactory.getEditSearchDefaultOperatorResult();
    }

    @Override
    public SearchDefaultOperatorEdit getEdit() {
        return SearchEditFactory.getSearchDefaultOperatorEdit();
    }

    @Override
    public SearchDefaultOperator getEntity(EditSearchDefaultOperatorResult result) {
        var searchControl = Session.getModelController(SearchControl.class);
        SearchDefaultOperator searchDefaultOperator;
        var searchDefaultOperatorName = spec.getSearchDefaultOperatorName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            searchDefaultOperator = searchControl.getSearchDefaultOperatorByName(searchDefaultOperatorName);
        } else { // EditMode.UPDATE
            searchDefaultOperator = searchControl.getSearchDefaultOperatorByNameForUpdate(searchDefaultOperatorName);
        }

        if(searchDefaultOperator == null) {
            addExecutionError(ExecutionErrors.UnknownSearchDefaultOperatorName.name(), searchDefaultOperatorName);
        }

        return searchDefaultOperator;
    }

    @Override
    public SearchDefaultOperator getLockEntity(SearchDefaultOperator searchDefaultOperator) {
        return searchDefaultOperator;
    }

    @Override
    public void fillInResult(EditSearchDefaultOperatorResult result, SearchDefaultOperator searchDefaultOperator) {
        var searchControl = Session.getModelController(SearchControl.class);

        result.setSearchDefaultOperator(searchControl.getSearchDefaultOperatorTransfer(getUserVisit(), searchDefaultOperator));
    }

    @Override
    public void doLock(SearchDefaultOperatorEdit edit, SearchDefaultOperator searchDefaultOperator) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchDefaultOperatorDescription = searchControl.getSearchDefaultOperatorDescription(searchDefaultOperator, getPreferredLanguage());
        var searchDefaultOperatorDetail = searchDefaultOperator.getLastDetail();

        edit.setSearchDefaultOperatorName(searchDefaultOperatorDetail.getSearchDefaultOperatorName());
        edit.setIsDefault(searchDefaultOperatorDetail.getIsDefault().toString());
        edit.setSortOrder(searchDefaultOperatorDetail.getSortOrder().toString());

        if(searchDefaultOperatorDescription != null) {
            edit.setDescription(searchDefaultOperatorDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(SearchDefaultOperator searchDefaultOperator) {
        var searchControl = Session.getModelController(SearchControl.class);
        var searchDefaultOperatorName = edit.getSearchDefaultOperatorName();
        var duplicateSearchDefaultOperator = searchControl.getSearchDefaultOperatorByName(searchDefaultOperatorName);

        if(duplicateSearchDefaultOperator != null && !searchDefaultOperator.equals(duplicateSearchDefaultOperator)) {
            addExecutionError(ExecutionErrors.DuplicateSearchDefaultOperatorName.name(), searchDefaultOperatorName);
        }
    }

    @Override
    public void doUpdate(SearchDefaultOperator searchDefaultOperator) {
        var searchControl = Session.getModelController(SearchControl.class);
        var partyPK = getPartyPK();
        var searchDefaultOperatorDetailValue = searchControl.getSearchDefaultOperatorDetailValueForUpdate(searchDefaultOperator);
        var searchDefaultOperatorDescription = searchControl.getSearchDefaultOperatorDescriptionForUpdate(searchDefaultOperator, getPreferredLanguage());
        var description = edit.getDescription();

        searchDefaultOperatorDetailValue.setSearchDefaultOperatorName(edit.getSearchDefaultOperatorName());
        searchDefaultOperatorDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        searchDefaultOperatorDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        searchControl.updateSearchDefaultOperatorFromValue(searchDefaultOperatorDetailValue, partyPK);

        if(searchDefaultOperatorDescription == null && description != null) {
            searchControl.createSearchDefaultOperatorDescription(searchDefaultOperator, getPreferredLanguage(), description, partyPK);
        } else {
            if(searchDefaultOperatorDescription != null && description == null) {
                searchControl.deleteSearchDefaultOperatorDescription(searchDefaultOperatorDescription, partyPK);
            } else {
                if(searchDefaultOperatorDescription != null && description != null) {
                    var searchDefaultOperatorDescriptionValue = searchControl.getSearchDefaultOperatorDescriptionValue(searchDefaultOperatorDescription);

                    searchDefaultOperatorDescriptionValue.setDescription(description);
                    searchControl.updateSearchDefaultOperatorDescriptionFromValue(searchDefaultOperatorDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
