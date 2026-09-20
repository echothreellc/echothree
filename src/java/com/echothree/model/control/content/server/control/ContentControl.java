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

package com.echothree.model.control.content.server.control;

import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_PAGE_LAYOUT_AREAS_CONTENT_PAGE_AREA_TYPE_FK;
import com.echothree.model.control.content.common.ContentCategories;
import com.echothree.model.control.content.common.ContentSections;
import com.echothree.model.control.content.common.choice.ContentCatalogChoicesBean;
import com.echothree.model.control.content.common.choice.ContentCategoryChoicesBean;
import com.echothree.model.control.content.common.choice.ContentCollectionChoicesBean;
import com.echothree.model.control.content.common.choice.ContentPageAreaTypeChoicesBean;
import com.echothree.model.control.content.common.choice.ContentPageLayoutChoicesBean;
import com.echothree.model.control.content.common.choice.ContentSectionChoicesBean;
import com.echothree.model.control.content.common.choice.ContentWebAddressChoicesBean;
import com.echothree.model.control.content.common.transfer.ContentCatalogDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentCatalogItemTransfer;
import com.echothree.model.control.content.common.transfer.ContentCatalogTransfer;
import com.echothree.model.control.content.common.transfer.ContentCategoryDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentCategoryItemTransfer;
import com.echothree.model.control.content.common.transfer.ContentCategoryTransfer;
import com.echothree.model.control.content.common.transfer.ContentCollectionDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentCollectionTransfer;
import com.echothree.model.control.content.common.transfer.ContentForumTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageAreaTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageAreaTypeTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageLayoutAreaTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageLayoutDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageLayoutTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageTransfer;
import com.echothree.model.control.content.common.transfer.ContentSectionDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentSectionTransfer;
import com.echothree.model.control.content.common.transfer.ContentWebAddressDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentWebAddressTransfer;
import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.control.content.server.transfer.ContentCatalogDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCatalogItemTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCatalogTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCategoryDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCategoryItemTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCategoryTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCollectionDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentCollectionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentForumTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageAreaTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageAreaTypeTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageLayoutAreaTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageLayoutDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageLayoutTransferCache;
import com.echothree.model.control.content.server.transfer.ContentPageTransferCache;
import com.echothree.model.control.content.server.transfer.ContentSectionDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentSectionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentWebAddressDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentWebAddressTransferCache;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.content.common.pk.ContentCatalogItemFixedPricePK;
import com.echothree.model.data.content.common.pk.ContentCatalogItemPK;
import com.echothree.model.data.content.common.pk.ContentCatalogItemVariablePricePK;
import com.echothree.model.data.content.common.pk.ContentCatalogPK;
import com.echothree.model.data.content.common.pk.ContentCategoryPK;
import com.echothree.model.data.content.common.pk.ContentCollectionPK;
import com.echothree.model.data.content.common.pk.ContentForumPK;
import com.echothree.model.data.content.common.pk.ContentPageAreaPK;
import com.echothree.model.data.content.common.pk.ContentPageAreaTypePK;
import com.echothree.model.data.content.common.pk.ContentPageLayoutPK;
import com.echothree.model.data.content.common.pk.ContentPagePK;
import com.echothree.model.data.content.common.pk.ContentWebAddressPK;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogDescription;
import com.echothree.model.data.content.server.entity.ContentCatalogItem;
import com.echothree.model.data.content.server.entity.ContentCatalogItemFixedPrice;
import com.echothree.model.data.content.server.entity.ContentCatalogItemVariablePrice;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryDescription;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentCollectionDescription;
import com.echothree.model.data.content.server.entity.ContentForum;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentPageArea;
import com.echothree.model.data.content.server.entity.ContentPageAreaBlob;
import com.echothree.model.data.content.server.entity.ContentPageAreaClob;
import com.echothree.model.data.content.server.entity.ContentPageAreaDetail;
import com.echothree.model.data.content.server.entity.ContentPageAreaString;
import com.echothree.model.data.content.server.entity.ContentPageAreaType;
import com.echothree.model.data.content.server.entity.ContentPageAreaTypeDescription;
import com.echothree.model.data.content.server.entity.ContentPageAreaUrl;
import com.echothree.model.data.content.server.entity.ContentPageDescription;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.content.server.entity.ContentPageLayoutArea;
import com.echothree.model.data.content.server.entity.ContentPageLayoutAreaDescription;
import com.echothree.model.data.content.server.entity.ContentPageLayoutDescription;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.content.server.entity.ContentSectionDescription;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.content.server.entity.ContentWebAddressDescription;
import com.echothree.model.data.content.server.entity.ContentWebAddressServer;
import com.echothree.model.data.content.server.factory.ContentCatalogDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentCatalogDetailFactory;
import com.echothree.model.data.content.server.factory.ContentCatalogFactory;
import com.echothree.model.data.content.server.factory.ContentCatalogItemFactory;
import com.echothree.model.data.content.server.factory.ContentCatalogItemFixedPriceFactory;
import com.echothree.model.data.content.server.factory.ContentCatalogItemVariablePriceFactory;
import com.echothree.model.data.content.server.factory.ContentCategoryDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentCategoryDetailFactory;
import com.echothree.model.data.content.server.factory.ContentCategoryFactory;
import com.echothree.model.data.content.server.factory.ContentCategoryItemFactory;
import com.echothree.model.data.content.server.factory.ContentCollectionDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentCollectionDetailFactory;
import com.echothree.model.data.content.server.factory.ContentCollectionFactory;
import com.echothree.model.data.content.server.factory.ContentForumDetailFactory;
import com.echothree.model.data.content.server.factory.ContentForumFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaBlobFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaClobFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaDetailFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaStringFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaTypeDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaTypeFactory;
import com.echothree.model.data.content.server.factory.ContentPageAreaUrlFactory;
import com.echothree.model.data.content.server.factory.ContentPageDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentPageDetailFactory;
import com.echothree.model.data.content.server.factory.ContentPageFactory;
import com.echothree.model.data.content.server.factory.ContentPageLayoutAreaDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentPageLayoutAreaFactory;
import com.echothree.model.data.content.server.factory.ContentPageLayoutDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentPageLayoutDetailFactory;
import com.echothree.model.data.content.server.factory.ContentPageLayoutFactory;
import com.echothree.model.data.content.server.factory.ContentSectionDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentSectionDetailFactory;
import com.echothree.model.data.content.server.factory.ContentSectionFactory;
import com.echothree.model.data.content.server.factory.ContentWebAddressDescriptionFactory;
import com.echothree.model.data.content.server.factory.ContentWebAddressDetailFactory;
import com.echothree.model.data.content.server.factory.ContentWebAddressFactory;
import com.echothree.model.data.content.server.factory.ContentWebAddressServerFactory;
import com.echothree.model.data.content.server.value.ContentCatalogDescriptionValue;
import com.echothree.model.data.content.server.value.ContentCatalogDetailValue;
import com.echothree.model.data.content.server.value.ContentCatalogItemFixedPriceValue;
import com.echothree.model.data.content.server.value.ContentCatalogItemVariablePriceValue;
import com.echothree.model.data.content.server.value.ContentCategoryDescriptionValue;
import com.echothree.model.data.content.server.value.ContentCategoryDetailValue;
import com.echothree.model.data.content.server.value.ContentCategoryItemValue;
import com.echothree.model.data.content.server.value.ContentCollectionDescriptionValue;
import com.echothree.model.data.content.server.value.ContentCollectionDetailValue;
import com.echothree.model.data.content.server.value.ContentForumDetailValue;
import com.echothree.model.data.content.server.value.ContentPageAreaDetailValue;
import com.echothree.model.data.content.server.value.ContentPageDescriptionValue;
import com.echothree.model.data.content.server.value.ContentPageDetailValue;
import com.echothree.model.data.content.server.value.ContentPageLayoutDescriptionValue;
import com.echothree.model.data.content.server.value.ContentPageLayoutDetailValue;
import com.echothree.model.data.content.server.value.ContentSectionDescriptionValue;
import com.echothree.model.data.content.server.value.ContentSectionDetailValue;
import com.echothree.model.data.content.server.value.ContentWebAddressDescriptionValue;
import com.echothree.model.data.content.server.value.ContentWebAddressDetailValue;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATALOGS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATALOGS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATALOG_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATALOG_DETAILS_CONTENT_CATALOG_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATALOG_DETAILS_CONTENT_COLLECTION_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATALOG_ITEMS_CONTENT_CATALOG_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATALOG_ITEMS_CURRENCY_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATALOG_ITEMS_INVENTORY_CONDITION_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATALOG_ITEMS_ITEM_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATALOG_ITEMS_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATEGORIES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATEGORIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATEGORY_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATEGORY_DETAILS_CONTENT_CATALOG_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATEGORY_ITEMS_CONTENT_CATALOG_ITEM_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATEGORY_ITEMS_CONTENT_CATEGORY_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_COLLECTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_COLLECTIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_COLLECTION_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_COLLECTION_DETAILS_CONTENT_COLLECTION_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_FORUMS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_FORUM_DETAILS_CONTENT_FORUM_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_FORUM_DETAILS_FORUM_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_PAGES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_PAGE_AREAS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_PAGE_AREA_DETAILS_CONTENT_PAGE_AREA_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_PAGE_AREA_DETAILS_CONTENT_PAGE_LAYOUT_AREA_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_PAGE_AREA_DETAILS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_PAGE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_PAGE_DETAILS_CONTENT_PAGE_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_PAGE_DETAILS_CONTENT_SECTION_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_PAGE_LAYOUTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_PAGE_LAYOUT_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_PAGE_LAYOUT_DETAILS_CONTENT_PAGE_LAYOUT_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_SECTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_SECTIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_SECTION_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_SECTION_DETAILS_CONTENT_SECTION_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_WEB_ADDRESSES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_WEB_ADDRESS_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_WEB_ADDRESS_DETAILS_CONTENT_WEB_ADDRESS_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_WEB_ADDRESS_SERVERS_SERVER_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.SERVERS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUMS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_CONDITIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEMS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_KINDS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_TYPE_DETAILS_UNIT_OF_MEASURE_KIND_FK;
import static com.echothree.model.jooq.server.tables.accounting.Currencies.Currencies;
import static com.echothree.model.jooq.server.tables.content.ContentCatalogDescriptions.ContentCatalogDescriptions;
import static com.echothree.model.jooq.server.tables.content.ContentCatalogDetails.ContentCatalogDetails;
import static com.echothree.model.jooq.server.tables.content.ContentCatalogItemFixedPrices.ContentCatalogItemFixedPrices;
import static com.echothree.model.jooq.server.tables.content.ContentCatalogItemVariablePrices.ContentCatalogItemVariablePrices;
import static com.echothree.model.jooq.server.tables.content.ContentCatalogItems.ContentCatalogItems;
import static com.echothree.model.jooq.server.tables.content.ContentCatalogs.ContentCatalogs;
import static com.echothree.model.jooq.server.tables.content.ContentCategories.ContentCategories;
import static com.echothree.model.jooq.server.tables.content.ContentCategoryDescriptions.ContentCategoryDescriptions;
import static com.echothree.model.jooq.server.tables.content.ContentCategoryDetails.ContentCategoryDetails;
import static com.echothree.model.jooq.server.tables.content.ContentCategoryItems.ContentCategoryItems;
import static com.echothree.model.jooq.server.tables.content.ContentCollectionDescriptions.ContentCollectionDescriptions;
import static com.echothree.model.jooq.server.tables.content.ContentCollectionDetails.ContentCollectionDetails;
import static com.echothree.model.jooq.server.tables.content.ContentCollections.ContentCollections;
import static com.echothree.model.jooq.server.tables.content.ContentForumDetails.ContentForumDetails;
import static com.echothree.model.jooq.server.tables.content.ContentForums.ContentForums;
import static com.echothree.model.jooq.server.tables.content.ContentPageAreaBlobs.ContentPageAreaBlobs;
import static com.echothree.model.jooq.server.tables.content.ContentPageAreaClobs.ContentPageAreaClobs;
import static com.echothree.model.jooq.server.tables.content.ContentPageAreaDetails.ContentPageAreaDetails;
import static com.echothree.model.jooq.server.tables.content.ContentPageAreaStrings.ContentPageAreaStrings;
import static com.echothree.model.jooq.server.tables.content.ContentPageAreaTypeDescriptions.ContentPageAreaTypeDescriptions;
import static com.echothree.model.jooq.server.tables.content.ContentPageAreaTypes.ContentPageAreaTypes;
import static com.echothree.model.jooq.server.tables.content.ContentPageAreaUrls.ContentPageAreaUrls;
import static com.echothree.model.jooq.server.tables.content.ContentPageAreas.ContentPageAreas;
import static com.echothree.model.jooq.server.tables.content.ContentPageDescriptions.ContentPageDescriptions;
import static com.echothree.model.jooq.server.tables.content.ContentPageDetails.ContentPageDetails;
import static com.echothree.model.jooq.server.tables.content.ContentPageLayoutAreaDescriptions.ContentPageLayoutAreaDescriptions;
import static com.echothree.model.jooq.server.tables.content.ContentPageLayoutAreas.ContentPageLayoutAreas;
import static com.echothree.model.jooq.server.tables.content.ContentPageLayoutDescriptions.ContentPageLayoutDescriptions;
import static com.echothree.model.jooq.server.tables.content.ContentPageLayoutDetails.ContentPageLayoutDetails;
import static com.echothree.model.jooq.server.tables.content.ContentPageLayouts.ContentPageLayouts;
import static com.echothree.model.jooq.server.tables.content.ContentPages.ContentPages;
import static com.echothree.model.jooq.server.tables.content.ContentSectionDescriptions.ContentSectionDescriptions;
import static com.echothree.model.jooq.server.tables.content.ContentSectionDetails.ContentSectionDetails;
import static com.echothree.model.jooq.server.tables.content.ContentSections.ContentSections;
import static com.echothree.model.jooq.server.tables.content.ContentWebAddressDescriptions.ContentWebAddressDescriptions;
import static com.echothree.model.jooq.server.tables.content.ContentWebAddressDetails.ContentWebAddressDetails;
import static com.echothree.model.jooq.server.tables.content.ContentWebAddressServers.ContentWebAddressServers;
import static com.echothree.model.jooq.server.tables.content.ContentWebAddresses.ContentWebAddresses;
import static com.echothree.model.jooq.server.tables.core.ServerDetails.ServerDetails;
import static com.echothree.model.jooq.server.tables.core.Servers.Servers;
import static com.echothree.model.jooq.server.tables.forum.ForumDetails.ForumDetails;
import static com.echothree.model.jooq.server.tables.forum.Forums.Forums;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionDetails.InventoryConditionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditions.InventoryConditions;
import static com.echothree.model.jooq.server.tables.item.ItemDetails.ItemDetails;
import static com.echothree.model.jooq.server.tables.item.Items.Items;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKindDetails.UnitOfMeasureKindDetails;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKinds.UnitOfMeasureKinds;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypeDetails.UnitOfMeasureTypeDetails;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypes.UnitOfMeasureTypes;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;

