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

package com.echothree.cucumber.vendor;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.EditVendorResult;
import com.echothree.cucumber.BasePersona;
import com.echothree.cucumber.LastCommandResult;
import com.echothree.cucumber.user.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import javax.naming.NamingException;
import static org.assertj.core.api.Assertions.assertThat;

public class VendorSteps implements En {

    public VendorSteps() {
        When("^the user begins entering a new vendor$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createVendorForm).isNull();
                    assertThat(persona.vendorSpec).isNull();

                    persona.createVendorForm = PartyUtil.getHome().getCreateVendorForm();
                });

        When("^the user adds the new vendor$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createVendorForm).isNotNull();

                    var partyUtil = PartyUtil.getHome();
                    var createVendorForm = partyUtil.getCreateVendorForm();

                    createVendorForm.set(persona.createVendorForm.get());

                    var commandResult = partyUtil.createVendor(persona.userVisitPK, createVendorForm);

                    LastCommandResult.commandResult = commandResult;

                    persona.createVendorForm = null;
                });

        When("^the user sets the status of the last vendor added to ([^\"]*)$",
                (String vendorStatusChoice) -> {
                    var persona = CurrentPersona.persona;

                    setVendorStatus(persona, persona.lastVendorName, vendorStatusChoice);
                });
        
        When("^the user begins specifying an vendor to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createVendorForm).isNull();
                    assertThat(persona.entityListItemSpec).isNull();

                    persona.vendorSpec = VendorUtil.getHome().getVendorSpec();
                });

        When("^the user begins editing the vendor",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.vendorSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = VendorUtil.getHome().getEditVendorForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = VendorUtil.getHome().editVendor(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditVendorResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.vendorEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the vendor",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.vendorSpec;
                    var edit = persona.vendorEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = VendorUtil.getHome().getEditVendorForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = VendorUtil.getHome().editVendor(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.vendorSpec = null;
                    persona.vendorEdit = null;
                });
        
        // TODO: everything for forms

        When("^the user sets the vendor's name to ([a-zA-Z0-9-_]*)$",
                (String vendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorSpec = persona.vendorSpec;

                    assertThat(createVendorForm != null || vendorSpec != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setVendorName(vendorName);
                    } else {
                        vendorSpec.setVendorName(vendorName);
                    }
                });

        When("^the user sets the vendor's new name to ([a-zA-Z0-9-_]*)$",
                (String vendorName) -> {
                    var persona = CurrentPersona.persona;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(vendorEdit != null).isTrue();

                    vendorEdit.setVendorName(vendorName);
                });

        When("^the user sets the vendor's type to ([a-zA-Z0-9-_]*)$",
                (String vendorTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setVendorTypeName(vendorTypeName);
                    } else {
                        vendorEdit.setVendorTypeName(vendorTypeName);
                    }
                });

    }

    private void setVendorStatus(BasePersona persona, String vendorName, String vendorStatusChoice)
            throws NamingException {
        var partyUtil = PartyUtil.getHome();
        var setVendorStatusForm = partyUtil.getSetVendorStatusForm();

        setVendorStatusForm.setVendorName(vendorName);
        setVendorStatusForm.setVendorStatusChoice(vendorStatusChoice);

        var commandResult = partyUtil.setVendorStatus(persona.userVisitPK, setVendorStatusForm);

        LastCommandResult.commandResult = commandResult;
    }

}
