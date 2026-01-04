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

package com.echothree.ui.web.main.action.core.editor;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.EditorDescriptionEdit;
import com.echothree.control.user.core.common.form.EditEditorDescriptionForm;
import com.echothree.control.user.core.common.result.EditEditorDescriptionResult;
import com.echothree.control.user.core.common.spec.EditorDescriptionSpec;
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
    path = "/Core/Editor/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "EditorDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Editor/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/editor/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, EditorDescriptionSpec, EditorDescriptionEdit, EditEditorDescriptionForm, EditEditorDescriptionResult> {
    
    @Override
    protected EditorDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getEditorDescriptionSpec();
        
        spec.setEditorName(findParameter(request, ParameterConstants.EDITOR_NAME, actionForm.getEditorName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected EditorDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getEditorDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditEditorDescriptionForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditEditorDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditEditorDescriptionResult result, EditorDescriptionSpec spec, EditorDescriptionEdit edit) {
        actionForm.setEditorName(spec.getEditorName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditEditorDescriptionForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editEditorDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.EDITOR_NAME, actionForm.getEditorName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditEditorDescriptionResult result) {
        request.setAttribute(AttributeConstants.EDITOR_DESCRIPTION, result.getEditorDescription());
    }

}
