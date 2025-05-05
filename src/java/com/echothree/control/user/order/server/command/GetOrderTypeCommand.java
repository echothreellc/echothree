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

import com.echothree.control.user.order.common.form.GetOrderTypeForm;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.order.server.logic.OrderTypeLogic;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetOrderTypeCommand
        extends BaseSingleEntityCommand<OrderType, GetOrderTypeForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }
    
    /** Creates a new instance of GetOrderTypeCommand */
    public GetOrderTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected OrderType getEntity() {
        var orderType = OrderTypeLogic.getInstance().getOrderTypeByUniversalSpec(this, form, true);

        if(orderType != null) {
            sendEvent(orderType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return orderType;
    }

    @Override
    protected BaseResult getResult(OrderType itemAliasType) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        var result = OrderResultFactory.getGetOrderTypeResult();

        if(itemAliasType != null) {
            result.setOrderType(orderTypeControl.getOrderTypeTransfer(getUserVisit(), itemAliasType));
        }

        return result;
    }
    
}
