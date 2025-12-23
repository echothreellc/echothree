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

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.GlAccountObject;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicyObject;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicySecurityUtils;
import com.echothree.model.control.filter.server.graphql.FilterObject;
import com.echothree.model.control.filter.server.graphql.FilterSecurityUtils;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.item.server.graphql.ItemAliasTypeObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.party.server.graphql.BasePartyObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicyObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicySecurityUtils;
import com.echothree.model.control.selector.server.graphql.SelectorObject;
import com.echothree.model.control.selector.server.graphql.SelectorSecurityUtils;
import com.echothree.model.control.vendor.common.workflow.VendorStatusConstants;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.vendor.common.VendorItemConstants;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("vendor object")
@GraphQLName("Vendor")
public class VendorObject
        extends BasePartyObject {

    public VendorObject(Party party) {
        super(party);
    }

    public VendorObject(Vendor vendor) {
        super(vendor.getParty());

        this.vendor = vendor;
    }

    private Vendor vendor;  // Optional, use getVendor()

    protected Vendor getVendor() {
        if(vendor == null) {
            var vendorControl = Session.getModelController(VendorControl.class);

            vendor = vendorControl.getVendor(party);
        }

        return vendor;
    }

    @GraphQLField
    @GraphQLDescription("vendor name")
    @GraphQLNonNull
    public String getVendorName() {
        return getVendor().getVendorName();
    }

    @GraphQLField
    @GraphQLDescription("vendor type")
    @GraphQLNonNull
    public VendorTypeObject getVendorType(final DataFetchingEnvironment env) {
        return VendorSecurityUtils.getHasVendorTypeAccess(env) ?
                new VendorTypeObject(getVendor().getVendorType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("minimum purchase order lines")
    public Integer getMinimumPurchaseOrderLines() {
        return getVendor().getMinimumPurchaseOrderLines();
    }

    @GraphQLField
    @GraphQLDescription("minimum purchase order lines")
    public Integer getMaximumPurchaseOrderLines() {
        return getVendor().getMaximumPurchaseOrderLines();
    }

    @GraphQLField
    @GraphQLDescription("minimum purchase order amount")
    public Long getMinimumPurchaseOrderAmount() {
        return getVendor().getMinimumPurchaseOrderAmount();
    }

    @GraphQLField
    @GraphQLDescription("maximum purchase order amount")
    public Long getMaximumPurchaseOrderAmount() {
        return getVendor().getMaximumPurchaseOrderAmount();
    }

    @GraphQLField
    @GraphQLDescription("use item purchasing categories")
    @GraphQLNonNull
    public boolean getUseItemPurchasingCategories() {
        return getVendor().getUseItemPurchasingCategories();
    }

    @GraphQLField
    @GraphQLDescription("default item alias type")
    public ItemAliasTypeObject getDefaultItemAliasType(final DataFetchingEnvironment env) {
        var defaultItemAliasType = getVendor().getDefaultItemAliasType();

        return defaultItemAliasType == null ? null : ItemSecurityUtils.getHasItemAliasAccess(env) ?
                new ItemAliasTypeObject(defaultItemAliasType) : null;
    }

    @GraphQLField
    @GraphQLDescription("cancellation policy")
    public CancellationPolicyObject getCancellationPolicy(final DataFetchingEnvironment env) {
        var cancellationPolicy = getVendor().getCancellationPolicy();

        return cancellationPolicy == null ? null : CancellationPolicySecurityUtils.getHasCancellationPolicyAccess(env) ?
                new CancellationPolicyObject(cancellationPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("return policy")
    public ReturnPolicyObject getReturnPolicy(final DataFetchingEnvironment env) {
        var returnPolicy = getVendor().getReturnPolicy();

        return returnPolicy == null ? null : ReturnPolicySecurityUtils.getHasReturnPolicyAccess(env) ?
                new ReturnPolicyObject(returnPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("AP GL account")
    public GlAccountObject getApGlAccount(final DataFetchingEnvironment env) {
        var apGlAccount = getVendor().getApGlAccount();

        return apGlAccount == null ? null : AccountingSecurityUtils.getHasGlAccountAccess(env) ?
                new GlAccountObject(apGlAccount) : null;
    }

    @GraphQLField
    @GraphQLDescription("hold until complete")
    @GraphQLNonNull
    public boolean getHoldUntilComplete() {
        return getVendor().getHoldUntilComplete();
    }

    @GraphQLField
    @GraphQLDescription("allow backorders")
    @GraphQLNonNull
    public boolean getAllowBackorders() {
        return getVendor().getAllowBackorders();
    }

    @GraphQLField
    @GraphQLDescription("allow substitutions")
    @GraphQLNonNull
    public boolean getAllowSubstitutions() {
        return getVendor().getAllowSubstitutions();
    }

    @GraphQLField
    @GraphQLDescription("allow combining shipments")
    @GraphQLNonNull
    public boolean getAllowCombiningShipments() {
        return getVendor().getAllowCombiningShipments();
    }

    @GraphQLField
    @GraphQLDescription("require reference")
    @GraphQLNonNull
    public boolean getRequireReference() {
        return getVendor().getRequireReference();
    }

    @GraphQLField
    @GraphQLDescription("allow reference duplicates")
    @GraphQLNonNull
    public boolean getAllowReferenceDuplicates() {
        return getVendor().getAllowReferenceDuplicates();
    }

    @GraphQLField
    @GraphQLDescription("reference validation pattern")
    public String getReferenceValidationPattern() {
        return getVendor().getReferenceValidationPattern();
    }

    @GraphQLField
    @GraphQLDescription("vendor item selector")
    public SelectorObject getVendorItemSelector(final DataFetchingEnvironment env) {
        var vendorItemSelector = getVendor().getVendorItemSelector();

        return vendorItemSelector == null ? null : SelectorSecurityUtils.getHasSelectorAccess(env) ?
                new SelectorObject(vendorItemSelector) : null;
    }

    @GraphQLField
    @GraphQLDescription("vendor item cost filter")
    public FilterObject getVendorItemCostFilter(final DataFetchingEnvironment env) {
        var vendorItemCostFilter = getVendor().getVendorItemCostFilter();

        return vendorItemCostFilter == null ? null : FilterSecurityUtils.getHasFilterAccess(env) ?
                new FilterObject(vendorItemCostFilter) : null;
    }

    @GraphQLField
    @GraphQLDescription("vendor status")
    public WorkflowEntityStatusObject getVendorStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, VendorStatusConstants.Workflow_VENDOR_STATUS);
    }

    @GraphQLField
    @GraphQLDescription("vendor items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<VendorItemObject> getVendorItems(final DataFetchingEnvironment env) {
        if(VendorSecurityUtils.getHasVendorItemsAccess(env)) {
            var itemControl = Session.getModelController(VendorControl.class);
            var totalCount = itemControl.countVendorItemsByVendorParty(party);

            try(var objectLimiter = new ObjectLimiter(env, VendorItemConstants.COMPONENT_VENDOR_NAME, VendorItemConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getVendorItemsByVendorParty(party);
                var items = entities.stream().map(VendorItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, items);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
