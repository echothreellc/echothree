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

package com.echothree.model.control.offer.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.offer.common.choice.OfferChoicesBean;
import com.echothree.model.control.offer.common.transfer.OfferChainTypeTransfer;
import com.echothree.model.control.offer.common.transfer.OfferCustomerTypeTransfer;
import com.echothree.model.control.offer.common.transfer.OfferDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.OfferResultTransfer;
import com.echothree.model.control.offer.common.transfer.OfferTransfer;
import com.echothree.model.control.offer.server.logic.OfferItemLogic;
import com.echothree.model.control.offer.server.transfer.OfferChainTypeTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferCustomerTypeTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferTransferCache;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.offer.common.pk.OfferPK;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferChainType;
import com.echothree.model.data.offer.server.entity.OfferCustomerType;
import com.echothree.model.data.offer.server.entity.OfferDescription;
import com.echothree.model.data.offer.server.entity.OfferTime;
import com.echothree.model.data.offer.server.factory.OfferChainTypeFactory;
import com.echothree.model.data.offer.server.factory.OfferCustomerTypeFactory;
import com.echothree.model.data.offer.server.factory.OfferDescriptionFactory;
import com.echothree.model.data.offer.server.factory.OfferDetailFactory;
import com.echothree.model.data.offer.server.factory.OfferFactory;
import com.echothree.model.data.offer.server.factory.OfferTimeFactory;
import com.echothree.model.data.offer.server.value.OfferChainTypeValue;
import com.echothree.model.data.offer.server.value.OfferCustomerTypeValue;
import com.echothree.model.data.offer.server.value.OfferDescriptionValue;
import com.echothree.model.data.offer.server.value.OfferDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.factory.SelectorFactory;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_KINDS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_TYPE_DETAILS_CHAIN_KIND_FK;
import static com.echothree.model.jooq.server.keys.customer.CustomerForeignKeys.CUSTOMER_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFERS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFERS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_CHAIN_TYPES_CHAIN_TYPE_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_CHAIN_TYPES_OFFER_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_CUSTOMER_TYPES_CUSTOMER_TYPE_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_CUSTOMER_TYPES_OFFER_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_DETAILS_OFFER_ITEM_SELECTOR_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.chain.ChainKindDetails.ChainKindDetails;
import static com.echothree.model.jooq.server.tables.chain.ChainKinds.ChainKinds;
import static com.echothree.model.jooq.server.tables.chain.ChainTypeDetails.ChainTypeDetails;
import static com.echothree.model.jooq.server.tables.chain.ChainTypes.ChainTypes;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.customer.CustomerTypeDetails.CustomerTypeDetails;
import static com.echothree.model.jooq.server.tables.customer.CustomerTypes.CustomerTypes;
import static com.echothree.model.jooq.server.tables.offer.OfferChainTypes.OfferChainTypes;
import static com.echothree.model.jooq.server.tables.offer.OfferCustomerTypes.OfferCustomerTypes;
import static com.echothree.model.jooq.server.tables.offer.OfferDescriptions.OfferDescriptions;
import static com.echothree.model.jooq.server.tables.offer.OfferDetails.OfferDetails;
import static com.echothree.model.jooq.server.tables.offer.OfferTimes.OfferTimes;
import static com.echothree.model.jooq.server.tables.offer.Offers.Offers;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
import static com.echothree.model.jooq.server.tables.selector.Selectors.Selectors;
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
import org.jooq.Select;

