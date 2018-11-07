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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.CreateItemForm;
import com.echothree.control.user.item.common.result.CreateItemResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.item.common.ItemConstants;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.returnpolicy.common.ReturnPolicyConstants;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.control.item.common.workflow.ItemStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowSecurityLogic;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.item.server.entity.ItemInventoryType;
import com.echothree.model.data.item.server.entity.ItemPriceType;
import com.echothree.model.data.item.server.entity.ItemType;
import com.echothree.model.data.item.server.entity.ItemUseType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateItemCommand
        extends BaseSimpleCommand<CreateItemForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
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
    public CreateItemCommand(UserVisitPK userVisitPK, CreateItemForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CreateItemResult result = ItemResultFactory.getCreateItemResult();
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        String itemName = form.getItemName();
        Item item = itemName == null ? null : itemControl.getItemByNameThenAlias(itemName);
        
        if(item == null) {
            String itemTypeName = form.getItemTypeName();
            ItemType itemType = itemControl.getItemTypeByName(itemTypeName);
            
            if(itemType != null) {
                String itemUseTypeName = form.getItemUseTypeName();
                ItemUseType itemUseType = itemControl.getItemUseTypeByName(itemUseTypeName);
                    
                itemTypeName = itemType.getItemTypeName();
                
                if(itemUseType != null) {
                    String itemCategoryName = form.getItemCategoryName();
                    ItemCategory itemCategory = itemCategoryName == null ? null : itemControl.getItemCategoryByName(itemCategoryName);
                    
                    if(itemCategoryName == null || itemCategory != null) {
                        boolean isKitOrStyle = itemTypeName.equals(ItemConstants.ItemType_KIT)
                                || itemTypeName.equals(ItemConstants.ItemType_STYLE);
                        String itemAccountingCategoryName = form.getItemAccountingCategoryName();
                        ItemAccountingCategory itemAccountingCategory = null;
                        
                        if(isKitOrStyle) {
                            if(itemAccountingCategoryName != null) {
                                addExecutionError(ExecutionErrors.NotPermittedItemAccountingCategory.name(), itemAccountingCategoryName);
                            }
                        } else {
                            AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                            
                            itemAccountingCategory = itemAccountingCategoryName == null? null: accountingControl.getItemAccountingCategoryByName(itemAccountingCategoryName);
                            
                            if(itemAccountingCategoryName == null) {
                                addExecutionError(ExecutionErrors.MissingItemAccountingCategoryName.name());
                            } else if(itemAccountingCategory == null) {
                                addExecutionError(ExecutionErrors.UnknownItemAccountingCategoryName.name(), itemAccountingCategoryName);
                            }
                        }
                        
                        if(!hasExecutionErrors()) {
                            String itemPurchasingCategoryName = form.getItemPurchasingCategoryName();
                            ItemPurchasingCategory itemPurchasingCategory = null;
                            
                            if(isKitOrStyle) {
                                if(itemPurchasingCategoryName != null) {
                                    addExecutionError(ExecutionErrors.NotPermittedItemPurchasingCategory.name(), itemPurchasingCategoryName);
                                }
                            } else {
                                VendorControl vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
                                
                                itemPurchasingCategory = itemPurchasingCategoryName == null? null: vendorControl.getItemPurchasingCategoryByName(itemPurchasingCategoryName);
                                
                                if(itemPurchasingCategoryName == null) {
                                    addExecutionError(ExecutionErrors.MissingItemPurchasingCategoryName.name());
                                } else if(itemPurchasingCategory == null) {
                                    addExecutionError(ExecutionErrors.UnknownItemPurchasingCategoryName.name(), itemPurchasingCategoryName);
                                }
                            }
                            
                            if(!hasExecutionErrors()) {
                                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                                String companyName = form.getCompanyName();
                                PartyCompany partyCompany = partyControl.getPartyCompanyByName(companyName);
                                
                                if(partyCompany != null) {
                                    String itemDeliveryTypeName = form.getItemDeliveryTypeName();
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
                                        String itemInventoryTypeName = form.getItemInventoryTypeName();
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
                                            UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
                                            String unitOfMeasureKindName = form.getUnitOfMeasureKindName();
                                            UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

                                            if(unitOfMeasureKind != null) {
                                                String itemPriceTypeName = form.getItemPriceTypeName();
                                                ItemPriceType itemPriceType = itemControl.getItemPriceTypeByName(itemPriceTypeName);

                                                if(itemPriceType != null) {
                                                    String cancellationPolicyName = form.getCancellationPolicyName();
                                                    CancellationPolicy cancellationPolicy = null;

                                                    if(cancellationPolicyName != null) {
                                                        CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
                                                        CancellationKind cancellationKind = cancellationPolicyControl.getCancellationKindByName(CancellationPolicyConstants.CancellationKind_CUSTOMER_CANCELLATION);

                                                        cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);
                                                    }

                                                    if(cancellationPolicyName == null || cancellationPolicy != null) {
                                                        String returnPolicyName = form.getReturnPolicyName();
                                                        ReturnPolicy returnPolicy = null;

                                                        if(returnPolicyName != null) {
                                                            ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
                                                            ReturnKind returnKind = returnPolicyControl.getReturnKindByName(ReturnPolicyConstants.ReturnKind_CUSTOMER_RETURN);

                                                            returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                                                        }

                                                        if(returnPolicyName == null || returnPolicy != null) {
                                                            String rawInventorySerialized = form.getInventorySerialized();
                                                            Boolean inventorySerialized = rawInventorySerialized == null? null: Boolean.valueOf(rawInventorySerialized);

                                                            if(isKitOrStyle ? inventorySerialized == null : inventorySerialized != null) {
                                                                WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                                                                String itemStatusChoice = form.getItemStatus();
                                                                WorkflowEntrance itemStatus = workflowControl.getWorkflowEntranceUsingNames(this, ItemStatusConstants.Workflow_ITEM_STATUS,
                                                                        itemStatusChoice);
                                                                PartyPK createdBy = getPartyPK();

                                                                if(!hasExecutionErrors() && WorkflowSecurityLogic.getInstance().checkAddEntityToWorkflow(this, itemStatus, createdBy)) {
                                                                    CoreControl coreControl = getCoreControl();
                                                                    CustomerControl customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
                                                                    Boolean shippingChargeExempt = Boolean.valueOf(form.getShippingChargeExempt());
                                                                    String strShippingStartTime = form.getShippingStartTime();
                                                                    Long shippingStartTime = strShippingStartTime == null ? session.START_TIME_LONG : Long.valueOf(strShippingStartTime);
                                                                    String strShippingEndTime = form.getShippingEndTime();
                                                                    Long shippingEndTime = strShippingEndTime == null ? null : Long.valueOf(strShippingEndTime);
                                                                    String strSalesOrderStartTime = form.getSalesOrderStartTime();
                                                                    Long salesOrderStartTime = strSalesOrderStartTime == null ? session.START_TIME_LONG : Long.valueOf(strSalesOrderStartTime);
                                                                    String strSalesOrderEndTime = form.getSalesOrderEndTime();
                                                                    Long salesOrderEndTime = strSalesOrderEndTime == null ? null : Long.valueOf(strSalesOrderEndTime);
                                                                    String strPurchaseOrderStartTime = form.getPurchaseOrderStartTime();
                                                                    Long purchaseOrderStartTime = isKitOrStyle ? null : strPurchaseOrderStartTime == null ? session.START_TIME_LONG : Long.valueOf(strPurchaseOrderStartTime);
                                                                    String strPurchaseOrderEndTime = form.getPurchaseOrderEndTime();
                                                                    Long purchaseOrderEndTime = isKitOrStyle ? null : strPurchaseOrderEndTime == null ? null : Long.valueOf(strPurchaseOrderEndTime);
                                                                    Boolean allowClubDiscounts = Boolean.valueOf(form.getAllowClubDiscounts());
                                                                    Boolean allowCouponDiscounts = Boolean.valueOf(form.getAllowCouponDiscounts());
                                                                    Boolean allowAssociatePayments = Boolean.valueOf(form.getAllowAssociatePayments());

                                                                    // TODO: SerialNumberSequenceName is currently ignored.
                                                                    item = ItemLogic.getInstance().createItem(this, itemName, itemType, itemUseType, itemCategory,
                                                                            itemAccountingCategory, itemPurchasingCategory, partyCompany.getParty(),
                                                                            itemDeliveryType, itemInventoryType, inventorySerialized, null, shippingChargeExempt,
                                                                            shippingStartTime, shippingEndTime, salesOrderStartTime, salesOrderEndTime,
                                                                            purchaseOrderStartTime, purchaseOrderEndTime, allowClubDiscounts, allowCouponDiscounts,
                                                                            allowAssociatePayments, unitOfMeasureKind, itemPriceType, cancellationPolicy,
                                                                            returnPolicy, null, createdBy);

                                                                    if(!hasExecutionErrors()) {
                                                                        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(item.getPrimaryKey());

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
