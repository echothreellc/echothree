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

package com.echothree.ui.web.main.action.chain.chainaction;

import com.echothree.control.user.chain.common.ChainUtil;
import com.echothree.control.user.chain.common.result.GetChainActionsResult;
import com.echothree.model.control.chain.common.ChainOptions;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Chain/ChainAction/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/chain/chainaction/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var commandForm = ChainUtil.getHome().getGetChainActionsForm();

        commandForm.setChainKindName(request.getParameter(ParameterConstants.CHAIN_KIND_NAME));
        commandForm.setChainTypeName(request.getParameter(ParameterConstants.CHAIN_TYPE_NAME));
        commandForm.setChainName(request.getParameter(ParameterConstants.CHAIN_NAME));
        commandForm.setChainActionSetName(request.getParameter(ParameterConstants.CHAIN_ACTION_SET_NAME));

        Set<String> options = new HashSet<>();
        options.add(ChainOptions.ChainActionIncludeRelated);
        commandForm.setOptions(options);

        var commandResult = ChainUtil.getHome().getChainActions(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetChainActionsResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CHAIN_ACTION_SET, result.getChainActionSet());
        request.setAttribute(AttributeConstants.CHAIN_ACTIONS, result.getChainActions());

        return mapping.findForward(ForwardConstants.DISPLAY);
    }

}