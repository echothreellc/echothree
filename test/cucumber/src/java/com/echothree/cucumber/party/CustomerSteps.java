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

package com.echothree.cucumber.party;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.CreateCustomerResult;
import com.echothree.control.user.party.common.result.CreateCustomerWithLoginResult;
import com.echothree.cucumber.AnonymousPersonas;
import com.echothree.cucumber.BasePersona;
import com.echothree.cucumber.EmployeePersonas;
import com.echothree.cucumber.LastCommandResult;
import io.cucumber.java8.En;
import javax.naming.NamingException;

public class CustomerSteps implements En {

    public CustomerSteps() {
        When("^the anonymous user ([^\"]*) adds a new customer with the first name \"([^\"]*)\" and the last name \"([^\"]*)\" " +
                        "and the email address \"([^\"]*)\" and (does|does not) allow solicitations to it and the username \"([^\"]*)\" " +
                        "and the password \"([^\"]*)\" and the recovery question ([^\"]*) " +
                        "and the answer \"([^\"]*)\"$",
                (String persona, String firstName, String lastName, String emailAddress, String allowSolicitation,
                        String username, String password, String recoveryQuestionName, String answer) -> {
                    var anonymousPersona = AnonymousPersonas.getAnonymousPersona(persona);

                    createCustomerWithLogin(anonymousPersona, null, null,
                            null, null, null, null,
                            null, null, firstName, null, lastName,
                            null, null, null, null,
                            null, null, emailAddress,
                            allowSolicitation, username, password, password, recoveryQuestionName,
                            answer, null, null);
                });

        When("^the employee ([^\"]*) sets the status of the last customer added to ([^\"]*)$",
                (String persona, String customerStatusChoice) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    setCustomerStatus(employeePersona, employeePersona.lastCustomerName, customerStatusChoice);
                });
    }

    private void createCustomerWithLogin(BasePersona persona, String customerTypeName, String cancellationPolicyName,
            String returnPolicyName, String arGlAccountName, String initialOfferName, String initialUseName,
            String initialSourceName, String personalTitleId, String firstName, String middleName, String lastName,
            String nameSuffixId, String name, String preferredLanguageIsoName, String preferredCurrencyIsoName,
            String preferredJavaTimeZoneName, String preferredDateTimeFormatName, String emailAddress,
            String allowSolicitation, String username, String password1, String password2, String recoveryQuestionName,
            String answer, String customerStatusChoice, String customerCreditStatusChoice)
            throws NamingException {
        var partyService = PartyUtil.getHome();
        var createCustomerWithLoginForm = partyService.getCreateCustomerWithLoginForm();

        createCustomerWithLoginForm.setCustomerTypeName(customerTypeName);
        createCustomerWithLoginForm.setCancellationPolicyName(cancellationPolicyName);
        createCustomerWithLoginForm.setReturnPolicyName(returnPolicyName);
        createCustomerWithLoginForm.setArGlAccountName(arGlAccountName);
        createCustomerWithLoginForm.setInitialOfferName(initialOfferName);
        createCustomerWithLoginForm.setInitialUseName(initialUseName);
        createCustomerWithLoginForm.setInitialSourceName(initialSourceName);
        createCustomerWithLoginForm.setPersonalTitleId(personalTitleId);
        createCustomerWithLoginForm.setFirstName(firstName);
        createCustomerWithLoginForm.setMiddleName(middleName);
        createCustomerWithLoginForm.setLastName(lastName);
        createCustomerWithLoginForm.setNameSuffixId(nameSuffixId);
        createCustomerWithLoginForm.setName(name);
        createCustomerWithLoginForm.setPreferredLanguageIsoName(preferredLanguageIsoName);
        createCustomerWithLoginForm.setPreferredCurrencyIsoName(preferredCurrencyIsoName);
        createCustomerWithLoginForm.setPreferredJavaTimeZoneName(preferredJavaTimeZoneName);
        createCustomerWithLoginForm.setPreferredDateTimeFormatName(preferredDateTimeFormatName);
        createCustomerWithLoginForm.setEmailAddress(emailAddress);
        createCustomerWithLoginForm.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
        createCustomerWithLoginForm.setUsername(username);
        createCustomerWithLoginForm.setPassword1(password1);
        createCustomerWithLoginForm.setPassword2(password2);
        createCustomerWithLoginForm.setRecoveryQuestionName(recoveryQuestionName);
        createCustomerWithLoginForm.setAnswer(answer);
        createCustomerWithLoginForm.setCustomerStatusChoice(customerStatusChoice);
        createCustomerWithLoginForm.setCustomerCreditStatusChoice(customerCreditStatusChoice);

        var commandResult = partyService.createCustomerWithLogin(persona.userVisitPK, createCustomerWithLoginForm);

        LastCommandResult.commandResult = commandResult;
        var result = (CreateCustomerWithLoginResult)commandResult.getExecutionResult().getResult();

        if(persona != null) {
            persona.lastCustomerName = commandResult.getHasErrors() ? null : result.getCustomerName();
            persona.lastPartyName = commandResult.getHasErrors() ? null : result.getPartyName();
            persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
        }
    }

    private void createCustomer(BasePersona persona, String customerTypeName, String cancellationPolicyName,
            String returnPolicyName, String arGlAccountName, String initialOfferName, String initialUseName,
            String initialSourceName, String personalTitleId, String firstName, String middleName, String lastName,
            String nameSuffixId, String name, String preferredLanguageIsoName, String preferredCurrencyIsoName,
            String preferredJavaTimeZoneName, String preferredDateTimeFormatName, String emailAddress,
            String allowSolicitation, String customerStatusChoice, String customerCreditStatusChoice)
            throws NamingException {
        var partyService = PartyUtil.getHome();
        var createCustomerForm = partyService.getCreateCustomerForm();

        createCustomerForm.setCustomerTypeName(customerTypeName);
        createCustomerForm.setCancellationPolicyName(cancellationPolicyName);
        createCustomerForm.setReturnPolicyName(returnPolicyName);
        createCustomerForm.setArGlAccountName(arGlAccountName);
        createCustomerForm.setInitialOfferName(initialOfferName);
        createCustomerForm.setInitialUseName(initialUseName);
        createCustomerForm.setInitialSourceName(initialSourceName);
        createCustomerForm.setPersonalTitleId(personalTitleId);
        createCustomerForm.setFirstName(firstName);
        createCustomerForm.setMiddleName(middleName);
        createCustomerForm.setLastName(lastName);
        createCustomerForm.setNameSuffixId(nameSuffixId);
        createCustomerForm.setName(name);
        createCustomerForm.setPreferredLanguageIsoName(preferredLanguageIsoName);
        createCustomerForm.setPreferredCurrencyIsoName(preferredCurrencyIsoName);
        createCustomerForm.setPreferredJavaTimeZoneName(preferredJavaTimeZoneName);
        createCustomerForm.setPreferredDateTimeFormatName(preferredDateTimeFormatName);
        createCustomerForm.setEmailAddress(emailAddress);
        createCustomerForm.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
        createCustomerForm.setCustomerStatusChoice(customerStatusChoice);
        createCustomerForm.setCustomerCreditStatusChoice(customerCreditStatusChoice);

        var commandResult = partyService.createCustomer(persona.userVisitPK, createCustomerForm);

        LastCommandResult.commandResult = commandResult;
        var result = (CreateCustomerResult)commandResult.getExecutionResult().getResult();

        persona.lastCustomerName = commandResult.getHasErrors() ? null : result.getCustomerName();
        persona.lastPartyName = commandResult.getHasErrors() ? null : result.getPartyName();
        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
    }

    private void setCustomerStatus(BasePersona persona, String customerName, String customerStatusChoice)
            throws NamingException {
        var customerService = CustomerUtil.getHome();
        var setCustomerStatusForm = customerService.getSetCustomerStatusForm();

        setCustomerStatusForm.setCustomerName(customerName);
        setCustomerStatusForm.setCustomerStatusChoice(customerStatusChoice);

        var commandResult = customerService.setCustomerStatus(persona.userVisitPK, setCustomerStatusForm);

        LastCommandResult.commandResult = commandResult;
    }

}
