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

package com.echothree.ui.web.main.action.sequence.sequencetype;

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Sequence/SequenceType/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "SequenceTypeDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Sequence/SequenceType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/sequence/sequencetype/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var sequenceTypeName = request.getParameter(ParameterConstants.SEQUENCE_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                if(wasPost(request)) {
                    var descriptionAddActionForm = (DescriptionAddActionForm)form;

                    var createSequenceTypeDescriptionForm = SequenceUtil.getHome().getCreateSequenceTypeDescriptionForm();
                    
                    if(sequenceTypeName == null)
                        sequenceTypeName = descriptionAddActionForm.getSequenceTypeName();
                    
                    createSequenceTypeDescriptionForm.setSequenceTypeName(sequenceTypeName);
                    createSequenceTypeDescriptionForm.setLanguageIsoName(descriptionAddActionForm.getLanguageChoice());
                    createSequenceTypeDescriptionForm.setDescription(descriptionAddActionForm.getDescription());

                    var commandResult = SequenceUtil.getHome().createSequenceTypeDescription(getUserVisitPK(request), createSequenceTypeDescriptionForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else
                    forwardKey = ForwardConstants.FORM;
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM) || forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            parameters.put(ParameterConstants.SEQUENCE_TYPE_NAME, sequenceTypeName);
            customActionForward.setParameters(parameters);
            
            request.setAttribute("sequenceTypeName", sequenceTypeName); // TODO: not encoded
        }
        
        return customActionForward;
    }
    
}