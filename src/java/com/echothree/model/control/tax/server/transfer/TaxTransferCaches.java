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

package com.echothree.model.control.tax.server.transfer;

import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class TaxTransferCaches
        extends BaseTransferCaches {
    
    protected TaxControl taxControl;
    
    protected TaxClassificationTransferCache taxClassificationTransferCache;
    protected TaxClassificationTranslationTransferCache taxClassificationTranslationTransferCache;
    protected ItemTaxClassificationTransferCache itemTaxClassificationTransferCache;
    protected TaxTransferCache taxTransferCache;
    protected TaxDescriptionTransferCache taxDescriptionTransferCache;
    protected GeoCodeTaxTransferCache geoCodeTaxTransferCache;
    
    /** Creates a new instance of TaxTransferCaches */
    public TaxTransferCaches(UserVisit userVisit, TaxControl taxControl) {
        super(userVisit);
        
        this.taxControl = taxControl;
    }
    
    public TaxClassificationTransferCache getTaxClassificationTransferCache() {
        if(taxClassificationTransferCache == null) {
            taxClassificationTransferCache = new TaxClassificationTransferCache(userVisit, taxControl);
        }

        return taxClassificationTransferCache;
    }
                                                                
    public TaxClassificationTranslationTransferCache getTaxClassificationTranslationTransferCache() {
        if(taxClassificationTranslationTransferCache == null) {
            taxClassificationTranslationTransferCache = new TaxClassificationTranslationTransferCache(userVisit, taxControl);
        }

        return taxClassificationTranslationTransferCache;
    }
    
    public ItemTaxClassificationTransferCache getItemTaxClassificationTransferCache() {
        if(itemTaxClassificationTransferCache == null) {
            itemTaxClassificationTransferCache = new ItemTaxClassificationTransferCache(userVisit, taxControl);
        }

        return itemTaxClassificationTransferCache;
    }

    public TaxTransferCache getTaxTransferCache() {
        if(taxTransferCache == null)
            taxTransferCache = new TaxTransferCache(userVisit, taxControl);
        
        return taxTransferCache;
    }
    
    public TaxDescriptionTransferCache getTaxDescriptionTransferCache() {
        if(taxDescriptionTransferCache == null)
            taxDescriptionTransferCache = new TaxDescriptionTransferCache(userVisit, taxControl);
        
        return taxDescriptionTransferCache;
    }
    
    public GeoCodeTaxTransferCache getGeoCodeTaxTransferCache() {
        if(geoCodeTaxTransferCache == null)
            geoCodeTaxTransferCache = new GeoCodeTaxTransferCache(userVisit, taxControl);
        
        return geoCodeTaxTransferCache;
    }
    
}
