// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.offer.common.transfer.OfferNameElementDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.OfferNameElementTransfer;
import com.echothree.model.control.offer.server.transfer.OfferNameElementDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferNameElementTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.offer.common.pk.OfferNameElementPK;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.offer.server.entity.OfferNameElementDescription;
import com.echothree.model.data.offer.server.entity.OfferNameElementDetail;
import com.echothree.model.data.offer.server.factory.OfferNameElementDescriptionFactory;
import com.echothree.model.data.offer.server.factory.OfferNameElementDetailFactory;
import com.echothree.model.data.offer.server.factory.OfferNameElementFactory;
import com.echothree.model.data.offer.server.value.OfferNameElementDescriptionValue;
import com.echothree.model.data.offer.server.value.OfferNameElementDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OfferNameElementControl
        extends BaseOfferControl {

    /** Creates a new instance of OfferNameElementControl */
    public OfferNameElementControl() {
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

    public long countOfferNameElements() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM offernameelements, offernameelementdetails " +
                "WHERE ofrne_activedetailid = ofrnedt_offernameelementdetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.OfferNameElement */
    public OfferNameElement getOfferNameElementByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new OfferNameElementPK(entityInstance.getEntityUniqueId());
        var offerNameElement = OfferNameElementFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return offerNameElement;
    }

    public OfferNameElement getOfferNameElementByEntityInstance(EntityInstance entityInstance) {
        return getOfferNameElementByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public OfferNameElement getOfferNameElementByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getOfferNameElementByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public OfferNameElement getOfferNameElementByName(String offerNameElementName, EntityPermission entityPermission) {
        OfferNameElement offerNameElement;
        
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
        
        for(var offerNameElement : offerNameElements) {
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
        OfferNameElementDescription offerNameElementDescription;
        
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
        List<OfferNameElementDescription> offerNameElementDescriptions;
        
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
    
    public List<OfferNameElementDescriptionTransfer> getOfferNameElementDescriptionTransfersByOfferNameElement(UserVisit userVisit, OfferNameElement offerNameElement) {
        List<OfferNameElementDescription> offerNameElementDescriptions = getOfferNameElementDescriptionsByOfferNameElement(offerNameElement);
        List<OfferNameElementDescriptionTransfer> offerNameElementDescriptionTransfers = new ArrayList<>(offerNameElementDescriptions.size());
        OfferNameElementDescriptionTransferCache offerNameElementDescriptionTransferCache = getOfferTransferCaches(userVisit).getOfferNameElementDescriptionTransferCache();
        
        offerNameElementDescriptions.forEach((offerNameElementDescription) ->
                offerNameElementDescriptionTransfers.add(offerNameElementDescriptionTransferCache.getOfferNameElementDescriptionTransfer(offerNameElementDescription))
        );
        
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
        
        offerNameElementDescriptions.forEach((offerNameElementDescription) -> 
                deleteOfferNameElementDescription(offerNameElementDescription, deletedBy)
        );
    }
    
}
