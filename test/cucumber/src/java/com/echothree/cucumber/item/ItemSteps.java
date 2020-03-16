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

package com.echothree.cucumber.item;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.CreateItemResult;
import com.echothree.control.user.item.common.result.EditItemResult;
import com.echothree.cucumber.BasePersona;
import com.echothree.cucumber.EmployeePersonas;
import com.echothree.cucumber.LastCommandResult;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import javax.naming.NamingException;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemSteps implements En {

    public ItemSteps() {
        When("^the employee ([^\"]*) begins entering a new item",
                (String persona) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.itemEdit).isNull();

                    employeePersona.itemEdit = ItemUtil.getHome().getItemEdit();
                });

//        When("^the employee ([^\"]*) sets the item's type to ([^\"]*)",
//                (String persona, String itemTypeName) -> {
//                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);
//
//                    assertThat(employeePersona.itemEdit).isNotNull();
//
//                    employeePersona.itemEdit.setItemTypeName(itemTypeName);
//                });

        When("^the employee ([^\"]*) adds the new item",
                (String persona) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.itemEdit).isNotNull();

                    var itemService = ItemUtil.getHome();
                    var createItemForm = itemService.getCreateItemForm();

                    createItemForm.set(employeePersona.itemEdit.get());

                    var commandResult = itemService.createItem(employeePersona.userVisitPK, createItemForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateItemResult)commandResult.getExecutionResult().getResult();

                    employeePersona.lastItemName = commandResult.getHasErrors() ? null : result.getItemName();
                    employeePersona.itemEdit = null;
                });

        When("^the employee ([^\"]*) sets the status of the last item added to ([^\"]*)$",
                (String persona, String itemStatusChoice) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    setItemStatus(employeePersona, employeePersona.lastItemName, itemStatusChoice);
                });

        When("^the employee ([^\"]*) begins editing the last item added",
                (String persona) -> {
                    var spec = ItemUtil.getHome().getItemSpec();
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.itemEdit).isNull();

                    spec.setItemName(employeePersona.lastItemName);

                    var commandForm = ItemUtil.getHome().getEditItemForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ItemUtil.getHome().editItem(employeePersona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemResult)executionResult.getResult();

                    employeePersona.itemEdit = result.getEdit();
                });

        When("^the employee ([^\"]*) finishes editing the item",
                (String persona) -> {
                    var spec = ItemUtil.getHome().getItemSpec();
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);
                    var edit = employeePersona.itemEdit;

                    assertThat(edit).isNotNull();

                    spec.setItemName(employeePersona.lastItemName);

                    var commandForm = ItemUtil.getHome().getEditItemForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ItemUtil.getHome().editItem(employeePersona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;
                });
    }

    private void createItem(BasePersona persona, String itemName, String itemTypeName, String itemUseTypeName, String itemCategoryName,
            String itemAccountingCategoryName, String itemPurchasingCategoryName, String companyName, String itemDeliveryTypeName,
            String itemInventoryTypeName, String inventorySerialized, String shippingChargeExempt, String shippingStartTime,
            String shippingEndTime, String salesOrderStartTime, String salesOrderEndTime, String purchaseOrderStartTime,
            String purchaseOrderEndTime, String allowClubDiscounts, String allowCouponDiscounts, String allowAssociatePayments,
            String itemStatus, String unitOfMeasureKindName, String itemPriceTypeName, String cancellationPolicyName, String returnPolicyName)
            throws NamingException {
        var itemService = ItemUtil.getHome();
        var createItemForm = itemService.getCreateItemForm();

        createItemForm.setItemName(itemName);
        createItemForm.setItemTypeName(itemTypeName);
        createItemForm.setItemUseTypeName(itemUseTypeName);
        createItemForm.setItemCategoryName(itemCategoryName);
        createItemForm.setItemAccountingCategoryName(itemAccountingCategoryName);
        createItemForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
        createItemForm.setCompanyName(companyName);
        createItemForm.setItemDeliveryTypeName(itemDeliveryTypeName);
        createItemForm.setItemInventoryTypeName(itemInventoryTypeName);
        createItemForm.setInventorySerialized(Boolean.valueOf(inventorySerialized.equals("has")).toString());
        createItemForm.setShippingChargeExempt(Boolean.valueOf(shippingChargeExempt.equals("is")).toString());
        createItemForm.setShippingStartTime(shippingStartTime);
        createItemForm.setShippingEndTime(shippingEndTime);
        createItemForm.setSalesOrderStartTime(salesOrderStartTime);
        createItemForm.setSalesOrderEndTime(salesOrderEndTime);
        createItemForm.setPurchaseOrderStartTime(purchaseOrderStartTime);
        createItemForm.setPurchaseOrderEndTime(purchaseOrderEndTime);
        createItemForm.setAllowClubDiscounts(Boolean.valueOf(allowClubDiscounts.equals("does")).toString());
        createItemForm.setAllowCouponDiscounts(Boolean.valueOf(allowCouponDiscounts.equals("does")).toString());
        createItemForm.setAllowAssociatePayments(Boolean.valueOf(allowAssociatePayments.equals("does")).toString());
        createItemForm.setItemStatus(itemStatus);
        createItemForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
        createItemForm.setItemPriceTypeName(itemPriceTypeName);
        createItemForm.setCancellationPolicyName(cancellationPolicyName);
        createItemForm.setReturnPolicyName(returnPolicyName);

        var commandResult = itemService.createItem(persona.userVisitPK, createItemForm);

        LastCommandResult.commandResult = commandResult;
        var result = (CreateItemResult)commandResult.getExecutionResult().getResult();

        persona.lastItemName = commandResult.getHasErrors() ? null : result.getItemName();
    }

    private void setItemStatus(BasePersona persona, String itemName, String itemStatusChoice)
            throws NamingException {
        var itemService = ItemUtil.getHome();
        var setItemStatusForm = itemService.getSetItemStatusForm();

        setItemStatusForm.setItemName(itemName);
        setItemStatusForm.setItemStatusChoice(itemStatusChoice);

        var commandResult = itemService.setItemStatus(persona.userVisitPK, setItemStatusForm);

        LastCommandResult.commandResult = commandResult;
    }

}
