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

import com.echothree.control.user.offer.common.spec.UseNameElementUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.offer.common.exception.DuplicateUseNameElementNameException;
import com.echothree.model.control.offer.common.exception.UnknownUseNameElementNameException;
import com.echothree.model.control.offer.server.control.UseNameElementControl;
import com.echothree.model.data.offer.server.entity.UseNameElement;
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
public class UseNameElementLogic
        extends BaseLogic {

    protected UseNameElementLogic() {
        super();
    }

    public static UseNameElementLogic getInstance() {
        return CDI.current().select(UseNameElementLogic.class).get();
    }

    public UseNameElement createUseNameElement(final ExecutionErrorAccumulator eea, final String useNameElementName,
            final Integer offset, final Integer length, final String validationPattern, final Language language, final String description,
            final BasePK createdBy) {
        var useNameElementControl = Session.getModelController(UseNameElementControl.class);
        var useNameElement = useNameElementControl.getUseNameElementByName(useNameElementName);

        if(useNameElement == null) {
            useNameElement = useNameElementControl.createUseNameElement(useNameElementName, offset, length, validationPattern,
                    createdBy);

            if(description != null) {
                useNameElementControl.createUseNameElementDescription(useNameElement, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateUseNameElementNameException.class, eea, ExecutionErrors.DuplicateUseNameElementName.name(),
                    useNameElementName);
        }

        return useNameElement;
    }

    public UseNameElement getUseNameElementByName(final ExecutionErrorAccumulator eea, final String useNameElementName,
            final EntityPermission entityPermission) {
        var useNameElementControl = Session.getModelController(UseNameElementControl.class);
        var useNameElement = useNameElementControl.getUseNameElementByName(useNameElementName, entityPermission);

        if(useNameElement == null) {
            handleExecutionError(UnknownUseNameElementNameException.class, eea, ExecutionErrors.UnknownUseNameElementName.name(), useNameElementName);
        }

        return useNameElement;
    }

    public UseNameElement getUseNameElementByName(final ExecutionErrorAccumulator eea, final String useNameElementName) {
        return getUseNameElementByName(eea, useNameElementName, EntityPermission.READ_ONLY);
    }

    public UseNameElement getUseNameElementByNameForUpdate(final ExecutionErrorAccumulator eea, final String useNameElementName) {
        return getUseNameElementByName(eea, useNameElementName, EntityPermission.READ_WRITE);
    }

    public UseNameElement getUseNameElementByUniversalSpec(final ExecutionErrorAccumulator eea,
            final UseNameElementUniversalSpec universalSpec, final EntityPermission entityPermission) {
        var useNameElementControl = Session.getModelController(UseNameElementControl.class);
        var useNameElementName = universalSpec.getUseNameElementName();
        var parameterCount = (useNameElementName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        UseNameElement useNameElement = null;

        if(parameterCount == 1) {
            if(useNameElementName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.UseNameElement.name());

                if(!eea.hasExecutionErrors()) {
                    useNameElement = useNameElementControl.getUseNameElementByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                useNameElement = getUseNameElementByName(eea, useNameElementName, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return useNameElement;
    }

    public UseNameElement getUseNameElementByUniversalSpec(final ExecutionErrorAccumulator eea,
            final UseNameElementUniversalSpec universalSpec) {
        return getUseNameElementByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public UseNameElement getUseNameElementByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final UseNameElementUniversalSpec universalSpec) {
        return getUseNameElementByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public void deleteUseNameElement(final ExecutionErrorAccumulator eea, final UseNameElement useNameElement,
            final BasePK deletedBy) {
        var useNameElementControl = Session.getModelController(UseNameElementControl.class);

        useNameElementControl.deleteUseNameElement(useNameElement, deletedBy);
    }

}
