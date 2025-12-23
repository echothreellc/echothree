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

package com.echothree.cucumber.core;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.CreateEntityListItemResult;
import com.echothree.control.user.core.common.result.EditEntityListItemResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class EntityListItemSteps implements En {

    public EntityListItemSteps() {
        When("^the user begins entering a new entity list item$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityListItemForm).isNull();
                    assertThat(persona.deleteEntityListItemForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.createEntityListItemForm = CoreUtil.getHome().getCreateEntityListItemForm();
                });

        When("^the user adds the new entity list item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createEntityListItemForm = persona.createEntityListItemForm;

                    assertThat(createEntityListItemForm).isNotNull();

                    var commandResult = CoreUtil.getHome().createEntityListItem(persona.userVisitPK, createEntityListItemForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateEntityListItemResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastEntityListItemName = commandResult.getHasErrors() ? null : result.getEntityListItemName();
                    persona.createEntityListItemForm = null;
                });

        When("^the user begins deleting an entity list item$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityListItemForm).isNull();
                    assertThat(persona.deleteEntityListItemForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteEntityListItemForm = CoreUtil.getHome().getDeleteEntityListItemForm();
                });

        When("^the user deletes the entity list item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteEntityListItemForm = persona.deleteEntityListItemForm;

                    assertThat(deleteEntityListItemForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteEntityListItem(persona.userVisitPK, deleteEntityListItemForm);

                    persona.deleteEntityListItemForm = null;
                });

        When("^the user begins specifying an entity list item to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityListItemForm).isNull();
                    assertThat(persona.deleteEntityListItemForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.entityListItemUniversalSpec = CoreUtil.getHome().getEntityListItemUniversalSpec();
                });

        When("^the user begins editing the entity list item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityListItemUniversalSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityListItemForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editEntityListItem(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditEntityListItemResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.entityListItemEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the entity list item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityListItemUniversalSpec;
                    var edit = persona.entityListItemEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityListItemForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CoreUtil.getHome().editEntityListItem(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.entityListItemUniversalSpec = null;
                    persona.entityListItemEdit = null;
                });
        
        When("^the user sets the entity list item's component vendor to ([a-zA-Z0-9-_]*)$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityListItemForm = persona.createEntityListItemForm;
                    var deleteEntityListItemForm = persona.deleteEntityListItemForm;
                    var entityListItemSpec = persona.entityListItemUniversalSpec;

                    assertThat(createEntityListItemForm != null || deleteEntityListItemForm != null || entityListItemSpec != null).isTrue();

                    if(createEntityListItemForm != null) {
                        createEntityListItemForm.setComponentVendorName(componentVendorName);
                    } else if(deleteEntityListItemForm != null) {
                        deleteEntityListItemForm.setComponentVendorName(componentVendorName);
                    } else {
                        entityListItemSpec.setComponentVendorName(componentVendorName);
                    }
                });

        When("^the user sets the entity list item's entity type to ([a-zA-Z0-9-_]*)$",
                (String entityTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityListItemForm = persona.createEntityListItemForm;
                    var deleteEntityListItemForm = persona.deleteEntityListItemForm;
                    var entityListItemSpec = persona.entityListItemUniversalSpec;

                    assertThat(createEntityListItemForm != null || deleteEntityListItemForm != null || entityListItemSpec != null).isTrue();

                    if(createEntityListItemForm != null) {
                        createEntityListItemForm.setEntityTypeName(entityTypeName);
                    } else if(deleteEntityListItemForm != null) {
                        deleteEntityListItemForm.setEntityTypeName(entityTypeName);
                    } else {
                        entityListItemSpec.setEntityTypeName(entityTypeName);
                    }
                });


        When("^the user sets the entity list item's entity attribute to ([a-zA-Z0-9-_]*)$",
                (String entityAttributeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityListItemForm = persona.createEntityListItemForm;
                    var deleteEntityListItemForm = persona.deleteEntityListItemForm;
                    var entityListItemSpec = persona.entityListItemUniversalSpec;

                    assertThat(createEntityListItemForm != null || deleteEntityListItemForm != null || entityListItemSpec != null).isTrue();

                    if(createEntityListItemForm != null) {
                        createEntityListItemForm.setEntityAttributeName(entityAttributeName);
                    } else if(deleteEntityListItemForm != null) {
                        deleteEntityListItemForm.setEntityAttributeName(entityAttributeName);
                    } else {
                        entityListItemSpec.setEntityAttributeName(entityAttributeName);
                    }
                });

        When("^the user sets the entity list item's entity attribute to the last entity attribute added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastEntityAttributeName = persona.lastEntityAttributeName;
                    var createEntityListItemForm = persona.createEntityListItemForm;
                    var deleteEntityListItemForm = persona.deleteEntityListItemForm;
                    var entityListItemSpec = persona.entityListItemUniversalSpec;

                    assertThat(createEntityListItemForm != null || deleteEntityListItemForm != null || entityListItemSpec != null).isTrue();

                    if(createEntityListItemForm != null) {
                        createEntityListItemForm.setEntityAttributeName(lastEntityAttributeName);
                    } else if(deleteEntityListItemForm != null) {
                        deleteEntityListItemForm.setEntityAttributeName(lastEntityAttributeName);
                    } else {
                        entityListItemSpec.setEntityAttributeName(lastEntityAttributeName);
                    }
                });

        When("^the user sets the entity list item's name to ([a-zA-Z0-9-_]*)$",
                (String entityListItemName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityListItemForm = persona.createEntityListItemForm;
                    var deleteEntityListItemForm = persona.deleteEntityListItemForm;
                    var entityListItemSpec = persona.entityListItemUniversalSpec;

                    assertThat(createEntityListItemForm != null || deleteEntityListItemForm != null || entityListItemSpec != null).isTrue();

                    if(createEntityListItemForm != null) {
                        createEntityListItemForm.setEntityListItemName(entityListItemName);
                    } else if(deleteEntityListItemForm != null) {
                        deleteEntityListItemForm.setEntityListItemName(entityListItemName);
                    } else {
                        entityListItemSpec.setEntityListItemName(entityListItemName);
                    }
                });

        When("^the user sets the entity list item's name to the last entity list item added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastEntityListItemName = persona.lastEntityListItemName;
                    var deleteEntityListItemForm = persona.deleteEntityListItemForm;
                    var entityListItemSpec = persona.entityListItemUniversalSpec;

                    assertThat(deleteEntityListItemForm != null || entityListItemSpec != null).isTrue();

                    if(deleteEntityListItemForm != null) {
                        deleteEntityListItemForm.setEntityListItemName(lastEntityListItemName);
                    } else {
                        entityListItemSpec.setEntityListItemName(lastEntityListItemName);
                    }
                });

        When("^the user sets the entity list item to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityListItemForm = persona.createEntityListItemForm;
                    var entityListItemEdit = persona.entityListItemEdit;

                    assertThat(createEntityListItemForm != null || entityListItemEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createEntityListItemForm != null) {
                        createEntityListItemForm.setIsDefault(isDefault);
                    } else {
                        entityListItemEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the entity list item's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityListItemForm = persona.createEntityListItemForm;
                    var entityListItemEdit = persona.entityListItemEdit;

                    assertThat(createEntityListItemForm != null || entityListItemEdit != null).isTrue();

                    if(createEntityListItemForm != null) {
                        createEntityListItemForm.setSortOrder(sortOrder);
                    } else {
                        entityListItemEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the entity list item's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityListItemForm = persona.createEntityListItemForm;
                    var entityListItemEdit = persona.entityListItemEdit;

                    assertThat(createEntityListItemForm != null || entityListItemEdit != null).isTrue();

                    if(createEntityListItemForm != null) {
                        createEntityListItemForm.setDescription(description);
                    } else {
                        entityListItemEdit.setDescription(description);
                    }
                });
    }

}
