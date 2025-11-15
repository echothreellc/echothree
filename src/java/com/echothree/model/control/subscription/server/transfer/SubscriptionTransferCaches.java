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

package com.echothree.model.control.subscription.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;

public class SubscriptionTransferCaches
        extends BaseTransferCaches {
    
    protected SubscriptionKindTransferCache subscriptionKindTransferCache;
    protected SubscriptionKindDescriptionTransferCache subscriptionKindDescriptionTransferCache;
    protected SubscriptionTypeTransferCache subscriptionTypeTransferCache;
    protected SubscriptionTypeDescriptionTransferCache subscriptionTypeDescriptionTransferCache;
    protected SubscriptionTransferCache subscriptionTransferCache;
    protected SubscriptionTypeChainTransferCache subscriptionTypeChainTransferCache;
    
    /** Creates a new instance of SubscriptionTransferCaches */
    public SubscriptionTransferCaches() {
        super();
    }
    
    public SubscriptionKindTransferCache getSubscriptionKindTransferCache() {
        if(subscriptionKindTransferCache == null)
            subscriptionKindTransferCache = new SubscriptionKindTransferCache();

        return subscriptionKindTransferCache;
    }

    public SubscriptionKindDescriptionTransferCache getSubscriptionKindDescriptionTransferCache() {
        if(subscriptionKindDescriptionTransferCache == null)
            subscriptionKindDescriptionTransferCache = new SubscriptionKindDescriptionTransferCache();

        return subscriptionKindDescriptionTransferCache;
    }

    public SubscriptionTypeTransferCache getSubscriptionTypeTransferCache() {
        if(subscriptionTypeTransferCache == null)
            subscriptionTypeTransferCache = new SubscriptionTypeTransferCache();
        
        return subscriptionTypeTransferCache;
    }
    
    public SubscriptionTypeDescriptionTransferCache getSubscriptionTypeDescriptionTransferCache() {
        if(subscriptionTypeDescriptionTransferCache == null)
            subscriptionTypeDescriptionTransferCache = new SubscriptionTypeDescriptionTransferCache();
        
        return subscriptionTypeDescriptionTransferCache;
    }
    
    public SubscriptionTransferCache getSubscriptionTransferCache() {
        if(subscriptionTransferCache == null)
            subscriptionTransferCache = new SubscriptionTransferCache();
        
        return subscriptionTransferCache;
    }
    
    public SubscriptionTypeChainTransferCache getSubscriptionTypeChainTransferCache() {
        if(subscriptionTypeChainTransferCache == null)
            subscriptionTypeChainTransferCache = new SubscriptionTypeChainTransferCache();
        
        return subscriptionTypeChainTransferCache;
    }
    
}
