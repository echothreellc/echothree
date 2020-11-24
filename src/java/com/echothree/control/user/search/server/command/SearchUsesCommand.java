// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.search.common.form.SearchUsesForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.result.SearchUsesResult;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.offer.server.search.UseSearchEvaluator;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.search.server.entity.PartySearchTypePreference;
import com.echothree.model.data.search.server.entity.PartySearchTypePreferenceDetail;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.persistence.Session;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchUsesCommand
        extends BaseSearchCommand<SearchUsesForm, SearchUsesResult> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
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
                ));
    }

    /** Creates a new instance of SearchUsesCommand */
    public SearchUsesCommand(UserVisitPK userVisitPK, SearchUsesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        SearchUsesResult result = SearchResultFactory.getSearchUsesResult();
        SearchLogic searchLogic = SearchLogic.getInstance();
        SearchKind searchKind = searchLogic.getSearchKindByName(null, SearchConstants.SearchKind_USE);

        if(!hasExecutionErrors()) {
            String searchTypeName = form.getSearchTypeName();
            SearchType searchType = searchLogic.getSearchTypeByName(this, searchKind, searchTypeName);

            if(!hasExecutionErrors()) {
                String languageIsoName = form.getLanguageIsoName();
                Language language = languageIsoName == null ? null : LanguageLogic.getInstance().getLanguageByName(this, languageIsoName);
                
                if(!hasExecutionErrors()) {
                    var searchControl = Session.getModelController(SearchControl.class);
                    PartySearchTypePreference partySearchTypePreference = getPartySearchTypePreference(searchControl, searchType);
                    PartySearchTypePreferenceDetail partySearchTypePreferenceDetail = partySearchTypePreference == null ? null : partySearchTypePreference.getLastDetail();
                    boolean rememberPreferences = Boolean.valueOf(form.getRememberPreferences());
                    String searchDefaultOperatorName = form.getSearchDefaultOperatorName();
                    SearchDefaultOperator searchDefaultOperator = searchDefaultOperatorName == null 
                            ? getDefaultSearchDefaultOperator(searchLogic, rememberPreferences, partySearchTypePreferenceDetail)
                            : searchLogic.getSearchDefaultOperatorByName(this, searchDefaultOperatorName);

                    if(!hasExecutionErrors()) {
                        String searchSortOrderName = form.getSearchSortOrderName();
                        SearchSortOrder searchSortOrder = searchSortOrderName == null
                                ? getDefaultSearchSortOrder(searchLogic, rememberPreferences, searchKind, partySearchTypePreferenceDetail)
                                : searchLogic.getSearchSortOrderByName(this, searchKind, searchSortOrderName);

                        if(!hasExecutionErrors()) {
                            String searchSortDirectionName = form.getSearchSortDirectionName();
                            SearchSortDirection searchSortDirection = searchSortDirectionName == null
                                    ? getDefaultSearchSortDirection(searchLogic, rememberPreferences, partySearchTypePreferenceDetail)
                                    : searchLogic.getSearchSortDirectionByName(this, searchSortDirectionName);

                            if(!hasExecutionErrors()) {
                                String searchUseTypeName = form.getSearchUseTypeName();
                                SearchUseType searchUseType = searchUseTypeName == null ? null : SearchLogic.getInstance().getSearchUseTypeByName(this, searchUseTypeName);

                                if(!hasExecutionErrors()) {
                                    UserVisit userVisit = getUserVisit();
                                    String createdSince = form.getCreatedSince();
                                    String modifiedSince = form.getModifiedSince();
                                    String fields = form.getFields();

                                    if(rememberPreferences) {
                                        Party party = getParty();

                                        if(party != null) {
                                            updatePartySearchTypePreferences(searchControl, searchType, partySearchTypePreference, searchDefaultOperator,
                                                    searchSortOrder, searchSortDirection, party);
                                        }
                                    }

                                    UseSearchEvaluator useSearchEvaluator = new UseSearchEvaluator(userVisit,
                                            language, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, searchUseType);

                                    useSearchEvaluator.setQ(this, form.getQ());
                                    useSearchEvaluator.setCreatedSince(createdSince == null ? null : Long.valueOf(createdSince));
                                    useSearchEvaluator.setModifiedSince(modifiedSince == null ? null : Long.valueOf(modifiedSince));
                                    useSearchEvaluator.setFields(fields == null ? null : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(fields).toArray(new String[0]));

                                    if(!hasExecutionErrors()) {
                                        result.setCount(useSearchEvaluator.execute(this));
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
