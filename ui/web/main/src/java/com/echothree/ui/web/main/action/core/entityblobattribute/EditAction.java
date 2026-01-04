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

package com.echothree.ui.web.main.action.core.entityblobattribute;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.EditEntityBlobAttributeResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.type.ByteArray;
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
    path = "/Core/EntityBlobAttribute/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EntityBlobAttributeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Form", path = "/core/entityblobattribute/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var returnUrl = request.getParameter(ParameterConstants.RETURN_URL);
        
        try {
            var entityRef = request.getParameter(ParameterConstants.ENTITY_REF);
            var entityAttributeName = request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_NAME);
            var languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
            
            if(forwardKey == null) {
                var actionForm = (EditActionForm)form;
                var commandForm = CoreUtil.getHome().getEditEntityBlobAttributeForm();
                var spec = CoreUtil.getHome().getEntityBlobAttributeSpec();
                var blobAttribute = actionForm.getBlobAttribute();
                
                if(entityRef == null)
                    entityRef = actionForm.getEntityRef();
                if(entityAttributeName == null)
                    entityAttributeName = actionForm.getEntityAttributeName();
                if(returnUrl == null)
                    returnUrl = actionForm.getReturnUrl();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setEntityRef(entityRef);
                spec.setEntityAttributeName(entityAttributeName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    var edit = CoreUtil.getHome().getEntityBlobAttributeEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setMimeTypeName(blobAttribute.getContentType());
                    edit.setBlobAttribute(new ByteArray(blobAttribute.getFileData()));

                    var commandResult = CoreUtil.getHome().editEntityBlobAttribute(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditEntityBlobAttributeResult)executionResult.getResult();
                            
                            if(result != null) {
                                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                            }
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editEntityBlobAttribute(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditEntityBlobAttributeResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setEntityRef(entityRef);
                            actionForm.setEntityAttributeName(entityAttributeName);
                            actionForm.setReturnUrl(returnUrl);
                            actionForm.setLanguageIsoName(languageIsoName);
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
        
        return forwardKey == null ? new ActionForward(returnUrl, true) : mapping.findForward(forwardKey);
    }
    
}
