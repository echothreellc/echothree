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

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.inventory.server.graphql.InventoryConditionObject;
import com.echothree.model.control.inventory.server.graphql.InventorySecurityUtils;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.uom.server.graphql.UomSecurityUtils;
import com.echothree.model.data.item.server.entity.ItemFixedPrice;
import com.echothree.model.data.item.server.entity.ItemPrice;
import com.echothree.model.data.item.server.entity.ItemVariablePrice;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("item price object")
@GraphQLName("ItemPrice")
public class ItemPriceObject
        extends BaseObject {
    
    private final ItemPrice itemPrice; // Always Present
    
    public ItemPriceObject(ItemPrice itemPrice) {
        super(itemPrice.getPrimaryKey());
        
        this.itemPrice = itemPrice;
    }

    String itemPriceTypeName;

    private String getItemPriceTypeName() {
        if(itemPriceTypeName == null) {
            itemPriceTypeName = itemPrice.getItem().getLastDetail().getItemPriceType().getItemPriceTypeName();
        }

        return itemPriceTypeName;
    }

    private ItemFixedPrice itemFixedPrice;

    private ItemFixedPrice getItemFixedPrice() {
        if(itemFixedPrice == null) {
            if(getItemPriceTypeName().equals(ItemPriceTypes.FIXED.name())) {
                var itemControl = Session.getModelController(ItemControl.class);

                itemFixedPrice = itemControl.getItemFixedPrice(itemPrice);
            }
        }

        return itemFixedPrice;
    }

    private ItemVariablePrice itemVariablePrice;

    private ItemVariablePrice getItemVariablePrice() {
        if(itemVariablePrice == null) {
            if(getItemPriceTypeName().equals(ItemPriceTypes.VARIABLE.name())) {
                var itemControl = Session.getModelController(ItemControl.class);

                itemVariablePrice = itemControl.getItemVariablePrice(itemPrice);
            }
        }

        return itemVariablePrice;
    }

    @GraphQLField
    @GraphQLDescription("item")
    public ItemObject getItem(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getInstance().getHasItemAccess(env) ? new ItemObject(itemPrice.getItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory condition")
    public InventoryConditionObject getInventoryCondition(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getInstance().getHasInventoryConditionAccess(env) ? new InventoryConditionObject(itemPrice.getInventoryCondition()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unit of measure type")
    public UnitOfMeasureTypeObject getUnitOfMeasureType(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getInstance().getHasUnitOfMeasureTypeAccess(env) ? new UnitOfMeasureTypeObject(itemPrice.getUnitOfMeasureType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("currency")
    public CurrencyObject getCurrency(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getInstance().getHasCurrencyAccess(env) ? new CurrencyObject(itemPrice.getCurrency()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unformattedUnitPrice")
    public Long getUnformattedUnitPrice() {
        var itemFixedPrice = getItemFixedPrice();

        return itemFixedPrice == null ? null : itemFixedPrice.getUnitPrice();
    }

    @GraphQLField
    @GraphQLDescription("unitPrice")
    public String getUnitPrice() {
        var itemFixedPrice = getItemFixedPrice();

        return itemFixedPrice == null ? null :
                AmountUtils.getInstance().formatPriceUnit(itemPrice.getCurrency(), itemFixedPrice.getUnitPrice());
    }

    @GraphQLField
    @GraphQLDescription("unformattedMinimumUnitPrice")
    public Long getUnformattedMinimumUnitPrice() {
        var itemVariablePrice = getItemVariablePrice();

        return itemVariablePrice == null ? null : itemVariablePrice.getMinimumUnitPrice();
    }

    @GraphQLField
    @GraphQLDescription("minimumUnitPrice")
    public String getMinimumUnitPrice() {
        var itemVariablePrice = getItemVariablePrice();

        return itemVariablePrice == null ? null :
                AmountUtils.getInstance().formatPriceUnit(itemPrice.getCurrency(), itemVariablePrice.getMinimumUnitPrice());
    }

    @GraphQLField
    @GraphQLDescription("unformattedMaximumUnitPrice")
    public Long getUnformattedMaximumUnitPrice() {
        var itemVariablePrice = getItemVariablePrice();

        return itemVariablePrice == null ? null : itemVariablePrice.getMaximumUnitPrice();
    }

    @GraphQLField
    @GraphQLDescription("maximumUnitPrice")
    public String getMaximumUnitPrice() {
        var itemVariablePrice = getItemVariablePrice();

        return itemVariablePrice == null ? null :
                AmountUtils.getInstance().formatPriceUnit(itemPrice.getCurrency(), itemVariablePrice.getMaximumUnitPrice());
    }

    @GraphQLField
    @GraphQLDescription("unformattedUnitPriceIncrement")
    public Long getUnformattedUnitPriceIncrement() {
        var itemVariablePrice = getItemVariablePrice();

        return itemVariablePrice == null ? null : itemVariablePrice.getUnitPriceIncrement();
    }

    @GraphQLField
    @GraphQLDescription("unitPriceIncrement")
    public String getUnitPriceIncrement() {
        var itemVariablePrice = getItemVariablePrice();

        return itemVariablePrice == null ? null :
                AmountUtils.getInstance().formatPriceUnit(itemPrice.getCurrency(), itemVariablePrice.getUnitPriceIncrement());
    }

}
