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

package com.echothree.ui.web.main.action.core.mimetype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.MimeTypeDescriptionEdit;
import com.echothree.control.user.core.common.form.EditMimeTypeDescriptionForm;
import com.echothree.control.user.core.common.result.EditMimeTypeDescriptionResult;
import com.echothree.control.user.core.common.spec.MimeTypeDescriptionSpec;
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
    path = "/Core/MimeType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "MimeTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/MimeType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/mimetype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, MimeTypeDescriptionSpec, MimeTypeDescriptionEdit, EditMimeTypeDescriptionForm, EditMimeTypeDescriptionResult> {
    
    @Override
    protected MimeTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getMimeTypeDescriptionSpec();
        
        spec.setMimeTypeName(findParameter(request, ParameterConstants.MIME_TYPE_NAME, actionForm.getMimeTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected MimeTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getMimeTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditMimeTypeDescriptionForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditMimeTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditMimeTypeDescriptionResult result, MimeTypeDescriptionSpec spec, MimeTypeDescriptionEdit edit) {
        actionForm.setMimeTypeName(spec.getMimeTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditMimeTypeDescriptionForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editMimeTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.MIME_TYPE_NAME, actionForm.getMimeTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditMimeTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.MIME_TYPE_DESCRIPTION, result.getMimeTypeDescription());
    }

}
