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

import com.echothree.control.user.search.common.result.BaseCheckSpellingResult;
import com.echothree.model.control.graphql.server.graphql.MutationResultObject;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import static java.util.Collections.emptyList;
import java.util.List;

public abstract class BaseCheckSpellingObject<R extends BaseCheckSpellingResult>
        extends MutationResultObject {

    private R result;

    public void setResult(R result) {
        this.result = result;
    }

    @GraphQLField
    @GraphQLDescription("check spelling words")
    @GraphQLNonNull
    public List<CheckSpellingWordObject> getCheckSpellingWords(final DataFetchingEnvironment env) {
        List<CheckSpellingWordObject> objects;

        if(result == null) {
            objects = emptyList();
        } else {
            var checkSpellingWords = result.getCheckSpellingWords();

            objects = new ArrayList<>(checkSpellingWords.size());

            checkSpellingWords.stream()
                    .map(CheckSpellingWordObject::new)
                    .forEachOrdered(objects::add);
        }

        return objects;
    }
    
}
