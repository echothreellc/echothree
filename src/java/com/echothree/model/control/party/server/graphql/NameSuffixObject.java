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

package com.echothree.model.control.party.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.NameSuffixDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("name suffix object")
@GraphQLName("NameSuffix")
public class NameSuffixObject
        extends BaseEntityInstanceObject {
    
    private final NameSuffix nameSuffix; // Always Present
    
    public NameSuffixObject(NameSuffix nameSuffix) {
        super(nameSuffix.getPrimaryKey());

        this.nameSuffix = nameSuffix;
    }

    private NameSuffixDetail nameSuffixDetail; // Optional, use getNameSuffixDetail()
    
    private NameSuffixDetail getNameSuffixDetail() {
        if(nameSuffixDetail == null) {
            nameSuffixDetail = nameSuffix.getLastDetail();
        }
        
        return nameSuffixDetail;
    }

    @GraphQLField
    @GraphQLDescription("name suffix id")
    @GraphQLNonNull
    public String getNameSuffixId() {
        return Long.toString(nameSuffix.getPrimaryKey().getEntityId());
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription() {
        return getNameSuffixDetail().getDescription();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getNameSuffixDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getNameSuffixDetail().getSortOrder();
    }

}
