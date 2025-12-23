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

package com.echothree.ui.web.main.action.configuration.geocodetype;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.edit.GeoCodeTypeEdit;
import com.echothree.control.user.geo.common.form.EditGeoCodeTypeForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeTypeResult;
import com.echothree.control.user.geo.common.spec.GeoCodeTypeSpec;
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
    path = "/Configuration/GeoCodeType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCodeType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocodetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, GeoCodeTypeSpec, GeoCodeTypeEdit, EditGeoCodeTypeForm, EditGeoCodeTypeResult> {
    
    @Override
    protected GeoCodeTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = GeoUtil.getHome().getGeoCodeTypeSpec();
        
        spec.setGeoCodeTypeName(findParameter(request, ParameterConstants.ORIGINAL_GEO_CODE_TYPE_NAME, actionForm.getOriginalGeoCodeTypeName()));
        
        return spec;
    }
    
    @Override
    protected GeoCodeTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = GeoUtil.getHome().getGeoCodeTypeEdit();

        edit.setGeoCodeTypeName(actionForm.getGeoCodeTypeName());
        edit.setParentGeoCodeTypeName(actionForm.getParentGeoCodeTypeChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditGeoCodeTypeForm getForm()
            throws NamingException {
        return GeoUtil.getHome().getEditGeoCodeTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditGeoCodeTypeResult result, GeoCodeTypeSpec spec, GeoCodeTypeEdit edit) {
        actionForm.setOriginalGeoCodeTypeName(spec.getGeoCodeTypeName());
        actionForm.setGeoCodeTypeName(edit.getGeoCodeTypeName());
        actionForm.setParentGeoCodeTypeChoice(edit.getParentGeoCodeTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditGeoCodeTypeForm commandForm)
            throws Exception {
        return GeoUtil.getHome().editGeoCodeType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditGeoCodeTypeResult result) {
        request.setAttribute(AttributeConstants.GEO_CODE_TYPE, result.getGeoCodeType());
    }

}
