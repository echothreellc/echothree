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

import com.echothree.control.user.item.common.edit.ItemEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditItemForm;
import com.echothree.control.user.item.common.result.EditItemResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.item.common.ItemConstants;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.returnpolicy.common.ReturnPolicyConstants;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.item.server.value.ItemDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditItemCommand
        extends BaseAbstractEditCommand<ItemSpec, ItemEdit, EditItemResult, Item, Item> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemAccountingCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemPurchasingCategoryName", FieldType.ENTITY_NAME, false, null, null),
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
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditItemCommand */
    public EditItemCommand(UserVisitPK userVisitPK, EditItemForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemResult getResult() {
        return ItemResultFactory.getEditItemResult();
    }

    @Override
    public ItemEdit getEdit() {
        return ItemEditFactory.getItemEdit();
    }

    @Override
    public Item getEntity(EditItemResult result) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        Item item = null;
        String itemName = spec.getItemName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            item = itemControl.getItemByName(itemName);
        } else { // EditMode.UPDATE
            item = itemControl.getItemByNameForUpdate(itemName);
        }

        if(item != null) {
            result.setItem(itemControl.getItemTransfer(getUserVisit(), item));
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }

        return item;
    }

    @Override
    public Item getLockEntity(Item item) {
        return item;
    }

    @Override
    public void fillInResult(EditItemResult result, Item item) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);

        result.setItem(itemControl.getItemTransfer(getUserVisit(), item));
    }

    @Override
    public void doLock(ItemEdit edit, Item item) {
        DateUtils dateUtils = DateUtils.getInstance();
        ItemDetail itemDetail = item.getLastDetail();
        UserVisit userVisit = getUserVisit();
        DateTimeFormat preferredDateTimeFormat = getPreferredDateTimeFormat();

        itemAccountingCategory = itemDetail.getItemAccountingCategory();
        itemPurchasingCategory = itemDetail.getItemPurchasingCategory();
        cancellationPolicy = itemDetail.getCancellationPolicy();
        returnPolicy = itemDetail.getReturnPolicy();

        edit.setItemName(itemDetail.getItemName());
        edit.setItemName(edit.getItemName());
        edit.setItemCategoryName(itemDetail.getItemCategory().getLastDetail().getItemCategoryName());
        edit.setItemAccountingCategoryName(itemAccountingCategory == null? null: itemAccountingCategory.getLastDetail().getItemAccountingCategoryName());
        edit.setItemPurchasingCategoryName(itemPurchasingCategory == null? null: itemPurchasingCategory.getLastDetail().getItemPurchasingCategoryName());
        edit.setShippingChargeExempt(itemDetail.getShippingChargeExempt().toString());
        edit.setShippingStartTime(dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, itemDetail.getShippingStartTime()));
        edit.setShippingEndTime(dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, itemDetail.getShippingEndTime()));
        edit.setSalesOrderStartTime(dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, itemDetail.getSalesOrderStartTime()));
        edit.setSalesOrderEndTime(dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, itemDetail.getSalesOrderEndTime()));
        edit.setPurchaseOrderStartTime(dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, itemDetail.getPurchaseOrderStartTime()));
        edit.setPurchaseOrderEndTime(dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, itemDetail.getPurchaseOrderEndTime()));
        edit.setAllowClubDiscounts(itemDetail.getAllowClubDiscounts().toString());
        edit.setAllowCouponDiscounts(itemDetail.getAllowCouponDiscounts().toString());
        edit.setAllowAssociatePayments(itemDetail.getAllowAssociatePayments().toString());
        edit.setCancellationPolicyName(cancellationPolicy == null? null: cancellationPolicy.getLastDetail().getCancellationPolicyName());
        edit.setReturnPolicyName(returnPolicy == null? null: returnPolicy.getLastDetail().getReturnPolicyName());
    }

    ItemCategory itemCategory;
    boolean isKit;
    ItemAccountingCategory itemAccountingCategory;
    ItemPurchasingCategory itemPurchasingCategory;
    CancellationPolicy cancellationPolicy;
    ReturnPolicy returnPolicy;

    @Override
    public void canUpdate(Item item) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        String itemName = edit.getItemName();
        Item duplicateItem = itemControl.getItemByName(itemName);

        if(duplicateItem == null || item.equals(duplicateItem)) {
            String itemCategoryName = edit.getItemCategoryName();
            String itemTypeName = item.getLastDetail().getItemType().getItemTypeName();

            itemCategory = itemControl.getItemCategoryByName(itemCategoryName);

            if(itemCategory != null) {
                String itemAccountingCategoryName = edit.getItemAccountingCategoryName();

                isKit = itemTypeName.equals(ItemConstants.ItemType_KIT);

                if(isKit) {
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
                    String itemPurchasingCategoryName = edit.getItemPurchasingCategoryName();

                    if(isKit) {
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
                        String cancellationPolicyName = edit.getCancellationPolicyName();

                        if(cancellationPolicyName != null) {
                            CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
                            CancellationKind cancellationKind = cancellationPolicyControl.getCancellationKindByName(CancellationPolicyConstants.CancellationKind_CUSTOMER_CANCELLATION);

                            cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);
                        }

                        if(cancellationPolicyName == null || cancellationPolicy != null) {
                            String returnPolicyName = edit.getReturnPolicyName();

                            if(returnPolicyName != null) {
                                ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
                                ReturnKind returnKind = returnPolicyControl.getReturnKindByName(ReturnPolicyConstants.ReturnKind_CUSTOMER_RETURN);

                                returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                            }

                            if(returnPolicyName != null && returnPolicy == null) {
                                addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnPolicyName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationPolicyName);
                        }
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemCategoryName.name(), itemCategoryName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateItemName.name(), itemName);
        }
    }

    @Override
    public void doUpdate(Item item) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        PartyPK partyPK = getPartyPK();
        ItemDetailValue itemDetailValue = itemControl.getItemDetailValueForUpdate(item);
        Boolean shippingChargeExempt = Boolean.valueOf(edit.getShippingChargeExempt());
        String strShippingStartTime = edit.getShippingStartTime();
        Long shippingStartTime = strShippingStartTime == null? session.START_TIME_LONG: Long.valueOf(strShippingStartTime);
        String strShippingEndTime = edit.getShippingEndTime();
        Long shippingEndTime = strShippingEndTime == null? null: Long.valueOf(strShippingEndTime);
        String strSalesOrderStartTime = edit.getSalesOrderStartTime();
        Long salesOrderStartTime = strSalesOrderStartTime == null? session.START_TIME_LONG: Long.valueOf(strSalesOrderStartTime);
        String strSalesOrderEndTime = edit.getSalesOrderEndTime();
        Long salesOrderEndTime = strSalesOrderEndTime == null? null: Long.valueOf(strSalesOrderEndTime);
        String strPurchaseOrderStartTime = edit.getPurchaseOrderStartTime();
        Long purchaseOrderStartTime = isKit? null: strPurchaseOrderStartTime == null? session.START_TIME_LONG: Long.valueOf(strPurchaseOrderStartTime);
        String strPurchaseOrderEndTime = edit.getPurchaseOrderEndTime();
        Long purchaseOrderEndTime = isKit? null: strPurchaseOrderEndTime == null? null: Long.valueOf(strPurchaseOrderEndTime);
        Boolean allowClubDiscounts = Boolean.valueOf(edit.getAllowClubDiscounts());
        Boolean allowCouponDiscounts = Boolean.valueOf(edit.getAllowCouponDiscounts());
        Boolean allowAssociatePayments = Boolean.valueOf(edit.getAllowAssociatePayments());

        itemDetailValue.setItemName(edit.getItemName());
        itemDetailValue.setItemCategoryPK(itemCategory.getPrimaryKey());
        itemDetailValue.setItemAccountingCategoryPK(itemAccountingCategory == null? null: itemAccountingCategory.getPrimaryKey());
        itemDetailValue.setItemPurchasingCategoryPK(itemPurchasingCategory == null? null: itemPurchasingCategory.getPrimaryKey());
        itemDetailValue.setShippingChargeExempt(shippingChargeExempt);
        itemDetailValue.setShippingStartTime(shippingStartTime);
        itemDetailValue.setShippingEndTime(shippingEndTime);
        itemDetailValue.setSalesOrderStartTime(salesOrderStartTime);
        itemDetailValue.setSalesOrderEndTime(salesOrderEndTime);
        itemDetailValue.setPurchaseOrderStartTime(purchaseOrderStartTime);
        itemDetailValue.setPurchaseOrderEndTime(purchaseOrderEndTime);
        itemDetailValue.setAllowClubDiscounts(allowClubDiscounts);
        itemDetailValue.setAllowCouponDiscounts(allowCouponDiscounts);
        itemDetailValue.setAllowAssociatePayments(allowAssociatePayments);
        itemDetailValue.setCancellationPolicyPK(cancellationPolicy == null? null: cancellationPolicy.getPrimaryKey());
        itemDetailValue.setReturnPolicyPK(returnPolicy == null? null: returnPolicy.getPrimaryKey());

        itemControl.updateItemFromValue(itemDetailValue, partyPK);
    }
    
}
