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
import com.echothree.model.control.core.server.control.ComponentVendorControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class ComponentVendorLogic
        extends BaseLogic {

    private ComponentVendorLogic() {
        super();
    }

    private static class ComponentVendorLogicHolder {
        static ComponentVendorLogic instance = new ComponentVendorLogic();
    }

    public static ComponentVendorLogic getInstance() {
        return ComponentVendorLogicHolder.instance;
    }

    public ComponentVendor getComponentVendorByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final EntityPermission entityPermission) {
        var componentVendorControl = Session.getModelController(ComponentVendorControl.class);
        var componentVendor = componentVendorControl.getComponentVendorByName(componentVendorName, entityPermission);

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
        var parameterCount = (componentVendorName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1:
                if(componentVendorName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ComponentVendor.name());

                    if(!eea.hasExecutionErrors()) {
                        var componentVendorControl = Session.getModelController(ComponentVendorControl.class);

                        componentVendor = componentVendorControl.getComponentVendorByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    componentVendor = getComponentVendorByName(eea, componentVendorName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
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
