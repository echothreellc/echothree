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

import com.echothree.control.user.core.server.command.GetEntityAttributeCommand;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemDetail;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("entity list item object")
@GraphQLName("EntityListItem")
public class EntityListItemObject
        extends BaseEntityInstanceObject {
    
    private final EntityListItem entityListItem; // Always Present
    
    public EntityListItemObject(EntityListItem entityListItem) {
        super(entityListItem.getPrimaryKey());
        
        this.entityListItem = entityListItem;
    }

    private EntityListItemDetail entityListItemDetail; // Optional, use getEntityListItemDetail()
    
    private EntityListItemDetail getEntityListItemDetail() {
        if(entityListItemDetail == null) {
            entityListItemDetail = entityListItem.getLastDetail();
        }
        
        return entityListItemDetail;
    }
    
    private Boolean hasEntityAttributeAccess;
    
    private boolean getHasEntityAttributeAccess(final DataFetchingEnvironment env) {
        if(hasEntityAttributeAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetEntityAttributeCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasEntityAttributeAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasEntityAttributeAccess;
    }
        
    @GraphQLField
    @GraphQLDescription("entity type name")
    @GraphQLNonNull
    public String getEntityListItemName() {
        return getEntityListItemDetail().getEntityListItemName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getEntityListItemDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getEntityListItemDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return coreControl.getBestEntityListItemDescription(entityListItem, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return getHasEntityAttributeAccess(env) ? new EntityAttributeObject(getEntityListItemDetail().getEntityAttribute(), null) : null;
    }
    
}
