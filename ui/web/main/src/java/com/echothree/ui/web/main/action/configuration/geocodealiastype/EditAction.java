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
import com.echothree.control.user.geo.common.edit.GeoCodeAliasTypeEdit;
import com.echothree.control.user.geo.common.form.EditGeoCodeAliasTypeForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeAliasTypeResult;
import com.echothree.control.user.geo.common.spec.GeoCodeAliasTypeSpec;
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
    path = "/Configuration/GeoCodeAliasType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeAliasTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCodeAliasType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocodealiastype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, GeoCodeAliasTypeSpec, GeoCodeAliasTypeEdit, EditGeoCodeAliasTypeForm, EditGeoCodeAliasTypeResult> {
    
    @Override
    protected GeoCodeAliasTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = GeoUtil.getHome().getGeoCodeAliasTypeSpec();

        spec.setGeoCodeTypeName(findParameter(request, ParameterConstants.GEO_CODE_TYPE_NAME, actionForm.getGeoCodeTypeName()));
        spec.setGeoCodeAliasTypeName(findParameter(request, ParameterConstants.ORIGINAL_GEO_CODE_ALIAS_TYPE_NAME, actionForm.getOriginalGeoCodeAliasTypeName()));
        
        return spec;
    }
    
    @Override
    protected GeoCodeAliasTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = GeoUtil.getHome().getGeoCodeAliasTypeEdit();

        edit.setGeoCodeAliasTypeName(actionForm.getGeoCodeAliasTypeName());
        edit.setValidationPattern(actionForm.getValidationPattern());
        edit.setIsRequired(actionForm.getIsRequired().toString());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditGeoCodeAliasTypeForm getForm()
            throws NamingException {
        return GeoUtil.getHome().getEditGeoCodeAliasTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditGeoCodeAliasTypeResult result, GeoCodeAliasTypeSpec spec, GeoCodeAliasTypeEdit edit) {
        actionForm.setGeoCodeTypeName(spec.getGeoCodeTypeName());
        actionForm.setGeoCodeAliasTypeName(spec.getGeoCodeAliasTypeName());
        actionForm.setOriginalGeoCodeAliasTypeName(spec.getGeoCodeAliasTypeName());
        actionForm.setValidationPattern(edit.getValidationPattern());
        actionForm.setIsRequired(Boolean.valueOf(edit.getIsRequired()));
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditGeoCodeAliasTypeForm commandForm)
            throws Exception {
        var commandResult = GeoUtil.getHome().editGeoCodeAliasType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditGeoCodeAliasTypeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.GEO_CODE_ALIAS_TYPE, result.getGeoCodeAliasType());
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.GEO_CODE_TYPE_NAME, actionForm.getGeoCodeTypeName());
    }
    
}