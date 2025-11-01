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

package com.echothree.model.control.returnpolicy.server.logic;

import com.echothree.control.user.returnpolicy.common.spec.ReturnPolicyUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderLineControl;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.common.exception.DuplicateReturnPolicyNameException;
import com.echothree.model.control.returnpolicy.common.exception.UnknownDefaultReturnKindException;
import com.echothree.model.control.returnpolicy.common.exception.UnknownDefaultReturnPolicyException;
import com.echothree.model.control.returnpolicy.common.exception.UnknownReturnPolicyNameException;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
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
public class ReturnPolicyLogic
        extends BaseLogic {

    protected ReturnPolicyLogic() {
        super();
    }

    public static ReturnPolicyLogic getInstance() {
        return CDI.current().select(ReturnPolicyLogic.class).get();
    }

    public ReturnPolicy createReturnPolicy(final ExecutionErrorAccumulator eea, final String returnKindName, final String returnPolicyName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final String policyMimeTypeName,
            final String policy, final BasePK createdBy) {
        var returnKind = ReturnKindLogic.getInstance().getReturnKindByName(eea, returnKindName);
        var policyMimeType = MimeTypeLogic.getInstance().checkMimeType(eea, policyMimeTypeName, policy, MimeTypeUsageTypes.TEXT.name(),
                ExecutionErrors.MissingRequiredPolicyMimeTypeName.name(), ExecutionErrors.MissingRequiredPolicy.name(),
                ExecutionErrors.UnknownPolicyMimeTypeName.name(), ExecutionErrors.UnknownPolicyMimeTypeUsage.name());
        ReturnPolicy returnPolicy = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            returnPolicy = createReturnPolicy(eea, returnKind, returnPolicyName, isDefault, sortOrder, language, description, policyMimeType, policy, createdBy);
        }

        return returnPolicy;
    }

    public ReturnPolicy createReturnPolicy(final ExecutionErrorAccumulator eea, final ReturnKind returnKind, final String returnPolicyName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final MimeType policyMimeType,
            final String policy, final BasePK createdBy) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);

        if(returnPolicy == null) {
            returnPolicy = returnPolicyControl.createReturnPolicy(returnKind, returnPolicyName, isDefault, sortOrder, createdBy);

            if(description != null) {
                returnPolicyControl.createReturnPolicyTranslation(returnPolicy, language, description, policyMimeType, policy, createdBy);
            }
        } else {
            handleExecutionError(DuplicateReturnPolicyNameException.class, eea, ExecutionErrors.DuplicateReturnPolicyName.name(), returnPolicyName);
        }
        return returnPolicy;
    }

    public ReturnPolicy getReturnPolicyByName(final ExecutionErrorAccumulator eea, final ReturnKind returnKind, final String returnPolicyName,
            final EntityPermission entityPermission) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName, entityPermission);

        if(returnPolicy == null) {
            handleExecutionError(UnknownReturnPolicyNameException.class, eea, ExecutionErrors.UnknownReturnPolicyName.name(),
                    returnKind.getLastDetail().getReturnKindName(), returnPolicyName);
        }

        return returnPolicy;
    }

    public ReturnPolicy getReturnPolicyByName(final ExecutionErrorAccumulator eea, final ReturnKind returnKind, final String returnPolicyName) {
        return getReturnPolicyByName(eea, returnKind, returnPolicyName, EntityPermission.READ_ONLY);
    }

    public ReturnPolicy getReturnPolicyByNameForUpdate(final ExecutionErrorAccumulator eea, final ReturnKind returnKind, final String returnPolicyName) {
        return getReturnPolicyByName(eea, returnKind, returnPolicyName, EntityPermission.READ_WRITE);
    }

    public ReturnPolicy getReturnPolicyByName(final ExecutionErrorAccumulator eea, final String returnKindName, final String returnPolicyName,
            final EntityPermission entityPermission) {
        var returnKind = ReturnKindLogic.getInstance().getReturnKindByName(eea, returnKindName);
        ReturnPolicy returnPolicy = null;

        if(!eea.hasExecutionErrors()) {
            returnPolicy = getReturnPolicyByName(eea, returnKind, returnPolicyName, entityPermission);
        }

        return returnPolicy;
    }

    public ReturnPolicy getReturnPolicyByName(final ExecutionErrorAccumulator eea, final String returnKindName, final String returnPolicyName) {
        return getReturnPolicyByName(eea, returnKindName, returnPolicyName, EntityPermission.READ_ONLY);
    }

    public ReturnPolicy getReturnPolicyByNameForUpdate(final ExecutionErrorAccumulator eea, final String returnKindName, final String returnPolicyName) {
        return getReturnPolicyByName(eea, returnKindName, returnPolicyName, EntityPermission.READ_WRITE);
    }

    public ReturnPolicy getReturnPolicyByUniversalSpec(final ExecutionErrorAccumulator eea, final ReturnPolicyUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var returnKindName = universalSpec.getReturnKindName();
        var returnPolicyName = universalSpec.getReturnPolicyName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(returnKindName, returnPolicyName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        ReturnPolicy returnPolicy = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            ReturnKind returnKind = null;

            if(returnKindName == null) {
                if(allowDefault) {
                    returnKind = returnPolicyControl.getDefaultReturnKind();

                    if(returnKind == null) {
                        handleExecutionError(UnknownDefaultReturnKindException.class, eea, ExecutionErrors.UnknownDefaultReturnKind.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                returnKind = ReturnKindLogic.getInstance().getReturnKindByName(eea, returnKindName);
            }

            if(!eea.hasExecutionErrors()) {
                if(returnPolicyName == null) {
                    if(allowDefault) {
                        returnPolicy = returnPolicyControl.getDefaultReturnPolicy(returnKind, entityPermission);

                        if(returnPolicy == null) {
                            handleExecutionError(UnknownDefaultReturnPolicyException.class, eea, ExecutionErrors.UnknownDefaultReturnPolicy.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    returnPolicy = getReturnPolicyByName(eea, returnKind, returnPolicyName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.ReturnPolicy.name());

            if(!eea.hasExecutionErrors()) {
                returnPolicy = returnPolicyControl.getReturnPolicyByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return returnPolicy;
    }

    public ReturnPolicy getReturnPolicyByUniversalSpec(final ExecutionErrorAccumulator eea, final ReturnPolicyUniversalSpec universalSpec,
            boolean allowDefault) {
        return getReturnPolicyByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ReturnPolicy getReturnPolicyByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final ReturnPolicyUniversalSpec universalSpec,
            boolean allowDefault) {
        return getReturnPolicyByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public ReturnPolicy getDefaultReturnPolicyByKind(final ExecutionErrorAccumulator eea, final String returnKindName,
            final ReturnPolicy returnPolicies[]) {
        ReturnPolicy returnPolicy = null;

        for(var i = 0; returnPolicy == null && i < returnPolicies.length ; i++) {
            returnPolicy = returnPolicies[i];
        }

        if(returnPolicy == null) {
            var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
            var returnKind = returnPolicyControl.getReturnKindByName(returnKindName);

            if(returnKind != null) {
                returnPolicy = returnPolicyControl.getDefaultReturnPolicy(returnKind);

                if(returnPolicy == null) {
                    eea.addExecutionError(ExecutionErrors.UnknownDefaultReturnPolicy.name(), returnKindName);
                }
            } else {
                eea.addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
            }
        }

        return returnPolicy;
    }

    public void checkDeleteReturnPolicy(final ExecutionErrorAccumulator eea, final ReturnPolicy returnPolicy) {
        var orderControl = Session.getModelController(OrderControl.class);

        // Both CUSTOMERs and VENDORs use Orders and OrderLines, so check for ReturnPolicy use there first.
        var inUse = orderControl.countOrdersByReturnPolicy(returnPolicy) != 0;

        if(!inUse) {
            var orderLineControl = Session.getModelController(OrderLineControl.class);

            inUse |= orderLineControl.countOrderLinesByReturnPolicy(returnPolicy) != 0;
        }

        if(!inUse) {
            var returnKindName = returnPolicy.getLastDetail().getReturnKind().getLastDetail().getReturnKindName();

            if(returnKindName.equals(ReturnKinds.CUSTOMER_RETURN.name())) {
                var itemControl = Session.getModelController(ItemControl.class);

                inUse |= itemControl.countItemsByReturnPolicy(returnPolicy) != 0;
            } else if(returnKindName.equals(ReturnKinds.VENDOR_RETURN.name())) {
                var vendorControl = Session.getModelController(VendorControl.class);

                inUse |= vendorControl.countVendorItemsByReturnPolicy(returnPolicy) != 0;
            }
        }

        if(inUse) {
            eea.addExecutionError(ExecutionErrors.CannotDeleteReturnPolicyInUse.name(), returnPolicy.getLastDetail().getReturnPolicyName());
        }
    }

    public void deleteReturnPolicy(final ReturnPolicy returnPolicy, final BasePK deletedBy) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);

        returnPolicyControl.deleteReturnPolicy(returnPolicy, deletedBy);
    }

}
