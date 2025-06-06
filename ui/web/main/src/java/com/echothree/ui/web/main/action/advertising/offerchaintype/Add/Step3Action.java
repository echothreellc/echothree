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

package com.echothree.ui.web.main.action.advertising.offerchaintype.Add;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.ui.web.main.action.advertising.offerchaintype.AddActionForm;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
    path = "/Advertising/OfferChainType/Add/Step3",
    mappingClass = SecureActionMapping.class,
    name = "OfferChainTypeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Advertising/OfferChainType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/advertising/offerchaintype/add/step3.jsp")
    }
)
public class Step3Action
        extends BaseAddAction {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var offerName = request.getParameter(ParameterConstants.OFFER_NAME);
        var chainKindName = request.getParameter(ParameterConstants.CHAIN_KIND_NAME);
        var chainTypeName = request.getParameter(ParameterConstants.CHAIN_TYPE_NAME);
        var actionForm = (AddActionForm)form;
        
        if(offerName == null) {
            offerName = actionForm.getOfferName();
        }
        
        if(chainKindName == null) {
            chainKindName = actionForm.getChainKindName();
        }
        
        if(chainTypeName == null) {
            chainTypeName = actionForm.getChainTypeName();
        }
        
        if(wasPost(request)) {
            var commandForm = OfferUtil.getHome().getCreateOfferChainTypeForm();
            
            commandForm.setOfferName(offerName);
            commandForm.setChainKindName(chainKindName);
            commandForm.setChainTypeName(chainTypeName);
            commandForm.setChainName(actionForm.getChainChoice());

            var commandResult = OfferUtil.getHome().createOfferChainType(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setOfferName(offerName);
            actionForm.setChainKindName(chainKindName);
            actionForm.setChainTypeName(chainTypeName);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupChainType(request, chainKindName, chainTypeName);
            setupOffer(request, offerName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.OFFER_NAME, offerName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }

}
