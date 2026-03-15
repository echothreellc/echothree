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

package com.echothree.ui.web.main.action.chain.chain;

import com.echothree.control.user.chain.common.ChainUtil;
import com.echothree.control.user.chain.common.result.GetChainResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Chain/Chain/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ChainDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/Chain/Main", redirect = true),
        @SproutForward(name = "Form", path = "/chain/chain/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName(final DeleteActionForm actionForm) {
        return EntityTypes.Chain.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setChainKindName(findParameter(request, ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName()));
        actionForm.setChainTypeName(findParameter(request, ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName()));
        actionForm.setChainName(findParameter(request, ParameterConstants.CHAIN_NAME, actionForm.getChainName()));
        actionForm.setChainName(findParameter(request, ParameterConstants.CHAIN_NAME, actionForm.getChainName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ChainUtil.getHome().getGetChainForm();
        
        commandForm.setChainKindName(actionForm.getChainKindName());
        commandForm.setChainTypeName(actionForm.getChainTypeName());
        commandForm.setChainName(actionForm.getChainName());
        commandForm.setChainName(actionForm.getChainName());

        var commandResult = ChainUtil.getHome().getChain(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetChainResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.CHAIN, result.getChain());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ChainUtil.getHome().getDeleteChainForm();

        commandForm.setChainKindName(actionForm.getChainKindName());
        commandForm.setChainTypeName(actionForm.getChainTypeName());
        commandForm.setChainName(actionForm.getChainName());
        commandForm.setChainName(actionForm.getChainName());

        return ChainUtil.getHome().deleteChain(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName());
        parameters.put(ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName());
        parameters.put(ParameterConstants.CHAIN_NAME, actionForm.getChainName());
    }
    
}
