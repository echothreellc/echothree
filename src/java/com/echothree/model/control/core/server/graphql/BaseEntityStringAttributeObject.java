// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.party.server.graphql.LanguageObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

public class BaseEntityStringAttributeObject
        implements BaseGraphQl {

    protected final EntityStringAttribute entityStringAttribute; // Always Present

    protected BaseEntityStringAttributeObject(EntityStringAttribute entityStringAttribute) {
        this.entityStringAttribute = entityStringAttribute;
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityStringAttribute.getEntityAttribute(), entityStringAttribute.getEntityInstance()) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityInstanceAccess(env) ? new EntityInstanceObject(entityStringAttribute.getEntityInstance()) : null;
    }

    @GraphQLField
    @GraphQLDescription("language")
    public LanguageObject getLanguage(final DataFetchingEnvironment env) {
        return PartySecurityUtils.getHasLanguageAccess(env) ? new LanguageObject(entityStringAttribute.getLanguage()) : null;
    }

    @GraphQLField
    @GraphQLDescription("string attribute")
    @GraphQLNonNull
    public String getStringAttribute() {
        return entityStringAttribute.getStringAttribute();
    }

}
