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

import com.echothree.model.control.returnpolicy.common.exception.UnknownReturnReasonTypeException;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ReturnReasonTypeLogic
        extends BaseLogic {

    @Inject
    ReturnPolicyControl returnPolicyControl;

    @Inject
    ReturnKindLogic returnKindLogic;

    @Inject
    ReturnReasonLogic returnReasonLogic;

    @Inject
    ReturnTypeLogic returnTypeLogic;

    protected ReturnReasonTypeLogic() {
        super();
    }

    public ReturnReasonType getReturnReasonType(final ExecutionErrorAccumulator eea, final ReturnReason returnReason,
            final ReturnType returnType, final EntityPermission entityPermission) {
        var returnReasonType = returnPolicyControl.getReturnReasonType(returnReason, returnType, entityPermission);

        if(returnReasonType == null) {
            var returnReasonDetail = returnReason.getLastDetail();

            handleExecutionError(UnknownReturnReasonTypeException.class, eea, ExecutionErrors.UnknownReturnReasonType.name(),
                    returnReasonDetail.getReturnKind().getLastDetail().getReturnKindName(), returnReasonDetail.getReturnReasonName(),
                    returnType.getLastDetail().getReturnTypeName());
        }

        return returnReasonType;
    }

    public ReturnReasonType getReturnReasonType(final ExecutionErrorAccumulator eea, final ReturnReason returnReason,
            final ReturnType returnType) {
        return getReturnReasonType(eea, returnReason, returnType, EntityPermission.READ_ONLY);
    }

    public ReturnReasonType getReturnReasonTypeForUpdate(final ExecutionErrorAccumulator eea, final ReturnReason returnReason,
            final ReturnType returnType) {
        return getReturnReasonType(eea, returnReason, returnType, EntityPermission.READ_WRITE);
    }

    public ReturnReasonType getReturnReasonType(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnReasonName, final String returnTypeName, final EntityPermission entityPermission) {
        var returnKind = returnKindLogic.getReturnKindByName(eea, returnKindName);
        ReturnReasonType returnReasonType = null;

        if(!hasExecutionErrors(eea)) {
            var returnReason = returnReasonLogic.getReturnReasonByName(eea, returnKind, returnReasonName);
            var returnType = returnTypeLogic.getReturnTypeByName(eea, returnKind, returnTypeName);

            if(!hasExecutionErrors(eea)) {
                returnReasonType = getReturnReasonType(eea, returnReason, returnType, entityPermission);
            }
        }

        return returnReasonType;
    }

    public ReturnReasonType getReturnReasonType(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnReasonName, final String returnTypeName) {
        return getReturnReasonType(eea, returnKindName, returnReasonName, returnTypeName, EntityPermission.READ_ONLY);
    }

    public ReturnReasonType getReturnReasonTypeForUpdate(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnReasonName, final String returnTypeName) {
        return getReturnReasonType(eea, returnKindName, returnReasonName, returnTypeName, EntityPermission.READ_WRITE);
    }

}
