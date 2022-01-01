// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.search.common.form.CheckItemSpellingForm;
import com.echothree.control.user.search.common.form.GetItemResultsForm;
import com.echothree.control.user.search.common.result.CheckItemSpellingResult;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.Collections;
import java.util.List;

@GraphQLDescription("check item spelling object")
@GraphQLName("CheckItemSpelling")
public class CheckItemSpellingObject
        extends BaseCheckSpellingObject<CheckItemSpellingResult> {

    // Nothing additional beyond BaseCheckSpellingObject

}