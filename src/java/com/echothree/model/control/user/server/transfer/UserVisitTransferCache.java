// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.accounting.remote.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.associate.remote.transfer.AssociateReferralTransfer;
import com.echothree.model.control.associate.server.AssociateControl;
import com.echothree.model.control.campaign.server.CampaignControl;
import com.echothree.model.control.offer.remote.transfer.OfferUseTransfer;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.remote.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.control.party.remote.transfer.TimeZoneTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.user.common.UserOptions;
import com.echothree.model.control.user.remote.transfer.UserKeyTransfer;
import com.echothree.model.control.user.remote.transfer.UserVisitTransfer;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.server.entity.UserKey;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.remote.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class UserVisitTransferCache
        extends BaseUserTransferCache<UserVisit, UserVisitTransfer> {
    
    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    AssociateControl associateControl = (AssociateControl)Session.getModelController(AssociateControl.class);
    CampaignControl campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
    OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    
    boolean includeUserVisitCampaigns;
    
    /** Creates a new instance of UserVisitTransferCache */
    public UserVisitTransferCache(UserVisit userVisit, UserControl userControl) {
        super(userVisit, userControl);

        Set<String> options = session.getOptions();
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
            OfferUseTransfer offerUseTransfer = offerUse == null ? null : offerControl.getOfferUseTransfer(userVisit, offerUse);
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
