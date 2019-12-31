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

package com.echothree.model.control.offer.server.transfer;

import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class OfferTransferCaches
        extends BaseTransferCaches {
    
    protected OfferControl offerControl;
    
    protected OfferTransferCache offerTransferCache;
    protected OfferDescriptionTransferCache offerDescriptionTransferCache;
    protected OfferUseTransferCache offerUseTransferCache;
    protected SourceTransferCache sourceTransferCache;
    protected OfferItemPriceTransferCache offerItemPriceTransferCache;
    protected OfferItemTransferCache offerItemTransferCache;
    protected OfferNameElementDescriptionTransferCache offerNameElementDescriptionTransferCache;
    protected OfferNameElementTransferCache offerNameElementTransferCache;
    protected UseNameElementDescriptionTransferCache useNameElementDescriptionTransferCache;
    protected UseNameElementTransferCache useNameElementTransferCache;
    protected UseDescriptionTransferCache useDescriptionTransferCache;
    protected UseTransferCache useTransferCache;
    protected UseTypeDescriptionTransferCache useTypeDescriptionTransferCache;
    protected UseTypeTransferCache useTypeTransferCache;
    protected OfferCustomerTypeTransferCache offerCustomerTypeTransferCache;
    protected OfferChainTypeTransferCache offerChainTypeTransferCache;
    
    /** Creates a new instance of OfferTransferCaches */
    public OfferTransferCaches(UserVisit userVisit, OfferControl offerControl) {
        super(userVisit);
        
        this.offerControl = offerControl;
    }
    
    public OfferTransferCache getOfferTransferCache() {
        if(offerTransferCache == null)
            offerTransferCache = new OfferTransferCache(userVisit, offerControl);
        
        return offerTransferCache;
    }
    
    public OfferDescriptionTransferCache getOfferDescriptionTransferCache() {
        if(offerDescriptionTransferCache == null)
            offerDescriptionTransferCache = new OfferDescriptionTransferCache(userVisit, offerControl);
        
        return offerDescriptionTransferCache;
    }
    
    public OfferUseTransferCache getOfferUseTransferCache() {
        if(offerUseTransferCache == null)
            offerUseTransferCache = new OfferUseTransferCache(userVisit, offerControl);
        
        return offerUseTransferCache;
    }
    
    public SourceTransferCache getSourceTransferCache() {
        if(sourceTransferCache == null)
            sourceTransferCache = new SourceTransferCache(userVisit, offerControl);
        
        return sourceTransferCache;
    }
    
    public OfferItemPriceTransferCache getOfferItemPriceTransferCache() {
        if(offerItemPriceTransferCache == null)
            offerItemPriceTransferCache = new OfferItemPriceTransferCache(userVisit, offerControl);
        
        return offerItemPriceTransferCache;
    }
    
    public OfferItemTransferCache getOfferItemTransferCache() {
        if(offerItemTransferCache == null)
            offerItemTransferCache = new OfferItemTransferCache(userVisit, offerControl);
        
        return offerItemTransferCache;
    }
    
    public OfferNameElementDescriptionTransferCache getOfferNameElementDescriptionTransferCache() {
        if(offerNameElementDescriptionTransferCache == null)
            offerNameElementDescriptionTransferCache = new OfferNameElementDescriptionTransferCache(userVisit, offerControl);
        
        return offerNameElementDescriptionTransferCache;
    }
    
    public OfferNameElementTransferCache getOfferNameElementTransferCache() {
        if(offerNameElementTransferCache == null)
            offerNameElementTransferCache = new OfferNameElementTransferCache(userVisit, offerControl);
        
        return offerNameElementTransferCache;
    }
    
    public UseNameElementDescriptionTransferCache getUseNameElementDescriptionTransferCache() {
        if(useNameElementDescriptionTransferCache == null)
            useNameElementDescriptionTransferCache = new UseNameElementDescriptionTransferCache(userVisit, offerControl);
        
        return useNameElementDescriptionTransferCache;
    }
    
    public UseNameElementTransferCache getUseNameElementTransferCache() {
        if(useNameElementTransferCache == null)
            useNameElementTransferCache = new UseNameElementTransferCache(userVisit, offerControl);
        
        return useNameElementTransferCache;
    }
    
    public UseDescriptionTransferCache getUseDescriptionTransferCache() {
        if(useDescriptionTransferCache == null)
            useDescriptionTransferCache = new UseDescriptionTransferCache(userVisit, offerControl);
        
        return useDescriptionTransferCache;
    }
    
    public UseTransferCache getUseTransferCache() {
        if(useTransferCache == null)
            useTransferCache = new UseTransferCache(userVisit, offerControl);
        
        return useTransferCache;
    }
    
    public UseTypeDescriptionTransferCache getUseTypeDescriptionTransferCache() {
        if(useTypeDescriptionTransferCache == null)
            useTypeDescriptionTransferCache = new UseTypeDescriptionTransferCache(userVisit, offerControl);
        
        return useTypeDescriptionTransferCache;
    }
    
    public UseTypeTransferCache getUseTypeTransferCache() {
        if(useTypeTransferCache == null)
            useTypeTransferCache = new UseTypeTransferCache(userVisit, offerControl);
        
        return useTypeTransferCache;
    }
    
    public OfferCustomerTypeTransferCache getOfferCustomerTypeTransferCache() {
        if(offerCustomerTypeTransferCache == null)
            offerCustomerTypeTransferCache = new OfferCustomerTypeTransferCache(userVisit, offerControl);
        
        return offerCustomerTypeTransferCache;
    }
    
    public OfferChainTypeTransferCache getOfferChainTypeTransferCache() {
        if(offerChainTypeTransferCache == null)
            offerChainTypeTransferCache = new OfferChainTypeTransferCache(userVisit, offerControl);
        
        return offerChainTypeTransferCache;
    }
    
}
