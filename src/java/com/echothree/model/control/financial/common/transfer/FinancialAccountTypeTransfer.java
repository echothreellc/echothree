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

package com.echothree.model.control.financial.common.transfer;

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class FinancialAccountTypeTransfer
        extends BaseTransfer {

    private String financialAccountTypeName;
    private FinancialAccountTypeTransfer parentFinancialAccountType;
    private GlAccountTransfer defaultGlAccount;
    private SequenceTypeTransfer financialAccountSequenceType;
    private SequenceTypeTransfer financialAccountTransactionSequenceType;
    private WorkflowTransfer financialAccountWorkflow;
    private WorkflowEntranceTransfer financialAccountWorkflowEntrance;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;

    /** Creates a new instance of FinancialAccountTypeTransfer */
    public FinancialAccountTypeTransfer(String financialAccountTypeName, FinancialAccountTypeTransfer parentFinancialAccountType,
            GlAccountTransfer defaultGlAccount, SequenceTypeTransfer financialAccountSequenceType, SequenceTypeTransfer financialAccountTransactionSequenceType,
            WorkflowTransfer financialAccountWorkflow, WorkflowEntranceTransfer financialAccountWorkflowEntrance, Boolean isDefault, Integer sortOrder,
            String description) {
        this.financialAccountTypeName = financialAccountTypeName;
        this.parentFinancialAccountType = parentFinancialAccountType;
        this.defaultGlAccount = defaultGlAccount;
        this.financialAccountSequenceType = financialAccountSequenceType;
        this.financialAccountTransactionSequenceType = financialAccountTransactionSequenceType;
        this.financialAccountWorkflow = financialAccountWorkflow;
        this.financialAccountWorkflowEntrance = financialAccountWorkflowEntrance;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public String getFinancialAccountTypeName() {
        return financialAccountTypeName;
    }

    public void setFinancialAccountTypeName(String financialAccountTypeName) {
        this.financialAccountTypeName = financialAccountTypeName;
    }

    public FinancialAccountTypeTransfer getParentFinancialAccountType() {
        return parentFinancialAccountType;
    }

    public void setParentFinancialAccountType(FinancialAccountTypeTransfer parentFinancialAccountType) {
        this.parentFinancialAccountType = parentFinancialAccountType;
    }

    public GlAccountTransfer getDefaultGlAccount() {
        return defaultGlAccount;
    }

    public void setDefaultGlAccount(GlAccountTransfer defaultGlAccount) {
        this.defaultGlAccount = defaultGlAccount;
    }

    public SequenceTypeTransfer getFinancialAccountSequenceType() {
        return financialAccountSequenceType;
    }

    public void setFinancialAccountSequenceType(SequenceTypeTransfer financialAccountSequenceType) {
        this.financialAccountSequenceType = financialAccountSequenceType;
    }

    public SequenceTypeTransfer getFinancialAccountTransactionSequenceType() {
        return financialAccountTransactionSequenceType;
    }

    public void setFinancialAccountTransactionSequenceType(SequenceTypeTransfer financialAccountTransactionSequenceType) {
        this.financialAccountTransactionSequenceType = financialAccountTransactionSequenceType;
    }

    public WorkflowTransfer getFinancialAccountWorkflow() {
        return financialAccountWorkflow;
    }

    public void setFinancialAccountWorkflow(WorkflowTransfer financialAccountWorkflow) {
        this.financialAccountWorkflow = financialAccountWorkflow;
    }

    public WorkflowEntranceTransfer getFinancialAccountWorkflowEntrance() {
        return financialAccountWorkflowEntrance;
    }

    public void setFinancialAccountWorkflowEntrance(WorkflowEntranceTransfer financialAccountWorkflowEntrance) {
        this.financialAccountWorkflowEntrance = financialAccountWorkflowEntrance;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
