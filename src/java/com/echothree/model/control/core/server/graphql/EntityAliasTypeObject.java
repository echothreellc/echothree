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

import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAliasTypeDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity alias type object")
@GraphQLName("EntityAliasType")
public class EntityAliasTypeObject
        extends BaseEntityInstanceObject {
    
    private final EntityAliasType entityAliasType; // Always Present
    private final EntityInstance entityInstance; // Optional
    
    public EntityAliasTypeObject(EntityAliasType entityAliasType, EntityInstance entityInstance) {
        super(entityAliasType.getPrimaryKey());
        
        this.entityAliasType = entityAliasType;
        this.entityInstance = entityInstance;
    }

    private EntityAliasTypeDetail entityAliasTypeDetail; // Optional, use getEntityAliasTypeDetail()
    
    private EntityAliasTypeDetail getEntityAliasTypeDetail() {
        if(entityAliasTypeDetail == null) {
            entityAliasTypeDetail = entityAliasType.getLastDetail();
        }
        
        return entityAliasTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("entity type")
    public EntityTypeObject getEntityType(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityTypeAccess(env) ? new EntityTypeObject(getEntityAliasTypeDetail().getEntityType()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("entity alias type name")
    @GraphQLNonNull
    public String getEntityAliasTypeName() {
        return getEntityAliasTypeDetail().getEntityAliasTypeName();
    }

    @GraphQLField
    @GraphQLDescription("validation pattern")
    public String getValidationPattern() {
        return getEntityAliasTypeDetail().getValidationPattern();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getEntityAliasTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getEntityAliasTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var entityAliasControl = Session.getModelController(EntityAliasControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return entityAliasControl.getBestEntityAliasTypeDescription(entityAliasType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
    @GraphQLField
    @GraphQLDescription("entity alias")
    public EntityAliasObject getEntityAlias(final DataFetchingEnvironment env) {
        EntityAliasObject entityAliasObject = null;

        if(entityInstance != null && CoreSecurityUtils.getHasEntityAliasAccess(env)) {
            var entityAliasControl = Session.getModelController(EntityAliasControl.class);
            var entityAlias = entityAliasControl.getEntityAlias(entityInstance, entityAliasType);

            if(entityAlias != null) {
                entityAliasObject = new EntityAliasObject(entityAlias);
            }
        }

        return entityAliasObject;
    }

}
