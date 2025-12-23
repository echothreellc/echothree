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
import com.echothree.control.user.core.common.edit.MimeTypeEdit;
import com.echothree.control.user.core.common.form.EditMimeTypeForm;
import com.echothree.control.user.core.common.result.EditMimeTypeResult;
import com.echothree.control.user.core.common.spec.MimeTypeSpec;
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
    path = "/Core/MimeType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "MimeTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/MimeType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/mimetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, MimeTypeSpec, MimeTypeEdit, EditMimeTypeForm, EditMimeTypeResult> {
    
    @Override
    protected MimeTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getMimeTypeSpec();
        
        spec.setMimeTypeName(findParameter(request, ParameterConstants.ORIGINAL_MIME_TYPE_NAME, actionForm.getOriginalMimeTypeName()));
        
        return spec;
    }
    
    @Override
    protected MimeTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getMimeTypeEdit();

        edit.setMimeTypeName(actionForm.getMimeTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditMimeTypeForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditMimeTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditMimeTypeResult result, MimeTypeSpec spec, MimeTypeEdit edit) {
        actionForm.setOriginalMimeTypeName(spec.getMimeTypeName());
        actionForm.setMimeTypeName(edit.getMimeTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditMimeTypeForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editMimeType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditMimeTypeResult result) {
        request.setAttribute(AttributeConstants.MIME_TYPE, result.getMimeType());
    }

}
