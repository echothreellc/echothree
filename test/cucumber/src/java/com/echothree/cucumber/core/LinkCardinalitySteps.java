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
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import io.cucumber.datatable.DataTable;
import com.echothree.model.control.core.common.transfer.LinkCardinalityTransfer;
import com.echothree.model.control.core.common.transfer.LinkCardinalityDescriptionTransfer;
import com.echothree.util.common.transfer.Limit;
import com.echothree.model.data.core.common.LinkCardinalityDescriptionConstants;
import java.util.Map;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class LinkCardinalitySteps implements En {

    private LinkCardinalityTransfer retrievedLinkCardinality;
    private LinkCardinalityDescriptionTransfer retrievedDescription;
    private List<LinkCardinalityDescriptionTransfer> retrievedDescriptions;
    private Long retrievedDescriptionCount;
    private String originalDefaultName;

    public LinkCardinalitySteps() {
        When("the user begins entering a new link cardinality",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkCardinalityForm).isNull();
                    assertThat(persona.deleteLinkCardinalityForm).isNull();
                    assertThat(persona.linkCardinalitySpec).isNull();

                    persona.createLinkCardinalityForm = CoreUtil.getHome().getCreateLinkCardinalityForm();
                });

        And("the user adds the new link cardinality",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLinkCardinalityForm = persona.createLinkCardinalityForm;

                    assertThat(createLinkCardinalityForm).isNotNull();

                    var commandResult = CoreUtil.getHome().createLinkCardinality(persona.userVisitPK, createLinkCardinalityForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastLinkCardinalityName = commandResult.getHasErrors() ? null : result.getLinkCardinalityName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createLinkCardinalityForm = null;
                });

        When("the user begins deleting a link cardinality",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkCardinalityForm).isNull();
                    assertThat(persona.deleteLinkCardinalityForm).isNull();
                    assertThat(persona.linkCardinalitySpec).isNull();

                    persona.deleteLinkCardinalityForm = CoreUtil.getHome().getDeleteLinkCardinalityForm();
                });

        And("the user deletes the link cardinality",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteLinkCardinalityForm = persona.deleteLinkCardinalityForm;

                    assertThat(deleteLinkCardinalityForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteLinkCardinality(persona.userVisitPK, deleteLinkCardinalityForm);

                    persona.deleteLinkCardinalityForm = null;
                });

        When("the user begins specifying a link cardinality to edit",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkCardinalityForm).isNull();
                    assertThat(persona.deleteLinkCardinalityForm).isNull();
                    assertThat(persona.linkCardinalitySpec).isNull();

                    persona.linkCardinalitySpec = CoreUtil.getHome().getLinkCardinalityUniversalSpec();
                });

        When("the user begins editing the link cardinality",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.linkCardinalitySpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditLinkCardinalityForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editLinkCardinality(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.linkCardinalityEdit = result.getEdit();
                    } else {
                        persona.linkCardinalitySpec = null;
                    }
                });

        And("the user finishes editing the link cardinality",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.linkCardinalitySpec;
                    var edit = persona.linkCardinalityEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditLinkCardinalityForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CoreUtil.getHome().editLinkCardinality(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.linkCardinalitySpec = null;
                    persona.linkCardinalityEdit = null;
                });

        And("the user sets the link cardinality's link cardinality name to {string}",
                (String linkCardinalityName) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkCardinalityForm = persona.createLinkCardinalityForm;
                    var deleteLinkCardinalityForm = persona.deleteLinkCardinalityForm;
                    var linkCardinalitySpec = persona.linkCardinalitySpec;

                    assertThat(createLinkCardinalityForm != null || deleteLinkCardinalityForm != null
                            || linkCardinalitySpec != null).isTrue();

                    if(createLinkCardinalityForm != null) {
                        createLinkCardinalityForm.setLinkCardinalityName(linkCardinalityName);
                    } else if(deleteLinkCardinalityForm != null) {
                        deleteLinkCardinalityForm.setLinkCardinalityName(linkCardinalityName);
                    } else {
                        linkCardinalitySpec.setLinkCardinalityName(linkCardinalityName);
                    }
                });

        And("the user sets the link cardinality's link cardinality name to the last link cardinality added",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLinkCardinalityForm = persona.createLinkCardinalityForm;
                    var deleteLinkCardinalityForm = persona.deleteLinkCardinalityForm;
                    var linkCardinalitySpec = persona.linkCardinalitySpec;

                    assertThat(createLinkCardinalityForm != null || deleteLinkCardinalityForm != null
                            || linkCardinalitySpec != null).isTrue();

                    if(createLinkCardinalityForm != null) {
                        createLinkCardinalityForm.setLinkCardinalityName(persona.lastLinkCardinalityName);
                    } else if(deleteLinkCardinalityForm != null) {
                        deleteLinkCardinalityForm.setLinkCardinalityName(persona.lastLinkCardinalityName);
                    } else {
                        linkCardinalitySpec.setLinkCardinalityName(persona.lastLinkCardinalityName);
                    }
                });

        And("the user sets the link cardinality's new link cardinality name to {string}",
                (String linkCardinalityName) -> {
                    var persona = CurrentPersona.persona;
                    var linkCardinalityEdit = persona.linkCardinalityEdit;

                    assertThat(linkCardinalityEdit).isNotNull();

                    linkCardinalityEdit.setLinkCardinalityName(linkCardinalityName);
                });

        And("the user sets the link cardinality to {string} the default",
                (String isDefault) -> {
                    assertThat(isDefault).isIn("be", "not be");

                    var persona = CurrentPersona.persona;
                    var createLinkCardinalityForm = persona.createLinkCardinalityForm;
                    var linkCardinalityEdit = persona.linkCardinalityEdit;

                    assertThat(createLinkCardinalityForm != null || linkCardinalityEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createLinkCardinalityForm != null) {
                        createLinkCardinalityForm.setIsDefault(isDefault);
                    } else {
                        linkCardinalityEdit.setIsDefault(isDefault);
                    }
                });

        And("the user sets the link cardinality's sort order to {string}",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkCardinalityForm = persona.createLinkCardinalityForm;
                    var linkCardinalityEdit = persona.linkCardinalityEdit;

                    assertThat(createLinkCardinalityForm != null || linkCardinalityEdit != null).isTrue();

                    if(createLinkCardinalityForm != null) {
                        createLinkCardinalityForm.setSortOrder(sortOrder);
                    } else {
                        linkCardinalityEdit.setSortOrder(sortOrder);
                    }
                });

        And("the user sets the link cardinality's description to {string}",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkCardinalityForm = persona.createLinkCardinalityForm;
                    var linkCardinalityEdit = persona.linkCardinalityEdit;

                    assertThat(createLinkCardinalityForm != null || linkCardinalityEdit != null).isTrue();

                    if(createLinkCardinalityForm != null) {
                        createLinkCardinalityForm.setDescription(description);
                    } else {
                        linkCardinalityEdit.setDescription(description);
                    }
                });

        And("the user enters these link cardinality values:", (DataTable values) -> {
            var persona = CurrentPersona.persona;
            var edit = persona.createLinkCardinalityForm == null ? persona.linkCardinalityEdit : persona.createLinkCardinalityForm;

            assertThat(edit).isNotNull();
            values.asMap(String.class, String.class).forEach(edit::set);
        });

        And("the user clears the link cardinality field {string}", (String fieldName) -> {
            var persona = CurrentPersona.persona;
            var edit = persona.createLinkCardinalityForm == null ? persona.linkCardinalityEdit : persona.createLinkCardinalityForm;

            assertThat(edit).isNotNull();
            edit.set(fieldName, null);
        });

        Then("the link cardinality field {string} validation error {string} should occur", (String fieldName, String error) -> {
            var commandResult = LastCommandResult.commandResult;

            assertThat(commandResult.hasValidationErrors()).isTrue();
            assertThat(commandResult.getValidationResult().getValidationMessages().containsKey(fieldName, error)).isTrue();
        });

        And("the user specifies the link cardinality with:", (DataTable values) -> {
            var persona = CurrentPersona.persona;

            assertThat(persona.linkCardinalitySpec).isNotNull();
            values.asMap(String.class, String.class).forEach(persona.linkCardinalitySpec::set);
        });

        When("the user retrieves the link cardinality with:", (DataTable values) -> {
            var commandForm = CoreUtil.getHome().getGetLinkCardinalityForm();

            values.asMap(String.class, String.class).forEach(commandForm::set);

            var commandResult = CoreUtil.getHome().getLinkCardinality(CurrentPersona.persona.userVisitPK, commandForm);
            LastCommandResult.commandResult = commandResult;

            retrievedLinkCardinality = commandResult.hasErrors() ? null : commandResult.getExecutionResult().getResult().getLinkCardinality();
        });

        When("the user retrieves the last link cardinality by entity reference", () -> {
            var persona = CurrentPersona.persona;
            var commandForm = CoreUtil.getHome().getGetLinkCardinalityForm();

            assertThat(persona.lastEntityRef).isNotNull();

            commandForm.setEntityRef(persona.lastEntityRef);

            var commandResult = CoreUtil.getHome().getLinkCardinality(persona.userVisitPK, commandForm);
            LastCommandResult.commandResult = commandResult;

            retrievedLinkCardinality = commandResult.hasErrors() ? null : commandResult.getExecutionResult().getResult().getLinkCardinality();
        });

        Then("the retrieved link cardinality has these values:", (DataTable values) -> {
            assertThat(retrievedLinkCardinality).isNotNull();

            for(var entry : values.asMap(String.class, String.class).entrySet()) {
                var actual = switch(entry.getKey()) {
                    case "LinkCardinalityName" -> retrievedLinkCardinality.getLinkCardinalityName();
                    case "IsDefault" -> retrievedLinkCardinality.getIsDefault().toString();
                    case "SortOrder" -> retrievedLinkCardinality.getSortOrder().toString();
                    case "Description" -> retrievedLinkCardinality.getDescription();
                    default -> throw new IllegalArgumentException(entry.getKey());
                };

                assertThat(actual).as(entry.getKey()).isEqualTo(entry.getValue());
            }
        });

        When("the user deletes the link cardinality with:", (DataTable values) -> {
            var commandForm = CoreUtil.getHome().getDeleteLinkCardinalityForm();

            values.asMap(String.class, String.class).forEach(commandForm::set);
            LastCommandResult.commandResult = CoreUtil.getHome().deleteLinkCardinality(CurrentPersona.persona.userVisitPK, commandForm);
        });

        When("the user sets the default link cardinality with:", (DataTable values) -> {
            var commandForm = CoreUtil.getHome().getSetDefaultLinkCardinalityForm();

            values.asMap(String.class, String.class).forEach(commandForm::set);
            LastCommandResult.commandResult = CoreUtil.getHome().setDefaultLinkCardinality(CurrentPersona.persona.userVisitPK, commandForm);
        });

        Given("the user remembers the default link cardinality", () -> {
            var commandForm = CoreUtil.getHome().getGetLinkCardinalityForm();
            var commandResult = CoreUtil.getHome().getLinkCardinality(CurrentPersona.persona.userVisitPK, commandForm);

            assertThat(commandResult.hasErrors()).isFalse();
            originalDefaultName = commandResult.getExecutionResult().getResult().getLinkCardinality().getLinkCardinalityName();
        });

        When("the user restores the original default link cardinality", () -> restoreDefault());
        After(() -> {
            if(originalDefaultName != null) {
                restoreDefault();
            }
        });

        When("the user abandons editing link cardinality {string}", (String entityName) -> {
            var persona = CurrentPersona.persona;
            var commandForm = CoreUtil.getHome().getEditLinkCardinalityForm();
            var spec = CoreUtil.getHome().getLinkCardinalityUniversalSpec();

            spec.setLinkCardinalityName(entityName);

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.ABANDON);

            LastCommandResult.commandResult = CoreUtil.getHome().editLinkCardinality(persona.userVisitPK, commandForm);

            persona.linkCardinalitySpec = null;
            persona.linkCardinalityEdit = null;
        });

        When("the user {string} a link cardinality description with:", (String operation, DataTable values) -> {
            assertThat(operation).isIn("creates", "retrieves", "deletes");

            var persona = CurrentPersona.persona;
            var fields = values.asMap(String.class, String.class);

            switch(operation) {
                case "creates" -> {
                    var commandForm = CoreUtil.getHome().getCreateLinkCardinalityDescriptionForm();
                    fields.forEach(commandForm::set);
                    LastCommandResult.commandResult = CoreUtil.getHome().createLinkCardinalityDescription(persona.userVisitPK, commandForm);
                }
                case "retrieves" -> {
                    var commandForm = CoreUtil.getHome().getGetLinkCardinalityDescriptionForm();
                    fields.forEach(commandForm::set);
                    var commandResult = CoreUtil.getHome().getLinkCardinalityDescription(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;
                    retrievedDescription = commandResult.hasErrors() ? null : commandResult.getExecutionResult().getResult().getLinkCardinalityDescription();
                }
                case "deletes" -> {
                    var commandForm = CoreUtil.getHome().getDeleteLinkCardinalityDescriptionForm();
                    fields.forEach(commandForm::set);
                    LastCommandResult.commandResult = CoreUtil.getHome().deleteLinkCardinalityDescription(persona.userVisitPK, commandForm);
                }
                default -> throw new IllegalArgumentException(operation);
            }
        });

        Then("the retrieved link cardinality description is {string}", (String description) -> {
            assertThat(retrievedDescription).isNotNull();
            assertThat(retrievedDescription.getDescription()).isEqualTo(description);
        });

        When("the user edits the link cardinality description with:", (DataTable values) -> {
            var fields = values.asMap(String.class, String.class);
            var persona = CurrentPersona.persona;
            var spec = CoreUtil.getHome().getLinkCardinalityDescriptionSpec();
            var commandForm = CoreUtil.getHome().getEditLinkCardinalityDescriptionForm();

            spec.setLinkCardinalityName(fields.get("LinkCardinalityName"));
            spec.setLanguageIsoName(fields.get("LanguageIsoName"));

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editLinkCardinalityDescription(persona.userVisitPK, commandForm);
            LastCommandResult.commandResult = commandResult;

            if(!commandResult.hasErrors()) {
                var edit = commandResult.getExecutionResult().getResult().getEdit();
                edit.setDescription(fields.get("Description"));
                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editLinkCardinalityDescription(persona.userVisitPK, commandForm);
                LastCommandResult.commandResult = commandResult;

                if(commandResult.hasErrors()) {
                    commandForm.setEditMode(EditMode.ABANDON);

                    var abandonResult = CoreUtil.getHome().editLinkCardinalityDescription(persona.userVisitPK, commandForm);
                    assertThat(abandonResult.hasErrors()).isFalse();
                }
            }
        });

        When("the user lists descriptions for link cardinality {string} with limit {string}", (String entityName, String limit) -> {
            var commandForm = CoreUtil.getHome().getGetLinkCardinalityDescriptionsForm();

            commandForm.setLinkCardinalityName(entityName);
            commandForm.setLimits(Map.of(LinkCardinalityDescriptionConstants.ENTITY_TYPE_NAME, new Limit(limit)));

            var commandResult = CoreUtil.getHome().getLinkCardinalityDescriptions(CurrentPersona.persona.userVisitPK, commandForm);
            LastCommandResult.commandResult = commandResult;

            retrievedDescriptions = commandResult.hasErrors() ? null : commandResult.getExecutionResult().getResult().getLinkCardinalityDescriptions();
            retrievedDescriptionCount = commandResult.hasErrors() ? null : commandResult.getExecutionResult().getResult().getLinkCardinalityDescriptionCount();
        });

        Then("the link cardinality description page has {int} entries out of {long}", (Integer count, Long total) -> {
            assertThat(retrievedDescriptions).hasSize(count);
            assertThat(retrievedDescriptionCount).isEqualTo(total);
        });

    }

    private void restoreDefault()
            throws javax.naming.NamingException {
        var commandForm = CoreUtil.getHome().getSetDefaultLinkCardinalityForm();

        assertThat(originalDefaultName).isNotNull();

        commandForm.setLinkCardinalityName(originalDefaultName);

        var commandResult = CoreUtil.getHome().setDefaultLinkCardinality(CurrentPersona.persona.userVisitPK, commandForm);
        LastCommandResult.commandResult = commandResult;

        assertThat(commandResult.hasErrors()).isFalse();

        originalDefaultName = null;
    }

}
