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
import com.echothree.control.user.core.common.result.CreateEntityInstanceResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class EntityInstanceSteps implements En {

    public EntityInstanceSteps() {
        When("^the user begins entering a new entity instance$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityInstanceForm).isNull();
                    assertThat(persona.deleteEntityInstanceForm).isNull();
                    assertThat(persona.removeEntityInstanceForm).isNull();

                    persona.createEntityInstanceForm = CoreUtil.getHome().getCreateEntityInstanceForm();
                });

        When("^the user adds the new entity instance$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createEntityInstanceForm = persona.createEntityInstanceForm;

                    assertThat(createEntityInstanceForm).isNotNull();

                    var commandResult = CoreUtil.getHome().createEntityInstance(persona.userVisitPK, createEntityInstanceForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateEntityInstanceResult)executionResult.getResult();
                    persona.lastEntityRef = result.getEntityRef();

                    persona.createEntityInstanceForm = null;
                });

        When("^the user begins deleting an entity instance$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityInstanceForm).isNull();
                    assertThat(persona.deleteEntityInstanceForm).isNull();
                    assertThat(persona.removeEntityInstanceForm).isNull();

                    persona.deleteEntityInstanceForm = CoreUtil.getHome().getDeleteEntityInstanceForm();
                });

        When("^the user deletes the entity instance$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteEntityInstanceForm = persona.deleteEntityInstanceForm;

                    assertThat(deleteEntityInstanceForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteEntityInstance(persona.userVisitPK, deleteEntityInstanceForm);

                    persona.deleteEntityInstanceForm = null;
                });

        When("^the user begins removing an entity instance$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityInstanceForm).isNull();
                    assertThat(persona.deleteEntityInstanceForm).isNull();
                    assertThat(persona.removeEntityInstanceForm).isNull();

                    persona.removeEntityInstanceForm = CoreUtil.getHome().getRemoveEntityInstanceForm();
                });

        When("^the user removes the entity instance$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var removeEntityInstanceForm = persona.removeEntityInstanceForm;

                    assertThat(removeEntityInstanceForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().removeEntityInstance(persona.userVisitPK, removeEntityInstanceForm);

                    persona.removeEntityInstanceForm = null;
                });

        When("^the user sets the entity instance's component vendor to ([a-zA-Z0-9-_]*)$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityInstanceForm = persona.createEntityInstanceForm;

                    assertThat(createEntityInstanceForm != null).isTrue();

                    if(createEntityInstanceForm != null) {
                        createEntityInstanceForm.setComponentVendorName(componentVendorName);
                    }
                });

        When("^the user sets the entity instance's component vendor to the last component vendor added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createEntityInstanceForm = persona.createEntityInstanceForm;
                    var lastComponentVendorName = persona.lastComponentVendorName;

                    assertThat(createEntityInstanceForm != null && lastComponentVendorName != null).isTrue();

                    createEntityInstanceForm.setComponentVendorName(lastComponentVendorName);
                });

        When("^the user sets the entity instance's entity type to ([a-zA-Z0-9-_]*)$",
                (String entityTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityInstanceForm = persona.createEntityInstanceForm;

                    assertThat(createEntityInstanceForm != null).isTrue();

                    createEntityInstanceForm.setEntityTypeName(entityTypeName);
                });

        When("^the user sets the entity instance's entity type to the last entity type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createEntityInstanceForm = persona.createEntityInstanceForm;
                    var lastEntityTypeName = persona.lastEntityTypeName;

                    assertThat(createEntityInstanceForm != null && lastEntityTypeName != null).isTrue();

                    createEntityInstanceForm.setEntityTypeName(lastEntityTypeName);
                });

        When("^the user sets the entity instance's entity instance to the last entity instance added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteEntityInstanceForm = persona.deleteEntityInstanceForm;
                    var removeEntityInstanceForm = persona.removeEntityInstanceForm;
                    var lastEntityRef = persona.lastEntityRef;

                    assertThat((deleteEntityInstanceForm != null || removeEntityInstanceForm != null) && lastEntityRef != null).isTrue();

                    if(deleteEntityInstanceForm != null) {
                        deleteEntityInstanceForm.setEntityRef(lastEntityRef);
                    } else if(removeEntityInstanceForm != null) {
                        removeEntityInstanceForm.setEntityRef(lastEntityRef);
                    }
                });
    }

}