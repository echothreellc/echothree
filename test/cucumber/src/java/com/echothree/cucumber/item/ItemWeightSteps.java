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
import com.echothree.control.user.item.common.result.EditItemWeightResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemWeightSteps implements En {

    public ItemWeightSteps() {
        When("^the user begins entering a new item weight$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemWeightForm).isNull();
                    assertThat(persona.deleteItemWeightForm).isNull();
                    assertThat(persona.itemWeightSpec).isNull();

                    persona.createItemWeightForm = ItemUtil.getHome().getCreateItemWeightForm();
                });

        When("^the user adds the new item weight$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createItemWeightForm = persona.createItemWeightForm;

                    assertThat(createItemWeightForm).isNotNull();

                    var commandResult = ItemUtil.getHome().createItemWeight(persona.userVisitPK, createItemWeightForm);
                    LastCommandResult.commandResult = commandResult;

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();

                    persona.createItemWeightForm = null;
                });

        When("^the user begins deleting an item weight$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemWeightForm).isNull();
                    assertThat(persona.deleteItemWeightForm).isNull();
                    assertThat(persona.itemWeightSpec).isNull();

                    persona.deleteItemWeightForm = ItemUtil.getHome().getDeleteItemWeightForm();
                });

        When("^the user deletes the item weight$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteItemWeightForm = persona.deleteItemWeightForm;

                    assertThat(deleteItemWeightForm).isNotNull();

                    LastCommandResult.commandResult = ItemUtil.getHome().deleteItemWeight(persona.userVisitPK, deleteItemWeightForm);

                    persona.deleteItemWeightForm = null;
                });

        When("^the user begins specifying an item weight to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemWeightForm).isNull();
                    assertThat(persona.deleteItemWeightForm).isNull();
                    assertThat(persona.itemWeightSpec).isNull();

                    persona.itemWeightSpec = ItemUtil.getHome().getItemWeightSpec();
                });

        When("^the user begins editing the item weight$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemWeightSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemWeightForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ItemUtil.getHome().editItemWeight(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemWeightResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.itemWeightEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the item weight$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemWeightSpec;
                    var edit = persona.itemWeightEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemWeightForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = ItemUtil.getHome().editItemWeight(persona.userVisitPK, commandForm);

                    persona.itemWeightSpec = null;
                    persona.itemWeightEdit = null;
                });

        When("^the user sets the item weight's item to \"([^\"]*)\"$",
                (String itemName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemWeightForm = persona.createItemWeightForm;
                    var deleteItemWeightForm = persona.deleteItemWeightForm;
                    var itemWeightSpec = persona.itemWeightSpec;

                    assertThat(createItemWeightForm != null || deleteItemWeightForm != null || itemWeightSpec != null).isTrue();

                    if(createItemWeightForm != null) {
                        createItemWeightForm.setItemName(itemName);
                    } else if(deleteItemWeightForm != null) {
                        deleteItemWeightForm.setItemName(itemName);
                    } else {
                        itemWeightSpec.setItemName(itemName);
                    }
                });

        When("^the user sets the item weight's item to the last item added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastItemName = persona.lastItemName;
                    var createItemWeightForm = persona.createItemWeightForm;
                    var deleteItemWeightForm = persona.deleteItemWeightForm;
                    var itemWeightSpec = persona.itemWeightSpec;

                    assertThat(createItemWeightForm != null || deleteItemWeightForm != null || itemWeightSpec != null).isTrue();
                    assertThat(lastItemName).isNotNull();

                    if(createItemWeightForm != null) {
                        createItemWeightForm.setItemName(lastItemName);
                    } else if(deleteItemWeightForm != null) {
                        deleteItemWeightForm.setItemName(lastItemName);
                    } else {
                        itemWeightSpec.setItemName(lastItemName);
                    }
                });

        When("^the user sets the item weight's unit of measure type to \"([^\"]*)\"$",
                (String unitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemWeightForm = persona.createItemWeightForm;
                    var deleteItemWeightForm = persona.deleteItemWeightForm;
                    var itemWeightSpec = persona.itemWeightSpec;

                    assertThat(createItemWeightForm != null || deleteItemWeightForm != null || itemWeightSpec != null).isTrue();

                    if(createItemWeightForm != null) {
                        createItemWeightForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else if(deleteItemWeightForm != null) {
                        deleteItemWeightForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else {
                        itemWeightSpec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    }
                });

        When("^the user sets the item weight's unit of measure type to the last unit of measure type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastUnitOfMeasureTypeName = persona.lastUnitOfMeasureTypeName;
                    var createItemWeightForm = persona.createItemWeightForm;
                    var deleteItemWeightForm = persona.deleteItemWeightForm;
                    var itemWeightSpec = persona.itemWeightSpec;

                    assertThat(createItemWeightForm != null || deleteItemWeightForm != null || itemWeightSpec != null).isTrue();
                    assertThat(lastUnitOfMeasureTypeName).isNotNull();

                    if(createItemWeightForm != null) {
                        createItemWeightForm.setUnitOfMeasureTypeName(lastUnitOfMeasureTypeName);
                    } else if(deleteItemWeightForm != null) {
                        deleteItemWeightForm.setUnitOfMeasureTypeName(lastUnitOfMeasureTypeName);
                    } else {
                        itemWeightSpec.setUnitOfMeasureTypeName(lastUnitOfMeasureTypeName);
                    }
                });

        When("^the user sets the item weight's item weight type to \"([^\"]*)\"$",
                (String itemWeightTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemWeightForm = persona.createItemWeightForm;
                    var deleteItemWeightForm = persona.deleteItemWeightForm;
                    var itemWeightSpec = persona.itemWeightSpec;

                    assertThat(createItemWeightForm != null || deleteItemWeightForm != null || itemWeightSpec != null).isTrue();

                    if(createItemWeightForm != null) {
                        createItemWeightForm.setItemWeightTypeName(itemWeightTypeName);
                    } else if(deleteItemWeightForm != null) {
                        deleteItemWeightForm.setItemWeightTypeName(itemWeightTypeName);
                    } else {
                        itemWeightSpec.setItemWeightTypeName(itemWeightTypeName);
                    }
                });

        When("^the user sets the item weight's item weight type to the last item weight type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastItemWeightTypeName = persona.lastItemWeightTypeName;
                    var createItemWeightForm = persona.createItemWeightForm;
                    var deleteItemWeightForm = persona.deleteItemWeightForm;
                    var itemWeightSpec = persona.itemWeightSpec;

                    assertThat(createItemWeightForm != null || deleteItemWeightForm != null || itemWeightSpec != null).isTrue();
                    assertThat(lastItemWeightTypeName).isNotNull();

                    if(createItemWeightForm != null) {
                        createItemWeightForm.setItemWeightTypeName(lastItemWeightTypeName);
                    } else if(deleteItemWeightForm != null) {
                        deleteItemWeightForm.setItemWeightTypeName(lastItemWeightTypeName);
                    } else {
                        itemWeightSpec.setItemWeightTypeName(lastItemWeightTypeName);
                    }
                });

        When("^the user sets the item weight's weight unit of measure type to \"([^\"]*)\"$",
                (String weightUnitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemWeightForm = persona.createItemWeightForm;
                    var itemWeightEdit = persona.itemWeightEdit;

                    assertThat(createItemWeightForm != null || itemWeightEdit != null).isTrue();

                    if(createItemWeightForm != null) {
                        createItemWeightForm.setWeightUnitOfMeasureTypeName(weightUnitOfMeasureTypeName);
                    } else {
                        itemWeightEdit.setWeightUnitOfMeasureTypeName(weightUnitOfMeasureTypeName);
                    }
                });

        When("^the user sets the item weight's weight to \"([^\"]*)\"$",
                (String weight) -> {
                    var persona = CurrentPersona.persona;
                    var createItemWeightForm = persona.createItemWeightForm;
                    var itemWeightEdit = persona.itemWeightEdit;

                    assertThat(createItemWeightForm != null || itemWeightEdit != null).isTrue();

                    if(createItemWeightForm != null) {
                        createItemWeightForm.setWeight(weight);
                    } else {
                        itemWeightEdit.setWeight(weight);
                    }
                });
    }

}
