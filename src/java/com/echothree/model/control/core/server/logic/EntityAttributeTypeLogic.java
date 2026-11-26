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

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.UnknownEntityAttributeTypeNameException;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EntityAttributeTypeLogic
        extends BaseLogic {

    @Inject
    protected EntityInstanceLogic entityInstanceLogic;

    protected EntityAttributeTypeLogic() {
        super();
    }

    public EntityAttributeType getEntityAttributeTypeByName(final ExecutionErrorAccumulator eea, final String entityAttributeTypeName) {
        var entityAttributeType = coreControl.getEntityAttributeTypeByName(entityAttributeTypeName);

        if(entityAttributeType == null) {
            handleExecutionError(UnknownEntityAttributeTypeNameException.class, eea, ExecutionErrors.UnknownEntityAttributeTypeName.name(), entityAttributeTypeName);
        }

        return entityAttributeType;
    }

    public EntityAttributeType getEntityAttributeTypeByUuid(final ExecutionErrorAccumulator eea, final String uuid, final EntityPermission entityPermission) {
        EntityAttributeType entityAttributeType = null;

        var entityInstance = entityInstanceLogic.getEntityInstance(eea, (String)null, uuid,
                ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityAttributeType.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            entityAttributeType = coreControl.getEntityAttributeTypeByEntityInstance(entityInstance, entityPermission);
        }

        return entityAttributeType;
    }

    public EntityAttributeType getEntityAttributeTypeByUuid(final ExecutionErrorAccumulator eea, final String uuid) {
        return getEntityAttributeTypeByUuid(eea, uuid, EntityPermission.READ_ONLY);
    }

    public EntityAttributeType getEntityAttributeTypeByUuidForUpdate(final ExecutionErrorAccumulator eea, final String uuid) {
        return getEntityAttributeTypeByUuid(eea, uuid, EntityPermission.READ_WRITE);
    }

}
