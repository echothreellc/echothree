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

package com.echothree.model.control.uom.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.item.common.ItemConstants;
import com.echothree.model.data.uom.common.UnitOfMeasureKindUseConstants;
import com.echothree.model.data.uom.common.UnitOfMeasureTypeConstants;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("unit of measure kind object")
@GraphQLName("UnitOfMeasureKind")
public class UnitOfMeasureKindObject
        extends BaseEntityInstanceObject {
    
    private final UnitOfMeasureKind unitOfMeasureKind; // Always Present
    
    public UnitOfMeasureKindObject(UnitOfMeasureKind unitOfMeasureKind) {
        super(unitOfMeasureKind.getPrimaryKey());
        
        this.unitOfMeasureKind = unitOfMeasureKind;
    }

    private UnitOfMeasureKindDetail unitOfMeasureKindDetail; // Optional, use getUnitOfMeasureKindDetail()
    
    private UnitOfMeasureKindDetail getUnitOfMeasureKindDetail() {
        if(unitOfMeasureKindDetail == null) {
            unitOfMeasureKindDetail = unitOfMeasureKind.getLastDetail();
        }
        
        return unitOfMeasureKindDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure kind name")
    @GraphQLNonNull
    public String getUnitOfMeasureKindName() {
        return getUnitOfMeasureKindDetail().getUnitOfMeasureKindName();
    }
    
    @GraphQLField
    @GraphQLDescription("fraction digits")
    @GraphQLNonNull
    public int getFractionDigits() {
        return getUnitOfMeasureKindDetail().getFractionDigits();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getUnitOfMeasureKindDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getUnitOfMeasureKindDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var uomControl = Session.getModelController(UomControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return uomControl.getBestUnitOfMeasureKindDescription(unitOfMeasureKind, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("unit of measure types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<UnitOfMeasureTypeObject> getUnitOfMeasureTypes(final DataFetchingEnvironment env) {
        if(UomSecurityUtils.getHasUnitOfMeasureTypesAccess(env)) {
            var uomControl = Session.getModelController(UomControl.class);
            var totalCount = uomControl.countUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);

            try(var objectLimiter = new ObjectLimiter(env, UnitOfMeasureTypeConstants.COMPONENT_VENDOR_NAME, UnitOfMeasureTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = uomControl.getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);
                var unitOfMeasureTypes = entities.stream().map(UnitOfMeasureTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, unitOfMeasureTypes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("unit of measure kind uses")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<UnitOfMeasureKindUseObject> getUnitOfMeasureKindUses(final DataFetchingEnvironment env) {
        if(UomSecurityUtils.getHasUnitOfMeasureKindUsesAccess(env)) {
            var uomControl = Session.getModelController(UomControl.class);
            var totalCount = uomControl.countUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind);

            try(var objectLimiter = new ObjectLimiter(env, UnitOfMeasureKindUseConstants.COMPONENT_VENDOR_NAME, UnitOfMeasureKindUseConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = uomControl.getUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind);
                var unitOfMeasureKindUses = entities.stream().map(UnitOfMeasureKindUseObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, unitOfMeasureKindUses);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemObject> getItems(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasItemsAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemsByUnitOfMeasureKind(unitOfMeasureKind);

            try(var objectLimiter = new ObjectLimiter(env, ItemConstants.COMPONENT_VENDOR_NAME, ItemConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getItemsByUnitOfMeasureKind(unitOfMeasureKind);
                var itemAliass = entities.stream().map(ItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, itemAliass);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}
