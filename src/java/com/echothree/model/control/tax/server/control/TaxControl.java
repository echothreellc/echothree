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

package com.echothree.model.control.tax.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.tax.common.choice.TaxClassificationChoicesBean;
import com.echothree.model.control.tax.common.transfer.GeoCodeTaxTransfer;
import com.echothree.model.control.tax.common.transfer.ItemTaxClassificationTransfer;
import com.echothree.model.control.tax.common.transfer.TaxClassificationTransfer;
import com.echothree.model.control.tax.common.transfer.TaxClassificationTranslationTransfer;
import com.echothree.model.control.tax.common.transfer.TaxDescriptionTransfer;
import com.echothree.model.control.tax.common.transfer.TaxTransfer;
import com.echothree.model.control.tax.server.transfer.GeoCodeTaxTransferCache;
import com.echothree.model.control.tax.server.transfer.ItemTaxClassificationTransferCache;
import com.echothree.model.control.tax.server.transfer.TaxClassificationTransferCache;
import com.echothree.model.control.tax.server.transfer.TaxClassificationTranslationTransferCache;
import com.echothree.model.control.tax.server.transfer.TaxDescriptionTransferCache;
import com.echothree.model.control.tax.server.transfer.TaxTransferCache;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tax.common.pk.TaxClassificationPK;
import com.echothree.model.data.tax.server.entity.GeoCodeTax;
import com.echothree.model.data.tax.server.entity.ItemTaxClassification;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.tax.server.entity.TaxClassification;
import com.echothree.model.data.tax.server.entity.TaxClassificationTranslation;
import com.echothree.model.data.tax.server.entity.TaxDescription;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.echothree.util.server.cdi.CommandScope;
import javax.inject.Inject;

