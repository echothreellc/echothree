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
import com.echothree.util.common.transfer.ListWrapper;

public class PrinterGroupTransfer
        extends BaseTransfer {
    
    private String printerGroupName;
    private Long unformattedKeepPrintedJobsTime;
    private String keepPrintedJobsTime;
    private Boolean isDefault;
    private Integer sortOrder;
    private WorkflowEntityStatusTransfer printerGroupStatus;
    private String description;
    
    private ListWrapper<PrinterTransfer> printers;

    /** Creates a new instance of PrinterGroupTransfer */
    public PrinterGroupTransfer(String printerGroupName, Long unformattedKeepPrintedJobsTime, String keepPrintedJobsTime, Boolean isDefault, Integer sortOrder,
            WorkflowEntityStatusTransfer printerGroupStatus, String description) {
        this.printerGroupName = printerGroupName;
        this.unformattedKeepPrintedJobsTime = unformattedKeepPrintedJobsTime;
        this.keepPrintedJobsTime = keepPrintedJobsTime;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.printerGroupStatus = printerGroupStatus;
        this.description = description;
    }

    /**
     * Returns the printerGroupName.
     * @return the printerGroupName
     */
    public String getPrinterGroupName() {
        return printerGroupName;
    }

    /**
     * Sets the printerGroupName.
     * @param printerGroupName the printerGroupName to set
     */
    public void setPrinterGroupName(String printerGroupName) {
        this.printerGroupName = printerGroupName;
    }

    /**
     * Returns the unformattedKeepPrintedJobsTime.
     * @return the unformattedKeepPrintedJobsTime
     */
    public Long getUnformattedKeepPrintedJobsTime() {
        return unformattedKeepPrintedJobsTime;
    }

    /**
     * Sets the unformattedKeepPrintedJobsTime.
     * @param unformattedKeepPrintedJobsTime the unformattedKeepPrintedJobsTime to set
     */
    public void setUnformattedKeepPrintedJobsTime(Long unformattedKeepPrintedJobsTime) {
        this.unformattedKeepPrintedJobsTime = unformattedKeepPrintedJobsTime;
    }

    /**
     * Returns the keepPrintedJobsTime.
     * @return the keepPrintedJobsTime
     */
    public String getKeepPrintedJobsTime() {
        return keepPrintedJobsTime;
    }

    /**
     * Sets the keepPrintedJobsTime.
     * @param keepPrintedJobsTime the keepPrintedJobsTime to set
     */
    public void setKeepPrintedJobsTime(String keepPrintedJobsTime) {
        this.keepPrintedJobsTime = keepPrintedJobsTime;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the printerGroupStatus.
     * @return the printerGroupStatus
     */
    public WorkflowEntityStatusTransfer getPrinterGroupStatus() {
        return printerGroupStatus;
    }

    /**
     * Sets the printerGroupStatus.
     * @param printerGroupStatus the printerGroupStatus to set
     */
    public void setPrinterGroupStatus(WorkflowEntityStatusTransfer printerGroupStatus) {
        this.printerGroupStatus = printerGroupStatus;
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

    /**
     * Returns the printers.
     * @return the printers
     */
    public ListWrapper<PrinterTransfer> getPrinters() {
        return printers;
    }

    /**
     * Sets the printers.
     * @param printers the printers to set
     */
    public void setPrinters(ListWrapper<PrinterTransfer> printers) {
        this.printers = printers;
    }

}
