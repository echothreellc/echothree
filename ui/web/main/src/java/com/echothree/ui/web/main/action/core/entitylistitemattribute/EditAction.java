// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.entitylistitemattribute;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.EntityListItemAttributeEdit;
import com.echothree.control.user.core.common.form.EditEntityListItemAttributeForm;
import com.echothree.control.user.core.common.result.EditEntityListItemAttributeResult;
import com.echothree.control.user.core.common.spec.EntityListItemAttributeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/EntityListItemAttribute/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EntityListItemAttributeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Form", path = "/core/entitylistitemattribute/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String returnUrl = request.getParameter(ParameterConstants.RETURN_URL);
        
        try {
            String entityRef = request.getParameter(ParameterConstants.ENTITY_REF);
            String entityAttributeName = request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_NAME);
            
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditEntityListItemAttributeForm commandForm = CoreUtil.getHome().getEditEntityListItemAttributeForm();
                EntityListItemAttributeSpec spec = CoreUtil.getHome().getEntityListItemAttributeSpec();
                
                if(entityRef == null)
                    entityRef = actionForm.getEntityRef();
                if(entityAttributeName == null)
                    entityAttributeName = actionForm.getEntityAttributeName();
                if(returnUrl == null)
                    returnUrl = actionForm.getReturnUrl();
                
                commandForm.setSpec(spec);
                spec.setEntityRef(entityRef);
                spec.setEntityAttributeName(entityAttributeName);
                
                if(wasPost(request)) {
                    EntityListItemAttributeEdit edit = CoreUtil.getHome().getEntityListItemAttributeEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setEntityListItemName(actionForm.getEntityListItemChoice());
                    
                    CommandResult commandResult = CoreUtil.getHome().editEntityListItemAttribute(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditEntityListItemAttributeResult result = (EditEntityListItemAttributeResult)executionResult.getResult();
                            
                            if(result != null) {
                                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                            }
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = CoreUtil.getHome().editEntityListItemAttribute(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditEntityListItemAttributeResult result = (EditEntityListItemAttributeResult)executionResult.getResult();
                    
                    if(result != null) {
                        EntityListItemAttributeEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setEntityRef(entityRef);
                            actionForm.setEntityAttributeName(entityAttributeName);
                            actionForm.setReturnUrl(returnUrl);
                            actionForm.setEntityListItemChoice(edit.getEntityListItemName());
                        }
                        
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return forwardKey == null? new ActionForward(returnUrl, true): mapping.findForward(forwardKey);
    }
    
}
