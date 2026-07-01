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

import com.echothree.control.user.order.common.form.GetOrderAdjustmentTypeForm;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.order.server.control.OrderAdjustmentControl;
import com.echothree.model.control.order.server.logic.OrderTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderAdjustmentType;
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
public class GetOrderAdjustmentTypeCommand
        extends BaseSingleEntityCommand<OrderAdjustmentType, GetOrderAdjustmentTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderAdjustmentType.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    OrderAdjustmentControl orderAdjustmentControl;

    @Inject
    OrderTypeLogic orderTypeLogic;

    /** Creates a new instance of GetOrderAdjustmentTypeCommand */
    public GetOrderAdjustmentTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected OrderAdjustmentType getEntity() {
        var orderTypeName = form.getOrderTypeName();
        var orderType = orderTypeLogic.getOrderTypeByName(this, orderTypeName);
        OrderAdjustmentType orderAdjustmentType = null;

        if(!hasExecutionErrors()) {
            var orderAdjustmentTypeName = form.getOrderAdjustmentTypeName();

            orderAdjustmentType = orderAdjustmentControl.getOrderAdjustmentTypeByName(orderType, orderAdjustmentTypeName);

            if(orderAdjustmentType != null) {
                sendEvent(orderAdjustmentType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderAdjustmentTypeName.name(),
                        orderType.getLastDetail().getOrderTypeName(), orderAdjustmentTypeName);
            }
        }

        return orderAdjustmentType;
    }

    @Override
    protected BaseResult getResult(OrderAdjustmentType orderAdjustmentType) {
        var result = OrderResultFactory.getGetOrderAdjustmentTypeResult();

        if(orderAdjustmentType != null) {
            result.setOrderAdjustmentType(orderAdjustmentControl.getOrderAdjustmentTypeTransfer(getUserVisit(), orderAdjustmentType));
        }

        return result;
    }
    
}