@CommandScope
public class ContentControl
        extends BaseModelControl {
    
    @Inject
    protected ContentLogic contentLogic;

    /** Creates a new instance of ContentControl */
    protected ContentControl() {
        super();
    }

    @Inject
    OrderControl orderControl;

    // --------------------------------------------------------------------------------
    //   Content Page Area Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentPageAreaTypeFactory contentPageAreaTypeFactory;

    @Inject
    ContentPageAreaTypeTransferCache contentPageAreaTypeTransferCache;

    public ContentPageAreaType createContentPageAreaType(String contentPageAreaTypeName, BasePK createdBy) {
        var contentPageAreaType = contentPageAreaTypeFactory.create(contentPageAreaTypeName);
        
        sendEvent(contentPageAreaType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return contentPageAreaType;
    }

    public long countContentPageAreaTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ContentPageAreaTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContentPageAreaType */
    public ContentPageAreaType getContentPageAreaTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContentPageAreaTypePK(entityInstance.getEntityUniqueId());

        return contentPageAreaTypeFactory.getEntityFromPK(entityPermission, pk);
    }
    
    public ContentPageAreaType getContentPageAreaTypeByEntityInstance(EntityInstance entityInstance) {
        return getContentPageAreaTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContentPageAreaType getContentPageAreaTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContentPageAreaTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public ContentPageAreaType getContentPageAreaTypeByName(String contentPageAreaTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentPageAreaTypes.fields())
                .from(ContentPageAreaTypes)
                .where(ContentPageAreaTypes.CONTENT_PAGE_AREA_TYPE_NAME.eq(contentPageAreaTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentPageAreaTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentPageAreaType getContentPageAreaTypeByName(String contentPageAreaTypeName) {
        return getContentPageAreaTypeByName(contentPageAreaTypeName, EntityPermission.READ_ONLY);
    }
    
    public ContentPageAreaType getContentPageAreaTypeByNameForUpdate(String contentPageAreaTypeName) {
        return getContentPageAreaTypeByName(contentPageAreaTypeName, EntityPermission.READ_WRITE);
    }
    
    private List<ContentPageAreaType> getContentPageAreaTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentPageAreaTypes.fields())
                .from(ContentPageAreaTypes);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContentPageAreaTypes.CONTENT_PAGE_AREA_TYPE_NAME),
                    ContentPageAreaTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentPageAreaTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentPageAreaType> getContentPageAreaTypes() {
        return getContentPageAreaTypes(EntityPermission.READ_ONLY);
    }
    
    public List<ContentPageAreaType> getContentPageAreaTypesForUpdate() {
        return getContentPageAreaTypes(EntityPermission.READ_WRITE);
    }
    
    public ContentPageAreaTypeTransfer getContentPageAreaTypeTransfer(UserVisit userVisit, ContentPageAreaType contentPageAreaType) {
        return contentPageAreaTypeTransferCache.getTransfer(userVisit, contentPageAreaType);
    }
    
    public List<ContentPageAreaTypeTransfer> getContentPageAreaTypeTransfers(UserVisit userVisit, Collection<ContentPageAreaType> contentPageAreaTypes) {
        List<ContentPageAreaTypeTransfer> contentPageAreaTypeTransfers = new ArrayList<>(contentPageAreaTypes.size());
        
        contentPageAreaTypes.forEach((contentPageAreaType) ->
                contentPageAreaTypeTransfers.add(contentPageAreaTypeTransferCache.getTransfer(userVisit, contentPageAreaType))
        );
        
        return contentPageAreaTypeTransfers;
    }
    
    public List<ContentPageAreaTypeTransfer> getContentPageAreaTypeTransfers(UserVisit userVisit) {
        return getContentPageAreaTypeTransfers(userVisit, getContentPageAreaTypes());
    }
    
    public ContentPageAreaTypeChoicesBean getContentPageAreaTypeChoices(String defaultContentPageAreaTypeChoice, Language language,
            boolean allowNullChoice) {
        var contentPageAreaTypes = getContentPageAreaTypes();
        var size = contentPageAreaTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultContentPageAreaTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var contentPageAreaType : contentPageAreaTypes) {
            var label = getBestContentPageAreaTypeDescription(contentPageAreaType, language);
            var value = contentPageAreaType.getContentPageAreaTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultContentPageAreaTypeChoice == null ? false : defaultContentPageAreaTypeChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new ContentPageAreaTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentPageAreaTypeDescriptionFactory contentPageAreaTypeDescriptionFactory;

    public ContentPageAreaTypeDescription createContentPageAreaTypeDescription(ContentPageAreaType contentPageAreaType, Language language,
            String description, BasePK createdBy) {
        var contentPageAreaTypeDescription = contentPageAreaTypeDescriptionFactory.create(contentPageAreaType,
                language, description);
        
        sendEvent(contentPageAreaType.getPrimaryKey(), EventTypes.MODIFY, contentPageAreaTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentPageAreaTypeDescription;
    }
    
    public ContentPageAreaTypeDescription getContentPageAreaTypeDescription(ContentPageAreaType contentPageAreaType, Language language) {
        var query = session.getDslContext()
                .select(ContentPageAreaTypeDescriptions.fields())
                .from(ContentPageAreaTypeDescriptions)
                .where(ContentPageAreaTypeDescriptions.CONTENT_PAGE_AREA_TYPE.eq(contentPageAreaType.getPrimaryKey()),
                        ContentPageAreaTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return contentPageAreaTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestContentPageAreaTypeDescription(ContentPageAreaType contentPageAreaType, Language language) {
        String description;
        var contentPageAreaTypeDescription = getContentPageAreaTypeDescription(contentPageAreaType, language);
        
        if(contentPageAreaTypeDescription == null && !language.getIsDefault()) {
            contentPageAreaTypeDescription = getContentPageAreaTypeDescription(contentPageAreaType, partyControl.getDefaultLanguage());
        }
        
        if(contentPageAreaTypeDescription == null) {
            description = contentPageAreaType.getContentPageAreaTypeName();
        } else {
            description = contentPageAreaTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layouts
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentPageLayoutDetailFactory contentPageLayoutDetailFactory;

    @Inject
    protected ContentPageLayoutFactory contentPageLayoutFactory;

    @Inject
    ContentPageLayoutTransferCache contentPageLayoutTransferCache;

    public ContentPageLayout createContentPageLayout(String contentPageLayoutName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultContentPageLayout = getDefaultContentPageLayout();
        var defaultFound = defaultContentPageLayout != null;
        
        if(defaultFound && isDefault) {
            var defaultContentPageLayoutDetailValue = getDefaultContentPageLayoutDetailValueForUpdate();
            
            defaultContentPageLayoutDetailValue.setIsDefault(false);
            updateContentPageLayoutFromValue(defaultContentPageLayoutDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var contentPageLayout = contentPageLayoutFactory.create();
        var contentPageLayoutDetail = contentPageLayoutDetailFactory.create(
                contentPageLayout, contentPageLayoutName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        contentPageLayout = contentPageLayoutFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentPageLayout.getPrimaryKey());
        contentPageLayout.setActiveDetail(contentPageLayoutDetail);
        contentPageLayout.setLastDetail(contentPageLayoutDetail);
        contentPageLayout.store();
        
        sendEvent(contentPageLayout.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentPageLayout;
    }

    public long countContentPageLayouts() {
        return session.getDslContext()
                .selectCount()
                .from(ContentPageLayouts)
                .join(ContentPageLayoutDetails).onKey(CONTENT_PAGE_LAYOUTS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContentPageLayout */
    public ContentPageLayout getContentPageLayoutByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContentPageLayoutPK(entityInstance.getEntityUniqueId());

        return contentPageLayoutFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContentPageLayout getContentPageLayoutByEntityInstance(EntityInstance entityInstance) {
        return getContentPageLayoutByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContentPageLayout getContentPageLayoutByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContentPageLayoutByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public ContentPageLayout getContentPageLayoutByName(String contentPageLayoutName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentPageLayouts.fields())
                .from(ContentPageLayouts)
                .join(ContentPageLayoutDetails).onKey(CONTENT_PAGE_LAYOUT_DETAILS_CONTENT_PAGE_LAYOUT_FK)
                .where(ContentPageLayoutDetails.CONTENT_PAGE_LAYOUT_NAME.eq(contentPageLayoutName),
                        ContentPageLayoutDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentPageLayoutFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentPageLayout getContentPageLayoutByName(String contentPageLayoutName) {
        return getContentPageLayoutByName(contentPageLayoutName, EntityPermission.READ_ONLY);
    }
    
    public ContentPageLayout getContentPageLayoutByNameForUpdate(String contentPageLayoutName) {
        return getContentPageLayoutByName(contentPageLayoutName, EntityPermission.READ_WRITE);
    }
    
    public ContentPageLayoutDetailValue getContentPageLayoutDetailValueForUpdate(ContentPageLayout contentPageLayout) {
        return contentPageLayout == null? null: contentPageLayout.getLastDetailForUpdate().getContentPageLayoutDetailValue().clone();
    }
    
    public ContentPageLayoutDetailValue getContentPageLayoutDetailValueByNameForUpdate(String contentPageLayoutName) {
        return getContentPageLayoutDetailValueForUpdate(getContentPageLayoutByNameForUpdate(contentPageLayoutName));
    }
    
    public ContentPageLayout getDefaultContentPageLayout(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentPageLayouts.fields())
                .from(ContentPageLayouts)
                .join(ContentPageLayoutDetails).onKey(CONTENT_PAGE_LAYOUT_DETAILS_CONTENT_PAGE_LAYOUT_FK)
                .where(ContentPageLayoutDetails.IS_DEFAULT.eq(true),
                        ContentPageLayoutDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentPageLayoutFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentPageLayout getDefaultContentPageLayout() {
        return getDefaultContentPageLayout(EntityPermission.READ_ONLY);
    }
    
    public ContentPageLayout getDefaultContentPageLayoutForUpdate() {
        return getDefaultContentPageLayout(EntityPermission.READ_WRITE);
    }
    
    public ContentPageLayoutDetailValue getDefaultContentPageLayoutDetailValueForUpdate() {
        return getDefaultContentPageLayoutForUpdate().getLastDetailForUpdate().getContentPageLayoutDetailValue().clone();
    }
    
    private List<ContentPageLayout> getContentPageLayouts(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentPageLayouts.fields())
                .from(ContentPageLayouts)
                .join(ContentPageLayoutDetails).onKey(CONTENT_PAGE_LAYOUT_DETAILS_CONTENT_PAGE_LAYOUT_FK)
                .where(ContentPageLayoutDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContentPageLayoutDetails.SORT_ORDER, ContentPageLayoutDetails.CONTENT_PAGE_LAYOUT_NAME),
                    ContentPageLayoutFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentPageLayoutFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentPageLayout> getContentPageLayouts() {
        return getContentPageLayouts(EntityPermission.READ_ONLY);
    }
    
    public List<ContentPageLayout> getContentPageLayoutsForUpdate() {
        return getContentPageLayouts(EntityPermission.READ_WRITE);
    }
    
    public ContentPageLayoutTransfer getContentPageLayoutTransfer(UserVisit userVisit, ContentPageLayout contentPageLayout) {
        return contentPageLayoutTransferCache.getTransfer(userVisit, contentPageLayout);
    }
    
    public List<ContentPageLayoutTransfer> getContentPageLayoutTransfers(UserVisit userVisit, Collection<ContentPageLayout> contentPageLayouts) {
        List<ContentPageLayoutTransfer> contentPageLayoutTransfers = new ArrayList<>(contentPageLayouts.size());
        
        contentPageLayouts.forEach((contentPageLayout) ->
                contentPageLayoutTransfers.add(contentPageLayoutTransferCache.getTransfer(userVisit, contentPageLayout))
        );
        
        return contentPageLayoutTransfers;
    }
    
    public List<ContentPageLayoutTransfer> getContentPageLayoutTransfers(UserVisit userVisit) {
        return getContentPageLayoutTransfers(userVisit, getContentPageLayouts());
    }
    
    public ContentPageLayoutChoicesBean getContentPageLayoutChoices(String defaultContentPageLayoutChoice, Language language,
            boolean allowNullChoice) {
        var contentPageLayouts = getContentPageLayouts();
        var size = contentPageLayouts.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultContentPageLayoutChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var contentPageLayout : contentPageLayouts) {
            var contentPageLayoutDetail = contentPageLayout.getLastDetail();
            
            var label = getBestContentPageLayoutDescription(contentPageLayout, language);
            var value = contentPageLayoutDetail.getContentPageLayoutName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultContentPageLayoutChoice == null ? false : defaultContentPageLayoutChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contentPageLayoutDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ContentPageLayoutChoicesBean(labels, values, defaultValue);
    }
    
    private void updateContentPageLayoutFromValue(ContentPageLayoutDetailValue contentPageLayoutDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(contentPageLayoutDetailValue.hasBeenModified()) {
            var contentPageLayout = contentPageLayoutFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     contentPageLayoutDetailValue.getContentPageLayoutPK());
            var contentPageLayoutDetail = contentPageLayout.getActiveDetailForUpdate();
            
            contentPageLayoutDetail.setThruTime(session.getStartTime());
            contentPageLayoutDetail.store();

            var contentPageLayoutPK = contentPageLayoutDetail.getContentPageLayoutPK();
            var contentPageLayoutName = contentPageLayoutDetailValue.getContentPageLayoutName();
            var isDefault = contentPageLayoutDetailValue.getIsDefault();
            var sortOrder = contentPageLayoutDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultContentPageLayout = getDefaultContentPageLayout();
                var defaultFound = defaultContentPageLayout != null && !defaultContentPageLayout.equals(contentPageLayout);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultContentPageLayoutDetailValue = getDefaultContentPageLayoutDetailValueForUpdate();
                    
                    defaultContentPageLayoutDetailValue.setIsDefault(false);
                    updateContentPageLayoutFromValue(defaultContentPageLayoutDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            contentPageLayoutDetail = contentPageLayoutDetailFactory.create(contentPageLayoutPK,
                    contentPageLayoutName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            contentPageLayout.setActiveDetail(contentPageLayoutDetail);
            contentPageLayout.setLastDetail(contentPageLayoutDetail);
            
            sendEvent(contentPageLayoutPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateContentPageLayoutFromValue(ContentPageLayoutDetailValue contentPageLayoutDetailValue, BasePK updatedBy) {
        updateContentPageLayoutFromValue(contentPageLayoutDetailValue, true, updatedBy);
    }
    
    public void deleteContentPageLayout(ContentPageLayout contentPageLayout, BasePK deletedBy) {
        deleteContentPagesByContentPageLayout(contentPageLayout, deletedBy);
        deleteContentPageLayoutDescriptionsByContentPageLayout(contentPageLayout, deletedBy);

        var contentPageLayoutDetail = contentPageLayout.getLastDetailForUpdate();
        contentPageLayoutDetail.setThruTime(session.getStartTime());
        contentPageLayoutDetail.store();
        contentPageLayout.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var defaultContentPageLayout = getDefaultContentPageLayout();
        if(defaultContentPageLayout == null) {
            var contentPageLayouts = getContentPageLayoutsForUpdate();
            
            if(!contentPageLayouts.isEmpty()) {
                var iter = contentPageLayouts.iterator();
                if(iter.hasNext()) {
                    defaultContentPageLayout = iter.next();
                }
                var contentPageLayoutDetailValue = Objects.requireNonNull(defaultContentPageLayout).getLastDetailForUpdate().getContentPageLayoutDetailValue().clone();
                
                contentPageLayoutDetailValue.setIsDefault(true);
                updateContentPageLayoutFromValue(contentPageLayoutDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(contentPageLayout.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentPageLayoutDescriptionFactory contentPageLayoutDescriptionFactory;

    @Inject
    ContentPageLayoutDescriptionTransferCache contentPageLayoutDescriptionTransferCache;

    public ContentPageLayoutDescription createContentPageLayoutDescription(ContentPageLayout contentPageLayout, Language language, String description, BasePK createdBy) {
        var contentPageLayoutDescription = contentPageLayoutDescriptionFactory.create(contentPageLayout, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(contentPageLayout.getPrimaryKey(), EventTypes.MODIFY, contentPageLayoutDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentPageLayoutDescription;
    }
    
    public ContentPageLayoutDescription getContentPageLayoutDescription(ContentPageLayout contentPageLayout, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentPageLayoutDescriptions.fields())
                .from(ContentPageLayoutDescriptions)
                .where(ContentPageLayoutDescriptions.CONTENT_PAGE_LAYOUT.eq(contentPageLayout.getPrimaryKey()),
                        ContentPageLayoutDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ContentPageLayoutDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentPageLayoutDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentPageLayoutDescription getContentPageLayoutDescription(ContentPageLayout contentPageLayout, Language language) {
        return getContentPageLayoutDescription(contentPageLayout, language, EntityPermission.READ_ONLY);
    }
    
    public ContentPageLayoutDescription getContentPageLayoutDescriptionForUpdate(ContentPageLayout contentPageLayout, Language language) {
        return getContentPageLayoutDescription(contentPageLayout, language, EntityPermission.READ_WRITE);
    }
    
    public ContentPageLayoutDescriptionValue getContentPageLayoutDescriptionValue(ContentPageLayoutDescription contentPageLayoutDescription) {
        return contentPageLayoutDescription == null? null: contentPageLayoutDescription.getContentPageLayoutDescriptionValue().clone();
    }
    
    public ContentPageLayoutDescriptionValue getContentPageLayoutDescriptionValueForUpdate(ContentPageLayout contentPageLayout, Language language) {
        return getContentPageLayoutDescriptionValue(getContentPageLayoutDescriptionForUpdate(contentPageLayout, language));
    }
    
    private List<ContentPageLayoutDescription> getContentPageLayoutDescriptionsByContentPageLayout(ContentPageLayout contentPageLayout, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentPageLayoutDescriptions.fields())
                    .from(ContentPageLayoutDescriptions)
                    .join(Languages).onKey(CONTENT_PAGE_LAYOUT_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ContentPageLayoutDescriptions.CONTENT_PAGE_LAYOUT.eq(contentPageLayout.getPrimaryKey()),
                            ContentPageLayoutDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ContentPageLayoutDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentPageLayoutDescriptions.fields())
                    .from(ContentPageLayoutDescriptions)
                    .where(ContentPageLayoutDescriptions.CONTENT_PAGE_LAYOUT.eq(contentPageLayout.getPrimaryKey()),
                            ContentPageLayoutDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentPageLayoutDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentPageLayoutDescription> getContentPageLayoutDescriptionsByContentPageLayout(ContentPageLayout contentPageLayout) {
        return getContentPageLayoutDescriptionsByContentPageLayout(contentPageLayout, EntityPermission.READ_ONLY);
    }
    
    public List<ContentPageLayoutDescription> getContentPageLayoutDescriptionsByContentPageLayoutForUpdate(ContentPageLayout contentPageLayout) {
        return getContentPageLayoutDescriptionsByContentPageLayout(contentPageLayout, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentPageLayoutDescription(ContentPageLayout contentPageLayout, Language language) {
        String description;
        var contentPageLayoutDescription = getContentPageLayoutDescription(contentPageLayout, language);
        
        if(contentPageLayoutDescription == null && !language.getIsDefault()) {
            contentPageLayoutDescription = getContentPageLayoutDescription(contentPageLayout, partyControl.getDefaultLanguage());
        }
        
        if(contentPageLayoutDescription == null) {
            description = contentPageLayout.getLastDetail().getContentPageLayoutName();
        } else {
            description = contentPageLayoutDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentPageLayoutDescriptionTransfer getContentPageLayoutDescriptionTransfer(UserVisit userVisit, ContentPageLayoutDescription contentPageLayoutDescription) {
        return contentPageLayoutDescriptionTransferCache.getTransfer(userVisit, contentPageLayoutDescription);
    }
    
    public List<ContentPageLayoutDescriptionTransfer> getContentPageLayoutDescriptionTransfersByContentPageLayout(UserVisit userVisit, ContentPageLayout contentPageLayout) {
        var contentPageLayoutDescriptions = getContentPageLayoutDescriptionsByContentPageLayout(contentPageLayout);
        List<ContentPageLayoutDescriptionTransfer> contentPageLayoutDescriptionTransfers = new ArrayList<>(contentPageLayoutDescriptions.size());
        
        contentPageLayoutDescriptions.forEach((contentPageLayoutDescription) ->
                contentPageLayoutDescriptionTransfers.add(contentPageLayoutDescriptionTransferCache.getTransfer(userVisit, contentPageLayoutDescription))
        );
        
        return contentPageLayoutDescriptionTransfers;
    }
    
    public void updateContentPageLayoutDescriptionFromValue(ContentPageLayoutDescriptionValue contentPageLayoutDescriptionValue, BasePK updatedBy) {
        if(contentPageLayoutDescriptionValue.hasBeenModified()) {
            var contentPageLayoutDescription = contentPageLayoutDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentPageLayoutDescriptionValue.getPrimaryKey());
            
            contentPageLayoutDescription.setThruTime(session.getStartTime());
            contentPageLayoutDescription.store();

            var contentPageLayout = contentPageLayoutDescription.getContentPageLayout();
            var language = contentPageLayoutDescription.getLanguage();
            var description = contentPageLayoutDescriptionValue.getDescription();
            
            contentPageLayoutDescription = contentPageLayoutDescriptionFactory.create(contentPageLayout, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contentPageLayout.getPrimaryKey(), EventTypes.MODIFY, contentPageLayoutDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentPageLayoutDescription(ContentPageLayoutDescription contentPageLayoutDescription, BasePK deletedBy) {
        contentPageLayoutDescription.setThruTime(session.getStartTime());
        
        sendEvent(contentPageLayoutDescription.getContentPageLayoutPK(), EventTypes.MODIFY, contentPageLayoutDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentPageLayoutDescriptionsByContentPageLayout(ContentPageLayout contentPageLayout, BasePK deletedBy) {
        var contentPageLayoutDescriptions = getContentPageLayoutDescriptionsByContentPageLayoutForUpdate(contentPageLayout);
        
        contentPageLayoutDescriptions.forEach((contentPageLayoutDescription) -> 
                deleteContentPageLayoutDescription(contentPageLayoutDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Content Page Layout Areas
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentPageLayoutAreaFactory contentPageLayoutAreaFactory;

    @Inject
    ContentPageLayoutAreaTransferCache contentPageLayoutAreaTransferCache;

    public ContentPageLayoutArea createContentPageLayoutArea(ContentPageLayout contentPageLayout,
            ContentPageAreaType contentPageAreaType, Boolean showDescriptionField, Integer sortOrder) {
        return contentPageLayoutAreaFactory.create(contentPageLayout, contentPageAreaType,
                showDescriptionField, sortOrder);
    }
    
    public long countContentPageLayoutAreasByContentPageLayout(ContentPageLayout contentPageLayout) {
        return session.getDslContext()
                .selectCount()
                .from(ContentPageLayoutAreas)
                .where(ContentPageLayoutAreas.CONTENT_PAGE_LAYOUT.eq(contentPageLayout.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ContentPageLayoutArea getContentPageLayoutArea(ContentPageLayout contentPageLayout, Integer sortOrder) {
        var query = session.getDslContext()
                .select(ContentPageLayoutAreas.fields())
                .from(ContentPageLayoutAreas)
                .where(ContentPageLayoutAreas.CONTENT_PAGE_LAYOUT.eq(contentPageLayout.getPrimaryKey()),
                        ContentPageLayoutAreas.SORT_ORDER.eq(sortOrder));

        return contentPageLayoutAreaFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<ContentPageLayoutArea> getContentPageLayoutAreasByContentPageLayout(ContentPageLayout contentPageLayout) {
        var query = session.applyLimit(session.getDslContext()
                .select(ContentPageLayoutAreas.fields())
                .from(ContentPageLayoutAreas)
                .where(ContentPageLayoutAreas.CONTENT_PAGE_LAYOUT.eq(contentPageLayout.getPrimaryKey()))
                .orderBy(ContentPageLayoutAreas.SORT_ORDER),
                ContentPageLayoutAreaFactory.class);

        return contentPageLayoutAreaFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<ContentPageLayoutAreaTransfer> getContentPageLayoutAreaTransfers(UserVisit userVisit, Collection<ContentPageLayoutArea> contentPageLayoutAreas) {
        List<ContentPageLayoutAreaTransfer> contentPageLayoutAreaTransfers = new ArrayList<>(contentPageLayoutAreas.size());
        
        contentPageLayoutAreas.forEach((contentPageLayoutArea) ->
                contentPageLayoutAreaTransfers.add(contentPageLayoutAreaTransferCache.getContentPageLayoutAreaTransfer(userVisit, contentPageLayoutArea))
        );
        
        return contentPageLayoutAreaTransfers;
    }
    
    public List<ContentPageLayoutAreaTransfer> getContentPageLayoutAreaTransfersByContentPageLayout(UserVisit userVisit, ContentPageLayout contentPageLayout) {
        return getContentPageLayoutAreaTransfers(userVisit, getContentPageLayoutAreasByContentPageLayout(contentPageLayout));
    }
    
    public ContentPageLayoutAreaTransfer getContentPageLayoutAreaTransfer(UserVisit userVisit, ContentPageLayoutArea contentPageLayoutArea) {
        return contentPageLayoutAreaTransferCache.getContentPageLayoutAreaTransfer(userVisit, contentPageLayoutArea);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Area Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentPageLayoutAreaDescriptionFactory contentPageLayoutAreaDescriptionFactory;

    public ContentPageLayoutAreaDescription createContentPageLayoutAreaDescription(ContentPageLayoutArea contentPageLayoutArea, Language language, String description) {
        return contentPageLayoutAreaDescriptionFactory.create(contentPageLayoutArea, language, description);
    }
    
    public ContentPageLayoutAreaDescription getContentPageLayoutAreaDescription(ContentPageLayoutArea contentPageLayoutArea, Language language) {
        var query = session.getDslContext()
                .select(ContentPageLayoutAreaDescriptions.fields())
                .from(ContentPageLayoutAreaDescriptions)
                .where(ContentPageLayoutAreaDescriptions.CONTENT_PAGE_LAYOUT_AREA.eq(contentPageLayoutArea.getPrimaryKey()),
                        ContentPageLayoutAreaDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return contentPageLayoutAreaDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestContentPageLayoutAreaDescription(ContentPageLayoutArea contentPageLayoutArea, Language language) {
        String description;
        var contentPageLayoutAreaDescription = getContentPageLayoutAreaDescription(contentPageLayoutArea, language);
        
        if(contentPageLayoutAreaDescription == null && !language.getIsDefault()) {
            contentPageLayoutAreaDescription = getContentPageLayoutAreaDescription(contentPageLayoutArea, partyControl.getDefaultLanguage());
        }
        
        if(contentPageLayoutAreaDescription == null) {
            description = contentPageLayoutArea.getSortOrder().toString();
        } else {
            description = contentPageLayoutAreaDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Content Collections
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentCollectionDetailFactory contentCollectionDetailFactory;

    @Inject
    protected ContentCollectionFactory contentCollectionFactory;

    @Inject
    ContentCollectionTransferCache contentCollectionTransferCache;

    public ContentCollection createContentCollection(String contentCollectionName, OfferUse defaultOfferUse, BasePK createdBy) {
        var contentCollection = contentCollectionFactory.create();
        var contentCollectionDetail = contentCollectionDetailFactory.create(
                contentCollection, contentCollectionName, defaultOfferUse, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        contentCollection = contentCollectionFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentCollection.getPrimaryKey());
        contentCollection.setActiveDetail(contentCollectionDetail);
        contentCollection.setLastDetail(contentCollectionDetail);
        contentCollection.store();
        
        sendEvent(contentCollection.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentCollection;
    }

    public long countContentCollections() {
        return session.getDslContext()
                .selectCount()
                .from(ContentCollections)
                .join(ContentCollectionDetails).onKey(CONTENT_COLLECTIONS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContentCollectionsByDefaultOfferUse(OfferUse defaultOfferUse) {
        return session.getDslContext()
                .selectCount()
                .from(ContentCollections)
                .join(ContentCollectionDetails).onKey(CONTENT_COLLECTIONS_ACTIVE_DETAIL_FK)
                .where(ContentCollectionDetails.DEFAULT_OFFER_USE.eq(defaultOfferUse.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContentCollection */
    public ContentCollection getContentCollectionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContentCollectionPK(entityInstance.getEntityUniqueId());

        return contentCollectionFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContentCollection getContentCollectionByEntityInstance(EntityInstance entityInstance) {
        return getContentCollectionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContentCollection getContentCollectionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContentCollectionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public List<ContentCollection> getContentCollections() {
        var query = session.applyLimit(session.getDslContext()
                .select(ContentCollections.fields())
                .from(ContentCollections)
                .join(ContentCollectionDetails).onKey(CONTENT_COLLECTION_DETAILS_CONTENT_COLLECTION_FK)
                .where(ContentCollectionDetails.THRU_TIME.eq(Session.MAX_TIME))
                .orderBy(ContentCollectionDetails.CONTENT_COLLECTION_NAME),
                ContentCollectionFactory.class);

        return contentCollectionFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public ContentCollection getContentCollectionByName(String contentCollectionName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentCollections.fields())
                .from(ContentCollections)
                .join(ContentCollectionDetails).onKey(CONTENT_COLLECTION_DETAILS_CONTENT_COLLECTION_FK)
                .where(ContentCollectionDetails.CONTENT_COLLECTION_NAME.eq(contentCollectionName),
                        ContentCollectionDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentCollectionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentCollection getContentCollectionByName(String contentCollectionName) {
        return getContentCollectionByName(contentCollectionName, EntityPermission.READ_ONLY);
    }
    
    public ContentCollection getContentCollectionByNameForUpdate(String contentCollectionName) {
        return getContentCollectionByName(contentCollectionName, EntityPermission.READ_WRITE);
    }
    
    public ContentCollectionDetailValue getContentCollectionDetailValueForUpdate(ContentCollection contentCollection) {
        return contentCollection == null? null: contentCollection.getLastDetailForUpdate().getContentCollectionDetailValue().clone();
    }
    
    public ContentCollectionDetailValue getContentCollectionDetailValueByNameForUpdate(String contentCollectionName) {
        return getContentCollectionDetailValueForUpdate(getContentCollectionByNameForUpdate(contentCollectionName));
    }
    
    public ContentCollectionChoicesBean getContentCollectionChoices(String defaultContentCollectionChoice, Language language) {
        var contentCollections = getContentCollections();
        var size = contentCollections.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        for(var contentCollection : contentCollections) {
            var label = getBestContentCollectionDescription(contentCollection, language);
            var value = contentCollection.getLastDetail().getContentCollectionName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultContentCollectionChoice != null && defaultContentCollectionChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new ContentCollectionChoicesBean(labels, values, defaultValue);
    }
    
    public ContentCollectionTransfer getContentCollectionTransfer(UserVisit userVisit, ContentCollection contentCollection) {
        return contentCollectionTransferCache.getContentCollectionTransfer(userVisit, contentCollection);
    }
    
    public List<ContentCollectionTransfer> getContentCollectionTransfers(UserVisit userVisit, Collection<ContentCollection> contentCollections) {
        List<ContentCollectionTransfer> contentCollectionTransfers = new ArrayList<>(contentCollections.size());
        
        contentCollections.forEach((contentCollection) ->
                contentCollectionTransfers.add(contentCollectionTransferCache.getContentCollectionTransfer(userVisit, contentCollection))
        );
        
        return contentCollectionTransfers;
    }
    
    public List<ContentCollectionTransfer> getContentCollectionTransfers(UserVisit userVisit) {
        return getContentCollectionTransfers(userVisit, getContentCollections());
    }
    
    public void updateContentCollectionFromValue(ContentCollectionDetailValue contentCollectionDetailValue, BasePK updatedBy) {
        if(contentCollectionDetailValue.hasBeenModified()) {
            var contentCollection = contentCollectionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     contentCollectionDetailValue.getContentCollectionPK());
            var contentCollectionDetail = contentCollection.getActiveDetailForUpdate();

            contentCollectionDetail.setThruTime(session.getStartTime());
            contentCollectionDetail.store();

            var contentCollectionPK = contentCollectionDetail.getContentCollectionPK();
            var contentCollectionName = contentCollectionDetailValue.getContentCollectionName();
            var defaultOfferUsePK = contentCollectionDetailValue.getDefaultOfferUsePK();

            contentCollectionDetail = contentCollectionDetailFactory.create(contentCollectionPK,
                    contentCollectionName, defaultOfferUsePK, session.getStartTime(), Session.MAX_TIME);

            contentCollection.setActiveDetail(contentCollectionDetail);
            contentCollection.setLastDetail(contentCollectionDetail);
            contentCollection.store();

            sendEvent(contentCollectionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteContentCollection(ContentCollection contentCollection, BasePK deletedBy) {
        deleteContentSectionsByContentCollection(contentCollection, deletedBy);
        deleteContentCatalogsByContentCollection(contentCollection, deletedBy);
        deleteContentForumsByContentCollection(contentCollection, deletedBy);
        deleteContentWebAddressesByContentCollection(contentCollection, deletedBy);
        deleteContentCollectionDescriptionsByContentCollection(contentCollection, deletedBy);
        contentCollection.getLastDetailForUpdate().setThruTime(session.getStartTime());
        contentCollection.setActiveDetail(null);
        
        sendEvent(contentCollection.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Collection Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentCollectionDescriptionFactory contentCollectionDescriptionFactory;

    @Inject
    ContentCollectionDescriptionTransferCache contentCollectionDescriptionTransferCache;

    public ContentCollectionDescription createContentCollectionDescription(ContentCollection contentCollection, Language language, String description, BasePK createdBy) {

        var contentCollectionDescription = contentCollectionDescriptionFactory.create(contentCollection, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(contentCollection.getPrimaryKey(), EventTypes.MODIFY, contentCollectionDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentCollectionDescription;
    }
    
    private ContentCollectionDescription getContentCollectionDescription(ContentCollection contentCollection, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentCollectionDescriptions.fields())
                .from(ContentCollectionDescriptions)
                .where(ContentCollectionDescriptions.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                        ContentCollectionDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ContentCollectionDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentCollectionDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentCollectionDescription getContentCollectionDescription(ContentCollection contentCollection, Language language) {
        return getContentCollectionDescription(contentCollection, language, EntityPermission.READ_ONLY);
    }
    
    public ContentCollectionDescription getContentCollectionDescriptionForUpdate(ContentCollection contentCollection, Language language) {
        return getContentCollectionDescription(contentCollection, language, EntityPermission.READ_WRITE);
    }
    
    public ContentCollectionDescriptionValue getContentCollectionDescriptionValue(ContentCollectionDescription contentCollectionDescription) {
        return contentCollectionDescription == null? null: contentCollectionDescription.getContentCollectionDescriptionValue().clone();
    }
    
    public ContentCollectionDescriptionValue getContentCollectionDescriptionValueForUpdate(ContentCollection contentCollection, Language language) {
        return getContentCollectionDescriptionValue(getContentCollectionDescriptionForUpdate(contentCollection, language));
    }
    
    private List<ContentCollectionDescription> getContentCollectionDescriptionsByContentCollection(ContentCollection contentCollection, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCollectionDescriptions.fields())
                    .from(ContentCollectionDescriptions)
                    .join(Languages).onKey(CONTENT_COLLECTION_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ContentCollectionDescriptions.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                            ContentCollectionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ContentCollectionDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCollectionDescriptions.fields())
                    .from(ContentCollectionDescriptions)
                    .where(ContentCollectionDescriptions.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                            ContentCollectionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentCollectionDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentCollectionDescription> getContentCollectionDescriptionsByContentCollection(ContentCollection contentCollection) {
        return getContentCollectionDescriptionsByContentCollection(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCollectionDescription> getContentCollectionDescriptionsByContentCollectionForUpdate(ContentCollection contentCollection) {
        return getContentCollectionDescriptionsByContentCollection(contentCollection, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentCollectionDescription(ContentCollection contentCollection, Language language) {
        String description;
        var contentCollectionDescription = getContentCollectionDescription(contentCollection, language);
        
        if(contentCollectionDescription == null && !language.getIsDefault()) {
            contentCollectionDescription = getContentCollectionDescription(contentCollection, partyControl.getDefaultLanguage());
        }
        
        if(contentCollectionDescription == null) {
            description = contentCollection.getLastDetail().getContentCollectionName();
        } else {
            description = contentCollectionDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentCollectionDescriptionTransfer getContentCollectionDescriptionTransfer(UserVisit userVisit, ContentCollectionDescription contentCollectionDescription) {
        return contentCollectionDescriptionTransferCache.getContentCollectionDescriptionTransfer(userVisit, contentCollectionDescription);
    }
    
    public List<ContentCollectionDescriptionTransfer> getContentCollectionDescriptionTransfers(UserVisit userVisit, ContentCollection contentCollection) {
        var contentCollectionDescriptions = getContentCollectionDescriptionsByContentCollection(contentCollection);
        List<ContentCollectionDescriptionTransfer> contentCollectionDescriptionTransfers = new ArrayList<>(contentCollectionDescriptions.size());
        
        for(var contentCollectionDescription : contentCollectionDescriptions) {
            contentCollectionDescriptionTransfers.add(contentCollectionDescriptionTransferCache.getContentCollectionDescriptionTransfer(userVisit, contentCollectionDescription));
        }
        
        return contentCollectionDescriptionTransfers;
    }
    
    public void updateContentCollectionDescriptionFromValue(ContentCollectionDescriptionValue contentCollectionDescriptionValue, BasePK updatedBy) {
        if(contentCollectionDescriptionValue.hasBeenModified()) {
            var contentCollectionDescription = contentCollectionDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentCollectionDescriptionValue.getPrimaryKey());
            
            contentCollectionDescription.setThruTime(session.getStartTime());
            contentCollectionDescription.store();

            var contentCollection = contentCollectionDescription.getContentCollection();
            var language = contentCollectionDescription.getLanguage();
            var description = contentCollectionDescriptionValue.getDescription();
            
            contentCollectionDescription = contentCollectionDescriptionFactory.create(contentCollection, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contentCollection.getPrimaryKey(), EventTypes.MODIFY, contentCollectionDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentCollectionDescription(ContentCollectionDescription contentCollectionDescription, BasePK deletedBy) {
        contentCollectionDescription.setThruTime(session.getStartTime());
        
        sendEvent(contentCollectionDescription.getContentCollectionPK(), EventTypes.MODIFY, contentCollectionDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentCollectionDescriptionsByContentCollection(ContentCollection contentCollection, BasePK deletedBy) {
        var contentCollectionDescriptions = getContentCollectionDescriptionsByContentCollectionForUpdate(contentCollection);
        
        contentCollectionDescriptions.forEach((contentCollectionDescription) -> 
                deleteContentCollectionDescription(contentCollectionDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Content Sections
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentSectionDetailFactory contentSectionDetailFactory;

    @Inject
    protected ContentSectionFactory contentSectionFactory;

    @Inject
    ContentSectionTransferCache contentSectionTransferCache;

    public ContentSection createContentSection(ContentCollection contentCollection, String contentSectionName,
            ContentSection parentContentSection, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultContentSection = getDefaultContentSection(contentCollection);
        var defaultFound = defaultContentSection != null;
        
        if(defaultFound) {
            if(defaultContentSection.getLastDetail().getContentSectionName().equals(com.echothree.model.control.content.common.ContentSections.ROOT.toString())) {
                isDefault = true;
            }
        }
        
        if(defaultFound && isDefault) {
            var defaultContentSectionDetailValue = getDefaultContentSectionDetailValueForUpdate(contentCollection);
            
            defaultContentSectionDetailValue.setIsDefault(false);
            updateContentSectionFromValue(defaultContentSectionDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var contentSection = contentSectionFactory.create();
        var contentSectionDetail = contentSectionDetailFactory.create(contentSection, contentCollection, contentSectionName, parentContentSection, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        contentSection = contentSectionFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentSection.getPrimaryKey());
        contentSection.setActiveDetail(contentSectionDetail);
        contentSection.setLastDetail(contentSectionDetail);
        contentSection.store();
        
        sendEvent(contentSection.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentSection;
    }

    public long countContentSectionsByContentCollection(ContentCollection contentCollection) {
        return session.getDslContext()
                .selectCount()
                .from(ContentSections)
                .join(ContentSectionDetails).onKey(CONTENT_SECTIONS_ACTIVE_DETAIL_FK)
                .where(ContentSectionDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContentSectionsByParentContentSection(ContentSection parentContentSection) {
        return session.getDslContext()
                .selectCount()
                .from(ContentSections)
                .join(ContentSectionDetails).onKey(CONTENT_SECTIONS_ACTIVE_DETAIL_FK)
                .where(ContentSectionDetails.PARENT_CONTENT_SECTION.eq(parentContentSection.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<ContentSection> getContentSections(ContentCollection contentCollection, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentSections.fields())
                    .from(ContentSections)
                    .join(ContentSectionDetails).onKey(CONTENT_SECTION_DETAILS_CONTENT_SECTION_FK)
                    .where(ContentSectionDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                            ContentSectionDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentSectionDetails.SORT_ORDER, ContentSectionDetails.CONTENT_SECTION_NAME),
                    ContentSectionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentSections.fields())
                    .from(ContentSections)
                    .join(ContentSectionDetails).onKey(CONTENT_SECTION_DETAILS_CONTENT_SECTION_FK)
                    .where(ContentSectionDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                            ContentSectionDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentSectionDetails.SORT_ORDER, ContentSectionDetails.CONTENT_SECTION_NAME)
                    .forUpdate();
        };

        return contentSectionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentSection> getContentSections(ContentCollection contentCollection) {
        return getContentSections(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentSection> getContentSectionsForUpdate(ContentCollection contentCollection) {
        return getContentSections(contentCollection, EntityPermission.READ_WRITE);
    }
    
    private List<ContentSection> getContentSectionsByParentContentSection(ContentSection parentContentSection, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentSections.fields())
                    .from(ContentSections)
                    .join(ContentSectionDetails).onKey(CONTENT_SECTION_DETAILS_CONTENT_SECTION_FK)
                    .where(ContentSectionDetails.PARENT_CONTENT_SECTION.eq(parentContentSection.getPrimaryKey()),
                            ContentSectionDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentSectionDetails.SORT_ORDER, ContentSectionDetails.CONTENT_SECTION_NAME),
                    ContentSectionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentSections.fields())
                    .from(ContentSections)
                    .join(ContentSectionDetails).onKey(CONTENT_SECTION_DETAILS_CONTENT_SECTION_FK)
                    .where(ContentSectionDetails.PARENT_CONTENT_SECTION.eq(parentContentSection.getPrimaryKey()),
                            ContentSectionDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentSectionDetails.SORT_ORDER, ContentSectionDetails.CONTENT_SECTION_NAME)
                    .forUpdate();
        };

        return contentSectionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentSection> getContentSectionsByParentContentSection(ContentSection parentContentSection) {
        return getContentSectionsByParentContentSection(parentContentSection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentSection> getContentSectionsByParentContentSectionForUpdate(ContentSection parentContentSection) {
        return getContentSectionsByParentContentSection(parentContentSection, EntityPermission.READ_WRITE);
    }
    
    private ContentSection getContentSectionByName(ContentCollection contentCollection, String contentSectionName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentSections.fields())
                .from(ContentSections)
                .join(ContentSectionDetails).onKey(CONTENT_SECTION_DETAILS_CONTENT_SECTION_FK)
                .where(ContentSectionDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                        ContentSectionDetails.CONTENT_SECTION_NAME.eq(contentSectionName),
                        ContentSectionDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentSectionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentSection getContentSectionByName(ContentCollection contentCollection, String contentSectionName) {
        return getContentSectionByName(contentCollection, contentSectionName, EntityPermission.READ_ONLY);
    }
    
    public ContentSection getContentSectionByNameForUpdate(ContentCollection contentCollection, String contentSectionName) {
        return getContentSectionByName(contentCollection, contentSectionName, EntityPermission.READ_WRITE);
    }
    
    public ContentSectionDetailValue getContentSectionDetailValueForUpdate(ContentSection contentSection) {
        return contentSection == null? null: contentSection.getLastDetailForUpdate().getContentSectionDetailValue().clone();
    }
    
    public ContentSectionDetailValue getContentSectionDetailValueByNameForUpdate(ContentCollection contentCollection, String contentSectionName) {
        return getContentSectionDetailValueForUpdate(getContentSectionByNameForUpdate(contentCollection, contentSectionName));
    }
    
    private ContentSection getDefaultContentSection(ContentCollection contentCollection, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentSections.fields())
                .from(ContentSections)
                .join(ContentSectionDetails).onKey(CONTENT_SECTION_DETAILS_CONTENT_SECTION_FK)
                .where(ContentSectionDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                        ContentSectionDetails.IS_DEFAULT.eq(true),
                        ContentSectionDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentSectionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentSection getDefaultContentSection(ContentCollection contentCollection) {
        return getDefaultContentSection(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public ContentSectionDetailValue getDefaultContentSectionDetailValueForUpdate(ContentCollection contentCollection) {
        return getDefaultContentSection(contentCollection, EntityPermission.READ_WRITE).getLastDetailForUpdate().getContentSectionDetailValue().clone();
    }
    
    public ContentSectionChoicesBean getContentSectionChoices(ContentCollection contentCollection, String defaultContentSectionChoice, Language language, boolean allowNullChoice) {
        var contentSections = getContentSections(contentCollection);
        var size = contentSections.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultContentSectionChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var contentSection : contentSections) {
            var contentSectionDetail = contentSection.getLastDetail();
            var value = contentSectionDetail.getContentSectionName();
            
            if(!value.equals(com.echothree.model.control.content.common.ContentSections.ROOT.toString())) {
                var label = getBestContentSectionDescription(contentSection, language);

                labels.add(label == null? value: label);
                values.add(value);

                var usingDefaultChoice = Objects.equals(defaultContentSectionChoice, value);
                if(usingDefaultChoice || (defaultValue == null && contentSectionDetail.getIsDefault())) {
                    defaultValue = value;
                }
            }
        }
        
        return new ContentSectionChoicesBean(labels, values, defaultValue);
    }
    
    public ContentSectionTransfer getContentSectionTransfer(UserVisit userVisit, ContentSection contentSection) {
        return contentSectionTransferCache.getContentSectionTransfer(userVisit, contentSection);
    }
    
    public List<ContentSectionTransfer> getContentSectionTransfers(UserVisit userVisit, Collection<ContentSection> contentSections) {
        List<ContentSectionTransfer> contentSectionTransfers = new ArrayList<>(contentSections.size());
        
        contentSections.forEach((contentSection) ->
                contentSectionTransfers.add(contentSectionTransferCache.getContentSectionTransfer(userVisit, contentSection))
        );
        
        return contentSectionTransfers;
    }
    
    public List<ContentSectionTransfer> getContentSectionTransfers(UserVisit userVisit, ContentCollection contentCollection) {
        return getContentSectionTransfers(userVisit, getContentSections(contentCollection));
    }
    
    public List<ContentSectionTransfer> getContentSectionTransfersByParentContentSection(UserVisit userVisit, ContentSection parentContentSection) {
        return getContentSectionTransfers(userVisit, getContentSectionsByParentContentSection(parentContentSection));
    }
    
    public boolean isParentContentSectionSafe(ContentSection contentSection, ContentSection parentContentSection) {
        var safe = true;
        
        if(parentContentSection != null) {
            Set<ContentSection> parentItemPurchasingCategories = new HashSet<>();
            
            parentItemPurchasingCategories.add(contentSection);
            do {
                if(parentItemPurchasingCategories.contains(parentContentSection)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategories.add(parentContentSection);
                parentContentSection = parentContentSection.getLastDetail().getParentContentSection();
            } while(parentContentSection != null);
        }
        
        return safe;
    }
    
    private void updateContentSectionFromValue(ContentSectionDetailValue contentSectionDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(contentSectionDetailValue.hasBeenModified()) {
            var contentSection = contentSectionFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentSectionDetailValue.getContentSectionPK());
            var contentSectionDetail = contentSection.getActiveDetailForUpdate();

            contentSectionDetail.setThruTime(session.getStartTime());
            contentSectionDetail.store();

            var contentSectionPK = contentSectionDetail.getContentSectionPK();
            var contentCollection = contentSectionDetail.getContentCollection();
            var contentCollectionPK = contentSectionDetail.getContentCollectionPK();
            var contentSectionName = contentSectionDetailValue.getContentSectionName();
            var parentContentSectionPK = contentSectionDetailValue.getParentContentSectionPK();
            var isDefault = contentSectionDetailValue.getIsDefault();
            var sortOrder = contentSectionDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultContentSection = getDefaultContentSection(contentCollection);
                var defaultFound = defaultContentSection != null && !defaultContentSection.equals(contentSection);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultContentSectionDetailValue = getDefaultContentSectionDetailValueForUpdate(contentCollection);

                    defaultContentSectionDetailValue.setIsDefault(false);
                    updateContentSectionFromValue(defaultContentSectionDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            contentSectionDetail = contentSectionDetailFactory.create(contentSectionPK, contentCollectionPK, contentSectionName, parentContentSectionPK, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            contentSection.setActiveDetail(contentSectionDetail);
            contentSection.setLastDetail(contentSectionDetail);
            contentSection.store();

            sendEvent(contentSectionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateContentSectionFromValue(ContentSectionDetailValue contentSectionDetailValue, BasePK updatedBy) {
        updateContentSectionFromValue(contentSectionDetailValue, true, updatedBy);
    }
    
    private void deleteContentSection(ContentSection contentSection, BasePK deletedBy, boolean recursive) {
        if(recursive) {
            var subContentSections = getContentSectionsByParentContentSectionForUpdate(contentSection);
            
            subContentSections.forEach((subContentSection) -> {
                deleteContentSection(subContentSection, deletedBy, true);
            });
        }
        
        deleteContentPagesByContentSection(contentSection, deletedBy);
        deleteContentSectionDescriptionsByContentSection(contentSection, deletedBy);

        var contentSectionDetail = contentSection.getLastDetailForUpdate();
        contentSectionDetail.setThruTime(session.getStartTime());
        contentSectionDetail.store();
        contentSection.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var contentCollection = contentSectionDetail.getContentCollection();
        var defaultContentSection = getDefaultContentSection(contentCollection);
        if(defaultContentSection == null) {
            // Try and find a new default from sections on the same level as the one that was deleted
            var parentContentSection = contentSectionDetail.getParentContentSection();
            var contentSections = parentContentSection == null? null: getContentSectionsByParentContentSectionForUpdate(parentContentSection);
            
            // If that failed, pick one from the current collection
            if(contentSections == null || contentSections.isEmpty()) {
                contentSections = getContentSectionsForUpdate(contentCollection);
            }
            
            if(!contentSections.isEmpty()) {
                for(var iter = contentSections.iterator(); iter.hasNext();) {
                    defaultContentSection = iter.next();
                    if(!defaultContentSection.getLastDetail().getContentSectionName().equals(com.echothree.model.control.content.common.ContentSections.ROOT.toString())) {
                        break;
                    }
                }
                var contentSectionDetailValue = Objects.requireNonNull(defaultContentSection).getLastDetailForUpdate().getContentSectionDetailValue().clone();
                
                contentSectionDetailValue.setIsDefault(true);
                updateContentSectionFromValue(contentSectionDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(contentSection.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteContentSection(ContentSection contentSection, BasePK deletedBy) {
        deleteContentSection(contentSection, deletedBy, true);
    }
    
    public void deleteContentSectionsByContentCollection(ContentCollection contentCollection, BasePK deletedBy) {
        var contentSections = getContentSectionsForUpdate(contentCollection);
        
        contentSections.forEach((contentSection) -> {
            deleteContentSection(contentSection, deletedBy, false);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Section Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentSectionDescriptionFactory contentSectionDescriptionFactory;

    @Inject
    ContentSectionDescriptionTransferCache contentSectionDescriptionTransferCache;

    public ContentSectionDescription createContentSectionDescription(ContentSection contentSection, Language language, String description, BasePK createdBy) {

        var contentSectionDescription = contentSectionDescriptionFactory.create(contentSection, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(contentSection.getPrimaryKey(), EventTypes.MODIFY, contentSectionDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentSectionDescription;
    }
    
    private ContentSectionDescription getContentSectionDescription(ContentSection contentSection, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentSectionDescriptions.fields())
                .from(ContentSectionDescriptions)
                .where(ContentSectionDescriptions.CONTENT_SECTION.eq(contentSection.getPrimaryKey()),
                        ContentSectionDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ContentSectionDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentSectionDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentSectionDescription getContentSectionDescription(ContentSection contentSection, Language language) {
        return getContentSectionDescription(contentSection, language, EntityPermission.READ_ONLY);
    }
    
    public ContentSectionDescription getContentSectionDescriptionForUpdate(ContentSection contentSection, Language language) {
        return getContentSectionDescription(contentSection, language, EntityPermission.READ_WRITE);
    }
    
    public ContentSectionDescriptionValue getContentSectionDescriptionValue(ContentSectionDescription contentSectionDescription) {
        return contentSectionDescription == null? null: contentSectionDescription.getContentSectionDescriptionValue().clone();
    }
    
    public ContentSectionDescriptionValue getContentSectionDescriptionValueForUpdate(ContentSection contentSection, Language language) {
        return getContentSectionDescriptionValue(getContentSectionDescriptionForUpdate(contentSection, language));
    }
    
    private List<ContentSectionDescription> getContentSectionDescriptionsByContentSection(ContentSection contentSection, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentSectionDescriptions.fields())
                    .from(ContentSectionDescriptions)
                    .join(Languages).onKey(CONTENT_SECTION_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ContentSectionDescriptions.CONTENT_SECTION.eq(contentSection.getPrimaryKey()),
                            ContentSectionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ContentSectionDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentSectionDescriptions.fields())
                    .from(ContentSectionDescriptions)
                    .where(ContentSectionDescriptions.CONTENT_SECTION.eq(contentSection.getPrimaryKey()),
                            ContentSectionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentSectionDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentSectionDescription> getContentSectionDescriptionsByContentSection(ContentSection contentSection) {
        return getContentSectionDescriptionsByContentSection(contentSection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentSectionDescription> getContentSectionDescriptionsByContentSectionForUpdate(ContentSection contentSection) {
        return getContentSectionDescriptionsByContentSection(contentSection, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentSectionDescription(ContentSection contentSection, Language language) {
        String description;
        var contentSectionDescription = getContentSectionDescription(contentSection, language);
        
        if(contentSectionDescription == null && !language.getIsDefault()) {
            contentSectionDescription = getContentSectionDescription(contentSection, partyControl.getDefaultLanguage());
        }
        
        if(contentSectionDescription == null) {
            description = contentSection.getLastDetail().getContentSectionName();
        } else {
            description = contentSectionDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentSectionDescriptionTransfer getContentSectionDescriptionTransfer(UserVisit userVisit, ContentSectionDescription contentSectionDescription) {
        return contentSectionDescriptionTransferCache.getContentSectionDescriptionTransfer(userVisit, contentSectionDescription);
    }
    
    public List<ContentSectionDescriptionTransfer> getContentSectionDescriptionTransfers(UserVisit userVisit, ContentSection contentSection) {
        var contentSectionDescriptions = getContentSectionDescriptionsByContentSection(contentSection);
        List<ContentSectionDescriptionTransfer> contentSectionDescriptionTransfers = new ArrayList<>(contentSectionDescriptions.size());
            
        contentSectionDescriptions.forEach((contentSectionDescription) ->
                contentSectionDescriptionTransfers.add(contentSectionDescriptionTransferCache.getContentSectionDescriptionTransfer(userVisit, contentSectionDescription))
        );
        
        return contentSectionDescriptionTransfers;
    }
    
    public void updateContentSectionDescriptionFromValue(ContentSectionDescriptionValue contentSectionDescriptionValue, BasePK updatedBy) {
        if(contentSectionDescriptionValue.hasBeenModified()) {
            var contentSectionDescription = contentSectionDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentSectionDescriptionValue.getPrimaryKey());
            
            contentSectionDescription.setThruTime(session.getStartTime());
            contentSectionDescription.store();

            var contentSection = contentSectionDescription.getContentSection();
            var language = contentSectionDescription.getLanguage();
            var description = contentSectionDescriptionValue.getDescription();
            
            contentSectionDescription = contentSectionDescriptionFactory.create(contentSection, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contentSection.getPrimaryKey(), EventTypes.MODIFY, contentSectionDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentSectionDescription(ContentSectionDescription contentSectionDescription, BasePK deletedBy) {
        contentSectionDescription.setThruTime(session.getStartTime());
        
        sendEvent(contentSectionDescription.getContentSectionPK(), EventTypes.MODIFY, contentSectionDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentSectionDescriptionsByContentSection(ContentSection contentSection, BasePK deletedBy) {
        getContentSectionDescriptionsByContentSectionForUpdate(contentSection).forEach((contentSectionDescription) -> {
            deleteContentSectionDescription(contentSectionDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Pages
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentPageDetailFactory contentPageDetailFactory;

    @Inject
    protected ContentPageFactory contentPageFactory;

    @Inject
    ContentPageTransferCache contentPageTransferCache;

    public ContentPage createContentPage(ContentSection contentSection, String contentPageName, ContentPageLayout contentPageLayout, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultFound = getDefaultContentPage(contentSection) != null;
        
        if(defaultFound && isDefault) {
            var defaultContentPageDetailValue = getDefaultContentPageDetailValueForUpdate(contentSection);
            
            defaultContentPageDetailValue.setIsDefault(false);
            updateContentPageFromValue(defaultContentPageDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var contentPage = contentPageFactory.create();
        var contentPageDetail = contentPageDetailFactory.create(contentPage, contentSection, contentPageName, contentPageLayout, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        contentPage = contentPageFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentPage.getPrimaryKey());
        contentPage.setActiveDetail(contentPageDetail);
        contentPage.setLastDetail(contentPageDetail);
        contentPage.store();
        
        sendEvent(contentPage.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentPage;
    }
    
    public long countContentPagesByContentSection(ContentSection contentSection) {
        return session.getDslContext()
                .selectCount()
                .from(ContentPageDetails)
                .where(ContentPageDetails.CONTENT_SECTION.eq(contentSection.getPrimaryKey()),
                        ContentPageDetails.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContentPage */
    public ContentPage getContentPageByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContentPagePK(entityInstance.getEntityUniqueId());

        return contentPageFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContentPage getContentPageByEntityInstance(EntityInstance entityInstance) {
        return getContentPageByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContentPage getContentPageByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContentPageByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<ContentPage> getContentPagesByContentSection(ContentSection contentSection, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentPages.fields())
                    .from(ContentPages)
                    .join(ContentPageDetails).onKey(CONTENT_PAGE_DETAILS_CONTENT_PAGE_FK)
                    .where(ContentPageDetails.CONTENT_SECTION.eq(contentSection.getPrimaryKey()),
                            ContentPageDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentPageDetails.SORT_ORDER, ContentPageDetails.CONTENT_PAGE_NAME),
                    ContentPageFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentPages.fields())
                    .from(ContentPages)
                    .join(ContentPageDetails).onKey(CONTENT_PAGE_DETAILS_CONTENT_PAGE_FK)
                    .where(ContentPageDetails.CONTENT_SECTION.eq(contentSection.getPrimaryKey()),
                            ContentPageDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentPageDetails.SORT_ORDER, ContentPageDetails.CONTENT_PAGE_NAME)
                    .forUpdate();
        };

        return contentPageFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentPage> getContentPagesByContentSection(ContentSection contentSection) {
        return getContentPagesByContentSection(contentSection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentPage> getContentPagesByContentSectionForUpdate(ContentSection contentSection) {
        return getContentPagesByContentSection(contentSection, EntityPermission.READ_WRITE);
    }
    
    private List<ContentPage> getContentPagesByContentPageLayout(ContentPageLayout contentPageLayout, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentPages.fields())
                    .from(ContentPages)
                    .join(ContentPageDetails).onKey(CONTENT_PAGES_ACTIVE_DETAIL_FK)
                    .join(ContentSections).onKey(CONTENT_PAGE_DETAILS_CONTENT_SECTION_FK)
                    .join(ContentSectionDetails).onKey(CONTENT_SECTIONS_LAST_DETAIL_FK)
                    .where(ContentPageDetails.CONTENT_PAGE_LAYOUT.eq(contentPageLayout.getPrimaryKey()))
                    .orderBy(ContentSectionDetails.SORT_ORDER, ContentSectionDetails.CONTENT_SECTION_NAME, ContentPageDetails.SORT_ORDER, ContentPageDetails.CONTENT_PAGE_NAME),
                    ContentPageFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentPages.fields())
                    .from(ContentPages)
                    .join(ContentPageDetails).onKey(CONTENT_PAGES_ACTIVE_DETAIL_FK)
                    .where(ContentPageDetails.CONTENT_PAGE_LAYOUT.eq(contentPageLayout.getPrimaryKey()))
                    .orderBy(ContentPageDetails.SORT_ORDER, ContentPageDetails.CONTENT_PAGE_NAME)
                    .forUpdate();
        };

        return contentPageFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentPage> getContentPagesByContentPageLayout(ContentPageLayout contentPageLayout) {
        return getContentPagesByContentPageLayout(contentPageLayout, EntityPermission.READ_ONLY);
    }
    
    public List<ContentPage> getContentPagesByContentPageLayoutForUpdate(ContentPageLayout contentPageLayout) {
        return getContentPagesByContentPageLayout(contentPageLayout, EntityPermission.READ_WRITE);
    }
    
    private ContentPage getContentPageByName(ContentSection contentSection, String contentPageName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentPages.fields())
                .from(ContentPages)
                .join(ContentPageDetails).onKey(CONTENT_PAGE_DETAILS_CONTENT_PAGE_FK)
                .where(ContentPageDetails.CONTENT_SECTION.eq(contentSection.getPrimaryKey()),
                        ContentPageDetails.CONTENT_PAGE_NAME.eq(contentPageName),
                        ContentPageDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentPageFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentPage getContentPageByName(ContentSection contentSection, String contentPageName) {
        return getContentPageByName(contentSection, contentPageName, EntityPermission.READ_ONLY);
    }
    
    public ContentPage getContentPageByNameForUpdate(ContentSection contentSection, String contentPageName) {
        return getContentPageByName(contentSection, contentPageName, EntityPermission.READ_WRITE);
    }
    
    public ContentPageDetailValue getContentPageDetailValueForUpdate(ContentPage contentPage) {
        return contentPage == null? null: contentPage.getLastDetailForUpdate().getContentPageDetailValue().clone();
    }
    
    public ContentPageDetailValue getContentPageDetailValueByNameForUpdate(ContentSection contentSection, String contentPageName) {
        return getContentPageDetailValueForUpdate(getContentPageByNameForUpdate(contentSection, contentPageName));
    }
    
    private ContentPage getDefaultContentPage(ContentSection contentSection, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentPages.fields())
                .from(ContentPages)
                .join(ContentPageDetails).onKey(CONTENT_PAGE_DETAILS_CONTENT_PAGE_FK)
                .where(ContentPageDetails.CONTENT_SECTION.eq(contentSection.getPrimaryKey()),
                        ContentPageDetails.IS_DEFAULT.eq(true),
                        ContentPageDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentPageFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentPage getDefaultContentPage(ContentSection contentSection) {
        return getDefaultContentPage(contentSection, EntityPermission.READ_ONLY);
    }
    
    public ContentPageDetailValue getDefaultContentPageDetailValueForUpdate(ContentSection contentSection) {
        return getDefaultContentPage(contentSection, EntityPermission.READ_WRITE).getLastDetailForUpdate().getContentPageDetailValue().clone();
    }
    
    public ContentPageTransfer getContentPageTransfer(UserVisit userVisit, ContentPage contentPage) {
        return contentPageTransferCache.getContentPageTransfer(userVisit, contentPage);
    }
    
    public List<ContentPageTransfer> getContentPageTransfers(UserVisit userVisit, Collection<ContentPage> contentPages) {
        List<ContentPageTransfer> contentPageTransfers = new ArrayList<>(contentPages.size());
            
        contentPages.forEach((contentPage) ->
                contentPageTransfers.add(contentPageTransferCache.getContentPageTransfer(userVisit, contentPage))
        );
        
        return contentPageTransfers;
    }
    
    public List<ContentPageTransfer> getContentPageTransfers(UserVisit userVisit, ContentSection contentSection) {
        return getContentPageTransfers(userVisit, getContentPagesByContentSection(contentSection));
    }
    
    private void updateContentPageFromValue(ContentPageDetailValue contentPageDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(contentPageDetailValue.hasBeenModified()) {
            var contentPage = contentPageFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentPageDetailValue.getContentPagePK());
            var contentPageDetail = contentPage.getActiveDetailForUpdate();

            contentPageDetail.setThruTime(session.getStartTime());
            contentPageDetail.store();

            var contentPagePK = contentPage.getPrimaryKey();
            var contentSection = contentPageDetail.getContentSection();
            var contentSectionPK = contentSection.getPrimaryKey();
            var contentPageName = contentPageDetailValue.getContentPageName();
            var contentPageLayoutPK = contentPageDetailValue.getContentPageLayoutPK();
            var isDefault = contentPageDetailValue.getIsDefault();
            var sortOrder = contentPageDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultContentPage = getDefaultContentPage(contentSection);
                var defaultFound = defaultContentPage != null && !defaultContentPage.equals(contentPage);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultContentPageDetailValue = getDefaultContentPageDetailValueForUpdate(contentSection);

                    defaultContentPageDetailValue.setIsDefault(false);
                    updateContentPageFromValue(defaultContentPageDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            // If the Content Page Layout was modified, then all the Content Page Areas associated with the page
            // are deleted, since there's a good chance that they will not match up correctly with the new layout.
            if(contentPageDetailValue.getContentPageLayoutPKHasBeenModified()) {
                deleteContentPageAreasByContentPage(contentPage, updatedBy);
            }

            contentPageDetail = contentPageDetailFactory.create(contentPagePK, contentSectionPK, contentPageName, contentPageLayoutPK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            contentPage.setActiveDetail(contentPageDetail);
            contentPage.setLastDetail(contentPageDetail);
            contentPage.store();

            sendEvent(contentPagePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateContentPageFromValue(ContentPageDetailValue contentPageDetailValue, BasePK updatedBy) {
        updateContentPageFromValue(contentPageDetailValue, true, updatedBy);
    }
    
    public void deleteContentPage(ContentPage contentPage, BasePK deletedBy) {
        deleteContentPageAreasByContentPage(contentPage, deletedBy);
        deleteContentPageDescriptionsByContentPage(contentPage, deletedBy);

        var contentPageDetail = contentPage.getLastDetailForUpdate();
        contentPageDetail.setThruTime(session.getStartTime());
        contentPageDetail.store();
        contentPage.setActiveDetail(null);

        var contentSection = contentPageDetail.getContentSection();
        var defaultContentPage = getDefaultContentPage(contentSection);
        if(defaultContentPage == null) {
            var contentPages = getContentPagesByContentSectionForUpdate(contentSection);
            
            if(!contentPages.isEmpty()) {
                defaultContentPage = contentPages.iterator().next();
                var contentPageDetailValue = Objects.requireNonNull(defaultContentPage).getLastDetailForUpdate().getContentPageDetailValue().clone();
                
                contentPageDetailValue.setIsDefault(true);
                updateContentPageFromValue(contentPageDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(contentPage.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteContentPagesByContentSection(ContentSection contentSection, BasePK deletedBy) {
        getContentPagesByContentSectionForUpdate(contentSection).forEach((contentPage) -> {
            deleteContentPage(contentPage, deletedBy);
        });
    }
    
    public void deleteContentPagesByContentPageLayout(ContentPageLayout contentPageLayout, BasePK deletedBy) {
        getContentPagesByContentPageLayoutForUpdate(contentPageLayout).forEach((contentPage) -> {
            deleteContentPage(contentPage, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentPageDescriptionFactory contentPageDescriptionFactory;

    @Inject
    ContentPageDescriptionTransferCache contentPageDescriptionTransferCache;

    public ContentPageDescription createContentPageDescription(ContentPage contentPage, Language language, String description, BasePK createdBy) {
        var contentPageDescription = contentPageDescriptionFactory.create(contentPage, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(contentPage.getPrimaryKey(), EventTypes.MODIFY, contentPageDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentPageDescription;
    }
    
    private ContentPageDescription getContentPageDescription(ContentPage contentPage, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentPageDescriptions.fields())
                .from(ContentPageDescriptions)
                .where(ContentPageDescriptions.CONTENT_PAGE.eq(contentPage.getPrimaryKey()),
                        ContentPageDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ContentPageDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentPageDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentPageDescription getContentPageDescription(ContentPage contentPage, Language language) {
        return getContentPageDescription(contentPage, language, EntityPermission.READ_ONLY);
    }
    
    public ContentPageDescription getContentPageDescriptionForUpdate(ContentPage contentPage, Language language) {
        return getContentPageDescription(contentPage, language, EntityPermission.READ_WRITE);
    }
    
    public ContentPageDescriptionValue getContentPageDescriptionValue(ContentPageDescription contentPageDescription) {
        return contentPageDescription == null? null: contentPageDescription.getContentPageDescriptionValue().clone();
    }
    
    public ContentPageDescriptionValue getContentPageDescriptionValueForUpdate(ContentPage contentPage, Language language) {
        return getContentPageDescriptionValue(getContentPageDescriptionForUpdate(contentPage, language));
    }
    
    private List<ContentPageDescription> getContentPageDescriptionsByContentPage(ContentPage contentPage, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentPageDescriptions.fields())
                    .from(ContentPageDescriptions)
                    .join(Languages).onKey(CONTENT_PAGE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ContentPageDescriptions.CONTENT_PAGE.eq(contentPage.getPrimaryKey()),
                            ContentPageDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ContentPageDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentPageDescriptions.fields())
                    .from(ContentPageDescriptions)
                    .where(ContentPageDescriptions.CONTENT_PAGE.eq(contentPage.getPrimaryKey()),
                            ContentPageDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentPageDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentPageDescription> getContentPageDescriptionsByContentPage(ContentPage contentPage) {
        return getContentPageDescriptionsByContentPage(contentPage, EntityPermission.READ_ONLY);
    }
    
    public List<ContentPageDescription> getContentPageDescriptionsByContentPageForUpdate(ContentPage contentPage) {
        return getContentPageDescriptionsByContentPage(contentPage, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentPageDescription(ContentPage contentPage, Language language) {
        String description;
        var contentPageDescription = getContentPageDescription(contentPage, language);
        
        if(contentPageDescription == null && !language.getIsDefault()) {
            contentPageDescription = getContentPageDescription(contentPage, partyControl.getDefaultLanguage());
        }
        
        if(contentPageDescription == null) {
            description = contentPage.getLastDetail().getContentPageName();
        } else {
            description = contentPageDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentPageDescriptionTransfer getContentPageDescriptionTransfer(UserVisit userVisit, ContentPageDescription contentPageDescription) {
        return contentPageDescriptionTransferCache.getContentPageDescriptionTransfer(userVisit, contentPageDescription);
    }
    
    public List<ContentPageDescriptionTransfer> getContentPageDescriptionTransfers(UserVisit userVisit, ContentPage contentPage) {
        var contentPageDescriptions = getContentPageDescriptionsByContentPage(contentPage);
        List<ContentPageDescriptionTransfer> contentPageDescriptionTransfers = new ArrayList<>(contentPageDescriptions.size());
        
        contentPageDescriptions.forEach((contentPageDescription) ->
                contentPageDescriptionTransfers.add(contentPageDescriptionTransferCache.getContentPageDescriptionTransfer(userVisit, contentPageDescription))
        );
        
        return contentPageDescriptionTransfers;
    }
    
    public void updateContentPageDescriptionFromValue(ContentPageDescriptionValue contentPageDescriptionValue, BasePK updatedBy) {
        if(contentPageDescriptionValue.hasBeenModified()) {
            var contentPageDescription = contentPageDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentPageDescriptionValue.getPrimaryKey());
            
            contentPageDescription.setThruTime(session.getStartTime());
            contentPageDescription.store();

            var contentPage = contentPageDescription.getContentPage();
            var language = contentPageDescription.getLanguage();
            var description = contentPageDescriptionValue.getDescription();
            
            contentPageDescription = contentPageDescriptionFactory.create(contentPage, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contentPage.getPrimaryKey(), EventTypes.MODIFY, contentPageDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentPageDescription(ContentPageDescription contentPageDescription, BasePK deletedBy) {
        contentPageDescription.setThruTime(session.getStartTime());
        
        sendEvent(contentPageDescription.getContentPagePK(), EventTypes.MODIFY, contentPageDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentPageDescriptionsByContentPage(ContentPage contentPage, BasePK deletedBy) {
        getContentPageDescriptionsByContentPageForUpdate(contentPage).forEach((contentPageDescription) -> {
            deleteContentPageDescription(contentPageDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Areas
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentPageAreaDetailFactory contentPageAreaDetailFactory;

    @Inject
    protected ContentPageAreaFactory contentPageAreaFactory;

    @Inject
    ContentPageAreaTransferCache contentPageAreaTransferCache;

    public ContentPageArea createContentPageArea(ContentPage contentPage, ContentPageLayoutArea contentPageLayoutArea, Language language, MimeType mimeType, BasePK createdBy) {
        var contentPageArea = contentPageAreaFactory.create();
        var contentPageAreaDetail = contentPageAreaDetailFactory.create(contentPageArea, contentPage, contentPageLayoutArea,
                language, mimeType, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        contentPageArea = contentPageAreaFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentPageArea.getPrimaryKey());
        contentPageArea.setActiveDetail(contentPageAreaDetail);
        contentPageArea.setLastDetail(contentPageAreaDetail);
        contentPageArea.store();
        
        sendEvent(contentPage.getPrimaryKey(), EventTypes.MODIFY, contentPageArea.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentPageArea;
    }

    public long countContentPageAreasByContentPage(ContentPage contentPage) {
        return session.getDslContext()
                .selectCount()
                .from(ContentPageAreas)
                .join(ContentPageAreaDetails).onKey(CONTENT_PAGE_AREAS_ACTIVE_DETAIL_FK)
                .where(ContentPageAreaDetails.CONTENT_PAGE.eq(contentPage.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContentPageArea */
    public ContentPageArea getContentPageAreaByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContentPageAreaPK(entityInstance.getEntityUniqueId());

        return contentPageAreaFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContentPageArea getContentPageAreaByEntityInstance(EntityInstance entityInstance) {
        return getContentPageAreaByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContentPageArea getContentPageAreaByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContentPageAreaByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<ContentPageArea> getContentPageAreasByContentPage(ContentPage contentPage, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentPageAreas.fields())
                    .from(ContentPageAreas)
                    .join(ContentPageAreaDetails).onKey(CONTENT_PAGE_AREA_DETAILS_CONTENT_PAGE_AREA_FK)
                    .join(ContentPageLayoutAreas).onKey(CONTENT_PAGE_AREA_DETAILS_CONTENT_PAGE_LAYOUT_AREA_FK)
                    .join(Languages).onKey(CONTENT_PAGE_AREA_DETAILS_LANGUAGE_FK)
                    .where(ContentPageAreaDetails.CONTENT_PAGE.eq(contentPage.getPrimaryKey()),
                            ContentPageAreaDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentPageLayoutAreas.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ContentPageAreaFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentPageAreas.fields())
                    .from(ContentPageAreas)
                    .join(ContentPageAreaDetails).onKey(CONTENT_PAGE_AREA_DETAILS_CONTENT_PAGE_AREA_FK)
                    .where(ContentPageAreaDetails.CONTENT_PAGE.eq(contentPage.getPrimaryKey()),
                            ContentPageAreaDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentPageAreaFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentPageArea> getContentPageAreasByContentPage(ContentPage contentPage) {
        return getContentPageAreasByContentPage(contentPage, EntityPermission.READ_ONLY);
    }
    
    public List<ContentPageArea> getContentPageAreasByContentPageForUpdate(ContentPage contentPage) {
        return getContentPageAreasByContentPage(contentPage, EntityPermission.READ_WRITE);
    }
    
    private List<ContentPageArea> getContentPageAreasByContentPage(ContentPage contentPage, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentPageAreas.fields())
                .from(ContentPageAreas)
                .join(ContentPageAreaDetails).onKey(CONTENT_PAGE_AREAS_ACTIVE_DETAIL_FK)
                .where(ContentPageAreaDetails.CONTENT_PAGE.eq(contentPage.getPrimaryKey()),
                        ContentPageAreaDetails.LANGUAGE.eq(language.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentPageAreas.fields())
                    .from(ContentPageAreas)
                    .join(ContentPageAreaDetails).onKey(CONTENT_PAGE_AREAS_ACTIVE_DETAIL_FK)
                    .join(ContentPageLayoutAreas).onKey(CONTENT_PAGE_AREA_DETAILS_CONTENT_PAGE_LAYOUT_AREA_FK)
                    .join(ContentPageAreaTypes).onKey(CONTENT_PAGE_LAYOUT_AREAS_CONTENT_PAGE_AREA_TYPE_FK)
                    .where(ContentPageAreaDetails.CONTENT_PAGE.eq(contentPage.getPrimaryKey()),
                            ContentPageAreaDetails.LANGUAGE.eq(language.getPrimaryKey()))
                    .orderBy(ContentPageLayoutAreas.SORT_ORDER, ContentPageAreaTypes.CONTENT_PAGE_AREA_TYPE_NAME),
                    ContentPageAreaFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentPageAreaFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentPageArea> getContentPageAreasByContentPage(ContentPage contentPage, Language language) {
        return getContentPageAreasByContentPage(contentPage, language, EntityPermission.READ_ONLY);
    }
    
    public List<ContentPageArea> getContentPageAreasByContentPageForUpdate(ContentPage contentPage, Language language) {
        return getContentPageAreasByContentPage(contentPage, language, EntityPermission.READ_WRITE);
    }
    
    private ContentPageArea getContentPageArea(ContentPage contentPage, ContentPageLayoutArea contentPageLayoutArea, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentPageAreas.fields())
                .from(ContentPageAreas)
                .join(ContentPageAreaDetails).onKey(CONTENT_PAGE_AREAS_ACTIVE_DETAIL_FK)
                .where(ContentPageAreaDetails.CONTENT_PAGE.eq(contentPage.getPrimaryKey()),
                        ContentPageAreaDetails.CONTENT_PAGE_LAYOUT_AREA.eq(contentPageLayoutArea.getPrimaryKey()),
                        ContentPageAreaDetails.LANGUAGE.eq(language.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentPageAreaFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentPageArea getContentPageArea(ContentPage contentPage, ContentPageLayoutArea contentPageLayoutArea, Language language) {
        return getContentPageArea(contentPage, contentPageLayoutArea, language, EntityPermission.READ_ONLY);
    }
    
    public ContentPageArea getContentPageAreaForUpdate(ContentPage contentPage, ContentPageLayoutArea contentPageLayoutArea, Language language) {
        return getContentPageArea(contentPage, contentPageLayoutArea, language, EntityPermission.READ_WRITE);
    }
    
    public ContentPageAreaDetailValue getContentPageAreaDetailValueForUpdate(ContentPage contentPage, ContentPageLayoutArea contentPageLayoutArea, Language language) {
        return getContentPageArea(contentPage, contentPageLayoutArea, language, EntityPermission.READ_WRITE).getLastDetailForUpdate().getContentPageAreaDetailValue().clone();
    }
    
    public ContentPageAreaDetailValue getContentPageAreaDetailValueForUpdate(ContentPageArea contentPageArea) {
        return contentPageArea.getLastDetailForUpdate().getContentPageAreaDetailValue().clone();
    }
    
    public ContentPageArea getBestContentPageArea(ContentPage contentPage, ContentPageLayoutArea contentPageLayoutArea, Language language) {
        var contentPageArea = getContentPageArea(contentPage, contentPageLayoutArea, language);
        
        if(contentPageArea == null && !language.getIsDefault()) {
            contentPageArea = getContentPageArea(contentPage, contentPageLayoutArea, partyControl.getDefaultLanguage());
        }
        
        return contentPageArea;
    }
    
    public ContentPageAreaTransfer getContentPageAreaTransfer(UserVisit userVisit, ContentPageArea contentPageArea) {
        return contentPageAreaTransferCache.getContentPageAreaTransfer(userVisit, contentPageArea);
    }
    
    public List<ContentPageAreaTransfer> getContentPageAreaTransfers(UserVisit userVisit, Collection<ContentPageArea> contentPageAreas) {
        List<ContentPageAreaTransfer> contentPageAreaTransfers = new ArrayList<>(contentPageAreas.size());

        contentPageAreas.forEach((contentPageArea) -> {
            contentPageAreaTransfers.add(contentPageAreaTransferCache.getContentPageAreaTransfer(userVisit, contentPageArea));
        });
        
        return contentPageAreaTransfers;
    }
    
    public List<ContentPageAreaTransfer> getContentPageAreaTransfersByContentPage(UserVisit userVisit, ContentPage contentPage) {
        return getContentPageAreaTransfers(userVisit, getContentPageAreasByContentPage(contentPage));
    }
    
    // TODO: fall back on default Language when a Content Page Area is missing for a specific Layout used on the Page.
    public List<ContentPageAreaTransfer> getContentPageAreaTransfersByContentPage(UserVisit userVisit, ContentPage contentPage, Language language) {
        return getContentPageAreaTransfers(userVisit, getContentPageAreasByContentPage(contentPage, language));
    }
    
    public ContentPageAreaDetail updateContentPageAreaFromValue(ContentPageAreaDetailValue contentPageAreaDetailValue, boolean forceUpdate, BasePK updatedBy) {
        var contentPageAreaDetail = contentPageAreaDetailFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentPageAreaDetailValue.getPrimaryKey());
        
        if(forceUpdate || contentPageAreaDetailValue.hasBeenModified()) {
            contentPageAreaDetail.setThruTime(session.getStartTime());
            contentPageAreaDetail.store();

            var contentPageAreaPK = contentPageAreaDetail.getContentPageAreaPK();
            var contentPagePK = contentPageAreaDetail.getContentPagePK();
            var contentPageLayoutAreaPK = contentPageAreaDetail.getContentPageLayoutAreaPK();
            var languagePK = contentPageAreaDetail.getLanguagePK();
            var mimeTypePK = contentPageAreaDetailValue.getMimeTypePK();
            
            contentPageAreaDetail = contentPageAreaDetailFactory.create(contentPageAreaPK, contentPagePK, contentPageLayoutAreaPK,
                    languagePK, mimeTypePK, session.getStartTime(), Session.MAX_TIME);

            var contentPageArea = contentPageAreaFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentPageAreaPK);
            contentPageArea.setActiveDetail(contentPageAreaDetail);
            contentPageArea.setLastDetail(contentPageAreaDetail);
            
            sendEvent(contentPagePK, EventTypes.MODIFY, contentPageAreaDetailValue.getContentPageAreaPK(), EventTypes.MODIFY, updatedBy);
        }
        
        return contentPageAreaDetail;
    }
    
    public void deleteContentPageArea(ContentPageArea contentPageArea, BasePK deletedBy) {
        var contentPageAreaDetail = contentPageArea.getLastDetailForUpdate();
        
        contentPageAreaDetail.setThruTime(session.getStartTime());
        contentPageArea.setActiveDetail(null);
        
        sendEvent(contentPageAreaDetail.getContentPagePK(), EventTypes.MODIFY, contentPageArea.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContentPageAreasByContentPage(ContentPage contentPage, BasePK deletedBy) {
        getContentPageAreasByContentPageForUpdate(contentPage).forEach((contentPageArea) -> {
            deleteContentPageArea(contentPageArea, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Blobs
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentPageAreaBlobFactory contentPageAreaBlobFactory;

    public ContentPageAreaBlob createContentPageAreaBlob(ContentPageAreaDetail contentPageAreaDetail, ByteArray blob) {
        return contentPageAreaBlobFactory.create(contentPageAreaDetail, blob);
    }
    
    public ContentPageAreaBlob getContentPageAreaBlob(ContentPageAreaDetail contentPageAreaDetail) {
        var query = session.getDslContext()
                .select(ContentPageAreaBlobs.fields())
                .from(ContentPageAreaBlobs)
                .where(ContentPageAreaBlobs.CONTENT_PAGE_AREA_DETAIL.eq(contentPageAreaDetail.getPrimaryKey()));

        return contentPageAreaBlobFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Clobs
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentPageAreaClobFactory contentPageAreaClobFactory;

    public ContentPageAreaClob createContentPageAreaClob(ContentPageAreaDetail contentPageAreaDetail, String clob) {
        return contentPageAreaClobFactory.create(contentPageAreaDetail, clob);
    }
    
    public ContentPageAreaClob getContentPageAreaClob(ContentPageAreaDetail contentPageAreaDetail) {
        var query = session.getDslContext()
                .select(ContentPageAreaClobs.fields())
                .from(ContentPageAreaClobs)
                .where(ContentPageAreaClobs.CONTENT_PAGE_AREA_DETAIL.eq(contentPageAreaDetail.getPrimaryKey()));

        return contentPageAreaClobFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Strings
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentPageAreaStringFactory contentPageAreaStringFactory;

    public ContentPageAreaString createContentPageAreaString(ContentPageAreaDetail contentPageAreaDetail, String string) {
        return contentPageAreaStringFactory.create(contentPageAreaDetail, string);
    }
    
    public ContentPageAreaString getContentPageAreaString(ContentPageAreaDetail contentPageAreaDetail) {
        var query = session.getDslContext()
                .select(ContentPageAreaStrings.fields())
                .from(ContentPageAreaStrings)
                .where(ContentPageAreaStrings.CONTENT_PAGE_AREA_DETAIL.eq(contentPageAreaDetail.getPrimaryKey()));

        return contentPageAreaStringFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Urls
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentPageAreaUrlFactory contentPageAreaUrlFactory;

    public ContentPageAreaUrl createContentPageAreaUrl(ContentPageAreaDetail contentPageAreaDetail, String contentPageAreaURL) {
        return contentPageAreaUrlFactory.create(contentPageAreaDetail, contentPageAreaURL);
    }
    
    public ContentPageAreaUrl getContentPageAreaUrl(ContentPageAreaDetail contentPageAreaDetail) {
        var query = session.getDslContext()
                .select(ContentPageAreaUrls.fields())
                .from(ContentPageAreaUrls)
                .where(ContentPageAreaUrls.CONTENT_PAGE_AREA_DETAIL.eq(contentPageAreaDetail.getPrimaryKey()));

        return contentPageAreaUrlFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalogs
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentCatalogDetailFactory contentCatalogDetailFactory;

    @Inject
    protected ContentCatalogFactory contentCatalogFactory;

    @Inject
    ContentCatalogTransferCache contentCatalogTransferCache;

    public ContentCatalog createContentCatalog(ContentCollection contentCollection, String contentCatalogName,
            OfferUse defaultOfferUse, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultFound = getDefaultContentCatalog(contentCollection) != null;
        
        if(defaultFound && isDefault) {
            var defaultContentCatalogDetailValue = getDefaultContentCatalogDetailValueForUpdate(contentCollection);
            
            defaultContentCatalogDetailValue.setIsDefault(false);
            updateContentCatalogFromValue(defaultContentCatalogDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var contentCatalog = contentCatalogFactory.create();
        var contentCatalogDetail = contentCatalogDetailFactory.create(contentCatalog,
                contentCollection, contentCatalogName, defaultOfferUse, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        contentCatalog = contentCatalogFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentCatalog.getPrimaryKey());
        contentCatalog.setActiveDetail(contentCatalogDetail);
        contentCatalog.setLastDetail(contentCatalogDetail);
        contentCatalog.store();
        
        sendEvent(contentCatalog.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentCatalog;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContentCatalog */
    public ContentCatalog getContentCatalogByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContentCatalogPK(entityInstance.getEntityUniqueId());

        return contentCatalogFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContentCatalog getContentCatalogByEntityInstance(EntityInstance entityInstance) {
        return getContentCatalogByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContentCatalog getContentCatalogByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContentCatalogByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContentCatalogsByContentCollection(ContentCollection contentCollection) {
        return session.getDslContext()
                .selectCount()
                .from(ContentCatalogDetails)
                .where(ContentCatalogDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                        ContentCatalogDetails.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContentCatalogsByDefaultOfferUse(OfferUse defaultOfferUse) {
        return session.getDslContext()
                .selectCount()
                .from(ContentCatalogs)
                .join(ContentCatalogDetails).onKey(CONTENT_CATALOGS_ACTIVE_DETAIL_FK)
                .where(ContentCatalogDetails.DEFAULT_OFFER_USE.eq(defaultOfferUse.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<ContentCatalog> getContentCatalogs(ContentCollection contentCollection, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCatalogs.fields())
                    .from(ContentCatalogs)
                    .join(ContentCatalogDetails).onKey(CONTENT_CATALOG_DETAILS_CONTENT_CATALOG_FK)
                    .where(ContentCatalogDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                            ContentCatalogDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentCatalogDetails.SORT_ORDER, ContentCatalogDetails.CONTENT_CATALOG_NAME),
                    ContentCatalogFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCatalogs.fields())
                    .from(ContentCatalogs)
                    .join(ContentCatalogDetails).onKey(CONTENT_CATALOG_DETAILS_CONTENT_CATALOG_FK)
                    .where(ContentCatalogDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                            ContentCatalogDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentCatalogDetails.SORT_ORDER, ContentCatalogDetails.CONTENT_CATALOG_NAME)
                    .forUpdate();
        };

        return contentCatalogFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentCatalog> getContentCatalogs(ContentCollection contentCollection) {
        return getContentCatalogs(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCatalog> getContentCatalogsForUpdate(ContentCollection contentCollection) {
        return getContentCatalogs(contentCollection, EntityPermission.READ_WRITE);
    }
    
    private ContentCatalog getContentCatalogByName(ContentCollection contentCollection, String contentCatalogName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentCatalogs.fields())
                .from(ContentCatalogs)
                .join(ContentCatalogDetails).onKey(CONTENT_CATALOG_DETAILS_CONTENT_CATALOG_FK)
                .where(ContentCatalogDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                        ContentCatalogDetails.CONTENT_CATALOG_NAME.eq(contentCatalogName),
                        ContentCatalogDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentCatalogFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentCatalog getContentCatalogByName(ContentCollection contentCollection, String contentCatalogName) {
        return getContentCatalogByName(contentCollection, contentCatalogName, EntityPermission.READ_ONLY);
    }
    
    public ContentCatalog getContentCatalogByNameForUpdate(ContentCollection contentCollection, String contentCatalogName) {
        return getContentCatalogByName(contentCollection, contentCatalogName, EntityPermission.READ_WRITE);
    }
    
    public ContentCatalogDetailValue getContentCatalogDetailValueForUpdate(ContentCatalog contentCatalog) {
        return contentCatalog == null? null: contentCatalog.getLastDetailForUpdate().getContentCatalogDetailValue().clone();
    }
    
    public ContentCatalogDetailValue getContentCatalogDetailValueByNameForUpdate(ContentCollection contentCollection, String contentCatalogName) {
        return getContentCatalogDetailValueForUpdate(getContentCatalogByNameForUpdate(contentCollection, contentCatalogName));
    }
    
    private ContentCatalog getDefaultContentCatalog(ContentCollection contentCollection, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentCatalogs.fields())
                .from(ContentCatalogs)
                .join(ContentCatalogDetails).onKey(CONTENT_CATALOG_DETAILS_CONTENT_CATALOG_FK)
                .where(ContentCatalogDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                        ContentCatalogDetails.IS_DEFAULT.eq(true),
                        ContentCatalogDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentCatalogFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentCatalog getDefaultContentCatalog(ContentCollection contentCollection) {
        return getDefaultContentCatalog(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public ContentCatalogDetailValue getDefaultContentCatalogDetailValueForUpdate(ContentCollection contentCollection) {
        return getDefaultContentCatalog(contentCollection, EntityPermission.READ_WRITE).getLastDetailForUpdate().getContentCatalogDetailValue().clone();
    }
    
    public ContentCatalogChoicesBean getContentCatalogChoices(ContentCollection contentCollection, String defaultContentCatalogChoice, Language language) {
        var contentCatalogs = getContentCatalogs(contentCollection);
        var size = contentCatalogs.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        for(var contentCatalog : contentCatalogs) {
            var contentCatalogDetail = contentCatalog.getLastDetail();
            
            var label = getBestContentCatalogDescription(contentCatalog, language);
            var value = contentCatalogDetail.getContentCatalogName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultContentCatalogChoice != null && defaultContentCatalogChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && contentCatalogDetail.getIsDefault()))
                defaultValue = value;
        }
        
        return new ContentCatalogChoicesBean(labels, values, defaultValue);
    }
    
    public ContentCatalogTransfer getContentCatalogTransfer(UserVisit userVisit, ContentCatalog contentCatalog) {
        return contentCatalogTransferCache.getContentCatalogTransfer(userVisit, contentCatalog);
    }
    
    public List<ContentCatalogTransfer> getContentCatalogTransfers(UserVisit userVisit, Collection<ContentCatalog> contentCatalogs) {
        List<ContentCatalogTransfer> contentCatalogTransfers = new ArrayList<>(contentCatalogs.size());

        contentCatalogs.forEach((contentCatalog) ->
                contentCatalogTransfers.add(contentCatalogTransferCache.getContentCatalogTransfer(userVisit, contentCatalog))
        );
        
        return contentCatalogTransfers;
    }
    
    public List<ContentCatalogTransfer> getContentCatalogTransfers(UserVisit userVisit, ContentCollection contentCollection) {
        return getContentCatalogTransfers(userVisit, getContentCatalogs(contentCollection));
    }
    
    private void updateContentCatalogFromValue(ContentCatalogDetailValue contentCatalogDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(contentCatalogDetailValue.hasBeenModified()) {
            var contentCatalog = contentCatalogFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentCatalogDetailValue.getContentCatalogPK());
            var contentCatalogDetail = contentCatalog.getActiveDetailForUpdate();

            contentCatalogDetail.setThruTime(session.getStartTime());
            contentCatalogDetail.store();

            var contentCatalogPK = contentCatalogDetail.getContentCatalogPK();
            var contentCollection = contentCatalogDetail.getContentCollection();
            var contentCollectionPK = contentCollection.getPrimaryKey();
            var contentCatalogName = contentCatalogDetailValue.getContentCatalogName();
            var defaultOfferUsePK = contentCatalogDetailValue.getDefaultOfferUsePK();
            var isDefault = contentCatalogDetailValue.getIsDefault();
            var sortOrder = contentCatalogDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultContentCatalog = getDefaultContentCatalog(contentCollection);
                var defaultFound = defaultContentCatalog != null && !defaultContentCatalog.equals(contentCatalog);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultContentCatalogDetailValue = getDefaultContentCatalogDetailValueForUpdate(contentCollection);

                    defaultContentCatalogDetailValue.setIsDefault(false);
                    updateContentCatalogFromValue(defaultContentCatalogDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            contentCatalogDetail = contentCatalogDetailFactory.create(contentCatalogPK, contentCollectionPK,
                    contentCatalogName, defaultOfferUsePK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            contentCatalog.setActiveDetail(contentCatalogDetail);
            contentCatalog.setLastDetail(contentCatalogDetail);
            contentCatalog.store();

            sendEvent(contentCatalogPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateContentCatalogFromValue(ContentCatalogDetailValue contentCatalogDetailValue, BasePK updatedBy) {
        updateContentCatalogFromValue(contentCatalogDetailValue, true, updatedBy);
    }

    public ContentCatalog getContentCatalogByPK(ContentCatalogPK contentCatalogPK) {
        return contentCatalogFactory.getEntityFromPK(EntityPermission.READ_ONLY, contentCatalogPK);
    }

    public void deleteContentCatalog(ContentCatalog contentCatalog, BasePK deletedBy) {
        // ContentLogic not used since everything is being deleted from the ContentCatalog anyway.
        // Items are deleted separately to avoid duplicate key errors due to the pricing for the same item
        // being adjusted multiple times.
        deleteContentCatalogItemsByContentCatalog(contentCatalog, deletedBy);
        deleteContentCategoriesByContentCatalog(contentCatalog, deletedBy);
        deleteContentCatalogDescriptionsByContentCatalog(contentCatalog, deletedBy);
        orderControl.deleteOrderContentCatalogsByContentCatalog(contentCatalog, deletedBy);

        var contentCatalogDetail = contentCatalog.getLastDetailForUpdate();
        contentCatalogDetail.setThruTime(session.getStartTime());
        contentCatalogDetail.store();
        contentCatalog.setActiveDetail(null);

        var contentCollection = contentCatalogDetail.getContentCollection();
        var defaultContentCatalog = getDefaultContentCatalog(contentCollection);
        if(defaultContentCatalog == null) {
            var contentCatalogs = getContentCatalogsForUpdate(contentCollection);
            
            if(!contentCatalogs.isEmpty()) {
                defaultContentCatalog = contentCatalogs.iterator().next();
                var contentCatalogDetailValue = Objects.requireNonNull(defaultContentCatalog).getLastDetailForUpdate().getContentCatalogDetailValue().clone();
                
                contentCatalogDetailValue.setIsDefault(true);
                updateContentCatalogFromValue(contentCatalogDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(contentCatalog.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteContentCatalogsByContentCollection(ContentCollection contentCollection, BasePK deletedBy) {
        getContentCatalogsForUpdate(contentCollection).forEach((contentCatalog) -> {
            deleteContentCatalog(contentCatalog, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentCatalogDescriptionFactory contentCatalogDescriptionFactory;

    @Inject
    ContentCatalogDescriptionTransferCache contentCatalogDescriptionTransferCache;

    public ContentCatalogDescription createContentCatalogDescription(ContentCatalog contentCatalog, Language language, String description, BasePK createdBy) {

        var contentCatalogDescription = contentCatalogDescriptionFactory.create(contentCatalog, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(contentCatalog.getPrimaryKey(), EventTypes.MODIFY, contentCatalogDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentCatalogDescription;
    }
    
    private ContentCatalogDescription getContentCatalogDescription(ContentCatalog contentCatalog, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentCatalogDescriptions.fields())
                .from(ContentCatalogDescriptions)
                .where(ContentCatalogDescriptions.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                        ContentCatalogDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ContentCatalogDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentCatalogDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentCatalogDescription getContentCatalogDescription(ContentCatalog contentCatalog, Language language) {
        return getContentCatalogDescription(contentCatalog, language, EntityPermission.READ_ONLY);
    }
    
    public ContentCatalogDescription getContentCatalogDescriptionForUpdate(ContentCatalog contentCatalog, Language language) {
        return getContentCatalogDescription(contentCatalog, language, EntityPermission.READ_WRITE);
    }
    
    public ContentCatalogDescriptionValue getContentCatalogDescriptionValue(ContentCatalogDescription contentCatalogDescription) {
        return contentCatalogDescription == null? null: contentCatalogDescription.getContentCatalogDescriptionValue().clone();
    }
    
    public ContentCatalogDescriptionValue getContentCatalogDescriptionValueForUpdate(ContentCatalog contentCatalog, Language language) {
        return getContentCatalogDescriptionValue(getContentCatalogDescriptionForUpdate(contentCatalog, language));
    }
    
    private List<ContentCatalogDescription> getContentCatalogDescriptionsByContentCatalog(ContentCatalog contentCatalog, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCatalogDescriptions.fields())
                    .from(ContentCatalogDescriptions)
                    .join(Languages).onKey(CONTENT_CATALOG_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ContentCatalogDescriptions.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                            ContentCatalogDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ContentCatalogDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCatalogDescriptions.fields())
                    .from(ContentCatalogDescriptions)
                    .where(ContentCatalogDescriptions.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                            ContentCatalogDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentCatalogDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentCatalogDescription> getContentCatalogDescriptionsByContentCatalog(ContentCatalog contentCatalog) {
        return getContentCatalogDescriptionsByContentCatalog(contentCatalog, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCatalogDescription> getContentCatalogDescriptionsByContentCatalogForUpdate(ContentCatalog contentCatalog) {
        return getContentCatalogDescriptionsByContentCatalog(contentCatalog, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentCatalogDescription(ContentCatalog contentCatalog, Language language) {
        String description;
        var contentCatalogDescription = getContentCatalogDescription(contentCatalog, language);
        
        if(contentCatalogDescription == null && !language.getIsDefault()) {
            contentCatalogDescription = getContentCatalogDescription(contentCatalog, partyControl.getDefaultLanguage());
        }
        
        if(contentCatalogDescription == null) {
            description = contentCatalog.getLastDetail().getContentCatalogName();
        } else {
            description = contentCatalogDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentCatalogDescriptionTransfer getContentCatalogDescriptionTransfer(UserVisit userVisit, ContentCatalogDescription contentCatalogDescription) {
        return contentCatalogDescriptionTransferCache.getContentCatalogDescriptionTransfer(userVisit, contentCatalogDescription);
    }
    
    public List<ContentCatalogDescriptionTransfer> getContentCatalogDescriptionTransfers(UserVisit userVisit, ContentCatalog contentCatalog) {
        var contentCatalogDescriptions = getContentCatalogDescriptionsByContentCatalog(contentCatalog);
        List<ContentCatalogDescriptionTransfer> contentCatalogDescriptionTransfers = new ArrayList<>(contentCatalogDescriptions.size());

        contentCatalogDescriptions.forEach((contentCatalogDescription) ->
                contentCatalogDescriptionTransfers.add(contentCatalogDescriptionTransferCache.getContentCatalogDescriptionTransfer(userVisit, contentCatalogDescription))
        );
        
        return contentCatalogDescriptionTransfers;
    }
    
    public void updateContentCatalogDescriptionFromValue(ContentCatalogDescriptionValue contentCatalogDescriptionValue, BasePK updatedBy) {
        if(contentCatalogDescriptionValue.hasBeenModified()) {
            var contentCatalogDescription = contentCatalogDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentCatalogDescriptionValue.getPrimaryKey());
            
            contentCatalogDescription.setThruTime(session.getStartTime());
            contentCatalogDescription.store();

            var contentCatalog = contentCatalogDescription.getContentCatalog();
            var language = contentCatalogDescription.getLanguage();
            var description = contentCatalogDescriptionValue.getDescription();
            
            contentCatalogDescription = contentCatalogDescriptionFactory.create(contentCatalog, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contentCatalog.getPrimaryKey(), EventTypes.MODIFY, contentCatalogDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentCatalogDescription(ContentCatalogDescription contentCatalogDescription, BasePK deletedBy) {
        contentCatalogDescription.setThruTime(session.getStartTime());
        
        sendEvent(contentCatalogDescription.getContentCatalogPK(), EventTypes.MODIFY, contentCatalogDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentCatalogDescriptionsByContentCatalog(ContentCatalog contentCatalog, BasePK deletedBy) {
        getContentCatalogDescriptionsByContentCatalogForUpdate(contentCatalog).forEach((contentCatalogDescription) -> {
            deleteContentCatalogDescription(contentCatalogDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Items
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentCatalogItemFactory contentCatalogItemFactory;

    @Inject
    ContentCatalogItemTransferCache contentCatalogItemTransferCache;

    public ContentCatalogItem createContentCatalogItem(ContentCatalog contentCatalog, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency, BasePK createdBy) {
        var contentCatalogItem = contentCatalogItemFactory.create(contentCatalog, item,
                inventoryCondition, unitOfMeasureType, currency, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(contentCatalogItem.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentCatalogItem;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContentCatalogItem */
    public ContentCatalogItem getContentCatalogItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContentCatalogItemPK(entityInstance.getEntityUniqueId());

        return contentCatalogItemFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContentCatalogItem getContentCatalogItemByEntityInstance(EntityInstance entityInstance) {
        return getContentCatalogItemByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContentCatalogItem getContentCatalogItemByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContentCatalogItemByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContentCatalogItemsByContentCatalog(ContentCatalog contentCatalog) {
        return session.getDslContext()
                .selectCount()
                .from(ContentCatalogItems)
                .where(ContentCatalogItems.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                        ContentCatalogItems.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<ContentCatalogItem> getContentCatalogItemsByContentCatalog(ContentCatalog contentCatalog, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCatalogItems.fields())
                    .from(ContentCatalogItems)
                    .join(Items).onKey(CONTENT_CATALOG_ITEMS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(InventoryConditions).onKey(CONTENT_CATALOG_ITEMS_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(CONTENT_CATALOG_ITEMS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(UnitOfMeasureKinds).onKey(UNIT_OF_MEASURE_TYPE_DETAILS_UNIT_OF_MEASURE_KIND_FK)
                    .join(UnitOfMeasureKindDetails).onKey(UNIT_OF_MEASURE_KINDS_LAST_DETAIL_FK)
                    .join(Currencies).onKey(CONTENT_CATALOG_ITEMS_CURRENCY_FK)
                    .where(ContentCatalogItems.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                            ContentCatalogItems.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    ContentCatalogItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCatalogItems.fields())
                    .from(ContentCatalogItems)
                    .where(ContentCatalogItems.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                            ContentCatalogItems.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentCatalogItemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContentCatalogItem> getContentCatalogItemsByContentCatalog(ContentCatalog contentCatalog) {
        return getContentCatalogItemsByContentCatalog(contentCatalog, EntityPermission.READ_ONLY);
    }

    public List<ContentCatalogItem> getContentCatalogItemsByContentCatalogForUpdate(ContentCatalog contentCatalog) {
        return getContentCatalogItemsByContentCatalog(contentCatalog, EntityPermission.READ_WRITE);
    }

    private List<ContentCatalogItem> getContentCatalogItemsByContentCatalogAndItem(ContentCatalog contentCatalog, Item item,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCatalogItems.fields())
                    .from(ContentCatalogItems)
                    .join(InventoryConditions).onKey(CONTENT_CATALOG_ITEMS_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(CONTENT_CATALOG_ITEMS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(Currencies).onKey(CONTENT_CATALOG_ITEMS_CURRENCY_FK)
                    .where(ContentCatalogItems.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                            ContentCatalogItems.ITEM.eq(item.getPrimaryKey()),
                            ContentCatalogItems.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    ContentCatalogItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCatalogItems.fields())
                    .from(ContentCatalogItems)
                    .where(ContentCatalogItems.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                            ContentCatalogItems.ITEM.eq(item.getPrimaryKey()),
                            ContentCatalogItems.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentCatalogItemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContentCatalogItem> getContentCatalogItemsByContentCatalogAndItem(ContentCatalog contentCatalog, Item item) {
        return getContentCatalogItemsByContentCatalogAndItem(contentCatalog, item, EntityPermission.READ_ONLY);
    }

    public List<ContentCatalogItem> getContentCatalogItemsByContentCatalogAndItemForUpdate(ContentCatalog contentCatalog, Item item) {
        return getContentCatalogItemsByContentCatalogAndItem(contentCatalog, item, EntityPermission.READ_WRITE);
    }

    private List<ContentCatalogItem> getContentCatalogItemsByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCatalogItems.fields())
                    .from(ContentCatalogItems)
                    .join(ContentCatalogs).onKey(CONTENT_CATALOG_ITEMS_CONTENT_CATALOG_FK)
                    .join(ContentCatalogDetails).onKey(CONTENT_CATALOGS_LAST_DETAIL_FK)
                    .join(ContentCollections).onKey(CONTENT_CATALOG_DETAILS_CONTENT_COLLECTION_FK)
                    .join(ContentCollectionDetails).onKey(CONTENT_COLLECTIONS_LAST_DETAIL_FK)
                    .join(InventoryConditions).onKey(CONTENT_CATALOG_ITEMS_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(CONTENT_CATALOG_ITEMS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(Currencies).onKey(CONTENT_CATALOG_ITEMS_CURRENCY_FK)
                    .where(ContentCatalogItems.ITEM.eq(item.getPrimaryKey()),
                            ContentCatalogItems.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentCollectionDetails.CONTENT_COLLECTION_NAME, ContentCatalogDetails.SORT_ORDER, ContentCatalogDetails.CONTENT_CATALOG_NAME, InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    ContentCatalogItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCatalogItems.fields())
                    .from(ContentCatalogItems)
                    .where(ContentCatalogItems.ITEM.eq(item.getPrimaryKey()),
                            ContentCatalogItems.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentCatalogItemFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentCatalogItem> getContentCatalogItemsByItem(Item item) {
        return getContentCatalogItemsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCatalogItem> getContentCatalogItemsByItemForUpdate(Item item) {
        return getContentCatalogItemsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<ContentCatalogItem> getContentCatalogItemsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCatalogItems.fields())
                    .from(ContentCatalogItems)
                    .join(ContentCatalogs).onKey(CONTENT_CATALOG_ITEMS_CONTENT_CATALOG_FK)
                    .join(ContentCatalogDetails).onKey(CONTENT_CATALOGS_LAST_DETAIL_FK)
                    .join(ContentCollections).onKey(CONTENT_CATALOG_DETAILS_CONTENT_COLLECTION_FK)
                    .join(ContentCollectionDetails).onKey(CONTENT_COLLECTIONS_LAST_DETAIL_FK)
                    .join(Items).onKey(CONTENT_CATALOG_ITEMS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(CONTENT_CATALOG_ITEMS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(Currencies).onKey(CONTENT_CATALOG_ITEMS_CURRENCY_FK)
                    .where(ContentCatalogItems.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ContentCatalogItems.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentCollectionDetails.CONTENT_COLLECTION_NAME, ContentCatalogDetails.SORT_ORDER, ContentCatalogDetails.CONTENT_CATALOG_NAME, ItemDetails.ITEM_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    ContentCatalogItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCatalogItems.fields())
                    .from(ContentCatalogItems)
                    .where(ContentCatalogItems.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            ContentCatalogItems.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentCatalogItemFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentCatalogItem> getContentCatalogItemsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getContentCatalogItemsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCatalogItem> getContentCatalogItemsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getContentCatalogItemsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    private List<ContentCatalogItem> getContentCatalogItemsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCatalogItems.fields())
                    .from(ContentCatalogItems)
                    .join(ContentCatalogs).onKey(CONTENT_CATALOG_ITEMS_CONTENT_CATALOG_FK)
                    .join(ContentCatalogDetails).onKey(CONTENT_CATALOGS_LAST_DETAIL_FK)
                    .join(ContentCollections).onKey(CONTENT_CATALOG_DETAILS_CONTENT_COLLECTION_FK)
                    .join(ContentCollectionDetails).onKey(CONTENT_COLLECTIONS_LAST_DETAIL_FK)
                    .join(Items).onKey(CONTENT_CATALOG_ITEMS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(InventoryConditions).onKey(CONTENT_CATALOG_ITEMS_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(Currencies).onKey(CONTENT_CATALOG_ITEMS_CURRENCY_FK)
                    .where(ContentCatalogItems.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ContentCatalogItems.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentCollectionDetails.CONTENT_COLLECTION_NAME, ContentCatalogDetails.SORT_ORDER, ContentCatalogDetails.CONTENT_CATALOG_NAME, ItemDetails.ITEM_NAME, InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    ContentCatalogItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCatalogItems.fields())
                    .from(ContentCatalogItems)
                    .where(ContentCatalogItems.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            ContentCatalogItems.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentCatalogItemFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentCatalogItem> getContentCatalogItemsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getContentCatalogItemsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCatalogItem> getContentCatalogItemsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getContentCatalogItemsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    private ContentCatalogItem getContentCatalogItem(ContentCatalog contentCatalog, Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentCatalogItems.fields())
                .from(ContentCatalogItems)
                .where(ContentCatalogItems.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                        ContentCatalogItems.ITEM.eq(item.getPrimaryKey()),
                        ContentCatalogItems.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        ContentCatalogItems.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        ContentCatalogItems.CURRENCY.eq(currency.getPrimaryKey()),
                        ContentCatalogItems.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentCatalogItemFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentCatalogItem getContentCatalogItem(ContentCatalog contentCatalog, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getContentCatalogItem(contentCatalog, item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_ONLY);
    }
    
    public ContentCatalogItem getContentCatalogItemForUpdate(ContentCatalog contentCatalog, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getContentCatalogItem(contentCatalog, item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_WRITE);
    }
    
    public List<ContentCatalogItemTransfer> getContentCatalogItemTransfers(UserVisit userVisit, Collection<ContentCatalogItem> contentCatalogItems) {
        List<ContentCatalogItemTransfer> contentCatalogItemTransfers = new ArrayList<>(contentCatalogItems.size());

        contentCatalogItems.forEach((contentCatalogItem) ->
                contentCatalogItemTransfers.add(contentCatalogItemTransferCache.getContentCatalogItemTransfer(userVisit, contentCatalogItem))
        );

        return contentCatalogItemTransfers;
    }

    public ContentCatalogItemTransfer getContentCatalogItemTransfer(UserVisit userVisit, ContentCatalogItem contentCatalogItem) {
        return contentCatalogItemTransferCache.getContentCatalogItemTransfer(userVisit, contentCatalogItem);
    }

    public List<ContentCatalogItemTransfer> getContentCatalogItemTransfers(UserVisit userVisit, ContentCatalog contentCatalog) {
        return getContentCatalogItemTransfers(userVisit, getContentCatalogItemsByContentCatalog(contentCatalog));
    }

    public ContentCatalogItem getContentCatalogItemByPK(ContentCatalogItemPK contentCatalogItemPK) {
        return contentCatalogItemFactory.getEntityFromPK(EntityPermission.READ_ONLY, contentCatalogItemPK);
    }

    public void deleteContentCatalogItem(ContentCatalogItem contentCatalogItem, BasePK deletedBy) {
        var item = contentCatalogItem.getItem();
        var itemPriceTypeName = item.getLastDetail().getItemPriceType().getItemPriceTypeName();

        deleteContentCategoryItemsByContentCatalogItem(contentCatalogItem, deletedBy);

        if(ItemPriceTypes.FIXED.name().equals(itemPriceTypeName)) {
            var contentCatalogItemFixedPrice = getContentCatalogItemFixedPriceForUpdate(contentCatalogItem);
            
            if(contentCatalogItemFixedPrice != null) {
                deleteContentCatalogItemFixedPrice(contentCatalogItemFixedPrice, deletedBy);
            }
        } else if(ItemPriceTypes.VARIABLE.name().equals(itemPriceTypeName)) {
            var contentCatalogItemVariablePrice = getContentCatalogItemVariablePriceForUpdate(contentCatalogItem);

            if(contentCatalogItemVariablePrice != null) {
                deleteContentCatalogItemVariablePrice(contentCatalogItemVariablePrice, deletedBy);
            }
        }
        
        contentCatalogItem.setThruTime(session.getStartTime());
        contentCatalogItem.store();
        
        sendEvent(contentCatalogItem.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteContentCatalogItems(List<ContentCatalogItem> contentCatalogItems, BasePK deletedBy) {
        contentCatalogItems.forEach((contentCatalogItem) -> 
                deleteContentCatalogItem(contentCatalogItem, deletedBy)
        );
    }
    
    public void deleteContentCatalogItemsByContentCatalog(ContentCatalog contentCatalog, BasePK deletedBy) {
        deleteContentCatalogItems(getContentCatalogItemsByContentCatalogForUpdate(contentCatalog), deletedBy);
    }

    public void deleteContentCatalogItemsByItem(Item item, BasePK deletedBy) {
        deleteContentCatalogItems(getContentCatalogItemsByItemForUpdate(item), deletedBy);
    }

    public void deleteContentCatalogItemsByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deleteContentCatalogItems(getContentCatalogItemsByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }
    
    public void deleteContentCatalogItemsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteContentCatalogItems(getContentCatalogItemsByUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Item Fixed Prices
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentCatalogItemFixedPriceFactory contentCatalogItemFixedPriceFactory;

    public ContentCatalogItemFixedPrice createContentCatalogItemFixedPrice(ContentCatalogItem contentCatalogItem, Long unitPrice,
            BasePK createdBy) {
        var contentCatalogItemFixedPrice = contentCatalogItemFixedPriceFactory.create(
                contentCatalogItem, unitPrice, session.getStartTime(), Session.MAX_TIME);

        sendEvent(contentCatalogItem.getPrimaryKey(), EventTypes.MODIFY, contentCatalogItemFixedPrice.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return contentCatalogItemFixedPrice;
    }

    public ContentCatalogItemFixedPriceValue getContentCatalogItemFixedPriceValueByPKForUpdate(ContentCatalogItemFixedPricePK contentCatalogItemFixedPricePK) {
        return contentCatalogItemFixedPriceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                contentCatalogItemFixedPricePK).getContentCatalogItemFixedPriceValue().clone();
    }

    private ContentCatalogItemFixedPrice getContentCatalogItemFixedPrice(ContentCatalogItem contentCatalogItem, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentCatalogItemFixedPrices.fields())
                .from(ContentCatalogItemFixedPrices)
                .where(ContentCatalogItemFixedPrices.CONTENT_CATALOG_ITEM.eq(contentCatalogItem.getPrimaryKey()),
                        ContentCatalogItemFixedPrices.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentCatalogItemFixedPriceFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContentCatalogItemFixedPrice getContentCatalogItemFixedPrice(ContentCatalogItem contentCatalogItem) {
        return getContentCatalogItemFixedPrice(contentCatalogItem, EntityPermission.READ_ONLY);
    }

    public ContentCatalogItemFixedPrice getContentCatalogItemFixedPriceForUpdate(ContentCatalogItem contentCatalogItem) {
        return getContentCatalogItemFixedPrice(contentCatalogItem, EntityPermission.READ_WRITE);
    }

    public ContentCatalogItemFixedPriceValue getContentCatalogItemFixedPriceValue(ContentCatalogItemFixedPrice contentCatalogItemFixedPrice) {
        return contentCatalogItemFixedPrice == null? null: contentCatalogItemFixedPrice.getContentCatalogItemFixedPriceValue().clone();
    }

    public ContentCatalogItemFixedPriceValue getContentCatalogItemFixedPriceValueForUpdate(ContentCatalogItem contentCatalogItem) {
        return getContentCatalogItemFixedPriceValue(getContentCatalogItemFixedPriceForUpdate(contentCatalogItem));
    }

    public void updateContentCatalogItemFixedPriceFromValue(ContentCatalogItemFixedPriceValue contentCatalogItemFixedPriceValue, BasePK updatedBy) {
        if(contentCatalogItemFixedPriceValue.hasBeenModified()) {
            var contentCatalogItemFixedPrice = contentCatalogItemFixedPriceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     contentCatalogItemFixedPriceValue.getPrimaryKey());

            contentCatalogItemFixedPrice.setThruTime(session.getStartTime());
            contentCatalogItemFixedPrice.store();

            var contentCatalogItemPK = contentCatalogItemFixedPrice.getContentCatalogItemPK();
            var unitPrice = contentCatalogItemFixedPriceValue.getUnitPrice();

            contentCatalogItemFixedPrice = contentCatalogItemFixedPriceFactory.create(contentCatalogItemPK,
                    unitPrice, session.getStartTime(), Session.MAX_TIME);

            sendEvent(contentCatalogItemFixedPrice.getContentCatalogItemPK(), EventTypes.MODIFY, contentCatalogItemFixedPrice.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteContentCatalogItemFixedPrice(ContentCatalogItemFixedPrice contentCatalogItemFixedPrice, BasePK deletedBy) {
        contentCatalogItemFixedPrice.setThruTime(session.getStartTime());
        contentCatalogItemFixedPrice.store();

        sendEvent(contentCatalogItemFixedPrice.getContentCatalogItemPK(), EventTypes.MODIFY, contentCatalogItemFixedPrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Content Catalog Item Variable Prices
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentCatalogItemVariablePriceFactory contentCatalogItemVariablePriceFactory;

    public ContentCatalogItemVariablePrice createContentCatalogItemVariablePrice(ContentCatalogItem contentCatalogItem, Long minimumUnitPrice,
            Long maximumUnitPrice, Long unitPriceIncrement, BasePK createdBy) {
        var contentCatalogItemVariablePrice = contentCatalogItemVariablePriceFactory.create(
                contentCatalogItem, minimumUnitPrice, maximumUnitPrice, unitPriceIncrement, session.getStartTime(), Session.MAX_TIME);

        sendEvent(contentCatalogItem.getPrimaryKey(), EventTypes.MODIFY, contentCatalogItemVariablePrice.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return contentCatalogItemVariablePrice;
    }

    public ContentCatalogItemVariablePriceValue getContentCatalogItemVariablePriceValueByPKForUpdate(ContentCatalogItemVariablePricePK contentCatalogItemVariablePricePK) {
        return contentCatalogItemVariablePriceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                contentCatalogItemVariablePricePK).getContentCatalogItemVariablePriceValue().clone();
    }

    private ContentCatalogItemVariablePrice getContentCatalogItemVariablePrice(ContentCatalogItem contentCatalogItem, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentCatalogItemVariablePrices.fields())
                .from(ContentCatalogItemVariablePrices)
                .where(ContentCatalogItemVariablePrices.CONTENT_CATALOG_ITEM.eq(contentCatalogItem.getPrimaryKey()),
                        ContentCatalogItemVariablePrices.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentCatalogItemVariablePriceFactory.getEntityFromQuery(entityPermission, query);
    }

    public ContentCatalogItemVariablePrice getContentCatalogItemVariablePrice(ContentCatalogItem contentCatalogItem) {
        return getContentCatalogItemVariablePrice(contentCatalogItem, EntityPermission.READ_ONLY);
    }

    public ContentCatalogItemVariablePrice getContentCatalogItemVariablePriceForUpdate(ContentCatalogItem contentCatalogItem) {
        return getContentCatalogItemVariablePrice(contentCatalogItem, EntityPermission.READ_WRITE);
    }

    public ContentCatalogItemVariablePriceValue getContentCatalogItemVariablePriceValue(ContentCatalogItemVariablePrice contentCatalogItemVariablePrice) {
        return contentCatalogItemVariablePrice == null? null: contentCatalogItemVariablePrice.getContentCatalogItemVariablePriceValue().clone();
    }

    public ContentCatalogItemVariablePriceValue getContentCatalogItemVariablePriceValueForUpdate(ContentCatalogItem contentCatalogItem) {
        return getContentCatalogItemVariablePriceValue(getContentCatalogItemVariablePriceForUpdate(contentCatalogItem));
    }

    public void updateContentCatalogItemVariablePriceFromValue(ContentCatalogItemVariablePriceValue contentCatalogItemVariablePriceValue, BasePK updatedBy) {
        if(contentCatalogItemVariablePriceValue.hasBeenModified()) {
            var contentCatalogItemVariablePrice = contentCatalogItemVariablePriceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     contentCatalogItemVariablePriceValue.getPrimaryKey());

            contentCatalogItemVariablePrice.setThruTime(session.getStartTime());
            contentCatalogItemVariablePrice.store();

            var contentCatalogItemPK = contentCatalogItemVariablePrice.getContentCatalogItemPK();
            var maximumUnitPrice = contentCatalogItemVariablePriceValue.getMaximumUnitPrice();
            var minimumUnitPrice = contentCatalogItemVariablePriceValue.getMinimumUnitPrice();
            var unitPriceIncrement = contentCatalogItemVariablePriceValue.getUnitPriceIncrement();

            contentCatalogItemVariablePrice = contentCatalogItemVariablePriceFactory.create(contentCatalogItemPK, maximumUnitPrice,
                    minimumUnitPrice, unitPriceIncrement, session.getStartTime(), Session.MAX_TIME);

            sendEvent(contentCatalogItemVariablePrice.getContentCatalogItemPK(), EventTypes.MODIFY, contentCatalogItemVariablePrice.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteContentCatalogItemVariablePrice(ContentCatalogItemVariablePrice contentCatalogItemVariablePrice, BasePK deletedBy) {
        contentCatalogItemVariablePrice.setThruTime(session.getStartTime());
        contentCatalogItemVariablePrice.store();

        sendEvent(contentCatalogItemVariablePrice.getContentCatalogItemPK(), EventTypes.MODIFY, contentCatalogItemVariablePrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Content Categories
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentCategoryDetailFactory contentCategoryDetailFactory;

    @Inject
    protected ContentCategoryFactory contentCategoryFactory;

    @Inject
    ContentCategoryTransferCache contentCategoryTransferCache;

    public ContentCategory createContentCategory(ContentCatalog contentCatalog, String contentCategoryName,
            ContentCategory parentContentCategory, OfferUse defaultOfferUse, Selector itemSelector, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultContentCategory = getDefaultContentCategory(contentCatalog);
        var defaultFound = defaultContentCategory != null;
        
        if(defaultFound) {
            if(defaultContentCategory.getLastDetail().getContentCategoryName().equals(com.echothree.model.control.content.common.ContentCategories.ROOT.toString()))
                isDefault = true;
        }
        
        if(defaultFound && isDefault) {
            var defaultContentCategoryDetailValue = getDefaultContentCategoryDetailValueForUpdate(contentCatalog);
            
            defaultContentCategoryDetailValue.setIsDefault(false);
            updateContentCategoryFromValue(defaultContentCategoryDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var contentCategory = contentCategoryFactory.create();
        var contentCategoryDetail = contentCategoryDetailFactory.create(contentCategory,
                contentCatalog, contentCategoryName, parentContentCategory, defaultOfferUse, itemSelector, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        contentCategory = contentCategoryFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentCategory.getPrimaryKey());
        contentCategory.setActiveDetail(contentCategoryDetail);
        contentCategory.setLastDetail(contentCategoryDetail);
        contentCategory.store();
        
        sendEvent(contentCategory.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentCategory;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContentCategory */
    public ContentCategory getContentCategoryByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContentCategoryPK(entityInstance.getEntityUniqueId());

        return contentCategoryFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContentCategory getContentCategoryByEntityInstance(EntityInstance entityInstance) {
        return getContentCategoryByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContentCategory getContentCategoryByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContentCategoryByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContentCategories() {
        return session.getDslContext()
                .selectCount()
                .from(ContentCategories)
                .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContentCategoriesByContentCatalog(ContentCatalog contentCatalog) {
        return session.getDslContext()
                .selectCount()
                .from(ContentCategories)
                .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                .where(ContentCategoryDetails.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContentCategoriesByParentContentCategory(ContentCategory parentContentCategory) {
        return session.getDslContext()
                .selectCount()
                .from(ContentCategories)
                .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                .where(ContentCategoryDetails.PARENT_CONTENT_CATEGORY.eq(parentContentCategory.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContentCategoriesByDefaultOfferUse(OfferUse defaultOfferUse) {
        return session.getDslContext()
                .selectCount()
                .from(ContentCategories)
                .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                .where(ContentCategoryDetails.DEFAULT_OFFER_USE.eq(defaultOfferUse.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<ContentCategory> getContentCategories(ContentCatalog contentCatalog, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCategories.fields())
                    .from(ContentCategories)
                    .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                    .where(ContentCategoryDetails.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()))
                    .orderBy(ContentCategoryDetails.SORT_ORDER, ContentCategoryDetails.CONTENT_CATEGORY_NAME),
                    ContentCategoryFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCategories.fields())
                    .from(ContentCategories)
                    .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                    .where(ContentCategoryDetails.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()))
                    .orderBy(ContentCategoryDetails.SORT_ORDER, ContentCategoryDetails.CONTENT_CATEGORY_NAME)
                    .forUpdate();
        };

        return contentCategoryFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentCategory> getContentCategories(ContentCatalog contentCatalog) {
        return getContentCategories(contentCatalog, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCategory> getContentCategoriesForUpdate(ContentCatalog contentCatalog) {
        return getContentCategories(contentCatalog, EntityPermission.READ_WRITE);
    }
    
    private List<ContentCategory> getContentCategoriesByParentContentCategory(ContentCategory parentContentCategory, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCategories.fields())
                    .from(ContentCategories)
                    .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                    .where(ContentCategoryDetails.PARENT_CONTENT_CATEGORY.eq(parentContentCategory.getPrimaryKey()))
                    .orderBy(ContentCategoryDetails.SORT_ORDER, ContentCategoryDetails.CONTENT_CATEGORY_NAME),
                    ContentCategoryFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCategories.fields())
                    .from(ContentCategories)
                    .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                    .where(ContentCategoryDetails.PARENT_CONTENT_CATEGORY.eq(parentContentCategory.getPrimaryKey()))
                    .orderBy(ContentCategoryDetails.SORT_ORDER, ContentCategoryDetails.CONTENT_CATEGORY_NAME)
                    .forUpdate();
        };

        return contentCategoryFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContentCategory> getContentCategoriesByParentContentCategory(ContentCategory parentContentCategory) {
        return getContentCategoriesByParentContentCategory(parentContentCategory, EntityPermission.READ_ONLY);
    }

    public List<ContentCategory> getContentCategoriesByParentContentCategoryForUpdate(ContentCategory parentContentCategory) {
        return getContentCategoriesByParentContentCategory(parentContentCategory, EntityPermission.READ_WRITE);
    }

    private List<ContentCategory> getContentCategoriesByDefaultOfferUse(OfferUse defaultOfferUse, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCategories.fields())
                    .from(ContentCategories)
                    .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                    .join(ContentCatalogs).onKey(CONTENT_CATEGORY_DETAILS_CONTENT_CATALOG_FK)
                    .join(ContentCatalogDetails).onKey(CONTENT_CATALOGS_LAST_DETAIL_FK)
                    .join(ContentCollections).onKey(CONTENT_CATALOG_DETAILS_CONTENT_COLLECTION_FK)
                    .join(ContentCollectionDetails).onKey(CONTENT_COLLECTIONS_LAST_DETAIL_FK)
                    .where(ContentCategoryDetails.DEFAULT_OFFER_USE.eq(defaultOfferUse.getPrimaryKey()))
                    .orderBy(ContentCollectionDetails.CONTENT_COLLECTION_NAME, ContentCatalogDetails.SORT_ORDER, ContentCatalogDetails.CONTENT_CATALOG_NAME, ContentCategoryDetails.SORT_ORDER, ContentCategoryDetails.CONTENT_CATEGORY_NAME),
                    ContentCategoryFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCategories.fields())
                    .from(ContentCategories)
                    .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                    .where(ContentCategoryDetails.DEFAULT_OFFER_USE.eq(defaultOfferUse.getPrimaryKey()))
                    .orderBy(ContentCategoryDetails.SORT_ORDER, ContentCategoryDetails.CONTENT_CATEGORY_NAME)
                    .forUpdate();
        };

        return contentCategoryFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContentCategory> getContentCategoriesByDefaultOfferUse(OfferUse defaultOfferUse) {
        return getContentCategoriesByDefaultOfferUse(defaultOfferUse, EntityPermission.READ_ONLY);
    }

    public List<ContentCategory> getContentCategoriesByDefaultOfferUseForUpdate(OfferUse defaultOfferUse) {
        return getContentCategoriesByDefaultOfferUse(defaultOfferUse, EntityPermission.READ_WRITE);
    }

    private List<ContentCategory> getContentCategoriesByContentCategoryItemSelector(Selector contentCategoryItemSelector, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCategories.fields())
                    .from(ContentCategories)
                    .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                    .join(ContentCatalogs).onKey(CONTENT_CATEGORY_DETAILS_CONTENT_CATALOG_FK)
                    .join(ContentCatalogDetails).onKey(CONTENT_CATALOGS_LAST_DETAIL_FK)
                    .join(ContentCollections).onKey(CONTENT_CATALOG_DETAILS_CONTENT_COLLECTION_FK)
                    .join(ContentCollectionDetails).onKey(CONTENT_COLLECTIONS_LAST_DETAIL_FK)
                    .where(ContentCategoryDetails.CONTENT_CATEGORY_ITEM_SELECTOR.eq(contentCategoryItemSelector.getPrimaryKey()))
                    .orderBy(ContentCollectionDetails.CONTENT_COLLECTION_NAME, ContentCatalogDetails.SORT_ORDER, ContentCatalogDetails.CONTENT_CATALOG_NAME, ContentCategoryDetails.SORT_ORDER, ContentCategoryDetails.CONTENT_CATEGORY_NAME),
                    ContentCategoryFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCategories.fields())
                    .from(ContentCategories)
                    .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                    .where(ContentCategoryDetails.CONTENT_CATEGORY_ITEM_SELECTOR.eq(contentCategoryItemSelector.getPrimaryKey()))
                    .orderBy(ContentCategoryDetails.SORT_ORDER, ContentCategoryDetails.CONTENT_CATEGORY_NAME)
                    .forUpdate();
        };

        return contentCategoryFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ContentCategory> getContentCategoriesByParentContentCategory(Selector contentCategoryItemSelector) {
        return getContentCategoriesByContentCategoryItemSelector(contentCategoryItemSelector, EntityPermission.READ_ONLY);
    }

    public List<ContentCategory> getContentCategoriesByContentCategoryItemSelectorForUpdate(Selector contentCategoryItemSelector) {
        return getContentCategoriesByContentCategoryItemSelector(contentCategoryItemSelector, EntityPermission.READ_WRITE);
    }

    private ContentCategory getContentCategoryByName(ContentCatalog contentCatalog, String contentCategoryName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentCategories.fields())
                .from(ContentCategories)
                .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                .where(ContentCategoryDetails.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                        ContentCategoryDetails.CONTENT_CATEGORY_NAME.eq(contentCategoryName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentCategoryFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentCategory getContentCategoryByName(ContentCatalog contentCatalog, String contentCategoryName) {
        return getContentCategoryByName(contentCatalog, contentCategoryName, EntityPermission.READ_ONLY);
    }
    
    public ContentCategory getContentCategoryByNameForUpdate(ContentCatalog contentCatalog, String contentCategoryName) {
        return getContentCategoryByName(contentCatalog, contentCategoryName, EntityPermission.READ_WRITE);
    }
    
    public ContentCategoryDetailValue getContentCategoryDetailValueForUpdate(ContentCategory contentCategory) {
        return contentCategory == null? null: contentCategory.getLastDetailForUpdate().getContentCategoryDetailValue().clone();
    }
    
    public ContentCategoryDetailValue getContentCategoryDetailValueByNameForUpdate(ContentCatalog contentCatalog, String contentCategoryName) {
        return getContentCategoryDetailValueForUpdate(getContentCategoryByNameForUpdate(contentCatalog, contentCategoryName));
    }
    
    private ContentCategory getDefaultContentCategory(ContentCatalog contentCatalog, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentCategories.fields())
                .from(ContentCategories)
                .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_ACTIVE_DETAIL_FK)
                .where(ContentCategoryDetails.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                        ContentCategoryDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentCategoryFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentCategory getDefaultContentCategory(ContentCatalog contentCatalog) {
        return getDefaultContentCategory(contentCatalog, EntityPermission.READ_ONLY);
    }
    
    public ContentCategoryDetailValue getDefaultContentCategoryDetailValueForUpdate(ContentCatalog contentCatalog) {
        return getDefaultContentCategory(contentCatalog, EntityPermission.READ_WRITE).getLastDetailForUpdate().getContentCategoryDetailValue().clone();
    }
    
    public ContentCategoryChoicesBean getContentCategoryChoices(ContentCatalog contentCatalog, String defaultContentCategoryChoice, Language language, boolean allowNullChoice) {
        var contentCategorys = getContentCategories(contentCatalog);
        var size = contentCategorys.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultContentCategoryChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var contentCategory : contentCategorys) {
            var contentCategoryDetail = contentCategory.getLastDetail();
            var value = contentCategoryDetail.getContentCategoryName();
            
            if(!value.equals(com.echothree.model.control.content.common.ContentCategories.ROOT.toString())) {
                var label = getBestContentCategoryDescription(contentCategory, language);

                labels.add(label == null? value: label);
                values.add(value);

                var usingDefaultChoice = Objects.equals(defaultContentCategoryChoice, value);
                if(usingDefaultChoice || (defaultValue == null && contentCategoryDetail.getIsDefault())) {
                    defaultValue = value;
                }
            }
        }
        
        return new ContentCategoryChoicesBean(labels, values, defaultValue);
    }
    
    public List<ContentCategoryTransfer> getContentCategoryTransfers(UserVisit userVisit, Collection<ContentCategory> contentCategories) {
        List<ContentCategoryTransfer> contentCategoryTransfers = new ArrayList<>(contentCategories.size());

        contentCategories.forEach((contentCategory) ->
                contentCategoryTransfers.add(contentCategoryTransferCache.getContentCategoryTransfer(userVisit, contentCategory))
        );
        
        return contentCategoryTransfers;
    }
    
    public ContentCategoryTransfer getContentCategoryTransfer(UserVisit userVisit, ContentCategory contentCategory) {
        return contentCategoryTransferCache.getContentCategoryTransfer(userVisit, contentCategory);
    }
    
    public List<ContentCategoryTransfer> getContentCategoryTransfers(UserVisit userVisit, ContentCatalog contentCatalog) {
        return getContentCategoryTransfers(userVisit, getContentCategories(contentCatalog));
    }
    
    public List<ContentCategoryTransfer> getContentCategoryTransfersByParentContentCategory(UserVisit userVisit, ContentCategory parentContentCategory) {
        return getContentCategoryTransfers(userVisit, getContentCategoriesByParentContentCategory(parentContentCategory));
    }
    
    public boolean isParentContentCategorySafe(ContentCategory contentCategory, ContentCategory parentContentCategory) {
        var safe = true;
        
        if(parentContentCategory != null) {
            Set<ContentCategory> parentItemPurchasingCategories = new HashSet<>();
            
            parentItemPurchasingCategories.add(contentCategory);
            do {
                if(parentItemPurchasingCategories.contains(parentContentCategory)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategories.add(parentContentCategory);
                parentContentCategory = parentContentCategory.getLastDetail().getParentContentCategory();
            } while(parentContentCategory != null);
        }
        
        return safe;
    }
    
    private void updateContentCategoryFromValue(ContentCategoryDetailValue contentCategoryDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(contentCategoryDetailValue.hasBeenModified()) {
            var contentCategory = contentCategoryFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentCategoryDetailValue.getContentCategoryPK());
            var contentCategoryDetail = contentCategory.getActiveDetailForUpdate();

            contentCategoryDetail.setThruTime(session.getStartTime());
            contentCategoryDetail.store();

            var contentCategoryPK = contentCategoryDetail.getContentCategoryPK();
            var contentCatalogPK = contentCategoryDetail.getContentCatalogPK();
            var contentCategoryName = contentCategoryDetailValue.getContentCategoryName();
            var parentContentCategoryPK = contentCategoryDetailValue.getParentContentCategoryPK();
            var defaultOfferUsePK = contentCategoryDetailValue.getDefaultOfferUsePK();
            var itemSelectorPK = contentCategoryDetailValue.getContentCategoryItemSelectorPK();
            var isDefault = contentCategoryDetailValue.getIsDefault();
            var sortOrder = contentCategoryDetailValue.getSortOrder();

            if(checkDefault) {
                var contentCatalog = contentCategoryDetail.getContentCatalog();
                var defaultContentCategory = getDefaultContentCategory(contentCatalog);
                var defaultFound = defaultContentCategory != null && !defaultContentCategory.equals(contentCategory);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultContentCategoryDetailValue = getDefaultContentCategoryDetailValueForUpdate(contentCatalog);

                    defaultContentCategoryDetailValue.setIsDefault(false);
                    updateContentCategoryFromValue(defaultContentCategoryDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            contentCategoryDetail = contentCategoryDetailFactory.create(contentCategoryPK, contentCatalogPK,
                    contentCategoryName, parentContentCategoryPK, defaultOfferUsePK, itemSelectorPK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            contentCategory.setActiveDetail(contentCategoryDetail);
            contentCategory.setLastDetail(contentCategoryDetail);
            contentCategory.store();

            sendEvent(contentCategoryPK, EventTypes.MODIFY, null, null, updatedBy);

            if(contentCategoryDetailValue.getDefaultOfferUsePKHasBeenModified()) {
                contentLogic.updateContentCatalogItemPricesByContentCategory(contentCategoryDetail.getContentCategory(), updatedBy);
            }
        }
    }
    
    public void updateContentCategoryFromValue(ContentCategoryDetailValue contentCategoryDetailValue, BasePK updatedBy) {
        updateContentCategoryFromValue(contentCategoryDetailValue, true, updatedBy);
    }

    public ContentCategory getContentCategoryByPK(ContentCategoryPK contentCategoryPK) {
        return contentCategoryFactory.getEntityFromPK(EntityPermission.READ_ONLY, contentCategoryPK);
    }

    private void deleteContentCategory(ContentCategory contentCategory, BasePK deletedBy, boolean recursive) {
        if(recursive) {
            getContentCategoriesByParentContentCategoryForUpdate(contentCategory).forEach((childContentCategory) -> {
                deleteContentCategory(childContentCategory, deletedBy, true);
            });
        }
        
        deleteContentCategoryItemsByContentCategory(contentCategory, deletedBy);
        deleteContentCategoryDescriptionsByContentCategory(contentCategory, deletedBy);

        var contentCategoryDetail = contentCategory.getLastDetailForUpdate();
        contentCategoryDetail.setThruTime(session.getStartTime());
        contentCategory.setActiveDetail(null);
        contentCategory.store();
        
        // Check for default, and pick one if necessary
        var contentCatalog = contentCategoryDetail.getContentCatalog();
        var defaultContentCategory = getDefaultContentCategory(contentCatalog);
        if(defaultContentCategory == null) {
            // Try and find a new default from sections on the same level as the one that was deleted
            var parentContentCategory = contentCategoryDetail.getParentContentCategory();
            var contentCategories = parentContentCategory == null? null: getContentCategoriesByParentContentCategoryForUpdate(parentContentCategory);
            
            // If that failed, pick one from the current collection
            if(contentCategories == null || contentCategories.isEmpty()) {
                contentCategories = getContentCategoriesForUpdate(contentCatalog);
            }
            
            if(!contentCategories.isEmpty()) {
                for(var iter = contentCategories.iterator(); iter.hasNext();) {
                    defaultContentCategory = iter.next();
                    if(!defaultContentCategory.getLastDetail().getContentCategoryName().equals(com.echothree.model.control.content.common.ContentCategories.ROOT.toString()))
                        break;
                }
                var contentCategoryDetailValue = Objects.requireNonNull(defaultContentCategory).getLastDetailForUpdate().getContentCategoryDetailValue().clone();
                
                contentCategoryDetailValue.setIsDefault(true);
                updateContentCategoryFromValue(contentCategoryDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(contentCategory.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteContentCategory(ContentCategory contentCategory, BasePK deletedBy) {
        deleteContentCategory(contentCategory, deletedBy, true);
    }
    
    public void deleteContentCategoriesByContentCatalog(ContentCatalog contentCatalog, BasePK deletedBy) {
        getContentCategoriesForUpdate(contentCatalog).forEach((contentCategory) -> {
            deleteContentCategory(contentCategory, deletedBy, false);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Category Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentCategoryDescriptionFactory contentCategoryDescriptionFactory;

    @Inject
    ContentCategoryDescriptionTransferCache contentCategoryDescriptionTransferCache;

    public ContentCategoryDescription createContentCategoryDescription(ContentCategory contentCategory, Language language,
            String description, BasePK createdBy) {

        var contentCategoryDescription = contentCategoryDescriptionFactory.create(
                contentCategory, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(contentCategory.getPrimaryKey(), EventTypes.MODIFY, contentCategoryDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentCategoryDescription;
    }
    
    private ContentCategoryDescription getContentCategoryDescription(ContentCategory contentCategory, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentCategoryDescriptions.fields())
                .from(ContentCategoryDescriptions)
                .where(ContentCategoryDescriptions.CONTENT_CATEGORY.eq(contentCategory.getPrimaryKey()),
                        ContentCategoryDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ContentCategoryDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentCategoryDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentCategoryDescription getContentCategoryDescription(ContentCategory contentCategory, Language language) {
        return getContentCategoryDescription(contentCategory, language, EntityPermission.READ_ONLY);
    }
    
    public ContentCategoryDescription getContentCategoryDescriptionForUpdate(ContentCategory contentCategory, Language language) {
        return getContentCategoryDescription(contentCategory, language, EntityPermission.READ_WRITE);
    }
    
    public ContentCategoryDescriptionValue getContentCategoryDescriptionValue(ContentCategoryDescription contentCategoryDescription) {
        return contentCategoryDescription == null? null: contentCategoryDescription.getContentCategoryDescriptionValue().clone();
    }
    
    public ContentCategoryDescriptionValue getContentCategoryDescriptionValueForUpdate(ContentCategory contentCategory, Language language) {
        return getContentCategoryDescriptionValue(getContentCategoryDescriptionForUpdate(contentCategory, language));
    }
    
    private List<ContentCategoryDescription> getContentCategoryDescriptionsByContentCategory(ContentCategory contentCategory, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCategoryDescriptions.fields())
                    .from(ContentCategoryDescriptions)
                    .join(Languages).onKey(CONTENT_CATEGORY_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ContentCategoryDescriptions.CONTENT_CATEGORY.eq(contentCategory.getPrimaryKey()),
                            ContentCategoryDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ContentCategoryDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCategoryDescriptions.fields())
                    .from(ContentCategoryDescriptions)
                    .where(ContentCategoryDescriptions.CONTENT_CATEGORY.eq(contentCategory.getPrimaryKey()),
                            ContentCategoryDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentCategoryDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentCategoryDescription> getContentCategoryDescriptionsByContentCategory(ContentCategory contentCategory) {
        return getContentCategoryDescriptionsByContentCategory(contentCategory, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCategoryDescription> getContentCategoryDescriptionsByContentCategoryForUpdate(ContentCategory contentCategory) {
        return getContentCategoryDescriptionsByContentCategory(contentCategory, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentCategoryDescription(ContentCategory contentCategory, Language language) {
        String description;
        var contentCategoryDescription = getContentCategoryDescription(contentCategory, language);
        
        if(contentCategoryDescription == null && !language.getIsDefault()) {
            contentCategoryDescription = getContentCategoryDescription(contentCategory, partyControl.getDefaultLanguage());
        }
        
        if(contentCategoryDescription == null) {
            description = contentCategory.getLastDetail().getContentCategoryName();
        } else {
            description = contentCategoryDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentCategoryDescriptionTransfer getContentCategoryDescriptionTransfer(UserVisit userVisit, ContentCategoryDescription contentCategoryDescription) {
        return contentCategoryDescriptionTransferCache.getContentCategoryDescriptionTransfer(userVisit, contentCategoryDescription);
    }
    
    public List<ContentCategoryDescriptionTransfer> getContentCategoryDescriptionTransfers(UserVisit userVisit, ContentCategory contentCategory) {
        var contentCategoryDescriptions = getContentCategoryDescriptionsByContentCategory(contentCategory);
        List<ContentCategoryDescriptionTransfer> contentCategoryDescriptionTransfers = new ArrayList<>(contentCategoryDescriptions.size());

        contentCategoryDescriptions.forEach((contentCategoryDescription) ->
                contentCategoryDescriptionTransfers.add(contentCategoryDescriptionTransferCache.getContentCategoryDescriptionTransfer(userVisit, contentCategoryDescription))
        );
        
        return contentCategoryDescriptionTransfers;
    }
    
    public void updateContentCategoryDescriptionFromValue(ContentCategoryDescriptionValue contentCategoryDescriptionValue, BasePK updatedBy) {
        if(contentCategoryDescriptionValue.hasBeenModified()) {
            var contentCategoryDescription = contentCategoryDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentCategoryDescriptionValue.getPrimaryKey());
            
            contentCategoryDescription.setThruTime(session.getStartTime());
            contentCategoryDescription.store();

            var contentCategory = contentCategoryDescription.getContentCategory();
            var language = contentCategoryDescription.getLanguage();
            var description = contentCategoryDescriptionValue.getDescription();
            
            contentCategoryDescription = contentCategoryDescriptionFactory.create(contentCategory, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contentCategory.getPrimaryKey(), EventTypes.MODIFY, contentCategoryDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentCategoryDescription(ContentCategoryDescription contentCategoryDescription, BasePK deletedBy) {
        contentCategoryDescription.setThruTime(session.getStartTime());
        
        sendEvent(contentCategoryDescription.getContentCategoryPK(), EventTypes.MODIFY, contentCategoryDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentCategoryDescriptionsByContentCategory(ContentCategory contentCategory, BasePK deletedBy) {
        getContentCategoryDescriptionsByContentCategoryForUpdate(contentCategory).forEach((contentCategoryDescription) -> {
            deleteContentCategoryDescription(contentCategoryDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Category Items
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentCategoryItemFactory contentCategoryItemFactory;

    @Inject
    ContentCategoryItemTransferCache contentCategoryItemTransferCache;

    /** Use the function in ContentLogic instead. */
    public ContentCategoryItem createContentCategoryItem(ContentCategory contentCategory, ContentCatalogItem contentCatalogItem,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultContentCategoryItem = getDefaultContentCategoryItem(contentCatalogItem);
        var defaultFound = defaultContentCategoryItem != null;
        
        if(defaultFound && isDefault) {
            var defaultContentCategoryItemValue = getDefaultContentCategoryItemValueForUpdate(contentCatalogItem);
            
            defaultContentCategoryItemValue.setIsDefault(false);
            updateContentCategoryItemFromValue(defaultContentCategoryItemValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var contentCategoryItem = contentCategoryItemFactory.create(
                contentCategory, contentCatalogItem, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(contentCategory.getPrimaryKey(), EventTypes.MODIFY, contentCategoryItem.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentCategoryItem;
    }
    
    public long countContentCategoryItemsByContentCategory(ContentCategory contentCategory) {
        return session.getDslContext()
                .selectCount()
                .from(ContentCategoryItems)
                .where(ContentCategoryItems.CONTENT_CATEGORY.eq(contentCategory.getPrimaryKey()),
                        ContentCategoryItems.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ContentCategoryItem getContentCategoryItem(ContentCategory contentCategory, ContentCatalogItem contentCatalogItem,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentCategoryItems.fields())
                .from(ContentCategoryItems)
                .where(ContentCategoryItems.CONTENT_CATEGORY.eq(contentCategory.getPrimaryKey()),
                        ContentCategoryItems.CONTENT_CATALOG_ITEM.eq(contentCatalogItem.getPrimaryKey()),
                        ContentCategoryItems.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentCategoryItemFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentCategoryItem getContentCategoryItem(ContentCategory contentCategory, ContentCatalogItem contentCatalogItem) {
        return getContentCategoryItem(contentCategory, contentCatalogItem, EntityPermission.READ_ONLY);
    }
    
    public ContentCategoryItem getContentCategoryItemForUpdate(ContentCategory contentCategory, ContentCatalogItem contentCatalogItem) {
        return getContentCategoryItem(contentCategory, contentCatalogItem, EntityPermission.READ_WRITE);
    }
    
    public ContentCategoryItemValue getContentCategoryItemValue(ContentCategoryItem contentCategoryItem) {
        return contentCategoryItem == null? null: contentCategoryItem.getContentCategoryItemValue().clone();
    }
    
    public ContentCategoryItemValue getContentCategoryItemValueForUpdate(ContentCategory contentCategory, ContentCatalogItem contentCatalogItem) {
        return getContentCategoryItemValue(getContentCategoryItemForUpdate(contentCategory, contentCatalogItem));
    }
    
    private ContentCategoryItem getDefaultContentCategoryItem(ContentCatalogItem contentCatalogItem, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentCategoryItems.fields())
                .from(ContentCategoryItems)
                .where(ContentCategoryItems.CONTENT_CATALOG_ITEM.eq(contentCatalogItem.getPrimaryKey()),
                        ContentCategoryItems.IS_DEFAULT.eq(true),
                        ContentCategoryItems.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentCategoryItemFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentCategoryItem getDefaultContentCategoryItem(ContentCatalogItem contentCatalogItem) {
        return getDefaultContentCategoryItem(contentCatalogItem, EntityPermission.READ_ONLY);
    }
    
    public ContentCategoryItem getDefaultContentCategoryItemForUpdate(ContentCatalogItem contentCatalogItem) {
        return getDefaultContentCategoryItem(contentCatalogItem, EntityPermission.READ_WRITE);
    }
    
    public ContentCategoryItemValue getDefaultContentCategoryItemValueForUpdate(ContentCatalogItem contentCatalogItem) {
        var contentCategoryItem = getDefaultContentCategoryItemForUpdate(contentCatalogItem);
        
        return contentCategoryItem == null? null: contentCategoryItem.getContentCategoryItemValue().clone();
    }
    
    private List<ContentCategoryItem> getContentCategoryItemsByContentCategory(ContentCategory contentCategory, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCategoryItems.fields())
                    .from(ContentCategoryItems)
                    .join(ContentCatalogItems).onKey(CONTENT_CATEGORY_ITEMS_CONTENT_CATALOG_ITEM_FK)
                    .join(Items).onKey(CONTENT_CATALOG_ITEMS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .join(InventoryConditions).onKey(CONTENT_CATALOG_ITEMS_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(CONTENT_CATALOG_ITEMS_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(Currencies).onKey(CONTENT_CATALOG_ITEMS_CURRENCY_FK)
                    .where(ContentCategoryItems.CONTENT_CATEGORY.eq(contentCategory.getPrimaryKey()),
                            ContentCategoryItems.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentCategoryItems.SORT_ORDER, ItemDetails.ITEM_NAME, InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    ContentCategoryItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCategoryItems.fields())
                    .from(ContentCategoryItems)
                    .where(ContentCategoryItems.CONTENT_CATEGORY.eq(contentCategory.getPrimaryKey()),
                            ContentCategoryItems.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentCategoryItemFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentCategoryItem> getContentCategoryItemsByContentCategory(ContentCategory contentCategory) {
        return getContentCategoryItemsByContentCategory(contentCategory, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCategoryItem> getContentCategoryItemsByContentCategoryForUpdate(ContentCategory contentCategory) {
        return getContentCategoryItemsByContentCategory(contentCategory, EntityPermission.READ_WRITE);
    }
    
    private List<ContentCategoryItem> getContentCategoryItemsByContentCatalogItem(ContentCatalogItem contentCatalogItem, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentCategoryItems.fields())
                    .from(ContentCategoryItems)
                    .join(ContentCategories).onKey(CONTENT_CATEGORY_ITEMS_CONTENT_CATEGORY_FK)
                    .join(ContentCategoryDetails).onKey(CONTENT_CATEGORIES_LAST_DETAIL_FK)
                    .where(ContentCategoryItems.CONTENT_CATALOG_ITEM.eq(contentCatalogItem.getPrimaryKey()),
                            ContentCategoryItems.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentCategoryDetails.SORT_ORDER, ContentCategoryDetails.CONTENT_CATEGORY_NAME),
                    ContentCategoryItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentCategoryItems.fields())
                    .from(ContentCategoryItems)
                    .where(ContentCategoryItems.CONTENT_CATALOG_ITEM.eq(contentCatalogItem.getPrimaryKey()),
                            ContentCategoryItems.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentCategoryItemFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentCategoryItem> getContentCategoryItemsByContentCatalogItem(ContentCatalogItem contentCatalogItem) {
        return getContentCategoryItemsByContentCatalogItem(contentCatalogItem, EntityPermission.READ_ONLY);
    }
    
    public List<ContentCategoryItem> getContentCategoryItemsByContentCatalogItemForUpdate(ContentCatalogItem contentCatalogItem) {
        return getContentCategoryItemsByContentCatalogItem(contentCatalogItem, EntityPermission.READ_WRITE);
    }
    
    public ContentCategoryItemTransfer getContentCategoryItemTransfer(UserVisit userVisit, ContentCategoryItem contentCategoryItem) {
        return contentCategoryItemTransferCache.getContentCategoryItemTransfer(userVisit, contentCategoryItem);
    }
    
    public List<ContentCategoryItemTransfer> getContentCategoryItemTransfers(UserVisit userVisit, Collection<ContentCategoryItem> contentCategoryItems) {
        List<ContentCategoryItemTransfer> contentCategoryItemTransfers = new ArrayList<>(contentCategoryItems.size());
        
        contentCategoryItems.forEach((contentCategoryItem) ->
                contentCategoryItemTransfers.add(contentCategoryItemTransferCache.getContentCategoryItemTransfer(userVisit, contentCategoryItem))
        );
        
        return contentCategoryItemTransfers;
    }
    
    public List<ContentCategoryItemTransfer> getContentCategoryItemTransfersByContentCategory(UserVisit userVisit, ContentCategory contentCategory) {
        return getContentCategoryItemTransfers(userVisit, getContentCategoryItemsByContentCategory(contentCategory));
    }
    
    public long countContentCategoryItemsByContentCatalogItem(ContentCatalogItem contentCatalogItem) {
        return session.getDslContext()
                .selectCount()
                .from(ContentCategoryItems)
                .where(ContentCategoryItems.CONTENT_CATALOG_ITEM.eq(contentCatalogItem.getPrimaryKey()),
                        ContentCategoryItems.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private void updateContentCategoryItemFromValue(ContentCategoryItemValue contentCategoryItemValue, boolean checkDefault, BasePK updatedBy) {
        if(contentCategoryItemValue.hasBeenModified()) {
            var contentCategoryItem = contentCategoryItemFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     contentCategoryItemValue.getPrimaryKey());
            
            contentCategoryItem.setThruTime(session.getStartTime());
            contentCategoryItem.store();

            var contentCategoryPK = contentCategoryItem.getContentCategoryPK(); // Not Updated
            var contentCatalogItem = contentCategoryItem.getContentCatalogItem(); // Not Updated
            var contentCatalogItemPK = contentCategoryItem.getContentCatalogItemPK(); // Not Updated
            var isDefault = contentCategoryItemValue.getIsDefault();
            var sortOrder = contentCategoryItemValue.getSortOrder();
            
            if(checkDefault) {
                var defaultContentCategoryItem = getDefaultContentCategoryItem(contentCatalogItem);
                var defaultFound = defaultContentCategoryItem != null && !defaultContentCategoryItem.equals(contentCategoryItem);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultContentCategoryItemValue = getDefaultContentCategoryItemValueForUpdate(contentCatalogItem);
                    
                    defaultContentCategoryItemValue.setIsDefault(false);
                    updateContentCategoryItemFromValue(defaultContentCategoryItemValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            contentCategoryItem = contentCategoryItemFactory.create(contentCategoryPK, contentCatalogItemPK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contentCategoryPK, EventTypes.MODIFY, contentCategoryItem.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    /** Use the function in ContentLogic instead. */
    public void updateContentCategoryItemFromValue(ContentCategoryItemValue contentCategoryItemValue, BasePK updatedBy) {
        updateContentCategoryItemFromValue(contentCategoryItemValue, true, updatedBy);
    }
    
    /** Use the function in ContentLogic instead. */
    private void deleteContentCategoryItem(ContentCategoryItem contentCategoryItem, boolean checkDefault, BasePK deletedBy) {
        var contentCatalogItem = contentCategoryItem.getContentCatalogItemForUpdate();
        
        contentCategoryItem.setThruTime(session.getStartTime());
        contentCategoryItem.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultContentCategoryItem = getDefaultContentCategoryItem(contentCatalogItem);
            if(defaultContentCategoryItem == null) {
                var contentCategoryItems = getContentCategoryItemsByContentCatalogItemForUpdate(contentCatalogItem);

                if(!contentCategoryItems.isEmpty()) {
                    var iter = contentCategoryItems.iterator();
                    if(iter.hasNext()) {
                        defaultContentCategoryItem = iter.next();
                    }
                    var contentCategoryItemValue = defaultContentCategoryItem.getContentCategoryItemValue().clone();

                    contentCategoryItemValue.setIsDefault(true);
                    updateContentCategoryItemFromValue(contentCategoryItemValue, false, deletedBy);
                }
            }
        }

        sendEvent(contentCategoryItem.getContentCategoryPK(), EventTypes.MODIFY, contentCategoryItem.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteContentCategoryItem(ContentCategoryItem contentCategoryItem,BasePK deletedBy) {
        deleteContentCategoryItem(contentCategoryItem, true, deletedBy);
    }
    
    public void deleteContentCategoryItemsByContentCategory(ContentCategory contentCategory, BasePK deletedBy) {
        getContentCategoryItemsByContentCategoryForUpdate(contentCategory).forEach((contentCategoryItem) -> {
            contentLogic.deleteContentCategoryItem(contentCategoryItem, deletedBy);
        });
    }

    private void deleteContentCategoryItemsByContentCatalogItem(ContentCatalogItem contentCatalogItem, BasePK deletedBy) {
        getContentCategoryItemsByContentCatalogItemForUpdate(contentCatalogItem).forEach((contentCategoryItem) -> {
            deleteContentCategoryItem(contentCategoryItem, false, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Content Forums
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentForumDetailFactory contentForumDetailFactory;

    @Inject
    protected ContentForumFactory contentForumFactory;

    @Inject
    ContentForumTransferCache contentForumTransferCache;

    public ContentForum createContentForum(ContentCollection contentCollection, Forum forum, Boolean isDefault, BasePK createdBy) {
        var defaultContentForum = getDefaultContentForum(contentCollection);
        var defaultFound = defaultContentForum != null;
        
        if(defaultFound && isDefault) {
            var defaultContentForumDetailValue = getDefaultContentForumDetailValueForUpdate(contentCollection);
            
            defaultContentForumDetailValue.setIsDefault(false);
            updateContentForumFromValue(defaultContentForumDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var contentForum = contentForumFactory.create();
        var contentForumDetail = contentForumDetailFactory.create(contentForum, contentCollection, forum, isDefault,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        contentForum = contentForumFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentForum.getPrimaryKey());
        contentForum.setActiveDetail(contentForumDetail);
        contentForum.setLastDetail(contentForumDetail);
        contentForum.store();
        
        sendEvent(contentForum.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentForum;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContentForum */
    public ContentForum getContentForumByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContentForumPK(entityInstance.getEntityUniqueId());

        return contentForumFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContentForum getContentForumByEntityInstance(EntityInstance entityInstance) {
        return getContentForumByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContentForum getContentForumByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContentForumByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countContentForumsByContentCollection(ContentCollection contentCollection) {
        return session.getDslContext()
                .selectCount()
                .from(ContentForums)
                .join(ContentForumDetails).onKey(CONTENT_FORUMS_ACTIVE_DETAIL_FK)
                .where(ContentForumDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }
    
    private List<ContentForum> getContentForums(ContentCollection contentCollection, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentForums.fields())
                    .from(ContentForums)
                    .join(ContentForumDetails).onKey(CONTENT_FORUMS_ACTIVE_DETAIL_FK)
                    .join(Forums).onKey(CONTENT_FORUM_DETAILS_FORUM_FK)
                    .join(ForumDetails).onKey(FORUMS_LAST_DETAIL_FK)
                    .where(ContentForumDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()))
                    .orderBy(ForumDetails.SORT_ORDER, ForumDetails.FORUM_NAME),
                    ContentForumFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentForums.fields())
                    .from(ContentForums)
                    .join(ContentForumDetails).onKey(CONTENT_FORUMS_ACTIVE_DETAIL_FK)
                    .where(ContentForumDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()))
                    .forUpdate();
        };

        return contentForumFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentForum> getContentForums(ContentCollection contentCollection) {
        return getContentForums(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentForum> getContentForumsForUpdate(ContentCollection contentCollection) {
        return getContentForums(contentCollection, EntityPermission.READ_WRITE);
    }
    
    private ContentForum getContentForum(ContentCollection contentCollection, Forum forum, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentForums.fields())
                .from(ContentForums)
                .join(ContentForumDetails).onKey(CONTENT_FORUM_DETAILS_CONTENT_FORUM_FK)
                .where(ContentForumDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                        ContentForumDetails.FORUM.eq(forum.getPrimaryKey()),
                        ContentForumDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentForumFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentForum getContentForum(ContentCollection contentCollection, Forum forum) {
        return getContentForum(contentCollection, forum, EntityPermission.READ_ONLY);
    }
    
    public ContentForum getContentForumForUpdate(ContentCollection contentCollection, Forum forum) {
        return getContentForum(contentCollection, forum, EntityPermission.READ_WRITE);
    }
    
    public ContentForumDetailValue getContentForumDetailValueForUpdate(ContentCollection contentCollection, Forum forum) {
        var contentForum = getContentForumForUpdate(contentCollection, forum);
        
        return contentForum == null? null: contentForum.getLastDetailForUpdate().getContentForumDetailValue().clone();
    }
    
    private ContentForum getDefaultContentForum(ContentCollection contentCollection, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentForums.fields())
                .from(ContentForums)
                .join(ContentForumDetails).onKey(CONTENT_FORUM_DETAILS_CONTENT_FORUM_FK)
                .where(ContentForumDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                        ContentForumDetails.IS_DEFAULT.eq(true),
                        ContentForumDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentForumFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentForum getDefaultContentForum(ContentCollection contentCollection) {
        return getDefaultContentForum(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public ContentForumDetailValue getDefaultContentForumDetailValueForUpdate(ContentCollection contentCollection) {
        return getDefaultContentForum(contentCollection, EntityPermission.READ_WRITE).getLastDetailForUpdate().getContentForumDetailValue().clone();
    }
    
    public ContentForumTransfer getContentForumTransfer(UserVisit userVisit, ContentForum contentForum) {
        return contentForumTransferCache.getContentForumTransfer(userVisit, contentForum);
    }
    
    public List<ContentForumTransfer> getContentForumTransfers(UserVisit userVisit, Collection<ContentForum> contentForums) {
        List<ContentForumTransfer> contentForumTransfers = new ArrayList<>(contentForums.size());

        contentForums.forEach((contentForum) ->
                contentForumTransfers.add(contentForumTransferCache.getContentForumTransfer(userVisit, contentForum))
        );
        
        return contentForumTransfers;
    }
    
    public List<ContentForumTransfer> getContentForumTransfers(UserVisit userVisit, ContentCollection contentCollection) {
        return getContentForumTransfers(userVisit, getContentForums(contentCollection));
    }
    
    private void updateContentForumFromValue(ContentForumDetailValue contentForumDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(contentForumDetailValue.hasBeenModified()) {
            var contentForum = contentForumFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    contentForumDetailValue.getContentForumPK());
            var contentForumDetail = contentForum.getActiveDetailForUpdate();

            contentForumDetail.setThruTime(session.getStartTime());
            contentForumDetail.store();

            var contentForumPK = contentForumDetail.getContentForumPK();
            var contentCollection = contentForumDetail.getContentCollection();
            var contentCollectionPK = contentForumDetail.getContentCollectionPK();
            var forumPK = contentForumDetail.getForumPK();
            var isDefault = contentForumDetailValue.getIsDefault();

            if(checkDefault) {
                var defaultContentForum = getDefaultContentForum(contentCollection);
                var defaultFound = defaultContentForum != null && !defaultContentForum.equals(contentForum);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultContentForumDetailValue = getDefaultContentForumDetailValueForUpdate(contentCollection);

                    defaultContentForumDetailValue.setIsDefault(false);
                    updateContentForumFromValue(defaultContentForumDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            contentForumDetail = contentForumDetailFactory.create(contentForumPK, contentCollectionPK, forumPK,
                    isDefault, session.getStartTime(), Session.MAX_TIME);

            contentForum.setActiveDetail(contentForumDetail);
            contentForum.setLastDetail(contentForumDetail);
            contentForum.store();

            sendEvent(contentForumPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateContentForumFromValue(ContentForumDetailValue contentForumDetailValue, BasePK updatedBy) {
        updateContentForumFromValue(contentForumDetailValue, true, updatedBy);
    }
    
    public void deleteContentForum(ContentForum contentForum, BasePK deletedBy) {
        var contentForumDetail = contentForum.getLastDetailForUpdate();
        contentForumDetail.setThruTime(session.getStartTime());
        contentForumDetail.store();
        contentForum.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var contentCollection = contentForumDetail.getContentCollection();
        var defaultContentForum = getDefaultContentForum(contentCollection);
        if(defaultContentForum == null) {
            var contentForums = getContentForumsForUpdate(contentCollection);
            
            if(!contentForums.isEmpty()) {
                for(var iter = contentForums.iterator(); iter.hasNext();) {
                    defaultContentForum = iter.next();
                    break;
                }
                var contentForumDetailValue = Objects.requireNonNull(defaultContentForum).getLastDetailForUpdate().getContentForumDetailValue().clone();
                
                contentForumDetailValue.setIsDefault(true);
                updateContentForumFromValue(contentForumDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(contentForum.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteContentForumsByContentCollection(ContentCollection contentCollection, BasePK deletedBy) {
        getContentForumsForUpdate(contentCollection).forEach((contentForum) -> {
            deleteContentForum(contentForum, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Addresses
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentWebAddressDetailFactory contentWebAddressDetailFactory;

    @Inject
    protected ContentWebAddressFactory contentWebAddressFactory;

    @Inject
    ContentWebAddressTransferCache contentWebAddressTransferCache;

    public ContentWebAddress createContentWebAddress(String contentWebAddressName, ContentCollection contentCollection,
            BasePK createdBy) {
        var contentWebAddress = contentWebAddressFactory.create();
        var contentWebAddressDetail = contentWebAddressDetailFactory.create(contentWebAddress,
                contentWebAddressName, contentCollection, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        contentWebAddress = contentWebAddressFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentWebAddress.getPrimaryKey());
        contentWebAddress.setActiveDetail(contentWebAddressDetail);
        contentWebAddress.setLastDetail(contentWebAddressDetail);
        contentWebAddress.store();
        
        sendEvent(contentWebAddress.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return contentWebAddress;
    }
    
    public boolean validContentWebAddressName(String contentWebAddressName) {
        return session.getDslContext()
                .selectCount()
                .from(ContentWebAddresses)
                .join(ContentWebAddressDetails).onKey(CONTENT_WEB_ADDRESSES_ACTIVE_DETAIL_FK)
                .where(ContentWebAddressDetails.CONTENT_WEB_ADDRESS_NAME.eq(contentWebAddressName))
                .fetchOptional(0, Long.class)
                .orElse(0L) == 1L;
    }

    public long countContentWebAddresses() {
        return session.getDslContext()
                .selectCount()
                .from(ContentWebAddresses)
                .join(ContentWebAddressDetails).onKey(CONTENT_WEB_ADDRESSES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countContentWebAddressesByContentCollection(ContentCollection contentCollection) {
        return session.getDslContext()
                .selectCount()
                .from(ContentWebAddresses)
                .join(ContentWebAddressDetails).onKey(CONTENT_WEB_ADDRESSES_ACTIVE_DETAIL_FK)
                .where(ContentWebAddressDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ContentWebAddress */
    public ContentWebAddress getContentWebAddressByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ContentWebAddressPK(entityInstance.getEntityUniqueId());

        return contentWebAddressFactory.getEntityFromPK(entityPermission, pk);
    }

    public ContentWebAddress getContentWebAddressByEntityInstance(EntityInstance entityInstance) {
        return getContentWebAddressByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ContentWebAddress getContentWebAddressByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getContentWebAddressByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<ContentWebAddress> getContentWebAddresses(EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentWebAddresses.fields())
                    .from(ContentWebAddresses)
                    .join(ContentWebAddressDetails).onKey(CONTENT_WEB_ADDRESS_DETAILS_CONTENT_WEB_ADDRESS_FK)
                    .where(ContentWebAddressDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentWebAddressDetails.CONTENT_WEB_ADDRESS_NAME),
                    ContentWebAddressFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentWebAddresses.fields())
                    .from(ContentWebAddresses)
                    .where(ContentWebAddressDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentWebAddressFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentWebAddress> getContentWebAddresses() {
        return getContentWebAddresses(EntityPermission.READ_ONLY);
    }
    
    public List<ContentWebAddress> getContentWebAddressesForUpdate() {
        return getContentWebAddresses(EntityPermission.READ_WRITE);
    }
    
    private ContentWebAddress getContentWebAddressByName(String contentWebAddressName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentWebAddresses.fields())
                .from(ContentWebAddresses)
                .join(ContentWebAddressDetails).onKey(CONTENT_WEB_ADDRESS_DETAILS_CONTENT_WEB_ADDRESS_FK)
                .where(ContentWebAddressDetails.CONTENT_WEB_ADDRESS_NAME.eq(contentWebAddressName),
                        ContentWebAddressDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentWebAddressFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentWebAddress getContentWebAddressByName(String contentWebAddressName) {
        return getContentWebAddressByName(contentWebAddressName, EntityPermission.READ_ONLY);
    }
    
    public ContentWebAddress getContentWebAddressByNameForUpdate(String contentWebAddressName) {
        return getContentWebAddressByName(contentWebAddressName, EntityPermission.READ_WRITE);
    }
    
    public ContentWebAddressDetailValue getContentWebAddressDetailValueForUpdate(ContentWebAddress contentWebAddress) {
        return contentWebAddress == null? null: contentWebAddress.getLastDetailForUpdate().getContentWebAddressDetailValue().clone();
    }
    
    public ContentWebAddressDetailValue getContentWebAddressDetailValueByNameForUpdate(String contentWebAddressName) {
        return getContentWebAddressDetailValueForUpdate(getContentWebAddressByNameForUpdate(contentWebAddressName));
    }
    
    private List<ContentWebAddress> getContentWebAddressesByContentCollection(ContentCollection contentCollection, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentWebAddresses.fields())
                .from(ContentWebAddresses)
                .join(ContentWebAddressDetails).onKey(CONTENT_WEB_ADDRESS_DETAILS_CONTENT_WEB_ADDRESS_FK)
                .where(ContentWebAddressDetails.CONTENT_COLLECTION.eq(contentCollection.getPrimaryKey()),
                        ContentWebAddressDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ContentWebAddressDetails.CONTENT_WEB_ADDRESS_NAME),
                    ContentWebAddressFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentWebAddressFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentWebAddress> getContentWebAddressesByContentCollection(ContentCollection contentCollection) {
        return getContentWebAddressesByContentCollection(contentCollection, EntityPermission.READ_ONLY);
    }
    
    public List<ContentWebAddress> getContentWebAddressesByContentCollectionForUpdate(ContentCollection contentCollection) {
        return getContentWebAddressesByContentCollection(contentCollection, EntityPermission.READ_WRITE);
    }
    
    public ContentWebAddressChoicesBean getContentWebAddressChoices(String defaultContentWebAddressChoice, Language language) {
        var contentWebAddresses = getContentWebAddresses();
        var size = contentWebAddresses.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        for(var contentWebAddress : contentWebAddresses) {
            var label = getBestContentWebAddressDescription(contentWebAddress, language);
            var value = contentWebAddress.getLastDetail().getContentWebAddressName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultContentWebAddressChoice != null && defaultContentWebAddressChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new ContentWebAddressChoicesBean(labels, values, defaultValue);
    }
    
    public List<ContentWebAddressTransfer> getContentWebAddressTransfers(UserVisit userVisit, Collection<ContentWebAddress> contentWebAddresses) {
        List<ContentWebAddressTransfer> contentWebAddressTransfers = new ArrayList<>(contentWebAddresses.size());

        contentWebAddresses.forEach((contentWebAddress) ->
                contentWebAddressTransfers.add(contentWebAddressTransferCache.getContentWebAddressTransfer(userVisit, contentWebAddress))
        );
        
        return contentWebAddressTransfers;
    }
    
    public List<ContentWebAddressTransfer> getContentWebAddressTransfers(UserVisit userVisit) {
        return getContentWebAddressTransfers(userVisit, getContentWebAddresses());
    }
    
    public ContentWebAddressTransfer getContentWebAddressTransfer(UserVisit userVisit, ContentWebAddress contentWebAddress) {
        return contentWebAddressTransferCache.getContentWebAddressTransfer(userVisit, contentWebAddress);
    }
    
    public void updateContentWebAddressFromValue(ContentWebAddressDetailValue contentWebAddressDetailValue, BasePK updatedBy) {
        if(contentWebAddressDetailValue.hasBeenModified()) {
            var contentWebAddress = contentWebAddressFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    contentWebAddressDetailValue.getContentWebAddressPK());
            var contentWebAddressDetail = contentWebAddress.getActiveDetailForUpdate();
            
            contentWebAddressDetail.setThruTime(session.getStartTime());
            contentWebAddressDetail.store();

            var contentWebAddressPK = contentWebAddressDetail.getContentWebAddressPK();
            var contentWebAddressName = contentWebAddressDetailValue.getContentWebAddressName();
            var contentCollectionPK = contentWebAddressDetailValue.getContentCollectionPK();
            
            contentWebAddressDetail = contentWebAddressDetailFactory.create(contentWebAddressPK, contentWebAddressName, contentCollectionPK, session.getStartTime(),
                    Session.MAX_TIME);
            
            contentWebAddress.setActiveDetail(contentWebAddressDetail);
            contentWebAddress.setLastDetail(contentWebAddressDetail);
            
            sendEvent(contentWebAddressPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteContentWebAddress(ContentWebAddress contentWebAddress, BasePK deletedBy) {
        deleteContentWebAddressDescriptionsByContentWebAddress(contentWebAddress, deletedBy);
        deleteContentWebAddressServersByContentWebAddress(contentWebAddress, deletedBy);
        contentWebAddress.getLastDetailForUpdate().setThruTime(session.getStartTime());
        contentWebAddress.setActiveDetail(null);
        
        sendEvent(contentWebAddress.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteContentWebAddressesByContentCollection(ContentCollection contentCollection, BasePK deletedBy) {
        getContentWebAddressesByContentCollectionForUpdate(contentCollection).forEach((contentWebAddress) -> {
            deleteContentWebAddress(contentWebAddress, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentWebAddressDescriptionFactory contentWebAddressDescriptionFactory;

    @Inject
    ContentWebAddressDescriptionTransferCache contentWebAddressDescriptionTransferCache;

    public ContentWebAddressDescription createContentWebAddressDescription(ContentWebAddress contentWebAddress, Language language, String description, BasePK createdBy) {

        var contentWebAddressDescription = contentWebAddressDescriptionFactory.create(contentWebAddress, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(contentWebAddress.getPrimaryKey(), EventTypes.MODIFY, contentWebAddressDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentWebAddressDescription;
    }
    
    private ContentWebAddressDescription getContentWebAddressDescription(ContentWebAddress contentWebAddress, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentWebAddressDescriptions.fields())
                .from(ContentWebAddressDescriptions)
                .where(ContentWebAddressDescriptions.CONTENT_WEB_ADDRESS.eq(contentWebAddress.getPrimaryKey()),
                        ContentWebAddressDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ContentWebAddressDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentWebAddressDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentWebAddressDescription getContentWebAddressDescription(ContentWebAddress contentWebAddress, Language language) {
        return getContentWebAddressDescription(contentWebAddress, language, EntityPermission.READ_ONLY);
    }
    
    public ContentWebAddressDescription getContentWebAddressDescriptionForUpdate(ContentWebAddress contentWebAddress, Language language) {
        return getContentWebAddressDescription(contentWebAddress, language, EntityPermission.READ_WRITE);
    }
    
    public ContentWebAddressDescriptionValue getContentWebAddressDescriptionValue(ContentWebAddressDescription contentWebAddressDescription) {
        return contentWebAddressDescription == null? null: contentWebAddressDescription.getContentWebAddressDescriptionValue().clone();
    }
    
    public ContentWebAddressDescriptionValue getContentWebAddressDescriptionValueForUpdate(ContentWebAddress contentWebAddress, Language language) {
        return getContentWebAddressDescriptionValue(getContentWebAddressDescriptionForUpdate(contentWebAddress, language));
    }
    
    private List<ContentWebAddressDescription> getContentWebAddressDescriptionsByContentWebAddress(ContentWebAddress contentWebAddress, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentWebAddressDescriptions.fields())
                    .from(ContentWebAddressDescriptions)
                    .join(Languages).onKey(CONTENT_WEB_ADDRESS_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ContentWebAddressDescriptions.CONTENT_WEB_ADDRESS.eq(contentWebAddress.getPrimaryKey()),
                            ContentWebAddressDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ContentWebAddressDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentWebAddressDescriptions.fields())
                    .from(ContentWebAddressDescriptions)
                    .where(ContentWebAddressDescriptions.CONTENT_WEB_ADDRESS.eq(contentWebAddress.getPrimaryKey()),
                            ContentWebAddressDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentWebAddressDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentWebAddressDescription> getContentWebAddressDescriptionsByContentWebAddress(ContentWebAddress contentWebAddress) {
        return getContentWebAddressDescriptionsByContentWebAddress(contentWebAddress, EntityPermission.READ_ONLY);
    }
    
    public List<ContentWebAddressDescription> getContentWebAddressDescriptionsByContentWebAddressForUpdate(ContentWebAddress contentWebAddress) {
        return getContentWebAddressDescriptionsByContentWebAddress(contentWebAddress, EntityPermission.READ_WRITE);
    }
    
    public String getBestContentWebAddressDescription(ContentWebAddress contentWebAddress, Language language) {
        String description;
        var contentWebAddressDescription = getContentWebAddressDescription(contentWebAddress, language);
        
        if(contentWebAddressDescription == null && !language.getIsDefault()) {
            contentWebAddressDescription = getContentWebAddressDescription(contentWebAddress, partyControl.getDefaultLanguage());
        }
        
        if(contentWebAddressDescription == null) {
            description = contentWebAddress.getLastDetail().getContentWebAddressName();
        } else {
            description = contentWebAddressDescription.getDescription();
        }
        
        return description;
    }
    
    public ContentWebAddressDescriptionTransfer getContentWebAddressDescriptionTransfer(UserVisit userVisit, ContentWebAddressDescription contentWebAddressDescription) {
        return contentWebAddressDescriptionTransferCache.getContentWebAddressDescriptionTransfer(userVisit, contentWebAddressDescription);
    }
    
    public List<ContentWebAddressDescriptionTransfer> getContentWebAddressDescriptionTransfers(UserVisit userVisit, ContentWebAddress contentWebAddress) {
        var contentWebAddressDescriptions = getContentWebAddressDescriptionsByContentWebAddress(contentWebAddress);
        List<ContentWebAddressDescriptionTransfer> contentWebAddressDescriptionTransfers = new ArrayList<>(contentWebAddressDescriptions.size());

        contentWebAddressDescriptions.forEach((contentWebAddressDescription) ->
                contentWebAddressDescriptionTransfers.add(contentWebAddressDescriptionTransferCache.getContentWebAddressDescriptionTransfer(userVisit, contentWebAddressDescription))
        );
        
        return contentWebAddressDescriptionTransfers;
    }
    
    public void updateContentWebAddressDescriptionFromValue(ContentWebAddressDescriptionValue contentWebAddressDescriptionValue, BasePK updatedBy) {
        if(contentWebAddressDescriptionValue.hasBeenModified()) {
            var contentWebAddressDescription = contentWebAddressDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, contentWebAddressDescriptionValue.getPrimaryKey());
            
            contentWebAddressDescription.setThruTime(session.getStartTime());
            contentWebAddressDescription.store();

            var contentWebAddress = contentWebAddressDescription.getContentWebAddress();
            var language = contentWebAddressDescription.getLanguage();
            var description = contentWebAddressDescriptionValue.getDescription();
            
            contentWebAddressDescription = contentWebAddressDescriptionFactory.create(contentWebAddress, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(contentWebAddress.getPrimaryKey(), EventTypes.MODIFY, contentWebAddressDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteContentWebAddressDescription(ContentWebAddressDescription contentWebAddressDescription, BasePK deletedBy) {
        contentWebAddressDescription.setThruTime(session.getStartTime());
        
        sendEvent(contentWebAddressDescription.getContentWebAddressPK(), EventTypes.MODIFY, contentWebAddressDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteContentWebAddressDescriptionsByContentWebAddress(ContentWebAddress contentWebAddress, BasePK deletedBy) {
        getContentWebAddressDescriptionsByContentWebAddressForUpdate(contentWebAddress).forEach((contentWebAddressDescription) -> {
            deleteContentWebAddressDescription(contentWebAddressDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Servers
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentWebAddressServerFactory contentWebAddressServerFactory;

    public ContentWebAddressServer createContentWebAddressServer(ContentWebAddress contentWebAddress, Server server, BasePK createdBy) {

        var contentWebAddressServer = contentWebAddressServerFactory.create(contentWebAddress, server,  session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(contentWebAddress.getPrimaryKey(), EventTypes.MODIFY, contentWebAddressServer.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return contentWebAddressServer;
    }
    
    private ContentWebAddressServer getContentWebAddressServer(ContentWebAddress contentWebAddress, Server server, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ContentWebAddressServers.fields())
                .from(ContentWebAddressServers)
                .where(ContentWebAddressServers.CONTENT_WEB_ADDRESS.eq(contentWebAddress.getPrimaryKey()),
                        ContentWebAddressServers.SERVER.eq(server.getPrimaryKey()),
                        ContentWebAddressServers.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return contentWebAddressServerFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ContentWebAddressServer getContentWebAddressServer(ContentWebAddress contentWebAddress, Server server) {
        return getContentWebAddressServer(contentWebAddress, server, EntityPermission.READ_ONLY);
    }
    
    public ContentWebAddressServer getContentWebAddressServerForUpdate(ContentWebAddress contentWebAddress, Server server) {
        return getContentWebAddressServer(contentWebAddress, server, EntityPermission.READ_WRITE);
    }
    
    private List<ContentWebAddressServer> getContentWebAddressServersByContentWebAddress(ContentWebAddress contentWebAddress, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ContentWebAddressServers.fields())
                    .from(ContentWebAddressServers)
                    .join(Servers).onKey(CONTENT_WEB_ADDRESS_SERVERS_SERVER_FK)
                    .join(ServerDetails).onKey(SERVERS_LAST_DETAIL_FK)
                    .where(ContentWebAddressServers.CONTENT_WEB_ADDRESS.eq(contentWebAddress.getPrimaryKey()),
                            ContentWebAddressServers.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ServerDetails.SERVER_NAME),
                    ContentWebAddressServerFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ContentWebAddressServers.fields())
                    .from(ContentWebAddressServers)
                    .where(ContentWebAddressServers.CONTENT_WEB_ADDRESS.eq(contentWebAddress.getPrimaryKey()),
                            ContentWebAddressServers.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return contentWebAddressServerFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ContentWebAddressServer> getContentWebAddressServersByContentWebAddress(ContentWebAddress contentWebAddress) {
        return getContentWebAddressServersByContentWebAddress(contentWebAddress, EntityPermission.READ_ONLY);
    }
    
    public List<ContentWebAddressServer> getContentWebAddressServersByContentWebAddressForUpdate(ContentWebAddress contentWebAddress) {
        return getContentWebAddressServersByContentWebAddress(contentWebAddress, EntityPermission.READ_WRITE);
    }
    
    public void deleteContentWebAddressServer(ContentWebAddressServer contentWebAddressServer, BasePK deletedBy) {
        contentWebAddressServer.setThruTime(session.getStartTime());
        
        sendEvent(contentWebAddressServer.getContentWebAddressPK(), EventTypes.MODIFY, contentWebAddressServer.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteContentWebAddressServersByContentWebAddress(ContentWebAddress contentWebAddress, BasePK deletedBy) {
        getContentWebAddressServersByContentWebAddressForUpdate(contentWebAddress).forEach((contentWebAddressServer) -> {
            deleteContentWebAddressServer(contentWebAddressServer, deletedBy);
        });
    }
    
}
