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
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.offer.common.choice.OfferChoicesBean;
import com.echothree.model.control.offer.common.choice.SourceChoicesBean;
import com.echothree.model.control.offer.common.choice.UseChoicesBean;
import com.echothree.model.control.offer.common.choice.UseTypeChoicesBean;
import com.echothree.model.control.offer.common.transfer.OfferChainTypeTransfer;
import com.echothree.model.control.offer.common.transfer.OfferCustomerTypeTransfer;
import com.echothree.model.control.offer.common.transfer.OfferDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.OfferItemPriceTransfer;
import com.echothree.model.control.offer.common.transfer.OfferItemTransfer;
import com.echothree.model.control.offer.common.transfer.OfferNameElementDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.OfferNameElementTransfer;
import com.echothree.model.control.offer.common.transfer.OfferTransfer;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.offer.common.transfer.SourceTransfer;
import com.echothree.model.control.offer.common.transfer.UseDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.UseNameElementDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.UseNameElementTransfer;
import com.echothree.model.control.offer.common.transfer.UseTransfer;
import com.echothree.model.control.offer.common.transfer.UseTypeDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.UseTypeTransfer;
import com.echothree.model.control.offer.server.logic.OfferLogic;
import com.echothree.model.control.offer.server.transfer.OfferChainTypeTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferCustomerTypeTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferItemPriceTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferItemTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferNameElementDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferNameElementTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferTransferCaches;
import com.echothree.model.control.offer.server.transfer.OfferUseTransferCache;
import com.echothree.model.control.offer.server.transfer.UseNameElementDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.UseNameElementTransferCache;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.chain.common.pk.ChainPK;
import com.echothree.model.data.chain.common.pk.ChainTypePK;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.core.common.pk.AppearancePK;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.factory.AppearanceFactory;
import com.echothree.model.data.customer.common.pk.CustomerTypePK;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.filter.common.pk.FilterPK;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemPriceType;
import com.echothree.model.data.offer.common.pk.OfferItemPricePK;
import com.echothree.model.data.offer.common.pk.OfferNameElementPK;
import com.echothree.model.data.offer.common.pk.OfferPK;
import com.echothree.model.data.offer.common.pk.OfferUsePK;
import com.echothree.model.data.offer.common.pk.SourcePK;
import com.echothree.model.data.offer.common.pk.UseNameElementPK;
import com.echothree.model.data.offer.common.pk.UsePK;
import com.echothree.model.data.offer.common.pk.UseTypePK;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferChainType;
import com.echothree.model.data.offer.server.entity.OfferCustomerType;
import com.echothree.model.data.offer.server.entity.OfferDescription;
import com.echothree.model.data.offer.server.entity.OfferDetail;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemFixedPrice;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferItemVariablePrice;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.offer.server.entity.OfferNameElementDescription;
import com.echothree.model.data.offer.server.entity.OfferNameElementDetail;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.OfferUseDetail;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.offer.server.entity.SourceDetail;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseDescription;
import com.echothree.model.data.offer.server.entity.UseDetail;
import com.echothree.model.data.offer.server.entity.UseNameElement;
import com.echothree.model.data.offer.server.entity.UseNameElementDescription;
import com.echothree.model.data.offer.server.entity.UseNameElementDetail;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.entity.UseTypeDescription;
import com.echothree.model.data.offer.server.entity.UseTypeDetail;
import com.echothree.model.data.offer.server.factory.OfferChainTypeFactory;
import com.echothree.model.data.offer.server.factory.OfferCustomerTypeFactory;
import com.echothree.model.data.offer.server.factory.OfferDescriptionFactory;
import com.echothree.model.data.offer.server.factory.OfferDetailFactory;
import com.echothree.model.data.offer.server.factory.OfferFactory;
import com.echothree.model.data.offer.server.factory.OfferItemFactory;
import com.echothree.model.data.offer.server.factory.OfferItemFixedPriceFactory;
import com.echothree.model.data.offer.server.factory.OfferItemPriceFactory;
import com.echothree.model.data.offer.server.factory.OfferItemVariablePriceFactory;
import com.echothree.model.data.offer.server.factory.OfferNameElementDescriptionFactory;
import com.echothree.model.data.offer.server.factory.OfferNameElementDetailFactory;
import com.echothree.model.data.offer.server.factory.OfferNameElementFactory;
import com.echothree.model.data.offer.server.factory.OfferUseDetailFactory;
import com.echothree.model.data.offer.server.factory.OfferUseFactory;
import com.echothree.model.data.offer.server.factory.SourceDetailFactory;
import com.echothree.model.data.offer.server.factory.SourceFactory;
import com.echothree.model.data.offer.server.factory.UseDescriptionFactory;
import com.echothree.model.data.offer.server.factory.UseDetailFactory;
import com.echothree.model.data.offer.server.factory.UseFactory;
import com.echothree.model.data.offer.server.factory.UseNameElementDescriptionFactory;
import com.echothree.model.data.offer.server.factory.UseNameElementDetailFactory;
import com.echothree.model.data.offer.server.factory.UseNameElementFactory;
import com.echothree.model.data.offer.server.factory.UseTypeDescriptionFactory;
import com.echothree.model.data.offer.server.factory.UseTypeDetailFactory;
import com.echothree.model.data.offer.server.factory.UseTypeFactory;
import com.echothree.model.data.offer.server.value.OfferChainTypeValue;
import com.echothree.model.data.offer.server.value.OfferCustomerTypeValue;
import com.echothree.model.data.offer.server.value.OfferDescriptionValue;
import com.echothree.model.data.offer.server.value.OfferDetailValue;
import com.echothree.model.data.offer.server.value.OfferItemFixedPriceValue;
import com.echothree.model.data.offer.server.value.OfferItemVariablePriceValue;
import com.echothree.model.data.offer.server.value.OfferNameElementDescriptionValue;
import com.echothree.model.data.offer.server.value.OfferNameElementDetailValue;
import com.echothree.model.data.offer.server.value.OfferUseDetailValue;
import com.echothree.model.data.offer.server.value.SourceDetailValue;
import com.echothree.model.data.offer.server.value.UseDescriptionValue;
import com.echothree.model.data.offer.server.value.UseDetailValue;
import com.echothree.model.data.offer.server.value.UseNameElementDescriptionValue;
import com.echothree.model.data.offer.server.value.UseNameElementDetailValue;
import com.echothree.model.data.offer.server.value.UseTypeDescriptionValue;
import com.echothree.model.data.offer.server.value.UseTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.factory.SelectorFactory;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.transfer.HistoryTransfer;
import com.echothree.util.common.transfer.ListWrapper;
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

