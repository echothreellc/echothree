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

package com.echothree.model.control.club.common.transfer;

import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ClubItemTransfer
        extends BaseTransfer {
    
    private ClubTransfer club;
    private ClubItemTypeTransfer clubItemType;
    private ItemTransfer item;
    private Long unformattedSubscriptionTime;
    private String subscriptionTime;
    
    /** Creates a new instance of ClubItemTransfer */
    public ClubItemTransfer(ClubTransfer club, ClubItemTypeTransfer clubItemType, ItemTransfer item, Long unformattedSubscriptionTime,
            String subscriptionTime) {
        this.club = club;
        this.clubItemType = clubItemType;
        this.item = item;
        this.unformattedSubscriptionTime = unformattedSubscriptionTime;
        this.subscriptionTime = subscriptionTime;
    }

    /**
     * Returns the club.
     * @return the club
     */
    public ClubTransfer getClub() {
        return club;
    }

    /**
     * Sets the club.
     * @param club the club to set
     */
    public void setClub(ClubTransfer club) {
        this.club = club;
    }

    /**
     * Returns the clubItemType.
     * @return the clubItemType
     */
    public ClubItemTypeTransfer getClubItemType() {
        return clubItemType;
    }

    /**
     * Sets the clubItemType.
     * @param clubItemType the clubItemType to set
     */
    public void setClubItemType(ClubItemTypeTransfer clubItemType) {
        this.clubItemType = clubItemType;
    }

    /**
     * Returns the item.
     * @return the item
     */
    public ItemTransfer getItem() {
        return item;
    }

    /**
     * Sets the item.
     * @param item the item to set
     */
    public void setItem(ItemTransfer item) {
        this.item = item;
    }

    /**
     * Returns the unformattedSubscriptionTime.
     * @return the unformattedSubscriptionTime
     */
    public Long getUnformattedSubscriptionTime() {
        return unformattedSubscriptionTime;
    }

    /**
     * Sets the unformattedSubscriptionTime.
     * @param unformattedSubscriptionTime the unformattedSubscriptionTime to set
     */
    public void setUnformattedSubscriptionTime(Long unformattedSubscriptionTime) {
        this.unformattedSubscriptionTime = unformattedSubscriptionTime;
    }

    /**
     * Returns the subscriptionTime.
     * @return the subscriptionTime
     */
    public String getSubscriptionTime() {
        return subscriptionTime;
    }

    /**
     * Sets the subscriptionTime.
     * @param subscriptionTime the subscriptionTime to set
     */
    public void setSubscriptionTime(String subscriptionTime) {
        this.subscriptionTime = subscriptionTime;
    }
    
}
