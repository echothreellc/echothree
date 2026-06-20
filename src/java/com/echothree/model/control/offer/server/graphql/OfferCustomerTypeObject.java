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

package com.echothree.model.control.offer.server.graphql;

import com.echothree.model.control.customer.server.graphql.CustomerSecurityUtils;
import com.echothree.model.control.customer.server.graphql.CustomerTypeObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.data.offer.server.entity.OfferCustomerType;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("offer customer type price object")
@GraphQLName("OfferCustomerType")
public class OfferCustomerTypeObject
        extends BaseEntityInstanceObject {
    
    private final OfferCustomerType offerCustomerType; // Always Present
    
    public OfferCustomerTypeObject(OfferCustomerType offerCustomerType) {
        super(offerCustomerType.getPrimaryKey());
        
        this.offerCustomerType = offerCustomerType;
    }

    @GraphQLField
    @GraphQLDescription("offer")
    public OfferObject getOffer(final DataFetchingEnvironment env) {
        return OfferSecurityUtils.getHasOfferAccess(env) ? new OfferObject(offerCustomerType.getOffer()) : null;
    }

    @GraphQLField
    @GraphQLDescription("customer type")
    public CustomerTypeObject getCustomerType(final DataFetchingEnvironment env) {
        return CustomerSecurityUtils.getHasCustomerTypeAccess(env) ? new CustomerTypeObject(offerCustomerType.getCustomerType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return offerCustomerType.getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return offerCustomerType.getSortOrder();
    }

}
