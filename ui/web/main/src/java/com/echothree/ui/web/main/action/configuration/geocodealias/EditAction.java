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
import com.echothree.control.user.geo.common.edit.GeoCodeAliasEdit;
import com.echothree.control.user.geo.common.form.EditGeoCodeAliasForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeAliasResult;
import com.echothree.control.user.geo.common.spec.GeoCodeAliasSpec;
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
    path = "/Configuration/GeoCodeAlias/Edit",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeAliasEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCodeAlias/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocodealias/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, GeoCodeAliasSpec, GeoCodeAliasEdit, EditGeoCodeAliasForm, EditGeoCodeAliasResult> {
    
    @Override
    protected GeoCodeAliasSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = GeoUtil.getHome().getGeoCodeAliasSpec();
        
        spec.setGeoCodeName(findParameter(request, ParameterConstants.GEO_CODE_NAME, actionForm.getGeoCodeName()));
        spec.setGeoCodeAliasTypeName(findParameter(request, ParameterConstants.GEO_CODE_ALIAS_TYPE_NAME, actionForm.getGeoCodeAliasTypeName()));
        
        return spec;
    }
    
    @Override
    protected GeoCodeAliasEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = GeoUtil.getHome().getGeoCodeAliasEdit();

        edit.setAlias(actionForm.getAlias());

        return edit;
    }
    
    @Override
    protected EditGeoCodeAliasForm getForm()
            throws NamingException {
        return GeoUtil.getHome().getEditGeoCodeAliasForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditGeoCodeAliasResult result, GeoCodeAliasSpec spec, GeoCodeAliasEdit edit) {
        actionForm.setGeoCodeName(spec.getGeoCodeName());
        actionForm.setGeoCodeAliasTypeName(spec.getGeoCodeAliasTypeName());
        actionForm.setAlias(edit.getAlias());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditGeoCodeAliasForm commandForm)
            throws Exception {
        return GeoUtil.getHome().editGeoCodeAlias(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.GEO_CODE_NAME, actionForm.getGeoCodeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditGeoCodeAliasResult result) {
        request.setAttribute(AttributeConstants.GEO_CODE_ALIAS, result.getGeoCodeAlias());
    }

}
