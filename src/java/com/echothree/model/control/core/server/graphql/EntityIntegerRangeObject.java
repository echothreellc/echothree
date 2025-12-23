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
import com.echothree.model.data.core.server.entity.EntityIntegerRange;
import com.echothree.model.data.core.server.entity.EntityIntegerRangeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity integer range object")
@GraphQLName("EntityIntegerRange")
public class EntityIntegerRangeObject
        extends BaseEntityInstanceObject {
    
    private final EntityIntegerRange entityIntegerRange; // Always Present
    
    public EntityIntegerRangeObject(EntityIntegerRange entityIntegerRange) {
        super(entityIntegerRange.getPrimaryKey());
        
        this.entityIntegerRange = entityIntegerRange;
    }

    private EntityIntegerRangeDetail entityIntegerRangeDetail; // Optional, use getEntityIntegerRangeDetail()
    
    private EntityIntegerRangeDetail getEntityIntegerRangeDetail() {
        if(entityIntegerRangeDetail == null) {
            entityIntegerRangeDetail = entityIntegerRange.getLastDetail();
        }
        
        return entityIntegerRangeDetail;
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeAccess(env) ? new EntityAttributeObject(getEntityIntegerRangeDetail().getEntityAttribute(), null) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity integer range name")
    @GraphQLNonNull
    public String getEntityIntegerRangeName() {
        return getEntityIntegerRangeDetail().getEntityIntegerRangeName();
    }

    @GraphQLField
    @GraphQLDescription("minimum integer value")
    public Integer getMinimumIntegerValue() {
        return getEntityIntegerRangeDetail().getMinimumIntegerValue();
    }

    @GraphQLField
    @GraphQLDescription("maximum integer value")
    public Integer getMaximumIntegerValue() {
        return getEntityIntegerRangeDetail().getMaximumIntegerValue();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getEntityIntegerRangeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getEntityIntegerRangeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var coreControl = Session.getModelController(CoreControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return coreControl.getBestEntityIntegerRangeDescription(entityIntegerRange, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
