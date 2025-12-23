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

package com.echothree.model.control.user.server.graphql;

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.graphql.server.graphql.TimeObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.offer.server.graphql.OfferSecurityUtils;
import com.echothree.model.control.offer.server.graphql.OfferUseObject;
import com.echothree.model.control.party.server.graphql.DateTimeFormatObject;
import com.echothree.model.control.party.server.graphql.LanguageObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.control.party.server.graphql.TimeZoneObject;
import com.echothree.model.data.user.server.entity.UserVisit;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("user visit object")
@GraphQLName("UserVisit")
public class UserVisitObject
        implements BaseGraphQl {

    private final UserVisit userVisit; // Always Present

    public UserVisitObject(UserVisit userVisit) {
        this.userVisit = userVisit;
    }

    @GraphQLField
    @GraphQLDescription("user visit group")
    public UserVisitGroupObject getUserVisitGroup(final DataFetchingEnvironment env) {
        var userVisitGroup = userVisit.getUserVisitGroup();

        return userVisitGroup != null && UserSecurityUtils.getHasUserVisitGroupAccess(env) ?
                new UserVisitGroupObject(userVisit.getUserVisitGroup()) : null;
    }

    @GraphQLField
    @GraphQLDescription("user key")
    public UserKeyObject getUserKey() {
        var userKey = userVisit.getUserKey();

        return userKey == null ? null : new UserKeyObject(userKey);
    }

    @GraphQLField
    @GraphQLDescription("preferred language")
    public LanguageObject getPreferredLanguage(final DataFetchingEnvironment env) {
        var preferredLanguage = userVisit.getPreferredLanguage();

        return preferredLanguage != null && PartySecurityUtils.getHasLanguageAccess(env) ? new LanguageObject(preferredLanguage) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred language")
    public CurrencyObject getPreferredCurrency(final DataFetchingEnvironment env) {
        var preferredCurrency = userVisit.getPreferredCurrency();

        return preferredCurrency != null && AccountingSecurityUtils.getHasCurrencyAccess(env) ? new CurrencyObject(preferredCurrency) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred time zone")
    public TimeZoneObject getPreferredTimeZone(final DataFetchingEnvironment env) {
        var preferredTimeZone = userVisit.getPreferredTimeZone();

        return preferredTimeZone != null && PartySecurityUtils.getHasTimeZoneAccess(env) ? new TimeZoneObject(preferredTimeZone) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred date time format")
    public DateTimeFormatObject getPreferredDateTimeFormat(final DataFetchingEnvironment env) {
        var preferredDateTimeFormat = userVisit.getPreferredDateTimeFormat();

        return preferredDateTimeFormat != null && PartySecurityUtils.getHasDateTimeFormatAccess(env) ? new DateTimeFormatObject(preferredDateTimeFormat) : null;
    }

    @GraphQLField
    @GraphQLDescription("last command time")
    @GraphQLNonNull
    public TimeObject getLastCommandTime(final DataFetchingEnvironment env) {
        return new TimeObject(userVisit.getLastCommandTime());
    }
    
    @GraphQLField
    @GraphQLDescription("offer use")
    public OfferUseObject getOfferUse(final DataFetchingEnvironment env) {
        var offeruse = userVisit.getOfferUse();

        return offeruse != null && OfferSecurityUtils.getHasOfferUseAccess(env) ? new OfferUseObject(userVisit.getOfferUse()) : null;
    }

//    @GraphQLField
//    @GraphQLDescription("associate referral")
//    public AssociateReferralObject getAssociateReferral() {
//        var associateReferral = userVisit.getAssociateReferral();
//
//        return associateReferral != null && AssociateSecurityUtils.getHasAssociateReferralAccess(env) ? new AssociateReferralObject(associateReferral) : null;
//    }

    @GraphQLField
    @GraphQLDescription("retain until time")
    public TimeObject getRetainUntilTime(final DataFetchingEnvironment env) {
        var retainUntilTime = userVisit.getRetainUntilTime();

        return retainUntilTime == null ? null : new TimeObject(retainUntilTime);
    }
}
