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

package com.echothree.model.control.user.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.associate.common.transfer.AssociateReferralTransfer;
import com.echothree.model.control.campaign.common.transfer.UserVisitCampaignTransfer;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

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
     * Returns the userKey.
     * @return the userKey
     */
    public UserKeyTransfer getUserKey() {
        return userKey;
    }

    /**
     * Sets the userKey.
     * @param userKey the userKey to set
     */
    public void setUserKey(UserKeyTransfer userKey) {
        this.userKey = userKey;
    }

    /**
     * Returns the preferredLanguage.
     * @return the preferredLanguage
     */
    public LanguageTransfer getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     * Sets the preferredLanguage.
     * @param preferredLanguage the preferredLanguage to set
     */
    public void setPreferredLanguage(LanguageTransfer preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    /**
     * Returns the preferredCurrency.
     * @return the preferredCurrency
     */
    public CurrencyTransfer getPreferredCurrency() {
        return preferredCurrency;
    }

    /**
     * Sets the preferredCurrency.
     * @param preferredCurrency the preferredCurrency to set
     */
    public void setPreferredCurrency(CurrencyTransfer preferredCurrency) {
        this.preferredCurrency = preferredCurrency;
    }

    /**
     * Returns the preferredTimeZone.
     * @return the preferredTimeZone
     */
    public TimeZoneTransfer getPreferredTimeZone() {
        return preferredTimeZone;
    }

    /**
     * Sets the preferredTimeZone.
     * @param preferredTimeZone the preferredTimeZone to set
     */
    public void setPreferredTimeZone(TimeZoneTransfer preferredTimeZone) {
        this.preferredTimeZone = preferredTimeZone;
    }

    /**
     * Returns the preferredDateTimeFormat.
     * @return the preferredDateTimeFormat
     */
    public DateTimeFormatTransfer getPreferredDateTimeFormat() {
        return preferredDateTimeFormat;
    }

    /**
     * Sets the preferredDateTimeFormat.
     * @param preferredDateTimeFormat the preferredDateTimeFormat to set
     */
    public void setPreferredDateTimeFormat(DateTimeFormatTransfer preferredDateTimeFormat) {
        this.preferredDateTimeFormat = preferredDateTimeFormat;
    }

    /**
     * Returns the unformattedLastCommandTime.
     * @return the unformattedLastCommandTime
     */
    public Long getUnformattedLastCommandTime() {
        return unformattedLastCommandTime;
    }

    /**
     * Sets the unformattedLastCommandTime.
     * @param unformattedLastCommandTime the unformattedLastCommandTime to set
     */
    public void setUnformattedLastCommandTime(Long unformattedLastCommandTime) {
        this.unformattedLastCommandTime = unformattedLastCommandTime;
    }

    /**
     * Returns the lastCommandTime.
     * @return the lastCommandTime
     */
    public String getLastCommandTime() {
        return lastCommandTime;
    }

    /**
     * Sets the lastCommandTime.
     * @param lastCommandTime the lastCommandTime to set
     */
    public void setLastCommandTime(String lastCommandTime) {
        this.lastCommandTime = lastCommandTime;
    }

    /**
     * Returns the offerUse.
     * @return the offerUse
     */
    public OfferUseTransfer getOfferUse() {
        return offerUse;
    }

    /**
     * Sets the offerUse.
     * @param offerUse the offerUse to set
     */
    public void setOfferUse(OfferUseTransfer offerUse) {
        this.offerUse = offerUse;
    }

    /**
     * Returns the associateReferral.
     * @return the associateReferral
     */
    public AssociateReferralTransfer getAssociateReferral() {
        return associateReferral;
    }

    /**
     * Sets the associateReferral.
     * @param associateReferral the associateReferral to set
     */
    public void setAssociateReferral(AssociateReferralTransfer associateReferral) {
        this.associateReferral = associateReferral;
    }

    /**
     * Returns the unformattedRetainUntilTime.
     * @return the unformattedRetainUntilTime
     */
    public Long getUnformattedRetainUntilTime() {
        return unformattedRetainUntilTime;
    }

    /**
     * Sets the unformattedRetainUntilTime.
     * @param unformattedRetainUntilTime the unformattedRetainUntilTime to set
     */
    public void setUnformattedRetainUntilTime(Long unformattedRetainUntilTime) {
        this.unformattedRetainUntilTime = unformattedRetainUntilTime;
    }

    /**
     * Returns the retainUntilTime.
     * @return the retainUntilTime
     */
    public String getRetainUntilTime() {
        return retainUntilTime;
    }

    /**
     * Sets the retainUntilTime.
     * @param retainUntilTime the retainUntilTime to set
     */
    public void setRetainUntilTime(String retainUntilTime) {
        this.retainUntilTime = retainUntilTime;
    }

    /**
     * Returns the userVisitCampaigns.
     * @return the userVisitCampaigns
     */
    public ListWrapper<UserVisitCampaignTransfer> getUserVisitCampaigns() {
        return userVisitCampaigns;
    }

    /**
     * Sets the userVisitCampaigns.
     * @param userVisitCampaigns the userVisitCampaigns to set
     */
    public void setUserVisitCampaigns(ListWrapper<UserVisitCampaignTransfer> userVisitCampaigns) {
        this.userVisitCampaigns = userVisitCampaigns;
    }
    
}
