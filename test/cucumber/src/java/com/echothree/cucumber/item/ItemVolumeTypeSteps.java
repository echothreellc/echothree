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

package com.echothree.cucumber.item;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.CreateItemVolumeTypeResult;
import com.echothree.control.user.item.common.result.EditItemVolumeTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemVolumeTypeSteps implements En {

    public ItemVolumeTypeSteps() {
        When("^the user begins entering a new item volume type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemVolumeTypeForm).isNull();
                    assertThat(persona.deleteItemVolumeTypeForm).isNull();
                    assertThat(persona.itemVolumeTypeSpec).isNull();

                    persona.createItemVolumeTypeForm = ItemUtil.getHome().getCreateItemVolumeTypeForm();
                });

        When("^the user adds the new item volume type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeTypeForm = persona.createItemVolumeTypeForm;

                    assertThat(createItemVolumeTypeForm).isNotNull();

                    var commandResult = ItemUtil.getHome().createItemVolumeType(persona.userVisitPK, createItemVolumeTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateItemVolumeTypeResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastItemVolumeTypeName = commandResult.getHasErrors() ? null : result.getItemVolumeTypeName();
                    persona.createItemVolumeTypeForm = null;
                });

        When("^the user begins deleting a item volume type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemVolumeTypeForm).isNull();
                    assertThat(persona.deleteItemVolumeTypeForm).isNull();
                    assertThat(persona.itemVolumeTypeSpec).isNull();

                    persona.deleteItemVolumeTypeForm = ItemUtil.getHome().getDeleteItemVolumeTypeForm();
                });

        When("^the user deletes the item volume type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteItemVolumeTypeForm = persona.deleteItemVolumeTypeForm;

                    assertThat(deleteItemVolumeTypeForm).isNotNull();

                    LastCommandResult.commandResult = ItemUtil.getHome().deleteItemVolumeType(persona.userVisitPK, deleteItemVolumeTypeForm);

                    persona.deleteItemVolumeTypeForm = null;
                });

        When("^the user begins specifying a item volume type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemVolumeTypeForm).isNull();
                    assertThat(persona.deleteItemVolumeTypeForm).isNull();
                    assertThat(persona.itemVolumeTypeSpec).isNull();

                    persona.itemVolumeTypeSpec = ItemUtil.getHome().getItemVolumeTypeUniversalSpec();
                });

        When("^the user begins editing the item volume type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemVolumeTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemVolumeTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ItemUtil.getHome().editItemVolumeType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemVolumeTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.itemVolumeTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the item volume type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemVolumeTypeSpec;
                    var edit = persona.itemVolumeTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemVolumeTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ItemUtil.getHome().editItemVolumeType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.itemVolumeTypeSpec = null;
                    persona.itemVolumeTypeEdit = null;
                });
        
        When("^the user sets the item volume type's name to ([a-zA-Z0-9-_]*)$",
                (String itemVolumeTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeTypeForm = persona.createItemVolumeTypeForm;
                    var deleteItemVolumeTypeForm = persona.deleteItemVolumeTypeForm;
                    var itemVolumeTypeSpec = persona.itemVolumeTypeSpec;

                    assertThat(createItemVolumeTypeForm != null || deleteItemVolumeTypeForm != null || itemVolumeTypeSpec != null).isTrue();

                    if(createItemVolumeTypeForm != null) {
                        createItemVolumeTypeForm.setItemVolumeTypeName(itemVolumeTypeName);
                    } else if(deleteItemVolumeTypeForm != null) {
                        deleteItemVolumeTypeForm.setItemVolumeTypeName(itemVolumeTypeName);
                    } else {
                        itemVolumeTypeSpec.setItemVolumeTypeName(itemVolumeTypeName);
                    }
                });

        When("^the user sets the item volume type's name to the last item volume type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastItemVolumeTypeName = persona.lastItemVolumeTypeName;
                    var deleteItemVolumeTypeForm = persona.deleteItemVolumeTypeForm;
                    var itemVolumeTypeSpec = persona.itemVolumeTypeSpec;

                    assertThat(deleteItemVolumeTypeForm != null || itemVolumeTypeSpec != null).isTrue();

                    if(deleteItemVolumeTypeForm != null) {
                        deleteItemVolumeTypeForm.setItemVolumeTypeName(lastItemVolumeTypeName);
                    } else {
                        itemVolumeTypeSpec.setItemVolumeTypeName(lastItemVolumeTypeName);
                    }
                });
        
        When("^the user sets the item volume type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeTypeForm = persona.createItemVolumeTypeForm;
                    var itemVolumeTypeEdit = persona.itemVolumeTypeEdit;

                    assertThat(createItemVolumeTypeForm != null || itemVolumeTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createItemVolumeTypeForm != null) {
                        createItemVolumeTypeForm.setIsDefault(isDefault);
                    } else {
                        itemVolumeTypeEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the item volume type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeTypeForm = persona.createItemVolumeTypeForm;
                    var itemVolumeTypeEdit = persona.itemVolumeTypeEdit;

                    assertThat(createItemVolumeTypeForm != null || itemVolumeTypeEdit != null).isTrue();

                    if(createItemVolumeTypeForm != null) {
                        createItemVolumeTypeForm.setSortOrder(sortOrder);
                    } else {
                        itemVolumeTypeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the item volume type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeTypeForm = persona.createItemVolumeTypeForm;
                    var itemVolumeTypeEdit = persona.itemVolumeTypeEdit;

                    assertThat(createItemVolumeTypeForm != null || itemVolumeTypeEdit != null).isTrue();

                    if(createItemVolumeTypeForm != null) {
                        createItemVolumeTypeForm.setDescription(description);
                    } else {
                        itemVolumeTypeEdit.setDescription(description);
                    }
                });
    }

}
