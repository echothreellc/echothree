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

package com.echothree.cucumber.tag;

import com.echothree.control.user.tag.common.TagUtil;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class TagScopeEntityTypeSteps implements En {

    public TagScopeEntityTypeSteps() {
        When("^the user begins entering a new tag scope entity type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createTagScopeEntityTypeForm).isNull();
                    assertThat(persona.deleteTagScopeEntityTypeForm).isNull();

                    persona.createTagScopeEntityTypeForm = TagUtil.getHome().getCreateTagScopeEntityTypeForm();
                });

        When("^the user adds the new tag scope entity type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createTagScopeEntityTypeForm = persona.createTagScopeEntityTypeForm;

                    assertThat(createTagScopeEntityTypeForm).isNotNull();

                    LastCommandResult.commandResult = TagUtil.getHome().createTagScopeEntityType(persona.userVisitPK, createTagScopeEntityTypeForm);

                    persona.createTagScopeEntityTypeForm = null;
                });

        When("^the user begins deleting a tag scope entity type$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createTagScopeEntityTypeForm).isNull();
                    assertThat(persona.deleteTagScopeEntityTypeForm).isNull();

                    persona.deleteTagScopeEntityTypeForm = TagUtil.getHome().getDeleteTagScopeEntityTypeForm();
                });

        When("^the user deletes the tag scope entity type$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteTagScopeEntityTypeForm = persona.deleteTagScopeEntityTypeForm;

                    assertThat(deleteTagScopeEntityTypeForm).isNotNull();

                    LastCommandResult.commandResult = TagUtil.getHome().deleteTagScopeEntityType(persona.userVisitPK, deleteTagScopeEntityTypeForm);

                    persona.deleteTagScopeEntityTypeForm = null;
                });

        When("^the user sets the tag scope entity type's tag scope name to \"([^\"]*)\"$",
                (String offerName) -> {
                    var persona = CurrentPersona.persona;
                    var createTagScopeEntityTypeForm = persona.createTagScopeEntityTypeForm;
                    var deleteTagScopeEntityTypeForm = persona.deleteTagScopeEntityTypeForm;

                    assertThat(createTagScopeEntityTypeForm != null || deleteTagScopeEntityTypeForm != null).isTrue();

                    Objects.requireNonNullElse(createTagScopeEntityTypeForm, deleteTagScopeEntityTypeForm).setTagScopeName(offerName);
                });

        When("^the user sets the tag scope entity type's tag scope name to the last tag scope added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createTagScopeEntityTypeForm = persona.createTagScopeEntityTypeForm;
                    var deleteTagScopeEntityTypeForm = persona.deleteTagScopeEntityTypeForm;

                    assertThat(createTagScopeEntityTypeForm != null || deleteTagScopeEntityTypeForm != null).isTrue();

                    Objects.requireNonNullElse(createTagScopeEntityTypeForm, deleteTagScopeEntityTypeForm).setTagScopeName(persona.lastTagScopeName);
                });

        When("^the user sets the tag scope entity type's component vendor name to \"([^\"]*)\"$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createTagScopeEntityTypeForm = persona.createTagScopeEntityTypeForm;
                    var deleteTagScopeEntityTypeForm = persona.deleteTagScopeEntityTypeForm;

                    assertThat(createTagScopeEntityTypeForm != null || deleteTagScopeEntityTypeForm != null).isTrue();

                    Objects.requireNonNullElse(createTagScopeEntityTypeForm, deleteTagScopeEntityTypeForm).setComponentVendorName(componentVendorName);
                });

        When("^the user sets the tag scope entity type's item name to the last component vendor added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createTagScopeEntityTypeForm = persona.createTagScopeEntityTypeForm;
                    var deleteTagScopeEntityTypeForm = persona.deleteTagScopeEntityTypeForm;

                    assertThat(createTagScopeEntityTypeForm != null || deleteTagScopeEntityTypeForm != null).isTrue();

                    Objects.requireNonNullElse(createTagScopeEntityTypeForm, deleteTagScopeEntityTypeForm).setComponentVendorName(persona.lastComponentVendorName);
                });

        When("^the user sets the tag scope entity type's entity type name to \"([^\"]*)\"$",
                (String componentVendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createTagScopeEntityTypeForm = persona.createTagScopeEntityTypeForm;
                    var deleteTagScopeEntityTypeForm = persona.deleteTagScopeEntityTypeForm;

                    assertThat(createTagScopeEntityTypeForm != null || deleteTagScopeEntityTypeForm != null).isTrue();

                    Objects.requireNonNullElse(createTagScopeEntityTypeForm, deleteTagScopeEntityTypeForm).setEntityTypeName(componentVendorName);
                });

        When("^the user sets the tag scope entity type's item name to the last entity type added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createTagScopeEntityTypeForm = persona.createTagScopeEntityTypeForm;
                    var deleteTagScopeEntityTypeForm = persona.deleteTagScopeEntityTypeForm;

                    assertThat(createTagScopeEntityTypeForm != null || deleteTagScopeEntityTypeForm != null).isTrue();

                    Objects.requireNonNullElse(createTagScopeEntityTypeForm, deleteTagScopeEntityTypeForm).setEntityTypeName(persona.lastEntityTypeName);
                });
    }

}
