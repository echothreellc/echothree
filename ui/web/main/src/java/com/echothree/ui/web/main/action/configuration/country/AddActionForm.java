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

package com.echothree.ui.web.main.action.configuration.country;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.GetPostalAddressFormatChoicesResult;
import com.echothree.model.control.contact.common.choice.PostalAddressFormatChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name = "CountryAdd")
public class AddActionForm
        extends BaseActionForm {

    private PostalAddressFormatChoicesBean postalAddressFormatChoices;

    private String countryName;
    private String iso3Number;
    private String iso3Letter;
    private String iso2Letter;
    private String telephoneCode;
    private String areaCodePattern;
    private Boolean areaCodeRequired;
    private String areaCodeExample;
    private String telephoneNumberPattern;
    private String telephoneNumberExample;
    private String postalAddressFormatChoice;
    private Boolean cityRequired;
    private Boolean cityGeoCodeRequired;
    private Boolean stateRequired;
    private Boolean stateGeoCodeRequired;
    private String postalCodePattern;
    private Boolean postalCodeRequired;
    private Boolean postalCodeGeoCodeRequired;
    private String postalCodeLength;
    private String postalCodeGeoCodeLength;
    private String postalCodeExample;
    private Boolean isDefault;
    private String sortOrder;
    private String description;

    public void setupPostalAddressFormatChoices()
            throws NamingException {
        if(postalAddressFormatChoices == null) {
            var form = ContactUtil.getHome().getGetPostalAddressFormatChoicesForm();

            form.setDefaultPostalAddressFormatChoice(postalAddressFormatChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ContactUtil.getHome().getPostalAddressFormatChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPostalAddressFormatChoicesResult)executionResult.getResult();
            postalAddressFormatChoices = result.getPostalAddressFormatChoices();

            if(postalAddressFormatChoice == null) {
                postalAddressFormatChoice = postalAddressFormatChoices.getDefaultValue();
            }
        }
    }

    /**
     * Returns the countryName.
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the countryName.
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Returns the iso3Number.
     * @return the iso3Number
     */
    public String getIso3Number() {
        return iso3Number;
    }

    /**
     * Sets the iso3Number.
     * @param iso3Number the iso3Number to set
     */
    public void setIso3Number(String iso3Number) {
        this.iso3Number = iso3Number;
    }

    /**
     * Returns the iso3Letter.
     * @return the iso3Letter
     */
    public String getIso3Letter() {
        return iso3Letter;
    }

    /**
     * Sets the iso3Letter.
     * @param iso3Letter the iso3Letter to set
     */
    public void setIso3Letter(String iso3Letter) {
        this.iso3Letter = iso3Letter;
    }

    /**
     * Returns the iso2Letter.
     * @return the iso2Letter
     */
    public String getIso2Letter() {
        return iso2Letter;
    }

    /**
     * Sets the iso2Letter.
     * @param iso2Letter the iso2Letter to set
     */
    public void setIso2Letter(String iso2Letter) {
        this.iso2Letter = iso2Letter;
    }

    /**
     * Returns the telephoneCode.
     * @return the telephoneCode
     */
    public String getTelephoneCode() {
        return telephoneCode;
    }

    /**
     * Sets the telephoneCode.
     * @param telephoneCode the telephoneCode to set
     */
    public void setTelephoneCode(String telephoneCode) {
        this.telephoneCode = telephoneCode;
    }

    /**
     * Returns the areaCodePattern.
     * @return the areaCodePattern
     */
    public String getAreaCodePattern() {
        return areaCodePattern;
    }

    /**
     * Sets the areaCodePattern.
     * @param areaCodePattern the areaCodePattern to set
     */
    public void setAreaCodePattern(String areaCodePattern) {
        this.areaCodePattern = areaCodePattern;
    }

    /**
     * Returns the areaCodeRequired.
     * @return the areaCodeRequired
     */
    public Boolean getAreaCodeRequired() {
        return areaCodeRequired;
    }

    /**
     * Sets the areaCodeRequired.
     * @param areaCodeRequired the areaCodeRequired to set
     */
    public void setAreaCodeRequired(Boolean areaCodeRequired) {
        this.areaCodeRequired = areaCodeRequired;
    }

    /**
     * Returns the areaCodeExample.
     * @return the areaCodeExample
     */
    public String getAreaCodeExample() {
        return areaCodeExample;
    }

    /**
     * Sets the areaCodeExample.
     * @param areaCodeExample the areaCodeExample to set
     */
    public void setAreaCodeExample(String areaCodeExample) {
        this.areaCodeExample = areaCodeExample;
    }

    /**
     * Returns the telephoneNumberPattern.
     * @return the telephoneNumberPattern
     */
    public String getTelephoneNumberPattern() {
        return telephoneNumberPattern;
    }

    /**
     * Sets the telephoneNumberPattern.
     * @param telephoneNumberPattern the telephoneNumberPattern to set
     */
    public void setTelephoneNumberPattern(String telephoneNumberPattern) {
        this.telephoneNumberPattern = telephoneNumberPattern;
    }

    /**
     * Returns the telephoneNumberExample.
     * @return the telephoneNumberExample
     */
    public String getTelephoneNumberExample() {
        return telephoneNumberExample;
    }

    /**
     * Sets the telephoneNumberExample.
     * @param telephoneNumberExample the telephoneNumberExample to set
     */
    public void setTelephoneNumberExample(String telephoneNumberExample) {
        this.telephoneNumberExample = telephoneNumberExample;
    }

    public String getPostalAddressFormatChoice() {
        return postalAddressFormatChoice;
    }

    public void setPostalAddressFormatChoice(String postalAddressFormatChoice) {
        this.postalAddressFormatChoice = postalAddressFormatChoice;
    }

    public List<LabelValueBean> getPostalAddressFormatChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupPostalAddressFormatChoices();
        if(postalAddressFormatChoices != null) {
            choices = convertChoices(postalAddressFormatChoices);
        }

        return choices;
    }

    /**
     * Returns the cityRequired.
     * @return the cityRequired
     */
    public Boolean getCityRequired() {
        return cityRequired;
    }

    /**
     * Sets the cityRequired.
     * @param cityRequired the cityRequired to set
     */
    public void setCityRequired(Boolean cityRequired) {
        this.cityRequired = cityRequired;
    }

    /**
     * Returns the cityGeoCodeRequired.
     * @return the cityGeoCodeRequired
     */
    public Boolean getCityGeoCodeRequired() {
        return cityGeoCodeRequired;
    }

    /**
     * Sets the cityGeoCodeRequired.
     * @param cityGeoCodeRequired the cityGeoCodeRequired to set
     */
    public void setCityGeoCodeRequired(Boolean cityGeoCodeRequired) {
        this.cityGeoCodeRequired = cityGeoCodeRequired;
    }

    /**
     * Returns the stateRequired.
     * @return the stateRequired
     */
    public Boolean getStateRequired() {
        return stateRequired;
    }

    /**
     * Sets the stateRequired.
     * @param stateRequired the stateRequired to set
     */
    public void setStateRequired(Boolean stateRequired) {
        this.stateRequired = stateRequired;
    }

    /**
     * Returns the stateGeoCodeRequired.
     * @return the stateGeoCodeRequired
     */
    public Boolean getStateGeoCodeRequired() {
        return stateGeoCodeRequired;
    }

    /**
     * Sets the stateGeoCodeRequired.
     * @param stateGeoCodeRequired the stateGeoCodeRequired to set
     */
    public void setStateGeoCodeRequired(Boolean stateGeoCodeRequired) {
        this.stateGeoCodeRequired = stateGeoCodeRequired;
    }

    /**
     * Returns the postalCodePattern.
     * @return the postalCodePattern
     */
    public String getPostalCodePattern() {
        return postalCodePattern;
    }

    /**
     * Sets the postalCodePattern.
     * @param postalCodePattern the postalCodePattern to set
     */
    public void setPostalCodePattern(String postalCodePattern) {
        this.postalCodePattern = postalCodePattern;
    }

    /**
     * Returns the postalCodeRequired.
     * @return the postalCodeRequired
     */
    public Boolean getPostalCodeRequired() {
        return postalCodeRequired;
    }

    /**
     * Sets the postalCodeRequired.
     * @param postalCodeRequired the postalCodeRequired to set
     */
    public void setPostalCodeRequired(Boolean postalCodeRequired) {
        this.postalCodeRequired = postalCodeRequired;
    }

    /**
     * Returns the postalCodeGeoCodeRequired.
     * @return the postalCodeGeoCodeRequired
     */
    public Boolean getPostalCodeGeoCodeRequired() {
        return postalCodeGeoCodeRequired;
    }

    /**
     * Sets the postalCodeGeoCodeRequired.
     * @param postalCodeGeoCodeRequired the postalCodeGeoCodeRequired to set
     */
    public void setPostalCodeGeoCodeRequired(Boolean postalCodeGeoCodeRequired) {
        this.postalCodeGeoCodeRequired = postalCodeGeoCodeRequired;
    }

    /**
     * Returns the postalCodeLength.
     * @return the postalCodeLength
     */
    public String getPostalCodeLength() {
        return postalCodeLength;
    }

    /**
     * Sets the postalCodeLength.
     * @param postalCodeLength the postalCodeLength to set
     */
    public void setPostalCodeLength(String postalCodeLength) {
        this.postalCodeLength = postalCodeLength;
    }

    /**
     * Returns the postalCodeGeoCodeLength.
     * @return the postalCodeGeoCodeLength
     */
    public String getPostalCodeGeoCodeLength() {
        return postalCodeGeoCodeLength;
    }

    /**
     * Sets the postalCodeGeoCodeLength.
     * @param postalCodeGeoCodeLength the postalCodeGeoCodeLength to set
     */
    public void setPostalCodeGeoCodeLength(String postalCodeGeoCodeLength) {
        this.postalCodeGeoCodeLength = postalCodeGeoCodeLength;
    }

    /**
     * Returns the postalCodeExample.
     * @return the postalCodeExample
     */
    public String getPostalCodeExample() {
        return postalCodeExample;
    }

    /**
     * Sets the postalCodeExample.
     * @param postalCodeExample the postalCodeExample to set
     */
    public void setPostalCodeExample(String postalCodeExample) {
        this.postalCodeExample = postalCodeExample;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        this.areaCodeRequired = false;
        this.cityRequired = false;
        this.cityGeoCodeRequired = false;
        this.stateRequired = false;
        this.stateGeoCodeRequired = false;
        this.postalCodeRequired = false;
        this.postalCodeGeoCodeRequired = false;
        this.isDefault = false;
    }

}
