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

package com.echothree.model.control.accounting.server.database;

import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.util.server.persistence.BaseDatabaseQuery;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class TransactionBalancedQuery
        extends BaseDatabaseQuery<TransactionBalancedResult> {

    public TransactionBalancedQuery() {
        super("""
                SELECT SUM(trxglent_originalcredit) - SUM(trxglent_originaldebit) AS OriginalDifference,
                SUM(trxglent_credit) - SUM(trxglent_debit) AS Difference
                FROM transactionglentries
                WHERE trxglent_trx_transactionid = ?
                AND trxglent_thrutime = ?
                """, EntityPermission.READ_ONLY);
    }

    public List<TransactionBalancedResult> execute(final Transaction transaction) {
        return super.execute(transaction, Session.MAX_TIME);
    }

}
