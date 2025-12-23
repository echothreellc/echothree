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

package com.echothree.ui.web.main.action.configuration.geocodealiastype;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.edit.GeoCodeAliasTypeDescriptionEdit;
import com.echothree.control.user.geo.common.form.EditGeoCodeAliasTypeDescriptionForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeAliasTypeDescriptionResult;
import com.echothree.control.user.geo.common.spec.GeoCodeAliasTypeDescriptionSpec;
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
    path = "/Configuration/GeoCodeAliasType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeAliasTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCodeAliasType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocodealiastype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, GeoCodeAliasTypeDescriptionSpec, GeoCodeAliasTypeDescriptionEdit, EditGeoCodeAliasTypeDescriptionForm, EditGeoCodeAliasTypeDescriptionResult> {

    @Override
    protected GeoCodeAliasTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = GeoUtil.getHome().getGeoCodeAliasTypeDescriptionSpec();

        spec.setGeoCodeTypeName(findParameter(request, ParameterConstants.GEO_CODE_TYPE_NAME, actionForm.getGeoCodeTypeName()));
        spec.setGeoCodeAliasTypeName(findParameter(request, ParameterConstants.GEO_CODE_ALIAS_TYPE_NAME, actionForm.getGeoCodeAliasTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));

        return spec;
    }

    @Override
    protected GeoCodeAliasTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = GeoUtil.getHome().getGeoCodeAliasTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }

    @Override
    protected EditGeoCodeAliasTypeDescriptionForm getForm()
            throws NamingException {
        return GeoUtil.getHome().getEditGeoCodeAliasTypeDescriptionForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditGeoCodeAliasTypeDescriptionResult result, GeoCodeAliasTypeDescriptionSpec spec, GeoCodeAliasTypeDescriptionEdit edit) {
        actionForm.setGeoCodeTypeName(spec.getGeoCodeTypeName());
        actionForm.setGeoCodeAliasTypeName(spec.getGeoCodeAliasTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditGeoCodeAliasTypeDescriptionForm commandForm)
            throws Exception {
        var commandResult = GeoUtil.getHome().editGeoCodeAliasTypeDescription(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditGeoCodeAliasTypeDescriptionResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.GEO_CODE_ALIAS_TYPE_DESCRIPTION, result.getGeoCodeAliasTypeDescription());

        return commandResult;
    }

    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.GEO_CODE_TYPE_NAME, actionForm.getGeoCodeTypeName());
        parameters.put(ParameterConstants.GEO_CODE_ALIAS_TYPE_NAME, actionForm.getGeoCodeAliasTypeName());
    }
    
}