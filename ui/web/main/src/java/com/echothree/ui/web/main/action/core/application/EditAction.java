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

package com.echothree.ui.web.main.action.core.application;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.ApplicationEdit;
import com.echothree.control.user.core.common.form.EditApplicationForm;
import com.echothree.control.user.core.common.result.EditApplicationResult;
import com.echothree.control.user.core.common.spec.ApplicationSpec;
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
    path = "/Core/Application/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ApplicationEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Application/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/application/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ApplicationSpec, ApplicationEdit, EditApplicationForm, EditApplicationResult> {
    
    @Override
    protected ApplicationSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getApplicationSpec();
        
        spec.setApplicationName(findParameter(request, ParameterConstants.ORIGINAL_APPLICATION_NAME, actionForm.getOriginalApplicationName()));
        
        return spec;
    }
    
    @Override
    protected ApplicationEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getApplicationEdit();

        edit.setApplicationName(actionForm.getApplicationName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditApplicationForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditApplicationForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditApplicationResult result, ApplicationSpec spec, ApplicationEdit edit) {
        actionForm.setOriginalApplicationName(spec.getApplicationName());
        actionForm.setApplicationName(edit.getApplicationName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditApplicationForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editApplication(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditApplicationResult result) {
        request.setAttribute(AttributeConstants.APPLICATION, result.getApplication());
    }

}
