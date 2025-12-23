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
import com.echothree.control.user.item.common.result.CreateItemAliasTypeResult;
import com.echothree.control.user.item.common.result.EditItemAliasTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemAliasTypeSteps implements En {

    public ItemAliasTypeSteps() {
        When("^the user begins entering a new item alias type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemAliasTypeForm).isNull();
                    assertThat(persona.deleteItemAliasTypeForm).isNull();
                    assertThat(persona.itemAliasTypeSpec).isNull();

                    persona.createItemAliasTypeForm = ItemUtil.getHome().getCreateItemAliasTypeForm();
                });

        When("^the user adds the new item alias type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasTypeForm = persona.createItemAliasTypeForm;

                    assertThat(createItemAliasTypeForm).isNotNull();

                    var commandResult = ItemUtil.getHome().createItemAliasType(persona.userVisitPK, createItemAliasTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateItemAliasTypeResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastItemAliasTypeName = commandResult.getHasErrors() ? null : result.getItemAliasTypeName();
                    persona.createItemAliasTypeForm = null;
                });

        When("^the user begins deleting a item alias type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemAliasTypeForm).isNull();
                    assertThat(persona.deleteItemAliasTypeForm).isNull();
                    assertThat(persona.itemAliasTypeSpec).isNull();

                    persona.deleteItemAliasTypeForm = ItemUtil.getHome().getDeleteItemAliasTypeForm();
                });

        When("^the user deletes the item alias type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteItemAliasTypeForm = persona.deleteItemAliasTypeForm;

                    assertThat(deleteItemAliasTypeForm).isNotNull();

                    LastCommandResult.commandResult = ItemUtil.getHome().deleteItemAliasType(persona.userVisitPK, deleteItemAliasTypeForm);

                    persona.deleteItemAliasTypeForm = null;
                });

        When("^the user begins specifying a item alias type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemAliasTypeForm).isNull();
                    assertThat(persona.deleteItemAliasTypeForm).isNull();
                    assertThat(persona.itemAliasTypeSpec).isNull();

                    persona.itemAliasTypeSpec = ItemUtil.getHome().getItemAliasTypeUniversalSpec();
                });

        When("^the user begins editing the item alias type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemAliasTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemAliasTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ItemUtil.getHome().editItemAliasType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemAliasTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.itemAliasTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the item alias type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.itemAliasTypeSpec;
                    var edit = persona.itemAliasTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ItemUtil.getHome().getEditItemAliasTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = ItemUtil.getHome().editItemAliasType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.itemAliasTypeSpec = null;
                    persona.itemAliasTypeEdit = null;
                });
        
        When("^the user sets the item alias type's name to ([a-zA-Z0-9-_]*)$",
                (String itemAliasTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasTypeForm = persona.createItemAliasTypeForm;
                    var deleteItemAliasTypeForm = persona.deleteItemAliasTypeForm;
                    var itemAliasTypeSpec = persona.itemAliasTypeSpec;

                    assertThat(createItemAliasTypeForm != null || deleteItemAliasTypeForm != null || itemAliasTypeSpec != null).isTrue();

                    if(createItemAliasTypeForm != null) {
                        createItemAliasTypeForm.setItemAliasTypeName(itemAliasTypeName);
                    } else if(deleteItemAliasTypeForm != null) {
                        deleteItemAliasTypeForm.setItemAliasTypeName(itemAliasTypeName);
                    } else {
                        itemAliasTypeSpec.setItemAliasTypeName(itemAliasTypeName);
                    }
                });

        When("^the user sets the item alias type's name to the last item alias type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastItemAliasTypeName = persona.lastItemAliasTypeName;
                    var deleteItemAliasTypeForm = persona.deleteItemAliasTypeForm;
                    var itemAliasTypeSpec = persona.itemAliasTypeSpec;

                    assertThat(deleteItemAliasTypeForm != null || itemAliasTypeSpec != null).isTrue();

                    if(deleteItemAliasTypeForm != null) {
                        deleteItemAliasTypeForm.setItemAliasTypeName(lastItemAliasTypeName);
                    } else {
                        itemAliasTypeSpec.setItemAliasTypeName(lastItemAliasTypeName);
                    }
                });

        When("^the user sets the item alias type's validation pattern to \"([^\"]*)\"$",
                (String validationPattern) -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasTypeForm = persona.createItemAliasTypeForm;
                    var itemAliasTypeEdit = persona.itemAliasTypeEdit;

                    assertThat(createItemAliasTypeForm != null || itemAliasTypeEdit != null).isTrue();

                    if(createItemAliasTypeForm != null) {
                        createItemAliasTypeForm.setValidationPattern(validationPattern);
                    } else {
                        itemAliasTypeEdit.setValidationPattern(validationPattern);
                    }
                });

        When("^the user sets the item alias type's checksum type to \"([^\"]*)\"$",
                (String itemAliasChecksumTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasTypeForm = persona.createItemAliasTypeForm;
                    var itemAliasTypeEdit = persona.itemAliasTypeEdit;

                    assertThat(createItemAliasTypeForm != null || itemAliasTypeEdit != null).isTrue();

                    if(createItemAliasTypeForm != null) {
                        createItemAliasTypeForm.setItemAliasChecksumTypeName(itemAliasChecksumTypeName);
                    } else {
                        itemAliasTypeEdit.setItemAliasChecksumTypeName(itemAliasChecksumTypeName);
                    }
                });

        When("^the user sets the item alias type to (allow|not allow) multiple values per item$",
                (String allowMultiple) -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasTypeForm = persona.createItemAliasTypeForm;
                    var itemAliasTypeEdit = persona.itemAliasTypeEdit;

                    assertThat(createItemAliasTypeForm != null || itemAliasTypeEdit != null).isTrue();

                    allowMultiple = Boolean.valueOf(allowMultiple.equals("allow")).toString();
                    if(createItemAliasTypeForm != null) {
                        createItemAliasTypeForm.setAllowMultiple(allowMultiple);
                    } else {
                        itemAliasTypeEdit.setIsDefault(allowMultiple);
                    }
                });

        When("^the user sets the item alias type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasTypeForm = persona.createItemAliasTypeForm;
                    var itemAliasTypeEdit = persona.itemAliasTypeEdit;

                    assertThat(createItemAliasTypeForm != null || itemAliasTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createItemAliasTypeForm != null) {
                        createItemAliasTypeForm.setIsDefault(isDefault);
                    } else {
                        itemAliasTypeEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the item alias type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasTypeForm = persona.createItemAliasTypeForm;
                    var itemAliasTypeEdit = persona.itemAliasTypeEdit;

                    assertThat(createItemAliasTypeForm != null || itemAliasTypeEdit != null).isTrue();

                    if(createItemAliasTypeForm != null) {
                        createItemAliasTypeForm.setSortOrder(sortOrder);
                    } else {
                        itemAliasTypeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the item alias type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createItemAliasTypeForm = persona.createItemAliasTypeForm;
                    var itemAliasTypeEdit = persona.itemAliasTypeEdit;

                    assertThat(createItemAliasTypeForm != null || itemAliasTypeEdit != null).isTrue();

                    if(createItemAliasTypeForm != null) {
                        createItemAliasTypeForm.setDescription(description);
                    } else {
                        itemAliasTypeEdit.setDescription(description);
                    }
                });
    }

}
