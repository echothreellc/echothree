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

package com.echothree.ui.web.main.action.configuration.geocodescope;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.edit.GeoCodeScopeDescriptionEdit;
import com.echothree.control.user.geo.common.form.EditGeoCodeScopeDescriptionForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeScopeDescriptionResult;
import com.echothree.control.user.geo.common.spec.GeoCodeScopeDescriptionSpec;
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
    path = "/Configuration/GeoCodeScope/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeScopeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCodeScope/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocodescope/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, GeoCodeScopeDescriptionSpec, GeoCodeScopeDescriptionEdit, EditGeoCodeScopeDescriptionForm, EditGeoCodeScopeDescriptionResult> {
    
    @Override
    protected GeoCodeScopeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = GeoUtil.getHome().getGeoCodeScopeDescriptionSpec();
        
        spec.setGeoCodeScopeName(findParameter(request, ParameterConstants.GEO_CODE_SCOPE_NAME, actionForm.getGeoCodeScopeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected GeoCodeScopeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = GeoUtil.getHome().getGeoCodeScopeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditGeoCodeScopeDescriptionForm getForm()
            throws NamingException {
        return GeoUtil.getHome().getEditGeoCodeScopeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditGeoCodeScopeDescriptionResult result, GeoCodeScopeDescriptionSpec spec, GeoCodeScopeDescriptionEdit edit) {
        actionForm.setGeoCodeScopeName(spec.getGeoCodeScopeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditGeoCodeScopeDescriptionForm commandForm)
            throws Exception {
        return GeoUtil.getHome().editGeoCodeScopeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.GEO_CODE_SCOPE_NAME, actionForm.getGeoCodeScopeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditGeoCodeScopeDescriptionResult result) {
        request.setAttribute(AttributeConstants.GEO_CODE_SCOPE_DESCRIPTION, result.getGeoCodeScopeDescription());
    }

}
