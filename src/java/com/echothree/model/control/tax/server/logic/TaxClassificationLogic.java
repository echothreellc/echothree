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

import com.echothree.control.user.tax.common.spec.TaxClassificationUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.tax.common.exception.DuplicateTaxClassificationNameException;
import com.echothree.model.control.tax.common.exception.UnknownTaxClassificationNameException;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tax.server.entity.TaxClassification;
import com.echothree.model.data.tax.server.value.TaxClassificationDetailValue;
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
public class TaxClassificationLogic
        extends BaseLogic {

    @Inject
    GeoControl geoControl;

    @Inject
    TaxControl taxControl;

    protected TaxClassificationLogic() {
        super();
    }

    public static TaxClassificationLogic getInstance() {
        return CDI.current().select(TaxClassificationLogic.class).get();
    }

    public TaxClassification createTaxClassification(final ExecutionErrorAccumulator eea, final GeoCode countryGeoCode,
            final String taxClassificationName, final Boolean isDefault, final Integer sortOrder, final Language language,
            final String description, final MimeType overviewMimeType, final String overview, final BasePK createdBy) {
        var taxClassification = taxControl.getTaxClassificationByName(countryGeoCode, taxClassificationName);

        if(taxClassification == null) {
            taxClassification = taxControl.createTaxClassification(countryGeoCode, taxClassificationName, isDefault, sortOrder, createdBy);

            if(description != null) {
                taxControl.createTaxClassificationTranslation(taxClassification, language, description, overviewMimeType, overview, createdBy);
            }
        } else {
            handleExecutionError(DuplicateTaxClassificationNameException.class, eea, ExecutionErrors.DuplicateTaxClassificationName.name(),
                    countryGeoCode.getLastDetail().getGeoCodeName(), taxClassificationName);
        }

        return taxClassification;
    }

    public TaxClassification getTaxClassificationByName(final ExecutionErrorAccumulator eea, final GeoCode countryGeoCode,
            final String taxClassificationName, final EntityPermission entityPermission) {
        var taxClassification = entityPermission == EntityPermission.READ_WRITE
                ? taxControl.getTaxClassificationByNameForUpdate(countryGeoCode, taxClassificationName)
                : taxControl.getTaxClassificationByName(countryGeoCode, taxClassificationName);

        if(taxClassification == null) {
            handleExecutionError(UnknownTaxClassificationNameException.class, eea, ExecutionErrors.UnknownTaxClassificationName.name(),
                    countryGeoCode.getLastDetail().getGeoCodeName(), taxClassificationName);
        }

        return taxClassification;
    }

    public TaxClassification getTaxClassificationByName(final ExecutionErrorAccumulator eea, final GeoCode countryGeoCode,
            final String taxClassificationName) {
        return getTaxClassificationByName(eea, countryGeoCode, taxClassificationName, EntityPermission.READ_ONLY);
    }

    public TaxClassification getTaxClassificationByNameForUpdate(final ExecutionErrorAccumulator eea, final GeoCode countryGeoCode,
            final String taxClassificationName) {
        return getTaxClassificationByName(eea, countryGeoCode, taxClassificationName, EntityPermission.READ_WRITE);
    }

    public TaxClassification getTaxClassificationByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TaxClassificationUniversalSpec universalSpec, final EntityPermission entityPermission) {
        var countryName = universalSpec.getCountryName();
        var taxClassificationName = universalSpec.getTaxClassificationName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(countryName, taxClassificationName);
        var possibleEntitySpecs = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        TaxClassification taxClassification = null;

        if(nameParameterCount == 2 && possibleEntitySpecs == 0) {
            var countryGeoCode = geoControl.getCountryByAlias(countryName);

            if(countryGeoCode == null) {
                handleExecutionError(null, eea, ExecutionErrors.UnknownGeoCodeName.name(), countryName);
            } else {
                taxClassification = getTaxClassificationByName(eea, countryGeoCode, taxClassificationName, entityPermission);
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.TaxClassification.name());

            if(eea == null || !eea.hasExecutionErrors()) {
                taxClassification = taxControl.getTaxClassificationByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return taxClassification;
    }

    public TaxClassification getTaxClassificationByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TaxClassificationUniversalSpec universalSpec) {
        return getTaxClassificationByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public TaxClassification getTaxClassificationByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final TaxClassificationUniversalSpec universalSpec) {
        return getTaxClassificationByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public void updateTaxClassificationFromValue(final TaxClassificationDetailValue taxClassificationDetailValue, final BasePK updatedBy) {
        taxControl.updateTaxClassificationFromValue(taxClassificationDetailValue, updatedBy);
    }

    public void deleteTaxClassification(final ExecutionErrorAccumulator eea, final TaxClassification taxClassification, final BasePK deletedBy) {
        taxControl.deleteTaxClassification(taxClassification, deletedBy);
    }
    
}
