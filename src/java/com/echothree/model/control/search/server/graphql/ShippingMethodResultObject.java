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

package com.echothree.model.control.search.server.graphql;

import com.echothree.model.control.shipping.server.graphql.ShippingMethodObject;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("shipping method result object")
@GraphQLName("ShippingMethodResult")
public class ShippingMethodResultObject {
    
    private final ShippingMethod shippingMethod;
    
    public ShippingMethodResultObject(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
    
    @GraphQLField
    @GraphQLDescription("entity attribute")
    @GraphQLNonNull
    public ShippingMethodObject getShippingMethod() {
        return new ShippingMethodObject(shippingMethod);
    }
    
}
