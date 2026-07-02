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

package com.echothree.control.user.order.server.command;

import com.echothree.control.user.order.common.form.GetOrderAliasForm;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.server.command.util.OrderAliasUtil;
import com.echothree.model.control.order.server.control.OrderAliasControl;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.order.server.logic.OrderTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderAlias;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetOrderAliasCommand
        extends BaseSingleEntityCommand<OrderAlias, GetOrderAliasForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetOrderAliasCommand */
    public GetOrderAliasCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    @Inject
    OrderAliasControl orderAliasControl;

    @Inject
    OrderLogic orderLogic;

    @Inject
    OrderTypeLogic orderTypeLogic;

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(OrderAliasUtil.getInstance().getSecurityRoleGroupNameByOrderTypeSpec(form), SecurityRoles.Review.name())
                ))
        ));
    }

    @Override
    protected OrderAlias getEntity() {
        var orderTypeName = form.getOrderTypeName();
        var orderType = orderTypeLogic.getOrderTypeByName(this, orderTypeName);
        OrderAlias orderAlias = null;

        if(!hasExecutionErrors()) {
            var orderName = form.getOrderName();
            var order = orderLogic.getOrderByName(this, orderTypeName, orderName);

            if(!hasExecutionErrors()) {
                var orderAliasTypeName = form.getOrderAliasTypeName();
                var orderAliasType = orderLogic.getOrderAliasTypeByName(this, orderType, orderAliasTypeName);

                if(!hasExecutionErrors()) {
                    orderAlias = orderAliasControl.getOrderAlias(order, orderAliasType);

                    if(orderAlias == null) {
                        addExecutionError(ExecutionErrors.UnknownOrderAlias.name(), orderName, orderAliasTypeName);
                    }
                }
            }
        }

        return orderAlias;
    }

    @Override
    protected BaseResult getResult(OrderAlias orderAlias) {
        var result = OrderResultFactory.getGetOrderAliasResult();

        if(orderAlias != null) {
            result.setOrderAlias(orderAliasControl.getOrderAliasTransfer(getUserVisit(), orderAlias));
        }

        return result;
    }
    
}
