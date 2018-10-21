// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.control.subscription.remote.transfer.SubscriptionKindDescriptionTransfer;
import com.echothree.model.control.subscription.remote.transfer.SubscriptionKindTransfer;
import com.echothree.model.control.subscription.server.SubscriptionControl;
import com.echothree.model.data.subscription.server.entity.SubscriptionKindDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SubscriptionKindDescriptionTransferCache
        extends BaseSubscriptionDescriptionTransferCache<SubscriptionKindDescription, SubscriptionKindDescriptionTransfer> {
    
    /** Creates a new instance of SubscriptionKindDescriptionTransferCache */
    public SubscriptionKindDescriptionTransferCache(UserVisit userVisit, SubscriptionControl subscriptionControl) {
        super(userVisit, subscriptionControl);
    }
    
    public SubscriptionKindDescriptionTransfer getSubscriptionKindDescriptionTransfer(SubscriptionKindDescription subscriptionKindDescription) {
        SubscriptionKindDescriptionTransfer subscriptionKindDescriptionTransfer = get(subscriptionKindDescription);
        
        if(subscriptionKindDescriptionTransfer == null) {
            SubscriptionKindTransfer subscriptionKindTransfer = subscriptionControl.getSubscriptionKindTransfer(userVisit, subscriptionKindDescription.getSubscriptionKind());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, subscriptionKindDescription.getLanguage());
            
            subscriptionKindDescriptionTransfer = new SubscriptionKindDescriptionTransfer(languageTransfer, subscriptionKindTransfer, subscriptionKindDescription.getDescription());
            put(subscriptionKindDescription, subscriptionKindDescriptionTransfer);
        }
        
        return subscriptionKindDescriptionTransfer;
    }
    
}
