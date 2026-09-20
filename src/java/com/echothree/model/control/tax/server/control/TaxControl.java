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
import com.echothree.model.data.tax.common.pk.TaxPK;
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
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEMS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.tax.TaxForeignKeys.GEO_CODE_TAXES_GEO_CODE_FK;
import static com.echothree.model.jooq.server.keys.tax.TaxForeignKeys.GEO_CODE_TAXES_TAX_FK;
import static com.echothree.model.jooq.server.keys.tax.TaxForeignKeys.ITEM_TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.tax.TaxForeignKeys.ITEM_TAX_CLASSIFICATION_DETAILS_COUNTRY_GEO_CODE_FK;
import static com.echothree.model.jooq.server.keys.tax.TaxForeignKeys.ITEM_TAX_CLASSIFICATION_DETAILS_ITEM_FK;
import static com.echothree.model.jooq.server.keys.tax.TaxForeignKeys.TAXES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.tax.TaxForeignKeys.TAXES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.tax.TaxForeignKeys.TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.tax.TaxForeignKeys.TAX_CLASSIFICATION_TRANSLATIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.tax.TaxForeignKeys.TAX_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeDetails.GeoCodeDetails;
import static com.echothree.model.jooq.server.tables.geo.GeoCodes.GeoCodes;
import static com.echothree.model.jooq.server.tables.item.ItemDetails.ItemDetails;
import static com.echothree.model.jooq.server.tables.item.Items.Items;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.tax.GeoCodeTaxes.GeoCodeTaxes;
import static com.echothree.model.jooq.server.tables.tax.ItemTaxClassificationDetails.ItemTaxClassificationDetails;
import static com.echothree.model.jooq.server.tables.tax.ItemTaxClassifications.ItemTaxClassifications;
import static com.echothree.model.jooq.server.tables.tax.TaxClassificationDetails.TaxClassificationDetails;
import static com.echothree.model.jooq.server.tables.tax.TaxClassificationTranslations.TaxClassificationTranslations;
import static com.echothree.model.jooq.server.tables.tax.TaxClassifications.TaxClassifications;
import static com.echothree.model.jooq.server.tables.tax.TaxDescriptions.TaxDescriptions;
import static com.echothree.model.jooq.server.tables.tax.TaxDetails.TaxDetails;
import static com.echothree.model.jooq.server.tables.tax.Taxes.Taxes;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class TaxControl
        extends BaseModelControl {
    
    /** Creates a new instance of TaxControl */
    protected TaxControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Tax Classifications
    // --------------------------------------------------------------------------------

    @Inject
    protected TaxClassificationDetailFactory taxClassificationDetailFactory;

    @Inject
    protected TaxClassificationFactory taxClassificationFactory;

    @Inject
    private TaxClassificationTransferCache taxClassificationTransferCache;

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

        var taxClassification = taxClassificationFactory.create();
        var taxClassificationDetail = taxClassificationDetailFactory.create( taxClassification, countryGeoCode,
                taxClassificationName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        taxClassification = taxClassificationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                taxClassification.getPrimaryKey());
        taxClassification.setActiveDetail(taxClassificationDetail);
        taxClassification.setLastDetail(taxClassificationDetail);
        taxClassification.store();

        sendEvent(taxClassification.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return taxClassification;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TaxClassification */
    public TaxClassification getTaxClassificationByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TaxClassificationPK(entityInstance.getEntityUniqueId());

        return taxClassificationFactory.getEntityFromPK(entityPermission, pk);
    }

    public TaxClassification getTaxClassificationByEntityInstance(EntityInstance entityInstance) {
        return getTaxClassificationByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TaxClassification getTaxClassificationByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTaxClassificationByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTaxClassificationsByCountryGeoCode(GeoCode countryGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(TaxClassifications)
                .join(TaxClassificationDetails).onKey(TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                .where(TaxClassificationDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<TaxClassification> getTaxClassificationsByCountryGeoCode(GeoCode countryGeoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TaxClassifications.fields())
                .from(TaxClassifications)
                .join(TaxClassificationDetails).onKey(TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                .where(TaxClassificationDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TaxClassificationDetails.SORT_ORDER, TaxClassificationDetails.TAX_CLASSIFICATION_NAME),
                    TaxClassificationFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return taxClassificationFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<TaxClassification> getTaxClassificationsByCountryGeoCode(GeoCode countryGeoCode) {
        return getTaxClassificationsByCountryGeoCode(countryGeoCode, EntityPermission.READ_ONLY);
    }

    public List<TaxClassification> getTaxClassificationsByCountryGeoCodeForUpdate(GeoCode countryGeoCode) {
        return getTaxClassificationsByCountryGeoCode(countryGeoCode, EntityPermission.READ_WRITE);
    }

    private TaxClassification getDefaultTaxClassification(GeoCode countryGeoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TaxClassifications.fields())
                .from(TaxClassifications)
                .join(TaxClassificationDetails).onKey(TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                .where(TaxClassificationDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                        TaxClassificationDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return taxClassificationFactory.getEntityFromQuery(entityPermission, query);
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

    private TaxClassification getTaxClassificationByName(GeoCode countryGeoCode, String taxClassificationName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TaxClassifications.fields())
                .from(TaxClassifications)
                .join(TaxClassificationDetails).onKey(TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                .where(TaxClassificationDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()),
                        TaxClassificationDetails.TAX_CLASSIFICATION_NAME.eq(taxClassificationName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return taxClassificationFactory.getEntityFromQuery(entityPermission, query);
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
            var taxClassification = taxClassificationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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

            taxClassificationDetail = taxClassificationDetailFactory.create(taxClassificationPK, countryGeoCodePK, taxClassificationName,
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

    @Inject
    protected TaxClassificationTranslationFactory taxClassificationTranslationFactory;

    @Inject
    private TaxClassificationTranslationTransferCache taxClassificationTranslationTransferCache;

    public TaxClassificationTranslation createTaxClassificationTranslation(TaxClassification taxClassification,
            Language language, String description, MimeType overviewMimeType, String overview, BasePK createdBy) {
        var taxClassificationTranslation = taxClassificationTranslationFactory.create(taxClassification,
                language, description, overviewMimeType, overview, session.getStartTime(), Session.MAX_TIME);

        sendEvent(taxClassification.getPrimaryKey(), EventTypes.MODIFY, taxClassificationTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return taxClassificationTranslation;
    }

    private TaxClassificationTranslation getTaxClassificationTranslation(TaxClassification taxClassification, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TaxClassificationTranslations.fields())
                .from(TaxClassificationTranslations)
                .where(TaxClassificationTranslations.TAX_CLASSIFICATION.eq(taxClassification.getPrimaryKey()),
                        TaxClassificationTranslations.LANGUAGE.eq(language.getPrimaryKey()),
                        TaxClassificationTranslations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return taxClassificationTranslationFactory.getEntityFromQuery(entityPermission, query);
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

    private List<TaxClassificationTranslation> getTaxClassificationTranslationsByTaxClassification(TaxClassification taxClassification, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TaxClassificationTranslations.fields())
                    .from(TaxClassificationTranslations)
                    .join(Languages).onKey(TAX_CLASSIFICATION_TRANSLATIONS_LANGUAGE_FK)
                    .where(TaxClassificationTranslations.TAX_CLASSIFICATION.eq(taxClassification.getPrimaryKey()),
                            TaxClassificationTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    TaxClassificationTranslationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TaxClassificationTranslations.fields())
                    .from(TaxClassificationTranslations)
                    .where(TaxClassificationTranslations.TAX_CLASSIFICATION.eq(taxClassification.getPrimaryKey()),
                            TaxClassificationTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return taxClassificationTranslationFactory.getEntitiesFromQuery(entityPermission, query);
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
            var taxClassificationTranslation = taxClassificationTranslationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     taxClassificationTranslationValue.getPrimaryKey());

            taxClassificationTranslation.setThruTime(session.getStartTime());
            taxClassificationTranslation.store();

            var taxClassificationPK = taxClassificationTranslation.getTaxClassificationPK();
            var languagePK = taxClassificationTranslation.getLanguagePK();
            var description = taxClassificationTranslationValue.getDescription();
            var overviewMimeTypePK = taxClassificationTranslationValue.getOverviewMimeTypePK();
            var overview = taxClassificationTranslationValue.getOverview();

            taxClassificationTranslation = taxClassificationTranslationFactory.create(taxClassificationPK,
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

    @Inject
    protected ItemTaxClassificationDetailFactory itemTaxClassificationDetailFactory;

    @Inject
    protected ItemTaxClassificationFactory itemTaxClassificationFactory;

    @Inject
    private ItemTaxClassificationTransferCache itemTaxClassificationTransferCache;

    public ItemTaxClassification createItemTaxClassification(Item item, GeoCode countryGeoCode, TaxClassification taxClassification, BasePK createdBy) {
        var itemTaxClassification = itemTaxClassificationFactory.create();
        var itemTaxClassificationDetail = itemTaxClassificationDetailFactory.create(
                itemTaxClassification, item, countryGeoCode, taxClassification, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        itemTaxClassification = itemTaxClassificationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                itemTaxClassification.getPrimaryKey());
        itemTaxClassification.setActiveDetail(itemTaxClassificationDetail);
        itemTaxClassification.setLastDetail(itemTaxClassificationDetail);
        itemTaxClassification.store();

        sendEvent(item.getPrimaryKey(), EventTypes.MODIFY, itemTaxClassification.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return itemTaxClassification;
    }

    public long countItemTaxClassificationByItem(final Item item) {
        return session.getDslContext()
                .selectCount()
                .from(ItemTaxClassifications)
                .join(ItemTaxClassificationDetails).onKey(ITEM_TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                .where(ItemTaxClassificationDetails.ITEM.eq(item.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemTaxClassificationByCountryGeoCode(final GeoCode countryGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(ItemTaxClassifications)
                .join(ItemTaxClassificationDetails).onKey(ITEM_TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                .where(ItemTaxClassificationDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countItemTaxClassificationByTaxClassification(final TaxClassification taxClassification) {
        return session.getDslContext()
                .selectCount()
                .from(ItemTaxClassifications)
                .join(ItemTaxClassificationDetails).onKey(ITEM_TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                .where(ItemTaxClassificationDetails.TAX_CLASSIFICATION.eq(taxClassification.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<ItemTaxClassification> getItemTaxClassificationsByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemTaxClassifications.fields())
                    .from(ItemTaxClassifications)
                    .join(ItemTaxClassificationDetails).onKey(ITEM_TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                    .join(GeoCodes).onKey(ITEM_TAX_CLASSIFICATION_DETAILS_COUNTRY_GEO_CODE_FK)
                    .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                    .where(ItemTaxClassificationDetails.ITEM.eq(item.getPrimaryKey()))
                    .orderBy(GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME),
                    ItemTaxClassificationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemTaxClassifications.fields())
                    .from(ItemTaxClassifications)
                    .join(ItemTaxClassificationDetails).onKey(ITEM_TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                    .where(ItemTaxClassificationDetails.ITEM.eq(item.getPrimaryKey()))
                    .forUpdate();
        };

        return itemTaxClassificationFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemTaxClassification> getItemTaxClassificationsByItem(Item item) {
        return getItemTaxClassificationsByItem(item, EntityPermission.READ_ONLY);
    }

    public List<ItemTaxClassification> getItemTaxClassificationsByItemForUpdate(Item item) {
        return getItemTaxClassificationsByItem(item, EntityPermission.READ_WRITE);
    }

    private List<ItemTaxClassification> getItemTaxClassificationsByCountryGeoCode(GeoCode countryGeoCode, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemTaxClassifications.fields())
                    .from(ItemTaxClassifications)
                    .join(ItemTaxClassificationDetails).onKey(ITEM_TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                    .join(Items).onKey(ITEM_TAX_CLASSIFICATION_DETAILS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(ItemTaxClassificationDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()))
                    .orderBy(ItemDetails.ITEM_NAME),
                    ItemTaxClassificationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemTaxClassifications.fields())
                    .from(ItemTaxClassifications)
                    .join(ItemTaxClassificationDetails).onKey(ITEM_TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                    .where(ItemTaxClassificationDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()))
                    .forUpdate();
        };

        return itemTaxClassificationFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemTaxClassification> getItemTaxClassificationsByCountryGeoCode(GeoCode countryGeoCode) {
        return getItemTaxClassificationsByCountryGeoCode(countryGeoCode, EntityPermission.READ_ONLY);
    }

    public List<ItemTaxClassification> getItemTaxClassificationsByCountryGeoCodeForUpdate(GeoCode countryGeoCode) {
        return getItemTaxClassificationsByCountryGeoCode(countryGeoCode, EntityPermission.READ_WRITE);
    }

    private List<ItemTaxClassification> getItemTaxClassificationsByTaxClassification(TaxClassification taxClassification, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemTaxClassifications.fields())
                    .from(ItemTaxClassifications)
                    .join(ItemTaxClassificationDetails).onKey(ITEM_TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                    .join(Items).onKey(ITEM_TAX_CLASSIFICATION_DETAILS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(GeoCodes).onKey(ITEM_TAX_CLASSIFICATION_DETAILS_COUNTRY_GEO_CODE_FK)
                    .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                    .where(ItemTaxClassificationDetails.TAX_CLASSIFICATION.eq(taxClassification.getPrimaryKey()))
                    .orderBy(ItemDetails.ITEM_NAME, GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME),
                    ItemTaxClassificationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemTaxClassifications.fields())
                    .from(ItemTaxClassifications)
                    .join(ItemTaxClassificationDetails).onKey(ITEM_TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                    .where(ItemTaxClassificationDetails.TAX_CLASSIFICATION.eq(taxClassification.getPrimaryKey()))
                    .forUpdate();
        };

        return itemTaxClassificationFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemTaxClassification> getItemTaxClassificationsByTaxClassification(TaxClassification taxClassification) {
        return getItemTaxClassificationsByTaxClassification(taxClassification, EntityPermission.READ_ONLY);
    }

    public List<ItemTaxClassification> getItemTaxClassificationsByTaxClassificationForUpdate(TaxClassification taxClassification) {
        return getItemTaxClassificationsByTaxClassification(taxClassification, EntityPermission.READ_WRITE);
    }

    private ItemTaxClassification getItemTaxClassification(Item item, GeoCode countryGeoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemTaxClassifications.fields())
                .from(ItemTaxClassifications)
                .join(ItemTaxClassificationDetails).onKey(ITEM_TAX_CLASSIFICATIONS_ACTIVE_DETAIL_FK)
                .where(ItemTaxClassificationDetails.ITEM.eq(item.getPrimaryKey()),
                        ItemTaxClassificationDetails.COUNTRY_GEO_CODE.eq(countryGeoCode.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemTaxClassificationFactory.getEntityFromQuery(entityPermission, query);
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
            var itemTaxClassification = itemTaxClassificationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemTaxClassificationDetailValue.getItemTaxClassificationPK());
            var itemTaxClassificationDetail = itemTaxClassification.getActiveDetailForUpdate();

            itemTaxClassificationDetail.setThruTime(session.getStartTime());
            itemTaxClassificationDetail.store();

            var itemTaxClassificationPK = itemTaxClassificationDetail.getItemTaxClassificationPK();
            var itemPK = itemTaxClassificationDetail.getItemPK();
            var countryGeoCodePK = itemTaxClassificationDetail.getCountryGeoCodePK();
            var taxClassificationPK = itemTaxClassificationDetailValue.getTaxClassificationPK();

            itemTaxClassificationDetail = itemTaxClassificationDetailFactory.create(itemTaxClassificationPK, itemPK, countryGeoCodePK,
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

    @Inject
    protected TaxDetailFactory taxDetailFactory;

    @Inject
    protected TaxFactory taxFactory;

    @Inject
    private TaxTransferCache taxTransferCache;

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

        var tax = taxFactory.create();
        var taxDetail = taxDetailFactory.create(tax, taxName, contactMechanismPurpose, glAccount,
                includeShippingCharge, includeProcessingCharge, includeInsuranceCharge, percent, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        tax = taxFactory.getEntityFromPK(EntityPermission.READ_WRITE, tax.getPrimaryKey());
        tax.setActiveDetail(taxDetail);
        tax.setLastDetail(taxDetail);
        tax.store();
        
        sendEvent(tax.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return tax;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Tax */
    public Tax getTaxByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TaxPK(entityInstance.getEntityUniqueId());

        return taxFactory.getEntityFromPK(entityPermission, pk);
    }

    public Tax getTaxByEntityInstance(EntityInstance entityInstance) {
        return getTaxByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Tax getTaxByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTaxByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTaxes() {
        return session.getDslContext()
                .selectCount()
                .from(Taxes)
                .join(TaxDetails).onKey(TAXES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<Tax> getTaxes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Taxes.fields())
                .from(Taxes)
                .join(TaxDetails).onKey(TAXES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TaxDetails.SORT_ORDER, TaxDetails.TAX_NAME),
                    TaxFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return taxFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Tax> getTaxes() {
        return getTaxes(EntityPermission.READ_ONLY);
    }
    
    public List<Tax> getTaxesForUpdate() {
        return getTaxes(EntityPermission.READ_WRITE);
    }
    
    public Tax getTaxByName(String taxName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Taxes.fields())
                .from(Taxes)
                .join(TaxDetails).onKey(TAXES_ACTIVE_DETAIL_FK)
                .where(TaxDetails.TAX_NAME.eq(taxName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return taxFactory.getEntityFromQuery(entityPermission, query);
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
    
    public Tax getDefaultTax(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Taxes.fields())
                .from(Taxes)
                .join(TaxDetails).onKey(TAXES_ACTIVE_DETAIL_FK)
                .where(TaxDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return taxFactory.getEntityFromQuery(entityPermission, query);
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
    
    public List<TaxTransfer> getTaxTransfers(UserVisit userVisit, Collection<Tax> taxes) {
        List<TaxTransfer> taxTransfers = new ArrayList<>(taxes.size());
        
        taxes.forEach((tax) ->
                taxTransfers.add(taxTransferCache.getTransfer(userVisit, tax))
        );
        
        return taxTransfers;
    }
    
    public List<TaxTransfer> getTaxTransfers(UserVisit userVisit) {
        return getTaxTransfers(userVisit, getTaxes());
    }
    
    private void updateTaxFromValue(TaxDetailValue taxDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(taxDetailValue.hasBeenModified()) {
            var tax = taxFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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
            
            taxDetail = taxDetailFactory.create(taxPK, taxName, contactMechanismPurposePK, glAccountPK,
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

    @Inject
    protected TaxDescriptionFactory taxDescriptionFactory;

    @Inject
    private TaxDescriptionTransferCache taxDescriptionTransferCache;

    public TaxDescription createTaxDescription(Tax tax, Language language, String description, BasePK createdBy) {
        var taxDescription = taxDescriptionFactory.create(tax, language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(tax.getPrimaryKey(), EventTypes.MODIFY, taxDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return taxDescription;
    }
    
    private TaxDescription getTaxDescription(Tax tax, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TaxDescriptions.fields())
                .from(TaxDescriptions)
                .where(TaxDescriptions.TAX.eq(tax.getPrimaryKey()),
                        TaxDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        TaxDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return taxDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TaxDescriptions.fields())
                    .from(TaxDescriptions)
                    .join(Languages).onKey(TAX_DESCRIPTIONS_LANGUAGE_FK)
                    .where(TaxDescriptions.TAX.eq(tax.getPrimaryKey()),
                            TaxDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    TaxDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TaxDescriptions.fields())
                    .from(TaxDescriptions)
                    .where(TaxDescriptions.TAX.eq(tax.getPrimaryKey()),
                            TaxDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return taxDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            var taxDescription = taxDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    taxDescriptionValue.getPrimaryKey());
            
            taxDescription.setThruTime(session.getStartTime());
            taxDescription.store();

            var tax = taxDescription.getTax();
            var language = taxDescription.getLanguage();
            var description = taxDescriptionValue.getDescription();
            
            taxDescription = taxDescriptionFactory.create(tax, language, description,
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

    @Inject
    protected GeoCodeTaxFactory geoCodeTaxFactory;

    @Inject
    private GeoCodeTaxTransferCache geoCodeTaxTransferCache;

    public GeoCodeTax createGeoCodeTax(GeoCode geoCode, Tax tax, BasePK createdBy) {
        var geoCodeTax = geoCodeTaxFactory.create(geoCode, tax, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeTax.getPrimaryKey(), null, createdBy);
        sendEvent(tax.getPrimaryKey(), EventTypes.MODIFY, geoCodeTax.getPrimaryKey(), null, createdBy);
        
        return geoCodeTax;
    }
    
    public long countGeoCodeTaxesByGeoCode(GeoCode geoCode) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeTaxes)
                .where(GeoCodeTaxes.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeTaxes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countGeoCodeTaxesByTax(Tax tax) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeTaxes)
                .where(GeoCodeTaxes.TAX.eq(tax.getPrimaryKey()),
                        GeoCodeTaxes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private GeoCodeTax getGeoCodeTax(GeoCode geoCode, Tax tax, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeTaxes.fields())
                .from(GeoCodeTaxes)
                .where(GeoCodeTaxes.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeTaxes.TAX.eq(tax.getPrimaryKey()),
                        GeoCodeTaxes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeTaxFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeTax getGeoCodeTax(GeoCode geoCode, Tax tax) {
        return getGeoCodeTax(geoCode, tax, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeTax getGeoCodeTaxForUpdate(GeoCode geoCode, Tax tax) {
        return getGeoCodeTax(geoCode, tax, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeTax> getGeoCodeTaxesByGeoCode(GeoCode geoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeTaxes.fields())
                .from(GeoCodeTaxes)
                .join(Taxes).onKey(GEO_CODE_TAXES_TAX_FK)
                .join(TaxDetails).onKey(TAXES_LAST_DETAIL_FK)
                .where(GeoCodeTaxes.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeTaxes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TaxDetails.SORT_ORDER, TaxDetails.TAX_NAME),
                    GeoCodeTaxFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeTaxFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeTax> getGeoCodeTaxesByGeoCode(GeoCode geoCode) {
        return getGeoCodeTaxesByGeoCode(geoCode, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeTax> getGeoCodeTaxesByGeoCodeForUpdate(GeoCode geoCode) {
        return getGeoCodeTaxesByGeoCode(geoCode, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeTax> getGeoCodeTaxesByTax(Tax tax, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeTaxes.fields())
                    .from(GeoCodeTaxes)
                    .join(GeoCodes).onKey(GEO_CODE_TAXES_GEO_CODE_FK)
                    .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                    .where(GeoCodeTaxes.TAX.eq(tax.getPrimaryKey()),
                            GeoCodeTaxes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME),
                    GeoCodeTaxFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(GeoCodeTaxes.fields())
                    .from(GeoCodeTaxes)
                    .where(GeoCodeTaxes.TAX.eq(tax.getPrimaryKey()),
                            GeoCodeTaxes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return geoCodeTaxFactory.getEntitiesFromQuery(entityPermission, query);
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
    
    public List<GeoCodeTaxTransfer> getGeoCodeTaxTransfers(UserVisit userVisit, Collection<GeoCodeTax> geoCodeTaxes) {
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
