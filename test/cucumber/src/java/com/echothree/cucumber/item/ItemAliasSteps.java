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
import com.echothree.control.user.item.common.result.EditItemAliasResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemAliasSteps implements En {

    public ItemAliasSteps() {
        When("^the user begins entering a new item alias$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemAliasForm).isNull();
                    assertThat(persona.deleteItemAliasForm).isNull();
                    assertThat(persona.itemAliasSpec).isNull();

                    persona.createItemAliasForm = ItemUtil.getHome().getCreateItemAliasForm();
                });

        When("^the user adds the new item alias$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasForm = persona.createItemAliasForm;

                    assertThat(createItemAliasForm).isNotNull();

                    var commandResult = ItemUtil.getHome().createItemAlias(persona.userVisitPK, createItemAliasForm);
                    LastCommandResult.commandResult = commandResult;

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();

                    persona.lastAlias = commandResult.getHasErrors() ? null : createItemAliasForm.getAlias();
                    persona.createItemAliasForm = null;
                });

        When("^the user begins deleting an item alias$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemAliasForm).isNull();
                    assertThat(persona.deleteItemAliasForm).isNull();
                    assertThat(persona.itemAliasSpec).isNull();

                    persona.deleteItemAliasForm = ItemUtil.getHome().getDeleteItemAliasForm();
                });

        When("^the user deletes the item alias$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteItemAliasForm = persona.deleteItemAliasForm;

                    assertThat(deleteItemAliasForm).isNotNull();

                    LastCommandResult.commandResult = ItemUtil.getHome().deleteItemAlias(persona.userVisitPK, deleteItemAliasForm);

                    persona.deleteItemAliasForm = null;
                });

        When("^the user begins specifying an item alias to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemAliasForm).isNull();
                    assertThat(persona.deleteItemAliasForm).isNull();
                    assertThat(persona.itemAliasSpec).isNull();

                    persona.itemAliasSpec = ItemUtil.getHome().getItemAliasSpec();
                });

        When("^the user begins editing the item alias$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemAliasSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemAliasForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ItemUtil.getHome().editItemAlias(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemAliasResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.itemAliasEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the item alias$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemAliasSpec;
                    var edit = persona.itemAliasEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemAliasForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = ItemUtil.getHome().editItemAlias(persona.userVisitPK, commandForm);

                    persona.itemAliasSpec = null;
                    persona.itemAliasEdit = null;
                });

        When("^the user sets the item alias's item to \"([^\"]*)\"$",
                (String itemName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasForm = persona.createItemAliasForm;

                    assertThat(createItemAliasForm != null).isTrue();

                    createItemAliasForm.setItemName(itemName);
                });

        When("^the user sets the item alias's item to the last item added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasForm = persona.createItemAliasForm;
                    var lastItemName = persona.lastItemName;

                    assertThat(createItemAliasForm != null).isTrue();
                    assertThat(lastItemName).isNotNull();

                    createItemAliasForm.setItemName(lastItemName);
                });

        When("^the user sets the item alias's unit of measure type to \"([^\"]*)\"$",
                (String unitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasForm = persona.createItemAliasForm;
                    var itemAliasEdit = persona.itemAliasEdit;

                    assertThat(createItemAliasForm != null || itemAliasEdit != null).isTrue();

                    Objects.requireNonNullElse(createItemAliasForm, itemAliasEdit).setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                });

        When("^the user sets the item alias's unit of measure type to the last unit of measure type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastUnitOfMeasureTypeName = persona.lastUnitOfMeasureTypeName;
                    var itemAliasEdit = persona.itemAliasEdit;

                    assertThat( itemAliasEdit != null).isTrue();

                    itemAliasEdit.setUnitOfMeasureTypeName(lastUnitOfMeasureTypeName);
                });

        When("^the user sets the item alias's item alias type to \"([^\"]*)\"$",
                (String itemAliasTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasForm = persona.createItemAliasForm;
                    var itemAliasEdit = persona.itemAliasEdit;

                    assertThat(createItemAliasForm != null || itemAliasEdit != null).isTrue();

                    Objects.requireNonNullElse(createItemAliasForm, itemAliasEdit).setItemAliasTypeName(itemAliasTypeName);
                });

        When("^the user sets the item alias's item alias type to the last item alias type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastItemAliasTypeName = persona.lastItemAliasTypeName;
                    var itemAliasEdit = persona.itemAliasEdit;

                    assertThat( itemAliasEdit != null).isTrue();

                    itemAliasEdit.setItemAliasTypeName(lastItemAliasTypeName);
                });

        When("^the user sets the item alias's alias to \"([^\"]*)\"$",
                (String alias) -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasForm = persona.createItemAliasForm;
                    var deleteItemAliasForm = persona.deleteItemAliasForm;
                    var itemAliasSpec = persona.itemAliasSpec;

                    assertThat(createItemAliasForm != null || deleteItemAliasForm != null || itemAliasSpec != null).isTrue();

                    Objects.requireNonNullElseGet(createItemAliasForm, () -> Objects.requireNonNullElse(deleteItemAliasForm, itemAliasSpec)).setAlias(alias);
                });

        When("^the user sets the item alias's alias to the last alias added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastItemAliasName = persona.lastAlias;
                    var deleteItemAliasForm = persona.deleteItemAliasForm;
                    var itemAliasSpec = persona.itemAliasSpec;

                    assertThat(deleteItemAliasForm != null || itemAliasSpec != null).isTrue();

                    Objects.requireNonNullElse(deleteItemAliasForm, itemAliasSpec).setAlias(lastItemAliasName);
                });
    }

}
