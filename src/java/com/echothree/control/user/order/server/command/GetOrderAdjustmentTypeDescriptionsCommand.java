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

import com.echothree.control.user.order.common.form.GetOrderAdjustmentTypeDescriptionsForm;
import com.echothree.control.user.order.common.result.GetOrderAdjustmentTypeDescriptionsResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderAdjustmentType;
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

public class GetOrderAdjustmentTypeDescriptionsCommand
        extends BaseSimpleCommand<GetOrderAdjustmentTypeDescriptionsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderAdjustmentType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetOrderAdjustmentTypeDescriptionsCommand */
    public GetOrderAdjustmentTypeDescriptionsCommand(UserVisitPK userVisitPK, GetOrderAdjustmentTypeDescriptionsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        GetOrderAdjustmentTypeDescriptionsResult result = OrderResultFactory.getGetOrderAdjustmentTypeDescriptionsResult();
        String orderTypeName = form.getOrderTypeName();
        OrderType orderType = orderControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            String orderAdjustmentTypeName = form.getOrderAdjustmentTypeName();
            OrderAdjustmentType orderAdjustmentType = orderControl.getOrderAdjustmentTypeByName(orderType, orderAdjustmentTypeName);

            if(orderAdjustmentType != null) {
                result.setOrderAdjustmentType(orderControl.getOrderAdjustmentTypeTransfer(getUserVisit(), orderAdjustmentType));
                result.setOrderAdjustmentTypeDescriptions(orderControl.getOrderAdjustmentTypeDescriptionTransfersByOrderAdjustmentType(getUserVisit(), orderAdjustmentType));
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderAdjustmentTypeName.name(), orderTypeName, orderAdjustmentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return result;
    }
    
}
