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

package com.echothree.model.control.offer.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.offer.common.choice.OfferChoicesBean;
import com.echothree.model.control.offer.common.transfer.OfferChainTypeTransfer;
import com.echothree.model.control.offer.common.transfer.OfferCustomerTypeTransfer;
import com.echothree.model.control.offer.common.transfer.OfferDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.OfferResultTransfer;
import com.echothree.model.control.offer.common.transfer.OfferTransfer;
import com.echothree.model.control.offer.server.logic.OfferItemLogic;
import com.echothree.model.control.search.common.SearchOptions;
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
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.factory.SelectorFactory;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class OfferControl
        extends BaseOfferControl {
    
    /** Creates a new instance of OfferControl */
    protected OfferControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Offers
    // --------------------------------------------------------------------------------

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

        var offer = OfferFactory.getInstance().create();
        var offerDetail = OfferDetailFactory.getInstance().create(offer, offerName, salesOrderSequence, departmentParty, offerItemSelector,
                offerItemPriceFilter, isDefault, sortOrder, session.getStartTimeLong(), Session.MAX_TIME_LONG);
        
        // Convert to R/W
        offer = OfferFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, offer.getPrimaryKey());
        offer.setActiveDetail(offerDetail);
        offer.setLastDetail(offerDetail);
        offer.store();
        
        sendEvent(offer.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return offer;
    }

    public long countOffers() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM offers, offerdetails " +
                "WHERE ofr_activedetailid = ofrdt_offerdetailid");
    }

    public long countOffersBySalesOrderSequence(Sequence sequence) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM offers, offerdetails " +
                "WHERE ofr_activedetailid = ofrdt_offerdetailid AND ofrdt_salesordersequenceid = ?",
                sequence);
    }

    public long countOffersByDepartmentParty(Party departmentParty) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM offers, offerdetails " +
                "WHERE ofr_activedetailid = ofrdt_offerdetailid AND ofrdt_departmentpartyid = ?",
                departmentParty);
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

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Offer */
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

        var ps = OfferFactory.getInstance().prepareStatement(query);
        
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

            var ps = OfferFactory.getInstance().prepareStatement(query);
            
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

    public List<Offer> getOffersByDepartmentParty(Party departmentParty) {
        List<Offer> offers;

        try {
            var ps = OfferFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM offers, offerdetails " +
                    "WHERE ofr_activedetailid = ofrdt_offerdetailid AND ofrdt_departmentpartyid = ? " +
                    "ORDER BY ofrdt_sortorder, ofrdt_offername " +
                    "_LIMIT_");

            ps.setLong(1, departmentParty.getPrimaryKey().getEntityId());

            offers = OfferFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return offers;
    }

    public List<Offer> getOffersByOfferItemSelector(Selector offerItemSelector) {
        List<Offer> offers;

        try {
            var ps = OfferFactory.getInstance().prepareStatement(
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
        var ps = OfferFactory.getInstance().prepareStatement(
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
        var ps = SelectorFactory.getInstance().prepareStatement(
                "SELECT DISTINCT _ALL_ " +
                "FROM offers, offerdetails, selectors " +
                "WHERE ofr_activedetailid = ofrdt_offerdetailid AND ofrdt_offeritemselectorid = sl_selectorid");
        
        return SelectorFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
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
            var offer = OfferFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerDetailValue.getOfferPK());
            var offerDetail = offer.getActiveDetailForUpdate();
            
            offerDetail.setThruTime(session.getStartTimeLong());
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
            
            offerDetail = OfferDetailFactory.getInstance().create(offerPK, offerName, salesOrderSequencePK, departmentPartyPK, offerItemSelectorPK,
                    offerItemPriceFilterPK, isDefault, sortOrder, session.getStartTimeLong(), Session.MAX_TIME_LONG);
            
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
        var offerUseControl = Session.getModelController(OfferUseControl.class);

        deleteOfferCustomerTypesByOffer(offer, deletedBy);
        deleteOfferChainTypesByOffer(offer, deletedBy);
        OfferItemLogic.getInstance().deleteOfferItemsByOffer(offer, deletedBy);
        offerUseControl.deleteOfferUsesByOffer(offer, deletedBy);
        deleteOfferDescriptionsByOffer(offer, deletedBy);

        removeOfferTimeByOffer(offer);

        var offerDetail = offer.getLastDetailForUpdate();
        offerDetail.setThruTime(session.getStartTimeLong());
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
    
    public OfferDescription createOfferDescription(Offer offer, Language language, String description, BasePK createdBy) {
        var offerDescription = OfferDescriptionFactory.getInstance().create(offer, language, description,
                session.getStartTimeLong(), Session.MAX_TIME_LONG);
        
        sendEvent(offer.getPrimaryKey(), EventTypes.MODIFY, offerDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
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

            var ps = OfferDescriptionFactory.getInstance().prepareStatement(query);
            
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

            var ps = OfferDescriptionFactory.getInstance().prepareStatement(query);
            
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
            var offerDescription = OfferDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerDescriptionValue.getPrimaryKey());
            
            offerDescription.setThruTime(session.getStartTimeLong());
            offerDescription.store();

            var offer = offerDescription.getOffer();
            var language = offerDescription.getLanguage();
            var description = offerDescriptionValue.getDescription();
            
            offerDescription = OfferDescriptionFactory.getInstance().create(offer, language, description,
                    session.getStartTimeLong(), Session.MAX_TIME_LONG);
            
            sendEvent(offer.getPrimaryKey(), EventTypes.MODIFY, offerDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteOfferDescription(OfferDescription offerDescription, BasePK deletedBy) {
        offerDescription.setThruTime(session.getStartTimeLong());
        
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
        var offerTime = getOfferTimeForUpdate(offer);

        if(offerTime != null) {
            offerTime.remove();
        }
    }

    // --------------------------------------------------------------------------------
    //   Offer Customer Types
    // --------------------------------------------------------------------------------

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

        var offerCustomerType = OfferCustomerTypeFactory.getInstance().create(offer, customerType,
                isDefault, sortOrder, session.getStartTimeLong(), Session.MAX_TIME_LONG);

        sendEvent(offer.getPrimaryKey(), EventTypes.MODIFY, offerCustomerType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return offerCustomerType;
    }

    private OfferCustomerType getOfferCustomerType(Offer offer, CustomerType customerType, EntityPermission entityPermission) {
        OfferCustomerType offerCustomerType;

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

            var ps = OfferCustomerTypeFactory.getInstance().prepareStatement(query);

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
        var offerCustomerType = getOfferCustomerTypeForUpdate(offer, customerType);

        return offerCustomerType == null? null: offerCustomerType.getOfferCustomerTypeValue().clone();
    }

    private OfferCustomerType getDefaultOfferCustomerType(Offer offer, EntityPermission entityPermission) {
        OfferCustomerType offerCustomerType;

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

            var ps = OfferCustomerTypeFactory.getInstance().prepareStatement(query);

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
        var offerCustomerType = getDefaultOfferCustomerTypeForUpdate(offer);

        return offerCustomerType == null? null: offerCustomerType.getOfferCustomerTypeValue().clone();
    }

    private List<OfferCustomerType> getOfferCustomerTypesByOffer(Offer offer, EntityPermission entityPermission) {
        List<OfferCustomerType> offerCustomerTypes;

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

            var ps = OfferCustomerTypeFactory.getInstance().prepareStatement(query);

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
        List<OfferCustomerType> offerCustomerTypes;

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

            var ps = OfferCustomerTypeFactory.getInstance().prepareStatement(query);

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
            var offerCustomerType = OfferCustomerTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerCustomerTypeValue.getPrimaryKey());

            offerCustomerType.setThruTime(session.getStartTimeLong());
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

            offerCustomerType = OfferCustomerTypeFactory.getInstance().create(offerPK, customerTypePK,
                    isDefault, sortOrder, session.getStartTimeLong(), Session.MAX_TIME_LONG);

            sendEvent(offerPK, EventTypes.MODIFY, offerCustomerType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void updateOfferCustomerTypeFromValue(OfferCustomerTypeValue offerCustomerTypeValue, BasePK updatedBy) {
        updateOfferCustomerTypeFromValue(offerCustomerTypeValue, true, updatedBy);
    }

    public void deleteOfferCustomerType(OfferCustomerType offerCustomerType, BasePK deletedBy) {
        offerCustomerType.setThruTime(session.getStartTimeLong());
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
    
    public OfferChainType createOfferChainType(Offer offer, ChainType chainType, Chain chain, BasePK createdBy) {
        var offerChainType = OfferChainTypeFactory.getInstance().create(offer, chainType, chain,
                session.getStartTimeLong(), Session.MAX_TIME_LONG);
        
        sendEvent(offer.getPrimaryKey(), EventTypes.MODIFY, offerChainType.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
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

            var ps = OfferChainTypeFactory.getInstance().prepareStatement(query);
            
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
        List<OfferChainType> offerChainTypes;
        
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

            var ps = OfferChainTypeFactory.getInstance().prepareStatement(query);
            
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
        List<OfferChainType> offerChainTypes;
        
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

            var ps = OfferChainTypeFactory.getInstance().prepareStatement(query);
            
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
        List<OfferChainType> offerChainTypes;
        
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

            var ps = OfferChainTypeFactory.getInstance().prepareStatement(query);
            
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
            var offerChainType = OfferChainTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerChainTypeValue.getPrimaryKey());
            
            offerChainType.setThruTime(session.getStartTimeLong());
            offerChainType.store();

            var offer = offerChainType.getOffer(); // Not Updated
            var offerPK = offer.getPrimaryKey(); // Not Updated
            var chainTypePK = offerChainType.getChainTypePK(); // Not Updated
            var chainPK = offerChainTypeValue.getChainPK();
            
            offerChainType = OfferChainTypeFactory.getInstance().create(offerPK, chainTypePK, chainPK,
                    session.getStartTimeLong(), Session.MAX_TIME_LONG);
            
            sendEvent(offerPK, EventTypes.MODIFY, offerChainType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteOfferChainType(OfferChainType offerChainType, BasePK deletedBy) {
        offerChainType.setThruTime(session.getStartTimeLong());
        
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

    public List<OfferResultTransfer> getOfferResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var offerResultTransfers = new ArrayList<OfferResultTransfer>();
        var includeOffer = false;

        var options = session.getOptions();
        if(options != null) {
            includeOffer = options.contains(SearchOptions.OfferResultIncludeOffer);
        }

        try {
            var offerControl = Session.getModelController(OfferControl.class);
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
