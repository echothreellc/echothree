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

package com.echothree.model.control.order.server.logic;

import com.echothree.control.user.order.common.spec.OrderLineAdjustmentTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.order.common.exception.UnknownDefaultOrderLineAdjustmentTypeException;
import com.echothree.model.control.order.common.exception.UnknownDefaultOrderTypeException;
import com.echothree.model.control.order.common.exception.UnknownOrderLineAdjustmentTypeNameException;
import com.echothree.model.control.order.server.control.OrderLineAdjustmentControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentType;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class OrderLineAdjustmentTypeLogic
        extends BaseLogic {

    protected OrderLineAdjustmentTypeLogic() {
        super();
    }

    @Inject
    OrderLineAdjustmentControl orderLineAdjustmentControl;
    
    @Inject
    OrderTypeControl orderTypeControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    OrderTypeLogic orderTypeLogic;

    public OrderLineAdjustmentType getOrderLineAdjustmentTypeByName(final ExecutionErrorAccumulator eea, final OrderType orderType,
            final String orderLineAdjustmentTypeName, final EntityPermission entityPermission) {
        var orderLineAdjustmentType = orderLineAdjustmentControl.getOrderLineAdjustmentTypeByName(orderType, orderLineAdjustmentTypeName, entityPermission);

        if(orderLineAdjustmentType == null) {
            handleExecutionError(UnknownOrderLineAdjustmentTypeNameException.class, eea, ExecutionErrors.UnknownOrderLineAdjustmentTypeName.name(),
                    orderType.getLastDetail().getOrderTypeName(), orderLineAdjustmentTypeName);
        }

        return orderLineAdjustmentType;
    }

    public OrderLineAdjustmentType getOrderLineAdjustmentTypeByName(final ExecutionErrorAccumulator eea, final OrderType orderType,
            final String orderLineAdjustmentTypeName) {
        return getOrderLineAdjustmentTypeByName(eea, orderType, orderLineAdjustmentTypeName, EntityPermission.READ_ONLY);
    }

    public OrderLineAdjustmentType getOrderLineAdjustmentTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final OrderType orderType,
            final String orderLineAdjustmentTypeName) {
        return getOrderLineAdjustmentTypeByName(eea, orderType, orderLineAdjustmentTypeName, EntityPermission.READ_WRITE);
    }

    public OrderLineAdjustmentType getOrderLineAdjustmentTypeByName(final ExecutionErrorAccumulator eea, final String orderTypeName,
            final String orderLineAdjustmentTypeName, final EntityPermission entityPermission) {
        var orderType = orderTypeLogic.getOrderTypeByName(eea, orderTypeName);
        OrderLineAdjustmentType orderLineAdjustmentType = null;

        if(!eea.hasExecutionErrors()) {
            orderLineAdjustmentType = getOrderLineAdjustmentTypeByName(eea, orderType, orderLineAdjustmentTypeName, entityPermission);
        }

        return orderLineAdjustmentType;
    }

    public OrderLineAdjustmentType getOrderLineAdjustmentTypeByName(final ExecutionErrorAccumulator eea, final String orderTypeName,
            final String orderLineAdjustmentTypeName) {
        return getOrderLineAdjustmentTypeByName(eea, orderTypeName, orderLineAdjustmentTypeName, EntityPermission.READ_ONLY);
    }

    public OrderLineAdjustmentType getOrderLineAdjustmentTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderTypeName,
            final String orderLineAdjustmentTypeName) {
        return getOrderLineAdjustmentTypeByName(eea, orderTypeName, orderLineAdjustmentTypeName, EntityPermission.READ_WRITE);
    }

    public OrderLineAdjustmentType getOrderLineAdjustmentTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final OrderLineAdjustmentTypeUniversalSpec universalSpec, final boolean allowDefault, final EntityPermission entityPermission) {
        var orderTypeName = universalSpec.getOrderTypeName();
        var orderLineAdjustmentTypeName = universalSpec.getOrderLineAdjustmentTypeName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(orderTypeName, orderLineAdjustmentTypeName);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        OrderLineAdjustmentType orderLineAdjustmentType = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            OrderType orderType = null;

            if(orderTypeName == null) {
                if(allowDefault) {
                    orderType = orderTypeControl.getDefaultOrderType();

                    if(orderType == null) {
                        handleExecutionError(UnknownDefaultOrderTypeException.class, eea, ExecutionErrors.UnknownDefaultOrderType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                orderType = orderTypeLogic.getOrderTypeByName(eea, orderTypeName);
            }

            if(!eea.hasExecutionErrors()) {
                if(orderLineAdjustmentTypeName == null) {
                    if(allowDefault) {
                        orderLineAdjustmentType = orderLineAdjustmentControl.getDefaultOrderLineAdjustmentType(orderType, entityPermission);

                        if(orderLineAdjustmentType == null) {
                            handleExecutionError(UnknownDefaultOrderLineAdjustmentTypeException.class, eea, ExecutionErrors.UnknownDefaultOrderLineAdjustmentType.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    orderLineAdjustmentType = getOrderLineAdjustmentTypeByName(eea, orderType, orderLineAdjustmentTypeName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.OrderLineAdjustmentType.name());

            if(!eea.hasExecutionErrors()) {
                orderLineAdjustmentType = orderLineAdjustmentControl.getOrderLineAdjustmentTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return orderLineAdjustmentType;
    }

    public OrderLineAdjustmentType getOrderLineAdjustmentTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final OrderLineAdjustmentTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getOrderLineAdjustmentTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public OrderLineAdjustmentType getOrderLineAdjustmentTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final OrderLineAdjustmentTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getOrderLineAdjustmentTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
