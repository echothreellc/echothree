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

import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.party.server.graphql.LanguageObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.core.server.entity.EntityStringDefault;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

public class BaseEntityStringDefaultObject
        implements BaseGraphQl {

    protected final EntityStringDefault entityStringDefault; // Always Present

    protected BaseEntityStringDefaultObject(EntityStringDefault entityStringDefault) {
        this.entityStringDefault = entityStringDefault;
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityStringDefault.getEntityAttribute(), null) : null;
    }

    @GraphQLField
    @GraphQLDescription("language")
    public LanguageObject getLanguage(final DataFetchingEnvironment env) {
        return PartySecurityUtils.getHasLanguageAccess(env) ? new LanguageObject(entityStringDefault.getLanguage()) : null;
    }

    @GraphQLField
    @GraphQLDescription("string attribute")
    @GraphQLNonNull
    public String getStringAttribute() {
        return entityStringDefault.getStringAttribute();
    }

}
