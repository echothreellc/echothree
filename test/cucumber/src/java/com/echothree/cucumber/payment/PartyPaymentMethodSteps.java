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

package com.echothree.cucumber.payment;

import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.result.CreatePartyPaymentMethodResult;
import com.echothree.control.user.payment.common.result.EditPartyPaymentMethodResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class PartyPaymentMethodSteps implements En {

    public PartyPaymentMethodSteps() {
        When("^the user deletes the last payment method added$",
                () -> {
                    var paymentService = PaymentUtil.getHome();
                    var deletePartyPaymentMethodForm = paymentService.getDeletePartyPaymentMethodForm();
                    var persona = CurrentPersona.persona;
                    
                    deletePartyPaymentMethodForm.setPartyPaymentMethodName(persona.lastPartyPaymentMethodName);

                    LastCommandResult.commandResult = paymentService.deletePartyPaymentMethod(persona.userVisitPK, deletePartyPaymentMethodForm);
                });

        When("^the user begins entering a new payment method$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNull();

                    persona.createPartyPaymentMethodForm = PaymentUtil.getHome().getCreatePartyPaymentMethodForm();
                });

        When("^the user sets the payment method to ([a-zA-Z0-9-_]*)$",
                (String paymentMethodName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNotNull();

                    persona.createPartyPaymentMethodForm.setPaymentMethodName(paymentMethodName);
                });

        When("^the user sets the payment method's description to \"([^\"]*)\"$",
                (String description) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNotNull();

                    persona.createPartyPaymentMethodForm.setDescription(description);
                });

        When("^the user sets the payment method to (be|not be) deleted when unused$",
                (String deleteWhenUnused) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNotNull();

                    persona.createPartyPaymentMethodForm.setDeleteWhenUnused(Boolean.valueOf(deleteWhenUnused.equals("be")).toString());
                });

        When("^the user sets the payment method to (be|not be) the default$",
                (String isDefault) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNotNull();

                    persona.createPartyPaymentMethodForm.setIsDefault(Boolean.valueOf(isDefault.equals("be")).toString());
                });

        When("^the user sets the payment method's sort order to \"([^\"]*)\"$",
                (String sortOrder) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNotNull();

                    persona.createPartyPaymentMethodForm.setSortOrder(sortOrder);
                });

        When("^the user sets the payment method's number to \"([^\"]*)\"$",
                (String number) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNotNull();

                    persona.createPartyPaymentMethodForm.setNumber(number);
                });

        When("^the user sets the payment method's security code to \"([^\"]*)\"$",
                (String securityCode) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNotNull();

                    persona.createPartyPaymentMethodForm.setSecurityCode(securityCode);
                });

        When("^the user sets the payment method's expiration month to \"([^\"]*)\"$",
                (String expirationMonth) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNotNull();

                    persona.createPartyPaymentMethodForm.setExpirationMonth(expirationMonth);
                });

        When("^the user sets the payment method's expiration year to \"([^\"]*)\"$",
                (String expirationYear) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNotNull();

                    persona.createPartyPaymentMethodForm.setExpirationYear(expirationYear);
                });

        When("^the user sets the payment method's first name to \"([^\"]*)\"$",
                (String firstName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNotNull();

                    persona.createPartyPaymentMethodForm.setFirstName(firstName);
                });

        When("^the user sets the payment method's last name to \"([^\"]*)\"$",
                (String lastName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNotNull();

                    persona.createPartyPaymentMethodForm.setLastName(lastName);
                });

        When("^the user sets the payment method's billing contact to the last postal address added$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNotNull();

                    persona.createPartyPaymentMethodForm.setBillingContactMechanismName(persona.lastPostalAddressContactMechanismName);
                });

        When("^the user adds the new payment method$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createPartyPaymentMethodForm = persona.createPartyPaymentMethodForm;

                    assertThat(createPartyPaymentMethodForm).isNotNull();

                    var paymentUtil = PaymentUtil.getHome();
                    var commandResult = paymentUtil.createPartyPaymentMethod(persona.userVisitPK, createPartyPaymentMethodForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreatePartyPaymentMethodResult)commandResult.getExecutionResult().getResult();

                    persona.lastPartyPaymentMethodName = commandResult.getHasErrors() ? null : result.getPartyPaymentMethodName();
                    persona.createPartyPaymentMethodForm = null;
                });

        When("^the user begins editing the last payment method added$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNull();

                    var paymentUtil = PaymentUtil.getHome();
                    var spec = paymentUtil.getPartyPaymentMethodSpec();
                    spec.setPartyPaymentMethodName(persona.lastPartyPaymentMethodName);

                    var commandForm = paymentUtil.getEditPartyPaymentMethodForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = paymentUtil.editPartyPaymentMethod(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditPartyPaymentMethodResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        persona.createPartyPaymentMethodForm = paymentUtil.getCreatePartyPaymentMethodForm();
                        persona.createPartyPaymentMethodForm.set(result.getEdit().get());
                    }
                });

        When("^the user finishes editing the payment method$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createPartyPaymentMethodForm).isNotNull();

                    var paymentUtil = PaymentUtil.getHome();
                    var spec = paymentUtil.getPartyPaymentMethodSpec();
                    spec.setPartyPaymentMethodName(persona.lastPartyPaymentMethodName);

                    var edit = paymentUtil.getPartyPaymentMethodEdit();
                    edit.set(persona.createPartyPaymentMethodForm.get());

                    var commandForm = paymentUtil.getEditPartyPaymentMethodForm();
                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = paymentUtil.editPartyPaymentMethod(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.createPartyPaymentMethodForm = null;
                });
    }

}
