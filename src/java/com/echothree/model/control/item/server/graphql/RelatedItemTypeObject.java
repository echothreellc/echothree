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

package com.echothree.model.control.item.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.item.common.RelatedItemTypeConstants;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.RelatedItemType;
import com.echothree.model.data.item.server.entity.RelatedItemTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("related item type object")
@GraphQLName("RelatedItemType")
public class RelatedItemTypeObject
        extends BaseEntityInstanceObject {
    
    private final RelatedItemType relatedItemType; // Always Present
    private final Item fromItem; // Optional

    public RelatedItemTypeObject(final RelatedItemType relatedItemType, final Item fromItem) {
        super(relatedItemType.getPrimaryKey());

        this.relatedItemType = relatedItemType;
        this.fromItem = fromItem;
    }

    private RelatedItemTypeDetail relatedItemTypeDetail; // Optional, use getRelatedItemTypeDetail()
    
    private RelatedItemTypeDetail getRelatedItemTypeDetail() {
        if(relatedItemTypeDetail == null) {
            relatedItemTypeDetail = relatedItemType.getLastDetail();
        }
        
        return relatedItemTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("related item type name")
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

        return itemControl.getBestRelatedItemTypeDescription(relatedItemType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("related items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<RelatedItemObject> getRelatedItems(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasRelatedItemsAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countRelatedItemsByRelatedItemType(relatedItemType);

            try(var objectLimiter = new ObjectLimiter(env, RelatedItemTypeConstants.COMPONENT_VENDOR_NAME, RelatedItemTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getRelatedItemsByRelatedItemType(relatedItemType);
                var relatedItems = entities.stream().map(RelatedItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, relatedItems);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("from items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<RelatedItemObject> getFromItems(final DataFetchingEnvironment env) {
        if(fromItem != null && ItemSecurityUtils.getHasRelatedItemsAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countRelatedItemsByRelatedItemTypeAndFromItem(relatedItemType, fromItem);

            try(var objectLimiter = new ObjectLimiter(env, RelatedItemTypeConstants.COMPONENT_VENDOR_NAME, RelatedItemTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getRelatedItemsByRelatedItemTypeAndFromItem(relatedItemType, fromItem);
                var relatedItems = entities.stream().map(RelatedItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, relatedItems);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
