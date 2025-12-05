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

package com.echothree.model.control.search.server.database;

import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EntityLongAttributeFacetQuery
        extends BaseFacetQuery<EntityLongAttributeFacetResult> {
    
    public EntityLongAttributeFacetQuery(UserVisitSearch userVisitSearch) {
        super(userVisitSearch.getSearch().getCachedSearch() == null
                ? "SELECT enla_longattribute AS LongAttribute, COUNT(*) AS Count "
                + "FROM searchresults, entitylongattributes "
                + "WHERE srchr_srch_searchid = ? "
                + "AND srchr_eni_entityinstanceid = enla_eni_entityinstanceid AND enla_ena_entityattributeid = ? AND enla_thrutime = ? "
                + "GROUP BY enla_longattribute"
                : "SELECT enla_longattribute AS LongAttribute, COUNT(*) AS Count "
                + "FROM cachedexecutedsearchresults, entitylongattributes "
                + "WHERE cxsrchr_cxsrch_cachedexecutedsearchid = ? "
                + "AND cxsrchr_eni_entityinstanceid = enla_eni_entityinstanceid AND enla_ena_entityattributeid = ? AND enla_thrutime = ? "
                + "GROUP BY enla_longattribute",
                userVisitSearch);
    }
    
    public List<EntityLongAttributeFacetResult> execute(EntityAttribute entityAttribute) {
        return super.execute(cachedExecutedSearch == null ? search : cachedExecutedSearch, entityAttribute, Session.MAX_TIME);
    }
    
}
