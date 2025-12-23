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

package com.echothree.model.control.search.server.graphql;

import com.echothree.model.control.search.common.transfer.CheckSpellingWordTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import static java.util.Collections.emptyList;
import java.util.List;

@GraphQLDescription("check spelling word")
@GraphQLName("CheckSpellingWord")
public class CheckSpellingWordObject {

    private final CheckSpellingWordTransfer checkSpellingWord;

    public CheckSpellingWordObject(CheckSpellingWordTransfer checkSpellingWord) {
        this.checkSpellingWord = checkSpellingWord;
    }

    @GraphQLField
    @GraphQLDescription("word")
    @GraphQLNonNull
    public String getWord() {
        return checkSpellingWord.getWord();
    }

    @GraphQLField
    @GraphQLDescription("search check spelling action type")
    public SearchCheckSpellingActionTypeObject getSearchCheckSpellingActionType(final DataFetchingEnvironment env) {
        SearchCheckSpellingActionTypeObject object;

        if(SearchSecurityUtils.getHasSearchCheckSpellingActionTypeAccess(env)) {
            var searchControl = Session.getModelController(SearchControl.class);
            var entity = searchControl.getSearchCheckSpellingActionTypeByName(
                    checkSpellingWord.getSearchCheckSpellingActionType().getSearchCheckSpellingActionTypeName());

            object = new SearchCheckSpellingActionTypeObject(entity);
        } else {
            object = null;
        }

        return object;
    }

    @GraphQLField
    @GraphQLDescription("check spelling suggestions")
    @GraphQLNonNull
    public List<CheckSpellingSuggestionObject> getCheckSpellingSuggestions(final DataFetchingEnvironment env) {
        List<CheckSpellingSuggestionObject> objects;
        var checkSpellingSuggestions = checkSpellingWord.getCheckSpellingSuggestions().getList();

        if(checkSpellingSuggestions == null) {
            objects = emptyList();
        } else {
            objects = new ArrayList<>(checkSpellingSuggestions.size());

            checkSpellingSuggestions.stream()
                    .map(CheckSpellingSuggestionObject::new)
                    .forEachOrdered(objects::add);
        }

        return objects;
    }

}
