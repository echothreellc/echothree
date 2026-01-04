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

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.EntityLongRange;
import com.echothree.model.data.core.server.entity.EntityLongRangeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity long range object")
@GraphQLName("EntityLongRange")
public class EntityLongRangeObject
        extends BaseEntityInstanceObject {
    
    private final EntityLongRange entityLongRange; // Always Present
    
    public EntityLongRangeObject(EntityLongRange entityLongRange) {
        super(entityLongRange.getPrimaryKey());
        
        this.entityLongRange = entityLongRange;
    }

    private EntityLongRangeDetail entityLongRangeDetail; // Optional, use getEntityLongRangeDetail()
    
    private EntityLongRangeDetail getEntityLongRangeDetail() {
        if(entityLongRangeDetail == null) {
            entityLongRangeDetail = entityLongRange.getLastDetail();
        }
        
        return entityLongRangeDetail;
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeAccess(env) ? new EntityAttributeObject(getEntityLongRangeDetail().getEntityAttribute(), null) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity long range name")
    @GraphQLNonNull
    public String getEntityLongRangeName() {
        return getEntityLongRangeDetail().getEntityLongRangeName();
    }

    @GraphQLField
    @GraphQLDescription("minimum long value")
    public Long getMinimumLongValue() {
        return getEntityLongRangeDetail().getMinimumLongValue();
    }

    @GraphQLField
    @GraphQLDescription("maximum long value")
    public Long getMaximumLongValue() {
        return getEntityLongRangeDetail().getMaximumLongValue();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getEntityLongRangeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getEntityLongRangeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var coreControl = Session.getModelController(CoreControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return coreControl.getBestEntityLongRangeDescription(entityLongRange, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
