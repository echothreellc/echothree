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
import com.echothree.model.control.offer.common.transfer.OfferNameElementDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.OfferNameElementTransfer;
import com.echothree.model.control.offer.server.transfer.OfferNameElementDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferNameElementTransferCache;
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
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_NAME_ELEMENTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_NAME_ELEMENT_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.offer.OfferNameElementDescriptions.OfferNameElementDescriptions;
import static com.echothree.model.jooq.server.tables.offer.OfferNameElementDetails.OfferNameElementDetails;
import static com.echothree.model.jooq.server.tables.offer.OfferNameElements.OfferNameElements;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class OfferNameElementControl
        extends BaseModelControl {

    /** Creates a new instance of OfferNameElementControl */
    protected OfferNameElementControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Name Elements
    // --------------------------------------------------------------------------------

    @Inject
    protected OfferNameElementDetailFactory offerNameElementDetailFactory;

    @Inject
    private OfferNameElementFactory offerNameElementFactory;

    @Inject
    private OfferNameElementTransferCache offerNameElementTransferCache;

    public OfferNameElement createOfferNameElement(String offerNameElementName, Integer offset, Integer length,
            String validationPattern, BasePK createdBy) {
        var offerNameElement = offerNameElementFactory.create();
        var offerNameElementDetail = offerNameElementDetailFactory.create(
                offerNameElement, offerNameElementName, offset, length, validationPattern, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        offerNameElement = offerNameElementFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                offerNameElement.getPrimaryKey());
        offerNameElement.setActiveDetail(offerNameElementDetail);
        offerNameElement.setLastDetail(offerNameElementDetail);
        offerNameElement.store();
        
        sendEvent(offerNameElement.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return offerNameElement;
    }

    public long countOfferNameElements() {
        return session.getDslContext()
                .selectCount()
                .from(OfferNameElements)
                .join(OfferNameElementDetails).onKey(OFFER_NAME_ELEMENTS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.OfferNameElement */
    public OfferNameElement getOfferNameElementByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new OfferNameElementPK(entityInstance.getEntityUniqueId());
        var offerNameElement = offerNameElementFactory.getEntityFromPK(entityPermission, pk);

        return offerNameElement;
    }

    public OfferNameElement getOfferNameElementByEntityInstance(EntityInstance entityInstance) {
        return getOfferNameElementByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public OfferNameElement getOfferNameElementByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getOfferNameElementByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public OfferNameElement getOfferNameElementByName(String offerNameElementName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferNameElements.fields())
                .from(OfferNameElements)
                .join(OfferNameElementDetails).onKey(OFFER_NAME_ELEMENTS_ACTIVE_DETAIL_FK)
                .where(OfferNameElementDetails.OFFER_NAME_ELEMENT_NAME.eq(offerNameElementName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerNameElementFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(OfferNameElements.fields())
                .from(OfferNameElements)
                .join(OfferNameElementDetails).onKey(OFFER_NAME_ELEMENTS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(OfferNameElementDetails.OFFSET),
                    OfferNameElementFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerNameElementFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<OfferNameElement> getOfferNameElements() {
        return getOfferNameElements(EntityPermission.READ_ONLY);
    }
    
    public List<OfferNameElement> getOfferNameElementsForUpdate() {
        return getOfferNameElements(EntityPermission.READ_WRITE);
    }
    
    public OfferNameElementTransfer getOfferNameElementTransfer(UserVisit userVisit, OfferNameElement offerNameElement) {
        return offerNameElementTransferCache.getOfferNameElementTransfer(userVisit, offerNameElement);
    }
    
    public List<OfferNameElementTransfer> getOfferNameElementTransfers(UserVisit userVisit, Collection<OfferNameElement> offerNameElements) {
        List<OfferNameElementTransfer> offerNameElementTransfers = new ArrayList<>(offerNameElements.size());
        
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
            var offerNameElement = offerNameElementFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     offerNameElementDetailValue.getOfferNameElementPK());
            var offerNameElementDetail = offerNameElement.getActiveDetailForUpdate();
            
            offerNameElementDetail.setThruTime(session.getStartTime());
            offerNameElementDetail.store();

            var offerNameElementPK = offerNameElementDetail.getOfferNameElementPK();
            var offerNameElementName = offerNameElementDetailValue.getOfferNameElementName();
            var offset = offerNameElementDetailValue.getOffset();
            var length = offerNameElementDetailValue.getLength();
            var validationPattern = offerNameElementDetailValue.getValidationPattern();
            
            offerNameElementDetail = offerNameElementDetailFactory.create(offerNameElementPK,
                    offerNameElementName, offset, length, validationPattern, session.getStartTime(), Session.MAX_TIME);
            
            offerNameElement.setActiveDetail(offerNameElementDetail);
            offerNameElement.setLastDetail(offerNameElementDetail);
            
            sendEvent(offerNameElementPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteOfferNameElement(OfferNameElement offerNameElement, BasePK deletedBy) {
        deleteOfferNameElementDescriptionsByOfferNameElement(offerNameElement, deletedBy);

        var offerNameElementDetail = offerNameElement.getLastDetailForUpdate();
        offerNameElementDetail.setThruTime(session.getStartTime());
        offerNameElement.setActiveDetail(null);
        offerNameElement.store();
        
        sendEvent(offerNameElement.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Name Element Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private OfferNameElementDescriptionFactory offerNameElementDescriptionFactory;

    @Inject
    private OfferNameElementDescriptionTransferCache offerNameElementDescriptionTransferCache;

    public OfferNameElementDescription createOfferNameElementDescription(OfferNameElement offerNameElement, Language language,
            String description, BasePK createdBy) {
        var offerNameElementDescription = offerNameElementDescriptionFactory.create(
                offerNameElement, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(offerNameElement.getPrimaryKey(), EventTypes.MODIFY,
                offerNameElementDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return offerNameElementDescription;
    }
    
    private OfferNameElementDescription getOfferNameElementDescription(OfferNameElement offerNameElement, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferNameElementDescriptions.fields())
                .from(OfferNameElementDescriptions)
                .where(OfferNameElementDescriptions.OFFER_NAME_ELEMENT.eq(offerNameElement.getPrimaryKey()),
                        OfferNameElementDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        OfferNameElementDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerNameElementDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferNameElementDescriptions.fields())
                    .from(OfferNameElementDescriptions)
                    .join(Languages).onKey(OFFER_NAME_ELEMENT_DESCRIPTIONS_LANGUAGE_FK)
                    .where(OfferNameElementDescriptions.OFFER_NAME_ELEMENT.eq(offerNameElement.getPrimaryKey()),
                            OfferNameElementDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    OfferNameElementDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OfferNameElementDescriptions.fields())
                    .from(OfferNameElementDescriptions)
                    .where(OfferNameElementDescriptions.OFFER_NAME_ELEMENT.eq(offerNameElement.getPrimaryKey()),
                            OfferNameElementDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return offerNameElementDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return offerNameElementDescriptionTransferCache.getOfferNameElementDescriptionTransfer(userVisit, offerNameElementDescription);
    }
    
    public List<OfferNameElementDescriptionTransfer> getOfferNameElementDescriptionTransfersByOfferNameElement(UserVisit userVisit, OfferNameElement offerNameElement) {
        var offerNameElementDescriptions = getOfferNameElementDescriptionsByOfferNameElement(offerNameElement);
        List<OfferNameElementDescriptionTransfer> offerNameElementDescriptionTransfers = new ArrayList<>(offerNameElementDescriptions.size());
        
        offerNameElementDescriptions.forEach((offerNameElementDescription) ->
                offerNameElementDescriptionTransfers.add(offerNameElementDescriptionTransferCache.getOfferNameElementDescriptionTransfer(userVisit, offerNameElementDescription))
        );
        
        return offerNameElementDescriptionTransfers;
    }
    
    public void updateOfferNameElementDescriptionFromValue(OfferNameElementDescriptionValue offerNameElementDescriptionValue, BasePK updatedBy) {
        if(offerNameElementDescriptionValue.hasBeenModified()) {
            var offerNameElementDescription = offerNameElementDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     offerNameElementDescriptionValue.getPrimaryKey());
            
            offerNameElementDescription.setThruTime(session.getStartTime());
            offerNameElementDescription.store();

            var offerNameElement = offerNameElementDescription.getOfferNameElement();
            var language = offerNameElementDescription.getLanguage();
            var description = offerNameElementDescriptionValue.getDescription();
            
            offerNameElementDescription = offerNameElementDescriptionFactory.create(offerNameElement,
                    language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(offerNameElement.getPrimaryKey(), EventTypes.MODIFY,
                    offerNameElementDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteOfferNameElementDescription(OfferNameElementDescription offerNameElementDescription, BasePK deletedBy) {
        offerNameElementDescription.setThruTime(session.getStartTime());
        
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
