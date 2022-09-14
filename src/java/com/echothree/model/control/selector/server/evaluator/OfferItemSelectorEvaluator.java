// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.selector.server.evaluator;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.filter.server.evaluator.FilteredItemFixedPrice;
import com.echothree.model.control.filter.server.evaluator.OfferItemPriceFilterEvaluator;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.offer.server.logic.OfferItemLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemFixedPrice;
import com.echothree.model.data.item.server.entity.ItemPrice;
import com.echothree.model.data.item.server.entity.ItemPriceType;
import com.echothree.model.data.item.server.entity.ItemVariablePrice;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemFixedPrice;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferItemVariablePrice;
import com.echothree.model.data.offer.server.value.OfferItemFixedPriceValue;
import com.echothree.model.data.offer.server.value.OfferItemVariablePriceValue;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;
import java.util.HashSet;
import java.util.List;

public class OfferItemSelectorEvaluator
        extends BaseItemSelectorEvaluator {

    protected OfferControl offerControl = Session.getModelController(OfferControl.class);
    protected OfferItemControl offerItemControl = Session.getModelController(OfferItemControl.class);
    protected OfferItemPriceFilterEvaluator offerItemPriceFilterEvaluator;
    
    ItemPriceType fixedItemPriceType;
    ItemPriceType variableItemPriceType;
    
    /** Creates a new instance of OfferItemSelectorEvaluator */
    public OfferItemSelectorEvaluator(Session session, BasePK evaluatedBy) {
        super(session, evaluatedBy, OfferItemSelectorEvaluator.class);
        
        offerItemPriceFilterEvaluator = new OfferItemPriceFilterEvaluator(session, evaluatedBy);
        
        fixedItemPriceType = itemControl.getItemPriceTypeByName(ItemPriceTypes.FIXED.name());
        variableItemPriceType = itemControl.getItemPriceTypeByName(ItemPriceTypes.VARIABLE.name());
    }
    
    
    OfferItemPrice createOfferItemPrice(Offer offer, OfferItem offerItem, ItemPriceType itemPriceType, ItemPrice itemPrice) {
        InventoryCondition inventoryCondition = itemPrice.getInventoryCondition();
        UnitOfMeasureType unitOfMeasureType = itemPrice.getUnitOfMeasureType();
        Currency currency = itemPrice.getCurrency();
        OfferItemPrice offerItemPrice = OfferItemLogic.getInstance().createOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency, evaluatedBy);
        
        if(itemPriceType.equals(fixedItemPriceType)) {
            Item item = offerItem.getItem();
            ItemFixedPrice itemFixedPrice = itemControl.getItemFixedPrice(itemPrice);
            Filter filter = offer.getLastDetail().getOfferItemPriceFilter();
            FilteredItemFixedPrice filteredItemFixedPrice = offerItemPriceFilterEvaluator.evaluate(item, itemPrice, itemFixedPrice,
                    filter);

            OfferItemLogic.getInstance().createOfferItemFixedPrice(offerItemPrice, filteredItemFixedPrice.getUnitPrice(), evaluatedBy);
        } else if(itemPriceType.equals(variableItemPriceType)) {
            ItemVariablePrice itemVariablePrice = itemControl.getItemVariablePrice(itemPrice);
            Long minimumUnitPrice = itemVariablePrice.getMinimumUnitPrice();
            Long maximumUnitPrice = itemVariablePrice.getMaximumUnitPrice();
            Long unitPriceIncrement = itemVariablePrice.getUnitPriceIncrement();

            OfferItemLogic.getInstance().createOfferItemVariablePrice(offerItemPrice, minimumUnitPrice, maximumUnitPrice, unitPriceIncrement, evaluatedBy);
        } else
            throw new IllegalArgumentException();
        
        return offerItemPrice;
    }
    
    void updateOfferItemPrice(Offer offer, OfferItem offerItem, OfferItemPrice offerItemPrice, ItemPriceType itemPriceType,
            ItemPrice itemPrice) {
        if(itemPriceType.equals(fixedItemPriceType)) {
            Item item = offerItem.getItem();
            OfferItemFixedPrice offerItemFixedPrice = offerItemControl.getOfferItemFixedPrice(offerItemPrice);
            ItemFixedPrice itemFixedPrice = itemControl.getItemFixedPrice(itemPrice);
            Filter filter = offer.getLastDetail().getOfferItemPriceFilter();
            
            FilteredItemFixedPrice filteredItemFixedPrice = offerItemPriceFilterEvaluator.evaluate(item, itemPrice, itemFixedPrice,
                    filter);
            Long unitPrice = filteredItemFixedPrice.getUnitPrice();
            
            if(!offerItemFixedPrice.getUnitPrice().equals(unitPrice)) {
                OfferItemFixedPriceValue offerItemFixedPriceValue = offerItemControl.getOfferItemFixedPriceValueForUpdate(offerItemPrice);

                offerItemFixedPriceValue.setUnitPrice(unitPrice);

                OfferItemLogic.getInstance().updateOfferItemFixedPriceFromValue(offerItemFixedPriceValue, evaluatedBy);
            }
        } else if(itemPriceType.equals(variableItemPriceType)) {
            OfferItemVariablePrice offerItemVariablePrice = offerItemControl.getOfferItemVariablePrice(offerItemPrice);
            ItemVariablePrice itemVariablePrice = itemControl.getItemVariablePrice(itemPrice);
            Long minimumUnitPrice = itemVariablePrice.getMinimumUnitPrice();
            Long maximumUnitPrice = itemVariablePrice.getMaximumUnitPrice();
            Long unitPriceIncrement = itemVariablePrice.getUnitPriceIncrement();
            
            if(!offerItemVariablePrice.getMinimumUnitPrice().equals(minimumUnitPrice)
            || !offerItemVariablePrice.getMaximumUnitPrice().equals(maximumUnitPrice)
            || !offerItemVariablePrice.getUnitPriceIncrement().equals(unitPriceIncrement)) {
                OfferItemVariablePriceValue offerItemVariablePriceValue = offerItemControl.getOfferItemVariablePriceValueForUpdate(offerItemPrice);

                offerItemVariablePriceValue.setMinimumUnitPrice(minimumUnitPrice);
                offerItemVariablePriceValue.setMaximumUnitPrice(maximumUnitPrice);
                offerItemVariablePriceValue.setUnitPriceIncrement(unitPriceIncrement);

                OfferItemLogic.getInstance().updateOfferItemVariablePriceFromValue(offerItemVariablePriceValue, evaluatedBy);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    /** Used when adding an item to an offer, and also when an item already in an offer is modified. Despite
     * the name, this is really "createOrUpdateItemInOffers."
     */
    void createItemInOffers(List<Offer> offers, Item item) {
        if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
            log.info("--- OfferItemSelectorEvaluator.createItemInOffers(offers = " + offers + ", item = " + item + ")");
        
        offers.forEach((offer) -> {
            OfferItem offerItem = offerItemControl.getOfferItem(offer, item);
            ItemPriceType itemPriceType = item.getLastDetail().getItemPriceType();
            List<ItemPrice> itemPrices = itemControl.getItemPricesByItem(item);
            if (offerItem == null) {
                // New item in this offer, create it, don't worry about sync'ing prices.
                offerItem = OfferItemLogic.getInstance().createOfferItem(null, offer, item, evaluatedBy);
                
                for(var itemPrice : itemPrices) {
                    createOfferItemPrice(offer, offerItem, itemPriceType, itemPrice);
                }
            } else {
                HashSet<OfferItemPrice> offerItemPrices = new HashSet<>(offerItemControl.getOfferItemPricesByOfferItem(offerItem));
                for(var itemPrice : itemPrices) {
                    InventoryCondition inventoryCondition = itemPrice.getInventoryCondition();
                    UnitOfMeasureType unitOfMeasureType = itemPrice.getUnitOfMeasureType();
                    Currency currency = itemPrice.getCurrency();
                    OfferItemPrice offerItemPrice = offerItemControl.getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType,
                            currency);
                    
                    if(offerItemPrice == null) {
                        offerItemPrice = createOfferItemPrice(offer, offerItem, itemPriceType, itemPrice);
                    } else {
                        updateOfferItemPrice(offer, offerItem, offerItemPrice, itemPriceType, itemPrice);
                        offerItemPrices.remove(offerItemPrice);
                    }
                }
                offerItemPrices.forEach((offerItemPrice) -> {
                    OfferItemLogic.getInstance().deleteOfferItemPrice(offerItemPrice,  evaluatedBy);
                });
            }
        });
    }
    
    void deleteItemFromOffers(List<Offer> offers, Item item) {
        if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
            log.info("--- OfferItemSelectorEvaluator.deleteItemFromOffers(offers = " + offers + ", item = " + item + ")");
        
        offers.stream().map((offer) -> offerItemControl.getOfferItemForUpdate(offer, item)).filter((offerItem) -> (offerItem != null)).forEach((offerItem) -> {
            OfferItemLogic.getInstance().deleteOfferItem(offerItem, evaluatedBy);
        });
    }
    
    Long addItemEntitiesToOffer(List<Offer> offers, CachedSelectorWithTime cachedSelectorWithTime, List<EntityTime> entityTimes, long remainingTime) {
        if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
            log.info(">>> OfferItemSelectorEvaluator.addItemEntitiesToOffer");
        long startTime = System.currentTimeMillis();
        long entityCount = 0;
        Long selectionTime = null;
        
        for(var entityTime : entityTimes) {
            entityCount++;
            if(!(entityCount % 10 == 0)) {
                if((System.currentTimeMillis() - startTime) > remainingTime)
                    break;
            }
            
            EntityInstance entityInstance = entityTime.getEntityInstance();
            Item item = itemControl.getItemByEntityInstance(entityInstance);

            selectionTime = entityTime.getCreatedTime();
            
            if(item != null) {
                if(entityTime.getDeletedTime() == null) {
                    Boolean selected = isItemSelected(cachedSelectorWithTime, entityInstance, item);

                    if(selected) {
                        createItemInOffers(offers, item);
                    }
                }
            }
        }
        
        if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
            log.info("<<< OfferItemSelectorEvaluator.addItemEntitiesToOffer, selectionTime = " + selectionTime);
        return selectionTime;
    }
    
    Long updateItemEntitiesInOffer(List<Offer> offers, CachedSelectorWithTime cachedSelectorWithTime, List<EntityTime> entityTimes, long remainingTime) {
        if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
            log.info(">>> OfferItemSelectorEvaluator.updateItemEntitiesInOffer");
        long startTime = System.currentTimeMillis();
        long entityCount = 0;
        Long selectionTime = null;
        
        for(var entityTime : entityTimes) {
            entityCount++;
            if(!(entityCount % 10 == 0)) {
                if((System.currentTimeMillis() - startTime) > remainingTime)
                    break;
            }
            
            EntityInstance entityInstance = entityTime.getEntityInstance();
            Item item = itemControl.getItemByEntityInstance(entityInstance);

            selectionTime = entityTime.getModifiedTime();

            if(item != null) {
                if(entityTime.getDeletedTime() == null) {
                    Boolean selected = isItemSelected(cachedSelectorWithTime, entityInstance, item);

                    if(selected) {
                        createItemInOffers(offers, item);
                    } else {
                        deleteItemFromOffers(offers, item);
                    }
                }
            }
            
        }
        
        if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
            log.info("<<< OfferItemSelectorEvaluator.updateItemEntitiesInOffer, selectionTime = " + selectionTime);
        return selectionTime;
    }
    
    Long removeItemEntitiesFromOffer(List<Offer> offers, List<EntityTime> entityTimes, long remainingTime) {
        if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
            log.info(">>> OfferItemSelectorEvaluator.removeItemEntitiesFromOffer");
        long startTime = System.currentTimeMillis();
        long entityCount = 0;
        Long selectionTime = null;
        
        for(var entityTime : entityTimes) {
            entityCount++;
            if(!(entityCount % 10 == 0)) {
                if((System.currentTimeMillis() - startTime) > remainingTime)
                    break;
            }
            
            EntityInstance entityInstance = entityTime.getEntityInstance();
            Item item = itemControl.getItemByEntityInstance(entityInstance);

            selectionTime = entityTime.getDeletedTime();
            
            if(item != null) {
                deleteItemFromOffers(offers, item);
            }
        }
        
        if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
            log.info("<<< OfferItemSelectorEvaluator.removeItemEntitiesFromOffer, selectionTime = " + selectionTime);
        return selectionTime;
    }
    
    public Long evaluate(Long maximumTime) {
        if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
            log.info(">>> OfferItemSelectorEvaluator.evaluate");
        
        long remainingTime = maximumTime;
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(ComponentVendors.ECHOTHREE.name());
        
        if(componentVendor != null) {
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, EntityTypes.Item.name());
            
            if(entityType != null) {
                List<Selector> offerItemSelectors = offerControl.getDistinctOfferItemSelectors();
                
                for(var offerItemSelector : offerItemSelectors) {
                    List<Offer> offers = offerControl.getOffersByOfferItemSelector(offerItemSelector);
                    CachedSelectorWithTime cachedSelectorWithTime = new CachedSelectorWithTime(offerItemSelector);
                    
                    if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator) {
                        log.info("--- offerItemSelector = " + offerItemSelector);
                        log.info("--- offers = " + offers);
                    }
                    
                    EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(offerItemSelector.getPrimaryKey());
                    EntityTime entityTime = coreControl.getEntityTime(entityInstance);
                    Long entityCreatedTime = entityTime.getCreatedTime();
                    Long entityModifiedTime = entityTime.getModifiedTime();
                    Long lastModifiedTime = entityModifiedTime != null? entityModifiedTime: entityCreatedTime;
                    Long lastEvaluationTime = cachedSelectorWithTime.getLastEvaluationTime();
                    
                    if(lastEvaluationTime != null) {
                        if(lastModifiedTime > lastEvaluationTime) {
                            if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
                                log.info("--- selector modified since last evaluation");
                            OfferItemLogic.getInstance().deleteOfferItemsByOffers(offers, evaluatedBy);
                            
                            cachedSelectorWithTime.setLastEvaluationTime(null);
                            cachedSelectorWithTime.setMaxEntityCreatedTime(null);
                            cachedSelectorWithTime.setMaxEntityModifiedTime(null);
                            cachedSelectorWithTime.setMaxEntityDeletedTime(null);
                        }
                    }
                    
                    Long selectionTime = cachedSelectorWithTime.getMaxEntityCreatedTime();
                    
                    List<EntityTime> entityTimes;
                    if(selectionTime == null) {
                        entityTimes = coreControl.getEntityTimesByEntityType(entityType);
                    } else {
                        entityTimes = coreControl.getEntityTimesByEntityTypeCreatedAfter(entityType, selectionTime);
                    }
                    
                    if(entityTimes != null) {
                        if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
                            log.info("--- entityTimes.size() = " + entityTimes.size());
                        
                        long indexingStartTime = System.currentTimeMillis();
                        selectionTime = addItemEntitiesToOffer(offers, cachedSelectorWithTime, entityTimes, remainingTime);
                        remainingTime -= System.currentTimeMillis() - indexingStartTime;
                    }
                    
                    if(selectionTime != null) {
                        cachedSelectorWithTime.setMaxEntityCreatedTime(selectionTime);
                    }
                    
                    if(remainingTime > 0) {
                        selectionTime = cachedSelectorWithTime.getMaxEntityModifiedTime();
                        if(selectionTime == null) {
                            selectionTime = Long.valueOf(0);
                        }
                        
                        entityTimes = coreControl.getEntityTimesByEntityTypeModifiedAfter(entityType, selectionTime);
                        
                        if(entityTimes != null) {
                            if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
                                log.info("--- entityTimes.size() = " + entityTimes.size());
                            
                            long indexingStartTime = System.currentTimeMillis();
                            selectionTime = updateItemEntitiesInOffer(offers, cachedSelectorWithTime, entityTimes, remainingTime);
                            remainingTime -= System.currentTimeMillis() - indexingStartTime;
                        }
                        
                        if(selectionTime != null) {
                            cachedSelectorWithTime.setMaxEntityModifiedTime(selectionTime);
                        }
                    }
                    
                    if(remainingTime > 0) {
                        selectionTime = cachedSelectorWithTime.getMaxEntityDeletedTime();
                        if(selectionTime == null) {
                            selectionTime = Long.valueOf(0);
                        }
                        
                        entityTimes = coreControl.getEntityTimesByEntityTypeDeletedAfter(entityType, selectionTime);
                        
                        if(entityTimes != null) {
                            if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
                                log.info("--- entityTimes.size() = " + entityTimes.size());
                            
                            long indexingStartTime = System.currentTimeMillis();
                            selectionTime = removeItemEntitiesFromOffer(offers, entityTimes, remainingTime);
                            remainingTime -= System.currentTimeMillis() - indexingStartTime;
                        }
                        
                        if(selectionTime != null) {
                            cachedSelectorWithTime.setMaxEntityDeletedTime(selectionTime);
                        }
                    }
                    
                    cachedSelectorWithTime.setLastEvaluationTime(session.START_TIME_LONG);
                }
            } // Error, unknown entityTypeName
        } // Error, unknown componentVendorName
        
        if(BaseSelectorEvaluatorDebugFlags.OfferItemSelectorEvaluator)
            log.info("<<< OfferItemSelectorEvaluator.evaluate, remainingTime = " + remainingTime);
        return remainingTime;
    }
    
}
