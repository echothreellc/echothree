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

package com.echothree.model.control.communication.server.logic;

import com.echothree.control.user.communication.common.spec.CommunicationEventPurposeUniversalSpec;
import com.echothree.model.control.communication.common.exception.UnknownCommunicationEventPurposeNameException;
import com.echothree.model.control.communication.common.exception.UnknownDefaultCommunicationEventPurposeException;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurpose;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CommunicationEventPurposeLogic
        extends BaseLogic {

    @Inject
    CommunicationControl communicationControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected CommunicationEventPurposeLogic() {
        super();
    }

    public CommunicationEventPurpose getCommunicationEventPurposeByName(final ExecutionErrorAccumulator eea, final String communicationEventPurposeName,
            final EntityPermission entityPermission) {
        var communicationEventPurpose = communicationControl.getCommunicationEventPurposeByName(communicationEventPurposeName, entityPermission);

        if(communicationEventPurpose == null) {
            handleExecutionError(UnknownCommunicationEventPurposeNameException.class, eea, ExecutionErrors.UnknownCommunicationEventPurposeName.name(), communicationEventPurposeName);
        }

        return communicationEventPurpose;
    }

    public CommunicationEventPurpose getCommunicationEventPurposeByName(final ExecutionErrorAccumulator eea, final String communicationEventPurposeName) {
        return getCommunicationEventPurposeByName(eea, communicationEventPurposeName, EntityPermission.READ_ONLY);
    }

    public CommunicationEventPurpose getCommunicationEventPurposeByNameForUpdate(final ExecutionErrorAccumulator eea, final String communicationEventPurposeName) {
        return getCommunicationEventPurposeByName(eea, communicationEventPurposeName, EntityPermission.READ_WRITE);
    }

    public CommunicationEventPurpose getCommunicationEventPurposeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final CommunicationEventPurposeUniversalSpec universalSpec, final boolean allowDefault, final EntityPermission entityPermission) {
        CommunicationEventPurpose communicationEventPurpose = null;
        var communicationEventPurposeName = universalSpec.getCommunicationEventPurposeName();
        var parameterCount = (communicationEventPurposeName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(parameterCount == 0 && allowDefault) {
            communicationEventPurpose = communicationControl.getDefaultCommunicationEventPurpose(entityPermission);

            if(communicationEventPurpose == null) {
                handleExecutionError(UnknownDefaultCommunicationEventPurposeException.class, eea,
                        ExecutionErrors.UnknownDefaultCommunicationEventPurpose.name());
            }
        } else if(parameterCount == 1) {
            if(communicationEventPurposeName == null) {
                var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.CommunicationEventPurpose.name());

                if(eea == null || !eea.hasExecutionErrors()) {
                    communicationEventPurpose = communicationControl.getCommunicationEventPurposeByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                communicationEventPurpose = getCommunicationEventPurposeByName(eea, communicationEventPurposeName, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return communicationEventPurpose;
    }

    public CommunicationEventPurpose getCommunicationEventPurposeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final CommunicationEventPurposeUniversalSpec universalSpec, final boolean allowDefault) {
        return getCommunicationEventPurposeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public CommunicationEventPurpose getCommunicationEventPurposeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final CommunicationEventPurposeUniversalSpec universalSpec, final boolean allowDefault) {
        return getCommunicationEventPurposeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
