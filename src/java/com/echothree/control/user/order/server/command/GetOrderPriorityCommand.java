// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.order.common.form.GetOrderPriorityForm;
import com.echothree.control.user.order.common.result.GetOrderPriorityResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.order.server.control.OrderPriorityControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetOrderPriorityCommand
        extends BaseSimpleCommand<GetOrderPriorityForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderPriority.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderPriorityName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetOrderPriorityCommand */
    public GetOrderPriorityCommand(UserVisitPK userVisitPK, GetOrderPriorityForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        GetOrderPriorityResult result = OrderResultFactory.getGetOrderPriorityResult();
        var orderTypeName = form.getOrderTypeName();
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderPriorityControl = Session.getModelController(OrderPriorityControl.class);
            String orderPriorityName = form.getOrderPriorityName();
            OrderPriority orderPriority = orderPriorityControl.getOrderPriorityByName(orderType, orderPriorityName);

            if(orderPriority != null) {
                result.setOrderPriority(orderPriorityControl.getOrderPriorityTransfer(getUserVisit(), orderPriority));

                sendEventUsingNames(orderPriority.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderPriorityName.name(), orderPriorityName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return result;
    }
    
}
