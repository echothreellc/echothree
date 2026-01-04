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
import com.echothree.control.user.item.common.result.CreateItemWeightTypeResult;
import com.echothree.control.user.item.common.result.EditItemWeightTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemWeightTypeSteps implements En {

    public ItemWeightTypeSteps() {
        When("^the user begins entering a new item weight type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemWeightTypeForm).isNull();
                    assertThat(persona.deleteItemWeightTypeForm).isNull();
                    assertThat(persona.itemWeightTypeSpec).isNull();

                    persona.createItemWeightTypeForm = ItemUtil.getHome().getCreateItemWeightTypeForm();
                });

        When("^the user adds the new item weight type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createItemWeightTypeForm = persona.createItemWeightTypeForm;

                    assertThat(createItemWeightTypeForm).isNotNull();

                    var commandResult = ItemUtil.getHome().createItemWeightType(persona.userVisitPK, createItemWeightTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateItemWeightTypeResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastItemWeightTypeName = commandResult.getHasErrors() ? null : result.getItemWeightTypeName();
                    persona.createItemWeightTypeForm = null;
                });

        When("^the user begins deleting a item weight type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemWeightTypeForm).isNull();
                    assertThat(persona.deleteItemWeightTypeForm).isNull();
                    assertThat(persona.itemWeightTypeSpec).isNull();

                    persona.deleteItemWeightTypeForm = ItemUtil.getHome().getDeleteItemWeightTypeForm();
                });

        When("^the user deletes the item weight type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteItemWeightTypeForm = persona.deleteItemWeightTypeForm;

                    assertThat(deleteItemWeightTypeForm).isNotNull();

                    LastCommandResult.commandResult = ItemUtil.getHome().deleteItemWeightType(persona.userVisitPK, deleteItemWeightTypeForm);

                    persona.deleteItemWeightTypeForm = null;
                });

        When("^the user begins specifying a item weight type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemWeightTypeForm).isNull();
                    assertThat(persona.deleteItemWeightTypeForm).isNull();
                    assertThat(persona.itemWeightTypeSpec).isNull();

                    persona.itemWeightTypeSpec = ItemUtil.getHome().getItemWeightTypeUniversalSpec();
                });

        When("^the user begins editing the item weight type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemWeightTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemWeightTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ItemUtil.getHome().editItemWeightType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemWeightTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.itemWeightTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the item weight type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemWeightTypeSpec;
                    var edit = persona.itemWeightTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemWeightTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ItemUtil.getHome().editItemWeightType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.itemWeightTypeSpec = null;
                    persona.itemWeightTypeEdit = null;
                });
        
        When("^the user sets the item weight type's name to ([a-zA-Z0-9-_]*)$",
                (String itemWeightTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemWeightTypeForm = persona.createItemWeightTypeForm;
                    var deleteItemWeightTypeForm = persona.deleteItemWeightTypeForm;
                    var itemWeightTypeSpec = persona.itemWeightTypeSpec;

                    assertThat(createItemWeightTypeForm != null || deleteItemWeightTypeForm != null || itemWeightTypeSpec != null).isTrue();

                    if(createItemWeightTypeForm != null) {
                        createItemWeightTypeForm.setItemWeightTypeName(itemWeightTypeName);
                    } else if(deleteItemWeightTypeForm != null) {
                        deleteItemWeightTypeForm.setItemWeightTypeName(itemWeightTypeName);
                    } else {
                        itemWeightTypeSpec.setItemWeightTypeName(itemWeightTypeName);
                    }
                });

        When("^the user sets the item weight type's name to the last item weight type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastItemWeightTypeName = persona.lastItemWeightTypeName;
                    var deleteItemWeightTypeForm = persona.deleteItemWeightTypeForm;
                    var itemWeightTypeSpec = persona.itemWeightTypeSpec;

                    assertThat(deleteItemWeightTypeForm != null || itemWeightTypeSpec != null).isTrue();

                    if(deleteItemWeightTypeForm != null) {
                        deleteItemWeightTypeForm.setItemWeightTypeName(lastItemWeightTypeName);
                    } else {
                        itemWeightTypeSpec.setItemWeightTypeName(lastItemWeightTypeName);
                    }
                });

        When("^the user sets the item weight type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createItemWeightTypeForm = persona.createItemWeightTypeForm;
                    var itemWeightTypeEdit = persona.itemWeightTypeEdit;

                    assertThat(createItemWeightTypeForm != null || itemWeightTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createItemWeightTypeForm != null) {
                        createItemWeightTypeForm.setIsDefault(isDefault);
                    } else {
                        itemWeightTypeEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the item weight type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createItemWeightTypeForm = persona.createItemWeightTypeForm;
                    var itemWeightTypeEdit = persona.itemWeightTypeEdit;

                    assertThat(createItemWeightTypeForm != null || itemWeightTypeEdit != null).isTrue();

                    if(createItemWeightTypeForm != null) {
                        createItemWeightTypeForm.setSortOrder(sortOrder);
                    } else {
                        itemWeightTypeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the item weight type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createItemWeightTypeForm = persona.createItemWeightTypeForm;
                    var itemWeightTypeEdit = persona.itemWeightTypeEdit;

                    assertThat(createItemWeightTypeForm != null || itemWeightTypeEdit != null).isTrue();

                    if(createItemWeightTypeForm != null) {
                        createItemWeightTypeForm.setDescription(description);
                    } else {
                        itemWeightTypeEdit.setDescription(description);
                    }
                });
    }

}
