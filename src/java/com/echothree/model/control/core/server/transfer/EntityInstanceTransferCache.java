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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.communication.server.CommunicationControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.transfer.ComponentVendorTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.control.item.common.ItemConstants;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.communication.server.entity.CommunicationEvent;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.entity.ForumMessagePart;
import com.echothree.model.data.forum.server.entity.ForumMessagePartType;
import com.echothree.model.data.forum.server.entity.ForumStringMessagePart;
import com.echothree.model.data.forum.server.entity.ForumThread;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionDetail;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.model.data.vendor.server.entity.VendorItemDetail;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.EntityNamesUtils;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.translator.EntityInstanceAndNames;
import java.util.List;
import java.util.Set;

public class EntityInstanceTransferCache
        extends BaseCoreTransferCache<EntityInstance, EntityInstanceTransfer> {
    
    CommunicationControl communicationControl = (CommunicationControl)Session.getModelController(CommunicationControl.class);
    ForumControl forumControl = (ForumControl)Session.getModelController(ForumControl.class);
    ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
    VendorControl vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
    
    boolean includeEntityAppearance;
    boolean includeNames;
    boolean includeKeyIfAvailable;
    boolean includeGuidIfAvailable;
    boolean includeUlidIfAvailable;
    
    TransferProperties transferProperties;
    boolean filterEntityType;
    boolean filterEntityUniqueId;
    boolean filterEntityRef;
    boolean filterEntityTime;
    boolean filterDescription;
    
    ItemDescriptionType itemDefaultDescriptionType;
    ItemDescriptionType itemPurchaseOrderDescriptionType;

    /** Creates a new instance of EntityInstanceTransferCache */
    public EntityInstanceTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeEntityAppearance = options.contains(CoreOptions.EntityInstanceIncludeEntityAppearance);
            includeNames = options.contains(CoreOptions.EntityInstanceIncludeNames);
            includeKeyIfAvailable = options.contains(CoreOptions.EntityInstanceIncludeKeyIfAvailable);
            includeGuidIfAvailable = options.contains(CoreOptions.EntityInstanceIncludeGuidIfAvailable);
            includeUlidIfAvailable = options.contains(CoreOptions.EntityInstanceIncludeUlidIfAvailable);
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(EntityInstanceTransfer.class);
            
            if(properties != null) {
                filterEntityType = !properties.contains(CoreProperties.ENTITY_TYPE);
                filterEntityUniqueId = !properties.contains(CoreProperties.ENTITY_UNIQUE_ID);
                filterEntityRef = !properties.contains(CoreProperties.ENTITY_REF);
                filterEntityTime = !properties.contains(CoreProperties.ENTITY_TIME);
                filterDescription = !properties.contains(CoreProperties.DESCRIPTION);
            }
        }
    }

    private ItemDescriptionType getItemDefaultDescriptionType() {
        if(itemDefaultDescriptionType == null) {
            itemDefaultDescriptionType = itemControl.getItemDescriptionTypeByName(ItemConstants.ItemDescriptionType_DEFAULT_DESCRIPTION);
        }
        
        return itemDefaultDescriptionType;
    }

    private ItemDescriptionType getItemPurchaseOrderDescriptionType() {
        if(itemPurchaseOrderDescriptionType == null) {
            itemPurchaseOrderDescriptionType = itemControl.getItemDescriptionTypeByName(ItemConstants.ItemDescriptionType_PURCHASE_ORDER_DESCRIPTION);
        }
        
        return itemPurchaseOrderDescriptionType;
    }

    private String getDescriptionForForumMessage(ForumMessage forumMessage) {
        String description = null;

        ForumMessagePartType forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_TITLE);
        ForumMessagePart forumMessagePart = forumControl.getBestForumMessagePart(forumMessage, forumMessagePartType, getLanguage());

        if(forumMessagePart != null) {
            ForumStringMessagePart forumStringMessagePart = forumControl.getForumStringMessagePart(forumMessagePart);

            description = forumStringMessagePart.getString();
        }

        return description;
    }
    
    private String getDescription(String componentVendorName, String entityTypeName, EntityInstance entityInstance) {
        String description = null;
        
        if(componentVendorName.equals(ComponentVendors.ECHOTHREE.name())) {
            if(entityTypeName.equals(EntityTypes.Party.name())) {
                Party party = partyControl.getPartyByEntityInstance(entityInstance);
                
                description = party == null ? null : partyControl.getBestPartyDescription(party, getLanguage());
            } else if(entityTypeName.equals(EntityTypes.CommunicationEvent.name())) {
                CommunicationEvent communicationEvent = communicationControl.getCommunicationEventByEntityInstance(entityInstance);
                
                description = communicationEvent == null ? null : communicationEvent.getLastDetail().getCommunicationEventName();
            } else if(entityTypeName.equals(EntityTypes.TrainingClass.name())) {
                TrainingClass trainingClass = trainingControl.getTrainingClassByEntityInstance(entityInstance);
                
                if(trainingClass != null) {
                    TrainingClassTranslation trainingClassTranslation = trainingControl.getBestTrainingClassTranslation(trainingClass, getLanguage());

                    description = trainingClassTranslation == null ? null : trainingClassTranslation.getDescription();
                }
            } else if(entityTypeName.equals(EntityTypes.PartyTrainingClass.name())) {
                PartyTrainingClass partyTrainingClass = trainingControl.getPartyTrainingClassByEntityInstance(entityInstance);
                
                description = partyTrainingClass == null ? null : partyTrainingClass.getLastDetail().getPartyTrainingClassName();
            } else if(entityTypeName.equals(EntityTypes.Item.name())) {
                Item item = itemControl.getItemByEntityInstance(entityInstance);
                
                description = item == null ? null : itemControl.getBestItemStringDescription(getItemDefaultDescriptionType(), item, getLanguage());
            } else if(entityTypeName.equals(EntityTypes.ItemDescription.name())) {
                ItemDescription itemDescription = itemControl.getItemDescriptionByEntityInstance(entityInstance);
                
                if(itemDescription != null) {
                    ItemDescriptionDetail itemDescriptionDetail = itemDescription.getLastDetail();
                    Language language = getLanguage();

                    description = new StringBuilder(itemControl.getBestItemDescriptionTypeDescription(itemDescriptionDetail.getItemDescriptionType(), language))
                            .append(", ").append(itemControl.getBestItemStringDescription(getItemDefaultDescriptionType(), itemDescriptionDetail.getItem(), language))
                            .append(", ").append(partyControl.getBestLanguageDescription(itemDescriptionDetail.getLanguage(), language)).toString();
                }
            } else if(entityTypeName.equals(EntityTypes.VendorItem.name())) {
                VendorItem vendorItem = vendorControl.getVendorItemByEntityInstance(entityInstance);
                
                if(vendorItem != null) {
                    VendorItemDetail vendorItemDetail = vendorItem.getLastDetail();
                    
                    description = vendorItemDetail.getDescription();
                    
                    if(description == null) {
                        description = itemControl.getBestItemStringDescription(getItemPurchaseOrderDescriptionType(), vendorItemDetail.getItem(), getLanguage());
                    }
                }
            } else if(entityTypeName.equals(EntityTypes.ForumGroup.name())) {
                ForumGroup forumGroup = forumControl.getForumGroupByEntityInstance(entityInstance);
                
                description = forumGroup == null ? null : forumControl.getBestForumGroupDescription(forumGroup, getLanguage());
            } else if(entityTypeName.equals(EntityTypes.Forum.name())) {
                Forum forum = forumControl.getForumByEntityInstance(entityInstance);
                
                description = forum == null ? null : forumControl.getBestForumDescription(forum, getLanguage());
            } else if(entityTypeName.equals(EntityTypes.ForumThread.name())) {
                // TODO: A method is needed to push the current limit on ForumMessages, and then pop it. A quick implemention of that
                // proved difficult due to the limitCache in the Session. Its key is the Class for the BaseFactory vs. a String used by
                // the limits Map.
                ForumThread forumThread = forumControl.getForumThreadByEntityInstance(entityInstance);
                
                if(forumThread != null) {
                    List<ForumMessage> forumMessages = forumControl.getForumMessagesByForumThread(forumThread);

                    if(forumMessages.size() > 0) {
                        description = getDescriptionForForumMessage(forumMessages.iterator().next());
                    }
                }
            } else if(entityTypeName.equals(EntityTypes.ForumMessage.name())) {
                ForumMessage forumMessage = forumControl.getForumMessageByEntityInstance(entityInstance);
                
                description = forumMessage == null ? null : getDescriptionForForumMessage(forumMessage);
            } else if(entityTypeName.equals(EntityTypes.MimeType.name())) {
                MimeType mimeType = coreControl.getMimeTypeByEntityInstance(entityInstance);
                
                description = mimeType == null ? null : coreControl.getBestMimeTypeDescription(mimeType, getLanguage());
            }
        }
        
        return description;
    }
    
    public EntityInstanceTransfer getEntityInstanceTransfer(EntityInstance entityInstance, boolean includeEntityAppearance, boolean includeNames,
            boolean includeKey, boolean includeGuid, boolean includeUlid) {
        EntityInstanceTransfer entityInstanceTransfer = get(entityInstance);
        
        if(entityInstanceTransfer == null) {
            EntityTypeTransfer entityTypeTransfer = filterEntityType ? null : coreControl.getEntityTypeTransfer(userVisit, entityInstance.getEntityType());
            Long entityUniqueId = filterEntityUniqueId ? null : entityInstance.getEntityUniqueId();
            String key = null;
            String guid = null;
            String ulid = null;
            ComponentVendorTransfer componentVendorTransfer = entityTypeTransfer == null ? null : entityTypeTransfer.getComponentVendor();
            String componentVendorName = componentVendorTransfer == null ? null : componentVendorTransfer.getComponentVendorName();
            String entityTypeName = entityTypeTransfer == null ? null : entityTypeTransfer.getEntityTypeName();
            String entityRef = filterEntityRef || componentVendorName == null || entityTypeName == null || entityUniqueId == null ? null : new StringBuilder(componentVendorName).append('.').append(entityTypeName).append('.').append(entityUniqueId).toString();
            EntityTime entityTime = filterEntityTime ? null : coreControl.getEntityTime(entityInstance);
            EntityTimeTransfer entityTimeTransfer = entityTime == null ? null : coreControl.getEntityTimeTransfer(userVisit, entityTime);
            String description = null;
            
            if(includeKey || includeKeyIfAvailable) {
                key = entityInstance.getKey();
                
                if(includeKey && key == null) {
                    entityInstance = coreControl.ensureKeyForEntityInstance(entityInstance, false);
                    key = entityInstance.getKey();
                }
            }
            
            if(includeGuid || includeGuidIfAvailable) {
                guid = entityInstance.getGuid();
                
                if(includeGuid && guid == null) {
                    entityInstance = coreControl.ensureGuidForEntityInstance(entityInstance, false);
                    guid = entityInstance.getGuid();
                }
            }
            
            if(includeUlid || includeUlidIfAvailable) {
                ulid = entityInstance.getUlid();
                
                if(includeUlid && ulid == null) {
                    entityInstance = coreControl.ensureUlidForEntityInstance(entityInstance, false);
                    ulid = entityInstance.getUlid();
                }
            }
            
            if(!filterDescription && componentVendorName != null && entityTypeName != null) {
                description = getDescription(componentVendorName, entityTypeName, entityInstance);
            }
            
            entityInstanceTransfer = new EntityInstanceTransfer(entityTypeTransfer, entityUniqueId, key, guid, ulid, entityRef,
                    entityTimeTransfer, description);
            put(entityInstance, entityInstanceTransfer);
            
            if(includeEntityAppearance || this.includeEntityAppearance) {
                EntityAppearance entityAppearance = coreControl.getEntityAppearance(entityInstance);
                
                entityInstanceTransfer.setEntityAppearance(entityAppearance == null ? null : coreControl.getEntityAppearanceTransfer(userVisit, entityAppearance));
            }
            
            if(includeNames || this.includeNames) {
                EntityInstanceAndNames entityNamesMapping = EntityNamesUtils.getInstance().getEntityNames(entityInstance);
                
                entityInstanceTransfer.setEntityNames(entityNamesMapping == null ? null : entityNamesMapping.getEntityNames());
            }
        }
        
        return entityInstanceTransfer;
    }

}
