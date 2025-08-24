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

package com.echothree.model.control.vendor.server.transfer;

import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.item.common.ItemDescriptionTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemDescriptionLogic;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.vendor.common.VendorOptions;
import com.echothree.model.control.vendor.common.transfer.VendorItemTransfer;
import com.echothree.model.control.vendor.common.workflow.VendorItemStatusConstants;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class VendorItemTransferCache
        extends BaseVendorTransferCache<VendorItem, VendorItemTransfer> {
    
    CancellationPolicyControl cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    ItemControl itemControl = Session.getModelController(ItemControl.class);
    ReturnPolicyControl returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    ItemDescriptionLogic itemDescriptionLogic = ItemDescriptionLogic.getInstance();
    
    boolean includeVendorItemCosts;
    boolean includePurchasingComments;
    
    /** Creates a new instance of VendorItemTransferCache */
    public VendorItemTransferCache(UserVisit userVisit, VendorControl vendorControl) {
        super(userVisit, vendorControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeVendorItemCosts = options.contains(VendorOptions.VendorItemIncludeVendorItemCosts);
            includePurchasingComments = options.contains(VendorOptions.VendorItemIncludePurchasingComments);
            setIncludeEntityAttributeGroups(options.contains(VendorOptions.VendorItemIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(VendorOptions.VendorItemIncludeTagScopes));
        }

        setIncludeEntityInstance(true);
    }
    
    public VendorItemTransfer getVendorItemTransfer(VendorItem vendorItem) {
        var vendorItemTransfer = get(vendorItem);
        
        if(vendorItemTransfer == null) {
            var vendorItemDetail = vendorItem.getLastDetail();
            var item = vendorItemDetail.getItem();
            var itemTransfer = itemControl.getItemTransfer(userVisit, item);
            var vendor = vendorControl.getVendorTransfer(userVisit, vendorItemDetail.getVendorParty());
            var vendorItemName = vendorItemDetail.getVendorItemName();
            var description = vendorItemDetail.getDescription();
            var priority = vendorItemDetail.getPriority();
            var cancellationPolicy = vendorItemDetail.getCancellationPolicy();
            var cancellationPolicyTransfer = cancellationPolicy == null? null: cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, cancellationPolicy);
            var returnPolicy = vendorItemDetail.getReturnPolicy();
            var returnPolicyTransfer = returnPolicy == null? null: returnPolicyControl.getReturnPolicyTransfer(userVisit, returnPolicy);
            
            if(description == null) {
                description = itemDescriptionLogic.getBestStringUsingNames(null, ItemDescriptionTypes.PURCHASE_ORDER_DESCRIPTION.name(), item, getParty());
            }

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(vendorItem.getPrimaryKey());
            var vendorItemStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    VendorItemStatusConstants.Workflow_VENDOR_ITEM_STATUS, entityInstance);

            vendorItemTransfer = new VendorItemTransfer(itemTransfer, vendor, vendorItemName, description, priority, cancellationPolicyTransfer,
                    returnPolicyTransfer, vendorItemStatusTransfer);
            put(vendorItem, vendorItemTransfer);

            if(includeVendorItemCosts) {
                vendorItemTransfer.setVendorItemCosts(new ListWrapper<>(vendorControl.getVendorItemCostTransfersByVendorItem(userVisit, vendorItem)));
            }
            
            if(includePurchasingComments) {
                setupComments(null, entityInstance, vendorItemTransfer, CommentConstants.CommentType_VENDOR_ITEM_PURCHASING);
            }
        }
        
        return vendorItemTransfer;
    }
    
}
