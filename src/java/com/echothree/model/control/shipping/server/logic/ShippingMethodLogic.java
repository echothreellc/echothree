// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.shipping.server.logic;

import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.evaluator.CachedSelector;
import com.echothree.model.control.selector.server.evaluator.SelectorCache;
import com.echothree.model.control.selector.server.evaluator.SelectorCacheFactory;
import com.echothree.model.control.selector.server.evaluator.ShippingMethodItemSelectorEvaluator;
import com.echothree.model.control.shipping.common.exception.ItemNotAcceptibleForShippingMethodException;
import com.echothree.model.control.shipping.common.exception.UnknownShippingMethodNameException;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class ShippingMethodLogic
    extends BaseLogic {

    private ShippingMethodLogic() {
        super();
    }

    private static class ShippingMethodLogicHolder {
        static ShippingMethodLogic instance = new ShippingMethodLogic();
    }

    public static ShippingMethodLogic getInstance() {
        return ShippingMethodLogicHolder.instance;
    }

    private ShippingMethod getShippingMethodByName(final ExecutionErrorAccumulator eea, final String shippingMethodName, final EntityPermission entityPermission) {
        var shippingControl = Session.getModelController(ShippingControl.class);
        ShippingMethod shippingMethod = null;

        shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName, entityPermission);

        if(shippingMethod == null) {
            handleExecutionError(UnknownShippingMethodNameException.class, eea, ExecutionErrors.UnknownShippingMethodName.name(), shippingMethodName);
        }

        return shippingMethod;
    }

    public ShippingMethod getShippingMethodByName(final ExecutionErrorAccumulator eea, final String shippingMethodName) {
        return getShippingMethodByName(eea, shippingMethodName, EntityPermission.READ_ONLY);
    }

    public ShippingMethod getShippingMethodByNameForUpdate(final ExecutionErrorAccumulator eea, final String shippingMethodName) {
        return getShippingMethodByName(eea, shippingMethodName, EntityPermission.READ_WRITE);
    }
    
    public void checkAcceptanceOfItem(final Session session, final ExecutionErrorAccumulator eea, final SelectorCache selectorCache,
            final ShippingMethod shippingMethod, final Item item, final BasePK evaluatedBy) {
        Selector selector = shippingMethod.getLastDetail().getItemSelector();
        
        if(selector != null) {
            CachedSelector cachedSelector = selectorCache.getSelector(selector);
            
            if(!new ShippingMethodItemSelectorEvaluator(session, evaluatedBy).evaluate(cachedSelector, item)) {
                handleExecutionError(ItemNotAcceptibleForShippingMethodException.class, eea, ExecutionErrors.ItemNotAcceptibleForShippingMethod.name(),
                        shippingMethod.getLastDetail().getShippingMethodName(), item.getLastDetail().getItemName());
            }
        }
    }
    
    public void checkAcceptanceOfItem(final Session session, final ExecutionErrorAccumulator eea, final ShippingMethod shippingMethod, final Item item,
            final BasePK evaluatedBy) {
        Selector selector = shippingMethod.getLastDetail().getItemSelector();
        
        if(selector != null) {
            SelectorCache selectorCache = SelectorCacheFactory.getInstance().getSelectorCache(session, SelectorKinds.ITEM.name(),
                    SelectorTypes.SHIPPING_METHOD.name());
            
            checkAcceptanceOfItem(session, eea, selectorCache, shippingMethod, item, evaluatedBy);
        }
    }
    
    public void checkAcceptanceOfItems(final Session session, final ExecutionErrorAccumulator eea, final ShippingMethod shippingMethod, final Set<Item> items,
            final BasePK evaluatedBy) {
        Selector selector = shippingMethod.getLastDetail().getItemSelector();
        
        if(selector != null) {
            SelectorCache selectorCache = SelectorCacheFactory.getInstance().getSelectorCache(session, SelectorKinds.ITEM.name(),
                    SelectorTypes.SHIPPING_METHOD.name());
            
            items.forEach((item) -> {
                checkAcceptanceOfItem(session, eea, selectorCache, shippingMethod, item, evaluatedBy);
            });
        }
    }
    
}
