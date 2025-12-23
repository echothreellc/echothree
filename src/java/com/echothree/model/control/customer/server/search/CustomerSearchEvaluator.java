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

package com.echothree.model.control.customer.server.search;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.customer.server.analyzer.CustomerAnalyzer;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.common.Indexes;
import com.echothree.model.control.index.server.analyzer.BasicAnalyzer;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.search.PartySearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class CustomerSearchEvaluator
        extends PartySearchEvaluator {
    
    private CustomerType customerType;
    private String customerName;
    
    /** Creates a new instance of CustomerSearchEvaluator */
    public CustomerSearchEvaluator(UserVisit userVisit, SearchType searchType, SearchDefaultOperator searchDefaultOperator,
            SearchSortOrder searchSortOrder, SearchSortDirection searchSortDirection) {
        super(userVisit, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, PartyTypes.CUSTOMER.name(),
                IndexFields.customerName.name(), Indexes.CUSTOMER.name());
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByCustomerType(CustomerType customerType) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ " +
                "FROM entityinstances, partytypes, parties, partydetails, customers " +
                "WHERE par_activedetailid = pardt_partydetailid " +
                "AND ptyp_partytypename = ? AND ptyp_partytypeid = pardt_ptyp_partytypeid " +
                "AND par_partyid = cu_par_partyid AND cu_cuty_customertypeid = ? AND cu_thrutime = ? " +
                "AND eni_ent_entitytypeid = ? AND par_partyid = eni_entityuniqueid"),
                PartyTypes.CUSTOMER.name(), customerType, Session.MAX_TIME, entityType);
    }
    
    public CustomerType getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public BasicAnalyzer getAnalyzer(final ExecutionErrorAccumulator eea, final Language language) {
        return new CustomerAnalyzer(eea, language, entityType, partyType, entityNameIndexField);
    }

    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        EntityInstancePKHolder resultSet = null;
        
        if(customerName == null) {
            resultSet = super.executeSearch(eea);
            
            if((resultSet == null || resultSet.size() > 0) && getCustomerType() != null) {
                var entityInstancePKHolder = getEntityInstancePKHolderByCustomerType(customerType);
                
                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
        } else {
            Customer customer = null;
            
            if(customerName != null) {
                var customerControl = Session.getModelController(CustomerControl.class);
                
                customer = customerControl.getCustomerByName(customerName);
            }
            
            if(customer != null) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

                resultSet = new EntityInstancePKHolder(1);
                resultSet.add(entityInstanceControl.getEntityInstanceByBasePK(customer.getPartyPK()).getPrimaryKey(), null);
            }
        }
        
        return resultSet;
    }
    
}
