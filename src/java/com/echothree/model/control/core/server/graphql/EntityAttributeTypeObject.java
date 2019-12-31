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

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("entity attribute type object")
@GraphQLName("EntityAttributeType")
public class EntityAttributeTypeObject
        extends BaseEntityInstanceObject {
    
    private final EntityAttributeType entityAttributeType; // Always Present
    
    public EntityAttributeTypeObject(EntityAttributeType entityAttributeType) {
        super(entityAttributeType.getPrimaryKey());
        
        this.entityAttributeType = entityAttributeType;
    }
    
    @GraphQLField
    @GraphQLDescription("entity attribute type name")
    @GraphQLNonNull
    public String getEntityAttributeTypeName() {
        return entityAttributeType.getEntityAttributeTypeName();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return coreControl.getBestEntityAttributeTypeDescription(entityAttributeType, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
}
