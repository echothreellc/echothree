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

package com.echothree.model.control.financial.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTypeTransfer;
import com.echothree.model.control.financial.server.control.FinancialControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.financial.server.entity.FinancialAccountType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FinancialAccountTypeTransferCache
        extends BaseFinancialTransferCache<FinancialAccountType, FinancialAccountTypeTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    FinancialControl financialControl = Session.getModelController(FinancialControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of FinancialAccountTypeTransferCache */
    public FinancialAccountTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public FinancialAccountTypeTransfer getFinancialAccountTypeTransfer(UserVisit userVisit, FinancialAccountType financialAccountType) {
        var financialAccountTypeTransfer = get(financialAccountType);
        
        if(financialAccountTypeTransfer == null) {
            var financialAccountTypeDetail = financialAccountType.getLastDetail();
            var financialAccountTypeName = financialAccountTypeDetail.getFinancialAccountTypeName();
            var parentFinancialAccountType = financialAccountTypeDetail.getParentFinancialAccountType();
            var parentFinancialAccountTypeTransfer = parentFinancialAccountType == null ? null : getFinancialAccountTypeTransfer(userVisit, parentFinancialAccountType);
            var defaultGlAccountTransfer = accountingControl.getGlAccountTransfer(userVisit, financialAccountTypeDetail.getDefaultGlAccount());
            var financialAccountSequenceType = financialAccountTypeDetail.getFinancialAccountSequenceType();
            var financialAccountSequenceTypeTransfer = financialAccountSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, financialAccountSequenceType);
            var financialAccountTransactionSequenceType = financialAccountTypeDetail.getFinancialAccountTransactionSequenceType();
            var financialAccountTransactionSequenceTypeTransfer = financialAccountTransactionSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, financialAccountTransactionSequenceType);
            var financialAccountWorkflow = financialAccountTypeDetail.getFinancialAccountWorkflow();
            var financialAccountWorkflowTransfer = financialAccountWorkflow == null? null: workflowControl.getWorkflowTransfer(userVisit, financialAccountWorkflow);
            var financialAccountWorkflowEntrance = financialAccountTypeDetail.getFinancialAccountWorkflowEntrance();
            var financialAccountWorkflowEntranceTransfer = financialAccountWorkflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, financialAccountWorkflowEntrance);
            var isDefault = financialAccountTypeDetail.getIsDefault();
            var sortOrder = financialAccountTypeDetail.getSortOrder();
            var description = financialControl.getBestFinancialAccountTypeDescription(financialAccountType, getLanguage(userVisit));
            
            financialAccountTypeTransfer = new FinancialAccountTypeTransfer(financialAccountTypeName, parentFinancialAccountTypeTransfer,
                    defaultGlAccountTransfer, financialAccountSequenceTypeTransfer, financialAccountTransactionSequenceTypeTransfer,
                    financialAccountWorkflowTransfer, financialAccountWorkflowEntranceTransfer, isDefault, sortOrder,
                    description);
            put(userVisit, financialAccountType, financialAccountTypeTransfer);
        }
        
        return financialAccountTypeTransfer;
    }
    
}
