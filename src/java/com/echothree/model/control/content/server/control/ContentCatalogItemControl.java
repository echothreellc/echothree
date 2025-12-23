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

import com.echothree.model.control.content.common.transfer.ContentCatalogItemResultTransfer;
import com.echothree.model.control.content.server.graphql.ContentCatalogItemObject;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
import com.echothree.model.data.content.common.pk.ContentCatalogItemPK;
import com.echothree.model.data.content.server.factory.ContentCatalogItemFactory;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.server.control.BaseModelControl;
import javax.enterprise.inject.spi.CDI;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class ContentCatalogItemControl
        extends BaseModelControl {

    /** Creates a new instance of ContentCatalogItemControl */
    protected ContentCatalogItemControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Content CatalogItem Searches
    // --------------------------------------------------------------------------------

    public List<ContentCatalogItemResultTransfer> getContentCatalogItemResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var contentCatalogItemResultTransfers = new ArrayList<ContentCatalogItemResultTransfer>();
        var includeContentCatalogItem = false;

        var options = session.getOptions();
        if(options != null) {
            includeContentCatalogItem = options.contains(SearchOptions.ContentCatalogItemResultIncludeContentCatalogItem);
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
                    var contentCatalogItem = ContentCatalogItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new ContentCatalogItemPK(rs.getLong(1)));
                    var contentCatalogDetail = contentCatalogItem.getContentCatalog().getLastDetail();
                    var itemDetail = contentCatalogItem.getItem().getLastDetail();

                    contentCatalogItemResultTransfers.add(new ContentCatalogItemResultTransfer(contentCatalogDetail.getContentCollection().getLastDetail().getContentCollectionName(),
                            contentCatalogDetail.getContentCatalogName(), itemDetail.getItemName(),
                            includeContentCatalogItem ? contentControl.getContentCatalogItemTransfer(userVisit, contentCatalogItem) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return contentCatalogItemResultTransfers;
    }


    public List<ContentCatalogItemObject> getContentCatalogItemObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var contentControl = Session.getModelController(ContentControl.class);
        var searchControl = Session.getModelController(SearchControl.class);
        var contentCatalogItemObjects = new ArrayList<ContentCatalogItemObject>();

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            while(rs.next()) {
                var contentCatalogItem = contentControl.getContentCatalogItemByPK(new ContentCatalogItemPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                contentCatalogItemObjects.add(new ContentCatalogItemObject(contentCatalogItem));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return contentCatalogItemObjects;
    }
    
}
