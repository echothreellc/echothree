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
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.item.common.ItemAliasConstants;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.item.server.entity.ItemAliasTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("item alias type object")
@GraphQLName("ItemAliasType")
public class ItemAliasTypeObject
        extends BaseEntityInstanceObject {
    
    private final ItemAliasType itemAliasType; // Always Present
    
    public ItemAliasTypeObject(ItemAliasType itemAliasType) {
        super(itemAliasType.getPrimaryKey());
        
        this.itemAliasType = itemAliasType;
    }

    private ItemAliasTypeDetail itemAliasTypeDetail; // Optional, use getItemAliasTypeDetail()
    
    private ItemAliasTypeDetail getItemAliasTypeDetail() {
        if(itemAliasTypeDetail == null) {
            itemAliasTypeDetail = itemAliasType.getLastDetail();
        }
        
        return itemAliasTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("item alias type name")
    @GraphQLNonNull
    public String getItemAliasTypeName() {
        return getItemAliasTypeDetail().getItemAliasTypeName();
    }

    @GraphQLField
    @GraphQLDescription("validation pattern")
    public String getValidationPattern() {
        return getItemAliasTypeDetail().getValidationPattern();
    }

    @GraphQLField
    @GraphQLDescription("item alias checksum type")
    @GraphQLNonNull
    public ItemAliasChecksumTypeObject getItemAliasChecksumType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemAliasChecksumTypeAccess(env) ? new ItemAliasChecksumTypeObject(getItemAliasTypeDetail().getItemAliasChecksumType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("allow multiple")
    @GraphQLNonNull
    public boolean getAllowMultiple() {
        return getItemAliasTypeDetail().getAllowMultiple();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getItemAliasTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getItemAliasTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var itemControl = Session.getModelController(ItemControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return itemControl.getBestItemAliasTypeDescription(itemAliasType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("item aliases")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemAliasObject> getItemAliases(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasItemAliasesAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemAliasesByItemAliasType(itemAliasType);

            try(var objectLimiter = new ObjectLimiter(env, ItemAliasConstants.COMPONENT_VENDOR_NAME, ItemAliasConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getItemAliasesByItemAliasType(itemAliasType);
                var itemAliases = entities.stream().map(ItemAliasObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, itemAliases);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
