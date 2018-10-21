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

package com.echothree.model.control.vendor.remote.transfer;

import com.echothree.model.control.cancellationpolicy.remote.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.item.remote.transfer.ItemTransfer;
import com.echothree.model.control.returnpolicy.remote.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.workflow.remote.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;
import com.echothree.util.remote.transfer.ListWrapper;

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
     * @return the item
     */
    public ItemTransfer getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(ItemTransfer item) {
        this.item = item;
    }

    /**
     * @return the vendor
     */
    public VendorTransfer getVendor() {
        return vendor;
    }

    /**
     * @param vendor the vendor to set
     */
    public void setVendor(VendorTransfer vendor) {
        this.vendor = vendor;
    }

    /**
     * @return the vendorItemName
     */
    public String getVendorItemName() {
        return vendorItemName;
    }

    /**
     * @param vendorItemName the vendorItemName to set
     */
    public void setVendorItemName(String vendorItemName) {
        this.vendorItemName = vendorItemName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * @return the cancellationPolicy
     */
    public CancellationPolicyTransfer getCancellationPolicy() {
        return cancellationPolicy;
    }

    /**
     * @param cancellationPolicy the cancellationPolicy to set
     */
    public void setCancellationPolicy(CancellationPolicyTransfer cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    /**
     * @return the returnPolicy
     */
    public ReturnPolicyTransfer getReturnPolicy() {
        return returnPolicy;
    }

    /**
     * @param returnPolicy the returnPolicy to set
     */
    public void setReturnPolicy(ReturnPolicyTransfer returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    /**
     * @return the vendorItemStatus
     */
    public WorkflowEntityStatusTransfer getVendorItemStatus() {
        return vendorItemStatus;
    }

    /**
     * @param vendorItemStatus the vendorItemStatus to set
     */
    public void setVendorItemStatus(WorkflowEntityStatusTransfer vendorItemStatus) {
        this.vendorItemStatus = vendorItemStatus;
    }

    /**
     * @return the vendorItemCosts
     */
    public ListWrapper<VendorItemCostTransfer> getVendorItemCosts() {
        return vendorItemCosts;
    }

    /**
     * @param vendorItemCosts the vendorItemCosts to set
     */
    public void setVendorItemCosts(ListWrapper<VendorItemCostTransfer> vendorItemCosts) {
        this.vendorItemCosts = vendorItemCosts;
    }
    
}
