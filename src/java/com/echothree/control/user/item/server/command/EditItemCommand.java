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

import com.echothree.control.user.item.common.edit.ItemEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.result.EditItemResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.item.common.ItemTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditItemCommand
        extends BaseAbstractEditCommand<ItemSpec, ItemEdit, EditItemResult, Item, Item> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemCategory.name(), SecurityRoles.Edit.name())
                        )))
                )));

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
    public EditItemCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var itemControl = Session.getModelController(ItemControl.class);
        Item item;
        var itemName = spec.getItemName();

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
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItem(itemControl.getItemTransfer(getUserVisit(), item));
    }

    @Override
    public void doLock(ItemEdit edit, Item item) {
        var dateUtils = DateUtils.getInstance();
        var itemDetail = item.getLastDetail();
        var userVisit = getUserVisit();
        var preferredDateTimeFormat = getPreferredDateTimeFormat();

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
        var itemControl = Session.getModelController(ItemControl.class);
        var itemName = edit.getItemName();
        var duplicateItem = itemControl.getItemByName(itemName);

        if(duplicateItem == null || item.equals(duplicateItem)) {
            var itemCategoryName = edit.getItemCategoryName();
            var itemTypeName = item.getLastDetail().getItemType().getItemTypeName();

            itemCategory = itemControl.getItemCategoryByName(itemCategoryName);

            if(itemCategory != null) {
                var itemAccountingCategoryName = edit.getItemAccountingCategoryName();

                isKit = itemTypeName.equals(ItemTypes.KIT.name());

                if(isKit) {
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
                    var itemPurchasingCategoryName = edit.getItemPurchasingCategoryName();

                    if(isKit) {
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
                        var cancellationPolicyName = edit.getCancellationPolicyName();

                        if(cancellationPolicyName != null) {
                            var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
                            var cancellationKind = cancellationPolicyControl.getCancellationKindByName(CancellationKinds.CUSTOMER_CANCELLATION.name());

                            cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);
                        }

                        if(cancellationPolicyName == null || cancellationPolicy != null) {
                            var returnPolicyName = edit.getReturnPolicyName();

                            if(returnPolicyName != null) {
                                var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
                                var returnKind = returnPolicyControl.getReturnKindByName(ReturnKinds.CUSTOMER_RETURN.name());

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
        var itemControl = Session.getModelController(ItemControl.class);
        var partyPK = getPartyPK();
        var itemDetailValue = itemControl.getItemDetailValueForUpdate(item);
        var shippingChargeExempt = Boolean.valueOf(edit.getShippingChargeExempt());
        var strShippingStartTime = edit.getShippingStartTime();
        var shippingStartTime = strShippingStartTime == null? session.START_TIME_LONG: Long.valueOf(strShippingStartTime);
        var strShippingEndTime = edit.getShippingEndTime();
        var shippingEndTime = strShippingEndTime == null? null: Long.valueOf(strShippingEndTime);
        var strSalesOrderStartTime = edit.getSalesOrderStartTime();
        var salesOrderStartTime = strSalesOrderStartTime == null? session.START_TIME_LONG: Long.valueOf(strSalesOrderStartTime);
        var strSalesOrderEndTime = edit.getSalesOrderEndTime();
        var salesOrderEndTime = strSalesOrderEndTime == null? null: Long.valueOf(strSalesOrderEndTime);
        var strPurchaseOrderStartTime = edit.getPurchaseOrderStartTime();
        var purchaseOrderStartTime = isKit? null: strPurchaseOrderStartTime == null? session.START_TIME_LONG: Long.valueOf(strPurchaseOrderStartTime);
        var strPurchaseOrderEndTime = edit.getPurchaseOrderEndTime();
        var purchaseOrderEndTime = isKit? null: strPurchaseOrderEndTime == null? null: Long.valueOf(strPurchaseOrderEndTime);
        var allowClubDiscounts = Boolean.valueOf(edit.getAllowClubDiscounts());
        var allowCouponDiscounts = Boolean.valueOf(edit.getAllowCouponDiscounts());
        var allowAssociatePayments = Boolean.valueOf(edit.getAllowAssociatePayments());

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
