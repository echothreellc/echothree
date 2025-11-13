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
import com.echothree.model.control.offer.common.transfer.OfferNameElementDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.OfferNameElementTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.offer.common.pk.OfferNameElementPK;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.offer.server.entity.OfferNameElementDescription;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OfferNameElementControl
        extends BaseOfferControl {

    /** Creates a new instance of OfferNameElementControl */
    protected OfferNameElementControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Name Elements
    // --------------------------------------------------------------------------------
    
    public OfferNameElement createOfferNameElement(String offerNameElementName, Integer offset, Integer length,
            String validationPattern, BasePK createdBy) {
        var offerNameElement = OfferNameElementFactory.getInstance().create();
        var offerNameElementDetail = OfferNameElementDetailFactory.getInstance().create(session,
                offerNameElement, offerNameElementName, offset, length, validationPattern, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        offerNameElement = OfferNameElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                offerNameElement.getPrimaryKey());
        offerNameElement.setActiveDetail(offerNameElementDetail);
        offerNameElement.setLastDetail(offerNameElementDetail);
        offerNameElement.store();
        
        sendEvent(offerNameElement.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return offerNameElement;
    }

    public long countOfferNameElements() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM offernameelements, offernameelementdetails " +
                "WHERE ofrne_activedetailid = ofrnedt_offernameelementdetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.OfferNameElement */
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

            var ps = OfferNameElementFactory.getInstance().prepareStatement(query);
            
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

        var ps = OfferNameElementFactory.getInstance().prepareStatement(query);
        
        return OfferNameElementFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<OfferNameElement> getOfferNameElements() {
        return getOfferNameElements(EntityPermission.READ_ONLY);
    }
    
    public List<OfferNameElement> getOfferNameElementsForUpdate() {
        return getOfferNameElements(EntityPermission.READ_WRITE);
    }
    
    public OfferNameElementTransfer getOfferNameElementTransfer(UserVisit userVisit, OfferNameElement offerNameElement) {
        return getOfferTransferCaches().getOfferNameElementTransferCache().getOfferNameElementTransfer(userVisit, offerNameElement);
    }
    
    public List<OfferNameElementTransfer> getOfferNameElementTransfers(UserVisit userVisit, Collection<OfferNameElement> offerNameElements) {
        List<OfferNameElementTransfer> offerNameElementTransfers = new ArrayList<>(offerNameElements.size());
        var offerNameElementTransferCache = getOfferTransferCaches().getOfferNameElementTransferCache();
        
        for(var offerNameElement : offerNameElements) {
            offerNameElementTransfers.add(offerNameElementTransferCache.getOfferNameElementTransfer(userVisit, offerNameElement));
        }
        
        return offerNameElementTransfers;
    }
    
    public List<OfferNameElementTransfer> getOfferNameElementTransfers(UserVisit userVisit) {
        return getOfferNameElementTransfers(userVisit, getOfferNameElements());
    }
    
    public void updateOfferNameElementFromValue(OfferNameElementDetailValue offerNameElementDetailValue, BasePK updatedBy) {
        if(offerNameElementDetailValue.hasBeenModified()) {
            var offerNameElement = OfferNameElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerNameElementDetailValue.getOfferNameElementPK());
            var offerNameElementDetail = offerNameElement.getActiveDetailForUpdate();
            
            offerNameElementDetail.setThruTime(session.START_TIME_LONG);
            offerNameElementDetail.store();

            var offerNameElementPK = offerNameElementDetail.getOfferNameElementPK();
            var offerNameElementName = offerNameElementDetailValue.getOfferNameElementName();
            var offset = offerNameElementDetailValue.getOffset();
            var length = offerNameElementDetailValue.getLength();
            var validationPattern = offerNameElementDetailValue.getValidationPattern();
            
            offerNameElementDetail = OfferNameElementDetailFactory.getInstance().create(offerNameElementPK,
                    offerNameElementName, offset, length, validationPattern, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            offerNameElement.setActiveDetail(offerNameElementDetail);
            offerNameElement.setLastDetail(offerNameElementDetail);
            
            sendEvent(offerNameElementPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteOfferNameElement(OfferNameElement offerNameElement, BasePK deletedBy) {
        deleteOfferNameElementDescriptionsByOfferNameElement(offerNameElement, deletedBy);

        var offerNameElementDetail = offerNameElement.getLastDetailForUpdate();
        offerNameElementDetail.setThruTime(session.START_TIME_LONG);
        offerNameElement.setActiveDetail(null);
        offerNameElement.store();
        
        sendEvent(offerNameElement.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Name Element Descriptions
    // --------------------------------------------------------------------------------
    
    public OfferNameElementDescription createOfferNameElementDescription(OfferNameElement offerNameElement, Language language,
            String description, BasePK createdBy) {
        var offerNameElementDescription = OfferNameElementDescriptionFactory.getInstance().create(session,
                offerNameElement, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(offerNameElement.getPrimaryKey(), EventTypes.MODIFY,
                offerNameElementDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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

            var ps = OfferNameElementDescriptionFactory.getInstance().prepareStatement(query);
            
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

            var ps = OfferNameElementDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var offerNameElementDescription = getOfferNameElementDescription(offerNameElement, language);
        
        if(offerNameElementDescription == null && !language.getIsDefault()) {
            offerNameElementDescription = getOfferNameElementDescription(offerNameElement, partyControl.getDefaultLanguage());
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
        return getOfferTransferCaches().getOfferNameElementDescriptionTransferCache().getOfferNameElementDescriptionTransfer(userVisit, offerNameElementDescription);
    }
    
    public List<OfferNameElementDescriptionTransfer> getOfferNameElementDescriptionTransfersByOfferNameElement(UserVisit userVisit, OfferNameElement offerNameElement) {
        var offerNameElementDescriptions = getOfferNameElementDescriptionsByOfferNameElement(offerNameElement);
        List<OfferNameElementDescriptionTransfer> offerNameElementDescriptionTransfers = new ArrayList<>(offerNameElementDescriptions.size());
        var offerNameElementDescriptionTransferCache = getOfferTransferCaches().getOfferNameElementDescriptionTransferCache();
        
        offerNameElementDescriptions.forEach((offerNameElementDescription) ->
                offerNameElementDescriptionTransfers.add(offerNameElementDescriptionTransferCache.getOfferNameElementDescriptionTransfer(userVisit, offerNameElementDescription))
        );
        
        return offerNameElementDescriptionTransfers;
    }
    
    public void updateOfferNameElementDescriptionFromValue(OfferNameElementDescriptionValue offerNameElementDescriptionValue, BasePK updatedBy) {
        if(offerNameElementDescriptionValue.hasBeenModified()) {
            var offerNameElementDescription = OfferNameElementDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     offerNameElementDescriptionValue.getPrimaryKey());
            
            offerNameElementDescription.setThruTime(session.START_TIME_LONG);
            offerNameElementDescription.store();

            var offerNameElement = offerNameElementDescription.getOfferNameElement();
            var language = offerNameElementDescription.getLanguage();
            var description = offerNameElementDescriptionValue.getDescription();
            
            offerNameElementDescription = OfferNameElementDescriptionFactory.getInstance().create(offerNameElement,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(offerNameElement.getPrimaryKey(), EventTypes.MODIFY,
                    offerNameElementDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteOfferNameElementDescription(OfferNameElementDescription offerNameElementDescription, BasePK deletedBy) {
        offerNameElementDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(offerNameElementDescription.getOfferNameElementPK(), EventTypes.MODIFY,
                offerNameElementDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteOfferNameElementDescriptionsByOfferNameElement(OfferNameElement offerNameElement, BasePK deletedBy) {
        var offerNameElementDescriptions = getOfferNameElementDescriptionsByOfferNameElementForUpdate(offerNameElement);
        
        offerNameElementDescriptions.forEach((offerNameElementDescription) -> 
                deleteOfferNameElementDescription(offerNameElementDescription, deletedBy)
        );
    }
    
}
