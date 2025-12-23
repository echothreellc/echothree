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
import com.echothree.control.user.core.common.result.CreateEntityAliasTypeResult;
import com.echothree.control.user.core.common.result.EditEntityAliasTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class EntityAliasTypeSteps implements En {

    public EntityAliasTypeSteps() {
        When("^the user begins entering a new entity alias type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAliasTypeForm).isNull();
                    assertThat(persona.deleteEntityAliasTypeForm).isNull();
                    assertThat(persona.entityAliasTypeUniversalSpec).isNull();

                    persona.createEntityAliasTypeForm = CoreUtil.getHome().getCreateEntityAliasTypeForm();
                });

        When("^the user adds the new entity alias type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAliasTypeForm = persona.createEntityAliasTypeForm;

                    assertThat(createEntityAliasTypeForm).isNotNull();

                    var commandResult = CoreUtil.getHome().createEntityAliasType(persona.userVisitPK, createEntityAliasTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateEntityAliasTypeResult)executionResult.getResult();
                    persona.lastEntityAliasTypeName = result.getEntityAliasTypeName();

                    persona.createEntityAliasTypeForm = null;
                });

        When("^the user begins deleting an entity alias type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAliasTypeForm).isNull();
                    assertThat(persona.deleteEntityAliasTypeForm).isNull();
                    assertThat(persona.entityAliasTypeUniversalSpec).isNull();

                    persona.deleteEntityAliasTypeForm = CoreUtil.getHome().getDeleteEntityAliasTypeForm();
                });

        When("^the user deletes the entity alias type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteEntityAliasTypeForm = persona.deleteEntityAliasTypeForm;

                    assertThat(deleteEntityAliasTypeForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteEntityAliasType(persona.userVisitPK, deleteEntityAliasTypeForm);

                    persona.deleteEntityAliasTypeForm = null;
                });

        When("^the user begins specifying an entity alias type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAliasTypeForm).isNull();
                    assertThat(persona.deleteEntityAliasTypeForm).isNull();
                    assertThat(persona.entityAliasTypeUniversalSpec).isNull();

                    persona.entityAliasTypeUniversalSpec = CoreUtil.getHome().getEntityAliasTypeUniversalSpec();
                });

        When("^the user begins editing the entity alias type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityAliasTypeUniversalSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityAliasTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editEntityAliasType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditEntityAliasTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.entityAliasTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the entity alias type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityAliasTypeUniversalSpec;
                    var edit = persona.entityAliasTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityAliasTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CoreUtil.getHome().editEntityAliasType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.entityAliasTypeUniversalSpec = null;
                    persona.entityAliasTypeEdit = null;
                });
        
        When("^the user sets the entity alias type's component vendor to \"([^\"]*)\"$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAliasTypeForm = persona.createEntityAliasTypeForm;
                    var deleteEntityAliasTypeForm = persona.deleteEntityAliasTypeForm;
                    var entityAliasTypeSpec = persona.entityAliasTypeUniversalSpec;

                    assertThat(createEntityAliasTypeForm != null || deleteEntityAliasTypeForm != null || entityAliasTypeSpec != null).isTrue();

                    if(createEntityAliasTypeForm != null) {
                        createEntityAliasTypeForm.setComponentVendorName(componentVendorName);
                    } else if(deleteEntityAliasTypeForm != null) {
                        deleteEntityAliasTypeForm.setComponentVendorName(componentVendorName);
                    } else {
                        entityAliasTypeSpec.setComponentVendorName(componentVendorName);
                    }
                });

        When("^the user sets the entity alias type's component vendor to the last component vendor added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastComponentVendorName = persona.lastComponentVendorName;
                    var deleteEntityAliasTypeForm = persona.deleteEntityAliasTypeForm;
                    var entityAliasTypeSpec = persona.entityAliasTypeUniversalSpec;

                    assertThat(deleteEntityAliasTypeForm != null || entityAliasTypeSpec != null).isTrue();

                    if(deleteEntityAliasTypeForm != null) {
                        deleteEntityAliasTypeForm.setComponentVendorName(lastComponentVendorName);
                    } else {
                        entityAliasTypeSpec.setComponentVendorName(lastComponentVendorName);
                    }
                });

        When("^the user sets the entity alias type's entity type to \"([^\"]*)\"$",
                (String entityTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAliasTypeForm = persona.createEntityAliasTypeForm;
                    var deleteEntityAliasTypeForm = persona.deleteEntityAliasTypeForm;
                    var entityAliasTypeSpec = persona.entityAliasTypeUniversalSpec;

                    assertThat(createEntityAliasTypeForm != null || deleteEntityAliasTypeForm != null || entityAliasTypeSpec != null).isTrue();

                    if(createEntityAliasTypeForm != null) {
                        createEntityAliasTypeForm.setEntityTypeName(entityTypeName);
                    } else if(deleteEntityAliasTypeForm != null) {
                        deleteEntityAliasTypeForm.setEntityTypeName(entityTypeName);
                    } else {
                        entityAliasTypeSpec.setEntityTypeName(entityTypeName);
                    }
                });

        When("^the user sets the entity alias type's entity type to the last entity type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastEntityTypeName = persona.lastEntityTypeName;
                    var deleteEntityAliasTypeForm = persona.deleteEntityAliasTypeForm;
                    var entityAliasTypeSpec = persona.entityAliasTypeUniversalSpec;

                    assertThat(deleteEntityAliasTypeForm != null || entityAliasTypeSpec != null).isTrue();

                    if(deleteEntityAliasTypeForm != null) {
                        deleteEntityAliasTypeForm.setEntityTypeName(lastEntityTypeName);
                    } else {
                        entityAliasTypeSpec.setEntityTypeName(lastEntityTypeName);
                    }
                });

        When("^the user sets the entity alias type's name to \"([^\"]*)\"$",
                (String entityAliasTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAliasTypeForm = persona.createEntityAliasTypeForm;
                    var deleteEntityAliasTypeForm = persona.deleteEntityAliasTypeForm;
                    var entityAliasTypeSpec = persona.entityAliasTypeUniversalSpec;

                    assertThat(createEntityAliasTypeForm != null || deleteEntityAliasTypeForm != null || entityAliasTypeSpec != null).isTrue();

                    if(createEntityAliasTypeForm != null) {
                        createEntityAliasTypeForm.setEntityAliasTypeName(entityAliasTypeName);
                    } else if(deleteEntityAliasTypeForm != null) {
                        deleteEntityAliasTypeForm.setEntityAliasTypeName(entityAliasTypeName);
                    } else {
                        entityAliasTypeSpec.setEntityAliasTypeName(entityAliasTypeName);
                    }
                });

        When("^the user sets the entity alias type's name to the last entity alias type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastEntityAliasTypeName = persona.lastEntityAliasTypeName;
                    var deleteEntityAliasTypeForm = persona.deleteEntityAliasTypeForm;
                    var entityAliasTypeSpec = persona.entityAliasTypeUniversalSpec;

                    assertThat(deleteEntityAliasTypeForm != null || entityAliasTypeSpec != null).isTrue();

                    if(deleteEntityAliasTypeForm != null) {
                        deleteEntityAliasTypeForm.setEntityAliasTypeName(lastEntityAliasTypeName);
                    } else {
                        entityAliasTypeSpec.setEntityAliasTypeName(lastEntityAliasTypeName);
                    }
                });

        When("^the user sets the entity alias type's validation pattern to \"([^\"]*)\"$",
                (String validationPattern) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAliasTypeForm = persona.createEntityAliasTypeForm;
                    var entityAliasTypeEdit = persona.entityAliasTypeEdit;

                    assertThat(createEntityAliasTypeForm != null || entityAliasTypeEdit != null).isTrue();

                    if(createEntityAliasTypeForm != null) {
                        createEntityAliasTypeForm.setValidationPattern(validationPattern);
                    } else {
                        entityAliasTypeEdit.setValidationPattern(validationPattern);
                    }
                });

        When("^the user sets the entity alias type to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAliasTypeForm = persona.createEntityAliasTypeForm;
                    var entityAliasTypeEdit = persona.entityAliasTypeEdit;

                    assertThat(createEntityAliasTypeForm != null || entityAliasTypeEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createEntityAliasTypeForm != null) {
                        createEntityAliasTypeForm.setIsDefault(isDefault);
                    } else {
                        entityAliasTypeEdit.setIsDefault(isDefault);
                    }
                });

        When("^the user sets the entity alias type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAliasTypeForm = persona.createEntityAliasTypeForm;
                    var entityAliasTypeEdit = persona.entityAliasTypeEdit;

                    assertThat(createEntityAliasTypeForm != null || entityAliasTypeEdit != null).isTrue();

                    if(createEntityAliasTypeForm != null) {
                        createEntityAliasTypeForm.setSortOrder(sortOrder);
                    } else {
                        entityAliasTypeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the entity alias type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAliasTypeForm = persona.createEntityAliasTypeForm;
                    var entityAliasTypeEdit = persona.entityAliasTypeEdit;

                    assertThat(createEntityAliasTypeForm != null || entityAliasTypeEdit != null).isTrue();

                    if(createEntityAliasTypeForm != null) {
                        createEntityAliasTypeForm.setDescription(description);
                    } else {
                        entityAliasTypeEdit.setDescription(description);
                    }
                });
    }

}
