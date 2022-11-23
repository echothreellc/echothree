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

package com.echothree.model.control.tax.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.tax.common.choice.TaxClassificationChoicesBean;
import com.echothree.model.control.tax.common.transfer.GeoCodeTaxTransfer;
import com.echothree.model.control.tax.common.transfer.ItemTaxClassificationTransfer;
import com.echothree.model.control.tax.common.transfer.TaxClassificationTransfer;
import com.echothree.model.control.tax.common.transfer.TaxClassificationTranslationTransfer;
import com.echothree.model.control.tax.common.transfer.TaxDescriptionTransfer;
import com.echothree.model.control.tax.common.transfer.TaxTransfer;
import com.echothree.model.control.tax.server.transfer.ItemTaxClassificationTransferCache;
import com.echothree.model.control.tax.server.transfer.TaxClassificationTransferCache;
import com.echothree.model.control.tax.server.transfer.TaxTransferCache;
import com.echothree.model.control.tax.server.transfer.TaxTransferCaches;
import com.echothree.model.data.accounting.common.pk.GlAccountPK;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.contact.common.pk.ContactMechanismPurposePK;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.geo.common.pk.GeoCodePK;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.common.pk.ItemPK;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tax.common.pk.ItemTaxClassificationPK;
import com.echothree.model.data.tax.common.pk.TaxClassificationPK;
import com.echothree.model.data.tax.common.pk.TaxPK;
import com.echothree.model.data.tax.server.entity.GeoCodeTax;
import com.echothree.model.data.tax.server.entity.ItemTaxClassification;
import com.echothree.model.data.tax.server.entity.ItemTaxClassificationDetail;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.tax.server.entity.TaxClassification;
import com.echothree.model.data.tax.server.entity.TaxClassificationDetail;
import com.echothree.model.data.tax.server.entity.TaxClassificationTranslation;
import com.echothree.model.data.tax.server.entity.TaxDescription;
import com.echothree.model.data.tax.server.entity.TaxDetail;
import com.echothree.model.data.tax.server.factory.GeoCodeTaxFactory;
import com.echothree.model.data.tax.server.factory.ItemTaxClassificationDetailFactory;
import com.echothree.model.data.tax.server.factory.ItemTaxClassificationFactory;
import com.echothree.model.data.tax.server.factory.TaxClassificationDetailFactory;
import com.echothree.model.data.tax.server.factory.TaxClassificationFactory;
import com.echothree.model.data.tax.server.factory.TaxClassificationTranslationFactory;
import com.echothree.model.data.tax.server.factory.TaxDescriptionFactory;
import com.echothree.model.data.tax.server.factory.TaxDetailFactory;
import com.echothree.model.data.tax.server.factory.TaxFactory;
import com.echothree.model.data.tax.server.value.ItemTaxClassificationDetailValue;
import com.echothree.model.data.tax.server.value.TaxClassificationDetailValue;
import com.echothree.model.data.tax.server.value.TaxClassificationTranslationValue;
import com.echothree.model.data.tax.server.value.TaxDescriptionValue;
import com.echothree.model.data.tax.server.value.TaxDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TaxControl
        extends BaseModelControl {
    
    /** Creates a new instance of TaxControl */
    public TaxControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Tax Transfer Caches
    // --------------------------------------------------------------------------------
    
    private TaxTransferCaches taxTransferCaches;
    
    public TaxTransferCaches getTaxTransferCaches(UserVisit userVisit) {
        if(taxTransferCaches == null) {
            taxTransferCaches = new TaxTransferCaches(userVisit, this);
        }
        
        return taxTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Tax Classifications
    // --------------------------------------------------------------------------------

    public TaxClassification createTaxClassification(GeoCode countryGeoCode, String taxClassificationName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        TaxClassification defaultTaxClassification = getDefaultTaxClassification(countryGeoCode);
        boolean defaultFound = defaultTaxClassification != null;

        if(defaultFound && isDefault) {
            TaxClassificationDetailValue defaultTaxClassificationDetailValue = getDefaultTaxClassificationDetailValueForUpdate(countryGeoCode);

            defaultTaxClassificationDetailValue.setIsDefault(Boolean.FALSE);
            updateTaxClassificationFromValue(defaultTaxClassificationDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        TaxClassification taxClassification = TaxClassificationFactory.getInstance().create();
        TaxClassificationDetail taxClassificationDetail = TaxClassificationDetailFactory.getInstance().create(session, taxClassification, countryGeoCode,
                taxClassificationName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        taxClassification = TaxClassificationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                taxClassification.getPrimaryKey());
        taxClassification.setActiveDetail(taxClassificationDetail);
        taxClassification.setLastDetail(taxClassificationDetail);
        taxClassification.store();

        sendEvent(taxClassification.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return taxClassification;
    }

    public long countTaxClassificationsByCountryGeoCode(GeoCode countryGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM taxclassifications, taxclassificationdetails "
                + "WHERE txclsfn_activedetailid = txclsfndt_taxclassificationdetailid AND txclsfndt_countrygeocodeid = ?",
                countryGeoCode);
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.TaxClassification */
    public TaxClassification getTaxClassificationByEntityInstance(EntityInstance entityInstance) {
        TaxClassificationPK pk = new TaxClassificationPK(entityInstance.getEntityUniqueId());
        TaxClassification taxClassification = TaxClassificationFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return taxClassification;
    }
    
    public long countTaxClassifications() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM taxclassifications, taxclassificationdetails " +
                "WHERE txclsfn_activedetailid = txclsfndt_taxclassificationdetailid");
    }

    private static final Map<EntityPermission, String> getTaxClassificationsByCountryGeoCodeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM taxclassifications, taxclassificationdetails "
                + "WHERE txclsfn_activedetailid = txclsfndt_taxclassificationdetailid AND txclsfndt_countrygeocodeid = ? "
                + "ORDER BY txclsfndt_sortorder, txclsfndt_taxclassificationname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM taxclassifications, taxclassificationdetails "
                + "WHERE txclsfn_activedetailid = txclsfndt_taxclassificationdetailid AND txclsfndt_countrygeocodeid = ? "
                + "FOR UPDATE");
        getTaxClassificationsByCountryGeoCodeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TaxClassification> getTaxClassificationsByCountryGeoCode(GeoCode countryGeoCode, EntityPermission entityPermission) {
        return TaxClassificationFactory.getInstance().getEntitiesFromQuery(entityPermission, getTaxClassificationsByCountryGeoCodeQueries,
                countryGeoCode);
    }

    public List<TaxClassification> getTaxClassificationsByCountryGeoCode(GeoCode countryGeoCode) {
        return getTaxClassificationsByCountryGeoCode(countryGeoCode, EntityPermission.READ_ONLY);
    }

    public List<TaxClassification> getTaxClassificationsByCountryGeoCodeForUpdate(GeoCode countryGeoCode) {
        return getTaxClassificationsByCountryGeoCode(countryGeoCode, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultTaxClassificationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM taxclassifications, taxclassificationdetails "
                + "WHERE txclsfn_activedetailid = txclsfndt_taxclassificationdetailid "
                + "AND txclsfndt_countrygeocodeid = ? AND txclsfndt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM taxclassifications, taxclassificationdetails "
                + "WHERE txclsfn_activedetailid = txclsfndt_taxclassificationdetailid "
                + "AND txclsfndt_countrygeocodeid = ? AND txclsfndt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultTaxClassificationQueries = Collections.unmodifiableMap(queryMap);
    }

    private TaxClassification getDefaultTaxClassification(GeoCode countryGeoCode, EntityPermission entityPermission) {
        return TaxClassificationFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultTaxClassificationQueries,
                countryGeoCode);
    }

    public TaxClassification getDefaultTaxClassification(GeoCode countryGeoCode) {
        return getDefaultTaxClassification(countryGeoCode, EntityPermission.READ_ONLY);
    }

    public TaxClassification getDefaultTaxClassificationForUpdate(GeoCode countryGeoCode) {
        return getDefaultTaxClassification(countryGeoCode, EntityPermission.READ_WRITE);
    }

    public TaxClassificationDetailValue getDefaultTaxClassificationDetailValueForUpdate(GeoCode countryGeoCode) {
        return getDefaultTaxClassificationForUpdate(countryGeoCode).getLastDetailForUpdate().getTaxClassificationDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getTaxClassificationByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM taxclassifications, taxclassificationdetails "
                + "WHERE txclsfn_activedetailid = txclsfndt_taxclassificationdetailid "
                + "AND txclsfndt_countrygeocodeid = ? AND txclsfndt_taxclassificationname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM taxclassifications, taxclassificationdetails "
                + "WHERE txclsfn_activedetailid = txclsfndt_taxclassificationdetailid "
                + "AND txclsfndt_countrygeocodeid = ? AND txclsfndt_taxclassificationname = ? "
                + "FOR UPDATE");
        getTaxClassificationByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private TaxClassification getTaxClassificationByName(GeoCode countryGeoCode, String taxClassificationName, EntityPermission entityPermission) {
        return TaxClassificationFactory.getInstance().getEntityFromQuery(entityPermission, getTaxClassificationByNameQueries,
                countryGeoCode, taxClassificationName);
    }

    public TaxClassification getTaxClassificationByName(GeoCode countryGeoCode, String taxClassificationName) {
        return getTaxClassificationByName(countryGeoCode, taxClassificationName, EntityPermission.READ_ONLY);
    }

    public TaxClassification getTaxClassificationByNameForUpdate(GeoCode countryGeoCode, String taxClassificationName) {
        return getTaxClassificationByName(countryGeoCode, taxClassificationName, EntityPermission.READ_WRITE);
    }

    public TaxClassificationDetailValue getTaxClassificationDetailValueForUpdate(TaxClassification taxClassification) {
        return taxClassification == null? null: taxClassification.getLastDetailForUpdate().getTaxClassificationDetailValue().clone();
    }

    public TaxClassificationDetailValue getTaxClassificationDetailValueByNameForUpdate(GeoCode countryGeoCode, String taxClassificationName) {
        return getTaxClassificationDetailValueForUpdate(getTaxClassificationByNameForUpdate(countryGeoCode, taxClassificationName));
    }

    public TaxClassificationChoicesBean getTaxClassificationChoices(String defaultTaxClassificationChoice, Language language, boolean allowNullChoice, GeoCode countryGeoCode) {
        List<TaxClassification> taxClassificationes = getTaxClassificationsByCountryGeoCode(countryGeoCode);
        var size = taxClassificationes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultTaxClassificationChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var taxClassification : taxClassificationes) {
            TaxClassificationDetail taxClassificationDetail = taxClassification.getLastDetail();
            String taxClassificationName = taxClassificationDetail.getTaxClassificationName();
            TaxClassificationTranslation taxClassificationTranslation = getBestTaxClassificationTranslation(taxClassification, language);
            
            var label = taxClassificationTranslation == null ? taxClassificationName : taxClassificationTranslation.getDescription();
            var value = taxClassificationName;
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultTaxClassificationChoice != null && defaultTaxClassificationChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && taxClassificationDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new TaxClassificationChoicesBean(labels, values, defaultValue);
    }
    
    public TaxClassificationTransfer getTaxClassificationTransfer(UserVisit userVisit, TaxClassification taxClassification) {
        return getTaxTransferCaches(userVisit).getTaxClassificationTransferCache().getTransfer(taxClassification);
    }

    public List<TaxClassificationTransfer> getTaxClassificationTransfers(UserVisit userVisit, Collection<TaxClassification> taxClassifications) {
        List<TaxClassificationTransfer> taxClassificationTransfers = new ArrayList<>(taxClassifications.size());
        TaxClassificationTransferCache taxClassificationTransferCache = getTaxTransferCaches(userVisit).getTaxClassificationTransferCache();

        taxClassifications.forEach((taxClassification) ->
                taxClassificationTransfers.add(taxClassificationTransferCache.getTransfer(taxClassification))
        );

        return taxClassificationTransfers;
    }

    public List<TaxClassificationTransfer> getTaxClassificationTransfersByCountryGeoCode(UserVisit userVisit, GeoCode countryGeoCode) {
        return getTaxClassificationTransfers(userVisit, getTaxClassificationsByCountryGeoCode(countryGeoCode));
    }

    private void updateTaxClassificationFromValue(TaxClassificationDetailValue taxClassificationDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(taxClassificationDetailValue.hasBeenModified()) {
            TaxClassification taxClassification = TaxClassificationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     taxClassificationDetailValue.getTaxClassificationPK());
            TaxClassificationDetail taxClassificationDetail = taxClassification.getActiveDetailForUpdate();

            taxClassificationDetail.setThruTime(session.START_TIME_LONG);
            taxClassificationDetail.store();

            TaxClassificationPK taxClassificationPK = taxClassificationDetail.getTaxClassificationPK();
            GeoCode countryGeoCode = taxClassificationDetail.getCountryGeoCode();
            GeoCodePK countryGeoCodePK = countryGeoCode.getPrimaryKey();
            String taxClassificationName = taxClassificationDetailValue.getTaxClassificationName();
            Boolean isDefault = taxClassificationDetailValue.getIsDefault();
            Integer sortOrder = taxClassificationDetailValue.getSortOrder();

            if(checkDefault) {
                TaxClassification defaultTaxClassification = getDefaultTaxClassification(countryGeoCode);
                boolean defaultFound = defaultTaxClassification != null && !defaultTaxClassification.equals(taxClassification);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    TaxClassificationDetailValue defaultTaxClassificationDetailValue = getDefaultTaxClassificationDetailValueForUpdate(countryGeoCode);

                    defaultTaxClassificationDetailValue.setIsDefault(Boolean.FALSE);
                    updateTaxClassificationFromValue(defaultTaxClassificationDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            taxClassificationDetail = TaxClassificationDetailFactory.getInstance().create(taxClassificationPK, countryGeoCodePK, taxClassificationName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            taxClassification.setActiveDetail(taxClassificationDetail);
            taxClassification.setLastDetail(taxClassificationDetail);

            sendEvent(taxClassificationPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateTaxClassificationFromValue(TaxClassificationDetailValue taxClassificationDetailValue, BasePK updatedBy) {
        updateTaxClassificationFromValue(taxClassificationDetailValue, true, updatedBy);
    }

    public void deleteTaxClassification(TaxClassification taxClassification, BasePK deletedBy) {
        deleteTaxClassificationTranslationsByTaxClassification(taxClassification, deletedBy);
        deleteItemTaxClassificationsByTaxClassification(taxClassification, deletedBy);
        
        TaxClassificationDetail taxClassificationDetail = taxClassification.getLastDetailForUpdate();
        taxClassificationDetail.setThruTime(session.START_TIME_LONG);
        taxClassification.setActiveDetail(null);
        taxClassification.store();

        // Check for default, and pick one if necessary
        GeoCode countryGeoCode = taxClassificationDetail.getCountryGeoCode();
        TaxClassification defaultTaxClassification = getDefaultTaxClassification(countryGeoCode);
        if(defaultTaxClassification == null) {
            List<TaxClassification> taxClassifications = getTaxClassificationsByCountryGeoCode(countryGeoCode);

            if(!taxClassifications.isEmpty()) {
                Iterator<TaxClassification> iter = taxClassifications.iterator();
                if(iter.hasNext()) {
                    defaultTaxClassification = iter.next();
                }
                TaxClassificationDetailValue taxClassificationDetailValue = Objects.requireNonNull(defaultTaxClassification).getLastDetailForUpdate().getTaxClassificationDetailValue().clone();

                taxClassificationDetailValue.setIsDefault(Boolean.TRUE);
                updateTaxClassificationFromValue(taxClassificationDetailValue, false, deletedBy);
            }
        }

        sendEvent(taxClassification.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteTaxClassifications(List<TaxClassification> taxClassifications, BasePK deletedBy) {
        taxClassifications.forEach((taxClassification) -> 
                deleteTaxClassification(taxClassification, deletedBy)
        );
    }

    public void deleteTaxClassificationsByCountryGeoCode(GeoCode countryGeoCode, BasePK deletedBy) {
        deleteTaxClassifications(getTaxClassificationsByCountryGeoCodeForUpdate(countryGeoCode), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Tax Classification Translations
    // --------------------------------------------------------------------------------

    public TaxClassificationTranslation createTaxClassificationTranslation(TaxClassification taxClassification,
            Language language, String description, MimeType overviewMimeType, String overview, BasePK createdBy) {
        TaxClassificationTranslation taxClassificationTranslation = TaxClassificationTranslationFactory.getInstance().create(taxClassification,
                language, description, overviewMimeType, overview, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(taxClassification.getPrimaryKey(), EventTypes.MODIFY, taxClassificationTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return taxClassificationTranslation;
    }

    private static final Map<EntityPermission, String> getTaxClassificationTranslationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM taxclassificationtranslations "
                + "WHERE txclsfntr_txclsfn_taxclassificationid = ? AND txclsfntr_lang_languageid = ? AND txclsfntr_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM taxclassificationtranslations "
                + "WHERE txclsfntr_txclsfn_taxclassificationid = ? AND txclsfntr_lang_languageid = ? AND txclsfntr_thrutime = ? "
                + "FOR UPDATE");
        getTaxClassificationTranslationQueries = Collections.unmodifiableMap(queryMap);
    }

    private TaxClassificationTranslation getTaxClassificationTranslation(TaxClassification taxClassification, Language language, EntityPermission entityPermission) {
        return TaxClassificationTranslationFactory.getInstance().getEntityFromQuery(entityPermission, getTaxClassificationTranslationQueries,
                taxClassification, language, Session.MAX_TIME);
    }

    public TaxClassificationTranslation getTaxClassificationTranslation(TaxClassification taxClassification, Language language) {
        return getTaxClassificationTranslation(taxClassification, language, EntityPermission.READ_ONLY);
    }

    public TaxClassificationTranslation getTaxClassificationTranslationForUpdate(TaxClassification taxClassification, Language language) {
        return getTaxClassificationTranslation(taxClassification, language, EntityPermission.READ_WRITE);
    }

    public TaxClassificationTranslationValue getTaxClassificationTranslationValue(TaxClassificationTranslation taxClassificationTranslation) {
        return taxClassificationTranslation == null? null: taxClassificationTranslation.getTaxClassificationTranslationValue().clone();
    }

    public TaxClassificationTranslationValue getTaxClassificationTranslationValueForUpdate(TaxClassification taxClassification, Language language) {
        return getTaxClassificationTranslationValue(getTaxClassificationTranslationForUpdate(taxClassification, language));
    }

    private static final Map<EntityPermission, String> getTaxClassificationTranslationsByTaxClassificationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM taxclassificationtranslations, languages "
                + "WHERE txclsfntr_txclsfn_taxclassificationid = ? AND txclsfntr_thrutime = ? AND txclsfntr_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM taxclassificationtranslations "
                + "WHERE txclsfntr_txclsfn_taxclassificationid = ? AND txclsfntr_thrutime = ? "
                + "FOR UPDATE");
        getTaxClassificationTranslationsByTaxClassificationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TaxClassificationTranslation> getTaxClassificationTranslationsByTaxClassification(TaxClassification taxClassification, EntityPermission entityPermission) {
        return TaxClassificationTranslationFactory.getInstance().getEntitiesFromQuery(entityPermission, getTaxClassificationTranslationsByTaxClassificationQueries,
                taxClassification, Session.MAX_TIME);
    }

    public List<TaxClassificationTranslation> getTaxClassificationTranslationsByTaxClassification(TaxClassification taxClassification) {
        return getTaxClassificationTranslationsByTaxClassification(taxClassification, EntityPermission.READ_ONLY);
    }

    public List<TaxClassificationTranslation> getTaxClassificationTranslationsByTaxClassificationForUpdate(TaxClassification taxClassification) {
        return getTaxClassificationTranslationsByTaxClassification(taxClassification, EntityPermission.READ_WRITE);
    }

    public TaxClassificationTranslation getBestTaxClassificationTranslation(TaxClassification taxClassification, Language language) {
        TaxClassificationTranslation taxClassificationTranslation = getTaxClassificationTranslation(taxClassification, language);
        
        if(taxClassificationTranslation == null && !language.getIsDefault()) {
            taxClassificationTranslation = getTaxClassificationTranslation(taxClassification, getPartyControl().getDefaultLanguage());
        }
        
        return taxClassificationTranslation;
    }
    
    public TaxClassificationTranslationTransfer getTaxClassificationTranslationTransfer(UserVisit userVisit, TaxClassificationTranslation taxClassificationTranslation) {
        return getTaxTransferCaches(userVisit).getTaxClassificationTranslationTransferCache().getTransfer(taxClassificationTranslation);
    }

    public List<TaxClassificationTranslationTransfer> getTaxClassificationTranslationTransfersByTaxClassification(UserVisit userVisit, TaxClassification taxClassification) {
        List<TaxClassificationTranslation> taxClassificationTranslations = getTaxClassificationTranslationsByTaxClassification(taxClassification);
        List<TaxClassificationTranslationTransfer> taxClassificationTranslationTransfers = new ArrayList<>(taxClassificationTranslations.size());

        taxClassificationTranslations.forEach((taxClassificationTranslation) -> {
            taxClassificationTranslationTransfers.add(getTaxTransferCaches(userVisit).getTaxClassificationTranslationTransferCache().getTransfer(taxClassificationTranslation));
        });

        return taxClassificationTranslationTransfers;
    }

    public void updateTaxClassificationTranslationFromValue(TaxClassificationTranslationValue taxClassificationTranslationValue, BasePK updatedBy) {
        if(taxClassificationTranslationValue.hasBeenModified()) {
            TaxClassificationTranslation taxClassificationTranslation = TaxClassificationTranslationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     taxClassificationTranslationValue.getPrimaryKey());

            taxClassificationTranslation.setThruTime(session.START_TIME_LONG);
            taxClassificationTranslation.store();

            TaxClassificationPK taxClassificationPK = taxClassificationTranslation.getTaxClassificationPK();
            LanguagePK languagePK = taxClassificationTranslation.getLanguagePK();
            String description = taxClassificationTranslationValue.getDescription();
            MimeTypePK overviewMimeTypePK = taxClassificationTranslationValue.getOverviewMimeTypePK();
            String overview = taxClassificationTranslationValue.getOverview();

            taxClassificationTranslation = TaxClassificationTranslationFactory.getInstance().create(taxClassificationPK,
                    languagePK, description, overviewMimeTypePK, overview, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(taxClassificationPK, EventTypes.MODIFY, taxClassificationTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteTaxClassificationTranslation(TaxClassificationTranslation taxClassificationTranslation, BasePK deletedBy) {
        taxClassificationTranslation.setThruTime(session.START_TIME_LONG);

        sendEvent(taxClassificationTranslation.getTaxClassificationPK(), EventTypes.MODIFY, taxClassificationTranslation.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteTaxClassificationTranslationsByTaxClassification(TaxClassification taxClassification, BasePK deletedBy) {
        List<TaxClassificationTranslation> taxClassificationTranslations = getTaxClassificationTranslationsByTaxClassificationForUpdate(taxClassification);

        taxClassificationTranslations.forEach((taxClassificationTranslation) -> 
                deleteTaxClassificationTranslation(taxClassificationTranslation, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Item Tax Classifications
    // --------------------------------------------------------------------------------

    public ItemTaxClassification createItemTaxClassification(Item item, GeoCode countryGeoCode, TaxClassification taxClassification, BasePK createdBy) {
        ItemTaxClassification itemTaxClassification = ItemTaxClassificationFactory.getInstance().create();
        ItemTaxClassificationDetail itemTaxClassificationDetail = ItemTaxClassificationDetailFactory.getInstance().create(session,
                itemTaxClassification, item, countryGeoCode, taxClassification, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        itemTaxClassification = ItemTaxClassificationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                itemTaxClassification.getPrimaryKey());
        itemTaxClassification.setActiveDetail(itemTaxClassificationDetail);
        itemTaxClassification.setLastDetail(itemTaxClassificationDetail);
        itemTaxClassification.store();

        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemTaxClassification.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemTaxClassification;
    }

    private static final Map<EntityPermission, String> getItemTaxClassificationsByItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM itemtaxclassifications, itemtaxclassificationdetails, geocodes, geocodedetails "
                + "WHERE itmtxclsfn_activedetailid = itmtxclsfndt_itemtaxclassificationdetailid AND itmtxclsfndt_itm_itemid = ? "
                + "AND itmtxclsfndt_countrygeocodeid = geo_geocodeid AND geo_lastdetailid = geodt_geocodedetailid "
                + "ORDER BY geodt_sortorder, geodt_geocodename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM itemtaxclassifications, itemtaxclassificationdetails "
                + "WHERE itmtxclsfn_activedetailid = itmtxclsfndt_itemtaxclassificationdetailid AND itmtxclsfndt_itm_itemid = ? "
                + "FOR UPDATE");
        getItemTaxClassificationsByItemQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemTaxClassification> getItemTaxClassificationsByItem(Item item, EntityPermission entityPermission) {
        return ItemTaxClassificationFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemTaxClassificationsByItemQueries,
                item);
    }

    public List<ItemTaxClassification> getItemTaxClassificationsByItem(Item item) {
        return getItemTaxClassificationsByItem(item, EntityPermission.READ_ONLY);
    }

    public List<ItemTaxClassification> getItemTaxClassificationsByItemForUpdate(Item item) {
        return getItemTaxClassificationsByItem(item, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getItemTaxClassificationsByCountryGeoCodeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM itemtaxclassifications, itemtaxclassificationdetails, items, itemdetails "
                + "WHERE itmtxclsfn_activedetailid = itmtxclsfndt_itemtaxclassificationdetailid AND itmtxclsfndt_countrygeocodeid = ? "
                + "AND itmtxclsfndt_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid "
                + "ORDER BY itmdt_itemname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM itemtaxclassifications, itemtaxclassificationdetails "
                + "WHERE itmtxclsfn_activedetailid = itmtxclsfndt_itemtaxclassificationdetailid AND itmtxclsfndt_countrygeocodeid = ? "
                + "FOR UPDATE");
        getItemTaxClassificationsByCountryGeoCodeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemTaxClassification> getItemTaxClassificationsByCountryGeoCode(GeoCode countryGeoCode, EntityPermission entityPermission) {
        return ItemTaxClassificationFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemTaxClassificationsByCountryGeoCodeQueries,
                countryGeoCode);
    }

    public List<ItemTaxClassification> getItemTaxClassificationsByCountryGeoCode(GeoCode countryGeoCode) {
        return getItemTaxClassificationsByCountryGeoCode(countryGeoCode, EntityPermission.READ_ONLY);
    }

    public List<ItemTaxClassification> getItemTaxClassificationsByCountryGeoCodeForUpdate(GeoCode countryGeoCode) {
        return getItemTaxClassificationsByCountryGeoCode(countryGeoCode, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getItemTaxClassificationsByTaxClassificationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM itemtaxclassifications, itemtaxclassificationdetails, items, itemdetails, geocodes, geocodedetails "
                + "WHERE itmtxclsfn_activedetailid = itmtxclsfndt_itemtaxclassificationdetailid AND itmtxclsfndt_hztsc_taxclassificationid = ? "
                + "AND itmtxclsfndt_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid "
                + "AND itmtxclsfndt_countrygeocodeid = geo_geocodeid AND geo_lastdetailid = geodt_geocodedetailid "
                + "ORDER BY itmdt_itemname, geodt_sortorder, geodt_geocodename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM itemtaxclassifications, itemtaxclassificationdetails "
                + "WHERE itmtxclsfn_activedetailid = itmtxclsfndt_itemtaxclassificationdetailid AND itmtxclsfndt_hztsc_taxclassificationid = ? "
                + "FOR UPDATE");
        getItemTaxClassificationsByTaxClassificationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ItemTaxClassification> getItemTaxClassificationsByTaxClassification(TaxClassification taxClassification, EntityPermission entityPermission) {
        return ItemTaxClassificationFactory.getInstance().getEntitiesFromQuery(entityPermission, getItemTaxClassificationsByTaxClassificationQueries,
                taxClassification);
    }

    public List<ItemTaxClassification> getItemTaxClassificationsByTaxClassification(TaxClassification taxClassification) {
        return getItemTaxClassificationsByTaxClassification(taxClassification, EntityPermission.READ_ONLY);
    }

    public List<ItemTaxClassification> getItemTaxClassificationsByTaxClassificationForUpdate(TaxClassification taxClassification) {
        return getItemTaxClassificationsByTaxClassification(taxClassification, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getItemTaxClassificationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM itemtaxclassifications, itemtaxclassificationdetails "
                + "WHERE itmtxclsfn_activedetailid = itmtxclsfndt_itemtaxclassificationdetailid "
                + "AND itmtxclsfndt_itm_itemid = ? AND itmtxclsfndt_countrygeocodeid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM itemtaxclassifications, itemtaxclassificationdetails "
                + "WHERE itmtxclsfn_activedetailid = itmtxclsfndt_itemtaxclassificationdetailid "
                + "AND itmtxclsfndt_itm_itemid = ? AND itmtxclsfndt_countrygeocodeid = ? "
                + "FOR UPDATE");
        getItemTaxClassificationQueries = Collections.unmodifiableMap(queryMap);
    }

    private ItemTaxClassification getItemTaxClassification(Item item, GeoCode countryGeoCode, EntityPermission entityPermission) {
        return ItemTaxClassificationFactory.getInstance().getEntityFromQuery(entityPermission, getItemTaxClassificationQueries,
                item, countryGeoCode);
    }

    public ItemTaxClassification getItemTaxClassification(Item item, GeoCode countryGeoCode) {
        return getItemTaxClassification(item, countryGeoCode, EntityPermission.READ_ONLY);
    }

    public ItemTaxClassification getItemTaxClassificationForUpdate(Item item, GeoCode countryGeoCode) {
        return getItemTaxClassification(item, countryGeoCode, EntityPermission.READ_WRITE);
    }

    public ItemTaxClassificationDetailValue getItemTaxClassificationDetailValueForUpdate(ItemTaxClassification itemTaxClassification) {
        return itemTaxClassification == null ? null : itemTaxClassification.getLastDetailForUpdate().getItemTaxClassificationDetailValue().clone();
    }

    public ItemTaxClassificationDetailValue getItemTaxClassificationDetailValueForUpdate(Item item, GeoCode countryGeoCode) {
        return getItemTaxClassificationDetailValueForUpdate(getItemTaxClassificationForUpdate(item, countryGeoCode));
    }

    public ItemTaxClassificationTransfer getItemTaxClassificationTransfer(UserVisit userVisit, ItemTaxClassification itemTaxClassification) {
        return getTaxTransferCaches(userVisit).getItemTaxClassificationTransferCache().getTransfer(itemTaxClassification);
    }

    public List<ItemTaxClassificationTransfer> getItemTaxClassificationTransfers(UserVisit userVisit, Collection<ItemTaxClassification> itemTaxClassifications) {
        List<ItemTaxClassificationTransfer> itemTaxClassificationTransfers = new ArrayList<>(itemTaxClassifications.size());
        ItemTaxClassificationTransferCache itemTaxClassificationTransferCache = getTaxTransferCaches(userVisit).getItemTaxClassificationTransferCache();

        itemTaxClassifications.forEach((itemTaxClassification) ->
                itemTaxClassificationTransfers.add(itemTaxClassificationTransferCache.getTransfer(itemTaxClassification))
        );

        return itemTaxClassificationTransfers;
    }

    public List<ItemTaxClassificationTransfer> getItemTaxClassificationTransfersByItem(UserVisit userVisit, Item item) {
        return getItemTaxClassificationTransfers(userVisit, getItemTaxClassificationsByItem(item));
    }

    public List<ItemTaxClassificationTransfer> getItemTaxClassificationTransfersByCountryGeoCode(UserVisit userVisit, GeoCode countryGeoCode) {
        return getItemTaxClassificationTransfers(userVisit, getItemTaxClassificationsByCountryGeoCode(countryGeoCode));
    }

    public List<ItemTaxClassificationTransfer> getItemTaxClassificationTransfersByTaxClassification(UserVisit userVisit, TaxClassification taxClassification) {
        return getItemTaxClassificationTransfers(userVisit, getItemTaxClassificationsByTaxClassification(taxClassification));
    }

    public void updateItemTaxClassificationFromValue(ItemTaxClassificationDetailValue itemTaxClassificationDetailValue,
            BasePK updatedBy) {
        if(itemTaxClassificationDetailValue.hasBeenModified()) {
            ItemTaxClassification itemTaxClassification = ItemTaxClassificationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemTaxClassificationDetailValue.getItemTaxClassificationPK());
            ItemTaxClassificationDetail itemTaxClassificationDetail = itemTaxClassification.getActiveDetailForUpdate();

            itemTaxClassificationDetail.setThruTime(session.START_TIME_LONG);
            itemTaxClassificationDetail.store();

            ItemTaxClassificationPK itemTaxClassificationPK = itemTaxClassificationDetail.getItemTaxClassificationPK();
            ItemPK itemPK = itemTaxClassificationDetail.getItemPK();
            GeoCodePK countryGeoCodePK = itemTaxClassificationDetail.getCountryGeoCodePK();
            TaxClassificationPK taxClassificationPK = itemTaxClassificationDetailValue.getTaxClassificationPK();

            itemTaxClassificationDetail = ItemTaxClassificationDetailFactory.getInstance().create(itemTaxClassificationPK, itemPK, countryGeoCodePK,
                    taxClassificationPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            itemTaxClassification.setActiveDetail(itemTaxClassificationDetail);
            itemTaxClassification.setLastDetail(itemTaxClassificationDetail);

            sendEvent(itemPK, EventTypes.MODIFY, itemTaxClassificationPK, EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemTaxClassification(ItemTaxClassification itemTaxClassification, BasePK deletedBy) {
        ItemTaxClassificationDetail itemTaxClassificationDetail = itemTaxClassification.getLastDetailForUpdate();
        itemTaxClassificationDetail.setThruTime(session.START_TIME_LONG);
        itemTaxClassification.setActiveDetail(null);
        itemTaxClassification.store();

        sendEvent(itemTaxClassificationDetail.getItemPK(), EventTypes.MODIFY, itemTaxClassification.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteItemTaxClassifications(List<ItemTaxClassification> itemTaxClassifications, BasePK deletedBy) {
        itemTaxClassifications.forEach((itemTaxClassification) -> 
                deleteItemTaxClassification(itemTaxClassification, deletedBy)
        );
    }

    public void deleteItemTaxClassificationsByItem(Item item, BasePK deletedBy) {
        deleteItemTaxClassifications(getItemTaxClassificationsByItem(item), deletedBy);
    }

    public void deleteItemTaxClassificationsByCountryGeoCode(GeoCode countryGeoCode, BasePK deletedBy) {
        deleteItemTaxClassifications(getItemTaxClassificationsByCountryGeoCode(countryGeoCode), deletedBy);
    }

    public void deleteItemTaxClassificationsByTaxClassification(TaxClassification taxClassification, BasePK deletedBy) {
        deleteItemTaxClassifications(getItemTaxClassificationsByTaxClassificationForUpdate(taxClassification), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Taxes
    // --------------------------------------------------------------------------------
    
    public Tax createTax(String taxName, ContactMechanismPurpose contactMechanismPurpose, GlAccount glAccount,
            Boolean includeShippingCharge, Boolean includeProcessingCharge, Boolean includeInsuranceCharge, Integer percent,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Tax defaultTax = getDefaultTax();
        boolean defaultFound = defaultTax != null;
        
        if(defaultFound && isDefault) {
            TaxDetailValue defaultTaxDetailValue = getDefaultTaxDetailValueForUpdate();
            
            defaultTaxDetailValue.setIsDefault(Boolean.FALSE);
            updateTaxFromValue(defaultTaxDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        Tax tax = TaxFactory.getInstance().create();
        TaxDetail taxDetail = TaxDetailFactory.getInstance().create(tax, taxName, contactMechanismPurpose, glAccount,
                includeShippingCharge, includeProcessingCharge, includeInsuranceCharge, percent, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        tax = TaxFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, tax.getPrimaryKey());
        tax.setActiveDetail(taxDetail);
        tax.setLastDetail(taxDetail);
        tax.store();
        
        sendEvent(tax.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return tax;
    }
    
    private List<Tax> getTaxes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM taxes, taxdetails " +
                    "WHERE tx_activedetailid = txdt_taxdetailid " +
                    "ORDER BY txdt_sortorder, txdt_taxname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM taxes, taxdetails " +
                    "WHERE tx_activedetailid = txdt_taxdetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TaxFactory.getInstance().prepareStatement(query);
        
        return TaxFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<Tax> getTaxes() {
        return getTaxes(EntityPermission.READ_ONLY);
    }
    
    public List<Tax> getTaxesForUpdate() {
        return getTaxes(EntityPermission.READ_WRITE);
    }
    
    private Tax getTaxByName(String taxName, EntityPermission entityPermission) {
        Tax tax;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM taxes, taxdetails " +
                        "WHERE tx_activedetailid = txdt_taxdetailid AND txdt_taxname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM taxes, taxdetails " +
                        "WHERE tx_activedetailid = txdt_taxdetailid AND txdt_taxname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TaxFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, taxName);
            
            tax = TaxFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return tax;
    }
    
    public Tax getTaxByName(String taxName) {
        return getTaxByName(taxName, EntityPermission.READ_ONLY);
    }
    
    public Tax getTaxByNameForUpdate(String taxName) {
        return getTaxByName(taxName, EntityPermission.READ_WRITE);
    }
    
    public TaxDetailValue getTaxDetailValueForUpdate(Tax tax) {
        return tax == null? null: tax.getLastDetailForUpdate().getTaxDetailValue().clone();
    }
    
    public TaxDetailValue getTaxDetailValueByNameForUpdate(String taxName) {
        return getTaxDetailValueForUpdate(getTaxByNameForUpdate(taxName));
    }
    
    private Tax getDefaultTax(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM taxes, taxdetails " +
                    "WHERE tx_activedetailid = txdt_taxdetailid AND txdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM taxes, taxdetails " +
                    "WHERE tx_activedetailid = txdt_taxdetailid AND txdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TaxFactory.getInstance().prepareStatement(query);
        
        return TaxFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public Tax getDefaultTax() {
        return getDefaultTax(EntityPermission.READ_ONLY);
    }
    
    public Tax getDefaultTaxForUpdate() {
        return getDefaultTax(EntityPermission.READ_WRITE);
    }
    
    public TaxDetailValue getDefaultTaxDetailValueForUpdate() {
        return getDefaultTaxForUpdate().getLastDetailForUpdate().getTaxDetailValue().clone();
    }
    
    public TaxTransfer getTaxTransfer(UserVisit userVisit, Tax tax) {
        return getTaxTransferCaches(userVisit).getTaxTransferCache().getTransfer(tax);
    }
    
    public List<TaxTransfer> getTaxTransfers(UserVisit userVisit) {
        List<Tax> taxes = getTaxes();
        List<TaxTransfer> taxTransfers = new ArrayList<>(taxes.size());
        TaxTransferCache taxTransferCache = getTaxTransferCaches(userVisit).getTaxTransferCache();
        
        taxes.forEach((tax) ->
                taxTransfers.add(taxTransferCache.getTransfer(tax))
        );
        
        return taxTransfers;
    }
    
    private void updateTaxFromValue(TaxDetailValue taxDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(taxDetailValue.hasBeenModified()) {
            Tax tax = TaxFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     taxDetailValue.getTaxPK());
            TaxDetail taxDetail = tax.getActiveDetailForUpdate();
            
            taxDetail.setThruTime(session.START_TIME_LONG);
            taxDetail.store();
            
            TaxPK taxPK = taxDetail.getTaxPK();
            String taxName = taxDetailValue.getTaxName();
            ContactMechanismPurposePK contactMechanismPurposePK = taxDetailValue.getContactMechanismPurposePK();
            GlAccountPK glAccountPK = taxDetailValue.getGlAccountPK();
            Boolean includeShippingCharge = taxDetailValue.getIncludeShippingCharge();
            Boolean includeProcessingCharge = taxDetailValue.getIncludeProcessingCharge();
            Boolean includeInsuranceCharge = taxDetailValue.getIncludeInsuranceCharge();
            Integer percent = taxDetailValue.getPercent();
            Boolean isDefault = taxDetailValue.getIsDefault();
            Integer sortOrder = taxDetailValue.getSortOrder();
            
            if(checkDefault) {
                Tax defaultTax = getDefaultTax();
                boolean defaultFound = defaultTax != null && !defaultTax.equals(tax);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    TaxDetailValue defaultTaxDetailValue = getDefaultTaxDetailValueForUpdate();
                    
                    defaultTaxDetailValue.setIsDefault(Boolean.FALSE);
                    updateTaxFromValue(defaultTaxDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            taxDetail = TaxDetailFactory.getInstance().create(taxPK, taxName, contactMechanismPurposePK, glAccountPK,
                    includeShippingCharge, includeProcessingCharge, includeInsuranceCharge, percent, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            tax.setActiveDetail(taxDetail);
            tax.setLastDetail(taxDetail);
            
            sendEvent(taxPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateTaxFromValue(TaxDetailValue taxDetailValue, BasePK updatedBy) {
        updateTaxFromValue(taxDetailValue, true, updatedBy);
    }
    
    public void deleteTax(Tax tax, BasePK deletedBy) {
        deleteTaxDescriptionsByTax(tax, deletedBy);
        deleteGeoCodeTaxesByTax(tax, deletedBy);
        
        TaxDetail taxDetail = tax.getLastDetailForUpdate();
        taxDetail.setThruTime(session.START_TIME_LONG);
        tax.setActiveDetail(null);
        tax.store();
        
        // Check for default, and pick one if necessary
        Tax defaultTax = getDefaultTax();
        if(defaultTax == null) {
            List<Tax> taxes = getTaxesForUpdate();
            
            if(!taxes.isEmpty()) {
                Iterator<Tax> iter = taxes.iterator();
                if(iter.hasNext()) {
                    defaultTax = iter.next();
                }
                TaxDetailValue taxDetailValue = Objects.requireNonNull(defaultTax).getLastDetailForUpdate().getTaxDetailValue().clone();
                
                taxDetailValue.setIsDefault(Boolean.TRUE);
                updateTaxFromValue(taxDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(tax.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Tax Descriptions
    // --------------------------------------------------------------------------------
    
    public TaxDescription createTaxDescription(Tax tax, Language language, String description, BasePK createdBy) {
        TaxDescription taxDescription = TaxDescriptionFactory.getInstance().create(tax, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(tax.getPrimaryKey(), EventTypes.MODIFY, taxDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return taxDescription;
    }
    
    private TaxDescription getTaxDescription(Tax tax, Language language, EntityPermission entityPermission) {
        TaxDescription taxDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM taxdescriptions " +
                        "WHERE txd_tx_taxid = ? AND txd_lang_languageid = ? AND txd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM taxdescriptions " +
                        "WHERE txd_tx_taxid = ? AND txd_lang_languageid = ? AND txd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TaxDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, tax.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            taxDescription = TaxDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return taxDescription;
    }
    
    public TaxDescription getTaxDescription(Tax tax, Language language) {
        return getTaxDescription(tax, language, EntityPermission.READ_ONLY);
    }
    
    public TaxDescription getTaxDescriptionForUpdate(Tax tax, Language language) {
        return getTaxDescription(tax, language, EntityPermission.READ_WRITE);
    }
    
    public TaxDescriptionValue getTaxDescriptionValue(TaxDescription taxDescription) {
        return taxDescription == null? null: taxDescription.getTaxDescriptionValue().clone();
    }
    
    public TaxDescriptionValue getTaxDescriptionValueForUpdate(Tax tax, Language language) {
        return getTaxDescriptionValue(getTaxDescriptionForUpdate(tax, language));
    }
    
    private List<TaxDescription> getTaxDescriptionsByTax(Tax tax, EntityPermission entityPermission) {
        List<TaxDescription> taxDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM taxdescriptions, languages " +
                        "WHERE txd_tx_taxid = ? AND txd_thrutime = ? AND txd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM taxdescriptions " +
                        "WHERE txd_tx_taxid = ? AND txd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TaxDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, tax.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            taxDescriptions = TaxDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return taxDescriptions;
    }
    
    public List<TaxDescription> getTaxDescriptionsByTax(Tax tax) {
        return getTaxDescriptionsByTax(tax, EntityPermission.READ_ONLY);
    }
    
    public List<TaxDescription> getTaxDescriptionsByTaxForUpdate(Tax tax) {
        return getTaxDescriptionsByTax(tax, EntityPermission.READ_WRITE);
    }
    
    public String getBestTaxDescription(Tax tax, Language language) {
        String description;
        TaxDescription taxDescription = getTaxDescription(tax, language);
        
        if(taxDescription == null && !language.getIsDefault()) {
            taxDescription = getTaxDescription(tax, getPartyControl().getDefaultLanguage());
        }
        
        if(taxDescription == null) {
            description = tax.getLastDetail().getTaxName();
        } else {
            description = taxDescription.getDescription();
        }
        
        return description;
    }
    
    public TaxDescriptionTransfer getTaxDescriptionTransfer(UserVisit userVisit, TaxDescription taxDescription) {
        return getTaxTransferCaches(userVisit).getTaxDescriptionTransferCache().getTransfer(taxDescription);
    }
    
    public List<TaxDescriptionTransfer> getTaxDescriptionTransfersByTax(UserVisit userVisit, Tax tax) {
        List<TaxDescription> taxDescriptions = getTaxDescriptionsByTax(tax);
        List<TaxDescriptionTransfer> taxDescriptionTransfers = null;
        
        if(taxDescriptions != null) {
            taxDescriptionTransfers = new ArrayList<>(taxDescriptions.size());
            
            for(var taxDescription : taxDescriptions) {
                taxDescriptionTransfers.add(getTaxTransferCaches(userVisit).getTaxDescriptionTransferCache().getTransfer(taxDescription));
            }
        }
        
        return taxDescriptionTransfers;
    }
    
    public void updateTaxDescriptionFromValue(TaxDescriptionValue taxDescriptionValue, BasePK updatedBy) {
        if(taxDescriptionValue.hasBeenModified()) {
            TaxDescription taxDescription = TaxDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    taxDescriptionValue.getPrimaryKey());
            
            taxDescription.setThruTime(session.START_TIME_LONG);
            taxDescription.store();
            
            Tax tax = taxDescription.getTax();
            Language language = taxDescription.getLanguage();
            String description = taxDescriptionValue.getDescription();
            
            taxDescription = TaxDescriptionFactory.getInstance().create(tax, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(tax.getPrimaryKey(), EventTypes.MODIFY, taxDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTaxDescription(TaxDescription taxDescription, BasePK deletedBy) {
        taxDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(taxDescription.getTaxPK(), EventTypes.MODIFY,
                taxDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteTaxDescriptionsByTax(Tax tax, BasePK deletedBy) {
        List<TaxDescription> taxDescriptions = getTaxDescriptionsByTaxForUpdate(tax);
        
        taxDescriptions.forEach((taxDescription) -> 
                deleteTaxDescription(taxDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Taxes
    // --------------------------------------------------------------------------------
    
    public GeoCodeTax createGeoCodeTax(GeoCode geoCode, Tax tax, BasePK createdBy) {
        GeoCodeTax geoCodeTax = GeoCodeTaxFactory.getInstance().create(geoCode, tax, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeTax.getPrimaryKey(), null, createdBy);
        sendEvent(tax.getPrimaryKey(), EventTypes.MODIFY, geoCodeTax.getPrimaryKey(), null, createdBy);
        
        return geoCodeTax;
    }
    
    public long countGeoCodeTaxesByGeoCode(GeoCode geoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM geocodetaxes " +
                "WHERE geotx_geo_geocodeid = ? AND geotx_thrutime = ?",
                geoCode, Session.MAX_TIME_LONG);
    }

    private GeoCodeTax getGeoCodeTax(GeoCode geoCode, Tax tax, EntityPermission entityPermission) {
        GeoCodeTax geoCodeTax;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetaxes " +
                        "WHERE geotx_geo_geocodeid = ? AND geotx_tx_taxid = ? AND geotx_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetaxes " +
                        "WHERE geotx_geo_geocodeid = ? AND geotx_tx_taxid = ? AND geotx_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GeoCodeTaxFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, tax.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            geoCodeTax = GeoCodeTaxFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeTax;
    }
    
    public GeoCodeTax getGeoCodeTax(GeoCode geoCode, Tax tax) {
        return getGeoCodeTax(geoCode, tax, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeTax getGeoCodeTaxForUpdate(GeoCode geoCode, Tax tax) {
        return getGeoCodeTax(geoCode, tax, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeTax> getGeoCodeTaxesByGeoCode(GeoCode geoCode, EntityPermission entityPermission) {
        List<GeoCodeTax> geoCodeTaxes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetaxes, taxes, taxdetails " +
                        "WHERE geotx_geo_geocodeid = ? AND geotx_thrutime = ? " +
                        "AND geotx_tx_taxid = tx_taxid AND tx_activedetailid = txdt_taxdetailid " +
                        "ORDER BY txdt_sortorder, txdt_taxname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetaxes, taxes, taxdetails " +
                        "WHERE geotx_geo_geocodeid = ? AND geotx_thrutime = ? " +
                        "AND geotx_tx_taxid = tx_taxid AND tx_activedetailid = txdt_taxdetailid " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GeoCodeTaxFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeTaxes = GeoCodeTaxFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeTaxes;
    }
    
    public List<GeoCodeTax> getGeoCodeTaxesByGeoCode(GeoCode geoCode) {
        return getGeoCodeTaxesByGeoCode(geoCode, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeTax> getGeoCodeTaxesByGeoCodeForUpdate(GeoCode geoCode) {
        return getGeoCodeTaxesByGeoCode(geoCode, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeTax> getGeoCodeTaxesByTax(Tax tax, EntityPermission entityPermission) {
        List<GeoCodeTax> geoCodeTaxes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetaxes, taxes, taxdetails " +
                        "WHERE geotx_tx_taxid = ? AND geotx_thrutime = ? " +
                        "AND geotx_tx_taxid = tx_taxid AND tx_activedetailid = txdt_taxdetailid " +
                        "ORDER BY txdt_sortorder, txdt_taxname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetaxes, taxes, taxdetails " +
                        "WHERE geotx_tx_taxid = ? AND geotx_thrutime = ? " +
                        "AND geotx_tx_taxid = tx_taxid AND tx_activedetailid = txdt_taxdetailid " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = GeoCodeTaxFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, tax.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeTaxes = GeoCodeTaxFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeTaxes;
    }
    
    public List<GeoCodeTax> getGeoCodeTaxesByTax(Tax tax) {
        return getGeoCodeTaxesByTax(tax, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeTax> getGeoCodeTaxesByTaxForUpdate(Tax tax) {
        return getGeoCodeTaxesByTax(tax, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeTaxTransfer getGeoCodeTaxTransfer(UserVisit userVisit, GeoCodeTax geoCodeTax) {
        return getTaxTransferCaches(userVisit).getGeoCodeTaxTransferCache().getTransfer(geoCodeTax);
    }
    
    private List<GeoCodeTaxTransfer> getGeoCodeTaxTransfers(UserVisit userVisit, Collection<GeoCodeTax> geoCodeTaxes) {
        List<GeoCodeTaxTransfer> geoCodeTaxTransfers = new ArrayList<>(geoCodeTaxes.size());
        
        geoCodeTaxes.forEach((geoCodeTax) -> {
            geoCodeTaxTransfers.add(getTaxTransferCaches(userVisit).getGeoCodeTaxTransferCache().getTransfer(geoCodeTax));
        });
        
        return geoCodeTaxTransfers;
    }
    
    public List<GeoCodeTaxTransfer> getGeoCodeTaxTransfersByGeoCode(UserVisit userVisit, GeoCode geoCode) {
        return getGeoCodeTaxTransfers(userVisit, getGeoCodeTaxesByGeoCode(geoCode));
    }
    
    public List<GeoCodeTaxTransfer> getGeoCodeTaxTransfersByTax(UserVisit userVisit, Tax tax) {
        return getGeoCodeTaxTransfers(userVisit, getGeoCodeTaxesByTax(tax));
    }
    
    public void deleteGeoCodeTax(GeoCodeTax geoCodeTax, BasePK deletedBy) {
        geoCodeTax.setThruTime(session.START_TIME_LONG);
        
        sendEvent(geoCodeTax.getGeoCodePK(), EventTypes.MODIFY, geoCodeTax.getPrimaryKey(), null, deletedBy);
        sendEvent(geoCodeTax.getTaxPK(), EventTypes.MODIFY, geoCodeTax.getPrimaryKey(), null, deletedBy);
    }
    
    private void deleteGeoCodeTaxes(List<GeoCodeTax> geoCodeTaxes, BasePK deletedBy) {
        geoCodeTaxes.forEach((geoCodeTax) -> 
                deleteGeoCodeTax(geoCodeTax, deletedBy)
        );
    }
    
    public void deleteGeoCodeTaxesByGeoCode(GeoCode geoCode, BasePK deletedBy) {
        deleteGeoCodeTaxes(getGeoCodeTaxesByGeoCodeForUpdate(geoCode), deletedBy);
    }
    
    public void deleteGeoCodeTaxesByTax(Tax tax, BasePK deletedBy) {
        deleteGeoCodeTaxes(getGeoCodeTaxesByTaxForUpdate(tax), deletedBy);
    }
    
}
