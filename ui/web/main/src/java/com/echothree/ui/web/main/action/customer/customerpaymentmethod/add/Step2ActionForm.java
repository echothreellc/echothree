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

package com.echothree.ui.web.main.action.customer.customerpaymentmethod.add;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.GetContactMechanismChoicesResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetNameSuffixChoicesResult;
import com.echothree.control.user.party.common.result.GetPersonalTitleChoicesResult;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.common.choice.ContactMechanismChoicesBean;
import com.echothree.model.control.party.common.choice.NameSuffixChoicesBean;
import com.echothree.model.control.party.common.choice.PersonalTitleChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerPaymentMethodAdd")
public class Step2ActionForm
        extends BaseActionForm {
    
    private PersonalTitleChoicesBean personalTitleChoices;
    private NameSuffixChoicesBean nameSuffixChoices;
    private ContactMechanismChoicesBean billingContactMechanismChoices;
    private ContactMechanismChoicesBean issuerContactMechanismChoices;
    
    private String partyName;
    private String description;
    private String paymentMethodName;
    private Boolean deleteWhenUnused;
    private Boolean isDefault;
    private String sortOrder;

    private String personalTitleChoice;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nameSuffixChoice;
    private String name;
    private String number;
    private String securityCode;
    private String expirationMonth;
    private String expirationYear;
    private String billingContactMechanismChoice;
    private String issuerName;
    private String issuerContactMechanismChoice;
    
    private void setupPersonalTitleChoices()
            throws NamingException {
        if(personalTitleChoices == null) {
            var commandForm = PartyUtil.getHome().getGetPersonalTitleChoicesForm();

            commandForm.setDefaultPersonalTitleChoice(personalTitleChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = PartyUtil.getHome().getPersonalTitleChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPersonalTitleChoicesResult)executionResult.getResult();
            personalTitleChoices = result.getPersonalTitleChoices();

            if(personalTitleChoice == null)
                personalTitleChoice = personalTitleChoices.getDefaultValue();
        }
    }

    private void setupNameSuffixChoices()
            throws NamingException {
        if(nameSuffixChoices == null) {
            var commandForm = PartyUtil.getHome().getGetNameSuffixChoicesForm();

            commandForm.setDefaultNameSuffixChoice(nameSuffixChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = PartyUtil.getHome().getNameSuffixChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetNameSuffixChoicesResult)executionResult.getResult();
            nameSuffixChoices = result.getNameSuffixChoices();

            if(nameSuffixChoice == null)
                nameSuffixChoice = nameSuffixChoices.getDefaultValue();
        }
    }

    private void setupBillingContactMechanismChoices()
            throws NamingException {
        if(billingContactMechanismChoices == null) {
            var commandForm = ContactUtil.getHome().getGetContactMechanismChoicesForm();

            commandForm.setPartyName(partyName);
            commandForm.setContactMechanismTypeName(ContactMechanismTypes.POSTAL_ADDRESS.name());
            commandForm.setDefaultContactMechanismChoice(billingContactMechanismChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = ContactUtil.getHome().getContactMechanismChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContactMechanismChoicesResult)executionResult.getResult();
            billingContactMechanismChoices = result.getContactMechanismChoices();

            if(billingContactMechanismChoice == null) {
                billingContactMechanismChoice = billingContactMechanismChoices.getDefaultValue();
            }
        }
    }

    private void setupIssuerContactMechanismChoices()
            throws NamingException {
        if(issuerContactMechanismChoices == null) {
            var commandForm = ContactUtil.getHome().getGetContactMechanismChoicesForm();

            commandForm.setPartyName(partyName);
            commandForm.setContactMechanismTypeName(ContactMechanismTypes.TELECOM_ADDRESS.name());
            commandForm.setDefaultContactMechanismChoice(issuerContactMechanismChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = ContactUtil.getHome().getContactMechanismChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContactMechanismChoicesResult)executionResult.getResult();
            issuerContactMechanismChoices = result.getContactMechanismChoices();

            if(issuerContactMechanismChoice == null) {
                issuerContactMechanismChoice = issuerContactMechanismChoices.getDefaultValue();
            }
        }
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
    
    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    /**
     * Returns the deleteWhenUnused.
     * @return the deleteWhenUnused
     */
    public Boolean getDeleteWhenUnused() {
        return deleteWhenUnused;
    }

    /**
     * Sets the deleteWhenUnused.
     * @param deleteWhenUnused the deleteWhenUnused to set
     */
    public void setDeleteWhenUnused(Boolean deleteWhenUnused) {
        this.deleteWhenUnused = deleteWhenUnused;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<LabelValueBean> getPersonalTitleChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupPersonalTitleChoices();
        if(personalTitleChoices != null) {
            choices = convertChoices(personalTitleChoices);
        }

        return choices;
    }

    public void setPersonalTitleChoice(String personalTitleChoice) {
        this.personalTitleChoice = personalTitleChoice;
    }

    public String getPersonalTitleChoice()
            throws NamingException {
        setupPersonalTitleChoices();

        return personalTitleChoice;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<LabelValueBean> getNameSuffixChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupNameSuffixChoices();
        if(nameSuffixChoices != null) {
            choices = convertChoices(nameSuffixChoices);
        }

        return choices;
    }

    public void setNameSuffixChoice(String nameSuffixChoice) {
        this.nameSuffixChoice = nameSuffixChoice;
    }

    public String getNameSuffixChoice()
            throws NamingException {
        setupNameSuffixChoices();

        return nameSuffixChoice;
    }

    /**
     * Returns the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the number.
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the number.
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Returns the securityCode.
     * @return the securityCode
     */
    public String getSecurityCode() {
        return securityCode;
    }

    /**
     * Sets the securityCode.
     * @param securityCode the securityCode to set
     */
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    /**
     * Returns the expirationMonth.
     * @return the expirationMonth
     */
    public String getExpirationMonth() {
        return expirationMonth;
    }

    /**
     * Sets the expirationMonth.
     * @param expirationMonth the expirationMonth to set
     */
    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    /**
     * Returns the expirationYear.
     * @return the expirationYear
     */
    public String getExpirationYear() {
        return expirationYear;
    }

    /**
     * Sets the expirationYear.
     * @param expirationYear the expirationYear to set
     */
    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public List<LabelValueBean> getBillingContactMechanismChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupBillingContactMechanismChoices();
        if(billingContactMechanismChoices != null) {
            choices = convertChoices(billingContactMechanismChoices);
        }

        return choices;
    }

    public void setBillingContactMechanismChoice(String billingContactMechanismChoice) {
        this.billingContactMechanismChoice = billingContactMechanismChoice;
    }

    public String getBillingContactMechanismChoice()
            throws NamingException {
        setupBillingContactMechanismChoices();

        return billingContactMechanismChoice;
    }

    /**
     * Returns the issuerName.
     * @return the issuerName
     */
    public String getIssuerName() {
        return issuerName;
    }

    /**
     * Sets the issuerName.
     * @param issuerName the issuerName to set
     */
    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public List<LabelValueBean> getIssuerContactMechanismChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupIssuerContactMechanismChoices();
        if(issuerContactMechanismChoices != null) {
            choices = convertChoices(issuerContactMechanismChoices);
        }

        return choices;
    }

    public void setIssuerContactMechanismChoice(String issuerContactMechanismChoice) {
        this.issuerContactMechanismChoice = issuerContactMechanismChoice;
    }

    public String getIssuerContactMechanismChoice()
            throws NamingException {
        setupIssuerContactMechanismChoices();

        return issuerContactMechanismChoice;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        setDeleteWhenUnused(false);
        setIsDefault(false);
    }

}
