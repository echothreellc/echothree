// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.vendor.common.transfer.VendorTypeTransfer;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.VendorType;
import com.echothree.model.data.vendor.server.entity.VendorTypeDetail;
import com.echothree.util.server.persistence.Session;

public class VendorTypeTransferCache
        extends BaseVendorTransferCache<VendorType, VendorTypeTransfer> {
    
    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
    ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
    
    /** Creates a new instance of VendorTypeTransferCache */
    public VendorTypeTransferCache(UserVisit userVisit, VendorControl vendorControl) {
        super(userVisit, vendorControl);
        
        setIncludeEntityInstance(true);
    }
    
    public VendorTypeTransfer getVendorTypeTransfer(VendorType vendorType) {
        VendorTypeTransfer vendorTypeTransfer = get(vendorType);
        
        if(vendorTypeTransfer == null) {
            VendorTypeDetail vendorTypeDetail = vendorType.getLastDetail();
            String vendorTypeName = vendorTypeDetail.getVendorTypeName();
            CancellationPolicy defaultCancellationPolicy = vendorTypeDetail.getDefaultCancellationPolicy();
            CancellationPolicyTransfer defaultCancellationPolicyTransfer = defaultCancellationPolicy == null ? null : cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, defaultCancellationPolicy);
            ReturnPolicy defaultReturnPolicy = vendorTypeDetail.getDefaultReturnPolicy();
            ReturnPolicyTransfer defaultReturnPolicyTransfer = defaultReturnPolicy == null ? null : returnPolicyControl.getReturnPolicyTransfer(userVisit, defaultReturnPolicy);
            GlAccount defaultApGlAccount = vendorTypeDetail.getDefaultApGlAccount();
            GlAccountTransfer defaultApGlAccountTransfer = defaultApGlAccount == null ? null : accountingControl.getGlAccountTransfer(userVisit, defaultApGlAccount);
            Boolean defaultHoldUntilComplete = vendorTypeDetail.getDefaultHoldUntilComplete();
            Boolean defaultAllowBackorders = vendorTypeDetail.getDefaultAllowBackorders();
            Boolean defaultAllowSubstitutions = vendorTypeDetail.getDefaultAllowSubstitutions();
            Boolean defaultAllowCombiningShipments = vendorTypeDetail.getDefaultAllowCombiningShipments();
            Boolean defaultRequireReference = vendorTypeDetail.getDefaultRequireReference();
            Boolean defaultAllowReferenceDuplicates = vendorTypeDetail.getDefaultAllowReferenceDuplicates();
            String defaultReferenceValidationPattern = vendorTypeDetail.getDefaultReferenceValidationPattern();
            Boolean isDefault = vendorTypeDetail.getIsDefault();
            Integer sortOrder = vendorTypeDetail.getSortOrder();
            String description = vendorControl.getBestVendorTypeDescription(vendorType, getLanguage());
            
            vendorTypeTransfer = new VendorTypeTransfer(vendorTypeName, defaultCancellationPolicyTransfer, defaultReturnPolicyTransfer,
                    defaultApGlAccountTransfer, defaultHoldUntilComplete, defaultAllowBackorders, defaultAllowSubstitutions, defaultAllowCombiningShipments,
                    defaultRequireReference, defaultAllowReferenceDuplicates, defaultReferenceValidationPattern, isDefault, sortOrder, description);
            put(vendorType, vendorTypeTransfer);
        }
        
        return vendorTypeTransfer;
    }
    
}
