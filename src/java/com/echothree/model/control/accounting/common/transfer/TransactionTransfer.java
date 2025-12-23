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

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.common.transfer.MapWrapper;

public class TransactionTransfer
        extends BaseTransfer {
    
    private String transactionName;
    private PartyTransfer groupParty;
    private TransactionGroupTransfer transactionGroup;
    private TransactionTypeTransfer transactionType;
    private WorkflowEntityStatusTransfer transactionStatus;

    private ListWrapper<TransactionGlEntryTransfer> transactionGlEntries;
    private ListWrapper<TransactionEntityRoleTransfer> transactionEntityRoles;
    private MapWrapper<TransactionTimeTransfer> transactionTimes;

    /** Creates a new instance of TransactionTransfer */
    public TransactionTransfer(final String transactionName, final PartyTransfer groupParty, final TransactionGroupTransfer transactionGroup,
            final TransactionTypeTransfer transactionType, final WorkflowEntityStatusTransfer transactionStatus) {
        this.transactionName = transactionName;
        this.groupParty = groupParty;
        this.transactionGroup = transactionGroup;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
    }
    
    public String getTransactionName() {
        return transactionName;
    }
    
    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }
    
    public PartyTransfer getGroupParty() {
        return groupParty;
    }
    
    public void setGroupParty(PartyTransfer groupParty) {
        this.groupParty = groupParty;
    }
    
    public TransactionGroupTransfer getTransactionGroup() {
        return transactionGroup;
    }
    
    public void setTransactionGroup(TransactionGroupTransfer transactionGroup) {
        this.transactionGroup = transactionGroup;
    }
    
    public TransactionTypeTransfer getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(TransactionTypeTransfer transactionType) {
        this.transactionType = transactionType;
    }

    public WorkflowEntityStatusTransfer getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(WorkflowEntityStatusTransfer transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public ListWrapper<TransactionGlEntryTransfer> getTransactionGlEntries() {
        return transactionGlEntries;
    }

    public void setTransactionGlEntries(ListWrapper<TransactionGlEntryTransfer> transactionGlEntries) {
        this.transactionGlEntries = transactionGlEntries;
    }

    public ListWrapper<TransactionEntityRoleTransfer> getTransactionEntityRoles() {
        return transactionEntityRoles;
    }

    public void setTransactionEntityRoles(ListWrapper<TransactionEntityRoleTransfer> transactionEntityRoles) {
        this.transactionEntityRoles = transactionEntityRoles;
    }

    public MapWrapper<TransactionTimeTransfer> getTransactionTimes() {
        return transactionTimes;
    }

    public void setTransactionTimes(MapWrapper<TransactionTimeTransfer> transactionTimes) {
        this.transactionTimes = transactionTimes;
    }

}
