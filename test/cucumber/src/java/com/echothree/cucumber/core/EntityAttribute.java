// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.core.common.result.CreateEntityAttributeResult;
import com.echothree.cucumber.LastCommandResult;
import com.echothree.cucumber.user.CurrentPersona;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class EntityAttribute implements En {

    public EntityAttribute() {
        When("^the user begins entering a new entity attribute$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAttributeForm).isNull();
                    assertThat(persona.deleteEntityAttributeForm).isNull();

                    persona.createEntityAttributeForm = CoreUtil.getHome().getCreateEntityAttributeForm();
                });

        When("^the user adds the new entity attribute$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;

                    assertThat(createEntityAttributeForm).isNotNull();

                    var commandResult = CoreUtil.getHome().createEntityAttribute(persona.userVisitPK, createEntityAttributeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateEntityAttributeResult)executionResult.getResult();
                    persona.lastEntityAttributeName = result.getEntityAttributeName();

                    persona.createEntityAttributeForm = null;
                });

        When("^the user begins deleting an entity attribute$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAttributeForm).isNull();
                    assertThat(persona.deleteEntityAttributeForm).isNull();

                    persona.deleteEntityAttributeForm = CoreUtil.getHome().getDeleteEntityAttributeForm();
                });

        When("^the user deletes the entity attribute$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteEntityAttributeForm = persona.deleteEntityAttributeForm;

                    assertThat(deleteEntityAttributeForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteEntityAttribute(persona.userVisitPK, deleteEntityAttributeForm);

                    persona.deleteEntityAttributeForm = null;
                });

        When("^the user sets the entity attribute's component vendor to ([a-zA-Z0-9-_]*)$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var deleteEntityAttributeForm = persona.deleteEntityAttributeForm;

                    assertThat(createEntityAttributeForm != null || deleteEntityAttributeForm != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setComponentVendorName(componentVendorName);
                    } else if(deleteEntityAttributeForm != null) {
                        deleteEntityAttributeForm.setComponentVendorName(componentVendorName);
                    }
                });

        When("^the user sets the entity attribute's entity type to ([a-zA-Z0-9-_]*)$",
                (String entityTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var deleteEntityAttributeForm = persona.deleteEntityAttributeForm;

                    assertThat(createEntityAttributeForm != null || deleteEntityAttributeForm != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setEntityTypeName(entityTypeName);
                    } else if(deleteEntityAttributeForm != null) {
                        deleteEntityAttributeForm.setEntityTypeName(entityTypeName);
                    }
                });

        When("^the user sets the entity attribute's entity attribute type to ([a-zA-Z0-9-_]*)$",
                (String entityAttributeTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;

                    assertThat(createEntityAttributeForm).isNotNull();

                    createEntityAttributeForm.setEntityAttributeTypeName(entityAttributeTypeName);
                });

        When("^the user sets the entity attribute's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;

                    assertThat(createEntityAttributeForm != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the entity attribute to (track|not track) revisions when modified$",
                (String trackRevisions) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;

                    assertThat(createEntityAttributeForm != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setTrackRevisions(Boolean.valueOf(trackRevisions.equals("track")).toString());
                    }
                });

        When("^the user sets the entity attribute's name to the last entity attribute added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastEntityAttributeName = persona.lastEntityAttributeName;
                    var deleteEntityAttributeForm = persona.deleteEntityAttributeForm;

                    assertThat(deleteEntityAttributeForm != null).isTrue();

                    if(deleteEntityAttributeForm != null) {
                        deleteEntityAttributeForm.setEntityAttributeName(lastEntityAttributeName);
                    }
                });
    }

}
