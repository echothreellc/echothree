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

package com.echothree.model.control.item.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.item.server.entity.RelatedItemType;
import com.echothree.model.data.item.server.entity.RelatedItemTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("related item type object")
@GraphQLName("RelatedItemType")
public class RelatedItemTypeObject
        extends BaseEntityInstanceObject {
    
    private final RelatedItemType relatedItemType; // Always Present
    
    public RelatedItemTypeObject(RelatedItemType relatedItemType) {
        super(relatedItemType.getPrimaryKey());
        
        this.relatedItemType = relatedItemType;
    }

    private RelatedItemTypeDetail relatedItemTypeDetail; // Optional, use getRelatedItemTypeDetail()
    
    private RelatedItemTypeDetail getRelatedItemTypeDetail() {
        if(relatedItemTypeDetail == null) {
            relatedItemTypeDetail = relatedItemType.getLastDetail();
        }
        
        return relatedItemTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("item alias type name")
    @GraphQLNonNull
    public String getRelatedItemTypeName() {
        return getRelatedItemTypeDetail().getRelatedItemTypeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getRelatedItemTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getRelatedItemTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var itemControl = Session.getModelController(ItemControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return itemControl.getBestRelatedItemTypeDescription(relatedItemType, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }

}
