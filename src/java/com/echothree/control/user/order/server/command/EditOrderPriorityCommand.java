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

import com.echothree.control.user.order.remote.edit.OrderEditFactory;
import com.echothree.control.user.order.remote.edit.OrderPriorityEdit;
import com.echothree.control.user.order.remote.form.EditOrderPriorityForm;
import com.echothree.control.user.order.remote.result.EditOrderPriorityResult;
import com.echothree.control.user.order.remote.result.OrderResultFactory;
import com.echothree.control.user.order.remote.spec.OrderPrioritySpec;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderPriorityDescription;
import com.echothree.model.data.order.server.entity.OrderPriorityDetail;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.value.OrderPriorityDescriptionValue;
import com.echothree.model.data.order.server.value.OrderPriorityDetailValue;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
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
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
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
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderPriority orderPriority = null;
        String orderTypeName = spec.getOrderTypeName();
        OrderType orderType = orderControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            String orderPriorityName = spec.getOrderPriorityName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                orderPriority = orderControl.getOrderPriorityByName(orderType, orderPriorityName);
            } else { // EditMode.UPDATE
                orderPriority = orderControl.getOrderPriorityByNameForUpdate(orderType, orderPriorityName);
            }

            if(orderPriority != null) {
                result.setOrderPriority(orderControl.getOrderPriorityTransfer(getUserVisit(), orderPriority));
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
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);

        result.setOrderPriority(orderControl.getOrderPriorityTransfer(getUserVisit(), orderPriority));
    }

    @Override
    public void doLock(OrderPriorityEdit edit, OrderPriority orderPriority) {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderPriorityDescription orderPriorityDescription = orderControl.getOrderPriorityDescription(orderPriority, getPreferredLanguage());
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
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        String orderTypeName = spec.getOrderTypeName();
        OrderType orderType = orderControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            String orderPriorityName = edit.getOrderPriorityName();
            OrderPriority duplicateOrderPriority = orderControl.getOrderPriorityByName(orderType, orderPriorityName);

            if(duplicateOrderPriority != null && !orderPriority.equals(duplicateOrderPriority)) {
                addExecutionError(ExecutionErrors.DuplicateOrderPriorityName.name(), orderTypeName, orderPriorityName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }
    }

    @Override
    public void doUpdate(OrderPriority orderPriority) {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        PartyPK partyPK = getPartyPK();
        OrderPriorityDetailValue orderPriorityDetailValue = orderControl.getOrderPriorityDetailValueForUpdate(orderPriority);
        OrderPriorityDescription orderPriorityDescription = orderControl.getOrderPriorityDescriptionForUpdate(orderPriority, getPreferredLanguage());
        String description = edit.getDescription();

        orderPriorityDetailValue.setOrderPriorityName(edit.getOrderPriorityName());
        orderPriorityDetailValue.setPriority(Integer.valueOf(edit.getPriority()));
        orderPriorityDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        orderPriorityDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        orderControl.updateOrderPriorityFromValue(orderPriorityDetailValue, partyPK);

        if(orderPriorityDescription == null && description != null) {
            orderControl.createOrderPriorityDescription(orderPriority, getPreferredLanguage(), description, partyPK);
        } else {
            if(orderPriorityDescription != null && description == null) {
                orderControl.deleteOrderPriorityDescription(orderPriorityDescription, partyPK);
            } else {
                if(orderPriorityDescription != null && description != null) {
                    OrderPriorityDescriptionValue orderPriorityDescriptionValue = orderControl.getOrderPriorityDescriptionValue(orderPriorityDescription);

                    orderPriorityDescriptionValue.setDescription(description);
                    orderControl.updateOrderPriorityDescriptionFromValue(orderPriorityDescriptionValue, partyPK);
                }
            }
        }
    }

}
