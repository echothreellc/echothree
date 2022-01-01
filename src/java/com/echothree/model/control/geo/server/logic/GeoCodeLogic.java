// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.geo.common.exception.UnknownGeoCodeAliasTypeNameException;
import com.echothree.model.control.geo.common.exception.UnknownGeoCodeNameException;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.geo.server.value.GeoCodeAliasValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class GeoCodeLogic
        extends BaseLogic {

    private GeoCodeLogic() {
        super();
    }

    private static class GeoLogicHolder {
        static GeoCodeLogic instance = new GeoCodeLogic();
    }

    public static GeoCodeLogic getInstance() {
        return GeoLogicHolder.instance;
    }

    public GeoCode getGeoCodeByName(final ExecutionErrorAccumulator eea, final String geoCodeName) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCode = geoControl.getGeoCodeByName(geoCodeName);

        if(geoCode == null) {
            handleExecutionError(UnknownGeoCodeNameException.class, eea, ExecutionErrors.UnknownGeoCodeName.name(),
                    geoCodeName);
        }

        return geoCode;
    }
    
    public GeoCodeAlias getGeoCodeAliasUsingNames(final ExecutionErrorAccumulator eea, final GeoCode geoCode,
            final String geoCodeAliasTypeName) {
        var geoControl = Session.getModelController(GeoControl.class);

        return geoControl.getGeoCodeAlias(geoCode, getGeoCodeAliasTypeByName(eea, geoCode.getLastDetail().getGeoCodeType(), geoCodeAliasTypeName));
    }

    public GeoCodeAliasValue getGeoCodeAliasValueUsingNames(final ExecutionErrorAccumulator ema, final GeoCode geoCode,
            final String geoCodeAliasTypeName) {
        var geoControl = Session.getModelController(GeoControl.class);

        return geoControl.getGeoCodeAliasValueForUpdate(geoCode, getGeoCodeAliasTypeByName(ema, geoCode.getLastDetail().getGeoCodeType(), geoCodeAliasTypeName));
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

    public GeoCodeAliasType getGeoCodeAliasTypeByName(final ExecutionErrorAccumulator eea, final GeoCodeType geoCodeType,
            final String geoCodeAliasTypeName) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, geoCodeAliasTypeName);

        if(geoCodeAliasType == null) {
            handleExecutionError(UnknownGeoCodeAliasTypeNameException.class, eea, ExecutionErrors.UnknownGeoCodeAliasTypeName.name(),
                    geoCodeType.getLastDetail().getGeoCodeTypeName(), geoCodeAliasTypeName);
        }

        return geoCodeAliasType;
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
