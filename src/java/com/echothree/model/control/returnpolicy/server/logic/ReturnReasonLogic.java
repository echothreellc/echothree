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

}
