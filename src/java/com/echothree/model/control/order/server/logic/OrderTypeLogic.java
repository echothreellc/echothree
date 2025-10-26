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

package com.echothree.model.control.order.server.logic;

import com.echothree.control.user.order.common.spec.OrderTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.order.common.exception.DuplicateOrderTypeNameException;
import com.echothree.model.control.order.common.exception.UnknownDefaultOrderTypeException;
import com.echothree.model.control.order.common.exception.UnknownOrderTypeNameException;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.value.OrderTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class OrderTypeLogic
    extends BaseLogic {

    private OrderTypeLogic() {
        super();
    }

    private static class OrderTypeLogicHolder {
        static OrderTypeLogic instance = new OrderTypeLogic();
    }

    public static OrderTypeLogic getInstance() {
        return OrderTypeLogicHolder.instance;
    }

    public OrderType createOrderType(final ExecutionErrorAccumulator eea, final String orderTypeName,
            final SequenceType orderSequenceType, final Workflow orderWorkflow,
            final WorkflowEntrance orderWorkflowEntrance, final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType == null) {
            orderType = orderTypeControl.createOrderType(orderTypeName, orderSequenceType, orderWorkflow,
                    orderWorkflowEntrance, isDefault, sortOrder, createdBy);

            if(description != null) {
                orderTypeControl.createOrderTypeDescription(orderType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateOrderTypeNameException.class, eea, ExecutionErrors.DuplicateOrderTypeName.name(), orderTypeName);
        }

        return orderType;
    }

    public OrderType getOrderTypeByName(final ExecutionErrorAccumulator eea, final String orderTypeName,
            final EntityPermission entityPermission) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName, entityPermission);

        if(orderType == null) {
            handleExecutionError(UnknownOrderTypeNameException.class, eea, ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderType;
    }

    public OrderType getOrderTypeByName(final ExecutionErrorAccumulator eea, final String orderTypeName) {
        return getOrderTypeByName(eea, orderTypeName, EntityPermission.READ_ONLY);
    }

    public OrderType getOrderTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderTypeName) {
        return getOrderTypeByName(eea, orderTypeName, EntityPermission.READ_WRITE);
    }

    public OrderType getOrderTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final OrderTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        OrderType orderType = null;
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        var orderTypeName = universalSpec.getOrderTypeName();
        var parameterCount = (orderTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    orderType = orderTypeControl.getDefaultOrderType(entityPermission);

                    if(orderType == null) {
                        handleExecutionError(UnknownDefaultOrderTypeException.class, eea, ExecutionErrors.UnknownDefaultOrderType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(orderTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.OrderType.name());

                    if(!eea.hasExecutionErrors()) {
                        orderType = orderTypeControl.getOrderTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    orderType = getOrderTypeByName(eea, orderTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return orderType;
    }

    public OrderType getOrderTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final OrderTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getOrderTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public OrderType getOrderTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final OrderTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getOrderTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateOrderTypeFromValue(final OrderTypeDetailValue orderTypeDetailValue,
            final BasePK updatedBy) {
        final var orderTypeControl = Session.getModelController(OrderTypeControl.class);

        orderTypeControl.updateOrderTypeFromValue(orderTypeDetailValue, updatedBy);
    }
    
    public void deleteOrderType(final ExecutionErrorAccumulator eea, final OrderType orderType,
            final BasePK deletedBy) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);

        orderTypeControl.deleteOrderType(orderType, deletedBy);
    }

}
