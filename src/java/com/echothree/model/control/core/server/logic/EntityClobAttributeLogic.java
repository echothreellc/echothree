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

package com.echothree.model.control.core.server.logic;

import com.echothree.model.control.core.common.exception.DuplicateEntityClobAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityClobAttributeException;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityClobAttributeLogic
        extends BaseEntityAttributeValueLogic {

    protected EntityClobAttributeLogic() {
        super();
    }

    public EntityClobAttribute createEntityClobAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Language language, final String clobAttribute, final MimeType mimeType,
            final BasePK createdBy) {
        EntityClobAttribute entityClobAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityClobAttribute = coreControl.getEntityClobAttribute(entityAttribute, entityInstance, language);

            if(entityClobAttribute == null) {
                coreControl.createEntityClobAttribute(entityAttribute, entityInstance, language, clobAttribute, mimeType, createdBy);
            } else {
                handleExecutionError(DuplicateEntityClobAttributeException.class, eea, ExecutionErrors.DuplicateEntityClobAttribute.name(),
                        entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName(),
                        language.getLanguageIsoName());
            }
        }

        return entityClobAttribute;
    }

    public EntityClobAttribute getEntityClobAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance, Language language,
            EntityPermission entityPermission) {
        var entityClobAttribute = coreControl.getEntityClobAttribute(entityAttribute, entityInstance, language, entityPermission);

        if(entityClobAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityClobAttributeException.class, eea, ExecutionErrors.UnknownEntityClobAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName(), language.getLanguageIsoName());
        }

        return entityClobAttribute;
    }

    public EntityClobAttribute getEntityClobAttributeForUpdate(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute,
            EntityInstance entityInstance, Language language) {
        return getEntityClobAttribute(eea, entityAttribute, entityInstance, language, EntityPermission.READ_WRITE);
    }

}
