// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.search.common.edit.SearchSortOrderEdit;
import com.echothree.control.user.search.common.form.EditSearchSortOrderForm;
import com.echothree.control.user.search.common.result.EditSearchSortOrderResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchSortOrderSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.search.server.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchKindDetail;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchSortOrderDescription;
import com.echothree.model.data.search.server.entity.SearchSortOrderDetail;
import com.echothree.model.data.search.server.value.SearchSortOrderDescriptionValue;
import com.echothree.model.data.search.server.value.SearchSortOrderDetailValue;
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

public class EditSearchSortOrderCommand
        extends BaseAbstractEditCommand<SearchSortOrderSpec, SearchSortOrderEdit, EditSearchSortOrderResult, SearchSortOrder, SearchSortOrder> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchSortOrder.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchSortOrderName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchSortOrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditSearchSortOrderCommand */
    public EditSearchSortOrderCommand(UserVisitPK userVisitPK, EditSearchSortOrderForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSearchSortOrderResult getResult() {
        return SearchResultFactory.getEditSearchSortOrderResult();
    }

    @Override
    public SearchSortOrderEdit getEdit() {
        return SearchEditFactory.getSearchSortOrderEdit();
    }

    SearchKind searchKind;

    @Override
    public SearchSortOrder getEntity(EditSearchSortOrderResult result) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchSortOrder searchSortOrder = null;
        String searchKindName = spec.getSearchKindName();

        searchKind = searchControl.getSearchKindByName(searchKindName);

        if(searchKind != null) {
            String searchSortOrderName = spec.getSearchSortOrderName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                searchSortOrder = searchControl.getSearchSortOrderByName(searchKind, searchSortOrderName);
            } else { // EditMode.UPDATE
                searchSortOrder = searchControl.getSearchSortOrderByNameForUpdate(searchKind, searchSortOrderName);
            }

            if(searchSortOrder == null) {
                addExecutionError(ExecutionErrors.UnknownSearchSortOrderName.name(), searchKindName, searchSortOrderName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), searchKindName);
        }

        return searchSortOrder;
    }

    @Override
    public SearchSortOrder getLockEntity(SearchSortOrder searchSortOrder) {
        return searchSortOrder;
    }

    @Override
    public void fillInResult(EditSearchSortOrderResult result, SearchSortOrder searchSortOrder) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);

        result.setSearchSortOrder(searchControl.getSearchSortOrderTransfer(getUserVisit(), searchSortOrder));
    }

    @Override
    public void doLock(SearchSortOrderEdit edit, SearchSortOrder searchSortOrder) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchSortOrderDescription searchSortOrderDescription = searchControl.getSearchSortOrderDescription(searchSortOrder, getPreferredLanguage());
        SearchSortOrderDetail searchSortOrderDetail = searchSortOrder.getLastDetail();

        edit.setSearchSortOrderName(searchSortOrderDetail.getSearchSortOrderName());
        edit.setIsDefault(searchSortOrderDetail.getIsDefault().toString());
        edit.setSortOrder(searchSortOrderDetail.getSortOrder().toString());

        if(searchSortOrderDescription != null) {
            edit.setDescription(searchSortOrderDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(SearchSortOrder searchSortOrder) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchKindDetail searchKindDetail = searchKind.getLastDetail();
        String searchSortOrderName = edit.getSearchSortOrderName();
        SearchSortOrder duplicateSearchSortOrder = searchControl.getSearchSortOrderByName(searchKind, searchSortOrderName);

        if(duplicateSearchSortOrder != null && !searchSortOrder.equals(duplicateSearchSortOrder)) {
            addExecutionError(ExecutionErrors.DuplicateSearchSortOrderName.name(), searchKindDetail.getSearchKindName(), searchSortOrderName);
        }
    }

    @Override
    public void doUpdate(SearchSortOrder searchSortOrder) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        PartyPK partyPK = getPartyPK();
        SearchSortOrderDetailValue searchSortOrderDetailValue = searchControl.getSearchSortOrderDetailValueForUpdate(searchSortOrder);
        SearchSortOrderDescription searchSortOrderDescription = searchControl.getSearchSortOrderDescriptionForUpdate(searchSortOrder, getPreferredLanguage());
        String description = edit.getDescription();

        searchSortOrderDetailValue.setSearchSortOrderName(edit.getSearchSortOrderName());
        searchSortOrderDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        searchSortOrderDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        searchControl.updateSearchSortOrderFromValue(searchSortOrderDetailValue, partyPK);

        if(searchSortOrderDescription == null && description != null) {
            searchControl.createSearchSortOrderDescription(searchSortOrder, getPreferredLanguage(), description, partyPK);
        } else if(searchSortOrderDescription != null && description == null) {
            searchControl.deleteSearchSortOrderDescription(searchSortOrderDescription, partyPK);
        } else if(searchSortOrderDescription != null && description != null) {
            SearchSortOrderDescriptionValue searchSortOrderDescriptionValue = searchControl.getSearchSortOrderDescriptionValue(searchSortOrderDescription);

            searchSortOrderDescriptionValue.setDescription(description);
            searchControl.updateSearchSortOrderDescriptionFromValue(searchSortOrderDescriptionValue, partyPK);
        }
    }

}
