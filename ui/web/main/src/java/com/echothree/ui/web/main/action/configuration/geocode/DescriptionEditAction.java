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

package com.echothree.ui.web.main.action.configuration.geocode;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.edit.GeoCodeDescriptionEdit;
import com.echothree.control.user.geo.common.form.EditGeoCodeDescriptionForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeDescriptionResult;
import com.echothree.control.user.geo.common.spec.GeoCodeDescriptionSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/GeoCode/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCode/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocode/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, GeoCodeDescriptionSpec, GeoCodeDescriptionEdit, EditGeoCodeDescriptionForm, EditGeoCodeDescriptionResult> {
    
    @Override
    protected GeoCodeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = GeoUtil.getHome().getGeoCodeDescriptionSpec();
        
        spec.setGeoCodeName(findParameter(request, ParameterConstants.GEO_CODE_NAME, actionForm.getGeoCodeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected GeoCodeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = GeoUtil.getHome().getGeoCodeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditGeoCodeDescriptionForm getForm()
            throws NamingException {
        return GeoUtil.getHome().getEditGeoCodeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditGeoCodeDescriptionResult result, GeoCodeDescriptionSpec spec, GeoCodeDescriptionEdit edit) {
        actionForm.setGeoCodeName(spec.getGeoCodeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditGeoCodeDescriptionForm commandForm)
            throws Exception {
        return GeoUtil.getHome().editGeoCodeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.GEO_CODE_NAME, actionForm.getGeoCodeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditGeoCodeDescriptionResult result) {
        request.setAttribute(AttributeConstants.GEO_CODE_DESCRIPTION, result.getGeoCodeDescription());
    }

}
