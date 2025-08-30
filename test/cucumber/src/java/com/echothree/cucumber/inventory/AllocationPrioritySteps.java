// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.cucumber.inventory;

import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.CreateAllocationPriorityResult;
import com.echothree.control.user.inventory.common.result.EditAllocationPriorityResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class AllocationPrioritySteps implements En {

    public AllocationPrioritySteps() {
        When("^the user begins entering a new allocation priority",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createAllocationPriorityForm).isNull();
                    assertThat(persona.deleteAllocationPriorityForm).isNull();
                    assertThat(persona.allocationPrioritySpec).isNull();

                    persona.createAllocationPriorityForm = InventoryUtil.getHome().getCreateAllocationPriorityForm();
                });

        And("^the user adds the new allocation priority",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createAllocationPriorityForm = persona.createAllocationPriorityForm;

                    assertThat(createAllocationPriorityForm).isNotNull();

                    var commandResult = InventoryUtil.getHome().createAllocationPriority(persona.userVisitPK, createAllocationPriorityForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateAllocationPriorityResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastAllocationPriorityName = commandResult.getHasErrors() ? null : result.getAllocationPriorityName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createAllocationPriorityForm = null;
                });

        When("^the user begins deleting an allocation priority",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createAllocationPriorityForm).isNull();
                    assertThat(persona.deleteAllocationPriorityForm).isNull();
                    assertThat(persona.allocationPrioritySpec).isNull();

                    persona.deleteAllocationPriorityForm = InventoryUtil.getHome().getDeleteAllocationPriorityForm();
                });

        And("^the user deletes the allocation priority",
                () -> {
                    var persona = CurrentPersona.persona;
                    var deleteAllocationPriorityForm = persona.deleteAllocationPriorityForm;

                    assertThat(deleteAllocationPriorityForm).isNotNull();

                    LastCommandResult.commandResult = InventoryUtil.getHome().deleteAllocationPriority(persona.userVisitPK, deleteAllocationPriorityForm);

                    persona.deleteAllocationPriorityForm = null;
                });

        When("^the user begins specifying an allocation priority to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createAllocationPriorityForm).isNull();
                    assertThat(persona.deleteAllocationPriorityForm).isNull();
                    assertThat(persona.allocationPrioritySpec).isNull();

                    persona.allocationPrioritySpec = InventoryUtil.getHome().getAllocationPriorityUniversalSpec();
                });

        When("^the user begins editing the allocation priority",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.allocationPrioritySpec;

                    assertThat(spec).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditAllocationPriorityForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = InventoryUtil.getHome().editAllocationPriority(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditAllocationPriorityResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.allocationPriorityEdit = result.getEdit();
                    }
                });

        And("^the user finishes editing the allocation priority",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.allocationPrioritySpec;
                    var edit = persona.allocationPriorityEdit;

                    assertThat(spec).isNotNull();
                    assertThat(edit).isNotNull();

                    var commandForm = InventoryUtil.getHome().getEditAllocationPriorityForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = InventoryUtil.getHome().editAllocationPriority(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.allocationPrioritySpec = null;
                    persona.allocationPriorityEdit = null;
                });

        And("^the user sets the allocation priority's allocation priority name to \"([a-zA-Z0-9-_]*)\"$",
                (String allocationPriorityName) -> {
                    var persona = CurrentPersona.persona;
                    var createAllocationPriorityForm = persona.createAllocationPriorityForm;
                    var deleteAllocationPriorityForm = persona.deleteAllocationPriorityForm;
                    var allocationPrioritySpec = persona.allocationPrioritySpec;

                    assertThat(createAllocationPriorityForm != null || deleteAllocationPriorityForm != null
                            || allocationPrioritySpec != null).isTrue();

                    if(createAllocationPriorityForm != null) {
                        createAllocationPriorityForm.setAllocationPriorityName(allocationPriorityName);
                    } else if(deleteAllocationPriorityForm != null) {
                        deleteAllocationPriorityForm.setAllocationPriorityName(allocationPriorityName);
                    } else {
                        allocationPrioritySpec.setAllocationPriorityName(allocationPriorityName);
                    }
                });

        And("^the user sets the allocation priority's allocation priority name to the last allocation priority added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createAllocationPriorityForm = persona.createAllocationPriorityForm;
                    var deleteAllocationPriorityForm = persona.deleteAllocationPriorityForm;
                    var allocationPrioritySpec = persona.allocationPrioritySpec;

                    assertThat(createAllocationPriorityForm != null || deleteAllocationPriorityForm != null
                            || allocationPrioritySpec != null).isTrue();

                    if(createAllocationPriorityForm != null) {
                        createAllocationPriorityForm.setAllocationPriorityName(persona.lastAllocationPriorityName);
                    } else if(deleteAllocationPriorityForm != null) {
                        deleteAllocationPriorityForm.setAllocationPriorityName(persona.lastAllocationPriorityName);
                    } else {
                        allocationPrioritySpec.setAllocationPriorityName(persona.lastAllocationPriorityName);
                    }
                });

        And("^the user sets the allocation priority's new allocation priority name to \"([a-zA-Z0-9-_]*)\"$",
                (String allocationPriorityName) -> {
                    var persona = CurrentPersona.persona;
                    var allocationPriorityEdit = persona.allocationPriorityEdit;

                    assertThat(allocationPriorityEdit).isNotNull();

                    allocationPriorityEdit.setAllocationPriorityName(allocationPriorityName);
                });

        And("^the user sets the allocation priority's priority to \"([^\"]*)\"$",
                (String priority) -> {
                    var persona = CurrentPersona.persona;
                    var createAllocationPriorityForm = persona.createAllocationPriorityForm;
                    var allocationPriorityEdit = persona.allocationPriorityEdit;

                    assertThat(createAllocationPriorityForm != null || allocationPriorityEdit != null).isTrue();

                    if(createAllocationPriorityForm != null) {
                        createAllocationPriorityForm.setPriority(priority);
                    } else {
                        allocationPriorityEdit.setPriority(priority);
                    }
                });

        And("^the user sets the allocation priority to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;
                    var createAllocationPriorityForm = persona.createAllocationPriorityForm;
                    var allocationPriorityEdit = persona.allocationPriorityEdit;

                    assertThat(createAllocationPriorityForm != null || allocationPriorityEdit != null).isTrue();

                    isDefault = Boolean.valueOf(isDefault.equals("be")).toString();
                    if(createAllocationPriorityForm != null) {
                        createAllocationPriorityForm.setIsDefault(isDefault);
                    } else {
                        allocationPriorityEdit.setIsDefault(isDefault);
                    }
                });

        And("^the user sets the allocation priority's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;
                    var createAllocationPriorityForm = persona.createAllocationPriorityForm;
                    var allocationPriorityEdit = persona.allocationPriorityEdit;

                    assertThat(createAllocationPriorityForm != null || allocationPriorityEdit != null).isTrue();

                    if(createAllocationPriorityForm != null) {
                        createAllocationPriorityForm.setSortOrder(sortOrder);
                    } else {
                        allocationPriorityEdit.setSortOrder(sortOrder);
                    }
                });

        And("^the user sets the allocation priority's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;
                    var createAllocationPriorityForm = persona.createAllocationPriorityForm;
                    var allocationPriorityEdit = persona.allocationPriorityEdit;

                    assertThat(createAllocationPriorityForm != null || allocationPriorityEdit != null).isTrue();

                    if(createAllocationPriorityForm != null) {
                        createAllocationPriorityForm.setDescription(description);
                    } else {
                        allocationPriorityEdit.setDescription(description);
                    }
                });

    }

}
