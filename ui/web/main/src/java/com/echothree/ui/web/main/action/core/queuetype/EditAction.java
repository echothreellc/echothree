// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.queuetype;

import com.echothree.control.user.queue.common.QueueUtil;
import com.echothree.control.user.queue.common.edit.QueueTypeEdit;
import com.echothree.control.user.queue.common.form.EditQueueTypeForm;
import com.echothree.control.user.queue.common.result.EditQueueTypeResult;
import com.echothree.control.user.queue.common.spec.QueueTypeSpec;
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
    path = "/Core/QueueType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "QueueTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/QueueType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/queuetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, QueueTypeSpec, QueueTypeEdit, EditQueueTypeForm, EditQueueTypeResult> {
    
    @Override
    protected QueueTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = QueueUtil.getHome().getQueueTypeSpec();
        
        spec.setQueueTypeName(findParameter(request, ParameterConstants.ORIGINAL_QUEUE_TYPE_NAME, actionForm.getOriginalQueueTypeName()));
        
        return spec;
    }
    
    @Override
    protected QueueTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = QueueUtil.getHome().getQueueTypeEdit();

        edit.setQueueTypeName(actionForm.getQueueTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditQueueTypeForm getForm()
            throws NamingException {
        return QueueUtil.getHome().getEditQueueTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditQueueTypeResult result, QueueTypeSpec spec, QueueTypeEdit edit) {
        actionForm.setOriginalQueueTypeName(spec.getQueueTypeName());
        actionForm.setQueueTypeName(edit.getQueueTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditQueueTypeForm commandForm)
            throws Exception {
        return QueueUtil.getHome().editQueueType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditQueueTypeResult result) {
        request.setAttribute(AttributeConstants.QUEUE_TYPE, result.getQueueType());
    }

}
