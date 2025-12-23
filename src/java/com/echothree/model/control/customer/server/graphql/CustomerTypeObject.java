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

package com.echothree.model.control.customer.server.graphql;

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.GlAccountObject;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicyObject;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicySecurityUtils;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.inventory.server.graphql.AllocationPriorityObject;
import com.echothree.model.control.inventory.server.graphql.InventorySecurityUtils;
import com.echothree.model.control.offer.server.graphql.OfferSecurityUtils;
import com.echothree.model.control.offer.server.graphql.OfferUseObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicyObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicySecurityUtils;
import com.echothree.model.control.sequence.server.graphql.SequenceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.shipment.server.graphql.FreeOnBoardObject;
import com.echothree.model.control.shipment.server.graphql.ShipmentSecurityUtils;
import com.echothree.model.control.term.server.graphql.TermObject;
import com.echothree.model.control.term.server.graphql.TermSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntranceObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowSecurityUtils;
import com.echothree.model.data.customer.common.CustomerTypeShippingMethodConstants;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("customer type object")
@GraphQLName("CustomerType")
public class CustomerTypeObject
        extends BaseEntityInstanceObject {
    
    private final CustomerType customerType; // Always Present
    
    public CustomerTypeObject(CustomerType customerType) {
        super(customerType.getPrimaryKey());
        
        this.customerType = customerType;
    }

    private CustomerTypeDetail customerTypeDetail; // Optional, use getCustomerTypeDetail()
    
    private CustomerTypeDetail getCustomerTypeDetail() {
        if(customerTypeDetail == null) {
            customerTypeDetail = customerType.getLastDetail();
        }
        
        return customerTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("customer type name")
    @GraphQLNonNull
    public String getCustomerTypeName() {
        return getCustomerTypeDetail().getCustomerTypeName();
    }

    @GraphQLField
    @GraphQLDescription("customer sequence")
    public SequenceObject getCustomerSequence(final DataFetchingEnvironment env) {
        var customerSequence = getCustomerTypeDetail().getCustomerSequence();

        return customerSequence == null ? null : (SequenceSecurityUtils.getHasSequenceAccess(env) ? new SequenceObject(customerSequence) : null);
    }

    @GraphQLField
    @GraphQLDescription("customer sequence")
    public OfferUseObject getDefaultOfferUse(final DataFetchingEnvironment env) {
        var defaultOfferUse = getCustomerTypeDetail().getDefaultOfferUse();

        return defaultOfferUse == null ? null : (OfferSecurityUtils.getHasOfferUseAccess(env) ? new OfferUseObject(defaultOfferUse) : null);
    }

    @GraphQLField
    @GraphQLDescription("default term")
    public TermObject getDefaultTerm(final DataFetchingEnvironment env) {
        var defaultTerm = getCustomerTypeDetail().getDefaultTerm();

        return defaultTerm == null ? null : TermSecurityUtils.getHasTermAccess(env) ?
                new TermObject(defaultTerm) : null;
    }

    @GraphQLField
    @GraphQLDescription("default free on board")
    public FreeOnBoardObject getDefaultFreeOnBoard(final DataFetchingEnvironment env) {
        var defaultFreeOnBoard = getCustomerTypeDetail().getDefaultFreeOnBoard();

        return defaultFreeOnBoard == null ? null : ShipmentSecurityUtils.getHasFreeOnBoardAccess(env) ?
                new FreeOnBoardObject(defaultFreeOnBoard) : null;
    }

    @GraphQLField
    @GraphQLDescription("default cancellation policy")
    public CancellationPolicyObject getDefaultCancellationPolicy(final DataFetchingEnvironment env) {
        var defaultCancellationPolicy = getCustomerTypeDetail().getDefaultCancellationPolicy();

        return defaultCancellationPolicy == null ? null : CancellationPolicySecurityUtils.getHasCancellationPolicyAccess(env) ?
                new CancellationPolicyObject(defaultCancellationPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("default return policy")
    public ReturnPolicyObject getDefaultReturnPolicy(final DataFetchingEnvironment env) {
        var defaultReturnPolicy = getCustomerTypeDetail().getDefaultReturnPolicy();

        return defaultReturnPolicy == null ? null : ReturnPolicySecurityUtils.getHasReturnPolicyAccess(env) ?
                new ReturnPolicyObject(defaultReturnPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("default customer status")
    public WorkflowEntranceObject getDefaultCustomerStatus(final DataFetchingEnvironment env) {
        var defaultCustomerStatus = getCustomerTypeDetail().getDefaultCustomerStatus();

        return defaultCustomerStatus == null ? null : (WorkflowSecurityUtils.getHasWorkflowEntranceAccess(env) ? new WorkflowEntranceObject(defaultCustomerStatus) : null);
    }

    @GraphQLField
    @GraphQLDescription("default customer credit status")
    public WorkflowEntranceObject getDefaultCustomerCreditStatus(final DataFetchingEnvironment env) {
        var defaultCustomerCreditStatus = getCustomerTypeDetail().getDefaultCustomerCreditStatus();

        return defaultCustomerCreditStatus == null ? null : (WorkflowSecurityUtils.getHasWorkflowEntranceAccess(env) ? new WorkflowEntranceObject(defaultCustomerCreditStatus) : null);
    }

    @GraphQLField
    @GraphQLDescription("default AR GL account")
    public GlAccountObject getDefaultArGlAccount(final DataFetchingEnvironment env) {
        var defaultApGlAccount = getCustomerTypeDetail().getDefaultArGlAccount();

        return defaultApGlAccount == null ? null : AccountingSecurityUtils.getHasGlAccountAccess(env) ?
                new GlAccountObject(defaultApGlAccount) : null;
    }

    @GraphQLField
    @GraphQLDescription("default hold until complete")
    @GraphQLNonNull
    public boolean getDefaultHoldUntilComplete() {
        return getCustomerTypeDetail().getDefaultHoldUntilComplete();
    }

    @GraphQLField
    @GraphQLDescription("default allow backorders")
    @GraphQLNonNull
    public boolean getDefaultAllowBackorders() {
        return getCustomerTypeDetail().getDefaultAllowBackorders();
    }

    @GraphQLField
    @GraphQLDescription("default allow substitutions")
    @GraphQLNonNull
    public boolean getDefaultAllowSubstitutions() {
        return getCustomerTypeDetail().getDefaultAllowSubstitutions();
    }

    @GraphQLField
    @GraphQLDescription("default allow combining shipments")
    @GraphQLNonNull
    public boolean getDefaultAllowCombiningShipments() {
        return getCustomerTypeDetail().getDefaultAllowCombiningShipments();
    }

    @GraphQLField
    @GraphQLDescription("default require reference")
    @GraphQLNonNull
    public boolean getDefaultRequireReference() {
        return getCustomerTypeDetail().getDefaultRequireReference();
    }

    @GraphQLField
    @GraphQLDescription("default allow reference duplicates")
    @GraphQLNonNull
    public boolean getDefaultAllowReferenceDuplicates() {
        return getCustomerTypeDetail().getDefaultAllowReferenceDuplicates();
    }

    @GraphQLField
    @GraphQLDescription("default reference validation pattern")
    public String getDefaultReferenceValidationPattern() {
        return getCustomerTypeDetail().getDefaultReferenceValidationPattern();
    }

    @GraphQLField
    @GraphQLDescription("default taxable")
    @GraphQLNonNull
    public boolean getDefaultTaxable() {
        return getCustomerTypeDetail().getDefaultTaxable();
    }

    @GraphQLField
    @GraphQLDescription("default AR GL account")
    public AllocationPriorityObject getAllocationPriority(final DataFetchingEnvironment env) {
        var allocationPriority = getCustomerTypeDetail().getAllocationPriority();

        return allocationPriority == null ? null : InventorySecurityUtils.getHasAllocationPriorityAccess(env) ?
                new AllocationPriorityObject(allocationPriority) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getCustomerTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getCustomerTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var customerControl = Session.getModelController(CustomerControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return customerControl.getBestCustomerTypeDescription(customerType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("customer type shipping methods")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<CustomerTypeShippingMethodObject> getCustomerTypeShippingMethods(final DataFetchingEnvironment env) {
//        if(CustomerSecurityUtils.getHasCustomerTypeShippingMethodsAccess(env)) {
        var customerControl = Session.getModelController(CustomerControl.class);
            var totalCount = customerControl.countCustomerTypeShippingMethodsByCustomerType(customerType);

            try(var objectLimiter = new ObjectLimiter(env, CustomerTypeShippingMethodConstants.COMPONENT_VENDOR_NAME, CustomerTypeShippingMethodConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = customerControl.getCustomerTypeShippingMethodsByCustomerType(customerType);
                var objects = entities.stream().map(CustomerTypeShippingMethodObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, objects);
            }
//        } else {
//            return Connections.emptyConnection();
//        }
    }

}
