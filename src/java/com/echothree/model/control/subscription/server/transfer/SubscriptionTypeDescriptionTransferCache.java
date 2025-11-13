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

import com.echothree.model.control.subscription.common.transfer.SubscriptionTypeDescriptionTransfer;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.subscription.server.entity.SubscriptionTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SubscriptionTypeDescriptionTransferCache
        extends BaseSubscriptionDescriptionTransferCache<SubscriptionTypeDescription, SubscriptionTypeDescriptionTransfer> {
    
    /** Creates a new instance of SubscriptionTypeDescriptionTransferCache */
    public SubscriptionTypeDescriptionTransferCache(SubscriptionControl subscriptionControl) {
        super(subscriptionControl);
    }
    
    public SubscriptionTypeDescriptionTransfer getSubscriptionTypeDescriptionTransfer(SubscriptionTypeDescription subscriptionTypeDescription) {
        var subscriptionTypeDescriptionTransfer = get(subscriptionTypeDescription);
        
        if(subscriptionTypeDescriptionTransfer == null) {
            var subscriptionTypeTransfer = subscriptionControl.getSubscriptionTypeTransfer(userVisit, subscriptionTypeDescription.getSubscriptionType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, subscriptionTypeDescription.getLanguage());
            
            subscriptionTypeDescriptionTransfer = new SubscriptionTypeDescriptionTransfer(languageTransfer, subscriptionTypeTransfer, subscriptionTypeDescription.getDescription());
            put(userVisit, subscriptionTypeDescription, subscriptionTypeDescriptionTransfer);
        }
        
        return subscriptionTypeDescriptionTransfer;
    }
    
}
