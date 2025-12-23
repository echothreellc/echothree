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

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.SymbolPositionObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.graphql.ItemAliasObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.item.server.graphql.ItemUnitOfMeasureTypeObject;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.item.common.ItemAliasConstants;
import com.echothree.model.data.item.common.ItemUnitOfMeasureTypeConstants;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDescription;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("unit of measure type object")
@GraphQLName("UnitOfMeasureType")
public class UnitOfMeasureTypeObject
        extends BaseEntityInstanceObject {
    
    private final UnitOfMeasureType unitOfMeasureType; // Always Present
    
    public UnitOfMeasureTypeObject(UnitOfMeasureType unitOfMeasureType) {
        super(unitOfMeasureType.getPrimaryKey());
        
        this.unitOfMeasureType = unitOfMeasureType;
    }

    private UnitOfMeasureTypeDetail unitOfMeasureTypeDetail; // Optional, use getUnitOfMeasureTypeDetail()
    
    private UnitOfMeasureTypeDetail getUnitOfMeasureTypeDetail() {
        if(unitOfMeasureTypeDetail == null) {
            unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetail();
        }
        
        return unitOfMeasureTypeDetail;
    }
    
    private UnitOfMeasureTypeDescription unitOfMeasureTypeDescription; // Optional, use getUnitOfMeasureTypeDescription()
    
    private UnitOfMeasureTypeDescription getUnitOfMeasureTypeDescription(final DataFetchingEnvironment env) {
        if(unitOfMeasureTypeDescription == null) {
            var uomControl = Session.getModelController(UomControl.class);
            var userControl = Session.getModelController(UserControl.class);

            unitOfMeasureTypeDescription = uomControl.getBestUnitOfMeasureTypeDescription(unitOfMeasureType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
        }
        
        return unitOfMeasureTypeDescription;
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure kind")
    public UnitOfMeasureKindObject getUnitOfMeasureKind(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getHasUnitOfMeasureKindAccess(env) ? new UnitOfMeasureKindObject(getUnitOfMeasureTypeDetail().getUnitOfMeasureKind()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("unit of measure type name")
    @GraphQLNonNull
    public String getUnitOfMeasureTypeName() {
        return getUnitOfMeasureTypeDetail().getUnitOfMeasureTypeName();
    }
    
    @GraphQLField
    @GraphQLDescription("symbol position")
    public SymbolPositionObject getSymbolPosition(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getHasSymbolPositionAccess(env) ? new SymbolPositionObject(getUnitOfMeasureTypeDetail().getSymbolPosition()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("suppress symbol separator")
    @GraphQLNonNull
    public boolean getSuppressSymbolSeparator() {
        return getUnitOfMeasureTypeDetail().getSuppressSymbolSeparator();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getUnitOfMeasureTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getUnitOfMeasureTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("singular description")
    @GraphQLNonNull
    public String getSingularDescription(final DataFetchingEnvironment env) {
        var uomControl = Session.getModelController(UomControl.class);
        
        return uomControl.getBestSingularUnitOfMeasureTypeDescription(unitOfMeasureType, getUnitOfMeasureTypeDescription(env));
    }
    
    @GraphQLField
    @GraphQLDescription("plural description")
    @GraphQLNonNull
    public String getPluralDescription(final DataFetchingEnvironment env) {
        var uomControl = Session.getModelController(UomControl.class);
        
        return uomControl.getBestPluralUnitOfMeasureTypeDescription(unitOfMeasureType, getUnitOfMeasureTypeDescription(env));
    }
    
    @GraphQLField
    @GraphQLDescription("symbol")
    @GraphQLNonNull
    public String getSymbol(final DataFetchingEnvironment env) {
        var uomControl = Session.getModelController(UomControl.class);
        
        return uomControl.getBestUnitOfMeasureTypeDescriptionSymbol(unitOfMeasureType, getUnitOfMeasureTypeDescription(env));
    }

    @GraphQLField
    @GraphQLDescription("item unit of measure types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemUnitOfMeasureTypeObject> getItemUnitOfMeasureTypes(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasItemUnitOfMeasureTypesAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemUnitOfMeasureTypesByUnitOfMeasureType(unitOfMeasureType);

            try(var objectLimiter = new ObjectLimiter(env, ItemUnitOfMeasureTypeConstants.COMPONENT_VENDOR_NAME, ItemUnitOfMeasureTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getItemUnitOfMeasureTypesByUnitOfMeasureType(unitOfMeasureType);
                var itemAliass = entities.stream().map(ItemUnitOfMeasureTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, itemAliass);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("item aliases")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemAliasObject> getItemAliases(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasItemAliasesAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemAliasesByUnitOfMeasureType(unitOfMeasureType);

            try(var objectLimiter = new ObjectLimiter(env, ItemAliasConstants.COMPONENT_VENDOR_NAME, ItemAliasConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getItemAliasesByUnitOfMeasureType(unitOfMeasureType);
                var itemAliases = entities.stream().map(ItemAliasObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, itemAliases);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
