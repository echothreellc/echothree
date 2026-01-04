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
import com.echothree.control.user.geo.common.edit.GeoCodeScopeEdit;
import com.echothree.control.user.geo.common.form.EditGeoCodeScopeForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeScopeResult;
import com.echothree.control.user.geo.common.spec.GeoCodeScopeSpec;
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
    path = "/Configuration/GeoCodeScope/Edit",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeScopeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCodeScope/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocodescope/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, GeoCodeScopeSpec, GeoCodeScopeEdit, EditGeoCodeScopeForm, EditGeoCodeScopeResult> {
    
    @Override
    protected GeoCodeScopeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = GeoUtil.getHome().getGeoCodeScopeSpec();
        
        spec.setGeoCodeScopeName(findParameter(request, ParameterConstants.ORIGINAL_GEO_CODE_SCOPE_NAME, actionForm.getOriginalGeoCodeScopeName()));
        
        return spec;
    }
    
    @Override
    protected GeoCodeScopeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = GeoUtil.getHome().getGeoCodeScopeEdit();

        edit.setGeoCodeScopeName(actionForm.getGeoCodeScopeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditGeoCodeScopeForm getForm()
            throws NamingException {
        return GeoUtil.getHome().getEditGeoCodeScopeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditGeoCodeScopeResult result, GeoCodeScopeSpec spec, GeoCodeScopeEdit edit) {
        actionForm.setOriginalGeoCodeScopeName(spec.getGeoCodeScopeName());
        actionForm.setGeoCodeScopeName(edit.getGeoCodeScopeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditGeoCodeScopeForm commandForm)
            throws Exception {
        return GeoUtil.getHome().editGeoCodeScope(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditGeoCodeScopeResult result) {
        request.setAttribute(AttributeConstants.GEO_CODE_SCOPE, result.getGeoCodeScope());
    }

}
