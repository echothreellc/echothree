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

import com.echothree.control.user.core.common.spec.AppearanceUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.UnknownAppearanceNameException;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class AppearanceLogic
        extends BaseLogic {

    @Inject
    protected AppearanceControl appearanceControl;

    protected AppearanceLogic() {
        super();
    }

    public static AppearanceLogic getInstance() {
        return CDI.current().select(AppearanceLogic.class).get();
    }

    public Appearance getAppearanceByName(final ExecutionErrorAccumulator eea, final String appearanceName,
            final EntityPermission entityPermission) {
        var appearance = appearanceControl.getAppearanceByName(appearanceName, entityPermission);

        if(appearance == null) {
            handleExecutionError(UnknownAppearanceNameException.class, eea, ExecutionErrors.UnknownAppearanceName.name(), appearanceName);
        }

        return appearance;
    }

    public Appearance getAppearanceByName(final ExecutionErrorAccumulator eea, final String appearanceName) {
        return getAppearanceByName(eea, appearanceName, EntityPermission.READ_ONLY);
    }

    public Appearance getAppearanceByNameForUpdate(final ExecutionErrorAccumulator eea, final String appearanceName) {
        return getAppearanceByName(eea, appearanceName, EntityPermission.READ_WRITE);
    }

    public Appearance getAppearanceByUniversalSpec(final ExecutionErrorAccumulator eea,
            final AppearanceUniversalSpec universalSpec, final EntityPermission entityPermission) {
        Appearance appearance = null;
        var appearanceName = universalSpec.getAppearanceName();
        var parameterCount = (appearanceName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(appearanceName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Appearance.name());

                    if(!eea.hasExecutionErrors()) {
                        appearance = appearanceControl.getAppearanceByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    appearance = getAppearanceByName(eea, appearanceName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return appearance;
    }

    public Appearance getAppearanceByUniversalSpec(final ExecutionErrorAccumulator eea,
            final AppearanceUniversalSpec universalSpec) {
        return getAppearanceByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Appearance getAppearanceByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final AppearanceUniversalSpec universalSpec) {
        return getAppearanceByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }
    
}
