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

package com.echothree.model.control.content.server.eventbus;

import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.core.server.eventbus.BaseEventSubscriber;
import com.echothree.model.control.core.server.eventbus.Function5Arity;
import com.echothree.model.control.core.server.eventbus.SentEvent;
import com.echothree.model.control.core.server.eventbus.SentEventSubscriber;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.content.common.ContentCatalogConstants;
import com.echothree.model.data.content.common.ContentCategoryConstants;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.inventory.common.InventoryConditionConstants;
import com.echothree.model.data.item.common.ItemConstants;
import com.echothree.model.data.uom.common.UnitOfMeasureTypeConstants;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import com.google.common.eventbus.Subscribe;

@SentEventSubscriber
public class ContentCatalogItemModificationSubscriber
        extends BaseEventSubscriber {

    @Subscribe
    public void receiveSentEventForContentCategories(SentEvent se) {
        decodeEventAndApply(se, touchContentCatalogItemsIfContentCategory);
    }

    private static final Function5Arity<Event, EntityInstance, EventTypes, String, String>
            touchContentCatalogItemsIfContentCategory = (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
        if(ContentCategoryConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && ContentCategoryConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
            var eventControl = Session.getModelController(EventControl.class);
            var contentControl = Session.getModelController(ContentControl.class);
            var contentCategory = contentControl.getContentCategoryByEntityInstance(entityInstance);
            var contentCategoryItems = contentControl.getContentCategoryItemsByContentCategory(contentCategory);

            for(var contentCategoryItem : contentCategoryItems) {
                var contentCatalogItem = contentCategoryItem.getContentCatalogItem();

                eventControl.sendEvent(contentCatalogItem.getPrimaryKey(), EventTypes.TOUCH,
                        contentCategory.getPrimaryKey(), eventType,
                        PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy()));
            }
        }
    };

    @Subscribe
    public void receiveSentEventForContentCatalogs(SentEvent se) {
        decodeEventAndApply(se, touchContentCatalogItemsIfContentCatalog);
    }

    private static final Function5Arity<Event, EntityInstance, EventTypes, String, String>
            touchContentCatalogItemsIfContentCatalog = (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
        if(ContentCatalogConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && ContentCatalogConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
            var eventControl = Session.getModelController(EventControl.class);
            var contentControl = Session.getModelController(ContentControl.class);
            var contentCatalog = contentControl.getContentCatalogByEntityInstance(entityInstance);
            var contentCatalogItems = contentControl.getContentCatalogItemsByContentCatalog(contentCatalog);

            for(var contentCatalogItem : contentCatalogItems) {
                eventControl.sendEvent(contentCatalogItem.getPrimaryKey(), EventTypes.TOUCH,
                        contentCatalog.getPrimaryKey(), eventType,
                        PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy()));
            }
        }
    };

    @Subscribe
    public void receiveSentEventForItems(SentEvent se) {
        decodeEventAndApply(se, touchContentCatalogItemsIfItem);
    }

    private static final Function5Arity<Event, EntityInstance, EventTypes, String, String>
            touchContentCatalogItemsIfItem = (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
        if(ItemConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && ItemConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
            var eventControl = Session.getModelController(EventControl.class);
            var itemControl = Session.getModelController(ItemControl.class);
            var contentControl = Session.getModelController(ContentControl.class);
            var item = itemControl.getItemByEntityInstance(entityInstance);
            var contentCatalogItems = contentControl.getContentCatalogItemsByItem(item);

            for(var contentCatalogItem : contentCatalogItems) {
                eventControl.sendEvent(contentCatalogItem.getPrimaryKey(), EventTypes.TOUCH,
                        item.getPrimaryKey(), eventType,
                        PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy()));
            }
        }
    };

    @Subscribe
    public void receiveSentEventForInventoryConditions(SentEvent se) {
        decodeEventAndApply(se, touchContentCatalogInventoryConditionsIfInventoryCondition);
    }

    private static final Function5Arity<Event, EntityInstance, EventTypes, String, String>
            touchContentCatalogInventoryConditionsIfInventoryCondition = (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
        if(InventoryConditionConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && InventoryConditionConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
            var eventControl = Session.getModelController(EventControl.class);
            var inventoryControl = Session.getModelController(InventoryControl.class);
            var contentControl = Session.getModelController(ContentControl.class);
            var inventoryCondition = inventoryControl.getInventoryConditionByEntityInstance(entityInstance);
            var contentCatalogItems = contentControl.getContentCatalogItemsByInventoryCondition(inventoryCondition);

            for(var contentCatalogItem : contentCatalogItems) {
                eventControl.sendEvent(contentCatalogItem.getPrimaryKey(), EventTypes.TOUCH,
                        inventoryCondition.getPrimaryKey(), eventType,
                        PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy()));
            }
        }
    };

    @Subscribe
    public void receiveSentEventForUnitOfMeasureTypes(SentEvent se) {
        decodeEventAndApply(se, touchContentCatalogUnitOfMeasureTypesIfUnitOfMeasureType);
    }

    private static final Function5Arity<Event, EntityInstance, EventTypes, String, String>
            touchContentCatalogUnitOfMeasureTypesIfUnitOfMeasureType = (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
        if(UnitOfMeasureTypeConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && UnitOfMeasureTypeConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
            var eventControl = Session.getModelController(EventControl.class);
            var uomControl = Session.getModelController(UomControl.class);
            var contentControl = Session.getModelController(ContentControl.class);
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByEntityInstance(entityInstance);
            var contentCatalogItems = contentControl.getContentCatalogItemsByUnitOfMeasureType(unitOfMeasureType);

            for(var contentCatalogItem : contentCatalogItems) {
                eventControl.sendEvent(contentCatalogItem.getPrimaryKey(), EventTypes.TOUCH,
                        unitOfMeasureType.getPrimaryKey(), eventType,
                        PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy()));
            }
        }
    };

}
