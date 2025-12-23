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
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.party.server.entity.PersonalTitleDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("personal title object")
@GraphQLName("PersonalTitle")
public class PersonalTitleObject
        extends BaseEntityInstanceObject {
    
    private final PersonalTitle personalTitle; // Always Present
    
    public PersonalTitleObject(PersonalTitle personalTitle) {
        super(personalTitle.getPrimaryKey());

        this.personalTitle = personalTitle;
    }

    private PersonalTitleDetail personalTitleDetail; // Optional, use getPersonalTitleDetail()
    
    private PersonalTitleDetail getPersonalTitleDetail() {
        if(personalTitleDetail == null) {
            personalTitleDetail = personalTitle.getLastDetail();
        }
        
        return personalTitleDetail;
    }

    @GraphQLField
    @GraphQLDescription("personal title id")
    @GraphQLNonNull
    public String getPersonalTitleId() {
        return Long.toString(personalTitle.getPrimaryKey().getEntityId());
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription() {
        return getPersonalTitleDetail().getDescription();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPersonalTitleDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPersonalTitleDetail().getSortOrder();
    }

}
