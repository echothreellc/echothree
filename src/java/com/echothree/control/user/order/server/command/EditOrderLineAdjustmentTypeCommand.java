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

import com.echothree.control.user.order.common.edit.OrderEditFactory;
import com.echothree.control.user.order.common.edit.OrderLineAdjustmentTypeEdit;
import com.echothree.control.user.order.common.form.EditOrderLineAdjustmentTypeForm;
import com.echothree.control.user.order.common.result.EditOrderLineAdjustmentTypeResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderLineAdjustmentTypeSpec;
import com.echothree.model.control.order.server.control.OrderLineAdjustmentControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentType;
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

public class EditOrderLineAdjustmentTypeCommand
        extends BaseAbstractEditCommand<OrderLineAdjustmentTypeSpec, OrderLineAdjustmentTypeEdit, EditOrderLineAdjustmentTypeResult, OrderLineAdjustmentType, OrderLineAdjustmentType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.OrderLineAdjustmentType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderLineAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderLineAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditOrderLineAdjustmentTypeCommand */
    public EditOrderLineAdjustmentTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditOrderLineAdjustmentTypeResult getResult() {
        return OrderResultFactory.getEditOrderLineAdjustmentTypeResult();
    }

    @Override
    public OrderLineAdjustmentTypeEdit getEdit() {
        return OrderEditFactory.getOrderLineAdjustmentTypeEdit();
    }

    @Override
    public OrderLineAdjustmentType getEntity(EditOrderLineAdjustmentTypeResult result) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        OrderLineAdjustmentType orderLineAdjustmentType = null;
        var orderTypeName = spec.getOrderTypeName();
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderLineAdjustmentControl = Session.getModelController(OrderLineAdjustmentControl.class);
            var orderLineAdjustmentTypeName = spec.getOrderLineAdjustmentTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                orderLineAdjustmentType = orderLineAdjustmentControl.getOrderLineAdjustmentTypeByName(orderType, orderLineAdjustmentTypeName);
            } else { // EditMode.UPDATE
                orderLineAdjustmentType = orderLineAdjustmentControl.getOrderLineAdjustmentTypeByNameForUpdate(orderType, orderLineAdjustmentTypeName);
            }

            if(orderLineAdjustmentType != null) {
                result.setOrderLineAdjustmentType(orderLineAdjustmentControl.getOrderLineAdjustmentTypeTransfer(getUserVisit(), orderLineAdjustmentType));
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderLineAdjustmentTypeName.name(), orderTypeName, orderLineAdjustmentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderLineAdjustmentType;
    }

    @Override
    public OrderLineAdjustmentType getLockEntity(OrderLineAdjustmentType orderLineAdjustmentType) {
        return orderLineAdjustmentType;
    }

    @Override
    public void fillInResult(EditOrderLineAdjustmentTypeResult result, OrderLineAdjustmentType orderLineAdjustmentType) {
        var orderLineAdjustmentControl = Session.getModelController(OrderLineAdjustmentControl.class);

        result.setOrderLineAdjustmentType(orderLineAdjustmentControl.getOrderLineAdjustmentTypeTransfer(getUserVisit(), orderLineAdjustmentType));
    }

    @Override
    public void doLock(OrderLineAdjustmentTypeEdit edit, OrderLineAdjustmentType orderLineAdjustmentType) {
        var orderLineAdjustmentControl = Session.getModelController(OrderLineAdjustmentControl.class);
        var orderLineAdjustmentTypeDescription = orderLineAdjustmentControl.getOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, getPreferredLanguage());
        var orderLineAdjustmentTypeDetail = orderLineAdjustmentType.getLastDetail();

        edit.setOrderLineAdjustmentTypeName(orderLineAdjustmentTypeDetail.getOrderLineAdjustmentTypeName());
        edit.setIsDefault(orderLineAdjustmentTypeDetail.getIsDefault().toString());
        edit.setSortOrder(orderLineAdjustmentTypeDetail.getSortOrder().toString());

        if(orderLineAdjustmentTypeDescription != null) {
            edit.setDescription(orderLineAdjustmentTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(OrderLineAdjustmentType orderLineAdjustmentType) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        var orderTypeName = spec.getOrderTypeName();
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderLineAdjustmentControl = Session.getModelController(OrderLineAdjustmentControl.class);
            var orderLineAdjustmentTypeName = edit.getOrderLineAdjustmentTypeName();
            var duplicateOrderLineAdjustmentType = orderLineAdjustmentControl.getOrderLineAdjustmentTypeByName(orderType, orderLineAdjustmentTypeName);

            if(duplicateOrderLineAdjustmentType != null && !orderLineAdjustmentType.equals(duplicateOrderLineAdjustmentType)) {
                addExecutionError(ExecutionErrors.DuplicateOrderLineAdjustmentTypeName.name(), orderTypeName, orderLineAdjustmentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }
    }

    @Override
    public void doUpdate(OrderLineAdjustmentType orderLineAdjustmentType) {
        var orderLineAdjustmentControl = Session.getModelController(OrderLineAdjustmentControl.class);
        var partyPK = getPartyPK();
        var orderLineAdjustmentTypeDetailValue = orderLineAdjustmentControl.getOrderLineAdjustmentTypeDetailValueForUpdate(orderLineAdjustmentType);
        var orderLineAdjustmentTypeDescription = orderLineAdjustmentControl.getOrderLineAdjustmentTypeDescriptionForUpdate(orderLineAdjustmentType, getPreferredLanguage());
        var description = edit.getDescription();

        orderLineAdjustmentTypeDetailValue.setOrderLineAdjustmentTypeName(edit.getOrderLineAdjustmentTypeName());
        orderLineAdjustmentTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        orderLineAdjustmentTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        orderLineAdjustmentControl.updateOrderLineAdjustmentTypeFromValue(orderLineAdjustmentTypeDetailValue, partyPK);

        if(orderLineAdjustmentTypeDescription == null && description != null) {
            orderLineAdjustmentControl.createOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, getPreferredLanguage(), description, partyPK);
        } else {
            if(orderLineAdjustmentTypeDescription != null && description == null) {
                orderLineAdjustmentControl.deleteOrderLineAdjustmentTypeDescription(orderLineAdjustmentTypeDescription, partyPK);
            } else {
                if(orderLineAdjustmentTypeDescription != null && description != null) {
                    var orderLineAdjustmentTypeDescriptionValue = orderLineAdjustmentControl.getOrderLineAdjustmentTypeDescriptionValue(orderLineAdjustmentTypeDescription);

                    orderLineAdjustmentTypeDescriptionValue.setDescription(description);
                    orderLineAdjustmentControl.updateOrderLineAdjustmentTypeDescriptionFromValue(orderLineAdjustmentTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
