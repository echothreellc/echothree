// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.search.common.form.GetVendorResultsForm;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.Collections;
import java.util.List;

@GraphQLDescription("vendor results object")
@GraphQLName("VendorResults")
public class VendorResultsObject
        extends BaseResultsObject<GetVendorResultsForm> {

    public VendorResultsObject() {
        super(SearchConstants.SearchKind_VENDOR);
    }

    @GraphQLField
    @GraphQLDescription("count")
    @GraphQLNonNull
    public int getCount(final DataFetchingEnvironment env) {
        return getCount(env);
    }
    
    @GraphQLField
    @GraphQLDescription("vendors")
    @GraphQLNonNull
    public List<VendorResultObject> getVendors(final DataFetchingEnvironment env) {
        List<VendorResultObject> objects = null;
        var userVisitSearch = getUserVisitSearch(env);

        if(userVisitSearch != null) {
            var vendorControl = Session.getModelController(VendorControl.class);

            objects = vendorControl.getVendorResultObjects(userVisitSearch);
        }
        
        return objects == null ? Collections.emptyList() : objects;
    }
    
}
