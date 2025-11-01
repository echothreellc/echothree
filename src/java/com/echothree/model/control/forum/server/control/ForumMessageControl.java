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

package com.echothree.model.control.forum.server.control;

import com.echothree.model.control.forum.common.transfer.ForumMessageResultTransfer;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.data.forum.common.pk.ForumMessagePK;
import com.echothree.model.data.forum.server.factory.ForumMessageFactory;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ForumMessageControl
        extends BaseModelControl {

    /** Creates a new instance of ForumControl */
    protected ForumMessageControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Searches
    // --------------------------------------------------------------------------------

    public List<ForumMessageResultTransfer> getForumMessageResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var forumMessageResultTransfers = new ArrayList<ForumMessageResultTransfer>();
        var includeForumMessage = false;

        var options = session.getOptions();
        if(options != null) {
            includeForumMessage = options.contains(SearchOptions.ForumMessageResultIncludeForumMessage);
        }

        try {
            var forumControl = Session.getModelController(ForumControl.class);
            var ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                            "FROM searchresults, entityinstances " +
                            "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                            "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                            "_LIMIT_");

            ps.setLong(1, search.getPrimaryKey().getEntityId());

            try (var rs = ps.executeQuery()) {
                while(rs.next()) {
                    var forumMessage = ForumMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new ForumMessagePK(rs.getLong(1)));

                    forumMessageResultTransfers.add(new ForumMessageResultTransfer(forumMessage.getLastDetail().getForumMessageName(),
                            includeForumMessage ? forumControl.getForumMessageTransfer(userVisit, forumMessage) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return forumMessageResultTransfers;
    }

}