public class OfferControl
        extends BaseOfferControl {
    
    /** Creates a new instance of OfferControl */
    public OfferControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Name Elements
    // --------------------------------------------------------------------------------
    
    public OfferNameElement createOfferNameElement(String offerNameElementName, Integer offset, Integer length,
            String validationPattern, BasePK createdBy) {
        OfferNameElement offerNameElement = OfferNameElementFactory.getInstance().create();
        OfferNameElementDetail offerNameElementDetail = OfferNameElementDetailFactory.getInstance().create(session,
                offerNameElement, offerNameElementName, offset, length, validationPattern, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        offerNameElement = OfferNameElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                offerNameElement.getPrimaryKey());
        offerNameElement.setActiveDetail(offerNameElementDetail);
        offerNameElement.setLastDetail(offerNameElementDetail);
        offerNameElement.store();
        
        sendEventUsingNames(offerNameElement.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return offerNameElement;
    }
    
    private OfferNameElement getOfferNameElementByName(String offerNameElementName, EntityPermission entityPermission) {
        OfferNameElement offerNameElement = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offernameelements, offernameelementdetails " +
                        "WHERE ofrne_activedetailid = ofrnedt_offernameelementdetailid AND ofrnedt_offernameelementname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offernameelements, offernameelementdetails " +
                        "WHERE ofrne_activedetailid = ofrnedt_offernameelementdetailid AND ofrnedt_offernameelementname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferNameElementFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, offerNameElementName);
            
            offerNameElement = OfferNameElementFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerNameElement;
    }
    
    public OfferNameElement getOfferNameElementByName(String offerNameElementName) {
        return getOfferNameElementByName(offerNameElementName, EntityPermission.READ_ONLY);
    }
    
    public OfferNameElement getOfferNameElementByNameForUpdate(String offerNameElementName) {
        return getOfferNameElementByName(offerNameElementName, EntityPermission.READ_WRITE);
    }
    
    public OfferNameElementDetailValue getOfferNameElementDetailValueForUpdate(OfferNameElement offerNameElement) {
        return offerNameElement == null? null: offerNameElement.getLastDetailForUpdate().getOfferNameElementDetailValue().clone();
    }
    
    public OfferNameElementDetailValue getOfferNameElementDetailValueByNameForUpdate(String offerNameElementName) {
        return getOfferNameElementDetailValueForUpdate(getOfferNameElementByNameForUpdate(offerNameElementName));
    }
    
    private List<OfferNameElement> getOfferNameElements(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM offernameelements, offernameelementdetails " +
                    "WHERE ofrne_activedetailid = ofrnedt_offernameelementdetailid " +
                    "ORDER BY ofrnedt_offset";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM offernameelements, offernameelementdetails " +
                    "WHERE ofrne_activedetailid = ofrnedt_offernameelementdetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = OfferNameElementFactory.getInstance().prepareStatement(query);
        
        return OfferNameElementFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<OfferNameElement> getOfferNameElements() {
        return getOfferNameElements(EntityPermission.READ_ONLY);
    }
    
    public List<OfferNameElement> getOfferNameElementsForUpdate() {
        return getOfferNameElements(EntityPermission.READ_WRITE);
    }
    
    public OfferNameElementTransfer getOfferNameElementTransfer(UserVisit userVisit, OfferNameElement offerNameElement) {
        return getOfferTransferCaches(userVisit).getOfferNameElementTransferCache().getOfferNameElementTransfer(offerNameElement);
    }
    
    public List<OfferNameElementTransfer> getOfferNameElementTransfers(UserVisit userVisit, Collection<OfferNameElement> offerNameElements) {
        List<OfferNameElementTransfer> offerNameElementTransfers = new ArrayList<>(offerNameElements.size());
        OfferNameElementTransferCache offerNameElementTransferCache = getOfferTransferCaches(userVisit).getOfferNameElementTransferCache();
        
        for(OfferNameElement offerNameElement: offerNameElements) {
            offerNameElementTransfers.add(offerNameElementTransferCache.getOfferNameElementTransfer(offerNameElement));
        }
        
        return offerNameElementTransfers;
    }
    
    public List<OfferNameElementTransfer> getOfferNameElementTransfers(UserVisit userVisit) {
        return getOfferNameElementTransfers(userVisit, getOfferNameElements());
    }
    
    public void updateOfferNameElementFromValue(OfferNameElementDetailValue offerNameElementDetailValue, BasePK updatedBy) {
        if(offerNameElementDetailValue.hasBeenModified()) {
            OfferNameElement offerNameElement = OfferNameElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerNameElementDetailValue.getOfferNameElementPK());
            OfferNameElementDetail offerNameElementDetail = offerNameElement.getActiveDetailForUpdate();
            
            offerNameElementDetail.setThruTime(session.START_TIME_LONG);
            offerNameElementDetail.store();
            
            OfferNameElementPK offerNameElementPK = offerNameElementDetail.getOfferNameElementPK();
            String offerNameElementName = offerNameElementDetailValue.getOfferNameElementName();
            Integer offset = offerNameElementDetailValue.getOffset();
            Integer length = offerNameElementDetailValue.getLength();
            String validationPattern = offerNameElementDetailValue.getValidationPattern();
            
            offerNameElementDetail = OfferNameElementDetailFactory.getInstance().create(offerNameElementPK,
                    offerNameElementName, offset, length, validationPattern, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            offerNameElement.setActiveDetail(offerNameElementDetail);
            offerNameElement.setLastDetail(offerNameElementDetail);
            
            sendEventUsingNames(offerNameElementPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteOfferNameElement(OfferNameElement offerNameElement, BasePK deletedBy) {
        deleteOfferNameElementDescriptionsByOfferNameElement(offerNameElement, deletedBy);
        
        OfferNameElementDetail offerNameElementDetail = offerNameElement.getLastDetailForUpdate();
        offerNameElementDetail.setThruTime(session.START_TIME_LONG);
        offerNameElement.setActiveDetail(null);
        offerNameElement.store();
        
        sendEventUsingNames(offerNameElement.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Name Element Descriptions
    // --------------------------------------------------------------------------------
    
    public OfferNameElementDescription createOfferNameElementDescription(OfferNameElement offerNameElement, Language language,
            String description, BasePK createdBy) {
        OfferNameElementDescription offerNameElementDescription = OfferNameElementDescriptionFactory.getInstance().create(session,
                offerNameElement, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(offerNameElement.getPrimaryKey(), EventTypes.MODIFY.name(),
                offerNameElementDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return offerNameElementDescription;
    }
    
    private OfferNameElementDescription getOfferNameElementDescription(OfferNameElement offerNameElement, Language language,
            EntityPermission entityPermission) {
        OfferNameElementDescription offerNameElementDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offernameelementdescriptions " +
                        "WHERE ofrned_ofrne_offernameelementid = ? AND ofrned_lang_languageid = ? AND ofrned_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offernameelementdescriptions " +
                        "WHERE ofrned_ofrne_offernameelementid = ? AND ofrned_lang_languageid = ? AND ofrned_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferNameElementDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, offerNameElement.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            offerNameElementDescription = OfferNameElementDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerNameElementDescription;
    }
    
    public OfferNameElementDescription getOfferNameElementDescription(OfferNameElement offerNameElement, Language language) {
        return getOfferNameElementDescription(offerNameElement, language, EntityPermission.READ_ONLY);
    }
    
    public OfferNameElementDescription getOfferNameElementDescriptionForUpdate(OfferNameElement offerNameElement, Language language) {
        return getOfferNameElementDescription(offerNameElement, language, EntityPermission.READ_WRITE);
    }
    
    public OfferNameElementDescriptionValue getOfferNameElementDescriptionValue(OfferNameElementDescription offerNameElementDescription) {
        return offerNameElementDescription == null? null: offerNameElementDescription.getOfferNameElementDescriptionValue().clone();
    }
    
    public OfferNameElementDescriptionValue getOfferNameElementDescriptionValueForUpdate(OfferNameElement offerNameElement, Language language) {
        return getOfferNameElementDescriptionValue(getOfferNameElementDescriptionForUpdate(offerNameElement, language));
    }
    
    private List<OfferNameElementDescription> getOfferNameElementDescriptionsByOfferNameElement(OfferNameElement offerNameElement,
            EntityPermission entityPermission) {
        List<OfferNameElementDescription> offerNameElementDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offernameelementdescriptions, languages " +
                        "WHERE ofrned_ofrne_offernameelementid = ? AND ofrned_thrutime = ? AND ofrned_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offernameelementdescriptions " +
                        "WHERE ofrned_ofrne_offernameelementid = ? AND ofrned_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferNameElementDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, offerNameElement.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            offerNameElementDescriptions = OfferNameElementDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerNameElementDescriptions;
    }
    
    public List<OfferNameElementDescription> getOfferNameElementDescriptionsByOfferNameElement(OfferNameElement offerNameElement) {
        return getOfferNameElementDescriptionsByOfferNameElement(offerNameElement, EntityPermission.READ_ONLY);
    }
    
    public List<OfferNameElementDescription> getOfferNameElementDescriptionsByOfferNameElementForUpdate(OfferNameElement offerNameElement) {
        return getOfferNameElementDescriptionsByOfferNameElement(offerNameElement, EntityPermission.READ_WRITE);
    }
    
    public String getBestOfferNameElementDescription(OfferNameElement offerNameElement, Language language) {
        String description;
        OfferNameElementDescription offerNameElementDescription = getOfferNameElementDescription(offerNameElement, language);
        
        if(offerNameElementDescription == null && !language.getIsDefault()) {
            offerNameElementDescription = getOfferNameElementDescription(offerNameElement, getPartyControl().getDefaultLanguage());
        }
        
        if(offerNameElementDescription == null) {
            description = offerNameElement.getLastDetail().getOfferNameElementName();
        } else {
            description = offerNameElementDescription.getDescription();
        }
        
        return description;
    }
    
    public OfferNameElementDescriptionTransfer getOfferNameElementDescriptionTransfer(UserVisit userVisit,
            OfferNameElementDescription offerNameElementDescription) {
        return getOfferTransferCaches(userVisit).getOfferNameElementDescriptionTransferCache().getOfferNameElementDescriptionTransfer(offerNameElementDescription);
    }
    
    public List<OfferNameElementDescriptionTransfer> getOfferNameElementDescriptionTransfers(UserVisit userVisit, OfferNameElement offerNameElement) {
        List<OfferNameElementDescription> offerNameElementDescriptions = getOfferNameElementDescriptionsByOfferNameElement(offerNameElement);
        List<OfferNameElementDescriptionTransfer> offerNameElementDescriptionTransfers = new ArrayList<>(offerNameElementDescriptions.size());
        OfferNameElementDescriptionTransferCache offerNameElementDescriptionTransferCache = getOfferTransferCaches(userVisit).getOfferNameElementDescriptionTransferCache();
        
        offerNameElementDescriptions.stream().forEach((offerNameElementDescription) -> {
            offerNameElementDescriptionTransfers.add(offerNameElementDescriptionTransferCache.getOfferNameElementDescriptionTransfer(offerNameElementDescription));
        });
        
        return offerNameElementDescriptionTransfers;
    }
    
    public void updateOfferNameElementDescriptionFromValue(OfferNameElementDescriptionValue offerNameElementDescriptionValue, BasePK updatedBy) {
        if(offerNameElementDescriptionValue.hasBeenModified()) {
            OfferNameElementDescription offerNameElementDescription = OfferNameElementDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerNameElementDescriptionValue.getPrimaryKey());
            
            offerNameElementDescription.setThruTime(session.START_TIME_LONG);
            offerNameElementDescription.store();
            
            OfferNameElement offerNameElement = offerNameElementDescription.getOfferNameElement();
            Language language = offerNameElementDescription.getLanguage();
            String description = offerNameElementDescriptionValue.getDescription();
            
            offerNameElementDescription = OfferNameElementDescriptionFactory.getInstance().create(offerNameElement,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(offerNameElement.getPrimaryKey(), EventTypes.MODIFY.name(),
                    offerNameElementDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteOfferNameElementDescription(OfferNameElementDescription offerNameElementDescription, BasePK deletedBy) {
        offerNameElementDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(offerNameElementDescription.getOfferNameElementPK(), EventTypes.MODIFY.name(),
                offerNameElementDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteOfferNameElementDescriptionsByOfferNameElement(OfferNameElement offerNameElement, BasePK deletedBy) {
        List<OfferNameElementDescription> offerNameElementDescriptions = getOfferNameElementDescriptionsByOfferNameElementForUpdate(offerNameElement);
        
        offerNameElementDescriptions.stream().forEach((offerNameElementDescription) -> {
            deleteOfferNameElementDescription(offerNameElementDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Offers
    // --------------------------------------------------------------------------------
    
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
    public Offer getOfferByEntityInstance(EntityInstance entityInstance) {
        OfferPK pk = new OfferPK(entityInstance.getEntityUniqueId());
        Offer offer = OfferFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return offer;
    }
    
    private Offer getDefaultOffer(EntityPermission entityPermission) {
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
    
    private Offer getOfferByName(String offerName, EntityPermission entityPermission) {
        Offer offer = null;
        
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
    
    /** Gets a List that contains all the Selectors used by Offers.
     */
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

            boolean usingDefaultChoice = defaultOfferChoice == null? false: defaultOfferChoice.equals(value);
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
    
    public void updateOfferFromValue(OfferDetailValue offerDetailValue, BasePK updatedBy) {
        updateOfferFromValue(offerDetailValue, true, updatedBy);
    }
    
    public void deleteOffer(Offer offer, BasePK deletedBy) {
        deleteOfferCustomerTypesByOffer(offer, deletedBy);
        deleteOfferChainTypesByOffer(offer, deletedBy);
        deleteOfferItemsByOffer(offer, deletedBy);
        deleteOfferUsesByOffer(offer, deletedBy);
        deleteOfferDescriptionsByOffer(offer, deletedBy);
        
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
        OfferDescription offerDescription = null;
        
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
        List<OfferDescription> offerDescriptions = null;
        
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
        
        offerDescriptions.stream().forEach((offerDescription) -> {
            deleteOfferDescription(offerDescription, deletedBy);
        });
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

        offerCustomerTypes.stream().forEach((offerCustomerType) -> {
            offerCustomerTypeTransfers.add(offerCustomerTypeTransferCache.getOfferCustomerTypeTransfer(offerCustomerType));
        });

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
        offerCustomerTypes.stream().forEach((offerCustomerType) -> {
            deleteOfferCustomerType(offerCustomerType, deletedBy);
        });
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
        OfferChainType offerChainType = null;
        
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
        
        offerChainTypes.stream().forEach((offerChainType) -> {
            offerChainTypeTransfers.add(offerChainTypeTransferCache.getOfferChainTypeTransfer(offerChainType));
        });
        
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
        offerChainTypes.stream().forEach((offerChainType) -> {
            deleteOfferChainType(offerChainType, deletedBy);
        });
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
    //   Use Name Elements
    // --------------------------------------------------------------------------------
    
    public UseNameElement createUseNameElement(String useNameElementName, Integer offset,
            Integer length, String validationPattern, BasePK createdBy) {
        UseNameElement useNameElement = UseNameElementFactory.getInstance().create();
        UseNameElementDetail useNameElementDetail = UseNameElementDetailFactory.getInstance().create(session,
                useNameElement, useNameElementName, offset, length, validationPattern, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        useNameElement = UseNameElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                useNameElement.getPrimaryKey());
        useNameElement.setActiveDetail(useNameElementDetail);
        useNameElement.setLastDetail(useNameElementDetail);
        useNameElement.store();
        
        sendEventUsingNames(useNameElement.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return useNameElement;
    }
    
    private UseNameElement getUseNameElementByName(String useNameElementName, EntityPermission entityPermission) {
        UseNameElement useNameElement = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usenameelements, usenameelementdetails " +
                        "WHERE usene_activedetailid = usenedt_usenameelementdetailid AND usenedt_usenameelementname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usenameelements, usenameelementdetails " +
                        "WHERE usene_activedetailid = usenedt_usenameelementdetailid AND usenedt_usenameelementname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UseNameElementFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, useNameElementName);
            
            useNameElement = UseNameElementFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useNameElement;
    }
    
    public UseNameElement getUseNameElementByName(String useNameElementName) {
        return getUseNameElementByName(useNameElementName, EntityPermission.READ_ONLY);
    }
    
    public UseNameElement getUseNameElementByNameForUpdate(String useNameElementName) {
        return getUseNameElementByName(useNameElementName, EntityPermission.READ_WRITE);
    }
    
    public UseNameElementDetailValue getUseNameElementDetailValueForUpdate(UseNameElement useNameElement) {
        return useNameElement == null? null: useNameElement.getLastDetailForUpdate().getUseNameElementDetailValue().clone();
    }
    
    public UseNameElementDetailValue getUseNameElementDetailValueByNameForUpdate(String useNameElementName) {
        return getUseNameElementDetailValueForUpdate(getUseNameElementByNameForUpdate(useNameElementName));
    }
    
    private List<UseNameElement> getUseNameElements(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM usenameelements, usenameelementdetails " +
                    "WHERE usene_activedetailid = usenedt_usenameelementdetailid " +
                    "ORDER BY usenedt_offset";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM usenameelements, usenameelementdetails " +
                    "WHERE usene_activedetailid = usenedt_usenameelementdetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = UseNameElementFactory.getInstance().prepareStatement(query);
        
        return UseNameElementFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<UseNameElement> getUseNameElements() {
        return getUseNameElements(EntityPermission.READ_ONLY);
    }
    
    public List<UseNameElement> getUseNameElementsForUpdate() {
        return getUseNameElements(EntityPermission.READ_WRITE);
    }
    
    public UseNameElementTransfer getUseNameElementTransfer(UserVisit userVisit, UseNameElement useNameElement) {
        return getOfferTransferCaches(userVisit).getUseNameElementTransferCache().getUseNameElementTransfer(useNameElement);
    }
    
    public List<UseNameElementTransfer> getUseNameElementTransfers(UserVisit userVisit, Collection<UseNameElement> useNameElements) {
        List<UseNameElementTransfer> useNameElementTransfers = new ArrayList<>(useNameElements.size());
        UseNameElementTransferCache useNameElementTransferCache = getOfferTransferCaches(userVisit).getUseNameElementTransferCache();
        
        useNameElements.stream().forEach((useNameElement) -> {
            useNameElementTransfers.add(useNameElementTransferCache.getUseNameElementTransfer(useNameElement));
        });
        
        return useNameElementTransfers;
    }
    
    public List<UseNameElementTransfer> getUseNameElementTransfers(UserVisit userVisit) {
        return getUseNameElementTransfers(userVisit, getUseNameElements());
    }
    
    public void updateUseNameElementFromValue(UseNameElementDetailValue useNameElementDetailValue, BasePK updatedBy) {
        if(useNameElementDetailValue.hasBeenModified()) {
            UseNameElement useNameElement = UseNameElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     useNameElementDetailValue.getUseNameElementPK());
            UseNameElementDetail useNameElementDetail = useNameElement.getActiveDetailForUpdate();
            
            useNameElementDetail.setThruTime(session.START_TIME_LONG);
            useNameElementDetail.store();
            
            UseNameElementPK useNameElementPK = useNameElementDetail.getUseNameElementPK();
            String useNameElementName = useNameElementDetailValue.getUseNameElementName();
            Integer offset = useNameElementDetailValue.getOffset();
            Integer length = useNameElementDetailValue.getLength();
            String validationPattern = useNameElementDetailValue.getValidationPattern();
            
            useNameElementDetail = UseNameElementDetailFactory.getInstance().create(useNameElementPK,
                    useNameElementName, offset, length, validationPattern, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            useNameElement.setActiveDetail(useNameElementDetail);
            useNameElement.setLastDetail(useNameElementDetail);
            
            sendEventUsingNames(useNameElementPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteUseNameElement(UseNameElement useNameElement, BasePK deletedBy) {
        deleteUseNameElementDescriptionsByUseNameElement(useNameElement, deletedBy);
        
        UseNameElementDetail useNameElementDetail = useNameElement.getLastDetailForUpdate();
        useNameElementDetail.setThruTime(session.START_TIME_LONG);
        useNameElement.setActiveDetail(null);
        useNameElement.store();
        
        sendEventUsingNames(useNameElement.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Use Name Element Descriptions
    // --------------------------------------------------------------------------------
    
    public UseNameElementDescription createUseNameElementDescription(UseNameElement useNameElement, Language language,
            String description, BasePK createdBy) {
        UseNameElementDescription useNameElementDescription = UseNameElementDescriptionFactory.getInstance().create(session,
                useNameElement, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(useNameElement.getPrimaryKey(), EventTypes.MODIFY.name(),
                useNameElementDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return useNameElementDescription;
    }
    
    private UseNameElementDescription getUseNameElementDescription(UseNameElement useNameElement, Language language,
            EntityPermission entityPermission) {
        UseNameElementDescription useNameElementDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usenameelementdescriptions " +
                        "WHERE usened_usene_usenameelementid = ? AND usened_lang_languageid = ? AND usened_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usenameelementdescriptions " +
                        "WHERE usened_usene_usenameelementid = ? AND usened_lang_languageid = ? AND usened_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UseNameElementDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, useNameElement.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            useNameElementDescription = UseNameElementDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useNameElementDescription;
    }
    
    public UseNameElementDescription getUseNameElementDescription(UseNameElement useNameElement, Language language) {
        return getUseNameElementDescription(useNameElement, language, EntityPermission.READ_ONLY);
    }
    
    public UseNameElementDescription getUseNameElementDescriptionForUpdate(UseNameElement useNameElement, Language language) {
        return getUseNameElementDescription(useNameElement, language, EntityPermission.READ_WRITE);
    }
    
    public UseNameElementDescriptionValue getUseNameElementDescriptionValue(UseNameElementDescription useNameElementDescription) {
        return useNameElementDescription == null? null: useNameElementDescription.getUseNameElementDescriptionValue().clone();
    }
    
    public UseNameElementDescriptionValue getUseNameElementDescriptionValueForUpdate(UseNameElement useNameElement, Language language) {
        return getUseNameElementDescriptionValue(getUseNameElementDescriptionForUpdate(useNameElement, language));
    }
    
    private List<UseNameElementDescription> getUseNameElementDescriptionsByUseNameElement(UseNameElement useNameElement, EntityPermission entityPermission) {
        List<UseNameElementDescription> useNameElementDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usenameelementdescriptions, languages " +
                        "WHERE usened_usene_usenameelementid = ? AND usened_thrutime = ? AND usened_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usenameelementdescriptions " +
                        "WHERE usened_usene_usenameelementid = ? AND usened_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UseNameElementDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, useNameElement.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            useNameElementDescriptions = UseNameElementDescriptionFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useNameElementDescriptions;
    }
    
    public List<UseNameElementDescription> getUseNameElementDescriptionsByUseNameElement(UseNameElement useNameElement) {
        return getUseNameElementDescriptionsByUseNameElement(useNameElement, EntityPermission.READ_ONLY);
    }
    
    public List<UseNameElementDescription> getUseNameElementDescriptionsByUseNameElementForUpdate(UseNameElement useNameElement) {
        return getUseNameElementDescriptionsByUseNameElement(useNameElement, EntityPermission.READ_WRITE);
    }
    
    public String getBestUseNameElementDescription(UseNameElement useNameElement, Language language) {
        String description;
        UseNameElementDescription useNameElementDescription = getUseNameElementDescription(useNameElement, language);
        
        if(useNameElementDescription == null && !language.getIsDefault()) {
            useNameElementDescription = getUseNameElementDescription(useNameElement, getPartyControl().getDefaultLanguage());
        }
        
        if(useNameElementDescription == null) {
            description = useNameElement.getLastDetail().getUseNameElementName();
        } else {
            description = useNameElementDescription.getDescription();
        }
        
        return description;
    }
    
    public UseNameElementDescriptionTransfer getUseNameElementDescriptionTransfer(UserVisit userVisit,
            UseNameElementDescription useNameElementDescription) {
        return getOfferTransferCaches(userVisit).getUseNameElementDescriptionTransferCache().getUseNameElementDescriptionTransfer(useNameElementDescription);
    }
    
    public List<UseNameElementDescriptionTransfer> getUseNameElementDescriptionTransfers(UserVisit userVisit, UseNameElement useNameElement) {
        List<UseNameElementDescription> useNameElementDescriptions = getUseNameElementDescriptionsByUseNameElement(useNameElement);
        List<UseNameElementDescriptionTransfer> useNameElementDescriptionTransfers = new ArrayList<>(useNameElementDescriptions.size());
        UseNameElementDescriptionTransferCache useNameElementDescriptionTransferCache = getOfferTransferCaches(userVisit).getUseNameElementDescriptionTransferCache();
        
        useNameElementDescriptions.stream().forEach((useNameElementDescription) -> {
            useNameElementDescriptionTransfers.add(useNameElementDescriptionTransferCache.getUseNameElementDescriptionTransfer(useNameElementDescription));
        });
        
        return useNameElementDescriptionTransfers;
    }
    
    public void updateUseNameElementDescriptionFromValue(UseNameElementDescriptionValue useNameElementDescriptionValue,
            BasePK updatedBy) {
        if(useNameElementDescriptionValue.hasBeenModified()) {
            UseNameElementDescription useNameElementDescription = UseNameElementDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     useNameElementDescriptionValue.getPrimaryKey());
            
            useNameElementDescription.setThruTime(session.START_TIME_LONG);
            useNameElementDescription.store();
            
            UseNameElement useNameElement = useNameElementDescription.getUseNameElement();
            Language language = useNameElementDescription.getLanguage();
            String description = useNameElementDescriptionValue.getDescription();
            
            useNameElementDescription = UseNameElementDescriptionFactory.getInstance().create(useNameElement,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(useNameElement.getPrimaryKey(), EventTypes.MODIFY.name(),
                    useNameElementDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteUseNameElementDescription(UseNameElementDescription useNameElementDescription, BasePK deletedBy) {
        useNameElementDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(useNameElementDescription.getUseNameElementPK(), EventTypes.MODIFY.name(),
                useNameElementDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteUseNameElementDescriptionsByUseNameElement(UseNameElement useNameElement, BasePK deletedBy) {
        List<UseNameElementDescription> useNameElementDescriptions = getUseNameElementDescriptionsByUseNameElementForUpdate(useNameElement);
        
        useNameElementDescriptions.stream().forEach((useNameElementDescription) -> {
            deleteUseNameElementDescription(useNameElementDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Use Types
    // --------------------------------------------------------------------------------
    
    public UseType createUseType(String useTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        UseType defaultUseType = getDefaultUseType();
        boolean defaultFound = defaultUseType != null;
        
        if(defaultFound && isDefault) {
            UseTypeDetailValue defaultUseTypeDetailValue = getDefaultUseTypeDetailValueForUpdate();
            
            defaultUseTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateUseTypeFromValue(defaultUseTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        UseType useType = UseTypeFactory.getInstance().create();
        UseTypeDetail useTypeDetail = UseTypeDetailFactory.getInstance().create(useType, useTypeName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        useType = UseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, useType.getPrimaryKey());
        useType.setActiveDetail(useTypeDetail);
        useType.setLastDetail(useTypeDetail);
        useType.store();
        
        sendEventUsingNames(useType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return useType;
    }
    
    public long countUseTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM usetypes, usetypedetails " +
                "WHERE usetyp_activedetailid = usetypdt_usetypedetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.UseType */
    public UseType getUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UseTypePK(entityInstance.getEntityUniqueId());
        var useType = UseTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return useType;
    }

    public UseType getUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UseType getUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<UseType> getUseTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ "
                    + "FROM usetypes, usetypedetails "
                    + "WHERE usetyp_activedetailid = usetypdt_usetypedetailid "
                    + "ORDER BY usetypdt_sortorder, usetypdt_usetypename "
                    + "_LIMIT_";
        } else if (entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ "
                    + "FROM usetypes, usetypedetails "
                    + "WHERE usetyp_activedetailid = usetypdt_usetypedetailid "
                    + "FOR UPDATE";
        }
        
        PreparedStatement ps = UseTypeFactory.getInstance().prepareStatement(query);
        
        return UseTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<UseType> getUseTypes() {
        return getUseTypes(EntityPermission.READ_ONLY);
    }
    
    public List<UseType> getUseTypesForUpdate() {
        return getUseTypes(EntityPermission.READ_WRITE);
    }

    public UseType getUseTypeByName(String useTypeName, EntityPermission entityPermission) {
        UseType useType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypes, usetypedetails " +
                        "WHERE usetyp_activedetailid = usetypdt_usetypedetailid AND usetypdt_usetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypes, usetypedetails " +
                        "WHERE usetyp_activedetailid = usetypdt_usetypedetailid AND usetypdt_usetypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UseTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, useTypeName);
            
            useType = UseTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useType;
    }
    
    public UseType getUseTypeByName(String useTypeName) {
        return getUseTypeByName(useTypeName, EntityPermission.READ_ONLY);
    }
    
    public UseType getUseTypeByNameForUpdate(String useTypeName) {
        return getUseTypeByName(useTypeName, EntityPermission.READ_WRITE);
    }
    
    public UseTypeDetailValue getUseTypeDetailValueForUpdate(UseType useType) {
        return useType == null? null: useType.getLastDetailForUpdate().getUseTypeDetailValue().clone();
    }
    
    public UseTypeDetailValue getUseTypeDetailValueByNameForUpdate(String useTypeName) {
        return getUseTypeDetailValueForUpdate(getUseTypeByNameForUpdate(useTypeName));
    }
    
    public UseType getDefaultUseType(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM usetypes, usetypedetails " +
                    "WHERE usetyp_activedetailid = usetypdt_usetypedetailid AND usetypdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM usetypes, usetypedetails " +
                    "WHERE usetyp_activedetailid = usetypdt_usetypedetailid AND usetypdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = UseTypeFactory.getInstance().prepareStatement(query);
        
        return UseTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public UseType getDefaultUseType() {
        return getDefaultUseType(EntityPermission.READ_ONLY);
    }
    
    public UseType getDefaultUseTypeForUpdate() {
        return getDefaultUseType(EntityPermission.READ_WRITE);
    }
    
    public UseTypeDetailValue getDefaultUseTypeDetailValueForUpdate() {
        return getDefaultUseTypeForUpdate().getLastDetailForUpdate().getUseTypeDetailValue().clone();
    }
    
    public List<UseTypeTransfer> getUseTypeTransfers(UserVisit userVisit, Collection<UseType> useTypes) {
        List<UseTypeTransfer> useTypeTransfers = new ArrayList<>(useTypes.size());
        
        useTypes.stream().forEach((useType) -> {
            useTypeTransfers.add(getOfferTransferCaches(userVisit).getUseTypeTransferCache().getUseTypeTransfer(useType));
        });
        
        return useTypeTransfers;
    }
    
    public List<UseTypeTransfer> getUseTypeTransfers(UserVisit userVisit) {
        return getUseTypeTransfers(userVisit, getUseTypes());
    }
    
    public UseTypeTransfer getUseTypeTransfer(UserVisit userVisit, UseType useType) {
        return getOfferTransferCaches(userVisit).getUseTypeTransferCache().getUseTypeTransfer(useType);
    }
    
    public UseTypeChoicesBean getUseTypeChoices(String defaultUseTypeChoice, Language language, boolean allowNullChoice) {
        List<UseType> useTypes = getUseTypes();
        int size = useTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUseTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(UseType useType : useTypes) {
            UseTypeDetail useTypeDetail = useType.getLastDetail();
            
            String label = getBestUseTypeDescription(useType, language);
            String value = useTypeDetail.getUseTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultUseTypeChoice == null? false: defaultUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && useTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new UseTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateUseTypeFromValue(UseTypeDetailValue useTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(useTypeDetailValue.hasBeenModified()) {
            UseType useType = UseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     useTypeDetailValue.getUseTypePK());
            UseTypeDetail useTypeDetail = useType.getActiveDetailForUpdate();
            
            useTypeDetail.setThruTime(session.START_TIME_LONG);
            useTypeDetail.store();
            
            UseTypePK useTypePK = useTypeDetail.getUseTypePK();
            String useTypeName = useTypeDetailValue.getUseTypeName();
            Boolean isDefault = useTypeDetailValue.getIsDefault();
            Integer sortOrder = useTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                UseType defaultUseType = getDefaultUseType();
                boolean defaultFound = defaultUseType != null && !defaultUseType.equals(useType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    UseTypeDetailValue defaultUseTypeDetailValue = getDefaultUseTypeDetailValueForUpdate();
                    
                    defaultUseTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateUseTypeFromValue(defaultUseTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            useTypeDetail = UseTypeDetailFactory.getInstance().create(useTypePK, useTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            useType.setActiveDetail(useTypeDetail);
            useType.setLastDetail(useTypeDetail);
            
            sendEventUsingNames(useTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateUseTypeFromValue(UseTypeDetailValue useTypeDetailValue, BasePK updatedBy) {
        updateUseTypeFromValue(useTypeDetailValue, true, updatedBy);
    }
    
    public void deleteUseType(UseType useType, BasePK deletedBy) {
        deleteUseTypeDescriptionsByUseType(useType, deletedBy);
        deleteUsesByUseType(useType, deletedBy);
        
        UseTypeDetail useTypeDetail = useType.getLastDetailForUpdate();
        useTypeDetail.setThruTime(session.START_TIME_LONG);
        useType.setActiveDetail(null);
        useType.store();
        
        // Check for default, and pick one if necessary
        UseType defaultUseType = getDefaultUseType();
        if(defaultUseType == null) {
            List<UseType> useTypes = getUseTypesForUpdate();
            
            if(!useTypes.isEmpty()) {
                Iterator<UseType> iter = useTypes.iterator();
                if(iter.hasNext()) {
                    defaultUseType = iter.next();
                }
                UseTypeDetailValue useTypeDetailValue = defaultUseType.getLastDetailForUpdate().getUseTypeDetailValue().clone();
                
                useTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateUseTypeFromValue(useTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(useType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    public UseTypeDescription createUseTypeDescription(UseType useType, Language language, String description, BasePK createdBy) {
        UseTypeDescription useTypeDescription = UseTypeDescriptionFactory.getInstance().create(useType, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(useType.getPrimaryKey(), EventTypes.MODIFY.name(), useTypeDescription.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return useTypeDescription;
    }
    
    private UseTypeDescription getUseTypeDescription(UseType useType, Language language, EntityPermission entityPermission) {
        UseTypeDescription useTypeDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypedescriptions " +
                        "WHERE usetypd_usetyp_usetypeid = ? AND usetypd_lang_languageid = ? AND usetypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypedescriptions " +
                        "WHERE usetypd_usetyp_usetypeid = ? AND usetypd_lang_languageid = ? AND usetypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UseTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, useType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            useTypeDescription = UseTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useTypeDescription;
    }
    
    public UseTypeDescription getUseTypeDescription(UseType useType, Language language) {
        return getUseTypeDescription(useType, language, EntityPermission.READ_ONLY);
    }
    
    public UseTypeDescription getUseTypeDescriptionForUpdate(UseType useType, Language language) {
        return getUseTypeDescription(useType, language, EntityPermission.READ_WRITE);
    }
    
    public UseTypeDescriptionValue getUseTypeDescriptionValue(UseTypeDescription useTypeDescription) {
        return useTypeDescription == null? null: useTypeDescription.getUseTypeDescriptionValue().clone();
    }
    
    public UseTypeDescriptionValue getUseTypeDescriptionValueForUpdate(UseType useType, Language language) {
        return getUseTypeDescriptionValue(getUseTypeDescriptionForUpdate(useType, language));
    }
    
    private List<UseTypeDescription> getUseTypeDescriptionsByUseType(UseType useType, EntityPermission entityPermission) {
        List<UseTypeDescription> useTypeDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypedescriptions, languages " +
                        "WHERE usetypd_usetyp_usetypeid = ? AND usetypd_thrutime = ? AND usetypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypedescriptions " +
                        "WHERE usetypd_usetyp_usetypeid = ? AND usetypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UseTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, useType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            useTypeDescriptions = UseTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useTypeDescriptions;
    }
    
    public List<UseTypeDescription> getUseTypeDescriptionsByUseType(UseType useType) {
        return getUseTypeDescriptionsByUseType(useType, EntityPermission.READ_ONLY);
    }
    
    public List<UseTypeDescription> getUseTypeDescriptionsByUseTypeForUpdate(UseType useType) {
        return getUseTypeDescriptionsByUseType(useType, EntityPermission.READ_WRITE);
    }
    
    public String getBestUseTypeDescription(UseType useType, Language language) {
        String description;
        UseTypeDescription useTypeDescription = getUseTypeDescription(useType, language);
        
        if(useTypeDescription == null && !language.getIsDefault()) {
            useTypeDescription = getUseTypeDescription(useType, getPartyControl().getDefaultLanguage());
        }
        
        if(useTypeDescription == null) {
            description = useType.getLastDetail().getUseTypeName();
        } else {
            description = useTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public UseTypeDescriptionTransfer getUseTypeDescriptionTransfer(UserVisit userVisit, UseTypeDescription useTypeDescription) {
        return getOfferTransferCaches(userVisit).getUseTypeDescriptionTransferCache().getUseTypeDescriptionTransfer(useTypeDescription);
    }
    
    public List<UseTypeDescriptionTransfer> getUseTypeDescriptionTransfersByUseType(UserVisit userVisit, UseType useType) {
        List<UseTypeDescription> useTypeDescriptions = getUseTypeDescriptionsByUseType(useType);
        List<UseTypeDescriptionTransfer> useTypeDescriptionTransfers = new ArrayList<>(useTypeDescriptions.size());
        
        useTypeDescriptions.stream().forEach((useTypeDescription) -> {
            useTypeDescriptionTransfers.add(getOfferTransferCaches(userVisit).getUseTypeDescriptionTransferCache().getUseTypeDescriptionTransfer(useTypeDescription));
        });
        
        return useTypeDescriptionTransfers;
    }
    
    public void updateUseTypeDescriptionFromValue(UseTypeDescriptionValue useTypeDescriptionValue, BasePK updatedBy) {
        if(useTypeDescriptionValue.hasBeenModified()) {
            UseTypeDescription useTypeDescription = UseTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     useTypeDescriptionValue.getPrimaryKey());
            
            useTypeDescription.setThruTime(session.START_TIME_LONG);
            useTypeDescription.store();
            
            UseType useType = useTypeDescription.getUseType();
            Language language = useTypeDescription.getLanguage();
            String description = useTypeDescriptionValue.getDescription();
            
            useTypeDescription = UseTypeDescriptionFactory.getInstance().create(useType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(useType.getPrimaryKey(), EventTypes.MODIFY.name(), useTypeDescription.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteUseTypeDescription(UseTypeDescription useTypeDescription, BasePK deletedBy) {
        useTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(useTypeDescription.getUseTypePK(), EventTypes.MODIFY.name(),
                useTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteUseTypeDescriptionsByUseType(UseType useType, BasePK deletedBy) {
        List<UseTypeDescription> useTypeDescriptions = getUseTypeDescriptionsByUseTypeForUpdate(useType);
        
        useTypeDescriptions.stream().forEach((useTypeDescription) -> {
            deleteUseTypeDescription(useTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Uses
    // --------------------------------------------------------------------------------
    
    public Use createUse(String useName, UseType useType, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Use defaultUse = getDefaultUse();
        boolean defaultFound = defaultUse != null;
        
        if(defaultFound && isDefault) {
            UseDetailValue defaultUseDetailValue = getDefaultUseDetailValueForUpdate();
            
            defaultUseDetailValue.setIsDefault(Boolean.FALSE);
            updateUseFromValue(defaultUseDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        Use use = UseFactory.getInstance().create();
        UseDetail useDetail = UseDetailFactory.getInstance().create(use, useName, useType, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        use = UseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, use.getPrimaryKey());
        use.setActiveDetail(useDetail);
        use.setLastDetail(useDetail);
        use.store();
        
        sendEventUsingNames(use.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return use;
    }
    
    public long countUses() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM uses, usedetails " +
                "WHERE use_activedetailid = usedt_usedetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.Use */
    public Use getUseByEntityInstance(EntityInstance entityInstance) {
        UsePK pk = new UsePK(entityInstance.getEntityUniqueId());
        Use use = UseFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return use;
    }
    
    private List<Use> getUses(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ "
                    + "FROM uses, usedetails "
                    + "WHERE use_activedetailid = usedt_usedetailid "
                    + "ORDER BY usedt_sortorder, usedt_usename "
                    + "_LIMIT_";
        } else if (entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ "
                    + "FROM uses, usedetails "
                    + "WHERE use_activedetailid = usedt_usedetailid "
                    + "FOR UPDATE";
        }
        
        PreparedStatement ps = UseFactory.getInstance().prepareStatement(query);
        
        return UseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<Use> getUses() {
        return getUses(EntityPermission.READ_ONLY);
    }
    
    public List<Use> getUsesForUpdate() {
        return getUses(EntityPermission.READ_WRITE);
    }
    
    private Use getUseByName(String useName, EntityPermission entityPermission) {
        Use use = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM uses, usedetails " +
                        "WHERE use_activedetailid = usedt_usedetailid AND usedt_usename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM uses, usedetails " +
                        "WHERE use_activedetailid = usedt_usedetailid AND usedt_usename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UseFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, useName);
            
            use = UseFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return use;
    }
    
    public Use getUseByName(String useName) {
        return getUseByName(useName, EntityPermission.READ_ONLY);
    }
    
    public Use getUseByNameForUpdate(String useName) {
        return getUseByName(useName, EntityPermission.READ_WRITE);
    }
    
    public UseDetailValue getUseDetailValueForUpdate(Use use) {
        return use == null? null: use.getLastDetailForUpdate().getUseDetailValue().clone();
    }
    
    public UseDetailValue getUseDetailValueByNameForUpdate(String useName) {
        return getUseDetailValueForUpdate(getUseByNameForUpdate(useName));
    }
    
    private Use getDefaultUse(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM uses, usedetails " +
                    "WHERE use_activedetailid = usedt_usedetailid AND usedt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM uses, usedetails " +
                    "WHERE use_activedetailid = usedt_usedetailid AND usedt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = UseFactory.getInstance().prepareStatement(query);
        
        return UseFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public Use getDefaultUse() {
        return getDefaultUse(EntityPermission.READ_ONLY);
    }
    
    public Use getDefaultUseForUpdate() {
        return getDefaultUse(EntityPermission.READ_WRITE);
    }
    
    public UseDetailValue getDefaultUseDetailValueForUpdate() {
        return getDefaultUseForUpdate().getLastDetailForUpdate().getUseDetailValue().clone();
    }
    
    private List<Use> getUsesByUseType(UseType useType, EntityPermission entityPermission) {
        List<Use> uses = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM uses, usedetails " +
                        "WHERE use_activedetailid = usedt_usedetailid AND usedt_usetyp_usetypeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM uses, usedetails " +
                        "WHERE use_activedetailid = usedt_usedetailid AND usedt_usetyp_usetypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, useType.getPrimaryKey().getEntityId());
            
            uses = UseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return uses;
    }
    
    public List<Use> getUsesByUseType(UseType useType) {
        return getUsesByUseType(useType, EntityPermission.READ_ONLY);
    }
    
    public List<Use> getUsesByUseTypeForUpdate(UseType useType) {
        return getUsesByUseType(useType, EntityPermission.READ_WRITE);
    }
    
    public List<UseTransfer> getUseTransfers(UserVisit userVisit, Collection<Use> uses) {
        List<UseTransfer> useTransfers = new ArrayList<>(uses.size());
            
        uses.stream().forEach((use) -> {
            useTransfers.add(getOfferTransferCaches(userVisit).getUseTransferCache().getUseTransfer(use));
        });
        
        return useTransfers;
    }
    
    public List<UseTransfer> getUseTransfers(UserVisit userVisit) {
        return getUseTransfers(userVisit, getUses());
    }
    
    public UseTransfer getUseTransfer(UserVisit userVisit, Use use) {
        return getOfferTransferCaches(userVisit).getUseTransferCache().getUseTransfer(use);
    }
    
    public UseChoicesBean getUseChoices(String defaultUseChoice, Language language, boolean allowNullChoice) {
        List<Use> uses = getUses();
        int size = uses.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUseChoice == null) {
                defaultValue = "";
            }
        }
        
        for(Use use : uses) {
            UseDetail useDetail = use.getLastDetail();
            
            String label = getBestUseDescription(use, language);
            String value = useDetail.getUseName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultUseChoice == null? false: defaultUseChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && useDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new UseChoicesBean(labels, values, defaultValue);
    }
    
    private void updateUseFromValue(UseDetailValue useDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(useDetailValue.hasBeenModified()) {
            Use use = UseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     useDetailValue.getUsePK());
            UseDetail useDetail = use.getActiveDetailForUpdate();
            
            useDetail.setThruTime(session.START_TIME_LONG);
            useDetail.store();
            
            UsePK usePK = useDetail.getUsePK();
            String useName = useDetailValue.getUseName();
            UseTypePK useTypePK = useDetailValue.getUseTypePK();
            Boolean isDefault = useDetailValue.getIsDefault();
            Integer sortOrder = useDetailValue.getSortOrder();
            
            if(checkDefault) {
                Use defaultUse = getDefaultUse();
                boolean defaultFound = defaultUse != null && !defaultUse.equals(use);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    UseDetailValue defaultUseDetailValue = getDefaultUseDetailValueForUpdate();
                    
                    defaultUseDetailValue.setIsDefault(Boolean.FALSE);
                    updateUseFromValue(defaultUseDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            useDetail = UseDetailFactory.getInstance().create(usePK, useName, useTypePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            use.setActiveDetail(useDetail);
            use.setLastDetail(useDetail);
            
            sendEventUsingNames(usePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateUseFromValue(UseDetailValue useDetailValue, BasePK updatedBy) {
        updateUseFromValue(useDetailValue, true, updatedBy);
    }
    
    public void deleteUse(Use use, BasePK deletedBy) {
        deleteUseDescriptionsByUse(use, deletedBy);
        deleteOfferUsesByUse(use, deletedBy);
        
        UseDetail useDetail = use.getLastDetailForUpdate();
        useDetail.setThruTime(session.START_TIME_LONG);
        use.setActiveDetail(null);
        use.store();
        
        // Check for default, and pick one if necessary
        Use defaultUse = getDefaultUse();
        if(defaultUse == null) {
            List<Use> uses = getUsesForUpdate();
            
            if(!uses.isEmpty()) {
                Iterator<Use> iter = uses.iterator();
                if(iter.hasNext()) {
                    defaultUse = iter.next();
                }
                UseDetailValue useDetailValue = defaultUse.getLastDetailForUpdate().getUseDetailValue().clone();
                
                useDetailValue.setIsDefault(Boolean.TRUE);
                updateUseFromValue(useDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(use.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteUses(List<Use> uses, BasePK deletedBy) {
        uses.stream().forEach((use) -> {
            deleteUse(use, deletedBy);
        });
    }
    
    public void deleteUsesByUseType(UseType useType, BasePK deletedBy) {
        deleteUses(getUsesByUseTypeForUpdate(useType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Use Descriptions
    // --------------------------------------------------------------------------------
    
    public UseDescription createUseDescription(Use use, Language language, String description, BasePK createdBy) {
        UseDescription useDescription = UseDescriptionFactory.getInstance().create(use, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(use.getPrimaryKey(), EventTypes.MODIFY.name(), useDescription.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return useDescription;
    }
    
    private UseDescription getUseDescription(Use use, Language language, EntityPermission entityPermission) {
        UseDescription useDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usedescriptions " +
                        "WHERE used_use_useid = ? AND used_lang_languageid = ? AND used_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usedescriptions " +
                        "WHERE used_use_useid = ? AND used_lang_languageid = ? AND used_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UseDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, use.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            useDescription = UseDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useDescription;
    }
    
    public UseDescription getUseDescription(Use use, Language language) {
        return getUseDescription(use, language, EntityPermission.READ_ONLY);
    }
    
    public UseDescription getUseDescriptionForUpdate(Use use, Language language) {
        return getUseDescription(use, language, EntityPermission.READ_WRITE);
    }
    
    public UseDescriptionValue getUseDescriptionValue(UseDescription useDescription) {
        return useDescription == null? null: useDescription.getUseDescriptionValue().clone();
    }
    
    public UseDescriptionValue getUseDescriptionValueForUpdate(Use use, Language language) {
        return getUseDescriptionValue(getUseDescriptionForUpdate(use, language));
    }
    
    private List<UseDescription> getUseDescriptionsByUse(Use use, EntityPermission entityPermission) {
        List<UseDescription> useDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usedescriptions, languages " +
                        "WHERE used_use_useid = ? AND used_thrutime = ? AND used_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usedescriptions " +
                        "WHERE used_use_useid = ? AND used_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UseDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, use.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            useDescriptions = UseDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useDescriptions;
    }
    
    public List<UseDescription> getUseDescriptionsByUse(Use use) {
        return getUseDescriptionsByUse(use, EntityPermission.READ_ONLY);
    }
    
    public List<UseDescription> getUseDescriptionsByUseForUpdate(Use use) {
        return getUseDescriptionsByUse(use, EntityPermission.READ_WRITE);
    }
    
    public String getBestUseDescription(Use use, Language language) {
        String description;
        UseDescription useDescription = getUseDescription(use, language);
        
        if(useDescription == null && !language.getIsDefault()) {
            useDescription = getUseDescription(use, getPartyControl().getDefaultLanguage());
        }
        
        if(useDescription == null) {
            description = use.getLastDetail().getUseName();
        } else {
            description = useDescription.getDescription();
        }
        
        return description;
    }
    
    public UseDescriptionTransfer getUseDescriptionTransfer(UserVisit userVisit, UseDescription useDescription) {
        return getOfferTransferCaches(userVisit).getUseDescriptionTransferCache().getUseDescriptionTransfer(useDescription);
    }
    
    public List<UseDescriptionTransfer> getUseDescriptionTransfers(UserVisit userVisit, Use use) {
        List<UseDescription> useDescriptions = getUseDescriptionsByUse(use);
        List<UseDescriptionTransfer> useDescriptionTransfers = new ArrayList<>(useDescriptions.size());
            
        useDescriptions.stream().forEach((useDescription) -> {
            useDescriptionTransfers.add(getOfferTransferCaches(userVisit).getUseDescriptionTransferCache().getUseDescriptionTransfer(useDescription));
        });
        
        return useDescriptionTransfers;
    }
    
    public void updateUseDescriptionFromValue(UseDescriptionValue useDescriptionValue, BasePK updatedBy) {
        if(useDescriptionValue.hasBeenModified()) {
            UseDescription useDescription = UseDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    useDescriptionValue.getPrimaryKey());
            
            useDescription.setThruTime(session.START_TIME_LONG);
            useDescription.store();
            
            Use use = useDescription.getUse();
            Language language = useDescription.getLanguage();
            String description = useDescriptionValue.getDescription();
            
            useDescription = UseDescriptionFactory.getInstance().create(use, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(use.getPrimaryKey(), EventTypes.MODIFY.name(), useDescription.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteUseDescription(UseDescription useDescription, BasePK deletedBy) {
        useDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(useDescription.getUsePK(), EventTypes.MODIFY.name(),
                useDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteUseDescriptionsByUse(Use use, BasePK deletedBy) {
        List<UseDescription> useDescriptions = getUseDescriptionsByUseForUpdate(use);
        
        useDescriptions.stream().forEach((useDescription) -> {
            deleteUseDescription(useDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Uses
    // --------------------------------------------------------------------------------
    
    public OfferUse createOfferUse(Offer offer, Use use, Sequence salesOrderSequence, BasePK createdBy) {
        OfferUse offerUse = OfferUseFactory.getInstance().create();
        OfferUseDetail offerUseDetail = OfferUseDetailFactory.getInstance().create(offerUse, offer, use, salesOrderSequence,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        offerUse = OfferUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, offerUse.getPrimaryKey());
        offerUse.setActiveDetail(offerUseDetail);
        offerUse.setLastDetail(offerUseDetail);
        offerUse.store();
        
        sendEventUsingNames(offerUse.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return offerUse;
    }
    
    private List<OfferUse> getOfferUsesByOffer(Offer offer, EntityPermission entityPermission) {
        List<OfferUse> offerUses = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails, uses, usedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_ofr_offerid = ? " +
                        "AND ofrusedt_use_useid = use_useid AND use_activedetailid = usedt_usedetailid " +
                        "ORDER BY usedt_sortorder, usedt_usename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_ofr_offerid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferUseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, offer.getPrimaryKey().getEntityId());
            
            offerUses = OfferUseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerUses;
    }
    
    public List<OfferUse> getOfferUsesByOffer(Offer offer) {
        return getOfferUsesByOffer(offer, EntityPermission.READ_ONLY);
    }
    
    public List<OfferUse> getOfferUsesByOfferForUpdate(Offer offer) {
        return getOfferUsesByOffer(offer, EntityPermission.READ_WRITE);
    }
    
    private List<OfferUse> getOfferUsesByUse(Use use, EntityPermission entityPermission) {
        List<OfferUse> offerUses = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails, offers, offerdetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_use_useid = ? " +
                        "AND ofrusedt_ofr_offerid = ofr_offerid AND ofr_activedetailid = ofrdt_offerdetailid " +
                        "ORDER BY ofrdt_offername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_use_useid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferUseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, use.getPrimaryKey().getEntityId());
            
            offerUses = OfferUseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerUses;
    }
    
    public List<OfferUse> getOfferUsesByUse(Use use) {
        return getOfferUsesByUse(use, EntityPermission.READ_ONLY);
    }
    
    public List<OfferUse> getOfferUsesByUseForUpdate(Use use) {
        return getOfferUsesByUse(use, EntityPermission.READ_WRITE);
    }
    
    private List<OfferUse> getOfferUsesBySalesOrderSequence(Sequence salesOrderSequence, EntityPermission entityPermission) {
        List<OfferUse> offerUses = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails, sequences, sequencedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_salesordersequenceid = ? " +
                        "AND ofrusedt_salesordersequenceid = sq_sequenceid AND sq_activedetailid = sqdt_sequencedetailid " +
                        "ORDER BY sqdt_sortorder, sqdt_sequencename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_salesordersequenceid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferUseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, salesOrderSequence.getPrimaryKey().getEntityId());
            
            offerUses = OfferUseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerUses;
    }
    
    public List<OfferUse> getOfferUsesBySalesOrderSequence(Sequence salesOrderSequence) {
        return getOfferUsesBySalesOrderSequence(salesOrderSequence, EntityPermission.READ_ONLY);
    }
    
    public List<OfferUse> getOfferUsesBySalesOrderSequenceForUpdate(Sequence salesOrderSequence) {
        return getOfferUsesBySalesOrderSequence(salesOrderSequence, EntityPermission.READ_WRITE);
    }
    
    private OfferUse getOfferUse(Offer offer, Use use, EntityPermission entityPermission) {
        OfferUse offerUse = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_ofr_offerid = ? AND ofrusedt_use_useid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_ofr_offerid = ? AND ofrusedt_use_useid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OfferUseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, offer.getPrimaryKey().getEntityId());
            ps.setLong(2, use.getPrimaryKey().getEntityId());
            
            offerUse = OfferUseFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return offerUse;
    }
    
    public OfferUse getOfferUse(Offer offer, Use use) {
        return getOfferUse(offer, use, EntityPermission.READ_ONLY);
    }
    
    public OfferUse getOfferUseForUpdate(Offer offer, Use use) {
        return getOfferUse(offer, use, EntityPermission.READ_WRITE);
    }
    
    public OfferUseDetailValue getOfferUseDetailValueForUpdate(OfferUse offerUse) {
        return offerUse == null? null: offerUse.getLastDetailForUpdate().getOfferUseDetailValue().clone();
    }
    
    public OfferUseDetailValue getOfferUseDetailValueByNameForUpdate(Offer offer, Use use) {
        return getOfferUseDetailValueForUpdate(getOfferUseForUpdate(offer, use));
    }
    
    public List<OfferUseTransfer> getOfferUseTransfers(UserVisit userVisit, Collection<OfferUse> offerUses) {
        List<OfferUseTransfer> offerUseTransfers = new ArrayList<>(offerUses.size());
        OfferUseTransferCache offerUseTransferCache = getOfferTransferCaches(userVisit).getOfferUseTransferCache();
        
        offerUses.stream().forEach((offerUse) -> {
            offerUseTransfers.add(offerUseTransferCache.getOfferUseTransfer(offerUse));
        });
        
        return offerUseTransfers;
    }
    
    public List<OfferUseTransfer> getOfferUseTransfersByOffer(UserVisit userVisit, Offer offer) {
        return getOfferUseTransfers(userVisit, getOfferUsesByOffer(offer));
    }
    
    public OfferUseTransfer getOfferUseTransfer(UserVisit userVisit, OfferUse offerUse) {
        return getOfferTransferCaches(userVisit).getOfferUseTransferCache().getOfferUseTransfer(offerUse);
    }
    
    public void updateOfferUseFromValue(OfferUseDetailValue offerUseDetailValue, BasePK updatedBy) {
        if(offerUseDetailValue.hasBeenModified()) {
            OfferUse offerUse = OfferUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerUseDetailValue.getOfferUsePK());
            OfferUseDetail offerUseDetail = offerUse.getActiveDetailForUpdate();
            
            offerUseDetail.setThruTime(session.START_TIME_LONG);
            offerUseDetail.store();
            
            OfferUsePK offerUsePK = offerUseDetail.getOfferUsePK();
            OfferPK offerPK = offerUseDetail.getOfferPK();
            UsePK usePK = offerUseDetail.getUsePK();
            SequencePK sequencePK = offerUseDetailValue.getSalesOrderSequencePK();
            
            offerUseDetail = OfferUseDetailFactory.getInstance().create(offerUsePK, offerPK, usePK, sequencePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            offerUse.setActiveDetail(offerUseDetail);
            offerUse.setLastDetail(offerUseDetail);
            
            sendEventUsingNames(offerUsePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteOfferUse(OfferUse offerUse, BasePK deletedBy) {
        deleteSourcesByOfferUse(offerUse, deletedBy);
        
        OfferUseDetail offerUseDetail = offerUse.getLastDetailForUpdate();
        offerUseDetail.setThruTime(session.START_TIME_LONG);
        offerUse.setActiveDetail(null);
        offerUse.store();
        
        sendEventUsingNames(offerUse.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteOfferUses(List<OfferUse> offerUses, BasePK deletedBy) {
        offerUses.stream().forEach((offerUse) -> {
            deleteOfferUse(offerUse, deletedBy);
        });
    }
    
    public void deleteOfferUsesByOffer(Offer offer, BasePK deletedBy) {
        deleteOfferUses(getOfferUsesByOfferForUpdate(offer), deletedBy);
    }
    
    public void deleteOfferUsesByUse(Use use, BasePK deletedBy) {
        deleteOfferUses(getOfferUsesByUseForUpdate(use), deletedBy);
    }
    
    public void deleteOfferUsesBySalesOrderSequence(Sequence salesOrderSequence, BasePK deletedBy) {
        deleteOfferUses(getOfferUsesBySalesOrderSequenceForUpdate(salesOrderSequence), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Sources
    // --------------------------------------------------------------------------------
    
    public Source createSource(String sourceName, OfferUse offerUse, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Source defaultSource = getDefaultSource();
        boolean defaultFound = defaultSource != null;
        
        if(defaultFound && isDefault) {
            SourceDetailValue defaultSourceDetailValue = getDefaultSourceDetailValueForUpdate();
            
            defaultSourceDetailValue.setIsDefault(Boolean.FALSE);
            updateSourceFromValue(defaultSourceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        Source source = SourceFactory.getInstance().create();
        SourceDetail sourceDetail = SourceDetailFactory.getInstance().create(source, sourceName, offerUse, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        source = SourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, source.getPrimaryKey());
        source.setActiveDetail(sourceDetail);
        source.setLastDetail(sourceDetail);
        source.store();
        
        sendEventUsingNames(offerUse.getLastDetail().getOfferPK(), EventTypes.MODIFY.name(), source.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return source;
    }
    
    public long countSourcesByOfferUse(OfferUse offerUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM sources, sourcedetails " +
                "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_ofruse_offeruseid = ?",
                offerUse);
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.Source */
    public Source getSourceByEntityInstance(EntityInstance entityInstance) {
        SourcePK pk = new SourcePK(entityInstance.getEntityUniqueId());
        Source source = SourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return source;
    }
    
    private Source getDefaultSource(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM sources, sourcedetails " +
                    "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM sources, sourcedetails " +
                    "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = SourceFactory.getInstance().prepareStatement(query);
        
        return SourceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public Source getDefaultSource() {
        return getDefaultSource(EntityPermission.READ_ONLY);
    }
    
    public Source getDefaultSourceForUpdate() {
        return getDefaultSource(EntityPermission.READ_WRITE);
    }
    
    public SourceDetailValue getDefaultSourceDetailValueForUpdate() {
        return getDefaultSourceForUpdate().getLastDetailForUpdate().getSourceDetailValue().clone();
    }
    
    private List<Source> getSources(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM sources, sourcedetails " +
                    "WHERE src_activedetailid = srcdt_sourcedetailid " +
                    "ORDER BY srcdt_sortorder, srcdt_sourcename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM sources, sourcedetails " +
                    "WHERE src_activedetailid = srcdt_sourcedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = SourceFactory.getInstance().prepareStatement(query);
        
        return SourceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<Source> getSources() {
        return getSources(EntityPermission.READ_ONLY);
    }
    
    public List<Source> getSourcesForUpdate() {
        return getSources(EntityPermission.READ_WRITE);
    }
    
    private Source getSourceByName(String sourceName, EntityPermission entityPermission) {
        Source source = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sources, sourcedetails " +
                        "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_sourcename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sources, sourcedetails " +
                        "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_sourcename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SourceFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, sourceName);
            
            source = SourceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return source;
    }
    
    public Source getSourceByName(String sourceName) {
        return getSourceByName(sourceName, EntityPermission.READ_ONLY);
    }
    
    public Source getSourceByNameForUpdate(String sourceName) {
        return getSourceByName(sourceName, EntityPermission.READ_WRITE);
    }
    
    public SourceDetailValue getSourceDetailValueForUpdate(Source source) {
        return source == null? null: source.getLastDetailForUpdate().getSourceDetailValue().clone();
    }
    
    public SourceDetailValue getSourceDetailValueByNameForUpdate(String sourceName) {
        return getSourceDetailValueForUpdate(getSourceByNameForUpdate(sourceName));
    }
    
    private List<Source> getSourcesByOfferUse(OfferUse offerUse, EntityPermission entityPermission) {
        List<Source> sources = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sources, sourcedetails " +
                        "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_ofruse_offeruseid = ? " +
                        "ORDER BY srcdt_sortorder, srcdt_sourcename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sources, sourcedetails " +
                        "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_ofruse_offeruseid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SourceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, offerUse.getPrimaryKey().getEntityId());
            
            sources = SourceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sources;
    }
    
    public List<Source> getSourcesByOfferUse(OfferUse offerUse) {
        return getSourcesByOfferUse(offerUse, EntityPermission.READ_ONLY);
    }
    
    public List<Source> getSourcesByOfferUseForUpdate(OfferUse offerUse) {
        return getSourcesByOfferUse(offerUse, EntityPermission.READ_WRITE);
    }
    
    public SourceChoicesBean getSourceChoices(String defaultSourceChoice, Language language, boolean allowNullChoice) {
        List<Source> sources = getSources();
        int size = sources.size() + (allowNullChoice? 1: 0);
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSourceChoice == null) {
                defaultValue = "";
            }
        }
        
        for(Source source : sources) {
            SourceDetail sourceDetail = source.getLastDetail();
            
            String label = getBestSourceDescription(source, language);
            String value = sourceDetail.getSourceName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultSourceChoice == null? false: defaultSourceChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && sourceDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SourceChoicesBean(labels, values, defaultValue);
    }
    
    public String getBestSourceDescription(Source source, Language language) {
        SourceDetail sourceDetail = source.getLastDetail();
        String sourceName = sourceDetail.getSourceName();
        OfferUseDetail offerUseDetail = sourceDetail.getOfferUse().getLastDetail();
        String offerDescription = getBestOfferDescription(offerUseDetail.getOffer(), language);
        String useDescription = getBestUseDescription(offerUseDetail.getUse(), language);
        
        return new StringBuilder(sourceName).append(", ").append(offerDescription).append(", ").append(useDescription).toString();
    }
    
    public SourceTransfer getSourceTransfer(UserVisit userVisit, Source source) {
        return getOfferTransferCaches(userVisit).getSourceTransferCache().getSourceTransfer(source);
    }
    
    public List<SourceTransfer> getSourceTransfers(UserVisit userVisit, Collection<Source> sources) {
        List<SourceTransfer> sourceTransfers = new ArrayList<>(sources.size());
            
        sources.stream().forEach((source) -> {
            sourceTransfers.add(getOfferTransferCaches(userVisit).getSourceTransferCache().getSourceTransfer(source));
        });
        
        return sourceTransfers;
    }
    
    public List<SourceTransfer> getSourceTransfers(UserVisit userVisit) {
        return getSourceTransfers(userVisit, getSources());
    }
    
    private void updateSourceFromValue(SourceDetailValue sourceDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(sourceDetailValue.hasBeenModified()) {
            Source source = SourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     sourceDetailValue.getSourcePK());
            SourceDetail sourceDetail = source.getActiveDetailForUpdate();
            
            sourceDetail.setThruTime(session.START_TIME_LONG);
            sourceDetail.store();
            
            SourcePK sourcePK = sourceDetail.getSourcePK(); // Do not update
            String sourceName = sourceDetailValue.getSourceName();
            OfferUse offerUse = sourceDetail.getOfferUse(); // Do not update
            Boolean isDefault = sourceDetailValue.getIsDefault();
            Integer sortOrder = sourceDetailValue.getSortOrder();
            
            if(checkDefault) {
                Source defaultSource = getDefaultSource();
                boolean defaultFound = defaultSource != null && !defaultSource.equals(source);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SourceDetailValue defaultSourceDetailValue = getDefaultSourceDetailValueForUpdate();
                    
                    defaultSourceDetailValue.setIsDefault(Boolean.FALSE);
                    updateSourceFromValue(defaultSourceDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            sourceDetail = SourceDetailFactory.getInstance().create(sourcePK, sourceName, offerUse.getPrimaryKey(), isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            source.setActiveDetail(sourceDetail);
            source.setLastDetail(sourceDetail);
            
            sendEventUsingNames(offerUse.getLastDetail().getOfferPK(), EventTypes.MODIFY.name(), source.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateSourceFromValue(SourceDetailValue sourceDetailValue, BasePK updatedBy) {
        updateSourceFromValue(sourceDetailValue, true, updatedBy);
    }
    
    public void deleteSource(Source source, BasePK deletedBy) {
        SourceDetail sourceDetail = source.getLastDetailForUpdate();
        sourceDetail.setThruTime(session.START_TIME_LONG);
        source.setActiveDetail(null);
        source.store();
        
        // Check for default, and pick one if necessary
        Source defaultSource = getDefaultSource();
        if(defaultSource == null) {
            List<Source> sources = getSourcesForUpdate();
            
            if(!sources.isEmpty()) {
                Iterator<Source> iter = sources.iterator();
                if(iter.hasNext()) {
                    defaultSource = iter.next();
                }
                SourceDetailValue sourceDetailValue = defaultSource.getLastDetailForUpdate().getSourceDetailValue().clone();
                
                sourceDetailValue.setIsDefault(Boolean.TRUE);
                updateSourceFromValue(sourceDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(source.getLastDetail().getOfferUse().getLastDetail().getOfferPK(),
                EventTypes.MODIFY.name(), source.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteSources(List<Source> sources, BasePK deletedBy) {
        sources.stream().forEach((source) -> {
            deleteSource(source, deletedBy);
        });
    }
    
    public void deleteSourcesByOfferUse(OfferUse offerUse, BasePK deletedBy) {
        deleteSources(getSourcesByOfferUseForUpdate(offerUse), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Items
    // --------------------------------------------------------------------------------

    /** Use the function in OfferLogic instead. */
    public OfferItem createOfferItem(Offer offer, Item item, BasePK createdBy) {
        OfferItem offerItem = OfferItemFactory.getInstance().create(offer, item, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(offerItem.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return offerItem;
    }
    
    public long countOfferItemsByOffer(Offer offer) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM offeritems "
                + "WHERE ofri_ofr_offerid = ? AND ofri_thrutime = ?",
                offer, Session.MAX_TIME);
    }

    public long countOfferItemsByItem(Item item) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM offeritems "
                + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ?",
                item, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getOfferItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM offeritems "
                + "WHERE ofri_ofr_offerid = ? AND ofri_itm_itemid = ? AND ofri_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM offeritems "
                + "WHERE ofri_ofr_offerid = ? AND ofri_itm_itemid = ? AND ofri_thrutime = ? "
                + "FOR UPDATE");
        getOfferItemQueries = Collections.unmodifiableMap(queryMap);
    }

    private OfferItem getOfferItem(Offer offer, Item item, EntityPermission entityPermission) {
        return OfferItemFactory.getInstance().getEntityFromQuery(entityPermission, getOfferItemQueries,
                offer, item, Session.MAX_TIME);
    }
    
    public OfferItem getOfferItem(Offer offer, Item item) {
        return getOfferItem(offer, item, EntityPermission.READ_ONLY);
    }
    
    public OfferItem getOfferItemForUpdate(Offer offer, Item item) {
        return getOfferItem(offer, item, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getOfferItemsByOfferQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM offeritems, items, itemdetails "
                + "WHERE ofri_ofr_offerid = ? AND ofri_thrutime = ? "
                + "AND ofri_itm_itemid = itm_itemid AND itm_activedetailid = itmdt_itemdetailid "
                + "ORDER BY itmdt_itemname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM offeritems "
                + "WHERE ofri_ofr_offerid = ? AND ofri_thrutime = ? "
                + "FOR UPDATE");
        getOfferItemsByOfferQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OfferItem> getOfferItemsByOffer(Offer offer, EntityPermission entityPermission) {
        return OfferItemFactory.getInstance().getEntitiesFromQuery(entityPermission, getOfferItemsByOfferQueries,
                offer, Session.MAX_TIME);
    }
    
    public List<OfferItem> getOfferItemsByOffer(Offer offer) {
        return getOfferItemsByOffer(offer, EntityPermission.READ_ONLY);
    }
    
    public List<OfferItem> getOfferItemsByOfferForUpdate(Offer offer) {
        return getOfferItemsByOffer(offer, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getOfferItemsByItemQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM offeritems, offers, offerdetails "
                + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? "
                + "AND ofri_ofr_offerid = ofr_offerid AND ofr_activedetailid = ofrdt_offerdetailid "
                + "ORDER BY ofrdt_sortorder, ofrdt_offername "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM offeritems "
                + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? "
                + "FOR UPDATE");
        getOfferItemsByItemQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OfferItem> getOfferItemsByItem(Item item, EntityPermission entityPermission) {
        return OfferItemFactory.getInstance().getEntitiesFromQuery(entityPermission, getOfferItemsByItemQueries,
                item, Session.MAX_TIME);
    }
    
    public List<OfferItem> getOfferItemsByItem(Item item) {
        return getOfferItemsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<OfferItem> getOfferItemsByItemForUpdate(Item item) {
        return getOfferItemsByItem(item, EntityPermission.READ_WRITE);
    }
    
    public OfferItemTransfer getOfferItemTransfer(UserVisit userVisit, OfferItem offerItem) {
        return getOfferTransferCaches(userVisit).getOfferItemTransferCache().getOfferItemTransfer(offerItem);
    }
    
    public List<OfferItemTransfer> getOfferItemTransfers(UserVisit userVisit, Collection<OfferItem> offerItems) {
        List<OfferItemTransfer> offerItemTransfers = new ArrayList<>(offerItems.size());
        OfferItemTransferCache offerItemTransferCache = getOfferTransferCaches(userVisit).getOfferItemTransferCache();
        
        offerItems.stream().forEach((offerItem) -> {
            offerItemTransfers.add(offerItemTransferCache.getOfferItemTransfer(offerItem));
        });
        
        return offerItemTransfers;
    }
    
    public List<OfferItemTransfer> getOfferItemTransfersByOffer(UserVisit userVisit, Offer offer) {
        return getOfferItemTransfers(userVisit, getOfferItemsByOffer(offer));
    }
    
    public List<OfferItemTransfer> getOfferItemTransfersByItem(UserVisit userVisit, Item item) {
        return getOfferItemTransfers(userVisit, getOfferItemsByItem(item));
    }
    
    /** Use the function in OfferLogic instead. */
    public void deleteOfferItem(OfferItem offerItem, BasePK deletedBy) {
        deleteOfferItemPricesByOfferItem(offerItem, deletedBy);
        
        offerItem.setThruTime(session.START_TIME_LONG);
        offerItem.store();
        
        sendEventUsingNames(offerItem.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteOfferItems(List<OfferItem> offerItems, BasePK deletedBy) {
        offerItems.stream().forEach((offerItem) -> {
            OfferLogic.getInstance().deleteOfferItem(offerItem, deletedBy);
        });
    }
    
    public void deleteOfferItemsByOffer(Offer offer, BasePK deletedBy) {
        deleteOfferItems(getOfferItemsByOfferForUpdate(offer), deletedBy);
    }
    
    public void deleteOfferItemsByOffers(List<Offer> offers, BasePK deletedBy) {
        offers.stream().forEach((offer) -> {
            deleteOfferItemsByOffer(offer, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Item Prices
    // --------------------------------------------------------------------------------
    
    /** Use the function in OfferLogic instead. */
    public OfferItemPrice createOfferItemPrice(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency, BasePK createdBy) {
        OfferItemPrice offerItemPrice = OfferItemPriceFactory.getInstance().create(offerItem, inventoryCondition, unitOfMeasureType, currency,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(offerItem.getPrimaryKey(), EventTypes.MODIFY.name(), offerItemPrice.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return offerItemPrice;
    }
    
    private static final Map<EntityPermission, String> getOfferItemPricesByOfferItemQueries1;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM offeritemprices "
                + "WHERE ofritmp_ofri_offeritemid = ? AND ofritmp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM offeritemprices "
                + "WHERE ofritmp_ofri_offeritemid = ? AND ofritmp_thrutime = ? "
                + "FOR UPDATE");
        getOfferItemPricesByOfferItemQueries1 = Collections.unmodifiableMap(queryMap);
    }

    private List<OfferItemPrice> getOfferItemPricesByOfferItem(OfferItem offerItem, EntityPermission entityPermission) {
        return OfferItemPriceFactory.getInstance().getEntitiesFromQuery(entityPermission, getOfferItemPricesByOfferItemQueries1,
                offerItem, Session.MAX_TIME);
    }
    
    public List<OfferItemPrice> getOfferItemPricesByOfferItem(OfferItem offerItem) {
        return getOfferItemPricesByOfferItem(offerItem, EntityPermission.READ_ONLY);
    }
    
    public List<OfferItemPrice> getOfferItemPricesByOfferItemForUpdate(OfferItem offerItem) {
        return getOfferItemPricesByOfferItem(offerItem, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getOfferItemPricesByItemAndUnitOfMeasureTypeQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM offeritems, offeritemprices "
                + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? "
                + "AND ofri_offeritemid = ofritmp_ofri_offeritemid AND ofritmp_uomt_unitofmeasuretypeid = ? AND ofritmp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM offeritems, offeritemprices "
                + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? "
                + "AND ofri_offeritemid = ofritmp_ofri_offeritemid AND ofritmp_uomt_unitofmeasuretypeid = ? AND ofritmp_thrutime = ? "
                + "FOR UPDATE");
        getOfferItemPricesByItemAndUnitOfMeasureTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OfferItemPrice> getOfferItemPricesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        return OfferItemPriceFactory.getInstance().getEntitiesFromQuery(entityPermission, getOfferItemPricesByItemAndUnitOfMeasureTypeQueries,
                item, Session.MAX_TIME, unitOfMeasureType, Session.MAX_TIME);
    }
    
    public List<OfferItemPrice> getOfferItemPricesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getOfferItemPricesByItemAndUnitOfMeasureType(item, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<OfferItemPrice> getOfferItemPricesByItemAndUnitOfMeasureTypeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getOfferItemPricesByItemAndUnitOfMeasureType(item, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getOfferItemPricesByOfferItemQueries2;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM offeritemprices "
                + "WHERE ofritmp_ofri_offeritemid = ? AND ofritmp_invcon_inventoryconditionid = ? "
                + "AND ofritmp_uomt_unitofmeasuretypeid = ? AND ofritmp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM offeritemprices "
                + "WHERE ofritmp_ofri_offeritemid = ? AND ofritmp_invcon_inventoryconditionid = ? "
                + "AND ofritmp_uomt_unitofmeasuretypeid = ? AND ofritmp_thrutime = ? "
                + "FOR UPDATE");
        getOfferItemPricesByOfferItemQueries2 = Collections.unmodifiableMap(queryMap);
    }

    private List<OfferItemPrice> getOfferItemPricesByOfferItem(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            EntityPermission entityPermission) {
        return OfferItemPriceFactory.getInstance().getEntitiesFromQuery(entityPermission, getOfferItemPricesByOfferItemQueries2,
                offerItem, inventoryCondition, unitOfMeasureType, Session.MAX_TIME);
    }
    
    public List<OfferItemPrice> getOfferItemPricesByOfferItem(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType) {
        return getOfferItemPricesByOfferItem(offerItem, inventoryCondition, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<OfferItemPrice> getOfferItemPricesByOfferItemForUpdate(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType) {
        return getOfferItemPricesByOfferItem(offerItem, inventoryCondition, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getOfferItemPricesQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM offeritems, offeritemprices "
                + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? AND ofri_offeritemid = ofritmp_ofri_offeritemid "
                + "AND ofritmp_invcon_inventoryconditionid = ? AND ofritmp_uomt_unitofmeasuretypeid = ? "
                + "AND ofritmp_cur_currencyid = ? AND ofritmp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM offeritems, offeritemprices "
                + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? AND ofri_offeritemid = ofritmp_ofri_offeritemid "
                + "AND ofritmp_invcon_inventoryconditionid = ? AND ofritmp_uomt_unitofmeasuretypeid = ? "
                + "AND ofritmp_cur_currencyid = ? AND ofritmp_thrutime = ? "
                + "FOR UPDATE");
        getOfferItemPricesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OfferItemPrice> getOfferItemPrices(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency, EntityPermission entityPermission) {
        return OfferItemPriceFactory.getInstance().getEntitiesFromQuery(entityPermission, getOfferItemPricesQueries,
                item, Session.MAX_TIME, inventoryCondition, unitOfMeasureType, currency, Session.MAX_TIME);
    }
    
    public List<OfferItemPrice> getOfferItemPrices(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getOfferItemPrices(item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_ONLY);
    }
    
    public List<OfferItemPrice> getOfferItemPricesForUpdate(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getOfferItemPrices(item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getOfferItemPriceQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM offeritemprices "
                + "WHERE ofritmp_ofri_offeritemid = ? AND ofritmp_invcon_inventoryconditionid = ? "
                + "AND ofritmp_uomt_unitofmeasuretypeid = ? AND ofritmp_cur_currencyid = ? AND ofritmp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM offeritemprices "
                + "WHERE ofritmp_ofri_offeritemid = ? AND ofritmp_invcon_inventoryconditionid = ? "
                + "AND ofritmp_uomt_unitofmeasuretypeid = ? AND ofritmp_cur_currencyid = ? AND ofritmp_thrutime = ? "
                + "FOR UPDATE");
        getOfferItemPriceQueries = Collections.unmodifiableMap(queryMap);
    }

    private OfferItemPrice getOfferItemPrice(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency, EntityPermission entityPermission) {
        return OfferItemPriceFactory.getInstance().getEntityFromQuery(entityPermission, getOfferItemPriceQueries,
                offerItem, inventoryCondition, unitOfMeasureType, currency, Session.MAX_TIME);
    }
    
    public OfferItemPrice getOfferItemPrice(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency) {
        return getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_ONLY);
    }
    
    public OfferItemPrice getOfferItemPriceForUpdate(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency) {
        return getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_WRITE);
    }
    
    public long countOfferItemPricesByItem(Item item) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM offeritems, offeritemprices "
                + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? "
                + "AND ofri_offeritemid = ofritmp_ofri_offeritemid AND ofritmp_thrutime = ?",
                item, Session.MAX_TIME, Session.MAX_TIME);
    }

    public OfferItemPriceTransfer getOfferItemPriceTransfer(UserVisit userVisit, OfferItemPrice offerItemPrice) {
        return getOfferTransferCaches(userVisit).getOfferItemPriceTransferCache().getTransfer(offerItemPrice);
    }
    
    public List<OfferItemPriceTransfer> getOfferItemPriceTransfers(UserVisit userVisit, Collection<OfferItemPrice> offerItemPrices) {
        List<OfferItemPriceTransfer> offerItemPriceTransfers = new ArrayList<>(offerItemPrices.size());
        OfferItemPriceTransferCache offerItemPriceTransferCache = getOfferTransferCaches(userVisit).getOfferItemPriceTransferCache();
        
        offerItemPrices.stream().forEach((offerItemPrice) -> {
            offerItemPriceTransfers.add(offerItemPriceTransferCache.getTransfer(offerItemPrice));
        });
        
        return offerItemPriceTransfers;
    }
    
    public ListWrapper<HistoryTransfer<OfferItemPriceTransfer>> getOfferItemPriceHistory(UserVisit userVisit, OfferItemPrice offerItemPrice) {
        return getOfferTransferCaches(userVisit).getOfferItemPriceTransferCache().getHistory(offerItemPrice);
    }
    
    public List<OfferItemPriceTransfer> getOfferItemPriceTransfersByOfferItem(UserVisit userVisit, OfferItem offerItem) {
        return getOfferItemPriceTransfers(userVisit, getOfferItemPricesByOfferItem(offerItem));
    }
    
    /** Use the function in OfferLogic instead. */
    public void deleteOfferItemPrice(OfferItemPrice offerItemPrice, BasePK deletedBy) {
        offerItemPrice.setThruTime(session.START_TIME_LONG);
        offerItemPrice.store();
        
        OfferItem offerItem = offerItemPrice.getOfferItemForUpdate();
        Item item = offerItem.getItem();
        ItemPriceType itemPriceType = item.getLastDetail().getItemPriceType();
        String itemPriceTypeName = itemPriceType.getItemPriceTypeName();
        
        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
            OfferItemFixedPrice offerItemFixedPrice = getOfferItemFixedPriceForUpdate(offerItemPrice);
            
            OfferLogic.getInstance().deleteOfferItemFixedPrice(offerItemFixedPrice, deletedBy);
        } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
            OfferItemVariablePrice offerItemVariablePrice = getOfferItemVariablePriceForUpdate(offerItemPrice);
            
            OfferLogic.getInstance().deleteOfferItemVariablePrice(offerItemVariablePrice, deletedBy);
        }
        
        sendEventUsingNames(offerItem.getPrimaryKey(), EventTypes.MODIFY.name(), offerItemPrice.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
        if(countOfferItemPricesByItem(item) == 0) {
            OfferLogic.getInstance().deleteOfferItem(offerItem, deletedBy);
        }
    }
    
    public void deleteOfferItemPrices(List<OfferItemPrice> offerItemPrices, BasePK deletedBy) {
        offerItemPrices.stream().forEach((offerItemPrice) -> {
            OfferLogic.getInstance().deleteOfferItemPrice(offerItemPrice, deletedBy);
        });
    }
    
    public void deleteOfferItemPricesByOfferItem(OfferItem offerItem, BasePK deletedBy) {
        deleteOfferItemPrices(getOfferItemPricesByOfferItemForUpdate(offerItem), deletedBy);
    }
    
    public void deleteOfferItemPricesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteOfferItemPrices(getOfferItemPricesByItemAndUnitOfMeasureTypeForUpdate(item, unitOfMeasureType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Item Fixed Prices
    // --------------------------------------------------------------------------------
    
    /** Use the function in OfferLogic instead. */
    public OfferItemFixedPrice createOfferItemFixedPrice(OfferItemPrice offerItemPrice, Long unitPrice, BasePK createdBy) {
        OfferItemFixedPrice offerItemFixedPrice = OfferItemFixedPriceFactory.getInstance().create(offerItemPrice,
                unitPrice, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(offerItemPrice.getOfferItemPK(), EventTypes.MODIFY.name(), offerItemFixedPrice.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return offerItemFixedPrice;
    }
    
    private static final Map<EntityPermission, String> getOfferItemFixedPriceQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM offeritemfixedprices "
                + "WHERE ofritmfp_ofritmp_offeritempriceid = ? AND ofritmfp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM offeritemfixedprices "
                + "WHERE ofritmfp_ofritmp_offeritempriceid = ? AND ofritmfp_thrutime = ? "
                + "FOR UPDATE");
        getOfferItemFixedPriceQueries = Collections.unmodifiableMap(queryMap);
    }

    private OfferItemFixedPrice getOfferItemFixedPrice(OfferItemPrice offerItemPrice, EntityPermission entityPermission) {
        return OfferItemFixedPriceFactory.getInstance().getEntityFromQuery(entityPermission, getOfferItemFixedPriceQueries,
                offerItemPrice, Session.MAX_TIME);
    }
    
    public OfferItemFixedPrice getOfferItemFixedPrice(OfferItemPrice offerItemPrice) {
        return getOfferItemFixedPrice(offerItemPrice, EntityPermission.READ_ONLY);
    }
    
    public OfferItemFixedPrice getOfferItemFixedPriceForUpdate(OfferItemPrice offerItemPrice) {
        return getOfferItemFixedPrice(offerItemPrice, EntityPermission.READ_WRITE);
    }
    
    public OfferItemFixedPriceValue getOfferItemFixedPriceValueForUpdate(OfferItemPrice offerItemPrice) {
        OfferItemFixedPrice offerItemFixedPrice = getOfferItemFixedPriceForUpdate(offerItemPrice);
        
        return offerItemFixedPrice == null? null: offerItemFixedPrice.getOfferItemFixedPriceValue().clone();
    }
    
    private static final Map<EntityPermission, String> getOfferItemFixedPriceHistoryQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM offeritemfixedprices "
                + "WHERE ofritmfp_ofritmp_offeritempriceid = ? "
                + "ORDER BY ofritmfp_thrutime");
        getOfferItemFixedPriceHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<OfferItemFixedPrice> getOfferItemFixedPriceHistory(OfferItemPrice offerItemPrice) {
        return OfferItemFixedPriceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getOfferItemFixedPriceHistoryQueries,
                offerItemPrice);
    }
    
    /** Use the function in OfferLogic instead. */
    public OfferItemFixedPrice updateOfferItemFixedPriceFromValue(OfferItemFixedPriceValue offerItemFixedPriceValue, BasePK updatedBy) {
        OfferItemFixedPrice offerItemFixedPrice = null;

        if(offerItemFixedPriceValue.hasBeenModified()) {
            offerItemFixedPrice = OfferItemFixedPriceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    offerItemFixedPriceValue.getPrimaryKey());
            
            offerItemFixedPrice.setThruTime(session.START_TIME_LONG);
            offerItemFixedPrice.store();
            
            OfferItemPricePK offerItemPricePK = offerItemFixedPrice.getOfferItemPricePK();
            Long unitPrice = offerItemFixedPriceValue.getUnitPrice();
            
            offerItemFixedPrice = OfferItemFixedPriceFactory.getInstance().create(offerItemPricePK, unitPrice,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(offerItemFixedPrice.getOfferItemPrice().getOfferItemPK(), EventTypes.MODIFY.name(),
                    offerItemFixedPrice.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }

        return offerItemFixedPrice;
    }
    
    /** Use the function in OfferLogic instead. */
    public void deleteOfferItemFixedPrice(OfferItemFixedPrice offerItemFixedPrice, BasePK deletedBy) {
        offerItemFixedPrice.setThruTime(session.START_TIME_LONG);
        offerItemFixedPrice.store();
        
        sendEventUsingNames(offerItemFixedPrice.getOfferItemPrice().getOfferItemPK(), EventTypes.MODIFY.name(),
                offerItemFixedPrice.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Item Variable Prices
    // --------------------------------------------------------------------------------
    
    /** Use the function in OfferLogic instead. */
    public OfferItemVariablePrice createOfferItemVariablePrice(OfferItemPrice offerItemPrice, Long minimumUnitPrice, Long maximumUnitPrice,
            Long unitPriceIncrement, BasePK createdBy) {
        OfferItemVariablePrice offerItemVariablePrice = OfferItemVariablePriceFactory.getInstance().create(offerItemPrice, minimumUnitPrice, maximumUnitPrice,
                unitPriceIncrement, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(offerItemPrice.getOfferItemPK(), EventTypes.MODIFY.name(), offerItemVariablePrice.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return offerItemVariablePrice;
    }
    
    private static final Map<EntityPermission, String> getOfferItemVariablePriceQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM offeritemvariableprices "
                + "WHERE ofritmvp_ofritmp_offeritempriceid = ? AND ofritmvp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM offeritemvariableprices "
                + "WHERE ofritmvp_ofritmp_offeritempriceid = ? AND ofritmvp_thrutime = ? "
                + "FOR UPDATE");
        getOfferItemVariablePriceQueries = Collections.unmodifiableMap(queryMap);
    }

    private OfferItemVariablePrice getOfferItemVariablePrice(OfferItemPrice offerItemPrice, EntityPermission entityPermission) {
        return OfferItemVariablePriceFactory.getInstance().getEntityFromQuery(entityPermission, getOfferItemVariablePriceQueries,
                offerItemPrice, Session.MAX_TIME);
    }
    
    public OfferItemVariablePrice getOfferItemVariablePrice(OfferItemPrice offerItemPrice) {
        return getOfferItemVariablePrice(offerItemPrice, EntityPermission.READ_ONLY);
    }
    
    public OfferItemVariablePrice getOfferItemVariablePriceForUpdate(OfferItemPrice offerItemPrice) {
        return getOfferItemVariablePrice(offerItemPrice, EntityPermission.READ_WRITE);
    }
    
    public OfferItemVariablePriceValue getOfferItemVariablePriceValueForUpdate(OfferItemPrice offerItemPrice) {
        OfferItemVariablePrice offerItemVariablePrice = getOfferItemVariablePriceForUpdate(offerItemPrice);
        
        return offerItemVariablePrice == null? null: offerItemVariablePrice.getOfferItemVariablePriceValue().clone();
    }
    
    private static final Map<EntityPermission, String> getOfferItemVariablePriceHistoryQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM offeritemvariableprices "
                + "WHERE ofritmvp_ofritmp_offeritempriceid = ? "
                + "ORDER BY ofritmvp_thrutime");
        getOfferItemVariablePriceHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<OfferItemVariablePrice> getOfferItemVariablePriceHistory(OfferItemPrice offerItemPrice) {
        return OfferItemVariablePriceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getOfferItemVariablePriceHistoryQueries,
                offerItemPrice);
    }
    
    /** Use the function in OfferLogic instead. */
    public OfferItemVariablePrice updateOfferItemVariablePriceFromValue(OfferItemVariablePriceValue offerItemVariablePriceValue, BasePK updatedBy) {
        OfferItemVariablePrice offerItemVariablePrice = null;

        if(offerItemVariablePriceValue.hasBeenModified()) {
            offerItemVariablePrice = OfferItemVariablePriceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerItemVariablePriceValue.getPrimaryKey());
            
            offerItemVariablePrice.setThruTime(session.START_TIME_LONG);
            offerItemVariablePrice.store();
            
            OfferItemPricePK offerItemPricePK = offerItemVariablePrice.getOfferItemPricePK();
            Long maximumUnitPrice = offerItemVariablePriceValue.getMaximumUnitPrice();
            Long minimumUnitPrice = offerItemVariablePriceValue.getMinimumUnitPrice();
            Long unitPriceIncrement = offerItemVariablePriceValue.getUnitPriceIncrement();
            
            offerItemVariablePrice = OfferItemVariablePriceFactory.getInstance().create(offerItemPricePK, maximumUnitPrice,
                    minimumUnitPrice, unitPriceIncrement, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(offerItemVariablePrice.getOfferItemPrice().getOfferItemPK(), EventTypes.MODIFY.name(),
                    offerItemVariablePrice.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }

        return offerItemVariablePrice;
    }
    
    /** Use the function in OfferLogic instead. */
    public void deleteOfferItemVariablePrice(OfferItemVariablePrice offerItemVariablePrice, BasePK deletedBy) {
        offerItemVariablePrice.setThruTime(session.START_TIME_LONG);
        offerItemVariablePrice.store();
        
        sendEventUsingNames(offerItemVariablePrice.getOfferItemPrice().getOfferItemPK(), EventTypes.MODIFY.name(),
                offerItemVariablePrice.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
}
