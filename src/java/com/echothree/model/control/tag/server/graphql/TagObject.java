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

package com.echothree.model.control.tag.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.tag.server.entity.Tag;
import com.echothree.model.data.tag.server.entity.TagDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("tag object")
@GraphQLName("Tag")
public class TagObject
        extends BaseEntityInstanceObject {
    
    private final Tag tag; // Always Present
    
    public TagObject(Tag tag) {
        super(tag.getPrimaryKey());
        
        this.tag = tag;
    }

    private TagDetail tagDetail; // Optional, use getTagDetail()
    
    private TagDetail getTagDetail() {
        if(tagDetail == null) {
            tagDetail = tag.getLastDetail();
        }
        
        return tagDetail;
    }

    @GraphQLField
    @GraphQLDescription("tag scope")
    @GraphQLNonNull
    public TagScopeObject getTagScope(final DataFetchingEnvironment env) {
        return TagSecurityUtils.getHasTagScopeAccess(env) ? new TagScopeObject(getTagDetail().getTagScope(), null): null;
    }

    @GraphQLField
    @GraphQLDescription("tag scope name")
    @GraphQLNonNull
    public String getTagName() {
        return getTagDetail().getTagName();
    }

    @GraphQLField
    @GraphQLDescription("usage count")
    @GraphQLNonNull
    public long getUsageCount() {
        var tagControl = Session.getModelController(TagControl.class);

        return tagControl.countEntityTagsByTag(tag);
    }

}
