// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTypeTransfer;
import com.echothree.model.control.financial.server.FinancialControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.financial.server.entity.FinancialAccountType;
import com.echothree.model.data.financial.server.entity.FinancialAccountTypeDetail;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.server.persistence.Session;

public class FinancialAccountTypeTransferCache
        extends BaseFinancialTransferCache<FinancialAccountType, FinancialAccountTypeTransfer> {
    
    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of FinancialAccountTypeTransferCache */
    public FinancialAccountTypeTransferCache(UserVisit userVisit, FinancialControl financialControl) {
        super(userVisit, financialControl);
        
        setIncludeEntityInstance(true);
    }
    
    public FinancialAccountTypeTransfer getFinancialAccountTypeTransfer(FinancialAccountType financialAccountType) {
        FinancialAccountTypeTransfer financialAccountTypeTransfer = get(financialAccountType);
        
        if(financialAccountTypeTransfer == null) {
            FinancialAccountTypeDetail financialAccountTypeDetail = financialAccountType.getLastDetail();
            String financialAccountTypeName = financialAccountTypeDetail.getFinancialAccountTypeName();
            FinancialAccountType parentFinancialAccountType = financialAccountTypeDetail.getParentFinancialAccountType();
            FinancialAccountTypeTransfer parentFinancialAccountTypeTransfer = parentFinancialAccountType == null? null: getFinancialAccountTypeTransfer(parentFinancialAccountType);
            GlAccountTransfer defaultGlAccountTransfer = accountingControl.getGlAccountTransfer(userVisit, financialAccountTypeDetail.getDefaultGlAccount());
            SequenceType financialAccountSequenceType = financialAccountTypeDetail.getFinancialAccountSequenceType();
            SequenceTypeTransfer financialAccountSequenceTypeTransfer = financialAccountSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, financialAccountSequenceType);
            SequenceType financialAccountTransactionSequenceType = financialAccountTypeDetail.getFinancialAccountTransactionSequenceType();
            SequenceTypeTransfer financialAccountTransactionSequenceTypeTransfer = financialAccountTransactionSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, financialAccountTransactionSequenceType);
            Workflow financialAccountWorkflow = financialAccountTypeDetail.getFinancialAccountWorkflow();
            WorkflowTransfer financialAccountWorkflowTransfer = financialAccountWorkflow == null? null: workflowControl.getWorkflowTransfer(userVisit, financialAccountWorkflow);
            WorkflowEntrance financialAccountWorkflowEntrance = financialAccountTypeDetail.getFinancialAccountWorkflowEntrance();
            WorkflowEntranceTransfer financialAccountWorkflowEntranceTransfer = financialAccountWorkflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, financialAccountWorkflowEntrance);
            Boolean isDefault = financialAccountTypeDetail.getIsDefault();
            Integer sortOrder = financialAccountTypeDetail.getSortOrder();
            String description = financialControl.getBestFinancialAccountTypeDescription(financialAccountType, getLanguage());
            
            financialAccountTypeTransfer = new FinancialAccountTypeTransfer(financialAccountTypeName, parentFinancialAccountTypeTransfer,
                    defaultGlAccountTransfer, financialAccountSequenceTypeTransfer, financialAccountTransactionSequenceTypeTransfer,
                    financialAccountWorkflowTransfer, financialAccountWorkflowEntranceTransfer, isDefault, sortOrder,
                    description);
            put(financialAccountType, financialAccountTypeTransfer);
        }
        
        return financialAccountTypeTransfer;
    }
    
}
