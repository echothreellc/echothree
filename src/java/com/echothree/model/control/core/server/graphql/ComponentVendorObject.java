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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.data.core.common.EntityTypeConstants;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.ComponentVendorDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

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

    @GraphQLField
    @GraphQLDescription("entity types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<EntityTypeObject> getEntityTypes(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasEntityTypesAccess(env)) {
            var entityTypeControl = Session.getModelController(EntityTypeControl.class);
            var totalCount = entityTypeControl.countEntityTypesByComponentVendor(componentVendor);

            try(var objectLimiter = new ObjectLimiter(env, EntityTypeConstants.COMPONENT_VENDOR_NAME, EntityTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = entityTypeControl.getEntityTypesByComponentVendor(componentVendor);
                var entityTypes = entities.stream().map(EntityTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, entityTypes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
