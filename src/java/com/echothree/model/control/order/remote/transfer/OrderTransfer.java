// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.order.remote.transfer;

import com.echothree.model.control.accounting.remote.transfer.CurrencyTransfer;
import com.echothree.model.control.cancellationpolicy.remote.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.returnpolicy.remote.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.term.remote.transfer.TermTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;
import com.echothree.util.remote.transfer.MapWrapper;

public class OrderTransfer
        extends BaseTransfer {

    private OrderTypeTransfer orderType;
    protected String orderName;
    private OrderPriorityTransfer orderPriority;
    private CurrencyTransfer currency;
    private Boolean holdUntilComplete;
    private Boolean allowBackorders;
    private Boolean allowSubstitutions;
    private Boolean allowCombiningShipments;
    private TermTransfer term;
    private String reference;
    private String description;
    private CancellationPolicyTransfer cancellationPolicy;
    private ReturnPolicyTransfer returnPolicy;
    private Boolean taxable;

    private MapWrapper<OrderRoleTransfer> orderRoles;
    private MapWrapper<OrderTimeTransfer> orderTimes;

    protected OrderTransfer(OrderTypeTransfer orderType, String orderName, OrderPriorityTransfer orderPriority, CurrencyTransfer currency,
            Boolean holdUntilComplete, Boolean allowBackorders, Boolean allowSubstitutions, Boolean allowCombiningShipments, TermTransfer term,
            String reference, String description, CancellationPolicyTransfer cancellationPolicy, ReturnPolicyTransfer returnPolicy, Boolean taxable) {
        this.orderType = orderType;
        this.orderName = orderName;
        this.orderPriority = orderPriority;
        this.currency = currency;
        this.holdUntilComplete = holdUntilComplete;
        this.allowBackorders = allowBackorders;
        this.allowSubstitutions = allowSubstitutions;
        this.allowCombiningShipments = allowCombiningShipments;
        this.term = term;
        this.reference = reference;
        this.description = description;
        this.cancellationPolicy = cancellationPolicy;
        this.returnPolicy = returnPolicy;
        this.taxable = taxable;
    }

    /**
     * @return the orderType
     */
    public OrderTypeTransfer getOrderType() {
        return orderType;
    }

    /**
     * @param orderType the orderType to set
     */
    public void setOrderType(OrderTypeTransfer orderType) {
        this.orderType = orderType;
    }

    /**
     * @return the orderName
     */
    public String getOrderName() {
        return orderName;
    }

    /**
     * @param orderName the orderName to set
     */
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    /**
     * @return the orderPriority
     */
    public OrderPriorityTransfer getOrderPriority() {
        return orderPriority;
    }

    /**
     * @param orderPriority the orderPriority to set
     */
    public void setOrderPriority(OrderPriorityTransfer orderPriority) {
        this.orderPriority = orderPriority;
    }

    /**
     * @return the currency
     */
    public CurrencyTransfer getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(CurrencyTransfer currency) {
        this.currency = currency;
    }

    /**
     * @return the holdUntilComplete
     */
    public Boolean getHoldUntilComplete() {
        return holdUntilComplete;
    }

    /**
     * @param holdUntilComplete the holdUntilComplete to set
     */
    public void setHoldUntilComplete(Boolean holdUntilComplete) {
        this.holdUntilComplete = holdUntilComplete;
    }

    /**
     * @return the allowBackorders
     */
    public Boolean getAllowBackorders() {
        return allowBackorders;
    }

    /**
     * @param allowBackorders the allowBackorders to set
     */
    public void setAllowBackorders(Boolean allowBackorders) {
        this.allowBackorders = allowBackorders;
    }

    /**
     * @return the allowSubstitutions
     */
    public Boolean getAllowSubstitutions() {
        return allowSubstitutions;
    }

    /**
     * @param allowSubstitutions the allowSubstitutions to set
     */
    public void setAllowSubstitutions(Boolean allowSubstitutions) {
        this.allowSubstitutions = allowSubstitutions;
    }

    /**
     * @return the allowCombiningShipments
     */
    public Boolean getAllowCombiningShipments() {
        return allowCombiningShipments;
    }

    /**
     * @param allowCombiningShipments the allowCombiningShipments to set
     */
    public void setAllowCombiningShipments(Boolean allowCombiningShipments) {
        this.allowCombiningShipments = allowCombiningShipments;
    }

    /**
     * @return the term
     */
    public TermTransfer getTerm() {
        return term;
    }

    /**
     * @param term the term to set
     */
    public void setTerm(TermTransfer term) {
        this.term = term;
    }

    /**
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the cancellationPolicy
     */
    public CancellationPolicyTransfer getCancellationPolicy() {
        return cancellationPolicy;
    }

    /**
     * @param cancellationPolicy the cancellationPolicy to set
     */
    public void setCancellationPolicy(CancellationPolicyTransfer cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    /**
     * @return the returnPolicy
     */
    public ReturnPolicyTransfer getReturnPolicy() {
        return returnPolicy;
    }

    /**
     * @param returnPolicy the returnPolicy to set
     */
    public void setReturnPolicy(ReturnPolicyTransfer returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    /**
     * @return the taxable
     */
    public Boolean getTaxable() {
        return taxable;
    }

    /**
     * @param taxable the taxable to set
     */
    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    /**
     * @return the orderRoles
     */
    public MapWrapper<OrderRoleTransfer> getOrderRoles() {
        return orderRoles;
    }

    /**
     * @param orderRoles the orderRoles to set
     */
    public void setOrderRoles(MapWrapper<OrderRoleTransfer> orderRoles) {
        this.orderRoles = orderRoles;
    }

    /**
     * @return the orderTimes
     */
    public MapWrapper<OrderTimeTransfer> getOrderTimes() {
        return orderTimes;
    }

    /**
     * @param orderTimes the orderTimes to set
     */
    public void setOrderTimes(MapWrapper<OrderTimeTransfer> orderTimes) {
        this.orderTimes = orderTimes;
    }

 }