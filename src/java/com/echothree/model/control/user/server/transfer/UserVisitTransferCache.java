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

package com.echothree.model.control.user.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.common.UserOptions;
import com.echothree.model.control.user.common.transfer.UserVisitTransfer;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class UserVisitTransferCache
        extends BaseUserTransferCache<UserVisit, UserVisitTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    AssociateControl associateControl = Session.getModelController(AssociateControl.class);
    CampaignControl campaignControl = Session.getModelController(CampaignControl.class);
    OfferUseControl offerUseControl = Session.getModelController(OfferUseControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    UserControl userControl = Session.getModelController(UserControl.class);

    boolean includeUserVisitCampaigns;
    
    /** Creates a new instance of UserVisitTransferCache */
    public UserVisitTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            includeUserVisitCampaigns = options.contains(UserOptions.UserVisitIncludeUserVisitCampaigns);
        }
    }
    
    public UserVisitTransfer getUserVisitTransfer(UserVisit userVisit, UserVisit userVisitEntity) {
        var userVisitTransfer = get(userVisit);
        
        if(userVisitTransfer == null) {
            var userKey = userVisitEntity.getUserKey();
            var userKeyTransfer = userKey == null ? null : userControl.getUserKeyTransfer(userVisit, userKey);
            var preferredLanguage = userVisitEntity.getPreferredLanguage();
            var preferredLanguageTransfer = preferredLanguage == null ? null : partyControl.getLanguageTransfer(userVisit, preferredLanguage);
            var preferredCurrency = userVisitEntity.getPreferredCurrency();
            var preferredCurrencyTransfer = preferredCurrency == null ? null : accountingControl.getCurrencyTransfer(userVisit, preferredCurrency);
            var preferredTimeZone = userVisitEntity.getPreferredTimeZone();
            var preferredTimeZoneTransfer = preferredTimeZone == null ? null : partyControl.getTimeZoneTransfer(userVisit, preferredTimeZone);
            var preferredDateTimeFormat = userVisitEntity.getPreferredDateTimeFormat();
            var preferredDateTimeFormatTransfer = preferredDateTimeFormat == null ? null : partyControl.getDateTimeFormatTransfer(userVisit, preferredDateTimeFormat);
            var unformattedLastCommandTime = userVisitEntity.getLastCommandTime();
            var lastCommandTime = formatTypicalDateTime(userVisit, unformattedLastCommandTime);
            var offerUse = userVisitEntity.getOfferUse();
            var offerUseTransfer = offerUse == null ? null : offerUseControl.getOfferUseTransfer(userVisit, offerUse);
            var associateReferral = userVisitEntity.getAssociateReferral();
            var associateReferralTransfer = associateReferral == null ? null : associateControl.getAssociateReferralTransfer(userVisit, associateReferral);
            var unformattedRetainUntilTime = userVisitEntity.getRetainUntilTime();
            var retainUntilTime = formatTypicalDateTime(userVisit, unformattedRetainUntilTime);

            userVisitTransfer = new UserVisitTransfer(userKeyTransfer, preferredLanguageTransfer, preferredCurrencyTransfer, preferredTimeZoneTransfer,
                    preferredDateTimeFormatTransfer, unformattedLastCommandTime, lastCommandTime, offerUseTransfer, associateReferralTransfer,
                    unformattedRetainUntilTime, retainUntilTime);
            put(userVisit, userVisitEntity, userVisitTransfer);
            
            if(includeUserVisitCampaigns) {
                userVisitTransfer.setUserVisitCampaigns(new ListWrapper<>(campaignControl.getUserVisitCampaignTransfersByUserVisit(userVisit, userVisitEntity)));
            }
        }

        return userVisitTransfer;
    }
    
}
