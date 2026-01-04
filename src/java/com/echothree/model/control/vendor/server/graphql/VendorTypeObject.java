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
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicyObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicySecurityUtils;
import com.echothree.model.control.shipment.server.graphql.FreeOnBoardObject;
import com.echothree.model.control.shipment.server.graphql.ShipmentSecurityUtils;
import com.echothree.model.control.term.server.graphql.TermObject;
import com.echothree.model.control.term.server.graphql.TermSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.vendor.server.entity.VendorType;
import com.echothree.model.data.vendor.server.entity.VendorTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("vendor type object")
@GraphQLName("VendorType")
public class VendorTypeObject
        extends BaseEntityInstanceObject {
    
    private final VendorType vendorType; // Always Present
    
    public VendorTypeObject(VendorType vendorType) {
        super(vendorType.getPrimaryKey());
        
        this.vendorType = vendorType;
    }

    private VendorTypeDetail vendorTypeDetail; // Optional, use getVendorTypeDetail()
    
    private VendorTypeDetail getVendorTypeDetail() {
        if(vendorTypeDetail == null) {
            vendorTypeDetail = vendorType.getLastDetail();
        }
        
        return vendorTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("vendor type name")
    @GraphQLNonNull
    public String getVendorTypeName() {
        return getVendorTypeDetail().getVendorTypeName();
    }

    @GraphQLField
    @GraphQLDescription("default term")
    public TermObject getDefaultTerm(final DataFetchingEnvironment env) {
        var defaultTerm = getVendorTypeDetail().getDefaultTerm();

        return defaultTerm == null ? null : TermSecurityUtils.getHasTermAccess(env) ?
                new TermObject(defaultTerm) : null;
    }

    @GraphQLField
    @GraphQLDescription("default free on board")
    public FreeOnBoardObject getDefaultFreeOnBoard(final DataFetchingEnvironment env) {
        var defaultFreeOnBoard = getVendorTypeDetail().getDefaultFreeOnBoard();

        return defaultFreeOnBoard == null ? null : ShipmentSecurityUtils.getHasFreeOnBoardAccess(env) ?
                new FreeOnBoardObject(defaultFreeOnBoard) : null;
    }

    @GraphQLField
    @GraphQLDescription("default cancellation policy")
    public CancellationPolicyObject getDefaultCancellationPolicy(final DataFetchingEnvironment env) {
        var defaultCancellationPolicy = getVendorTypeDetail().getDefaultCancellationPolicy();

        return defaultCancellationPolicy == null ? null : CancellationPolicySecurityUtils.getHasCancellationPolicyAccess(env) ?
                new CancellationPolicyObject(defaultCancellationPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("default return policy")
    public ReturnPolicyObject getDefaultReturnPolicy(final DataFetchingEnvironment env) {
        var defaultReturnPolicy = getVendorTypeDetail().getDefaultReturnPolicy();

        return defaultReturnPolicy == null ? null : ReturnPolicySecurityUtils.getHasReturnPolicyAccess(env) ?
                new ReturnPolicyObject(defaultReturnPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("default AP GL account")
    public GlAccountObject getDefaultApGlAccount(final DataFetchingEnvironment env) {
        var defaultApGlAccount = getVendorTypeDetail().getDefaultApGlAccount();

        return defaultApGlAccount == null ? null : AccountingSecurityUtils.getHasGlAccountAccess(env) ?
                new GlAccountObject(defaultApGlAccount) : null;
    }

    @GraphQLField
    @GraphQLDescription("default hold until complete")
    @GraphQLNonNull
    public boolean getDefaultHoldUntilComplete() {
        return getVendorTypeDetail().getDefaultHoldUntilComplete();
    }

    @GraphQLField
    @GraphQLDescription("default allow backorders")
    @GraphQLNonNull
    public boolean getDefaultAllowBackorders() {
        return getVendorTypeDetail().getDefaultAllowBackorders();
    }

    @GraphQLField
    @GraphQLDescription("default allow substitutions")
    @GraphQLNonNull
    public boolean getDefaultAllowSubstitutions() {
        return getVendorTypeDetail().getDefaultAllowSubstitutions();
    }

    @GraphQLField
    @GraphQLDescription("default allow combining shipments")
    @GraphQLNonNull
    public boolean getDefaultAllowCombiningShipments() {
        return getVendorTypeDetail().getDefaultAllowCombiningShipments();
    }

    @GraphQLField
    @GraphQLDescription("default require reference")
    @GraphQLNonNull
    public boolean getDefaultRequireReference() {
        return getVendorTypeDetail().getDefaultRequireReference();
    }

    @GraphQLField
    @GraphQLDescription("default allow reference duplicates")
    @GraphQLNonNull
    public boolean getDefaultAllowReferenceDuplicates() {
        return getVendorTypeDetail().getDefaultAllowReferenceDuplicates();
    }

    @GraphQLField
    @GraphQLDescription("default reference validation pattern")
    public String getDefaultReferenceValidationPattern() {
        return getVendorTypeDetail().getDefaultReferenceValidationPattern();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getVendorTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getVendorTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return vendorControl.getBestVendorTypeDescription(vendorType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
