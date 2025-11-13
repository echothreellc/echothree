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

package com.echothree.model.control.offer.server.transfer;

import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class OfferTransferCaches
        extends BaseTransferCaches {
    
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
    public OfferTransferCaches() {
        super();
    }
    
    public OfferTransferCache getOfferTransferCache() {
        if(offerTransferCache == null)
            offerTransferCache = new OfferTransferCache();
        
        return offerTransferCache;
    }
    
    public OfferDescriptionTransferCache getOfferDescriptionTransferCache() {
        if(offerDescriptionTransferCache == null)
            offerDescriptionTransferCache = new OfferDescriptionTransferCache();
        
        return offerDescriptionTransferCache;
    }
    
    public OfferUseTransferCache getOfferUseTransferCache() {
        if(offerUseTransferCache == null)
            offerUseTransferCache = new OfferUseTransferCache();
        
        return offerUseTransferCache;
    }
    
    public SourceTransferCache getSourceTransferCache() {
        if(sourceTransferCache == null)
            sourceTransferCache = new SourceTransferCache();
        
        return sourceTransferCache;
    }
    
    public OfferItemPriceTransferCache getOfferItemPriceTransferCache() {
        if(offerItemPriceTransferCache == null)
            offerItemPriceTransferCache = new OfferItemPriceTransferCache();
        
        return offerItemPriceTransferCache;
    }
    
    public OfferItemTransferCache getOfferItemTransferCache() {
        if(offerItemTransferCache == null)
            offerItemTransferCache = new OfferItemTransferCache();
        
        return offerItemTransferCache;
    }
    
    public OfferNameElementDescriptionTransferCache getOfferNameElementDescriptionTransferCache() {
        if(offerNameElementDescriptionTransferCache == null)
            offerNameElementDescriptionTransferCache = new OfferNameElementDescriptionTransferCache();
        
        return offerNameElementDescriptionTransferCache;
    }
    
    public OfferNameElementTransferCache getOfferNameElementTransferCache() {
        if(offerNameElementTransferCache == null)
            offerNameElementTransferCache = new OfferNameElementTransferCache();
        
        return offerNameElementTransferCache;
    }
    
    public UseNameElementDescriptionTransferCache getUseNameElementDescriptionTransferCache() {
        if(useNameElementDescriptionTransferCache == null)
            useNameElementDescriptionTransferCache = new UseNameElementDescriptionTransferCache();
        
        return useNameElementDescriptionTransferCache;
    }
    
    public UseNameElementTransferCache getUseNameElementTransferCache() {
        if(useNameElementTransferCache == null)
            useNameElementTransferCache = new UseNameElementTransferCache();
        
        return useNameElementTransferCache;
    }
    
    public UseDescriptionTransferCache getUseDescriptionTransferCache() {
        if(useDescriptionTransferCache == null)
            useDescriptionTransferCache = new UseDescriptionTransferCache();
        
        return useDescriptionTransferCache;
    }
    
    public UseTransferCache getUseTransferCache() {
        if(useTransferCache == null)
            useTransferCache = new UseTransferCache();
        
        return useTransferCache;
    }
    
    public UseTypeDescriptionTransferCache getUseTypeDescriptionTransferCache() {
        if(useTypeDescriptionTransferCache == null)
            useTypeDescriptionTransferCache = new UseTypeDescriptionTransferCache();
        
        return useTypeDescriptionTransferCache;
    }
    
    public UseTypeTransferCache getUseTypeTransferCache() {
        if(useTypeTransferCache == null)
            useTypeTransferCache = new UseTypeTransferCache();
        
        return useTypeTransferCache;
    }
    
    public OfferCustomerTypeTransferCache getOfferCustomerTypeTransferCache() {
        if(offerCustomerTypeTransferCache == null)
            offerCustomerTypeTransferCache = new OfferCustomerTypeTransferCache();
        
        return offerCustomerTypeTransferCache;
    }
    
    public OfferChainTypeTransferCache getOfferChainTypeTransferCache() {
        if(offerChainTypeTransferCache == null)
            offerChainTypeTransferCache = new OfferChainTypeTransferCache();
        
        return offerChainTypeTransferCache;
    }
    
}
