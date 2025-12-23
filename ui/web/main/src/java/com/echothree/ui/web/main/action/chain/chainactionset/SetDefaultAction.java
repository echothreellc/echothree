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

package com.echothree.ui.web.main.action.chain.chainactionset;

import com.echothree.control.user.chain.common.ChainUtil;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Chain/ChainActionSet/SetDefault",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/ChainActionSet/Main", redirect = true)
    }
)
public class SetDefaultAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var chainKindName = request.getParameter(ParameterConstants.CHAIN_KIND_NAME);
        var chainTypeName = request.getParameter(ParameterConstants.CHAIN_TYPE_NAME);
        var chainName = request.getParameter(ParameterConstants.CHAIN_NAME);
        var chainActionSetName = request.getParameter(ParameterConstants.CHAIN_ACTION_SET_NAME);
        var commandForm = ChainUtil.getHome().getSetDefaultChainActionSetForm();

        commandForm.setChainKindName(chainKindName);
        commandForm.setChainTypeName(chainTypeName);
        commandForm.setChainName(chainName);
        commandForm.setChainActionSetName(chainActionSetName);

        ChainUtil.getHome().setDefaultChainActionSet(getUserVisitPK(request), commandForm);

        var customActionForward = new CustomActionForward(mapping.findForward(ForwardConstants.DISPLAY));
        Map<String, String> parameters = new HashMap<>(3);
        
        parameters.put(ParameterConstants.CHAIN_KIND_NAME, chainKindName);
        parameters.put(ParameterConstants.CHAIN_TYPE_NAME, chainTypeName);
        parameters.put(ParameterConstants.CHAIN_NAME, chainName);
        customActionForward.setParameters(parameters);
        
        return customActionForward;
    }

}