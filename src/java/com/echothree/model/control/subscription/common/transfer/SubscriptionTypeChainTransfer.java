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

package com.echothree.model.control.subscription.common.transfer;

import com.echothree.model.control.chain.common.transfer.ChainTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class SubscriptionTypeChainTransfer
        extends BaseTransfer {
    
    private SubscriptionTypeTransfer subscriptionType;
    private ChainTransfer chain;
    private Long unformattedRemainingTime;
    private String remainingTime;
    
    /** Creates a new instance of SubscriptionTypeChainTransfer */
    public SubscriptionTypeChainTransfer(SubscriptionTypeTransfer subscriptionType, ChainTransfer chain, Long unformattedRemainingTime, String remainingTime) {
        this.subscriptionType = subscriptionType;
        this.chain = chain;
        this.unformattedRemainingTime = unformattedRemainingTime;
        this.remainingTime = remainingTime;
    }

    /**
     * Returns the subscriptionType.
     * @return the subscriptionType
     */
    public SubscriptionTypeTransfer getSubscriptionType() {
        return subscriptionType;
    }

    /**
     * Sets the subscriptionType.
     * @param subscriptionType the subscriptionType to set
     */
    public void setSubscriptionType(SubscriptionTypeTransfer subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    /**
     * Returns the chain.
     * @return the chain
     */
    public ChainTransfer getChain() {
        return chain;
    }

    /**
     * Sets the chain.
     * @param chain the chain to set
     */
    public void setChain(ChainTransfer chain) {
        this.chain = chain;
    }

    /**
     * Returns the unformattedRemainingTime.
     * @return the unformattedRemainingTime
     */
    public Long getUnformattedRemainingTime() {
        return unformattedRemainingTime;
    }

    /**
     * Sets the unformattedRemainingTime.
     * @param unformattedRemainingTime the unformattedRemainingTime to set
     */
    public void setUnformattedRemainingTime(Long unformattedRemainingTime) {
        this.unformattedRemainingTime = unformattedRemainingTime;
    }

    /**
     * Returns the remainingTime.
     * @return the remainingTime
     */
    public String getRemainingTime() {
        return remainingTime;
    }

    /**
     * Sets the remainingTime.
     * @param remainingTime the remainingTime to set
     */
    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }
    
}
