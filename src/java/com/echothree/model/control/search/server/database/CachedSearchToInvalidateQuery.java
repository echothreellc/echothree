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

import com.echothree.model.data.index.server.entity.Index;
import com.echothree.util.server.persistence.BaseDatabaseQuery;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class CachedSearchToInvalidateQuery
        extends BaseDatabaseQuery<CachedSearchToInvalidateResult> {
    
    public CachedSearchToInvalidateQuery() {
        super("SELECT csrchst_cachedsearchstatusid AS CachedSearchStatus "
                + "FROM cachedsearches, cachedsearchstatuses "
                + "WHERE csrch_idx_indexid = ? AND csrch_thrutime = ? "
                + "AND csrch_cachedsearchid = csrchst_csrch_cachedsearchid AND csrchst_isconsistent = 1",
                EntityPermission.READ_WRITE);
    }
    
    public List<CachedSearchToInvalidateResult> execute(Index index) {
        return super.execute(index, Session.MAX_TIME);
    }
    
}
