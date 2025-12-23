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

package com.echothree.model.control.printer.common.transfer;

import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PrinterTransfer
        extends BaseTransfer {
    
    private String printerName;
    private PrinterGroupTransfer printerGroup;
    private Integer priority;
    private WorkflowEntityStatusTransfer printerStatus;
    private String description;
    
    /** Creates a new instance of PrinterTransfer */
    public PrinterTransfer(String printerName, PrinterGroupTransfer printerGroup, Integer priority, WorkflowEntityStatusTransfer printerStatus,
            String description) {
        this.printerName = printerName;
        this.printerGroup = printerGroup;
        this.priority = priority;
        this.printerStatus = printerStatus;
        this.description = description;
    }

    /**
     * Returns the printerName.
     * @return the printerName
     */
    public String getPrinterName() {
        return printerName;
    }

    /**
     * Sets the printerName.
     * @param printerName the printerName to set
     */
    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    /**
     * Returns the printerGroup.
     * @return the printerGroup
     */
    public PrinterGroupTransfer getPrinterGroup() {
        return printerGroup;
    }

    /**
     * Sets the printerGroup.
     * @param printerGroup the printerGroup to set
     */
    public void setPrinterGroup(PrinterGroupTransfer printerGroup) {
        this.printerGroup = printerGroup;
    }

    /**
     * Returns the priority.
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Sets the priority.
     * @param priority the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * Returns the printerStatus.
     * @return the printerStatus
     */
    public WorkflowEntityStatusTransfer getPrinterStatus() {
        return printerStatus;
    }

    /**
     * Sets the printerStatus.
     * @param printerStatus the printerStatus to set
     */
    public void setPrinterStatus(WorkflowEntityStatusTransfer printerStatus) {
        this.printerStatus = printerStatus;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
