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
import com.echothree.model.data.item.common.ItemDescriptionTypeUseConstants;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("item description type use type object")
@GraphQLName("ItemDescriptionTypeUseType")
public class ItemDescriptionTypeUseTypeObject
        extends BaseEntityInstanceObject {
    
    private final ItemDescriptionTypeUseType itemDescriptionTypeUseType; // Always Present
    
    public ItemDescriptionTypeUseTypeObject(ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        super(itemDescriptionTypeUseType.getPrimaryKey());
        
        this.itemDescriptionTypeUseType = itemDescriptionTypeUseType;
    }

    private ItemDescriptionTypeUseTypeDetail itemDescriptionTypeUseTypeDetail; // Optional, use getItemDescriptionTypeUseTypeDetail()
    
    private ItemDescriptionTypeUseTypeDetail getItemDescriptionTypeUseTypeDetail() {
        if(itemDescriptionTypeUseTypeDetail == null) {
            itemDescriptionTypeUseTypeDetail = itemDescriptionTypeUseType.getLastDetail();
        }
        
        return itemDescriptionTypeUseTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("item description type use type name")
    @GraphQLNonNull
    public String getItemDescriptionTypeUseTypeName() {
        return getItemDescriptionTypeUseTypeDetail().getItemDescriptionTypeUseTypeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getItemDescriptionTypeUseTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getItemDescriptionTypeUseTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var itemControl = Session.getModelController(ItemControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return itemControl.getBestItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("item description type uses")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemDescriptionTypeUseObject> getItemDescriptionTypeUses(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasItemDescriptionTypeUsesAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemDescriptionTypeUsesByItemDescriptionTypeUseType(itemDescriptionTypeUseType);

            try(var objectLimiter = new ObjectLimiter(env, ItemDescriptionTypeUseConstants.COMPONENT_VENDOR_NAME, ItemDescriptionTypeUseConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getItemDescriptionTypeUsesByItemDescriptionTypeUseType(itemDescriptionTypeUseType);
                var itemDescriptionTypeUses = entities.stream().map(ItemDescriptionTypeUseObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, itemDescriptionTypeUses);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
