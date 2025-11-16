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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class OfferTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    OfferTransferCache offerTransferCache;
    
    @Inject
    OfferDescriptionTransferCache offerDescriptionTransferCache;
    
    @Inject
    OfferUseTransferCache offerUseTransferCache;
    
    @Inject
    SourceTransferCache sourceTransferCache;
    
    @Inject
    OfferItemPriceTransferCache offerItemPriceTransferCache;
    
    @Inject
    OfferItemTransferCache offerItemTransferCache;
    
    @Inject
    OfferNameElementDescriptionTransferCache offerNameElementDescriptionTransferCache;
    
    @Inject
    OfferNameElementTransferCache offerNameElementTransferCache;
    
    @Inject
    UseNameElementDescriptionTransferCache useNameElementDescriptionTransferCache;
    
    @Inject
    UseNameElementTransferCache useNameElementTransferCache;
    
    @Inject
    UseDescriptionTransferCache useDescriptionTransferCache;
    
    @Inject
    UseTransferCache useTransferCache;
    
    @Inject
    UseTypeDescriptionTransferCache useTypeDescriptionTransferCache;
    
    @Inject
    UseTypeTransferCache useTypeTransferCache;
    
    @Inject
    OfferCustomerTypeTransferCache offerCustomerTypeTransferCache;
    
    @Inject
    OfferChainTypeTransferCache offerChainTypeTransferCache;

    /** Creates a new instance of OfferTransferCaches */
    protected OfferTransferCaches() {
        super();
    }
    
    public OfferTransferCache getOfferTransferCache() {
        return offerTransferCache;
    }
    
    public OfferDescriptionTransferCache getOfferDescriptionTransferCache() {
        return offerDescriptionTransferCache;
    }
    
    public OfferUseTransferCache getOfferUseTransferCache() {
        return offerUseTransferCache;
    }
    
    public SourceTransferCache getSourceTransferCache() {
        return sourceTransferCache;
    }
    
    public OfferItemPriceTransferCache getOfferItemPriceTransferCache() {
        return offerItemPriceTransferCache;
    }
    
    public OfferItemTransferCache getOfferItemTransferCache() {
        return offerItemTransferCache;
    }
    
    public OfferNameElementDescriptionTransferCache getOfferNameElementDescriptionTransferCache() {
        return offerNameElementDescriptionTransferCache;
    }
    
    public OfferNameElementTransferCache getOfferNameElementTransferCache() {
        return offerNameElementTransferCache;
    }
    
    public UseNameElementDescriptionTransferCache getUseNameElementDescriptionTransferCache() {
        return useNameElementDescriptionTransferCache;
    }
    
    public UseNameElementTransferCache getUseNameElementTransferCache() {
        return useNameElementTransferCache;
    }
    
    public UseDescriptionTransferCache getUseDescriptionTransferCache() {
        return useDescriptionTransferCache;
    }
    
    public UseTransferCache getUseTransferCache() {
        return useTransferCache;
    }
    
    public UseTypeDescriptionTransferCache getUseTypeDescriptionTransferCache() {
        return useTypeDescriptionTransferCache;
    }
    
    public UseTypeTransferCache getUseTypeTransferCache() {
        return useTypeTransferCache;
    }
    
    public OfferCustomerTypeTransferCache getOfferCustomerTypeTransferCache() {
        return offerCustomerTypeTransferCache;
    }
    
    public OfferChainTypeTransferCache getOfferChainTypeTransferCache() {
        return offerChainTypeTransferCache;
    }
    
}
