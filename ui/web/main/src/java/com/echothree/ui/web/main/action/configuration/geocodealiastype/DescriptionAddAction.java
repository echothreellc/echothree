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
import com.echothree.control.user.geo.common.result.GetGeoCodeAliasTypeResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
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
    path = "/Configuration/GeoCodeAliasType/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeAliasTypeDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCodeAliasType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocodealiastype/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {
    
    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setGeoCodeTypeName(findParameter(request, ParameterConstants.GEO_CODE_TYPE_NAME, actionForm.getGeoCodeTypeName()));
        actionForm.setGeoCodeAliasTypeName(findParameter(request, ParameterConstants.GEO_CODE_ALIAS_TYPE_NAME, actionForm.getGeoCodeAliasTypeName()));
    }

    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = GeoUtil.getHome().getGetGeoCodeAliasTypeForm();

        commandForm.setGeoCodeTypeName(actionForm.getGeoCodeTypeName());
        commandForm.setGeoCodeAliasTypeName(actionForm.getGeoCodeAliasTypeName());

        var commandResult = GeoUtil.getHome().getGeoCodeAliasType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetGeoCodeAliasTypeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.GEO_CODE_ALIAS_TYPE, result.getGeoCodeAliasType());
    }

    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = GeoUtil.getHome().getCreateGeoCodeAliasTypeDescriptionForm();

        commandForm.setGeoCodeTypeName(actionForm.getGeoCodeTypeName());
        commandForm.setGeoCodeAliasTypeName(actionForm.getGeoCodeAliasTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return GeoUtil.getHome().createGeoCodeAliasTypeDescription(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.GEO_CODE_TYPE_NAME, actionForm.getGeoCodeTypeName());
        parameters.put(ParameterConstants.GEO_CODE_ALIAS_TYPE_NAME, actionForm.getGeoCodeAliasTypeName());
    }

}