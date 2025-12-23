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
import com.echothree.control.user.geo.common.edit.CountryEdit;
import com.echothree.control.user.geo.common.form.EditCountryForm;
import com.echothree.control.user.geo.common.result.EditCountryResult;
import com.echothree.control.user.geo.common.spec.GeoCodeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/Country/Edit",
    mappingClass = SecureActionMapping.class,
    name = "CountryEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/Country/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/country/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, GeoCodeSpec, CountryEdit, EditCountryForm, EditCountryResult> {
    
    @Override
    protected GeoCodeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = GeoUtil.getHome().getGeoCodeSpec();
        
        spec.setGeoCodeName(findParameter(request, ParameterConstants.GEO_CODE_NAME, actionForm.getGeoCodeName()));
        
        return spec;
    }
    
    @Override
    protected CountryEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = GeoUtil.getHome().getCountryEdit();

        edit.setCountryName(actionForm.getCountryName());
        edit.setIso3Number(actionForm.getIso3Number());
        edit.setIso3Letter(actionForm.getIso3Letter());
        edit.setIso2Letter(actionForm.getIso2Letter());
        edit.setTelephoneCode(actionForm.getTelephoneCode());
        edit.setAreaCodePattern(actionForm.getAreaCodePattern());
        edit.setAreaCodeRequired(actionForm.getAreaCodeRequired().toString());
        edit.setAreaCodeExample(actionForm.getAreaCodeExample());
        edit.setTelephoneNumberPattern(actionForm.getTelephoneNumberPattern());
        edit.setTelephoneNumberExample(actionForm.getTelephoneNumberExample());
        edit.setPostalAddressFormatName(actionForm.getPostalAddressFormatChoice());
        edit.setCityRequired(actionForm.getCityRequired().toString());
        edit.setCityGeoCodeRequired(actionForm.getCityGeoCodeRequired().toString());
        edit.setStateRequired(actionForm.getStateRequired().toString());
        edit.setStateGeoCodeRequired(actionForm.getStateGeoCodeRequired().toString());
        edit.setPostalCodePattern(actionForm.getPostalCodePattern());
        edit.setPostalCodeRequired(actionForm.getPostalCodeRequired().toString());
        edit.setPostalCodeGeoCodeRequired(actionForm.getPostalCodeGeoCodeRequired().toString());
        edit.setPostalCodeLength(actionForm.getPostalCodeLength());
        edit.setPostalCodeGeoCodeLength(actionForm.getPostalCodeGeoCodeLength());
        edit.setPostalCodeExample(actionForm.getPostalCodeExample());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditCountryForm getForm()
            throws NamingException {
        return GeoUtil.getHome().getEditCountryForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditCountryResult result, GeoCodeSpec spec, CountryEdit edit) {
        actionForm.setGeoCodeName(spec.getGeoCodeName());
        actionForm.setCountryName(edit.getCountryName());
        actionForm.setIso3Number(edit.getIso3Number());
        actionForm.setIso3Letter(edit.getIso3Letter());
        actionForm.setIso2Letter(edit.getIso2Letter());
        actionForm.setTelephoneCode(edit.getTelephoneCode());
        actionForm.setAreaCodePattern(edit.getAreaCodePattern());
        actionForm.setAreaCodeRequired(Boolean.valueOf(edit.getAreaCodeRequired()));
        actionForm.setAreaCodeExample(edit.getAreaCodeExample());
        actionForm.setTelephoneNumberPattern(edit.getTelephoneNumberPattern());
        actionForm.setTelephoneNumberExample(edit.getTelephoneNumberExample());
        actionForm.setPostalAddressFormatChoice(edit.getPostalAddressFormatName());
        actionForm.setCityRequired(Boolean.valueOf(edit.getCityRequired()));
        actionForm.setCityGeoCodeRequired(Boolean.valueOf(edit.getCityGeoCodeRequired()));
        actionForm.setStateRequired(Boolean.valueOf(edit.getStateRequired()));
        actionForm.setStateGeoCodeRequired(Boolean.valueOf(edit.getStateGeoCodeRequired()));
        actionForm.setPostalCodePattern(edit.getPostalCodePattern());
        actionForm.setPostalCodeRequired(Boolean.valueOf(edit.getPostalCodeRequired()));
        actionForm.setPostalCodeGeoCodeRequired(Boolean.valueOf(edit.getPostalCodeGeoCodeRequired()));
        actionForm.setPostalCodeLength(edit.getPostalCodeLength());
        actionForm.setPostalCodeGeoCodeLength(edit.getPostalCodeGeoCodeLength());
        actionForm.setPostalCodeExample(edit.getPostalCodeExample());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditCountryForm commandForm)
            throws Exception {
        return GeoUtil.getHome().editCountry(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditCountryResult result) {
        request.setAttribute(AttributeConstants.COUNTRY, result.getCountry());
    }

}
