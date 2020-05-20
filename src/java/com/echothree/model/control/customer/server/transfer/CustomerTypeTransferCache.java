// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.customer.common.CustomerProperties;
import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.inventory.common.transfer.AllocationPriorityTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.term.common.transfer.TermTransfer;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeDetail;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class CustomerTypeTransferCache
        extends BaseCustomerTransferCache<CustomerType, CustomerTypeTransfer> {

    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
    OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
    InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
    TermControl termControl = (TermControl)Session.getModelController(TermControl.class);

    TransferProperties transferProperties;
    boolean filterCustomerTypeName;
    boolean filterCustomerSequence;
    boolean filterDefaultOfferUse;
    boolean filterDefaultTerm;
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
    public CustomerTypeTransferCache(UserVisit userVisit, CustomerControl customerControl) {
        super(userVisit, customerControl);

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(CustomerTypeTransfer.class);
            
            if(properties != null) {
                filterCustomerTypeName = !properties.contains(CustomerProperties.CUSTOMER_TYPE_NAME);
                filterCustomerSequence = !properties.contains(CustomerProperties.CUSTOMER_SEQUENCE);
                filterDefaultOfferUse = !properties.contains(CustomerProperties.DEFAULT_OFFER_USE);
                filterDefaultTerm = !properties.contains(CustomerProperties.DEFAULT_TERM);
                filterDefaultCancellationPolicy = !properties.contains(CustomerProperties.DEFAULT_CANCELLATION_POLICY);
                filterDefaultReturnPolicy = !properties.contains(CustomerProperties.DEFAULT_RETURN_POLICY);
                filterDefaultArGlAccount = !properties.contains(CustomerProperties.DEFAULT_AR_GL_ACCOUNT);
                filterDefaultHoldUntilComplete = !properties.contains(CustomerProperties.DEFAULT_HOLD_UNTIL_COMPLETE);
                filterDefaultAllowBackorders = !properties.contains(CustomerProperties.DEFAULT_ALLOW_BACKORDERS);
                filterDefaultAllowSubstitutions = !properties.contains(CustomerProperties.DEFAULT_HOLD_UNTIL_COMPLETE);
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

    public CustomerTypeTransfer getCustomerTypeTransfer(CustomerType customerType) {
        CustomerTypeTransfer customerTypeTransfer = get(customerType);

        if(customerTypeTransfer == null) {
            CustomerTypeDetail customerTypeDetail = customerType.getLastDetail();
            String customerTypeName = filterCustomerTypeName ? null : customerTypeDetail.getCustomerTypeName();
            Sequence customerSequence = filterCustomerSequence ? null : customerTypeDetail.getCustomerSequence();
            SequenceTransfer customerSequenceTransfer = customerSequence == null ? null : sequenceControl.getSequenceTransfer(userVisit, customerSequence);
            OfferUse defaultOfferUse = filterDefaultOfferUse ? null : customerTypeDetail.getDefaultOfferUse();
            OfferUseTransfer defaultOfferUseTransfer = defaultOfferUse == null ? null : offerControl.getOfferUseTransfer(userVisit, defaultOfferUse);
            Term defaultTerm = filterDefaultTerm ? null : customerTypeDetail.getDefaultTerm();
            TermTransfer defaultTermTransfer = defaultTerm == null ? null : termControl.getTermTransfer(userVisit, defaultTerm);
            CancellationPolicy defaultCancellationPolicy = filterDefaultCancellationPolicy ? null : customerTypeDetail.getDefaultCancellationPolicy();
            CancellationPolicyTransfer defaultCancellationPolicyTransfer = defaultCancellationPolicy == null ? null : cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, defaultCancellationPolicy);
            ReturnPolicy defaultReturnPolicy = filterDefaultReturnPolicy ? null : customerTypeDetail.getDefaultReturnPolicy();
            ReturnPolicyTransfer defaultReturnPolicyTransfer = defaultReturnPolicy == null ? null : returnPolicyControl.getReturnPolicyTransfer(userVisit, defaultReturnPolicy);
            GlAccount defaultArGlAccount = filterDefaultArGlAccount ? null : customerTypeDetail.getDefaultArGlAccount();
            GlAccountTransfer defaultArGlAccountTransfer = defaultArGlAccount == null ? null : accountingControl.getGlAccountTransfer(userVisit, defaultArGlAccount);
            Boolean defaultHoldUntilComplete = filterDefaultHoldUntilComplete ? null : customerTypeDetail.getDefaultHoldUntilComplete();
            Boolean defaultAllowBackorders = filterDefaultAllowBackorders ? null : customerTypeDetail.getDefaultAllowBackorders();
            Boolean defaultAllowSubstitutions = filterDefaultAllowSubstitutions ? null : customerTypeDetail.getDefaultAllowSubstitutions();
            Boolean defaultAllowCombiningShipments = filterDefaultAllowCombiningShipments ? null : customerTypeDetail.getDefaultAllowCombiningShipments();
            Boolean defaultRequireReference = filterDefaultRequireReference ? null : customerTypeDetail.getDefaultRequireReference();
            Boolean defaultAllowReferenceDuplicates = filterDefaultAllowReferenceDuplicates ? null : customerTypeDetail.getDefaultAllowReferenceDuplicates();
            String defaultReferenceValidationPattern = filterDefaultReferenceValidationPattern ? null : customerTypeDetail.getDefaultReferenceValidationPattern();
            Boolean defaultTaxable = filterDefaultTaxable ? null : customerTypeDetail.getDefaultTaxable();
            AllocationPriority allocationPriority = filterAllocationPriority ? null : customerTypeDetail.getAllocationPriority();
            AllocationPriorityTransfer allocationPriorityTransfer = allocationPriority == null ? null : inventoryControl.getAllocationPriorityTransfer(userVisit, allocationPriority);
            Boolean isDefault = filterIsDefault ? null : customerTypeDetail.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : customerTypeDetail.getSortOrder();
            String description = filterDescription ? null : customerControl.getBestCustomerTypeDescription(customerType, getLanguage());

            customerTypeTransfer = new CustomerTypeTransfer(customerTypeName, customerSequenceTransfer, defaultOfferUseTransfer, defaultTermTransfer,
                    defaultCancellationPolicyTransfer, defaultReturnPolicyTransfer, defaultArGlAccountTransfer, defaultHoldUntilComplete,
                    defaultAllowBackorders, defaultAllowSubstitutions, defaultAllowCombiningShipments, defaultRequireReference, defaultAllowReferenceDuplicates,
                    defaultReferenceValidationPattern, defaultTaxable, allocationPriorityTransfer, isDefault, sortOrder, description);
            put(customerType, customerTypeTransfer);
        }

        return customerTypeTransfer;
    }
}