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

import static com.echothree.model.jooq.server.keys.customer.CustomerForeignKeys.CUSTOMERS_PARTY_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTY_DETAILS_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.customer.Customers.Customers;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.party.PartyTypes.PartyTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.customer.server.analyzer.CustomerAnalyzer;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.common.Indexes;
import com.echothree.model.control.index.server.analyzer.BasicAnalyzer;
import com.echothree.model.control.party.server.search.PartySearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
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
        super(userVisit, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, com.echothree.model.control.party.common.PartyTypes.CUSTOMER.name(),
                IndexFields.customerName.name(), Indexes.CUSTOMER.name());
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByCustomerType(CustomerType customerType) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(Parties).on(Parties.PARTY.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(PartyDetails).onKey(PARTIES_ACTIVE_DETAIL_FK)
                .join(PartyTypes).onKey(PARTY_DETAILS_PARTY_TYPE_FK)
                .join(Customers).onKey(CUSTOMERS_PARTY_FK)
                .where(PartyTypes.PARTY_TYPE_NAME.eq(com.echothree.model.control.party.common.PartyTypes.CUSTOMER.name()),
                        Customers.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        Customers.THRU_TIME.eq(Session.MAX_TIME),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        return getEntityInstancePKHolderFromQuery(query);
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
