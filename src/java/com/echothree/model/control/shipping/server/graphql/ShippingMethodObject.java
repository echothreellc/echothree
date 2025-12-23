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

package com.echothree.model.control.shipping.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.selector.server.graphql.SelectorObject;
import com.echothree.model.control.selector.server.graphql.SelectorSecurityUtils;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.shipping.server.entity.ShippingMethodDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("shipping method object")
@GraphQLName("ShippingMethod")
public class ShippingMethodObject
        extends BaseEntityInstanceObject {
    
    private final ShippingMethod shippingMethod; // Always Present
    
    public ShippingMethodObject(ShippingMethod shippingMethod) {
        super(shippingMethod.getPrimaryKey());
        
        this.shippingMethod = shippingMethod;
    }

    private ShippingMethodDetail shippingMethodDetail; // Optional, use getShippingMethodDetail()
    
    private ShippingMethodDetail getShippingMethodDetail() {
        if(shippingMethodDetail == null) {
            shippingMethodDetail = shippingMethod.getLastDetail();
        }
        
        return shippingMethodDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("shipping method name")
    @GraphQLNonNull
    public String getShippingMethodName() {
        return getShippingMethodDetail().getShippingMethodName();
    }

    @GraphQLField
    @GraphQLDescription("geo code selector")
    public SelectorObject getGeoCodeSelector(final DataFetchingEnvironment env) {
        SelectorObject result;

        if(SelectorSecurityUtils.getHasSelectorAccess(env)) {
            var selector = getShippingMethodDetail().getGeoCodeSelector();

            result = selector == null ? null : new SelectorObject(selector);
        } else {
            result = null;
        }

        return result;
    }

    @GraphQLField
    @GraphQLDescription("item selector")
    public SelectorObject getItemSelector(final DataFetchingEnvironment env) {
        SelectorObject result;

        if(SelectorSecurityUtils.getHasSelectorAccess(env)) {
            var selector = getShippingMethodDetail().getItemSelector();

            result = selector == null ? null : new SelectorObject(selector);
        } else {
            result = null;
        }

        return result;
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getShippingMethodDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var shippingControl = Session.getModelController(ShippingControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return shippingControl.getBestShippingMethodDescription(shippingMethod, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
