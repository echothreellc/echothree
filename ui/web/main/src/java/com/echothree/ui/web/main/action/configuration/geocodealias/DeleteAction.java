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

package com.echothree.ui.web.main.action.configuration.geocodealias;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.result.GetGeoCodeAliasResult;
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
    path = "/Configuration/GeoCodeAlias/Delete",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeAliasDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCodeAlias/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocodealias/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.GeoCodeAlias.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setGeoCodeName(findParameter(request, ParameterConstants.GEO_CODE_NAME, actionForm.getGeoCodeName()));
        actionForm.setGeoCodeAliasTypeName(findParameter(request, ParameterConstants.GEO_CODE_ALIAS_TYPE_NAME, actionForm.getGeoCodeAliasTypeName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = GeoUtil.getHome().getGetGeoCodeAliasForm();
        
        commandForm.setGeoCodeName(actionForm.getGeoCodeName());
        commandForm.setGeoCodeAliasTypeName(actionForm.getGeoCodeAliasTypeName());

        var commandResult = GeoUtil.getHome().getGeoCodeAlias(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetGeoCodeAliasResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.GEO_CODE_ALIAS, result.getGeoCodeAlias());
        }
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = GeoUtil.getHome().getDeleteGeoCodeAliasForm();

        commandForm.setGeoCodeName(actionForm.getGeoCodeName());
        commandForm.setGeoCodeAliasTypeName(actionForm.getGeoCodeAliasTypeName());

        return GeoUtil.getHome().deleteGeoCodeAlias(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.GEO_CODE_NAME, actionForm.getGeoCodeName());
    }
    
}
