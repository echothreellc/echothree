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

package com.echothree.model.control.security.server.control;

import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.transfer.SecurityRoleResultTransfer;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.security.common.pk.SecurityRolePK;
import com.echothree.model.data.security.server.factory.SecurityRoleFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.jooq.Select;

@CommandScope
public class SecurityRoleControl
        extends BaseModelControl {

    @Inject
    private SearchControl searchControl;

    @Inject
    protected SecurityControl securityControl;

    /** Creates a new instance of SecurityRoleControl */
    protected SecurityRoleControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Security Role Searches
    // --------------------------------------------------------------------------------

    @Inject
    protected SearchResultFactory searchResultFactory;

    @Inject
    protected SecurityRoleFactory securityRoleFactory;

    private List<Long> getSecurityRoleIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var cachedSearch = search.getCachedSearch();
        Select<?> query;

        if(cachedSearch == null) {
            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(SearchResults)
                    .join(EntityInstances).onKey(SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(SearchResults.SEARCH.eq(search.getPrimaryKey()))
                    .orderBy(SearchResults.SORT_ORDER, SearchResults.ENTITY_INSTANCE), SearchResultFactory.class);
        } else {
            var cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);

            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);

            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(CachedExecutedSearchResults)
                    .join(EntityInstances).onKey(CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(CachedExecutedSearchResults.CACHED_EXECUTED_SEARCH.eq(cachedExecutedSearch.getPrimaryKey()))
                    .orderBy(CachedExecutedSearchResults.SORT_ORDER, CachedExecutedSearchResults.ENTITY_INSTANCE),
                    CachedExecutedSearchResultFactory.class);
        }

        return query.fetch(EntityInstances.ENTITY_UNIQUE);
    }

    public List<SecurityRoleResultTransfer> getSecurityRoleResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityIds = getSecurityRoleIdsFromUserVisitSearch(userVisitSearch);
        var securityRoleResultTransfers = new ArrayList<SecurityRoleResultTransfer>(entityIds.size());
        var options = session.getOptions();
        var includeSecurityRole = options != null && options.contains(SearchOptions.SecurityRoleResultIncludeSecurityRole);

        for(var entityUniqueId : entityIds) {
            var securityRole = securityRoleFactory.getEntityFromPK(EntityPermission.READ_ONLY, new SecurityRolePK(entityUniqueId));
            var securityRoleDetail = securityRole.getLastDetail();

            securityRoleResultTransfers.add(new SecurityRoleResultTransfer(securityRoleDetail.getSecurityRoleName(),
                    securityRoleDetail.getSecurityRoleGroup().getLastDetail().getSecurityRoleGroupName(),
                    includeSecurityRole ? securityControl.getSecurityRoleTransfer(userVisit, securityRole) : null));
        }

        return securityRoleResultTransfers;
    }

}