@CommandScope
public class TaxControl
        extends BaseModelControl {
    
    /** Creates a new instance of TaxControl */
    protected TaxControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Tax Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    TaxClassificationTransferCache taxClassificationTransferCache;

    @Inject
    TaxClassificationTranslationTransferCache taxClassificationTranslationTransferCache;

    @Inject
    ItemTaxClassificationTransferCache itemTaxClassificationTransferCache;

    @Inject
    TaxTransferCache taxTransferCache;

    @Inject
    TaxDescriptionTransferCache taxDescriptionTransferCache;

    @Inject
    GeoCodeTaxTransferCache geoCodeTaxTransferCache;

    // --------------------------------------------------------------------------------
    //   Tax Classifications
    // --------------------------------------------------------------------------------

    public TaxClassification createTaxClassification(GeoCode countryGeoCode, String taxClassificationName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultTaxClassification = getDefaultTaxClassification(countryGeoCode);
        var defaultFound = defaultTaxClassification != null;

        if(defaultFound && isDefault) {
            var defaultTaxClassificationDetailValue = getDefaultTaxClassificationDetailValueForUpdate(countryGeoCode);

            defaultTaxClassificationDetailValue.setIsDefault(false);
            updateTaxClassificationFromValue(defaultTaxClassificationDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var taxClassification = TaxClassificationFactory.getInstance().create();
        var taxClassificationDetail = TaxClassificationDetailFactory.getInstance().create(session, taxClassification, countryGeoCode,
                taxClassificationName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

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

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TaxClassification */
    public TaxClassification getTaxClassificationByEntityInstance(EntityInstance entityInstance) {
        var pk = new TaxClassificationPK(entityInstance.getEntityUniqueId());
        var taxClassification = TaxClassificationFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
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
        var taxClassificationes = getTaxClassificationsByCountryGeoCode(countryGeoCode);
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
            var taxClassificationDetail = taxClassification.getLastDetail();
            var taxClassificationName = taxClassificationDetail.getTaxClassificationName();
            var taxClassificationTranslation = getBestTaxClassificationTranslation(taxClassification, language);
            
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
        return taxClassificationTransferCache.getTransfer(userVisit, taxClassification);
    }

    public List<TaxClassificationTransfer> getTaxClassificationTransfers(UserVisit userVisit, Collection<TaxClassification> taxClassifications) {
        List<TaxClassificationTransfer> taxClassificationTransfers = new ArrayList<>(taxClassifications.size());

        taxClassifications.forEach((taxClassification) ->
                taxClassificationTransfers.add(taxClassificationTransferCache.getTransfer(userVisit, taxClassification))
        );

        return taxClassificationTransfers;
    }

    public List<TaxClassificationTransfer> getTaxClassificationTransfersByCountryGeoCode(UserVisit userVisit, GeoCode countryGeoCode) {
        return getTaxClassificationTransfers(userVisit, getTaxClassificationsByCountryGeoCode(countryGeoCode));
    }

    private void updateTaxClassificationFromValue(TaxClassificationDetailValue taxClassificationDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(taxClassificationDetailValue.hasBeenModified()) {
            var taxClassification = TaxClassificationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     taxClassificationDetailValue.getTaxClassificationPK());
            var taxClassificationDetail = taxClassification.getActiveDetailForUpdate();

            taxClassificationDetail.setThruTime(session.getStartTime());
            taxClassificationDetail.store();

            var taxClassificationPK = taxClassificationDetail.getTaxClassificationPK();
            var countryGeoCode = taxClassificationDetail.getCountryGeoCode();
            var countryGeoCodePK = countryGeoCode.getPrimaryKey();
            var taxClassificationName = taxClassificationDetailValue.getTaxClassificationName();
            var isDefault = taxClassificationDetailValue.getIsDefault();
            var sortOrder = taxClassificationDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultTaxClassification = getDefaultTaxClassification(countryGeoCode);
                var defaultFound = defaultTaxClassification != null && !defaultTaxClassification.equals(taxClassification);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultTaxClassificationDetailValue = getDefaultTaxClassificationDetailValueForUpdate(countryGeoCode);

                    defaultTaxClassificationDetailValue.setIsDefault(false);
                    updateTaxClassificationFromValue(defaultTaxClassificationDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            taxClassificationDetail = TaxClassificationDetailFactory.getInstance().create(taxClassificationPK, countryGeoCodePK, taxClassificationName,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

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

        var taxClassificationDetail = taxClassification.getLastDetailForUpdate();
        taxClassificationDetail.setThruTime(session.getStartTime());
        taxClassification.setActiveDetail(null);
        taxClassification.store();

        // Check for default, and pick one if necessary
        var countryGeoCode = taxClassificationDetail.getCountryGeoCode();
        var defaultTaxClassification = getDefaultTaxClassification(countryGeoCode);
        if(defaultTaxClassification == null) {
            var taxClassifications = getTaxClassificationsByCountryGeoCode(countryGeoCode);

            if(!taxClassifications.isEmpty()) {
                var iter = taxClassifications.iterator();
                if(iter.hasNext()) {
                    defaultTaxClassification = iter.next();
                }
                var taxClassificationDetailValue = Objects.requireNonNull(defaultTaxClassification).getLastDetailForUpdate().getTaxClassificationDetailValue().clone();

                taxClassificationDetailValue.setIsDefault(true);
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
        var taxClassificationTranslation = TaxClassificationTranslationFactory.getInstance().create(taxClassification,
                language, description, overviewMimeType, overview, session.getStartTime(), Session.MAX_TIME);

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
        var taxClassificationTranslation = getTaxClassificationTranslation(taxClassification, language);
        
        if(taxClassificationTranslation == null && !language.getIsDefault()) {
            taxClassificationTranslation = getTaxClassificationTranslation(taxClassification, partyControl.getDefaultLanguage());
        }
        
        return taxClassificationTranslation;
    }
    
    public TaxClassificationTranslationTransfer getTaxClassificationTranslationTransfer(UserVisit userVisit, TaxClassificationTranslation taxClassificationTranslation) {
        return taxClassificationTranslationTransferCache.getTransfer(userVisit, taxClassificationTranslation);
    }

    public List<TaxClassificationTranslationTransfer> getTaxClassificationTranslationTransfersByTaxClassification(UserVisit userVisit, TaxClassification taxClassification) {
        var taxClassificationTranslations = getTaxClassificationTranslationsByTaxClassification(taxClassification);
        List<TaxClassificationTranslationTransfer> taxClassificationTranslationTransfers = new ArrayList<>(taxClassificationTranslations.size());

        taxClassificationTranslations.forEach((taxClassificationTranslation) -> {
            taxClassificationTranslationTransfers.add(taxClassificationTranslationTransferCache.getTransfer(userVisit, taxClassificationTranslation));
        });

        return taxClassificationTranslationTransfers;
    }

    public void updateTaxClassificationTranslationFromValue(TaxClassificationTranslationValue taxClassificationTranslationValue, BasePK updatedBy) {
        if(taxClassificationTranslationValue.hasBeenModified()) {
            var taxClassificationTranslation = TaxClassificationTranslationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     taxClassificationTranslationValue.getPrimaryKey());

            taxClassificationTranslation.setThruTime(session.getStartTime());
            taxClassificationTranslation.store();

            var taxClassificationPK = taxClassificationTranslation.getTaxClassificationPK();
            var languagePK = taxClassificationTranslation.getLanguagePK();
            var description = taxClassificationTranslationValue.getDescription();
            var overviewMimeTypePK = taxClassificationTranslationValue.getOverviewMimeTypePK();
            var overview = taxClassificationTranslationValue.getOverview();

            taxClassificationTranslation = TaxClassificationTranslationFactory.getInstance().create(taxClassificationPK,
                    languagePK, description, overviewMimeTypePK, overview, session.getStartTime(), Session.MAX_TIME);

            sendEvent(taxClassificationPK, EventTypes.MODIFY, taxClassificationTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteTaxClassificationTranslation(TaxClassificationTranslation taxClassificationTranslation, BasePK deletedBy) {
        taxClassificationTranslation.setThruTime(session.getStartTime());

        sendEvent(taxClassificationTranslation.getTaxClassificationPK(), EventTypes.MODIFY, taxClassificationTranslation.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteTaxClassificationTranslationsByTaxClassification(TaxClassification taxClassification, BasePK deletedBy) {
        var taxClassificationTranslations = getTaxClassificationTranslationsByTaxClassificationForUpdate(taxClassification);

        taxClassificationTranslations.forEach((taxClassificationTranslation) -> 
                deleteTaxClassificationTranslation(taxClassificationTranslation, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Item Tax Classifications
    // --------------------------------------------------------------------------------

    public ItemTaxClassification createItemTaxClassification(Item item, GeoCode countryGeoCode, TaxClassification taxClassification, BasePK createdBy) {
        var itemTaxClassification = ItemTaxClassificationFactory.getInstance().create();
        var itemTaxClassificationDetail = ItemTaxClassificationDetailFactory.getInstance().create(session,
                itemTaxClassification, item, countryGeoCode, taxClassification, session.getStartTime(),
                Session.MAX_TIME);

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
        return itemTaxClassificationTransferCache.getTransfer(userVisit, itemTaxClassification);
    }

    public List<ItemTaxClassificationTransfer> getItemTaxClassificationTransfers(UserVisit userVisit, Collection<ItemTaxClassification> itemTaxClassifications) {
        List<ItemTaxClassificationTransfer> itemTaxClassificationTransfers = new ArrayList<>(itemTaxClassifications.size());

        itemTaxClassifications.forEach((itemTaxClassification) ->
                itemTaxClassificationTransfers.add(itemTaxClassificationTransferCache.getTransfer(userVisit, itemTaxClassification))
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
            var itemTaxClassification = ItemTaxClassificationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     itemTaxClassificationDetailValue.getItemTaxClassificationPK());
            var itemTaxClassificationDetail = itemTaxClassification.getActiveDetailForUpdate();

            itemTaxClassificationDetail.setThruTime(session.getStartTime());
            itemTaxClassificationDetail.store();

            var itemTaxClassificationPK = itemTaxClassificationDetail.getItemTaxClassificationPK();
            var itemPK = itemTaxClassificationDetail.getItemPK();
            var countryGeoCodePK = itemTaxClassificationDetail.getCountryGeoCodePK();
            var taxClassificationPK = itemTaxClassificationDetailValue.getTaxClassificationPK();

            itemTaxClassificationDetail = ItemTaxClassificationDetailFactory.getInstance().create(itemTaxClassificationPK, itemPK, countryGeoCodePK,
                    taxClassificationPK, session.getStartTime(), Session.MAX_TIME);

            itemTaxClassification.setActiveDetail(itemTaxClassificationDetail);
            itemTaxClassification.setLastDetail(itemTaxClassificationDetail);

            sendEvent(itemPK, EventTypes.MODIFY, itemTaxClassificationPK, EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteItemTaxClassification(ItemTaxClassification itemTaxClassification, BasePK deletedBy) {
        var itemTaxClassificationDetail = itemTaxClassification.getLastDetailForUpdate();
        itemTaxClassificationDetail.setThruTime(session.getStartTime());
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
        var defaultTax = getDefaultTax();
        var defaultFound = defaultTax != null;
        
        if(defaultFound && isDefault) {
            var defaultTaxDetailValue = getDefaultTaxDetailValueForUpdate();
            
            defaultTaxDetailValue.setIsDefault(false);
            updateTaxFromValue(defaultTaxDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var tax = TaxFactory.getInstance().create();
        var taxDetail = TaxDetailFactory.getInstance().create(tax, taxName, contactMechanismPurpose, glAccount,
                includeShippingCharge, includeProcessingCharge, includeInsuranceCharge, percent, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);
        
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

        var ps = TaxFactory.getInstance().prepareStatement(query);
        
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

            var ps = TaxFactory.getInstance().prepareStatement(query);
            
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

        var ps = TaxFactory.getInstance().prepareStatement(query);
        
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
        return taxTransferCache.getTransfer(userVisit, tax);
    }
    
    public List<TaxTransfer> getTaxTransfers(UserVisit userVisit) {
        var taxes = getTaxes();
        List<TaxTransfer> taxTransfers = new ArrayList<>(taxes.size());
        
        taxes.forEach((tax) ->
                taxTransfers.add(taxTransferCache.getTransfer(userVisit, tax))
        );
        
        return taxTransfers;
    }
    
    private void updateTaxFromValue(TaxDetailValue taxDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(taxDetailValue.hasBeenModified()) {
            var tax = TaxFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     taxDetailValue.getTaxPK());
            var taxDetail = tax.getActiveDetailForUpdate();
            
            taxDetail.setThruTime(session.getStartTime());
            taxDetail.store();

            var taxPK = taxDetail.getTaxPK();
            var taxName = taxDetailValue.getTaxName();
            var contactMechanismPurposePK = taxDetailValue.getContactMechanismPurposePK();
            var glAccountPK = taxDetailValue.getGlAccountPK();
            var includeShippingCharge = taxDetailValue.getIncludeShippingCharge();
            var includeProcessingCharge = taxDetailValue.getIncludeProcessingCharge();
            var includeInsuranceCharge = taxDetailValue.getIncludeInsuranceCharge();
            var percent = taxDetailValue.getPercent();
            var isDefault = taxDetailValue.getIsDefault();
            var sortOrder = taxDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultTax = getDefaultTax();
                var defaultFound = defaultTax != null && !defaultTax.equals(tax);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultTaxDetailValue = getDefaultTaxDetailValueForUpdate();
                    
                    defaultTaxDetailValue.setIsDefault(false);
                    updateTaxFromValue(defaultTaxDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            taxDetail = TaxDetailFactory.getInstance().create(taxPK, taxName, contactMechanismPurposePK, glAccountPK,
                    includeShippingCharge, includeProcessingCharge, includeInsuranceCharge, percent, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);
            
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

        var taxDetail = tax.getLastDetailForUpdate();
        taxDetail.setThruTime(session.getStartTime());
        tax.setActiveDetail(null);
        tax.store();
        
        // Check for default, and pick one if necessary
        var defaultTax = getDefaultTax();
        if(defaultTax == null) {
            var taxes = getTaxesForUpdate();
            
            if(!taxes.isEmpty()) {
                var iter = taxes.iterator();
                if(iter.hasNext()) {
                    defaultTax = iter.next();
                }
                var taxDetailValue = Objects.requireNonNull(defaultTax).getLastDetailForUpdate().getTaxDetailValue().clone();
                
                taxDetailValue.setIsDefault(true);
                updateTaxFromValue(taxDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(tax.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Tax Descriptions
    // --------------------------------------------------------------------------------
    
    public TaxDescription createTaxDescription(Tax tax, Language language, String description, BasePK createdBy) {
        var taxDescription = TaxDescriptionFactory.getInstance().create(tax, language, description,
                session.getStartTime(), Session.MAX_TIME);
        
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

            var ps = TaxDescriptionFactory.getInstance().prepareStatement(query);
            
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

            var ps = TaxDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var taxDescription = getTaxDescription(tax, language);
        
        if(taxDescription == null && !language.getIsDefault()) {
            taxDescription = getTaxDescription(tax, partyControl.getDefaultLanguage());
        }
        
        if(taxDescription == null) {
            description = tax.getLastDetail().getTaxName();
        } else {
            description = taxDescription.getDescription();
        }
        
        return description;
    }
    
    public TaxDescriptionTransfer getTaxDescriptionTransfer(UserVisit userVisit, TaxDescription taxDescription) {
        return taxDescriptionTransferCache.getTransfer(userVisit, taxDescription);
    }
    
    public List<TaxDescriptionTransfer> getTaxDescriptionTransfersByTax(UserVisit userVisit, Tax tax) {
        var taxDescriptions = getTaxDescriptionsByTax(tax);
        List<TaxDescriptionTransfer> taxDescriptionTransfers = null;
        
        if(taxDescriptions != null) {
            taxDescriptionTransfers = new ArrayList<>(taxDescriptions.size());
            
            for(var taxDescription : taxDescriptions) {
                taxDescriptionTransfers.add(taxDescriptionTransferCache.getTransfer(userVisit, taxDescription));
            }
        }
        
        return taxDescriptionTransfers;
    }
    
    public void updateTaxDescriptionFromValue(TaxDescriptionValue taxDescriptionValue, BasePK updatedBy) {
        if(taxDescriptionValue.hasBeenModified()) {
            var taxDescription = TaxDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    taxDescriptionValue.getPrimaryKey());
            
            taxDescription.setThruTime(session.getStartTime());
            taxDescription.store();

            var tax = taxDescription.getTax();
            var language = taxDescription.getLanguage();
            var description = taxDescriptionValue.getDescription();
            
            taxDescription = TaxDescriptionFactory.getInstance().create(tax, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(tax.getPrimaryKey(), EventTypes.MODIFY, taxDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTaxDescription(TaxDescription taxDescription, BasePK deletedBy) {
        taxDescription.setThruTime(session.getStartTime());
        
        sendEvent(taxDescription.getTaxPK(), EventTypes.MODIFY,
                taxDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteTaxDescriptionsByTax(Tax tax, BasePK deletedBy) {
        var taxDescriptions = getTaxDescriptionsByTaxForUpdate(tax);
        
        taxDescriptions.forEach((taxDescription) -> 
                deleteTaxDescription(taxDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Taxes
    // --------------------------------------------------------------------------------
    
    public GeoCodeTax createGeoCodeTax(GeoCode geoCode, Tax tax, BasePK createdBy) {
        var geoCodeTax = GeoCodeTaxFactory.getInstance().create(geoCode, tax, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeTax.getPrimaryKey(), null, createdBy);
        sendEvent(tax.getPrimaryKey(), EventTypes.MODIFY, geoCodeTax.getPrimaryKey(), null, createdBy);
        
        return geoCodeTax;
    }
    
    public long countGeoCodeTaxesByGeoCode(GeoCode geoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM geocodetaxes " +
                "WHERE geotx_geo_geocodeid = ? AND geotx_thrutime = ?",
                geoCode, Session.MAX_TIME);
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

            var ps = GeoCodeTaxFactory.getInstance().prepareStatement(query);
            
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

            var ps = GeoCodeTaxFactory.getInstance().prepareStatement(query);
            
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

            var ps = GeoCodeTaxFactory.getInstance().prepareStatement(query);
            
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
        return geoCodeTaxTransferCache.getTransfer(userVisit, geoCodeTax);
    }
    
    private List<GeoCodeTaxTransfer> getGeoCodeTaxTransfers(UserVisit userVisit, Collection<GeoCodeTax> geoCodeTaxes) {
        List<GeoCodeTaxTransfer> geoCodeTaxTransfers = new ArrayList<>(geoCodeTaxes.size());
        
        geoCodeTaxes.forEach((geoCodeTax) -> {
            geoCodeTaxTransfers.add(geoCodeTaxTransferCache.getTransfer(userVisit, geoCodeTax));
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
        geoCodeTax.setThruTime(session.getStartTime());
        
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
