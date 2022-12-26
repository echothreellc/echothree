// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.club.server.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.club.common.transfer.ClubTransfer;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.filter.common.transfer.FilterTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTypeTransfer;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.model.data.club.server.entity.ClubDetail;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ClubTransferCache
        extends BaseClubTransferCache<Club, ClubTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    FilterControl filterControl = Session.getModelController(FilterControl.class);
    SubscriptionControl subscriptionControl = Session.getModelController(SubscriptionControl.class);
    
    /** Creates a new instance of ClubTransferCache */
    public ClubTransferCache(UserVisit userVisit, ClubControl clubControl) {
        super(userVisit, clubControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ClubTransfer getClubTransfer(Club club) {
        ClubTransfer clubTransfer = get(club);
        
        if(clubTransfer == null) {
            ClubDetail clubDetail = club.getLastDetail();
            String clubName = clubDetail.getClubName();
            SubscriptionTypeTransfer subscriptionTypeTransfer = subscriptionControl.getSubscriptionTypeTransfer(userVisit, clubDetail.getSubscriptionType());
            Filter clubPriceFilter = clubDetail.getClubPriceFilter();
            FilterTransfer clubPriceFilterTransfer = clubPriceFilter == null? null: filterControl.getFilterTransfer(userVisit, clubPriceFilter);
            Currency currency = clubDetail.getCurrency();
            CurrencyTransfer currencyTransfer = currency == null? null: accountingControl.getCurrencyTransfer(userVisit, currency);
            Boolean isDefault = clubDetail.getIsDefault();
            Integer sortOrder = clubDetail.getSortOrder();
            String description = clubControl.getBestClubDescription(club, getLanguage());
            
            clubTransfer = new ClubTransfer(clubName, subscriptionTypeTransfer, clubPriceFilterTransfer, currencyTransfer, isDefault, sortOrder, description);
            put(club, clubTransfer);
        }
        
        return clubTransfer;
    }
    
}
