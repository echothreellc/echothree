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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.form.GetReturnTypeShippingMethodsForm;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.returnpolicy.server.logic.ReturnKindLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.control.shipping.server.logic.ShippingMethodLogic;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeShippingMethod;
import com.echothree.model.data.returnpolicy.server.factory.ReturnTypeShippingMethodFactory;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetReturnTypeShippingMethodsCommand
        extends BasePaginatedMultipleEntitiesCommand<ReturnTypeShippingMethod, GetReturnTypeShippingMethodsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnTypeShippingMethod.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    ReturnPolicyControl returnPolicyControl;

    @Inject
    ShippingControl shippingControl;

    @Inject
    ReturnKindLogic returnKindLogic;

    @Inject
    ShippingMethodLogic shippingMethodLogic;

    /** Creates a new instance of GetReturnTypeShippingMethodsCommand */
    public GetReturnTypeShippingMethodsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private ReturnType returnType;
    private ShippingMethod shippingMethod;

    @Override
    protected void handleForm() {
        var returnKindName = form.getReturnKindName();
        var returnTypeName = form.getReturnTypeName();
        var shippingMethodName = form.getShippingMethodName();
        var parameterCount = (returnKindName != null && returnTypeName != null ? 1 : 0) + (shippingMethodName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(returnKindName != null && returnTypeName != null) {
                var returnKind = returnKindLogic.getReturnKindByName(this, returnKindName);

                if(!hasExecutionErrors()) {
                    returnType = returnPolicyControl.getReturnTypeByName(returnKind, returnTypeName);

                    if(returnType == null) {
                        addExecutionError(ExecutionErrors.UnknownReturnTypeName.name(), returnKindName, returnTypeName);
                    }
                }
            } else {
                shippingMethod = shippingMethodLogic.getShippingMethodByName(this, shippingMethodName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(returnType != null) {
                total = returnPolicyControl.countReturnTypeShippingMethodsByReturnType(returnType);
            } else {
                total = returnPolicyControl.countReturnTypeShippingMethodsByShippingMethod(shippingMethod);
            }
        }

        return total;
    }

    @Override
    protected Collection<ReturnTypeShippingMethod> getEntities() {
        Collection<ReturnTypeShippingMethod> entities = null;

        if(!hasExecutionErrors()) {
            if(returnType != null) {
                entities = returnPolicyControl.getReturnTypeShippingMethodsByReturnType(returnType);
            } else {
                entities = returnPolicyControl.getReturnTypeShippingMethodsByShippingMethod(shippingMethod);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<ReturnTypeShippingMethod> entities) {
        var result = ReturnPolicyResultFactory.getGetReturnTypeShippingMethodsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(returnType != null) {
                result.setReturnType(returnPolicyControl.getReturnTypeTransfer(userVisit, returnType));
            } else {
                result.setShippingMethod(shippingControl.getShippingMethodTransfer(userVisit, shippingMethod));
            }

            if(session.hasLimit(ReturnTypeShippingMethodFactory.class)) {
                result.setReturnTypeShippingMethodCount(getTotalEntities());
            }

            result.setReturnTypeShippingMethods(returnPolicyControl.getReturnTypeShippingMethodTransfers(userVisit, entities));
        }

        return result;
    }

}
