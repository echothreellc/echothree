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

package com.echothree.model.control.accounting.server.database;

import static com.echothree.model.jooq.server.tables.accounting.TransactionGlEntries.TransactionGlEntries;
import static org.jooq.impl.DSL.sum;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.util.server.persistence.BaseDatabaseQuery;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.math.BigDecimal;
import java.util.List;

public class TransactionBalancedQuery
        extends BaseDatabaseQuery<TransactionBalancedResult> {

    public TransactionBalancedQuery() {
        super(EntityPermission.READ_ONLY);
    }

    public List<TransactionBalancedResult> execute(final Transaction transaction) {
        var query = getDslContext()
                .select(sum(TransactionGlEntries.ORIGINAL_CREDIT).coalesce(BigDecimal.ZERO)
                        .sub(sum(TransactionGlEntries.ORIGINAL_DEBIT).coalesce(BigDecimal.ZERO)).as("OriginalDifference"),
                        sum(TransactionGlEntries.CREDIT).coalesce(BigDecimal.ZERO)
                        .sub(sum(TransactionGlEntries.DEBIT).coalesce(BigDecimal.ZERO)).as("Difference"))
                .from(TransactionGlEntries)
                .where(TransactionGlEntries.TRANSACTION.eq(transaction.getPrimaryKey()),
                        TransactionGlEntries.THRU_TIME.eq(Session.MAX_TIME));

        return super.execute(query);
    }

}
