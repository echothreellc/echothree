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

import com.echothree.control.user.order.common.spec.OrderTimeTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.order.common.exception.DuplicateOrderTimeTypeNameException;
import com.echothree.model.control.order.common.exception.UnknownDefaultOrderTimeTypeException;
import com.echothree.model.control.order.common.exception.UnknownDefaultOrderTypeException;
import com.echothree.model.control.order.common.exception.UnknownOrderTimeTypeNameException;
import com.echothree.model.control.order.server.control.OrderTimeControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.data.order.server.entity.OrderTimeType;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.value.OrderTimeTypeDetailValue;
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
public class OrderTimeTypeLogic
        extends BaseLogic {

    protected OrderTimeTypeLogic() {
        super();
    }

    public static OrderTimeTypeLogic getInstance() {
        return CDI.current().select(OrderTimeTypeLogic.class).get();
    }

    public OrderTimeType createOrderTimeType(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderTimeTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var orderType = OrderTypeLogic.getInstance().getOrderTypeByName(eea, orderTypeName);
        OrderTimeType orderTimeType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            orderTimeType = createOrderTimeType(eea, orderType, orderTimeTypeName, isDefault, sortOrder, language, description, createdBy);
        }

        return orderTimeType;
    }

    public OrderTimeType createOrderTimeType(final ExecutionErrorAccumulator eea, final OrderType orderType, final String orderTimeTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderTimeType = orderTimeControl.getOrderTimeTypeByName(orderType, orderTimeTypeName);

        if(orderTimeType == null) {
            orderTimeType = orderTimeControl.createOrderTimeType(orderType, orderTimeTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                orderTimeControl.createOrderTimeTypeDescription(orderTimeType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateOrderTimeTypeNameException.class, eea, ExecutionErrors.DuplicateOrderTimeTypeName.name(),
                    orderType.getLastDetail().getOrderTypeName(), orderTimeTypeName);
        }

        return orderTimeType;
    }

    public OrderTimeType getOrderTimeTypeByName(final ExecutionErrorAccumulator eea, final OrderType orderType, final String orderTimeTypeName,
            final EntityPermission entityPermission) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderTimeType = orderTimeControl.getOrderTimeTypeByName(orderType, orderTimeTypeName, entityPermission);

        if(orderTimeType == null) {
            handleExecutionError(UnknownOrderTimeTypeNameException.class, eea, ExecutionErrors.UnknownOrderTimeTypeName.name(),
                    orderType.getLastDetail().getOrderTypeName(), orderTimeTypeName);
        }

        return orderTimeType;
    }

    public OrderTimeType getOrderTimeTypeByName(final ExecutionErrorAccumulator eea, final OrderType orderType, final String orderTimeTypeName) {
        return getOrderTimeTypeByName(eea, orderType, orderTimeTypeName, EntityPermission.READ_ONLY);
    }

    public OrderTimeType getOrderTimeTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final OrderType orderType, final String orderTimeTypeName) {
        return getOrderTimeTypeByName(eea, orderType, orderTimeTypeName, EntityPermission.READ_WRITE);
    }

    public OrderTimeType getOrderTimeTypeByName(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderTimeTypeName,
            final EntityPermission entityPermission) {
        var orderType = OrderTypeLogic.getInstance().getOrderTypeByName(eea, orderTypeName);
        OrderTimeType orderTimeType = null;

        if(!eea.hasExecutionErrors()) {
            orderTimeType = getOrderTimeTypeByName(eea, orderType, orderTimeTypeName, entityPermission);
        }

        return orderTimeType;
    }

    public OrderTimeType getOrderTimeTypeByName(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderTimeTypeName) {
        return getOrderTimeTypeByName(eea, orderTypeName, orderTimeTypeName, EntityPermission.READ_ONLY);
    }

    public OrderTimeType getOrderTimeTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderTimeTypeName) {
        return getOrderTimeTypeByName(eea, orderTypeName, orderTimeTypeName, EntityPermission.READ_WRITE);
    }

    public OrderTimeType getOrderTimeTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final OrderTimeTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderTypeName = universalSpec.getOrderTypeName();
        var orderTimeTypeName = universalSpec.getOrderTimeTypeName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(orderTypeName, orderTimeTypeName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        OrderTimeType orderTimeType = null;

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
                if(orderTimeTypeName == null) {
                    if(allowDefault) {
                        orderTimeType = orderTimeControl.getDefaultOrderTimeType(orderType, entityPermission);

                        if(orderTimeType == null) {
                            handleExecutionError(UnknownDefaultOrderTimeTypeException.class, eea, ExecutionErrors.UnknownDefaultOrderTimeType.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    orderTimeType = getOrderTimeTypeByName(eea, orderType, orderTimeTypeName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.OrderTimeType.name());

            if(!eea.hasExecutionErrors()) {
                orderTimeType = orderTimeControl.getOrderTimeTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return orderTimeType;
    }

    public OrderTimeType getOrderTimeTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final OrderTimeTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getOrderTimeTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public OrderTimeType getOrderTimeTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final OrderTimeTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getOrderTimeTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateOrderTimeTypeFromValue(final ExecutionErrorAccumulator eea, OrderTimeTypeDetailValue orderTimeTypeDetailValue,
            BasePK updatedBy) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);

        orderTimeControl.updateOrderTimeTypeFromValue(orderTimeTypeDetailValue, updatedBy);
    }

    public void deleteOrderTimeType(final ExecutionErrorAccumulator eea, final OrderTimeType orderTimeType,
            final BasePK deletedBy) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);

        orderTimeControl.deleteOrderTimeType(orderTimeType, deletedBy);
    }

}
