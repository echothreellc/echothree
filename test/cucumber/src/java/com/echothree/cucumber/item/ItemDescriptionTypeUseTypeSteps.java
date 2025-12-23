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
import com.echothree.control.user.item.common.result.CreateItemDescriptionTypeUseTypeResult;
import com.echothree.control.user.item.common.result.EditItemDescriptionTypeUseTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemDescriptionTypeUseTypeSteps implements En {

    public ItemDescriptionTypeUseTypeSteps() {
        When("^the user begins entering a new item description type use type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemDescriptionTypeUseTypeForm).isNull();
                    assertThat(persona.deleteItemDescriptionTypeUseTypeForm).isNull();
                    assertThat(persona.itemDescriptionTypeUseTypeSpec).isNull();

                    persona.createItemDescriptionTypeUseTypeForm = ItemUtil.getHome().getCreateItemDescriptionTypeUseTypeForm();
                });

        When("^the user adds the new item description type use type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createItemDescriptionTypeUseTypeForm = persona.createItemDescriptionTypeUseTypeForm;

                    assertThat(createItemDescriptionTypeUseTypeForm).isNotNull();

                    var commandResult = ItemUtil.getHome().createItemDescriptionTypeUseType(persona.userVisitPK, createItemDescriptionTypeUseTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateItemDescriptionTypeUseTypeResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastItemDescriptionTypeUseTypeName = commandResult.getHasErrors() ? null : result.getItemDescriptionTypeUseTypeName();
                    persona.createItemDescriptionTypeUseTypeForm = null;
                });

        When("^the user begins deleting a item description type use type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemDescriptionTypeUseTypeForm).isNull();
                    assertThat(persona.deleteItemDescriptionTypeUseTypeForm).isNull();
                    assertThat(persona.itemDescriptionTypeUseTypeSpec).isNull();

                    persona.deleteItemDescriptionTypeUseTypeForm = ItemUtil.getHome().getDeleteItemDescriptionTypeUseTypeForm();
                });

        When("^the user deletes the item description type use type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteItemDescriptionTypeUseTypeForm = persona.deleteItemDescriptionTypeUseTypeForm;

                    assertThat(deleteItemDescriptionTypeUseTypeForm).isNotNull();

                    LastCommandResult.commandResult = ItemUtil.getHome().deleteItemDescriptionTypeUseType(persona.userVisitPK, deleteItemDescriptionTypeUseTypeForm);

                    persona.deleteItemDescriptionTypeUseTypeForm = null;
                });

        When("^the user begins specifying a item description type use type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemDescriptionTypeUseTypeForm).isNull();
                    assertThat(persona.deleteItemDescriptionTypeUseTypeForm).isNull();
                    assertThat(persona.itemDescriptionTypeUseTypeSpec).isNull();

                    persona.itemDescriptionTypeUseTypeSpec = ItemUtil.getHome().getItemDescriptionTypeUseTypeUniversalSpec();
                });

        When("^the user begins editing the item description type use type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemDescriptionTypeUseTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemDescriptionTypeUseTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ItemUtil.getHome().editItemDescriptionTypeUseType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemDescriptionTypeUseTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.itemDescriptionTypeUseTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the item description type use type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemDescriptionTypeUseTypeSpec;
                    var edit = persona.itemDescriptionTypeUseTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemDescriptionTypeUseTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ItemUtil.getHome().editItemDescriptionTypeUseType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.itemDescriptionTypeUseTypeSpec = null;
                    persona.itemDescriptionTypeUseTypeEdit = null;
                });
        
        When("^the user sets the item description type use type's name to ([a-zA-Z0-9-_]*)$",
                (String itemDescriptionTypeUseTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemDescriptionTypeUseTypeForm = persona.createItemDescriptionTypeUseTypeForm;
                    var deleteItemDescriptionTypeUseTypeForm = persona.deleteItemDescriptionTypeUseTypeForm;
                    var itemDescriptionTypeUseTypeSpec = persona.itemDescriptionTypeUseTypeSpec;

                    assertThat(createItemDescriptionTypeUseTypeForm != null || deleteItemDescriptionTypeUseTypeForm != null || itemDescriptionTypeUseTypeSpec != null).isTrue();

                    if(createItemDescriptionTypeUseTypeForm != null) {
                        createItemDescriptionTypeUseTypeForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
                    } else if(deleteItemDescriptionTypeUseTypeForm != null) {
                        deleteItemDescriptionTypeUseTypeForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
                    } else {
                        itemDescriptionTypeUseTypeSpec.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
                    }
                });

        When("^the user sets the item description type use type's name to the last item description type use type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastItemDescriptionTypeUseTypeName = persona.lastItemDescriptionTypeUseTypeName;
                    var deleteItemDescriptionTypeUseTypeForm = persona.deleteItemDescriptionTypeUseTypeForm;
                    var itemDescriptionTypeUseTypeSpec = persona.itemDescriptionTypeUseTypeSpec;

                    assertThat(deleteItemDescriptionTypeUseTypeForm != null || itemDescriptionTypeUseTypeSpec != null).isTrue();

                    if(deleteItemDescriptionTypeUseTypeForm != null) {
                        deleteItemDescriptionTypeUseTypeForm.setItemDescriptionTypeUseTypeName(lastItemDescriptionTypeUseTypeName);
                    } else {
                        itemDescriptionTypeUseTypeSpec.setItemDescriptionTypeUseTypeName(lastItemDescriptionTypeUseTypeName);
                    }
                });

        When("^the user sets the item description type use type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createItemDescriptionTypeUseTypeForm = persona.createItemDescriptionTypeUseTypeForm;
                    var itemDescriptionTypeUseTypeEdit = persona.itemDescriptionTypeUseTypeEdit;

                    assertThat(createItemDescriptionTypeUseTypeForm != null || itemDescriptionTypeUseTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createItemDescriptionTypeUseTypeForm != null) {
                        createItemDescriptionTypeUseTypeForm.setIsDefault(isDefault);
                    } else {
                        itemDescriptionTypeUseTypeEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the item description type use type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createItemDescriptionTypeUseTypeForm = persona.createItemDescriptionTypeUseTypeForm;
                    var itemDescriptionTypeUseTypeEdit = persona.itemDescriptionTypeUseTypeEdit;

                    assertThat(createItemDescriptionTypeUseTypeForm != null || itemDescriptionTypeUseTypeEdit != null).isTrue();

                    if(createItemDescriptionTypeUseTypeForm != null) {
                        createItemDescriptionTypeUseTypeForm.setSortOrder(sortOrder);
                    } else {
                        itemDescriptionTypeUseTypeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the item description type use type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createItemDescriptionTypeUseTypeForm = persona.createItemDescriptionTypeUseTypeForm;
                    var itemDescriptionTypeUseTypeEdit = persona.itemDescriptionTypeUseTypeEdit;

                    assertThat(createItemDescriptionTypeUseTypeForm != null || itemDescriptionTypeUseTypeEdit != null).isTrue();

                    if(createItemDescriptionTypeUseTypeForm != null) {
                        createItemDescriptionTypeUseTypeForm.setDescription(description);
                    } else {
                        itemDescriptionTypeUseTypeEdit.setDescription(description);
                    }
                });
    }

}
