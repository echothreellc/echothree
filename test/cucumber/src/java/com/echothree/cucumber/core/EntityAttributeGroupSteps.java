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
import com.echothree.control.user.core.common.result.CreateEntityAttributeGroupResult;
import com.echothree.control.user.core.common.result.EditEntityAttributeGroupResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class EntityAttributeGroupSteps implements En {

    public EntityAttributeGroupSteps() {
        When("^the user begins entering a new entity attribute group$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAttributeGroupForm).isNull();
                    assertThat(persona.deleteEntityAttributeGroupForm).isNull();
                    assertThat(persona.entityAttributeGroupSpec).isNull();

                    persona.createEntityAttributeGroupForm = CoreUtil.getHome().getCreateEntityAttributeGroupForm();
                });

        When("^the user adds the new entity attribute group$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeGroupForm = persona.createEntityAttributeGroupForm;

                    assertThat(createEntityAttributeGroupForm).isNotNull();

                    var commandResult = CoreUtil.getHome().createEntityAttributeGroup(persona.userVisitPK, createEntityAttributeGroupForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateEntityAttributeGroupResult)executionResult.getResult();

                    assertThat(persona).isNotNull();
                    assertThat(commandResult).isNotNull();
                    assertThat(result).isNotNull();

                    persona.lastEntityAttributeGroupName = commandResult.getHasErrors() ? null : result.getEntityAttributeGroupName();
                    persona.createEntityAttributeGroupForm = null;
                });

        When("^the user begins deleting an entity attribute group$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAttributeGroupForm).isNull();
                    assertThat(persona.deleteEntityAttributeGroupForm).isNull();
                    assertThat(persona.entityAttributeGroupSpec).isNull();

                    persona.deleteEntityAttributeGroupForm = CoreUtil.getHome().getDeleteEntityAttributeGroupForm();
                });

        When("^the user deletes the entity attribute group$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteEntityAttributeGroupForm = persona.deleteEntityAttributeGroupForm;

                    assertThat(deleteEntityAttributeGroupForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteEntityAttributeGroup(persona.userVisitPK, deleteEntityAttributeGroupForm);

                    persona.deleteEntityAttributeGroupForm = null;
                });

        When("^the user begins specifying an entity attribute group to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAttributeGroupForm).isNull();
                    assertThat(persona.deleteEntityAttributeGroupForm).isNull();
                    assertThat(persona.entityAttributeGroupSpec).isNull();

                    persona.entityAttributeGroupSpec = CoreUtil.getHome().getEntityAttributeGroupSpec();
                });

        When("^the user begins editing the entity attribute group$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityAttributeGroupSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityAttributeGroupForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editEntityAttributeGroup(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditEntityAttributeGroupResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.entityAttributeGroupEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the entity attribute group$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityAttributeGroupSpec;
                    var edit = persona.entityAttributeGroupEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityAttributeGroupForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CoreUtil.getHome().editEntityAttributeGroup(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.entityAttributeGroupSpec = null;
                    persona.entityAttributeGroupEdit = null;
                });
        
        When("^the user sets the entity attribute group's name to ([a-zA-Z0-9-_]*)$",
                (String entityAttributeGroupName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeGroupForm = persona.createEntityAttributeGroupForm;
                    var deleteEntityAttributeGroupForm = persona.deleteEntityAttributeGroupForm;
                    var entityAttributeGroupSpec = persona.entityAttributeGroupSpec;

                    assertThat(createEntityAttributeGroupForm != null || deleteEntityAttributeGroupForm != null || entityAttributeGroupSpec != null).isTrue();

                    if(createEntityAttributeGroupForm != null) {
                        createEntityAttributeGroupForm.setEntityAttributeGroupName(entityAttributeGroupName);
                    } else if(deleteEntityAttributeGroupForm != null) {
                        deleteEntityAttributeGroupForm.setEntityAttributeGroupName(entityAttributeGroupName);
                    } else {
                        entityAttributeGroupSpec.setEntityAttributeGroupName(entityAttributeGroupName);
                    }
                });

        When("^the user sets the entity attribute group's name to the last entity attribute group added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastEntityAttributeGroupName = persona.lastEntityAttributeGroupName;
                    var deleteEntityAttributeGroupForm = persona.deleteEntityAttributeGroupForm;
                    var entityAttributeGroupSpec = persona.entityAttributeGroupSpec;

                    assertThat(deleteEntityAttributeGroupForm != null || entityAttributeGroupSpec != null).isTrue();

                    if(deleteEntityAttributeGroupForm != null) {
                        deleteEntityAttributeGroupForm.setEntityAttributeGroupName(lastEntityAttributeGroupName);
                    } else {
                        entityAttributeGroupSpec.setEntityAttributeGroupName(lastEntityAttributeGroupName);
                    }
                });

        When("^the user sets the entity attribute group to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeGroupForm = persona.createEntityAttributeGroupForm;
                    var entityAttributeGroupEdit = persona.entityAttributeGroupEdit;

                    assertThat(createEntityAttributeGroupForm != null || entityAttributeGroupEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createEntityAttributeGroupForm != null) {
                        createEntityAttributeGroupForm.setIsDefault(isDefault);
                    } else {
                        entityAttributeGroupEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the entity attribute group's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeGroupForm = persona.createEntityAttributeGroupForm;
                    var entityAttributeGroupEdit = persona.entityAttributeGroupEdit;

                    assertThat(createEntityAttributeGroupForm != null || entityAttributeGroupEdit != null).isTrue();

                    if(createEntityAttributeGroupForm != null) {
                        createEntityAttributeGroupForm.setSortOrder(sortOrder);
                    } else {
                        entityAttributeGroupEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the entity attribute group's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeGroupForm = persona.createEntityAttributeGroupForm;
                    var entityAttributeGroupEdit = persona.entityAttributeGroupEdit;

                    assertThat(createEntityAttributeGroupForm != null || entityAttributeGroupEdit != null).isTrue();

                    if(createEntityAttributeGroupForm != null) {
                        createEntityAttributeGroupForm.setDescription(description);
                    } else {
                        entityAttributeGroupEdit.setDescription(description);
                    }
                });
    }

}
