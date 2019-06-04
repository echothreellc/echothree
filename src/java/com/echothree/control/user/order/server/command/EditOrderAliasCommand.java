// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.order.common.edit.OrderAliasEdit;
import com.echothree.control.user.order.common.edit.OrderEditFactory;
import com.echothree.control.user.order.common.form.EditOrderAliasForm;
import com.echothree.control.user.order.common.result.EditOrderAliasResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderAliasSpec;
import com.echothree.control.user.order.server.command.util.OrderAliasUtil;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderAlias;
import com.echothree.model.data.order.server.entity.OrderAliasType;
import com.echothree.model.data.order.server.entity.OrderAliasTypeDetail;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.value.OrderAliasValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditOrderAliasCommand
        extends BaseAbstractEditCommand<OrderAliasSpec, OrderAliasEdit, EditOrderAliasResult, OrderAlias, OrderAlias> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditOrderAliasCommand */
    public EditOrderAliasCommand(UserVisitPK userVisitPK, EditOrderAliasForm form) {
        super(userVisitPK, form, new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(OrderAliasUtil.getInstance().getSecurityRoleGroupNameByOrderTypeSpec(form == null ? null : form.getSpec()), SecurityRoles.Edit.name())
                        )))
                ))), SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditOrderAliasResult getResult() {
        return OrderResultFactory.getEditOrderAliasResult();
    }

    @Override
    public OrderAliasEdit getEdit() {
        return OrderEditFactory.getOrderAliasEdit();
    }

    OrderAliasType orderAliasType;
    
    @Override
    public OrderAlias getEntity(EditOrderAliasResult result) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderAlias orderAlias = null;
        String orderTypeName = spec.getOrderTypeName();
        OrderType orderType = orderControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            String orderName = spec.getOrderName();
            Order order = orderControl.getOrderByName(orderType, orderName);

            if(order != null) {
                String orderAliasTypeName = spec.getOrderAliasTypeName();

                orderAliasType = orderControl.getOrderAliasTypeByName(orderType, orderAliasTypeName);

                if(orderAliasType != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        orderAlias = orderControl.getOrderAlias(order, orderAliasType);
                    } else { // EditMode.UPDATE
                        orderAlias = orderControl.getOrderAliasForUpdate(order, orderAliasType);
                    }

                    if(orderAlias != null) {
                        result.setOrderAlias(orderControl.getOrderAliasTransfer(getUserVisit(), orderAlias));
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

        return orderAlias;
    }

    @Override
    public OrderAlias getLockEntity(OrderAlias orderAlias) {
        return orderAlias;
    }

    @Override
    public void fillInResult(EditOrderAliasResult result, OrderAlias orderAlias) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);

        result.setOrderAlias(orderControl.getOrderAliasTransfer(getUserVisit(), orderAlias));
    }

    @Override
    public void doLock(OrderAliasEdit edit, OrderAlias orderAlias) {
        edit.setAlias(orderAlias.getAlias());
    }

    @Override
    public void canUpdate(OrderAlias orderAlias) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        String alias = edit.getAlias();
        OrderAlias duplicateOrderAlias = orderControl.getOrderAliasByAlias(orderAliasType, alias);

        if(duplicateOrderAlias != null && !orderAlias.equals(duplicateOrderAlias)) {
            OrderAliasTypeDetail orderAliasTypeDetail = orderAlias.getOrderAliasType().getLastDetail();

            addExecutionError(ExecutionErrors.DuplicateOrderAlias.name(), orderAliasTypeDetail.getOrderType().getLastDetail().getOrderTypeName(),
                    orderAliasTypeDetail.getOrderAliasTypeName(), alias);
        }
    }

    @Override
    public void doUpdate(OrderAlias orderAlias) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderAliasValue orderAliasValue = orderControl.getOrderAliasValue(orderAlias);

        orderAliasValue.setAlias(edit.getAlias());

        orderControl.updateOrderAliasFromValue(orderAliasValue, getPartyPK());
    }

}
