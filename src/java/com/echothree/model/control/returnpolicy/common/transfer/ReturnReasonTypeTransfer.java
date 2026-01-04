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

public class ReturnReasonTypeTransfer
        extends BaseTransfer {
    
    private ReturnReasonTransfer returnReason;
    private ReturnTypeTransfer returnType;
    private Boolean isDefault;
    private Integer sortOrder;
    
    /** Creates a new instance of ReturnReasonTypeTransfer */
    public ReturnReasonTypeTransfer(ReturnReasonTransfer returnReason, ReturnTypeTransfer returnType, Boolean isDefault,
            Integer sortOrder) {
        this.returnReason = returnReason;
        this.returnType = returnType;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }
    
    public ReturnReasonTransfer getReturnReason() {
        return returnReason;
    }
    
    public void setReturnReason(ReturnReasonTransfer returnReason) {
        this.returnReason = returnReason;
    }
    
    public ReturnTypeTransfer getReturnType() {
        return returnType;
    }
    
    public void setReturnType(ReturnTypeTransfer returnType) {
        this.returnType = returnType;
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
