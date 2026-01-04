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

package com.echothree.model.control.sales.common.transfer;

import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class SalesOrderResultTransfer
        extends BaseTransfer {
    
    private String orderName;
    private WorkflowEntityStatusTransfer orderStatus;
    
    /** Creates a new instance of SalesOrderResultTransfer */
    public SalesOrderResultTransfer(String orderName, WorkflowEntityStatusTransfer orderStatus) {
        this.orderName = orderName;
        this.orderStatus = orderStatus;
    }

    /**
     * Returns the orderName.
     * @return the orderName
     */
    public String getOrderName() {
        return orderName;
    }

    /**
     * Sets the orderName.
     * @param orderName the orderName to set
     */
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    /**
     * Returns the orderStatus.
     * @return the orderStatus
     */
    public WorkflowEntityStatusTransfer getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the orderStatus.
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(WorkflowEntityStatusTransfer orderStatus) {
        this.orderStatus = orderStatus;
    }
    
}
