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

package com.echothree.model.control.sequence.server.graphql;

import com.echothree.control.user.sequence.common.result.GetSequenceValueResult;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("get sequence value result object")
@GraphQLName("GetSequenceValueResult")
public class GetSequenceValueResultObject
        extends BaseObject {

    GetSequenceValueResult result;

    public GetSequenceValueResultObject(final GetSequenceValueResult result) {
        this.result = result;
    }

    @GraphQLField
    @GraphQLDescription("value")
    @GraphQLNonNull
    public String getValue() {
        return result.getValue();
    }

}
