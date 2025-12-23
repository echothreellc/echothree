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
import com.echothree.control.user.item.common.result.CreateItemImageTypeResult;
import com.echothree.control.user.item.common.result.EditItemImageTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemImageTypeSteps implements En {

    public ItemImageTypeSteps() {
        When("^the user begins entering a new item image type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemImageTypeForm).isNull();
                    assertThat(persona.deleteItemImageTypeForm).isNull();
                    assertThat(persona.itemImageTypeSpec).isNull();

                    persona.createItemImageTypeForm = ItemUtil.getHome().getCreateItemImageTypeForm();
                });

        When("^the user adds the new item image type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createItemImageTypeForm = persona.createItemImageTypeForm;

                    assertThat(createItemImageTypeForm).isNotNull();

                    var commandResult = ItemUtil.getHome().createItemImageType(persona.userVisitPK, createItemImageTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateItemImageTypeResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastItemImageTypeName = commandResult.getHasErrors() ? null : result.getItemImageTypeName();
                    persona.createItemImageTypeForm = null;
                });

        When("^the user begins deleting a item image type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemImageTypeForm).isNull();
                    assertThat(persona.deleteItemImageTypeForm).isNull();
                    assertThat(persona.itemImageTypeSpec).isNull();

                    persona.deleteItemImageTypeForm = ItemUtil.getHome().getDeleteItemImageTypeForm();
                });

        When("^the user deletes the item image type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteItemImageTypeForm = persona.deleteItemImageTypeForm;

                    assertThat(deleteItemImageTypeForm).isNotNull();

                    LastCommandResult.commandResult = ItemUtil.getHome().deleteItemImageType(persona.userVisitPK, deleteItemImageTypeForm);

                    persona.deleteItemImageTypeForm = null;
                });

        When("^the user begins specifying a item image type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemImageTypeForm).isNull();
                    assertThat(persona.deleteItemImageTypeForm).isNull();
                    assertThat(persona.itemImageTypeSpec).isNull();

                    persona.itemImageTypeSpec = ItemUtil.getHome().getItemImageTypeUniversalSpec();
                });

        When("^the user begins editing the item image type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemImageTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemImageTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ItemUtil.getHome().editItemImageType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemImageTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.itemImageTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the item image type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemImageTypeSpec;
                    var edit = persona.itemImageTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemImageTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ItemUtil.getHome().editItemImageType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.itemImageTypeSpec = null;
                    persona.itemImageTypeEdit = null;
                });
        
        When("^the user sets the item image type's name to ([a-zA-Z0-9-_]*)$",
                (String itemImageTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemImageTypeForm = persona.createItemImageTypeForm;
                    var deleteItemImageTypeForm = persona.deleteItemImageTypeForm;
                    var itemImageTypeSpec = persona.itemImageTypeSpec;

                    assertThat(createItemImageTypeForm != null || deleteItemImageTypeForm != null || itemImageTypeSpec != null).isTrue();

                    if(createItemImageTypeForm != null) {
                        createItemImageTypeForm.setItemImageTypeName(itemImageTypeName);
                    } else if(deleteItemImageTypeForm != null) {
                        deleteItemImageTypeForm.setItemImageTypeName(itemImageTypeName);
                    } else {
                        itemImageTypeSpec.setItemImageTypeName(itemImageTypeName);
                    }
                });

        When("^the user sets the item image type's name to the last item image type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastItemImageTypeName = persona.lastItemImageTypeName;
                    var deleteItemImageTypeForm = persona.deleteItemImageTypeForm;
                    var itemImageTypeSpec = persona.itemImageTypeSpec;

                    assertThat(deleteItemImageTypeForm != null || itemImageTypeSpec != null).isTrue();

                    if(deleteItemImageTypeForm != null) {
                        deleteItemImageTypeForm.setItemImageTypeName(lastItemImageTypeName);
                    } else {
                        itemImageTypeSpec.setItemImageTypeName(lastItemImageTypeName);
                    }
                });

        When("^the user sets the item image type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createItemImageTypeForm = persona.createItemImageTypeForm;
                    var itemImageTypeEdit = persona.itemImageTypeEdit;

                    assertThat(createItemImageTypeForm != null || itemImageTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createItemImageTypeForm != null) {
                        createItemImageTypeForm.setIsDefault(isDefault);
                    } else {
                        itemImageTypeEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the item image type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createItemImageTypeForm = persona.createItemImageTypeForm;
                    var itemImageTypeEdit = persona.itemImageTypeEdit;

                    assertThat(createItemImageTypeForm != null || itemImageTypeEdit != null).isTrue();

                    if(createItemImageTypeForm != null) {
                        createItemImageTypeForm.setSortOrder(sortOrder);
                    } else {
                        itemImageTypeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the item image type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createItemImageTypeForm = persona.createItemImageTypeForm;
                    var itemImageTypeEdit = persona.itemImageTypeEdit;

                    assertThat(createItemImageTypeForm != null || itemImageTypeEdit != null).isTrue();

                    if(createItemImageTypeForm != null) {
                        createItemImageTypeForm.setDescription(description);
                    } else {
                        itemImageTypeEdit.setDescription(description);
                    }
                });
    }

}
