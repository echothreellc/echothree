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

package com.echothree.util.server.persistence;

import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.control.ComponentControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.item.common.ItemDescriptionTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;

public class EntityDescriptionUtils {
    
    private EntityDescriptionUtils() {
        super();
    }
    
    private static class EntityDescriptionUtilsHolder {
        static EntityDescriptionUtils instance = new EntityDescriptionUtils();
    }
    
    public static EntityDescriptionUtils getInstance() {
        return EntityDescriptionUtilsHolder.instance;
    }

    private ItemDescriptionType getItemDefaultDescriptionType() {
        var itemControl = Session.getModelController(ItemControl.class);

        // TODO: Context Cache
        return itemControl.getItemDescriptionTypeByName(ItemDescriptionTypes.DEFAULT_DESCRIPTION.name());
    }

    private ItemDescriptionType getItemPurchaseOrderDescriptionType() {
        var itemControl = Session.getModelController(ItemControl.class);

        // TODO: Context Cache
        return itemControl.getItemDescriptionTypeByName(ItemDescriptionTypes.PURCHASE_ORDER_DESCRIPTION.name());
    }

    private String getDescriptionForForumMessage(UserVisit userVisit, ForumMessage forumMessage) {
        var forumControl = Session.getModelController(ForumControl.class);
        String description = null;

        var forumMessagePartType = forumControl.getForumMessagePartTypeByName(ForumConstants.ForumMessagePartType_TITLE);
        var forumMessagePart = forumControl.getBestForumMessagePart(forumMessage, forumMessagePartType, getLanguage(userVisit));

        if(forumMessagePart != null) {
            var forumStringMessagePart = forumControl.getForumStringMessagePart(forumMessagePart);

            description = forumStringMessagePart.getString();
        }

        return description;
    }

    private Language getLanguage(UserVisit userVisit) {
        var userControl = Session.getModelController(UserControl.class);

        // TODO: Context Cache
        return userControl.getPreferredLanguageFromUserVisit(userVisit);
    }

