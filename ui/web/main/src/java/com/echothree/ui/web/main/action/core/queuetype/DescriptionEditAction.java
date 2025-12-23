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

package com.echothree.ui.web.main.action.core.queuetype;

import com.echothree.control.user.queue.common.QueueUtil;
import com.echothree.control.user.queue.common.edit.QueueTypeDescriptionEdit;
import com.echothree.control.user.queue.common.form.EditQueueTypeDescriptionForm;
import com.echothree.control.user.queue.common.result.EditQueueTypeDescriptionResult;
import com.echothree.control.user.queue.common.spec.QueueTypeDescriptionSpec;
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
    path = "/Core/QueueType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "QueueTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/QueueType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/queuetype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, QueueTypeDescriptionSpec, QueueTypeDescriptionEdit, EditQueueTypeDescriptionForm, EditQueueTypeDescriptionResult> {
    
    @Override
    protected QueueTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = QueueUtil.getHome().getQueueTypeDescriptionSpec();
        
        spec.setQueueTypeName(findParameter(request, ParameterConstants.QUEUE_TYPE_NAME, actionForm.getQueueTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected QueueTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = QueueUtil.getHome().getQueueTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditQueueTypeDescriptionForm getForm()
            throws NamingException {
        return QueueUtil.getHome().getEditQueueTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditQueueTypeDescriptionResult result, QueueTypeDescriptionSpec spec, QueueTypeDescriptionEdit edit) {
        actionForm.setQueueTypeName(spec.getQueueTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditQueueTypeDescriptionForm commandForm)
            throws Exception {
        return QueueUtil.getHome().editQueueTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.QUEUE_TYPE_NAME, actionForm.getQueueTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditQueueTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.QUEUE_TYPE_DESCRIPTION, result.getQueueTypeDescription());
    }

}
