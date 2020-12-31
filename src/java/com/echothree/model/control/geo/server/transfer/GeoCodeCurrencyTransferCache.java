// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.geo.server.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.geo.common.transfer.GeoCodeCurrencyTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeCurrency;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class GeoCodeCurrencyTransferCache
        extends BaseGeoTransferCache<GeoCodeCurrency, GeoCodeCurrencyTransfer> {
    
    AccountingControl accountingControl;
    
    /** Creates a new instance of GeoCodeCurrencyTransferCache */
    public GeoCodeCurrencyTransferCache(UserVisit userVisit, GeoControl geoControl) {
        super(userVisit, geoControl);
        
        accountingControl = Session.getModelController(AccountingControl.class);
    }
    
    public GeoCodeCurrencyTransfer getGeoCodeCurrencyTransfer(GeoCodeCurrency geoCodeCurrency) {
        GeoCodeCurrencyTransfer geoCodeCurrencyTransfer = get(geoCodeCurrency);
        
        if(geoCodeCurrencyTransfer == null) {
            GeoCodeTransfer geoCode = geoControl.getGeoCodeTransfer(userVisit, geoCodeCurrency.getGeoCode());
            CurrencyTransfer currency = accountingControl.getCurrencyTransfer(userVisit, geoCodeCurrency.getCurrency());
            Boolean isDefault = geoCodeCurrency.getIsDefault();
            Integer sortOrder = geoCodeCurrency.getSortOrder();
            
            geoCodeCurrencyTransfer = new GeoCodeCurrencyTransfer(geoCode, currency, isDefault, sortOrder);
            put(geoCodeCurrency, geoCodeCurrencyTransfer);
        }
        
        return geoCodeCurrencyTransfer;
    }
    
}
