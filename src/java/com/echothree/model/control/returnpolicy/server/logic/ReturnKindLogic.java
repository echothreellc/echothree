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

package com.echothree.model.control.returnpolicy.server.logic;

import com.echothree.control.user.returnpolicy.common.spec.ReturnKindUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.returnpolicy.common.exception.DuplicateReturnKindNameException;
import com.echothree.model.control.returnpolicy.common.exception.UnknownDefaultReturnKindException;
import com.echothree.model.control.returnpolicy.common.exception.UnknownReturnKindNameException;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
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
public class ReturnKindLogic
        extends BaseLogic {

    protected ReturnKindLogic() {
        super();
    }

    public static ReturnKindLogic getInstance() {
        return CDI.current().select(ReturnKindLogic.class).get();
    }

    public ReturnKind createReturnKind(final ExecutionErrorAccumulator eea, final String returnKindName,
            final SequenceType returnSequenceType, final Boolean isDefault, final Integer sortOrder, final Language language,
            final String description, final BasePK createdBy) {
        var returnControl = Session.getModelController(ReturnPolicyControl.class);
        var returnKind = returnControl.getReturnKindByName(returnKindName);

        if(returnKind == null) {
            returnKind = returnControl.createReturnKind(returnKindName, returnSequenceType,
                    isDefault, sortOrder, createdBy);

            if(description != null) {
                returnControl.createReturnKindDescription(returnKind, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateReturnKindNameException.class, eea, ExecutionErrors.DuplicateReturnKindName.name(), returnKindName);
        }

        return returnKind;
    }

    public ReturnKind getReturnKindByName(final ExecutionErrorAccumulator eea, final String returnKindName,
            final EntityPermission entityPermission) {
        var returnControl = Session.getModelController(ReturnPolicyControl.class);
        var returnKind = returnControl.getReturnKindByName(returnKindName, entityPermission);

        if(returnKind == null) {
            handleExecutionError(UnknownReturnKindNameException.class, eea, ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }

        return returnKind;
    }

    public ReturnKind getReturnKindByName(final ExecutionErrorAccumulator eea, final String returnKindName) {
        return getReturnKindByName(eea, returnKindName, EntityPermission.READ_ONLY);
    }

    public ReturnKind getReturnKindByNameForUpdate(final ExecutionErrorAccumulator eea, final String returnKindName) {
        return getReturnKindByName(eea, returnKindName, EntityPermission.READ_WRITE);
    }

    public ReturnKind getReturnKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ReturnKindUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ReturnKind returnKind = null;
        var returnControl = Session.getModelController(ReturnPolicyControl.class);
        var returnKindName = universalSpec.getReturnKindName();
        var parameterCount = (returnKindName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    returnKind = returnControl.getDefaultReturnKind(entityPermission);

                    if(returnKind == null) {
                        handleExecutionError(UnknownDefaultReturnKindException.class, eea, ExecutionErrors.UnknownDefaultReturnKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(returnKindName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ReturnKind.name());

                    if(!eea.hasExecutionErrors()) {
                        returnKind = returnControl.getReturnKindByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    returnKind = getReturnKindByName(eea, returnKindName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return returnKind;
    }

    public ReturnKind getReturnKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ReturnKindUniversalSpec universalSpec, boolean allowDefault) {
        return getReturnKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ReturnKind getReturnKindByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ReturnKindUniversalSpec universalSpec, boolean allowDefault) {
        return getReturnKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
