// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.inventory.server.graphql;

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.uom.server.graphql.UomSecurityUtils;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.inventory.server.entity.LotDetail;
import com.echothree.util.server.string.AmountUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("lot object")
@GraphQLName("Lot")
public class LotObject
        extends BaseEntityInstanceObject {
    
    private final Lot lot; // Always Present
    
    public LotObject(Lot lot) {
        super(lot.getPrimaryKey());
        
        this.lot = lot;
    }

    private LotDetail lotDetail; // Optional, use getLotDetail()
    
    private LotDetail getLotDetail() {
        if(lotDetail == null) {
            lotDetail = lot.getLastDetail();
        }
        
        return lotDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("inventory condition name")
    @GraphQLNonNull
    public String getLotName() {
        return getLotDetail().getLotName();
    }

    @GraphQLField
    @GraphQLDescription("item")
    public ItemObject getItem(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getInstance().getHasItemAccess(env) ? new ItemObject(getLotDetail().getItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory condition")
    public InventoryConditionObject getInventoryCondition(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getInstance().getHasInventoryConditionAccess(env) ? new InventoryConditionObject(getLotDetail().getInventoryCondition()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unit of measure type")
    public UnitOfMeasureTypeObject getUnitOfMeasureType(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getInstance().getHasUnitOfMeasureTypeAccess(env) ? new UnitOfMeasureTypeObject(getLotDetail().getUnitOfMeasureType()) : null;
    }

    @GraphQLDescription("quantity")
    public Long getQuantity() {
        return getLotDetail().getQuantity();
    }

    @GraphQLField
    @GraphQLDescription("currency")
    public CurrencyObject getCurrency(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getInstance().getHasCurrencyAccess(env) ? new CurrencyObject(getLotDetail().getCurrency()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unformatted unit cost")
    public Long getUnformattedUnitCost() {
        return getLotDetail().getUnitCost();
    }

    @GraphQLField
    @GraphQLDescription("unit cost")
    public String getUnitCost(final DataFetchingEnvironment env) {
        return AmountUtils.getInstance().formatCostUnit(getLotDetail().getCurrency(), getLotDetail().getUnitCost());
    }

}
