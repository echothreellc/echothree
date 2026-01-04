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

package com.echothree.model.control.party.server.graphql;

import com.echothree.control.user.contact.common.form.ContactFormFactory;
import com.echothree.control.user.contact.server.command.GetContactMechanismPurposeCommand;
import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contact.server.graphql.PartyContactMechanismObject;
import com.echothree.model.control.contact.server.graphql.PartyContactMechanismPurposeObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import static com.echothree.model.control.graphql.server.util.BaseGraphQl.getUserVisitPK;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.contact.common.PartyContactMechanismConstants;
import com.echothree.model.data.party.common.PartyAliasConstants;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.enterprise.inject.spi.CDI;

public abstract class BasePartyObject
        extends BaseEntityInstanceObject {

    protected final Party party; // Always Present

    protected BasePartyObject(Party party) {
        super(party.getPrimaryKey());
        
        this.party = party;
    }

    private PartyDetail partyDetail; // Optional, use getPartyDetail()

    protected PartyDetail getPartyDetail() {
        if(partyDetail == null) {
            partyDetail = party.getLastDetail();
        }
        
        return partyDetail;
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
    public PartyTypeObject getPartyType(final DataFetchingEnvironment env) {
        return PartySecurityUtils.getHasPartyTypeAccess(env) ? new PartyTypeObject(getPartyDetail().getPartyType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred language")
    public LanguageObject getPreferredLanguage(final DataFetchingEnvironment env) {
        var preferredLanguage = getPartyDetail().getPreferredLanguage();
        
        return preferredLanguage != null && PartySecurityUtils.getHasLanguageAccess(env) ? new LanguageObject(preferredLanguage) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred currency")
    public CurrencyObject getPreferredCurrency(final DataFetchingEnvironment env) {
        var preferredCurrency = getPartyDetail().getPreferredCurrency();
        
        return preferredCurrency != null && AccountingSecurityUtils.getHasCurrencyAccess(env) ? new CurrencyObject(preferredCurrency) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred time zone")
    public TimeZoneObject getPreferredTimeZone(final DataFetchingEnvironment env) {
        var preferredTimeZone = getPartyDetail().getPreferredTimeZone();
        
        return preferredTimeZone != null && PartySecurityUtils.getHasTimeZoneAccess(env) ? new TimeZoneObject(preferredTimeZone) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred date time format")
    public DateTimeFormatObject getPreferredDateTimeFormat(final DataFetchingEnvironment env) {
        var preferredDateTimeFormat = getPartyDetail().getPreferredDateTimeFormat();
        
        return preferredDateTimeFormat != null && PartySecurityUtils.getHasDateTimeFormatAccess(env) ? new DateTimeFormatObject(preferredDateTimeFormat) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("person")
    public PersonObject getPerson(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        var person = partyControl.getPerson(party);
        
        return person == null ? null : new PersonObject(person);
    }
    
    @GraphQLField
    @GraphQLDescription("party group")
    public PartyGroupObject getPartyGroup(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyGroup = partyControl.getPartyGroup(party);
        
        return partyGroup == null ? null : new PartyGroupObject(partyGroup);
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return partyControl.getBestPartyDescription(party, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("party aliases")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<PartyAliasObject> getPartyAliases(final DataFetchingEnvironment env) {
        if(PartySecurityUtils.getHasPartyAliasesAccess(env, party)) {
            var partyControl = Session.getModelController(PartyControl.class);
            var totalCount = partyControl.countPartyAliasesByParty(party);

            try(var objectLimiter = new ObjectLimiter(env, PartyAliasConstants.COMPONENT_VENDOR_NAME, PartyAliasConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = partyControl.getPartyAliasesByParty(party);
                var partyAliases = entities.stream().map(PartyAliasObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, partyAliases);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("party contact mechanisms")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<PartyContactMechanismObject> getPartyContactMechanisms(final DataFetchingEnvironment env) {
//        if(ContactSecurityUtils.getHasPartyContactMechanismsAccess(env, party)) {
            var contactControl = Session.getModelController(ContactControl.class);
            var totalCount = contactControl.countPartyContactMechanismsByParty(party);

            try(var objectLimiter = new ObjectLimiter(env, PartyContactMechanismConstants.COMPONENT_VENDOR_NAME, PartyContactMechanismConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = contactControl.getPartyContactMechanismsByParty(party);
                var partyContactMechanismes = entities.stream().map(PartyContactMechanismObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, partyContactMechanismes);
            }
//        } else {
//            return Connections.emptyConnection();
//        }
    }

    @GraphQLField
    @GraphQLDescription("party contact mechanism purpose")
    public PartyContactMechanismPurposeObject getPartyContactMechanismPurpose(final DataFetchingEnvironment env,
            @GraphQLName("contactMechanismPurposeName") @GraphQLNonNull final String contactMechanismPurposeName) {
        var commandForm = ContactFormFactory.getGetContactMechanismPurposeForm();

        commandForm.setContactMechanismPurposeName(contactMechanismPurposeName);

        var contactMechanismPurpose = CDI.current().select(GetContactMechanismPurposeCommand.class).get().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        if(contactMechanismPurpose != null) {
            var contactControl = Session.getModelController(ContactControl.class);
            var partyContactMechanismPurpose = contactControl.getDefaultPartyContactMechanismPurpose(party, contactMechanismPurpose);

            return partyContactMechanismPurpose == null ? null : new PartyContactMechanismPurposeObject(partyContactMechanismPurpose);
        } else {
            return null;
        }
    }

}
