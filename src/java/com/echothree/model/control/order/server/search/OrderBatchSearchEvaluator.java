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

import com.echothree.model.control.batch.server.search.BatchSearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
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
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, batches, batchdetails, orderbatches "
                + "WHERE btch_activedetailid = btchdt_batchdetailid "
                + "AND btchdt_btchtyp_batchtypeid = ? "
                + "AND btch_batchid = ordbtch_btch_batchid "
                + "AND ordbtch_cur_currencyid = ? AND ordbtch_thrutime = ? "
                + "AND eni_ent_entitytypeid = ? AND btch_batchid = eni_entityuniqueid"),
                batchType, currency, Session.MAX_TIME, entityType);
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
