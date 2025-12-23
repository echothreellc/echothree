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

package com.echothree.model.control.offer.server.logic;

import com.echothree.control.user.offer.common.spec.UseTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.offer.common.exception.CannotDeleteUseTypeInUseException;
import com.echothree.model.control.offer.common.exception.DuplicateUseTypeNameException;
import com.echothree.model.control.offer.common.exception.UnknownDefaultUseTypeException;
import com.echothree.model.control.offer.common.exception.UnknownUseTypeNameException;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.offer.server.control.UseTypeControl;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class UseTypeLogic
        extends BaseLogic {

    protected UseTypeLogic() {
        super();
    }

    public static UseTypeLogic getInstance() {
        return CDI.current().select(UseTypeLogic.class).get();
    }

    public UseType createUseType(final ExecutionErrorAccumulator eea, final String useTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var useTypeControl = Session.getModelController(UseTypeControl.class);
        var useType = useTypeControl.getUseTypeByName(useTypeName);

        if(useType == null) {
            useType = useTypeControl.createUseType(useTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                useTypeControl.createUseTypeDescription(useType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateUseTypeNameException.class, eea, ExecutionErrors.DuplicateUseTypeName.name(), useTypeName);
        }

        return useType;
    }

    public UseType getUseTypeByName(final ExecutionErrorAccumulator eea, final String useTypeName,
            final EntityPermission entityPermission) {
        var useTypeControl = Session.getModelController(UseTypeControl.class);
        var useType = useTypeControl.getUseTypeByName(useTypeName, entityPermission);

        if(useType == null) {
            handleExecutionError(UnknownUseTypeNameException.class, eea, ExecutionErrors.UnknownUseTypeName.name(), useTypeName);
        }

        return useType;
    }

    public UseType getUseTypeByName(final ExecutionErrorAccumulator eea, final String useTypeName) {
        return getUseTypeByName(eea, useTypeName, EntityPermission.READ_ONLY);
    }

    public UseType getUseTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String useTypeName) {
        return getUseTypeByName(eea, useTypeName, EntityPermission.READ_WRITE);
    }

    public UseType getUseTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final UseTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        UseType useType = null;
        var useTypeControl = Session.getModelController(UseTypeControl.class);
        var useTypeName = universalSpec.getUseTypeName();
        var parameterCount = (useTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    useType = useTypeControl.getDefaultUseType(entityPermission);

                    if(useType == null) {
                        handleExecutionError(UnknownDefaultUseTypeException.class, eea, ExecutionErrors.UnknownDefaultUseType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(useTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.UseType.name());

                    if(!eea.hasExecutionErrors()) {
                        useType = useTypeControl.getUseTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    useType = getUseTypeByName(eea, useTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return useType;
    }

    public UseType getUseTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final UseTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getUseTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public UseType getUseTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final UseTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getUseTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteUseType(final ExecutionErrorAccumulator eea, final UseType useType,
            final BasePK deletedBy) {
        var useControl = Session.getModelController(UseControl.class);

        if(useControl.countUsesByUseType(useType) == 0) {
            var useTypeControl = Session.getModelController(UseTypeControl.class);

            useTypeControl.deleteUseType(useType, deletedBy);
        } else {
            handleExecutionError(CannotDeleteUseTypeInUseException.class, eea, ExecutionErrors.CannotDeleteUseTypeInUse.name());
        }
    }

}
