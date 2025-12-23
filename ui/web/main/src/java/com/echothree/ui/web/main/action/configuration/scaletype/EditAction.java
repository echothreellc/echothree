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

package com.echothree.ui.web.main.action.configuration.scaletype;

import com.echothree.control.user.scale.common.ScaleUtil;
import com.echothree.control.user.scale.common.edit.ScaleTypeEdit;
import com.echothree.control.user.scale.common.form.EditScaleTypeForm;
import com.echothree.control.user.scale.common.result.EditScaleTypeResult;
import com.echothree.control.user.scale.common.spec.ScaleTypeSpec;
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
    path = "/Configuration/ScaleType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ScaleTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/ScaleType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/scaletype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ScaleTypeSpec, ScaleTypeEdit, EditScaleTypeForm, EditScaleTypeResult> {
    
    @Override
    protected ScaleTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ScaleUtil.getHome().getScaleTypeSpec();
        
        spec.setScaleTypeName(findParameter(request, ParameterConstants.ORIGINAL_SCALE_TYPE_NAME, actionForm.getOriginalScaleTypeName()));
        
        return spec;
    }
    
    @Override
    protected ScaleTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ScaleUtil.getHome().getScaleTypeEdit();

        edit.setScaleTypeName(actionForm.getScaleTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditScaleTypeForm getForm()
            throws NamingException {
        return ScaleUtil.getHome().getEditScaleTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditScaleTypeResult result, ScaleTypeSpec spec, ScaleTypeEdit edit) {
        actionForm.setOriginalScaleTypeName(spec.getScaleTypeName());
        actionForm.setScaleTypeName(edit.getScaleTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditScaleTypeForm commandForm)
            throws Exception {
        return ScaleUtil.getHome().editScaleType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditScaleTypeResult result) {
        request.setAttribute(AttributeConstants.SCALE_TYPE, result.getScaleType());
    }

}
