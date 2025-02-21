// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.order.common.form.GetOrderTimeTypesForm;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.model.control.order.server.control.OrderTimeControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.order.server.logic.OrderTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderTimeType;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.factory.OrderTimeTypeFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetOrderTimeTypesCommand
        extends BaseMultipleEntitiesCommand<OrderTimeType, GetOrderTimeTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderTimeType.name(), SecurityRoles.List.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetOrderTimeTypesCommand */
    public GetOrderTimeTypesCommand(UserVisitPK userVisitPK, GetOrderTimeTypesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    OrderType orderType;

    @Override
    protected Collection<OrderTimeType> getEntities() {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);

        orderType = OrderTypeLogic.getInstance().getOrderTypeByName(this, form.getOrderTypeName());

        return hasExecutionErrors() ? null : orderTimeControl.getOrderTimeTypes(orderType);
    }

    @Override
    protected BaseResult getResult(Collection<OrderTimeType> entities) {
        var result = OrderResultFactory.getGetOrderTimeTypesResult();

        if(entities != null) {
            var orderTypeControl = Session.getModelController(OrderTypeControl.class);
            var orderTimeControl = Session.getModelController(OrderTimeControl.class);

            if(session.hasLimit(OrderTimeTypeFactory.class)) {
                result.setOrderTimeTypeCount(orderTimeControl.countOrderTimeTypes(orderType));
            }

            result.setOrderType(orderTypeControl.getOrderTypeTransfer(getUserVisit(), orderType));
            result.setOrderTimeTypes(orderTimeControl.getOrderTimeTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
