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

import com.echothree.control.user.order.common.spec.OrderPriorityUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.order.common.exception.DuplicateOrderPriorityNameException;
import com.echothree.model.control.order.common.exception.UnknownDefaultOrderPriorityException;
import com.echothree.model.control.order.common.exception.UnknownDefaultOrderTypeException;
import com.echothree.model.control.order.common.exception.UnknownOrderPriorityNameException;
import com.echothree.model.control.order.server.control.OrderPriorityControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.value.OrderPriorityDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class OrderPriorityLogic
        extends BaseLogic {

    protected OrderPriorityLogic() {
        super();
    }

    public static OrderPriorityLogic getInstance() {
        return CDI.current().select(OrderPriorityLogic.class).get();
    }

    public OrderPriority createOrderPriority(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderPriorityName,
            final Integer priority, final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var orderType = OrderTypeLogic.getInstance().getOrderTypeByName(eea, orderTypeName);
        OrderPriority orderPriority = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            orderPriority = createOrderPriority(eea, orderType, orderPriorityName, priority, isDefault, sortOrder, language, description, createdBy);
        }

        return orderPriority;
    }

    public OrderPriority createOrderPriority(final ExecutionErrorAccumulator eea, final OrderType orderType, final String orderPriorityName,
            final Integer priority, final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var orderPriorityControl = Session.getModelController(OrderPriorityControl.class);
        var orderPriority = orderPriorityControl.getOrderPriorityByName(orderType, orderPriorityName);

        if(orderPriority == null) {
            orderPriority = orderPriorityControl.createOrderPriority(orderType, orderPriorityName, priority, isDefault, sortOrder, createdBy);

            if(description != null) {
                orderPriorityControl.createOrderPriorityDescription(orderPriority, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateOrderPriorityNameException.class, eea, ExecutionErrors.DuplicateOrderPriorityName.name(),
                    orderType.getLastDetail().getOrderTypeName(), orderPriorityName);
        }

        return orderPriority;
    }

    public OrderPriority getOrderPriorityByName(final ExecutionErrorAccumulator eea, final OrderType orderType, final String orderPriorityName,
            final EntityPermission entityPermission) {
        var orderPriorityControl = Session.getModelController(OrderPriorityControl.class);
        var orderPriority = orderPriorityControl.getOrderPriorityByName(orderType, orderPriorityName, entityPermission);

        if(orderPriority == null) {
            handleExecutionError(UnknownOrderPriorityNameException.class, eea, ExecutionErrors.UnknownOrderPriorityName.name(),
                    orderType.getLastDetail().getOrderTypeName(), orderPriorityName);
        }

        return orderPriority;
    }

    public OrderPriority getOrderPriorityByName(final ExecutionErrorAccumulator eea, final OrderType orderType, final String orderPriorityName) {
        return getOrderPriorityByName(eea, orderType, orderPriorityName, EntityPermission.READ_ONLY);
    }

    public OrderPriority getOrderPriorityByNameForUpdate(final ExecutionErrorAccumulator eea, final OrderType orderType, final String orderPriorityName) {
        return getOrderPriorityByName(eea, orderType, orderPriorityName, EntityPermission.READ_WRITE);
    }

    public OrderPriority getOrderPriorityByName(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderPriorityName,
            final EntityPermission entityPermission) {
        var orderType = OrderTypeLogic.getInstance().getOrderTypeByName(eea, orderTypeName);
        OrderPriority orderPriority = null;

        if(!eea.hasExecutionErrors()) {
            orderPriority = getOrderPriorityByName(eea, orderType, orderPriorityName, entityPermission);
        }

        return orderPriority;
    }

    public OrderPriority getOrderPriorityByName(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderPriorityName) {
        return getOrderPriorityByName(eea, orderTypeName, orderPriorityName, EntityPermission.READ_ONLY);
    }

    public OrderPriority getOrderPriorityByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderPriorityName) {
        return getOrderPriorityByName(eea, orderTypeName, orderPriorityName, EntityPermission.READ_WRITE);
    }

    public OrderPriority getOrderPriorityByUniversalSpec(final ExecutionErrorAccumulator eea, final OrderPriorityUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var orderPriorityControl = Session.getModelController(OrderPriorityControl.class);
        var orderTypeName = universalSpec.getOrderTypeName();
        var orderPriorityName = universalSpec.getOrderPriorityName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(orderTypeName, orderPriorityName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        OrderPriority orderPriority = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            OrderType orderType = null;

            if(orderTypeName == null) {
                if(allowDefault) {
                    var orderTypeControl = Session.getModelController(OrderTypeControl.class);
                    orderType = orderTypeControl.getDefaultOrderType();

                    if(orderType == null) {
                        handleExecutionError(UnknownDefaultOrderTypeException.class, eea, ExecutionErrors.UnknownDefaultOrderType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                orderType = OrderTypeLogic.getInstance().getOrderTypeByName(eea, orderTypeName);
            }

            if(!eea.hasExecutionErrors()) {
                if(orderPriorityName == null) {
                    if(allowDefault) {
                        orderPriority = orderPriorityControl.getDefaultOrderPriority(orderType, entityPermission);

                        if(orderPriority == null) {
                            handleExecutionError(UnknownDefaultOrderPriorityException.class, eea, ExecutionErrors.UnknownDefaultOrderPriority.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    orderPriority = getOrderPriorityByName(eea, orderType, orderPriorityName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.OrderPriority.name());

            if(!eea.hasExecutionErrors()) {
                orderPriority = orderPriorityControl.getOrderPriorityByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return orderPriority;
    }

    public OrderPriority getOrderPriorityByUniversalSpec(final ExecutionErrorAccumulator eea, final OrderPriorityUniversalSpec universalSpec,
            boolean allowDefault) {
        return getOrderPriorityByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public OrderPriority getOrderPriorityByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final OrderPriorityUniversalSpec universalSpec,
            boolean allowDefault) {
        return getOrderPriorityByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateOrderPriorityFromValue(final ExecutionErrorAccumulator eea, OrderPriorityDetailValue orderPriorityDetailValue,
            BasePK updatedBy) {
        var orderPriorityControl = Session.getModelController(OrderPriorityControl.class);

        orderPriorityControl.updateOrderPriorityFromValue(orderPriorityDetailValue, updatedBy);
    }

    public void deleteOrderPriority(final ExecutionErrorAccumulator eea, final OrderPriority orderPriority,
            final BasePK deletedBy) {
        var orderPriorityControl = Session.getModelController(OrderPriorityControl.class);

        orderPriorityControl.deleteOrderPriority(orderPriority, deletedBy);
    }

}
