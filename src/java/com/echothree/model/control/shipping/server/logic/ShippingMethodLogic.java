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

package com.echothree.model.control.shipping.server.logic;

import com.echothree.control.user.shipping.common.spec.ShippingMethodUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.evaluator.SelectorCache;
import com.echothree.model.control.selector.server.evaluator.SelectorCacheFactory;
import com.echothree.model.control.selector.server.evaluator.ShippingMethodItemSelectorEvaluator;
import com.echothree.model.control.shipping.common.exception.DuplicateShippingMethodNameException;
import com.echothree.model.control.shipping.common.exception.ItemNotAcceptibleForShippingMethodException;
import com.echothree.model.control.shipping.common.exception.UnknownShippingMethodNameException;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.shipping.server.value.ShippingMethodDetailValue;
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
public class ShippingMethodLogic
    extends BaseLogic {

    protected ShippingMethodLogic() {
        super();
    }

    public static ShippingMethodLogic getInstance() {
        return CDI.current().select(ShippingMethodLogic.class).get();
    }

    public ShippingMethod createShippingMethod(final ExecutionErrorAccumulator eea, final String shippingMethodName,
            final Selector geoCodeSelector, final Selector itemSelector, final Integer sortOrder, final Language language,
            final String description, final BasePK createdBy) {
        var shippingControl = Session.getModelController(ShippingControl.class);
        var shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);

        if(shippingMethod == null) {
            shippingMethod = shippingControl.createShippingMethod(shippingMethodName, geoCodeSelector, itemSelector,
                    sortOrder, createdBy);

            if(description != null) {
                shippingControl.createShippingMethodDescription(shippingMethod, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateShippingMethodNameException.class, eea, ExecutionErrors.DuplicateShippingMethodName.name(), shippingMethodName);
        }

        return shippingMethod;
    }
    
    private ShippingMethod getShippingMethodByName(final ExecutionErrorAccumulator eea, final String shippingMethodName,
            final EntityPermission entityPermission) {
        var shippingControl = Session.getModelController(ShippingControl.class);
        var shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName, entityPermission);

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

    public ShippingMethod getShippingMethodByUniversalSpec(final ExecutionErrorAccumulator eea, final ShippingMethodUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        ShippingMethod shippingMethod = null;
        var shippingControl = Session.getModelController(ShippingControl.class);
        var shippingMethodName = universalSpec.getShippingMethodName();
        var parameterCount = (shippingMethodName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(shippingMethodName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ShippingMethod.name());

                    if(!eea.hasExecutionErrors()) {
                        shippingMethod = shippingControl.getShippingMethodByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    shippingMethod = getShippingMethodByName(eea, shippingMethodName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return shippingMethod;
    }

    public ShippingMethod getShippingMethodByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ShippingMethodUniversalSpec universalSpec) {
        return getShippingMethodByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public ShippingMethod getShippingMethodByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ShippingMethodUniversalSpec universalSpec) {
        return getShippingMethodByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public void checkAcceptanceOfItem(final Session session, final ExecutionErrorAccumulator eea, final SelectorCache selectorCache,
            final ShippingMethod shippingMethod, final Item item, final BasePK evaluatedBy) {
        var selector = shippingMethod.getLastDetail().getItemSelector();
        
        if(selector != null) {
            var cachedSelector = selectorCache.getSelector(selector);
            
            if(!new ShippingMethodItemSelectorEvaluator(session, evaluatedBy).evaluate(cachedSelector, item)) {
                handleExecutionError(ItemNotAcceptibleForShippingMethodException.class, eea, ExecutionErrors.ItemNotAcceptibleForShippingMethod.name(),
                        shippingMethod.getLastDetail().getShippingMethodName(), item.getLastDetail().getItemName());
            }
        }
    }
    
    public void checkAcceptanceOfItem(final Session session, final ExecutionErrorAccumulator eea, final ShippingMethod shippingMethod, final Item item,
            final BasePK evaluatedBy) {
        var selector = shippingMethod.getLastDetail().getItemSelector();
        
        if(selector != null) {
            var selectorCache = SelectorCacheFactory.getInstance().getSelectorCache(session, SelectorKinds.ITEM.name(),
                    SelectorTypes.SHIPPING_METHOD.name());
            
            checkAcceptanceOfItem(session, eea, selectorCache, shippingMethod, item, evaluatedBy);
        }
    }
    
    public void checkAcceptanceOfItems(final Session session, final ExecutionErrorAccumulator eea, final ShippingMethod shippingMethod, final Set<Item> items,
            final BasePK evaluatedBy) {
        var selector = shippingMethod.getLastDetail().getItemSelector();
        
        if(selector != null) {
            var selectorCache = SelectorCacheFactory.getInstance().getSelectorCache(session, SelectorKinds.ITEM.name(),
                    SelectorTypes.SHIPPING_METHOD.name());
            
            items.forEach((item) -> {
                checkAcceptanceOfItem(session, eea, selectorCache, shippingMethod, item, evaluatedBy);
            });
        }
    }

    public void updateShippingMethodFromValue(final ExecutionErrorAccumulator eea, final ShippingMethodDetailValue shippingMethodDetailValue,
            final BasePK updatedBy) {
        var shippingControl = Session.getModelController(ShippingControl.class);

        shippingControl.updateShippingMethodFromValue(shippingMethodDetailValue, updatedBy);
    }

    public void deleteShippingMethod(final ExecutionErrorAccumulator eea, final ShippingMethod shippingMethod, final BasePK deletedBy) {
        var shippingControl = Session.getModelController(ShippingControl.class);

        shippingControl.deleteShippingMethod(shippingMethod, deletedBy);
    }
    
}
