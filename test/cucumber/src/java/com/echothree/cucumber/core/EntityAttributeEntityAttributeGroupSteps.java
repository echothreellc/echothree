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
import com.echothree.control.user.core.common.result.EditEntityAttributeEntityAttributeGroupResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class EntityAttributeEntityAttributeGroupSteps implements En {

    public EntityAttributeEntityAttributeGroupSteps() {
        When("^the user begins entering a new entity attribute entity attribute group$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAttributeEntityAttributeGroupForm).isNull();
                    assertThat(persona.deleteEntityAttributeEntityAttributeGroupForm).isNull();
                    assertThat(persona.entityAttributeEntityAttributeGroupSpec).isNull();

                    persona.createEntityAttributeEntityAttributeGroupForm = CoreUtil.getHome().getCreateEntityAttributeEntityAttributeGroupForm();
                });

        When("^the user adds the new entity attribute entity attribute group$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeEntityAttributeGroupForm = persona.createEntityAttributeEntityAttributeGroupForm;

                    assertThat(createEntityAttributeEntityAttributeGroupForm).isNotNull();

                    var commandResult = CoreUtil.getHome().createEntityAttributeEntityAttributeGroup(persona.userVisitPK, createEntityAttributeEntityAttributeGroupForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.createEntityAttributeEntityAttributeGroupForm = null;
                });

        When("^the user begins deleting an entity attribute entity attribute group$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAttributeEntityAttributeGroupForm).isNull();
                    assertThat(persona.deleteEntityAttributeEntityAttributeGroupForm).isNull();
                    assertThat(persona.entityAttributeEntityAttributeGroupSpec).isNull();

                    persona.deleteEntityAttributeEntityAttributeGroupForm = CoreUtil.getHome().getDeleteEntityAttributeEntityAttributeGroupForm();
                });

        When("^the user deletes the entity attribute entity attribute group$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteEntityAttributeEntityAttributeGroupForm = persona.deleteEntityAttributeEntityAttributeGroupForm;

                    assertThat(deleteEntityAttributeEntityAttributeGroupForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteEntityAttributeEntityAttributeGroup(persona.userVisitPK, deleteEntityAttributeEntityAttributeGroupForm);

                    persona.deleteEntityAttributeEntityAttributeGroupForm = null;
                });

        When("^the user begins specifying an entity attribute entity attribute group to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAttributeEntityAttributeGroupForm).isNull();
                    assertThat(persona.deleteEntityAttributeEntityAttributeGroupForm).isNull();
                    assertThat(persona.entityAttributeEntityAttributeGroupSpec).isNull();

                    persona.entityAttributeEntityAttributeGroupSpec = CoreUtil.getHome().getEntityAttributeEntityAttributeGroupSpec();
                });

        When("^the user begins editing the entity attribute entity attribute group$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityAttributeEntityAttributeGroupSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityAttributeEntityAttributeGroupForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editEntityAttributeEntityAttributeGroup(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditEntityAttributeEntityAttributeGroupResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.entityAttributeEntityAttributeGroupEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the entity attribute entity attribute group$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityAttributeEntityAttributeGroupSpec;
                    var edit = persona.entityAttributeEntityAttributeGroupEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityAttributeEntityAttributeGroupForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CoreUtil.getHome().editEntityAttributeEntityAttributeGroup(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.entityAttributeEntityAttributeGroupSpec = null;
                    persona.entityAttributeEntityAttributeGroupEdit = null;
                });

        When("^the user sets the entity attribute entity attribute group's component vendor to ([a-zA-Z0-9-_]*)$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeEntityAttributeGroupForm = persona.createEntityAttributeEntityAttributeGroupForm;
                    var deleteEntityAttributeEntityAttributeGroupForm = persona.deleteEntityAttributeEntityAttributeGroupForm;
                    var entityAttributeEntityAttributeGroupSpec = persona.entityAttributeEntityAttributeGroupSpec;

                    assertThat(createEntityAttributeEntityAttributeGroupForm != null || deleteEntityAttributeEntityAttributeGroupForm != null || entityAttributeEntityAttributeGroupSpec != null).isTrue();

                    if(createEntityAttributeEntityAttributeGroupForm != null) {
                        createEntityAttributeEntityAttributeGroupForm.setComponentVendorName(componentVendorName);
                    } else if(deleteEntityAttributeEntityAttributeGroupForm != null) {
                        deleteEntityAttributeEntityAttributeGroupForm.setComponentVendorName(componentVendorName);
                    } else {
                        entityAttributeEntityAttributeGroupSpec.setComponentVendorName(componentVendorName);
                    }
                });

        When("^the user sets the entity attribute entity attribute group's entity type to ([a-zA-Z0-9-_]*)$",
                (String entityTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeEntityAttributeGroupForm = persona.createEntityAttributeEntityAttributeGroupForm;
                    var deleteEntityAttributeEntityAttributeGroupForm = persona.deleteEntityAttributeEntityAttributeGroupForm;
                    var entityAttributeEntityAttributeGroupSpec = persona.entityAttributeEntityAttributeGroupSpec;

                    assertThat(createEntityAttributeEntityAttributeGroupForm != null || deleteEntityAttributeEntityAttributeGroupForm != null || entityAttributeEntityAttributeGroupSpec != null).isTrue();

                    if(createEntityAttributeEntityAttributeGroupForm != null) {
                        createEntityAttributeEntityAttributeGroupForm.setEntityTypeName(entityTypeName);
                    } else if(deleteEntityAttributeEntityAttributeGroupForm != null) {
                        deleteEntityAttributeEntityAttributeGroupForm.setEntityTypeName(entityTypeName);
                    } else {
                        entityAttributeEntityAttributeGroupSpec.setEntityTypeName(entityTypeName);
                    }
                });

        When("^the user sets the entity attribute entity attribute group's entity attribute to ([a-zA-Z0-9-_]*)$",
                (String entityAttributeEntityAttributeGroupName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeEntityAttributeGroupForm = persona.createEntityAttributeEntityAttributeGroupForm;
                    var deleteEntityAttributeEntityAttributeGroupForm = persona.deleteEntityAttributeEntityAttributeGroupForm;
                    var entityAttributeEntityAttributeGroupSpec = persona.entityAttributeEntityAttributeGroupSpec;

                    assertThat(createEntityAttributeEntityAttributeGroupForm != null || deleteEntityAttributeEntityAttributeGroupForm != null || entityAttributeEntityAttributeGroupSpec != null).isTrue();

                    if(createEntityAttributeEntityAttributeGroupForm != null) {
                        createEntityAttributeEntityAttributeGroupForm.setEntityAttributeName(entityAttributeEntityAttributeGroupName);
                    } else if(deleteEntityAttributeEntityAttributeGroupForm != null) {
                        deleteEntityAttributeEntityAttributeGroupForm.setEntityAttributeName(entityAttributeEntityAttributeGroupName);
                    } else {
                        entityAttributeEntityAttributeGroupSpec.setEntityAttributeName(entityAttributeEntityAttributeGroupName);
                    }
                });

        When("^the user sets the entity attribute entity attribute group's entity attribute to the last entity attribute added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastEntityAttributeEntityAttributeGroupName = persona.lastEntityAttributeName;
                    var createEntityAttributeEntityAttributeGroupForm = persona.createEntityAttributeEntityAttributeGroupForm;
                    var deleteEntityAttributeEntityAttributeGroupForm = persona.deleteEntityAttributeEntityAttributeGroupForm;
                    var entityAttributeEntityAttributeGroupSpec = persona.entityAttributeEntityAttributeGroupSpec;

                    assertThat(createEntityAttributeEntityAttributeGroupForm != null || deleteEntityAttributeEntityAttributeGroupForm != null || entityAttributeEntityAttributeGroupSpec != null).isTrue();

                    if(createEntityAttributeEntityAttributeGroupForm != null) {
                        createEntityAttributeEntityAttributeGroupForm.setEntityAttributeName(lastEntityAttributeEntityAttributeGroupName);
                    } else if(deleteEntityAttributeEntityAttributeGroupForm != null) {
                        deleteEntityAttributeEntityAttributeGroupForm.setEntityAttributeName(lastEntityAttributeEntityAttributeGroupName);
                    } else {
                        entityAttributeEntityAttributeGroupSpec.setEntityAttributeName(lastEntityAttributeEntityAttributeGroupName);
                    }
                });

        When("^the user sets the entity attribute entity attribute group's entity attribute group to ([a-zA-Z0-9-_]*)$",
                (String entityAttributeEntityAttributeGroupName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeEntityAttributeGroupForm = persona.createEntityAttributeEntityAttributeGroupForm;
                    var deleteEntityAttributeEntityAttributeGroupForm = persona.deleteEntityAttributeEntityAttributeGroupForm;
                    var entityAttributeEntityAttributeGroupSpec = persona.entityAttributeEntityAttributeGroupSpec;

                    assertThat(createEntityAttributeEntityAttributeGroupForm != null || deleteEntityAttributeEntityAttributeGroupForm != null || entityAttributeEntityAttributeGroupSpec != null).isTrue();

                    if(createEntityAttributeEntityAttributeGroupForm != null) {
                        createEntityAttributeEntityAttributeGroupForm.setEntityAttributeGroupName(entityAttributeEntityAttributeGroupName);
                    } else if(deleteEntityAttributeEntityAttributeGroupForm != null) {
                        deleteEntityAttributeEntityAttributeGroupForm.setEntityAttributeGroupName(entityAttributeEntityAttributeGroupName);
                    } else {
                        entityAttributeEntityAttributeGroupSpec.setEntityAttributeGroupName(entityAttributeEntityAttributeGroupName);
                    }
                });

        When("^the user sets the entity attribute entity attribute group's entity attribute group to the last entity attribute group added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastEntityAttributeGroupName = persona.lastEntityAttributeGroupName;
                    var createEntityAttributeEntityAttributeGroupForm = persona.createEntityAttributeEntityAttributeGroupForm;
                    var deleteEntityAttributeEntityAttributeGroupForm = persona.deleteEntityAttributeEntityAttributeGroupForm;
                    var entityAttributeEntityAttributeGroupSpec = persona.entityAttributeEntityAttributeGroupSpec;

                    assertThat(createEntityAttributeEntityAttributeGroupForm != null || deleteEntityAttributeEntityAttributeGroupForm != null || entityAttributeEntityAttributeGroupSpec != null).isTrue();

                    if(createEntityAttributeEntityAttributeGroupForm != null) {
                        createEntityAttributeEntityAttributeGroupForm.setEntityAttributeGroupName(lastEntityAttributeGroupName);
                    } else if(deleteEntityAttributeEntityAttributeGroupForm != null) {
                        deleteEntityAttributeEntityAttributeGroupForm.setEntityAttributeGroupName(lastEntityAttributeGroupName);
                    } else {
                        entityAttributeEntityAttributeGroupSpec.setEntityAttributeGroupName(lastEntityAttributeGroupName);
                    }
                });

        When("^the user sets the entity attribute entity attribute group's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeEntityAttributeGroupForm = persona.createEntityAttributeEntityAttributeGroupForm;
                    var entityAttributeEntityAttributeGroupEdit = persona.entityAttributeEntityAttributeGroupEdit;

                    assertThat(createEntityAttributeEntityAttributeGroupForm != null || entityAttributeEntityAttributeGroupEdit != null).isTrue();

                    if(createEntityAttributeEntityAttributeGroupForm != null) {
                        createEntityAttributeEntityAttributeGroupForm.setSortOrder(sortOrder);
                    } else {
                        entityAttributeEntityAttributeGroupEdit.setSortOrder(sortOrder);
                    }
                });
    }

}
