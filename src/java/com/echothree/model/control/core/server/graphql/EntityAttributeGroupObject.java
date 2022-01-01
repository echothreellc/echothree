// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.List;

@GraphQLDescription("font weight object")
@GraphQLName("EntityAttributeGroup")
public class EntityAttributeGroupObject
        extends BaseEntityInstanceObject {
    
    private final EntityAttributeGroup entityAttributeGroup; // Always Present
    private final EntityInstance entityInstance;
    
    public EntityAttributeGroupObject(EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        super(entityAttributeGroup.getPrimaryKey());

        this.entityAttributeGroup = entityAttributeGroup;
        this.entityInstance = entityInstance;
    }

    private EntityAttributeGroupDetail entityAttributeGroupDetail; // Optional, use getEntityAttributeGroupDetail()
    
    private EntityAttributeGroupDetail getEntityAttributeGroupDetail() {
        if(entityAttributeGroupDetail == null) {
            entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();
        }
        
        return entityAttributeGroupDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("entity attribute group name")
    @GraphQLNonNull
    public String getEntityAttributeGroupName() {
        return getEntityAttributeGroupDetail().getEntityAttributeGroupName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getEntityAttributeGroupDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getEntityAttributeGroupDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var coreControl = Session.getModelController(CoreControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return coreControl.getBestEntityAttributeGroupDescription(entityAttributeGroup, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("entity attributes")
    public List<EntityAttributeObject> getEntityAttributes() {
        if(entityInstance != null) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entities = coreControl.getEntityAttributesByEntityAttributeGroupAndEntityType(entityAttributeGroup,
                    entityInstance.getEntityType());
            var aspectEntityAttributes = new ArrayList<EntityAttributeObject>(entities.size());

            entities.forEach((entity) -> aspectEntityAttributes.add(new EntityAttributeObject(entity, entityInstance)));

            return aspectEntityAttributes;
        } else {
            return null;
        }
    }
    
}
