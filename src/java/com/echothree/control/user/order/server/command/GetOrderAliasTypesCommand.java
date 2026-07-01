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

import com.echothree.control.user.order.common.form.GetOrderAliasTypesForm;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.model.control.order.server.control.OrderAliasControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.order.server.logic.OrderTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderAliasType;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.factory.OrderAliasTypeFactory;
import com.echothree.util.common.command.BaseResult;
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
public class GetOrderAliasTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<OrderAliasType, GetOrderAliasTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderAliasType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    OrderAliasControl orderAliasControl;

    @Inject
    OrderTypeControl orderTypeControl;

    @Inject
    OrderTypeLogic orderTypeLogic;

    /** Creates a new instance of GetOrderAliasTypesCommand */
    public GetOrderAliasTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    OrderType orderType;

    @Override
    protected void handleForm() {
        orderType = orderTypeLogic.getOrderTypeByName(this, form.getOrderTypeName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : orderAliasControl.countOrderAliasTypesByOrderType(orderType);
    }

    @Override
    protected Collection<OrderAliasType> getEntities() {
        return hasExecutionErrors() ? null : orderAliasControl.getOrderAliasTypes(orderType);
    }

    @Override
    protected BaseResult getResult(Collection<OrderAliasType> entities) {
        var result = OrderResultFactory.getGetOrderAliasTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setOrderType(orderTypeControl.getOrderTypeTransfer(userVisit, orderType));

            if(session.hasLimit(OrderAliasTypeFactory.class)) {
                result.setOrderAliasTypeCount(getTotalEntities());
            }

            result.setOrderAliasTypes(orderAliasControl.getOrderAliasTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
