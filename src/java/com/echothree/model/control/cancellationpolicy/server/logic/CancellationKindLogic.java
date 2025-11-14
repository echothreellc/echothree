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

package com.echothree.model.control.cancellationpolicy.server.logic;

import com.echothree.control.user.cancellationpolicy.common.spec.CancellationKindUniversalSpec;
import com.echothree.model.control.cancellationpolicy.common.exception.DuplicateCancellationKindNameException;
import com.echothree.model.control.cancellationpolicy.common.exception.UnknownCancellationKindNameException;
import com.echothree.model.control.cancellationpolicy.common.exception.UnknownDefaultCancellationKindException;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class CancellationKindLogic
        extends BaseLogic {

    protected CancellationKindLogic() {
        super();
    }

    public static CancellationKindLogic getInstance() {
        return CDI.current().select(CancellationKindLogic.class).get();
    }

    public CancellationKind createCancellationKind(final ExecutionErrorAccumulator eea, final String cancellationKindName,
            final SequenceType cancellationSequenceType, final Boolean isDefault, final Integer sortOrder, final Language language,
            final String description, final BasePK createdBy) {
        var cancellationControl = Session.getModelController(CancellationPolicyControl.class);
        var cancellationKind = cancellationControl.getCancellationKindByName(cancellationKindName);

        if(cancellationKind == null) {
            cancellationKind = cancellationControl.createCancellationKind(cancellationKindName, cancellationSequenceType,
                    isDefault, sortOrder, createdBy);

            if(description != null) {
                cancellationControl.createCancellationKindDescription(cancellationKind, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateCancellationKindNameException.class, eea, ExecutionErrors.DuplicateCancellationKindName.name(), cancellationKindName);
        }

        return cancellationKind;
    }

    public CancellationKind getCancellationKindByName(final ExecutionErrorAccumulator eea, final String cancellationKindName,
            final EntityPermission entityPermission) {
        var cancellationControl = Session.getModelController(CancellationPolicyControl.class);
        var cancellationKind = cancellationControl.getCancellationKindByName(cancellationKindName, entityPermission);

        if(cancellationKind == null) {
            handleExecutionError(UnknownCancellationKindNameException.class, eea, ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
        }

        return cancellationKind;
    }

    public CancellationKind getCancellationKindByName(final ExecutionErrorAccumulator eea, final String cancellationKindName) {
        return getCancellationKindByName(eea, cancellationKindName, EntityPermission.READ_ONLY);
    }

    public CancellationKind getCancellationKindByNameForUpdate(final ExecutionErrorAccumulator eea, final String cancellationKindName) {
        return getCancellationKindByName(eea, cancellationKindName, EntityPermission.READ_WRITE);
    }

    public CancellationKind getCancellationKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final CancellationKindUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        CancellationKind cancellationKind = null;
        var cancellationControl = Session.getModelController(CancellationPolicyControl.class);
        var cancellationKindName = universalSpec.getCancellationKindName();
        var parameterCount = (cancellationKindName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    cancellationKind = cancellationControl.getDefaultCancellationKind(entityPermission);

                    if(cancellationKind == null) {
                        handleExecutionError(UnknownDefaultCancellationKindException.class, eea, ExecutionErrors.UnknownDefaultCancellationKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(cancellationKindName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.CancellationKind.name());

                    if(!eea.hasExecutionErrors()) {
                        cancellationKind = cancellationControl.getCancellationKindByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    cancellationKind = getCancellationKindByName(eea, cancellationKindName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return cancellationKind;
    }

    public CancellationKind getCancellationKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final CancellationKindUniversalSpec universalSpec, boolean allowDefault) {
        return getCancellationKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public CancellationKind getCancellationKindByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final CancellationKindUniversalSpec universalSpec, boolean allowDefault) {
        return getCancellationKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
