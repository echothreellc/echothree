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

import com.echothree.control.user.order.common.form.GetOrderLineAdjustmentTypeForm;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.order.server.control.OrderLineAdjustmentControl;
import com.echothree.model.control.order.server.logic.OrderLineAdjustmentTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentType;
import com.echothree.util.common.command.BaseResult;
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
public class GetOrderLineAdjustmentTypeCommand
        extends BaseSingleEntityCommand<OrderLineAdjustmentType, GetOrderLineAdjustmentTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderLineAdjustmentType.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderLineAdjustmentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }
    
    @Inject
    OrderLineAdjustmentControl orderLineAdjustmentControl;

    @Inject
    OrderLineAdjustmentTypeLogic orderLineAdjustmentTypeLogic;

    /** Creates a new instance of GetOrderLineAdjustmentTypeCommand */
    public GetOrderLineAdjustmentTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected OrderLineAdjustmentType getEntity() {
        var orderLineAdjustmentType = orderLineAdjustmentTypeLogic.getOrderLineAdjustmentTypeByUniversalSpec(this, form, true);

        if(orderLineAdjustmentType != null) {
            sendEvent(orderLineAdjustmentType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return orderLineAdjustmentType;
    }

    @Override
    protected BaseResult getResult(OrderLineAdjustmentType orderLineAdjustmentType) {
        var result = OrderResultFactory.getGetOrderLineAdjustmentTypeResult();

        if(orderLineAdjustmentType != null) {
            result.setOrderLineAdjustmentType(orderLineAdjustmentControl.getOrderLineAdjustmentTypeTransfer(getUserVisit(), orderLineAdjustmentType));
        }

        return result;
    }
    
}
