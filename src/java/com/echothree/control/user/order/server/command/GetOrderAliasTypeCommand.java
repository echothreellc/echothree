// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.order.common.form.GetOrderAliasTypeForm;
import com.echothree.control.user.order.common.result.GetOrderAliasTypeResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderAliasType;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetOrderAliasTypeCommand
        extends BaseSimpleCommand<GetOrderAliasTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderAliasType.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetOrderAliasTypeCommand */
    public GetOrderAliasTypeCommand(UserVisitPK userVisitPK, GetOrderAliasTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        GetOrderAliasTypeResult result = OrderResultFactory.getGetOrderAliasTypeResult();
        String orderTypeName = form.getOrderTypeName();
        OrderType orderType = orderControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            String orderAliasTypeName = form.getOrderAliasTypeName();
            OrderAliasType orderAliasType = orderControl.getOrderAliasTypeByName(orderType, orderAliasTypeName);

            if(orderAliasType != null) {
                result.setOrderAliasType(orderControl.getOrderAliasTypeTransfer(getUserVisit(), orderAliasType));

                sendEventUsingNames(orderAliasType.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderAliasTypeName.name(), orderTypeName, orderAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }
        
        return result;
    }
    
}
