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

package com.echothree.ui.web.main.action.configuration.country;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/Country/Add",
    mappingClass = SecureActionMapping.class,
    name = "CountryAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/Country/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/country/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupDefaults(AddActionForm actionForm)
            throws NamingException {
        actionForm.setSortOrder("1");
    }
    
    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = GeoUtil.getHome().getCreateCountryForm();

        commandForm.setCountryName(actionForm.getCountryName());
        commandForm.setIso3Number(actionForm.getIso3Number());
        commandForm.setIso3Letter(actionForm.getIso3Letter());
        commandForm.setIso2Letter(actionForm.getIso2Letter());
        commandForm.setTelephoneCode(actionForm.getTelephoneCode());
        commandForm.setAreaCodePattern(actionForm.getAreaCodePattern());
        commandForm.setAreaCodeRequired(actionForm.getAreaCodeRequired().toString());
        commandForm.setAreaCodeExample(actionForm.getAreaCodeExample());
        commandForm.setTelephoneNumberPattern(actionForm.getTelephoneNumberPattern());
        commandForm.setTelephoneNumberExample(actionForm.getTelephoneNumberExample());
        commandForm.setPostalAddressFormatName(actionForm.getPostalAddressFormatChoice());
        commandForm.setCityRequired(actionForm.getCityRequired().toString());
        commandForm.setCityGeoCodeRequired(actionForm.getCityGeoCodeRequired().toString());
        commandForm.setStateRequired(actionForm.getStateRequired().toString());
        commandForm.setStateGeoCodeRequired(actionForm.getStateGeoCodeRequired().toString());
        commandForm.setPostalCodePattern(actionForm.getPostalCodePattern());
        commandForm.setPostalCodeRequired(actionForm.getPostalCodeRequired().toString());
        commandForm.setPostalCodeGeoCodeRequired(actionForm.getPostalCodeGeoCodeRequired().toString());
        commandForm.setPostalCodeLength(actionForm.getPostalCodeLength());
        commandForm.setPostalCodeGeoCodeLength(actionForm.getPostalCodeGeoCodeLength());
        commandForm.setPostalCodeExample(actionForm.getPostalCodeExample());
        commandForm.setIsDefault(actionForm.getIsDefault().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());
        commandForm.setDescription(actionForm.getDescription());

        return GeoUtil.getHome().createCountry(getUserVisitPK(request), commandForm);
    }
    
}
