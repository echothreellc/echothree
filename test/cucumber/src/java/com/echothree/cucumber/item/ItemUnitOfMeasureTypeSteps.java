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
import com.echothree.control.user.item.common.result.EditItemUnitOfMeasureTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemUnitOfMeasureTypeSteps implements En {

    public ItemUnitOfMeasureTypeSteps() {
        When("^the user begins entering a new item unit of measure type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemUnitOfMeasureTypeForm).isNull();
                    assertThat(persona.deleteItemUnitOfMeasureTypeForm).isNull();
                    assertThat(persona.itemUnitOfMeasureTypeSpec).isNull();

                    persona.createItemUnitOfMeasureTypeForm = ItemUtil.getHome().getCreateItemUnitOfMeasureTypeForm();
                });

        When("^the user adds the new item unit of measure type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemUnitOfMeasureTypeForm).isNotNull();

                    var itemService = ItemUtil.getHome();
                    var createItemUnitOfMeasureTypeForm = itemService.getCreateItemUnitOfMeasureTypeForm();

                    createItemUnitOfMeasureTypeForm.set(persona.createItemUnitOfMeasureTypeForm.get());

                    var commandResult = itemService.createItemUnitOfMeasureType(persona.userVisitPK, createItemUnitOfMeasureTypeForm);

                    LastCommandResult.commandResult = commandResult;

                    persona.createItemUnitOfMeasureTypeForm = null;
                });

        When("^the user begins deleting an item unit of measure type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemUnitOfMeasureTypeForm).isNull();
                    assertThat(persona.deleteItemUnitOfMeasureTypeForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteItemUnitOfMeasureTypeForm = ItemUtil.getHome().getDeleteItemUnitOfMeasureTypeForm();
                });

        When("^the user deletes the item unit of measure type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteItemUnitOfMeasureTypeForm = persona.deleteItemUnitOfMeasureTypeForm;

                    assertThat(deleteItemUnitOfMeasureTypeForm).isNotNull();

                    LastCommandResult.commandResult = ItemUtil.getHome().deleteItemUnitOfMeasureType(persona.userVisitPK, deleteItemUnitOfMeasureTypeForm);

                    persona.deleteItemUnitOfMeasureTypeForm = null;
                });

        When("^the user begins specifying an item unit of measure type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemUnitOfMeasureTypeForm).isNull();
                    assertThat(persona.deleteItemUnitOfMeasureTypeForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.itemUnitOfMeasureTypeSpec = ItemUtil.getHome().getItemUnitOfMeasureTypeSpec();
                });

        When("^the user begins editing the item unit of measure type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemUnitOfMeasureTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemUnitOfMeasureTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ItemUtil.getHome().editItemUnitOfMeasureType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemUnitOfMeasureTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.itemUnitOfMeasureTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the item unit of measure type",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemUnitOfMeasureTypeSpec;
                    var edit = persona.itemUnitOfMeasureTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemUnitOfMeasureTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ItemUtil.getHome().editItemUnitOfMeasureType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.itemUnitOfMeasureTypeSpec = null;
                    persona.itemUnitOfMeasureTypeEdit = null;
                });


        When("^the user sets the item unit of measure type's item to ([a-zA-Z0-9-_]*)$",
                (String itemName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemUnitOfMeasureTypeForm = persona.createItemUnitOfMeasureTypeForm;
                    var deleteItemUnitOfMeasureTypeForm = persona.deleteItemUnitOfMeasureTypeForm;
                    var itemUnitOfMeasureTypeSpec = persona.itemUnitOfMeasureTypeSpec;

                    assertThat(createItemUnitOfMeasureTypeForm != null || deleteItemUnitOfMeasureTypeForm != null || itemUnitOfMeasureTypeSpec != null).isTrue();

                    if(createItemUnitOfMeasureTypeForm != null) {
                        createItemUnitOfMeasureTypeForm.setItemName(itemName);
                    } else if(deleteItemUnitOfMeasureTypeForm != null) {
                        deleteItemUnitOfMeasureTypeForm.setItemName(itemName);
                    } else {
                        itemUnitOfMeasureTypeSpec.setItemName(itemName);
                    }
                });

        When("^the user sets the item unit of measure type's item to the last item added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createItemUnitOfMeasureTypeForm = persona.createItemUnitOfMeasureTypeForm;
                    var deleteItemUnitOfMeasureTypeForm = persona.deleteItemUnitOfMeasureTypeForm;
                    var itemUnitOfMeasureTypeSpec = persona.itemUnitOfMeasureTypeSpec;
                    var lastItemName = persona.lastItemName;

                    assertThat(createItemUnitOfMeasureTypeForm != null || deleteItemUnitOfMeasureTypeForm != null || itemUnitOfMeasureTypeSpec != null).isTrue();
                    assertThat(lastItemName).isNotNull();

                    if(createItemUnitOfMeasureTypeForm != null) {
                        createItemUnitOfMeasureTypeForm.setItemName(lastItemName);
                    } else if(deleteItemUnitOfMeasureTypeForm != null) {
                        deleteItemUnitOfMeasureTypeForm.setItemName(lastItemName);
                    } else {
                        itemUnitOfMeasureTypeSpec.setItemName(lastItemName);
                    }
                });

        When("^the user sets the item unit of measure type's unit of measure type to ([a-zA-Z0-9-_]*)$",
                (String unitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemUnitOfMeasureTypeForm = persona.createItemUnitOfMeasureTypeForm;
                    var deleteItemUnitOfMeasureTypeForm = persona.deleteItemUnitOfMeasureTypeForm;
                    var itemUnitOfMeasureTypeSpec = persona.itemUnitOfMeasureTypeSpec;

                    assertThat(createItemUnitOfMeasureTypeForm != null || deleteItemUnitOfMeasureTypeForm != null || itemUnitOfMeasureTypeSpec != null).isTrue();

                    if(createItemUnitOfMeasureTypeForm != null) {
                        createItemUnitOfMeasureTypeForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else if(deleteItemUnitOfMeasureTypeForm != null) {
                        deleteItemUnitOfMeasureTypeForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else {
                        itemUnitOfMeasureTypeSpec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    }
                });

        When("^the user sets the item unit of measure type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createItemUnitOfMeasureTypeForm = persona.createItemUnitOfMeasureTypeForm;
                    var itemUnitOfMeasureTypeEdit = persona.itemUnitOfMeasureTypeEdit;

                    assertThat(createItemUnitOfMeasureTypeForm != null || itemUnitOfMeasureTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createItemUnitOfMeasureTypeForm != null) {
                        createItemUnitOfMeasureTypeForm.setIsDefault(isDefault);
                    } else {
                        itemUnitOfMeasureTypeEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the item unit of measure type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createItemUnitOfMeasureTypeForm = persona.createItemUnitOfMeasureTypeForm;
                    var itemUnitOfMeasureTypeEdit = persona.itemUnitOfMeasureTypeEdit;

                    assertThat(createItemUnitOfMeasureTypeForm != null || itemUnitOfMeasureTypeEdit != null).isTrue();

                    if(createItemUnitOfMeasureTypeForm != null) {
                        createItemUnitOfMeasureTypeForm.setSortOrder(sortOrder);
                    } else {
                        itemUnitOfMeasureTypeEdit.setSortOrder(sortOrder);
                    }
                });
    }

}
