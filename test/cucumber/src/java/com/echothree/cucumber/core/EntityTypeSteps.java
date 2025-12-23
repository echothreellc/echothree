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
import com.echothree.control.user.core.common.result.CreateEntityTypeResult;
import com.echothree.control.user.core.common.result.EditEntityTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class EntityTypeSteps implements En {

    public EntityTypeSteps() {
        When("^the user begins entering a new entity type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityTypeForm).isNull();
                    assertThat(persona.deleteEntityTypeForm).isNull();
                    assertThat(persona.entityTypeSpec).isNull();

                    persona.createEntityTypeForm = CoreUtil.getHome().getCreateEntityTypeForm();
                });

        When("^the user adds the new entity type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createEntityTypeForm = persona.createEntityTypeForm;

                    assertThat(createEntityTypeForm).isNotNull();

                    var commandResult = CoreUtil.getHome().createEntityType(persona.userVisitPK, createEntityTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateEntityTypeResult)executionResult.getResult();
                    persona.lastEntityTypeName = result.getEntityTypeName();

                    persona.createEntityTypeForm = null;
                });

        When("^the user begins deleting an entity type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityTypeForm).isNull();
                    assertThat(persona.deleteEntityTypeForm).isNull();
                    assertThat(persona.entityTypeSpec).isNull();

                    persona.deleteEntityTypeForm = CoreUtil.getHome().getDeleteEntityTypeForm();
                });

        When("^the user deletes the entity type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteEntityTypeForm = persona.deleteEntityTypeForm;

                    assertThat(deleteEntityTypeForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteEntityType(persona.userVisitPK, deleteEntityTypeForm);

                    persona.deleteEntityTypeForm = null;
                });

        When("^the user begins specifying an entity type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityTypeForm).isNull();
                    assertThat(persona.deleteEntityTypeForm).isNull();
                    assertThat(persona.entityTypeSpec).isNull();

                    persona.entityTypeSpec = CoreUtil.getHome().getEntityTypeUniversalSpec();
                });

        When("^the user begins editing the entity type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editEntityType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditEntityTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.entityTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the entity type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityTypeSpec;
                    var edit = persona.entityTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CoreUtil.getHome().editEntityType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.entityTypeSpec = null;
                    persona.entityTypeEdit = null;
                });
        
        When("^the user sets the entity type's component vendor to ([a-zA-Z0-9-_]*)$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityTypeForm = persona.createEntityTypeForm;
                    var deleteEntityTypeForm = persona.deleteEntityTypeForm;
                    var entityTypeSpec = persona.entityTypeSpec;

                    assertThat(createEntityTypeForm != null || deleteEntityTypeForm != null || entityTypeSpec != null).isTrue();

                    if(createEntityTypeForm != null) {
                        createEntityTypeForm.setComponentVendorName(componentVendorName);
                    } else if(deleteEntityTypeForm != null) {
                        deleteEntityTypeForm.setComponentVendorName(componentVendorName);
                    } else {
                        entityTypeSpec.setComponentVendorName(componentVendorName);
                    }
                });

        When("^the user sets the entity type's entity type to ([a-zA-Z0-9-_]*)$",
                (String entityTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityTypeForm = persona.createEntityTypeForm;
                    var deleteEntityTypeForm = persona.deleteEntityTypeForm;
                    var entityTypeSpec = persona.entityTypeSpec;

                    assertThat(createEntityTypeForm != null || deleteEntityTypeForm != null || entityTypeSpec != null).isTrue();

                    if(createEntityTypeForm != null) {
                        createEntityTypeForm.setEntityTypeName(entityTypeName);
                    } else if(deleteEntityTypeForm != null) {
                        deleteEntityTypeForm.setEntityTypeName(entityTypeName);
                    } else {
                        entityTypeSpec.setEntityTypeName(entityTypeName);
                    }
                });

        When("^the user sets the entity type to (keep|not keep) all history$",
                (String keepAllHistory) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityTypeForm = persona.createEntityTypeForm;
                    var entityTypeEdit = persona.entityTypeEdit;

                    assertThat(createEntityTypeForm != null || entityTypeEdit != null).isTrue();

                    keepAllHistory = Boolean.valueOf(keepAllHistory.equals("keep")).toString();
                    if(createEntityTypeForm != null) {
                        createEntityTypeForm.setKeepAllHistory(keepAllHistory);
                    } else {
                        entityTypeEdit.setKeepAllHistory(keepAllHistory);
                    }
                });

        When("^the user sets the entity type's lock timeout to \"([^\"]*)\"$",
                (String lockTimeout) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityTypeForm = persona.createEntityTypeForm;
                    var entityTypeEdit = persona.entityTypeEdit;

                    assertThat(createEntityTypeForm != null || entityTypeEdit != null).isTrue();

                    if(createEntityTypeForm != null) {
                        createEntityTypeForm.setLockTimeout(lockTimeout);
                    } else {
                        entityTypeEdit.setLockTimeout(lockTimeout);
                    }
                });

        When("^the user sets the entity type's lock timeout unit of measure type name to \"([^\"]*)\"$",
                (String lockTimeoutUnitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityTypeForm = persona.createEntityTypeForm;
                    var entityTypeEdit = persona.entityTypeEdit;

                    assertThat(createEntityTypeForm != null || entityTypeEdit != null).isTrue();

                    if(createEntityTypeForm != null) {
                        createEntityTypeForm.setLockTimeoutUnitOfMeasureTypeName(lockTimeoutUnitOfMeasureTypeName);
                    } else {
                        entityTypeEdit.setLockTimeoutUnitOfMeasureTypeName(lockTimeoutUnitOfMeasureTypeName);
                    }
                });

        When("^the user sets the entity type to (be|not be) extensible$",
                (String isExtensible) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityTypeForm = persona.createEntityTypeForm;
                    var entityTypeEdit = persona.entityTypeEdit;

                    assertThat(createEntityTypeForm != null || entityTypeEdit != null).isTrue();

                    isExtensible = Boolean.valueOf(isExtensible.equals("be")).toString();
                    if(createEntityTypeForm != null) {
                        createEntityTypeForm.setIsExtensible(isExtensible);
                    } else {
                        entityTypeEdit.setIsExtensible(isExtensible);
                    }
                });
        
        When("^the user sets the entity type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityTypeForm = persona.createEntityTypeForm;
                    var entityTypeEdit = persona.entityTypeEdit;

                    assertThat(createEntityTypeForm != null || entityTypeEdit != null).isTrue();

                    if(createEntityTypeForm != null) {
                        createEntityTypeForm.setSortOrder(sortOrder);
                    } else {
                        entityTypeEdit.setSortOrder(sortOrder);
                    }
                });
        
        When("^the user sets the entity type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityTypeForm = persona.createEntityTypeForm;
                    var entityTypeEdit = persona.entityTypeEdit;

                    assertThat(createEntityTypeForm != null || entityTypeEdit != null).isTrue();

                    if(createEntityTypeForm != null) {
                        createEntityTypeForm.setDescription(description);
                    } else {
                        entityTypeEdit.setDescription(description);
                    }
                });

        When("^the user sets the entity type's component vendor to the last component vendor added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastComponentVendorName = persona.lastComponentVendorName;
                    var createEntityTypeForm = persona.createEntityTypeForm;
                    var deleteEntityTypeForm = persona.deleteEntityTypeForm;
                    var entityTypeSpec = persona.entityTypeSpec;

                    assertThat(createEntityTypeForm != null || deleteEntityTypeForm != null || entityTypeSpec != null).isTrue();

                    if(createEntityTypeForm != null) {
                        createEntityTypeForm.setComponentVendorName(lastComponentVendorName);
                    } else if(deleteEntityTypeForm != null) {
                        deleteEntityTypeForm.setComponentVendorName(lastComponentVendorName);
                    } else {
                        entityTypeSpec.setComponentVendorName(lastComponentVendorName);
                    }
                });

        When("^the user sets the entity type's name to the last entity type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastEntityTypeName = persona.lastEntityTypeName;
                    var deleteEntityTypeForm = persona.deleteEntityTypeForm;
                    var entityTypeSpec = persona.entityTypeSpec;

                    assertThat(deleteEntityTypeForm != null || entityTypeSpec != null).isTrue();

                    if(deleteEntityTypeForm != null) {
                        deleteEntityTypeForm.setEntityTypeName(lastEntityTypeName);
                    } else {
                        entityTypeSpec.setEntityTypeName(lastEntityTypeName);
                    }
                });
    }

}
