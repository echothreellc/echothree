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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.CreateItemForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.item.common.ItemConstants;
import com.echothree.model.control.item.common.workflow.ItemStatusConstants;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowSecurityLogic;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.item.server.entity.ItemInventoryType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateItemCommand
        extends BaseSimpleCommand<CreateItemForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemCategory.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemAccountingCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemPurchasingCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemDeliveryTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemInventoryTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventorySerialized", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("ShippingChargeExempt", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("ShippingStartTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("ShippingEndTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("SalesOrderStartTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("SalesOrderEndTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("PurchaseOrderStartTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("PurchaseOrderEndTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("AllowClubDiscounts", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("AllowCouponDiscounts", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("AllowAssociatePayments", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("ItemStatus", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemPriceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateItemCommand */
    public CreateItemCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = ItemResultFactory.getCreateItemResult();
        var itemControl = Session.getModelController(ItemControl.class);
        var itemName = form.getItemName();
        var item = itemName == null ? null : itemControl.getItemByNameThenAlias(itemName);
        
        if(item == null) {
            var itemTypeName = form.getItemTypeName();
            var itemType = itemControl.getItemTypeByName(itemTypeName);
            
            if(itemType != null) {
                var itemUseTypeName = form.getItemUseTypeName();
                var itemUseType = itemControl.getItemUseTypeByName(itemUseTypeName);
                    
                itemTypeName = itemType.getItemTypeName();
                
                if(itemUseType != null) {
                    var itemCategoryName = form.getItemCategoryName();
                    var itemCategory = itemCategoryName == null ? null : itemControl.getItemCategoryByName(itemCategoryName);
                    
                    if(itemCategoryName == null || itemCategory != null) {
                        var isKitOrStyle = itemTypeName.equals(ItemConstants.ItemType_KIT)
                                || itemTypeName.equals(ItemConstants.ItemType_STYLE);
                        var itemAccountingCategoryName = form.getItemAccountingCategoryName();
                        ItemAccountingCategory itemAccountingCategory = null;
                        
                        if(isKitOrStyle) {
                            if(itemAccountingCategoryName != null) {
                                addExecutionError(ExecutionErrors.NotPermittedItemAccountingCategory.name(), itemAccountingCategoryName);
                            }
                        } else {
                            var accountingControl = Session.getModelController(AccountingControl.class);
                            
                            itemAccountingCategory = itemAccountingCategoryName == null? null: accountingControl.getItemAccountingCategoryByName(itemAccountingCategoryName);
                            
                            if(itemAccountingCategoryName == null) {
                                addExecutionError(ExecutionErrors.MissingItemAccountingCategoryName.name());
                            } else if(itemAccountingCategory == null) {
                                addExecutionError(ExecutionErrors.UnknownItemAccountingCategoryName.name(), itemAccountingCategoryName);
                            }
                        }
                        
                        if(!hasExecutionErrors()) {
                            var itemPurchasingCategoryName = form.getItemPurchasingCategoryName();
                            ItemPurchasingCategory itemPurchasingCategory = null;
                            
                            if(isKitOrStyle) {
                                if(itemPurchasingCategoryName != null) {
                                    addExecutionError(ExecutionErrors.NotPermittedItemPurchasingCategory.name(), itemPurchasingCategoryName);
                                }
                            } else {
                                var vendorControl = Session.getModelController(VendorControl.class);
                                
                                itemPurchasingCategory = itemPurchasingCategoryName == null? null: vendorControl.getItemPurchasingCategoryByName(itemPurchasingCategoryName);
                                
                                if(itemPurchasingCategoryName == null) {
                                    addExecutionError(ExecutionErrors.MissingItemPurchasingCategoryName.name());
                                } else if(itemPurchasingCategory == null) {
                                    addExecutionError(ExecutionErrors.UnknownItemPurchasingCategoryName.name(), itemPurchasingCategoryName);
                                }
                            }
                            
                            if(!hasExecutionErrors()) {
                                var partyControl = Session.getModelController(PartyControl.class);
                                var companyName = form.getCompanyName();
                                var partyCompany = partyControl.getPartyCompanyByName(companyName);
                                
                                if(partyCompany != null) {
                                    var itemDeliveryTypeName = form.getItemDeliveryTypeName();
                                    ItemDeliveryType itemDeliveryType = null;
                                    
                                    if(isKitOrStyle) {
                                        if(itemDeliveryTypeName != null) {
                                            addExecutionError(ExecutionErrors.NotPermittedItemDeliveryType.name(), itemDeliveryTypeName);
                                        }
                                    } else {
                                        itemDeliveryType = itemDeliveryTypeName == null? null: itemControl.getItemDeliveryTypeByName(itemDeliveryTypeName);
                                        
                                        if(itemDeliveryTypeName == null) {
                                            addExecutionError(ExecutionErrors.MissingItemDeliveryTypeName.name());
                                        } else if(itemDeliveryType == null) {
                                            addExecutionError(ExecutionErrors.UnknownItemDeliveryTypeName.name(), itemDeliveryTypeName);
                                        }
                                    }
                                    
                                    if(!hasExecutionErrors()) {
                                        var itemInventoryTypeName = form.getItemInventoryTypeName();
                                        ItemInventoryType itemInventoryType = null;

                                        if(isKitOrStyle) {
                                            if(itemInventoryTypeName != null) {
                                                addExecutionError(ExecutionErrors.NotPermittedItemInventoryType.name(), itemInventoryTypeName);
                                            }
                                        } else {
                                            itemInventoryType = itemInventoryTypeName == null? null: itemControl.getItemInventoryTypeByName(itemInventoryTypeName);

                                            if(itemInventoryTypeName == null) {
                                                addExecutionError(ExecutionErrors.MissingItemInventoryTypeName.name());
                                            } else if(itemInventoryType == null) {
                                                addExecutionError(ExecutionErrors.UnknownItemInventoryTypeName.name(), itemInventoryTypeName);
                                            }
                                        }

                                        if(!hasExecutionErrors()) {
                                            var uomControl = Session.getModelController(UomControl.class);
                                            var unitOfMeasureKindName = form.getUnitOfMeasureKindName();
                                            var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

                                            if(unitOfMeasureKind != null) {
                                                var itemPriceTypeName = form.getItemPriceTypeName();
                                                var itemPriceType = itemControl.getItemPriceTypeByName(itemPriceTypeName);

                                                if(itemPriceType != null) {
                                                    var cancellationPolicyName = form.getCancellationPolicyName();
                                                    CancellationPolicy cancellationPolicy = null;

                                                    if(cancellationPolicyName != null) {
                                                        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
                                                        var cancellationKind = cancellationPolicyControl.getCancellationKindByName(CancellationKinds.CUSTOMER_CANCELLATION.name());

                                                        cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);
                                                    }

                                                    if(cancellationPolicyName == null || cancellationPolicy != null) {
                                                        var returnPolicyName = form.getReturnPolicyName();
                                                        ReturnPolicy returnPolicy = null;

                                                        if(returnPolicyName != null) {
                                                            var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
                                                            var returnKind = returnPolicyControl.getReturnKindByName(ReturnKinds.CUSTOMER_RETURN.name());

                                                            returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                                                        }

                                                        if(returnPolicyName == null || returnPolicy != null) {
                                                            var rawInventorySerialized = form.getInventorySerialized();
                                                            var inventorySerialized = rawInventorySerialized == null? null: Boolean.valueOf(rawInventorySerialized);

                                                            if(isKitOrStyle ? inventorySerialized == null : inventorySerialized != null) {
                                                                var workflowControl = Session.getModelController(WorkflowControl.class);
                                                                var itemStatusChoice = form.getItemStatus();
                                                                var itemStatus = workflowControl.getWorkflowEntranceUsingNames(this, ItemStatusConstants.Workflow_ITEM_STATUS,
                                                                        itemStatusChoice);
                                                                var createdBy = getPartyPK();

                                                                if(!hasExecutionErrors() && WorkflowSecurityLogic.getInstance().checkAddEntityToWorkflow(this, itemStatus, createdBy)) {
                                                                    var shippingChargeExempt = Boolean.valueOf(form.getShippingChargeExempt());
                                                                    var strShippingStartTime = form.getShippingStartTime();
                                                                    var shippingStartTime = strShippingStartTime == null ? session.START_TIME_LONG : Long.valueOf(strShippingStartTime);
                                                                    var strShippingEndTime = form.getShippingEndTime();
                                                                    var shippingEndTime = strShippingEndTime == null ? null : Long.valueOf(strShippingEndTime);
                                                                    var strSalesOrderStartTime = form.getSalesOrderStartTime();
                                                                    var salesOrderStartTime = strSalesOrderStartTime == null ? session.START_TIME_LONG : Long.valueOf(strSalesOrderStartTime);
                                                                    var strSalesOrderEndTime = form.getSalesOrderEndTime();
                                                                    var salesOrderEndTime = strSalesOrderEndTime == null ? null : Long.valueOf(strSalesOrderEndTime);
                                                                    var strPurchaseOrderStartTime = form.getPurchaseOrderStartTime();
                                                                    var purchaseOrderStartTime = isKitOrStyle ? null : strPurchaseOrderStartTime == null ? session.START_TIME_LONG : Long.valueOf(strPurchaseOrderStartTime);
                                                                    var strPurchaseOrderEndTime = form.getPurchaseOrderEndTime();
                                                                    var purchaseOrderEndTime = isKitOrStyle ? null : strPurchaseOrderEndTime == null ? null : Long.valueOf(strPurchaseOrderEndTime);
                                                                    var allowClubDiscounts = Boolean.valueOf(form.getAllowClubDiscounts());
                                                                    var allowCouponDiscounts = Boolean.valueOf(form.getAllowCouponDiscounts());
                                                                    var allowAssociatePayments = Boolean.valueOf(form.getAllowAssociatePayments());

                                                                    // TODO: SerialNumberSequenceName is currently ignored.
                                                                    item = ItemLogic.getInstance().createItem(this, itemName, itemType, itemUseType, itemCategory,
                                                                            itemAccountingCategory, itemPurchasingCategory, partyCompany.getParty(),
                                                                            itemDeliveryType, itemInventoryType, inventorySerialized, null, shippingChargeExempt,
                                                                            shippingStartTime, shippingEndTime, salesOrderStartTime, salesOrderEndTime,
                                                                            purchaseOrderStartTime, purchaseOrderEndTime, allowClubDiscounts, allowCouponDiscounts,
                                                                            allowAssociatePayments, unitOfMeasureKind, itemPriceType, cancellationPolicy,
                                                                            returnPolicy, null, createdBy);

                                                                    if(!hasExecutionErrors()) {
                                                                        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                                                                        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(item.getPrimaryKey());

                                                                        workflowControl.addEntityToWorkflow(itemStatus, entityInstance, null, null, createdBy);
                                                                    }
                                                                }
                                                            } else {
                                                                if(isKitOrStyle) {
                                                                    if(inventorySerialized != null) {
                                                                        addExecutionError(ExecutionErrors.NotPermittedInventorySerialized.name());
                                                                    }
                                                                } else {
                                                                    if(inventorySerialized == null) {
                                                                        addExecutionError(ExecutionErrors.MissingInventorySerialized.name());
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnPolicyName);
                                                        }
                                                    } else {
                                                        addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationPolicyName);
                                                    }
                                                } else {
                                                    addExecutionError(ExecutionErrors.UnknownItemPriceTypeName.name(), itemPriceTypeName);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
                                            }
                                        }
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
                                }
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownItemCategoryName.name(), itemCategoryName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemUseTypeName.name(), itemUseTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemTypeName.name(), itemTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateItemName.name(), itemName);
        }
        
        if(item != null) {
            result.setItemName(item.getLastDetail().getItemName());
            result.setEntityRef(item.getPrimaryKey().getEntityRef());
        }
        
        return result;
    }
    
}
