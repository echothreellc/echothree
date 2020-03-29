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

package com.echothree.cucumber.payment;

import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.result.CreatePartyPaymentMethodResult;
import com.echothree.control.user.payment.common.result.EditPartyPaymentMethodResult;
import com.echothree.cucumber.CustomerPersonas;
import com.echothree.cucumber.LastCommandResult;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerPaymentMethod implements En {

    public CustomerPaymentMethod() {
        When("^the customer ([^\"]*) deletes the last payment method added$",
                (String persona) -> {
                    var paymentService = PaymentUtil.getHome();
                    var deletePartyPaymentMethodForm = paymentService.getDeletePartyPaymentMethodForm();
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    deletePartyPaymentMethodForm.setPartyPaymentMethodName(customerPersona.lastPartyPaymentMethodName);

                    LastCommandResult.commandResult = paymentService.deletePartyPaymentMethod(customerPersona.userVisitPK, deletePartyPaymentMethodForm);
                });

        When("^the customer ([^\"]*) begins entering a new payment method$",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNull();

                    customerPersona.createPartyPaymentMethodForm = PaymentUtil.getHome().getCreatePartyPaymentMethodForm();
                });

        When("^the customer ([^\"]*) sets the payment method to ([a-zA-Z0-9-_]*)$",
                (String persona, String paymentMethodName) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNotNull();

                    customerPersona.createPartyPaymentMethodForm.setPaymentMethodName(paymentMethodName);
                });

        When("^the customer ([^\"]*) sets the payment method's description to \"([^\"]*)\"$",
                (String persona, String description) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNotNull();

                    customerPersona.createPartyPaymentMethodForm.setDescription(description);
                });

        When("^the customer ([^\"]*) sets the payment method to (be|not be) deleted when unused$",
                (String persona, String deleteWhenUnused) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNotNull();

                    customerPersona.createPartyPaymentMethodForm.setDeleteWhenUnused(Boolean.valueOf(deleteWhenUnused.equals("be")).toString());
                });

        When("^the customer ([^\"]*) sets the payment method to (be|not be) the default$",
                (String persona, String isDefault) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNotNull();

                    customerPersona.createPartyPaymentMethodForm.setIsDefault(Boolean.valueOf(isDefault.equals("be")).toString());
                });

        When("^the customer ([^\"]*) sets the payment method's sort order to \"([^\"]*)\"$",
                (String persona, String sortOrder) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNotNull();

                    customerPersona.createPartyPaymentMethodForm.setSortOrder(sortOrder);
                });

        When("^the customer ([^\"]*) sets the payment method's number to \"([^\"]*)\"$",
                (String persona, String number) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNotNull();

                    customerPersona.createPartyPaymentMethodForm.setNumber(number);
                });

        When("^the customer ([^\"]*) sets the payment method's security code to \"([^\"]*)\"$",
                (String persona, String securityCode) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNotNull();

                    customerPersona.createPartyPaymentMethodForm.setSecurityCode(securityCode);
                });

        When("^the customer ([^\"]*) sets the payment method's expiration month to \"([^\"]*)\"$",
                (String persona, String expirationMonth) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNotNull();

                    customerPersona.createPartyPaymentMethodForm.setExpirationMonth(expirationMonth);
                });

        When("^the customer ([^\"]*) sets the payment method's expiration year to \"([^\"]*)\"$",
                (String persona, String expirationYear) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNotNull();

                    customerPersona.createPartyPaymentMethodForm.setExpirationYear(expirationYear);
                });

        When("^the customer ([^\"]*) sets the payment method's first name to \"([^\"]*)\"$",
                (String persona, String firstName) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNotNull();

                    customerPersona.createPartyPaymentMethodForm.setFirstName(firstName);
                });

        When("^the customer ([^\"]*) sets the payment method's last name to \"([^\"]*)\"$",
                (String persona, String lastName) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNotNull();

                    customerPersona.createPartyPaymentMethodForm.setLastName(lastName);
                });

        When("^the customer ([^\"]*) sets the payment method's billing contact to the last postal address added$",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNotNull();

                    customerPersona.createPartyPaymentMethodForm.setBillingContactMechanismName(customerPersona.lastPostalAddressContactMechanismName);
                });

        When("^the customer ([^\"]*) adds the new payment method$",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);
                    var createPartyPaymentMethodForm = customerPersona.createPartyPaymentMethodForm;

                    assertThat(createPartyPaymentMethodForm).isNotNull();

                    var paymentUtil = PaymentUtil.getHome();
                    var commandResult = paymentUtil.createPartyPaymentMethod(customerPersona.userVisitPK, createPartyPaymentMethodForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreatePartyPaymentMethodResult)commandResult.getExecutionResult().getResult();

                    customerPersona.lastPartyPaymentMethodName = commandResult.getHasErrors() ? null : result.getPartyPaymentMethodName();
                    customerPersona.createPartyPaymentMethodForm = null;
                });

        When("^the customer ([^\"]*) begins editing the last payment method$ added$",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNull();

                    var paymentUtil = PaymentUtil.getHome();
                    var spec = paymentUtil.getPartyPaymentMethodSpec();
                    spec.setPartyPaymentMethodName(customerPersona.lastPartyPaymentMethodName);

                    var commandForm = paymentUtil.getEditPartyPaymentMethodForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = paymentUtil.editPartyPaymentMethod(customerPersona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditPartyPaymentMethodResult)executionResult.getResult();

                    if(!executionResult.getHasErrors()) {
                        customerPersona.createPartyPaymentMethodForm = paymentUtil.getCreatePartyPaymentMethodForm();
                        customerPersona.createPartyPaymentMethodForm.set(result.getEdit().get());
                    }
                });

        When("^the customer ([^\"]*) finishes editing the payment method$",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona.createPartyPaymentMethodForm).isNotNull();

                    var paymentUtil = PaymentUtil.getHome();
                    var spec = paymentUtil.getPartyPaymentMethodSpec();
                    spec.setPartyPaymentMethodName(customerPersona.lastPartyPaymentMethodName);

                    var edit = paymentUtil.getPartyPaymentMethodEdit();
                    edit.set(customerPersona.createPartyPaymentMethodForm.get());

                    var commandForm = paymentUtil.getEditPartyPaymentMethodForm();
                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = paymentUtil.editPartyPaymentMethod(customerPersona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    customerPersona.createPartyPaymentMethodForm = null;
                });
    }

}
