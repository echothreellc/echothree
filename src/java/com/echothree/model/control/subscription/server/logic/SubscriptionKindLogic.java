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

package com.echothree.model.control.subscription.server.logic;

import com.echothree.control.user.subscription.common.spec.SubscriptionKindUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.subscription.common.exception.UnknownDefaultSubscriptionKindException;
import com.echothree.model.control.subscription.common.exception.UnknownSubscriptionKindNameException;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.subscription.server.entity.SubscriptionKind;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class SubscriptionKindLogic
        extends BaseLogic {

    @Inject
    SubscriptionControl subscriptionControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected SubscriptionKindLogic() {
        super();
    }

    public static SubscriptionKindLogic getInstance() {
        return CDI.current().select(SubscriptionKindLogic.class).get();
    }

    public SubscriptionKind getSubscriptionKindByName(final ExecutionErrorAccumulator eea, final String subscriptionKindName,
            final EntityPermission entityPermission) {
        var subscriptionKind = subscriptionControl.getSubscriptionKindByName(subscriptionKindName, entityPermission);

        if(subscriptionKind == null) {
            handleExecutionError(UnknownSubscriptionKindNameException.class, eea, ExecutionErrors.UnknownSubscriptionKindName.name(), subscriptionKindName);
        }

        return subscriptionKind;
    }

    public SubscriptionKind getSubscriptionKindByName(final ExecutionErrorAccumulator eea, final String subscriptionKindName) {
        return getSubscriptionKindByName(eea, subscriptionKindName, EntityPermission.READ_ONLY);
    }

    public SubscriptionKind getSubscriptionKindByNameForUpdate(final ExecutionErrorAccumulator eea, final String subscriptionKindName) {
        return getSubscriptionKindByName(eea, subscriptionKindName, EntityPermission.READ_WRITE);
    }

    public SubscriptionKind getSubscriptionKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SubscriptionKindUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        SubscriptionKind subscriptionKind = null;
        var subscriptionKindName = universalSpec.getSubscriptionKindName();
        var parameterCount = (subscriptionKindName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    subscriptionKind = subscriptionControl.getDefaultSubscriptionKind(entityPermission);

                    if(subscriptionKind == null) {
                        handleExecutionError(UnknownDefaultSubscriptionKindException.class, eea, ExecutionErrors.UnknownDefaultSubscriptionKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(subscriptionKindName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.SubscriptionKind.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        subscriptionKind = subscriptionControl.getSubscriptionKindByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    subscriptionKind = getSubscriptionKindByName(eea, subscriptionKindName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return subscriptionKind;
    }

    public SubscriptionKind getSubscriptionKindByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SubscriptionKindUniversalSpec universalSpec, boolean allowDefault) {
        return getSubscriptionKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SubscriptionKind getSubscriptionKindByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final SubscriptionKindUniversalSpec universalSpec, boolean allowDefault) {
        return getSubscriptionKindByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
