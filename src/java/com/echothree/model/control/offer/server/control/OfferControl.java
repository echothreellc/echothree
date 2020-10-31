// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.data.chain.common.pk.ChainPK;
import com.echothree.model.data.chain.common.pk.ChainTypePK;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.common.pk.CustomerTypePK;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.filter.common.pk.FilterPK;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.offer.common.pk.OfferPK;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferChainType;
import com.echothree.model.data.offer.server.entity.OfferCustomerType;
import com.echothree.model.data.offer.server.entity.OfferDescription;
import com.echothree.model.data.offer.server.entity.OfferDetail;
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
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.factory.SelectorFactory;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OfferControl
        extends BaseOfferControl {
    
    /** Creates a new instance of OfferControl */
    public OfferControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Offers
    // --------------------------------------------------------------------------------

    /** Use the function in OfferLogic instead. */
    public Offer createOffer(String offerName, Sequence salesOrderSequence, Party departmentParty, Selector offerItemSelector,
            Filter offerItemPriceFilter, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Offer defaultOffer = getDefaultOffer();
        boolean defaultFound = defaultOffer != null;
        
        if(defaultFound && isDefault) {
            OfferDetailValue defaultOfferDetailValue = getDefaultOfferDetailValueForUpdate();
            
            defaultOfferDetailValue.setIsDefault(Boolean.FALSE);
            updateOfferFromValue(defaultOfferDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        Offer offer = OfferFactory.getInstance().create();
        OfferDetail offerDetail = OfferDetailFactory.getInstance().create(offer, offerName, salesOrderSequence, departmentParty, offerItemSelector,
                offerItemPriceFilter, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        offer = OfferFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, offer.getPrimaryKey());
        offer.setActiveDetail(offerDetail);
        offer.setLastDetail(offerDetail);
        offer.store();
        
        sendEventUsingNames(offer.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return offer;
    }
    
    public long countOffers() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM offers, offerdetails " +
                "WHERE ofr_activedetailid = ofrdt_offerdetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.Offer */
    public Offer getOfferByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new OfferPK(entityInstance.getEntityUniqueId());
        var offer = OfferFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return offer;
    }

    public Offer getOfferByEntityInstance(EntityInstance entityInstance) {
        return getOfferByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Offer getOfferByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getOfferByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public Offer getDefaultOffer(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM offers, offerdetails " +
                    "WHERE ofr_activedetailid = ofrdt_offerdetailid AND ofrdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM offers, offerdetails " +
                    "WHERE ofr_activedetailid = ofrdt_offerdetailid AND ofrdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = OfferFactory.getInstance().prepareStatement(query);
        
        return OfferFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        Offer offer;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offers, offerdetails " +
                        "WHERE ofr_activedetailid = ofrdt_offerdetailid AND ofrdt_offername = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offers, offerdetails " +
                        "WHERE ofr_activedetailid = ofrdt_offerdetailid AND ofrdt_offername = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, offerName);
            
            offer = OfferFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offer;
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
    
    public List<Offer> getOffersByOfferItemSelector(Selector offerItemSelector) {
        List<Offer> offers = null;
        
        try {
            PreparedStatement ps = OfferFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM offers, offerdetails " +
                    "WHERE ofr_activedetailid = ofrdt_offerdetailid AND ofrdt_offeritemselectorid = ? " +
                    "ORDER BY ofrdt_sortorder, ofrdt_offername");
            
            ps.setLong(1, offerItemSelector.getPrimaryKey().getEntityId());
            
            offers = OfferFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offers;
    }
    
    private List<Offer> getOffers(EntityPermission entityPermission) {
        PreparedStatement ps = OfferFactory.getInstance().prepareStatement(
                "SELECT _ALL_ "
                + "FROM offers, offerdetails "
                + "WHERE ofr_activedetailid = ofrdt_offerdetailid "
                + "ORDER BY ofrdt_sortorder, ofrdt_offername "
                + "_LIMIT_");

        return OfferFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<Offer> getOffers() {
        return getOffers(EntityPermission.READ_ONLY);
    }
    
    public List<Offer> getOffersForUpdate() {
        return getOffers(EntityPermission.READ_WRITE);
    }
    
    /** Gets a List that contains all the Selectors used by Offers. */
    public List<Selector> getDistinctOfferItemSelectors() {
        PreparedStatement ps = SelectorFactory.getInstance().prepareStatement(
                "SELECT DISTINCT _ALL_ " +
                "FROM offers, offerdetails, selectors " +
                "WHERE ofr_activedetailid = ofrdt_offerdetailid AND ofrdt_offeritemselectorid = sl_selectorid");
        
        return SelectorFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public long countOffersBySelector(Selector selector) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM offers, offerdetails " +
                "WHERE ofr_activedetailid = ofrdt_offerdetailid AND ofrdt_offeritemselectorid = ?",
                selector);
    }

    public long countOffersByFilter(Filter filter) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM offers, offerdetails " +
                "WHERE ofr_activedetailid = ofrdt_offerdetailid AND ofrdt_offeritempricefilterid = ?",
                filter);
    }

    public OfferTransfer getOfferTransfer(UserVisit userVisit, Offer offer) {
        return getOfferTransferCaches(userVisit).getOfferTransferCache().getOfferTransfer(offer);
    }
    
    public List<OfferTransfer> getOfferTransfers(UserVisit userVisit, Collection<Offer> offers) {
        List<OfferTransfer> offerTransfers = new ArrayList<>(offers.size());
        
        offers.stream().forEach((offer) -> {
            offerTransfers.add(getOfferTransferCaches(userVisit).getOfferTransferCache().getOfferTransfer(offer));
        });
        
        return offerTransfers;
    }
    
    public List<OfferTransfer> getOfferTransfers(UserVisit userVisit) {
        return getOfferTransfers(userVisit, getOffers());
    }
    
    public OfferChoicesBean getOfferChoices(String defaultOfferChoice, Language language, boolean allowNullChoice) {
        List<Offer> offers = getOffers();
        int size = offers.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOfferChoice == null) {
                defaultValue = "";
            }
        }

        for(Offer offer : offers) {
            OfferDetail offerDetail = offer.getLastDetail();

            String label = getBestOfferDescription(offer, language);
            String value = offerDetail.getOfferName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultOfferChoice != null && defaultOfferChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && offerDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new OfferChoicesBean(labels, values, defaultValue);
    }

    private void updateOfferFromValue(OfferDetailValue offerDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(offerDetailValue.hasBeenModified()) {
            Offer offer = OfferFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerDetailValue.getOfferPK());
            OfferDetail offerDetail = offer.getActiveDetailForUpdate();
            
            offerDetail.setThruTime(session.START_TIME_LONG);
            offerDetail.store();
            
            OfferPK offerPK = offerDetail.getOfferPK();
            String offerName = offerDetailValue.getOfferName();
            SequencePK salesOrderSequencePK = offerDetailValue.getSalesOrderSequencePK();
            PartyPK departmentPartyPK = offerDetail.getDepartmentPartyPK(); // Not updated
            SelectorPK offerItemSelectorPK = offerDetailValue.getOfferItemSelectorPK();
            FilterPK offerItemPriceFilterPK = offerDetailValue.getOfferItemPriceFilterPK();
            Boolean isDefault = offerDetailValue.getIsDefault();
            Integer sortOrder = offerDetailValue.getSortOrder();
            
            if(checkDefault) {
                Offer defaultOffer = getDefaultOffer();
                boolean defaultFound = defaultOffer != null && !defaultOffer.equals(offer);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    OfferDetailValue defaultOfferDetailValue = getDefaultOfferDetailValueForUpdate();
                    
                    defaultOfferDetailValue.setIsDefault(Boolean.FALSE);
                    updateOfferFromValue(defaultOfferDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            offerDetail = OfferDetailFactory.getInstance().create(offerPK, offerName, salesOrderSequencePK, departmentPartyPK, offerItemSelectorPK,
                    offerItemPriceFilterPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            offer.setActiveDetail(offerDetail);
            offer.setLastDetail(offerDetail);
            
            sendEventUsingNames(offerPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    /** Use the function in OfferLogic instead. */
    public void updateOfferFromValue(OfferDetailValue offerDetailValue, BasePK updatedBy) {
        updateOfferFromValue(offerDetailValue, true, updatedBy);
    }

    /** Use the function in OfferLogic instead. */
    public void deleteOffer(Offer offer, BasePK deletedBy) {
        var offerUseControl = (OfferUseControl)Session.getModelController(OfferUseControl.class);

        deleteOfferCustomerTypesByOffer(offer, deletedBy);
        deleteOfferChainTypesByOffer(offer, deletedBy);
        OfferItemLogic.getInstance().deleteOfferItemsByOffer(offer, deletedBy);
        offerUseControl.deleteOfferUsesByOffer(offer, deletedBy);
        deleteOfferDescriptionsByOffer(offer, deletedBy);

        removeOfferTimeByOffer(offer);
        
        OfferDetail offerDetail = offer.getLastDetailForUpdate();
        offerDetail.setThruTime(session.START_TIME_LONG);
        offer.setActiveDetail(null);
        offer.store();
        
        // Check for default, and pick one if necessary
        Offer defaultOffer = getDefaultOffer();
        if(defaultOffer == null) {
            List<Offer> offers = getOffersForUpdate();
            
            if(!offers.isEmpty()) {
                Iterator<Offer> iter = offers.iterator();
                if(iter.hasNext()) {
                    defaultOffer = iter.next();
                }
                OfferDetailValue offerDetailValue = defaultOffer.getLastDetailForUpdate().getOfferDetailValue().clone();
                
                offerDetailValue.setIsDefault(Boolean.TRUE);
                updateOfferFromValue(offerDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(offer.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Descriptions
    // --------------------------------------------------------------------------------
    
    public OfferDescription createOfferDescription(Offer offer, Language language, String description, BasePK createdBy) {
        OfferDescription offerDescription = OfferDescriptionFactory.getInstance().create(offer, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(offer.getPrimaryKey(), EventTypes.MODIFY.name(), offerDescription.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return offerDescription;
    }
    
    private OfferDescription getOfferDescription(Offer offer, Language language, EntityPermission entityPermission) {
        OfferDescription offerDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offerdescriptions " +
                        "WHERE ofrd_ofr_offerid = ? AND ofrd_lang_languageid = ? AND ofrd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offerdescriptions " +
                        "WHERE ofrd_ofr_offerid = ? AND ofrd_lang_languageid = ? AND ofrd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, offer.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            offerDescription = OfferDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerDescription;
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
        List<OfferDescription> offerDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offerdescriptions, languages " +
                        "WHERE ofrd_ofr_offerid = ? AND ofrd_thrutime = ? AND ofrd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offerdescriptions " +
                        "WHERE ofrd_ofr_offerid = ? AND ofrd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, offer.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            offerDescriptions = OfferDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerDescriptions;
    }
    
    public List<OfferDescription> getOfferDescriptionsByOffer(Offer offer) {
        return getOfferDescriptionsByOffer(offer, EntityPermission.READ_ONLY);
    }
    
    public List<OfferDescription> getOfferDescriptionsByOfferForUpdate(Offer offer) {
        return getOfferDescriptionsByOffer(offer, EntityPermission.READ_WRITE);
    }
    
    public String getBestOfferDescription(Offer offer, Language language) {
        String description;
        OfferDescription offerDescription = getOfferDescription(offer, language);
        
        if(offerDescription == null && !language.getIsDefault()) {
            offerDescription = getOfferDescription(offer, getPartyControl().getDefaultLanguage());
        }
        
        if(offerDescription == null) {
            description = offer.getLastDetail().getOfferName();
        } else {
            description = offerDescription.getDescription();
        }
        
        return description;
    }
    
    public OfferDescriptionTransfer getOfferDescriptionTransfer(UserVisit userVisit, OfferDescription offerDescription) {
        return getOfferTransferCaches(userVisit).getOfferDescriptionTransferCache().getOfferDescriptionTransfer(offerDescription);
    }
    
    public List<OfferDescriptionTransfer> getOfferDescriptionTransfers(UserVisit userVisit, Offer offer) {
        List<OfferDescription> offerDescriptions = getOfferDescriptionsByOffer(offer);
        List<OfferDescriptionTransfer> offerDescriptionTransfers = new ArrayList<>(offerDescriptions.size());
        
        offerDescriptions.stream().forEach((offerDescription) -> {
            offerDescriptionTransfers.add(getOfferTransferCaches(userVisit).getOfferDescriptionTransferCache().getOfferDescriptionTransfer(offerDescription));
        });
        
        return offerDescriptionTransfers;
    }
    
    public void updateOfferDescriptionFromValue(OfferDescriptionValue offerDescriptionValue, BasePK updatedBy) {
        if(offerDescriptionValue.hasBeenModified()) {
            OfferDescription offerDescription = OfferDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerDescriptionValue.getPrimaryKey());
            
            offerDescription.setThruTime(session.START_TIME_LONG);
            offerDescription.store();
            
            Offer offer = offerDescription.getOffer();
            Language language = offerDescription.getLanguage();
            String description = offerDescriptionValue.getDescription();
            
            offerDescription = OfferDescriptionFactory.getInstance().create(offer, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(offer.getPrimaryKey(), EventTypes.MODIFY.name(), offerDescription.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteOfferDescription(OfferDescription offerDescription, BasePK deletedBy) {
        offerDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(offerDescription.getOfferPK(), EventTypes.MODIFY.name(), offerDescription.getPrimaryKey(),
                EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteOfferDescriptionsByOffer(Offer offer, BasePK deletedBy) {
        List<OfferDescription> offerDescriptions = getOfferDescriptionsByOfferForUpdate(offer);
        
        offerDescriptions.forEach((offerDescription) -> 
                deleteOfferDescription(offerDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Offer Times
    // --------------------------------------------------------------------------------

    public OfferTime createOfferTime(Offer offer) {
        return OfferTimeFactory.getInstance().create(offer, null, null, null, null);
    }

    private static final Map<EntityPermission, String> getOfferTimeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM offertimes " +
                    "WHERE ofrtm_ofr_offerid = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM offertimes " +
                    "WHERE ofrtm_ofr_offerid = ? " +
                    "FOR UPDATE"
    );

    private OfferTime getOfferTime(Offer offer, EntityPermission entityPermission) {
        return OfferTimeFactory.getInstance().getEntityFromQuery(entityPermission, getOfferTimeQueries, offer);
    }

    public OfferTime getOfferTime(Offer offer) {
        return getOfferTime(offer, EntityPermission.READ_ONLY);
    }

    public OfferTime getOfferTimeForUpdate(Offer offer) {
        return getOfferTime(offer, EntityPermission.READ_WRITE);
    }

    public void removeOfferTimeByOffer(Offer offer) {
        OfferTime offerTime = getOfferTimeForUpdate(offer);

        if(offerTime != null) {
            offerTime.remove();
        }
    }

    // --------------------------------------------------------------------------------
    //   Offer Customer Types
    // --------------------------------------------------------------------------------

    public OfferCustomerType createOfferCustomerType(Offer offer, CustomerType customerType, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        OfferCustomerType defaultOfferCustomerType = getDefaultOfferCustomerType(offer);
        boolean defaultFound = defaultOfferCustomerType != null;

        if(defaultFound && isDefault) {
            OfferCustomerTypeValue defaultOfferCustomerTypeValue = getDefaultOfferCustomerTypeValueForUpdate(offer);

            defaultOfferCustomerTypeValue.setIsDefault(Boolean.FALSE);
            updateOfferCustomerTypeFromValue(defaultOfferCustomerTypeValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        OfferCustomerType offerCustomerType = OfferCustomerTypeFactory.getInstance().create(offer, customerType,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(offer.getPrimaryKey(), EventTypes.MODIFY.name(), offerCustomerType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return offerCustomerType;
    }

    private OfferCustomerType getOfferCustomerType(Offer offer, CustomerType customerType, EntityPermission entityPermission) {
        OfferCustomerType offerCustomerType = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offercustomertypes " +
                        "WHERE ofrcuty_ofr_offerid = ? AND ofrcuty_cuty_customertypeid = ? AND ofrcuty_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offercustomertypes " +
                        "WHERE ofrcuty_ofr_offerid = ? AND ofrcuty_cuty_customertypeid = ? AND ofrcuty_thrutime = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = OfferCustomerTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, offer.getPrimaryKey().getEntityId());
            ps.setLong(2, customerType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            offerCustomerType = OfferCustomerTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return offerCustomerType;
    }

    public OfferCustomerType getOfferCustomerType(Offer offer, CustomerType customerType) {
        return getOfferCustomerType(offer, customerType, EntityPermission.READ_ONLY);
    }

    public OfferCustomerType getOfferCustomerTypeForUpdate(Offer offer, CustomerType customerType) {
        return getOfferCustomerType(offer, customerType, EntityPermission.READ_WRITE);
    }

    public OfferCustomerTypeValue getOfferCustomerTypeValueForUpdate(Offer offer, CustomerType customerType) {
        OfferCustomerType offerCustomerType = getOfferCustomerTypeForUpdate(offer, customerType);

        return offerCustomerType == null? null: offerCustomerType.getOfferCustomerTypeValue().clone();
    }

    private OfferCustomerType getDefaultOfferCustomerType(Offer offer, EntityPermission entityPermission) {
        OfferCustomerType offerCustomerType = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offercustomertypes " +
                        "WHERE ofrcuty_ofr_offerid = ? AND ofrcuty_isdefault = 1 AND ofrcuty_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offercustomertypes " +
                        "WHERE ofrcuty_ofr_offerid = ? AND ofrcuty_isdefault = 1 AND ofrcuty_thrutime = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = OfferCustomerTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, offer.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            offerCustomerType = OfferCustomerTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return offerCustomerType;
    }

    public OfferCustomerType getDefaultOfferCustomerType(Offer offer) {
        return getDefaultOfferCustomerType(offer, EntityPermission.READ_ONLY);
    }

    public OfferCustomerType getDefaultOfferCustomerTypeForUpdate(Offer offer) {
        return getDefaultOfferCustomerType(offer, EntityPermission.READ_WRITE);
    }

    public OfferCustomerTypeValue getDefaultOfferCustomerTypeValueForUpdate(Offer offer) {
        OfferCustomerType offerCustomerType = getDefaultOfferCustomerTypeForUpdate(offer);

        return offerCustomerType == null? null: offerCustomerType.getOfferCustomerTypeValue().clone();
    }

    private List<OfferCustomerType> getOfferCustomerTypesByOffer(Offer offer, EntityPermission entityPermission) {
        List<OfferCustomerType> offerCustomerTypes = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offercustomertypes, customertypes, customertypedetails " +
                        "WHERE ofrcuty_ofr_offerid = ? AND ofrcuty_thrutime = ? " +
                        "AND ofrcuty_cuty_customertypeid = cuty_customertypeid AND cuty_lastdetailid = cutydt_customertypedetailid " +
                        "ORDER BY cutydt_sortorder, cutydt_customertypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offercustomertypes " +
                        "WHERE ofrcuty_ofr_offerid = ? AND ofrcuty_thrutime = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = OfferCustomerTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, offer.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            offerCustomerTypes = OfferCustomerTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return offerCustomerTypes;
    }

    public List<OfferCustomerType> getOfferCustomerTypesByOffer(Offer offer) {
        return getOfferCustomerTypesByOffer(offer, EntityPermission.READ_ONLY);
    }

    public List<OfferCustomerType> getOfferCustomerTypesByOfferForUpdate(Offer offer) {
        return getOfferCustomerTypesByOffer(offer, EntityPermission.READ_WRITE);
    }

    private List<OfferCustomerType> getOfferCustomerTypesByCustomerType(CustomerType customerType, EntityPermission entityPermission) {
        List<OfferCustomerType> offerCustomerTypes = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offercustomertypes, offers, offerdetails " +
                        "WHERE ofrcuty_cuty_customertypeid = ? AND ofrcuty_thrutime = ? " +
                        "AND ofrcuty_ofr_offerid = ofr_offerid AND ofr_lastdetailid = ofrdt_offerdetailid " +
                        "ORDER BY ofrdt_sortorder, ofrdt_offername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offercustomertypes " +
                        "WHERE ofrcuty_cuty_customertypeid = ? AND ofrcuty_thrutime = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = OfferCustomerTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, customerType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            offerCustomerTypes = OfferCustomerTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return offerCustomerTypes;
    }

    public List<OfferCustomerType> getOfferCustomerTypesByCustomerType(CustomerType customerType) {
        return getOfferCustomerTypesByCustomerType(customerType, EntityPermission.READ_ONLY);
    }

    public List<OfferCustomerType> getOfferCustomerTypesByCustomerTypeForUpdate(CustomerType customerType) {
        return getOfferCustomerTypesByCustomerType(customerType, EntityPermission.READ_WRITE);
    }

    public List<OfferCustomerTypeTransfer> getOfferCustomerTypeTransfers(UserVisit userVisit, Collection<OfferCustomerType> offerCustomerTypes) {
        List<OfferCustomerTypeTransfer> offerCustomerTypeTransfers = new ArrayList<>(offerCustomerTypes.size());
        OfferCustomerTypeTransferCache offerCustomerTypeTransferCache = getOfferTransferCaches(userVisit).getOfferCustomerTypeTransferCache();

        offerCustomerTypes.forEach((offerCustomerType) ->
                offerCustomerTypeTransfers.add(offerCustomerTypeTransferCache.getOfferCustomerTypeTransfer(offerCustomerType))
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
        return getOfferTransferCaches(userVisit).getOfferCustomerTypeTransferCache().getOfferCustomerTypeTransfer(offerCustomerType);
    }

    private void updateOfferCustomerTypeFromValue(OfferCustomerTypeValue offerCustomerTypeValue, boolean checkDefault, BasePK updatedBy) {
        if(offerCustomerTypeValue.hasBeenModified()) {
            OfferCustomerType offerCustomerType = OfferCustomerTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerCustomerTypeValue.getPrimaryKey());

            offerCustomerType.setThruTime(session.START_TIME_LONG);
            offerCustomerType.store();

            Offer offer = offerCustomerType.getOffer(); // Not Updated
            OfferPK offerPK = offer.getPrimaryKey(); // Not Updated
            CustomerTypePK customerTypePK = offerCustomerType.getCustomerTypePK(); // Not Updated
            Boolean isDefault = offerCustomerTypeValue.getIsDefault();
            Integer sortOrder = offerCustomerTypeValue.getSortOrder();

            if(checkDefault) {
                OfferCustomerType defaultOfferCustomerType = getDefaultOfferCustomerType(offer);
                boolean defaultFound = defaultOfferCustomerType != null && !defaultOfferCustomerType.equals(offerCustomerType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    OfferCustomerTypeValue defaultOfferCustomerTypeValue = getDefaultOfferCustomerTypeValueForUpdate(offer);

                    defaultOfferCustomerTypeValue.setIsDefault(Boolean.FALSE);
                    updateOfferCustomerTypeFromValue(defaultOfferCustomerTypeValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            offerCustomerType = OfferCustomerTypeFactory.getInstance().create(offerPK, customerTypePK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(offerPK, EventTypes.MODIFY.name(), offerCustomerType.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void updateOfferCustomerTypeFromValue(OfferCustomerTypeValue offerCustomerTypeValue, BasePK updatedBy) {
        updateOfferCustomerTypeFromValue(offerCustomerTypeValue, true, updatedBy);
    }

    public void deleteOfferCustomerType(OfferCustomerType offerCustomerType, BasePK deletedBy) {
        offerCustomerType.setThruTime(session.START_TIME_LONG);
        offerCustomerType.store();

        // Check for default, and pick one if necessary
        Offer offer = offerCustomerType.getOffer();
        OfferCustomerType defaultOfferCustomerType = getDefaultOfferCustomerType(offer);
        if(defaultOfferCustomerType == null) {
            List<OfferCustomerType> offerCustomerTypes = getOfferCustomerTypesByOfferForUpdate(offer);

            if(!offerCustomerTypes.isEmpty()) {
                Iterator<OfferCustomerType> iter = offerCustomerTypes.iterator();
                if(iter.hasNext()) {
                    defaultOfferCustomerType = iter.next();
                }
                OfferCustomerTypeValue offerCustomerTypeValue = defaultOfferCustomerType.getOfferCustomerTypeValue().clone();

                offerCustomerTypeValue.setIsDefault(Boolean.TRUE);
                updateOfferCustomerTypeFromValue(offerCustomerTypeValue, false, deletedBy);
            }
        }

        sendEventUsingNames(offer.getPrimaryKey(), EventTypes.MODIFY.name(), offerCustomerType.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
    
    public OfferChainType createOfferChainType(Offer offer, ChainType chainType, Chain chain, BasePK createdBy) {
        OfferChainType offerChainType = OfferChainTypeFactory.getInstance().create(offer, chainType, chain,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(offer.getPrimaryKey(), EventTypes.MODIFY.name(), offerChainType.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return offerChainType;
    }
    
    private OfferChainType getOfferChainType(Offer offer, ChainType chainType, EntityPermission entityPermission) {
        OfferChainType offerChainType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offerchaintypes " +
                        "WHERE ofrchntyp_ofr_offerid = ? AND ofrchntyp_chntyp_chaintypeid = ? AND ofrchntyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offerchaintypes " +
                        "WHERE ofrchntyp_ofr_offerid = ? AND ofrchntyp_chntyp_chaintypeid = ? AND ofrchntyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferChainTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, offer.getPrimaryKey().getEntityId());
            ps.setLong(2, chainType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            offerChainType = OfferChainTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerChainType;
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
        List<OfferChainType> offerChainTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM offerchaintypes, chaintypes, chaintypedetails, chainkinds, chainkinddetails "
                        + "WHERE ofrchntyp_ofr_offerid = ? AND ofrchntyp_thrutime = ? "
                        + "AND ofrchntyp_chntyp_chaintypeid = chntyp_chaintypeid AND chntyp_lastdetailid = chntypdt_chaintypedetailid "
                        + "AND chntypdt_chnk_chainkindid = chnk_chainkindid AND chnk_lastdetailid = chnkdt_chainkinddetailid "
                        + "ORDER BY chntypdt_sortorder, chntypdt_chaintypename, chnkdt_sortorder, chnkdt_chainkindname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM offerchaintypes "
                        + "WHERE ofrchntyp_ofr_offerid = ? AND ofrchntyp_thrutime = ? "
                        + "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferChainTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, offer.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            offerChainTypes = OfferChainTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerChainTypes;
    }
    
    public List<OfferChainType> getOfferChainTypesByOffer(Offer offer) {
        return getOfferChainTypesByOffer(offer, EntityPermission.READ_ONLY);
    }
    
    public List<OfferChainType> getOfferChainTypesByOfferForUpdate(Offer offer) {
        return getOfferChainTypesByOffer(offer, EntityPermission.READ_WRITE);
    }
    
    private List<OfferChainType> getOfferChainTypesByChainType(ChainType chainType, EntityPermission entityPermission) {
        List<OfferChainType> offerChainTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offerchaintypes, offers, offerdetails " +
                        "WHERE ofrchntyp_chntyp_chaintypeid = ? AND ofrchntyp_thrutime = ? " +
                        "AND ofrchntyp_ofr_offerid = ofr_offerid AND ofr_activedetailid = ofrdt_offerdetailid " +
                        "ORDER BY ofrdt_sortorder, ofrdt_offername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offerchaintypes " +
                        "WHERE ofrchntyp_chntyp_chaintypeid = ? AND ofrchntyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferChainTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, chainType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            offerChainTypes = OfferChainTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerChainTypes;
    }
    
    public List<OfferChainType> getOfferChainTypesByChainType(ChainType chainType) {
        return getOfferChainTypesByChainType(chainType, EntityPermission.READ_ONLY);
    }
    
    public List<OfferChainType> getOfferChainTypesByChainTypeForUpdate(ChainType chainType) {
        return getOfferChainTypesByChainType(chainType, EntityPermission.READ_WRITE);
    }
    
    private List<OfferChainType> getOfferChainTypesByChain(Chain chain, EntityPermission entityPermission) {
        List<OfferChainType> offerChainTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offerchaintypes, offers, offerdetails " +
                        "WHERE ofrchntyp_chn_chainid = ? AND ofrchntyp_thrutime = ? " +
                        "AND ofrchntyp_ofr_offerid = ofr_offerid AND ofr_activedetailid = ofrdt_offerdetailid " +
                        "ORDER BY ofrdt_sortorder, ofrdt_offername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offerchaintypes " +
                        "WHERE ofrchntyp_chntyp_chaintypeid = ? AND ofrchntyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferChainTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, chain.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            offerChainTypes = OfferChainTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerChainTypes;
    }
    
    public List<OfferChainType> getOfferChainTypesByChain(Chain chain) {
        return getOfferChainTypesByChain(chain, EntityPermission.READ_ONLY);
    }
    
    public List<OfferChainType> getOfferChainTypesByChainForUpdate(Chain chain) {
        return getOfferChainTypesByChain(chain, EntityPermission.READ_WRITE);
    }
    
    public OfferChainTypeTransfer getOfferChainTypeTransfer(UserVisit userVisit, OfferChainType offerChainType) {
        return getOfferTransferCaches(userVisit).getOfferChainTypeTransferCache().getOfferChainTypeTransfer(offerChainType);
    }
    
    public List<OfferChainTypeTransfer> getOfferChainTypeTransfers(UserVisit userVisit, Collection<OfferChainType> offerChainTypes) {
        List<OfferChainTypeTransfer> offerChainTypeTransfers = new ArrayList<>(offerChainTypes.size());
        OfferChainTypeTransferCache offerChainTypeTransferCache = getOfferTransferCaches(userVisit).getOfferChainTypeTransferCache();
        
        offerChainTypes.forEach((offerChainType) ->
                offerChainTypeTransfers.add(offerChainTypeTransferCache.getOfferChainTypeTransfer(offerChainType))
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
            OfferChainType offerChainType = OfferChainTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerChainTypeValue.getPrimaryKey());
            
            offerChainType.setThruTime(session.START_TIME_LONG);
            offerChainType.store();
            
            Offer offer = offerChainType.getOffer(); // Not Updated
            OfferPK offerPK = offer.getPrimaryKey(); // Not Updated
            ChainTypePK chainTypePK = offerChainType.getChainTypePK(); // Not Updated
            ChainPK chainPK = offerChainTypeValue.getChainPK();
            
            offerChainType = OfferChainTypeFactory.getInstance().create(offerPK, chainTypePK, chainPK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(offerPK, EventTypes.MODIFY.name(), offerChainType.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteOfferChainType(OfferChainType offerChainType, BasePK deletedBy) {
        offerChainType.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(offerChainType.getOfferPK(), EventTypes.MODIFY.name(), offerChainType.getPrimaryKey(),
                EventTypes.DELETE.name(), deletedBy);
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

    public List<OfferResultTransfer> getOfferResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var offerResultTransfers = new ArrayList<OfferResultTransfer>();
        var includeOffer = false;

        var options = session.getOptions();
        if(options != null) {
            includeOffer = options.contains(SearchOptions.OfferResultIncludeOffer);
        }

        try {
            var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
            var ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                            "FROM searchresults, entityinstances " +
                            "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                            "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                            "_LIMIT_");

            ps.setLong(1, search.getPrimaryKey().getEntityId());

            try (var rs = ps.executeQuery()) {
                while(rs.next()) {
                    var offer = OfferFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new OfferPK(rs.getLong(1)));
                    var offerDetail = offer.getLastDetail();

                    offerResultTransfers.add(new OfferResultTransfer(offerDetail.getOfferName(),
                            includeOffer ? offerControl.getOfferTransfer(userVisit, offer) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return offerResultTransfers;
    }

}
