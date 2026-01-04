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

package com.echothree.cucumber.content;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.EditContentCategoryItemResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class ContentCategoryItemSteps implements En {

    public ContentCategoryItemSteps() {
        When("^the user begins entering a new content category item$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCategoryItemForm).isNull();
                    assertThat(persona.deleteContentCategoryItemForm).isNull();
                    assertThat(persona.contentCategoryItemSpec).isNull();

                    persona.createContentCategoryItemForm = ContentUtil.getHome().getCreateContentCategoryItemForm();
                });

        When("^the user adds the new content category item$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCategoryItemForm).isNotNull();

                    var contentUtil = ContentUtil.getHome();
                    var createContentCategoryItemForm = contentUtil.getCreateContentCategoryItemForm();

                    createContentCategoryItemForm.set(persona.createContentCategoryItemForm.get());

                    LastCommandResult.commandResult = contentUtil.createContentCategoryItem(persona.userVisitPK, createContentCategoryItemForm);

                    persona.createContentCategoryItemForm = null;
                });

        When("^the user begins deleting a content category item$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCategoryItemForm).isNull();
                    assertThat(persona.deleteContentCategoryItemForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.deleteContentCategoryItemForm = ContentUtil.getHome().getDeleteContentCategoryItemForm();
                });

        When("^the user deletes the content category item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteContentCategoryItemForm = persona.deleteContentCategoryItemForm;

                    assertThat(deleteContentCategoryItemForm).isNotNull();

                    LastCommandResult.commandResult = ContentUtil.getHome().deleteContentCategoryItem(persona.userVisitPK, deleteContentCategoryItemForm);

                    persona.deleteContentCategoryItemForm = null;
                });

        When("^the user begins specifying a content category item to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createContentCategoryItemForm).isNull();
                    assertThat(persona.deleteContentCategoryItemForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.contentCategoryItemSpec = ContentUtil.getHome().getContentCategoryItemSpec();
                });

        When("^the user begins editing the content category item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contentCategoryItemSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = ContentUtil.getHome().getEditContentCategoryItemForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContentUtil.getHome().editContentCategoryItem(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    if(!commandResult.getHasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditContentCategoryItemResult)executionResult.getResult();

                        persona.contentCategoryItemEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the content category item$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.contentCategoryItemSpec;
                    var edit = persona.contentCategoryItemEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = ContentUtil.getHome().getEditContentCategoryItemForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    LastCommandResult.commandResult = ContentUtil.getHome().editContentCategoryItem(persona.userVisitPK, commandForm);

                    persona.contentCategoryItemSpec = null;
                    persona.contentCategoryItemEdit = null;
                });

        When("^the user sets the content category item's content collection name to \"([^\"]*)\"$",
                (String contentCollectionName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryItemForm = persona.createContentCategoryItemForm;
                    var deleteContentCategoryItemForm = persona.deleteContentCategoryItemForm;
                    var contentCategoryItemSpec = persona.contentCategoryItemSpec;

                    assertThat(createContentCategoryItemForm != null || deleteContentCategoryItemForm != null
                            || contentCategoryItemSpec != null).isTrue();

                    if(createContentCategoryItemForm != null) {
                        createContentCategoryItemForm.setContentCollectionName(contentCollectionName);
                    } else if(deleteContentCategoryItemForm != null) {
                        deleteContentCategoryItemForm.setContentCollectionName(contentCollectionName);
                    } else {
                        contentCategoryItemSpec.setContentCollectionName(contentCollectionName);
                    }
                });

        When("^the user sets the content category item's content catalog name to \"([^\"]*)\"$",
                (String contentCatalogName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryItemForm = persona.createContentCategoryItemForm;
                    var deleteContentCategoryItemForm = persona.deleteContentCategoryItemForm;
                    var contentCategoryItemSpec = persona.contentCategoryItemSpec;

                    assertThat(createContentCategoryItemForm != null || deleteContentCategoryItemForm != null
                            || contentCategoryItemSpec != null).isTrue();

                    if(createContentCategoryItemForm != null) {
                        createContentCategoryItemForm.setContentCatalogName(contentCatalogName);
                    } else if(deleteContentCategoryItemForm != null) {
                        deleteContentCategoryItemForm.setContentCatalogName(contentCatalogName);
                    } else {
                        contentCategoryItemSpec.setContentCatalogName(contentCatalogName);
                    }
                });

        When("^the user sets the content category item's content category name to \"([^\"]*)\"$",
                (String contentCategoryName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryItemForm = persona.createContentCategoryItemForm;
                    var deleteContentCategoryItemForm = persona.deleteContentCategoryItemForm;
                    var contentCategoryItemSpec = persona.contentCategoryItemSpec;

                    assertThat(createContentCategoryItemForm != null || deleteContentCategoryItemForm != null
                            || contentCategoryItemSpec != null).isTrue();

                    if(createContentCategoryItemForm != null) {
                        createContentCategoryItemForm.setContentCategoryName(contentCategoryName);
                    } else if(deleteContentCategoryItemForm != null) {
                        deleteContentCategoryItemForm.setContentCategoryName(contentCategoryName);
                    } else {
                        contentCategoryItemSpec.setContentCategoryName(contentCategoryName);
                    }
                });

        When("^the user sets the content category item's item name to \"([^\"]*)\"$",
                (String itemName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryItemForm = persona.createContentCategoryItemForm;
                    var deleteContentCategoryItemForm = persona.deleteContentCategoryItemForm;
                    var contentCategoryItemSpec = persona.contentCategoryItemSpec;

                    assertThat(createContentCategoryItemForm != null || deleteContentCategoryItemForm != null
                            || contentCategoryItemSpec != null).isTrue();

                    if(createContentCategoryItemForm != null) {
                        createContentCategoryItemForm.setItemName(itemName);
                    } else if(deleteContentCategoryItemForm != null) {
                        deleteContentCategoryItemForm.setItemName(itemName);
                    } else {
                        contentCategoryItemSpec.setItemName(itemName);
                    }
                });

        When("^the user sets the content category item's inventory condition name to \"([^\"]*)\"$",
                (String inventoryConditionName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryItemForm = persona.createContentCategoryItemForm;
                    var deleteContentCategoryItemForm = persona.deleteContentCategoryItemForm;
                    var contentCategoryItemSpec = persona.contentCategoryItemSpec;

                    assertThat(createContentCategoryItemForm != null || deleteContentCategoryItemForm != null
                            || contentCategoryItemSpec != null).isTrue();

                    if(createContentCategoryItemForm != null) {
                        createContentCategoryItemForm.setInventoryConditionName(inventoryConditionName);
                    } else if(deleteContentCategoryItemForm != null) {
                        deleteContentCategoryItemForm.setInventoryConditionName(inventoryConditionName);
                    } else {
                        contentCategoryItemSpec.setInventoryConditionName(inventoryConditionName);
                    }
                });

        When("^the user sets the content category item's unit of measure type name to \"([^\"]*)\"$",
                (String unitOfMeasureTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryItemForm = persona.createContentCategoryItemForm;
                    var deleteContentCategoryItemForm = persona.deleteContentCategoryItemForm;
                    var contentCategoryItemSpec = persona.contentCategoryItemSpec;

                    assertThat(createContentCategoryItemForm != null || deleteContentCategoryItemForm != null
                            || contentCategoryItemSpec != null).isTrue();

                    if(createContentCategoryItemForm != null) {
                        createContentCategoryItemForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else if(deleteContentCategoryItemForm != null) {
                        deleteContentCategoryItemForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    } else {
                        contentCategoryItemSpec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    }
                });

        When("^the user sets the content category item's currency iso name to \"([^\"]*)\"$",
                (String currencyIsoName) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryItemForm = persona.createContentCategoryItemForm;
                    var deleteContentCategoryItemForm = persona.deleteContentCategoryItemForm;
                    var contentCategoryItemSpec = persona.contentCategoryItemSpec;

                    assertThat(createContentCategoryItemForm != null || deleteContentCategoryItemForm != null
                            || contentCategoryItemSpec != null).isTrue();

                    if(createContentCategoryItemForm != null) {
                        createContentCategoryItemForm.setCurrencyIsoName(currencyIsoName);
                    } else if(deleteContentCategoryItemForm != null) {
                        deleteContentCategoryItemForm.setCurrencyIsoName(currencyIsoName);
                    } else {
                        contentCategoryItemSpec.setCurrencyIsoName(currencyIsoName);
                    }
                });

        When("^the user sets the content category item to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryItemForm = persona.createContentCategoryItemForm;
                    var contentCategoryItemEdit = persona.contentCategoryItemEdit;

                    assertThat(createContentCategoryItemForm != null || contentCategoryItemEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    Objects.requireNonNullElse(createContentCategoryItemForm, contentCategoryItemEdit).setIsDefault(isDefault);
                });

        When("^the user sets the content category item's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createContentCategoryItemForm = persona.createContentCategoryItemForm;
                    var contentCategoryItemEdit = persona.contentCategoryItemEdit;

                    assertThat(createContentCategoryItemForm != null || contentCategoryItemEdit != null).isTrue();

                    Objects.requireNonNullElse(createContentCategoryItemForm, contentCategoryItemEdit).setSortOrder(sortOrder);
                });
    }

}
