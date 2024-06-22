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

import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.subscription.common.transfer.SubscriptionKindTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTypeTransfer;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.subscription.server.entity.SubscriptionKind;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.model.data.subscription.server.entity.SubscriptionTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class SubscriptionTypeTransferCache
        extends BaseSubscriptionTransferCache<SubscriptionType, SubscriptionTypeTransfer> {
    
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    
    /** Creates a new instance of SubscriptionTypeTransferCache */
    public SubscriptionTypeTransferCache(UserVisit userVisit, SubscriptionControl subscriptionControl) {
        super(userVisit, subscriptionControl);
        
        setIncludeEntityInstance(true);
    }
    
    public SubscriptionTypeTransfer getSubscriptionTypeTransfer(SubscriptionType subscriptionType) {
        SubscriptionTypeTransfer subscriptionTypeTransfer = get(subscriptionType);
        
        if(subscriptionTypeTransfer == null) {
            SubscriptionTypeDetail subscriptionTypeDetail = subscriptionType.getLastDetail();
            SubscriptionKind subscriptionKind = subscriptionTypeDetail.getSubscriptionKind();
            SubscriptionKindTransfer subscriptionKindTransfer = subscriptionKind == null? null: subscriptionControl.getSubscriptionKindTransfer(userVisit, subscriptionKind);
            String subscriptionTypeName = subscriptionTypeDetail.getSubscriptionTypeName();
            Sequence subscriptionSequence = subscriptionTypeDetail.getSubscriptionSequence();
            SequenceTransfer subscriptionSequenceTransfer = subscriptionSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, subscriptionSequence);
            Boolean isDefault = subscriptionTypeDetail.getIsDefault();
            Integer sortOrder = subscriptionTypeDetail.getSortOrder();
            String description = subscriptionControl.getBestSubscriptionTypeDescription(subscriptionType, getLanguage());
            
            subscriptionTypeTransfer = new SubscriptionTypeTransfer(subscriptionKindTransfer, subscriptionTypeName,
                    subscriptionSequenceTransfer, isDefault, sortOrder, description);
            put(subscriptionType, subscriptionTypeTransfer);
        }
        
        return subscriptionTypeTransfer;
    }
    
}
