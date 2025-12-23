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

import com.echothree.control.user.order.common.edit.OrderAdjustmentTypeEdit;
import com.echothree.control.user.order.common.edit.OrderEditFactory;
import com.echothree.control.user.order.common.form.EditOrderAdjustmentTypeForm;
import com.echothree.control.user.order.common.result.EditOrderAdjustmentTypeResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderAdjustmentTypeSpec;
import com.echothree.model.control.order.server.control.OrderAdjustmentControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderAdjustmentType;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditOrderAdjustmentTypeCommand
        extends BaseAbstractEditCommand<OrderAdjustmentTypeSpec, OrderAdjustmentTypeEdit, EditOrderAdjustmentTypeResult, OrderAdjustmentType, OrderAdjustmentType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.OrderAdjustmentType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditOrderAdjustmentTypeCommand */
    public EditOrderAdjustmentTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditOrderAdjustmentTypeResult getResult() {
        return OrderResultFactory.getEditOrderAdjustmentTypeResult();
    }

    @Override
    public OrderAdjustmentTypeEdit getEdit() {
        return OrderEditFactory.getOrderAdjustmentTypeEdit();
    }

    @Override
    public OrderAdjustmentType getEntity(EditOrderAdjustmentTypeResult result) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        OrderAdjustmentType orderAdjustmentType = null;
        var orderTypeName = spec.getOrderTypeName();
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderAdjustmentControl = Session.getModelController(OrderAdjustmentControl.class);
            var orderAdjustmentTypeName = spec.getOrderAdjustmentTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                orderAdjustmentType = orderAdjustmentControl.getOrderAdjustmentTypeByName(orderType, orderAdjustmentTypeName);
            } else { // EditMode.UPDATE
                orderAdjustmentType = orderAdjustmentControl.getOrderAdjustmentTypeByNameForUpdate(orderType, orderAdjustmentTypeName);
            }

            if(orderAdjustmentType != null) {
                result.setOrderAdjustmentType(orderAdjustmentControl.getOrderAdjustmentTypeTransfer(getUserVisit(), orderAdjustmentType));
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderAdjustmentTypeName.name(), orderTypeName, orderAdjustmentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderAdjustmentType;
    }

    @Override
    public OrderAdjustmentType getLockEntity(OrderAdjustmentType orderAdjustmentType) {
        return orderAdjustmentType;
    }

    @Override
    public void fillInResult(EditOrderAdjustmentTypeResult result, OrderAdjustmentType orderAdjustmentType) {
        var orderAdjustmentControl = Session.getModelController(OrderAdjustmentControl.class);

        result.setOrderAdjustmentType(orderAdjustmentControl.getOrderAdjustmentTypeTransfer(getUserVisit(), orderAdjustmentType));
    }

    @Override
    public void doLock(OrderAdjustmentTypeEdit edit, OrderAdjustmentType orderAdjustmentType) {
        var orderAdjustmentControl = Session.getModelController(OrderAdjustmentControl.class);
        var orderAdjustmentTypeDescription = orderAdjustmentControl.getOrderAdjustmentTypeDescription(orderAdjustmentType, getPreferredLanguage());
        var orderAdjustmentTypeDetail = orderAdjustmentType.getLastDetail();

        edit.setOrderAdjustmentTypeName(orderAdjustmentTypeDetail.getOrderAdjustmentTypeName());
        edit.setIsDefault(orderAdjustmentTypeDetail.getIsDefault().toString());
        edit.setSortOrder(orderAdjustmentTypeDetail.getSortOrder().toString());

        if(orderAdjustmentTypeDescription != null) {
            edit.setDescription(orderAdjustmentTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(OrderAdjustmentType orderAdjustmentType) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        var orderTypeName = spec.getOrderTypeName();
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderAdjustmentControl = Session.getModelController(OrderAdjustmentControl.class);
            var orderAdjustmentTypeName = edit.getOrderAdjustmentTypeName();
            var duplicateOrderAdjustmentType = orderAdjustmentControl.getOrderAdjustmentTypeByName(orderType, orderAdjustmentTypeName);

            if(duplicateOrderAdjustmentType != null && !orderAdjustmentType.equals(duplicateOrderAdjustmentType)) {
                addExecutionError(ExecutionErrors.DuplicateOrderAdjustmentTypeName.name(), orderTypeName, orderAdjustmentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }
    }

    @Override
    public void doUpdate(OrderAdjustmentType orderAdjustmentType) {
        var orderAdjustmentControl = Session.getModelController(OrderAdjustmentControl.class);
        var partyPK = getPartyPK();
        var orderAdjustmentTypeDetailValue = orderAdjustmentControl.getOrderAdjustmentTypeDetailValueForUpdate(orderAdjustmentType);
        var orderAdjustmentTypeDescription = orderAdjustmentControl.getOrderAdjustmentTypeDescriptionForUpdate(orderAdjustmentType, getPreferredLanguage());
        var description = edit.getDescription();

        orderAdjustmentTypeDetailValue.setOrderAdjustmentTypeName(edit.getOrderAdjustmentTypeName());
        orderAdjustmentTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        orderAdjustmentTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        orderAdjustmentControl.updateOrderAdjustmentTypeFromValue(orderAdjustmentTypeDetailValue, partyPK);

        if(orderAdjustmentTypeDescription == null && description != null) {
            orderAdjustmentControl.createOrderAdjustmentTypeDescription(orderAdjustmentType, getPreferredLanguage(), description, partyPK);
        } else {
            if(orderAdjustmentTypeDescription != null && description == null) {
                orderAdjustmentControl.deleteOrderAdjustmentTypeDescription(orderAdjustmentTypeDescription, partyPK);
            } else {
                if(orderAdjustmentTypeDescription != null && description != null) {
                    var orderAdjustmentTypeDescriptionValue = orderAdjustmentControl.getOrderAdjustmentTypeDescriptionValue(orderAdjustmentTypeDescription);

                    orderAdjustmentTypeDescriptionValue.setDescription(description);
                    orderAdjustmentControl.updateOrderAdjustmentTypeDescriptionFromValue(orderAdjustmentTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
