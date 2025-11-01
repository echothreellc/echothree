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

package com.echothree.model.control.cancellationpolicy.server.logic;

import com.echothree.control.user.cancellationpolicy.common.spec.CancellationPolicyUniversalSpec;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.common.exception.DuplicateCancellationPolicyNameException;
import com.echothree.model.control.cancellationpolicy.common.exception.UnknownCancellationPolicyNameException;
import com.echothree.model.control.cancellationpolicy.common.exception.UnknownDefaultCancellationKindException;
import com.echothree.model.control.cancellationpolicy.common.exception.UnknownDefaultCancellationPolicyException;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderLineControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.MimeType;
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
public class CancellationPolicyLogic
        extends BaseLogic {

    protected CancellationPolicyLogic() {
        super();
    }

    public static CancellationPolicyLogic getInstance() {
        return CDI.current().select(CancellationPolicyLogic.class).get();
    }

    public CancellationPolicy createCancellationPolicy(final ExecutionErrorAccumulator eea, final String cancellationKindName, final String cancellationPolicyName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final String policyMimeTypeName,
            final String policy, final BasePK createdBy) {
        var cancellationKind = CancellationKindLogic.getInstance().getCancellationKindByName(eea, cancellationKindName);
        var policyMimeType = MimeTypeLogic.getInstance().checkMimeType(eea, policyMimeTypeName, policy, MimeTypeUsageTypes.TEXT.name(),
                ExecutionErrors.MissingRequiredPolicyMimeTypeName.name(), ExecutionErrors.MissingRequiredPolicy.name(),
                ExecutionErrors.UnknownPolicyMimeTypeName.name(), ExecutionErrors.UnknownPolicyMimeTypeUsage.name());
        CancellationPolicy cancellationPolicy = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            cancellationPolicy = createCancellationPolicy(eea, cancellationKind, cancellationPolicyName, isDefault, sortOrder, language, description, policyMimeType, policy, createdBy);
        }

        return cancellationPolicy;
    }

    public CancellationPolicy createCancellationPolicy(final ExecutionErrorAccumulator eea, final CancellationKind cancellationKind, final String cancellationPolicyName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final MimeType policyMimeType,
            final String policy, final BasePK createdBy) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);

        if(cancellationPolicy == null) {
            cancellationPolicy = cancellationPolicyControl.createCancellationPolicy(cancellationKind, cancellationPolicyName, isDefault, sortOrder, createdBy);

            if(description != null) {
                cancellationPolicyControl.createCancellationPolicyTranslation(cancellationPolicy, language, description, policyMimeType, policy, createdBy);
            }
        } else {
            handleExecutionError(DuplicateCancellationPolicyNameException.class, eea, ExecutionErrors.DuplicateCancellationPolicyName.name(), cancellationPolicyName);
        }
        return cancellationPolicy;
    }

    public CancellationPolicy getCancellationPolicyByName(final ExecutionErrorAccumulator eea, final CancellationKind cancellationKind, final String cancellationPolicyName,
            final EntityPermission entityPermission) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName, entityPermission);

        if(cancellationPolicy == null) {
            handleExecutionError(UnknownCancellationPolicyNameException.class, eea, ExecutionErrors.UnknownCancellationPolicyName.name(),
                    cancellationKind.getLastDetail().getCancellationKindName(), cancellationPolicyName);
        }

        return cancellationPolicy;
    }

    public CancellationPolicy getCancellationPolicyByName(final ExecutionErrorAccumulator eea, final CancellationKind cancellationKind, final String cancellationPolicyName) {
        return getCancellationPolicyByName(eea, cancellationKind, cancellationPolicyName, EntityPermission.READ_ONLY);
    }

    public CancellationPolicy getCancellationPolicyByNameForUpdate(final ExecutionErrorAccumulator eea, final CancellationKind cancellationKind, final String cancellationPolicyName) {
        return getCancellationPolicyByName(eea, cancellationKind, cancellationPolicyName, EntityPermission.READ_WRITE);
    }

    public CancellationPolicy getCancellationPolicyByName(final ExecutionErrorAccumulator eea, final String cancellationKindName, final String cancellationPolicyName,
            final EntityPermission entityPermission) {
        var cancellationKind = CancellationKindLogic.getInstance().getCancellationKindByName(eea, cancellationKindName);
        CancellationPolicy cancellationPolicy = null;

        if(!eea.hasExecutionErrors()) {
            cancellationPolicy = getCancellationPolicyByName(eea, cancellationKind, cancellationPolicyName, entityPermission);
        }

        return cancellationPolicy;
    }

    public CancellationPolicy getCancellationPolicyByName(final ExecutionErrorAccumulator eea, final String cancellationKindName, final String cancellationPolicyName) {
        return getCancellationPolicyByName(eea, cancellationKindName, cancellationPolicyName, EntityPermission.READ_ONLY);
    }

    public CancellationPolicy getCancellationPolicyByNameForUpdate(final ExecutionErrorAccumulator eea, final String cancellationKindName, final String cancellationPolicyName) {
        return getCancellationPolicyByName(eea, cancellationKindName, cancellationPolicyName, EntityPermission.READ_WRITE);
    }

    public CancellationPolicy getCancellationPolicyByUniversalSpec(final ExecutionErrorAccumulator eea, final CancellationPolicyUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var cancellationKindName = universalSpec.getCancellationKindName();
        var cancellationPolicyName = universalSpec.getCancellationPolicyName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(cancellationKindName, cancellationPolicyName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        CancellationPolicy cancellationPolicy = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            CancellationKind cancellationKind = null;

            if(cancellationKindName == null) {
                if(allowDefault) {
                    cancellationKind = cancellationPolicyControl.getDefaultCancellationKind();

                    if(cancellationKind == null) {
                        handleExecutionError(UnknownDefaultCancellationKindException.class, eea, ExecutionErrors.UnknownDefaultCancellationKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                cancellationKind = CancellationKindLogic.getInstance().getCancellationKindByName(eea, cancellationKindName);
            }

            if(!eea.hasExecutionErrors()) {
                if(cancellationPolicyName == null) {
                    if(allowDefault) {
                        cancellationPolicy = cancellationPolicyControl.getDefaultCancellationPolicy(cancellationKind, entityPermission);

                        if(cancellationPolicy == null) {
                            handleExecutionError(UnknownDefaultCancellationPolicyException.class, eea, ExecutionErrors.UnknownDefaultCancellationPolicy.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    cancellationPolicy = getCancellationPolicyByName(eea, cancellationKind, cancellationPolicyName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.CancellationPolicy.name());

            if(!eea.hasExecutionErrors()) {
                cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return cancellationPolicy;
    }

    public CancellationPolicy getCancellationPolicyByUniversalSpec(final ExecutionErrorAccumulator eea, final CancellationPolicyUniversalSpec universalSpec,
            boolean allowDefault) {
        return getCancellationPolicyByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public CancellationPolicy getCancellationPolicyByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final CancellationPolicyUniversalSpec universalSpec,
            boolean allowDefault) {
        return getCancellationPolicyByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public CancellationPolicy getDefaultCancellationPolicyByKind(final ExecutionErrorAccumulator eea, final String cancellationKindName,
            final CancellationPolicy cancellationPolicies[]) {
        CancellationPolicy cancellationPolicy = null;

        for(var i = 0; cancellationPolicy == null && i < cancellationPolicies.length ; i++) {
            cancellationPolicy = cancellationPolicies[i];
        }

        if(cancellationPolicy == null) {
            var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
            var cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);

            if(cancellationKind != null) {
                cancellationPolicy = cancellationPolicyControl.getDefaultCancellationPolicy(cancellationKind);

                if(cancellationPolicy == null) {
                    eea.addExecutionError(ExecutionErrors.UnknownDefaultCancellationPolicy.name(), cancellationKindName);
                }
            } else {
                eea.addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
            }
        }

        return cancellationPolicy;
    }

    public void checkDeleteCancellationPolicy(final ExecutionErrorAccumulator eea, final CancellationPolicy cancellationPolicy) {
        var orderControl = Session.getModelController(OrderControl.class);

        // Both CUSTOMERs and VENDORs use Orders and OrderLines, so check for CancellationPolicy use there first.
        var inUse = orderControl.countOrdersByCancellationPolicy(cancellationPolicy) != 0;

        if(!inUse) {
            var orderLineControl = Session.getModelController(OrderLineControl.class);

            inUse |= orderLineControl.countOrderLinesByCancellationPolicy(cancellationPolicy) != 0;
        }

        if(!inUse) {
            var cancellationKindName = cancellationPolicy.getLastDetail().getCancellationKind().getLastDetail().getCancellationKindName();
            
            if(cancellationKindName.equals(CancellationKinds.CUSTOMER_CANCELLATION.name())) {
                var itemControl = Session.getModelController(ItemControl.class);

                inUse |= itemControl.countItemsByCancellationPolicy(cancellationPolicy) != 0;
            } else if(cancellationKindName.equals(CancellationKinds.VENDOR_CANCELLATION.name())) {
                var vendorControl = Session.getModelController(VendorControl.class);

                inUse |= vendorControl.countVendorItemsByCancellationPolicy(cancellationPolicy) != 0;
            }
        }

        if(inUse) {
            eea.addExecutionError(ExecutionErrors.CannotDeleteCancellationPolicyInUse.name(), cancellationPolicy.getLastDetail().getCancellationPolicyName());
        }
    }

    public void deleteCancellationPolicy(final CancellationPolicy cancellationPolicy, final BasePK deletedBy) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);

        cancellationPolicyControl.deleteCancellationPolicy(cancellationPolicy, deletedBy);
    }

}
