// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.user.server.graphql;

import com.echothree.control.user.accounting.server.command.GetCurrencyCommand;
import com.echothree.control.user.party.server.command.GetDateTimeFormatCommand;
import com.echothree.control.user.party.server.command.GetLanguageCommand;
import com.echothree.control.user.party.server.command.GetTimeZoneCommand;
import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.offer.server.graphql.OfferSecurityUtils;
import com.echothree.model.control.offer.server.graphql.OfferUseObject;
import com.echothree.model.control.party.server.graphql.DateTimeFormatObject;
import com.echothree.model.control.party.server.graphql.LanguageObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.control.party.server.graphql.TimeZoneObject;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.string.DateUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("user visit object")
@GraphQLName("UserVisit")
public class UserVisitObject
        extends BaseGraphQl {

    private final UserVisit userVisit; // Always Present

    public UserVisitObject(UserVisit userVisit) {
        this.userVisit = userVisit;
    }

//    @GraphQLField
//    @GraphQLDescription("user visit group")
//    public UserVisitGroupObject getUserVisitGroup() {
//        UserVisitGroup userVisitGroup = userVisit.getUserVisitGroup();
//
//        return userVisitGroup == null ? null : new UserVisitGroupObject(userVisitGroup);
//    }
//
//    @GraphQLField
//    @GraphQLDescription("user key")
//    public UserKeyObject getUserKey() {
//        UserKey userKey = userVisit.getUserKey();
//
//        return userKey == null ? null : new UserKeyObject(userKey);
//    }

    @GraphQLField
    @GraphQLDescription("preferred language")
    public LanguageObject getPreferredLanguage(final DataFetchingEnvironment env) {
        Language preferredLanguage = userVisit.getPreferredLanguage();

        return preferredLanguage != null && PartySecurityUtils.getInstance().getHasLanguageAccess(env) ? new LanguageObject(preferredLanguage) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred language")
    public CurrencyObject getPreferredCurrency(final DataFetchingEnvironment env) {
        Currency preferredCurrency = userVisit.getPreferredCurrency();

        return preferredCurrency != null && AccountingSecurityUtils.getInstance().getHasCurrencyAccess(env) ? new CurrencyObject(preferredCurrency) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred time zone")
    public TimeZoneObject getPreferredTimeZone(final DataFetchingEnvironment env) {
        TimeZone preferredTimeZone = userVisit.getPreferredTimeZone();

        return preferredTimeZone != null && PartySecurityUtils.getInstance().getHasTimeZoneAccess(env) ? new TimeZoneObject(preferredTimeZone) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred date time format")
    public DateTimeFormatObject getPreferredDateTimeFormat(final DataFetchingEnvironment env) {
        DateTimeFormat preferredDateTimeFormat = userVisit.getPreferredDateTimeFormat();

        return preferredDateTimeFormat != null && PartySecurityUtils.getInstance().getHasDateTimeFormatAccess(env) ? new DateTimeFormatObject(preferredDateTimeFormat) : null;
    }

    @GraphQLField
    @GraphQLDescription("unformatted last command time")
    @GraphQLNonNull
    public Long getUnformattedLastCommandTime() {
        return userVisit.getLastCommandTime();
    }
    
    @GraphQLField
    @GraphQLDescription("last command time")
    @GraphQLNonNull
    public String getLastCommandTime(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().formatTypicalDateTime(getUserVisit(env), userVisit.getLastCommandTime());
    }
    
    @GraphQLField
    @GraphQLDescription("offer use")
    public OfferUseObject getOfferUse(final DataFetchingEnvironment env) {
        return OfferSecurityUtils.getInstance().getHasOfferUseAccess(env) ?
                new OfferUseObject(userVisit.getOfferUse()) : null;
    }

//    @GraphQLField
//    @GraphQLDescription("associate referral")
//    public AssociateReferralObject getAssociateReferral() {
//        AssociateReferral associateReferral = userVisit.getAssociateReferral();
//
//        return associateReferral != null && AssociateSecurityUtils.getInstance().getHasAssociateReferralAccess(env) ? new AssociateReferralObject(associateReferral) : null;
//    }

    @GraphQLField
    @GraphQLDescription("unformatted retain until time")
    public Long getUnformattedRetainUntilTime() {
        return userVisit.getRetainUntilTime();
    }
    
    @GraphQLField
    @GraphQLDescription("retain until time")
    public String getRetainUntilTime(final DataFetchingEnvironment env) {
        Long retainUntilTime = userVisit.getRetainUntilTime();

        return retainUntilTime == null ? null : DateUtils.getInstance().formatTypicalDateTime(getUserVisit(env), retainUntilTime);
    }
}
