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
import com.echothree.control.user.core.common.result.EditEntityAliasResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class EntityAliasSteps implements En {

    public EntityAliasSteps() {
        When("^the user begins entering a new entity alias$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAliasForm).isNull();
                    assertThat(persona.deleteEntityAliasForm).isNull();
                    assertThat(persona.entityAliasSpec).isNull();

                    persona.createEntityAliasForm = CoreUtil.getHome().getCreateEntityAliasForm();
                });

        When("^the user adds the new entity alias$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAliasForm = persona.createEntityAliasForm;

                    assertThat(createEntityAliasForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().createEntityAlias(persona.userVisitPK, createEntityAliasForm);

                    persona.createEntityAliasForm = null;
                });

        When("^the user begins deleting an entity alias$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAliasForm).isNull();
                    assertThat(persona.deleteEntityAliasForm).isNull();
                    assertThat(persona.entityAliasSpec).isNull();

                    persona.deleteEntityAliasForm = CoreUtil.getHome().getDeleteEntityAliasForm();
                });

        When("^the user deletes the entity alias$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteEntityAliasForm = persona.deleteEntityAliasForm;

                    assertThat(deleteEntityAliasForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteEntityAlias(persona.userVisitPK, deleteEntityAliasForm);

                    persona.deleteEntityAliasForm = null;
                });

        When("^the user begins specifying an entity alias to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAliasForm).isNull();
                    assertThat(persona.deleteEntityAliasForm).isNull();
                    assertThat(persona.entityAliasSpec).isNull();

                    persona.entityAliasSpec = CoreUtil.getHome().getEntityAliasSpec();
                });

        When("^the user begins editing the entity alias$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityAliasSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityAliasForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editEntityAlias(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditEntityAliasResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.entityAliasEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the entity alias$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityAliasSpec;
                    var edit = persona.entityAliasEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityAliasForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CoreUtil.getHome().editEntityAlias(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.entityAliasSpec = null;
                    persona.entityAliasEdit = null;
                });

        When("^the user sets the entity alias's entity to \"([^\"]*)\"$",
                (String setEntityRef) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAliasForm = persona.createEntityAliasForm;
                    var deleteEntityAliasForm = persona.deleteEntityAliasForm;
                    var entityAliasSpec = persona.entityAliasSpec;

                    assertThat(createEntityAliasForm != null || deleteEntityAliasForm != null || entityAliasSpec != null).isTrue();

                    if(createEntityAliasForm != null) {
                        createEntityAliasForm.setEntityRef(setEntityRef);
                    } else if(deleteEntityAliasForm != null) {
                        deleteEntityAliasForm.setEntityRef(setEntityRef);
                    } else {
                        entityAliasSpec.setEntityRef(setEntityRef);
                    }
                });

        When("^the user sets the entity alias's entity to the last entity added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastEntityRef = persona.lastEntityRef;
                    var createEntityAliasForm = persona.createEntityAliasForm;
                    var deleteEntityAliasForm = persona.deleteEntityAliasForm;
                    var entityAliasSpec = persona.entityAliasSpec;

                    assertThat(createEntityAliasForm != null || deleteEntityAliasForm != null || entityAliasSpec != null).isTrue();

                    if(createEntityAliasForm != null) {
                        createEntityAliasForm.setEntityRef(lastEntityRef);
                    } else if(deleteEntityAliasForm != null) {
                        deleteEntityAliasForm.setEntityRef(lastEntityRef);
                    } else {
                        entityAliasSpec.setEntityRef(lastEntityRef);
                    }
                });

        When("^the user sets the entity alias's entity alias type to \"([^\"]*)\"$",
                (String entityAliasTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAliasForm = persona.createEntityAliasForm;
                    var deleteEntityAliasForm = persona.deleteEntityAliasForm;
                    var entityAliasSpec = persona.entityAliasSpec;

                    assertThat(createEntityAliasForm != null || deleteEntityAliasForm != null || entityAliasSpec != null).isTrue();

                    if(createEntityAliasForm != null) {
                        createEntityAliasForm.setEntityAliasTypeName(entityAliasTypeName);
                    } else if(deleteEntityAliasForm != null) {
                        deleteEntityAliasForm.setEntityAliasTypeName(entityAliasTypeName);
                    } else {
                        entityAliasSpec.setEntityAliasTypeName(entityAliasTypeName);
                    }
                });

        When("^the user sets the entity alias's entity alias type to the last entity alias type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastEntityAliasTypeName = persona.lastEntityAliasTypeName;
                    var createEntityAliasForm = persona.createEntityAliasForm;
                    var deleteEntityAliasForm = persona.deleteEntityAliasForm;
                    var entityAliasSpec = persona.entityAliasSpec;

                    assertThat(createEntityAliasForm != null || deleteEntityAliasForm != null || entityAliasSpec != null).isTrue();

                    if(createEntityAliasForm != null) {
                        createEntityAliasForm.setEntityAliasTypeName(lastEntityAliasTypeName);
                    } else if(deleteEntityAliasForm != null) {
                        deleteEntityAliasForm.setEntityAliasTypeName(lastEntityAliasTypeName);
                    } else {
                        entityAliasSpec.setEntityAliasTypeName(lastEntityAliasTypeName);
                    }
                });

        When("^the user sets the entity alias's alias to \"([^\"]*)\"$",
                (String alias) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAliasForm = persona.createEntityAliasForm;
                    var entityAliasEdit = persona.entityAliasEdit;

                    assertThat(createEntityAliasForm != null || entityAliasEdit != null).isTrue();

                    if(createEntityAliasForm != null) {
                        createEntityAliasForm.setAlias(alias);
                    } else {
                        entityAliasEdit.setAlias(alias);
                    }
                });

    }

}
