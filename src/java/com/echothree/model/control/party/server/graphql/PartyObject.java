// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.party.server.graphql;

import com.echothree.control.user.accounting.server.command.GetCurrencyCommand;
import com.echothree.control.user.party.server.command.GetDateTimeFormatCommand;
import com.echothree.control.user.party.server.command.GetLanguageCommand;
import com.echothree.control.user.party.server.command.GetTimeZoneCommand;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("party object")
@GraphQLName("Party")
public class PartyObject
        extends BaseEntityInstanceObject {
    
    private final Party party; // Always Present
    
    public PartyObject(Party party) {
        super(party.getPrimaryKey());
        
        this.party = party;
    }

    private PartyDetail partyDetail; // Optional, use getPartyDetail()
    
    private PartyDetail getPartyDetail() {
        if(partyDetail == null) {
            partyDetail = party.getLastDetail();
        }
        
        return partyDetail;
    }
    
    private Boolean hasLanguageAccess;
    
    private boolean getHasLanguageAccess(final DataFetchingEnvironment env) {
        if(hasLanguageAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetLanguageCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasLanguageAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasLanguageAccess;
    }
        
    private Boolean hasCurrencyAccess;
    
    private boolean getHasCurrencyAccess(final DataFetchingEnvironment env) {
        if(hasCurrencyAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetCurrencyCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasCurrencyAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasCurrencyAccess;
    }
        
    private Boolean hasTimeZoneAccess;
    
    private boolean getHasTimeZoneAccess(final DataFetchingEnvironment env) {
        if(hasTimeZoneAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetTimeZoneCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasTimeZoneAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasTimeZoneAccess;
    }
        
    private Boolean hasDateTimeFormatAccess;
    
    private boolean getHasDateTimeFormatAccess(final DataFetchingEnvironment env) {
        if(hasDateTimeFormatAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetDateTimeFormatCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasDateTimeFormatAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasDateTimeFormatAccess;
    }
    
    @GraphQLField
    @GraphQLDescription("party name")
    @GraphQLNonNull
    public String getPartyName() {
        return getPartyDetail().getPartyName();
    }

    @GraphQLField
    @GraphQLDescription("party type")
    @GraphQLNonNull
    public PartyTypeObject getPartyType() {
        return new PartyTypeObject(getPartyDetail().getPartyType());
    }

    @GraphQLField
    @GraphQLDescription("preferred language")
    public LanguageObject getPreferredLanguage(final DataFetchingEnvironment env) {
        Language preferredLanguage = getPartyDetail().getPreferredLanguage();
        
        return preferredLanguage != null && getHasLanguageAccess(env) ? new LanguageObject(preferredLanguage) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred language")
    public CurrencyObject getPreferredCurrency(final DataFetchingEnvironment env) {
        Currency preferredCurrency = getPartyDetail().getPreferredCurrency();
        
        return preferredCurrency != null && getHasCurrencyAccess(env) ? new CurrencyObject(preferredCurrency) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred time zone")
    public TimeZoneObject getPreferredTimeZone(final DataFetchingEnvironment env) {
        TimeZone preferredTimeZone = getPartyDetail().getPreferredTimeZone();
        
        return preferredTimeZone != null && getHasTimeZoneAccess(env) ? new TimeZoneObject(preferredTimeZone) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred date time format")
    public DateTimeFormatObject getPreferredDateTimeFormat(final DataFetchingEnvironment env) {
        DateTimeFormat preferredDateTimeFormat = getPartyDetail().getPreferredDateTimeFormat();
        
        return preferredDateTimeFormat != null && getHasDateTimeFormatAccess(env) ? new DateTimeFormatObject(preferredDateTimeFormat) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("person")
    public PersonObject getPerson(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        Person person = partyControl.getPerson(party);
        
        return person == null ? null : new PersonObject(person);
    }
    
    @GraphQLField
    @GraphQLDescription("party group")
    public PartyGroupObject getPartyGroup(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        PartyGroup partyGroup = partyControl.getPartyGroup(party);
        
        return partyGroup == null ? null : new PartyGroupObject(partyGroup);
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        var userControl = Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return partyControl.getBestPartyDescription(party, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
}
