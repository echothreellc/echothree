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

import com.echothree.model.control.returnpolicy.common.exception.UnknownReturnPolicyReasonException;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ReturnPolicyReasonLogic
        extends BaseLogic {

    @Inject
    ReturnPolicyControl returnPolicyControl;

    @Inject
    ReturnKindLogic returnKindLogic;

    @Inject
    ReturnPolicyLogic returnPolicyLogic;

    @Inject
    ReturnReasonLogic returnReasonLogic;

    protected ReturnPolicyReasonLogic() {
        super();
    }

    public ReturnPolicyReason getReturnPolicyReason(final ExecutionErrorAccumulator eea, final ReturnPolicy returnPolicy,
            final ReturnReason returnReason, final EntityPermission entityPermission) {
        var returnPolicyReason = returnPolicyControl.getReturnPolicyReason(returnPolicy, returnReason, entityPermission);

        if(returnPolicyReason == null) {
            var returnPolicyDetail = returnPolicy.getLastDetail();

            handleExecutionError(UnknownReturnPolicyReasonException.class, eea, ExecutionErrors.UnknownReturnPolicyReason.name(),
                    returnPolicyDetail.getReturnKind().getLastDetail().getReturnKindName(), returnPolicyDetail.getReturnPolicyName(),
                    returnReason.getLastDetail().getReturnReasonName());
        }

        return returnPolicyReason;
    }

    public ReturnPolicyReason getReturnPolicyReason(final ExecutionErrorAccumulator eea, final ReturnPolicy returnPolicy,
            final ReturnReason returnReason) {
        return getReturnPolicyReason(eea, returnPolicy, returnReason, EntityPermission.READ_ONLY);
    }

    public ReturnPolicyReason getReturnPolicyReasonForUpdate(final ExecutionErrorAccumulator eea, final ReturnPolicy returnPolicy,
            final ReturnReason returnReason) {
        return getReturnPolicyReason(eea, returnPolicy, returnReason, EntityPermission.READ_WRITE);
    }

    public ReturnPolicyReason getReturnPolicyReason(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnPolicyName, final String returnReasonName, final EntityPermission entityPermission) {
        var returnKind = returnKindLogic.getReturnKindByName(eea, returnKindName);
        ReturnPolicyReason returnPolicyReason = null;

        if(!hasExecutionErrors(eea)) {
            var returnPolicy = returnPolicyLogic.getReturnPolicyByName(eea, returnKind, returnPolicyName);
            var returnReason = returnReasonLogic.getReturnReasonByName(eea, returnKind, returnReasonName);

            if(!hasExecutionErrors(eea)) {
                returnPolicyReason = getReturnPolicyReason(eea, returnPolicy, returnReason, entityPermission);
            }
        }

        return returnPolicyReason;
    }

    public ReturnPolicyReason getReturnPolicyReason(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnPolicyName, final String returnReasonName) {
        return getReturnPolicyReason(eea, returnKindName, returnPolicyName, returnReasonName, EntityPermission.READ_ONLY);
    }

    public ReturnPolicyReason getReturnPolicyReasonForUpdate(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnPolicyName, final String returnReasonName) {
        return getReturnPolicyReason(eea, returnKindName, returnPolicyName, returnReasonName, EntityPermission.READ_WRITE);
    }

}
