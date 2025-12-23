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

@GraphQLDescription("amount object")
@GraphQLName("Amount")
public class AmountObject
        extends BaseCurrencyObject {

    private final Long unformattedAmount; // Optional

    public AmountObject(Currency currency, Long unformattedAmount) {
        super(currency);

        this.unformattedAmount = unformattedAmount;
    }

    @GraphQLField
    @GraphQLDescription("unformatted amount")
    @GraphQLNonNull
    public Long getUnformattedAmount() {
        return unformattedAmount;
    }

    @GraphQLField
    @GraphQLDescription("amount")
    public String getAmount() {
        return unformattedAmount == null ? null :
                AmountUtils.getInstance().formatAmount(currency, unformattedAmount);
    }

}
