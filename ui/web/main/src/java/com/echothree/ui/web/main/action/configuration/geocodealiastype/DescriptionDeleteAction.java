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
import com.echothree.control.user.geo.common.result.GetGeoCodeAliasTypeDescriptionResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Configuration/GeoCodeAliasType/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeAliasTypeDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCodeAliasType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocodealiastype/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName(final DescriptionDeleteActionForm actionForm) {
        return EntityTypes.GeoCodeAliasTypeDescription.name();
    }

    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setGeoCodeTypeName(findParameter(request, ParameterConstants.GEO_CODE_TYPE_NAME, actionForm.getGeoCodeTypeName()));
        actionForm.setGeoCodeAliasTypeName(findParameter(request, ParameterConstants.GEO_CODE_ALIAS_TYPE_NAME, actionForm.getGeoCodeAliasTypeName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }

    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = GeoUtil.getHome().getGetGeoCodeAliasTypeDescriptionForm();

        commandForm.setGeoCodeTypeName(actionForm.getGeoCodeTypeName());
        commandForm.setGeoCodeAliasTypeName(actionForm.getGeoCodeAliasTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        var commandResult = GeoUtil.getHome().getGeoCodeAliasTypeDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetGeoCodeAliasTypeDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.GEO_CODE_ALIAS_TYPE_DESCRIPTION, result.getGeoCodeAliasTypeDescription());
        }
    }

    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = GeoUtil.getHome().getDeleteGeoCodeAliasTypeDescriptionForm();

        commandForm.setGeoCodeTypeName(actionForm.getGeoCodeTypeName());
        commandForm.setGeoCodeAliasTypeName(actionForm.getGeoCodeAliasTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return GeoUtil.getHome().deleteGeoCodeAliasTypeDescription(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.GEO_CODE_TYPE_NAME, actionForm.getGeoCodeTypeName());
        parameters.put(ParameterConstants.GEO_CODE_ALIAS_TYPE_NAME, actionForm.getGeoCodeAliasTypeName());
    }

}
