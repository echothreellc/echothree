// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.item.common.ItemConstants;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemDescriptionLogic;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.vendor.common.VendorOptions;
import com.echothree.model.control.vendor.common.transfer.VendorItemTransfer;
import com.echothree.model.control.vendor.common.transfer.VendorTransfer;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.vendor.common.workflow.VendorItemStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.model.data.vendor.server.entity.VendorItemDetail;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class VendorItemTransferCache
        extends BaseVendorTransferCache<VendorItem, VendorItemTransfer> {
    
    CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
    ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    ItemDescriptionLogic itemDescriptionLogic = ItemDescriptionLogic.getInstance();
    
    boolean includeVendorItemCosts;
    boolean includePurchasingComments;
    
    /** Creates a new instance of VendorItemTransferCache */
    public VendorItemTransferCache(UserVisit userVisit, VendorControl vendorControl) {
        super(userVisit, vendorControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeVendorItemCosts = options.contains(VendorOptions.VendorItemIncludeVendorItemCosts);
            includePurchasingComments = options.contains(VendorOptions.VendorItemIncludePurchasingComments);
            setIncludeEntityAttributeGroups(options.contains(VendorOptions.VendorItemIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(VendorOptions.VendorItemIncludeTagScopes));
        }

        setIncludeEntityInstance(true);
    }
    
    public VendorItemTransfer getVendorItemTransfer(VendorItem vendorItem) {
        VendorItemTransfer vendorItemTransfer = get(vendorItem);
        
        if(vendorItemTransfer == null) {
            VendorItemDetail vendorItemDetail = vendorItem.getLastDetail();
            Item item = vendorItemDetail.getItem();
            ItemTransfer itemTransfer = itemControl.getItemTransfer(userVisit, item);
            VendorTransfer vendor = vendorControl.getVendorTransfer(userVisit, vendorItemDetail.getVendorParty());
            String vendorItemName = vendorItemDetail.getVendorItemName();
            String description = vendorItemDetail.getDescription();
            Integer priority = vendorItemDetail.getPriority();
            CancellationPolicy cancellationPolicy = vendorItemDetail.getCancellationPolicy();
            CancellationPolicyTransfer cancellationPolicyTransfer = cancellationPolicy == null? null: cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, cancellationPolicy);
            ReturnPolicy returnPolicy = vendorItemDetail.getReturnPolicy();
            ReturnPolicyTransfer returnPolicyTransfer = returnPolicy == null? null: returnPolicyControl.getReturnPolicyTransfer(userVisit, returnPolicy);
            
            if(description == null) {
                description = itemDescriptionLogic.getBestStringUsingNames(null, ItemConstants.ItemDescriptionType_PURCHASE_ORDER_DESCRIPTION, item, getParty());
            }
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(vendorItem.getPrimaryKey());
            WorkflowEntityStatusTransfer vendorItemStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
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
