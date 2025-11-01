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

package com.echothree.model.control.vendor.server.logic;

import com.echothree.control.user.vendor.common.spec.VendorTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.vendor.common.exception.DuplicateVendorTypeNameException;
import com.echothree.model.control.vendor.common.exception.UnknownDefaultVendorTypeException;
import com.echothree.model.control.vendor.common.exception.UnknownVendorTypeNameException;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.vendor.server.entity.VendorType;
import com.echothree.model.data.vendor.server.value.VendorTypeDetailValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class VendorTypeLogic
        extends BaseLogic {

    protected VendorTypeLogic() {
        super();
    }

    public static VendorTypeLogic getInstance() {
        return CDI.current().select(VendorTypeLogic.class).get();
    }

    public VendorType createVendorType(final ExecutionErrorAccumulator eea, final String vendorTypeName, final Term defaultTerm,
            final FreeOnBoard defaultFreeOnBoard, final CancellationPolicy defaultCancellationPolicy, final ReturnPolicy defaultReturnPolicy,
            final GlAccount defaultApGlAccount, final Boolean defaultHoldUntilComplete, final Boolean defaultAllowBackorders,
            final Boolean defaultAllowSubstitutions, final Boolean defaultAllowCombiningShipments, final Boolean defaultRequireReference,
            final Boolean defaultAllowReferenceDuplicates, final String defaultReferenceValidationPattern, final Boolean isDefault,
            final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var vendorType = vendorControl.getVendorTypeByName(vendorTypeName);

        if(vendorType == null) {
            vendorType = vendorControl.createVendorType(vendorTypeName, defaultTerm, defaultFreeOnBoard,
                    defaultCancellationPolicy, defaultReturnPolicy, defaultApGlAccount, defaultHoldUntilComplete,
                    defaultAllowBackorders, defaultAllowSubstitutions, defaultAllowCombiningShipments,
                    defaultRequireReference, defaultAllowReferenceDuplicates, defaultReferenceValidationPattern,
                    isDefault, sortOrder, createdBy);

            if(description != null) {
                vendorControl.createVendorTypeDescription(vendorType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateVendorTypeNameException.class, eea, ExecutionErrors.DuplicateVendorTypeName.name(), vendorTypeName);
        }

        return vendorType;
    }

    public VendorType getVendorTypeByName(final ExecutionErrorAccumulator eea, final String vendorTypeName,
            final EntityPermission entityPermission) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var vendorType = vendorControl.getVendorTypeByName(vendorTypeName, entityPermission);

        if(vendorType == null) {
            handleExecutionError(UnknownVendorTypeNameException.class, eea, ExecutionErrors.UnknownVendorTypeName.name(), vendorTypeName);
        }

        return vendorType;
    }

    public VendorType getVendorTypeByName(final ExecutionErrorAccumulator eea, final String vendorTypeName) {
        return getVendorTypeByName(eea, vendorTypeName, EntityPermission.READ_ONLY);
    }

    public VendorType getVendorTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String vendorTypeName) {
        return getVendorTypeByName(eea, vendorTypeName, EntityPermission.READ_WRITE);
    }

    public VendorType getVendorTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final VendorTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        VendorType vendorType = null;
        var vendorControl = Session.getModelController(VendorControl.class);
        var vendorTypeName = universalSpec.getVendorTypeName();
        var parameterCount = (vendorTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    vendorType = vendorControl.getDefaultVendorType(entityPermission);

                    if(vendorType == null) {
                        handleExecutionError(UnknownDefaultVendorTypeException.class, eea, ExecutionErrors.UnknownDefaultVendorType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(vendorTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.VendorType.name());

                    if(!eea.hasExecutionErrors()) {
                        vendorType = vendorControl.getVendorTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    vendorType = getVendorTypeByName(eea, vendorTypeName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return vendorType;
    }

    public VendorType getVendorTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final VendorTypeUniversalSpec universalSpec, final boolean allowDefault) {
        return getVendorTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public VendorType getVendorTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final VendorTypeUniversalSpec universalSpec, final boolean allowDefault) {
        return getVendorTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateVendorTypeFromValue(final ExecutionErrorAccumulator eea, final VendorTypeDetailValue vendorTypeDetailValue,
            final BasePK updatedBy) {
        var vendorControl = Session.getModelController(VendorControl.class);

        vendorControl.updateVendorTypeFromValue(vendorTypeDetailValue, updatedBy);
    }

    public void deleteVendorType(final ExecutionErrorAccumulator eea, final VendorType vendorType, final BasePK deletedBy) {
        var vendorControl = Session.getModelController(VendorControl.class);

        vendorControl.deleteVendorType(vendorType, deletedBy);
    }

}
