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
import com.echothree.control.user.core.common.edit.EditorEdit;
import com.echothree.control.user.core.common.form.EditEditorForm;
import com.echothree.control.user.core.common.result.EditEditorResult;
import com.echothree.control.user.core.common.spec.EditorSpec;
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
    path = "/Core/Editor/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EditorEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Editor/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/editor/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, EditorSpec, EditorEdit, EditEditorForm, EditEditorResult> {
    
    @Override
    protected EditorSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getEditorSpec();
        
        spec.setEditorName(findParameter(request, ParameterConstants.ORIGINAL_EDITOR_NAME, actionForm.getOriginalEditorName()));
        
        return spec;
    }
    
    @Override
    protected EditorEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getEditorEdit();

        edit.setEditorName(actionForm.getEditorName());
        edit.setHasDimensions(actionForm.getHasDimensions().toString());
        edit.setMinimumHeight(actionForm.getMinimumHeight());
        edit.setMinimumWidth(actionForm.getMinimumWidth());
        edit.setMaximumHeight(actionForm.getMaximumHeight());
        edit.setMaximumWidth(actionForm.getMaximumWidth());
        edit.setDefaultHeight(actionForm.getDefaultHeight());
        edit.setDefaultWidth(actionForm.getDefaultWidth());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditEditorForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditEditorForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditEditorResult result, EditorSpec spec, EditorEdit edit) {
        actionForm.setOriginalEditorName(spec.getEditorName());
        actionForm.setEditorName(edit.getEditorName());
        actionForm.setHasDimensions(Boolean.valueOf(edit.getHasDimensions()));
        actionForm.setMinimumHeight(edit.getMinimumHeight());
        actionForm.setMinimumWidth(edit.getMinimumWidth());
        actionForm.setMaximumHeight(edit.getMaximumHeight());
        actionForm.setMaximumWidth(edit.getMaximumWidth());
        actionForm.setDefaultHeight(edit.getDefaultHeight());
        actionForm.setDefaultWidth(edit.getDefaultWidth());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditEditorForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editEditor(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditEditorResult result) {
        request.setAttribute(AttributeConstants.EDITOR, result.getEditor());
    }

}
