// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.control.user.search.common.form.CheckItemSpellingForm;
import com.echothree.control.user.search.common.result.CheckItemSpellingResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.item.server.search.ItemSpellCheckEvaluator;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CheckItemSpellingCommand
        extends BaseCheckSpellingCommand<CheckItemSpellingForm, CheckItemSpellingResult> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SearchDefaultOperatorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Q", FieldType.STRING, false, null, null)
        );
    }

    /** Creates a new instance of CheckItemSpellingCommand */
    public CheckItemSpellingCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SearchResultFactory.getCheckItemSpellingResult();
        var searchLogic = SearchLogic.getInstance();
        var searchKind = searchLogic.getSearchKindByName(this, SearchKinds.ITEM.name());

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
                    var searchDefaultOperatorName = form.getSearchDefaultOperatorName();
                    var searchDefaultOperator = searchDefaultOperatorName == null
                            ? getDefaultSearchDefaultOperator(searchLogic, false, partySearchTypePreferenceDetail)
                            : searchLogic.getSearchDefaultOperatorByName(this, searchDefaultOperatorName);

                    if(!hasExecutionErrors()) {
                        var itemSpellCheckEvaluator = new ItemSpellCheckEvaluator(getUserVisit(), language, searchDefaultOperator);

                        itemSpellCheckEvaluator.setQ(this, form.getQ());

                        result.setCheckSpellingWords(itemSpellCheckEvaluator.checkSpelling(this));
                    }
                }
            }
        }

        return result;
    }

}
