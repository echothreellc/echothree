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

package com.echothree.model.control.tax.server.transfer;

import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.tax.common.transfer.TaxClassificationTransfer;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.tax.server.entity.TaxClassification;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TaxClassificationTransferCache
        extends BaseTaxTransferCache<TaxClassification, TaxClassificationTransfer> {
    
    GeoControl geoControl = Session.getModelController(GeoControl.class);
    TaxControl taxControl = Session.getModelController(TaxControl.class);
    
    /** Creates a new instance of TaxClassificationTransferCache */
    public TaxClassificationTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public TaxClassificationTransfer getTransfer(UserVisit userVisit, TaxClassification taxClassification) {
        var taxClassificationTransfer = get(taxClassification);
        
        if(taxClassificationTransfer == null) {
            var taxClassificationDetail = taxClassification.getLastDetail();
            var countryTransfer = geoControl.getCountryTransfer(userVisit, taxClassificationDetail.getCountryGeoCode());
            var taxClassificationName = taxClassificationDetail.getTaxClassificationName();
            var isDefault = taxClassificationDetail.getIsDefault();
            var sortOrder = taxClassificationDetail.getSortOrder();
            var taxClassificationTranslation = taxControl.getBestTaxClassificationTranslation(taxClassification, getLanguage(userVisit));
            var description = taxClassificationTranslation == null ? taxClassificationName : taxClassificationTranslation.getDescription();
            
            taxClassificationTransfer = new TaxClassificationTransfer(countryTransfer, taxClassificationName, isDefault, sortOrder, description);
            put(userVisit, taxClassification, taxClassificationTransfer);
        }
        
        return taxClassificationTransfer;
    }
    
}
