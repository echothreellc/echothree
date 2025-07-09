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

package com.echothree.ui.web.main.action.humanresources.employeecontactmechanism.contactmechanismadd;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetNameSuffixChoicesResult;
import com.echothree.control.user.party.common.result.GetPersonalTitleChoicesResult;
import com.echothree.model.control.party.common.choice.NameSuffixChoicesBean;
import com.echothree.model.control.party.common.choice.PersonalTitleChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EmployeeContactPostalAddressAdd")
public class ContactPostalAddressAddActionForm
        extends BaseActionForm {
    
    private PersonalTitleChoicesBean personalTitleChoices;
    private NameSuffixChoicesBean nameSuffixChoices;
    
    private String partyName;
    private Boolean allowSolicitation;
    private String personalTitleChoice;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nameSuffixChoice;
    private String companyName;
    private String attention;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String state;
    private String postalCode;
    private String countryName;
    private Boolean isCommercial;
    private String description;
    
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
    
    public String getPartyName() {
        return partyName;
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public Boolean getAllowSolicitation() {
        return allowSolicitation;
    }
    
    public void setAllowSolicitation(Boolean allowSolicitation) {
        this.allowSolicitation = allowSolicitation;
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
    
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getAddress1() {
        return address1;
    }
    
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    
    public String getAddress2() {
        return address2;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    
    public String getAddress3() {
        return address3;
    }
    
    public void setAddress3(String address3) {
        this.address3 = address3;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCountryName() {
        return countryName;
    }
    
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    public Boolean getIsCommercial() {
        return isCommercial;
    }
    
    public void setIsCommercial(Boolean isCommercial) {
        this.isCommercial = isCommercial;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setAllowSolicitation(false);
        setIsCommercial(false);
    }
    
}
