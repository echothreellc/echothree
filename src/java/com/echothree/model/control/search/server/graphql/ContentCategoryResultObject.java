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

import com.echothree.model.control.content.server.graphql.ContentCategoryObject;
import com.echothree.model.data.content.server.entity.ContentCategory;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("content category result object")
@GraphQLName("ContentCategoryResult")
public class ContentCategoryResultObject {
    
    private final ContentCategory contentCategory;
    
    public ContentCategoryResultObject(ContentCategory contentCategory) {
        this.contentCategory = contentCategory;
    }
    
    @GraphQLField
    @GraphQLDescription("content category")
    @GraphQLNonNull
    public ContentCategoryObject getContentCategory() {
        return new ContentCategoryObject(contentCategory);
    }
    
}
