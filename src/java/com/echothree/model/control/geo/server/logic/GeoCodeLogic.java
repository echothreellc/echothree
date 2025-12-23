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

package com.echothree.model.control.geo.server.logic;

import com.echothree.control.user.geo.common.spec.GeoCodeUniversalSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.geo.common.exception.UnknownGeoCodeAliasTypeNameException;
import com.echothree.model.control.geo.common.exception.UnknownGeoCodeNameException;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class GeoCodeLogic
        extends BaseLogic {

    protected GeoCodeLogic() {
        super();
    }

    public static GeoCodeLogic getInstance() {
        return CDI.current().select(GeoCodeLogic.class).get();
    }

    public GeoCode getGeoCodeByName(final ExecutionErrorAccumulator eea, final String geoCodeName,
            final EntityPermission entityPermission) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCode = geoControl.getGeoCodeByName(geoCodeName, entityPermission);

        if(geoCode == null) {
            handleExecutionError(UnknownGeoCodeNameException.class, eea, ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }

        return geoCode;
    }

    public GeoCode getGeoCodeByName(final ExecutionErrorAccumulator eea, final String geoCodeName) {
        return getGeoCodeByName(eea, geoCodeName, EntityPermission.READ_ONLY);
    }

    public GeoCode getGeoCodeByNameForUpdate(final ExecutionErrorAccumulator eea, final String geoCodeName) {
        return getGeoCodeByName(eea, geoCodeName, EntityPermission.READ_WRITE);
    }

    public GeoCode getGeoCodeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GeoCodeUniversalSpec universalSpec, final EntityPermission entityPermission) {
        GeoCode geoCode = null;
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeName = universalSpec.getGeoCodeName();
        var parameterCount = (geoCodeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(geoCodeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.GeoCode.name());

                    if(!eea.hasExecutionErrors()) {
                        geoCode = geoControl.getGeoCodeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    geoCode = getGeoCodeByName(eea, geoCodeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return geoCode;
    }

    public GeoCode getGeoCodeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GeoCodeUniversalSpec universalSpec) {
        return getGeoCodeByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public GeoCode getGeoCodeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final GeoCodeUniversalSpec universalSpec) {
        return getGeoCodeByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public GeoCode getGeoCodeByAlias(final ExecutionErrorAccumulator eea, final GeoCodeType geoCodeType, final GeoCodeScope geoCodeScope,
            final String geoCodeAliasTypeName, final String alias) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, geoCodeAliasTypeName);
        GeoCodeAlias geoCodeAlias = null;

        if(geoCodeAliasType != null) {
            geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, alias);
        } else {
            handleExecutionError(UnknownGeoCodeAliasTypeNameException.class, eea, ExecutionErrors.UnknownGeoCodeAliasTypeName.name(),
                    geoCodeType.getLastDetail().getGeoCodeTypeName(), geoCodeAliasTypeName);
        }

        return geoCodeAlias == null ? null : geoCodeAlias.getGeoCode();
    }

    public void deleteGeoCode(final ExecutionErrorAccumulator eea, final GeoCode geoCode, final BasePK deletedBy) {
        var contactControl = Session.getModelController(ContactControl.class);
        var geoControl = Session.getModelController(GeoControl.class);
        var itemControl = Session.getModelController(ItemControl.class);
        var selectorControl = Session.getModelController(SelectorControl.class);
        var taxControl = Session.getModelController(TaxControl.class);

        if(contactControl.countContactPostalAddressCorrectionsByCityGeoCode(geoCode) == 0
                && contactControl.countContactPostalAddressCorrectionsByCountyGeoCode(geoCode) == 0
                && contactControl.countContactPostalAddressCorrectionsByStateGeoCode(geoCode) == 0
                && contactControl.countContactPostalAddressCorrectionsByPostalCodeGeoCode(geoCode) == 0
                && contactControl.countContactPostalAddressCorrectionsByCountryGeoCode(geoCode) == 0
                && contactControl.countContactTelephonesByCountryGeoCode(geoCode) == 0
                && geoControl.countGeoCodeRelationshipsByFromGeoCode(geoCode) == 0
                && itemControl.countItemCountryOfOriginsByCountryGeoCode(geoCode) == 0
                && selectorControl.countSelectorNodeGeoCodesByGeoCode(geoCode) == 0
                && taxControl.countGeoCodeTaxesByGeoCode(geoCode) == 0) {

            geoControl.deleteGeoCode(geoCode, deletedBy);
        } else {
            handleExecutionError(null, eea, ExecutionErrors.CannotDeleteGeoCodeInUse.name(),
                    geoCode.getLastDetail().getGeoCodeName());
        }
    }

}
