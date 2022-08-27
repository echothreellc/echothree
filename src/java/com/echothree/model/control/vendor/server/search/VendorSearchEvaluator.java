// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.vendor.server.search;

import com.echothree.model.control.index.common.Indexes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.control.party.server.search.PartySearchEvaluator;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class VendorSearchEvaluator
        extends PartySearchEvaluator {
    
    private String vendorName;
    
    /** Creates a new instance of VendorSearchEvaluator */
    public VendorSearchEvaluator(UserVisit userVisit, SearchType searchType, SearchDefaultOperator searchDefaultOperator, SearchSortOrder searchSortOrder,
            SearchSortDirection searchSortDirection) {
        super(userVisit, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, PartyTypes.VENDOR.name(), Indexes.VENDOR.name());
    }
    
    public String getVendorName() {
        return vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        EntityInstancePKHolder resultSet = null;

        if(vendorName == null) {
            resultSet = super.executeSearch(eea);
        } else {
            Vendor vendor = null;
            
            if(vendorName != null) {
                var vendorControl = Session.getModelController(VendorControl.class);
                
                vendor = vendorControl.getVendorByName(vendorName);
            }
            
            if(vendor != null) {
                resultSet = new EntityInstancePKHolder(1);
                resultSet.add(getCoreControl().getEntityInstanceByBasePK(vendor.getPartyPK()).getPrimaryKey(), null);
            }
        }
        
        return resultSet;
    }
    
}
