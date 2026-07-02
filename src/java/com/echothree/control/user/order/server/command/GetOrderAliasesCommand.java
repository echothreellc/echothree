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

import com.echothree.control.user.order.common.form.GetOrderAliasesForm;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.server.command.util.OrderAliasUtil;
import com.echothree.model.control.order.server.control.OrderAliasControl;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderAlias;
import com.echothree.model.data.order.server.factory.OrderAliasFactory;
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
public class GetOrderAliasesCommand
        extends BasePaginatedMultipleEntitiesCommand<OrderAlias, GetOrderAliasesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    OrderAliasControl orderAliasControl;

    @Inject
    OrderControl orderControl;

    @Inject
    OrderLogic orderLogic;

    /** Creates a new instance of GetOrderAliasesCommand */
    public GetOrderAliasesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(OrderAliasUtil.getInstance().getSecurityRoleGroupNameByOrderTypeSpec(form), SecurityRoles.List.name())
                ))
        ));
    }

    Order order;

    @Override
    protected void handleForm() {
        var orderTypeName = form.getOrderTypeName();
        var orderName = form.getOrderName();

        order = orderLogic.getOrderByName(this, orderTypeName, orderName);
    }

    @Override
    protected Long getTotalEntities() {
        return order == null ? null : orderAliasControl.countOrderAliasByOrder(order);
    }

    @Override
    protected Collection<OrderAlias> getEntities() {
        return order == null ? null : orderAliasControl.getOrderAliasesByOrder(order);
    }

    @Override
    protected BaseResult getResult(Collection<OrderAlias> entities) {
        var result = OrderResultFactory.getGetOrderAliasesResult();

        if(order != null) {
            var userVisit = getUserVisit();

            result.setOrder(orderControl.getOrderTransfer(userVisit, order));

            if(session.hasLimit(OrderAliasFactory.class)) {
                result.setOrderAliasCount(getTotalEntities());
            }

            result.setOrderAliases(orderAliasControl.getOrderAliasTransfersByOrder(userVisit, order));
        }

        return result;
    }

}
