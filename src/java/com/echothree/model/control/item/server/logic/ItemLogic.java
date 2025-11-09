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

package com.echothree.model.control.item.server.logic;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.item.common.exception.DuplicateItemNameException;
import com.echothree.model.control.item.common.exception.UnknownItemNameException;
import com.echothree.model.control.item.common.exception.UnknownItemNameOrAliasException;
import com.echothree.model.control.item.common.exception.UnknownItemStatusChoiceException;
import com.echothree.model.control.item.common.exception.UnknownItemTypeNameException;
import com.echothree.model.control.item.common.exception.UnknownItemUseTypeNameException;
import com.echothree.model.control.item.common.workflow.ItemStatusConstants;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.exception.MissingDefaultSequenceException;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.item.server.entity.ItemInventoryType;
import com.echothree.model.data.item.server.entity.ItemPriceType;
import com.echothree.model.data.item.server.entity.ItemType;
import com.echothree.model.data.item.server.entity.ItemUseType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.style.server.entity.StylePath;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class ItemLogic
        extends BaseLogic {

    @Inject
    EntityInstanceControl entityInstanceControl;

    @Inject
    ItemControl itemControl;

    @Inject
    WorkflowControl workflowControl;

    protected ItemLogic() {
        super();
    }

    public static ItemLogic getInstance() {
        return CDI.current().select(ItemLogic.class).get();
    }
    
    public ItemType getItemTypeByName(final ExecutionErrorAccumulator eea, final String itemTypeName) {
        var itemType = itemControl.getItemTypeByName(itemTypeName);

        if(itemType == null) {
            handleExecutionError(UnknownItemTypeNameException.class, eea, ExecutionErrors.UnknownItemTypeName.name(), itemTypeName);
        }

        return itemType;
    }

    public ItemUseType getItemUseTypeByName(final ExecutionErrorAccumulator eea, final String itemUseTypeName) {
        var itemUseType = itemControl.getItemUseTypeByName(itemUseTypeName);

        if(itemUseType == null) {
            handleExecutionError(UnknownItemUseTypeNameException.class, eea, ExecutionErrors.UnknownItemUseTypeName.name(), itemUseTypeName);
        }

        return itemUseType;
    }

    public Item getItemByName(final ExecutionErrorAccumulator eea, final String itemName) {
        var item = itemControl.getItemByName(itemName);

        if(item == null) {
            handleExecutionError(UnknownItemNameException.class, eea, ExecutionErrors.UnknownItemName.name(), itemName);
        }

        return item;
    }

    public Item getItemByNameThenAlias(final ExecutionErrorAccumulator eea, final String itemNameOrAlias) {
        var item = itemControl.getItemByNameThenAlias(itemNameOrAlias);

        if(item == null) {
            handleExecutionError(UnknownItemNameOrAliasException.class, eea, ExecutionErrors.UnknownItemNameOrAlias.name(), itemNameOrAlias);
        }

        return item;
    }

    public Item createItem(final ExecutionErrorAccumulator eea, String itemName, final ItemType itemType, final ItemUseType itemUseType,
            ItemCategory itemCategory, final ItemAccountingCategory itemAccountingCategory, final ItemPurchasingCategory itemPurchasingCategory,
            final Party companyParty, final ItemDeliveryType itemDeliveryType, final ItemInventoryType itemInventoryType,
            final Boolean inventorySerialized, final Sequence serialNumberSequence, final Boolean shippingChargeExempt,
            final Long shippingStartTime, final Long shippingEndTime, final Long salesOrderStartTime, final Long salesOrderEndTime,
            final Long purchaseOrderStartTime, final Long purchaseOrderEndTime, final Boolean allowClubDiscounts, final Boolean allowCouponDiscounts,
            final Boolean allowAssociatePayments, final UnitOfMeasureKind unitOfMeasureKind, final ItemPriceType itemPriceType,
            final CancellationPolicy cancellationPolicy, final ReturnPolicy returnPolicy, final StylePath stylePath, final BasePK createdBy) {
        Item item = null;
        
        if(itemCategory == null) {
            itemCategory = ItemCategoryLogic.getInstance().getDefaultItemCategory(eea);
        }
        
        if(itemName == null && !hasExecutionErrors(eea)) {
            itemName = getItemName(eea, itemCategory);
        }
        
        if(itemControl.getItemByNameThenAlias(itemName) != null) {
            handleExecutionError(DuplicateItemNameException.class, eea, ExecutionErrors.DuplicateItemName.name(),
                    itemName);
        }
        
        if(!hasExecutionErrors(eea)) {
            item = itemControl.createItem(itemName, itemType, itemUseType, itemCategory, itemAccountingCategory,
                    itemPurchasingCategory, companyParty, itemDeliveryType, itemInventoryType, inventorySerialized,
                    serialNumberSequence, shippingChargeExempt, shippingStartTime, shippingEndTime, salesOrderStartTime,
                    salesOrderEndTime, purchaseOrderStartTime, purchaseOrderEndTime, allowClubDiscounts, allowCouponDiscounts,
                    allowAssociatePayments, unitOfMeasureKind, itemPriceType, cancellationPolicy, returnPolicy, stylePath,
                    createdBy);
        }
            
        return item;
    }

    private String getItemName(final ExecutionErrorAccumulator eea, final ItemCategory itemCategory) {
        String itemName = null;
        var itemSequence = itemCategory.getLastDetail().getItemSequence();
        
        if(itemSequence == null) {
            itemSequence = SequenceGeneratorLogic.getInstance().getDefaultSequence(eea, SequenceTypes.ITEM.name());
        }
        
        if(!hasExecutionErrors(eea)) {
            itemName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(eea, itemSequence);
        } else {
            handleExecutionError(MissingDefaultSequenceException.class, eea, ExecutionErrors.MissingDefaultSequence.name(),
                    SequenceTypes.ITEM.name());
        }
        
        return itemName;
    }

    public void setItemStatus(final Session session, final ExecutionErrorAccumulator eea, final Item item, final String itemStatusChoice, final PartyPK modifiedBy) {
        var workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, ItemStatusConstants.Workflow_ITEM_STATUS);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(item.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        var workflowDestination = itemStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), itemStatusChoice);

        if(workflowDestination != null || itemStatusChoice == null) {
            var workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            var currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            var map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            var handled = false;
            Long triggerTime = null;
            
            if(currentWorkflowStepName.equals(ItemStatusConstants.WorkflowStep_ITEM_STATUS_ACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, ItemStatusConstants.Workflow_ITEM_STATUS, ItemStatusConstants.WorkflowStep_ITEM_STATUS_DISCONTINUED)) {
                    // TODO
                    handled = true;
                } else if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, ItemStatusConstants.Workflow_ITEM_STATUS, ItemStatusConstants.WorkflowStep_ITEM_STATUS_CANCEL_IF_NOT_IN_STOCK)) {
                    // TODO
                    handled = true;
                }
            } else if(currentWorkflowStepName.equals(ItemStatusConstants.WorkflowStep_ITEM_STATUS_DISCONTINUED)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, ItemStatusConstants.Workflow_ITEM_STATUS, ItemStatusConstants.WorkflowStep_ITEM_STATUS_ACTIVE)) {
                    // TODO
                    handled = true;
                } else if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, ItemStatusConstants.Workflow_ITEM_STATUS, ItemStatusConstants.WorkflowStep_ITEM_STATUS_CANCEL_IF_NOT_IN_STOCK)) {
                    // TODO
                    handled = true;
                }
            } else if(currentWorkflowStepName.equals(ItemStatusConstants.WorkflowStep_ITEM_STATUS_CANCEL_IF_NOT_IN_STOCK)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, ItemStatusConstants.Workflow_ITEM_STATUS, ItemStatusConstants.WorkflowStep_ITEM_STATUS_ACTIVE)) {
                    // TODO
                    handled = true;
                } else if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, ItemStatusConstants.Workflow_ITEM_STATUS, ItemStatusConstants.WorkflowStep_ITEM_STATUS_DISCONTINUED)) {
                    // TODO
                    handled = true;
                }
            }
            
            if(eea == null || !eea.hasExecutionErrors()) {
                if(handled) {
                    workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
                } else {
                    // TODO: An error of some sort.
                }
            }
        } else {
            handleExecutionError(UnknownItemStatusChoiceException.class, eea, ExecutionErrors.UnknownItemStatusChoice.name(), itemStatusChoice);
        }
    }
    
}
