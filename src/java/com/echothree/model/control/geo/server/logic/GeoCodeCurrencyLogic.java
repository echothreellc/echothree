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

import com.echothree.control.user.geo.common.spec.GeoCodeCurrencySpec;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.geo.common.exception.UnknownGeoCodeCurrencyException;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeCurrency;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GeoCodeCurrencyLogic
        extends BaseLogic {

    @Inject
    GeoControl geoControl;

    @Inject
    CurrencyLogic currencyLogic;

    @Inject
    GeoCodeLogic geoCodeLogic;

    protected GeoCodeCurrencyLogic() {
        super();
    }

    public GeoCodeCurrency getGeoCodeCurrencyBySpec(final ExecutionErrorAccumulator eea,
            final GeoCodeCurrencySpec spec, final EntityPermission entityPermission) {
        GeoCodeCurrency geoCodeCurrency = null;
        var geoCodeName = spec.getGeoCodeName();
        var geoCode = geoCodeLogic.getGeoCodeByName(eea, geoCodeName);

        if(!hasExecutionErrors(eea)) {
            var currencyIsoName = spec.getCurrencyIsoName();
            var currency = currencyLogic.getCurrencyByName(eea, currencyIsoName);

            if(!hasExecutionErrors(eea)) {
                geoCodeCurrency = geoControl.getGeoCodeCurrency(geoCode, currency, entityPermission);

                if(geoCodeCurrency == null) {
                    handleExecutionError(UnknownGeoCodeCurrencyException.class, eea, ExecutionErrors.UnknownGeoCodeCurrency.name(), geoCodeName, currencyIsoName);
                }
            }
        }

        return geoCodeCurrency;
    }

}
