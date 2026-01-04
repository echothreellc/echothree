// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTypeTransfer;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SubscriptionTypeTransferCache
        extends BaseSubscriptionTransferCache<SubscriptionType, SubscriptionTypeTransfer> {
    
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    SubscriptionControl subscriptionControl = Session.getModelController(SubscriptionControl.class);

    /** Creates a new instance of SubscriptionTypeTransferCache */
    protected SubscriptionTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public SubscriptionTypeTransfer getSubscriptionTypeTransfer(UserVisit userVisit, SubscriptionType subscriptionType) {
        var subscriptionTypeTransfer = get(subscriptionType);
        
        if(subscriptionTypeTransfer == null) {
            var subscriptionTypeDetail = subscriptionType.getLastDetail();
            var subscriptionKind = subscriptionTypeDetail.getSubscriptionKind();
            var subscriptionKindTransfer = subscriptionKind == null? null: subscriptionControl.getSubscriptionKindTransfer(userVisit, subscriptionKind);
            var subscriptionTypeName = subscriptionTypeDetail.getSubscriptionTypeName();
            var subscriptionSequence = subscriptionTypeDetail.getSubscriptionSequence();
            var subscriptionSequenceTransfer = subscriptionSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, subscriptionSequence);
            var isDefault = subscriptionTypeDetail.getIsDefault();
            var sortOrder = subscriptionTypeDetail.getSortOrder();
            var description = subscriptionControl.getBestSubscriptionTypeDescription(subscriptionType, getLanguage(userVisit));
            
            subscriptionTypeTransfer = new SubscriptionTypeTransfer(subscriptionKindTransfer, subscriptionTypeName,
                    subscriptionSequenceTransfer, isDefault, sortOrder, description);
            put(userVisit, subscriptionType, subscriptionTypeTransfer);
        }
        
        return subscriptionTypeTransfer;
    }
    
}
