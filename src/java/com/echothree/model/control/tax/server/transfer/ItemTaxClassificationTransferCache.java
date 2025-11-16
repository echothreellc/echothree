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
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.tax.common.TaxOptions;
import com.echothree.model.control.tax.common.transfer.ItemTaxClassificationTransfer;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.tax.server.entity.ItemTaxClassification;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ItemTaxClassificationTransferCache
        extends BaseTaxTransferCache<ItemTaxClassification, ItemTaxClassificationTransfer> {
    
    GeoControl geoControl = Session.getModelController(GeoControl.class);
    ItemControl itemControl = Session.getModelController(ItemControl.class);
    TaxControl taxControl = Session.getModelController(TaxControl.class);

    /** Creates a new instance of ItemTaxClassificationTransferCache */
    public ItemTaxClassificationTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeEntityAttributeGroups(options.contains(TaxOptions.ItemTaxClassificationIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(TaxOptions.ItemTaxClassificationIncludeTagScopes));
        }

        setIncludeEntityInstance(true);
    }
    
    @Override
    public ItemTaxClassificationTransfer getTransfer(UserVisit userVisit, ItemTaxClassification itemTaxClassification) {
        var itemTaxClassificationTransfer = get(itemTaxClassification);
        
        if(itemTaxClassificationTransfer == null) {
            var itemTaxClassificationDetail = itemTaxClassification.getLastDetail();
            var item = itemControl.getItemTransfer(userVisit, itemTaxClassificationDetail.getItem());
            var countryGeoCode = geoControl.getCountryTransfer(userVisit, itemTaxClassificationDetail.getCountryGeoCode());
            var taxClassification = taxControl.getTaxClassificationTransfer(userVisit, itemTaxClassificationDetail.getTaxClassification());
            
            itemTaxClassificationTransfer = new ItemTaxClassificationTransfer(item, countryGeoCode, taxClassification);
            put(userVisit, itemTaxClassification, itemTaxClassificationTransfer);
        }
        
        return itemTaxClassificationTransfer;
    }
    
}
