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

package com.echothree.model.control.order.server.search;

import static com.echothree.model.jooq.server.keys.batch.BatchForeignKeys.BATCHES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_BATCHES_BATCH_FK;
import static com.echothree.model.jooq.server.tables.batch.BatchDetails.BatchDetails;
import static com.echothree.model.jooq.server.tables.batch.Batches.Batches;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.order.OrderBatches.OrderBatches;
import com.echothree.model.control.batch.server.search.BatchSearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class OrderBatchSearchEvaluator
        extends BatchSearchEvaluator {
    
    private Currency currency;
    
    /** Creates a new instance of OrderBatchSearchEvaluator */
    public OrderBatchSearchEvaluator(UserVisit userVisit, SearchType searchType, SearchDefaultOperator searchDefaultOperator, SearchSortOrder searchSortOrder,
            SearchSortDirection searchSortDirection, BatchType batchType) {
        super(userVisit, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, batchType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByCurrency(Currency currency) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(Batches).on(Batches.BATCH.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(BatchDetails).onKey(BATCHES_ACTIVE_DETAIL_FK)
                .join(OrderBatches).onKey(ORDER_BATCHES_BATCH_FK)
                .where(BatchDetails.BATCH_TYPE.eq(batchType.getPrimaryKey()),
                        OrderBatches.CURRENCY.eq(currency.getPrimaryKey()),
                        OrderBatches.THRU_TIME.eq(Session.MAX_TIME),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        return getEntityInstancePKHolderFromQuery(query);
    }
    
    public Currency getCurrency() {
        return currency;
    }
    
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
    /** Counts the number of search parameters. */
    @Override
    protected int countParameters() {
        return super.countParameters() + (currency == null ? 0 : 1);
    }

    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        var resultSet = super.executeSearch(eea);
            
        if(currency != null && (resultSet == null || resultSet.size() > 0)) {
            var entityInstancePKHolder = getEntityInstancePKHolderByCurrency(currency);

            if(resultSet == null) {
                resultSet = entityInstancePKHolder;
            } else {
                resultSet.retainAll(entityInstancePKHolder);
            }
        }
        
        return resultSet;
    }
    
}
