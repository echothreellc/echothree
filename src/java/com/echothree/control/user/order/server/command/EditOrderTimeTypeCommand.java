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
import com.echothree.control.user.order.common.edit.OrderTimeTypeEdit;
import com.echothree.control.user.order.common.form.EditOrderTimeTypeForm;
import com.echothree.control.user.order.common.result.EditOrderTimeTypeResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderTimeTypeSpec;
import com.echothree.model.control.order.server.control.OrderTimeControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderTimeType;
import com.echothree.model.data.order.server.entity.OrderTimeTypeDescription;
import com.echothree.model.data.order.server.entity.OrderTimeTypeDetail;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.value.OrderTimeTypeDescriptionValue;
import com.echothree.model.data.order.server.value.OrderTimeTypeDetailValue;
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

public class EditOrderTimeTypeCommand
        extends BaseAbstractEditCommand<OrderTimeTypeSpec, OrderTimeTypeEdit, EditOrderTimeTypeResult, OrderTimeType, OrderTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.OrderTimeType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderTimeTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditOrderTimeTypeCommand */
    public EditOrderTimeTypeCommand(UserVisitPK userVisitPK, EditOrderTimeTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditOrderTimeTypeResult getResult() {
        return OrderResultFactory.getEditOrderTimeTypeResult();
    }

    @Override
    public OrderTimeTypeEdit getEdit() {
        return OrderEditFactory.getOrderTimeTypeEdit();
    }

    @Override
    public OrderTimeType getEntity(EditOrderTimeTypeResult result) {
        var orderTypeControl = (OrderTypeControl)Session.getModelController(OrderTypeControl.class);
        OrderTimeType orderTimeType = null;
        String orderTypeName = spec.getOrderTypeName();
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderTimeControl = (OrderTimeControl)Session.getModelController(OrderTimeControl.class);
            String orderTimeTypeName = spec.getOrderTimeTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                orderTimeType = orderTimeControl.getOrderTimeTypeByName(orderType, orderTimeTypeName);
            } else { // EditMode.UPDATE
                orderTimeType = orderTimeControl.getOrderTimeTypeByNameForUpdate(orderType, orderTimeTypeName);
            }

            if(orderTimeType != null) {
                result.setOrderTimeType(orderTimeControl.getOrderTimeTypeTransfer(getUserVisit(), orderTimeType));
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderTimeTypeName.name(), orderTypeName, orderTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderTimeType;
    }

    @Override
    public OrderTimeType getLockEntity(OrderTimeType orderTimeType) {
        return orderTimeType;
    }

    @Override
    public void fillInResult(EditOrderTimeTypeResult result, OrderTimeType orderTimeType) {
        var orderTimeControl = (OrderTimeControl)Session.getModelController(OrderTimeControl.class);

        result.setOrderTimeType(orderTimeControl.getOrderTimeTypeTransfer(getUserVisit(), orderTimeType));
    }

    @Override
    public void doLock(OrderTimeTypeEdit edit, OrderTimeType orderTimeType) {
        var orderTimeControl = (OrderTimeControl)Session.getModelController(OrderTimeControl.class);
        OrderTimeTypeDescription orderTimeTypeDescription = orderTimeControl.getOrderTimeTypeDescription(orderTimeType, getPreferredLanguage());
        OrderTimeTypeDetail orderTimeTypeDetail = orderTimeType.getLastDetail();

        edit.setOrderTimeTypeName(orderTimeTypeDetail.getOrderTimeTypeName());
        edit.setIsDefault(orderTimeTypeDetail.getIsDefault().toString());
        edit.setSortOrder(orderTimeTypeDetail.getSortOrder().toString());

        if(orderTimeTypeDescription != null) {
            edit.setDescription(orderTimeTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(OrderTimeType orderTimeType) {
        var orderTypeControl = (OrderTypeControl)Session.getModelController(OrderTypeControl.class);
        String orderTypeName = spec.getOrderTypeName();
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderTimeControl = (OrderTimeControl)Session.getModelController(OrderTimeControl.class);
            String orderTimeTypeName = edit.getOrderTimeTypeName();
            OrderTimeType duplicateOrderTimeType = orderTimeControl.getOrderTimeTypeByName(orderType, orderTimeTypeName);

            if(duplicateOrderTimeType != null && !orderTimeType.equals(duplicateOrderTimeType)) {
                addExecutionError(ExecutionErrors.DuplicateOrderTimeTypeName.name(), orderTypeName, orderTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }
    }

    @Override
    public void doUpdate(OrderTimeType orderTimeType) {
        var orderTimeControl = (OrderTimeControl)Session.getModelController(OrderTimeControl.class);
        var partyPK = getPartyPK();
        OrderTimeTypeDetailValue orderTimeTypeDetailValue = orderTimeControl.getOrderTimeTypeDetailValueForUpdate(orderTimeType);
        OrderTimeTypeDescription orderTimeTypeDescription = orderTimeControl.getOrderTimeTypeDescriptionForUpdate(orderTimeType, getPreferredLanguage());
        String description = edit.getDescription();

        orderTimeTypeDetailValue.setOrderTimeTypeName(edit.getOrderTimeTypeName());
        orderTimeTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        orderTimeTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        orderTimeControl.updateOrderTimeTypeFromValue(orderTimeTypeDetailValue, partyPK);

        if(orderTimeTypeDescription == null && description != null) {
            orderTimeControl.createOrderTimeTypeDescription(orderTimeType, getPreferredLanguage(), description, partyPK);
        } else {
            if(orderTimeTypeDescription != null && description == null) {
                orderTimeControl.deleteOrderTimeTypeDescription(orderTimeTypeDescription, partyPK);
            } else {
                if(orderTimeTypeDescription != null && description != null) {
                    OrderTimeTypeDescriptionValue orderTimeTypeDescriptionValue = orderTimeControl.getOrderTimeTypeDescriptionValue(orderTimeTypeDescription);

                    orderTimeTypeDescriptionValue.setDescription(description);
                    orderTimeControl.updateOrderTimeTypeDescriptionFromValue(orderTimeTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