    public String getDescription(UserVisit userVisit, EntityInstance entityInstance) {
        String description = null;

        if(entityInstance != null) {
            var entityTypeDetail = entityInstance.getEntityType().getLastDetail();
            var componentVendorName = entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName();

            if(componentVendorName.equals(ComponentVendors.ECHO_THREE.name())) {
                var entityTypeName = entityTypeDetail.getEntityTypeName();

                if(entityTypeName.equals(EntityTypes.Party.name())) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var party = partyControl.getPartyByEntityInstance(entityInstance);

                    description = party == null ? null : partyControl.getBestPartyDescription(party, getLanguage(userVisit));
                } else if(entityTypeName.equals(EntityTypes.CommunicationEvent.name())) {
                    var communicationControl = Session.getModelController(CommunicationControl.class);
                    var communicationEvent = communicationControl.getCommunicationEventByEntityInstance(entityInstance);

                    description = communicationEvent == null ? null : communicationEvent.getLastDetail().getCommunicationEventName();
                } else if(entityTypeName.equals(EntityTypes.TrainingClass.name())) {
                    var trainingControl = Session.getModelController(TrainingControl.class);
                    var trainingClass = trainingControl.getTrainingClassByEntityInstance(entityInstance);

                    if(trainingClass != null) {
                        var trainingClassTranslation = trainingControl.getBestTrainingClassTranslation(trainingClass, getLanguage(userVisit));

                        description = trainingClassTranslation == null ? null : trainingClassTranslation.getDescription();
                    }
                } else if(entityTypeName.equals(EntityTypes.PartyTrainingClass.name())) {
                    var trainingControl = Session.getModelController(TrainingControl.class);
                    var partyTrainingClass = trainingControl.getPartyTrainingClassByEntityInstance(entityInstance);

                    description = partyTrainingClass == null ? null : partyTrainingClass.getLastDetail().getPartyTrainingClassName();
                } else if(entityTypeName.equals(EntityTypes.Item.name())) {
                    var itemControl = Session.getModelController(ItemControl.class);
                    var item = itemControl.getItemByEntityInstance(entityInstance);

                    description = item == null ? null : itemControl.getBestItemStringDescription(getItemDefaultDescriptionType(), item, getLanguage(userVisit));
                } else if(entityTypeName.equals(EntityTypes.ItemDescription.name())) {
                    var itemControl = Session.getModelController(ItemControl.class);
                    var itemDescription = itemControl.getItemDescriptionByEntityInstance(entityInstance);

                    if(itemDescription != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        var itemDescriptionDetail = itemDescription.getLastDetail();
                        var language = getLanguage(userVisit);

                        description = itemControl.getBestItemDescriptionTypeDescription(itemDescriptionDetail.getItemDescriptionType(), language) + ", " + itemControl.getBestItemStringDescription(getItemDefaultDescriptionType(), itemDescriptionDetail.getItem(), language) + ", " + partyControl.getBestLanguageDescription(itemDescriptionDetail.getLanguage(), language);
                    }
                } else if(entityTypeName.equals(EntityTypes.VendorItem.name())) {
                    var vendorControl = Session.getModelController(VendorControl.class);
                    var vendorItem = vendorControl.getVendorItemByEntityInstance(entityInstance);

                    if(vendorItem != null) {
                        var vendorItemDetail = vendorItem.getLastDetail();

                        description = vendorItemDetail.getDescription();

                        if(description == null) {
                            var itemControl = Session.getModelController(ItemControl.class);
                            description = itemControl.getBestItemStringDescription(getItemPurchaseOrderDescriptionType(), vendorItemDetail.getItem(), getLanguage(userVisit));
                        }
                    }
                } else if(entityTypeName.equals(EntityTypes.ForumGroup.name())) {
                    var forumControl = Session.getModelController(ForumControl.class);
                    var forumGroup = forumControl.getForumGroupByEntityInstance(entityInstance);

                    description = forumGroup == null ? null : forumControl.getBestForumGroupDescription(forumGroup, getLanguage(userVisit));
                } else if(entityTypeName.equals(EntityTypes.Forum.name())) {
                    var forumControl = Session.getModelController(ForumControl.class);
                    var forum = forumControl.getForumByEntityInstance(entityInstance);

                    description = forum == null ? null : forumControl.getBestForumDescription(forum, getLanguage(userVisit));
                } else if(entityTypeName.equals(EntityTypes.ForumThread.name())) {
                    // TODO: A method is needed to push the current limit on ForumMessages, and then pop it. A quick implementation of that
                    // proved difficult due to the limitCache in the Session. Its key is the Class for the BaseFactory vs. a String used by
                    // the limits Map.
                    var forumControl = Session.getModelController(ForumControl.class);
                    var forumThread = forumControl.getForumThreadByEntityInstance(entityInstance);

                    if(forumThread != null) {
                        var forumMessages = forumControl.getForumMessagesByForumThread(forumThread);

                        if(!forumMessages.isEmpty()) {
                            description = getDescriptionForForumMessage(userVisit, forumMessages.getFirst());
                        }
                    }
                } else if(entityTypeName.equals(EntityTypes.ForumMessage.name())) {
                    var forumControl = Session.getModelController(ForumControl.class);
                    var forumMessage = forumControl.getForumMessageByEntityInstance(entityInstance);

                    description = forumMessage == null ? null : getDescriptionForForumMessage(userVisit, forumMessage);
                } else if(entityTypeName.equals(EntityTypes.MimeType.name())) {
                    var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
                    var mimeType = mimeTypeControl.getMimeTypeByEntityInstance(entityInstance);

                    description = mimeType == null ? null : mimeTypeControl.getBestMimeTypeDescription(mimeType, getLanguage(userVisit));
                } else if(entityTypeName.equals(EntityTypes.Location.name())) {
                    var warehouseControl = Session.getModelController(WarehouseControl.class);
                    var location = warehouseControl.getLocationByEntityInstance(entityInstance);

                    description = location == null ? null : warehouseControl.getBestLocationDescription(location, getLanguage(userVisit));
                } else if(entityTypeName.equals(EntityTypes.ComponentVendor.name())) {
                    var componentControl = Session.getModelController(ComponentControl.class);
                    var componentVendor = componentControl.getComponentVendorByEntityInstance(entityInstance);

                    description = componentVendor == null ? null : componentVendor.getLastDetail().getDescription();
                } else if(entityTypeName.equals(EntityTypes.EntityType.name())) {
                    var entityTypeControl = Session.getModelController(EntityTypeControl.class);
                    var entityType = entityTypeControl.getEntityTypeByEntityInstance(entityInstance);

                    description = entityType == null ? null : entityTypeControl.getBestEntityTypeDescription(entityType, getLanguage(userVisit));
                } else if(entityTypeName.equals(EntityTypes.EntityAttribute.name())) {
                    var coreControl = Session.getModelController(CoreControl.class);
                    var entityAttribute = coreControl.getEntityAttributeByEntityInstance(entityInstance);

                    description = entityAttribute == null ? null : coreControl.getBestEntityAttributeDescription(entityAttribute, getLanguage(userVisit));
                } else if(entityTypeName.equals(EntityTypes.EntityListItem.name())) {
                    var coreControl = Session.getModelController(CoreControl.class);
                    var entityListItem = coreControl.getEntityListItemByEntityInstance(entityInstance);

                    description = entityListItem == null ? null : coreControl.getBestEntityListItemDescription(entityListItem, getLanguage(userVisit));
                }
            }
        }

        return description;
    }

}
