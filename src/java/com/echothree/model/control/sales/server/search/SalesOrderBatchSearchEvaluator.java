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

package com.echothree.model.control.sales.server.search;

import static com.echothree.model.jooq.server.keys.batch.BatchForeignKeys.BATCHES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.sales.SalesForeignKeys.SALES_ORDER_BATCHES_BATCH_FK;
import static com.echothree.model.jooq.server.tables.batch.BatchDetails.BatchDetails;
import static com.echothree.model.jooq.server.tables.batch.Batches.Batches;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.sales.SalesOrderBatches.SalesOrderBatches;
import com.echothree.model.control.batch.common.BatchTypes;
import com.echothree.model.control.batch.server.logic.BatchLogic;
import com.echothree.model.control.order.server.search.OrderBatchSearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class SalesOrderBatchSearchEvaluator
        extends OrderBatchSearchEvaluator {
    
    private PaymentMethod paymentMethod;
    
    /** Creates a new instance of SalesOrderBatchSearchEvaluator */
    public SalesOrderBatchSearchEvaluator(UserVisit userVisit, SearchType searchType, SearchDefaultOperator searchDefaultOperator,
            SearchSortOrder searchSortOrder, SearchSortDirection searchSortDirection) {
        super(userVisit, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection,BatchLogic.getInstance().getBatchTypeByName(null,
                BatchTypes.SALES_ORDER.name()));
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByPaymentMethod(PaymentMethod paymentMethod) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(Batches).on(Batches.BATCH.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(BatchDetails).onKey(BATCHES_ACTIVE_DETAIL_FK)
                .join(SalesOrderBatches).onKey(SALES_ORDER_BATCHES_BATCH_FK)
                .where(BatchDetails.BATCH_TYPE.eq(batchType.getPrimaryKey()),
                        SalesOrderBatches.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()),
                        SalesOrderBatches.THRU_TIME.eq(Session.MAX_TIME),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        return getEntityInstancePKHolderFromQuery(query);
    }
    
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    /** Counts the number of search parameters. */
    @Override
    protected int countParameters() {
        return super.countParameters() + (paymentMethod == null ? 0 : 1);
    }

    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        var resultSet = super.executeSearch(eea);
            
        if(paymentMethod != null && (resultSet == null || resultSet.size() > 0)) {
            var entityInstancePKHolder = getEntityInstancePKHolderByPaymentMethod(paymentMethod);

            if(resultSet == null) {
                resultSet = entityInstancePKHolder;
            } else {
                resultSet.retainAll(entityInstancePKHolder);
            }
        }
        
        return resultSet;
    }
    
}
