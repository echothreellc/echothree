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

package com.echothree.model.control.filter.server.graphql;

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.filter.common.FilterKinds;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.uom.server.graphql.UomSecurityUtils;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentFixedAmount;
import com.echothree.util.server.string.AmountUtils;
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
        return FilterSecurityUtils.getInstance().getHasFilterAdjustmentAccess(env) ? new FilterAdjustmentObject(filterAdjustmentFixedAmount.getFilterAdjustment()) : null;
    }

    @GraphQLField
    @GraphQLDescription("currency")
    public CurrencyObject getCurrency(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getInstance().getHasCurrencyAccess(env) ? new CurrencyObject(filterAdjustmentFixedAmount.getCurrency()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unit of measure type")
    public UnitOfMeasureTypeObject getUnitOfMeasureType(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getInstance().getHasUnitOfMeasureTypeAccess(env) ? new UnitOfMeasureTypeObject(filterAdjustmentFixedAmount.getUnitOfMeasureType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unformatted unit amount")
    public Long getUnformattedUnitAmount() {
        return filterAdjustmentFixedAmount.getUnitAmount();
    }

    @GraphQLField
    @GraphQLDescription("unit amount")
    public String getUnitAmount() {
        var filterKindName = filterAdjustmentFixedAmount.getFilterAdjustment().getLastDetail().getFilterKind().getLastDetail().getFilterKindName();
        var currency = filterAdjustmentFixedAmount.getCurrency();
        var unformattedFixedAmount = filterAdjustmentFixedAmount.getUnitAmount();
        String unitAmount = null;

        if(FilterKinds.COST.name().equals(filterKindName)) {
            unitAmount = AmountUtils.getInstance().formatCostUnit(currency, unformattedFixedAmount);
        } else if(FilterKinds.PRICE.name().equals(filterKindName)) {
            unitAmount = AmountUtils.getInstance().formatPriceUnit(currency, unformattedFixedAmount);
        }

        return unitAmount;
    }

}
