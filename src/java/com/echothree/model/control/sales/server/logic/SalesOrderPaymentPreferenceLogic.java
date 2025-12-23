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

package com.echothree.model.control.sales.server.logic;

import com.echothree.model.control.customer.common.exception.UnknownCustomerTypePaymentMethodException;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.sales.common.exception.BillToPartyMustMatchPartyPaymentMethodsPartyException;
import com.echothree.model.control.sales.common.exception.BillToRequiredWhenUsingPartyPaymentMethodException;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderPaymentPreference;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SalesOrderPaymentPreferenceLogic
        extends BaseLogic {

    protected SalesOrderPaymentPreferenceLogic() {
        super();
    }

    public static SalesOrderPaymentPreferenceLogic getInstance() {
        return CDI.current().select(SalesOrderPaymentPreferenceLogic.class).get();
    }
    
    /**
     * Verify that the CustomerType is authorized to use the PaymentMethod. If there are no CustomerTypePaymentMethods for any PaymentMethod,
     * then it is assumed they're authorized.
     * 
     * @param eea Required.
     * @param customerType Required.
     * @param paymentMethod Required.
     */
    public void checkCustomerTypePaymentMethod(final ExecutionErrorAccumulator eea, final CustomerType customerType,
            final PaymentMethod paymentMethod) {
        var customerControl = Session.getModelController(CustomerControl.class);
        
        if(!customerControl.getCustomerTypePaymentMethodExists(customerType, paymentMethod)
                && customerControl.countCustomerTypePaymentMethodsByCustomerType(customerType) != 0) {
            handleExecutionError(UnknownCustomerTypePaymentMethodException.class, eea, ExecutionErrors.UnknownCustomerTypePaymentMethod.name(),
                    customerType.getLastDetail().getCustomerTypeName(), paymentMethod.getLastDetail().getPaymentMethodName());
        }
    }
    
    /**
     * Create an Order Payment Preference for a given Order.
     * 
     * @param eea Required.
     * @param order Required.
     * @param orderPaymentPreferenceSequence Optional.
     * @param paymentMethod Required for all types except CREDIT_CARDs, GIFT_CARDs, GIFT_CERTIFICATEs.
     * @param partyPaymentMethod Required for CREDIT_CARDs, GIFT_CARDs, GIFT_CERTIFICATEs, otherwise null.
     * @param wasPresent Required for CREDIT_CARD, otherwise null.
     * @param maximumAmount Optional.
     * @param sortOrder Required.
     * @param createdBy Required.
     * @return The newly created OrderPaymentPreference, or null if there was an error.
     */
    public OrderPaymentPreference createSalesOrderPaymentPreference(final Session session, final ExecutionErrorAccumulator eea, final Order order,
            final Integer orderPaymentPreferenceSequence, final PaymentMethod paymentMethod, final PartyPaymentMethod partyPaymentMethod,
            final Boolean wasPresent, final Long maximumAmount, final Integer sortOrder, final PartyPK createdBy) {
        var salesOrderLogic = SalesOrderLogic.getInstance();
        OrderPaymentPreference orderPaymentPreference = null;
        
        salesOrderLogic.checkOrderAvailableForModification(session, eea, order, createdBy);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            var billTo = salesOrderLogic.getOrderBillToParty(order);
            var customerType = billTo == null ? null : salesOrderLogic.getCustomerTypeFromParty(billTo);

            if(customerType != null) {
                checkCustomerTypePaymentMethod(eea, customerType, paymentMethod);
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                if(partyPaymentMethod != null) {
                    if(billTo == null) {
                        // Order must have a bill to before a payment method that requires a partyPaymentMethod may be set.
                        handleExecutionError(BillToRequiredWhenUsingPartyPaymentMethodException.class, eea, ExecutionErrors.BillToRequiredWhenUsingPartyPaymentMethod.name());
                    } else if(!billTo.equals(partyPaymentMethod.getLastDetail().getParty())) {
                        // Verify partyPaymentMethod belongs to the BILL_TO Party on the order.
                        handleExecutionError(BillToPartyMustMatchPartyPaymentMethodsPartyException.class, eea, ExecutionErrors.BillToPartyMustMatchPartyPaymentMethodsParty.name());
                    }
                }

                if(eea == null || !eea.hasExecutionErrors()) {
                    orderPaymentPreference = OrderLogic.getInstance().createOrderPaymentPreference(session, eea, order, orderPaymentPreferenceSequence, paymentMethod,
                            partyPaymentMethod, wasPresent, maximumAmount, sortOrder, createdBy);
                }
            }
        }
        
        return orderPaymentPreference;
    }

}
