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

package com.echothree.model.control.payment.server.logic;

import com.echothree.model.control.payment.common.exception.ItemNotAcceptibleForPaymentMethodException;
import com.echothree.model.control.payment.common.exception.UnknownPaymentMethodNameException;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.evaluator.PaymentMethodItemSelectorEvaluator;
import com.echothree.model.control.selector.server.evaluator.SelectorCache;
import com.echothree.model.control.selector.server.evaluator.SelectorCacheFactory;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PaymentMethodLogic
    extends BaseLogic {

    protected PaymentMethodLogic() {
        super();
    }

    public static PaymentMethodLogic getInstance() {
        return CDI.current().select(PaymentMethodLogic.class).get();
    }

    private PaymentMethod getPaymentMethodByName(final ExecutionErrorAccumulator eea, final String paymentMethodName, final EntityPermission entityPermission) {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
        var paymentMethod = paymentMethodControl.getPaymentMethodByName(paymentMethodName, entityPermission);

        if(paymentMethod == null) {
            handleExecutionError(UnknownPaymentMethodNameException.class, eea, ExecutionErrors.UnknownPaymentMethodName.name(), paymentMethodName);
        }

        return paymentMethod;
    }

    public PaymentMethod getPaymentMethodByName(final ExecutionErrorAccumulator eea, final String paymentMethodName) {
        return getPaymentMethodByName(eea, paymentMethodName, EntityPermission.READ_ONLY);
    }

    public PaymentMethod getPaymentMethodByNameForUpdate(final ExecutionErrorAccumulator eea, final String paymentMethodName) {
        return getPaymentMethodByName(eea, paymentMethodName, EntityPermission.READ_WRITE);
    }
    
    public void checkAcceptanceOfItem(final Session session, final ExecutionErrorAccumulator eea, final SelectorCache selectorCache,
            final PaymentMethod paymentMethod, final Item item, final BasePK evaluatedBy) {
        var selector = paymentMethod.getLastDetail().getItemSelector();
        
        if(selector != null) {
            var cachedSelector = selectorCache.getSelector(selector);
            
            if(!new PaymentMethodItemSelectorEvaluator(session, evaluatedBy).evaluate(cachedSelector, item)) {
                handleExecutionError(ItemNotAcceptibleForPaymentMethodException.class, eea, ExecutionErrors.ItemNotAcceptibleForPaymentMethod.name(),
                        paymentMethod.getLastDetail().getPaymentMethodName(), item.getLastDetail().getItemName());
            }
        }
    }
    
    public void checkAcceptanceOfItem(final Session session, final ExecutionErrorAccumulator eea, final PaymentMethod paymentMethod, final Item item,
            final BasePK evaluatedBy) {
        var selector = paymentMethod.getLastDetail().getItemSelector();
        
        if(selector != null) {
            var selectorCache = SelectorCacheFactory.getInstance().getSelectorCache(session, SelectorKinds.ITEM.name(),
                    SelectorTypes.PAYMENT_METHOD.name());
            
            checkAcceptanceOfItem(session, eea, selectorCache, paymentMethod, item, evaluatedBy);
        }
    }
    
    public void checkAcceptanceOfItems(final Session session, final ExecutionErrorAccumulator eea, final PaymentMethod paymentMethod, final Set<Item> items,
            final BasePK evaluatedBy) {
        var selector = paymentMethod.getLastDetail().getItemSelector();
        
        if(selector != null) {
            var selectorCache = SelectorCacheFactory.getInstance().getSelectorCache(session, SelectorKinds.ITEM.name(),
                    SelectorTypes.PAYMENT_METHOD.name());
            
            items.forEach((item) -> {
                checkAcceptanceOfItem(session, eea, selectorCache, paymentMethod, item, evaluatedBy);
            });
        }
    }
    
}
