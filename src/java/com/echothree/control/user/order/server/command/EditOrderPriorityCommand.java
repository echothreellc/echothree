// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.order.common.edit.OrderEditFactory;
import com.echothree.control.user.order.common.edit.OrderPriorityEdit;
import com.echothree.control.user.order.common.form.EditOrderPriorityForm;
import com.echothree.control.user.order.common.result.EditOrderPriorityResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderPrioritySpec;
import com.echothree.model.control.order.server.control.OrderPriorityControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderPriorityDescription;
import com.echothree.model.data.order.server.entity.OrderPriorityDetail;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.value.OrderPriorityDescriptionValue;
import com.echothree.model.data.order.server.value.OrderPriorityDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditOrderPriorityCommand
        extends BaseAbstractEditCommand<OrderPrioritySpec, OrderPriorityEdit, EditOrderPriorityResult, OrderPriority, OrderPriority> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.OrderPriority.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderPriorityName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderPriorityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Priority", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditOrderPriorityCommand */
    public EditOrderPriorityCommand(UserVisitPK userVisitPK, EditOrderPriorityForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditOrderPriorityResult getResult() {
        return OrderResultFactory.getEditOrderPriorityResult();
    }

    @Override
    public OrderPriorityEdit getEdit() {
        return OrderEditFactory.getOrderPriorityEdit();
    }

    @Override
    public OrderPriority getEntity(EditOrderPriorityResult result) {
        var orderTypeControl = (OrderTypeControl)Session.getModelController(OrderTypeControl.class);
        OrderPriority orderPriority = null;
        String orderTypeName = spec.getOrderTypeName();
        OrderType orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderPriorityControl = (OrderPriorityControl)Session.getModelController(OrderPriorityControl.class);
            String orderPriorityName = spec.getOrderPriorityName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                orderPriority = orderPriorityControl.getOrderPriorityByName(orderType, orderPriorityName);
            } else { // EditMode.UPDATE
                orderPriority = orderPriorityControl.getOrderPriorityByNameForUpdate(orderType, orderPriorityName);
            }

            if(orderPriority != null) {
                result.setOrderPriority(orderPriorityControl.getOrderPriorityTransfer(getUserVisit(), orderPriority));
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderPriorityName.name(), orderTypeName, orderPriorityName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderPriority;
    }

    @Override
    public OrderPriority getLockEntity(OrderPriority orderPriority) {
        return orderPriority;
    }

    @Override
    public void fillInResult(EditOrderPriorityResult result, OrderPriority orderPriority) {
        var orderPriorityControl = (OrderPriorityControl)Session.getModelController(OrderPriorityControl.class);

        result.setOrderPriority(orderPriorityControl.getOrderPriorityTransfer(getUserVisit(), orderPriority));
    }

    @Override
    public void doLock(OrderPriorityEdit edit, OrderPriority orderPriority) {
        var orderPriorityControl = (OrderPriorityControl)Session.getModelController(OrderPriorityControl.class);
        OrderPriorityDescription orderPriorityDescription = orderPriorityControl.getOrderPriorityDescription(orderPriority, getPreferredLanguage());
        OrderPriorityDetail orderPriorityDetail = orderPriority.getLastDetail();

        edit.setOrderPriorityName(orderPriorityDetail.getOrderPriorityName());
        edit.setPriority(orderPriorityDetail.getPriority().toString());
        edit.setIsDefault(orderPriorityDetail.getIsDefault().toString());
        edit.setSortOrder(orderPriorityDetail.getSortOrder().toString());

        if(orderPriorityDescription != null) {
            edit.setDescription(orderPriorityDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(OrderPriority orderPriority) {
        var orderTypeControl = (OrderTypeControl)Session.getModelController(OrderTypeControl.class);
        String orderTypeName = spec.getOrderTypeName();
        OrderType orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderPriorityControl = (OrderPriorityControl)Session.getModelController(OrderPriorityControl.class);
            String orderPriorityName = edit.getOrderPriorityName();
            OrderPriority duplicateOrderPriority = orderPriorityControl.getOrderPriorityByName(orderType, orderPriorityName);

            if(duplicateOrderPriority != null && !orderPriority.equals(duplicateOrderPriority)) {
                addExecutionError(ExecutionErrors.DuplicateOrderPriorityName.name(), orderTypeName, orderPriorityName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }
    }

    @Override
    public void doUpdate(OrderPriority orderPriority) {
        var orderPriorityControl = (OrderPriorityControl)Session.getModelController(OrderPriorityControl.class);
        PartyPK partyPK = getPartyPK();
        OrderPriorityDetailValue orderPriorityDetailValue = orderPriorityControl.getOrderPriorityDetailValueForUpdate(orderPriority);
        OrderPriorityDescription orderPriorityDescription = orderPriorityControl.getOrderPriorityDescriptionForUpdate(orderPriority, getPreferredLanguage());
        String description = edit.getDescription();

        orderPriorityDetailValue.setOrderPriorityName(edit.getOrderPriorityName());
        orderPriorityDetailValue.setPriority(Integer.valueOf(edit.getPriority()));
        orderPriorityDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        orderPriorityDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        orderPriorityControl.updateOrderPriorityFromValue(orderPriorityDetailValue, partyPK);

        if(orderPriorityDescription == null && description != null) {
            orderPriorityControl.createOrderPriorityDescription(orderPriority, getPreferredLanguage(), description, partyPK);
        } else {
            if(orderPriorityDescription != null && description == null) {
                orderPriorityControl.deleteOrderPriorityDescription(orderPriorityDescription, partyPK);
            } else {
                if(orderPriorityDescription != null && description != null) {
                    OrderPriorityDescriptionValue orderPriorityDescriptionValue = orderPriorityControl.getOrderPriorityDescriptionValue(orderPriorityDescription);

                    orderPriorityDescriptionValue.setDescription(description);
                    orderPriorityControl.updateOrderPriorityDescriptionFromValue(orderPriorityDescriptionValue, partyPK);
                }
            }
        }
    }

}
