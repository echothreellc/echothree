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

import com.echothree.control.user.order.common.edit.OrderAliasTypeEdit;
import com.echothree.control.user.order.common.edit.OrderEditFactory;
import com.echothree.control.user.order.common.form.EditOrderAliasTypeForm;
import com.echothree.control.user.order.common.result.EditOrderAliasTypeResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderAliasTypeSpec;
import com.echothree.model.control.order.server.control.OrderAliasControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderAliasType;
import com.echothree.model.data.order.server.entity.OrderType;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditOrderAliasTypeCommand
        extends BaseAbstractEditCommand<OrderAliasTypeSpec, OrderAliasTypeEdit, EditOrderAliasTypeResult, OrderAliasType, OrderAliasType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderAliasType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditOrderAliasTypeCommand */
    public EditOrderAliasTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditOrderAliasTypeResult getResult() {
        return OrderResultFactory.getEditOrderAliasTypeResult();
    }

    @Override
    public OrderAliasTypeEdit getEdit() {
        return OrderEditFactory.getOrderAliasTypeEdit();
    }

    OrderType orderType;

    @Override
    public OrderAliasType getEntity(EditOrderAliasTypeResult result) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        OrderAliasType orderAliasType = null;
        var orderTypeName = spec.getOrderTypeName();

        orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderAliasControl = Session.getModelController(OrderAliasControl.class);
            var orderAliasTypeName = spec.getOrderAliasTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                orderAliasType = orderAliasControl.getOrderAliasTypeByName(orderType, orderAliasTypeName);
            } else { // EditMode.UPDATE
                orderAliasType = orderAliasControl.getOrderAliasTypeByNameForUpdate(orderType, orderAliasTypeName);
            }

            if(orderAliasType != null) {
                result.setOrderAliasType(orderAliasControl.getOrderAliasTypeTransfer(getUserVisit(), orderAliasType));
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderAliasTypeName.name(), orderTypeName, orderAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderAliasType;
    }

    @Override
    public OrderAliasType getLockEntity(OrderAliasType orderAliasType) {
        return orderAliasType;
    }

    @Override
    public void fillInResult(EditOrderAliasTypeResult result, OrderAliasType orderAliasType) {
        var orderAliasControl = Session.getModelController(OrderAliasControl.class);

        result.setOrderAliasType(orderAliasControl.getOrderAliasTypeTransfer(getUserVisit(), orderAliasType));
    }

    @Override
    public void doLock(OrderAliasTypeEdit edit, OrderAliasType orderAliasType) {
        var orderAliasControl = Session.getModelController(OrderAliasControl.class);
        var orderAliasTypeDescription = orderAliasControl.getOrderAliasTypeDescription(orderAliasType, getPreferredLanguage());
        var orderAliasTypeDetail = orderAliasType.getLastDetail();

        edit.setOrderAliasTypeName(orderAliasTypeDetail.getOrderAliasTypeName());
        edit.setValidationPattern(orderAliasTypeDetail.getValidationPattern());
        edit.setIsDefault(orderAliasTypeDetail.getIsDefault().toString());
        edit.setSortOrder(orderAliasTypeDetail.getSortOrder().toString());

        if(orderAliasTypeDescription != null) {
            edit.setDescription(orderAliasTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(OrderAliasType orderAliasType) {
        var orderAliasControl = Session.getModelController(OrderAliasControl.class);
        var orderAliasTypeName = edit.getOrderAliasTypeName();
        var duplicateOrderAliasType = orderAliasControl.getOrderAliasTypeByName(orderType, orderAliasTypeName);

        if(duplicateOrderAliasType != null && !orderAliasType.equals(duplicateOrderAliasType)) {
            addExecutionError(ExecutionErrors.DuplicateOrderAliasTypeName.name(), spec.getOrderTypeName(), orderAliasTypeName);
        }
    }

    @Override
    public void doUpdate(OrderAliasType orderAliasType) {
        var orderAliasControl = Session.getModelController(OrderAliasControl.class);
        var partyPK = getPartyPK();
        var orderAliasTypeDetailValue = orderAliasControl.getOrderAliasTypeDetailValueForUpdate(orderAliasType);
        var orderAliasTypeDescription = orderAliasControl.getOrderAliasTypeDescriptionForUpdate(orderAliasType, getPreferredLanguage());
        var description = edit.getDescription();

        orderAliasTypeDetailValue.setOrderAliasTypeName(edit.getOrderAliasTypeName());
        orderAliasTypeDetailValue.setValidationPattern(edit.getValidationPattern());
        orderAliasTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        orderAliasTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        orderAliasControl.updateOrderAliasTypeFromValue(orderAliasTypeDetailValue, partyPK);

        if(orderAliasTypeDescription == null && description != null) {
            orderAliasControl.createOrderAliasTypeDescription(orderAliasType, getPreferredLanguage(), description, partyPK);
        } else if(orderAliasTypeDescription != null && description == null) {
            orderAliasControl.deleteOrderAliasTypeDescription(orderAliasTypeDescription, partyPK);
        } else if(orderAliasTypeDescription != null && description != null) {
            var orderAliasTypeDescriptionValue = orderAliasControl.getOrderAliasTypeDescriptionValue(orderAliasTypeDescription);

            orderAliasTypeDescriptionValue.setDescription(description);
            orderAliasControl.updateOrderAliasTypeDescriptionFromValue(orderAliasTypeDescriptionValue, partyPK);
        }
    }

}
