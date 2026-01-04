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

import com.echothree.control.user.order.common.form.DeleteOrderAliasForm;
import com.echothree.control.user.order.server.command.util.OrderAliasUtil;
import com.echothree.model.control.order.server.control.OrderAliasControl;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoles;
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
import javax.enterprise.context.Dependent;

@Dependent
public class DeleteOrderAliasCommand
        extends BaseSimpleCommand<DeleteOrderAliasForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteOrderAliasCommand */
    public DeleteOrderAliasCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(OrderAliasUtil.getInstance().getSecurityRoleGroupNameByOrderTypeSpec(form), SecurityRoles.Delete.name())
                )))
        )));
    }

    @Override
    protected BaseResult execute() {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        var orderTypeName = form.getOrderTypeName();
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderControl = Session.getModelController(OrderControl.class);
            var orderName = form.getOrderName();
            var order = orderControl.getOrderByName(orderType, orderName);

            if(order != null) {
                var orderAliasControl = Session.getModelController(OrderAliasControl.class);
                var orderAliasTypeName = form.getOrderAliasTypeName();
                var orderAliasType = orderAliasControl.getOrderAliasTypeByName(orderType, orderAliasTypeName);

                if(orderAliasType != null) {
                    var orderAlias = orderAliasControl.getOrderAliasForUpdate(order, orderAliasType);

                    if(orderAlias != null) {
                        orderAliasControl.deleteOrderAlias(orderAlias, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownOrderAlias.name(), orderTypeName, orderName, orderAliasTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownOrderAliasTypeName.name(), orderTypeName, orderAliasTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderName.name(), orderTypeName, orderName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return null;
    }
    
}