@CommandScope
public class OfferControl
        extends BaseModelControl {
    
    @Inject
    protected OfferUseControl offerUseControl;

    @Inject
    private SearchControl searchControl;

    @Inject
    private OfferItemLogic offerItemLogic;

    /** Creates a new instance of OfferControl */
    protected OfferControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Offers
    // --------------------------------------------------------------------------------

    @Inject
    private OfferDetailFactory offerDetailFactory;

    @Inject
    private OfferFactory offerFactory;

    @Inject
    protected SelectorFactory selectorFactory;

    @Inject
    private OfferTransferCache offerTransferCache;

    /** Use the function in OfferLogic instead. */
    public Offer createOffer(String offerName, Sequence salesOrderSequence, Party departmentParty, Selector offerItemSelector,
            Filter offerItemPriceFilter, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultOffer = getDefaultOffer();
        var defaultFound = defaultOffer != null;
        
        if(defaultFound && isDefault) {
            var defaultOfferDetailValue = getDefaultOfferDetailValueForUpdate();
            
            defaultOfferDetailValue.setIsDefault(false);
            updateOfferFromValue(defaultOfferDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var offer = offerFactory.create();
        var offerDetail = offerDetailFactory.create(offer, offerName, salesOrderSequence, departmentParty, offerItemSelector,
                offerItemPriceFilter, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        offer = offerFactory.getEntityFromPK(EntityPermission.READ_WRITE, offer.getPrimaryKey());
        offer.setActiveDetail(offerDetail);
        offer.setLastDetail(offerDetail);
        offer.store();
        
        sendEvent(offer.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return offer;
    }

    public long countOffers() {
        return session.getDslContext()
                .selectCount()
                .from(Offers)
                .join(OfferDetails).onKey(OFFERS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOffersBySalesOrderSequence(Sequence sequence) {
        return session.getDslContext()
                .selectCount()
                .from(Offers)
                .join(OfferDetails).onKey(OFFERS_ACTIVE_DETAIL_FK)
                .where(OfferDetails.SALES_ORDER_SEQUENCE.eq(sequence.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOffersByDepartmentParty(Party departmentParty) {
        return session.getDslContext()
                .selectCount()
                .from(Offers)
                .join(OfferDetails).onKey(OFFERS_ACTIVE_DETAIL_FK)
                .where(OfferDetails.DEPARTMENT_PARTY.eq(departmentParty.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOffersBySelector(Selector selector) {
        return session.getDslContext()
                .selectCount()
                .from(Offers)
                .join(OfferDetails).onKey(OFFERS_ACTIVE_DETAIL_FK)
                .where(OfferDetails.OFFER_ITEM_SELECTOR.eq(selector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOffersByFilter(Filter filter) {
        return session.getDslContext()
                .selectCount()
                .from(Offers)
                .join(OfferDetails).onKey(OFFERS_ACTIVE_DETAIL_FK)
                .where(OfferDetails.OFFER_ITEM_PRICE_FILTER.eq(filter.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Offer */
    public Offer getOfferByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new OfferPK(entityInstance.getEntityUniqueId());
        var offer = offerFactory.getEntityFromPK(entityPermission, pk);

        return offer;
    }

    public Offer getOfferByEntityInstance(EntityInstance entityInstance) {
        return getOfferByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Offer getOfferByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getOfferByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public Offer getDefaultOffer(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Offers.fields())
                .from(Offers)
                .join(OfferDetails).onKey(OFFERS_ACTIVE_DETAIL_FK)
                .where(OfferDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Offer getDefaultOffer() {
        return getDefaultOffer(EntityPermission.READ_ONLY);
    }
    
    public Offer getDefaultOfferForUpdate() {
        return getDefaultOffer(EntityPermission.READ_WRITE);
    }
    
    public OfferDetailValue getDefaultOfferDetailValueForUpdate() {
        return getDefaultOfferForUpdate().getLastDetailForUpdate().getOfferDetailValue().clone();
    }
    
    public Offer getOfferByName(String offerName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Offers.fields())
                .from(Offers)
                .join(OfferDetails).onKey(OFFERS_ACTIVE_DETAIL_FK)
                .where(OfferDetails.OFFER_NAME.eq(offerName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Offer getOfferByName(String offerName) {
        return getOfferByName(offerName, EntityPermission.READ_ONLY);
    }
    
    public Offer getOfferByNameForUpdate(String offerName) {
        return getOfferByName(offerName, EntityPermission.READ_WRITE);
    }
    
    public OfferDetailValue getOfferDetailValueForUpdate(Offer offer) {
        return offer == null? null: offer.getLastDetailForUpdate().getOfferDetailValue().clone();
    }
    
    public OfferDetailValue getOfferDetailValueByNameForUpdate(String offerName) {
        return getOfferDetailValueForUpdate(getOfferByNameForUpdate(offerName));
    }

    public List<Offer> getOffersByDepartmentParty(Party departmentParty) {
        var query = session.applyLimit(session.getDslContext()
                .select(Offers.fields())
                .from(Offers)
                .join(OfferDetails).onKey(OFFERS_ACTIVE_DETAIL_FK)
                .where(OfferDetails.DEPARTMENT_PARTY.eq(departmentParty.getPrimaryKey()))
                .orderBy(OfferDetails.SORT_ORDER, OfferDetails.OFFER_NAME),
                OfferFactory.class);

        return offerFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public List<Offer> getOffersByOfferItemSelector(Selector offerItemSelector) {
        var query = session.applyLimit(session.getDslContext()
                .select(Offers.fields())
                .from(Offers)
                .join(OfferDetails).onKey(OFFERS_ACTIVE_DETAIL_FK)
                .where(OfferDetails.OFFER_ITEM_SELECTOR.eq(offerItemSelector.getPrimaryKey()))
                .orderBy(OfferDetails.SORT_ORDER, OfferDetails.OFFER_NAME),
                OfferFactory.class);

        return offerFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private List<Offer> getOffers(EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Offers.fields())
                    .from(Offers)
                    .join(OfferDetails).onKey(OFFERS_ACTIVE_DETAIL_FK)
                    .orderBy(OfferDetails.SORT_ORDER, OfferDetails.OFFER_NAME),
                    OfferFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Offers.fields())
                    .from(Offers)
                    .join(OfferDetails).onKey(OFFERS_ACTIVE_DETAIL_FK)
                    .orderBy(OfferDetails.SORT_ORDER, OfferDetails.OFFER_NAME)
                    .forUpdate();
        };

        return offerFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Offer> getOffers() {
        return getOffers(EntityPermission.READ_ONLY);
    }
    
    public List<Offer> getOffersForUpdate() {
        return getOffers(EntityPermission.READ_WRITE);
    }
    
    /** Gets a List that contains all the Selectors used by Offers. */
    public List<Selector> getDistinctOfferItemSelectors() {
        var query = session.applyLimit(session.getDslContext()
                .selectDistinct(Selectors.fields())
                .from(Selectors)
                .join(OfferDetails).onKey(OFFER_DETAILS_OFFER_ITEM_SELECTOR_FK)
                .join(Offers).onKey(OFFERS_ACTIVE_DETAIL_FK),
                SelectorFactory.class);

        return selectorFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public OfferTransfer getOfferTransfer(UserVisit userVisit, Offer offer) {
        return offerTransferCache.getOfferTransfer(userVisit, offer);
    }
    
    public List<OfferTransfer> getOfferTransfers(UserVisit userVisit, Collection<Offer> offers) {
        List<OfferTransfer> offerTransfers = new ArrayList<>(offers.size());
        
        offers.forEach((offer) -> {
            offerTransfers.add(offerTransferCache.getOfferTransfer(userVisit, offer));
        });
        
        return offerTransfers;
    }
    
    public List<OfferTransfer> getOfferTransfers(UserVisit userVisit) {
        return getOfferTransfers(userVisit, getOffers());
    }
    
    public OfferChoicesBean getOfferChoices(String defaultOfferChoice, Language language, boolean allowNullChoice) {
        var offers = getOffers();
        var size = offers.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOfferChoice == null) {
                defaultValue = "";
            }
        }

        for(var offer : offers) {
            var offerDetail = offer.getLastDetail();

            var label = getBestOfferDescription(offer, language);
            var value = offerDetail.getOfferName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultOfferChoice != null && defaultOfferChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && offerDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new OfferChoicesBean(labels, values, defaultValue);
    }

    private void updateOfferFromValue(OfferDetailValue offerDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(offerDetailValue.hasBeenModified()) {
            var offer = offerFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     offerDetailValue.getOfferPK());
            var offerDetail = offer.getActiveDetailForUpdate();
            
            offerDetail.setThruTime(session.getStartTime());
            offerDetail.store();

            var offerPK = offerDetail.getOfferPK();
            var offerName = offerDetailValue.getOfferName();
            var salesOrderSequencePK = offerDetailValue.getSalesOrderSequencePK();
            var departmentPartyPK = offerDetail.getDepartmentPartyPK(); // Not updated
            var offerItemSelectorPK = offerDetailValue.getOfferItemSelectorPK();
            var offerItemPriceFilterPK = offerDetailValue.getOfferItemPriceFilterPK();
            var isDefault = offerDetailValue.getIsDefault();
            var sortOrder = offerDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultOffer = getDefaultOffer();
                var defaultFound = defaultOffer != null && !defaultOffer.equals(offer);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultOfferDetailValue = getDefaultOfferDetailValueForUpdate();
                    
                    defaultOfferDetailValue.setIsDefault(false);
                    updateOfferFromValue(defaultOfferDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            offerDetail = offerDetailFactory.create(offerPK, offerName, salesOrderSequencePK, departmentPartyPK, offerItemSelectorPK,
                    offerItemPriceFilterPK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            offer.setActiveDetail(offerDetail);
            offer.setLastDetail(offerDetail);
            
            sendEvent(offerPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    /** Use the function in OfferLogic instead. */
    public void updateOfferFromValue(OfferDetailValue offerDetailValue, BasePK updatedBy) {
        updateOfferFromValue(offerDetailValue, true, updatedBy);
    }

    /** Use the function in OfferLogic instead. */
    public void deleteOffer(Offer offer, BasePK deletedBy) {

        deleteOfferCustomerTypesByOffer(offer, deletedBy);
        deleteOfferChainTypesByOffer(offer, deletedBy);
        offerItemLogic.deleteOfferItemsByOffer(offer, deletedBy);
        offerUseControl.deleteOfferUsesByOffer(offer, deletedBy);
        deleteOfferDescriptionsByOffer(offer, deletedBy);

        removeOfferTimeByOffer(offer);

        var offerDetail = offer.getLastDetailForUpdate();
        offerDetail.setThruTime(session.getStartTime());
        offer.setActiveDetail(null);
        offer.store();
        
        // Check for default, and pick one if necessary
        var defaultOffer = getDefaultOffer();
        if(defaultOffer == null) {
            var offers = getOffersForUpdate();
            
            if(!offers.isEmpty()) {
                var iter = offers.iterator();
                if(iter.hasNext()) {
                    defaultOffer = iter.next();
                }
                var offerDetailValue = Objects.requireNonNull(defaultOffer).getLastDetailForUpdate().getOfferDetailValue().clone();
                
                offerDetailValue.setIsDefault(true);
                updateOfferFromValue(offerDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(offer.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private OfferDescriptionFactory offerDescriptionFactory;

    @Inject
    private OfferDescriptionTransferCache offerDescriptionTransferCache;

    public OfferDescription createOfferDescription(Offer offer, Language language, String description, BasePK createdBy) {
        var offerDescription = offerDescriptionFactory.create(offer, language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(offer.getPrimaryKey(), EventTypes.MODIFY, offerDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return offerDescription;
    }
    
    private OfferDescription getOfferDescription(Offer offer, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferDescriptions.fields())
                .from(OfferDescriptions)
                .where(OfferDescriptions.OFFER.eq(offer.getPrimaryKey()),
                        OfferDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        OfferDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public OfferDescription getOfferDescription(Offer offer, Language language) {
        return getOfferDescription(offer, language, EntityPermission.READ_ONLY);
    }
    
    public OfferDescription getOfferDescriptionForUpdate(Offer offer, Language language) {
        return getOfferDescription(offer, language, EntityPermission.READ_WRITE);
    }
    
    public OfferDescriptionValue getOfferDescriptionValue(OfferDescription offerDescription) {
        return offerDescription == null? null: offerDescription.getOfferDescriptionValue().clone();
    }
    
    public OfferDescriptionValue getOfferDescriptionValueForUpdate(Offer offer, Language language) {
        return getOfferDescriptionValue(getOfferDescriptionForUpdate(offer, language));
    }
    
    private List<OfferDescription> getOfferDescriptionsByOffer(Offer offer, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferDescriptions.fields())
                    .from(OfferDescriptions)
                    .join(Languages).onKey(OFFER_DESCRIPTIONS_LANGUAGE_FK)
                    .where(OfferDescriptions.OFFER.eq(offer.getPrimaryKey()),
                            OfferDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    OfferDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OfferDescriptions.fields())
                    .from(OfferDescriptions)
                    .where(OfferDescriptions.OFFER.eq(offer.getPrimaryKey()),
                            OfferDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return offerDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<OfferDescription> getOfferDescriptionsByOffer(Offer offer) {
        return getOfferDescriptionsByOffer(offer, EntityPermission.READ_ONLY);
    }
    
    public List<OfferDescription> getOfferDescriptionsByOfferForUpdate(Offer offer) {
        return getOfferDescriptionsByOffer(offer, EntityPermission.READ_WRITE);
    }
    
    public String getBestOfferDescription(Offer offer, Language language) {
        String description;
        var offerDescription = getOfferDescription(offer, language);
        
        if(offerDescription == null && !language.getIsDefault()) {
            offerDescription = getOfferDescription(offer, partyControl.getDefaultLanguage());
        }
        
        if(offerDescription == null) {
            description = offer.getLastDetail().getOfferName();
        } else {
            description = offerDescription.getDescription();
        }
        
        return description;
    }
    
    public OfferDescriptionTransfer getOfferDescriptionTransfer(UserVisit userVisit, OfferDescription offerDescription) {
        return offerDescriptionTransferCache.getOfferDescriptionTransfer(userVisit, offerDescription);
    }
    
    public List<OfferDescriptionTransfer> getOfferDescriptionTransfers(UserVisit userVisit, Offer offer) {
        var offerDescriptions = getOfferDescriptionsByOffer(offer);
        List<OfferDescriptionTransfer> offerDescriptionTransfers = new ArrayList<>(offerDescriptions.size());
        
        offerDescriptions.forEach((offerDescription) -> {
            offerDescriptionTransfers.add(offerDescriptionTransferCache.getOfferDescriptionTransfer(userVisit, offerDescription));
        });
        
        return offerDescriptionTransfers;
    }
    
    public void updateOfferDescriptionFromValue(OfferDescriptionValue offerDescriptionValue, BasePK updatedBy) {
        if(offerDescriptionValue.hasBeenModified()) {
            var offerDescription = offerDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     offerDescriptionValue.getPrimaryKey());
            
            offerDescription.setThruTime(session.getStartTime());
            offerDescription.store();

            var offer = offerDescription.getOffer();
            var language = offerDescription.getLanguage();
            var description = offerDescriptionValue.getDescription();
            
            offerDescription = offerDescriptionFactory.create(offer, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(offer.getPrimaryKey(), EventTypes.MODIFY, offerDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteOfferDescription(OfferDescription offerDescription, BasePK deletedBy) {
        offerDescription.setThruTime(session.getStartTime());
        
        sendEvent(offerDescription.getOfferPK(), EventTypes.MODIFY, offerDescription.getPrimaryKey(),
                EventTypes.DELETE, deletedBy);
    }
    
    public void deleteOfferDescriptionsByOffer(Offer offer, BasePK deletedBy) {
        var offerDescriptions = getOfferDescriptionsByOfferForUpdate(offer);
        
        offerDescriptions.forEach((offerDescription) -> 
                deleteOfferDescription(offerDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Offer Times
    // --------------------------------------------------------------------------------

    @Inject
    private OfferTimeFactory offerTimeFactory;

    public OfferTime createOfferTime(Offer offer) {
        return offerTimeFactory.create(offer, null, null, null, null);
    }


    private OfferTime getOfferTime(Offer offer, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferTimes.fields())
                .from(OfferTimes)
                .where(OfferTimes.OFFER.eq(offer.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerTimeFactory.getEntityFromQuery(entityPermission, query);
    }

    public OfferTime getOfferTime(Offer offer) {
        return getOfferTime(offer, EntityPermission.READ_ONLY);
    }

    public OfferTime getOfferTimeForUpdate(Offer offer) {
        return getOfferTime(offer, EntityPermission.READ_WRITE);
    }

    public void removeOfferTimeByOffer(Offer offer) {
        var offerTime = getOfferTimeForUpdate(offer);

        if(offerTime != null) {
            offerTime.remove();
        }
    }

    // --------------------------------------------------------------------------------
    //   Offer Customer Types
    // --------------------------------------------------------------------------------

    @Inject
    private OfferCustomerTypeFactory offerCustomerTypeFactory;

    @Inject
    private OfferCustomerTypeTransferCache offerCustomerTypeTransferCache;

    public OfferCustomerType createOfferCustomerType(Offer offer, CustomerType customerType, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultOfferCustomerType = getDefaultOfferCustomerType(offer);
        var defaultFound = defaultOfferCustomerType != null;

        if(defaultFound && isDefault) {
            var defaultOfferCustomerTypeValue = getDefaultOfferCustomerTypeValueForUpdate(offer);

            defaultOfferCustomerTypeValue.setIsDefault(false);
            updateOfferCustomerTypeFromValue(defaultOfferCustomerTypeValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var offerCustomerType = offerCustomerTypeFactory.create(offer, customerType,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        sendEvent(offer.getPrimaryKey(), EventTypes.MODIFY, offerCustomerType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return offerCustomerType;
    }

    public long countOfferCustomerTypesByOffer(Offer offer) {
        return session.getDslContext()
                .selectCount()
                .from(OfferCustomerTypes)
                .where(OfferCustomerTypes.OFFER.eq(offer.getPrimaryKey()),
                        OfferCustomerTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOfferCustomerTypesByCustomerType(CustomerType customerType) {
        return session.getDslContext()
                .selectCount()
                .from(OfferCustomerTypes)
                .where(OfferCustomerTypes.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        OfferCustomerTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private OfferCustomerType getOfferCustomerType(Offer offer, CustomerType customerType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferCustomerTypes.fields())
                .from(OfferCustomerTypes)
                .where(OfferCustomerTypes.OFFER.eq(offer.getPrimaryKey()),
                        OfferCustomerTypes.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        OfferCustomerTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerCustomerTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public OfferCustomerType getOfferCustomerType(Offer offer, CustomerType customerType) {
        return getOfferCustomerType(offer, customerType, EntityPermission.READ_ONLY);
    }

    public OfferCustomerType getOfferCustomerTypeForUpdate(Offer offer, CustomerType customerType) {
        return getOfferCustomerType(offer, customerType, EntityPermission.READ_WRITE);
    }

    public OfferCustomerTypeValue getOfferCustomerTypeValueForUpdate(Offer offer, CustomerType customerType) {
        var offerCustomerType = getOfferCustomerTypeForUpdate(offer, customerType);

        return offerCustomerType == null? null: offerCustomerType.getOfferCustomerTypeValue().clone();
    }

    private OfferCustomerType getDefaultOfferCustomerType(Offer offer, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferCustomerTypes.fields())
                .from(OfferCustomerTypes)
                .where(OfferCustomerTypes.OFFER.eq(offer.getPrimaryKey()),
                        OfferCustomerTypes.IS_DEFAULT.eq(true),
                        OfferCustomerTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerCustomerTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public OfferCustomerType getDefaultOfferCustomerType(Offer offer) {
        return getDefaultOfferCustomerType(offer, EntityPermission.READ_ONLY);
    }

    public OfferCustomerType getDefaultOfferCustomerTypeForUpdate(Offer offer) {
        return getDefaultOfferCustomerType(offer, EntityPermission.READ_WRITE);
    }

    public OfferCustomerTypeValue getDefaultOfferCustomerTypeValueForUpdate(Offer offer) {
        var offerCustomerType = getDefaultOfferCustomerTypeForUpdate(offer);

        return offerCustomerType == null? null: offerCustomerType.getOfferCustomerTypeValue().clone();
    }

    private List<OfferCustomerType> getOfferCustomerTypesByOffer(Offer offer, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferCustomerTypes.fields())
                    .from(OfferCustomerTypes)
                    .join(CustomerTypes).onKey(OFFER_CUSTOMER_TYPES_CUSTOMER_TYPE_FK)
                    .join(CustomerTypeDetails).onKey(CUSTOMER_TYPES_LAST_DETAIL_FK)
                    .where(OfferCustomerTypes.OFFER.eq(offer.getPrimaryKey()),
                            OfferCustomerTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CustomerTypeDetails.SORT_ORDER, CustomerTypeDetails.CUSTOMER_TYPE_NAME),
                    OfferCustomerTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OfferCustomerTypes.fields())
                    .from(OfferCustomerTypes)
                    .where(OfferCustomerTypes.OFFER.eq(offer.getPrimaryKey()),
                            OfferCustomerTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return offerCustomerTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OfferCustomerType> getOfferCustomerTypesByOffer(Offer offer) {
        return getOfferCustomerTypesByOffer(offer, EntityPermission.READ_ONLY);
    }

    public List<OfferCustomerType> getOfferCustomerTypesByOfferForUpdate(Offer offer) {
        return getOfferCustomerTypesByOffer(offer, EntityPermission.READ_WRITE);
    }

    private List<OfferCustomerType> getOfferCustomerTypesByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferCustomerTypes.fields())
                    .from(OfferCustomerTypes)
                    .join(Offers).onKey(OFFER_CUSTOMER_TYPES_OFFER_FK)
                    .join(OfferDetails).onKey(OFFERS_LAST_DETAIL_FK)
                    .where(OfferCustomerTypes.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            OfferCustomerTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OfferDetails.SORT_ORDER, OfferDetails.OFFER_NAME),
                    OfferCustomerTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OfferCustomerTypes.fields())
                    .from(OfferCustomerTypes)
                    .where(OfferCustomerTypes.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            OfferCustomerTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return offerCustomerTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OfferCustomerType> getOfferCustomerTypesByCustomerType(CustomerType customerType) {
        return getOfferCustomerTypesByCustomerType(customerType, EntityPermission.READ_ONLY);
    }

    public List<OfferCustomerType> getOfferCustomerTypesByCustomerTypeForUpdate(CustomerType customerType) {
        return getOfferCustomerTypesByCustomerType(customerType, EntityPermission.READ_WRITE);
    }

    public List<OfferCustomerTypeTransfer> getOfferCustomerTypeTransfers(UserVisit userVisit, Collection<OfferCustomerType> offerCustomerTypes) {
        List<OfferCustomerTypeTransfer> offerCustomerTypeTransfers = new ArrayList<>(offerCustomerTypes.size());

        offerCustomerTypes.forEach((offerCustomerType) ->
                offerCustomerTypeTransfers.add(offerCustomerTypeTransferCache.getOfferCustomerTypeTransfer(userVisit, offerCustomerType))
        );

        return offerCustomerTypeTransfers;
    }

    public List<OfferCustomerTypeTransfer> getOfferCustomerTypeTransfersByOffer(UserVisit userVisit, Offer offer) {
        return getOfferCustomerTypeTransfers(userVisit, getOfferCustomerTypesByOffer(offer));
    }

    public List<OfferCustomerTypeTransfer> getOfferCustomerTypeTransfersByCustomerType(UserVisit userVisit, CustomerType customerType) {
        return getOfferCustomerTypeTransfers(userVisit, getOfferCustomerTypesByCustomerType(customerType));
    }

    public OfferCustomerTypeTransfer getOfferCustomerTypeTransfer(UserVisit userVisit, OfferCustomerType offerCustomerType) {
        return offerCustomerTypeTransferCache.getOfferCustomerTypeTransfer(userVisit, offerCustomerType);
    }

    private void updateOfferCustomerTypeFromValue(OfferCustomerTypeValue offerCustomerTypeValue, boolean checkDefault, BasePK updatedBy) {
        if(offerCustomerTypeValue.hasBeenModified()) {
            var offerCustomerType = offerCustomerTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     offerCustomerTypeValue.getPrimaryKey());

            offerCustomerType.setThruTime(session.getStartTime());
            offerCustomerType.store();

            var offer = offerCustomerType.getOffer(); // Not Updated
            var offerPK = offer.getPrimaryKey(); // Not Updated
            var customerTypePK = offerCustomerType.getCustomerTypePK(); // Not Updated
            var isDefault = offerCustomerTypeValue.getIsDefault();
            var sortOrder = offerCustomerTypeValue.getSortOrder();

            if(checkDefault) {
                var defaultOfferCustomerType = getDefaultOfferCustomerType(offer);
                var defaultFound = defaultOfferCustomerType != null && !defaultOfferCustomerType.equals(offerCustomerType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultOfferCustomerTypeValue = getDefaultOfferCustomerTypeValueForUpdate(offer);

                    defaultOfferCustomerTypeValue.setIsDefault(false);
                    updateOfferCustomerTypeFromValue(defaultOfferCustomerTypeValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            offerCustomerType = offerCustomerTypeFactory.create(offerPK, customerTypePK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            sendEvent(offerPK, EventTypes.MODIFY, offerCustomerType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void updateOfferCustomerTypeFromValue(OfferCustomerTypeValue offerCustomerTypeValue, BasePK updatedBy) {
        updateOfferCustomerTypeFromValue(offerCustomerTypeValue, true, updatedBy);
    }

    public void deleteOfferCustomerType(OfferCustomerType offerCustomerType, BasePK deletedBy) {
        offerCustomerType.setThruTime(session.getStartTime());
        offerCustomerType.store();

        // Check for default, and pick one if necessary
        var offer = offerCustomerType.getOffer();
        var defaultOfferCustomerType = getDefaultOfferCustomerType(offer);
        if(defaultOfferCustomerType == null) {
            var offerCustomerTypes = getOfferCustomerTypesByOfferForUpdate(offer);

            if(!offerCustomerTypes.isEmpty()) {
                var iter = offerCustomerTypes.iterator();
                if(iter.hasNext()) {
                    defaultOfferCustomerType = iter.next();
                }
                var offerCustomerTypeValue = defaultOfferCustomerType.getOfferCustomerTypeValue().clone();

                offerCustomerTypeValue.setIsDefault(true);
                updateOfferCustomerTypeFromValue(offerCustomerTypeValue, false, deletedBy);
            }
        }

        sendEvent(offer.getPrimaryKey(), EventTypes.MODIFY, offerCustomerType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteOfferCustomerTypes(List<OfferCustomerType> offerCustomerTypes, BasePK deletedBy) {
        offerCustomerTypes.forEach((offerCustomerType) -> 
                deleteOfferCustomerType(offerCustomerType, deletedBy)
        );
    }

    public void deleteOfferCustomerTypesByOffer(Offer offer, BasePK deletedBy) {
        deleteOfferCustomerTypes(getOfferCustomerTypesByOfferForUpdate(offer), deletedBy);
    }

    public void deleteOfferCustomerTypesByCustomerType(CustomerType customerType, BasePK deletedBy) {
        deleteOfferCustomerTypes(getOfferCustomerTypesByCustomerTypeForUpdate(customerType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Offer Chain Types
    // --------------------------------------------------------------------------------

    @Inject
    private OfferChainTypeFactory offerChainTypeFactory;

    @Inject
    private OfferChainTypeTransferCache offerChainTypeTransferCache;

    public OfferChainType createOfferChainType(Offer offer, ChainType chainType, Chain chain, BasePK createdBy) {
        var offerChainType = offerChainTypeFactory.create(offer, chainType, chain,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(offer.getPrimaryKey(), EventTypes.MODIFY, offerChainType.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return offerChainType;
    }

    public long countOfferChainTypesByOffer(Offer offer) {
        return session.getDslContext()
                .selectCount()
                .from(OfferChainTypes)
                .where(OfferChainTypes.OFFER.eq(offer.getPrimaryKey()),
                        OfferChainTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOfferChainTypesByChainType(ChainType chainType) {
        return session.getDslContext()
                .selectCount()
                .from(OfferChainTypes)
                .where(OfferChainTypes.CHAIN_TYPE.eq(chainType.getPrimaryKey()),
                        OfferChainTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private OfferChainType getOfferChainType(Offer offer, ChainType chainType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferChainTypes.fields())
                .from(OfferChainTypes)
                .where(OfferChainTypes.OFFER.eq(offer.getPrimaryKey()),
                        OfferChainTypes.CHAIN_TYPE.eq(chainType.getPrimaryKey()),
                        OfferChainTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerChainTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public OfferChainType getOfferChainType(Offer offer, ChainType chainType) {
        return getOfferChainType(offer, chainType, EntityPermission.READ_ONLY);
    }
    
    public OfferChainType getOfferChainTypeForUpdate(Offer offer, ChainType chainType) {
        return getOfferChainType(offer, chainType, EntityPermission.READ_WRITE);
    }
    
    public OfferChainTypeValue getOfferChainTypeValue(OfferChainType offerChainType) {
        return offerChainType == null? null: offerChainType.getOfferChainTypeValue().clone();
    }
    
    public OfferChainTypeValue getOfferChainTypeValueForUpdate(Offer offer, ChainType chainType) {
        return getOfferChainTypeValue(getOfferChainTypeForUpdate(offer, chainType));
    }
    
    private List<OfferChainType> getOfferChainTypesByOffer(Offer offer, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferChainTypes.fields())
                    .from(OfferChainTypes)
                    .join(ChainTypes).onKey(OFFER_CHAIN_TYPES_CHAIN_TYPE_FK)
                    .join(ChainTypeDetails).onKey(CHAIN_TYPES_LAST_DETAIL_FK)
                    .join(ChainKinds).onKey(CHAIN_TYPE_DETAILS_CHAIN_KIND_FK)
                    .join(ChainKindDetails).onKey(CHAIN_KINDS_LAST_DETAIL_FK)
                    .where(OfferChainTypes.OFFER.eq(offer.getPrimaryKey()),
                            OfferChainTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ChainTypeDetails.SORT_ORDER, ChainTypeDetails.CHAIN_TYPE_NAME, ChainKindDetails.SORT_ORDER, ChainKindDetails.CHAIN_KIND_NAME),
                    OfferChainTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OfferChainTypes.fields())
                    .from(OfferChainTypes)
                    .where(OfferChainTypes.OFFER.eq(offer.getPrimaryKey()),
                            OfferChainTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return offerChainTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<OfferChainType> getOfferChainTypesByOffer(Offer offer) {
        return getOfferChainTypesByOffer(offer, EntityPermission.READ_ONLY);
    }
    
    public List<OfferChainType> getOfferChainTypesByOfferForUpdate(Offer offer) {
        return getOfferChainTypesByOffer(offer, EntityPermission.READ_WRITE);
    }
    
    private List<OfferChainType> getOfferChainTypesByChainType(ChainType chainType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferChainTypes.fields())
                    .from(OfferChainTypes)
                    .join(Offers).onKey(OFFER_CHAIN_TYPES_OFFER_FK)
                    .join(OfferDetails).onKey(OFFERS_LAST_DETAIL_FK)
                    .where(OfferChainTypes.CHAIN_TYPE.eq(chainType.getPrimaryKey()),
                            OfferChainTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OfferDetails.SORT_ORDER, OfferDetails.OFFER_NAME),
                    OfferChainTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OfferChainTypes.fields())
                    .from(OfferChainTypes)
                    .where(OfferChainTypes.CHAIN_TYPE.eq(chainType.getPrimaryKey()),
                            OfferChainTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return offerChainTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<OfferChainType> getOfferChainTypesByChainType(ChainType chainType) {
        return getOfferChainTypesByChainType(chainType, EntityPermission.READ_ONLY);
    }
    
    public List<OfferChainType> getOfferChainTypesByChainTypeForUpdate(ChainType chainType) {
        return getOfferChainTypesByChainType(chainType, EntityPermission.READ_WRITE);
    }
    
    private List<OfferChainType> getOfferChainTypesByChain(Chain chain, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferChainTypes.fields())
                    .from(OfferChainTypes)
                    .join(Offers).onKey(OFFER_CHAIN_TYPES_OFFER_FK)
                    .join(OfferDetails).onKey(OFFERS_LAST_DETAIL_FK)
                    .where(OfferChainTypes.CHAIN.eq(chain.getPrimaryKey()),
                            OfferChainTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OfferDetails.SORT_ORDER, OfferDetails.OFFER_NAME),
                    OfferChainTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OfferChainTypes.fields())
                    .from(OfferChainTypes)
                    .where(OfferChainTypes.CHAIN.eq(chain.getPrimaryKey()),
                            OfferChainTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return offerChainTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<OfferChainType> getOfferChainTypesByChain(Chain chain) {
        return getOfferChainTypesByChain(chain, EntityPermission.READ_ONLY);
    }
    
    public List<OfferChainType> getOfferChainTypesByChainForUpdate(Chain chain) {
        return getOfferChainTypesByChain(chain, EntityPermission.READ_WRITE);
    }
    
    public OfferChainTypeTransfer getOfferChainTypeTransfer(UserVisit userVisit, OfferChainType offerChainType) {
        return offerChainTypeTransferCache.getOfferChainTypeTransfer(userVisit, offerChainType);
    }
    
    public List<OfferChainTypeTransfer> getOfferChainTypeTransfers(UserVisit userVisit, Collection<OfferChainType> offerChainTypes) {
        List<OfferChainTypeTransfer> offerChainTypeTransfers = new ArrayList<>(offerChainTypes.size());
        
        offerChainTypes.forEach((offerChainType) ->
                offerChainTypeTransfers.add(offerChainTypeTransferCache.getOfferChainTypeTransfer(userVisit, offerChainType))
        );
        
        return offerChainTypeTransfers;
    }
    
    public List<OfferChainTypeTransfer> getOfferChainTypeTransfersByOffer(UserVisit userVisit, Offer offer) {
        return getOfferChainTypeTransfers(userVisit, getOfferChainTypesByOffer(offer));
    }
    
    public List<OfferChainTypeTransfer> getOfferChainTypeTransfersByChainType(UserVisit userVisit, ChainType chainType) {
        return getOfferChainTypeTransfers(userVisit, getOfferChainTypesByChainType(chainType));
    }
    
    public void updateOfferChainTypeFromValue(OfferChainTypeValue offerChainTypeValue, BasePK updatedBy) {
        if(offerChainTypeValue.hasBeenModified()) {
            var offerChainType = offerChainTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     offerChainTypeValue.getPrimaryKey());
            
            offerChainType.setThruTime(session.getStartTime());
            offerChainType.store();

            var offer = offerChainType.getOffer(); // Not Updated
            var offerPK = offer.getPrimaryKey(); // Not Updated
            var chainTypePK = offerChainType.getChainTypePK(); // Not Updated
            var chainPK = offerChainTypeValue.getChainPK();
            
            offerChainType = offerChainTypeFactory.create(offerPK, chainTypePK, chainPK,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(offerPK, EventTypes.MODIFY, offerChainType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteOfferChainType(OfferChainType offerChainType, BasePK deletedBy) {
        offerChainType.setThruTime(session.getStartTime());
        
        sendEvent(offerChainType.getOfferPK(), EventTypes.MODIFY, offerChainType.getPrimaryKey(),
                EventTypes.DELETE, deletedBy);
    }
    
    public void deleteOfferChainTypes(List<OfferChainType> offerChainTypes, BasePK deletedBy) {
        offerChainTypes.forEach((offerChainType) -> 
                deleteOfferChainType(offerChainType, deletedBy)
        );
    }
    
    public void deleteOfferChainTypesByOffer(Offer offer, BasePK deletedBy) {
        deleteOfferChainTypes(getOfferChainTypesByOfferForUpdate(offer), deletedBy);
    }
    
    public void deleteOfferChainTypesByChainType(ChainType chainType, BasePK deletedBy) {
        deleteOfferChainTypes(getOfferChainTypesByChainTypeForUpdate(chainType), deletedBy);
    }
    
    public void deleteOfferChainTypesByChain(Chain chain, BasePK deletedBy) {
        deleteOfferChainTypes(getOfferChainTypesByChainForUpdate(chain), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Offer Searches
    // --------------------------------------------------------------------------------

    @Inject
    private SearchResultFactory searchResultFactory;

    private List<Long> getOfferIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var cachedSearch = search.getCachedSearch();
        Select<?> query;

        if(cachedSearch == null) {
            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(SearchResults)
                    .join(EntityInstances).onKey(SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(SearchResults.SEARCH.eq(search.getPrimaryKey()))
                    .orderBy(SearchResults.SORT_ORDER, SearchResults.ENTITY_INSTANCE), SearchResultFactory.class);
        } else {
            var cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);

            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);

            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(CachedExecutedSearchResults)
                    .join(EntityInstances).onKey(CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(CachedExecutedSearchResults.CACHED_EXECUTED_SEARCH.eq(cachedExecutedSearch.getPrimaryKey()))
                    .orderBy(CachedExecutedSearchResults.SORT_ORDER, CachedExecutedSearchResults.ENTITY_INSTANCE),
                    CachedExecutedSearchResultFactory.class);
        }

        return query.fetch(EntityInstances.ENTITY_UNIQUE);
    }

    public List<OfferResultTransfer> getOfferResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityIds = getOfferIdsFromUserVisitSearch(userVisitSearch);
        var offerResultTransfers = new ArrayList<OfferResultTransfer>(entityIds.size());
        var options = session.getOptions();
        var includeOffer = options != null && options.contains(SearchOptions.OfferResultIncludeOffer);

        for(var entityUniqueId : entityIds) {
            var offer = offerFactory.getEntityFromPK(EntityPermission.READ_ONLY, new OfferPK(entityUniqueId));
            var offerDetail = offer.getLastDetail();

            offerResultTransfers.add(new OfferResultTransfer(offerDetail.getOfferName(),
                    includeOffer ? getOfferTransfer(userVisit, offer) : null));
        }

        return offerResultTransfers;
    }

}
