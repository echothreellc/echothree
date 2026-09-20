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

package com.echothree.model.control.party.server.search;

import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.contact.ContactForeignKeys.PARTY_CONTACT_MECHANISM_DETAILS_PARTY_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TIMES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PEOPLE_PARTY_FK;
import static com.echothree.model.jooq.server.tables.contact.ContactEmailAddresses.ContactEmailAddresses;
import static com.echothree.model.jooq.server.tables.contact.ContactTelephones.ContactTelephones;
import static com.echothree.model.jooq.server.tables.contact.PartyContactMechanismDetails.PartyContactMechanismDetails;
import static com.echothree.model.jooq.server.tables.contact.PartyContactMechanisms.PartyContactMechanisms;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTimes.EntityTimes;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.party.People.People;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.server.analyzer.BasicAnalyzer;
import com.echothree.model.control.party.server.analyzer.PartyAnalyzer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.search.server.search.BaseSearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyAliasType;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class PartySearchEvaluator
        extends BaseSearchEvaluator {
    
    protected PartyType partyType;
    protected String entityNameIndexField;
    private String firstName;
    private Boolean firstNameSoundex;
    private String middleName;
    private Boolean middleNameSoundex;
    private String lastName;
    private Boolean lastNameSoundex;
    private GeoCode countryGeoCode;
    private String areaCode;
    private String telephoneNumber;
    private String telephoneExtension;
    private String emailAddress;
    private String partyName;
    private PartyAliasType partyAliasType;
    private String alias;
    
    protected PartySearchEvaluator(UserVisit userVisit, SearchType searchType, SearchDefaultOperator searchDefaultOperator, SearchSortOrder searchSortOrder,
            SearchSortDirection searchSortDirection, String partyTypeName, final String entityNameIndexField, String indexName) {
        super(userVisit, searchDefaultOperator, searchType, searchSortOrder, searchSortDirection, null, ComponentVendors.ECHO_THREE.name(),
                EntityTypes.Party.name(), null, null, indexName);
        
        this.partyType = PartyLogic.getInstance().getPartyTypeByName(null, partyTypeName);
        this.entityNameIndexField = entityNameIndexField;
        
        setField(IndexFields.name.name());
    }
    
    @Override
    public EntityInstancePKHolder getEntityInstancePKHolderByCreatedTime(Long createdTime) {
        // Custom version for PartySearchEvaluator that takes into account the PartyType.
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(EntityTimes).onKey(ENTITY_TIMES_ENTITY_INSTANCE_FK)
                .join(Parties).on(Parties.PARTY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                .where(PartyDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        EntityTimes.CREATED_TIME.ge(createdTime));

        return getEntityInstancePKHolderFromQuery(query);
    }

    @Override
    public EntityInstancePKHolder getEntityInstancePKHolderByModifiedTime(Long modifiedTime) {
        // Custom version for PartySearchEvaluator that takes into account the PartyType.
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(EntityTimes).onKey(ENTITY_TIMES_ENTITY_INSTANCE_FK)
                .join(Parties).on(Parties.PARTY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                .where(PartyDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        EntityTimes.MODIFIED_TIME.ge(modifiedTime));

        return getEntityInstancePKHolderFromQuery(query);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByFirstName(String firstName) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(Parties).on(Parties.PARTY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                .join(People).onKey(PEOPLE_PARTY_FK)
                .where(PartyDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        People.FIRST_NAME.eq(firstName),
                        People.THRU_TIME.eq(Session.MAX_TIME),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        return getEntityInstancePKHolderFromQuery(query);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByFirstNameSdx(String firstNameSdx) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(Parties).on(Parties.PARTY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                .join(People).onKey(PEOPLE_PARTY_FK)
                .where(PartyDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        People.FIRST_NAME_SDX.eq(firstNameSdx),
                        People.THRU_TIME.eq(Session.MAX_TIME),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        return getEntityInstancePKHolderFromQuery(query);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByMiddleName(String middleName) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(Parties).on(Parties.PARTY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                .join(People).onKey(PEOPLE_PARTY_FK)
                .where(PartyDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        People.MIDDLE_NAME.eq(middleName),
                        People.THRU_TIME.eq(Session.MAX_TIME),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        return getEntityInstancePKHolderFromQuery(query);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByMiddleNameSdx(String middleNameSdx) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(Parties).on(Parties.PARTY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                .join(People).onKey(PEOPLE_PARTY_FK)
                .where(PartyDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        People.MIDDLE_NAME_SDX.eq(middleNameSdx),
                        People.THRU_TIME.eq(Session.MAX_TIME),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        return getEntityInstancePKHolderFromQuery(query);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByLastName(String lastName) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(Parties).on(Parties.PARTY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                .join(People).onKey(PEOPLE_PARTY_FK)
                .where(PartyDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        People.LAST_NAME.eq(lastName),
                        People.THRU_TIME.eq(Session.MAX_TIME),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        return getEntityInstancePKHolderFromQuery(query);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByLastNameSdx(String lastNameSdx) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(Parties).on(Parties.PARTY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                .join(People).onKey(PEOPLE_PARTY_FK)
                .where(PartyDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        People.LAST_NAME_SDX.eq(lastNameSdx),
                        People.THRU_TIME.eq(Session.MAX_TIME),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        return getEntityInstancePKHolderFromQuery(query);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByAreaCode(GeoCode countryGeoCode, String areaCode) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(Parties).on(Parties.PARTY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISM_DETAILS_PARTY_FK)
                .join(PartyContactMechanisms).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .join(ContactTelephones).on(PartyContactMechanismDetails.CONTACT_MECHANISM.eq(ContactTelephones.CONTACT_MECHANISM))
                .where(PartyDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        ContactTelephones.THRU_TIME.eq(Session.MAX_TIME),
                        ContactTelephones.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                        ContactTelephones.AREA_CODE.eq(areaCode),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        return getEntityInstancePKHolderFromQuery(query);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByTelephoneNumber(GeoCode countryGeoCode, String telephoneNumber) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(Parties).on(Parties.PARTY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISM_DETAILS_PARTY_FK)
                .join(PartyContactMechanisms).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .join(ContactTelephones).on(PartyContactMechanismDetails.CONTACT_MECHANISM.eq(ContactTelephones.CONTACT_MECHANISM))
                .where(PartyDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        ContactTelephones.THRU_TIME.eq(Session.MAX_TIME),
                        ContactTelephones.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                        ContactTelephones.TELEPHONE_NUMBER.eq(telephoneNumber),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        return getEntityInstancePKHolderFromQuery(query);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByTelephoneExtension(GeoCode countryGeoCode, String telephoneExtension) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(Parties).on(Parties.PARTY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISM_DETAILS_PARTY_FK)
                .join(PartyContactMechanisms).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .join(ContactTelephones).on(PartyContactMechanismDetails.CONTACT_MECHANISM.eq(ContactTelephones.CONTACT_MECHANISM))
                .where(PartyDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        ContactTelephones.THRU_TIME.eq(Session.MAX_TIME),
                        ContactTelephones.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                        ContactTelephones.TELEPHONE_EXTENSION.eq(telephoneExtension),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        return getEntityInstancePKHolderFromQuery(query);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByEmailAddress(String emailAddress) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(Parties).on(Parties.PARTY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                .join(PartyContactMechanismDetails).onKey(PARTY_CONTACT_MECHANISM_DETAILS_PARTY_FK)
                .join(PartyContactMechanisms).onKey(PARTY_CONTACT_MECHANISMS_ACTIVE_DETAIL_FK)
                .join(ContactEmailAddresses).on(PartyContactMechanismDetails.CONTACT_MECHANISM.eq(ContactEmailAddresses.CONTACT_MECHANISM))
                .where(PartyDetails.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        ContactEmailAddresses.THRU_TIME.eq(Session.MAX_TIME),
                        ContactEmailAddresses.EMAIL_ADDRESS.eq(emailAddress),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        return getEntityInstancePKHolderFromQuery(query);
    }

    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public Boolean getFirstNameSoundex() {
        return firstNameSoundex;
    }
    
    public void setFirstNameSoundex(Boolean firstNameSoundex) {
        this.firstNameSoundex = firstNameSoundex;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public Boolean getMiddleNameSoundex() {
        return middleNameSoundex;
    }
    
    public void setMiddleNameSoundex(Boolean middleNameSoundex) {
        this.middleNameSoundex = middleNameSoundex;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Boolean getLastNameSoundex() {
        return lastNameSoundex;
    }
    
    public void setLastNameSoundex(Boolean lastNameSoundex) {
        this.lastNameSoundex = lastNameSoundex;
    }
    
    public String getPartyName() {
        return partyName;
    }

    public GeoCode getCountryGeoCode() {
        return countryGeoCode;
    }

    public void setCountryGeoCode(GeoCode countryGeoCode) {
        this.countryGeoCode = countryGeoCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getTelephoneExtension() {
        return telephoneExtension;
    }

    public void setTelephoneExtension(String telephoneExtension) {
        this.telephoneExtension = telephoneExtension;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public PartyAliasType getPartyAliasType() {
        return partyAliasType;
    }

    public void setPartyAliasType(PartyAliasType partyAliasType) {
        this.partyAliasType = partyAliasType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public BasicAnalyzer getAnalyzer(final ExecutionErrorAccumulator eea, final Language language) {
        return new PartyAnalyzer(eea, language, entityType, partyType, entityNameIndexField);
    }

    /** Subclasses should override and always call their super's executeSearch() */
    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        EntityInstancePKHolder resultSet = null;
        var parameterCount = (partyName == null ? 0 : 1) + (alias == null ? 0 : 1);

        if(parameterCount == 0) {
            resultSet = super.executeSearch(eea);
            
            if(resultSet == null || resultSet.size() > 0) {
                if(firstName != null) {
                    if(firstNameSoundex) {
                        String firstNameSdx;

                        try {
                            firstNameSdx = firstName == null? null: getSoundex().encode(firstName);
                        } catch (IllegalArgumentException iae) {
                            firstNameSdx = null;
                        }

                        if(firstNameSdx != null) {
                            var entityInstancePKHolder = getEntityInstancePKHolderByFirstNameSdx(firstNameSdx);

                            if(resultSet == null) {
                                resultSet = entityInstancePKHolder;
                            } else {
                                resultSet.retainAll(entityInstancePKHolder);
                            }
                        } else {
                            resultSet = null;
                        }
                    } else {
                        var entityInstancePKHolder = getEntityInstancePKHolderByFirstName(firstName);

                        if(resultSet == null) {
                            resultSet = entityInstancePKHolder;
                        } else {
                            resultSet.retainAll(entityInstancePKHolder);
                        }
                    }
                }
            }

            if(resultSet == null || resultSet.size() > 0) {
                if(middleName != null) {
                    if(middleNameSoundex) {
                        String middleNameSdx;

                        try {
                            middleNameSdx = middleName == null? null: getSoundex().encode(middleName);
                        } catch (IllegalArgumentException iae) {
                            middleNameSdx = null;
                        }

                        if(middleNameSdx != null) {
                            var entityInstancePKHolder = getEntityInstancePKHolderByMiddleNameSdx(middleNameSdx);

                            if(resultSet == null) {
                                resultSet = entityInstancePKHolder;
                            } else {
                                resultSet.retainAll(entityInstancePKHolder);
                            }
                        } else {
                            resultSet = null;
                        }
                    } else {
                        var entityInstancePKHolder = getEntityInstancePKHolderByMiddleName(middleName);

                        if(resultSet == null) {
                            resultSet = entityInstancePKHolder;
                        } else {
                            resultSet.retainAll(entityInstancePKHolder);
                        }
                    }
                }
            }

            if(resultSet == null || resultSet.size() > 0) {
                if(lastName != null) {
                    if(lastNameSoundex) {
                        String lastNameSdx;

                        try {
                            lastNameSdx = lastName == null? null: getSoundex().encode(lastName);
                        } catch (IllegalArgumentException iae) {
                            lastNameSdx = null;
                        }

                        if(lastNameSdx != null) {
                            var entityInstancePKHolder = getEntityInstancePKHolderByLastNameSdx(lastNameSdx);

                            if(resultSet == null) {
                                resultSet = entityInstancePKHolder;
                            } else {
                                resultSet.retainAll(entityInstancePKHolder);
                            }
                        } else {
                            resultSet = null;
                        }
                    } else {
                        var entityInstancePKHolder = getEntityInstancePKHolderByLastName(lastName);

                        if(resultSet == null) {
                            resultSet = entityInstancePKHolder;
                        } else {
                            resultSet.retainAll(entityInstancePKHolder);
                        }
                    }
                }
            }

            if(countryGeoCode != null && areaCode != null && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByAreaCode(countryGeoCode, areaCode);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
            
            if(countryGeoCode != null && telephoneNumber != null && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByTelephoneNumber(countryGeoCode, telephoneNumber);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
            
            if(countryGeoCode != null && telephoneExtension != null && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByTelephoneExtension(countryGeoCode, telephoneExtension);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
            
            if(emailAddress != null && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByEmailAddress(emailAddress);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
            
            if(resultSet == null || resultSet.size() > 0) {
                if(q != null) {
                    var entityInstancePKHolder = executeQuery(eea);
                    
                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }
        } else {
            if(parameterCount == 1) {
                var partyControl = Session.getModelController(PartyControl.class);
                Party party = null;

                if(alias != null) {
                    if(partyAliasType == null) {
                        partyAliasType = partyControl.getDefaultPartyAliasType(partyType);
                    }
                    
                    if(partyAliasType != null) {
                        party = partyControl.getPartyByAlias(partyAliasType, alias);
                    }
                }

                if(partyName != null) {
                    party = partyControl.getPartyByName(partyName);

                    // If it isn't the PartyType we're looking for, toss the Party away.
                    if(!party.getLastDetail().getPartyType().equals(partyType)) {
                        party = null;
                    }
                }

                if(party != null) {
                    var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

                    resultSet = new EntityInstancePKHolder(1);
                    resultSet.add(entityInstanceControl.getEntityInstanceByBasePK(party.getPrimaryKey()).getPrimaryKey(), null);
                }
            }
        }
        
        return resultSet;
    }

}
