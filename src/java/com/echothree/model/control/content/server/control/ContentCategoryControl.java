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

package com.echothree.model.control.content.server.control;

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
import com.echothree.model.control.content.common.transfer.ContentCategoryResultTransfer;
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
import com.echothree.model.control.content.server.transfer.ContentTransferCaches;
import com.echothree.model.control.content.server.transfer.ContentWebAddressDescriptionTransferCache;
import com.echothree.model.control.content.server.transfer.ContentWebAddressTransferCache;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.search.common.SearchOptions;
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
import com.echothree.model.data.content.common.pk.ContentPageLayoutAreaPK;
import com.echothree.model.data.content.common.pk.ContentPageLayoutPK;
import com.echothree.model.data.content.common.pk.ContentPagePK;
import com.echothree.model.data.content.common.pk.ContentSectionPK;
import com.echothree.model.data.content.common.pk.ContentWebAddressPK;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogDescription;
import com.echothree.model.data.content.server.entity.ContentCatalogDetail;
import com.echothree.model.data.content.server.entity.ContentCatalogItem;
import com.echothree.model.data.content.server.entity.ContentCatalogItemFixedPrice;
import com.echothree.model.data.content.server.entity.ContentCatalogItemVariablePrice;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryDescription;
import com.echothree.model.data.content.server.entity.ContentCategoryDetail;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentCollectionDescription;
import com.echothree.model.data.content.server.entity.ContentCollectionDetail;
import com.echothree.model.data.content.server.entity.ContentForum;
import com.echothree.model.data.content.server.entity.ContentForumDetail;
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
import com.echothree.model.data.content.server.entity.ContentPageDetail;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.content.server.entity.ContentPageLayoutArea;
import com.echothree.model.data.content.server.entity.ContentPageLayoutAreaDescription;
import com.echothree.model.data.content.server.entity.ContentPageLayoutDescription;
import com.echothree.model.data.content.server.entity.ContentPageLayoutDetail;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.content.server.entity.ContentSectionDescription;
import com.echothree.model.data.content.server.entity.ContentSectionDetail;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.content.server.entity.ContentWebAddressDescription;
import com.echothree.model.data.content.server.entity.ContentWebAddressDetail;
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
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.forum.common.pk.ForumPK;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.common.pk.OfferUsePK;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContentCategoryControl
        extends BaseModelControl {

    /** Creates a new instance of ContentCategoryControl */
    public ContentCategoryControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Content Category Searches
    // --------------------------------------------------------------------------------

    public List<ContentCategoryResultTransfer> getContentCategoryResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var contentCategoryResultTransfers = new ArrayList<ContentCategoryResultTransfer>();
        var includeContentCategory = false;

        var options = session.getOptions();
        if(options != null) {
            includeContentCategory = options.contains(SearchOptions.ContentCategoryResultIncludeContentCategory);
        }

        try {
            var contentControl = Session.getModelController(ContentControl.class);
            var ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                            "FROM searchresults, entityinstances " +
                            "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                            "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                            "_LIMIT_");

            ps.setLong(1, search.getPrimaryKey().getEntityId());

            try (var rs = ps.executeQuery()) {
                while(rs.next()) {
                    var contentCategory = ContentCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new ContentCategoryPK(rs.getLong(1)));
                    var contentCategoryDetail = contentCategory.getLastDetail();
                    var contentCatalogDetail = contentCategoryDetail.getContentCatalog().getLastDetail();

                    contentCategoryResultTransfers.add(new ContentCategoryResultTransfer(contentCatalogDetail.getContentCollection().getLastDetail().getContentCollectionName(),
                            contentCatalogDetail.getContentCatalogName(), contentCategoryDetail.getContentCategoryName(),
                            includeContentCategory ? contentControl.getContentCategoryTransfer(userVisit, contentCategory) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return contentCategoryResultTransfers;
    }

}
