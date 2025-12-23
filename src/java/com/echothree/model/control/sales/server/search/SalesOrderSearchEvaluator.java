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

import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.order.server.search.OrderSearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class SalesOrderSearchEvaluator
        extends OrderSearchEvaluator {
    
    private OfferUse offerUse;
    private AssociateReferral associateReferral;
    
    /** Creates a new instance of SalesOrderSearchEvaluator */
    public SalesOrderSearchEvaluator(UserVisit userVisit, SearchType searchType, SearchDefaultOperator searchDefaultOperator, SearchSortOrder searchSortOrder,
            SearchSortDirection searchSortDirection) {
        super(userVisit, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, OrderLogic.getInstance().getOrderTypeByName(null,
                OrderTypes.SALES_ORDER.name()));
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByOfferUse(OfferUse offerUse) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, orders, orderdetails, salesorders "
                + "WHERE ord_activedetailid = orddt_orderdetailid "
                + "AND orddt_ordtyp_ordertypeid = ? "
                + "AND ord_orderid = salord_ord_orderid "
                + "AND salord_ofruse_offeruseid = ? AND salord_thrutime = ? "
                + "AND eni_ent_entitytypeid = ? AND ord_orderid = eni_entityuniqueid"),
                orderType, offerUse, Session.MAX_TIME, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByAssociateReferral(AssociateReferral associateReferral) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, orders, orderdetails, salesorders "
                + "WHERE ord_activedetailid = orddt_orderdetailid "
                + "AND orddt_ordtyp_ordertypeid = ? "
                + "AND ord_orderid = salord_ord_orderid "
                + "AND salord_ascrfr_associatereferralid = ? AND salord_thrutime = ? "
                + "AND eni_ent_entitytypeid = ? AND ord_orderid = eni_entityuniqueid"),
                orderType, associateReferral, Session.MAX_TIME, entityType);
    }
    
    public OfferUse getOfferUse() {
        return offerUse;
    }
    
    public void setOfferUse(OfferUse offerUse) {
        this.offerUse = offerUse;
    }
    
    /** Counts the number of search parameters. */
    @Override
    protected int countParameters() {
        return super.countParameters() + (offerUse == null ? 0 : 1);
    }

    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        var resultSet = super.executeSearch(eea);
        
        if(offerUse != null && (resultSet == null || resultSet.size() > 0)) {
            var entityInstancePKHolder = getEntityInstancePKHolderByOfferUse(offerUse);

            if(resultSet == null) {
                resultSet = entityInstancePKHolder;
            } else {
                resultSet.retainAll(entityInstancePKHolder);
            }
        }
        
        if(associateReferral != null && (resultSet == null || resultSet.size() > 0)) {
            var entityInstancePKHolder = getEntityInstancePKHolderByAssociateReferral(associateReferral);

            if(resultSet == null) {
                resultSet = entityInstancePKHolder;
            } else {
                resultSet.retainAll(entityInstancePKHolder);
            }
        }
        
        return resultSet;
    }
    
}
