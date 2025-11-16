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

package com.echothree.model.control.vendor.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.vendor.common.transfer.VendorTypeTransfer;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.VendorType;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class VendorTypeTransferCache
        extends BaseVendorTransferCache<VendorType, VendorTypeTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    CancellationPolicyControl cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
    FreeOnBoardControl freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
    ReturnPolicyControl returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
    TermControl termControl = Session.getModelController(TermControl.class);
    VendorControl vendorControl = Session.getModelController(VendorControl.class);

    /** Creates a new instance of VendorTypeTransferCache */
    public VendorTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public VendorTypeTransfer getVendorTypeTransfer(UserVisit userVisit, VendorType vendorType) {
        var vendorTypeTransfer = get(vendorType);
        
        if(vendorTypeTransfer == null) {
            var vendorTypeDetail = vendorType.getLastDetail();
            var vendorTypeName = vendorTypeDetail.getVendorTypeName();
            var defaultTerm = vendorTypeDetail.getDefaultTerm();
            var defaultTermTransfer = defaultTerm == null ? null : termControl.getTermTransfer(userVisit, defaultTerm);
            var defaultFreeOnBoard = vendorTypeDetail.getDefaultFreeOnBoard();
            var defaultFreeOnBoardTransfer = defaultFreeOnBoard == null ? null : freeOnBoardControl.getFreeOnBoardTransfer(userVisit, defaultFreeOnBoard);
            var defaultCancellationPolicy = vendorTypeDetail.getDefaultCancellationPolicy();
            var defaultCancellationPolicyTransfer = defaultCancellationPolicy == null ? null : cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, defaultCancellationPolicy);
            var defaultReturnPolicy = vendorTypeDetail.getDefaultReturnPolicy();
            var defaultReturnPolicyTransfer = defaultReturnPolicy == null ? null : returnPolicyControl.getReturnPolicyTransfer(userVisit, defaultReturnPolicy);
            var defaultApGlAccount = vendorTypeDetail.getDefaultApGlAccount();
            var defaultApGlAccountTransfer = defaultApGlAccount == null ? null : accountingControl.getGlAccountTransfer(userVisit, defaultApGlAccount);
            var defaultHoldUntilComplete = vendorTypeDetail.getDefaultHoldUntilComplete();
            var defaultAllowBackorders = vendorTypeDetail.getDefaultAllowBackorders();
            var defaultAllowSubstitutions = vendorTypeDetail.getDefaultAllowSubstitutions();
            var defaultAllowCombiningShipments = vendorTypeDetail.getDefaultAllowCombiningShipments();
            var defaultRequireReference = vendorTypeDetail.getDefaultRequireReference();
            var defaultAllowReferenceDuplicates = vendorTypeDetail.getDefaultAllowReferenceDuplicates();
            var defaultReferenceValidationPattern = vendorTypeDetail.getDefaultReferenceValidationPattern();
            var isDefault = vendorTypeDetail.getIsDefault();
            var sortOrder = vendorTypeDetail.getSortOrder();
            var description = vendorControl.getBestVendorTypeDescription(vendorType, getLanguage(userVisit));
            
            vendorTypeTransfer = new VendorTypeTransfer(vendorTypeName, defaultTermTransfer, defaultFreeOnBoardTransfer, defaultCancellationPolicyTransfer, defaultReturnPolicyTransfer,
                    defaultApGlAccountTransfer, defaultHoldUntilComplete, defaultAllowBackorders, defaultAllowSubstitutions, defaultAllowCombiningShipments,
                    defaultRequireReference, defaultAllowReferenceDuplicates, defaultReferenceValidationPattern, isDefault, sortOrder, description);
            put(userVisit, vendorType, vendorTypeTransfer);
        }
        
        return vendorTypeTransfer;
    }
    
}
