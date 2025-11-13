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

import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class ReturnPolicyTransferCaches
        extends BaseTransferCaches {
    
    protected ReturnPolicyControl returnPolicyControl;
    
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
    public ReturnPolicyTransferCaches(ReturnPolicyControl returnPolicyControl) {
        super();
        
        this.returnPolicyControl = returnPolicyControl;
    }
    
    public ReturnPolicyTransferCache getReturnPolicyTransferCache() {
        if(returnPolicyTransferCache == null)
            returnPolicyTransferCache = new ReturnPolicyTransferCache(returnPolicyControl);
        
        return returnPolicyTransferCache;
    }
    
    public ReturnPolicyTranslationTransferCache getReturnPolicyTranslationTransferCache() {
        if(returnPolicyTranslationTransferCache == null)
            returnPolicyTranslationTransferCache = new ReturnPolicyTranslationTransferCache(returnPolicyControl);
        
        return returnPolicyTranslationTransferCache;
    }
    
    public PartyReturnPolicyTransferCache getPartyReturnPolicyTransferCache() {
        if(partyReturnPolicyTransferCache == null)
            partyReturnPolicyTransferCache = new PartyReturnPolicyTransferCache(returnPolicyControl);
        
        return partyReturnPolicyTransferCache;
    }
    
    public ReturnKindDescriptionTransferCache getReturnKindDescriptionTransferCache() {
        if(returnKindDescriptionTransferCache == null)
            returnKindDescriptionTransferCache = new ReturnKindDescriptionTransferCache(returnPolicyControl);
        
        return returnKindDescriptionTransferCache;
    }
    
    public ReturnReasonDescriptionTransferCache getReturnReasonDescriptionTransferCache() {
        if(returnReasonDescriptionTransferCache == null)
            returnReasonDescriptionTransferCache = new ReturnReasonDescriptionTransferCache(returnPolicyControl);
        
        return returnReasonDescriptionTransferCache;
    }
    
    public ReturnTypeDescriptionTransferCache getReturnTypeDescriptionTransferCache() {
        if(returnTypeDescriptionTransferCache == null)
            returnTypeDescriptionTransferCache = new ReturnTypeDescriptionTransferCache(returnPolicyControl);
        
        return returnTypeDescriptionTransferCache;
    }
    
    public ReturnKindTransferCache getReturnKindTransferCache() {
        if(returnKindTransferCache == null)
            returnKindTransferCache = new ReturnKindTransferCache(returnPolicyControl);
        
        return returnKindTransferCache;
    }
    
    public ReturnReasonTransferCache getReturnReasonTransferCache() {
        if(returnReasonTransferCache == null)
            returnReasonTransferCache = new ReturnReasonTransferCache(returnPolicyControl);
        
        return returnReasonTransferCache;
    }
    
    public ReturnTypeTransferCache getReturnTypeTransferCache() {
        if(returnTypeTransferCache == null)
            returnTypeTransferCache = new ReturnTypeTransferCache(returnPolicyControl);
        
        return returnTypeTransferCache;
    }
    
    public ReturnPolicyReasonTransferCache getReturnPolicyReasonTransferCache() {
        if(returnPolicyReasonTransferCache == null)
            returnPolicyReasonTransferCache = new ReturnPolicyReasonTransferCache(returnPolicyControl);
        
        return returnPolicyReasonTransferCache;
    }
    
    public ReturnReasonTypeTransferCache getReturnReasonTypeTransferCache() {
        if(returnReasonTypeTransferCache == null)
            returnReasonTypeTransferCache = new ReturnReasonTypeTransferCache(returnPolicyControl);
        
        return returnReasonTypeTransferCache;
    }
    
    public ReturnTypeShippingMethodTransferCache getReturnTypeShippingMethodTransferCache() {
        if(returnTypeShippingMethodTransferCache == null)
            returnTypeShippingMethodTransferCache = new ReturnTypeShippingMethodTransferCache(returnPolicyControl);
        
        return returnTypeShippingMethodTransferCache;
    }
    
}
