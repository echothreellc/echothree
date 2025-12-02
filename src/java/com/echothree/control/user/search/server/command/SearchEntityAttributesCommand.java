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

import com.echothree.control.user.search.common.form.SearchEntityAttributesForm;
import com.echothree.control.user.search.common.result.SearchEntityAttributesResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.core.server.search.EntityAttributeSearchEvaluator;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.google.common.base.Splitter;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class SearchEntityAttributesCommand
        extends BaseSearchCommand<SearchEntityAttributesForm, SearchEntityAttributesResult> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttribute.name(), SecurityRoles.Search.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SearchDefaultOperatorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SearchSortDirectionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SearchSortOrderName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Q", FieldType.STRING, false, null, null),
                new FieldDefinition("CreatedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("ModifiedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("Fields", FieldType.STRING, false, null, null),
                new FieldDefinition("RememberPreferences", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("SearchUseTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    /** Creates a new instance of SearchEntityAttributesCommand */
    public SearchEntityAttributesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SearchResultFactory.getSearchEntityAttributesResult();
        var searchLogic = SearchLogic.getInstance();
        var searchKind = searchLogic.getSearchKindByName(this, SearchKinds.ENTITY_ATTRIBUTE.name());

        if(!hasExecutionErrors()) {
            var searchTypeName = form.getSearchTypeName();
            var searchType = searchLogic.getSearchTypeByName(this, searchKind, searchTypeName);

            if(!hasExecutionErrors()) {
                var languageIsoName = form.getLanguageIsoName();
                var language = languageIsoName == null ? null : LanguageLogic.getInstance().getLanguageByName(this, languageIsoName);
                
                if(!hasExecutionErrors()) {
                    var searchControl = Session.getModelController(SearchControl.class);
                    var partySearchTypePreference = getPartySearchTypePreference(searchControl, searchType);
                    var partySearchTypePreferenceDetail = partySearchTypePreference == null ? null : partySearchTypePreference.getLastDetail();
                    boolean rememberPreferences = Boolean.valueOf(form.getRememberPreferences());
                    var searchDefaultOperatorName = form.getSearchDefaultOperatorName();
                    var searchDefaultOperator = searchDefaultOperatorName == null
                            ? getDefaultSearchDefaultOperator(searchLogic, rememberPreferences, partySearchTypePreferenceDetail)
                            : searchLogic.getSearchDefaultOperatorByName(this, searchDefaultOperatorName);

                    if(!hasExecutionErrors()) {
                        var searchSortOrderName = form.getSearchSortOrderName();
                        var searchSortOrder = searchSortOrderName == null
                                ? getDefaultSearchSortOrder(searchLogic, rememberPreferences, searchKind, partySearchTypePreferenceDetail)
                                : searchLogic.getSearchSortOrderByName(this, searchKind, searchSortOrderName);

                        if(!hasExecutionErrors()) {
                            var searchSortDirectionName = form.getSearchSortDirectionName();
                            var searchSortDirection = searchSortDirectionName == null
                                    ? getDefaultSearchSortDirection(searchLogic, rememberPreferences, partySearchTypePreferenceDetail)
                                    : searchLogic.getSearchSortDirectionByName(this, searchSortDirectionName);

                            if(!hasExecutionErrors()) {
                                var searchUseTypeName = form.getSearchUseTypeName();
                                var searchUseType = searchUseTypeName == null ? null : SearchLogic.getInstance().getSearchUseTypeByName(this, searchUseTypeName);

                                if(!hasExecutionErrors()) {
                                    var userVisit = getUserVisit();
                                    var createdSince = form.getCreatedSince();
                                    var modifiedSince = form.getModifiedSince();
                                    var fields = form.getFields();

                                    if(rememberPreferences) {
                                        var party = getParty();

                                        if(party != null) {
                                            updatePartySearchTypePreferences(searchControl, searchType, partySearchTypePreference, searchDefaultOperator,
                                                    searchSortOrder, searchSortDirection, party);
                                        }
                                    }

                                    var entityAttributeSearchEvaluator = new EntityAttributeSearchEvaluator(userVisit, language,
                                            searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, searchUseType);

                                    entityAttributeSearchEvaluator.setQ(this, form.getQ());
                                    entityAttributeSearchEvaluator.setCreatedSince(createdSince == null ? null : Long.valueOf(createdSince));
                                    entityAttributeSearchEvaluator.setModifiedSince(modifiedSince == null ? null : Long.valueOf(modifiedSince));
                                    entityAttributeSearchEvaluator.setFields(fields == null ? null : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(fields).toArray(new String[0]));

                                    if(!hasExecutionErrors()) {
                                        result.setCount(entityAttributeSearchEvaluator.execute(this));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return result;
    }
}
