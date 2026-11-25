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

import com.echothree.model.control.returnpolicy.common.exception.UnknownReturnTypeNameException;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ReturnTypeLogic
        extends BaseLogic {

    @Inject
    ReturnPolicyControl returnPolicyControl;

    @Inject
    ReturnKindLogic returnKindLogic;

    protected ReturnTypeLogic() {
        super();
    }

    public ReturnType getReturnTypeByName(final ExecutionErrorAccumulator eea, final ReturnKind returnKind,
            final String returnTypeName, final EntityPermission entityPermission) {
        var returnType = returnPolicyControl.getReturnTypeByName(returnKind, returnTypeName, entityPermission);

        if(returnType == null) {
            handleExecutionError(UnknownReturnTypeNameException.class, eea, ExecutionErrors.UnknownReturnTypeName.name(),
                    returnKind.getLastDetail().getReturnKindName(), returnTypeName);
        }

        return returnType;
    }

    public ReturnType getReturnTypeByName(final ExecutionErrorAccumulator eea, final ReturnKind returnKind,
            final String returnTypeName) {
        return getReturnTypeByName(eea, returnKind, returnTypeName, EntityPermission.READ_ONLY);
    }

    public ReturnType getReturnTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final ReturnKind returnKind,
            final String returnTypeName) {
        return getReturnTypeByName(eea, returnKind, returnTypeName, EntityPermission.READ_WRITE);
    }

    public ReturnType getReturnTypeByName(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnTypeName, final EntityPermission entityPermission) {
        var returnKind = returnKindLogic.getReturnKindByName(eea, returnKindName);
        ReturnType returnType = null;

        if(!hasExecutionErrors(eea)) {
            returnType = getReturnTypeByName(eea, returnKind, returnTypeName, entityPermission);
        }

        return returnType;
    }

    public ReturnType getReturnTypeByName(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnTypeName) {
        return getReturnTypeByName(eea, returnKindName, returnTypeName, EntityPermission.READ_ONLY);
    }

    public ReturnType getReturnTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String returnKindName,
            final String returnTypeName) {
        return getReturnTypeByName(eea, returnKindName, returnTypeName, EntityPermission.READ_WRITE);
    }

}
