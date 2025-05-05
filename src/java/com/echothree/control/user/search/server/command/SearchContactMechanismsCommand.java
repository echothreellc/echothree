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

import com.echothree.control.user.search.common.form.SearchContactMechanismsForm;
import com.echothree.control.user.search.common.result.SearchContactMechanismsResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.contact.server.search.ContactMechanismSearchEvaluator;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.persistence.Session;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchContactMechanismsCommand
        extends BaseSearchCommand<SearchContactMechanismsForm, SearchContactMechanismsResult> {
    
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

    /** Creates a new instance of SearchContactMechanismsCommand */
    public SearchContactMechanismsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SearchResultFactory.getSearchContactMechanismsResult();
        var searchLogic = SearchLogic.getInstance();
        var searchKind = searchLogic.getSearchKindByName(this, SearchKinds.CONTACT_MECHANISM.name());

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

                                    var contactMechanismSearchEvaluator = new ContactMechanismSearchEvaluator(userVisit, language,
                                            searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, searchUseType);

                                    contactMechanismSearchEvaluator.setQ(this, form.getQ());
                                    contactMechanismSearchEvaluator.setCreatedSince(createdSince == null ? null : Long.valueOf(createdSince));
                                    contactMechanismSearchEvaluator.setModifiedSince(modifiedSince == null ? null : Long.valueOf(modifiedSince));
                                    contactMechanismSearchEvaluator.setFields(fields == null ? null : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(fields).toArray(new String[0]));

                                    if(!hasExecutionErrors()) {
                                        result.setCount(contactMechanismSearchEvaluator.execute(this));
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
