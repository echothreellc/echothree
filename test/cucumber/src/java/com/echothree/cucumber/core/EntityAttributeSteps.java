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
import com.echothree.control.user.core.common.result.CreateEntityAttributeResult;
import com.echothree.control.user.core.common.result.EditEntityAttributeResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class EntityAttributeSteps implements En {

    public EntityAttributeSteps() {
        When("^the user begins entering a new entity attribute$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAttributeForm).isNull();
                    assertThat(persona.deleteEntityAttributeForm).isNull();
                    assertThat(persona.entityAttributeUniversalSpec).isNull();

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
                    assertThat(persona.entityAttributeUniversalSpec).isNull();

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

        When("^the user begins specifying an entity attribute to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createEntityAttributeForm).isNull();
                    assertThat(persona.deleteEntityAttributeForm).isNull();
                    assertThat(persona.entityAttributeUniversalSpec).isNull();

                    persona.entityAttributeUniversalSpec = CoreUtil.getHome().getEntityAttributeUniversalSpec();
                });

        When("^the user begins editing the entity attribute$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityAttributeUniversalSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityAttributeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editEntityAttribute(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditEntityAttributeResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.entityAttributeEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the entity attribute$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.entityAttributeUniversalSpec;
                    var edit = persona.entityAttributeEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditEntityAttributeForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CoreUtil.getHome().editEntityAttribute(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.entityAttributeUniversalSpec = null;
                    persona.entityAttributeEdit = null;
                });
        
        When("^the user sets the entity attribute's component vendor to ([a-zA-Z0-9-_]*)$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var deleteEntityAttributeForm = persona.deleteEntityAttributeForm;
                    var entityAttributeSpec = persona.entityAttributeUniversalSpec;

                    assertThat(createEntityAttributeForm != null || deleteEntityAttributeForm != null || entityAttributeSpec != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setComponentVendorName(componentVendorName);
                    } else if(deleteEntityAttributeForm != null) {
                        deleteEntityAttributeForm.setComponentVendorName(componentVendorName);
                    } else {
                        entityAttributeSpec.setComponentVendorName(componentVendorName);
                    }
                });

        When("^the user sets the entity attribute's entity type to ([a-zA-Z0-9-_]*)$",
                (String entityTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var deleteEntityAttributeForm = persona.deleteEntityAttributeForm;
                    var entityAttributeSpec = persona.entityAttributeUniversalSpec;

                    assertThat(createEntityAttributeForm != null || deleteEntityAttributeForm != null || entityAttributeSpec != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setEntityTypeName(entityTypeName);
                    } else if(deleteEntityAttributeForm != null) {
                        deleteEntityAttributeForm.setEntityTypeName(entityTypeName);
                    } else {
                        entityAttributeSpec.setEntityTypeName(entityTypeName);
                    }
                });

        When("^the user sets the entity attribute's entity attribute type to ([a-zA-Z0-9-_]*)$",
                (String entityAttributeTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;

                    assertThat(createEntityAttributeForm != null).isTrue();

                    createEntityAttributeForm.setEntityAttributeTypeName(entityAttributeTypeName);
                });

        When("^the user sets the entity attribute's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setSortOrder(sortOrder);
                    } else {
                        entityAttributeEdit.setSortOrder(sortOrder);
                    }
                });

        When("^the user sets the entity attribute's upper integer range to \"([^\"]*)\"$",
                (String upperRangeIntegerValue) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setUpperRangeIntegerValue(upperRangeIntegerValue);
                    } else {
                        entityAttributeEdit.setUpperRangeIntegerValue(upperRangeIntegerValue);
                    }
                });

        When("^the user sets the entity attribute's upper integer limit to \"([^\"]*)\"$",
                (String upperLimitIntegerValue) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setUpperLimitIntegerValue(upperLimitIntegerValue);
                    } else {
                        entityAttributeEdit.setUpperLimitIntegerValue(upperLimitIntegerValue);
                    }
                });

        When("^the user sets the entity attribute's lower integer limit to \"([^\"]*)\"$",
                (String lowerLimitIntegerValue) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setLowerLimitIntegerValue(lowerLimitIntegerValue);
                    } else {
                        entityAttributeEdit.setLowerLimitIntegerValue(lowerLimitIntegerValue);
                    }
                });

        When("^the user sets the entity attribute's lower integer range to \"([^\"]*)\"$",
                (String lowerRangeIntegerValue) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setLowerRangeIntegerValue(lowerRangeIntegerValue);
                    } else {
                        entityAttributeEdit.setLowerRangeIntegerValue(lowerRangeIntegerValue);
                    }
                });

        When("^the user sets the entity attribute's upper long range to \"([^\"]*)\"$",
                (String upperRangeLongValue) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setUpperRangeLongValue(upperRangeLongValue);
                    } else {
                        entityAttributeEdit.setUpperRangeLongValue(upperRangeLongValue);
                    }
                });

        When("^the user sets the entity attribute's upper long limit to \"([^\"]*)\"$",
                (String upperLimitLongValue) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setUpperLimitLongValue(upperLimitLongValue);
                    } else {
                        entityAttributeEdit.setUpperLimitLongValue(upperLimitLongValue);
                    }
                });

        When("^the user sets the entity attribute's lower long limit to \"([^\"]*)\"$",
                (String lowerLimitLongValue) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setLowerLimitLongValue(lowerLimitLongValue);
                    } else {
                        entityAttributeEdit.setLowerLimitLongValue(lowerLimitLongValue);
                    }
                });

        When("^the user sets the entity attribute's lower long range to \"([^\"]*)\"$",
                (String lowerRangeLongValue) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setLowerRangeLongValue(lowerRangeLongValue);
                    } else {
                        entityAttributeEdit.setLowerRangeLongValue(lowerRangeLongValue);
                    }
                });

        When("^the user sets the entity attribute's validation pattern to \"([^\"]*)\"$",
                (String validationPattern) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setValidationPattern(validationPattern);
                    } else {
                        entityAttributeEdit.setValidationPattern(validationPattern);
                    }
                });

        When("^the user sets the entity attribute's unit of measure kind to ([a-zA-Z0-9-_]*)$",
                (String unitOfMeasureKindName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
                    } else {
                        entityAttributeEdit.setUnitOfMeasureKindName(unitOfMeasureKindName);
                    }
                });

        When("^the user sets the entity attribute's unit of measure type to ([a-zA-Z0-9-_]*)$",
                (String unitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else {
                        entityAttributeEdit.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    }
                });

        When("^the user sets the entity attribute's entity list item sequence to \"([^\"]*)\"$",
                (String entityListItemSequenceName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setEntityListItemSequenceName(entityListItemSequenceName);
                    } else {
                        entityAttributeEdit.setEntityListItemSequenceName(entityListItemSequenceName);
                    }
                });

        When("^the user sets the entity attribute's workflow to \"([^\"]*)\"$",
                (String workflowName) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setWorkflowName(workflowName);
                    } else {
                        entityAttributeEdit.setWorkflowName(workflowName);
                    }
                });

        When("^the user sets the entity attribute's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setDescription(description);
                    } else {
                        entityAttributeEdit.setDescription(description);
                    }
                });

        When("^the user sets the entity attribute to (track|not track) revisions when modified$",
                (String trackRevisions) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    trackRevisions = Boolean.valueOf(trackRevisions.equals("track")).toString();
                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setTrackRevisions(trackRevisions);
                    } else {
                        entityAttributeEdit.setTrackRevisions(trackRevisions);
                    }
                });

        When("^the user sets the entity attribute to (check|not check) the content web address when requested$",
                (String checkContentWebAddress) -> {
                    var persona = CurrentPersona.persona;
                    var createEntityAttributeForm = persona.createEntityAttributeForm;
                    var entityAttributeEdit = persona.entityAttributeEdit;

                    assertThat(createEntityAttributeForm != null || entityAttributeEdit != null).isTrue();

                    checkContentWebAddress = Boolean.valueOf(checkContentWebAddress.equals("check")).toString();
                    if(createEntityAttributeForm != null) {
                        createEntityAttributeForm.setCheckContentWebAddress(checkContentWebAddress);
                    } else {
                        entityAttributeEdit.setCheckContentWebAddress(checkContentWebAddress);
                    }
                });

        When("^the user sets the entity attribute's name to the last entity attribute added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var lastEntityAttributeName = persona.lastEntityAttributeName;
                    var deleteEntityAttributeForm = persona.deleteEntityAttributeForm;
                    var entityAttributeSpec = persona.entityAttributeUniversalSpec;

                    assertThat(deleteEntityAttributeForm != null || entityAttributeSpec != null).isTrue();

                    if(deleteEntityAttributeForm != null) {
                        deleteEntityAttributeForm.setEntityAttributeName(lastEntityAttributeName);
                    } else {
                        entityAttributeSpec.setEntityAttributeName(lastEntityAttributeName);
                    }
                });
    }

}
