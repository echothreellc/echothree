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
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

public class BaseEntityClobAttributeObject
        implements BaseGraphQl, AttributeInterface {

    protected final EntityClobAttribute entityClobAttribute; // Always Present

    public BaseEntityClobAttributeObject(EntityClobAttribute entityClobAttribute) {
        this.entityClobAttribute = entityClobAttribute;
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityClobAttribute.getEntityAttribute(), entityClobAttribute.getEntityInstance()) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityInstanceAccess(env) ? new EntityInstanceObject(entityClobAttribute.getEntityInstance()) : null;
    }

    @GraphQLField
    @GraphQLDescription("language")
    public LanguageObject getLanguage(final DataFetchingEnvironment env) {
        return PartySecurityUtils.getHasLanguageAccess(env) ? new LanguageObject(entityClobAttribute.getLanguage()) : null;
    }

    @GraphQLField
    @GraphQLDescription("clob attribute")
    @GraphQLNonNull
    public String getClobAttribute() {
        return entityClobAttribute.getClobAttribute();
    }

    @GraphQLField
    @GraphQLDescription("mime type")
    public MimeTypeObject getMimeType(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasMimeTypeAccess(env) ? new MimeTypeObject(entityClobAttribute.getMimeType()) : null;
    }

}
