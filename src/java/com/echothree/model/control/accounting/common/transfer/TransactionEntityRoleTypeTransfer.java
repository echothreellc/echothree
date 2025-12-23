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

package com.echothree.model.control.accounting.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class TransactionEntityRoleTypeTransfer
        extends BaseTransfer {
    
    private TransactionTypeTransfer transactionType;
    private String transactionEntityRoleTypeName;
    private EntityTypeTransfer entityType;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of TransactionEntityRoleTypeTransfer */
    public TransactionEntityRoleTypeTransfer(TransactionTypeTransfer transactionType, String transactionEntityRoleTypeName,
            EntityTypeTransfer entityType, Integer sortOrder, String description) {
        this.transactionType = transactionType;
        this.transactionEntityRoleTypeName = transactionEntityRoleTypeName;
        this.entityType = entityType;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public TransactionTypeTransfer getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(TransactionTypeTransfer transactionType) {
        this.transactionType = transactionType;
    }
    
    public String getTransactionEntityRoleTypeName() {
        return transactionEntityRoleTypeName;
    }
    
    public void setTransactionEntityRoleTypeName(String transactionEntityRoleTypeName) {
        this.transactionEntityRoleTypeName = transactionEntityRoleTypeName;
    }
    
    public EntityTypeTransfer getEntityType() {
        return entityType;
    }
    
    public void setEntityType(EntityTypeTransfer entityType) {
        this.entityType = entityType;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
