// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.control.user.contact.remote.form.GetPostalAddressFormatChoicesForm;
import com.echothree.control.user.contact.remote.result.GetPostalAddressFormatChoicesResult;
import com.echothree.model.control.contact.remote.choice.PostalAddressFormatChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
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

    public void setupPostalAddressFormatChoices() {
        if(postalAddressFormatChoices == null) {
            try {
                GetPostalAddressFormatChoicesForm form = ContactUtil.getHome().getGetPostalAddressFormatChoicesForm();

                form.setDefaultPostalAddressFormatChoice(postalAddressFormatChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());

                CommandResult commandResult = ContactUtil.getHome().getPostalAddressFormatChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetPostalAddressFormatChoicesResult result = (GetPostalAddressFormatChoicesResult)executionResult.getResult();
                postalAddressFormatChoices = result.getPostalAddressFormatChoices();

                if(postalAddressFormatChoice == null) {
                    postalAddressFormatChoice = postalAddressFormatChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, postalAddressFormatChoices remains null, no default
            }
        }
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the iso3Number
     */
    public String getIso3Number() {
        return iso3Number;
    }

    /**
     * @param iso3Number the iso3Number to set
     */
    public void setIso3Number(String iso3Number) {
        this.iso3Number = iso3Number;
    }

    /**
     * @return the iso3Letter
     */
    public String getIso3Letter() {
        return iso3Letter;
    }

    /**
     * @param iso3Letter the iso3Letter to set
     */
    public void setIso3Letter(String iso3Letter) {
        this.iso3Letter = iso3Letter;
    }

    /**
     * @return the iso2Letter
     */
    public String getIso2Letter() {
        return iso2Letter;
    }

    /**
     * @param iso2Letter the iso2Letter to set
     */
    public void setIso2Letter(String iso2Letter) {
        this.iso2Letter = iso2Letter;
    }

    /**
     * @return the telephoneCode
     */
    public String getTelephoneCode() {
        return telephoneCode;
    }

    /**
     * @param telephoneCode the telephoneCode to set
     */
    public void setTelephoneCode(String telephoneCode) {
        this.telephoneCode = telephoneCode;
    }

    /**
     * @return the areaCodePattern
     */
    public String getAreaCodePattern() {
        return areaCodePattern;
    }

    /**
     * @param areaCodePattern the areaCodePattern to set
     */
    public void setAreaCodePattern(String areaCodePattern) {
        this.areaCodePattern = areaCodePattern;
    }

    /**
     * @return the areaCodeRequired
     */
    public Boolean getAreaCodeRequired() {
        return areaCodeRequired;
    }

    /**
     * @param areaCodeRequired the areaCodeRequired to set
     */
    public void setAreaCodeRequired(Boolean areaCodeRequired) {
        this.areaCodeRequired = areaCodeRequired;
    }

    /**
     * @return the areaCodeExample
     */
    public String getAreaCodeExample() {
        return areaCodeExample;
    }

    /**
     * @param areaCodeExample the areaCodeExample to set
     */
    public void setAreaCodeExample(String areaCodeExample) {
        this.areaCodeExample = areaCodeExample;
    }

    /**
     * @return the telephoneNumberPattern
     */
    public String getTelephoneNumberPattern() {
        return telephoneNumberPattern;
    }

    /**
     * @param telephoneNumberPattern the telephoneNumberPattern to set
     */
    public void setTelephoneNumberPattern(String telephoneNumberPattern) {
        this.telephoneNumberPattern = telephoneNumberPattern;
    }

    /**
     * @return the telephoneNumberExample
     */
    public String getTelephoneNumberExample() {
        return telephoneNumberExample;
    }

    /**
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

    public List<LabelValueBean> getPostalAddressFormatChoices() {
        List<LabelValueBean> choices = null;

        setupPostalAddressFormatChoices();
        if(postalAddressFormatChoices != null) {
            choices = convertChoices(postalAddressFormatChoices);
        }

        return choices;
    }

    /**
     * @return the cityRequired
     */
    public Boolean getCityRequired() {
        return cityRequired;
    }

    /**
     * @param cityRequired the cityRequired to set
     */
    public void setCityRequired(Boolean cityRequired) {
        this.cityRequired = cityRequired;
    }

    /**
     * @return the cityGeoCodeRequired
     */
    public Boolean getCityGeoCodeRequired() {
        return cityGeoCodeRequired;
    }

    /**
     * @param cityGeoCodeRequired the cityGeoCodeRequired to set
     */
    public void setCityGeoCodeRequired(Boolean cityGeoCodeRequired) {
        this.cityGeoCodeRequired = cityGeoCodeRequired;
    }

    /**
     * @return the stateRequired
     */
    public Boolean getStateRequired() {
        return stateRequired;
    }

    /**
     * @param stateRequired the stateRequired to set
     */
    public void setStateRequired(Boolean stateRequired) {
        this.stateRequired = stateRequired;
    }

    /**
     * @return the stateGeoCodeRequired
     */
    public Boolean getStateGeoCodeRequired() {
        return stateGeoCodeRequired;
    }

    /**
     * @param stateGeoCodeRequired the stateGeoCodeRequired to set
     */
    public void setStateGeoCodeRequired(Boolean stateGeoCodeRequired) {
        this.stateGeoCodeRequired = stateGeoCodeRequired;
    }

    /**
     * @return the postalCodePattern
     */
    public String getPostalCodePattern() {
        return postalCodePattern;
    }

    /**
     * @param postalCodePattern the postalCodePattern to set
     */
    public void setPostalCodePattern(String postalCodePattern) {
        this.postalCodePattern = postalCodePattern;
    }

    /**
     * @return the postalCodeRequired
     */
    public Boolean getPostalCodeRequired() {
        return postalCodeRequired;
    }

    /**
     * @param postalCodeRequired the postalCodeRequired to set
     */
    public void setPostalCodeRequired(Boolean postalCodeRequired) {
        this.postalCodeRequired = postalCodeRequired;
    }

    /**
     * @return the postalCodeGeoCodeRequired
     */
    public Boolean getPostalCodeGeoCodeRequired() {
        return postalCodeGeoCodeRequired;
    }

    /**
     * @param postalCodeGeoCodeRequired the postalCodeGeoCodeRequired to set
     */
    public void setPostalCodeGeoCodeRequired(Boolean postalCodeGeoCodeRequired) {
        this.postalCodeGeoCodeRequired = postalCodeGeoCodeRequired;
    }

    /**
     * @return the postalCodeLength
     */
    public String getPostalCodeLength() {
        return postalCodeLength;
    }

    /**
     * @param postalCodeLength the postalCodeLength to set
     */
    public void setPostalCodeLength(String postalCodeLength) {
        this.postalCodeLength = postalCodeLength;
    }

    /**
     * @return the postalCodeGeoCodeLength
     */
    public String getPostalCodeGeoCodeLength() {
        return postalCodeGeoCodeLength;
    }

    /**
     * @param postalCodeGeoCodeLength the postalCodeGeoCodeLength to set
     */
    public void setPostalCodeGeoCodeLength(String postalCodeGeoCodeLength) {
        this.postalCodeGeoCodeLength = postalCodeGeoCodeLength;
    }

    /**
     * @return the postalCodeExample
     */
    public String getPostalCodeExample() {
        return postalCodeExample;
    }

    /**
     * @param postalCodeExample the postalCodeExample to set
     */
    public void setPostalCodeExample(String postalCodeExample) {
        this.postalCodeExample = postalCodeExample;
    }

    /**
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        this.areaCodeRequired = Boolean.FALSE;
        this.cityRequired = Boolean.FALSE;
        this.cityGeoCodeRequired = Boolean.FALSE;
        this.stateRequired = Boolean.FALSE;
        this.stateGeoCodeRequired = Boolean.FALSE;
        this.postalCodeRequired = Boolean.FALSE;
        this.postalCodeGeoCodeRequired = Boolean.FALSE;
        this.isDefault = Boolean.FALSE;
    }

}
