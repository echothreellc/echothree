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
import com.echothree.control.user.chain.common.edit.ChainActionSetEdit;
import com.echothree.control.user.chain.common.form.EditChainActionSetForm;
import com.echothree.control.user.chain.common.result.EditChainActionSetResult;
import com.echothree.control.user.chain.common.spec.ChainActionSetSpec;
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
    path = "/Chain/ChainActionSet/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ChainActionSetEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/ChainActionSet/Main", redirect = true),
        @SproutForward(name = "Form", path = "/chain/chainactionset/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ChainActionSetSpec, ChainActionSetEdit, EditChainActionSetForm, EditChainActionSetResult> {
    
    @Override
    protected ChainActionSetSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ChainUtil.getHome().getChainActionSetSpec();
        
        spec.setChainKindName(findParameter(request, ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName()));
        spec.setChainTypeName(findParameter(request, ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName()));
        spec.setChainName(findParameter(request, ParameterConstants.CHAIN_NAME, actionForm.getChainName()));
        spec.setChainActionSetName(findParameter(request, ParameterConstants.ORIGINAL_CHAIN_ACTION_SET_NAME, actionForm.getOriginalChainActionSetName()));
        
        return spec;
    }
    
    @Override
    protected ChainActionSetEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ChainUtil.getHome().getChainActionSetEdit();

        edit.setChainActionSetName(actionForm.getChainActionSetName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditChainActionSetForm getForm()
            throws NamingException {
        return ChainUtil.getHome().getEditChainActionSetForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditChainActionSetResult result, ChainActionSetSpec spec, ChainActionSetEdit edit) {
        actionForm.setChainKindName(spec.getChainKindName());
        actionForm.setChainTypeName(spec.getChainTypeName());
        actionForm.setChainName(spec.getChainName());
        actionForm.setOriginalChainActionSetName(spec.getChainActionSetName());
        actionForm.setChainActionSetName(edit.getChainActionSetName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditChainActionSetForm commandForm)
            throws Exception {
        return ChainUtil.getHome().editChainActionSet(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditChainActionSetResult result) {
        request.setAttribute(AttributeConstants.CHAIN_ACTION_SET, result.getChainActionSet());
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName());
        parameters.put(ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName());
        parameters.put(ParameterConstants.CHAIN_NAME, actionForm.getChainName());
    }
    
}
