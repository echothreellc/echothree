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

package com.echothree.model.control.user.remote.transfer;

import com.echothree.model.control.accounting.remote.transfer.CurrencyTransfer;
import com.echothree.model.control.associate.remote.transfer.AssociateReferralTransfer;
import com.echothree.model.control.campaign.remote.transfer.UserVisitCampaignTransfer;
import com.echothree.model.control.offer.remote.transfer.OfferUseTransfer;
import com.echothree.model.control.party.remote.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.control.party.remote.transfer.TimeZoneTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;
import com.echothree.util.remote.transfer.ListWrapper;

public class UserVisitTransfer
        extends BaseTransfer {
    
    private UserKeyTransfer userKey;
    private LanguageTransfer preferredLanguage;
    private CurrencyTransfer preferredCurrency;
    private TimeZoneTransfer preferredTimeZone;
    private DateTimeFormatTransfer preferredDateTimeFormat;
    private Long unformattedLastCommandTime;
    private String lastCommandTime;
    private OfferUseTransfer offerUse;
    private AssociateReferralTransfer associateReferral;
    private Long unformattedRetainUntilTime;
    private String retainUntilTime;
    
    private ListWrapper<UserVisitCampaignTransfer> userVisitCampaigns;
    
    /** Creates a new instance of UserVisitTransfer */
    public UserVisitTransfer(UserKeyTransfer userKey, LanguageTransfer preferredLanguage, CurrencyTransfer preferredCurrency,
            TimeZoneTransfer preferredTimeZone, DateTimeFormatTransfer preferredDateTimeFormat, Long unformattedLastCommandTime, String lastCommandTime,
            OfferUseTransfer offerUse, AssociateReferralTransfer associateReferral, Long unformattedRetainUntilTime, String retainUntilTime) {
        this.userKey = userKey;
        this.preferredLanguage = preferredLanguage;
        this.preferredCurrency = preferredCurrency;
        this.preferredTimeZone = preferredTimeZone;
        this.preferredDateTimeFormat = preferredDateTimeFormat;
        this.unformattedLastCommandTime = unformattedLastCommandTime;
        this.lastCommandTime = lastCommandTime;
        this.offerUse = offerUse;
        this.associateReferral = associateReferral;
        this.unformattedRetainUntilTime = unformattedRetainUntilTime;
        this.retainUntilTime = retainUntilTime;
    }

    /**
     * @return the userKey
     */
    public UserKeyTransfer getUserKey() {
        return userKey;
    }

    /**
     * @param userKey the userKey to set
     */
    public void setUserKey(UserKeyTransfer userKey) {
        this.userKey = userKey;
    }

    /**
     * @return the preferredLanguage
     */
    public LanguageTransfer getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     * @param preferredLanguage the preferredLanguage to set
     */
    public void setPreferredLanguage(LanguageTransfer preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    /**
     * @return the preferredCurrency
     */
    public CurrencyTransfer getPreferredCurrency() {
        return preferredCurrency;
    }

    /**
     * @param preferredCurrency the preferredCurrency to set
     */
    public void setPreferredCurrency(CurrencyTransfer preferredCurrency) {
        this.preferredCurrency = preferredCurrency;
    }

    /**
     * @return the preferredTimeZone
     */
    public TimeZoneTransfer getPreferredTimeZone() {
        return preferredTimeZone;
    }

    /**
     * @param preferredTimeZone the preferredTimeZone to set
     */
    public void setPreferredTimeZone(TimeZoneTransfer preferredTimeZone) {
        this.preferredTimeZone = preferredTimeZone;
    }

    /**
     * @return the preferredDateTimeFormat
     */
    public DateTimeFormatTransfer getPreferredDateTimeFormat() {
        return preferredDateTimeFormat;
    }

    /**
     * @param preferredDateTimeFormat the preferredDateTimeFormat to set
     */
    public void setPreferredDateTimeFormat(DateTimeFormatTransfer preferredDateTimeFormat) {
        this.preferredDateTimeFormat = preferredDateTimeFormat;
    }

    /**
     * @return the unformattedLastCommandTime
     */
    public Long getUnformattedLastCommandTime() {
        return unformattedLastCommandTime;
    }

    /**
     * @param unformattedLastCommandTime the unformattedLastCommandTime to set
     */
    public void setUnformattedLastCommandTime(Long unformattedLastCommandTime) {
        this.unformattedLastCommandTime = unformattedLastCommandTime;
    }

    /**
     * @return the lastCommandTime
     */
    public String getLastCommandTime() {
        return lastCommandTime;
    }

    /**
     * @param lastCommandTime the lastCommandTime to set
     */
    public void setLastCommandTime(String lastCommandTime) {
        this.lastCommandTime = lastCommandTime;
    }

    /**
     * @return the offerUse
     */
    public OfferUseTransfer getOfferUse() {
        return offerUse;
    }

    /**
     * @param offerUse the offerUse to set
     */
    public void setOfferUse(OfferUseTransfer offerUse) {
        this.offerUse = offerUse;
    }

    /**
     * @return the associateReferral
     */
    public AssociateReferralTransfer getAssociateReferral() {
        return associateReferral;
    }

    /**
     * @param associateReferral the associateReferral to set
     */
    public void setAssociateReferral(AssociateReferralTransfer associateReferral) {
        this.associateReferral = associateReferral;
    }

    /**
     * @return the unformattedRetainUntilTime
     */
    public Long getUnformattedRetainUntilTime() {
        return unformattedRetainUntilTime;
    }

    /**
     * @param unformattedRetainUntilTime the unformattedRetainUntilTime to set
     */
    public void setUnformattedRetainUntilTime(Long unformattedRetainUntilTime) {
        this.unformattedRetainUntilTime = unformattedRetainUntilTime;
    }

    /**
     * @return the retainUntilTime
     */
    public String getRetainUntilTime() {
        return retainUntilTime;
    }

    /**
     * @param retainUntilTime the retainUntilTime to set
     */
    public void setRetainUntilTime(String retainUntilTime) {
        this.retainUntilTime = retainUntilTime;
    }

    /**
     * @return the userVisitCampaigns
     */
    public ListWrapper<UserVisitCampaignTransfer> getUserVisitCampaigns() {
        return userVisitCampaigns;
    }

    /**
     * @param userVisitCampaigns the userVisitCampaigns to set
     */
    public void setUserVisitCampaigns(ListWrapper<UserVisitCampaignTransfer> userVisitCampaigns) {
        this.userVisitCampaigns = userVisitCampaigns;
    }
    
}
