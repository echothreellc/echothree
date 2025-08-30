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

package com.echothree.model.control.content.server.logic;

import com.echothree.model.control.content.common.exception.DuplicateContentCategoryItemException;
import com.echothree.model.control.content.common.exception.MalformedUrlException;
import com.echothree.model.control.content.common.exception.UnknownContentWebAddressNameException;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.offer.server.logic.OfferItemLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogItem;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import com.echothree.model.data.content.server.value.ContentCategoryItemValue;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferItemVariablePrice;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContentLogic
        extends BaseLogic {

    private ContentLogic() {
        super();
    }

    private static class ContentLogicHolder {
        static ContentLogic instance = new ContentLogic();
    }

    public static ContentLogic getInstance() {
        return ContentLogicHolder.instance;
    }

    public void checkReferrer(final ExecutionErrorAccumulator eea, final String referrer) {
        // A null referrer is considered valid.
        if(referrer != null) {
            try {
                var contentControl = Session.getModelController(ContentControl.class);
                var uri = new URI(referrer);
                var url = uri.toURL();
                var contentWebAddressName = url.getHost();

                if(!contentControl.validContentWebAddressName(contentWebAddressName)) {
                    handleExecutionError(UnknownContentWebAddressNameException.class, eea, ExecutionErrors.UnknownContentWebAddressName.name(), contentWebAddressName);
                }
            } catch(URISyntaxException | MalformedURLException ex) {
                handleExecutionError(MalformedUrlException.class, eea, ExecutionErrors.MalformedUrl.name(), referrer);
            }
        }
    }

    /** Find a ContentCategory where a DefaultOfferUse is not null by following parents. */
    public ContentCategory getParentContentCategoryByNonNullDefaultOfferUse(final ContentCategory startingContentCategory) {
        var currentContentCategory = startingContentCategory;

        // Keep checking from the currentContentCategory through all its parents, until a DefaultOfferUse has been
        // found. If all else fails, the "ROOT" one will contain the same DefaultOfferUse as the ContentCatalog.
        while(currentContentCategory.getLastDetail().getDefaultOfferUse() == null) {
            currentContentCategory = currentContentCategory.getLastDetail().getParentContentCategory();
        }

        return currentContentCategory;
    }

    /** For a given ContentCategory, find the OfferUse that is used for items added to it. */
    public OfferUse getContentCategoryDefaultOfferUse(final ContentCategory contentCategory) {
        return getParentContentCategoryByNonNullDefaultOfferUse(contentCategory).getLastDetail().getDefaultOfferUse();
    }

    /** Get a Set of all OfferUses in a ContentCatalog for a given ContentCatalogItem. */
    private Set<OfferUse> getOfferUsesByContentCatalogItem(final ContentCatalogItem contentCatalogItem) {
        var contentControl = Session.getModelController(ContentControl.class);
        Set<OfferUse> offerUses = new HashSet<>();
        var contentCategoryItems = contentControl.getContentCategoryItemsByContentCatalogItem(contentCatalogItem);

        contentCategoryItems.forEach((contentCategoryItem) -> {
            offerUses.add(getContentCategoryDefaultOfferUse(contentCategoryItem.getContentCategory()));
        });

        return offerUses;
    }

    /** Checks across all Offers utilized in a ContentCatalog, and finds the lowest price that the ContentCatalogItem is offered for. */
    private Long getLowestUnitPrice(final ContentCatalogItem contentCatalogItem) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);
        var offerUses = getOfferUsesByContentCatalogItem(contentCatalogItem);
        long unitPrice = Integer.MAX_VALUE;

        for(var offerUse : offerUses) {
            var offerItem = offerItemControl.getOfferItem(offerUse.getLastDetail().getOffer(), contentCatalogItem.getItem());
            var offerItemPrice = offerItemControl.getOfferItemPrice(offerItem, contentCatalogItem.getInventoryCondition(),
                    contentCatalogItem.getUnitOfMeasureType(), contentCatalogItem.getCurrency());
            var offerItemFixedPrice = offerItemControl.getOfferItemFixedPrice(offerItemPrice);

            unitPrice = Math.min(unitPrice, offerItemFixedPrice.getUnitPrice());
        }

        return unitPrice;
    }

    /** Checks across all Offers utilized in a ContentCatalog, and finds an OfferItemVariablePrice for this ContentCatalogItem. All
     OfferItemVariablePrices should be the same, so we'll just use the first one that's found. */
    private OfferItemVariablePrice getOfferItemVariablePrice(final ContentCatalogItem contentCatalogItem) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);
        var offerUses = getOfferUsesByContentCatalogItem(contentCatalogItem);
        var offerUsesIterator = offerUses.iterator();
        var offerUse = offerUsesIterator.hasNext() ? offerUsesIterator.next() : null;
        var offerItem = offerUse == null ? null : offerItemControl.getOfferItem(offerUse.getLastDetail().getOffer(), contentCatalogItem.getItem());
        var offerItemPrice = offerItem == null ? null : offerItemControl.getOfferItemPrice(offerItem, contentCatalogItem.getInventoryCondition(),
                contentCatalogItem.getUnitOfMeasureType(), contentCatalogItem.getCurrency());
        var offerItemVariablePrice = offerItemPrice == null ? null : offerItemControl.getOfferItemVariablePrice(offerItemPrice);

        return offerItemVariablePrice;
    }

    /** Check to make sure the ContentCatalogItem has the lower price possible in the ContentCatalog, or if it is no longer used, delete it. */
    public void updateContentCatalogItemPriceByContentCatalogItem(final ContentCatalogItem contentCatalogItem, final BasePK updatedBy) {
        var contentControl = Session.getModelController(ContentControl.class);

        // Check to see if it still exists in any other categories, and delete ContentCatalogItem if it doesn't.
        if(contentControl.countContentCategoryItemsByContentCatalogItem(contentCatalogItem) == 0) {
            contentControl.deleteContentCatalogItem(contentCatalogItem, updatedBy);
        } else {
            var item = contentCatalogItem.getItem();
            var itemPriceTypeName = item.getLastDetail().getItemPriceType().getItemPriceTypeName();

            if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
                var unitPrice = getLowestUnitPrice(contentCatalogItem);
                var contentCatalogItemFixedPrice = contentControl.getContentCatalogItemFixedPriceForUpdate(contentCatalogItem);

                if(contentCatalogItemFixedPrice == null) {
                    contentControl.createContentCatalogItemFixedPrice(contentCatalogItem, unitPrice, updatedBy);
                } else {
                    var contentCatalogItemFixedPriceValue = contentControl.getContentCatalogItemFixedPriceValue(contentCatalogItemFixedPrice);

                    contentCatalogItemFixedPriceValue.setUnitPrice(unitPrice);

                    contentControl.updateContentCatalogItemFixedPriceFromValue(contentCatalogItemFixedPriceValue, updatedBy);
                }
            } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
                var contentCatalogItemVariablePrice = contentControl.getContentCatalogItemVariablePriceForUpdate(contentCatalogItem);
                var offerItemVariablePrice = getOfferItemVariablePrice(contentCatalogItem);
                var minimumUnitPrice = offerItemVariablePrice.getMinimumUnitPrice();
                var maximumUnitPrice = offerItemVariablePrice.getMaximumUnitPrice();
                var unitPriceIncrement = offerItemVariablePrice.getUnitPriceIncrement();

                if(contentCatalogItemVariablePrice == null) {
                    contentControl.createContentCatalogItemVariablePrice(contentCatalogItem, minimumUnitPrice, maximumUnitPrice, unitPriceIncrement, updatedBy);
                } else if(!minimumUnitPrice.equals(contentCatalogItemVariablePrice.getMinimumUnitPrice())
                        || !maximumUnitPrice.equals(contentCatalogItemVariablePrice.getMaximumUnitPrice())
                        || !unitPriceIncrement.equals(contentCatalogItemVariablePrice.getUnitPriceIncrement())) {
                    var contentCatalogItemVariablePriceValue = contentControl.getContentCatalogItemVariablePriceValue(contentCatalogItemVariablePrice);

                    contentCatalogItemVariablePriceValue.setMinimumUnitPrice(minimumUnitPrice);
                    contentCatalogItemVariablePriceValue.setMaximumUnitPrice(maximumUnitPrice);
                    contentCatalogItemVariablePriceValue.setUnitPriceIncrement(unitPriceIncrement);

                    contentControl.updateContentCatalogItemVariablePriceFromValue(contentCatalogItemVariablePriceValue, updatedBy);
                }
            }
        }
    }

    /** For all ContentCatalogItem in the Set, verify they have the lower price possible in their ContentCatalog. */
    public void updateContentCatalogItemPrices(final Iterable<ContentCatalogItem> contentCatalogItems, final BasePK updatedBy) {
        for(var contentCatalogItem : contentCatalogItems) {
            updateContentCatalogItemPriceByContentCatalogItem(contentCatalogItem, updatedBy);
        }
    }

    private Set<ContentCatalog> getContentCatalogsByOfferUses(final Iterable<OfferUse> offerUses) {
        var contentControl = Session.getModelController(ContentControl.class);
        Set<ContentCatalog> contentCatalogs = new HashSet<>();

        for(var offerUse : offerUses) {
            var contentCategories = contentControl.getContentCategoriesByDefaultOfferUse(offerUse);

            contentCategories.forEach((contentCategory) -> {
                contentCatalogs.add(contentCategory.getLastDetail().getContentCatalog());
            });
        }

        return contentCatalogs;
    }

    private Set<ContentCatalogItem> getContentCatalogItemsByContentCatalogs(final Iterable<ContentCatalog> contentCatalogs, final OfferItemPrice offerItemPrice) {
        var contentControl = Session.getModelController(ContentControl.class);
        Set<ContentCatalogItem> contentCatalogItems = new HashSet<>();

        for(var contentCatalog : contentCatalogs) {
            var contentCatalogItem = contentControl.getContentCatalogItem(contentCatalog, offerItemPrice.getOfferItem().getItem(),
                    offerItemPrice.getInventoryCondition(), offerItemPrice.getUnitOfMeasureType(), offerItemPrice.getCurrency());

            if(contentCatalogItem != null) {
                contentCatalogItems.add(contentCatalogItem);
            }
        }

        return contentCatalogItems;
    }

    public void updateContentCatalogItemPricesByOfferItemPrice(final OfferItemPrice offerItemPrice, final BasePK updatedBy) {
        var offerUseControl = Session.getModelController(OfferUseControl.class);
        Iterable<OfferUse> offerUses = offerUseControl.getOfferUsesByOffer(offerItemPrice.getOfferItem().getOffer());
        Iterable<ContentCatalog> contentCatalogs = getContentCatalogsByOfferUses(offerUses);
        Iterable<ContentCatalogItem> contentCatalogItems = getContentCatalogItemsByContentCatalogs(contentCatalogs, offerItemPrice);

        updateContentCatalogItemPrices(contentCatalogItems, updatedBy);
    }

    private void addContentCatalogItems(final Set<ContentCatalogItem> contentCatalogItems, final ContentCategory parentContentCategory) {
        var contentControl = Session.getModelController(ContentControl.class);
        Iterable<ContentCategoryItem> contentCategoryItems = contentControl.getContentCategoryItemsByContentCategory(parentContentCategory);
        Iterable<ContentCategory> childContentCategories = contentControl.getContentCategoriesByParentContentCategory(parentContentCategory);

        for(var contentCategoryItem : contentCategoryItems) {
            contentCatalogItems.add(contentCategoryItem.getContentCatalogItem());
        }

        for(var childContentCategory : childContentCategories) {
            if(childContentCategory.getLastDetail().getDefaultOfferUse() == null) {
                addContentCatalogItems(contentCatalogItems, childContentCategory);
            }
        }
    }

    /** Call when a ContentCategory is updated, and the DefaultOfferUse was modified. */
    public void updateContentCatalogItemPricesByContentCategory(final ContentCategory contentCategory, final BasePK updatedBy) {
        Set<ContentCatalogItem> contentCatalogItems = new HashSet<>();

        // All ContentCatalogItems from the highest ContentCategory with a non-null DefaultOfferUse on down.
        addContentCatalogItems(contentCatalogItems, getParentContentCategoryByNonNullDefaultOfferUse(contentCategory));

        // Check Prices for all of them.
        updateContentCatalogItemPrices(contentCatalogItems, updatedBy);
    }

    public ContentCategoryItem createContentCategoryItem(final ExecutionErrorAccumulator eea, final ContentCategory contentCategory, final Item item,
            final InventoryCondition inventoryCondition, final UnitOfMeasureType unitOfMeasureType, final Currency currency, final Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy) {
        ContentCategoryItem contentCategoryItem = null;
        var offerUse = getContentCategoryDefaultOfferUse(contentCategory);
        var offer = offerUse.getLastDetail().getOffer();

        if(OfferItemLogic.getInstance().getOfferItemPrice(eea, offer, item, inventoryCondition, unitOfMeasureType, currency) != null) {
            var contentControl = Session.getModelController(ContentControl.class);
            var contentCategoryDetail = contentCategory.getLastDetail();
            var contentCatalog = contentCategoryDetail.getContentCatalog();
            var contentCatalogItem = contentControl.getContentCatalogItem(contentCatalog, item, inventoryCondition, unitOfMeasureType, currency);

            if(contentCatalogItem == null) {
                contentCatalogItem = contentControl.createContentCatalogItem(contentCatalog, item, inventoryCondition, unitOfMeasureType, currency, createdBy);
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                contentCategoryItem = contentControl.getContentCategoryItem(contentCategory, contentCatalogItem);

                if(contentCategoryItem == null) {
                    contentCategoryItem = contentControl.createContentCategoryItem(contentCategory, contentCatalogItem, isDefault, sortOrder, createdBy);

                    updateContentCatalogItemPriceByContentCatalogItem(contentCatalogItem, createdBy);
                } else {
                    var contentCatalogDetail = contentCatalog.getLastDetail();

                    handleExecutionError(DuplicateContentCategoryItemException.class, eea, ExecutionErrors.DuplicateContentCategoryItem.name(),
                            contentCatalogDetail.getContentCollection().getLastDetail().getContentCollectionName(), contentCatalogDetail.getContentCatalogName(),
                            contentCategoryDetail.getContentCategoryName(), item.getLastDetail().getItemName(),
                            inventoryCondition.getLastDetail().getInventoryConditionName(), unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName(),
                            currency.getCurrencyIsoName());
                }
            }
        }

        return contentCategoryItem;
    }

    public void updateContentCategoryItemFromValue(final ContentCategoryItemValue contentCategoryItemValue, final BasePK updatedBy) {
        var contentControl = Session.getModelController(ContentControl.class);

        contentControl.updateContentCategoryItemFromValue(contentCategoryItemValue, updatedBy);
    }

    public void deleteContentCategoryItem(final ContentCategoryItem contentCategoryItem, final BasePK deletedBy) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCatalogItem = contentCategoryItem.getContentCatalogItemForUpdate();

        contentControl.deleteContentCategoryItem(contentCategoryItem, deletedBy);

        updateContentCatalogItemPriceByContentCatalogItem(contentCatalogItem, deletedBy);
    }

    private void getChildContentCategoriesByContentCategory(final ContentControl contentControl, final List<ContentCategory> contentCategories,
            final ContentCategory contentCategory) {
        contentCategories.add(contentCategory);

        contentControl.getContentCategoriesByParentContentCategory(contentCategory).stream().filter((childContentCategory) -> (childContentCategory.getLastDetail().getDefaultOfferUse() == null)).forEach((childContentCategory) -> {
            getChildContentCategoriesByContentCategory(contentControl, contentCategories, childContentCategory);
        });
    }

    /** Return a List of all ContentCategories, including the one passed to it, that inherit the DefaultOfferUse from it. */
    private List<ContentCategory> getChildContentCategoriesByContentCategory(final ContentCategory contentCategory) {
        var contentControl = Session.getModelController(ContentControl.class);
        List<ContentCategory> contentCategories = new ArrayList<>();

        getChildContentCategoriesByContentCategory(contentControl, contentCategories, contentCategory);

        return contentCategories;
    }

    private Set<ContentCategory> getContentCategoriesByOffer(Offer offer) {
        var contentControl = Session.getModelController(ContentControl.class);
        var offerUseControl = Session.getModelController(OfferUseControl.class);
        Set<ContentCategory> contentCategories = new HashSet<>();

        offerUseControl.getOfferUsesByOffer(offer).forEach((offerUse) -> {
            contentCategories.addAll(contentControl.getContentCategoriesByDefaultOfferUse(offerUse));
        });
        
        return contentCategories;
    }

    private Set<ContentCatalog> getContentCatalogsFromContentCategories(Iterable<ContentCategory> contentCategories) {
        Set<ContentCatalog> contentCatalogs = new HashSet<>();

        for(var contentCategory : contentCategories) {
            contentCatalogs.add(contentCategory.getLastDetail().getContentCatalog());
        }

        return contentCatalogs;
    }

    private Set<ContentCatalogItem> getPossibleContentCatalogItemsByOfferItemPrice(Iterable<ContentCatalog> contentCatalogs, OfferItemPrice offerItemPrice) {
        var contentControl = Session.getModelController(ContentControl.class);
        Set<ContentCatalogItem> contentCatalogItems = new HashSet<>();

        for(var contentCatalog : contentCatalogs) {
            var contentCatalogItem = contentControl.getContentCatalogItem(contentCatalog, offerItemPrice.getOfferItem().getItem(),
                    offerItemPrice.getInventoryCondition(), offerItemPrice.getUnitOfMeasureType(), offerItemPrice.getCurrency());

            if(contentCatalogItem != null) {
                contentCatalogItems.add(contentCatalogItem);
            }
        }

        return contentCatalogItems;
    }

    public void deleteContentCategoryItemByOfferItemPrice(final OfferItemPrice offerItemPrice, final BasePK deletedBy) {
        var contentControl = Session.getModelController(ContentControl.class);
        var offerItem = offerItemPrice.getOfferItem();

        // Create a list of all ContentCategories whose DefaultOfferUse is one that could be form this OfferItemPrice.
        Iterable<ContentCategory> contentCategories = getContentCategoriesByOffer(offerItem.getOffer());

        // Put together a list of all ContentCatalogItems that could be this OfferItemPrice.
        Iterable<ContentCatalog> contentCatalogs = getContentCatalogsFromContentCategories(contentCategories);
        Iterable<ContentCatalogItem> contentCatalogItems = getPossibleContentCatalogItemsByOfferItemPrice(contentCatalogs, offerItemPrice);

        // Go through the list of all the ContentCategories...
        for(var contentCategory : contentCategories) {
            List<ContentCategory> contentCategoriesToCheck = null;
            var contentCatalog = contentCategory.getLastDetail().getContentCatalog();

            // And the list of all the ContentCatalogItems...
            for(var contentCatalogItem : contentCatalogItems) {
                // And where the ContentCatalogItem's Catalog is the one from the current ContentCategory...
                if(contentCatalogItem.getContentCatalog().equals(contentCatalog)) {
                    if(contentCategoriesToCheck == null) {
                        // Get a list of all the possible ContentCategories that might contain the ContentCatalogItem.
                        contentCategoriesToCheck = getChildContentCategoriesByContentCategory(contentCategory);
                    }

                    for(var contentCategoryToCheck : contentCategoriesToCheck) {
                        var contentCategoryItem = contentControl.getContentCategoryItemForUpdate(contentCategoryToCheck, contentCatalogItem);

                        // If a ContentCategoryItem was found, delete it.
                        if(contentCategoryItem != null) {
                            deleteContentCategoryItem(contentCategoryItem, deletedBy);
                        }
                    }
                }
            }
        }
    }

}
