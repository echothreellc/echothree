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

package com.echothree.model.control.core.server.logic;

import com.echothree.control.user.core.common.spec.ComponentVendorUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.UnknownComponentVendorNameException;
import com.echothree.model.control.core.server.control.ComponentControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class ComponentVendorLogic
        extends BaseLogic {

    @Inject
    ComponentControl componentControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected ComponentVendorLogic() {
        super();
    }

    public static ComponentVendorLogic getInstance() {
        return CDI.current().select(ComponentVendorLogic.class).get();
    }

    public ComponentVendor getComponentVendorByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final EntityPermission entityPermission) {
        var componentVendor = componentControl.getComponentVendorByName(componentVendorName, entityPermission);

        if(componentVendor == null) {
            handleExecutionError(UnknownComponentVendorNameException.class, eea, ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }

        return componentVendor;
    }

    public ComponentVendor getComponentVendorByName(final ExecutionErrorAccumulator eea, final String componentVendorName) {
        return getComponentVendorByName(eea, componentVendorName, EntityPermission.READ_ONLY);
    }

    public ComponentVendor getComponentVendorByNameForUpdate(final ExecutionErrorAccumulator eea, final String componentVendorName) {
        return getComponentVendorByName(eea, componentVendorName, EntityPermission.READ_WRITE);
    }

    public ComponentVendor getComponentVendorByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ComponentVendorUniversalSpec universalSpec, final EntityPermission entityPermission) {
        ComponentVendor componentVendor = null;
        var componentVendorName = universalSpec.getComponentVendorName();
        var parameterCount = (componentVendorName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(componentVendorName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ComponentVendor.name());

                    if(!eea.hasExecutionErrors()) {
                        componentVendor = componentControl.getComponentVendorByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    componentVendor = getComponentVendorByName(eea, componentVendorName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return componentVendor;
    }

    public ComponentVendor getComponentVendorByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ComponentVendorUniversalSpec universalSpec) {
        return getComponentVendorByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public ComponentVendor getComponentVendorByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ComponentVendorUniversalSpec universalSpec) {
        return getComponentVendorByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
