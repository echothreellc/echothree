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

package com.echothree.model.control.vendor.common.transfer;

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class VendorItemTransfer
        extends BaseTransfer {
    
    private ItemTransfer item;
    private VendorTransfer vendor;
    private String vendorItemName;
    private String description;
    private Integer priority;
    private CancellationPolicyTransfer cancellationPolicy;
    private ReturnPolicyTransfer returnPolicy;
    private WorkflowEntityStatusTransfer vendorItemStatus;
    
    private ListWrapper<VendorItemCostTransfer> vendorItemCosts;
    
    /** Creates a new instance of VendorItemTransfer */
    public VendorItemTransfer(ItemTransfer item, VendorTransfer vendor, String vendorItemName, String description, Integer priority,
            CancellationPolicyTransfer cancellationPolicy, ReturnPolicyTransfer returnPolicy, WorkflowEntityStatusTransfer vendorItemStatus) {
        this.item = item;
        this.vendor = vendor;
        this.vendorItemName = vendorItemName;
        this.description = description;
        this.priority = priority;
        this.cancellationPolicy = cancellationPolicy;
        this.returnPolicy = returnPolicy;
        this.vendorItemStatus = vendorItemStatus;
    }

    /**
     * Returns the item.
     * @return the item
     */
    public ItemTransfer getItem() {
        return item;
    }

    /**
     * Sets the item.
     * @param item the item to set
     */
    public void setItem(ItemTransfer item) {
        this.item = item;
    }

    /**
     * Returns the vendor.
     * @return the vendor
     */
    public VendorTransfer getVendor() {
        return vendor;
    }

    /**
     * Sets the vendor.
     * @param vendor the vendor to set
     */
    public void setVendor(VendorTransfer vendor) {
        this.vendor = vendor;
    }

    /**
     * Returns the vendorItemName.
     * @return the vendorItemName
     */
    public String getVendorItemName() {
        return vendorItemName;
    }

    /**
     * Sets the vendorItemName.
     * @param vendorItemName the vendorItemName to set
     */
    public void setVendorItemName(String vendorItemName) {
        this.vendorItemName = vendorItemName;
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
     * Returns the cancellationPolicy.
     * @return the cancellationPolicy
     */
    public CancellationPolicyTransfer getCancellationPolicy() {
        return cancellationPolicy;
    }

    /**
     * Sets the cancellationPolicy.
     * @param cancellationPolicy the cancellationPolicy to set
     */
    public void setCancellationPolicy(CancellationPolicyTransfer cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    /**
     * Returns the returnPolicy.
     * @return the returnPolicy
     */
    public ReturnPolicyTransfer getReturnPolicy() {
        return returnPolicy;
    }

    /**
     * Sets the returnPolicy.
     * @param returnPolicy the returnPolicy to set
     */
    public void setReturnPolicy(ReturnPolicyTransfer returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    /**
     * Returns the vendorItemStatus.
     * @return the vendorItemStatus
     */
    public WorkflowEntityStatusTransfer getVendorItemStatus() {
        return vendorItemStatus;
    }

    /**
     * Sets the vendorItemStatus.
     * @param vendorItemStatus the vendorItemStatus to set
     */
    public void setVendorItemStatus(WorkflowEntityStatusTransfer vendorItemStatus) {
        this.vendorItemStatus = vendorItemStatus;
    }

    /**
     * Returns the vendorItemCosts.
     * @return the vendorItemCosts
     */
    public ListWrapper<VendorItemCostTransfer> getVendorItemCosts() {
        return vendorItemCosts;
    }

    /**
     * Sets the vendorItemCosts.
     * @param vendorItemCosts the vendorItemCosts to set
     */
    public void setVendorItemCosts(ListWrapper<VendorItemCostTransfer> vendorItemCosts) {
        this.vendorItemCosts = vendorItemCosts;
    }
    
}
