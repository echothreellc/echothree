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

package com.echothree.model.control.user.server.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.associate.common.transfer.AssociateReferralTransfer;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.common.UserOptions;
import com.echothree.model.control.user.common.transfer.UserKeyTransfer;
import com.echothree.model.control.user.common.transfer.UserVisitTransfer;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.server.entity.UserKey;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class UserVisitTransferCache
        extends BaseUserTransferCache<UserVisit, UserVisitTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    AssociateControl associateControl = Session.getModelController(AssociateControl.class);
    CampaignControl campaignControl = Session.getModelController(CampaignControl.class);
    OfferUseControl offerUseControl = Session.getModelController(OfferUseControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    boolean includeUserVisitCampaigns;
    
    /** Creates a new instance of UserVisitTransferCache */
    public UserVisitTransferCache(UserVisit userVisit, UserControl userControl) {
        super(userVisit, userControl);

        var options = session.getOptions();
        if(options != null) {
            includeUserVisitCampaigns = options.contains(UserOptions.UserVisitIncludeUserVisitCampaigns);
        }
    }
    
    public UserVisitTransfer getUserVisitTransfer(UserVisit userVisitEntity) {
        UserVisitTransfer userVisitTransfer = get(userVisit);
        
        if(userVisitTransfer == null) {
            UserKey userKey = userVisitEntity.getUserKey();
            UserKeyTransfer userKeyTransfer = userKey == null ? null : userControl.getUserKeyTransfer(userVisit, userKey);
            Language preferredLanguage = userVisitEntity.getPreferredLanguage();
            LanguageTransfer preferredLanguageTransfer = preferredLanguage == null ? null : partyControl.getLanguageTransfer(userVisit, preferredLanguage);
            Currency preferredCurrency = userVisitEntity.getPreferredCurrency();
            CurrencyTransfer preferredCurrencyTransfer = preferredCurrency == null ? null : accountingControl.getCurrencyTransfer(userVisit, preferredCurrency);
            TimeZone preferredTimeZone = userVisitEntity.getPreferredTimeZone();
            TimeZoneTransfer preferredTimeZoneTransfer = preferredTimeZone == null ? null : partyControl.getTimeZoneTransfer(userVisit, preferredTimeZone);
            DateTimeFormat preferredDateTimeFormat = userVisitEntity.getPreferredDateTimeFormat();
            DateTimeFormatTransfer preferredDateTimeFormatTransfer = preferredDateTimeFormat == null ? null : partyControl.getDateTimeFormatTransfer(userVisit, preferredDateTimeFormat);
            Long unformattedLastCommandTime = userVisitEntity.getLastCommandTime();
            String lastCommandTime = formatTypicalDateTime(unformattedLastCommandTime);
            OfferUse offerUse = userVisitEntity.getOfferUse();
            OfferUseTransfer offerUseTransfer = offerUse == null ? null : offerUseControl.getOfferUseTransfer(userVisit, offerUse);
            AssociateReferral associateReferral = userVisitEntity.getAssociateReferral();
            AssociateReferralTransfer associateReferralTransfer = associateReferral == null ? null : associateControl.getAssociateReferralTransfer(userVisit, associateReferral);
            Long unformattedRetainUntilTime = userVisitEntity.getRetainUntilTime();
            String retainUntilTime = formatTypicalDateTime(unformattedRetainUntilTime);

            userVisitTransfer = new UserVisitTransfer(userKeyTransfer, preferredLanguageTransfer, preferredCurrencyTransfer, preferredTimeZoneTransfer,
                    preferredDateTimeFormatTransfer, unformattedLastCommandTime, lastCommandTime, offerUseTransfer, associateReferralTransfer,
                    unformattedRetainUntilTime, retainUntilTime);
            put(userVisitEntity, userVisitTransfer);
            
            if(includeUserVisitCampaigns) {
                userVisitTransfer.setUserVisitCampaigns(new ListWrapper<>(campaignControl.getUserVisitCampaignTransfersByUserVisit(userVisit, userVisitEntity)));
            }
        }

        return userVisitTransfer;
    }
    
}
