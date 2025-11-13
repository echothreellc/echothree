// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.customer.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.customer.common.CustomerProperties;
import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;

public class CustomerTypeTransferCache
        extends BaseCustomerTransferCache<CustomerType, CustomerTypeTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    CancellationPolicyControl cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
    FreeOnBoardControl freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
    InventoryControl inventoryControl = Session.getModelController(InventoryControl.class);
    OfferUseControl offerUseControl = Session.getModelController(OfferUseControl.class);
    ReturnPolicyControl returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    TermControl termControl = Session.getModelController(TermControl.class);

    TransferProperties transferProperties;
    boolean filterCustomerTypeName;
    boolean filterCustomerSequence;
    boolean filterDefaultOfferUse;
    boolean filterDefaultTerm;
    boolean filterDefaultFreeOnBoard;
    boolean filterDefaultCancellationPolicy;
    boolean filterDefaultReturnPolicy;
    boolean filterDefaultArGlAccount;
    boolean filterDefaultHoldUntilComplete;
    boolean filterDefaultAllowBackorders;
    boolean filterDefaultAllowSubstitutions;
    boolean filterDefaultAllowCombiningShipments;
    boolean filterDefaultRequireReference;
    boolean filterDefaultAllowReferenceDuplicates;
    boolean filterDefaultReferenceValidationPattern;
    boolean filterDefaultTaxable;
    boolean filterAllocationPriority;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;

    /** Creates a new instance of CustomerTypeTransferCache */
    public CustomerTypeTransferCache(CustomerControl customerControl) {
        super(customerControl);

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(CustomerTypeTransfer.class);
            
            if(properties != null) {
                filterCustomerTypeName = !properties.contains(CustomerProperties.CUSTOMER_TYPE_NAME);
                filterCustomerSequence = !properties.contains(CustomerProperties.CUSTOMER_SEQUENCE);
                filterDefaultOfferUse = !properties.contains(CustomerProperties.DEFAULT_OFFER_USE);
                filterDefaultTerm = !properties.contains(CustomerProperties.DEFAULT_TERM);
                filterDefaultFreeOnBoard = !properties.contains(CustomerProperties.DEFAULT_FREE_ON_BOARD);
                filterDefaultCancellationPolicy = !properties.contains(CustomerProperties.DEFAULT_CANCELLATION_POLICY);
                filterDefaultReturnPolicy = !properties.contains(CustomerProperties.DEFAULT_RETURN_POLICY);
                filterDefaultArGlAccount = !properties.contains(CustomerProperties.DEFAULT_AR_GL_ACCOUNT);
                filterDefaultHoldUntilComplete = !properties.contains(CustomerProperties.DEFAULT_HOLD_UNTIL_COMPLETE);
                filterDefaultAllowBackorders = !properties.contains(CustomerProperties.DEFAULT_ALLOW_BACKORDERS);
                filterDefaultAllowSubstitutions = !properties.contains(CustomerProperties.DEFAULT_ALLOW_SUBSTITUTION);
                filterDefaultAllowCombiningShipments = !properties.contains(CustomerProperties.DEFAULT_ALLOW_COMBINING_SHIPMENTS);
                filterDefaultRequireReference = !properties.contains(CustomerProperties.DEFAULT_REQUIRE_REFERENCE);
                filterDefaultAllowReferenceDuplicates = !properties.contains(CustomerProperties.DEFAULT_ALLOW_REFERENCE_DUPLICATES);
                filterDefaultReferenceValidationPattern = !properties.contains(CustomerProperties.DEFAULT_REFERENCE_VALIDATION_PATTERN);
                filterDefaultTaxable = !properties.contains(CustomerProperties.DEFAULT_TAXABLE);
                filterAllocationPriority = !properties.contains(CustomerProperties.ALLOCATION_PRIORITY);
                filterIsDefault = !properties.contains(CustomerProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(CustomerProperties.SORT_ORDER);
                filterDescription = !properties.contains(CustomerProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(CustomerProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }

    public CustomerTypeTransfer getCustomerTypeTransfer(UserVisit userVisit, CustomerType customerType) {
        var customerTypeTransfer = get(customerType);

        if(customerTypeTransfer == null) {
            var customerTypeDetail = customerType.getLastDetail();
            var customerTypeName = filterCustomerTypeName ? null : customerTypeDetail.getCustomerTypeName();
            var customerSequence = filterCustomerSequence ? null : customerTypeDetail.getCustomerSequence();
            var customerSequenceTransfer = customerSequence == null ? null : sequenceControl.getSequenceTransfer(userVisit, customerSequence);
            var defaultOfferUse = filterDefaultOfferUse ? null : customerTypeDetail.getDefaultOfferUse();
            var defaultOfferUseTransfer = defaultOfferUse == null ? null : offerUseControl.getOfferUseTransfer(userVisit, defaultOfferUse);
            var defaultTerm = filterDefaultTerm ? null : customerTypeDetail.getDefaultTerm();
            var defaultTermTransfer = defaultTerm == null ? null : termControl.getTermTransfer(userVisit, defaultTerm);
            var defaultFreeOnBoard = filterDefaultFreeOnBoard ? null : customerTypeDetail.getDefaultFreeOnBoard();
            var defaultFreeOnBoardTransfer = defaultFreeOnBoard == null ? null : freeOnBoardControl.getFreeOnBoardTransfer(userVisit, defaultFreeOnBoard);
            var defaultCancellationPolicy = filterDefaultCancellationPolicy ? null : customerTypeDetail.getDefaultCancellationPolicy();
            var defaultCancellationPolicyTransfer = defaultCancellationPolicy == null ? null : cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, defaultCancellationPolicy);
            var defaultReturnPolicy = filterDefaultReturnPolicy ? null : customerTypeDetail.getDefaultReturnPolicy();
            var defaultReturnPolicyTransfer = defaultReturnPolicy == null ? null : returnPolicyControl.getReturnPolicyTransfer(userVisit, defaultReturnPolicy);
            var defaultArGlAccount = filterDefaultArGlAccount ? null : customerTypeDetail.getDefaultArGlAccount();
            var defaultArGlAccountTransfer = defaultArGlAccount == null ? null : accountingControl.getGlAccountTransfer(userVisit, defaultArGlAccount);
            var defaultHoldUntilComplete = filterDefaultHoldUntilComplete ? null : customerTypeDetail.getDefaultHoldUntilComplete();
            var defaultAllowBackorders = filterDefaultAllowBackorders ? null : customerTypeDetail.getDefaultAllowBackorders();
            var defaultAllowSubstitutions = filterDefaultAllowSubstitutions ? null : customerTypeDetail.getDefaultAllowSubstitutions();
            var defaultAllowCombiningShipments = filterDefaultAllowCombiningShipments ? null : customerTypeDetail.getDefaultAllowCombiningShipments();
            var defaultRequireReference = filterDefaultRequireReference ? null : customerTypeDetail.getDefaultRequireReference();
            var defaultAllowReferenceDuplicates = filterDefaultAllowReferenceDuplicates ? null : customerTypeDetail.getDefaultAllowReferenceDuplicates();
            var defaultReferenceValidationPattern = filterDefaultReferenceValidationPattern ? null : customerTypeDetail.getDefaultReferenceValidationPattern();
            var defaultTaxable = filterDefaultTaxable ? null : customerTypeDetail.getDefaultTaxable();
            var allocationPriority = filterAllocationPriority ? null : customerTypeDetail.getAllocationPriority();
            var allocationPriorityTransfer = allocationPriority == null ? null : inventoryControl.getAllocationPriorityTransfer(userVisit, allocationPriority);
            var isDefault = filterIsDefault ? null : customerTypeDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : customerTypeDetail.getSortOrder();
            var description = filterDescription ? null : customerControl.getBestCustomerTypeDescription(customerType, getLanguage(userVisit));

            customerTypeTransfer = new CustomerTypeTransfer(customerTypeName, customerSequenceTransfer, defaultOfferUseTransfer,
                    defaultTermTransfer, defaultFreeOnBoardTransfer, defaultCancellationPolicyTransfer, defaultReturnPolicyTransfer,
                    defaultArGlAccountTransfer, defaultHoldUntilComplete, defaultAllowBackorders, defaultAllowSubstitutions,
                    defaultAllowCombiningShipments, defaultRequireReference, defaultAllowReferenceDuplicates,
                    defaultReferenceValidationPattern, defaultTaxable, allocationPriorityTransfer, isDefault, sortOrder, description);
            put(userVisit, customerType, customerTypeTransfer);
        }

        return customerTypeTransfer;
    }
}