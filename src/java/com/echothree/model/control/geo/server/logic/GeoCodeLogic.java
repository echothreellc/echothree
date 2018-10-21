// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.geo.common.exception.UnknownGeoCodeAliasTypeNameException;
import com.echothree.model.control.geo.common.exception.UnknownGeoCodeScopeNameException;
import com.echothree.model.control.geo.common.exception.UnknownGeoCodeTypeNameException;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.control.tax.server.TaxControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.geo.server.value.GeoCodeAliasValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.remote.persistence.BasePK;
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
    
    public GeoCodeAlias getGeoCodeAliasUsingNames(final ExecutionErrorAccumulator ema, final GeoCode geoCode, final String geoCodeAliasTypeName) {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);

        return geoControl.getGeoCodeAlias(geoCode, getGeoCodeAliasTypeByName(ema, geoCode.getLastDetail().getGeoCodeType(), geoCodeAliasTypeName));
    }

    public GeoCodeAliasValue getGeoCodeAliasValueUsingNames(final ExecutionErrorAccumulator ema, final GeoCode geoCode, final String geoCodeAliasTypeName) {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);

        return geoControl.getGeoCodeAliasValueForUpdate(geoCode, getGeoCodeAliasTypeByName(ema, geoCode.getLastDetail().getGeoCodeType(), geoCodeAliasTypeName));
    }

    public GeoCode getGeoCodeByAlias(final ExecutionErrorAccumulator ema, final GeoCodeType geoCodeType, final GeoCodeScope geoCodeScope,
            final String geoCodeAliasTypeName, final String alias) {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        GeoCodeAlias geoCodeAlias = null;
        GeoCodeAliasType geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, geoCodeAliasTypeName);

        if(geoCodeAliasType != null) {
            geoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, alias);
        } else {
            handleExecutionError(UnknownGeoCodeAliasTypeNameException.class, ema, ExecutionErrors.UnknownGeoCodeAliasTypeName.name(),
                    geoCodeType.getLastDetail().getGeoCodeTypeName(), geoCodeAliasTypeName);
        }

        return geoCodeAlias == null ? null : geoCodeAlias.getGeoCode();
    }

    public GeoCodeAliasType getGeoCodeAliasTypeByName(final ExecutionErrorAccumulator ema, final GeoCodeType geoCodeType, final String geoCodeAliasTypeName) {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        GeoCodeAliasType geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, geoCodeAliasTypeName);

        if(geoCodeAliasType == null) {
            handleExecutionError(UnknownGeoCodeAliasTypeNameException.class, ema, ExecutionErrors.UnknownGeoCodeAliasTypeName.name(),
                    geoCodeType.getLastDetail().getGeoCodeTypeName(), geoCodeAliasTypeName);
        }

        return geoCodeAliasType;
    }

    public GeoCodeType getGeoCodeTypeByName(final ExecutionErrorAccumulator ema, final String geoCodeTypeName) {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        GeoCodeType geoCodeType = geoControl.getGeoCodeTypeByName(geoCodeTypeName);

        if(geoCodeType == null) {
            handleExecutionError(UnknownGeoCodeTypeNameException.class, ema, ExecutionErrors.UnknownGeoCodeTypeName.name(),
                    geoCodeTypeName);
        }

        return geoCodeType;
    }

    public GeoCodeScope getGeoCodeScopeByName(final ExecutionErrorAccumulator ema, final String geoCodeScopeName) {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        GeoCodeScope geoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName);

        if(geoCodeScope == null) {
            handleExecutionError(UnknownGeoCodeScopeNameException.class, ema, ExecutionErrors.UnknownGeoCodeScopeName.name(),
                    geoCodeScopeName);
        }

        return geoCodeScope;
    }

    public void deleteGeoCode(final ExecutionErrorAccumulator ema, final GeoCode geoCode, BasePK deletedBy) {
        ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        TaxControl taxControl = (TaxControl)Session.getModelController(TaxControl.class);

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
            handleExecutionError(null, ema, ExecutionErrors.CannotDeleteGeoCodeInUse.name(),
                    geoCode.getLastDetail().getGeoCodeName());
        }
    }

}
