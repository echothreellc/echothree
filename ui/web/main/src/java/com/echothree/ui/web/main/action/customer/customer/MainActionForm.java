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

package com.echothree.ui.web.main.action.customer.customer;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerTypeChoicesResult;
import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.result.GetCountryChoicesResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetPartyAliasTypeChoicesResult;
import com.echothree.model.control.customer.common.choice.CustomerTypeChoicesBean;
import com.echothree.model.control.geo.common.choice.CountryChoicesBean;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.choice.PartyAliasTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerMain")
public class MainActionForm
        extends BaseActionForm {
    
    private CustomerTypeChoicesBean customerTypeChoices;
    private CountryChoicesBean countryChoices;
    private PartyAliasTypeChoicesBean partyAliasTypeChoices;
    
    private String customerTypeChoice;
    private String customerName;
    private String firstName;
    private Boolean firstNameSoundex;
    private String middleName;
    private Boolean middleNameSoundex;
    private String lastName;
    private Boolean lastNameSoundex;
    private String name;
    private String emailAddress;
    private String countryChoice;
    private String areaCode;
    private String telephoneNumber;
    private String telephoneExtension;
    private String partyAliasTypeChoice;
    private String alias;
    private String createdSince;
    private String modifiedSince;
    
    private void setupCustomerTypeChoices()
            throws NamingException {
        if(customerTypeChoices == null) {
            var form = CustomerUtil.getHome().getGetCustomerTypeChoicesForm();

            form.setDefaultCustomerTypeChoice(customerTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = CustomerUtil.getHome().getCustomerTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getCustomerTypeChoicesResult = (GetCustomerTypeChoicesResult)executionResult.getResult();
            customerTypeChoices = getCustomerTypeChoicesResult.getCustomerTypeChoices();

            if(customerTypeChoice == null) {
                customerTypeChoice = customerTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupCountryChoices()
            throws NamingException {
        if(countryChoices == null) {
            var commandForm = GeoUtil.getHome().getGetCountryChoicesForm();

            commandForm.setDefaultCountryChoice(countryChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = GeoUtil.getHome().getCountryChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCountryChoicesResult)executionResult.getResult();
            countryChoices = result.getCountryChoices();

            if(countryChoice == null) {
                countryChoice = countryChoices.getDefaultValue();
            }
        }
    }

    private void setupPartyAliasTypeChoices()
            throws NamingException {
        if(partyAliasTypeChoices == null) {
            var form = PartyUtil.getHome().getGetPartyAliasTypeChoicesForm();

            form.setPartyTypeName(PartyTypes.CUSTOMER.name());
            form.setDefaultPartyAliasTypeChoice(partyAliasTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = PartyUtil.getHome().getPartyAliasTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getPartyAliasTypeChoicesResult = (GetPartyAliasTypeChoicesResult)executionResult.getResult();
            partyAliasTypeChoices = getPartyAliasTypeChoicesResult.getPartyAliasTypeChoices();

            if(partyAliasTypeChoice == null) {
                partyAliasTypeChoice = partyAliasTypeChoices.getDefaultValue();
            }
        }
    }

    public String getCustomerTypeChoice()
            throws NamingException {
        setupCustomerTypeChoices();
        return customerTypeChoice;
    }
    
    public void setCustomerTypeChoice(String customerTypeChoice) {
        this.customerTypeChoice = customerTypeChoice;
    }
    
    public List<LabelValueBean> getCustomerTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupCustomerTypeChoices();
        if(customerTypeChoices != null) {
            choices = convertChoices(customerTypeChoices);
        }

        return choices;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public Boolean getFirstNameSoundex() {
        return firstNameSoundex;
    }
    
    public void setFirstNameSoundex(Boolean firstNameSoundex) {
        this.firstNameSoundex = firstNameSoundex;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public Boolean getMiddleNameSoundex() {
        return middleNameSoundex;
    }
    
    public void setMiddleNameSoundex(Boolean middleNameSoundex) {
        this.middleNameSoundex = middleNameSoundex;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Boolean getLastNameSoundex() {
        return lastNameSoundex;
    }
    
    public void setLastNameSoundex(Boolean lastNameSoundex) {
        this.lastNameSoundex = lastNameSoundex;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public List<LabelValueBean> getCountryChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupCountryChoices();
        if(countryChoices != null) {
            choices = convertChoices(countryChoices);
        }

        return choices;
    }

    public void setCountryChoice(String countryChoice) {
        this.countryChoice = countryChoice;
    }

    public String getCountryChoice()
            throws NamingException {
        setupCountryChoices();

        return countryChoice;
    }

    public String getAreaCode() {
        return areaCode;
    }
    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    public String getTelephoneNumber() {
        return telephoneNumber;
    }
    
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    
    public String getTelephoneExtension() {
        return telephoneExtension;
    }
    
    public void setTelephoneExtension(String telephoneExtension) {
        this.telephoneExtension = telephoneExtension;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getPartyAliasTypeChoice()
            throws NamingException {
        setupPartyAliasTypeChoices();
        return partyAliasTypeChoice;
    }
    
    public void setPartyAliasTypeChoice(String partyAliasTypeChoice) {
        this.partyAliasTypeChoice = partyAliasTypeChoice;
    }
    
    public List<LabelValueBean> getPartyAliasTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupPartyAliasTypeChoices();
        if(partyAliasTypeChoices != null) {
            choices = convertChoices(partyAliasTypeChoices);
        }

        return choices;
    }
    
    public String getAlias() {
        return alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    public String getCreatedSince() {
        return createdSince;
    }

    public void setCreatedSince(String createdSince) {
        this.createdSince = createdSince;
    }

    public String getModifiedSince() {
        return modifiedSince;
    }

    public void setModifiedSince(String modifiedSince) {
        this.modifiedSince = modifiedSince;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setFirstNameSoundex(false);
        setMiddleNameSoundex(false);
        setLastNameSoundex(false);
    }
    
}
