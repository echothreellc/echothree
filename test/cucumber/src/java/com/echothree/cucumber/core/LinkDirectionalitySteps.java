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
import com.echothree.model.control.core.common.transfer.LinkDirectionalityTransfer;
import com.echothree.model.control.core.common.transfer.LinkDirectionalityDescriptionTransfer;
import com.echothree.util.common.transfer.Limit;
import com.echothree.model.data.core.common.LinkDirectionalityDescriptionConstants;
import java.util.Map;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class LinkDirectionalitySteps implements En {

    private LinkDirectionalityTransfer retrievedLinkDirectionality;
    private LinkDirectionalityDescriptionTransfer retrievedDescription;
    private List<LinkDirectionalityDescriptionTransfer> retrievedDescriptions;
    private Long retrievedDescriptionCount;
    private String originalDefaultName;

    public LinkDirectionalitySteps() {
        When("^the user begins entering a new link directionality$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkDirectionalityForm).isNull();
                    assertThat(persona.deleteLinkDirectionalityForm).isNull();
                    assertThat(persona.linkDirectionalitySpec).isNull();

                    persona.createLinkDirectionalityForm = CoreUtil.getHome().getCreateLinkDirectionalityForm();
                });

        And("^the user adds the new link directionality",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLinkDirectionalityForm = persona.createLinkDirectionalityForm;

                    assertThat(createLinkDirectionalityForm).isNotNull();

                    var commandResult = CoreUtil.getHome().createLinkDirectionality(persona.userVisitPK, createLinkDirectionalityForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastLinkDirectionalityName = commandResult.getHasErrors() ? null : result.getLinkDirectionalityName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createLinkDirectionalityForm = null;
                });

        When("^the user begins deleting a link directionality$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkDirectionalityForm).isNull();
                    assertThat(persona.deleteLinkDirectionalityForm).isNull();
                    assertThat(persona.linkDirectionalitySpec).isNull();

                    persona.deleteLinkDirectionalityForm = CoreUtil.getHome().getDeleteLinkDirectionalityForm();
                });

        And("^the user deletes the link directionality",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteLinkDirectionalityForm = persona.deleteLinkDirectionalityForm;

                    assertThat(deleteLinkDirectionalityForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().deleteLinkDirectionality(persona.userVisitPK, deleteLinkDirectionalityForm);

                    persona.deleteLinkDirectionalityForm = null;
                });

        When("^the user begins specifying a link directionality to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createLinkDirectionalityForm).isNull();
                    assertThat(persona.deleteLinkDirectionalityForm).isNull();
                    assertThat(persona.linkDirectionalitySpec).isNull();

                    persona.linkDirectionalitySpec = CoreUtil.getHome().getLinkDirectionalityUniversalSpec();
                });

        When("^the user begins editing the link directionality$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.linkDirectionalitySpec;

                    assertThat(spec).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditLinkDirectionalityForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editLinkDirectionality(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.linkDirectionalityEdit = result.getEdit();
                    } else {
                        persona.linkDirectionalitySpec = null;
                    }
                });

        And("^the user finishes editing the link directionality",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.linkDirectionalitySpec;
                    var edit = persona.linkDirectionalityEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = CoreUtil.getHome().getEditLinkDirectionalityForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = CoreUtil.getHome().editLinkDirectionality(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.linkDirectionalitySpec = null;
                    persona.linkDirectionalityEdit = null;
                });

        And("^the user sets the link directionality's link directionality name to \"([a-zA-Z0-9-_]*)\"$",
                (String linkDirectionalityName) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkDirectionalityForm = persona.createLinkDirectionalityForm;
                    var deleteLinkDirectionalityForm = persona.deleteLinkDirectionalityForm;
                    var linkDirectionalitySpec = persona.linkDirectionalitySpec;

                    assertThat(createLinkDirectionalityForm != null || deleteLinkDirectionalityForm != null
                            || linkDirectionalitySpec != null).isTrue();

                    if(createLinkDirectionalityForm != null) {
                        createLinkDirectionalityForm.setLinkDirectionalityName(linkDirectionalityName);
                    } else if(deleteLinkDirectionalityForm != null) {
                        deleteLinkDirectionalityForm.setLinkDirectionalityName(linkDirectionalityName);
                    } else {
                        linkDirectionalitySpec.setLinkDirectionalityName(linkDirectionalityName);
                    }
                });

        And("^the user sets the link directionality's link directionality name to the last link directionality added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createLinkDirectionalityForm = persona.createLinkDirectionalityForm;
                    var deleteLinkDirectionalityForm = persona.deleteLinkDirectionalityForm;
                    var linkDirectionalitySpec = persona.linkDirectionalitySpec;

                    assertThat(createLinkDirectionalityForm != null || deleteLinkDirectionalityForm != null
                            || linkDirectionalitySpec != null).isTrue();

                    if(createLinkDirectionalityForm != null) {
                        createLinkDirectionalityForm.setLinkDirectionalityName(persona.lastLinkDirectionalityName);
                    } else if(deleteLinkDirectionalityForm != null) {
                        deleteLinkDirectionalityForm.setLinkDirectionalityName(persona.lastLinkDirectionalityName);
                    } else {
                        linkDirectionalitySpec.setLinkDirectionalityName(persona.lastLinkDirectionalityName);
                    }
                });

        And("^the user sets the link directionality's new link directionality name to \"([a-zA-Z0-9-_]*)\"$",
                (String linkDirectionalityName) -> {
                    var persona = CurrentPersona.persona;
                    var linkDirectionalityEdit = persona.linkDirectionalityEdit;

                    assertThat(linkDirectionalityEdit).isNotNull();

                    linkDirectionalityEdit.setLinkDirectionalityName(linkDirectionalityName);
                });

        And("^the user sets the link directionality to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkDirectionalityForm = persona.createLinkDirectionalityForm;
                    var linkDirectionalityEdit = persona.linkDirectionalityEdit;

                    assertThat(createLinkDirectionalityForm != null || linkDirectionalityEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createLinkDirectionalityForm != null) {
                        createLinkDirectionalityForm.setIsDefault(isDefault);
                    } else {
                        linkDirectionalityEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the link directionality's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkDirectionalityForm = persona.createLinkDirectionalityForm;
                    var linkDirectionalityEdit = persona.linkDirectionalityEdit;

                    assertThat(createLinkDirectionalityForm != null || linkDirectionalityEdit != null).isTrue();

                    if(createLinkDirectionalityForm != null) {
                        createLinkDirectionalityForm.setSortOrder(sortOrder);
                    } else {
                        linkDirectionalityEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the link directionality's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createLinkDirectionalityForm = persona.createLinkDirectionalityForm;
                    var linkDirectionalityEdit = persona.linkDirectionalityEdit;

                    assertThat(createLinkDirectionalityForm != null || linkDirectionalityEdit != null).isTrue();

                    if(createLinkDirectionalityForm != null) {
                        createLinkDirectionalityForm.setDescription(description);
                    } else {
                        linkDirectionalityEdit.setDescription(description);
                    }
                });

        And("the user enters these link directionality values:", (DataTable values) -> {
            var persona = CurrentPersona.persona;
            var edit = persona.createLinkDirectionalityForm == null ? persona.linkDirectionalityEdit : persona.createLinkDirectionalityForm;

            assertThat(edit).isNotNull();
            values.asMap(String.class, String.class).forEach(edit::set);
        });

        And("the user clears the link directionality field {string}", (String fieldName) -> {
            var persona = CurrentPersona.persona;
            var edit = persona.createLinkDirectionalityForm == null ? persona.linkDirectionalityEdit : persona.createLinkDirectionalityForm;

            assertThat(edit).isNotNull();
            edit.set(fieldName, null);
        });

        Then("the link directionality field {string} validation error {string} should occur", (String fieldName, String error) -> {
            var commandResult = LastCommandResult.commandResult;

            assertThat(commandResult.hasValidationErrors()).isTrue();
            assertThat(commandResult.getValidationResult().getValidationMessages().containsKey(fieldName, error)).isTrue();
        });

        And("the user specifies the link directionality with:", (DataTable values) -> {
            var persona = CurrentPersona.persona;

            assertThat(persona.linkDirectionalitySpec).isNotNull();
            values.asMap(String.class, String.class).forEach(persona.linkDirectionalitySpec::set);
        });

        When("the user retrieves the link directionality with:", (DataTable values) -> {
            var commandForm = CoreUtil.getHome().getGetLinkDirectionalityForm();

            values.asMap(String.class, String.class).forEach(commandForm::set);

            var commandResult = CoreUtil.getHome().getLinkDirectionality(CurrentPersona.persona.userVisitPK, commandForm);
            LastCommandResult.commandResult = commandResult;

            retrievedLinkDirectionality = commandResult.hasErrors() ? null : commandResult.getExecutionResult().getResult().getLinkDirectionality();
        });

        When("the user retrieves the last link directionality by entity reference", () -> {
            var persona = CurrentPersona.persona;
            var commandForm = CoreUtil.getHome().getGetLinkDirectionalityForm();

            assertThat(persona.lastEntityRef).isNotNull();

            commandForm.setEntityRef(persona.lastEntityRef);

            var commandResult = CoreUtil.getHome().getLinkDirectionality(persona.userVisitPK, commandForm);
            LastCommandResult.commandResult = commandResult;

            retrievedLinkDirectionality = commandResult.hasErrors() ? null : commandResult.getExecutionResult().getResult().getLinkDirectionality();
        });

        Then("the retrieved link directionality has these values:", (DataTable values) -> {
            assertThat(retrievedLinkDirectionality).isNotNull();

            for(var entry : values.asMap(String.class, String.class).entrySet()) {
                var actual = switch(entry.getKey()) {
                    case "LinkDirectionalityName" -> retrievedLinkDirectionality.getLinkDirectionalityName();
                    case "IsDefault" -> retrievedLinkDirectionality.getIsDefault().toString();
                    case "SortOrder" -> retrievedLinkDirectionality.getSortOrder().toString();
                    case "Description" -> retrievedLinkDirectionality.getDescription();
                    default -> throw new IllegalArgumentException(entry.getKey());
                };

                assertThat(actual).as(entry.getKey()).isEqualTo(entry.getValue());
            }
        });

        When("the user deletes the link directionality with:", (DataTable values) -> {
            var commandForm = CoreUtil.getHome().getDeleteLinkDirectionalityForm();

            values.asMap(String.class, String.class).forEach(commandForm::set);
            LastCommandResult.commandResult = CoreUtil.getHome().deleteLinkDirectionality(CurrentPersona.persona.userVisitPK, commandForm);
        });

        When("the user sets the default link directionality with:", (DataTable values) -> {
            var commandForm = CoreUtil.getHome().getSetDefaultLinkDirectionalityForm();

            values.asMap(String.class, String.class).forEach(commandForm::set);
            LastCommandResult.commandResult = CoreUtil.getHome().setDefaultLinkDirectionality(CurrentPersona.persona.userVisitPK, commandForm);
        });

        Given("the user remembers the default link directionality", () -> {
            var commandForm = CoreUtil.getHome().getGetLinkDirectionalityForm();
            var commandResult = CoreUtil.getHome().getLinkDirectionality(CurrentPersona.persona.userVisitPK, commandForm);

            assertThat(commandResult.hasErrors()).isFalse();
            originalDefaultName = commandResult.getExecutionResult().getResult().getLinkDirectionality().getLinkDirectionalityName();
        });

        When("the user restores the original default link directionality", () -> restoreDefault());
        After(() -> {
            if(originalDefaultName != null) {
                restoreDefault();
            }
        });

        When("the user abandons editing link directionality {string}", (String entityName) -> {
            var persona = CurrentPersona.persona;
            var commandForm = CoreUtil.getHome().getEditLinkDirectionalityForm();
            var spec = CoreUtil.getHome().getLinkDirectionalityUniversalSpec();

            spec.setLinkDirectionalityName(entityName);

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.ABANDON);

            LastCommandResult.commandResult = CoreUtil.getHome().editLinkDirectionality(persona.userVisitPK, commandForm);

            persona.linkDirectionalitySpec = null;
            persona.linkDirectionalityEdit = null;
        });

        When("^the user (creates|retrieves|deletes) a link directionality description with:$", (String operation, DataTable values) -> {
            var persona = CurrentPersona.persona;
            var fields = values.asMap(String.class, String.class);

            switch(operation) {
                case "creates" -> {
                    var commandForm = CoreUtil.getHome().getCreateLinkDirectionalityDescriptionForm();
                    fields.forEach(commandForm::set);
                    LastCommandResult.commandResult = CoreUtil.getHome().createLinkDirectionalityDescription(persona.userVisitPK, commandForm);
                }
                case "retrieves" -> {
                    var commandForm = CoreUtil.getHome().getGetLinkDirectionalityDescriptionForm();
                    fields.forEach(commandForm::set);
                    var commandResult = CoreUtil.getHome().getLinkDirectionalityDescription(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;
                    retrievedDescription = commandResult.hasErrors() ? null : commandResult.getExecutionResult().getResult().getLinkDirectionalityDescription();
                }
                case "deletes" -> {
                    var commandForm = CoreUtil.getHome().getDeleteLinkDirectionalityDescriptionForm();
                    fields.forEach(commandForm::set);
                    LastCommandResult.commandResult = CoreUtil.getHome().deleteLinkDirectionalityDescription(persona.userVisitPK, commandForm);
                }
                default -> throw new IllegalArgumentException(operation);
            }
        });

        Then("the retrieved link directionality description is {string}", (String description) -> {
            assertThat(retrievedDescription).isNotNull();
            assertThat(retrievedDescription.getDescription()).isEqualTo(description);
        });

        When("the user edits the link directionality description with:", (DataTable values) -> {
            var fields = values.asMap(String.class, String.class);
            var persona = CurrentPersona.persona;
            var spec = CoreUtil.getHome().getLinkDirectionalityDescriptionSpec();
            var commandForm = CoreUtil.getHome().getEditLinkDirectionalityDescriptionForm();

            spec.setLinkDirectionalityName(fields.get("LinkDirectionalityName"));
            spec.setLanguageIsoName(fields.get("LanguageIsoName"));

            commandForm.setSpec(spec);
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = CoreUtil.getHome().editLinkDirectionalityDescription(persona.userVisitPK, commandForm);
            LastCommandResult.commandResult = commandResult;

            if(!commandResult.hasErrors()) {
                var edit = commandResult.getExecutionResult().getResult().getEdit();

                edit.setDescription(fields.get("Description"));

                commandForm.setEdit(edit);
                commandForm.setEditMode(EditMode.UPDATE);

                commandResult = CoreUtil.getHome().editLinkDirectionalityDescription(persona.userVisitPK, commandForm);
                LastCommandResult.commandResult = commandResult;

                if(commandResult.hasErrors()) {
                    commandForm.setEditMode(EditMode.ABANDON);
                    var abandonResult = CoreUtil.getHome().editLinkDirectionalityDescription(persona.userVisitPK, commandForm);
                    assertThat(abandonResult.hasErrors()).isFalse();
                }
            }
        });

        When("the user lists descriptions for link directionality {string} with limit {string}", (String entityName, String limit) -> {
            var commandForm = CoreUtil.getHome().getGetLinkDirectionalityDescriptionsForm();

            commandForm.setLinkDirectionalityName(entityName);
            commandForm.setLimits(Map.of(LinkDirectionalityDescriptionConstants.ENTITY_TYPE_NAME, new Limit(limit)));

            var commandResult = CoreUtil.getHome().getLinkDirectionalityDescriptions(CurrentPersona.persona.userVisitPK, commandForm);
            LastCommandResult.commandResult = commandResult;

            retrievedDescriptions = commandResult.hasErrors() ? null : commandResult.getExecutionResult().getResult().getLinkDirectionalityDescriptions();
            retrievedDescriptionCount = commandResult.hasErrors() ? null : commandResult.getExecutionResult().getResult().getLinkDirectionalityDescriptionCount();
        });

        Then("the link directionality description page has {int} entries out of {long}", (Integer count, Long total) -> {
            assertThat(retrievedDescriptions).hasSize(count);
            assertThat(retrievedDescriptionCount).isEqualTo(total);
        });

    }

    private void restoreDefault() throws javax.naming.NamingException {
        var commandForm = CoreUtil.getHome().getSetDefaultLinkDirectionalityForm();

        assertThat(originalDefaultName).isNotNull();

        commandForm.setLinkDirectionalityName(originalDefaultName);

        var commandResult = CoreUtil.getHome().setDefaultLinkDirectionality(CurrentPersona.persona.userVisitPK, commandForm);
        LastCommandResult.commandResult = commandResult;

        assertThat(commandResult.hasErrors()).isFalse();

        originalDefaultName = null;
    }

}
