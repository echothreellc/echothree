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

package com.echothree.model.control.club.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.club.common.transfer.ClubTransfer;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ClubTransferCache
        extends BaseClubTransferCache<Club, ClubTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    ClubControl clubControl = Session.getModelController(ClubControl.class);
    FilterControl filterControl = Session.getModelController(FilterControl.class);
    SubscriptionControl subscriptionControl = Session.getModelController(SubscriptionControl.class);
    
    /** Creates a new instance of ClubTransferCache */
    public ClubTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public ClubTransfer getClubTransfer(UserVisit userVisit, Club club) {
        var clubTransfer = get(club);
        
        if(clubTransfer == null) {
            var clubDetail = club.getLastDetail();
            var clubName = clubDetail.getClubName();
            var subscriptionTypeTransfer = subscriptionControl.getSubscriptionTypeTransfer(userVisit, clubDetail.getSubscriptionType());
            var clubPriceFilter = clubDetail.getClubPriceFilter();
            var clubPriceFilterTransfer = clubPriceFilter == null? null: filterControl.getFilterTransfer(userVisit, clubPriceFilter);
            var currency = clubDetail.getCurrency();
            var currencyTransfer = currency == null? null: accountingControl.getCurrencyTransfer(userVisit, currency);
            var isDefault = clubDetail.getIsDefault();
            var sortOrder = clubDetail.getSortOrder();
            var description = clubControl.getBestClubDescription(club, getLanguage(userVisit));
            
            clubTransfer = new ClubTransfer(clubName, subscriptionTypeTransfer, clubPriceFilterTransfer, currencyTransfer, isDefault, sortOrder, description);
            put(userVisit, club, clubTransfer);
        }
        
        return clubTransfer;
    }
    
}
