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

package com.echothree.model.control.returnpolicy.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ReturnPolicyReasonTransfer
        extends BaseTransfer {
    
    private ReturnPolicyTransfer returnPolicy;
    private ReturnReasonTransfer returnReason;
    private Boolean isDefault;
    private Integer sortOrder;
    
    /** Creates a new instance of ReturnPolicyReasonTransfer */
    public ReturnPolicyReasonTransfer(ReturnPolicyTransfer returnPolicy, ReturnReasonTransfer returnReason, Boolean isDefault,
            Integer sortOrder) {
        this.returnPolicy = returnPolicy;
        this.returnReason = returnReason;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }
    
    public ReturnPolicyTransfer getReturnPolicy() {
        return returnPolicy;
    }
    
    public void setReturnPolicy(ReturnPolicyTransfer returnPolicy) {
        this.returnPolicy = returnPolicy;
    }
    
    public ReturnReasonTransfer getReturnReason() {
        return returnReason;
    }
    
    public void setReturnReason(ReturnReasonTransfer returnReason) {
        this.returnReason = returnReason;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
