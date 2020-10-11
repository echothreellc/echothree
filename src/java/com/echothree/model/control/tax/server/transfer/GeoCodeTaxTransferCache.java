// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.tax.server.transfer;

import com.echothree.model.control.geo.common.transfer.GeoCodeTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.tax.common.transfer.GeoCodeTaxTransfer;
import com.echothree.model.control.tax.common.transfer.TaxTransfer;
import com.echothree.model.control.tax.server.TaxControl;
import com.echothree.model.data.tax.server.entity.GeoCodeTax;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class GeoCodeTaxTransferCache
        extends BaseTaxTransferCache<GeoCodeTax, GeoCodeTaxTransfer> {
    
    /** Creates a new instance of GeoCodeTaxTransferCache */
    public GeoCodeTaxTransferCache(UserVisit userVisit, TaxControl taxControl) {
        super(userVisit, taxControl);
    }
    
    @Override
    public GeoCodeTaxTransfer getTransfer(GeoCodeTax geoCodeTax) {
        GeoCodeTaxTransfer geoCodeTaxTransfer = get(geoCodeTax);
        
        if(geoCodeTaxTransfer == null) {
            GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
            GeoCodeTransfer geoCode = geoControl.getGeoCodeTransfer(userVisit, geoCodeTax.getGeoCode());
            TaxTransfer tax = taxControl.getTaxTransfer(userVisit, geoCodeTax.getTax());
            
            geoCodeTaxTransfer = new GeoCodeTaxTransfer(geoCode, tax);
            put(geoCodeTax, geoCodeTaxTransfer);
        }
        return geoCodeTaxTransfer;
    }
    
}
