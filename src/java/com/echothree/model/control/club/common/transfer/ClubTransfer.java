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

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.filter.common.transfer.FilterTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ClubTransfer
        extends BaseTransfer {
    
    private String clubName;
    private SubscriptionTypeTransfer subscriptionType;
    private FilterTransfer clubPriceFilter;
    private CurrencyTransfer currency;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ClubTransfer */
    public ClubTransfer(String clubName, SubscriptionTypeTransfer subscriptionType, FilterTransfer clubPriceFilter,
            CurrencyTransfer currency, Boolean isDefault, Integer sortOrder,
            String description) {
        this.clubName = clubName;
        this.subscriptionType = subscriptionType;
        this.clubPriceFilter = clubPriceFilter;
        this.currency = currency;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getClubName() {
        return clubName;
    }
    
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    
    public SubscriptionTypeTransfer getSubscriptionType() {
        return subscriptionType;
    }
    
    public void setSubscriptionType(SubscriptionTypeTransfer subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
    
    public FilterTransfer getClubPriceFilter() {
        return clubPriceFilter;
    }
    
    public void setClubPriceFilter(FilterTransfer clubPriceFilter) {
        this.clubPriceFilter = clubPriceFilter;
    }
    
    public CurrencyTransfer getCurrency() {
        return currency;
    }
    
    public void setCurrency(CurrencyTransfer currency) {
        this.currency = currency;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
