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

package com.echothree.model.control.cancellationpolicy.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class CancellationPolicyReasonTransfer
        extends BaseTransfer {
    
    private CancellationPolicyTransfer cancellationPolicy;
    private CancellationReasonTransfer cancellationReason;
    private Boolean isDefault;
    private Integer sortOrder;
    
    /** Creates a new instance of CancellationPolicyReasonTransfer */
    public CancellationPolicyReasonTransfer(CancellationPolicyTransfer cancellationPolicy, CancellationReasonTransfer cancellationReason, Boolean isDefault,
            Integer sortOrder) {
        this.cancellationPolicy = cancellationPolicy;
        this.cancellationReason = cancellationReason;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }
    
    public CancellationPolicyTransfer getCancellationPolicy() {
        return cancellationPolicy;
    }
    
    public void setCancellationPolicy(CancellationPolicyTransfer cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }
    
    public CancellationReasonTransfer getCancellationReason() {
        return cancellationReason;
    }
    
    public void setCancellationReason(CancellationReasonTransfer cancellationReason) {
        this.cancellationReason = cancellationReason;
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
