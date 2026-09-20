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

package com.echothree.model.control.search.server.database;

import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_SEARCH_STATUSES_CACHED_SEARCH_FK;
import static com.echothree.model.jooq.server.tables.search.CachedSearchStatuses.CachedSearchStatuses;
import static com.echothree.model.jooq.server.tables.search.CachedSearches.CachedSearches;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.util.server.persistence.BaseDatabaseQuery;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class CachedSearchToInvalidateQuery
        extends BaseDatabaseQuery<CachedSearchToInvalidateResult> {
    
    public CachedSearchToInvalidateQuery() {
        super(EntityPermission.READ_WRITE);
    }
    
    public List<CachedSearchToInvalidateResult> execute(Index index) {
        var query = getDslContext()
                .select(CachedSearchStatuses.CACHED_SEARCH_STATUS.as("CachedSearchStatus"))
                .from(CachedSearches)
                .join(CachedSearchStatuses).onKey(CACHED_SEARCH_STATUSES_CACHED_SEARCH_FK)
                .where(CachedSearches.INDEX.eq(index.getPrimaryKey()),
                        CachedSearches.THRU_TIME.eq(Session.MAX_TIME),
                        CachedSearchStatuses.IS_CONSISTENT.eq(true));

        return super.execute(query);
    }
    
}
