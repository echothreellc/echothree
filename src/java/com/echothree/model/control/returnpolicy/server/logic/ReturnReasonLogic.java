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

import com.echothree.control.user.returnpolicy.common.spec.ReturnReasonUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.returnpolicy.common.exception.UnknownDefaultReturnKindException;
import com.echothree.model.control.returnpolicy.common.exception.UnknownDefaultReturnReasonException;
import com.echothree.model.control.returnpolicy.common.exception.UnknownReturnReasonNameException;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ReturnReasonLogic
        extends BaseLogic {

    @Inject
    ReturnPolicyControl returnPolicyControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    ReturnKindLogic returnKindLogic;

    protected ReturnReasonLogic() {
        super();
    }

    public ReturnReason getReturnReasonByName(final ExecutionErrorAccumulator eea, final ReturnKind returnKind,
            final String returnReasonName, final EntityPermission entityPermission) {
        var returnReason = returnPolicyControl.getReturnReasonByName(returnKind, returnReasonName, entityPermission);

        if(returnReason == null) {
            handleExecutionError(UnknownReturnReasonNameException.class, eea, ExecutionErrors.UnknownReturnReasonName.name(),
                    returnKind.getLastDetail().getReturnKindName(), returnReasonName);
        }

        return returnReason;
    }

    public ReturnReason getReturnReasonByName(final ExecutionErrorAccumulator eea, final ReturnKind returnKind,
            final String returnReasonName) {
        return getReturnReasonByName(eea, returnKind, returnReasonName, EntityPermission.READ_ONLY);
    }

    public ReturnReason getReturnReasonByNameForUpdate(final ExecutionErrorAccumulator eea, final ReturnKind returnKind,
            final String returnReasonName) {
        return getReturnReasonByName(eea, returnKind, returnReasonName, EntityPermission.READ_WRITE);
    }

    public ReturnReason getReturnReasonByName(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnReasonName, final EntityPermission entityPermission) {
        var returnKind = returnKindLogic.getReturnKindByName(eea, returnKindName);
        ReturnReason returnReason = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            returnReason = getReturnReasonByName(eea, returnKind, returnReasonName, entityPermission);
        }

        return returnReason;
    }

    public ReturnReason getReturnReasonByName(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnReasonName) {
        return getReturnReasonByName(eea, returnKindName, returnReasonName, EntityPermission.READ_ONLY);
    }

    public ReturnReason getReturnReasonByNameForUpdate(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnReasonName) {
        return getReturnReasonByName(eea, returnKindName, returnReasonName, EntityPermission.READ_WRITE);
    }

    public ReturnReason getReturnReasonByUniversalSpec(final ExecutionErrorAccumulator eea, final ReturnReasonUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        ReturnReason returnReason = null;
        var returnKindName = universalSpec.getReturnKindName();
        var returnReasonName = universalSpec.getReturnReasonName();
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(possibleEntitySpecs == 0) {
            ReturnKind returnKind = null;

            if(returnKindName == null) {
                if(allowDefault) {
                    returnKind = returnPolicyControl.getDefaultReturnKind();

                    if(returnKind == null) {
                        handleExecutionError(UnknownDefaultReturnKindException.class, eea, ExecutionErrors.UnknownDefaultReturnKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                returnKind = returnKindLogic.getReturnKindByName(eea, returnKindName);
            }

            if(!hasExecutionErrors(eea)) {
                if(returnReasonName == null) {
                    if(allowDefault) {
                        returnReason = returnPolicyControl.getDefaultReturnReason(returnKind, entityPermission);

                        if(returnReason == null) {
                            handleExecutionError(UnknownDefaultReturnReasonException.class, eea, ExecutionErrors.UnknownDefaultReturnReason.name(),
                                    returnKind.getLastDetail().getReturnKindName());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    returnReason = getReturnReasonByName(eea, returnKind, returnReasonName, entityPermission);
                }
            }
        } else if(returnKindName == null && returnReasonName == null && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.ReturnReason.name());

            if(!hasExecutionErrors(eea)) {
                returnReason = returnPolicyControl.getReturnReasonByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return returnReason;
    }

    public ReturnReason getReturnReasonByUniversalSpec(final ExecutionErrorAccumulator eea, final ReturnReasonUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getReturnReasonByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ReturnReason getReturnReasonByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final ReturnReasonUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getReturnReasonByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
