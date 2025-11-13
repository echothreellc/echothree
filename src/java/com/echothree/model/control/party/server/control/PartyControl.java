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

package com.echothree.model.control.party.server.control;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.choice.BirthdayFormatChoicesBean;
import com.echothree.model.control.party.common.choice.CompanyChoicesBean;
import com.echothree.model.control.party.common.choice.DateTimeFormatChoicesBean;
import com.echothree.model.control.party.common.choice.DepartmentChoicesBean;
import com.echothree.model.control.party.common.choice.DivisionChoicesBean;
import com.echothree.model.control.party.common.choice.GenderChoicesBean;
import com.echothree.model.control.party.common.choice.LanguageChoicesBean;
import com.echothree.model.control.party.common.choice.MoodChoicesBean;
import com.echothree.model.control.party.common.choice.NameSuffixChoicesBean;
import com.echothree.model.control.party.common.choice.PartyAliasTypeChoicesBean;
import com.echothree.model.control.party.common.choice.PartyTypeChoicesBean;
import com.echothree.model.control.party.common.choice.PersonalTitleChoicesBean;
import com.echothree.model.control.party.common.choice.TimeZoneChoicesBean;
import com.echothree.model.control.party.common.transfer.BirthdayFormatDescriptionTransfer;
import com.echothree.model.control.party.common.transfer.BirthdayFormatTransfer;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.model.control.party.common.transfer.DateTimeFormatDescriptionTransfer;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.DepartmentTransfer;
import com.echothree.model.control.party.common.transfer.DivisionTransfer;
import com.echothree.model.control.party.common.transfer.GenderDescriptionTransfer;
import com.echothree.model.control.party.common.transfer.GenderTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.MoodDescriptionTransfer;
import com.echothree.model.control.party.common.transfer.MoodTransfer;
import com.echothree.model.control.party.common.transfer.NameSuffixTransfer;
import com.echothree.model.control.party.common.transfer.PartyAliasTransfer;
import com.echothree.model.control.party.common.transfer.PartyAliasTypeDescriptionTransfer;
import com.echothree.model.control.party.common.transfer.PartyAliasTypeTransfer;
import com.echothree.model.control.party.common.transfer.PartyGroupTransfer;
import com.echothree.model.control.party.common.transfer.PartyRelationshipTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeAuditPolicyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeLockoutPolicyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypePasswordStringPolicyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.PersonalTitleTransfer;
import com.echothree.model.control.party.common.transfer.ProfileTransfer;
import com.echothree.model.control.party.common.transfer.RoleTypeTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneDescriptionTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.party.common.pk.DateTimeFormatDetailPK;
import com.echothree.model.data.party.common.pk.DateTimeFormatPK;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.common.pk.NameSuffixPK;
import com.echothree.model.data.party.common.pk.PartyAliasTypePK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.common.pk.PersonalTitleDetailPK;
import com.echothree.model.data.party.common.pk.PersonalTitlePK;
import com.echothree.model.data.party.common.pk.RoleTypePK;
import com.echothree.model.data.party.common.pk.TimeZonePK;
import com.echothree.model.data.party.server.entity.BirthdayFormat;
import com.echothree.model.data.party.server.entity.BirthdayFormatDescription;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.DateTimeFormatDescription;
import com.echothree.model.data.party.server.entity.Gender;
import com.echothree.model.data.party.server.entity.GenderDescription;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.LanguageDescription;
import com.echothree.model.data.party.server.entity.Mood;
import com.echothree.model.data.party.server.entity.MoodDescription;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.NameSuffixDetail;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyAlias;
import com.echothree.model.data.party.server.entity.PartyAliasType;
import com.echothree.model.data.party.server.entity.PartyAliasTypeDescription;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.party.server.entity.PartyRelationshipType;
import com.echothree.model.data.party.server.entity.PartyRelationshipTypeDescription;
import com.echothree.model.data.party.server.entity.PartyStatus;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PartyTypeAuditPolicy;
import com.echothree.model.data.party.server.entity.PartyTypeDescription;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicy;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
import com.echothree.model.data.party.server.entity.PartyTypeUse;
import com.echothree.model.data.party.server.entity.PartyTypeUseType;
import com.echothree.model.data.party.server.entity.PartyTypeUseTypeDescription;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.party.server.entity.PersonalTitleDetail;
import com.echothree.model.data.party.server.entity.Profile;
import com.echothree.model.data.party.server.entity.RoleType;
import com.echothree.model.data.party.server.entity.RoleTypeDescription;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.party.server.entity.TimeZoneDescription;
import com.echothree.model.data.party.server.factory.BirthdayFormatDescriptionFactory;
import com.echothree.model.data.party.server.factory.BirthdayFormatDetailFactory;
import com.echothree.model.data.party.server.factory.BirthdayFormatFactory;
import com.echothree.model.data.party.server.factory.DateTimeFormatDescriptionFactory;
import com.echothree.model.data.party.server.factory.DateTimeFormatDetailFactory;
import com.echothree.model.data.party.server.factory.DateTimeFormatFactory;
import com.echothree.model.data.party.server.factory.GenderDescriptionFactory;
import com.echothree.model.data.party.server.factory.GenderDetailFactory;
import com.echothree.model.data.party.server.factory.GenderFactory;
import com.echothree.model.data.party.server.factory.LanguageDescriptionFactory;
import com.echothree.model.data.party.server.factory.LanguageFactory;
import com.echothree.model.data.party.server.factory.MoodDescriptionFactory;
import com.echothree.model.data.party.server.factory.MoodDetailFactory;
import com.echothree.model.data.party.server.factory.MoodFactory;
import com.echothree.model.data.party.server.factory.NameSuffixDetailFactory;
import com.echothree.model.data.party.server.factory.NameSuffixFactory;
import com.echothree.model.data.party.server.factory.PartyAliasFactory;
import com.echothree.model.data.party.server.factory.PartyAliasTypeDescriptionFactory;
import com.echothree.model.data.party.server.factory.PartyAliasTypeDetailFactory;
import com.echothree.model.data.party.server.factory.PartyAliasTypeFactory;
import com.echothree.model.data.party.server.factory.PartyCompanyFactory;
import com.echothree.model.data.party.server.factory.PartyDepartmentFactory;
import com.echothree.model.data.party.server.factory.PartyDetailFactory;
import com.echothree.model.data.party.server.factory.PartyDivisionFactory;
import com.echothree.model.data.party.server.factory.PartyFactory;
import com.echothree.model.data.party.server.factory.PartyGroupFactory;
import com.echothree.model.data.party.server.factory.PartyRelationshipFactory;
import com.echothree.model.data.party.server.factory.PartyRelationshipTypeDescriptionFactory;
import com.echothree.model.data.party.server.factory.PartyRelationshipTypeFactory;
import com.echothree.model.data.party.server.factory.PartyStatusFactory;
import com.echothree.model.data.party.server.factory.PartyTypeAuditPolicyDetailFactory;
import com.echothree.model.data.party.server.factory.PartyTypeAuditPolicyFactory;
import com.echothree.model.data.party.server.factory.PartyTypeDescriptionFactory;
import com.echothree.model.data.party.server.factory.PartyTypeFactory;
import com.echothree.model.data.party.server.factory.PartyTypeLockoutPolicyDetailFactory;
import com.echothree.model.data.party.server.factory.PartyTypeLockoutPolicyFactory;
import com.echothree.model.data.party.server.factory.PartyTypePasswordStringPolicyDetailFactory;
import com.echothree.model.data.party.server.factory.PartyTypePasswordStringPolicyFactory;
import com.echothree.model.data.party.server.factory.PartyTypeUseFactory;
import com.echothree.model.data.party.server.factory.PartyTypeUseTypeDescriptionFactory;
import com.echothree.model.data.party.server.factory.PartyTypeUseTypeFactory;
import com.echothree.model.data.party.server.factory.PersonFactory;
import com.echothree.model.data.party.server.factory.PersonalTitleDetailFactory;
import com.echothree.model.data.party.server.factory.PersonalTitleFactory;
import com.echothree.model.data.party.server.factory.ProfileFactory;
import com.echothree.model.data.party.server.factory.RoleTypeDescriptionFactory;
import com.echothree.model.data.party.server.factory.RoleTypeFactory;
import com.echothree.model.data.party.server.factory.TimeZoneDescriptionFactory;
import com.echothree.model.data.party.server.factory.TimeZoneDetailFactory;
import com.echothree.model.data.party.server.factory.TimeZoneFactory;
import com.echothree.model.data.party.server.value.BirthdayFormatDescriptionValue;
import com.echothree.model.data.party.server.value.BirthdayFormatDetailValue;
import com.echothree.model.data.party.server.value.DateTimeFormatDescriptionValue;
import com.echothree.model.data.party.server.value.GenderDescriptionValue;
import com.echothree.model.data.party.server.value.GenderDetailValue;
import com.echothree.model.data.party.server.value.MoodDescriptionValue;
import com.echothree.model.data.party.server.value.MoodDetailValue;
import com.echothree.model.data.party.server.value.NameSuffixDetailValue;
import com.echothree.model.data.party.server.value.PartyAliasTypeDescriptionValue;
import com.echothree.model.data.party.server.value.PartyAliasTypeDetailValue;
import com.echothree.model.data.party.server.value.PartyAliasValue;
import com.echothree.model.data.party.server.value.PartyCompanyValue;
import com.echothree.model.data.party.server.value.PartyDepartmentValue;
import com.echothree.model.data.party.server.value.PartyDetailValue;
import com.echothree.model.data.party.server.value.PartyDivisionValue;
import com.echothree.model.data.party.server.value.PartyGroupValue;
import com.echothree.model.data.party.server.value.PartyTypeAuditPolicyDetailValue;
import com.echothree.model.data.party.server.value.PartyTypeLockoutPolicyDetailValue;
import com.echothree.model.data.party.server.value.PartyTypePasswordStringPolicyDetailValue;
import com.echothree.model.data.party.server.value.PartyTypeValue;
import com.echothree.model.data.party.server.value.PersonValue;
import com.echothree.model.data.party.server.value.PersonalTitleDetailValue;
import com.echothree.model.data.party.server.value.ProfileValue;
import com.echothree.model.data.party.server.value.TimeZoneDescriptionValue;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyControl
        extends BasePartyControl {
    
    /** Creates a new instance of PartyControl */
    protected PartyControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Languages
    // --------------------------------------------------------------------------------
    
    public Language createLanguage(String languageIsoName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var language = LanguageFactory.getInstance().create(languageIsoName, isDefault, sortOrder);
        
        sendEvent(language.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return language;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Language */
    public Language getLanguageByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new LanguagePK(entityInstance.getEntityUniqueId());

        return LanguageFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public Language getLanguageByEntityInstance(EntityInstance entityInstance) {
        return getLanguageByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Language getLanguageByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getLanguageByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countLanguages() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM languages");
    }

    public Language getDefaultLanguage() {
        Language language;
        var ps = LanguageFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM languages " +
                "WHERE lang_isdefault = 1");
        
        language = LanguageFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        
        return language;
    }
    
    private static final Map<EntityPermission, String> getLanguageByIsoNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM languages " +
                "WHERE lang_languageisoname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM languages " +
                "WHERE lang_languageisoname = ? " +
                "FOR UPDATE");
        getLanguageByIsoNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public Language getLanguageByIsoName(String languageIsoName, EntityPermission entityPermission) {
        return LanguageFactory.getInstance().getEntityFromQuery(entityPermission, getLanguageByIsoNameQueries, languageIsoName);
    }
    
    public Language getLanguageByIsoName(String languageIsoName) {
        return getLanguageByIsoName(languageIsoName, EntityPermission.READ_ONLY);
    }
    
    public Language getLanguageByIsoNameForUpdate(String languageIsoName) {
        return getLanguageByIsoName(languageIsoName, EntityPermission.READ_WRITE);
    }
    
    public List<Language> getLanguages() {
        List<Language> languages;
        var ps = LanguageFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM languages " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        
        languages = LanguageFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        
        return languages;
    }
    
    public LanguageChoicesBean getLanguageChoices(String defaultLanguageChoice, Language descriptionLanguage, boolean allowNullChoice) {
        var languages = getLanguages();
        var size = languages.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultLanguageChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var language : languages) {
            var label = getBestLanguageDescription(language, descriptionLanguage);
            var value = language.getLanguageIsoName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultLanguageChoice != null && defaultLanguageChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && language.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new LanguageChoicesBean(labels, values, defaultValue);
    }
    
    public LanguageTransfer getLanguageTransfer(UserVisit userVisit, Language language) {
        return getPartyTransferCaches().getLanguageTransferCache().getTransfer(userVisit, language);
    }
    
    public List<LanguageTransfer> getLanguageTransfers(UserVisit userVisit, Collection<Language> languages) {
        List<LanguageTransfer> languageTransfers = new ArrayList<>(languages.size());
        var languageTransferCache = getPartyTransferCaches().getLanguageTransferCache();
        
        languages.forEach((language) ->
                languageTransfers.add(languageTransferCache.getTransfer(userVisit, language))
        );
        
        return languageTransfers;
    }
    
    public List<LanguageTransfer> getLanguageTransfers(UserVisit userVisit) {
        return getLanguageTransfers(userVisit, getLanguages());
    }
    
    // --------------------------------------------------------------------------------
    //   Language Descriptions
    // --------------------------------------------------------------------------------
    
    public LanguageDescription createLanguageDescription(Language language, Language descriptionLanguage, String description,
            BasePK createdBy) {
        var languageDescription = LanguageDescriptionFactory.getInstance().create(language, descriptionLanguage, description);
        
        sendEvent(language.getPrimaryKey(), EventTypes.MODIFY, languageDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return languageDescription;
    }
    
    public LanguageDescription getLanguageDescription(Language language, Language descriptionLanguage) {
        LanguageDescription languageDescription;
        
        try {
            var ps = LanguageDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM languagedescriptions " +
                    "WHERE langd_lang_languageid = ? AND langd_descriptionlanguageid = ?");
            
            ps.setLong(1, language.getPrimaryKey().getEntityId());
            ps.setLong(2, descriptionLanguage.getPrimaryKey().getEntityId());
            
            languageDescription = LanguageDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return languageDescription;
    }
    
    
    public String getBestLanguageDescription(Language language, Language descriptionLanguage) {
        String description;
        var languageDescription = getLanguageDescription(language, descriptionLanguage);

        if(languageDescription == null && !descriptionLanguage.getIsDefault()) {
            languageDescription = getLanguageDescription(language, getDefaultLanguage());
        }

        if(languageDescription == null) {
            description = language.getLanguageIsoName();
        } else {
            description = languageDescription.getDescription();
        }

        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Party Types
    // --------------------------------------------------------------------------------
    
    public PartyType createPartyType(String partyTypeName, PartyType parentPartyType, SequenceType billingAccountSequenceType, Boolean allowUserLogins,
            Boolean allowPartyAliases, Boolean isDefault, Integer sortOrder) {
        return PartyTypeFactory.getInstance().create(partyTypeName, parentPartyType, billingAccountSequenceType, allowUserLogins, allowPartyAliases, isDefault,
                sortOrder);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PartyType */
    public PartyType getPartyTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PartyTypePK(entityInstance.getEntityUniqueId());

        return PartyTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public PartyType getPartyTypeByEntityInstance(EntityInstance entityInstance) {
        return getPartyTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PartyType getPartyTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPartyTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM partytypes");
    }

    private static final Map<EntityPermission, String> getPartyTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM partytypes " +
                        "WHERE ptyp_partytypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM partytypes " +
                        "WHERE ptyp_partytypename = ? " +
                        "FOR UPDATE");
        getPartyTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public PartyType getPartyTypeByName(String partyTypeName, EntityPermission entityPermission) {
        return PartyTypeFactory.getInstance().getEntityFromQuery(entityPermission, getPartyTypeByNameQueries,
                partyTypeName);
    }

    public PartyType getPartyTypeByName(String partyTypeName) {
        return getPartyTypeByName(partyTypeName, EntityPermission.READ_ONLY);
    }

    public PartyType getPartyTypeByNameForUpdate(String partyTypeName) {
        return getPartyTypeByName(partyTypeName, EntityPermission.READ_WRITE);
    }

    public PartyTypeValue getPartyTypeValueForUpdate(PartyType partyType) {
        return partyType == null? null: partyType.getPartyTypeValue().clone();
    }

    public PartyTypeValue getPartyTypeValueByNameForUpdate(String partyTypeName) {
        return getPartyTypeValueForUpdate(getPartyTypeByNameForUpdate(partyTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultPartyTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM partyaliastypes, partyaliastypedetails " +
                        "WHERE pat_activedetailid = patdt_partyaliastypedetailid AND patdt_ptyp_partytypeid = ? " +
                        "AND patdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM partyaliastypes, partyaliastypedetails " +
                        "WHERE pat_activedetailid = patdt_partyaliastypedetailid AND patdt_ptyp_partytypeid = ? " +
                        "AND patdt_isdefault = 1 " +
                        "FOR UPDATE");
        getDefaultPartyTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public PartyType getDefaultPartyType(EntityPermission entityPermission) {
        return PartyTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPartyTypeQueries);
    }

    public PartyType getDefaultPartyType() {
        return getDefaultPartyType(EntityPermission.READ_ONLY);
    }

    public PartyType getDefaultPartyTypeForUpdate() {
        return getDefaultPartyType(EntityPermission.READ_WRITE);
    }

    public PartyTypeValue getDefaultPartyTypeValueForUpdate() {
        return getDefaultPartyTypeForUpdate().getPartyTypeValue().clone();
    }

    private static final Map<EntityPermission, String> getPartyTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM partytypes " +
                        "ORDER BY ptyp_sortorder, ptyp_partytypename " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM partytypes " +
                        "FOR UPDATE");
        getPartyTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyType> getPartyTypes(EntityPermission entityPermission) {
        return PartyTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTypesQueries);
    }

    public List<PartyType> getPartyTypes() {
        return getPartyTypes(EntityPermission.READ_ONLY);
    }

    public List<PartyType> getPartyTypesForUpdate() {
        return getPartyTypes(EntityPermission.READ_WRITE);
    }

    public PartyTypeChoicesBean getPartyTypeChoices(String defaultPartyTypeChoice, Language language, boolean allowNullChoice) {
        var partyTypes = getPartyTypes();
        var size = partyTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(var partyType : partyTypes) {
            var label = getBestPartyTypeDescription(partyType, language);
            var value = partyType.getPartyTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultPartyTypeChoice != null && defaultPartyTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partyType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new PartyTypeChoicesBean(labels, values, defaultValue);
    }
    
    public PartyTypeTransfer getPartyTypeTransfer(UserVisit userVisit, PartyType partyType) {
        return getPartyTransferCaches().getPartyTypeTransferCache().getTransfer(userVisit, partyType);
    }
    
    public List<PartyTypeTransfer> getPartyTypeTransfers(UserVisit userVisit, Collection<PartyType> partyTypes) {
        List<PartyTypeTransfer> partyTypeTransfers = new ArrayList<>(partyTypes.size());
        var partyTypeTransferCache = getPartyTransferCaches().getPartyTypeTransferCache();
        
        partyTypes.forEach((partyType) ->
                partyTypeTransfers.add(partyTypeTransferCache.getTransfer(userVisit, partyType))
        );
        
        return partyTypeTransfers;
    }
    
    public List<PartyTypeTransfer> getPartyTypeTransfers(UserVisit userVisit) {
        return getPartyTypeTransfers(userVisit, getPartyTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Party Type Descriptions
    // --------------------------------------------------------------------------------
    
    public PartyTypeDescription createPartyTypeDescription(PartyType partyType, Language language, String description) {
        return PartyTypeDescriptionFactory.getInstance().create(partyType, language, description);
    }
    
    public PartyTypeDescription getPartyTypeDescription(PartyType partyType, Language language) {
        PartyTypeDescription partyTypeDescription;
        
        try {
            var ps = PartyTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM partytypedescriptions " +
                    "WHERE ptypd_ptyp_partytypeid = ? AND ptypd_lang_languageid = ?");
            
            ps.setLong(1, partyType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            partyTypeDescription = PartyTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyTypeDescription;
    }
    
    public String getBestPartyTypeDescription(PartyType partyType, Language language) {
        String description;
        var partyTypeDescription = getPartyTypeDescription(partyType, language);
        
        if(partyTypeDescription == null && !language.getIsDefault()) {
            partyTypeDescription = getPartyTypeDescription(partyType, partyControl.getDefaultLanguage());
        }
        
        if(partyTypeDescription == null) {
            description = partyType.getPartyTypeName();
        } else {
            description = partyTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Party Type Use Types
    // --------------------------------------------------------------------------------
    
    public PartyTypeUseType createPartyTypeUseType(String partyTypeUseTypeName, Boolean isDefault, Integer sortOrder) {
        return PartyTypeUseTypeFactory.getInstance().create(partyTypeUseTypeName, isDefault, sortOrder);
    }
    
    public PartyTypeUseType getPartyTypeUseTypeByName(String partyTypeUseTypeName) {
        PartyTypeUseType partyTypeUseType;
        
        try {
            var ps = PartyTypeUseTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM partytypeusetypes " +
                    "WHERE ptyput_partytypeusetypename = ?");
            
            ps.setString(1, partyTypeUseTypeName);
            
            partyTypeUseType = PartyTypeUseTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyTypeUseType;
    }
    
    // --------------------------------------------------------------------------------
    //   Party Type Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    public PartyTypeUseTypeDescription createPartyTypeUseTypeDescription(PartyTypeUseType partyTypeUseType, Language language,
            String description) {
        return PartyTypeUseTypeDescriptionFactory.getInstance().create(partyTypeUseType, language, description);
    }
    
    public PartyTypeUseTypeDescription getPartyTypeUseTypeDescription(PartyTypeUseType partyTypeUseType, Language language) {
        PartyTypeUseTypeDescription partyTypeUseTypeDescription;
        
        try {
            var ps = PartyTypeUseTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM partytypeusetypedescriptions " +
                    "WHERE ptyputd_ptyput_partytypeusetypeid = ? AND ptyputd_lang_languageid = ?");
            
            ps.setLong(1, partyTypeUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            partyTypeUseTypeDescription = PartyTypeUseTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyTypeUseTypeDescription;
    }
    
    public String getBestPartyTypeUseTypeDescription(PartyTypeUseType partyTypeUseType, Language language) {
        String description;
        var partyTypeUseTypeDescription = getPartyTypeUseTypeDescription(partyTypeUseType, language);
        
        if(partyTypeUseTypeDescription == null && !language.getIsDefault()) {
            partyTypeUseTypeDescription = getPartyTypeUseTypeDescription(partyTypeUseType, partyControl.getDefaultLanguage());
        }
        
        if(partyTypeUseTypeDescription == null) {
            description = partyTypeUseType.getPartyTypeUseTypeName();
        } else {
            description = partyTypeUseTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Party Type Uses
    // --------------------------------------------------------------------------------
    
    public PartyTypeUse createPartyTypeUse(PartyTypeUseType partyTypeUseType, PartyType partyType, Boolean isDefault) {
        return PartyTypeUseFactory.getInstance().create(partyTypeUseType, partyType, isDefault);
    }
    
    public PartyTypeUse getPartyTypeUse(PartyTypeUseType partyTypeUseType, PartyType partyType) {
        PartyTypeUse partyTypeUse;
        
        try {
            var ps = PartyTypeUseFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM partytypeuses " +
                    "WHERE ptypu_ptyput_partytypeusetypeid = ? AND ptypu_ptyp_partytypeid = ?");
            
            ps.setLong(1, partyTypeUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, partyType.getPrimaryKey().getEntityId());
            
            partyTypeUse = PartyTypeUseFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyTypeUse;
    }
    
    // --------------------------------------------------------------------------------
    //   Personal Titles
    // --------------------------------------------------------------------------------
    
    public PersonalTitle createPersonalTitle(String description, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPersonalTitle = getDefaultPersonalTitle();
        var defaultFound = defaultPersonalTitle != null;
        
        if(defaultFound && isDefault) {
            var defaultPersonalTitleDetailValue = getDefaultPersonalTitleDetailValueForUpdate();
            
            defaultPersonalTitleDetailValue.setIsDefault(false);
            updatePersonalTitleFromValue(defaultPersonalTitleDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var personalTitle = PersonalTitleFactory.getInstance().create((PersonalTitleDetailPK)null,
                (PersonalTitleDetailPK)null);
        var personalTitleDetail = PersonalTitleDetailFactory.getInstance().create(personalTitle,
                description, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        personalTitle = PersonalTitleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                personalTitle.getPrimaryKey());
        personalTitle.setActiveDetail(personalTitleDetail);
        personalTitle.setLastDetail(personalTitleDetail);
        personalTitle.store();
        
        sendEvent(personalTitle.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return personalTitle;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PersonalTitle */
    public PersonalTitle getPersonalTitleByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PersonalTitlePK(entityInstance.getEntityUniqueId());

        return PersonalTitleFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public PersonalTitle getPersonalTitleByEntityInstance(EntityInstance entityInstance) {
        return getPersonalTitleByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PersonalTitle getPersonalTitleByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPersonalTitleByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPersonalTitles() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM personaltitles, personaltitledetails " +
                "WHERE pert_activedetailid = pertd_personaltitledetailid");
    }

    private List<PersonalTitle> getPersonalTitles(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM personaltitles, personaltitledetails " +
                    "WHERE pert_activedetailid = pertd_personaltitledetailid " +
                    "ORDER BY pertd_sortorder, pertd_description " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM personaltitles, personaltitledetails " +
                    "WHERE pert_activedetailid = pertd_personaltitledetailid " +
                    "ORDER BY pertd_sortorder, pertd_description " +
                    "FOR UPDATE";
        }

        var ps = PersonalTitleFactory.getInstance().prepareStatement(query);
        
        return PersonalTitleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<PersonalTitle> getPersonalTitles() {
        return getPersonalTitles(EntityPermission.READ_ONLY);
    }
    
    public List<PersonalTitle> getPersonalTitlesForUpdate() {
        return getPersonalTitles(EntityPermission.READ_WRITE);
    }
    
    public List<PersonalTitleDetail> getPersonalTitleDetails() {
        var ps = PersonalTitleDetailFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM personaltitles, personaltitledetails " +
                "WHERE pert_activedetailid = pertd_personaltitledetailid " +
                "ORDER BY pertd_sortorder, pertd_description");
        
        return PersonalTitleDetailFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    private PersonalTitle getDefaultPersonalTitle(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM personaltitles, personaltitledetails " +
                    "WHERE pert_activedetailid = pertd_personaltitledetailid AND pertd_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM personaltitles, personaltitledetails " +
                    "WHERE pert_activedetailid = pertd_personaltitledetailid AND pertd_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = PersonalTitleFactory.getInstance().prepareStatement(query);
        
        return PersonalTitleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public PersonalTitle getDefaultPersonalTitle() {
        return getDefaultPersonalTitle(EntityPermission.READ_ONLY);
    }
    
    public PersonalTitle getDefaultPersonalTitleForUpdate() {
        return getDefaultPersonalTitle(EntityPermission.READ_WRITE);
    }
    
    public PersonalTitleDetailValue getDefaultPersonalTitleDetailValueForUpdate() {
        return getDefaultPersonalTitleForUpdate().getLastDetailForUpdate().getPersonalTitleDetailValue().clone();
    }
    
    public PersonalTitleChoicesBean getPersonalTitleChoices(String defaultPersonalTitleChoice, boolean allowNullChoice) {
        var personalTitleDetails = getPersonalTitleDetails();
        var size = personalTitleDetails.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultPersonalTitleChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var personalTitleDetail : personalTitleDetails) {
            var label = personalTitleDetail.getDescription();
            var value = personalTitleDetail.getPersonalTitlePK().getEntityId().toString();
            
            labels.add(label == null? "": label);
            values.add(value);
            
            var usingDefaultChoice = defaultPersonalTitleChoice != null && defaultPersonalTitleChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && personalTitleDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new PersonalTitleChoicesBean(labels, values, defaultValue);
    }
    
    public boolean validPersonalTitlePK(PersonalTitlePK personalTitlePK) {
        return PersonalTitleFactory.getInstance().validPK(session, personalTitlePK);
    }
    
    public PersonalTitlePK convertPersonalTitleIdToPK(String personalTitleId) {
        var personalTitlePK = new PersonalTitlePK(Long.valueOf(personalTitleId));
        
        return validPersonalTitlePK(personalTitlePK)? personalTitlePK: null;
    }
    
    public PersonalTitle convertPersonalTitleIdToEntity(String personalTitleId, EntityPermission entityPermission) {
        var personalTitlePK = convertPersonalTitleIdToPK(personalTitleId);
        var personalTitle = personalTitlePK == null? null: PersonalTitleFactory.getInstance().getEntityFromPK(session,
                entityPermission, personalTitlePK);
        
        return personalTitle;
    }
    
    public PersonalTitleDetailValue getPersonalTitleDetailValueByPKForUpdate(PersonalTitlePK personalTitlePK) {
        var personalTitle = PersonalTitleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                personalTitlePK);
        var personalTitleDetail = personalTitle.getActiveDetailForUpdate();
        
        return personalTitleDetail.getPersonalTitleDetailValue().clone();
    }
    
    private void updatePersonalTitleFromValue(PersonalTitleDetailValue personalTitleDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(personalTitleDetailValue.hasBeenModified()) {
            var personalTitle = PersonalTitleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    personalTitleDetailValue.getPersonalTitlePK());
            var personalTitleDetail = personalTitle.getActiveDetailForUpdate();
            
            personalTitleDetail.setThruTime(session.START_TIME_LONG);
            personalTitleDetail.store();

            var description = personalTitleDetailValue.getDescription();
            var sortOrder = personalTitleDetailValue.getSortOrder();
            var isDefault = personalTitleDetailValue.getIsDefault();
            
            if(checkDefault) {
                var defaultPersonalTitle = getDefaultPersonalTitle();
                var defaultFound = defaultPersonalTitle != null && !defaultPersonalTitle.equals(personalTitle);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPersonalTitleDetailValue = getDefaultPersonalTitleDetailValueForUpdate();
                    
                    defaultPersonalTitleDetailValue.setIsDefault(false);
                    updatePersonalTitleFromValue(defaultPersonalTitleDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            personalTitleDetail = PersonalTitleDetailFactory.getInstance().create(personalTitle, description, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            personalTitle.setActiveDetail(personalTitleDetail);
            personalTitle.setLastDetail(personalTitleDetail);
            
            sendEvent(personalTitle.getPrimaryKey(), EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updatePersonalTitleFromValue(PersonalTitleDetailValue personalTitleDetailValue, BasePK updatedBy) {
        updatePersonalTitleFromValue(personalTitleDetailValue, true, updatedBy);
    }
    
    public PersonalTitleTransfer getPersonalTitleTransfer(UserVisit userVisit, PersonalTitle personalTitle) {
        return getPartyTransferCaches().getPersonalTitleTransferCache().getTransfer(userVisit, personalTitle);
    }
    
    public List<PersonalTitleTransfer> getPersonalTitleTransfers(UserVisit userVisit, Collection<PersonalTitle> personalTitles) {
        List<PersonalTitleTransfer> personalTitleTransfers = new ArrayList<>(personalTitles.size());
        var personalTitleTransferCache = getPartyTransferCaches().getPersonalTitleTransferCache();
        
        personalTitles.forEach((personalTitle) ->
                personalTitleTransfers.add(personalTitleTransferCache.getTransfer(userVisit, personalTitle))
        );
        
        return personalTitleTransfers;
    }
    
    public List<PersonalTitleTransfer> getPersonalTitleTransfers(UserVisit userVisit) {
        return getPersonalTitleTransfers(userVisit, getPersonalTitles());
    }
    
    public void deletePersonalTitle(PersonalTitle personalTitle, BasePK deletedBy) {
        var personalTitleDetail = personalTitle.getLastDetailForUpdate();
        personalTitleDetail.setThruTime(session.START_TIME_LONG);
        personalTitle.setActiveDetail(null);
        personalTitle.store();
        
        // Check for default, and pick one if necessary
        var defaultPersonalTitle = getDefaultPersonalTitle();
        if(defaultPersonalTitle == null) {
            var personalTitles = getPersonalTitlesForUpdate();
            
            if(!personalTitles.isEmpty()) {
                var iter = personalTitles.iterator();
                if(iter.hasNext()) {
                    defaultPersonalTitle = iter.next();
                }
                var personalTitleDetailValue = Objects.requireNonNull(defaultPersonalTitle).getLastDetailForUpdate().getPersonalTitleDetailValue().clone();
                
                personalTitleDetailValue.setIsDefault(true);
                updatePersonalTitleFromValue(personalTitleDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(personalTitle.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Name Suffixes
    // --------------------------------------------------------------------------------
    
    public NameSuffix createNameSuffix(String description, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultNameSuffix = getDefaultNameSuffix();
        var defaultFound = defaultNameSuffix != null;
        
        if(defaultFound && isDefault) {
            var defaultNameSuffixDetailValue = getDefaultNameSuffixDetailValueForUpdate();
            
            defaultNameSuffixDetailValue.setIsDefault(false);
            updateNameSuffixFromValue(defaultNameSuffixDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var nameSuffix = NameSuffixFactory.getInstance().create();
        var nameSuffixDetail = NameSuffixDetailFactory.getInstance().create(nameSuffix, description,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        nameSuffix = NameSuffixFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, nameSuffix.getPrimaryKey());
        nameSuffix.setActiveDetail(nameSuffixDetail);
        nameSuffix.setLastDetail(nameSuffixDetail);
        nameSuffix.store();
        
        sendEvent(nameSuffix.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return nameSuffix;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.NameSuffix */
    public NameSuffix getNameSuffixByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new NameSuffixPK(entityInstance.getEntityUniqueId());

        return NameSuffixFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public NameSuffix getNameSuffixByEntityInstance(EntityInstance entityInstance) {
        return getNameSuffixByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public NameSuffix getNameSuffixByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getNameSuffixByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countNameSuffixes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM namesuffixes, namesuffixdetails " +
                "WHERE nsfx_activedetailid = nsfxd_namesuffixdetailid");
    }

    private List<NameSuffix> getNameSuffixes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM namesuffixes, namesuffixdetails " +
                    "WHERE nsfx_activedetailid = nsfxd_namesuffixdetailid " +
                    "ORDER BY nsfxd_sortorder, nsfxd_description " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM namesuffixes, namesuffixdetails " +
                    "WHERE nsfx_activedetailid = nsfxd_namesuffixdetailid " +
                    "ORDER BY nsfxd_sortorder, nsfxd_description " +
                    "FOR UPDATE";
        }

        var ps = NameSuffixFactory.getInstance().prepareStatement(query);
        
        return NameSuffixFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<NameSuffix> getNameSuffixes() {
        return getNameSuffixes(EntityPermission.READ_ONLY);
    }
    
    public List<NameSuffix> getNameSuffixesForUpdate() {
        return getNameSuffixes(EntityPermission.READ_WRITE);
    }
    
    public List<NameSuffixDetail> getNameSuffixDetails() {
        var ps = NameSuffixDetailFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM namesuffixes, namesuffixdetails " +
                "WHERE nsfx_activedetailid = nsfxd_namesuffixdetailid " +
                "ORDER BY nsfxd_sortorder, nsfxd_description");
        
        return NameSuffixDetailFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    private NameSuffix getDefaultNameSuffix(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM namesuffixes, namesuffixdetails " +
                    "WHERE nsfx_activedetailid = nsfxd_namesuffixdetailid AND nsfxd_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM namesuffixes, namesuffixdetails " +
                    "WHERE nsfx_activedetailid = nsfxd_namesuffixdetailid AND nsfxd_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = NameSuffixFactory.getInstance().prepareStatement(query);
        
        return NameSuffixFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public NameSuffix getDefaultNameSuffix() {
        return getDefaultNameSuffix(EntityPermission.READ_ONLY);
    }
    
    public NameSuffix getDefaultNameSuffixForUpdate() {
        return getDefaultNameSuffix(EntityPermission.READ_WRITE);
    }
    
    public NameSuffixDetailValue getDefaultNameSuffixDetailValueForUpdate() {
        return getDefaultNameSuffixForUpdate().getLastDetailForUpdate().getNameSuffixDetailValue().clone();
    }
    
    public NameSuffixChoicesBean getNameSuffixChoices(String defaultNameSuffixChoice, boolean allowNullChoice) {
        var nameSuffixDetails = getNameSuffixDetails();
        var size = nameSuffixDetails.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultNameSuffixChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var nameSuffixDetail : nameSuffixDetails) {
            var label = nameSuffixDetail.getDescription();
            var value = nameSuffixDetail.getNameSuffixPK().getEntityId().toString();
            
            labels.add(label == null? "": label);
            values.add(value);
            
            var usingDefaultChoice = defaultNameSuffixChoice != null && defaultNameSuffixChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && nameSuffixDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new NameSuffixChoicesBean(labels, values, defaultValue);
    }
    
    public boolean validNameSuffixPK(NameSuffixPK nameSuffixPK) {
        return NameSuffixFactory.getInstance().validPK(session, nameSuffixPK);
    }
    
    public NameSuffixPK convertNameSuffixIdToPK(String nameSuffixId) {
        var nameSuffixPK = new NameSuffixPK(Long.valueOf(nameSuffixId));
        
        return validNameSuffixPK(nameSuffixPK)? nameSuffixPK: null;
    }
    
    public NameSuffix convertNameSuffixIdToEntity(String nameSuffixId, EntityPermission entityPermission) {
        var nameSuffixPK = convertNameSuffixIdToPK(nameSuffixId);
        var nameSuffix = nameSuffixPK == null? null: NameSuffixFactory.getInstance().getEntityFromPK(session,
                entityPermission, nameSuffixPK);
        
        return nameSuffix;
    }
    
    public NameSuffixDetailValue getNameSuffixDetailValueByPKForUpdate(NameSuffixPK nameSuffixPK) {
        var nameSuffix = NameSuffixFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, nameSuffixPK);
        var nameSuffixDetail = nameSuffix.getActiveDetailForUpdate();
        
        return nameSuffixDetail.getNameSuffixDetailValue().clone();
    }
    
    private void updateNameSuffixFromValue(NameSuffixDetailValue nameSuffixDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(nameSuffixDetailValue.hasBeenModified()) {
            var nameSuffix = NameSuffixFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    nameSuffixDetailValue.getNameSuffixPK());
            var nameSuffixDetail = nameSuffix.getActiveDetailForUpdate();
            
            nameSuffixDetail.setThruTime(session.START_TIME_LONG);
            nameSuffixDetail.store();

            var description = nameSuffixDetailValue.getDescription();
            var sortOrder = nameSuffixDetailValue.getSortOrder();
            var isDefault = nameSuffixDetailValue.getIsDefault();
            
            if(checkDefault) {
                var defaultNameSuffix = getDefaultNameSuffix();
                var defaultFound = defaultNameSuffix != null && !defaultNameSuffix.equals(nameSuffix);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultNameSuffixDetailValue = getDefaultNameSuffixDetailValueForUpdate();
                    
                    defaultNameSuffixDetailValue.setIsDefault(false);
                    updateNameSuffixFromValue(defaultNameSuffixDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            nameSuffixDetail = NameSuffixDetailFactory.getInstance().create(nameSuffix, description, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            nameSuffix.setActiveDetail(nameSuffixDetail);
            nameSuffix.setLastDetail(nameSuffixDetail);
            
            sendEvent(nameSuffix.getPrimaryKey(), EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateNameSuffixFromValue(NameSuffixDetailValue nameSuffixDetailValue, BasePK updatedBy) {
        updateNameSuffixFromValue(nameSuffixDetailValue, true, updatedBy);
    }
    
    public NameSuffixTransfer getNameSuffixTransfer(UserVisit userVisit, NameSuffix nameSuffix) {
        return getPartyTransferCaches().getNameSuffixTransferCache().getTransfer(userVisit, nameSuffix);
    }
    
    public List<NameSuffixTransfer> getNameSuffixTransfers(UserVisit userVisit, Collection<NameSuffix> nameSuffixes) {
        List<NameSuffixTransfer> nameSuffixTransfers = new ArrayList<>(nameSuffixes.size());
        var nameSuffixTransferCache = getPartyTransferCaches().getNameSuffixTransferCache();
        
        nameSuffixes.forEach((nameSuffix) ->
                nameSuffixTransfers.add(nameSuffixTransferCache.getTransfer(userVisit, nameSuffix))
        );
        
        return nameSuffixTransfers;
    }
    
    public List<NameSuffixTransfer> getNameSuffixTransfers(UserVisit userVisit) {
        return getNameSuffixTransfers(userVisit, getNameSuffixes());
    }
    
    public void deleteNameSuffix(NameSuffix nameSuffix, BasePK deletedBy) {
        var nameSuffixDetail = nameSuffix.getLastDetailForUpdate();
        nameSuffixDetail.setThruTime(session.START_TIME_LONG);
        nameSuffix.setActiveDetail(null);
        nameSuffix.store();
        
        // Check for default, and pick one if necessary
        var defaultNameSuffix = getDefaultNameSuffix();
        if(defaultNameSuffix == null) {
            var nameSuffixes = getNameSuffixesForUpdate();
            
            if(!nameSuffixes.isEmpty()) {
                var iter = nameSuffixes.iterator();
                if(iter.hasNext()) {
                    defaultNameSuffix = iter.next();
                }
                var nameSuffixDetailValue = Objects.requireNonNull(defaultNameSuffix).getLastDetailForUpdate().getNameSuffixDetailValue().clone();
                
                nameSuffixDetailValue.setIsDefault(true);
                updateNameSuffixFromValue(nameSuffixDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(nameSuffix.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Time Zones
    // --------------------------------------------------------------------------------
    
    public TimeZone createTimeZone(String javaTimeZoneName, String unixTimeZoneName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {

        var timeZone = TimeZoneFactory.getInstance().create();
        var timeZoneDetail = TimeZoneDetailFactory.getInstance().create(timeZone, javaTimeZoneName,
                unixTimeZoneName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        timeZone = TimeZoneFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, timeZone.getPrimaryKey());
        timeZone.setActiveDetail(timeZoneDetail);
        timeZone.setLastDetail(timeZoneDetail);
        timeZone.store();
        
        sendEvent(timeZone.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return timeZone;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TimeZone */
    public TimeZone getTimeZoneByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TimeZonePK(entityInstance.getEntityUniqueId());

        return TimeZoneFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public TimeZone getTimeZoneByEntityInstance(EntityInstance entityInstance) {
        return getTimeZoneByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TimeZone getTimeZoneByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTimeZoneByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTimeZones() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM timezones, timezonedetails " +
                "WHERE tz_activedetailid = tzdt_timezonedetailid");
    }

    public List<TimeZone> getTimeZones() {
        List<TimeZone> timeZones;
        
        try {
            var ps = TimeZoneFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM timezones, timezonedetails " +
                    "WHERE tz_timezoneid = tzdt_tz_timezoneid AND tzdt_thrutime = ? " +
                    "ORDER BY tzdt_sortorder, tzdt_javatimezonename " +
                    "_LIMIT_");
            
            ps.setLong(1, Session.MAX_TIME);
            
            timeZones = TimeZoneFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return timeZones;
    }
    
    public TimeZone getDefaultTimeZone() {
        TimeZone timeZone;
        
        try {
            var ps = TimeZoneFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM timezones, timezonedetails " +
                    "WHERE tz_timezoneid = tzdt_tz_timezoneid AND tzdt_isdefault = 1 AND tzdt_thrutime = ?");
            
            ps.setLong(1, Session.MAX_TIME);
            
            timeZone = TimeZoneFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return timeZone;
    }
    
    public TimeZone getTimeZoneByJavaName(String javaTimeZoneName) {
        TimeZone timeZone;
        
        try {
            var ps = TimeZoneFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM timezones, timezonedetails " +
                    "WHERE tz_timezoneid = tzdt_tz_timezoneid AND tzdt_javatimezonename = ? AND tzdt_thrutime = ?");
            
            ps.setString(1, javaTimeZoneName);
            ps.setLong(2, Session.MAX_TIME);
            
            timeZone = TimeZoneFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return timeZone;
    }
    
    public TimeZoneChoicesBean getTimeZoneChoices(String defaultTimeZoneChoice, Language language, boolean allowNullChoice) {
        var timeZones = getTimeZones();
        var size = timeZones.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultTimeZoneChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var timeZone : timeZones) {
            var timeZoneDetail = timeZone.getLastDetail();
            var value = timeZoneDetail.getJavaTimeZoneName();
            
            labels.add(getBestTimeZoneDescription(timeZone, language));
            values.add(value);
            
            var usingDefaultChoice = defaultTimeZoneChoice != null && defaultTimeZoneChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && timeZoneDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new TimeZoneChoicesBean(labels, values, defaultValue);
    }
    
    public TimeZoneTransfer getTimeZoneTransfer(UserVisit userVisit, TimeZone timeZone) {
        return getPartyTransferCaches().getTimeZoneTransferCache().getTransfer(userVisit, timeZone);
    }
    
    public List<TimeZoneTransfer> getTimeZoneTransfers(UserVisit userVisit, Collection<TimeZone> timeZones) {
        List<TimeZoneTransfer> timeZoneTransfers = new ArrayList<>(timeZones.size());
        var timeZoneTransferCache = getPartyTransferCaches().getTimeZoneTransferCache();
        
        timeZones.forEach((timeZone) ->
                timeZoneTransfers.add(timeZoneTransferCache.getTransfer(userVisit, timeZone))
        );
        
        return timeZoneTransfers;
    }
    
    public List<TimeZoneTransfer> getTimeZoneTransfers(UserVisit userVisit) {
        return getTimeZoneTransfers(userVisit, getTimeZones());
    }
    
    // --------------------------------------------------------------------------------
    //   Time Zone Descriptions
    // --------------------------------------------------------------------------------
    
    public TimeZoneDescription createTimeZoneDescription(TimeZone timeZone, Language language, String description, BasePK createdBy) {
        var timeZoneDescription = TimeZoneDescriptionFactory.getInstance().create(timeZone, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(timeZone.getPrimaryKey(), EventTypes.MODIFY, timeZoneDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return timeZoneDescription;
    }
    
    private List<TimeZoneDescription> getTimeZoneDescriptionsByTimeZone(TimeZone timeZone, EntityPermission entityPermission) {
        List<TimeZoneDescription> timeZoneDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM timezonedescriptions, languages " +
                        "WHERE tzd_tz_timezoneid = ? AND tzd_thrutime = ? AND tzd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM timezonedescriptions " +
                        "WHERE tzd_tz_timezoneid = ? AND tzd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = TimeZoneDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, timeZone.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            timeZoneDescriptions = TimeZoneDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return timeZoneDescriptions;
    }
    
    public List<TimeZoneDescription> getTimeZoneDescriptionsByTimeZone(TimeZone timeZone) {
        return getTimeZoneDescriptionsByTimeZone(timeZone, EntityPermission.READ_ONLY);
    }
    
    public List<TimeZoneDescription> getTimeZoneDescriptionsByTimeZoneForUpdate(TimeZone timeZone) {
        return getTimeZoneDescriptionsByTimeZone(timeZone, EntityPermission.READ_WRITE);
    }
    
    private TimeZoneDescription getTimeZoneDescription(TimeZone timeZone, Language language, EntityPermission entityPermission) {
        TimeZoneDescription timeZoneDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM timezonedescriptions " +
                        "WHERE tzd_tz_timezoneid = ? AND tzd_lang_languageid = ? AND tzd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM timezonedescriptions " +
                        "WHERE tzd_tz_timezoneid = ? AND tzd_lang_languageid = ? AND tzd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = TimeZoneDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, timeZone.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            timeZoneDescription = TimeZoneDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return timeZoneDescription;
    }
    
    public TimeZoneDescription getTimeZoneDescription(TimeZone timeZone, Language language) {
        return getTimeZoneDescription(timeZone, language, EntityPermission.READ_ONLY);
    }
    
    public TimeZoneDescription getTimeZoneDescriptionForUpdate(TimeZone timeZone, Language language) {
        return getTimeZoneDescription(timeZone, language, EntityPermission.READ_WRITE);
    }
    
    public TimeZoneDescriptionValue getTimeZoneDescriptionValue(TimeZoneDescription timeZoneDescription) {
        return timeZoneDescription == null? null: timeZoneDescription.getTimeZoneDescriptionValue().clone();
    }
    
    public TimeZoneDescriptionValue getTimeZoneDescriptionValueForUpdate(TimeZone timeZone, Language language) {
        return getTimeZoneDescriptionValue(getTimeZoneDescriptionForUpdate(timeZone, language));
    }
    
    public String getBestTimeZoneDescription(TimeZone timeZone, Language language) {
        String description;
        var timeZoneDescription = getTimeZoneDescription(timeZone, language);
        
        if(timeZoneDescription == null && !language.getIsDefault()) {
            timeZoneDescription = getTimeZoneDescription(timeZone, partyControl.getDefaultLanguage());
        }
        
        if(timeZoneDescription == null) {
            description = timeZone.getLastDetail().getJavaTimeZoneName();
        } else {
            description = timeZoneDescription.getDescription();
        }
        
        return description;
    }
    
    public TimeZoneDescriptionTransfer getTimeZoneDescriptionTransfer(UserVisit userVisit, TimeZoneDescription timeZoneDescription) {
        return getPartyTransferCaches().getTimeZoneDescriptionTransferCache().getTransfer(userVisit, timeZoneDescription);
    }
    
    public List<TimeZoneDescriptionTransfer> getTimeZoneDescriptionTransfers(UserVisit userVisit, TimeZone timeZone) {
        var timeZoneDescriptions = getTimeZoneDescriptionsByTimeZone(timeZone);
        List<TimeZoneDescriptionTransfer> timeZoneDescriptionTransfers = new ArrayList<>(timeZoneDescriptions.size());
        var timeZoneDescriptionTransferCache = getPartyTransferCaches().getTimeZoneDescriptionTransferCache();
        
        timeZoneDescriptions.forEach((timeZoneDescription) ->
                timeZoneDescriptionTransfers.add(timeZoneDescriptionTransferCache.getTransfer(userVisit, timeZoneDescription))
        );
        
        return timeZoneDescriptionTransfers;
    }
    
    public void updateTimeZoneDescriptionFromValue(TimeZoneDescriptionValue timeZoneDescriptionValue, BasePK updatedBy) {
        if(timeZoneDescriptionValue.hasBeenModified()) {
            var timeZoneDescription = TimeZoneDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     timeZoneDescriptionValue.getPrimaryKey());
            
            timeZoneDescription.setThruTime(session.START_TIME_LONG);
            timeZoneDescription.store();

            var timeZone = timeZoneDescription.getTimeZone();
            var language = timeZoneDescription.getLanguage();
            var description = timeZoneDescriptionValue.getDescription();
            
            timeZoneDescription = TimeZoneDescriptionFactory.getInstance().create(timeZone, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(timeZone.getPrimaryKey(), EventTypes.MODIFY, timeZoneDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTimeZoneDescription(TimeZoneDescription timeZoneDescription, BasePK deletedBy) {
        timeZoneDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(timeZoneDescription.getTimeZonePK(), EventTypes.MODIFY, timeZoneDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteTimeZoneDescriptionsByTimeZone(TimeZone timeZone, BasePK deletedBy) {
        var timeZoneDescriptions = getTimeZoneDescriptionsByTimeZoneForUpdate(timeZone);
        
        timeZoneDescriptions.forEach((timeZoneDescription) -> 
                deleteTimeZoneDescription(timeZoneDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Date Time Formats
    // --------------------------------------------------------------------------------
    
    public DateTimeFormat createDateTimeFormat(String dateTimeFormatName, String javaShortDateFormat, String javaAbbrevDateFormat,
            String javaAbbrevDateFormatWeekday, String javaLongDateFormat, String javaLongDateFormatWeekday, String javaTimeFormat,
            String javaTimeFormatSeconds, String unixShortDateFormat, String unixAbbrevDateFormat,
            String unixAbbrevDateFormatWeekday, String unixLongDateFormat, String unixLongDateFormatWeekday, String unixTimeFormat,
            String unixTimeFormatSeconds, String shortDateSeparator, String timeSeparator, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var dateTimeFormat = DateTimeFormatFactory.getInstance().create((DateTimeFormatDetailPK)null,
                (DateTimeFormatDetailPK)null);
        var dateTimeFormatDetail = DateTimeFormatDetailFactory.getInstance().create(dateTimeFormat,
                dateTimeFormatName, javaShortDateFormat, javaAbbrevDateFormat, javaAbbrevDateFormatWeekday, javaLongDateFormat,
                javaLongDateFormatWeekday, javaTimeFormat, javaTimeFormatSeconds, unixShortDateFormat, unixAbbrevDateFormat,
                unixAbbrevDateFormatWeekday, unixLongDateFormat, unixLongDateFormatWeekday, unixTimeFormat, unixTimeFormatSeconds,
                shortDateSeparator, timeSeparator, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        dateTimeFormat = DateTimeFormatFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                dateTimeFormat.getPrimaryKey());
        dateTimeFormat.setActiveDetail(dateTimeFormatDetail);
        dateTimeFormat.setLastDetail(dateTimeFormatDetail);
        dateTimeFormat.store();
        
        sendEvent(dateTimeFormat.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return dateTimeFormat;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.DateTimeFormat */
    public DateTimeFormat getDateTimeFormatByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new DateTimeFormatPK(entityInstance.getEntityUniqueId());

        return DateTimeFormatFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public DateTimeFormat getDateTimeFormatByEntityInstance(EntityInstance entityInstance) {
        return getDateTimeFormatByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public DateTimeFormat getDateTimeFormatByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getDateTimeFormatByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countDateTimeFormats() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM datetimeformats, datetimeformatdetails " +
                "WHERE dtf_activedetailid = dtfdt_datetimeformatdetailid");
    }

    public List<DateTimeFormat> getDateTimeFormats() {
        List<DateTimeFormat> dateTimeFormats;
        
        try {
            var ps = DateTimeFormatFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM datetimeformats, datetimeformatdetails " +
                    "WHERE dtf_datetimeformatid = dtfdt_dtf_datetimeformatid AND dtfdt_thrutime = ? " +
                    "ORDER BY dtfdt_sortorder, dtfdt_datetimeformatname " +
                    "_LIMIT_");
            
            ps.setLong(1, Session.MAX_TIME);
            
            dateTimeFormats = DateTimeFormatFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return dateTimeFormats;
    }
    
    public DateTimeFormat getDefaultDateTimeFormat() {
        DateTimeFormat dateTimeFormat;
        
        try {
            var ps = DateTimeFormatFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM datetimeformats, datetimeformatdetails " +
                    "WHERE dtf_datetimeformatid = dtfdt_dtf_datetimeformatid AND dtfdt_isdefault = 1 AND dtfdt_thrutime = ?");
            
            ps.setLong(1, Session.MAX_TIME);
            
            dateTimeFormat = DateTimeFormatFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return dateTimeFormat;
    }
    
    public DateTimeFormat getDateTimeFormatByName(String dateTimeFormatName) {
        DateTimeFormat dateTimeFormat;
        
        try {
            var ps = DateTimeFormatFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM datetimeformats, datetimeformatdetails " +
                    "WHERE dtf_datetimeformatid = dtfdt_dtf_datetimeformatid AND dtfdt_datetimeformatname = ? AND dtfdt_thrutime = ?");
            
            ps.setString(1, dateTimeFormatName);
            ps.setLong(2, Session.MAX_TIME);
            
            dateTimeFormat = DateTimeFormatFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return dateTimeFormat;
    }
    
    public DateTimeFormatChoicesBean getDateTimeFormatChoices(String defaultDateTimeFormatChoice, Language language, boolean allowNullChoice) {
        var dateTimeFormats = getDateTimeFormats();
        var size = dateTimeFormats.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultDateTimeFormatChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var dateTimeFormat : dateTimeFormats) {
            var dateTimeFormatDetail = dateTimeFormat.getLastDetail();
            var value = dateTimeFormatDetail.getDateTimeFormatName();
            
            labels.add(getBestDateTimeFormatDescription(dateTimeFormat, language));
            values.add(value);
            
            var usingDefaultChoice = defaultDateTimeFormatChoice != null && defaultDateTimeFormatChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && dateTimeFormatDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new DateTimeFormatChoicesBean(labels, values, defaultValue);
    }
    
    public DateTimeFormatTransfer getDateTimeFormatTransfer(UserVisit userVisit, DateTimeFormat dateTimeFormat) {
        return getPartyTransferCaches().getDateTimeFormatTransferCache().getTransfer(userVisit, dateTimeFormat);
    }
    
    public List<DateTimeFormatTransfer> getDateTimeFormatTransfers(UserVisit userVisit, Collection<DateTimeFormat> dateTimeFormats) {
        List<DateTimeFormatTransfer> dateTimeFormatTransfers = new ArrayList<>(dateTimeFormats.size());
        var dateTimeFormatTransferCache = getPartyTransferCaches().getDateTimeFormatTransferCache();
        
        dateTimeFormats.forEach((dateTimeFormat) ->
                dateTimeFormatTransfers.add(dateTimeFormatTransferCache.getTransfer(userVisit, dateTimeFormat))
        );
        
        return dateTimeFormatTransfers;
    }
    
    public List<DateTimeFormatTransfer> getDateTimeFormatTransfers(UserVisit userVisit) {
        return getDateTimeFormatTransfers(userVisit, getDateTimeFormats());
    }
    
    // --------------------------------------------------------------------------------
    //   Date Time Format Descriptions
    // --------------------------------------------------------------------------------
    
    public DateTimeFormatDescription createDateTimeFormatDescription(DateTimeFormat dateTimeFormat, Language language,
            String description, BasePK createdBy) {
        var dateTimeFormatDescription = DateTimeFormatDescriptionFactory.getInstance().create(session,
                dateTimeFormat, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(dateTimeFormat.getPrimaryKey(), EventTypes.MODIFY, dateTimeFormatDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return dateTimeFormatDescription;
    }
    
    private List<DateTimeFormatDescription> getDateTimeFormatDescriptionsByDateTimeFormat(DateTimeFormat dateTimeFormat, EntityPermission entityPermission) {
        List<DateTimeFormatDescription> dateTimeFormatDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM datetimeformatdescriptions, languages " +
                        "WHERE dtfd_dtf_datetimeformatid = ? AND dtfd_thrutime = ? AND dtfd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM datetimeformatdescriptions " +
                        "WHERE dtfd_dtf_datetimeformatid = ? AND dtfd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = DateTimeFormatDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, dateTimeFormat.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            dateTimeFormatDescriptions = DateTimeFormatDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return dateTimeFormatDescriptions;
    }
    
    public List<DateTimeFormatDescription> getDateTimeFormatDescriptionsByDateTimeFormat(DateTimeFormat dateTimeFormat) {
        return getDateTimeFormatDescriptionsByDateTimeFormat(dateTimeFormat, EntityPermission.READ_ONLY);
    }
    
    public List<DateTimeFormatDescription> getDateTimeFormatDescriptionsByDateTimeFormatForUpdate(DateTimeFormat dateTimeFormat) {
        return getDateTimeFormatDescriptionsByDateTimeFormat(dateTimeFormat, EntityPermission.READ_WRITE);
    }
    
    private DateTimeFormatDescription getDateTimeFormatDescription(DateTimeFormat dateTimeFormat, Language language, EntityPermission entityPermission) {
        DateTimeFormatDescription dateTimeFormatDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM datetimeformatdescriptions " +
                        "WHERE dtfd_dtf_datetimeformatid = ? AND dtfd_lang_languageid = ? AND dtfd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM datetimeformatdescriptions " +
                        "WHERE dtfd_dtf_datetimeformatid = ? AND dtfd_lang_languageid = ? AND dtfd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = DateTimeFormatDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, dateTimeFormat.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            dateTimeFormatDescription = DateTimeFormatDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return dateTimeFormatDescription;
    }
    
    public DateTimeFormatDescription getDateTimeFormatDescription(DateTimeFormat dateTimeFormat, Language language) {
        return getDateTimeFormatDescription(dateTimeFormat, language, EntityPermission.READ_ONLY);
    }
    
    public DateTimeFormatDescription getDateTimeFormatDescriptionForUpdate(DateTimeFormat dateTimeFormat, Language language) {
        return getDateTimeFormatDescription(dateTimeFormat, language, EntityPermission.READ_WRITE);
    }
    
    public DateTimeFormatDescriptionValue getDateTimeFormatDescriptionValue(DateTimeFormatDescription dateTimeFormatDescription) {
        return dateTimeFormatDescription == null? null: dateTimeFormatDescription.getDateTimeFormatDescriptionValue().clone();
    }
    
    public DateTimeFormatDescriptionValue getDateTimeFormatDescriptionValueForUpdate(DateTimeFormat dateTimeFormat, Language language) {
        return getDateTimeFormatDescriptionValue(getDateTimeFormatDescriptionForUpdate(dateTimeFormat, language));
    }
    
    public String getBestDateTimeFormatDescription(DateTimeFormat dateTimeFormat, Language language) {
        String description;
        var dateTimeFormatDescription = getDateTimeFormatDescription(dateTimeFormat, language);
        
        if(dateTimeFormatDescription == null && !language.getIsDefault()) {
            dateTimeFormatDescription = getDateTimeFormatDescription(dateTimeFormat, partyControl.getDefaultLanguage());
        }
        
        if(dateTimeFormatDescription == null) {
            description = dateTimeFormat.getLastDetail().getDateTimeFormatName();
        } else {
            description = dateTimeFormatDescription.getDescription();
        }
        
        return description;
    }
    
    public DateTimeFormatDescriptionTransfer getDateTimeFormatDescriptionTransfer(UserVisit userVisit, DateTimeFormatDescription dateTimeFormatDescription) {
        return getPartyTransferCaches().getDateTimeFormatDescriptionTransferCache().getTransfer(userVisit, dateTimeFormatDescription);
    }
    
    public List<DateTimeFormatDescriptionTransfer> getDateTimeFormatDescriptionTransfers(UserVisit userVisit, DateTimeFormat dateTimeFormat) {
        var dateTimeFormatDescriptions = getDateTimeFormatDescriptionsByDateTimeFormat(dateTimeFormat);
        List<DateTimeFormatDescriptionTransfer> dateTimeFormatDescriptionTransfers = new ArrayList<>(dateTimeFormatDescriptions.size());
        var dateTimeFormatDescriptionTransferCache = getPartyTransferCaches().getDateTimeFormatDescriptionTransferCache();
        
        dateTimeFormatDescriptions.forEach((dateTimeFormatDescription) ->
                dateTimeFormatDescriptionTransfers.add(dateTimeFormatDescriptionTransferCache.getTransfer(userVisit, dateTimeFormatDescription))
        );
        
        return dateTimeFormatDescriptionTransfers;
    }
    
    public void updateDateTimeFormatDescriptionFromValue(DateTimeFormatDescriptionValue dateTimeFormatDescriptionValue, BasePK updatedBy) {
        if(dateTimeFormatDescriptionValue.hasBeenModified()) {
            var dateTimeFormatDescription = DateTimeFormatDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     dateTimeFormatDescriptionValue.getPrimaryKey());
            
            dateTimeFormatDescription.setThruTime(session.START_TIME_LONG);
            dateTimeFormatDescription.store();

            var dateTimeFormat = dateTimeFormatDescription.getDateTimeFormat();
            var language = dateTimeFormatDescription.getLanguage();
            var description = dateTimeFormatDescriptionValue.getDescription();
            
            dateTimeFormatDescription = DateTimeFormatDescriptionFactory.getInstance().create(dateTimeFormat, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(dateTimeFormat.getPrimaryKey(), EventTypes.MODIFY, dateTimeFormatDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteDateTimeFormatDescription(DateTimeFormatDescription dateTimeFormatDescription, BasePK deletedBy) {
        dateTimeFormatDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(dateTimeFormatDescription.getDateTimeFormatPK(), EventTypes.MODIFY, dateTimeFormatDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteDateTimeFormatDescriptionsByDateTimeFormat(DateTimeFormat dateTimeFormat, BasePK deletedBy) {
        var dateTimeFormatDescriptions = getDateTimeFormatDescriptionsByDateTimeFormatForUpdate(dateTimeFormat);
        
        dateTimeFormatDescriptions.forEach((dateTimeFormatDescription) -> 
                deleteDateTimeFormatDescription(dateTimeFormatDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Parties
    // --------------------------------------------------------------------------------
    
    public Party createParty(String partyName, PartyType partyType, Language preferredLanguage, Currency preferredCurrency,
            TimeZone preferredTimeZone, DateTimeFormat preferredDateTimeFormat, BasePK createdBy) {
        var party = PartyFactory.getInstance().create();
        
        if(createdBy == null) {
            createdBy = party.getPrimaryKey();
        }
        
        if(partyName == null) {
            var sequenceControl = Session.getModelController(SequenceControl.class);
            var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.PARTY.name());
            if(sequenceType != null) {
                var sequence = sequenceControl.getDefaultSequence(sequenceType);
                
                if(sequence != null) {
                    partyName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
                }
            }
        }
        
        if(partyName == null) {
            party.remove();
            party = null;
        } else {
            var partyDetail = PartyDetailFactory.getInstance().create(party, partyName, partyType, preferredLanguage,
                    preferredCurrency, preferredTimeZone, preferredDateTimeFormat, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            // Convert to R/W
            party = PartyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, party.getPrimaryKey());
            party.setActiveDetail(partyDetail);
            party.setLastDetail(partyDetail);
            
            sendEvent(party.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        }
        
        return party;
    }

    public long countParties() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM parties, partydetails " +
                "WHERE par_activedetailid = pardt_partydetailid");
    }

    public long countPartiesByPartyType(PartyType partyType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM parties, partydetails " +
                        "WHERE par_activedetailid = pardt_partydetailid AND pardt_ptyp_partytypeid = ?",
                partyType);
    }

    public long countPartiesByPartyTypeUsingNames(String partyTypeName) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partytypes, parties, partydetails " +
                "WHERE ptyp_partytypename = ? " +
                "AND par_activedetailid = pardt_partydetailid AND pardt_ptyp_partytypeid = ptyp_partytypeid",
                partyTypeName);
    }
    
    private static final Map<EntityPermission, String> getPartyByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM parties, partydetails " +
                "WHERE par_activedetailid = pardt_partydetailid AND pardt_partyname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM parties, partydetails " +
                "WHERE par_activedetailid = pardt_partydetailid AND pardt_partyname = ? " +
                "FOR UPDATE");
        getPartyByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public Party getPartyByName(String partyName, EntityPermission entityPermission) {
        return PartyFactory.getInstance().getEntityFromQuery(entityPermission, getPartyByNameQueries, partyName);
    }
    
    public Party getPartyByName(String partyName) {
        return getPartyByName(partyName, EntityPermission.READ_ONLY);
    }
    
    public Party getPartyByNameForUpdate(String partyName) {
        return getPartyByName(partyName, EntityPermission.READ_WRITE);
    }
    
    public PartyDetailValue getPartyDetailValueForUpdate(Party party) {
        return party == null? null: party.getLastDetailForUpdate().getPartyDetailValue().clone();
    }
    
    public PartyDetailValue getPartyDetailValueByNameForUpdate(String partyName) {
        return getPartyDetailValueForUpdate(getPartyByNameForUpdate(partyName));
    }
    
    public PartyDetailValue getPartyDetailValueByPKForUpdate(PartyPK partyPK) {
        var party = PartyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyPK);
        var partyDetail = party.getActiveDetailForUpdate();
        
        return partyDetail.getPartyDetailValue().clone();
    }
    
    private static final Map<EntityPermission, String> getPartyByAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyaliases, parties, partydetails " +
                "WHERE pal_pat_partyaliastypeid = ? AND pal_alias = ? AND pal_thrutime = ? " +
                "AND pal_par_partyid = par_partyid " +
                "AND par_activedetailid = pardt_partydetailid");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyaliases, parties, partydetails " +
                "WHERE pal_pat_partyaliastypeid = ? AND pal_alias = ? AND pal_thrutime = ? " +
                "AND pal_par_partyid = par_partyid " +
                "AND par_activedetailid = pardt_partydetailid " +
                "FOR UPDATE");
        getPartyByAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private Party getPartyByAlias(PartyAliasType partyAliasType, String alias, EntityPermission entityPermission) {
        return PartyFactory.getInstance().getEntityFromQuery(entityPermission, getPartyByAliasQueries, partyAliasType, alias, Session.MAX_TIME);
    }

    public Party getPartyByAlias(PartyAliasType partyAliasType, String alias) {
        return getPartyByAlias(partyAliasType, alias, EntityPermission.READ_ONLY);
    }

    public Party getPartyByAliasForUpdate(PartyAliasType partyAliasType, String alias) {
        return getPartyByAlias(partyAliasType, alias, EntityPermission.READ_WRITE);
    }

    public List<Party> getParties() {
        var ps = PartyFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM parties, partydetails " +
                "WHERE par_activedetailid = pardt_partydetailid " +
                "_LIMIT_");

        return PartyFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }

    public List<Party> getPartiesByPartyType(PartyType partyType) {
        List<Party> parties;

        try {
            var ps = PartyFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM parties, partydetails " +
                    "WHERE par_partyid = pardt_par_partyid AND pardt_ptyp_partytypeid = ? AND pardt_thrutime = ? " +
                    "_LIMIT_");

            ps.setLong(1, partyType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            parties = PartyFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return parties;
    }

    public Language getPreferredLanguage(Party party) {
        var language = party.getLastDetail().getPreferredLanguage();
        
        if(language == null) {
            language = getDefaultLanguage();
        }
        
        return language;
    }
    
    public Currency getPreferredCurrency(Party party) {
        var currency = party.getLastDetail().getPreferredCurrency();
        
        if(currency == null) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            
            currency = accountingControl.getDefaultCurrency();
        }
        
        return currency;
    }
    
    public TimeZone getPreferredTimeZone(Party party) {
        var timeZone = party.getLastDetail().getPreferredTimeZone();
        
        if(timeZone == null) {
            timeZone = getDefaultTimeZone();
        }
        
        return timeZone;
    }
    
    public DateTimeFormat getPreferredDateTimeFormat(Party party) {
        var dateTimeFormat = party.getLastDetail().getPreferredDateTimeFormat();
        
        if(dateTimeFormat == null) {
            dateTimeFormat = getDefaultDateTimeFormat();
        }
        
        return dateTimeFormat;
    }

    public PartyTransfer getPartyTransfer(UserVisit userVisit, Party party) {
        return getPartyTransferCaches().getPartyTransferCache().getTransfer(userVisit, party);
    }

    public List<PartyTransfer> getPartyTransfers(UserVisit userVisit, Collection<Party> parties) {
        var partyTransfers = new ArrayList<PartyTransfer>(parties.size());
        var partyTransferCache = getPartyTransferCaches().getPartyTransferCache();

        parties.forEach((party) ->
                partyTransfers.add(partyTransferCache.getTransfer(userVisit, party))
        );

        return partyTransfers;
    }

    public List<PartyTransfer> getPartyTransfers(UserVisit userVisit) {
        return getPartyTransfers(userVisit, getParties());
    }

    public void updatePartyFromValue(PartyDetailValue partyDetailValue, BasePK updatedBy) {
        if(partyDetailValue.hasBeenModified()) {
            var partyPK = partyDetailValue.getPartyPK();
            var party = PartyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyPK);
            var partyDetail = party.getActiveDetailForUpdate();
            
            partyDetail.setThruTime(session.START_TIME_LONG);
            partyDetail.store();

            var partyName = partyDetailValue.getPartyName();
            var partyTypePK = partyDetailValue.getPartyTypePK();
            var preferredLanguagePK = partyDetailValue.getPreferredLanguagePK();
            var preferredCurrencyPK = partyDetailValue.getPreferredCurrencyPK();
            var preferredTimeZonePK = partyDetailValue.getPreferredTimeZonePK();
            var preferredDateTimeFormatPK = partyDetailValue.getPreferredDateTimeFormatPK();
            
            partyDetail = PartyDetailFactory.getInstance().create(partyPK, partyName, partyTypePK, preferredLanguagePK,
                    preferredCurrencyPK, preferredTimeZonePK, preferredDateTimeFormatPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            party.setActiveDetail(partyDetail);
            party.setLastDetail(partyDetail);
            
            sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public Party getPartyByPK(PartyPK partyPK) {
        return PartyFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, partyPK);
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Party */
    public Party getPartyByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PartyPK(entityInstance.getEntityUniqueId());

        return PartyFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public Party getPartyByEntityInstance(EntityInstance entityInstance) {
        return getPartyByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Party getPartyByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public void deleteParty(Party party, BasePK deletedBy) {
        var partyApplicationEditorUseControl = Session.getModelController(PartyApplicationEditorUseControl.class);
        var contactListControl = Session.getModelController(ContactListControl.class);
        var documentControl = Session.getModelController(DocumentControl.class);
        var printerControl = Session.getModelController(PrinterControl.class);
        var scaleControl = Session.getModelController(ScaleControl.class);
        var searchControl = Session.getModelController(SearchControl.class);
        var securityControl = Session.getModelController(SecurityControl.class);
        var termControl = Session.getModelController(TermControl.class);
        var userControl = Session.getModelController(UserControl.class);
        var partyDetail = party.getLastDetailForUpdate();
        var partyType = partyDetail.getPartyType();
        var partyTypeName = partyType.getPartyTypeName();
        
        // TODO: Doesn't clean up all relationships
        partyApplicationEditorUseControl.deletePartyApplicationEditorUsesByParty(party, deletedBy);
        contactListControl.deletePartyContactListsByParty(party, deletedBy);
        documentControl.deletePartyDocumentsByParty(party, deletedBy);
        printerControl.deletePartyPrinterGroupUsesByParty(party, deletedBy);
        scaleControl.deletePartyScaleUsesByParty(party, deletedBy);
        searchControl.deletePartySearchTypePreferencesByParty(party, deletedBy);
        searchControl.deleteSearchesByParty(party, deletedBy);
        securityControl.deletePartyEntitySecurityRolesByParty(party, deletedBy);
        securityControl.deletePartySecurityRoleTemplateUseByParty(party, deletedBy);
        termControl.deletePartyCreditLimitsByParty(party, deletedBy);
        termControl.deletePartyTermByParty(party, deletedBy);
        
        if(partyType.getAllowUserLogins()) {
            userControl.deleteRecoveryAnswerByParty(party, deletedBy);
            userControl.deleteUserLoginByParty(party, deletedBy);
        }
        
        if(partyTypeName.equals(PartyTypes.COMPANY.name()) || partyTypeName.equals(PartyTypes.CUSTOMER.name())
                || partyTypeName.equals(PartyTypes.VENDOR.name())) {
            var carrierControl = Session.getModelController(CarrierControl.class);

            carrierControl.deletePartyCarriersByParty(party, deletedBy);
            carrierControl.deletePartyCarrierAccountsByParty(party, deletedBy);
        }

        if(partyTypeName.equals(PartyTypes.CUSTOMER.name()) || partyTypeName.equals(PartyTypes.VENDOR.name())) {
            var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
            var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);

            cancellationPolicyControl.deletePartyCancellationPoliciesByParty(party, deletedBy);
            returnPolicyControl.deletePartyReturnPoliciesByParty(party, deletedBy);
        }

        if(partyTypeName.equals(PartyTypes.CUSTOMER.name())) {
            var partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);

            partyPaymentMethodControl.deletePartyPaymentMethodsByParty(party, deletedBy);
        }
        
        if(partyTypeName.equals(PartyTypes.EMPLOYEE.name())) {
            var employeeControl = Session.getModelController(EmployeeControl.class);
            var trainingControl = Session.getModelController(TrainingControl.class);
            var workRequirementControl = Session.getModelController(WorkRequirementControl.class);

            employeeControl.deleteEmploymentsByParty(party, deletedBy);
            employeeControl.deleteLeavesByParty(party, deletedBy);
            employeeControl.deletePartyEmployeeByParty(party, deletedBy);
            employeeControl.deletePartyResponsibilityByParty(party, deletedBy);
            employeeControl.deletePartySkillByParty(party, deletedBy);
            
            trainingControl.deletePartyTrainingClassByParty(party, deletedBy);
            
            workRequirementControl.deleteWorkAssignmentsByParty(party, deletedBy);
            workRequirementControl.deleteWorkTimesByParty(party, deletedBy);
        }
        
        deletePartyRelationshipsByParty(party, deletedBy);
        deletePersonByParty(party, deletedBy);
        deletePartyGroupByParty(party, deletedBy);
        deleteProfileByParty(party, deletedBy);
        deletePartyAliasesByParty(party, deletedBy); 
        
        removePartyStatusByParty(party);

        partyDetail.setThruTime(session.START_TIME_LONG);
        partyDetail.store();
        party.setActiveDetail(null);
        
        sendEvent(party.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Descriptions
    // --------------------------------------------------------------------------------
    
    public String getBestPartyDescription(final Party party, final Language language) {
        var sb = new StringBuilder();

        if(party != null) {
            var partyDetail = party.getLastDetail();
            var originalPartyType = partyDetail.getPartyType();
            var partyType = originalPartyType;

            while(partyType.getParentPartyTypePK() != null) {
                partyType = partyType.getParentPartyType();
            }

            var partyTypeName = partyType.getPartyTypeName();
            sb.append(getBestPartyTypeDescription(originalPartyType, language));

            if(partyTypeName.equals(PartyTypes.UTILITY.name())) {
                // If its a UTILITY, description will be "partyTypeDescription.description, partyTypeName"
                sb.append(", ").append(partyDetail.getPartyName());
            } else {
                var person = getPerson(party);
                var partyGroup = getPartyGroup(party);
                var firstName = person == null? null: person.getFirstName();
                var lastName = person == null? null: person.getLastName();
                var name = partyGroup == null? null: partyGroup.getName();

                if((firstName != null || lastName != null || name != null)) {
                    sb.append(", ");

                    if(partyTypeName.equals(PartyTypes.PERSON.name())) {
                        // If its a PERSON, description will be "firstName lastName, name"
                        if(firstName != null) {
                            sb.append(firstName);
                        }

                        if(lastName != null) {
                            if(firstName != null) {
                                sb.append(' ');
                            }
                            sb.append(lastName);
                        }

                        if(name != null) {
                            if(firstName != null || lastName != null) {
                                sb.append(", ");
                            }

                            sb.append(name);
                        }
                    } else if(partyTypeName.equals(PartyTypes.GROUP.name()) || partyTypeName.equals(PartyTypes.FACILITY.name())) {
                        // If its a GROUP or FACILITY, description will be "name, firstName lastName"
                        if(name != null) {
                            sb.append(name);
                        }

                        if(firstName != null || lastName != null) {
                            if(name != null) {
                                sb.append(", ");
                            }

                            if(firstName != null) {
                                sb.append(firstName);
                            }

                            if(lastName != null) {
                                if(firstName != null) {
                                    sb.append(' ');
                                }
                                sb.append(lastName);
                            }
                        }
                    }
                }
            }
        }
        
        return sb.toString();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Statuses
    // --------------------------------------------------------------------------------

    public PartyStatus createPartyStatus(Party party) {
        return PartyStatusFactory.getInstance().create(party, 0);
    }

    private static final Map<EntityPermission, String> getPartyStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partystatuses " +
                "WHERE parst_par_partyid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partystatuses " +
                "WHERE parst_par_partyid = ? " +
                "FOR UPDATE");
        getPartyStatusQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyStatus getPartyStatus(Party party, EntityPermission entityPermission) {
        return PartyStatusFactory.getInstance().getEntityFromQuery(entityPermission, getPartyStatusQueries, party);
    }

    public PartyStatus getPartyStatus(Party party) {
        var partyStatus = getPartyStatus(party, EntityPermission.READ_ONLY);

        return partyStatus == null ? createPartyStatus(party) : partyStatus;
    }

    public PartyStatus getPartyStatusForUpdate(Party party) {
        var partyStatus = getPartyStatus(party, EntityPermission.READ_WRITE);

        return partyStatus == null
                ? PartyStatusFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, createPartyStatus(party).getPrimaryKey())
                : partyStatus;
    }

    public void removePartyStatusByParty(Party party) {
        var partyStatus = getPartyStatusForUpdate(party);

        if(partyStatus != null) {
            partyStatus.remove();
        }
    }

    // --------------------------------------------------------------------------------
    //   Party Aliases
    // --------------------------------------------------------------------------------

    public PartyAlias createPartyAlias(Party party, PartyAliasType partyAliasType, String alias, BasePK createdBy) {
        var partyAlias = PartyAliasFactory.getInstance().create(party, partyAliasType, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyAlias;
    }

    public long countPartyAliasesByParty(Party party) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partyaliases " +
                "WHERE pal_par_partyid = ? AND pal_thrutime = ?",
                party, Session.MAX_TIME_LONG);
    }

    public long countPartyAliasesByPartyAliasType(PartyAliasType partyAliasType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partyaliases " +
                "WHERE pal_pat_partyaliastypeid = ? AND pal_thrutime = ?",
                partyAliasType, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getPartyAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyaliases " +
                "WHERE pal_par_partyid = ? AND pal_pat_partyaliastypeid = ? AND pal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyaliases " +
                "WHERE pal_par_partyid = ? AND pal_pat_partyaliastypeid = ? AND pal_thrutime = ? " +
                "FOR UPDATE");
        getPartyAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyAlias getPartyAlias(Party party, PartyAliasType partyAliasType, EntityPermission entityPermission) {
        return PartyAliasFactory.getInstance().getEntityFromQuery(entityPermission, getPartyAliasQueries,
                party, partyAliasType, Session.MAX_TIME);
    }

    public PartyAlias getPartyAlias(Party party, PartyAliasType partyAliasType) {
        return getPartyAlias(party, partyAliasType, EntityPermission.READ_ONLY);
    }

    public PartyAlias getPartyAliasForUpdate(Party party, PartyAliasType partyAliasType) {
        return getPartyAlias(party, partyAliasType, EntityPermission.READ_WRITE);
    }

    public PartyAliasValue getPartyAliasValue(PartyAlias partyAlias) {
        return partyAlias == null? null: partyAlias.getPartyAliasValue().clone();
    }

    public PartyAliasValue getPartyAliasValueForUpdate(Party party, PartyAliasType partyAliasType) {
        return getPartyAliasValue(getPartyAliasForUpdate(party, partyAliasType));
    }

    private static final Map<EntityPermission, String> getPartyAliasByAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyaliases " +
                "WHERE pal_pat_partyaliastypeid = ? AND pal_alias = ? AND pal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyaliases " +
                "WHERE pal_pat_partyaliastypeid = ? AND pal_alias = ? AND pal_thrutime = ? " +
                "FOR UPDATE");
        getPartyAliasByAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyAlias getPartyAliasByAlias(PartyAliasType partyAliasType, String alias, EntityPermission entityPermission) {
        return PartyAliasFactory.getInstance().getEntityFromQuery(entityPermission, getPartyAliasByAliasQueries, partyAliasType, alias, Session.MAX_TIME);
    }

    public PartyAlias getPartyAliasByAlias(PartyAliasType partyAliasType, String alias) {
        return getPartyAliasByAlias(partyAliasType, alias, EntityPermission.READ_ONLY);
    }

    public PartyAlias getPartyAliasByAliasForUpdate(PartyAliasType partyAliasType, String alias) {
        return getPartyAliasByAlias(partyAliasType, alias, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyAliasesByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyaliases, partyaliastypes, partyaliastypedetails " +
                "WHERE pal_par_partyid = ? AND pal_thrutime = ? " +
                "AND pal_pat_partyaliastypeid = pat_partyaliastypeid AND pat_lastdetailid = patdt_partyaliastypedetailid " +
                "ORDER BY patdt_sortorder, patdt_partyaliastypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyaliases " +
                "WHERE pal_par_partyid = ? AND pal_thrutime = ? " +
                "FOR UPDATE");
        getPartyAliasesByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyAlias> getPartyAliasesByParty(Party party, EntityPermission entityPermission) {
        return PartyAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyAliasesByPartyQueries,
                party, Session.MAX_TIME);
    }

    public List<PartyAlias> getPartyAliasesByParty(Party party) {
        return getPartyAliasesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyAlias> getPartyAliasesByPartyForUpdate(Party party) {
        return getPartyAliasesByParty(party, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyAliasesByPartyAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyaliases, parties, partydetails " +
                "WHERE pal_pat_partyaliastypeid = ? AND pal_thrutime = ? " +
                "AND pal_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                "ORDER BY pardt_par_partyid " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyaliases " +
                "WHERE pal_pat_partyaliastypeid = ? AND pal_thrutime = ? " +
                "FOR UPDATE");
        getPartyAliasesByPartyAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyAlias> getPartyAliasesByPartyAliasType(PartyAliasType partyAliasType, EntityPermission entityPermission) {
        return PartyAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyAliasesByPartyAliasTypeQueries,
                partyAliasType, Session.MAX_TIME);
    }

    public List<PartyAlias> getPartyAliasesByPartyAliasType(PartyAliasType partyAliasType) {
        return getPartyAliasesByPartyAliasType(partyAliasType, EntityPermission.READ_ONLY);
    }

    public List<PartyAlias> getPartyAliasesByPartyAliasTypeForUpdate(PartyAliasType partyAliasType) {
        return getPartyAliasesByPartyAliasType(partyAliasType, EntityPermission.READ_WRITE);
    }

    public PartyAliasTransfer getPartyAliasTransfer(UserVisit userVisit, PartyAlias partyAlias) {
        return getPartyTransferCaches().getPartyAliasTransferCache().getTransfer(userVisit, partyAlias);
    }

    public List<PartyAliasTransfer> getPartyAliasTransfers(UserVisit userVisit, Collection<PartyAlias> partyaliases) {
        List<PartyAliasTransfer> partyAliasTransfers = new ArrayList<>(partyaliases.size());
        var partyAliasTransferCache = getPartyTransferCaches().getPartyAliasTransferCache();

        partyaliases.forEach((partyAlias) ->
                partyAliasTransfers.add(partyAliasTransferCache.getTransfer(userVisit, partyAlias))
        );

        return partyAliasTransfers;
    }

    public List<PartyAliasTransfer> getPartyAliasTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyAliasTransfers(userVisit, getPartyAliasesByParty(party));
    }

    public void updatePartyAliasFromValue(PartyAliasValue partyAliasValue, BasePK updatedBy) {
        if(partyAliasValue.hasBeenModified()) {
            var partyAlias = PartyAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyAliasValue.getPrimaryKey());

            partyAlias.setThruTime(session.START_TIME_LONG);
            partyAlias.store();

            var partyPK = partyAlias.getPartyPK();
            var partyAliasTypePK = partyAlias.getPartyAliasTypePK();
            var alias  = partyAliasValue.getAlias();

            partyAlias = PartyAliasFactory.getInstance().create(partyPK, partyAliasTypePK, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(partyPK, EventTypes.MODIFY, partyAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePartyAlias(PartyAlias partyAlias, BasePK deletedBy) {
        partyAlias.setThruTime(session.START_TIME_LONG);

        sendEvent(partyAlias.getPartyPK(), EventTypes.MODIFY, partyAlias.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePartyAliasesByPartyAliasType(PartyAliasType partyAliasType, BasePK deletedBy) {
        var partyaliases = getPartyAliasesByPartyAliasTypeForUpdate(partyAliasType);

        partyaliases.forEach((partyAlias) -> 
                deletePartyAlias(partyAlias, deletedBy)
        );
    }

    public void deletePartyAliasesByParty(Party party, BasePK deletedBy) {
        var partyaliases = getPartyAliasesByPartyForUpdate(party);

        partyaliases.forEach((partyAlias) -> 
                deletePartyAlias(partyAlias, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Party Relationship Types
    // --------------------------------------------------------------------------------
    
    public PartyRelationshipType createPartyRelationshipType(String partyRelationshipTypeName) {
        return PartyRelationshipTypeFactory.getInstance().create(partyRelationshipTypeName);
    }
    
    public PartyRelationshipType getPartyRelationshipTypeByName(String partyRelationshipTypeName) {
        PartyRelationshipType partyRelationshipType;
        
        try {
            var ps = PartyRelationshipTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM partyrelationshiptypes " +
                    "WHERE prt_partyrelationshiptypename = ?");
            
            ps.setString(1, partyRelationshipTypeName);
            
            partyRelationshipType = PartyRelationshipTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyRelationshipType;
    }
    
    // --------------------------------------------------------------------------------
    //   Party Relationship Type Descriptions
    // --------------------------------------------------------------------------------
    
    public PartyRelationshipTypeDescription createPartyRelationshipTypeDescription(PartyRelationshipType partyRelationshipType,
            Language language, String description) {
        return PartyRelationshipTypeDescriptionFactory.getInstance().create(partyRelationshipType, language, description);
    }
    
    public PartyRelationshipTypeDescription getPartyRelationshipTypeDescription(PartyRelationshipType partyRelationshipType,
            Language language) {
        PartyRelationshipTypeDescription partyRelationshipTypeDescription;
        
        try {
            var ps = PartyRelationshipTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM partyrelationshiptypedescriptions " +
                    "WHERE prtd_prt_partyrelationshiptypeid = ? AND prtd_lang_languageid = ?");
            
            ps.setLong(1, partyRelationshipType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            partyRelationshipTypeDescription = PartyRelationshipTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyRelationshipTypeDescription;
    }

    public String getBestPartyRelationshipTypeDescription(PartyRelationshipType partyRelationshipType, Language language) {
        var partyRelationshipTypeDescription = getPartyRelationshipTypeDescription(partyRelationshipType, language);
        String description;

        if(partyRelationshipTypeDescription == null && !language.getIsDefault()) {
            partyRelationshipTypeDescription = getPartyRelationshipTypeDescription(partyRelationshipType, partyControl.getDefaultLanguage());
        }

        if(partyRelationshipTypeDescription == null) {
            description = partyRelationshipType.getPartyRelationshipTypeName();
        } else {
            description = partyRelationshipTypeDescription.getDescription();
        }

        return description;
    }

    // --------------------------------------------------------------------------------
    //   Party Alias Types
    // --------------------------------------------------------------------------------

    public PartyAliasType createPartyAliasType(PartyType partyType, String partyAliasTypeName, String validationPattern, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultPartyAliasType = getDefaultPartyAliasType(partyType);
        var defaultFound = defaultPartyAliasType != null;

        if(defaultFound && isDefault) {
            var defaultPartyAliasTypeDetailValue = getDefaultPartyAliasTypeDetailValueForUpdate(partyType);

            defaultPartyAliasTypeDetailValue.setIsDefault(false);
            updatePartyAliasTypeFromValue(defaultPartyAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var partyAliasType = PartyAliasTypeFactory.getInstance().create();
        var partyAliasTypeDetail = PartyAliasTypeDetailFactory.getInstance().create(partyAliasType, partyType, partyAliasTypeName,
                validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        partyAliasType = PartyAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyAliasType.getPrimaryKey());
        partyAliasType.setActiveDetail(partyAliasTypeDetail);
        partyAliasType.setLastDetail(partyAliasTypeDetail);
        partyAliasType.store();

        sendEvent(partyAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return partyAliasType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PartyAliasType */
    public PartyAliasType getPartyAliasTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PartyAliasTypePK(entityInstance.getEntityUniqueId());

        return PartyAliasTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public PartyAliasType getPartyAliasTypeByEntityInstance(EntityInstance entityInstance) {
        return getPartyAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PartyAliasType getPartyAliasTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPartyAliasTypesByPartyType(PartyType partyType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partyaliastypes, partyaliastypedetails " +
                "WHERE pat_activedetailid = patdt_partyaliastypedetailid AND patdt_ptyp_partytypeid = ?",
                partyType);
    }

    private static final Map<EntityPermission, String> getPartyAliasTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyaliastypes, partyaliastypedetails " +
                "WHERE pat_activedetailid = patdt_partyaliastypedetailid AND patdt_ptyp_partytypeid = ? " +
                "AND patdt_partyaliastypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyaliastypes, partyaliastypedetails " +
                "WHERE pat_activedetailid = patdt_partyaliastypedetailid AND patdt_ptyp_partytypeid = ? " +
                "AND patdt_partyaliastypename = ? " +
                "FOR UPDATE");
        getPartyAliasTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public PartyAliasType getPartyAliasTypeByName(PartyType partyType, String partyAliasTypeName, EntityPermission entityPermission) {
        return PartyAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getPartyAliasTypeByNameQueries,
                partyType, partyAliasTypeName);
    }

    public PartyAliasType getPartyAliasTypeByName(PartyType partyType, String partyAliasTypeName) {
        return getPartyAliasTypeByName(partyType, partyAliasTypeName, EntityPermission.READ_ONLY);
    }

    public PartyAliasType getPartyAliasTypeByNameForUpdate(PartyType partyType, String partyAliasTypeName) {
        return getPartyAliasTypeByName(partyType, partyAliasTypeName, EntityPermission.READ_WRITE);
    }

    public PartyAliasTypeDetailValue getPartyAliasTypeDetailValueForUpdate(PartyAliasType partyAliasType) {
        return partyAliasType == null? null: partyAliasType.getLastDetailForUpdate().getPartyAliasTypeDetailValue().clone();
    }

    public PartyAliasTypeDetailValue getPartyAliasTypeDetailValueByNameForUpdate(PartyType partyType,
            String partyAliasTypeName) {
        return getPartyAliasTypeDetailValueForUpdate(getPartyAliasTypeByNameForUpdate(partyType, partyAliasTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultPartyAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyaliastypes, partyaliastypedetails " +
                "WHERE pat_activedetailid = patdt_partyaliastypedetailid AND patdt_ptyp_partytypeid = ? " +
                "AND patdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyaliastypes, partyaliastypedetails " +
                "WHERE pat_activedetailid = patdt_partyaliastypedetailid AND patdt_ptyp_partytypeid = ? " +
                "AND patdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultPartyAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public PartyAliasType getDefaultPartyAliasType(PartyType partyType, EntityPermission entityPermission) {
        return PartyAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPartyAliasTypeQueries, partyType);
    }

    public PartyAliasType getDefaultPartyAliasType(PartyType partyType) {
        return getDefaultPartyAliasType(partyType, EntityPermission.READ_ONLY);
    }

    public PartyAliasType getDefaultPartyAliasTypeForUpdate(PartyType partyType) {
        return getDefaultPartyAliasType(partyType, EntityPermission.READ_WRITE);
    }

    public PartyAliasTypeDetailValue getDefaultPartyAliasTypeDetailValueForUpdate(PartyType partyType) {
        return getDefaultPartyAliasTypeForUpdate(partyType).getLastDetailForUpdate().getPartyAliasTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getPartyAliasTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyaliastypes, partyaliastypedetails " +
                "WHERE pat_activedetailid = patdt_partyaliastypedetailid AND patdt_ptyp_partytypeid = ? " +
                "ORDER BY patdt_sortorder, patdt_partyaliastypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyaliastypes, partyaliastypedetails " +
                "WHERE pat_activedetailid = patdt_partyaliastypedetailid AND patdt_ptyp_partytypeid = ? " +
                "FOR UPDATE");
        getPartyAliasTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyAliasType> getPartyAliasTypes(PartyType partyType, EntityPermission entityPermission) {
        return PartyAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyAliasTypesQueries, partyType);
    }

    public List<PartyAliasType> getPartyAliasTypes(PartyType partyType) {
        return getPartyAliasTypes(partyType, EntityPermission.READ_ONLY);
    }

    public List<PartyAliasType> getPartyAliasTypesForUpdate(PartyType partyType) {
        return getPartyAliasTypes(partyType, EntityPermission.READ_WRITE);
    }

    public PartyAliasTypeTransfer getPartyAliasTypeTransfer(UserVisit userVisit, PartyAliasType partyAliasType) {
        return getPartyTransferCaches().getPartyAliasTypeTransferCache().getTransfer(userVisit, partyAliasType);
    }

    public List<PartyAliasTypeTransfer> getPartyAliasTypeTransfers(UserVisit userVisit, Collection<PartyAliasType> partyAliasTypes) {
        List<PartyAliasTypeTransfer> partyAliasTypeTransfers = new ArrayList<>(partyAliasTypes.size());
        var partyAliasTypeTransferCache = getPartyTransferCaches().getPartyAliasTypeTransferCache();

        partyAliasTypes.forEach((partyAliasType) ->
                partyAliasTypeTransfers.add(partyAliasTypeTransferCache.getTransfer(userVisit, partyAliasType))
        );

        return partyAliasTypeTransfers;
    }

    public List<PartyAliasTypeTransfer> getPartyAliasTypeTransfers(UserVisit userVisit, PartyType partyType) {
        return getPartyAliasTypeTransfers(userVisit, getPartyAliasTypes(partyType));
    }

    public PartyAliasTypeChoicesBean getPartyAliasTypeChoices(String defaultPartyAliasTypeChoice, Language language,
            boolean allowNullChoice, PartyType partyType) {
        var partyAliasTypes = getPartyAliasTypes(partyType);
        var size = partyAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPartyAliasTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var partyAliasType : partyAliasTypes) {
            var partyAliasTypeDetail = partyAliasType.getLastDetail();

            var label = getBestPartyAliasTypeDescription(partyAliasType, language);
            var value = partyAliasTypeDetail.getPartyAliasTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultPartyAliasTypeChoice != null && defaultPartyAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partyAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PartyAliasTypeChoicesBean(labels, values, defaultValue);
    }

    private void updatePartyAliasTypeFromValue(PartyAliasTypeDetailValue partyAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(partyAliasTypeDetailValue.hasBeenModified()) {
            var partyAliasType = PartyAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    partyAliasTypeDetailValue.getPartyAliasTypePK());
            var partyAliasTypeDetail = partyAliasType.getActiveDetailForUpdate();

            partyAliasTypeDetail.setThruTime(session.START_TIME_LONG);
            partyAliasTypeDetail.store();

            var partyAliasTypePK = partyAliasTypeDetail.getPartyAliasTypePK();
            var partyType = partyAliasTypeDetail.getPartyType();
            var partyTypePK = partyType.getPrimaryKey();
            var partyAliasTypeName = partyAliasTypeDetailValue.getPartyAliasTypeName();
            var validationPattern = partyAliasTypeDetailValue.getValidationPattern();
            var isDefault = partyAliasTypeDetailValue.getIsDefault();
            var sortOrder = partyAliasTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPartyAliasType = getDefaultPartyAliasType(partyType);
                var defaultFound = defaultPartyAliasType != null && !defaultPartyAliasType.equals(partyAliasType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPartyAliasTypeDetailValue = getDefaultPartyAliasTypeDetailValueForUpdate(partyType);

                    defaultPartyAliasTypeDetailValue.setIsDefault(false);
                    updatePartyAliasTypeFromValue(defaultPartyAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            partyAliasTypeDetail = PartyAliasTypeDetailFactory.getInstance().create(partyAliasTypePK, partyTypePK, partyAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            partyAliasType.setActiveDetail(partyAliasTypeDetail);
            partyAliasType.setLastDetail(partyAliasTypeDetail);

            sendEvent(partyAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePartyAliasTypeFromValue(PartyAliasTypeDetailValue partyAliasTypeDetailValue, BasePK updatedBy) {
        updatePartyAliasTypeFromValue(partyAliasTypeDetailValue, true, updatedBy);
    }

    public void deletePartyAliasType(PartyAliasType partyAliasType, BasePK deletedBy) {
        deletePartyAliasesByPartyAliasType(partyAliasType, deletedBy);
        deletePartyAliasTypeDescriptionsByPartyAliasType(partyAliasType, deletedBy);

        var partyAliasTypeDetail = partyAliasType.getLastDetailForUpdate();
        partyAliasTypeDetail.setThruTime(session.START_TIME_LONG);
        partyAliasType.setActiveDetail(null);
        partyAliasType.store();

        // Check for default, and pick one if necessary
        var partyType = partyAliasTypeDetail.getPartyType();
        var defaultPartyAliasType = getDefaultPartyAliasType(partyType);
        if(defaultPartyAliasType == null) {
            var partyAliasTypes = getPartyAliasTypesForUpdate(partyType);

            if(!partyAliasTypes.isEmpty()) {
                var iter = partyAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultPartyAliasType = iter.next();
                }
                var partyAliasTypeDetailValue = Objects.requireNonNull(defaultPartyAliasType).getLastDetailForUpdate().getPartyAliasTypeDetailValue().clone();

                partyAliasTypeDetailValue.setIsDefault(true);
                updatePartyAliasTypeFromValue(partyAliasTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(partyAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deletePartyAliasTypes(List<PartyAliasType> partyAliasTypes, BasePK deletedBy) {
        partyAliasTypes.forEach((partyAliasType) -> 
                deletePartyAliasType(partyAliasType, deletedBy)
        );
    }

    public void deletePartyAliasTypesByPartyType(PartyType partyType, BasePK deletedBy) {
        deletePartyAliasTypes(getPartyAliasTypesForUpdate(partyType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Party Alias Type Descriptions
    // --------------------------------------------------------------------------------

    public PartyAliasTypeDescription createPartyAliasTypeDescription(PartyAliasType partyAliasType, Language language, String description, BasePK createdBy) {
        var partyAliasTypeDescription = PartyAliasTypeDescriptionFactory.getInstance().create(partyAliasType, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(partyAliasType.getPrimaryKey(), EventTypes.MODIFY, partyAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyAliasTypeDescription;
    }

    private static final Map<EntityPermission, String> getPartyAliasTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyaliastypedescriptions " +
                "WHERE patd_pat_partyaliastypeid = ? AND patd_lang_languageid = ? AND patd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyaliastypedescriptions " +
                "WHERE patd_pat_partyaliastypeid = ? AND patd_lang_languageid = ? AND patd_thrutime = ? " +
                "FOR UPDATE");
        getPartyAliasTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyAliasTypeDescription getPartyAliasTypeDescription(PartyAliasType partyAliasType, Language language, EntityPermission entityPermission) {
        return PartyAliasTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getPartyAliasTypeDescriptionQueries,
                partyAliasType, language, Session.MAX_TIME);
    }

    public PartyAliasTypeDescription getPartyAliasTypeDescription(PartyAliasType partyAliasType, Language language) {
        return getPartyAliasTypeDescription(partyAliasType, language, EntityPermission.READ_ONLY);
    }

    public PartyAliasTypeDescription getPartyAliasTypeDescriptionForUpdate(PartyAliasType partyAliasType, Language language) {
        return getPartyAliasTypeDescription(partyAliasType, language, EntityPermission.READ_WRITE);
    }

    public PartyAliasTypeDescriptionValue getPartyAliasTypeDescriptionValue(PartyAliasTypeDescription partyAliasTypeDescription) {
        return partyAliasTypeDescription == null? null: partyAliasTypeDescription.getPartyAliasTypeDescriptionValue().clone();
    }

    public PartyAliasTypeDescriptionValue getPartyAliasTypeDescriptionValueForUpdate(PartyAliasType partyAliasType, Language language) {
        return getPartyAliasTypeDescriptionValue(getPartyAliasTypeDescriptionForUpdate(partyAliasType, language));
    }

    private static final Map<EntityPermission, String> getPartyAliasTypeDescriptionsByPartyAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyaliastypedescriptions, languages " +
                "WHERE patd_pat_partyaliastypeid = ? AND patd_thrutime = ? AND patd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyaliastypedescriptions " +
                "WHERE patd_pat_partyaliastypeid = ? AND patd_thrutime = ? " +
                "FOR UPDATE");
        getPartyAliasTypeDescriptionsByPartyAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyAliasTypeDescription> getPartyAliasTypeDescriptionsByPartyAliasType(PartyAliasType partyAliasType, EntityPermission entityPermission) {
        return PartyAliasTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyAliasTypeDescriptionsByPartyAliasTypeQueries,
                partyAliasType, Session.MAX_TIME);
    }

    public List<PartyAliasTypeDescription> getPartyAliasTypeDescriptionsByPartyAliasType(PartyAliasType partyAliasType) {
        return getPartyAliasTypeDescriptionsByPartyAliasType(partyAliasType, EntityPermission.READ_ONLY);
    }

    public List<PartyAliasTypeDescription> getPartyAliasTypeDescriptionsByPartyAliasTypeForUpdate(PartyAliasType partyAliasType) {
        return getPartyAliasTypeDescriptionsByPartyAliasType(partyAliasType, EntityPermission.READ_WRITE);
    }

    public String getBestPartyAliasTypeDescription(PartyAliasType partyAliasType, Language language) {
        String description;
        var partyAliasTypeDescription = getPartyAliasTypeDescription(partyAliasType, language);

        if(partyAliasTypeDescription == null && !language.getIsDefault()) {
            partyAliasTypeDescription = getPartyAliasTypeDescription(partyAliasType, partyControl.getDefaultLanguage());
        }

        if(partyAliasTypeDescription == null) {
            description = partyAliasType.getLastDetail().getPartyAliasTypeName();
        } else {
            description = partyAliasTypeDescription.getDescription();
        }

        return description;
    }

    public PartyAliasTypeDescriptionTransfer getPartyAliasTypeDescriptionTransfer(UserVisit userVisit, PartyAliasTypeDescription partyAliasTypeDescription) {
        return getPartyTransferCaches().getPartyAliasTypeDescriptionTransferCache().getTransfer(userVisit, partyAliasTypeDescription);
    }

    public List<PartyAliasTypeDescriptionTransfer> getPartyAliasTypeDescriptionTransfersByPartyAliasType(UserVisit userVisit, PartyAliasType partyAliasType) {
        var partyAliasTypeDescriptions = getPartyAliasTypeDescriptionsByPartyAliasType(partyAliasType);
        List<PartyAliasTypeDescriptionTransfer> partyAliasTypeDescriptionTransfers = new ArrayList<>(partyAliasTypeDescriptions.size());
        var partyAliasTypeDescriptionTransferCache = getPartyTransferCaches().getPartyAliasTypeDescriptionTransferCache();

        partyAliasTypeDescriptions.forEach((partyAliasTypeDescription) ->
                partyAliasTypeDescriptionTransfers.add(partyAliasTypeDescriptionTransferCache.getTransfer(userVisit, partyAliasTypeDescription))
        );

        return partyAliasTypeDescriptionTransfers;
    }

    public void updatePartyAliasTypeDescriptionFromValue(PartyAliasTypeDescriptionValue partyAliasTypeDescriptionValue, BasePK updatedBy) {
        if(partyAliasTypeDescriptionValue.hasBeenModified()) {
            var partyAliasTypeDescription = PartyAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyAliasTypeDescriptionValue.getPrimaryKey());

            partyAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            partyAliasTypeDescription.store();

            var partyAliasType = partyAliasTypeDescription.getPartyAliasType();
            var language = partyAliasTypeDescription.getLanguage();
            var description = partyAliasTypeDescriptionValue.getDescription();

            partyAliasTypeDescription = PartyAliasTypeDescriptionFactory.getInstance().create(partyAliasType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(partyAliasType.getPrimaryKey(), EventTypes.MODIFY, partyAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePartyAliasTypeDescription(PartyAliasTypeDescription partyAliasTypeDescription, BasePK deletedBy) {
        partyAliasTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(partyAliasTypeDescription.getPartyAliasTypePK(), EventTypes.MODIFY, partyAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePartyAliasTypeDescriptionsByPartyAliasType(PartyAliasType partyAliasType, BasePK deletedBy) {
        var partyAliasTypeDescriptions = getPartyAliasTypeDescriptionsByPartyAliasTypeForUpdate(partyAliasType);

        partyAliasTypeDescriptions.forEach((partyAliasTypeDescription) -> 
                deletePartyAliasTypeDescription(partyAliasTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Role Types
    // --------------------------------------------------------------------------------
    
    public RoleType createRoleType(String roleTypeName, RoleType parentRoleType) {
        return RoleTypeFactory.getInstance().create(roleTypeName, parentRoleType);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.RoleType */
    public RoleType getRoleTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new RoleTypePK(entityInstance.getEntityUniqueId());

        return RoleTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public RoleType getRoleTypeByEntityInstance(EntityInstance entityInstance) {
        return getRoleTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public RoleType getRoleTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getRoleTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countRoleTypes() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM roletypes
                """);
    }

    private static final Map<EntityPermission, String> getRoleTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM roletypes " +
                        "WHERE rtyp_roletypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM roletypes " +
                        "WHERE rtyp_roletypename = ? " +
                        "FOR UPDATE");
        getRoleTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public RoleType getRoleTypeByName(String roleTypeName, EntityPermission entityPermission) {
        return RoleTypeFactory.getInstance().getEntityFromQuery(entityPermission, getRoleTypeByNameQueries,
                roleTypeName);
    }

    public RoleType getRoleTypeByName(String roleTypeName) {
        return getRoleTypeByName(roleTypeName, EntityPermission.READ_ONLY);
    }

    public RoleType getRoleTypeByNameForUpdate(String roleTypeName) {
        return getRoleTypeByName(roleTypeName, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getRoleTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM roletypes " +
                        "ORDER BY rtyp_roletypename " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM roletypes " +
                        "FOR UPDATE");
        getRoleTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<RoleType> getRoleTypes(EntityPermission entityPermission) {
        return RoleTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getRoleTypesQueries);
    }

    public List<RoleType> getRoleTypes() {
        return getRoleTypes(EntityPermission.READ_ONLY);
    }

    public List<RoleType> getRoleTypesForUpdate() {
        return getRoleTypes(EntityPermission.READ_WRITE);
    }

    public RoleTypeTransfer getRoleTypeTransfer(UserVisit userVisit, RoleType roleType) {
        return getPartyTransferCaches().getRoleTypeTransferCache().getTransfer(userVisit, roleType);
    }

    public List<RoleTypeTransfer> getRoleTypeTransfers(UserVisit userVisit, Collection<RoleType> roleTypes) {
        List<RoleTypeTransfer> roleTypeTransfers = new ArrayList<>(roleTypes.size());
        var roleTypeTransferCache = getPartyTransferCaches().getRoleTypeTransferCache();

        roleTypes.forEach((roleType) ->
                roleTypeTransfers.add(roleTypeTransferCache.getTransfer(userVisit, roleType))
        );

        return roleTypeTransfers;
    }

    public List<RoleTypeTransfer> getRoleTypeTransfers(UserVisit userVisit) {
        return getRoleTypeTransfers(userVisit, getRoleTypes());
    }

    // --------------------------------------------------------------------------------
    //   Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    public RoleTypeDescription createRoleTypeDescription(RoleType roleType, Language language, String description) {
        return RoleTypeDescriptionFactory.getInstance().create(roleType, language, description);
    }
    
    public RoleTypeDescription getRoleTypeDescription(RoleType roleType, Language language) {
        RoleTypeDescription roleTypeDescription;
        
        try {
            var ps = RoleTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM roletypedescriptions " +
                    "WHERE rtypd_rtyp_roletypeid = ? AND rtypd_lang_languageid = ?");
            
            ps.setLong(1, roleType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            roleTypeDescription = RoleTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return roleTypeDescription;
    }
    
    public String getBestRoleTypeDescription(RoleType roleType, Language language) {
        String description;
        var roleTypeDescription = getRoleTypeDescription(roleType, language);
        
        if(roleTypeDescription == null && !language.getIsDefault()) {
            roleTypeDescription = getRoleTypeDescription(roleType, partyControl.getDefaultLanguage());
        }
        
        if(roleTypeDescription == null) {
            description = roleType.getRoleTypeName();
        } else {
            description = roleTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Party Groups
    // --------------------------------------------------------------------------------
    
    public PartyGroup createPartyGroup(Party party, String name, BasePK createdBy) {
        var partyGroup = PartyGroupFactory.getInstance().create(party, name, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyGroup.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyGroup;
    }
    
    private PartyGroup getPartyGroup(Party party, EntityPermission entityPermission) {
        PartyGroup partyGroup;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partygroups " +
                        "WHERE pgp_par_partyid = ? AND pgp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partygroups " +
                        "WHERE pgp_par_partyid = ? AND pgp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyGroup = PartyGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyGroup;
    }
    
    public PartyGroup getPartyGroup(Party party) {
        return getPartyGroup(party, EntityPermission.READ_ONLY);
    }
    
    public PartyGroup getPartyGroupForUpdate(Party party) {
        return getPartyGroup(party, EntityPermission.READ_WRITE);
    }
    
    public PartyGroupValue getPartyGroupValue(PartyGroup partyGroup) {
        return partyGroup == null? null: partyGroup.getPartyGroupValue().clone();
    }
    
    public PartyGroupValue getPartyGroupValueForUpdate(Party party) {
        return getPartyGroupValue(getPartyGroupForUpdate(party));
    }
    
    public void updatePartyGroupFromValue(PartyGroupValue partyGroupValue, BasePK updatedBy) {
        if(partyGroupValue.hasBeenModified()) {
            var partyGroup = PartyGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    partyGroupValue.getPrimaryKey());
            
            partyGroup.setThruTime(session.START_TIME_LONG);
            partyGroup.store();

            var partyPK = partyGroup.getPartyPK();
            var name = partyGroupValue.getName();
            
            partyGroup = PartyGroupFactory.getInstance().create(partyPK, name, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(partyPK, EventTypes.MODIFY, partyGroup.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyGroup(PartyGroup partyGroup, BasePK deletedBy) {
        partyGroup.setThruTime(session.START_TIME_LONG);
        partyGroup.store();
        
        sendEvent(partyGroup.getPartyPK(), EventTypes.MODIFY, partyGroup.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyGroupByParty(Party party, BasePK deletedBy) {
        var partyGroup = getPartyGroupForUpdate(party);
        
        if(partyGroup != null) {
            deletePartyGroup(partyGroup, deletedBy);
        }
    }
    
    public PartyGroupTransfer getPartyGroupTransfer(UserVisit userVisit, PartyGroup partyGroup) {
        return getPartyTransferCaches().getPartyGroupTransferCache().getTransfer(userVisit, partyGroup);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Companies
    // --------------------------------------------------------------------------------
    
    public PartyCompany createPartyCompany(Party party, String partyCompanyName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultPartyCompany = getDefaultPartyCompany();
        var defaultFound = defaultPartyCompany != null;
        
        if(defaultFound && isDefault) {
            var defaultPartyCompanyValue = getDefaultPartyCompanyValueForUpdate();
            
            defaultPartyCompanyValue.setIsDefault(false);
            updatePartyCompanyFromValue(defaultPartyCompanyValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var partyCompany = PartyCompanyFactory.getInstance().create(party, partyCompanyName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyCompany.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyCompany;
    }

    public long countPartyCompanies() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM partycompanies " +
                        "WHERE pcomp_thrutime = ?",
                Session.MAX_TIME);
    }

    public PartyCompanyValue getPartyCompanyValueForUpdate(PartyCompany partyCompany) {
        return partyCompany == null? null: partyCompany.getPartyCompanyValue().clone();
    }
    
    private PartyCompany getPartyCompany(Party party, EntityPermission entityPermission) {
        PartyCompany partyCompany;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycompanies " +
                        "WHERE pcomp_par_partyid = ? AND pcomp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycompanies " +
                        "WHERE pcomp_par_partyid = ? AND pcomp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyCompanyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyCompany = PartyCompanyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyCompany;
    }
    
    public PartyCompany getPartyCompany(Party party) {
        return getPartyCompany(party, EntityPermission.READ_ONLY);
    }
    
    public PartyCompany getPartyCompanyForUpdate(Party party) {
        return getPartyCompany(party, EntityPermission.READ_WRITE);
    }
    
    public PartyCompanyValue getPartyCompanyValueForUpdate(Party party) {
        return getPartyCompanyValueForUpdate(getPartyCompanyForUpdate(party));
    }
    
    private PartyCompany getDefaultPartyCompany(EntityPermission entityPermission) {
        PartyCompany partyCompany;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycompanies, partydetails " +
                        "WHERE pcomp_isdefault = 1 AND pcomp_thrutime = ? AND pcomp_par_partyid = pardt_par_partyid " +
                        "AND pardt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycompanies, partydetails " +
                        "WHERE pcomp_isdefault = 1 AND pcomp_thrutime = ? AND pcomp_par_partyid = pardt_par_partyid " +
                        "AND pardt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyCompanyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            ps.setLong(2, Session.MAX_TIME);
            
            partyCompany = PartyCompanyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyCompany;
    }
    
    public PartyCompany getDefaultPartyCompany() {
        return getDefaultPartyCompany(EntityPermission.READ_ONLY);
    }
    
    public PartyCompany getDefaultPartyCompanyForUpdate() {
        return getDefaultPartyCompany(EntityPermission.READ_WRITE);
    }
    
    public PartyCompanyValue getDefaultPartyCompanyValueForUpdate() {
        return getPartyCompanyValueForUpdate(getDefaultPartyCompanyForUpdate());
    }
    
    private PartyCompany getPartyCompanyByName(String partyCompanyName, EntityPermission entityPermission) {
        PartyCompany partyCompany;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycompanies, partydetails " +
                        "WHERE pcomp_partycompanyname = ? AND pcomp_thrutime = ? AND pcomp_par_partyid = pardt_par_partyid " +
                        "AND pardt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycompanies, partydetails " +
                        "WHERE pcomp_partycompanyname = ? AND pcomp_thrutime = ? AND pcomp_par_partyid = pardt_par_partyid " +
                        "AND pardt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyCompanyFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, partyCompanyName);
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, Session.MAX_TIME);
            
            partyCompany = PartyCompanyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyCompany;
    }
    
    public PartyCompany getPartyCompanyByName(String partyCompanyName) {
        return getPartyCompanyByName(partyCompanyName, EntityPermission.READ_ONLY);
    }
    
    public PartyCompany getPartyCompanyByNameForUpdate(String partyCompanyName) {
        return getPartyCompanyByName(partyCompanyName, EntityPermission.READ_WRITE);
    }
    
    public PartyCompanyValue getPartyCompanyValueByNameForUpdate(String partyCompanyName) {
        return getPartyCompanyValueForUpdate(getPartyCompanyByNameForUpdate(partyCompanyName));
    }
    
    private List<PartyCompany> getCompanies(EntityPermission entityPermission) {
        List<PartyCompany> partyCompanies;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycompanies, partydetails " +
                        "WHERE pcomp_thrutime = ? AND pcomp_par_partyid = pardt_par_partyid AND pardt_thrutime = ? " +
                        "ORDER BY pcomp_sortorder, pcomp_partycompanyname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycompanies, partydetails " +
                        "WHERE pcomp_thrutime = ? AND pcomp_par_partyid = pardt_par_partyid AND pardt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyCompanyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            ps.setLong(2, Session.MAX_TIME);
            
            partyCompanies = PartyCompanyFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyCompanies;
    }
    
    public List<PartyCompany> getCompanies() {
        return getCompanies(EntityPermission.READ_ONLY);
    }
    
    public List<PartyCompany> getCompaniesForUpdate() {
        return getCompanies(EntityPermission.READ_WRITE);
    }
    
    public CompanyChoicesBean getCompanyChoices(String defaultCompanyChoice, boolean allowNullChoice) {
        var partyCompanies = getCompanies();
        var size = partyCompanies.size() + (allowNullChoice ? 1 : 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCompanyChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var partyCompany : partyCompanies) {
            var partyGroup = getPartyGroup(partyCompany.getParty());
            
            var label = partyGroup.getName();
            var value = partyCompany.getPartyCompanyName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultCompanyChoice != null && defaultCompanyChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partyCompany.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CompanyChoicesBean(labels, values, defaultValue);
    }
    
    public List<CompanyTransfer> getCompanyTransfers(UserVisit userVisit, Collection<PartyCompany> partyCompanies) {
        List<CompanyTransfer> companyTransfers = new ArrayList<>(partyCompanies.size());
        var companyTransferCache = getPartyTransferCaches().getCompanyTransferCache();
        
        partyCompanies.forEach((partyCompany) ->
                companyTransfers.add(companyTransferCache.getTransfer(userVisit, partyCompany))
        );
        
        return companyTransfers;
    }
    
    public List<CompanyTransfer> getCompanyTransfers(UserVisit userVisit) {
        return getCompanyTransfers(userVisit, getCompanies());
    }
    
    public CompanyTransfer getCompanyTransfer(UserVisit userVisit, PartyCompany partyCompany) {
        return getPartyTransferCaches().getCompanyTransferCache().getTransfer(userVisit, partyCompany);
    }
    
    public CompanyTransfer getCompanyTransfer(UserVisit userVisit, Party party) {
        return getCompanyTransfer(userVisit, getPartyCompany(party));
    }
    
    private void updatePartyCompanyFromValue(PartyCompanyValue partyCompanyValue, boolean checkDefault, BasePK updatedBy) {
        if(partyCompanyValue.hasBeenModified()) {
            var partyCompany = PartyCompanyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyCompanyValue.getPrimaryKey());
            
            partyCompany.setThruTime(session.START_TIME_LONG);
            partyCompany.store();

            var partyPK = partyCompanyValue.getPartyPK();
            var partyCompanyName = partyCompanyValue.getPartyCompanyName();
            var isDefault = partyCompanyValue.getIsDefault();
            var sortOrder = partyCompanyValue.getSortOrder();
            
            if(checkDefault) {
                var defaultPartyCompany = getDefaultPartyCompany();
                var defaultFound = defaultPartyCompany != null && !defaultPartyCompany.equals(partyCompany);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPartyCompanyValue = getDefaultPartyCompanyValueForUpdate();
                    
                    defaultPartyCompanyValue.setIsDefault(false);
                    updatePartyCompanyFromValue(defaultPartyCompanyValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            partyCompany = PartyCompanyFactory.getInstance().create(partyPK, partyCompanyName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(partyPK, EventTypes.MODIFY, partyCompany.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updatePartyCompanyFromValue(PartyCompanyValue partyCompanyValue, BasePK updatedBy) {
        updatePartyCompanyFromValue(partyCompanyValue, true, updatedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Divisions
    // --------------------------------------------------------------------------------
    
    public PartyDivision createPartyDivision(Party party, Party companyParty, String partyDivisionName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultPartyDivision = getDefaultPartyDivision(companyParty);
        var defaultFound = defaultPartyDivision != null;
        
        if(defaultFound && isDefault) {
            var defaultPartyDivisionValue = getDefaultPartyDivisionValueForUpdate(companyParty);
            
            defaultPartyDivisionValue.setIsDefault(false);
            updatePartyDivisionFromValue(defaultPartyDivisionValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var partyDivision = PartyDivisionFactory.getInstance().create(party, companyParty, partyDivisionName,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyDivision.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyDivision;
    }

    public long countPartyDivisions(Party companyParty) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partydivisions " +
                "WHERE pdiv_companypartyid = ? AND pdiv_thrutime = ?",
                companyParty, Session.MAX_TIME);
    }

    public PartyDivisionValue getPartyDivisionValueForUpdate(PartyDivision partyDivision) {
        return partyDivision == null? null: partyDivision.getPartyDivisionValue().clone();
    }
    
    private PartyDivision getPartyDivision(Party party, EntityPermission entityPermission) {
        PartyDivision partyDivision;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partydivisions " +
                        "WHERE pdiv_par_partyid = ? AND pdiv_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partydivisions " +
                        "WHERE pdiv_par_partyid = ? AND pdiv_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyDivisionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyDivision = PartyDivisionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyDivision;
    }
    
    public PartyDivision getPartyDivision(Party party) {
        return getPartyDivision(party, EntityPermission.READ_ONLY);
    }
    
    public PartyDivision getPartyDivisionForUpdate(Party party) {
        return getPartyDivision(party, EntityPermission.READ_WRITE);
    }
    
    public PartyDivisionValue getPartyDivisionValueForUpdate(Party party) {
        return getPartyDivisionValueForUpdate(getPartyDivisionForUpdate(party));
    }
    
    private PartyDivision getDefaultPartyDivision(Party companyParty, EntityPermission entityPermission) {
        PartyDivision partyDivision;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partydivisions, partydetails " +
                        "WHERE pdiv_companypartyid = ? AND pdiv_isdefault = 1 AND pdiv_thrutime = ? " +
                        "AND pdiv_par_partyid = pardt_par_partyid AND pardt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partydivisions, partydetails " +
                        "WHERE pdiv_companypartyid = ? AND pdiv_isdefault = 1 AND pdiv_thrutime = ? " +
                        "AND pdiv_par_partyid = pardt_par_partyid AND pardt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyDivisionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, companyParty.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, Session.MAX_TIME);
            
            partyDivision = PartyDivisionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyDivision;
    }
    
    public PartyDivision getDefaultPartyDivision(Party companyParty) {
        return getDefaultPartyDivision(companyParty, EntityPermission.READ_ONLY);
    }
    
    public PartyDivision getDefaultPartyDivisionForUpdate(Party companyParty) {
        return getDefaultPartyDivision(companyParty, EntityPermission.READ_WRITE);
    }
    
    public PartyDivisionValue getDefaultPartyDivisionValueForUpdate(Party companyParty) {
        return getPartyDivisionValueForUpdate(getDefaultPartyDivisionForUpdate(companyParty));
    }
    
    private PartyDivision getPartyDivisionByName(Party companyParty, String partyDivisionName, EntityPermission entityPermission) {
        PartyDivision partyDivision;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partydivisions, partydetails " +
                        "WHERE pdiv_companypartyid = ? AND pdiv_partydivisionname = ? " +
                        "AND pdiv_thrutime = ? AND pdiv_par_partyid = pardt_par_partyid AND pardt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partydivisions, partydetails " +
                        "WHERE pdiv_companypartyid = ? AND pdiv_partydivisionname = ? " +
                        "AND pdiv_thrutime = ? AND pdiv_par_partyid = pardt_par_partyid AND pardt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyDivisionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, companyParty.getPrimaryKey().getEntityId());
            ps.setString(2, partyDivisionName);
            ps.setLong(3, Session.MAX_TIME);
            ps.setLong(4, Session.MAX_TIME);
            
            partyDivision = PartyDivisionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyDivision;
    }
    
    public PartyDivision getPartyDivisionByName(Party companyParty, String partyDivisionName) {
        return getPartyDivisionByName(companyParty, partyDivisionName, EntityPermission.READ_ONLY);
    }
    
    public PartyDivision getPartyDivisionByNameForUpdate(Party companyParty, String partyDivisionName) {
        return getPartyDivisionByName(companyParty, partyDivisionName, EntityPermission.READ_WRITE);
    }
    
    public PartyDivisionValue getPartyDivisionValueByNameForUpdate(Party companyParty, String partyDivisionName) {
        return getPartyDivisionValueForUpdate(getPartyDivisionByNameForUpdate(companyParty, partyDivisionName));
    }

    private List<PartyDivision> getDivisionsByCompany(Party companyParty, EntityPermission entityPermission) {
        List<PartyDivision> partyDivisions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partydivisions, partydetails " +
                        "WHERE pdiv_companypartyid = ? AND pdiv_thrutime = ? " +
                        "AND pdiv_par_partyid = pardt_par_partyid AND pardt_thrutime = ? " +
                        "ORDER BY pdiv_sortorder, pdiv_partydivisionname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partydivisions, partydetails " +
                        "WHERE pdiv_companypartyid = ? AND pdiv_thrutime = ? " +
                        "AND pdiv_par_partyid = pardt_par_partyid AND pardt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyDivisionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, companyParty.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, Session.MAX_TIME);

            partyDivisions = PartyDivisionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return partyDivisions;
    }

    public List<PartyDivision> getDivisionsByCompany(Party companyParty) {
        return getDivisionsByCompany(companyParty, EntityPermission.READ_ONLY);
    }

    public List<PartyDivision> getDivisionsByCompanyForUpdate(Party companyParty) {
        return getDivisionsByCompany(companyParty, EntityPermission.READ_WRITE);
    }

    private List<PartyDivision> getDivisionsByName(String partyDivisionName, EntityPermission entityPermission) {
        List<PartyDivision> partyDivisions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partydivisions, parties, partydetails " +
                        "WHERE pdiv_partydivisionname = ? AND pdiv_thrutime = ? " +
                        "AND pdiv_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                        "ORDER BY pardt_partyname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partydivisions, parties, partydetails " +
                        "WHERE pdiv_partydivisionname = ? AND pdiv_thrutime = ? " +
                        "AND pdiv_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                        "FOR UPDATE";
            }

            var ps = PartyDivisionFactory.getInstance().prepareStatement(query);

            ps.setString(1, partyDivisionName);
            ps.setLong(2, Session.MAX_TIME);

            partyDivisions = PartyDivisionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return partyDivisions;
    }

    public List<PartyDivision> getDivisionsByName(String partyDivisionName) {
        return getDivisionsByName(partyDivisionName, EntityPermission.READ_ONLY);
    }

    public List<PartyDivision> getDivisionsByNameForUpdate(String partyDivisionName) {
        return getDivisionsByName(partyDivisionName, EntityPermission.READ_WRITE);
    }

    public DivisionChoicesBean getDivisionChoices(Party companyParty, String defaultDivisionChoice, boolean allowNullChoice) {
        var partyDivisions = getDivisionsByCompany(companyParty);
        var size = partyDivisions.size() + (allowNullChoice ? 1 : 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultDivisionChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var partyDivision : partyDivisions) {
            var partyGroup = getPartyGroup(partyDivision.getParty());
            
            var label = partyGroup.getName();
            var value = partyDivision.getPartyDivisionName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultDivisionChoice != null && defaultDivisionChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partyDivision.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new DivisionChoicesBean(labels, values, defaultValue);
    }
    
    public List<DivisionTransfer> getDivisionTransfers(UserVisit userVisit, Collection<PartyDivision> partyDivisions) {
        List<DivisionTransfer> divisionTransfers = new ArrayList<>(partyDivisions.size());
        var divisionTransferCache = getPartyTransferCaches().getDivisionTransferCache();
        
        partyDivisions.forEach((partyDivision) ->
                divisionTransfers.add(divisionTransferCache.getTransfer(userVisit, partyDivision))
        );
        
        return divisionTransfers;
    }
    
    public List<DivisionTransfer> getDivisionTransfersByCompany(UserVisit userVisit, Party companyParty) {
        return getDivisionTransfers(userVisit, getDivisionsByCompany(companyParty));
    }
    
    public DivisionTransfer getDivisionTransfer(UserVisit userVisit, PartyDivision partyDivision) {
        return getPartyTransferCaches().getDivisionTransferCache().getTransfer(userVisit, partyDivision);
    }
    
    public DivisionTransfer getDivisionTransfer(UserVisit userVisit, Party party) {
        return getDivisionTransfer(userVisit, getPartyDivision(party));
    }
    
    private void updatePartyDivisionFromValue(PartyDivisionValue partyDivisionValue, boolean checkDefault, BasePK updatedBy) {
        if(partyDivisionValue.hasBeenModified()) {
            var partyDivision = PartyDivisionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyDivisionValue.getPrimaryKey());
            
            partyDivision.setThruTime(session.START_TIME_LONG);
            partyDivision.store();

            var partyPK = partyDivisionValue.getPartyPK();
            var companyParty = partyDivision.getCompanyParty(); // Not Updated
            var partyDivisionName = partyDivisionValue.getPartyDivisionName();
            var isDefault = partyDivisionValue.getIsDefault();
            var sortOrder = partyDivisionValue.getSortOrder();
            
            if(checkDefault) {
                var defaultPartyDivision = getDefaultPartyDivision(companyParty);
                var defaultFound = defaultPartyDivision != null && !defaultPartyDivision.equals(partyDivision);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPartyDivisionValue = getDefaultPartyDivisionValueForUpdate(companyParty);
                    
                    defaultPartyDivisionValue.setIsDefault(false);
                    updatePartyDivisionFromValue(defaultPartyDivisionValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            partyDivision = PartyDivisionFactory.getInstance().create(partyPK, companyParty.getPrimaryKey(), partyDivisionName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(partyPK, EventTypes.MODIFY, partyDivision.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updatePartyDivisionFromValue(PartyDivisionValue partyDivisionValue, BasePK updatedBy) {
        updatePartyDivisionFromValue(partyDivisionValue, true, updatedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Departments
    // --------------------------------------------------------------------------------
    
    public PartyDepartment createPartyDepartment(Party party, Party divisionParty, String partyDepartmentName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultPartyDepartment = getDefaultPartyDepartment(divisionParty);
        var defaultFound = defaultPartyDepartment != null;
        
        if(defaultFound && isDefault) {
            var defaultPartyDepartmentValue = getDefaultPartyDepartmentValueForUpdate(divisionParty);
            
            defaultPartyDepartmentValue.setIsDefault(false);
            updatePartyDepartmentFromValue(defaultPartyDepartmentValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var partyDepartment = PartyDepartmentFactory.getInstance().create(party, divisionParty,
                partyDepartmentName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyDepartment.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyDepartment;
    }

    public long countPartyDepartments(Party divisionParty) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partydepartments " +
                "WHERE pdept_divisionpartyid = ? AND pdept_thrutime = ?",
                divisionParty, Session.MAX_TIME);
    }

    public PartyDepartmentValue getPartyDepartmentValueForUpdate(PartyDepartment partyDepartment) {
        return partyDepartment == null? null: partyDepartment.getPartyDepartmentValue().clone();
    }
    
    private PartyDepartment getPartyDepartment(Party party, EntityPermission entityPermission) {
        PartyDepartment partyDepartment;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partydepartments " +
                        "WHERE pdept_par_partyid = ? AND pdept_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partydepartments " +
                        "WHERE pdept_par_partyid = ? AND pdept_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyDepartmentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyDepartment = PartyDepartmentFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyDepartment;
    }
    
    public PartyDepartment getPartyDepartment(Party party) {
        return getPartyDepartment(party, EntityPermission.READ_ONLY);
    }
    
    public PartyDepartment getPartyDepartmentForUpdate(Party party) {
        return getPartyDepartment(party, EntityPermission.READ_WRITE);
    }
    
    public PartyDepartmentValue getPartyDepartmentValueForUpdate(Party party) {
        return getPartyDepartmentValueForUpdate(getPartyDepartmentForUpdate(party));
    }
    
    private PartyDepartment getDefaultPartyDepartment(Party divisionParty, EntityPermission entityPermission) {
        PartyDepartment partyDepartment;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partydepartments, partydetails " +
                        "WHERE pdept_divisionpartyid = ? AND pdept_isdefault = 1 AND pdept_thrutime = ? " +
                        "AND pdept_par_partyid = pardt_par_partyid AND pardt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partydepartments, partydetails " +
                        "WHERE pdept_divisionpartyid = ? AND pdept_isdefault = 1 AND pdept_thrutime = ? " +
                        "AND pdept_par_partyid = pardt_par_partyid AND pardt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyDepartmentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, divisionParty.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, Session.MAX_TIME);
            
            partyDepartment = PartyDepartmentFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyDepartment;
    }
    
    public PartyDepartment getDefaultPartyDepartment(Party divisionParty) {
        return getDefaultPartyDepartment(divisionParty, EntityPermission.READ_ONLY);
    }
    
    public PartyDepartment getDefaultPartyDepartmentForUpdate(Party divisionParty) {
        return getDefaultPartyDepartment(divisionParty, EntityPermission.READ_WRITE);
    }
    
    public PartyDepartmentValue getDefaultPartyDepartmentValueForUpdate(Party divisionParty) {
        return getPartyDepartmentValueForUpdate(getDefaultPartyDepartmentForUpdate(divisionParty));
    }
    
    private PartyDepartment getPartyDepartmentByName(Party divisionParty, String partyDepartmentName,
            EntityPermission entityPermission) {
        PartyDepartment partyDepartment;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partydepartments, partydetails " +
                        "WHERE pdept_divisionpartyid = ? AND pdept_partydepartmentname = ? AND pdept_thrutime = ? " +
                        "AND pdept_par_partyid = pardt_par_partyid AND pardt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partydepartments, partydetails " +
                        "WHERE pdept_divisionpartyid = ? AND pdept_partydepartmentname = ? AND pdept_thrutime = ? " +
                        "AND pdept_par_partyid = pardt_par_partyid AND pardt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyDepartmentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, divisionParty.getPrimaryKey().getEntityId());
            ps.setString(2, partyDepartmentName);
            ps.setLong(3, Session.MAX_TIME);
            ps.setLong(4, Session.MAX_TIME);
            
            partyDepartment = PartyDepartmentFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyDepartment;
    }
    
    public PartyDepartment getPartyDepartmentByName(Party divisionParty, String partyDepartmentName) {
        return getPartyDepartmentByName(divisionParty, partyDepartmentName, EntityPermission.READ_ONLY);
    }
    
    public PartyDepartment getPartyDepartmentByNameForUpdate(Party divisionParty, String partyDepartmentName) {
        return getPartyDepartmentByName(divisionParty, partyDepartmentName, EntityPermission.READ_WRITE);
    }
    
    public PartyDepartmentValue getPartyDepartmentValueByNameForUpdate(Party divisionParty, String partyDepartmentName) {
        return getPartyDepartmentValueForUpdate(getPartyDepartmentByNameForUpdate(divisionParty, partyDepartmentName));
    }

    private List<PartyDepartment> getDepartmentsByDivision(Party divisionParty, EntityPermission entityPermission) {
        List<PartyDepartment> partyDepartments;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partydepartments, partydetails " +
                        "WHERE pdept_divisionpartyid = ? AND pdept_thrutime = ? " +
                        "AND pdept_par_partyid = pardt_par_partyid AND pardt_thrutime = ? " +
                        "ORDER BY pdept_sortorder, pdept_partydepartmentname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partydepartments, partydetails " +
                        "WHERE pdept_divisionpartyid = ? AND pdept_thrutime = ? " +
                        "AND pdept_par_partyid = pardt_par_partyid AND pardt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyDepartmentFactory.getInstance().prepareStatement(query);

            ps.setLong(1, divisionParty.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, Session.MAX_TIME);

            partyDepartments = PartyDepartmentFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return partyDepartments;
    }

    public List<PartyDepartment> getDepartmentsByDivision(Party divisionParty) {
        return getDepartmentsByDivision(divisionParty, EntityPermission.READ_ONLY);
    }

    public List<PartyDepartment> getDepartmentsByDivisionForUpdate(Party divisionParty) {
        return getDepartmentsByDivision(divisionParty, EntityPermission.READ_WRITE);
    }

    private List<PartyDepartment> getDepartmentsByName(String partyDepartmentName, EntityPermission entityPermission) {
        List<PartyDepartment> partyDepartments;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partydepartments, parties, partydetails " +
                        "WHERE pdept_partydepartmentname = ? AND pdept_thrutime = ? " +
                        "AND pdept_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                        "ORDER BY pardt_partyname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partydepartments, parties, partydetails " +
                        "WHERE pdept_partydepartmentname = ? AND pdept_thrutime = ? " +
                        "AND pdept_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                        "FOR UPDATE";
            }

            var ps = PartyDepartmentFactory.getInstance().prepareStatement(query);

            ps.setString(1, partyDepartmentName);
            ps.setLong(2, Session.MAX_TIME);

            partyDepartments = PartyDepartmentFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return partyDepartments;
    }

    public List<PartyDepartment> getDepartmentsByName(String partyDepartmentName) {
        return getDepartmentsByName(partyDepartmentName, EntityPermission.READ_ONLY);
    }

    public List<PartyDepartment> getDepartmentsByNameForUpdate(String partyDepartmentName) {
        return getDepartmentsByName(partyDepartmentName, EntityPermission.READ_WRITE);
    }

    public DepartmentChoicesBean getDepartmentChoices(Party divisionParty, String defaultDepartmentChoice, boolean allowNullChoice) {
        var partyDepartments = getDepartmentsByDivision(divisionParty);
        var size = partyDepartments.size() + (allowNullChoice ? 1 : 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultDepartmentChoice == null) {
                defaultValue = "";
            }
        }

        for(var partyDepartment : partyDepartments) {
            var partyGroup = getPartyGroup(partyDepartment.getParty());
            
            var label = partyGroup.getName();
            var value = partyDepartment.getPartyDepartmentName();
            
            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultDepartmentChoice != null && defaultDepartmentChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partyDepartment.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new DepartmentChoicesBean(labels, values, defaultValue);
    }
    
    public List<DepartmentTransfer> getDepartmentTransfers(UserVisit userVisit, Collection<PartyDepartment> partyDepartments) {
        List<DepartmentTransfer> departmentTransfers = new ArrayList<>(partyDepartments.size());
        var departmentTransferCache = getPartyTransferCaches().getDepartmentTransferCache();
        
        partyDepartments.forEach((partyDepartment) ->
                departmentTransfers.add(departmentTransferCache.getTransfer(userVisit, partyDepartment))
        );
        
        return departmentTransfers;
    }
    
    public List<DepartmentTransfer> getDepartmentTransfersByDivision(UserVisit userVisit, Party divisionParty) {
        return getDepartmentTransfers(userVisit, getDepartmentsByDivision(divisionParty));
    }
    
    public DepartmentTransfer getDepartmentTransfer(UserVisit userVisit, PartyDepartment partyDepartment) {
        return getPartyTransferCaches().getDepartmentTransferCache().getTransfer(userVisit, partyDepartment);
    }
    
    public DepartmentTransfer getDepartmentTransfer(UserVisit userVisit, Party party) {
        return getDepartmentTransfer(userVisit, getPartyDepartment(party));
    }
    
    private void updatePartyDepartmentFromValue(PartyDepartmentValue partyDepartmentValue, boolean checkDefault, BasePK updatedBy) {
        if(partyDepartmentValue.hasBeenModified()) {
            var partyDepartment = PartyDepartmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyDepartmentValue.getPrimaryKey());
            
            partyDepartment.setThruTime(session.START_TIME_LONG);
            partyDepartment.store();

            var partyPK = partyDepartmentValue.getPartyPK();
            var companyParty = partyDepartment.getDivisionParty(); // Not Updated
            var partyDepartmentName = partyDepartmentValue.getPartyDepartmentName();
            var isDefault = partyDepartmentValue.getIsDefault();
            var sortOrder = partyDepartmentValue.getSortOrder();
            
            if(checkDefault) {
                var defaultPartyDepartment = getDefaultPartyDepartment(companyParty);
                var defaultFound = defaultPartyDepartment != null && !defaultPartyDepartment.equals(partyDepartment);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPartyDepartmentValue = getDefaultPartyDepartmentValueForUpdate(companyParty);
                    
                    defaultPartyDepartmentValue.setIsDefault(false);
                    updatePartyDepartmentFromValue(defaultPartyDepartmentValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            partyDepartment = PartyDepartmentFactory.getInstance().create(partyPK, companyParty.getPrimaryKey(), partyDepartmentName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(partyPK, EventTypes.MODIFY, partyDepartment.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updatePartyDepartmentFromValue(PartyDepartmentValue partyDepartmentValue, BasePK updatedBy) {
        updatePartyDepartmentFromValue(partyDepartmentValue, true, updatedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   People
    // --------------------------------------------------------------------------------
    
    public Person createPerson(Party party, PersonalTitle personalTitle, String firstName, String firstNameSdx, String middleName, String middleNameSdx,
            String lastName, String lastNameSdx, NameSuffix nameSuffix, BasePK createdBy) {
        var person = PersonFactory.getInstance().create(party, personalTitle, firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx,
                nameSuffix, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, person.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return person;
    }

    public long countPeopleByPersonalTitle(PersonalTitle personalTitle) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM people " +
                "WHERE peop_pert_personaltitleid = ? AND peop_thrutime = ?",
                personalTitle, Session.MAX_TIME_LONG);
    }

    public long countPeopleByNameSuffix(NameSuffix nameSuffix) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM people " +
                "WHERE peop_nsfx_namesuffixid = ? AND peop_thrutime = ?",
                nameSuffix, Session.MAX_TIME_LONG);
    }

    public Person getPerson(Party party, EntityPermission entityPermission) {
        Person person;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM people " +
                        "WHERE peop_par_partyid = ? AND peop_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM people " +
                        "WHERE peop_par_partyid = ? AND peop_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PersonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            person = PersonFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return person;
    }
    
    public Person getPerson(Party party) {
        return getPerson(party, EntityPermission.READ_ONLY);
    }
    
    public Person getPersonForUpdate(Party party) {
        return getPerson(party, EntityPermission.READ_WRITE);
    }
    
    public PersonValue getPersonValue(Person person) {
        return person == null? null: person.getPersonValue().clone();
    }
    
    public PersonValue getPersonValueForUpdate(Party party) {
        return getPersonValue(getPersonForUpdate(party));
    }
    
    public void updatePersonFromValue(PersonValue personValue, BasePK updatedBy) {
        if(personValue.hasBeenModified()) {
            var person = PersonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    personValue.getPrimaryKey());
            
            person.setThruTime(session.START_TIME_LONG);
            person.store();

            var partyPK = person.getPartyPK();
            var personalTitlePK = personValue.getPersonalTitlePK();
            var firstName = personValue.getFirstName();
            var firstNameSdx = personValue.getFirstNameSdx();
            var middleName = personValue.getMiddleName();
            var middleNameSdx = personValue.getMiddleNameSdx();
            var lastName = personValue.getLastName();
            var lastNameSdx = personValue.getLastNameSdx();
            var nameSuffixPK = personValue.getNameSuffixPK();
            
            person = PersonFactory.getInstance().create(partyPK, personalTitlePK, firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx,
                    nameSuffixPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(partyPK, EventTypes.MODIFY, person.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePerson(Person person, BasePK deletedBy) {
        person.setThruTime(session.START_TIME_LONG);
        person.store();
        
        sendEvent(person.getPartyPK(), EventTypes.MODIFY, person.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePersonByParty(Party party, BasePK deletedBy) {
        var person = getPersonForUpdate(party);
        
        if(person != null) {
            deletePerson(person, deletedBy);
        }
    }
    
    public PersonTransfer getPersonTransfer(UserVisit userVisit, Person person) {
        return getPartyTransferCaches().getPersonTransferCache().getTransfer(userVisit, person);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Relationships
    // --------------------------------------------------------------------------------
    
    public PartyRelationship createPartyRelationship(PartyRelationshipType partyRelationshipType, Party fromParty,
            RoleType fromRoleType, Party toParty, RoleType toRoleType, BasePK createdBy) {
        var partyRelationship = PartyRelationshipFactory.getInstance().create(partyRelationshipType,
                fromParty, fromRoleType, toParty, toRoleType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(fromParty.getPrimaryKey(), EventTypes.MODIFY, partyRelationship.getPrimaryKey(), EventTypes.CREATE, createdBy);
        sendEvent(toParty.getPrimaryKey(), EventTypes.MODIFY, partyRelationship.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyRelationship;
    }
    
    public long countPartyRelationships(final PartyRelationshipType partyRelationshipType, final Party fromParty, final RoleType fromRoleType,
            final Party toParty, final RoleType toRoleType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partyrelationships " +
                "WHERE prel_prt_partyrelationshiptypeid = ? AND prel_frompartyid = ? AND prel_fromroletypeid = ? " +
                "AND prel_topartyid = ? AND prel_toroletypeid = ? AND prel_thrutime = ?",
                partyRelationshipType, fromParty, fromRoleType, toParty, toRoleType, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getPartyRelationshipQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyrelationships " +
                "WHERE prel_prt_partyrelationshiptypeid = ? AND prel_frompartyid = ? AND prel_fromroletypeid = ? " +
                "AND prel_topartyid = ? AND prel_toroletypeid = ? AND prel_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyrelationships " +
                "WHERE prel_prt_partyrelationshiptypeid = ? AND prel_frompartyid = ? AND prel_fromroletypeid = ? " +
                "AND prel_topartyid = ? AND prel_toroletypeid = ? AND prel_thrutime = ? " +
                "FOR UPDATE");
        getPartyRelationshipQueries = Collections.unmodifiableMap(queryMap);
    }

    public PartyRelationship getPartyRelationship(PartyRelationshipType partyRelationshipType, Party fromParty, RoleType fromRoleType, Party toParty,
            RoleType toRoleType, EntityPermission entityPermission) {
        return PartyRelationshipFactory.getInstance().getEntityFromQuery(entityPermission, getPartyRelationshipQueries, partyRelationshipType, fromParty,
                fromRoleType, toParty, toRoleType, Session.MAX_TIME);
    }

    public PartyRelationship getPartyRelationship(PartyRelationshipType partyRelationshipType, Party fromParty, RoleType fromRoleType, Party toParty,
            RoleType toRoleType) {
        return getPartyRelationship(partyRelationshipType, fromParty, fromRoleType, toParty, toRoleType, EntityPermission.READ_ONLY);
    }

    public PartyRelationship getPartyRelationshipForUpdate(PartyRelationshipType partyRelationshipType, Party fromParty, RoleType fromRoleType, Party toParty,
            RoleType toRoleType) {
        return getPartyRelationship(partyRelationshipType, fromParty, fromRoleType, toParty, toRoleType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyRelationshipsByFromRelationshipQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyrelationships " +
                "WHERE prel_prt_partyrelationshiptypeid = ? AND prel_frompartyid = ? AND prel_fromroletypeid = ? " +
                "AND prel_thrutime = ? " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyrelationships " +
                "WHERE prel_prt_partyrelationshiptypeid = ? AND prel_frompartyid = ? AND prel_fromroletypeid = ? " +
                "AND prel_thrutime = ? " +
                "FOR UPDATE");
        getPartyRelationshipsByFromRelationshipQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyRelationship> getPartyRelationshipsByFromRelationship(PartyRelationshipType partyRelationshipType,
            Party fromParty, RoleType fromRoleType, EntityPermission entityPermission) {
        return PartyRelationshipFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyRelationshipsByFromRelationshipQueries,
                partyRelationshipType, fromParty, fromRoleType, Session.MAX_TIME);
    }
    
    public List<PartyRelationship> getPartyRelationshipsByFromRelationship(PartyRelationshipType partyRelationshipType,
            Party fromParty, RoleType fromRoleType) {
        return getPartyRelationshipsByFromRelationship(partyRelationshipType, fromParty, fromRoleType, EntityPermission.READ_ONLY);
    }
    
    public List<PartyRelationship> getPartyRelationshipsByFromRelationshipForUpdate(PartyRelationshipType partyRelationshipType,
            Party fromParty, RoleType fromRoleType) {
        return getPartyRelationshipsByFromRelationship(partyRelationshipType, fromParty, fromRoleType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyRelationshipsByToRelationshipQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyrelationships " +
                "WHERE prel_prt_partyrelationshiptypeid = ? AND prel_topartyid = ? AND prel_toroletypeid = ? " +
                "AND prel_thrutime = ? " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyrelationships " +
                "WHERE prel_prt_partyrelationshiptypeid = ? AND prel_topartyid = ? AND prel_toroletypeid = ? " +
                "AND prel_thrutime = ? " +
                "FOR UPDATE");
        getPartyRelationshipsByToRelationshipQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyRelationship> getPartyRelationshipsByToRelationship(PartyRelationshipType partyRelationshipType,
            Party toParty, RoleType toRoleType, EntityPermission entityPermission) {
        return PartyRelationshipFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyRelationshipsByToRelationshipQueries, partyRelationshipType,
                toParty, toRoleType, Session.MAX_TIME);
    }
    
    public List<PartyRelationship> getPartyRelationshipsByToRelationship(PartyRelationshipType partyRelationshipType,
            Party toParty, RoleType toRoleType) {
        return getPartyRelationshipsByToRelationship(partyRelationshipType, toParty, toRoleType, EntityPermission.READ_ONLY);
    }
    
    public List<PartyRelationship> getPartyRelationshipsByToRelationshipForUpdate(PartyRelationshipType partyRelationshipType,
            Party toParty, RoleType toRoleType) {
        return getPartyRelationshipsByToRelationship(partyRelationshipType, toParty, toRoleType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyRelationshipsByFromPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyrelationships " +
                "WHERE prel_frompartyid = ? AND prel_thrutime = ? " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyrelationships " +
                "WHERE prel_frompartyid = ? AND prel_thrutime = ? " +
                "FOR UPDATE");
        getPartyRelationshipsByFromPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyRelationship> getPartyRelationshipsByFromParty(Party fromParty, EntityPermission entityPermission) {
        return PartyRelationshipFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyRelationshipsByFromPartyQueries, fromParty,
                Session.MAX_TIME);
    }
    
    public List<PartyRelationship> getPartyRelationshipsByFromParty(Party fromParty) {
        return getPartyRelationshipsByFromParty(fromParty, EntityPermission.READ_ONLY);
    }
    
    public List<PartyRelationship> getPartyRelationshipsByFromPartyForUpdate(Party fromParty) {
        return getPartyRelationshipsByFromParty(fromParty, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getPartyRelationshipsByToPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyrelationships " +
                "WHERE prel_topartyid = ? AND prel_thrutime = ? " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyrelationships " +
                "WHERE prel_topartyid = ? AND prel_thrutime = ? " +
                "FOR UPDATE");
        getPartyRelationshipsByToPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyRelationship> getPartyRelationshipsByToParty(Party toParty, EntityPermission entityPermission) {
        return PartyRelationshipFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyRelationshipsByToPartyQueries, toParty,
                Session.MAX_TIME);
    }
    
    public List<PartyRelationship> getPartyRelationshipsByToParty(Party toParty) {
        return getPartyRelationshipsByToParty(toParty, EntityPermission.READ_ONLY);
    }
    
    public List<PartyRelationship> getPartyRelationshipsByToPartyForUpdate(Party toParty) {
        return getPartyRelationshipsByToParty(toParty, EntityPermission.READ_WRITE);
    }
    
    public PartyRelationshipTransfer getPartyRelationshipTransfer(UserVisit userVisit, PartyRelationship partyRelationship) {
        return getPartyTransferCaches().getPartyRelationshipTransferCache().getTransfer(userVisit, partyRelationship);
    }
    
    public List<PartyRelationshipTransfer> getPartyRelationshipTransfers(UserVisit userVisit, Collection<PartyRelationship> partyRelationships) {
        List<PartyRelationshipTransfer> partyRelationshipTransfers = new ArrayList<>(partyRelationships.size());
        var partyRelationshipTransferCache = getPartyTransferCaches().getPartyRelationshipTransferCache();
        
        partyRelationships.forEach((partyRelationship) ->
                partyRelationshipTransfers.add(partyRelationshipTransferCache.getTransfer(userVisit, partyRelationship))
        );
        
        return partyRelationshipTransfers;
    }
    
    public List<PartyRelationshipTransfer> getPartyRelationshipTransfersByFromRelationship(UserVisit userVisit,
            PartyRelationshipType partyRelationshipType, Party fromParty, RoleType fromRoleType) {
        return getPartyRelationshipTransfers(userVisit, getPartyRelationshipsByFromRelationship(partyRelationshipType, fromParty,
                fromRoleType));
    }
    
    public List<PartyRelationshipTransfer> getPartyRelationshipTransfersByToRelationship(UserVisit userVisit,
            PartyRelationshipType partyRelationshipType, Party toParty, RoleType toRoleType) {
        return getPartyRelationshipTransfers(userVisit, getPartyRelationshipsByToRelationship(partyRelationshipType, toParty,
                toRoleType));
    }
    
    public List<PartyRelationshipTransfer> getPartyRelationshipTransfersByFromParty(UserVisit userVisit, Party fromParty) {
        return getPartyRelationshipTransfers(userVisit, getPartyRelationshipsByFromParty(fromParty));
    }

    public List<PartyRelationshipTransfer> getPartyRelationshipTransfersByToParty(UserVisit userVisit, Party toParty) {
        return getPartyRelationshipTransfers(userVisit, getPartyRelationshipsByToParty(toParty));
    }

    public void deletePartyRelationship(PartyRelationship partyRelationship, BasePK deletedBy) {
        partyRelationship.setThruTime(session.START_TIME_LONG);
        
        sendEvent(partyRelationship.getFromPartyPK(), EventTypes.MODIFY, partyRelationship.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        sendEvent(partyRelationship.getToPartyPK(), EventTypes.MODIFY, partyRelationship.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyRelationships(List<PartyRelationship> partyRelationships, BasePK deletedBy) {
        partyRelationships.forEach((partyRelationship) -> 
                deletePartyRelationship(partyRelationship, deletedBy)
        );
    }
    
    public void deletePartyRelationshipsByParty(Party party, BasePK deletedBy) {
        deletePartyRelationships(getPartyRelationshipsByFromPartyForUpdate(party), deletedBy);
        deletePartyRelationships(getPartyRelationshipsByToPartyForUpdate(party), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Type Audit Policies
    // --------------------------------------------------------------------------------
    
    public PartyTypeAuditPolicy createPartyTypeAuditPolicy(PartyType partyType, Boolean auditCommands, Long retainUserVisitsTime, BasePK createdBy) {
        var partyTypeAuditPolicy = PartyTypeAuditPolicyFactory.getInstance().create();
        var partyTypeAuditPolicyDetail = PartyTypeAuditPolicyDetailFactory.getInstance().create(partyTypeAuditPolicy, partyType, auditCommands, retainUserVisitsTime,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        partyTypeAuditPolicy = PartyTypeAuditPolicyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyTypeAuditPolicy.getPrimaryKey());
        partyTypeAuditPolicy.setActiveDetail(partyTypeAuditPolicyDetail);
        partyTypeAuditPolicy.setLastDetail(partyTypeAuditPolicyDetail);
        partyTypeAuditPolicy.store();

        sendEvent(partyTypeAuditPolicy.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return partyTypeAuditPolicy;
    }
    
    private PartyTypeAuditPolicy getPartyTypeAuditPolicy(PartyType partyType, EntityPermission entityPermission) {
        PartyTypeAuditPolicy partyTypeAuditPolicy;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partytypeauditpolicies, partytypeauditpolicydetails " +
                        "WHERE ptypap_activedetailid = ptypapdt_partytypeauditpolicydetailid AND ptypapdt_ptyp_partytypeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partytypeauditpolicies, partytypeauditpolicydetails " +
                        "WHERE ptypap_activedetailid = ptypapdt_partytypeauditpolicydetailid AND ptypapdt_ptyp_partytypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyTypeAuditPolicyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyType.getPrimaryKey().getEntityId());
            
            partyTypeAuditPolicy = PartyTypeAuditPolicyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyTypeAuditPolicy;
    }
    
    public PartyTypeAuditPolicy getPartyTypeAuditPolicy(PartyType partyType) {
        return getPartyTypeAuditPolicy(partyType, EntityPermission.READ_ONLY);
    }
    
    public PartyTypeAuditPolicy getPartyTypeAuditPolicyForUpdate(PartyType partyType) {
        return getPartyTypeAuditPolicy(partyType, EntityPermission.READ_WRITE);
    }
    
    public PartyTypeAuditPolicyDetailValue getPartyTypeAuditPolicyDetailValueForUpdate(PartyTypeAuditPolicy partyTypeAuditPolicy) {
        return partyTypeAuditPolicy == null? null: partyTypeAuditPolicy.getLastDetailForUpdate().getPartyTypeAuditPolicyDetailValue().clone();
    }
    
    public PartyTypeAuditPolicyDetailValue getPartyTypeAuditPolicyDetailValueByNameForUpdate(PartyType partyType) {
        return getPartyTypeAuditPolicyDetailValueForUpdate(getPartyTypeAuditPolicyForUpdate(partyType));
    }
    
    public PartyTypeAuditPolicyTransfer getPartyTypeAuditPolicyTransfer(UserVisit userVisit, PartyTypeAuditPolicy partyTypeAuditPolicy) {
        return getPartyTransferCaches().getPartyTypeAuditPolicyTransferCache().getTransfer(userVisit, partyTypeAuditPolicy);
    }
    
    public PartyTypeAuditPolicyTransfer getPartyTypeAuditPolicyTransferByPartyType(UserVisit userVisit, PartyType partyType) {
        return getPartyTransferCaches(userVisit).getPartyTypeAuditPolicyTransferCache().getTransfer(getPartyTypeAuditPolicy(partyType));
    }
    
    public void updatePartyTypeAuditPolicyFromValue(PartyTypeAuditPolicyDetailValue partyTypeAuditPolicyDetailValue, BasePK updatedBy) {
        if(partyTypeAuditPolicyDetailValue.hasBeenModified()) {
            var partyTypeAuditPolicy = PartyTypeAuditPolicyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    partyTypeAuditPolicyDetailValue.getPartyTypeAuditPolicyPK());
            var partyTypeAuditPolicyDetail = partyTypeAuditPolicy.getActiveDetailForUpdate();

            partyTypeAuditPolicyDetail.setThruTime(session.START_TIME_LONG);
            partyTypeAuditPolicyDetail.store();

            var partyTypeAuditPolicyPK = partyTypeAuditPolicyDetail.getPartyTypeAuditPolicyPK();
            var partyTypePK = partyTypeAuditPolicyDetail.getPartyTypePK(); // Not updated
            var auditCommands = partyTypeAuditPolicyDetail.getAuditCommands();
            var retainUserVisitsTime = partyTypeAuditPolicyDetail.getRetainUserVisitsTime();

            partyTypeAuditPolicyDetail = PartyTypeAuditPolicyDetailFactory.getInstance().create(partyTypeAuditPolicyPK, partyTypePK, auditCommands, retainUserVisitsTime,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            partyTypeAuditPolicy.setActiveDetail(partyTypeAuditPolicyDetail);
            partyTypeAuditPolicy.setLastDetail(partyTypeAuditPolicyDetail);

            sendEvent(partyTypeAuditPolicyPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deletePartyTypeAuditPolicy(PartyTypeAuditPolicy partyTypeAuditPolicy, BasePK deletedBy) {
        var partyTypeAuditPolicyDetail = partyTypeAuditPolicy.getLastDetailForUpdate();
        partyTypeAuditPolicyDetail.setThruTime(session.START_TIME_LONG);
        partyTypeAuditPolicy.setActiveDetail(null);
        partyTypeAuditPolicy.store();
        
        sendEvent(partyTypeAuditPolicy.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Type Lockout Policies
    // --------------------------------------------------------------------------------
    
    public PartyTypeLockoutPolicy createPartyTypeLockoutPolicy(PartyType partyType, Integer lockoutFailureCount,
            Long resetFailureCountTime, Boolean manualLockoutReset, Long lockoutInactiveTime, BasePK createdBy) {
        var partyTypeLockoutPolicy = PartyTypeLockoutPolicyFactory.getInstance().create();
        var partyTypeLockoutPolicyDetail = PartyTypeLockoutPolicyDetailFactory.getInstance().create(session,
                partyTypeLockoutPolicy, partyType, lockoutFailureCount, resetFailureCountTime, manualLockoutReset,
                lockoutInactiveTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        partyTypeLockoutPolicy = PartyTypeLockoutPolicyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                partyTypeLockoutPolicy.getPrimaryKey());
        partyTypeLockoutPolicy.setActiveDetail(partyTypeLockoutPolicyDetail);
        partyTypeLockoutPolicy.setLastDetail(partyTypeLockoutPolicyDetail);
        partyTypeLockoutPolicy.store();
        
        sendEvent(partyTypeLockoutPolicy.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return partyTypeLockoutPolicy;
    }
    
    private PartyTypeLockoutPolicy getPartyTypeLockoutPolicy(PartyType partyType, EntityPermission entityPermission) {
        PartyTypeLockoutPolicy partyTypeLockoutPolicy;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partytypelockoutpolicies, partytypelockoutpolicydetails " +
                        "WHERE ptyplp_activedetailid = ptyplpdt_partytypelockoutpolicydetailid AND ptyplpdt_ptyp_partytypeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partytypelockoutpolicies, partytypelockoutpolicydetails " +
                        "WHERE ptyplp_activedetailid = ptyplpdt_partytypelockoutpolicydetailid AND ptyplpdt_ptyp_partytypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyTypeLockoutPolicyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyType.getPrimaryKey().getEntityId());
            
            partyTypeLockoutPolicy = PartyTypeLockoutPolicyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyTypeLockoutPolicy;
    }
    
    public PartyTypeLockoutPolicy getPartyTypeLockoutPolicy(PartyType partyType) {
        return getPartyTypeLockoutPolicy(partyType, EntityPermission.READ_ONLY);
    }
    
    public PartyTypeLockoutPolicy getPartyTypeLockoutPolicyForUpdate(PartyType partyType) {
        return getPartyTypeLockoutPolicy(partyType, EntityPermission.READ_WRITE);
    }
    
    public PartyTypeLockoutPolicyDetailValue getPartyTypeLockoutPolicyDetailValueForUpdate(PartyTypeLockoutPolicy partyTypeLockoutPolicy) {
        return partyTypeLockoutPolicy == null? null: partyTypeLockoutPolicy.getLastDetailForUpdate().getPartyTypeLockoutPolicyDetailValue().clone();
    }
    
    public PartyTypeLockoutPolicyDetailValue getPartyTypeLockoutPolicyDetailValueByPartyTypeForUpdate(PartyType partyType) {
        return getPartyTypeLockoutPolicyDetailValueForUpdate(getPartyTypeLockoutPolicyForUpdate(partyType));
    }
    
    public PartyTypeLockoutPolicyTransfer getPartyTypeLockoutPolicyTransfer(UserVisit userVisit, PartyTypeLockoutPolicy partyTypeLockoutPolicy) {
        return getPartyTransferCaches().getPartyTypeLockoutPolicyTransferCache().getTransfer(userVisit, partyTypeLockoutPolicy);
    }
    
    public PartyTypeLockoutPolicyTransfer getPartyTypeLockoutPolicyTransferByPartyType(UserVisit userVisit, PartyType partyType) {
        return getPartyTransferCaches(userVisit).getPartyTypeLockoutPolicyTransferCache().getTransfer(getPartyTypeLockoutPolicy(partyType));
    }
    
    public void updatePartyTypeLockoutPolicyFromValue(PartyTypeLockoutPolicyDetailValue partyTypeLockoutPolicyDetailValue, BasePK updatedBy) {
        if(partyTypeLockoutPolicyDetailValue.hasBeenModified()) {
            var partyTypeLockoutPolicy = PartyTypeLockoutPolicyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTypeLockoutPolicyDetailValue.getPartyTypeLockoutPolicyPK());
            var partyTypeLockoutPolicyDetail = partyTypeLockoutPolicy.getActiveDetailForUpdate();
            
            partyTypeLockoutPolicyDetail.setThruTime(session.START_TIME_LONG);
            partyTypeLockoutPolicyDetail.store();

            var partyTypeLockoutPolicyPK = partyTypeLockoutPolicyDetail.getPartyTypeLockoutPolicyPK();
            var partyTypePK = partyTypeLockoutPolicyDetail.getPartyTypePK(); // Not updated
            var lockoutFailureCount = partyTypeLockoutPolicyDetailValue.getLockoutFailureCount();
            var resetFailureCountTime = partyTypeLockoutPolicyDetailValue.getResetFailureCountTime();
            var manualLockoutReset = partyTypeLockoutPolicyDetailValue.getManualLockoutReset();
            var lockoutInactiveTime = partyTypeLockoutPolicyDetailValue.getLockoutInactiveTime();
            
            partyTypeLockoutPolicyDetail = PartyTypeLockoutPolicyDetailFactory.getInstance().create(session,
                    partyTypeLockoutPolicyPK, partyTypePK, lockoutFailureCount, resetFailureCountTime, manualLockoutReset,
                    lockoutInactiveTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            partyTypeLockoutPolicy.setActiveDetail(partyTypeLockoutPolicyDetail);
            partyTypeLockoutPolicy.setLastDetail(partyTypeLockoutPolicyDetail);
            
            sendEvent(partyTypeLockoutPolicyPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deletePartyTypeLockoutPolicy(PartyTypeLockoutPolicy partyTypeLockoutPolicy, BasePK deletedBy) {
        var partyTypeLockoutPolicyDetail = partyTypeLockoutPolicy.getLastDetailForUpdate();
        partyTypeLockoutPolicyDetail.setThruTime(session.START_TIME_LONG);
        partyTypeLockoutPolicy.setActiveDetail(null);
        partyTypeLockoutPolicy.store();
        
        sendEvent(partyTypeLockoutPolicy.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Type Password String Policies
    // --------------------------------------------------------------------------------
    
    public PartyTypePasswordStringPolicy createPartyTypePasswordStringPolicy(PartyType partyType, Boolean forceChangeAfterCreate,
            Boolean forceChangeAfterReset, Boolean allowChange, Integer passwordHistory, Long minimumPasswordLifetime, Long maximumPasswordLifetime,
            Long expirationWarningTime, Integer expiredLoginsPermitted, Integer minimumLength, Integer maximumLength,
            Integer requiredDigitCount, Integer requiredLetterCount, Integer requiredUpperCaseCount, Integer requiredLowerCaseCount,
            Integer maximumRepeated, Integer minimumCharacterTypes, BasePK createdBy) {
        var partyTypePasswordStringPolicy = PartyTypePasswordStringPolicyFactory.getInstance().create();
        var partyTypePasswordStringPolicyDetail = PartyTypePasswordStringPolicyDetailFactory.getInstance().create(session,
                partyTypePasswordStringPolicy, partyType, forceChangeAfterCreate, forceChangeAfterReset, allowChange, passwordHistory,
                minimumPasswordLifetime, maximumPasswordLifetime, expirationWarningTime, expiredLoginsPermitted, minimumLength,
                maximumLength, requiredDigitCount, requiredLetterCount, requiredUpperCaseCount, requiredLowerCaseCount,
                maximumRepeated, minimumCharacterTypes, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        partyTypePasswordStringPolicy = PartyTypePasswordStringPolicyFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, partyTypePasswordStringPolicy.getPrimaryKey());
        partyTypePasswordStringPolicy.setActiveDetail(partyTypePasswordStringPolicyDetail);
        partyTypePasswordStringPolicy.setLastDetail(partyTypePasswordStringPolicyDetail);
        partyTypePasswordStringPolicy.store();
        
        sendEvent(partyTypePasswordStringPolicy.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return partyTypePasswordStringPolicy;
    }
    
    private PartyTypePasswordStringPolicy getPartyTypePasswordStringPolicy(PartyType partyType, EntityPermission entityPermission) {
        PartyTypePasswordStringPolicy partyTypePasswordStringPolicy;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partytypepasswordstringpolicies, partytypepasswordstringpolicydetails " +
                        "WHERE ptyppsp_activedetailid = ptyppspdt_partytypepasswordstringpolicydetailid AND ptyppspdt_ptyp_partytypeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partytypepasswordstringpolicies, partytypepasswordstringpolicydetails " +
                        "WHERE ptyppsp_activedetailid = ptyppspdt_partytypepasswordstringpolicydetailid AND ptyppspdt_ptyp_partytypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyTypePasswordStringPolicyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyType.getPrimaryKey().getEntityId());
            
            partyTypePasswordStringPolicy = PartyTypePasswordStringPolicyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyTypePasswordStringPolicy;
    }
    
    public PartyTypePasswordStringPolicy getPartyTypePasswordStringPolicy(PartyType partyType) {
        return getPartyTypePasswordStringPolicy(partyType, EntityPermission.READ_ONLY);
    }
    
    public PartyTypePasswordStringPolicy getPartyTypePasswordStringPolicyForUpdate(PartyType partyType) {
        return getPartyTypePasswordStringPolicy(partyType, EntityPermission.READ_WRITE);
    }
    
    public PartyTypePasswordStringPolicyDetailValue getPartyTypePasswordStringPolicyDetailValueForUpdate(PartyTypePasswordStringPolicy partyTypePasswordStringPolicy) {
        return partyTypePasswordStringPolicy == null? null: partyTypePasswordStringPolicy.getLastDetailForUpdate().getPartyTypePasswordStringPolicyDetailValue().clone();
    }
    
    public PartyTypePasswordStringPolicyDetailValue getPartyTypePasswordStringPolicyDetailValueByPartyTypeForUpdate(PartyType partyType) {
        return getPartyTypePasswordStringPolicyDetailValueForUpdate(getPartyTypePasswordStringPolicyForUpdate(partyType));
    }
    
    public PartyTypePasswordStringPolicyTransfer getPartyTypePasswordStringPolicyTransfer(UserVisit userVisit, PartyTypePasswordStringPolicy partyTypePasswordStringPolicy) {
        return getPartyTransferCaches().getPartyTypePasswordStringPolicyTransferCache().getTransfer(userVisit, partyTypePasswordStringPolicy);
    }
    
    public PartyTypePasswordStringPolicyTransfer getPartyTypePasswordStringPolicyTransferByPartyType(UserVisit userVisit, PartyType partyType) {
        return getPartyTransferCaches(userVisit).getPartyTypePasswordStringPolicyTransferCache().getTransfer(getPartyTypePasswordStringPolicy(partyType));
    }
    
    public void updatePartyTypePasswordStringPolicyFromValue(PartyTypePasswordStringPolicyDetailValue partyTypePasswordStringPolicyDetailValue, BasePK updatedBy) {
        if(partyTypePasswordStringPolicyDetailValue.hasBeenModified()) {
            var partyTypePasswordStringPolicy = PartyTypePasswordStringPolicyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTypePasswordStringPolicyDetailValue.getPartyTypePasswordStringPolicyPK());
            var partyTypePasswordStringPolicyDetail = partyTypePasswordStringPolicy.getActiveDetailForUpdate();
            
            partyTypePasswordStringPolicyDetail.setThruTime(session.START_TIME_LONG);
            partyTypePasswordStringPolicyDetail.store();

            var partyTypePasswordStringPolicyPK = partyTypePasswordStringPolicyDetail.getPartyTypePasswordStringPolicyPK();
            var partyTypePK = partyTypePasswordStringPolicyDetail.getPartyTypePK(); // Not updated
            var forceChangeAfterCreate = partyTypePasswordStringPolicyDetailValue.getForceChangeAfterCreate();
            var forceChangeAfterReset = partyTypePasswordStringPolicyDetailValue.getForceChangeAfterReset();
            var allowChange = partyTypePasswordStringPolicyDetailValue.getAllowChange();
            var passwordHistory = partyTypePasswordStringPolicyDetailValue.getPasswordHistory();
            var minimumPasswordLifetime = partyTypePasswordStringPolicyDetailValue.getMinimumPasswordLifetime();
            var maximumPasswordLifetime = partyTypePasswordStringPolicyDetailValue.getMaximumPasswordLifetime();
            var expirationWarningTime = partyTypePasswordStringPolicyDetailValue.getExpirationWarningTime();
            var expiredLoginsPermitted = partyTypePasswordStringPolicyDetailValue.getExpiredLoginsPermitted();
            var minimumLength = partyTypePasswordStringPolicyDetailValue.getMinimumLength();
            var maximumLength = partyTypePasswordStringPolicyDetailValue.getMaximumLength();
            var requiredDigitCount = partyTypePasswordStringPolicyDetailValue.getRequiredDigitCount();
            var requiredLetterCount = partyTypePasswordStringPolicyDetailValue.getRequiredLetterCount();
            var requiredUpperCaseCount = partyTypePasswordStringPolicyDetailValue.getRequiredUpperCaseCount();
            var requiredLowerCaseCount = partyTypePasswordStringPolicyDetailValue.getRequiredLowerCaseCount();
            var maximumRepeated = partyTypePasswordStringPolicyDetailValue.getMaximumRepeated();
            var minimumCharacterTypes = partyTypePasswordStringPolicyDetailValue.getMinimumCharacterTypes();
            
            partyTypePasswordStringPolicyDetail = PartyTypePasswordStringPolicyDetailFactory.getInstance().create(session,
                    partyTypePasswordStringPolicyPK, partyTypePK, forceChangeAfterCreate, forceChangeAfterReset, allowChange, passwordHistory,
                    minimumPasswordLifetime, maximumPasswordLifetime, expirationWarningTime, expiredLoginsPermitted, minimumLength,
                    maximumLength, requiredDigitCount, requiredLetterCount, requiredUpperCaseCount, requiredLowerCaseCount,
                    maximumRepeated, minimumCharacterTypes, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            partyTypePasswordStringPolicy.setActiveDetail(partyTypePasswordStringPolicyDetail);
            partyTypePasswordStringPolicy.setLastDetail(partyTypePasswordStringPolicyDetail);
            
            sendEvent(partyTypePasswordStringPolicyPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deletePartyTypePasswordStringPolicy(PartyTypePasswordStringPolicy partyTypePasswordStringPolicy, BasePK deletedBy) {
        var partyTypePasswordStringPolicyDetail = partyTypePasswordStringPolicy.getLastDetailForUpdate();
        partyTypePasswordStringPolicyDetail.setThruTime(session.START_TIME_LONG);
        partyTypePasswordStringPolicy.setActiveDetail(null);
        partyTypePasswordStringPolicy.store();
        
        sendEvent(partyTypePasswordStringPolicy.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Genders
    // --------------------------------------------------------------------------------
    
    public Gender createGender(String genderName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultGender = getDefaultGender();
        var defaultFound = defaultGender != null;
        
        if(defaultFound && isDefault) {
            var defaultGenderDetailValue = getDefaultGenderDetailValueForUpdate();
            
            defaultGenderDetailValue.setIsDefault(false);
            updateGenderFromValue(defaultGenderDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var gender = GenderFactory.getInstance().create();
        var genderDetail = GenderDetailFactory.getInstance().create(gender, genderName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        gender = GenderFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, gender.getPrimaryKey());
        gender.setActiveDetail(genderDetail);
        gender.setLastDetail(genderDetail);
        gender.store();
        
        sendEvent(gender.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return gender;
    }
    
    private Gender getGenderByName(String genderName, EntityPermission entityPermission) {
        Gender gender;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM genders, genderdetails " +
                        "WHERE gndr_activedetailid = gndrdt_genderdetailid AND gndrdt_gendername = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM genders, genderdetails " +
                        "WHERE gndr_activedetailid = gndrdt_genderdetailid AND gndrdt_gendername = ? " +
                        "FOR UPDATE";
            }

            var ps = GenderFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, genderName);
            
            gender = GenderFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return gender;
    }
    
    public Gender getGenderByName(String genderName) {
        return getGenderByName(genderName, EntityPermission.READ_ONLY);
    }
    
    public Gender getGenderByNameForUpdate(String genderName) {
        return getGenderByName(genderName, EntityPermission.READ_WRITE);
    }
    
    public GenderDetailValue getGenderDetailValueForUpdate(Gender gender) {
        return gender == null? null: gender.getLastDetailForUpdate().getGenderDetailValue().clone();
    }
    
    public GenderDetailValue getGenderDetailValueByNameForUpdate(String genderName) {
        return getGenderDetailValueForUpdate(getGenderByNameForUpdate(genderName));
    }
    
    private Gender getDefaultGender(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM genders, genderdetails " +
                    "WHERE gndr_activedetailid = gndrdt_genderdetailid AND gndrdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM genders, genderdetails " +
                    "WHERE gndr_activedetailid = gndrdt_genderdetailid AND gndrdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = GenderFactory.getInstance().prepareStatement(query);
        
        return GenderFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public Gender getDefaultGender() {
        return getDefaultGender(EntityPermission.READ_ONLY);
    }
    
    public Gender getDefaultGenderForUpdate() {
        return getDefaultGender(EntityPermission.READ_WRITE);
    }
    
    public GenderDetailValue getDefaultGenderDetailValueForUpdate() {
        return getDefaultGender(EntityPermission.READ_WRITE).getLastDetailForUpdate().getGenderDetailValue();
    }
    
    private List<Gender> getGenders(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM genders, genderdetails " +
                    "WHERE gndr_activedetailid = gndrdt_genderdetailid " +
                    "ORDER BY gndrdt_sortorder, gndrdt_gendername " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM genders, genderdetails " +
                    "WHERE gndr_activedetailid = gndrdt_genderdetailid " +
                    "FOR UPDATE";
        }

        var ps = GenderFactory.getInstance().prepareStatement(query);
        
        return GenderFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<Gender> getGenders() {
        return getGenders(EntityPermission.READ_ONLY);
    }
    
    public List<Gender> getGendersForUpdate() {
        return getGenders(EntityPermission.READ_WRITE);
    }
    
    public GenderChoicesBean getGenderChoices(String defaultGenderChoice, Language language, boolean allowNullChoice) {
        var genders = getGenders();
        var size = genders.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGenderChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var gender : genders) {
            var genderDetail = gender.getLastDetail();
            
            var label = getBestGenderDescription(gender, language);
            var value = genderDetail.getGenderName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultGenderChoice != null && defaultGenderChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && genderDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GenderChoicesBean(labels, values, defaultValue);
    }
    
    public GenderTransfer getGenderTransfer(UserVisit userVisit, Gender gender) {
        return getPartyTransferCaches().getGenderTransferCache().getTransfer(userVisit, gender);
    }
    
    public List<GenderTransfer> getGenderTransfers(UserVisit userVisit) {
        var genders = getGenders();
        List<GenderTransfer> genderTransfers = new ArrayList<>(genders.size());
        var genderTransferCache = getPartyTransferCaches().getGenderTransferCache();
        
        genders.forEach((gender) ->
                genderTransfers.add(genderTransferCache.getTransfer(userVisit, gender))
        );
        
        return genderTransfers;
    }
    
    private void updateGenderFromValue(GenderDetailValue genderDetailValue, boolean checkDefault, BasePK updatedBy) {
        var gender = GenderFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, genderDetailValue.getGenderPK());
        var genderDetail = gender.getActiveDetailForUpdate();
        
        genderDetail.setThruTime(session.START_TIME_LONG);
        genderDetail.store();

        var genderPK = genderDetail.getGenderPK();
        var genderName = genderDetailValue.getGenderName();
        var isDefault = genderDetailValue.getIsDefault();
        var sortOrder = genderDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultGender = getDefaultGender();
            var defaultFound = defaultGender != null && !defaultGender.equals(gender);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultGenderDetailValue = getDefaultGenderDetailValueForUpdate();
                
                defaultGenderDetailValue.setIsDefault(false);
                updateGenderFromValue(defaultGenderDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        genderDetail = GenderDetailFactory.getInstance().create(genderPK, genderName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        gender.setActiveDetail(genderDetail);
        gender.setLastDetail(genderDetail);
        gender.store();
        
        sendEvent(genderPK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateGenderFromValue(GenderDetailValue genderDetailValue, BasePK updatedBy) {
        updateGenderFromValue(genderDetailValue, true, updatedBy);
    }
    
    public void deleteGender(Gender gender, BasePK deletedBy) {
        deleteGenderDescriptionsByGender(gender, deletedBy);

        var genderDetail = gender.getLastDetailForUpdate();
        genderDetail.setThruTime(session.START_TIME_LONG);
        gender.setActiveDetail(null);
        gender.store();
        
        // Check for default, and pick one if necessary
        var defaultGender = getDefaultGender();
        if(defaultGender == null) {
            var genders = getGendersForUpdate();
            
            if(!genders.isEmpty()) {
                var iter = genders.iterator();
                if(iter.hasNext()) {
                    defaultGender = iter.next();
                }
                var genderDetailValue = Objects.requireNonNull(defaultGender).getLastDetailForUpdate().getGenderDetailValue().clone();
                
                genderDetailValue.setIsDefault(true);
                updateGenderFromValue(genderDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(gender.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Gender Descriptions
    // --------------------------------------------------------------------------------
    
    public GenderDescription createGenderDescription(Gender gender, Language language, String description, BasePK createdBy) {
        var genderDescription = GenderDescriptionFactory.getInstance().create(gender, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(gender.getPrimaryKey(), EventTypes.MODIFY, genderDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return genderDescription;
    }
    
    private GenderDescription getGenderDescription(Gender gender, Language language, EntityPermission entityPermission) {
        GenderDescription genderDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM genderdescriptions " +
                        "WHERE gndrd_gndr_genderid = ? AND gndrd_lang_languageid = ? AND gndrd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM genderdescriptions " +
                        "WHERE gndrd_gndr_genderid = ? AND gndrd_lang_languageid = ? AND gndrd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GenderDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, gender.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            genderDescription = GenderDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return genderDescription;
    }
    
    public GenderDescription getGenderDescription(Gender gender, Language language) {
        return getGenderDescription(gender, language, EntityPermission.READ_ONLY);
    }
    
    public GenderDescription getGenderDescriptionForUpdate(Gender gender, Language language) {
        return getGenderDescription(gender, language, EntityPermission.READ_WRITE);
    }
    
    public GenderDescriptionValue getGenderDescriptionValue(GenderDescription genderDescription) {
        return genderDescription == null? null: genderDescription.getGenderDescriptionValue().clone();
    }
    
    public GenderDescriptionValue getGenderDescriptionValueForUpdate(Gender gender, Language language) {
        return getGenderDescriptionValue(getGenderDescriptionForUpdate(gender, language));
    }
    
    private List<GenderDescription> getGenderDescriptionsByGender(Gender gender, EntityPermission entityPermission) {
        List<GenderDescription> genderDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM genderdescriptions, languages " +
                        "WHERE gndrd_gndr_genderid = ? AND gndrd_thrutime = ? AND gndrd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM genderdescriptions " +
                        "WHERE gndrd_gndr_genderid = ? AND gndrd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GenderDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, gender.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            genderDescriptions = GenderDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return genderDescriptions;
    }
    
    public List<GenderDescription> getGenderDescriptionsByGender(Gender gender) {
        return getGenderDescriptionsByGender(gender, EntityPermission.READ_ONLY);
    }
    
    public List<GenderDescription> getGenderDescriptionsByGenderForUpdate(Gender gender) {
        return getGenderDescriptionsByGender(gender, EntityPermission.READ_WRITE);
    }
    
    public String getBestGenderDescription(Gender gender, Language language) {
        String description;
        var genderDescription = getGenderDescription(gender, language);
        
        if(genderDescription == null && !language.getIsDefault()) {
            genderDescription = getGenderDescription(gender, partyControl.getDefaultLanguage());
        }
        
        if(genderDescription == null) {
            description = gender.getLastDetail().getGenderName();
        } else {
            description = genderDescription.getDescription();
        }
        
        return description;
    }
    
    public GenderDescriptionTransfer getGenderDescriptionTransfer(UserVisit userVisit, GenderDescription genderDescription) {
        return getPartyTransferCaches().getGenderDescriptionTransferCache().getTransfer(userVisit, genderDescription);
    }
    
    public List<GenderDescriptionTransfer> getGenderDescriptionTransfersByGender(UserVisit userVisit, Gender gender) {
        var genderDescriptions = getGenderDescriptionsByGender(gender);
        List<GenderDescriptionTransfer> genderDescriptionTransfers = new ArrayList<>(genderDescriptions.size());
        var genderDescriptionTransferCache = getPartyTransferCaches().getGenderDescriptionTransferCache();
        
        genderDescriptions.forEach((genderDescription) ->
                genderDescriptionTransfers.add(genderDescriptionTransferCache.getTransfer(userVisit, genderDescription))
        );
        
        return genderDescriptionTransfers;
    }
    
    public void updateGenderDescriptionFromValue(GenderDescriptionValue genderDescriptionValue, BasePK updatedBy) {
        if(genderDescriptionValue.hasBeenModified()) {
            var genderDescription = GenderDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     genderDescriptionValue.getPrimaryKey());
            
            genderDescription.setThruTime(session.START_TIME_LONG);
            genderDescription.store();

            var gender = genderDescription.getGender();
            var language = genderDescription.getLanguage();
            var description = genderDescriptionValue.getDescription();
            
            genderDescription = GenderDescriptionFactory.getInstance().create(gender, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(gender.getPrimaryKey(), EventTypes.MODIFY, genderDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGenderDescription(GenderDescription genderDescription, BasePK deletedBy) {
        genderDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(genderDescription.getGenderPK(), EventTypes.MODIFY, genderDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteGenderDescriptionsByGender(Gender gender, BasePK deletedBy) {
        var genderDescriptions = getGenderDescriptionsByGenderForUpdate(gender);
        
        genderDescriptions.forEach((genderDescription) -> 
                deleteGenderDescription(genderDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Moods
    // --------------------------------------------------------------------------------
    
    public Mood createMood(String moodName, Icon icon, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultMood = getDefaultMood();
        var defaultFound = defaultMood != null;
        
        if(defaultFound && isDefault) {
            var defaultMoodDetailValue = getDefaultMoodDetailValueForUpdate();
            
            defaultMoodDetailValue.setIsDefault(false);
            updateMoodFromValue(defaultMoodDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var mood = MoodFactory.getInstance().create();
        var moodDetail = MoodDetailFactory.getInstance().create(mood, moodName, icon, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        mood = MoodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, mood.getPrimaryKey());
        mood.setActiveDetail(moodDetail);
        mood.setLastDetail(moodDetail);
        mood.store();
        
        sendEvent(mood.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return mood;
    }
    
    private Mood getMoodByName(String moodName, EntityPermission entityPermission) {
        Mood mood;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM moods, mooddetails " +
                        "WHERE md_activedetailid = mddt_mooddetailid AND mddt_moodname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM moods, mooddetails " +
                        "WHERE md_activedetailid = mddt_mooddetailid AND mddt_moodname = ? " +
                        "FOR UPDATE";
            }

            var ps = MoodFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, moodName);
            
            mood = MoodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return mood;
    }
    
    public Mood getMoodByName(String moodName) {
        return getMoodByName(moodName, EntityPermission.READ_ONLY);
    }
    
    public Mood getMoodByNameForUpdate(String moodName) {
        return getMoodByName(moodName, EntityPermission.READ_WRITE);
    }
    
    public MoodDetailValue getMoodDetailValueForUpdate(Mood mood) {
        return mood == null? null: mood.getLastDetailForUpdate().getMoodDetailValue().clone();
    }
    
    public MoodDetailValue getMoodDetailValueByNameForUpdate(String moodName) {
        return getMoodDetailValueForUpdate(getMoodByNameForUpdate(moodName));
    }
    
    private Mood getDefaultMood(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM moods, mooddetails " +
                    "WHERE md_activedetailid = mddt_mooddetailid AND mddt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM moods, mooddetails " +
                    "WHERE md_activedetailid = mddt_mooddetailid AND mddt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = MoodFactory.getInstance().prepareStatement(query);
        
        return MoodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public Mood getDefaultMood() {
        return getDefaultMood(EntityPermission.READ_ONLY);
    }
    
    public Mood getDefaultMoodForUpdate() {
        return getDefaultMood(EntityPermission.READ_WRITE);
    }
    
    public MoodDetailValue getDefaultMoodDetailValueForUpdate() {
        return getDefaultMood(EntityPermission.READ_WRITE).getLastDetailForUpdate().getMoodDetailValue();
    }
    
    private List<Mood> getMoods(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM moods, mooddetails " +
                    "WHERE md_activedetailid = mddt_mooddetailid " +
                    "ORDER BY mddt_sortorder, mddt_moodname " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM moods, mooddetails " +
                    "WHERE md_activedetailid = mddt_mooddetailid " +
                    "FOR UPDATE";
        }

        var ps = MoodFactory.getInstance().prepareStatement(query);
        
        return MoodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<Mood> getMoods() {
        return getMoods(EntityPermission.READ_ONLY);
    }
    
    public List<Mood> getMoodsForUpdate() {
        return getMoods(EntityPermission.READ_WRITE);
    }
    
    public MoodChoicesBean getMoodChoices(String defaultMoodChoice, Language language, boolean allowNullChoice) {
        var moods = getMoods();
        var size = moods.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultMoodChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var mood : moods) {
            var moodDetail = mood.getLastDetail();
            
            var label = getBestMoodDescription(mood, language);
            var value = moodDetail.getMoodName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultMoodChoice != null && defaultMoodChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && moodDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new MoodChoicesBean(labels, values, defaultValue);
    }
    
    public MoodTransfer getMoodTransfer(UserVisit userVisit, Mood mood) {
        return getPartyTransferCaches().getMoodTransferCache().getTransfer(userVisit, mood);
    }
    
    public List<MoodTransfer> getMoodTransfers(UserVisit userVisit) {
        var moods = getMoods();
        List<MoodTransfer> moodTransfers = new ArrayList<>(moods.size());
        var moodTransferCache = getPartyTransferCaches().getMoodTransferCache();
        
        moods.forEach((mood) ->
                moodTransfers.add(moodTransferCache.getTransfer(userVisit, mood))
        );
        
        return moodTransfers;
    }
    
    private void updateMoodFromValue(MoodDetailValue moodDetailValue, boolean checkDefault, BasePK updatedBy) {
        var mood = MoodFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, moodDetailValue.getMoodPK());
        var moodDetail = mood.getActiveDetailForUpdate();
        
        moodDetail.setThruTime(session.START_TIME_LONG);
        moodDetail.store();

        var moodPK = moodDetail.getMoodPK();
        var moodName = moodDetailValue.getMoodName();
        var iconPK = moodDetailValue.getIconPK();
        var isDefault = moodDetailValue.getIsDefault();
        var sortOrder = moodDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultMood = getDefaultMood();
            var defaultFound = defaultMood != null && !defaultMood.equals(mood);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultMoodDetailValue = getDefaultMoodDetailValueForUpdate();
                
                defaultMoodDetailValue.setIsDefault(false);
                updateMoodFromValue(defaultMoodDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        moodDetail = MoodDetailFactory.getInstance().create(moodPK, moodName, iconPK, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        mood.setActiveDetail(moodDetail);
        mood.setLastDetail(moodDetail);
        mood.store();
        
        sendEvent(moodPK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateMoodFromValue(MoodDetailValue moodDetailValue, BasePK updatedBy) {
        updateMoodFromValue(moodDetailValue, true, updatedBy);
    }
    
    public void deleteMood(Mood mood, BasePK deletedBy) {
        deleteMoodDescriptionsByMood(mood, deletedBy);

        var moodDetail = mood.getLastDetailForUpdate();
        moodDetail.setThruTime(session.START_TIME_LONG);
        mood.setActiveDetail(null);
        mood.store();
        
        // Check for default, and pick one if necessary
        var defaultMood = getDefaultMood();
        if(defaultMood == null) {
            var moods = getMoodsForUpdate();
            
            if(!moods.isEmpty()) {
                var iter = moods.iterator();
                if(iter.hasNext()) {
                    defaultMood = iter.next();
                }
                var moodDetailValue = Objects.requireNonNull(defaultMood).getLastDetailForUpdate().getMoodDetailValue().clone();
                
                moodDetailValue.setIsDefault(true);
                updateMoodFromValue(moodDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(mood.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Mood Descriptions
    // --------------------------------------------------------------------------------
    
    public MoodDescription createMoodDescription(Mood mood, Language language, String description, BasePK createdBy) {
        var moodDescription = MoodDescriptionFactory.getInstance().create(mood, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(mood.getPrimaryKey(), EventTypes.MODIFY, moodDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return moodDescription;
    }
    
    private MoodDescription getMoodDescription(Mood mood, Language language, EntityPermission entityPermission) {
        MoodDescription moodDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM mooddescriptions " +
                        "WHERE mdd_md_moodid = ? AND mdd_lang_languageid = ? AND mdd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM mooddescriptions " +
                        "WHERE mdd_md_moodid = ? AND mdd_lang_languageid = ? AND mdd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = MoodDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, mood.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            moodDescription = MoodDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return moodDescription;
    }
    
    public MoodDescription getMoodDescription(Mood mood, Language language) {
        return getMoodDescription(mood, language, EntityPermission.READ_ONLY);
    }
    
    public MoodDescription getMoodDescriptionForUpdate(Mood mood, Language language) {
        return getMoodDescription(mood, language, EntityPermission.READ_WRITE);
    }
    
    public MoodDescriptionValue getMoodDescriptionValue(MoodDescription moodDescription) {
        return moodDescription == null? null: moodDescription.getMoodDescriptionValue().clone();
    }
    
    public MoodDescriptionValue getMoodDescriptionValueForUpdate(Mood mood, Language language) {
        return getMoodDescriptionValue(getMoodDescriptionForUpdate(mood, language));
    }
    
    private List<MoodDescription> getMoodDescriptionsByMood(Mood mood, EntityPermission entityPermission) {
        List<MoodDescription> moodDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM mooddescriptions, languages " +
                        "WHERE mdd_md_moodid = ? AND mdd_thrutime = ? AND mdd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM mooddescriptions " +
                        "WHERE mdd_md_moodid = ? AND mdd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = MoodDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, mood.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            moodDescriptions = MoodDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return moodDescriptions;
    }
    
    public List<MoodDescription> getMoodDescriptionsByMood(Mood mood) {
        return getMoodDescriptionsByMood(mood, EntityPermission.READ_ONLY);
    }
    
    public List<MoodDescription> getMoodDescriptionsByMoodForUpdate(Mood mood) {
        return getMoodDescriptionsByMood(mood, EntityPermission.READ_WRITE);
    }
    
    public String getBestMoodDescription(Mood mood, Language language) {
        String description;
        var moodDescription = getMoodDescription(mood, language);
        
        if(moodDescription == null && !language.getIsDefault()) {
            moodDescription = getMoodDescription(mood, partyControl.getDefaultLanguage());
        }
        
        if(moodDescription == null) {
            description = mood.getLastDetail().getMoodName();
        } else {
            description = moodDescription.getDescription();
        }
        
        return description;
    }
    
    public MoodDescriptionTransfer getMoodDescriptionTransfer(UserVisit userVisit, MoodDescription moodDescription) {
        return getPartyTransferCaches().getMoodDescriptionTransferCache().getTransfer(userVisit, moodDescription);
    }
    
    public List<MoodDescriptionTransfer> getMoodDescriptionTransfersByMood(UserVisit userVisit, Mood mood) {
        var moodDescriptions = getMoodDescriptionsByMood(mood);
        List<MoodDescriptionTransfer> moodDescriptionTransfers = new ArrayList<>(moodDescriptions.size());
        var moodDescriptionTransferCache = getPartyTransferCaches().getMoodDescriptionTransferCache();
        
        moodDescriptions.forEach((moodDescription) ->
                moodDescriptionTransfers.add(moodDescriptionTransferCache.getTransfer(userVisit, moodDescription))
        );
        
        return moodDescriptionTransfers;
    }
    
    public void updateMoodDescriptionFromValue(MoodDescriptionValue moodDescriptionValue, BasePK updatedBy) {
        if(moodDescriptionValue.hasBeenModified()) {
            var moodDescription = MoodDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     moodDescriptionValue.getPrimaryKey());
            
            moodDescription.setThruTime(session.START_TIME_LONG);
            moodDescription.store();

            var mood = moodDescription.getMood();
            var language = moodDescription.getLanguage();
            var description = moodDescriptionValue.getDescription();
            
            moodDescription = MoodDescriptionFactory.getInstance().create(mood, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(mood.getPrimaryKey(), EventTypes.MODIFY, moodDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteMoodDescription(MoodDescription moodDescription, BasePK deletedBy) {
        moodDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(moodDescription.getMoodPK(), EventTypes.MODIFY, moodDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteMoodDescriptionsByMood(Mood mood, BasePK deletedBy) {
        var moodDescriptions = getMoodDescriptionsByMoodForUpdate(mood);
        
        moodDescriptions.forEach((moodDescription) -> 
                deleteMoodDescription(moodDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Birthday Formats
    // --------------------------------------------------------------------------------

    public BirthdayFormat createBirthdayFormat(String birthdayFormatName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultBirthdayFormat = getDefaultBirthdayFormat();
        var defaultFound = defaultBirthdayFormat != null;

        if(defaultFound && isDefault) {
            var defaultBirthdayFormatDetailValue = getDefaultBirthdayFormatDetailValueForUpdate();

            defaultBirthdayFormatDetailValue.setIsDefault(false);
            updateBirthdayFormatFromValue(defaultBirthdayFormatDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var birthdayFormat = BirthdayFormatFactory.getInstance().create();
        var birthdayFormatDetail = BirthdayFormatDetailFactory.getInstance().create(birthdayFormat,
                birthdayFormatName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        birthdayFormat = BirthdayFormatFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                birthdayFormat.getPrimaryKey());
        birthdayFormat.setActiveDetail(birthdayFormatDetail);
        birthdayFormat.setLastDetail(birthdayFormatDetail);
        birthdayFormat.store();

        sendEvent(birthdayFormat.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return birthdayFormat;
    }

    private List<BirthdayFormat> getBirthdayFormats(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM birthdayformats, birthdayformatdetails " +
                    "WHERE bdyf_activedetailid = bdyfdt_birthdayformatdetailid " +
                    "ORDER BY bdyfdt_sortorder, bdyfdt_birthdayformatname " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM birthdayformats, birthdayformatdetails " +
                    "WHERE bdyf_activedetailid = bdyfdt_birthdayformatdetailid " +
                    "FOR UPDATE";
        }

        var ps = BirthdayFormatFactory.getInstance().prepareStatement(query);

        return BirthdayFormatFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }

    public List<BirthdayFormat> getBirthdayFormats() {
        return getBirthdayFormats(EntityPermission.READ_ONLY);
    }

    public List<BirthdayFormat> getBirthdayFormatsForUpdate() {
        return getBirthdayFormats(EntityPermission.READ_WRITE);
    }

    private List<BirthdayFormat> getBirthdayFormatsByEntityType(EntityType entityType, EntityPermission entityPermission) {
        List<BirthdayFormat> birthdayFormats;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM birthdayformats, birthdayformatdetails, birthdayformatentitytypes " +
                        "WHERE bdyf_activedetailid = bdyfdt_birthdayformatdetailid " +
                        "AND bdyf_birthdayformatid = tent_bdyf_birthdayformatid AND tent_ent_entitytypeid = ? AND tent_thrutime = ? " +
                        "ORDER BY bdyfdt_sortorder, bdyfdt_birthdayformatname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM birthdayformats, birthdayformatdetails, birthdayformatentitytypes " +
                        "WHERE bdyf_activedetailid = bdyfdt_birthdayformatdetailid " +
                        "AND bdyf_birthdayformatid = tent_bdyf_birthdayformatid AND tent_ent_entitytypeid = ? AND tent_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = BirthdayFormatFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            birthdayFormats = BirthdayFormatFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return birthdayFormats;
    }

    public List<BirthdayFormat> getBirthdayFormatsByEntityType(EntityType entityType) {
        return getBirthdayFormatsByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<BirthdayFormat> getBirthdayFormatsByEntityTypeForUpdate(EntityType entityType) {
        return getBirthdayFormatsByEntityType(entityType, EntityPermission.READ_WRITE);
    }

    private BirthdayFormat getDefaultBirthdayFormat(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM birthdayformats, birthdayformatdetails " +
                    "WHERE bdyf_activedetailid = bdyfdt_birthdayformatdetailid AND bdyfdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM birthdayformats, birthdayformatdetails " +
                    "WHERE bdyf_activedetailid = bdyfdt_birthdayformatdetailid AND bdyfdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = BirthdayFormatFactory.getInstance().prepareStatement(query);

        return BirthdayFormatFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }

    public BirthdayFormat getDefaultBirthdayFormat() {
        return getDefaultBirthdayFormat(EntityPermission.READ_ONLY);
    }

    public BirthdayFormat getDefaultBirthdayFormatForUpdate() {
        return getDefaultBirthdayFormat(EntityPermission.READ_WRITE);
    }

    public BirthdayFormatDetailValue getDefaultBirthdayFormatDetailValueForUpdate() {
        return getDefaultBirthdayFormatForUpdate().getLastDetailForUpdate().getBirthdayFormatDetailValue().clone();
    }

    private BirthdayFormat getBirthdayFormatByName(String birthdayFormatName, EntityPermission entityPermission) {
        BirthdayFormat birthdayFormat;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM birthdayformats, birthdayformatdetails " +
                        "WHERE bdyf_activedetailid = bdyfdt_birthdayformatdetailid AND bdyfdt_birthdayformatname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM birthdayformats, birthdayformatdetails " +
                        "WHERE bdyf_activedetailid = bdyfdt_birthdayformatdetailid AND bdyfdt_birthdayformatname = ? " +
                        "FOR UPDATE";
            }

            var ps = BirthdayFormatFactory.getInstance().prepareStatement(query);

            ps.setString(1, birthdayFormatName);

            birthdayFormat = BirthdayFormatFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return birthdayFormat;
    }

    public BirthdayFormat getBirthdayFormatByName(String birthdayFormatName) {
        return getBirthdayFormatByName(birthdayFormatName, EntityPermission.READ_ONLY);
    }

    public BirthdayFormat getBirthdayFormatByNameForUpdate(String birthdayFormatName) {
        return getBirthdayFormatByName(birthdayFormatName, EntityPermission.READ_WRITE);
    }

    public BirthdayFormatDetailValue getBirthdayFormatDetailValueForUpdate(BirthdayFormat birthdayFormat) {
        return birthdayFormat == null? null: birthdayFormat.getLastDetailForUpdate().getBirthdayFormatDetailValue().clone();
    }

    public BirthdayFormatDetailValue getBirthdayFormatDetailValueByNameForUpdate(String birthdayFormatName) {
        return getBirthdayFormatDetailValueForUpdate(getBirthdayFormatByNameForUpdate(birthdayFormatName));
    }

    public BirthdayFormatChoicesBean getBirthdayFormatChoices(String defaultBirthdayFormatChoice, Language language, boolean allowNullChoice) {
        var birthdayFormats = getBirthdayFormats();
        var size = birthdayFormats.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultBirthdayFormatChoice == null) {
                defaultValue = "";
            }
        }

        for(var birthdayFormat : birthdayFormats) {
            var birthdayFormatDetail = birthdayFormat.getLastDetail();
            var label = getBestBirthdayFormatDescription(birthdayFormat, language);
            var value = birthdayFormatDetail.getBirthdayFormatName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultBirthdayFormatChoice != null && defaultBirthdayFormatChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && birthdayFormatDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new BirthdayFormatChoicesBean(labels, values, defaultValue);
    }

    public BirthdayFormatTransfer getBirthdayFormatTransfer(UserVisit userVisit, BirthdayFormat birthdayFormat) {
        return getPartyTransferCaches().getBirthdayFormatTransferCache().getTransfer(userVisit, birthdayFormat);
    }

    public List<BirthdayFormatTransfer> getBirthdayFormatTransfers(UserVisit userVisit, Collection<BirthdayFormat> birthdayFormats) {
        List<BirthdayFormatTransfer> birthdayFormatTransfers = new ArrayList<>(birthdayFormats.size());
        var birthdayFormatTransferCache = getPartyTransferCaches().getBirthdayFormatTransferCache();

        birthdayFormats.forEach((birthdayFormat) ->
                birthdayFormatTransfers.add(birthdayFormatTransferCache.getTransfer(userVisit, birthdayFormat))
        );

        return birthdayFormatTransfers;
    }

    public List<BirthdayFormatTransfer> getBirthdayFormatTransfers(UserVisit userVisit) {
        return getBirthdayFormatTransfers(userVisit, getBirthdayFormats());
    }

    public List<BirthdayFormatTransfer> getBirthdayFormatTransfersByEntityType(UserVisit userVisit, EntityType entityType) {
        return getBirthdayFormatTransfers(userVisit, getBirthdayFormatsByEntityType(entityType));
    }

    private void updateBirthdayFormatFromValue(BirthdayFormatDetailValue birthdayFormatDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(birthdayFormatDetailValue.hasBeenModified()) {
            var birthdayFormat = BirthdayFormatFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     birthdayFormatDetailValue.getBirthdayFormatPK());
            var birthdayFormatDetail = birthdayFormat.getActiveDetailForUpdate();

            birthdayFormatDetail.setThruTime(session.START_TIME_LONG);
            birthdayFormatDetail.store();

            var birthdayFormatPK = birthdayFormatDetail.getBirthdayFormatPK();
            var birthdayFormatName = birthdayFormatDetailValue.getBirthdayFormatName();
            var isDefault = birthdayFormatDetailValue.getIsDefault();
            var sortOrder = birthdayFormatDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultBirthdayFormat = getDefaultBirthdayFormat();
                var defaultFound = defaultBirthdayFormat != null && !defaultBirthdayFormat.equals(birthdayFormat);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultBirthdayFormatDetailValue = getDefaultBirthdayFormatDetailValueForUpdate();

                    defaultBirthdayFormatDetailValue.setIsDefault(false);
                    updateBirthdayFormatFromValue(defaultBirthdayFormatDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            birthdayFormatDetail = BirthdayFormatDetailFactory.getInstance().create(birthdayFormatPK, birthdayFormatName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            birthdayFormat.setActiveDetail(birthdayFormatDetail);
            birthdayFormat.setLastDetail(birthdayFormatDetail);

            sendEvent(birthdayFormatPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateBirthdayFormatFromValue(BirthdayFormatDetailValue birthdayFormatDetailValue, BasePK updatedBy) {
        updateBirthdayFormatFromValue(birthdayFormatDetailValue, true, updatedBy);
    }

    public void deleteBirthdayFormat(BirthdayFormat birthdayFormat, BasePK deletedBy) {
        deleteBirthdayFormatDescriptionsByBirthdayFormat(birthdayFormat, deletedBy);

        var birthdayFormatDetail = birthdayFormat.getLastDetailForUpdate();
        birthdayFormatDetail.setThruTime(session.START_TIME_LONG);
        birthdayFormat.setActiveDetail(null);
        birthdayFormat.store();

        // Check for default, and pick one if necessary
        var defaultBirthdayFormat = getDefaultBirthdayFormat();
        if(defaultBirthdayFormat == null) {
            var birthdayFormats = getBirthdayFormatsForUpdate();

            if(!birthdayFormats.isEmpty()) {
                var iter = birthdayFormats.iterator();
                if(iter.hasNext()) {
                    defaultBirthdayFormat = iter.next();
                }
                var birthdayFormatDetailValue = Objects.requireNonNull(defaultBirthdayFormat).getLastDetailForUpdate().getBirthdayFormatDetailValue().clone();

                birthdayFormatDetailValue.setIsDefault(true);
                updateBirthdayFormatFromValue(birthdayFormatDetailValue, false, deletedBy);
            }
        }

        sendEvent(birthdayFormat.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Birthday Format Descriptions
    // --------------------------------------------------------------------------------

    public BirthdayFormatDescription createBirthdayFormatDescription(BirthdayFormat birthdayFormat, Language language, String description,
            BasePK createdBy) {
        var birthdayFormatDescription = BirthdayFormatDescriptionFactory.getInstance().create(birthdayFormat,
                language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(birthdayFormat.getPrimaryKey(), EventTypes.MODIFY, birthdayFormatDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return birthdayFormatDescription;
    }

    private BirthdayFormatDescription getBirthdayFormatDescription(BirthdayFormat birthdayFormat, Language language, EntityPermission entityPermission) {
        BirthdayFormatDescription birthdayFormatDescription;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM birthdayformatdescriptions " +
                        "WHERE bdyfd_bdyf_birthdayformatid = ? AND bdyfd_lang_languageid = ? AND bdyfd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM birthdayformatdescriptions " +
                        "WHERE bdyfd_bdyf_birthdayformatid = ? AND bdyfd_lang_languageid = ? AND bdyfd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = BirthdayFormatDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, birthdayFormat.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            birthdayFormatDescription = BirthdayFormatDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return birthdayFormatDescription;
    }

    public BirthdayFormatDescription getBirthdayFormatDescription(BirthdayFormat birthdayFormat, Language language) {
        return getBirthdayFormatDescription(birthdayFormat, language, EntityPermission.READ_ONLY);
    }

    public BirthdayFormatDescription getBirthdayFormatDescriptionForUpdate(BirthdayFormat birthdayFormat, Language language) {
        return getBirthdayFormatDescription(birthdayFormat, language, EntityPermission.READ_WRITE);
    }

    public BirthdayFormatDescriptionValue getBirthdayFormatDescriptionValue(BirthdayFormatDescription birthdayFormatDescription) {
        return birthdayFormatDescription == null? null: birthdayFormatDescription.getBirthdayFormatDescriptionValue().clone();
    }

    public BirthdayFormatDescriptionValue getBirthdayFormatDescriptionValueForUpdate(BirthdayFormat birthdayFormat, Language language) {
        return getBirthdayFormatDescriptionValue(getBirthdayFormatDescriptionForUpdate(birthdayFormat, language));
    }

    private List<BirthdayFormatDescription> getBirthdayFormatDescriptionsByBirthdayFormat(BirthdayFormat birthdayFormat, EntityPermission entityPermission) {
        List<BirthdayFormatDescription> birthdayFormatDescriptions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM birthdayformatdescriptions, languages " +
                        "WHERE bdyfd_bdyf_birthdayformatid = ? AND bdyfd_thrutime = ? " +
                        "AND bdyfd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM birthdayformatdescriptions " +
                        "WHERE bdyfd_bdyf_birthdayformatid = ? AND bdyfd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = BirthdayFormatDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, birthdayFormat.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            birthdayFormatDescriptions = BirthdayFormatDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return birthdayFormatDescriptions;
    }

    public List<BirthdayFormatDescription> getBirthdayFormatDescriptionsByBirthdayFormat(BirthdayFormat birthdayFormat) {
        return getBirthdayFormatDescriptionsByBirthdayFormat(birthdayFormat, EntityPermission.READ_ONLY);
    }

    public List<BirthdayFormatDescription> getBirthdayFormatDescriptionsByBirthdayFormatForUpdate(BirthdayFormat birthdayFormat) {
        return getBirthdayFormatDescriptionsByBirthdayFormat(birthdayFormat, EntityPermission.READ_WRITE);
    }

    public String getBestBirthdayFormatDescription(BirthdayFormat birthdayFormat, Language language) {
        String description;
        var birthdayFormatDescription = getBirthdayFormatDescription(birthdayFormat, language);

        if(birthdayFormatDescription == null && !language.getIsDefault()) {
            birthdayFormatDescription = getBirthdayFormatDescription(birthdayFormat, partyControl.getDefaultLanguage());
        }

        if(birthdayFormatDescription == null) {
            description = birthdayFormat.getLastDetail().getBirthdayFormatName();
        } else {
            description = birthdayFormatDescription.getDescription();
        }

        return description;
    }

    public BirthdayFormatDescriptionTransfer getBirthdayFormatDescriptionTransfer(UserVisit userVisit, BirthdayFormatDescription birthdayFormatDescription) {
        return getPartyTransferCaches().getBirthdayFormatDescriptionTransferCache().getTransfer(userVisit, birthdayFormatDescription);
    }

    public List<BirthdayFormatDescriptionTransfer> getBirthdayFormatDescriptionTransfers(UserVisit userVisit, BirthdayFormat birthdayFormat) {
        var birthdayFormatDescriptions = getBirthdayFormatDescriptionsByBirthdayFormat(birthdayFormat);
        List<BirthdayFormatDescriptionTransfer> birthdayFormatDescriptionTransfers = new ArrayList<>(birthdayFormatDescriptions.size());
        var birthdayFormatDescriptionTransferCache = getPartyTransferCaches().getBirthdayFormatDescriptionTransferCache();

        birthdayFormatDescriptions.forEach((birthdayFormatDescription) ->
                birthdayFormatDescriptionTransfers.add(birthdayFormatDescriptionTransferCache.getTransfer(userVisit, birthdayFormatDescription))
        );

        return birthdayFormatDescriptionTransfers;
    }

    public void updateBirthdayFormatDescriptionFromValue(BirthdayFormatDescriptionValue birthdayFormatDescriptionValue, BasePK updatedBy) {
        if(birthdayFormatDescriptionValue.hasBeenModified()) {
            var birthdayFormatDescription = BirthdayFormatDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     birthdayFormatDescriptionValue.getPrimaryKey());

            birthdayFormatDescription.setThruTime(session.START_TIME_LONG);
            birthdayFormatDescription.store();

            var birthdayFormat = birthdayFormatDescription.getBirthdayFormat();
            var language = birthdayFormatDescription.getLanguage();
            var description = birthdayFormatDescriptionValue.getDescription();

            birthdayFormatDescription = BirthdayFormatDescriptionFactory.getInstance().create(birthdayFormat, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(birthdayFormat.getPrimaryKey(), EventTypes.MODIFY, birthdayFormatDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteBirthdayFormatDescription(BirthdayFormatDescription birthdayFormatDescription, BasePK deletedBy) {
        birthdayFormatDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(birthdayFormatDescription.getBirthdayFormatPK(), EventTypes.MODIFY, birthdayFormatDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteBirthdayFormatDescriptionsByBirthdayFormat(BirthdayFormat birthdayFormat, BasePK deletedBy) {
        var birthdayFormatDescriptions = getBirthdayFormatDescriptionsByBirthdayFormatForUpdate(birthdayFormat);

        birthdayFormatDescriptions.forEach((birthdayFormatDescription) -> 
                deleteBirthdayFormatDescription(birthdayFormatDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Profiles
    // --------------------------------------------------------------------------------
    
    public Profile createProfile(Party party, String nickname, Icon icon, String pronunciation, Gender gender, String pronouns,
            Integer birthday, BirthdayFormat birthdayFormat, String occupation, String hobbies, String location, MimeType bioMimeType,
            String bio, MimeType signatureMimeType, String signature, BasePK createdBy) {
        var profile = ProfileFactory.getInstance().create(party, nickname, icon, pronunciation, gender, pronouns,
                birthday, birthdayFormat, occupation, hobbies, location, bioMimeType, bio, signatureMimeType, signature,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, profile.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return profile;
    }
    
    private Profile getProfile(Party party, EntityPermission entityPermission) {
        Profile profile;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM profiles " +
                        "WHERE prfl_par_partyid = ? AND prfl_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM profiles " +
                        "WHERE prfl_par_partyid = ? AND prfl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ProfileFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            profile = ProfileFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return profile;
    }
    
    public Profile getProfile(Party party) {
        return getProfile(party, EntityPermission.READ_ONLY);
    }
    
    public Profile getProfileForUpdate(Party party) {
        return getProfile(party, EntityPermission.READ_WRITE);
    }
    
    public ProfileValue getProfileValue(Profile profile) {
        return profile == null ? null : profile.getProfileValue().clone();
    }

    public ProfileValue getProfileValueForUpdate(Party party) {
        return getProfileForUpdate(party).getProfileValue().clone();
    }
    
    private Profile getProfileByNickname(String nickname, EntityPermission entityPermission) {
        Profile profile;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM profiles " +
                        "WHERE prfl_nickname = ? AND prfl_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM profiles " +
                        "WHERE prfl_nickname = ? AND prfl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ProfileFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, nickname);
            ps.setLong(2, Session.MAX_TIME);
            
            profile = ProfileFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return profile;
    }
    
    public Profile getProfileByNickname(String nickname) {
        return getProfileByNickname(nickname, EntityPermission.READ_ONLY);
    }
    
    public Profile getProfileByNicknameForUpdate(String nickname) {
        return getProfileByNickname(nickname, EntityPermission.READ_WRITE);
    }
    
    public void updateProfileFromValue(ProfileValue profileValue, BasePK updatedBy) {
        if(profileValue.hasBeenModified()) {
            var profile = ProfileFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    profileValue.getPrimaryKey());
            
            profile.setThruTime(session.START_TIME_LONG);
            profile.store();

            var partyPK = profile.getPartyPK();
            var nickname = profileValue.getNickname();
            var iconPK = profileValue.getIconPK();
            var pronunciation = profileValue.getPronunciation();
            var genderPK = profileValue.getGenderPK();
            var pronouns = profileValue.getPronouns();
            var birthday = profileValue.getBirthday();
            var birthdayFormatPK = profileValue.getBirthdayFormatPK();
            var occupation = profileValue.getOccupation();
            var hobbies = profileValue.getHobbies();
            var location = profileValue.getLocation();
            var bioMimeTypePK = profileValue.getBioMimeTypePK();
            var bio = profileValue.getBio();
            var signatureMimeTypePK = profileValue.getSignatureMimeTypePK();
            var signature = profileValue.getSignature();
            
            profile = ProfileFactory.getInstance().create(partyPK, nickname, iconPK, pronunciation, genderPK, pronouns,
                    birthday, birthdayFormatPK, occupation, hobbies, location, bioMimeTypePK, bio, signatureMimeTypePK,
                    signature, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(partyPK, EventTypes.MODIFY, profile.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteProfile(Profile profile, BasePK deletedBy) {
        profile.setThruTime(session.START_TIME_LONG);
        profile.store();
        
        sendEvent(profile.getPartyPK(), EventTypes.MODIFY, profile.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteProfileByParty(Party party, BasePK deletedBy) {
        var profile = getProfileForUpdate(party);
        
        if(profile != null) {
            deleteProfile(profile, deletedBy);
        }
    }
    
    public ProfileTransfer getProfileTransfer(UserVisit userVisit, Profile profile) {
        return getPartyTransferCaches().getProfileTransferCache().getTransfer(userVisit, profile);
    }
    
}
