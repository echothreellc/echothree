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

package com.echothree.model.control.sales.server.logic;

import com.echothree.model.control.customer.common.exception.UnknownCustomerTypeShippingMethodException;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.order.server.control.OrderShipmentGroupControl;
import com.echothree.model.control.order.server.logic.BaseOrderShipmentGroupLogic;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderShipmentGroup;
import com.echothree.model.data.order.server.value.OrderShipmentGroupDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SalesOrderShipmentGroupLogic
        extends BaseOrderShipmentGroupLogic {

    protected SalesOrderShipmentGroupLogic() {
        super();
    }

    public static SalesOrderShipmentGroupLogic getInstance() {
        return CDI.current().select(SalesOrderShipmentGroupLogic.class).get();
    }
    
    /**
     * Verify that the CustomerType is authorized to use the ShippingMethod. If there are no CustomerTypeShippingMethods
     * for any ShippingMethod then it is assumed they're authorized.
     *
     * @param eea Required.
     * @param customerType Required.
     * @param shippingMethod Required.
     */
    public void checkCustomerTypeShippingMethod(final ExecutionErrorAccumulator eea, final CustomerType customerType,
            final ShippingMethod shippingMethod) {
        var customerControl = Session.getModelController(CustomerControl.class);

        if(!customerControl.getCustomerTypeShippingMethodExists(customerType, shippingMethod)
                && customerControl.countCustomerTypeShippingMethodsByCustomerType(customerType) != 0) {
            handleExecutionError(UnknownCustomerTypeShippingMethodException.class, eea, ExecutionErrors.UnknownCustomerTypeShippingMethod.name(),
                    customerType.getLastDetail().getCustomerTypeName(), shippingMethod.getLastDetail().getShippingMethodName());
        }
    }

    /**
     * Create a new Order Shipment Group for a given Order.
     *
     * @param session Required.
     * @param eea Required.
     * @param order Required.
     * @param orderShipmentGroupSequence Optional.
     * @param itemDeliveryType Required.
     * @param isDefault Required.
     * @param partyContactMechanism Optional.
     * @param shippingMethod Optional.
     * @param holdUntilComplete Required.
     * @param createdBy Required.
     * @return The newly created OrderShipmentGroup, or null if there was an error.
     */
    public OrderShipmentGroup createSalesOrderShipmentGroup(final Session session, final ExecutionErrorAccumulator eea,
            final Order order, Integer orderShipmentGroupSequence, final ItemDeliveryType itemDeliveryType, final Boolean isDefault,
            final PartyContactMechanism partyContactMechanism, final ShippingMethod shippingMethod, final Boolean holdUntilComplete,
            final PartyPK createdBy) {
        OrderShipmentGroup orderShipmentGroup = null;

        SalesOrderLogic.getInstance().checkOrderAvailableForModification(session, eea, order, createdBy);

        if(eea == null || !eea.hasExecutionErrors()) {
            orderShipmentGroup = createOrderShipmentGroup(eea, order, orderShipmentGroupSequence, itemDeliveryType, isDefault, partyContactMechanism,
                    shippingMethod, holdUntilComplete, createdBy);
        }

        return orderShipmentGroup;
    }

    public void updateSalesOrderShipmentGroupFromValue(final ExecutionErrorAccumulator eea, final OrderShipmentGroupDetailValue orderShipmentGroupDetailValue,
            final BasePK updatedBy) {
        var orderShipmentGroupControl = Session.getModelController(OrderShipmentGroupControl.class);

        orderShipmentGroupControl.updateOrderShipmentGroupFromValue(orderShipmentGroupDetailValue, updatedBy);
    }

}
