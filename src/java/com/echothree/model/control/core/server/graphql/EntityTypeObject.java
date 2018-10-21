// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("entity type object")
@GraphQLName("EntityType")
public class EntityTypeObject
        extends BaseEntityInstanceObject {
    
    private final EntityType entityType; // Always Present
    
    public EntityTypeObject(EntityType entityType) {
        super(entityType.getPrimaryKey());
        
        this.entityType = entityType;
    }

    private EntityTypeDetail entityTypeDetail; // Optional, use getEntityTypeDetail()
    
    private EntityTypeDetail getEntityTypeDetail() {
        if(entityTypeDetail == null) {
            entityTypeDetail = entityType.getLastDetail();
        }
        
        return entityTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("entity type name")
    @GraphQLNonNull
    public String getEntityTypeName() {
        return getEntityTypeDetail().getEntityTypeName();
    }
    
    @GraphQLField
    @GraphQLDescription("component vendor")
    @GraphQLNonNull
    public ComponentVendorObject getComponentVendor() {
        return new ComponentVendorObject(getEntityTypeDetail().getComponentVendor());
    }
    
    @GraphQLField
    @GraphQLDescription("keep all history")
    @GraphQLNonNull
    public boolean getKeepAllHistory() {
        return getEntityTypeDetail().getKeepAllHistory();
    }
    
    @GraphQLField
    @GraphQLDescription("lock timeout")
    public Long getLockTimeout() {
        return getEntityTypeDetail().getLockTimeout();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getEntityTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return coreControl.getBestEntityTypeDescription(entityType, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
}
