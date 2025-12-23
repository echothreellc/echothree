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
import com.echothree.control.user.item.common.result.EditItemPriceResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemPriceSteps implements En {

    public ItemPriceSteps() {
        When("^the user begins entering a new item price$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNull();
                    assertThat(persona.deleteItemPriceForm).isNull();
                    assertThat(persona.itemPriceSpec).isNull();

                    persona.createItemPriceForm = ItemUtil.getHome().getCreateItemPriceForm();
                });

        When("^the user adds the new item price$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNotNull();

                    var itemService = ItemUtil.getHome();
                    var createItemPriceForm = itemService.getCreateItemPriceForm();

                    createItemPriceForm.set(persona.createItemPriceForm.get());

                    var commandResult = itemService.createItemPrice(persona.userVisitPK, createItemPriceForm);

                    LastCommandResult.commandResult = commandResult;

                    persona.createItemPriceForm = null;
                });

        When("^the user begins deleting an item price$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNull();
                    assertThat(persona.deleteItemPriceForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteItemPriceForm = ItemUtil.getHome().getDeleteItemPriceForm();
                });

        When("^the user deletes the item price$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteItemPriceForm = persona.deleteItemPriceForm;

                    assertThat(deleteItemPriceForm).isNotNull();

                    LastCommandResult.commandResult = ItemUtil.getHome().deleteItemPrice(persona.userVisitPK, deleteItemPriceForm);

                    persona.deleteItemPriceForm = null;
                });

        When("^the user begins specifying an item price to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemPriceForm).isNull();
                    assertThat(persona.deleteItemPriceForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.itemPriceSpec = ItemUtil.getHome().getItemPriceSpec();
                });

        When("^the user begins editing the item price",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemPriceSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemPriceForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ItemUtil.getHome().editItemPrice(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemPriceResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.itemPriceEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the item price",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemPriceSpec;
                    var edit = persona.itemPriceEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemPriceForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ItemUtil.getHome().editItemPrice(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.itemPriceSpec = null;
                    persona.itemPriceEdit = null;
                });


        When("^the user sets the item price's item to ([a-zA-Z0-9-_]*)$",
                (String itemName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemPriceForm = persona.createItemPriceForm;
                    var deleteItemPriceForm = persona.deleteItemPriceForm;
                    var itemPriceSpec = persona.itemPriceSpec;

                    assertThat(createItemPriceForm != null || deleteItemPriceForm != null || itemPriceSpec != null).isTrue();

                    if(createItemPriceForm != null) {
                        createItemPriceForm.setItemName(itemName);
                    } else if(deleteItemPriceForm != null) {
                        deleteItemPriceForm.setItemName(itemName);
                    } else {
                        itemPriceSpec.setItemName(itemName);
                    }
                });

        When("^the user sets the item price's item to the last item added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createItemPriceForm = persona.createItemPriceForm;
                    var deleteItemPriceForm = persona.deleteItemPriceForm;
                    var itemPriceSpec = persona.itemPriceSpec;
                    var lastItemName = persona.lastItemName;

                    assertThat(createItemPriceForm != null || deleteItemPriceForm != null || itemPriceSpec != null).isTrue();
                    assertThat(lastItemName).isNotNull();

                    if(createItemPriceForm != null) {
                        createItemPriceForm.setItemName(lastItemName);
                    } else if(deleteItemPriceForm != null) {
                        deleteItemPriceForm.setItemName(lastItemName);
                    } else {
                        itemPriceSpec.setItemName(lastItemName);
                    }
                });

        When("^the user sets the item price's inventory condition to ([a-zA-Z0-9-_]*)$",
                (String inventoryConditionName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemPriceForm = persona.createItemPriceForm;
                    var deleteItemPriceForm = persona.deleteItemPriceForm;
                    var itemPriceSpec = persona.itemPriceSpec;

                    assertThat(createItemPriceForm != null || deleteItemPriceForm != null || itemPriceSpec != null).isTrue();

                    if(createItemPriceForm != null) {
                        createItemPriceForm.setInventoryConditionName(inventoryConditionName);
                    } else if(deleteItemPriceForm != null) {
                        deleteItemPriceForm.setInventoryConditionName(inventoryConditionName);
                    } else {
                        itemPriceSpec.setInventoryConditionName(inventoryConditionName);
                    }
                });

        When("^the user sets the item price's unit of measure type to ([a-zA-Z0-9-_]*)$",
                (String unitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemPriceForm = persona.createItemPriceForm;
                    var deleteItemPriceForm = persona.deleteItemPriceForm;
                    var itemPriceSpec = persona.itemPriceSpec;

                    assertThat(createItemPriceForm != null || deleteItemPriceForm != null || itemPriceSpec != null).isTrue();

                    if(createItemPriceForm != null) {
                        createItemPriceForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else if(deleteItemPriceForm != null) {
                        deleteItemPriceForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else {
                        itemPriceSpec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    }
                });

        When("^the user sets the item price's currency to ([a-zA-Z0-9-_]*)$",
                (String currencyIsoName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemPriceForm = persona.createItemPriceForm;
                    var deleteItemPriceForm = persona.deleteItemPriceForm;
                    var itemPriceSpec = persona.itemPriceSpec;

                    assertThat(createItemPriceForm != null || deleteItemPriceForm != null || itemPriceSpec != null).isTrue();

                    if(createItemPriceForm != null) {
                        createItemPriceForm.setCurrencyIsoName(currencyIsoName);
                    } else if(deleteItemPriceForm != null) {
                        deleteItemPriceForm.setCurrencyIsoName(currencyIsoName);
                    } else {
                        itemPriceSpec.setCurrencyIsoName(currencyIsoName);
                    }
                });

        When("^the user sets the item price's unit price to ([^\"]*)$",
                (String unitPrice) -> {
                    var persona = CurrentPersona.persona;
                    var createItemPriceForm = persona.createItemPriceForm;
                    var itemPriceEdit = persona.itemPriceEdit;

                    assertThat(createItemPriceForm != null || itemPriceEdit != null).isTrue();

                    if(createItemPriceForm != null) {
                        createItemPriceForm.setUnitPrice(unitPrice);
                    } else {
                        itemPriceEdit.setUnitPrice(unitPrice);
                    }
                });

        When("^the user sets the item price's minimum unit price to ([^\"]*)$",
                (String minimumUnitPrice) -> {
                    var persona = CurrentPersona.persona;
                    var createItemPriceForm = persona.createItemPriceForm;
                    var itemPriceEdit = persona.itemPriceEdit;

                    assertThat(createItemPriceForm != null || itemPriceEdit != null).isTrue();

                    if(createItemPriceForm != null) {
                        createItemPriceForm.setMinimumUnitPrice(minimumUnitPrice);
                    } else {
                        itemPriceEdit.setMinimumUnitPrice(minimumUnitPrice);
                    }
                });

        When("^the user sets the item price's maximum unit price to ([^\"]*)$",
                (String maximumUnitPrice) -> {
                    var persona = CurrentPersona.persona;
                    var createItemPriceForm = persona.createItemPriceForm;
                    var itemPriceEdit = persona.itemPriceEdit;

                    assertThat(createItemPriceForm != null || itemPriceEdit != null).isTrue();

                    if(createItemPriceForm != null) {
                        createItemPriceForm.setMaximumUnitPrice(maximumUnitPrice);
                    } else {
                        itemPriceEdit.setMaximumUnitPrice(maximumUnitPrice);
                    }
                });

        When("^the user sets the item price's unit price increment to ([^\"]*)$",
                (String unitPriceIncrement) -> {
                    var persona = CurrentPersona.persona;
                    var createItemPriceForm = persona.createItemPriceForm;
                    var itemPriceEdit = persona.itemPriceEdit;

                    assertThat(createItemPriceForm != null || itemPriceEdit != null).isTrue();

                    if(createItemPriceForm != null) {
                        createItemPriceForm.setUnitPriceIncrement(unitPriceIncrement);
                    } else {
                        itemPriceEdit.setUnitPriceIncrement(unitPriceIncrement);
                    }
                });
    }

}
