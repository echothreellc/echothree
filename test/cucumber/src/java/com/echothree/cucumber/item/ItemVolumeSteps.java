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
import com.echothree.control.user.item.common.result.EditItemVolumeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemVolumeSteps implements En {

    public ItemVolumeSteps() {
        When("^the user begins entering a new item volume$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemVolumeForm).isNull();
                    assertThat(persona.deleteItemVolumeForm).isNull();
                    assertThat(persona.itemVolumeSpec).isNull();

                    persona.createItemVolumeForm = ItemUtil.getHome().getCreateItemVolumeForm();
                });

        When("^the user adds the new item volume$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeForm = persona.createItemVolumeForm;

                    assertThat(createItemVolumeForm).isNotNull();

                    var commandResult = ItemUtil.getHome().createItemVolume(persona.userVisitPK, createItemVolumeForm);
                    LastCommandResult.commandResult = commandResult;

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();

                    persona.createItemVolumeForm = null;
                });

        When("^the user begins deleting an item volume$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemVolumeForm).isNull();
                    assertThat(persona.deleteItemVolumeForm).isNull();
                    assertThat(persona.itemVolumeSpec).isNull();

                    persona.deleteItemVolumeForm = ItemUtil.getHome().getDeleteItemVolumeForm();
                });

        When("^the user deletes the item volume$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteItemVolumeForm = persona.deleteItemVolumeForm;

                    assertThat(deleteItemVolumeForm).isNotNull();

                    LastCommandResult.commandResult = ItemUtil.getHome().deleteItemVolume(persona.userVisitPK, deleteItemVolumeForm);

                    persona.deleteItemVolumeForm = null;
                });

        When("^the user begins specifying an item volume to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemVolumeForm).isNull();
                    assertThat(persona.deleteItemVolumeForm).isNull();
                    assertThat(persona.itemVolumeSpec).isNull();

                    persona.itemVolumeSpec = ItemUtil.getHome().getItemVolumeSpec();
                });

        When("^the user begins editing the item volume$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemVolumeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemVolumeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ItemUtil.getHome().editItemVolume(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemVolumeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.itemVolumeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the item volume$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemVolumeSpec;
                    var edit = persona.itemVolumeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemVolumeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = ItemUtil.getHome().editItemVolume(persona.userVisitPK, commandForm);

                    persona.itemVolumeSpec = null;
                    persona.itemVolumeEdit = null;
                });

        When("^the user sets the item volume's item to \"([^\"]*)\"$",
                (String itemName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeForm = persona.createItemVolumeForm;
                    var deleteItemVolumeForm = persona.deleteItemVolumeForm;
                    var itemVolumeSpec = persona.itemVolumeSpec;

                    assertThat(createItemVolumeForm != null || deleteItemVolumeForm != null || itemVolumeSpec != null).isTrue();

                    if(createItemVolumeForm != null) {
                        createItemVolumeForm.setItemName(itemName);
                    } else if(deleteItemVolumeForm != null) {
                        deleteItemVolumeForm.setItemName(itemName);
                    } else {
                        itemVolumeSpec.setItemName(itemName);
                    }
                });

        When("^the user sets the item volume's item to the last item added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastItemName = persona.lastItemName;
                    var createItemVolumeForm = persona.createItemVolumeForm;
                    var deleteItemVolumeForm = persona.deleteItemVolumeForm;
                    var itemVolumeSpec = persona.itemVolumeSpec;

                    assertThat(createItemVolumeForm != null || deleteItemVolumeForm != null || itemVolumeSpec != null).isTrue();
                    assertThat(lastItemName).isNotNull();

                    if(createItemVolumeForm != null) {
                        createItemVolumeForm.setItemName(lastItemName);
                    } else if(deleteItemVolumeForm != null) {
                        deleteItemVolumeForm.setItemName(lastItemName);
                    } else {
                        itemVolumeSpec.setItemName(lastItemName);
                    }
                });

        When("^the user sets the item volume's unit of measure type to \"([^\"]*)\"$",
                (String unitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeForm = persona.createItemVolumeForm;
                    var deleteItemVolumeForm = persona.deleteItemVolumeForm;
                    var itemVolumeSpec = persona.itemVolumeSpec;

                    assertThat(createItemVolumeForm != null || deleteItemVolumeForm != null || itemVolumeSpec != null).isTrue();

                    if(createItemVolumeForm != null) {
                        createItemVolumeForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else if(deleteItemVolumeForm != null) {
                        deleteItemVolumeForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else {
                        itemVolumeSpec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    }
                });

        When("^the user sets the item volume's unit of measure type to the last unit of measure type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastUnitOfMeasureTypeName = persona.lastUnitOfMeasureTypeName;
                    var createItemVolumeForm = persona.createItemVolumeForm;
                    var deleteItemVolumeForm = persona.deleteItemVolumeForm;
                    var itemVolumeSpec = persona.itemVolumeSpec;

                    assertThat(createItemVolumeForm != null || deleteItemVolumeForm != null || itemVolumeSpec != null).isTrue();
                    assertThat(lastUnitOfMeasureTypeName).isNotNull();

                    if(createItemVolumeForm != null) {
                        createItemVolumeForm.setUnitOfMeasureTypeName(lastUnitOfMeasureTypeName);
                    } else if(deleteItemVolumeForm != null) {
                        deleteItemVolumeForm.setUnitOfMeasureTypeName(lastUnitOfMeasureTypeName);
                    } else {
                        itemVolumeSpec.setUnitOfMeasureTypeName(lastUnitOfMeasureTypeName);
                    }
                });

        When("^the user sets the item volume's item volume type to \"([^\"]*)\"$",
                (String itemVolumeTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeForm = persona.createItemVolumeForm;
                    var deleteItemVolumeForm = persona.deleteItemVolumeForm;
                    var itemVolumeSpec = persona.itemVolumeSpec;

                    assertThat(createItemVolumeForm != null || deleteItemVolumeForm != null || itemVolumeSpec != null).isTrue();

                    if(createItemVolumeForm != null) {
                        createItemVolumeForm.setItemVolumeTypeName(itemVolumeTypeName);
                    } else if(deleteItemVolumeForm != null) {
                        deleteItemVolumeForm.setItemVolumeTypeName(itemVolumeTypeName);
                    } else {
                        itemVolumeSpec.setItemVolumeTypeName(itemVolumeTypeName);
                    }
                });

        When("^the user sets the item volume's item volume type to the last item volume type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastItemVolumeTypeName = persona.lastItemVolumeTypeName;
                    var createItemVolumeForm = persona.createItemVolumeForm;
                    var deleteItemVolumeForm = persona.deleteItemVolumeForm;
                    var itemVolumeSpec = persona.itemVolumeSpec;

                    assertThat(createItemVolumeForm != null || deleteItemVolumeForm != null || itemVolumeSpec != null).isTrue();
                    assertThat(lastItemVolumeTypeName).isNotNull();

                    if(createItemVolumeForm != null) {
                        createItemVolumeForm.setItemVolumeTypeName(lastItemVolumeTypeName);
                    } else if(deleteItemVolumeForm != null) {
                        deleteItemVolumeForm.setItemVolumeTypeName(lastItemVolumeTypeName);
                    } else {
                        itemVolumeSpec.setItemVolumeTypeName(lastItemVolumeTypeName);
                    }
                });

        When("^the user sets the item volume's height unit of measure type to \"([^\"]*)\"$",
                (String volumeUnitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeForm = persona.createItemVolumeForm;
                    var itemVolumeEdit = persona.itemVolumeEdit;

                    assertThat(createItemVolumeForm != null || itemVolumeEdit != null).isTrue();

                    if(createItemVolumeForm != null) {
                        createItemVolumeForm.setHeightUnitOfMeasureTypeName(volumeUnitOfMeasureTypeName);
                    } else {
                        itemVolumeEdit.setHeightUnitOfMeasureTypeName(volumeUnitOfMeasureTypeName);
                    }
                });

        When("^the user sets the item volume's height to \"([^\"]*)\"$",
                (String height) -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeForm = persona.createItemVolumeForm;
                    var itemVolumeEdit = persona.itemVolumeEdit;

                    assertThat(createItemVolumeForm != null || itemVolumeEdit != null).isTrue();

                    if(createItemVolumeForm != null) {
                        createItemVolumeForm.setHeight(height);
                    } else {
                        itemVolumeEdit.setHeight(height);
                    }
                });

        When("^the user sets the item volume's width unit of measure type to \"([^\"]*)\"$",
                (String volumeUnitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeForm = persona.createItemVolumeForm;
                    var itemVolumeEdit = persona.itemVolumeEdit;

                    assertThat(createItemVolumeForm != null || itemVolumeEdit != null).isTrue();

                    if(createItemVolumeForm != null) {
                        createItemVolumeForm.setWidthUnitOfMeasureTypeName(volumeUnitOfMeasureTypeName);
                    } else {
                        itemVolumeEdit.setWidthUnitOfMeasureTypeName(volumeUnitOfMeasureTypeName);
                    }
                });

        When("^the user sets the item volume's width to \"([^\"]*)\"$",
                (String width) -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeForm = persona.createItemVolumeForm;
                    var itemVolumeEdit = persona.itemVolumeEdit;

                    assertThat(createItemVolumeForm != null || itemVolumeEdit != null).isTrue();

                    if(createItemVolumeForm != null) {
                        createItemVolumeForm.setWidth(width);
                    } else {
                        itemVolumeEdit.setWidth(width);
                    }
                });

        When("^the user sets the item volume's depth unit of measure type to \"([^\"]*)\"$",
                (String volumeUnitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeForm = persona.createItemVolumeForm;
                    var itemVolumeEdit = persona.itemVolumeEdit;

                    assertThat(createItemVolumeForm != null || itemVolumeEdit != null).isTrue();

                    if(createItemVolumeForm != null) {
                        createItemVolumeForm.setDepthUnitOfMeasureTypeName(volumeUnitOfMeasureTypeName);
                    } else {
                        itemVolumeEdit.setDepthUnitOfMeasureTypeName(volumeUnitOfMeasureTypeName);
                    }
                });

        When("^the user sets the item volume's depth to \"([^\"]*)\"$",
                (String depth) -> {
                    var persona = CurrentPersona.persona;
                    var createItemVolumeForm = persona.createItemVolumeForm;
                    var itemVolumeEdit = persona.itemVolumeEdit;

                    assertThat(createItemVolumeForm != null || itemVolumeEdit != null).isTrue();

                    if(createItemVolumeForm != null) {
                        createItemVolumeForm.setDepth(depth);
                    } else {
                        itemVolumeEdit.setDepth(depth);
                    }
                });
    }

}
