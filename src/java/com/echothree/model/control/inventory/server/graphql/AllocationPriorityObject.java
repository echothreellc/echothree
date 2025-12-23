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

package com.echothree.model.control.inventory.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.inventory.server.entity.AllocationPriorityDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("allocation priority object")
@GraphQLName("AllocationPriority")
public class AllocationPriorityObject
        extends BaseEntityInstanceObject {
    
    private final AllocationPriority allocationPriority; // Always Present
    
    public AllocationPriorityObject(AllocationPriority allocationPriority) {
        super(allocationPriority.getPrimaryKey());
        
        this.allocationPriority = allocationPriority;
    }

    private AllocationPriorityDetail allocationPriorityDetail; // Optional, use getAllocationPriorityDetail()
    
    private AllocationPriorityDetail getAllocationPriorityDetail() {
        if(allocationPriorityDetail == null) {
            allocationPriorityDetail = allocationPriority.getLastDetail();
        }
        
        return allocationPriorityDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("allocation priority name")
    @GraphQLNonNull
    public String getAllocationPriorityName() {
        return getAllocationPriorityDetail().getAllocationPriorityName();
    }

    @GraphQLField
    @GraphQLDescription("priority")
    @GraphQLNonNull
    public int getPriority() {
        return getAllocationPriorityDetail().getPriority();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getAllocationPriorityDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getAllocationPriorityDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return inventoryControl.getBestAllocationPriorityDescription(allocationPriority, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
