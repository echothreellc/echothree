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

import com.echothree.control.user.filter.common.spec.FilterTypeUniversalSpec;
import com.echothree.control.user.subscription.common.spec.SubscriptionKindUniversalSpec;
import com.echothree.control.user.subscription.common.spec.SubscriptionTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterKindException;
import com.echothree.model.control.filter.common.exception.UnknownDefaultFilterTypeException;
import com.echothree.model.control.filter.server.logic.FilterKindLogic;
import com.echothree.model.control.subscription.common.exception.UnknownDefaultSubscriptionKindException;
import com.echothree.model.control.subscription.common.exception.UnknownDefaultSubscriptionTypeException;
import com.echothree.model.control.subscription.common.exception.UnknownSubscriptionTypeNameException;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.subscription.server.entity.SubscriptionKind;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class SubscriptionTypeLogic
        extends BaseLogic {

    @Inject
    SubscriptionControl subscriptionControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    ParameterUtils parameterUtils;

    @Inject
    SubscriptionKindLogic subscriptionKindLogic;

    protected SubscriptionTypeLogic() {
        super();
    }

    public static SubscriptionTypeLogic getInstance() {
        return CDI.current().select(SubscriptionTypeLogic.class).get();
    }

    public SubscriptionType getSubscriptionTypeByName(final ExecutionErrorAccumulator eea, final SubscriptionKind subscriptionKind,
            final String subscriptionTypeName, final EntityPermission entityPermission) {
        var subscriptionType = subscriptionControl.getSubscriptionTypeByName(subscriptionKind, subscriptionTypeName, entityPermission);

        if(subscriptionType == null) {
            handleExecutionError(UnknownSubscriptionTypeNameException.class, eea, ExecutionErrors.UnknownSubscriptionTypeName.name(),
                    subscriptionKind.getLastDetail().getSubscriptionKindName(), subscriptionTypeName);
        }

        return subscriptionType;
    }

    public SubscriptionType getSubscriptionTypeByName(final ExecutionErrorAccumulator eea, final SubscriptionKind subscriptionKind,
            final String subscriptionTypeName) {
        return getSubscriptionTypeByName(eea, subscriptionKind, subscriptionTypeName, EntityPermission.READ_ONLY);
    }

    public SubscriptionType getSubscriptionTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final SubscriptionKind subscriptionKind,
            final String subscriptionTypeName) {
        return getSubscriptionTypeByName(eea, subscriptionKind, subscriptionTypeName, EntityPermission.READ_WRITE);
    }

    public SubscriptionType getSubscriptionTypeByName(final ExecutionErrorAccumulator eea, final String subscriptionKindName,
            final String subscriptionTypeName, final EntityPermission entityPermission) {
        var subscriptionKind = subscriptionKindLogic.getSubscriptionKindByName(eea, subscriptionKindName);
        SubscriptionType subscriptionType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            subscriptionType = getSubscriptionTypeByName(eea, subscriptionKind, subscriptionTypeName, entityPermission);
        }

        return subscriptionType;
    }

    public SubscriptionType getSubscriptionTypeByName(final ExecutionErrorAccumulator eea, final String subscriptionKindName,
            final String subscriptionTypeName) {
        return getSubscriptionTypeByName(eea, subscriptionKindName, subscriptionTypeName, EntityPermission.READ_ONLY);
    }

    public SubscriptionType getSubscriptionTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String subscriptionKindName,
            final String subscriptionTypeName) {
        return getSubscriptionTypeByName(eea, subscriptionKindName, subscriptionTypeName, EntityPermission.READ_WRITE);
    }

    public SubscriptionType getSubscriptionTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final SubscriptionTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var subscriptionKindName = universalSpec.getSubscriptionKindName();
        var subscriptionTypeName = universalSpec.getSubscriptionTypeName();
        var nameParameterCount = parameterUtils.countNonNullParameters(subscriptionKindName, subscriptionTypeName);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        SubscriptionType subscriptionType = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            SubscriptionKind subscriptionKind = null;

            if(subscriptionKindName == null) {
                if(allowDefault) {
                    subscriptionKind = subscriptionControl.getDefaultSubscriptionKind();

                    if(subscriptionKind == null) {
                        handleExecutionError(UnknownDefaultSubscriptionKindException.class, eea, ExecutionErrors.UnknownDefaultSubscriptionKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                subscriptionKind = subscriptionKindLogic.getSubscriptionKindByName(eea, subscriptionKindName);
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                if(subscriptionTypeName == null) {
                    if(allowDefault) {
                        subscriptionType = subscriptionControl.getDefaultSubscriptionType(subscriptionKind, entityPermission);

                        if(subscriptionType == null) {
                            handleExecutionError(UnknownDefaultSubscriptionTypeException.class, eea, ExecutionErrors.UnknownDefaultSubscriptionType.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    subscriptionType = getSubscriptionTypeByName(eea, subscriptionKind, subscriptionTypeName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.SubscriptionType.name());

            if(eea == null || !eea.hasExecutionErrors()) {
                subscriptionType = subscriptionControl.getSubscriptionTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return subscriptionType;
    }

    public SubscriptionType getSubscriptionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SubscriptionTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getSubscriptionTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public SubscriptionType getSubscriptionTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final SubscriptionTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getSubscriptionTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteSubscriptionType(final ExecutionErrorAccumulator eea, final SubscriptionType subscriptionType,
            final BasePK deletedBy) {
        subscriptionControl.deleteSubscriptionType(subscriptionType, deletedBy);
    }

}
