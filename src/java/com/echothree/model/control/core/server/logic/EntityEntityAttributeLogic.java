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

import com.echothree.control.user.core.common.spec.EntityInstanceAttributeSpec;
import com.echothree.model.control.core.common.exception.UnknownEntityEntityAttributeException;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityEntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityEntityAttributeLogic
        extends BaseEntityAttributeValueLogic {

    protected EntityEntityAttributeLogic() {
        super();
    }

    public EntityInstance getEntityInstanceAttribute(final ExecutionErrorAccumulator eea,
            final EntityInstanceAttributeSpec entityInstanceAttributeSpec) {
        var entityRef = entityInstanceAttributeSpec.getEntityRefAttribute();
        var uuid = entityInstanceAttributeSpec.getUuidAttribute();

        return entityInstanceLogic.getEntityInstance(eea, entityRef, uuid, null);
    }

    public EntityEntityAttribute getEntityEntityAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var entityEntityAttribute = coreControl.getEntityEntityAttribute(entityAttribute, entityInstance, entityPermission);

        if(entityEntityAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityEntityAttributeException.class, eea, ExecutionErrors.UnknownEntityEntityAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName());
        }

        return entityEntityAttribute;
    }

    public EntityEntityAttribute getEntityEntityAttributeForUpdate(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityEntityAttribute(eea, entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }

    public void deleteEntityEntityAttribute(final ExecutionErrorAccumulator eea, final EntityEntityAttribute entityEntityAttribute,
            final BasePK deletedBy) {
        coreControl.deleteEntityEntityAttribute(entityEntityAttribute, deletedBy);
    }

}
