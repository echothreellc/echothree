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

import com.echothree.model.control.core.common.exception.UnknownEntityBlobAttributeException;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.value.EntityBlobAttributeValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityBlobAttributeLogic
        extends BaseEntityAttributeValueLogic {

    protected EntityBlobAttributeLogic() {
        super();
    }

    public EntityBlobAttribute getEntityBlobAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance, Language language,
            EntityPermission entityPermission) {
        var entityBlobAttribute = coreControl.getEntityBlobAttribute(entityAttribute, entityInstance, language, entityPermission);

        if(entityBlobAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityBlobAttributeException.class, eea, ExecutionErrors.UnknownEntityBlobAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName(), language.getLanguageIsoName());
        }

        return entityBlobAttribute;
    }

    public EntityBlobAttribute getEntityBlobAttributeForUpdate(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute,
            EntityInstance entityInstance, Language language) {
        return getEntityBlobAttribute(eea, entityAttribute, entityInstance, language, EntityPermission.READ_WRITE);
    }

    public void updateEntityBlobAttributeFromValue(final ExecutionErrorAccumulator eea, final EntityBlobAttributeValue entityBlobAttributeValue,
            final BasePK updatedBy) {
        coreControl.updateEntityBlobAttributeFromValue(entityBlobAttributeValue, updatedBy);
    }

    public void deleteEntityBlobAttribute(final ExecutionErrorAccumulator eea, final EntityBlobAttribute entityBlobAttribute,
            final BasePK deletedBy) {
        coreControl.deleteEntityBlobAttribute(entityBlobAttribute, deletedBy);
    }

}
