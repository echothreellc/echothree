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

package com.echothree.model.control.graphql.server.graphql;

import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.util.server.string.AmountUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("unit price object")
@GraphQLName("UnitPrice")
public class UnitPriceObject
        extends BaseCurrencyObject
        implements UnitAmountInterface {

    private final Long unformattedUnitPrice; // Optional

    public UnitPriceObject(Currency currency, Long unformattedUnitPrice) {
        super(currency);

        this.unformattedUnitPrice = unformattedUnitPrice;
    }

    @GraphQLField
    @GraphQLDescription("unformatted unit price")
    @GraphQLNonNull
    public Long getUnformattedUnitPrice() {
        return unformattedUnitPrice;
    }

    @GraphQLField
    @GraphQLDescription("unit price")
    public String getUnitPrice() {
        return unformattedUnitPrice == null ? null :
                AmountUtils.getInstance().formatPriceUnit(currency, unformattedUnitPrice);
    }

}
