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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.transfer.EntityTypeResultTransfer;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.factory.EntityTypeFactory;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityTypeControl
        extends BaseCoreControl {

    /** Creates a new instance of EntityTypeControl */
    public EntityTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Entity Type Searches
    // --------------------------------------------------------------------------------

    public List<EntityTypeResultTransfer> getEntityTypeResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var entityTypeResultTransfers = new ArrayList<EntityTypeResultTransfer>();
        var includeEntityType = false;

        var options = session.getOptions();
        if(options != null) {
            includeEntityType = options.contains(SearchOptions.EntityTypeResultIncludeEntityType);
        }

        try {
            var coreControl = Session.getModelController(CoreControl.class);
            var ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                            "FROM searchresults, entityinstances " +
                            "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                            "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                            "_LIMIT_");

            ps.setLong(1, search.getPrimaryKey().getEntityId());

            try (var rs = ps.executeQuery()) {
                while(rs.next()) {
                    var entityType = EntityTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new EntityTypePK(rs.getLong(1)));
                    var entityTypeDetail = entityType.getLastDetail();

                    entityTypeResultTransfers.add(new EntityTypeResultTransfer(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                            entityTypeDetail.getEntityTypeName(), includeEntityType ? coreControl.getEntityTypeTransfer(userVisit, entityType) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTypeResultTransfers;
    }

}
