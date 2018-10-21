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

package com.echothree.ui.web.main.action.advertising.offeruse;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.remote.edit.OfferUseEdit;
import com.echothree.control.user.offer.remote.form.EditOfferUseForm;
import com.echothree.control.user.offer.remote.result.EditOfferUseResult;
import com.echothree.control.user.offer.remote.spec.OfferUseSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Advertising/OfferUse/Edit",
    mappingClass = SecureActionMapping.class,
    name = "OfferUseEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Advertising/OfferUse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/advertising/offeruse/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String offerName = request.getParameter(ParameterConstants.OFFER_NAME);
        String useName = request.getParameter(ParameterConstants.USE_NAME);
        EditActionForm actionForm = (EditActionForm)form;
        EditOfferUseForm commandForm = OfferUtil.getHome().getEditOfferUseForm();
        OfferUseSpec spec = OfferUtil.getHome().getOfferUseSpec();
        
        if(offerName == null)
            offerName = actionForm.getOfferName();
        if(useName == null)
            useName = actionForm.getUseName();
        
        commandForm.setSpec(spec);
        spec.setOfferName(offerName);
        spec.setUseName(useName);
        
        if(wasPost(request)) {
            OfferUseEdit edit = OfferUtil.getHome().getOfferUseEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            
            edit.setSalesOrderSequenceName(actionForm.getSalesOrderSequenceChoice());
            
            CommandResult commandResult = OfferUtil.getHome().editOfferUse(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    EditOfferUseResult result = (EditOfferUseResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = OfferUtil.getHome().editOfferUse(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditOfferUseResult result = (EditOfferUseResult)executionResult.getResult();
            
            if(result != null) {
                OfferUseEdit edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setOfferName(offerName);
                    actionForm.setUseName(useName);
                    actionForm.setSalesOrderSequenceChoice(edit.getSalesOrderSequenceName());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.OFFER_NAME, offerName);
            request.setAttribute(AttributeConstants.USE_NAME, useName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.OFFER_NAME, offerName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}