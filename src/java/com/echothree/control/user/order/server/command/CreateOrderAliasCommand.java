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

import com.echothree.control.user.order.common.form.CreateOrderAliasForm;
import com.echothree.control.user.order.server.command.util.OrderAliasUtil;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderAliasType;
import com.echothree.model.data.order.server.entity.OrderAliasTypeDetail;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateOrderAliasCommand
        extends BaseSimpleCommand<CreateOrderAliasForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateOrderAliasCommand */
    public CreateOrderAliasCommand(UserVisitPK userVisitPK, CreateOrderAliasForm form) {
        super(userVisitPK, form, new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(OrderAliasUtil.getInstance().getSecurityRoleGroupNameByOrderTypeSpec(form), SecurityRoles.Create.name())
                        )))
                ))), FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        String orderTypeName = form.getOrderTypeName();
        OrderType orderType = orderControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            String orderName = form.getOrderName();
            Order order = orderControl.getOrderByName(orderType, orderName);

            if(order != null) {
                String orderAliasTypeName = form.getOrderAliasTypeName();
                OrderAliasType orderAliasType = orderControl.getOrderAliasTypeByName(orderType, orderAliasTypeName);

                if(orderAliasType != null) {
                    OrderAliasTypeDetail orderAliasTypeDetail = orderAliasType.getLastDetail();
                    String validationPattern = orderAliasTypeDetail.getValidationPattern();
                    String alias = form.getAlias();

                    if(validationPattern != null) {
                        Pattern pattern = Pattern.compile(validationPattern);
                        Matcher m = pattern.matcher(alias);

                        if(!m.matches()) {
                            addExecutionError(ExecutionErrors.InvalidAlias.name(), alias);
                        }
                    }

                    if(!hasExecutionErrors()) {
                        if(orderControl.getOrderAlias(order, orderAliasType) == null && orderControl.getOrderAliasByAlias(orderAliasType, alias) == null) {
                            orderControl.createOrderAlias(order, orderAliasType, alias, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateOrderAlias.name(), orderTypeName, orderName, orderAliasTypeName, alias);
                        }
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
