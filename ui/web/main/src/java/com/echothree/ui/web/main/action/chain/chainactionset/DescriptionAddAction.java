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

package com.echothree.ui.web.main.action.chain.chainactionset;

import com.echothree.control.user.chain.common.ChainUtil;
import com.echothree.control.user.chain.common.result.GetChainActionSetResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
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
    path = "/Chain/ChainActionSet/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "ChainActionSetDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/ChainActionSet/Description", redirect = true),
        @SproutForward(name = "Form", path = "/chain/chainactionset/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setChainKindName(findParameter(request, ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName()));
        actionForm.setChainTypeName(findParameter(request, ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName()));
        actionForm.setChainName(findParameter(request, ParameterConstants.CHAIN_NAME, actionForm.getChainName()));
        actionForm.setChainActionSetName(findParameter(request, ParameterConstants.CHAIN_ACTION_SET_NAME, actionForm.getChainActionSetName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ChainUtil.getHome().getGetChainActionSetForm();

        commandForm.setChainKindName(actionForm.getChainKindName());
        commandForm.setChainTypeName(actionForm.getChainTypeName());
        commandForm.setChainName(actionForm.getChainName());
        commandForm.setChainActionSetName(actionForm.getChainActionSetName());

        var commandResult = ChainUtil.getHome().getChainActionSet(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetChainActionSetResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.CHAIN_ACTION_SET, result.getChainActionSet());
        }
    }
    
    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ChainUtil.getHome().getCreateChainActionSetDescriptionForm();

        commandForm.setChainKindName(actionForm.getChainKindName());
        commandForm.setChainTypeName(actionForm.getChainTypeName());
        commandForm.setChainName(actionForm.getChainName());
        commandForm.setChainActionSetName(actionForm.getChainActionSetName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return ChainUtil.getHome().createChainActionSetDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName());
        parameters.put(ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName());
        parameters.put(ParameterConstants.CHAIN_NAME, actionForm.getChainName());
        parameters.put(ParameterConstants.CHAIN_ACTION_SET_NAME, actionForm.getChainActionSetName());
    }
    
}
