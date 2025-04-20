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

package com.echothree.model.control.party.server.search;

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
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
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
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, entitytimes, parties, partydetails "
                + "WHERE par_activedetailid = pardt_partydetailid AND pardt_ptyp_partytypeid = ? "
                + "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid "
                + "AND eni_entityinstanceid = etim_eni_entityinstanceid AND etim_createdtime >= ?"),
                partyType, entityType, createdTime);
    }

    @Override
    public EntityInstancePKHolder getEntityInstancePKHolderByModifiedTime(Long modifiedTime) {
        // Custom version for PartySearchEvaluator that takes into account the PartyType.
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, entitytimes, parties, partydetails "
                + "WHERE par_activedetailid = pardt_partydetailid AND pardt_ptyp_partytypeid = ? "
                + "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid "
                + "AND eni_entityinstanceid = etim_eni_entityinstanceid AND etim_modifiedtime >= ?"),
                partyType, entityType, modifiedTime);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByFirstName(String firstName) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, parties, partydetails, people "
                + "WHERE par_activedetailid = pardt_partydetailid "
                + "AND pardt_ptyp_partytypeid = ? "
                + "AND par_partyid = peop_par_partyid AND peop_firstname = ? AND peop_thrutime = ? "
                + "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid"),
                partyType, firstName, Session.MAX_TIME, entityType);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByFirstNameSdx(String firstNameSdx) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, parties, partydetails, people "
                + "WHERE par_activedetailid = pardt_partydetailid "
                + "AND pardt_ptyp_partytypeid = ? "
                + "AND par_partyid = peop_par_partyid AND peop_firstnamesdx = ? AND peop_thrutime = ? "
                + "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid"),
                partyType, firstNameSdx, Session.MAX_TIME, entityType);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByMiddleName(String middleName) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, parties, partydetails, people "
                + "WHERE par_activedetailid = pardt_partydetailid "
                + "AND pardt_ptyp_partytypeid = ? "
                + "AND par_partyid = peop_par_partyid AND peop_middlename = ? AND peop_thrutime = ? "
                + "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid"),
                partyType, middleName, Session.MAX_TIME, entityType);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByMiddleNameSdx(String middleNameSdx) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, parties, partydetails, people "
                + "WHERE par_activedetailid = pardt_partydetailid "
                + "AND pardt_ptyp_partytypeid = ? "
                + "AND par_partyid = peop_par_partyid AND peop_middlenamesdx = ? AND peop_thrutime = ? "
                + "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid"),
                partyType, middleNameSdx, Session.MAX_TIME, entityType);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByLastName(String lastName) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, parties, partydetails, people "
                + "WHERE par_activedetailid = pardt_partydetailid "
                + "AND pardt_ptyp_partytypeid = ? "
                + "AND par_partyid = peop_par_partyid AND peop_lastname = ? AND peop_thrutime = ? "
                + "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid"),
                partyType, lastName, Session.MAX_TIME, entityType);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByLastNameSdx(String lastNameSdx) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, parties, partydetails, people "
                + "WHERE par_activedetailid = pardt_partydetailid "
                + "AND pardt_ptyp_partytypeid = ? "
                + "AND par_partyid = peop_par_partyid AND peop_lastnamesdx = ? AND peop_thrutime = ? "
                + "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid"),
                partyType, lastNameSdx, Session.MAX_TIME, entityType);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByAreaCode(GeoCode countryGeoCode, String areaCode) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, parties, partydetails, partycontactmechanisms, partycontactmechanismdetails, contacttelephones "
                + "WHERE par_activedetailid = pardt_partydetailid "
                + "AND pardt_ptyp_partytypeid = ? "
                + "AND pcm_activedetailid = pcmdt_partycontactmechanismdetailid "
                + "AND par_partyid = pcmdt_par_partyid "
                + "AND pcmdt_cmch_contactmechanismid = cttp_cmch_contactmechanismid AND cttp_thrutime = ? "
                + "AND cttp_countrygeocodeid = ? AND cttp_areacode = ? "
                + "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid"),
                partyType, Session.MAX_TIME, countryGeoCode, areaCode, entityType);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByTelephoneNumber(GeoCode countryGeoCode, String telephoneNumber) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, parties, partydetails, partycontactmechanisms, partycontactmechanismdetails, contacttelephones "
                + "WHERE par_activedetailid = pardt_partydetailid "
                + "AND pardt_ptyp_partytypeid = ? "
                + "AND pcm_activedetailid = pcmdt_partycontactmechanismdetailid "
                + "AND par_partyid = pcmdt_par_partyid "
                + "AND pcmdt_cmch_contactmechanismid = cttp_cmch_contactmechanismid AND cttp_thrutime = ? "
                + "AND cttp_countrygeocodeid = ? AND cttp_telephonenumber = ? "
                + "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid"),
                partyType, Session.MAX_TIME, countryGeoCode, telephoneNumber, entityType);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByTelephoneExtension(GeoCode countryGeoCode, String telephoneExtension) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, parties, partydetails, partycontactmechanisms, partycontactmechanismdetails, contacttelephones "
                + "WHERE par_activedetailid = pardt_partydetailid "
                + "AND pardt_ptyp_partytypeid = ? "
                + "AND pcm_activedetailid = pcmdt_partycontactmechanismdetailid "
                + "AND par_partyid = pcmdt_par_partyid "
                + "AND pcmdt_cmch_contactmechanismid = cttp_cmch_contactmechanismid AND cttp_thrutime = ? "
                + "AND cttp_countrygeocodeid = ? AND cttp_telephoneextension = ? "
                + "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid"),
                partyType, Session.MAX_TIME, countryGeoCode, telephoneExtension, entityType);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByEmailAddress(String emailAddress) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, parties, partydetails, partycontactmechanisms, partycontactmechanismdetails, contactemailaddresses "
                + "WHERE par_activedetailid = pardt_partydetailid "
                + "AND pardt_ptyp_partytypeid = ? "
                + "AND pcm_activedetailid = pcmdt_partycontactmechanismdetailid "
                + "AND par_partyid = pcmdt_par_partyid "
                + "AND pcmdt_cmch_contactmechanismid = ctea_cmch_contactmechanismid AND ctea_thrutime = ? "
                + "AND ctea_emailaddress = ? "
                + "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid"),
                partyType, Session.MAX_TIME, emailAddress, entityType);
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
