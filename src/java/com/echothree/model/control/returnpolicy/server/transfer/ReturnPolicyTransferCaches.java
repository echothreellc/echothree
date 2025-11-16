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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ReturnPolicyTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    ReturnPolicyTransferCache returnPolicyTransferCache;
    
    @Inject
    ReturnPolicyTranslationTransferCache returnPolicyTranslationTransferCache;
    
    @Inject
    PartyReturnPolicyTransferCache partyReturnPolicyTransferCache;
    
    @Inject
    ReturnKindDescriptionTransferCache returnKindDescriptionTransferCache;
    
    @Inject
    ReturnReasonDescriptionTransferCache returnReasonDescriptionTransferCache;
    
    @Inject
    ReturnTypeDescriptionTransferCache returnTypeDescriptionTransferCache;
    
    @Inject
    ReturnKindTransferCache returnKindTransferCache;
    
    @Inject
    ReturnReasonTransferCache returnReasonTransferCache;
    
    @Inject
    ReturnTypeTransferCache returnTypeTransferCache;
    
    @Inject
    ReturnPolicyReasonTransferCache returnPolicyReasonTransferCache;
    
    @Inject
    ReturnReasonTypeTransferCache returnReasonTypeTransferCache;
    
    @Inject
    ReturnTypeShippingMethodTransferCache returnTypeShippingMethodTransferCache;

    /** Creates a new instance of ReturnPolicyTransferCaches */
    protected ReturnPolicyTransferCaches() {
        super();
    }
    
    public ReturnPolicyTransferCache getReturnPolicyTransferCache() {
        return returnPolicyTransferCache;
    }
    
    public ReturnPolicyTranslationTransferCache getReturnPolicyTranslationTransferCache() {
        return returnPolicyTranslationTransferCache;
    }
    
    public PartyReturnPolicyTransferCache getPartyReturnPolicyTransferCache() {
        return partyReturnPolicyTransferCache;
    }
    
    public ReturnKindDescriptionTransferCache getReturnKindDescriptionTransferCache() {
        return returnKindDescriptionTransferCache;
    }
    
    public ReturnReasonDescriptionTransferCache getReturnReasonDescriptionTransferCache() {
        return returnReasonDescriptionTransferCache;
    }
    
    public ReturnTypeDescriptionTransferCache getReturnTypeDescriptionTransferCache() {
        return returnTypeDescriptionTransferCache;
    }
    
    public ReturnKindTransferCache getReturnKindTransferCache() {
        return returnKindTransferCache;
    }
    
    public ReturnReasonTransferCache getReturnReasonTransferCache() {
        return returnReasonTransferCache;
    }
    
    public ReturnTypeTransferCache getReturnTypeTransferCache() {
        return returnTypeTransferCache;
    }
    
    public ReturnPolicyReasonTransferCache getReturnPolicyReasonTransferCache() {
        return returnPolicyReasonTransferCache;
    }
    
    public ReturnReasonTypeTransferCache getReturnReasonTypeTransferCache() {
        return returnReasonTypeTransferCache;
    }
    
    public ReturnTypeShippingMethodTransferCache getReturnTypeShippingMethodTransferCache() {
        return returnTypeShippingMethodTransferCache;
    }
    
}
