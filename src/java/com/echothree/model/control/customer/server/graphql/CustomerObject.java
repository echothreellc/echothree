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
import com.echothree.model.control.offer.server.graphql.OfferSecurityUtils;
import com.echothree.model.control.offer.server.graphql.OfferUseObject;
import com.echothree.model.control.party.server.graphql.BasePartyObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicyObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicySecurityUtils;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("customer object")
@GraphQLName("Customer")
public class CustomerObject
        extends BasePartyObject {

    public CustomerObject(Party party) {
        super(party);
    }

    public CustomerObject(Customer customer) {
        super(customer.getParty());

        this.customer = customer;
    }

    private Customer customer;  // Optional, use getCustomer()

    protected Customer getCustomer() {
        if(customer == null) {
            var customerControl = Session.getModelController(CustomerControl.class);

            customer = customerControl.getCustomer(party);
        }

        return customer;
    }

    @GraphQLField
    @GraphQLDescription("customer name")
    @GraphQLNonNull
    public String getCustomerName() {
        return getCustomer().getCustomerName();
    }

    @GraphQLField
    @GraphQLDescription("customer type")
    @GraphQLNonNull
    public CustomerTypeObject getCustomerType(final DataFetchingEnvironment env) {
        return CustomerSecurityUtils.getHasCustomerTypeAccess(env) ?
                new CustomerTypeObject(getCustomer().getCustomerType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("initial offer use")
    @GraphQLNonNull
    public OfferUseObject getInitialOfferUse(final DataFetchingEnvironment env) {
        return OfferSecurityUtils.getHasOfferUseAccess(env) ?
                new OfferUseObject(getCustomer().getInitialOfferUse()) : null;
    }

    @GraphQLField
    @GraphQLDescription("cancellation policy")
    public CancellationPolicyObject getCancellationPolicy(final DataFetchingEnvironment env) {
        var cancellationPolicy = getCustomer().getCancellationPolicy();

        return cancellationPolicy == null ? null : CancellationPolicySecurityUtils.getHasCancellationPolicyAccess(env) ?
                new CancellationPolicyObject(cancellationPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("return policy")
    public ReturnPolicyObject getReturnPolicy(final DataFetchingEnvironment env) {
        var returnPolicy = getCustomer().getReturnPolicy();

        return returnPolicy == null ? null : ReturnPolicySecurityUtils.getHasReturnPolicyAccess(env) ?
                new ReturnPolicyObject(returnPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("AR GL account")
    public GlAccountObject getArGlAccount(final DataFetchingEnvironment env) {
        var arGlAccount = getCustomer().getArGlAccount();

        return arGlAccount == null ? null : AccountingSecurityUtils.getHasGlAccountAccess(env) ?
                new GlAccountObject(getCustomer().getArGlAccount()) : null;
    }

    @GraphQLField
    @GraphQLDescription("hold until complete")
    @GraphQLNonNull
    public boolean getHoldUntilComplete() {
        return getCustomer().getHoldUntilComplete();
    }

    @GraphQLField
    @GraphQLDescription("allow backorders")
    @GraphQLNonNull
    public boolean getAllowBackorders() {
        return getCustomer().getAllowBackorders();
    }

    @GraphQLField
    @GraphQLDescription("allow substitutions")
    @GraphQLNonNull
    public boolean getAllowSubstitutions() {
        return getCustomer().getAllowSubstitutions();
    }

    @GraphQLField
    @GraphQLDescription("allow combining shipments")
    @GraphQLNonNull
    public boolean getAllowCombiningShipments() {
        return getCustomer().getAllowCombiningShipments();
    }

    @GraphQLField
    @GraphQLDescription("require reference")
    @GraphQLNonNull
    public boolean getRequireReference() {
        return getCustomer().getRequireReference();
    }

    @GraphQLField
    @GraphQLDescription("allow reference duplicates")
    @GraphQLNonNull
    public boolean getAllowReferenceDuplicates() {
        return getCustomer().getAllowReferenceDuplicates();
    }

    @GraphQLField
    @GraphQLDescription("reference validation pattern")
    public String getReferenceValidationPattern() {
        return getCustomer().getReferenceValidationPattern();
    }

}
