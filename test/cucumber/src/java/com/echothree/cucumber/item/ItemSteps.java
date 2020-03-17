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
        When("^the employee ([^\"]*) begins entering a new item$",
                (String persona) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNull();

                    employeePersona.createItemForm = ItemUtil.getHome().getCreateItemForm();
                });

        When("^the employee ([^\"]*) sets the item's type to ([^\"]*)$",
                (String persona, String itemTypeName) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setItemTypeName(itemTypeName);
                });

        When("^the employee ([^\"]*) sets the item's use type to ([^\"]*)$",
                (String persona, String itemUseTypeName) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setItemUseTypeName(itemUseTypeName);
                });

        When("^the employee ([^\"]*) sets the item's category to ([^\"]*)",
                (String persona, String itemCategoryName) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setItemCategoryName(itemCategoryName);
                });

        When("^the employee ([^\"]*) sets the item's accounting category to ([^\"]*)",
                (String persona, String itemAccountingCategoryName) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setItemAccountingCategoryName(itemAccountingCategoryName);
                });

        When("^the employee ([^\"]*) sets the item's purchasing category to ([^\"]*)",
                (String persona, String itemPurchasingCategoryName) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
                });

        When("^the employee ([^\"]*) sets the item's company to ([^\"]*)",
                (String persona, String companyName) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setCompanyName(companyName);
                });

        When("^the employee ([^\"]*) sets the item's delivery type to ([^\"]*)",
                (String persona, String itemDeliveryTypeName) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setItemDeliveryTypeName(itemDeliveryTypeName);
                });

        When("^the employee ([^\"]*) sets the item's inventory type to ([^\"]*)",
                (String persona, String itemInventoryTypeName) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setItemInventoryTypeName(itemInventoryTypeName);
                });

        When("^the employee ([^\"]*)'s item (has|does not have) serialized inventory",
                (String persona, String inventorySerialized) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setInventorySerialized(Boolean.valueOf(inventorySerialized.equals("has")).toString());
                });

        When("^the employee ([^\"]*)'s item (is|is not) exempt from shipping",
                (String persona, String shippingChargeExempt) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setShippingChargeExempt(Boolean.valueOf(shippingChargeExempt.equals("is")).toString());
                });

        When("^the employee ([^\"]*)'s item (does|does not) allow club discounts",
                (String persona, String allowClubDiscounts) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setAllowClubDiscounts(Boolean.valueOf(allowClubDiscounts.equals("does")).toString());
                });

        When("^the employee ([^\"]*)'s item (does|does not) allow coupon discounts",
                (String persona, String allowCouponDiscounts) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setAllowCouponDiscounts(Boolean.valueOf(allowCouponDiscounts.equals("does")).toString());
                });

        When("^the employee ([^\"]*)'s item (does|does not) allow associate payments",
                (String persona, String allowAssociatePayments) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setAllowAssociatePayments(Boolean.valueOf(allowAssociatePayments.equals("does")).toString());
                });

        When("^the employee ([^\"]*) sets the item's status to ([^\"]*)",
                (String persona, String itemStatus) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setItemStatus(itemStatus);
                });

        When("^the employee ([^\"]*) sets the item's unit of measure kind to ([^\"]*)",
                (String persona, String unitOfMeasureKindName) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
                });

        When("^the employee ([^\"]*) sets the item's price type to ([^\"]*)",
                (String persona, String itemPriceTypeName) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    employeePersona.createItemForm.setItemPriceTypeName(itemPriceTypeName);
                });

        When("^the employee ([^\"]*) adds the new item",
                (String persona) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona.createItemForm).isNotNull();

                    var itemService = ItemUtil.getHome();
                    var createItemForm = itemService.getCreateItemForm();

                    createItemForm.set(employeePersona.createItemForm.get());

                    var commandResult = itemService.createItem(employeePersona.userVisitPK, createItemForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateItemResult)commandResult.getExecutionResult().getResult();

                    employeePersona.lastItemName = commandResult.getHasErrors() ? null : result.getItemName();
                    employeePersona.createItemForm = null;
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

                    assertThat(employeePersona.createItemForm).isNull();

                    spec.setItemName(employeePersona.lastItemName);

                    var commandForm = ItemUtil.getHome().getEditItemForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ItemUtil.getHome().editItem(employeePersona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemResult)executionResult.getResult();

                    employeePersona.createItemForm = ItemUtil.getHome().getCreateItemForm();
                    employeePersona.createItemForm.set(result.getEdit().get());
                });

        When("^the employee ([^\"]*) finishes editing the item",
                (String persona) -> {
                    var spec = ItemUtil.getHome().getItemSpec();
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);
                    var edit = employeePersona.createItemForm;

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
