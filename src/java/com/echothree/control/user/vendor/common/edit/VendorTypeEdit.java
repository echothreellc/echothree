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

package com.echothree.control.user.vendor.common.edit;

import com.echothree.control.user.vendor.common.spec.VendorTypeSpec;

public interface VendorTypeEdit
        extends VendorTypeSpec, VendorTypeDescriptionEdit {

    String getDefaultTermName();
    void setDefaultTermName(String DefaultTermName);

    String getDefaultFreeOnBoardName();
    void setDefaultFreeOnBoardName(String defaultFreeOnBoardName);

    String getDefaultCancellationPolicyName();
    void setDefaultCancellationPolicyName(String defaultCancellationPolicyName);

    String getDefaultReturnPolicyName();
    void setDefaultReturnPolicyName(String defaultReturnPolicyName);

    String getDefaultApGlAccountName();
    void setDefaultApGlAccountName(String defaultApGlAccountName);
    
    String getDefaultHoldUntilComplete();
    void setDefaultHoldUntilComplete(String defaultHoldUntilComplete);

    String getDefaultAllowBackorders();
    void setDefaultAllowBackorders(String defaultAllowBackorders);

    String getDefaultAllowSubstitutions();
    void setDefaultAllowSubstitutions(String defaultAllowSubstitutions);

    String getDefaultAllowCombiningShipments();
    void setDefaultAllowCombiningShipments(String defaultAllowCombiningShipments);

    String getDefaultRequireReference();
    void setDefaultRequireReference(String defaultRequireReference);
    
    String getDefaultAllowReferenceDuplicates();
    void setDefaultAllowReferenceDuplicates(String defaultAllowReferenceDuplicates);
    
    String getDefaultReferenceValidationPattern();
    void setDefaultReferenceValidationPattern(String defaultReferenceValidationPattern);
    
    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
}
