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

import com.echothree.model.control.subscription.common.transfer.SubscriptionKindTransfer;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.subscription.server.entity.SubscriptionKind;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SubscriptionKindTransferCache
        extends BaseSubscriptionTransferCache<SubscriptionKind, SubscriptionKindTransfer> {
    
    /** Creates a new instance of SubscriptionKindTransferCache */
    public SubscriptionKindTransferCache(SubscriptionControl subscriptionControl) {
        super(subscriptionControl);
        
        setIncludeEntityInstance(true);
    }
    
    public SubscriptionKindTransfer getSubscriptionKindTransfer(SubscriptionKind subscriptionKind) {
        var subscriptionKindTransfer = get(subscriptionKind);
        
        if(subscriptionKindTransfer == null) {
            var subscriptionKindDetail = subscriptionKind.getLastDetail();
            var subscriptionKindName = subscriptionKindDetail.getSubscriptionKindName();
            var isDefault = subscriptionKindDetail.getIsDefault();
            var sortOrder = subscriptionKindDetail.getSortOrder();
            var description = subscriptionControl.getBestSubscriptionKindDescription(subscriptionKind, getLanguage(userVisit));
            
            subscriptionKindTransfer = new SubscriptionKindTransfer(subscriptionKindName, isDefault, sortOrder, description);
            put(userVisit, subscriptionKind, subscriptionKindTransfer);
        }
        
        return subscriptionKindTransfer;
    }
    
}
