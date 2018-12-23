// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.ComponentVendorDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("component vendor object")
@GraphQLName("ComponentVendor")
public class ComponentVendorObject
        extends BaseEntityInstanceObject {
    
    private final ComponentVendor componentVendor; // Always Present
    
    public ComponentVendorObject(ComponentVendor componentVendor) {
        super(componentVendor.getPrimaryKey());
        
        this.componentVendor = componentVendor;
    }

    private ComponentVendorDetail componentVendorDetail; // Optional, use getComponentVendorDetail()
    
    private ComponentVendorDetail getComponentVendorDetail() {
        if(componentVendorDetail == null) {
            componentVendorDetail = componentVendor.getLastDetail();
        }
        
        return componentVendorDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("component vendor name")
    @GraphQLNonNull
    public String getComponentVendorName() {
        return getComponentVendorDetail().getComponentVendorName();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        return getComponentVendorDetail().getDescription();
    }
    
}
