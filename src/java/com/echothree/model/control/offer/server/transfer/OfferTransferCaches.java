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
import javax.enterprise.inject.spi.CDI;

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
            offerTransferCache = CDI.current().select(OfferTransferCache.class).get();
        
        return offerTransferCache;
    }
    
    public OfferDescriptionTransferCache getOfferDescriptionTransferCache() {
        if(offerDescriptionTransferCache == null)
            offerDescriptionTransferCache = CDI.current().select(OfferDescriptionTransferCache.class).get();
        
        return offerDescriptionTransferCache;
    }
    
    public OfferUseTransferCache getOfferUseTransferCache() {
        if(offerUseTransferCache == null)
            offerUseTransferCache = CDI.current().select(OfferUseTransferCache.class).get();
        
        return offerUseTransferCache;
    }
    
    public SourceTransferCache getSourceTransferCache() {
        if(sourceTransferCache == null)
            sourceTransferCache = CDI.current().select(SourceTransferCache.class).get();
        
        return sourceTransferCache;
    }
    
    public OfferItemPriceTransferCache getOfferItemPriceTransferCache() {
        if(offerItemPriceTransferCache == null)
            offerItemPriceTransferCache = CDI.current().select(OfferItemPriceTransferCache.class).get();
        
        return offerItemPriceTransferCache;
    }
    
    public OfferItemTransferCache getOfferItemTransferCache() {
        if(offerItemTransferCache == null)
            offerItemTransferCache = CDI.current().select(OfferItemTransferCache.class).get();
        
        return offerItemTransferCache;
    }
    
    public OfferNameElementDescriptionTransferCache getOfferNameElementDescriptionTransferCache() {
        if(offerNameElementDescriptionTransferCache == null)
            offerNameElementDescriptionTransferCache = CDI.current().select(OfferNameElementDescriptionTransferCache.class).get();
        
        return offerNameElementDescriptionTransferCache;
    }
    
    public OfferNameElementTransferCache getOfferNameElementTransferCache() {
        if(offerNameElementTransferCache == null)
            offerNameElementTransferCache = CDI.current().select(OfferNameElementTransferCache.class).get();
        
        return offerNameElementTransferCache;
    }
    
    public UseNameElementDescriptionTransferCache getUseNameElementDescriptionTransferCache() {
        if(useNameElementDescriptionTransferCache == null)
            useNameElementDescriptionTransferCache = CDI.current().select(UseNameElementDescriptionTransferCache.class).get();
        
        return useNameElementDescriptionTransferCache;
    }
    
    public UseNameElementTransferCache getUseNameElementTransferCache() {
        if(useNameElementTransferCache == null)
            useNameElementTransferCache = CDI.current().select(UseNameElementTransferCache.class).get();
        
        return useNameElementTransferCache;
    }
    
    public UseDescriptionTransferCache getUseDescriptionTransferCache() {
        if(useDescriptionTransferCache == null)
            useDescriptionTransferCache = CDI.current().select(UseDescriptionTransferCache.class).get();
        
        return useDescriptionTransferCache;
    }
    
    public UseTransferCache getUseTransferCache() {
        if(useTransferCache == null)
            useTransferCache = CDI.current().select(UseTransferCache.class).get();
        
        return useTransferCache;
    }
    
    public UseTypeDescriptionTransferCache getUseTypeDescriptionTransferCache() {
        if(useTypeDescriptionTransferCache == null)
            useTypeDescriptionTransferCache = CDI.current().select(UseTypeDescriptionTransferCache.class).get();
        
        return useTypeDescriptionTransferCache;
    }
    
    public UseTypeTransferCache getUseTypeTransferCache() {
        if(useTypeTransferCache == null)
            useTypeTransferCache = CDI.current().select(UseTypeTransferCache.class).get();
        
        return useTypeTransferCache;
    }
    
    public OfferCustomerTypeTransferCache getOfferCustomerTypeTransferCache() {
        if(offerCustomerTypeTransferCache == null)
            offerCustomerTypeTransferCache = CDI.current().select(OfferCustomerTypeTransferCache.class).get();
        
        return offerCustomerTypeTransferCache;
    }
    
    public OfferChainTypeTransferCache getOfferChainTypeTransferCache() {
        if(offerChainTypeTransferCache == null)
            offerChainTypeTransferCache = CDI.current().select(OfferChainTypeTransferCache.class).get();
        
        return offerChainTypeTransferCache;
    }
    
}
