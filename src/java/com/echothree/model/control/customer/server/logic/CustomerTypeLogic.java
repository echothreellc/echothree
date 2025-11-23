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

package com.echothree.model.control.customer.server.logic;

import com.echothree.control.user.customer.common.spec.CustomerTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.customer.common.exception.DuplicateCustomerTypeNameException;
import com.echothree.model.control.customer.common.exception.UnknownCustomerTypeNameException;
import com.echothree.model.control.customer.common.exception.UnknownDefaultCustomerTypeException;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.value.CustomerTypeDetailValue;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class CustomerTypeLogic
        extends BaseLogic {

    protected CustomerTypeLogic() {
        super();
    }

    public static CustomerTypeLogic getInstance() {
        return CDI.current().select(CustomerTypeLogic.class).get();
    }

    public CustomerType createCustomerType(final ExecutionErrorAccumulator eea, final String customerTypeName, final Sequence customerSequence,
            final OfferUse defaultOfferUse, final Term defaultTerm, final FreeOnBoard defaultFreeOnBoard, final CancellationPolicy defaultCancellationPolicy,
            final ReturnPolicy defaultReturnPolicy, final WorkflowEntrance defaultCustomerStatus, final WorkflowEntrance defaultCustomerCreditStatus,
            final GlAccount defaultArGlAccount, final Boolean defaultHoldUntilComplete, final Boolean defaultAllowBackorders,
            final Boolean defaultAllowSubstitutions, final Boolean defaultAllowCombiningShipments, final Boolean defaultRequireReference,
            final Boolean defaultAllowReferenceDuplicates, final String defaultReferenceValidationPattern, final Boolean defaultTaxable,
            final AllocationPriority allocationPriority, final Boolean isDefault, final Integer sortOrder, final Language language,
            final String description, final BasePK createdBy) {
        var customerControl = Session.getModelController(CustomerControl.class);
        var customerType = customerControl.getCustomerTypeByName(customerTypeName);

        if(customerType == null) {
            customerType = customerControl.createCustomerType(customerTypeName, customerSequence, defaultOfferUse,
                    defaultTerm, defaultFreeOnBoard, defaultCancellationPolicy, defaultReturnPolicy, defaultCustomerStatus,
                    defaultCustomerCreditStatus, defaultArGlAccount, defaultHoldUntilComplete, defaultAllowBackorders,
                    defaultAllowSubstitutions, defaultAllowCombiningShipments, defaultRequireReference,
                    defaultAllowReferenceDuplicates, defaultReferenceValidationPattern, defaultTaxable,
                    allocationPriority, isDefault, sortOrder, createdBy);

            if(description != null) {
                customerControl.createCustomerTypeDescription(customerType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateCustomerTypeNameException.class, eea, ExecutionErrors.DuplicateCustomerTypeName.name(), customerTypeName);
        }

        return customerType;
    }

    public CustomerType getCustomerTypeByName(final ExecutionErrorAccumulator eea, final String customerTypeName,
            final EntityPermission entityPermission) {
        var customerControl = Session.getModelController(CustomerControl.class);
        var customerType = customerControl.getCustomerTypeByName(customerTypeName, entityPermission);

        if(customerType == null) {
            handleExecutionError(UnknownCustomerTypeNameException.class, eea, ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }

        return customerType;
    }

    public CustomerType getCustomerTypeByName(final ExecutionErrorAccumulator eea, final String customerTypeName) {
        return getCustomerTypeByName(eea, customerTypeName, EntityPermission.READ_ONLY);
    }

    public CustomerType getCustomerTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String customerTypeName) {
        return getCustomerTypeByName(eea, customerTypeName, EntityPermission.READ_WRITE);
    }

    public CustomerType getCustomerTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final CustomerTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        CustomerType customerType = null;
        var customerControl = Session.getModelController(CustomerControl.class);
        var customerTypeName = universalSpec.getCustomerTypeName();
        var parameterCount = (customerTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    customerType = customerControl.getDefaultCustomerType(entityPermission);

                    if(customerType == null) {
                        handleExecutionError(UnknownDefaultCustomerTypeException.class, eea, ExecutionErrors.UnknownDefaultCustomerType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(customerTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.CustomerType.name());

                    if(!eea.hasExecutionErrors()) {
                        customerType = customerControl.getCustomerTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    customerType = getCustomerTypeByName(eea, customerTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return customerType;
    }

    public CustomerType getCustomerTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final CustomerTypeUniversalSpec universalSpec, final boolean allowDefault) {
        return getCustomerTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public CustomerType getCustomerTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final CustomerTypeUniversalSpec universalSpec, final boolean allowDefault) {
        return getCustomerTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateCustomerTypeFromValue(final ExecutionErrorAccumulator eea, final CustomerTypeDetailValue customerTypeDetailValue,
            final BasePK updatedBy) {
        var customerControl = Session.getModelController(CustomerControl.class);

        customerControl.updateCustomerTypeFromValue(customerTypeDetailValue, updatedBy);
    }

    public void deleteCustomerType(final ExecutionErrorAccumulator eea, final CustomerType customerType, final BasePK deletedBy) {
        var customerControl = Session.getModelController(CustomerControl.class);

        customerControl.deleteCustomerType(customerType, deletedBy);
    }

}
