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

import com.echothree.util.common.transfer.BaseTransfer;

public class TransactionTimeTransfer
        extends BaseTransfer {

    private TransactionTimeTypeTransfer transactionTimeType;
    private Long unformattedTime;
    private String time;
    
    /** Creates a new instance of TransactionTimeTransfer */
    public TransactionTimeTransfer(TransactionTimeTypeTransfer transactionTimeType, Long unformattedTime, String time) {
        this.transactionTimeType = transactionTimeType;
        this.unformattedTime = unformattedTime;
        this.time = time;
    }

    public TransactionTimeTypeTransfer getTransactionTimeType() {
        return transactionTimeType;
    }

    public void setTransactionTimeType(TransactionTimeTypeTransfer transactionTimeType) {
        this.transactionTimeType = transactionTimeType;
    }

    public Long getUnformattedTime() {
        return unformattedTime;
    }

    public void setUnformattedTime(Long unformattedTime) {
        this.unformattedTime = unformattedTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
}
