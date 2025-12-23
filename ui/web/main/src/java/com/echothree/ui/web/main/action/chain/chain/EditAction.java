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
import com.echothree.control.user.chain.common.edit.ChainEdit;
import com.echothree.control.user.chain.common.form.EditChainForm;
import com.echothree.control.user.chain.common.result.EditChainResult;
import com.echothree.control.user.chain.common.spec.ChainSpec;
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
    path = "/Chain/Chain/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ChainEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/Chain/Main", redirect = true),
        @SproutForward(name = "Form", path = "/chain/chain/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ChainSpec, ChainEdit, EditChainForm, EditChainResult> {
    
    @Override
    protected ChainSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ChainUtil.getHome().getChainSpec();
        
        spec.setChainKindName(findParameter(request, ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName()));
        spec.setChainTypeName(findParameter(request, ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName()));
        spec.setChainName(findParameter(request, ParameterConstants.ORIGINAL_CHAIN_NAME, actionForm.getOriginalChainName()));
        
        return spec;
    }
    
    @Override
    protected ChainEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ChainUtil.getHome().getChainEdit();

        edit.setChainName(actionForm.getChainName());
        edit.setChainInstanceSequenceName(actionForm.getChainInstanceSequenceChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditChainForm getForm()
            throws NamingException {
        return ChainUtil.getHome().getEditChainForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditChainResult result, ChainSpec spec, ChainEdit edit) {
        actionForm.setChainKindName(spec.getChainKindName());
        actionForm.setChainTypeName(spec.getChainTypeName());
        actionForm.setOriginalChainName(spec.getChainName());
        actionForm.setChainName(edit.getChainName());
        actionForm.setChainInstanceSequenceChoice(edit.getChainInstanceSequenceName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditChainForm commandForm)
            throws Exception {
        return ChainUtil.getHome().editChain(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditChainResult result) {
        request.setAttribute(AttributeConstants.CHAIN, result.getChain());
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName());
        parameters.put(ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName());
    }
    
}
