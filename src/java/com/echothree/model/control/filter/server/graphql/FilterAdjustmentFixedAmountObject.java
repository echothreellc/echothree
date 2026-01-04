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

package com.echothree.model.control.filter.server.graphql;

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.filter.common.FilterKinds;
import com.echothree.model.control.graphql.server.graphql.UnitAmountInterface;
import com.echothree.model.control.graphql.server.graphql.UnitCostObject;
import com.echothree.model.control.graphql.server.graphql.UnitPriceObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.uom.server.graphql.UomSecurityUtils;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentFixedAmount;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("filter adjustment fixed amount object")
@GraphQLName("FilterAdjustmentFixedAmount")
public class FilterAdjustmentFixedAmountObject {

    private final FilterAdjustmentFixedAmount filterAdjustmentFixedAmount; // Always Present

    public FilterAdjustmentFixedAmountObject(FilterAdjustmentFixedAmount filterAdjustmentFixedAmount) {
        this.filterAdjustmentFixedAmount = filterAdjustmentFixedAmount;
    }

    @GraphQLField
    @GraphQLDescription("filter adjustment")
    public FilterAdjustmentObject getFilterAdjustment(final DataFetchingEnvironment env) {
        return FilterSecurityUtils.getHasFilterAdjustmentAccess(env) ? new FilterAdjustmentObject(filterAdjustmentFixedAmount.getFilterAdjustment()) : null;
    }

    @GraphQLField
    @GraphQLDescription("currency")
    public CurrencyObject getCurrency(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getHasCurrencyAccess(env) ? new CurrencyObject(filterAdjustmentFixedAmount.getCurrency()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unit of measure type")
    public UnitOfMeasureTypeObject getUnitOfMeasureType(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getHasUnitOfMeasureTypeAccess(env) ? new UnitOfMeasureTypeObject(filterAdjustmentFixedAmount.getUnitOfMeasureType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unit amount")
    public UnitAmountInterface getUnitAmount() {
        var filterKindName = filterAdjustmentFixedAmount.getFilterAdjustment().getLastDetail().getFilterKind().getLastDetail().getFilterKindName();
        var currency = filterAdjustmentFixedAmount.getCurrency();
        var unformattedFixedAmount = filterAdjustmentFixedAmount.getUnitAmount();
        UnitAmountInterface unitAmount = null;

        if(FilterKinds.COST.name().equals(filterKindName)) {
            unitAmount = new UnitCostObject(currency, unformattedFixedAmount);
        } else if(FilterKinds.PRICE.name().equals(filterKindName)) {
            unitAmount = new UnitPriceObject(currency, unformattedFixedAmount);
        }

        return unitAmount;
    }

}
