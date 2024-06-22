// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTypeTransfer;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.subscription.server.entity.Subscription;
import com.echothree.model.data.subscription.server.entity.SubscriptionDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class SubscriptionTransferCache
        extends BaseSubscriptionTransferCache<Subscription, SubscriptionTransfer> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of SubscriptionTransferCache */
    public SubscriptionTransferCache(UserVisit userVisit, SubscriptionControl subscriptionControl) {
        super(userVisit, subscriptionControl);
        
        setIncludeEntityInstance(true);
    }
    
    public SubscriptionTransfer getSubscriptionTransfer(Subscription subscription) {
        SubscriptionTransfer subscriptionTransfer = get(subscription);

        if(subscriptionTransfer == null) {
            SubscriptionDetail subscriptionDetail = subscription.getLastDetail();
            String subscriptionName = subscriptionDetail.getSubscriptionName();
            SubscriptionTypeTransfer subscriptionType = subscriptionControl.getSubscriptionTypeTransfer(userVisit, subscriptionDetail.getSubscriptionType());
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, subscriptionDetail.getParty());
            Long unformattedStartTime = subscriptionDetail.getStartTime();
            String startTime = formatTypicalDateTime(unformattedStartTime);
            Long unformattedEndTime = subscriptionDetail.getEndTime();
            String endTime = formatTypicalDateTime(unformattedEndTime);

            subscriptionTransfer = new SubscriptionTransfer(subscriptionName, subscriptionType, party, unformattedStartTime, startTime, unformattedEndTime,
                    endTime);
            put(subscription, subscriptionTransfer);
        }

        return subscriptionTransfer;
    }

}
