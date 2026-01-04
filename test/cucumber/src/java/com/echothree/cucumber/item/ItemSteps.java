// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.cucumber.util.persona.BasePersona;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import javax.naming.NamingException;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemSteps implements En {

    public ItemSteps() {
        When("^the user begins entering a new item$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNull();

                    persona.createItemForm = ItemUtil.getHome().getCreateItemForm();
                });

        When("^the user sets the item's type to ([^\"]*)$",
                (String itemTypeName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setItemTypeName(itemTypeName);
                });

        When("^the user sets the item's use type to ([^\"]*)$",
                (String itemUseTypeName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setItemUseTypeName(itemUseTypeName);
                });

        When("^the user sets the item's category to ([^\"]*)$",
                (String itemCategoryName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setItemCategoryName(itemCategoryName);
                });

        When("^the user sets the item's accounting category to ([^\"]*)$",
                (String itemAccountingCategoryName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setItemAccountingCategoryName(itemAccountingCategoryName);
                });

        When("^the user sets the item's purchasing category to ([^\"]*)$",
                (String itemPurchasingCategoryName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
                });

        When("^the user sets the item's company to ([^\"]*)$",
                (String companyName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setCompanyName(companyName);
                });

        When("^the user sets the item's delivery type to ([^\"]*)$",
                (String itemDeliveryTypeName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setItemDeliveryTypeName(itemDeliveryTypeName);
                });

        When("^the user sets the item's inventory type to ([^\"]*)$",
                (String itemInventoryTypeName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setItemInventoryTypeName(itemInventoryTypeName);
                });

        When("^the user's item (has|does not have) serialized inventory$",
                (String inventorySerialized) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setInventorySerialized(Boolean.valueOf(inventorySerialized.equals("has")).toString());
                });

        When("^the user's item (is|is not) exempt from shipping$",
                (String shippingChargeExempt) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setShippingChargeExempt(Boolean.valueOf(shippingChargeExempt.equals("is")).toString());
                });

        When("^the user's item (does|does not) allow club discounts$",
                (String allowClubDiscounts) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setAllowClubDiscounts(Boolean.valueOf(allowClubDiscounts.equals("does")).toString());
                });

        When("^the user's item (does|does not) allow coupon discounts$",
                (String allowCouponDiscounts) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setAllowCouponDiscounts(Boolean.valueOf(allowCouponDiscounts.equals("does")).toString());
                });

        When("^the user's item (does|does not) allow associate payments$",
                (String allowAssociatePayments) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setAllowAssociatePayments(Boolean.valueOf(allowAssociatePayments.equals("does")).toString());
                });

        When("^the user sets the item's status to ([^\"]*)$",
                (String itemStatus) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setItemStatus(itemStatus);
                });

        When("^the user sets the item's unit of measure kind to ([^\"]*)$",
                (String unitOfMeasureKindName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
                });

        When("^the user sets the item's price type to ([^\"]*)$",
                (String itemPriceTypeName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    persona.createItemForm.setItemPriceTypeName(itemPriceTypeName);
                });

        When("^the user adds the new item$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNotNull();

                    var itemService = ItemUtil.getHome();
                    var createItemForm = itemService.getCreateItemForm();

                    createItemForm.set(persona.createItemForm.get());

                    var commandResult = itemService.createItem(persona.userVisitPK, createItemForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateItemResult)commandResult.getExecutionResult().getResult();

                    persona.lastItemName = commandResult.getHasErrors() ? null : result.getItemName();
                    persona.createItemForm = null;
                });

        When("^the user sets the status of the last item added to ([^\"]*)$",
                (String itemStatusChoice) -> {
                    var persona = CurrentPersona.persona;

                    setItemStatus(persona, persona.lastItemName, itemStatusChoice);
                });

        When("^the user begins editing the last item added$",
                () -> {
                    var spec = ItemUtil.getHome().getItemSpec();
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNull();

                    spec.setItemName(persona.lastItemName);

                    var commandForm = ItemUtil.getHome().getEditItemForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ItemUtil.getHome().editItem(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemResult)executionResult.getResult();

                    persona.createItemForm = ItemUtil.getHome().getCreateItemForm();
                    persona.createItemForm.set(result.getEdit().get());
                });

        When("^the user finishes editing the item$",
                () -> {
                    var spec = ItemUtil.getHome().getItemSpec();
                    var persona = CurrentPersona.persona;
                    var edit = persona.createItemForm;

                    assertThat(edit).isNotNull();

                    spec.setItemName(persona.lastItemName);

                    var commandForm = ItemUtil.getHome().getEditItemForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ItemUtil.getHome().editItem(persona.userVisitPK, commandForm);
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
