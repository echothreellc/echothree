// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.filter.server.graphql.FilterObject;
import com.echothree.model.control.filter.server.graphql.FilterSecurityUtils;
import com.echothree.model.control.selector.server.graphql.SelectorSecurityUtils;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.offer.server.graphql.OfferSecurityUtils;
import com.echothree.model.control.offer.server.graphql.OfferUseObject;
import com.echothree.model.control.party.server.graphql.BasePartyObject;
import com.echothree.model.data.accounting.common.pk.GlAccountPK;
import com.echothree.model.data.cancellationpolicy.common.pk.CancellationPolicyPK;
import com.echothree.model.data.filter.common.pk.FilterPK;
import com.echothree.model.data.item.common.pk.ItemAliasTypePK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnPolicyPK;
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.vendor.common.pk.VendorTypePK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

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

//    @GraphQLField
//    @GraphQLDescription("vendor type")
//    @GraphQLNonNull
//    public VendorTypeObject getVendorType(final DataFetchingEnvironment env) {
//        return VendorSecurityUtils.getInstance().getHasVendorTypeAccess(env) ?
//                new VendorTypeObject(getVendor().getVendorType()) : null;
//    }

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

//    @GraphQLField
//    @GraphQLDescription("default item alias type")
//    public ItemAliasTypeObject getDefaultItemAliasType(final DataFetchingEnvironment env) {
//        var defaultItemAliasType = getVendor().getDefaultItemAliasType();
//
//        return defaultItemAliasType == null ? null : ItemSecurityUtils.getInstance().getHasItemAliasAccess(env) ?
//                new ItemAliasObject(defaultItemAliasType) : null;
//    }
//
//    @GraphQLField
//    @GraphQLDescription("cancellation policy")
//    public CancellationPolicyObject getCancellationPolicy(final DataFetchingEnvironment env) {
//        var cancellationPolicy = getVendor().getCancellationPolicy();
//
//        return cancellationPolicy == null ? null : CancellationPolicySecurityUtils.getInstance().getHasCancellationPolicyAccess(env) ?
//                new CancellationPolicyObject(cancellationPolicy) : null;
//    }
//
//    @GraphQLField
//    @GraphQLDescription("return policy")
//    public ReturnPolicyObject getReturnPolicy(final DataFetchingEnvironment env) {
//        var returnPolicy = getVendor().getReturnPolicy();
//
//        return returnPolicy == null ? null : ReturnPolicySecurityUtils.getInstance().getHasReturnPolicyAccess(env) ?
//                new ReturnPolicyObject(returnPolicy) : null;
//    }
//
//    @GraphQLField
//    @GraphQLDescription("AP GL account")
//    public GlAccountObject getApGlAccount(final DataFetchingEnvironment env) {
//        var apGlAccount = getVendor().getApGlAccount();
//
//        return apGlAccount == null ? null : AccountingSecurityUtils.getInstance().getHasGlAccountAccess(env) ?
//                new GlAccountObject(getVendor().getApGlAccount()) : null;
//    }

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

//    @GraphQLField
//    @GraphQLDescription("vendor item selector")
//    public FilterObject getApGlAccount(final DataFetchingEnvironment env) {
//        var vendorItemSelector = getVendor().getVendorItemSelector();
//
//        return vendorItemSelector == null ? null : SelectorSecurityUtils.getInstance().getHasSelectorAccess(env) ?
//                new SelectorObject(vendorItemSelector) : null;
//    }

    @GraphQLField
    @GraphQLDescription("vendor item cost filter")
    public FilterObject getVendorItemCostFilter(final DataFetchingEnvironment env) {
        var vendorItemCostFilter = getVendor().getVendorItemCostFilter();

        return vendorItemCostFilter == null ? null : FilterSecurityUtils.getInstance().getHasFilterAccess(env) ?
                new FilterObject(vendorItemCostFilter) : null;
    }

}