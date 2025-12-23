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

package com.echothree.model.control.vendor.server.graphql;

import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicyObject;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicySecurityUtils;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicyObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicySecurityUtils;
import com.echothree.model.control.vendor.common.workflow.VendorItemStatusConstants;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.vendor.common.VendorItemCostConstants;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.model.data.vendor.server.entity.VendorItemDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("vendor item object")
@GraphQLName("VendorItem")
public class VendorItemObject
        extends BaseEntityInstanceObject {
    
    private final VendorItem vendorItem; // Always Present
    
    public VendorItemObject(VendorItem vendorItem) {
        super(vendorItem.getPrimaryKey());
        
        this.vendorItem = vendorItem;
    }

    private VendorItemDetail vendorItemDetail; // Optional, use getVendorItemDetail()
    
    private VendorItemDetail getVendorItemDetail() {
        if(vendorItemDetail == null) {
            vendorItemDetail = vendorItem.getLastDetail();
        }
        
        return vendorItemDetail;
    }

    @GraphQLField
    @GraphQLDescription("item")
    public ItemObject getItem(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemAccess(env) ? new ItemObject(getVendorItemDetail().getItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("vendor")
    public VendorObject getVendor(final DataFetchingEnvironment env) {
        var party = getVendorItemDetail().getVendorParty();

        return VendorSecurityUtils.getHasVendorAccess(env, party) ? new VendorObject(party) : null;
    }

    @GraphQLField
    @GraphQLDescription("vendor item name")
    @GraphQLNonNull
    public String getVendorItemName() {
        return getVendorItemDetail().getVendorItemName();
    }

    @GraphQLField
    @GraphQLDescription("description")
    public String getDescription() {
        return getVendorItemDetail().getDescription();
    }

    @GraphQLField
    @GraphQLDescription("priority")
    @GraphQLNonNull
    public int getPriority() {
        return getVendorItemDetail().getPriority();
    }

    @GraphQLField
    @GraphQLDescription("cancellation policy")
    public CancellationPolicyObject getCancellationPolicy(final DataFetchingEnvironment env) {
        var defaultCancellationPolicy = getVendorItemDetail().getCancellationPolicy();

        return defaultCancellationPolicy == null ? null : CancellationPolicySecurityUtils.getHasCancellationPolicyAccess(env) ?
                new CancellationPolicyObject(defaultCancellationPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("return policy")
    public ReturnPolicyObject getReturnPolicy(final DataFetchingEnvironment env) {
        var defaultReturnPolicy = getVendorItemDetail().getReturnPolicy();

        return defaultReturnPolicy == null ? null : ReturnPolicySecurityUtils.getHasReturnPolicyAccess(env) ?
                new ReturnPolicyObject(defaultReturnPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("vendor item status")
    public WorkflowEntityStatusObject getVendorItemStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, VendorItemStatusConstants.Workflow_VENDOR_ITEM_STATUS);
    }

    @GraphQLField
    @GraphQLDescription("vendor item costs")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<VendorItemCostObject> getVendorItemCosts(final DataFetchingEnvironment env) {
        if(VendorSecurityUtils.getHasVendorItemCostsAccess(env)) {
            var vendorControl = Session.getModelController(VendorControl.class);
            var totalCount = vendorControl.countVendorItemCostsByVendorItem(vendorItem);

            try(var objectLimiter = new ObjectLimiter(env, VendorItemCostConstants.COMPONENT_VENDOR_NAME, VendorItemCostConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = vendorControl.getVendorItemCostsByVendorItem(vendorItem);
                var items = entities.stream().map(VendorItemCostObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, items);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}
