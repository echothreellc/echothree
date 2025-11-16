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

package com.echothree.model.control.returnpolicy.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ReturnPolicyTransferCaches
        extends BaseTransferCaches {
    
    protected ReturnPolicyTransferCache returnPolicyTransferCache;
    protected ReturnPolicyTranslationTransferCache returnPolicyTranslationTransferCache;
    protected PartyReturnPolicyTransferCache partyReturnPolicyTransferCache;
    protected ReturnKindDescriptionTransferCache returnKindDescriptionTransferCache;
    protected ReturnReasonDescriptionTransferCache returnReasonDescriptionTransferCache;
    protected ReturnTypeDescriptionTransferCache returnTypeDescriptionTransferCache;
    protected ReturnKindTransferCache returnKindTransferCache;
    protected ReturnReasonTransferCache returnReasonTransferCache;
    protected ReturnTypeTransferCache returnTypeTransferCache;
    protected ReturnPolicyReasonTransferCache returnPolicyReasonTransferCache;
    protected ReturnReasonTypeTransferCache returnReasonTypeTransferCache;
    protected ReturnTypeShippingMethodTransferCache returnTypeShippingMethodTransferCache;
    
    /** Creates a new instance of ReturnPolicyTransferCaches */
    protected ReturnPolicyTransferCaches() {
        super();
    }
    
    public ReturnPolicyTransferCache getReturnPolicyTransferCache() {
        if(returnPolicyTransferCache == null)
            returnPolicyTransferCache = CDI.current().select(ReturnPolicyTransferCache.class).get();
        
        return returnPolicyTransferCache;
    }
    
    public ReturnPolicyTranslationTransferCache getReturnPolicyTranslationTransferCache() {
        if(returnPolicyTranslationTransferCache == null)
            returnPolicyTranslationTransferCache = CDI.current().select(ReturnPolicyTranslationTransferCache.class).get();
        
        return returnPolicyTranslationTransferCache;
    }
    
    public PartyReturnPolicyTransferCache getPartyReturnPolicyTransferCache() {
        if(partyReturnPolicyTransferCache == null)
            partyReturnPolicyTransferCache = CDI.current().select(PartyReturnPolicyTransferCache.class).get();
        
        return partyReturnPolicyTransferCache;
    }
    
    public ReturnKindDescriptionTransferCache getReturnKindDescriptionTransferCache() {
        if(returnKindDescriptionTransferCache == null)
            returnKindDescriptionTransferCache = CDI.current().select(ReturnKindDescriptionTransferCache.class).get();
        
        return returnKindDescriptionTransferCache;
    }
    
    public ReturnReasonDescriptionTransferCache getReturnReasonDescriptionTransferCache() {
        if(returnReasonDescriptionTransferCache == null)
            returnReasonDescriptionTransferCache = CDI.current().select(ReturnReasonDescriptionTransferCache.class).get();
        
        return returnReasonDescriptionTransferCache;
    }
    
    public ReturnTypeDescriptionTransferCache getReturnTypeDescriptionTransferCache() {
        if(returnTypeDescriptionTransferCache == null)
            returnTypeDescriptionTransferCache = CDI.current().select(ReturnTypeDescriptionTransferCache.class).get();
        
        return returnTypeDescriptionTransferCache;
    }
    
    public ReturnKindTransferCache getReturnKindTransferCache() {
        if(returnKindTransferCache == null)
            returnKindTransferCache = CDI.current().select(ReturnKindTransferCache.class).get();
        
        return returnKindTransferCache;
    }
    
    public ReturnReasonTransferCache getReturnReasonTransferCache() {
        if(returnReasonTransferCache == null)
            returnReasonTransferCache = CDI.current().select(ReturnReasonTransferCache.class).get();
        
        return returnReasonTransferCache;
    }
    
    public ReturnTypeTransferCache getReturnTypeTransferCache() {
        if(returnTypeTransferCache == null)
            returnTypeTransferCache = CDI.current().select(ReturnTypeTransferCache.class).get();
        
        return returnTypeTransferCache;
    }
    
    public ReturnPolicyReasonTransferCache getReturnPolicyReasonTransferCache() {
        if(returnPolicyReasonTransferCache == null)
            returnPolicyReasonTransferCache = CDI.current().select(ReturnPolicyReasonTransferCache.class).get();
        
        return returnPolicyReasonTransferCache;
    }
    
    public ReturnReasonTypeTransferCache getReturnReasonTypeTransferCache() {
        if(returnReasonTypeTransferCache == null)
            returnReasonTypeTransferCache = CDI.current().select(ReturnReasonTypeTransferCache.class).get();
        
        return returnReasonTypeTransferCache;
    }
    
    public ReturnTypeShippingMethodTransferCache getReturnTypeShippingMethodTransferCache() {
        if(returnTypeShippingMethodTransferCache == null)
            returnTypeShippingMethodTransferCache = CDI.current().select(ReturnTypeShippingMethodTransferCache.class).get();
        
        return returnTypeShippingMethodTransferCache;
    }
    
}
