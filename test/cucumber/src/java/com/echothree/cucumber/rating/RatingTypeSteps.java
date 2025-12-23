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

package com.echothree.cucumber.rating;

import com.echothree.control.user.rating.common.RatingUtil;
import com.echothree.control.user.rating.common.result.CreateRatingTypeResult;
import com.echothree.control.user.rating.common.result.EditRatingTypeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class RatingTypeSteps implements En {

    public RatingTypeSteps() {
        When("^the user begins entering a new rating type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createRatingTypeForm).isNull();
                    assertThat(persona.deleteRatingTypeForm).isNull();
                    assertThat(persona.ratingTypeSpec).isNull();

                    persona.createRatingTypeForm = RatingUtil.getHome().getCreateRatingTypeForm();
                });

        When("^the user adds the new rating type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createRatingTypeForm = persona.createRatingTypeForm;

                    assertThat(createRatingTypeForm).isNotNull();

                    var commandResult = RatingUtil.getHome().createRatingType(persona.userVisitPK, createRatingTypeForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateRatingTypeResult)executionResult.getResult();
                    persona.lastRatingTypeName = result.getRatingTypeName();

                    persona.createRatingTypeForm = null;
                });

        When("^the user begins deleting a rating type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createRatingTypeForm).isNull();
                    assertThat(persona.deleteRatingTypeForm).isNull();
                    assertThat(persona.ratingTypeSpec).isNull();

                    persona.deleteRatingTypeForm = RatingUtil.getHome().getDeleteRatingTypeForm();
                });

        When("^the user deletes the rating type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteRatingTypeForm = persona.deleteRatingTypeForm;

                    assertThat(deleteRatingTypeForm).isNotNull();

                    LastCommandResult.commandResult = RatingUtil.getHome().deleteRatingType(persona.userVisitPK, deleteRatingTypeForm);

                    persona.deleteRatingTypeForm = null;
                });

        When("^the user begins specifying a rating type to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createRatingTypeForm).isNull();
                    assertThat(persona.deleteRatingTypeForm).isNull();
                    assertThat(persona.ratingTypeSpec).isNull();

                    persona.ratingTypeSpec = RatingUtil.getHome().getRatingTypeSpec();
                });

        When("^the user begins editing the rating type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.ratingTypeSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = RatingUtil.getHome().getEditRatingTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = RatingUtil.getHome().editRatingType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditRatingTypeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.ratingTypeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the rating type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.ratingTypeSpec;
                    var edit = persona.ratingTypeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = RatingUtil.getHome().getEditRatingTypeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = RatingUtil.getHome().editRatingType(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.ratingTypeSpec = null;
                    persona.ratingTypeEdit = null;
                });
        
        When("^the user sets the rating type's component vendor to ([a-zA-Z0-9-_]*)$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createRatingTypeForm = persona.createRatingTypeForm;
                    var deleteRatingTypeForm = persona.deleteRatingTypeForm;
                    var ratingTypeSpec = persona.ratingTypeSpec;

                    assertThat(createRatingTypeForm != null || deleteRatingTypeForm != null || ratingTypeSpec != null).isTrue();

                    if(createRatingTypeForm != null) {
                        createRatingTypeForm.setComponentVendorName(componentVendorName);
                    } else if(deleteRatingTypeForm != null) {
                        deleteRatingTypeForm.setComponentVendorName(componentVendorName);
                    } else {
                        ratingTypeSpec.setComponentVendorName(componentVendorName);
                    }
                });

        When("^the user sets the rating type's entity type to ([a-zA-Z0-9-_]*)$",
                (String entityTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createRatingTypeForm = persona.createRatingTypeForm;
                    var deleteRatingTypeForm = persona.deleteRatingTypeForm;
                    var ratingTypeSpec = persona.ratingTypeSpec;

                    assertThat(createRatingTypeForm != null || deleteRatingTypeForm != null || ratingTypeSpec != null).isTrue();

                    if(createRatingTypeForm != null) {
                        createRatingTypeForm.setEntityTypeName(entityTypeName);
                    } else if(deleteRatingTypeForm != null) {
                        deleteRatingTypeForm.setEntityTypeName(entityTypeName);
                    } else {
                        ratingTypeSpec.setEntityTypeName(entityTypeName);
                    }
                });

        When("^the user sets the rating type's rating type to ([a-zA-Z0-9-_]*)$",
                (String ratingTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createRatingTypeForm = persona.createRatingTypeForm;
                    var deleteRatingTypeForm = persona.deleteRatingTypeForm;
                    var ratingTypeSpec = persona.ratingTypeSpec;

                    assertThat(createRatingTypeForm != null || deleteRatingTypeForm != null || ratingTypeSpec != null).isTrue();

                    if(createRatingTypeForm != null) {
                        createRatingTypeForm.setRatingTypeName(ratingTypeName);
                    } else if(deleteRatingTypeForm != null) {
                        deleteRatingTypeForm.setRatingTypeName(ratingTypeName);
                    } else {
                        ratingTypeSpec.setRatingTypeName(ratingTypeName);
                    }
                });

        When("^the user sets the rating type's rating sequence to ([a-zA-Z0-9-_]*)$",
                (String ratingSequenceName) -> {
                    var persona = CurrentPersona.persona;
                    var createRatingTypeForm = persona.createRatingTypeForm;
                    var ratingTypeEdit = persona.ratingTypeEdit;

                    assertThat(createRatingTypeForm != null || ratingTypeEdit != null).isTrue();

                    if(createRatingTypeForm != null) {
                        createRatingTypeForm.setRatingSequenceName(ratingSequenceName);
                    } else {
                        ratingTypeEdit.setRatingSequenceName(ratingSequenceName);
                    }
                });

        When("^the user sets the rating type's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createRatingTypeForm = persona.createRatingTypeForm;
                    var ratingTypeEdit = persona.ratingTypeEdit;

                    assertThat(createRatingTypeForm != null || ratingTypeEdit != null).isTrue();

                    if(createRatingTypeForm != null) {
                        createRatingTypeForm.setSortOrder(sortOrder);
                    } else {
                        ratingTypeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the rating type's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createRatingTypeForm = persona.createRatingTypeForm;
                    var ratingTypeEdit = persona.ratingTypeEdit;

                    assertThat(createRatingTypeForm != null || ratingTypeEdit != null).isTrue();

                    if(createRatingTypeForm != null) {
                        createRatingTypeForm.setDescription(description);
                    } else {
                        ratingTypeEdit.setDescription(description);
                    }
                });

        When("^the user sets the rating type's name to the last rating type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastRatingTypeName = persona.lastRatingTypeName;
                    var deleteRatingTypeForm = persona.deleteRatingTypeForm;
                    var ratingTypeSpec = persona.ratingTypeSpec;

                    assertThat(deleteRatingTypeForm != null || ratingTypeSpec != null).isTrue();

                    if(deleteRatingTypeForm != null) {
                        deleteRatingTypeForm.setRatingTypeName(lastRatingTypeName);
                    } else {
                        ratingTypeSpec.setRatingTypeName(lastRatingTypeName);
                    }
                });
    }

}
