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

package com.echothree.ui.web.main.action.configuration.scaleusetype;

import com.echothree.control.user.scale.common.ScaleUtil;
import com.echothree.control.user.scale.common.edit.ScaleUseTypeDescriptionEdit;
import com.echothree.control.user.scale.common.form.EditScaleUseTypeDescriptionForm;
import com.echothree.control.user.scale.common.result.EditScaleUseTypeDescriptionResult;
import com.echothree.control.user.scale.common.spec.ScaleUseTypeDescriptionSpec;
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
    path = "/Configuration/ScaleUseType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ScaleUseTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/ScaleUseType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/scaleusetype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ScaleUseTypeDescriptionSpec, ScaleUseTypeDescriptionEdit, EditScaleUseTypeDescriptionForm, EditScaleUseTypeDescriptionResult> {
    
    @Override
    protected ScaleUseTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ScaleUtil.getHome().getScaleUseTypeDescriptionSpec();
        
        spec.setScaleUseTypeName(findParameter(request, ParameterConstants.SCALE_USE_TYPE_NAME, actionForm.getScaleUseTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ScaleUseTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ScaleUtil.getHome().getScaleUseTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditScaleUseTypeDescriptionForm getForm()
            throws NamingException {
        return ScaleUtil.getHome().getEditScaleUseTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditScaleUseTypeDescriptionResult result, ScaleUseTypeDescriptionSpec spec, ScaleUseTypeDescriptionEdit edit) {
        actionForm.setScaleUseTypeName(spec.getScaleUseTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditScaleUseTypeDescriptionForm commandForm)
            throws Exception {
        return ScaleUtil.getHome().editScaleUseTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SCALE_USE_TYPE_NAME, actionForm.getScaleUseTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditScaleUseTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.SCALE_USE_TYPE_DESCRIPTION, result.getScaleUseTypeDescription());
    }

}
