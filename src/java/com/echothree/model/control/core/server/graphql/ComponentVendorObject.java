// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.core.server.command.GetEntityTypeCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureTypeCommand;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.ComponentVendorDetail;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import java.util.ArrayList;
import java.util.List;
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

    private Boolean hasEntityTypeAccess;

    private boolean getHasEntityTypeAccess(final DataFetchingEnvironment env) {
        if(hasEntityTypeAccess == null) {
            GraphQlContext context = env.getContext();
            var baseSingleEntityCommand = new GetEntityTypeCommand(context.getUserVisitPK(), null);

            baseSingleEntityCommand.security();

            hasEntityTypeAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }

        return hasEntityTypeAccess;
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
    public List<EntityTypeObject> getEntityTypes(final DataFetchingEnvironment env) {
        if(getHasEntityTypeAccess(env)) {
            var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            var entities = coreControl.getEntityTypesByComponentVendor(componentVendor);
            var entityTypes = entities.stream().map(EntityTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

            return entityTypes;
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("entity type count")
    public Long getEntityTypeCount(final DataFetchingEnvironment env) {
        if(getHasEntityTypeAccess(env)) {
            var coreControl = (CoreControl)Session.getModelController(CoreControl.class);

            return coreControl.countEntityTypesByComponentVendor(componentVendor);
        } else {
            return null;
        }
    }
    
}
