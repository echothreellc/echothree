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

import com.echothree.model.control.returnpolicy.common.exception.UnknownReturnTypeShippingMethodException;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.shipping.server.logic.ShippingMethodLogic;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeShippingMethod;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ReturnTypeShippingMethodLogic
        extends BaseLogic {

    @Inject
    ReturnPolicyControl returnPolicyControl;

    @Inject
    ReturnTypeLogic returnTypeLogic;

    @Inject
    ShippingMethodLogic shippingMethodLogic;

    protected ReturnTypeShippingMethodLogic() {
        super();
    }

    public ReturnTypeShippingMethod getReturnTypeShippingMethod(final ExecutionErrorAccumulator eea, final ReturnType returnType,
            final ShippingMethod shippingMethod, final EntityPermission entityPermission) {
        var returnTypeShippingMethod = returnPolicyControl.getReturnTypeShippingMethod(returnType, shippingMethod, entityPermission);

        if(returnTypeShippingMethod == null) {
            var returnTypeDetail = returnType.getLastDetail();

            handleExecutionError(UnknownReturnTypeShippingMethodException.class, eea, ExecutionErrors.UnknownReturnTypeShippingMethod.name(),
                    returnTypeDetail.getReturnKind().getLastDetail().getReturnKindName(), returnTypeDetail.getReturnTypeName(),
                    shippingMethod.getLastDetail().getShippingMethodName());
        }

        return returnTypeShippingMethod;
    }

    public ReturnTypeShippingMethod getReturnTypeShippingMethod(final ExecutionErrorAccumulator eea, final ReturnType returnType,
            final ShippingMethod shippingMethod) {
        return getReturnTypeShippingMethod(eea, returnType, shippingMethod, EntityPermission.READ_ONLY);
    }

    public ReturnTypeShippingMethod getReturnTypeShippingMethodForUpdate(final ExecutionErrorAccumulator eea, final ReturnType returnType,
            final ShippingMethod shippingMethod) {
        return getReturnTypeShippingMethod(eea, returnType, shippingMethod, EntityPermission.READ_WRITE);
    }

    public ReturnTypeShippingMethod getReturnTypeShippingMethod(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnTypeName, final String shippingMethodName, final EntityPermission entityPermission) {
        var returnType = returnTypeLogic.getReturnTypeByName(eea, returnKindName, returnTypeName);
        var shippingMethod = shippingMethodLogic.getShippingMethodByName(eea, shippingMethodName);
        ReturnTypeShippingMethod returnTypeShippingMethod = null;

        if(!hasExecutionErrors(eea)) {
            returnTypeShippingMethod = getReturnTypeShippingMethod(eea, returnType, shippingMethod, entityPermission);
        }

        return returnTypeShippingMethod;
    }

    public ReturnTypeShippingMethod getReturnTypeShippingMethod(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnTypeName, final String shippingMethodName) {
        return getReturnTypeShippingMethod(eea, returnKindName, returnTypeName, shippingMethodName, EntityPermission.READ_ONLY);
    }

    public ReturnTypeShippingMethod getReturnTypeShippingMethodForUpdate(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnTypeName, final String shippingMethodName) {
        return getReturnTypeShippingMethod(eea, returnKindName, returnTypeName, shippingMethodName, EntityPermission.READ_WRITE);
    }

}
