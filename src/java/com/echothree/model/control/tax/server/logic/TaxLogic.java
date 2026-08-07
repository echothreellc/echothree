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

package com.echothree.model.control.tax.server.logic;

import com.echothree.control.user.tax.common.spec.TaxUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.tax.common.exception.DuplicateTaxNameException;
import com.echothree.model.control.tax.common.exception.UnknownDefaultTaxException;
import com.echothree.model.control.tax.common.exception.UnknownTaxNameException;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.tax.server.value.TaxDetailValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class TaxLogic
        extends BaseLogic {

    @Inject
    TaxControl taxControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected TaxLogic() {
        super();
    }

    public static TaxLogic getInstance() {
        return CDI.current().select(TaxLogic.class).get();
    }

    public Tax createTax(final ExecutionErrorAccumulator eea, final String taxName,
            final ContactMechanismPurpose contactMechanismPurpose, final GlAccount glAccount,
            final Boolean includeShippingCharge, final Boolean includeProcessingCharge,
            final Boolean includeInsuranceCharge, final Integer percent, final Boolean isDefault,
            final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var tax = taxControl.getTaxByName(taxName);

        if(tax == null) {
            tax = taxControl.createTax(taxName, contactMechanismPurpose, glAccount, includeShippingCharge,
                    includeProcessingCharge, includeInsuranceCharge, percent, isDefault, sortOrder, createdBy);

            if(description != null) {
                taxControl.createTaxDescription(tax, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateTaxNameException.class, eea, ExecutionErrors.DuplicateTaxName.name(), taxName);
        }

        return tax;
    }

    public Tax getTaxByName(final ExecutionErrorAccumulator eea, final String taxName,
            final EntityPermission entityPermission) {
        var tax = taxControl.getTaxByName(taxName, entityPermission);

        if(tax == null) {
            handleExecutionError(UnknownTaxNameException.class, eea, ExecutionErrors.UnknownTaxName.name(), taxName);
        }

        return tax;
    }

    public Tax getTaxByName(final ExecutionErrorAccumulator eea, final String taxName) {
        return getTaxByName(eea, taxName, EntityPermission.READ_ONLY);
    }

    public Tax getTaxByNameForUpdate(final ExecutionErrorAccumulator eea, final String taxName) {
        return getTaxByName(eea, taxName, EntityPermission.READ_WRITE);
    }

    public Tax getTaxByUniversalSpec(final ExecutionErrorAccumulator eea, final TaxUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var taxName = universalSpec.getTaxName();
        Tax tax = null;
        var parameterCount = (taxName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    tax = taxControl.getDefaultTax(entityPermission);

                    if(tax == null) {
                        handleExecutionError(UnknownDefaultTaxException.class, eea, ExecutionErrors.UnknownDefaultTax.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(taxName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Tax.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        tax = taxControl.getTaxByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    tax = getTaxByName(eea, taxName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return tax;
    }

    public Tax getTaxByUniversalSpec(final ExecutionErrorAccumulator eea, final TaxUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getTaxByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public Tax getTaxByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final TaxUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getTaxByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateTaxFromValue(final TaxDetailValue taxDetailValue, final BasePK updatedBy) {
        taxControl.updateTaxFromValue(taxDetailValue, updatedBy);
    }

    public void deleteTax(final ExecutionErrorAccumulator eea, final Tax tax, final BasePK deletedBy) {
        taxControl.deleteTax(tax, deletedBy);
    }

}
