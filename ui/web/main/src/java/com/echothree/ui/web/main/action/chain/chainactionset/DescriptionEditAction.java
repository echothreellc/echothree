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
import com.echothree.control.user.chain.common.edit.ChainActionSetDescriptionEdit;
import com.echothree.control.user.chain.common.form.EditChainActionSetDescriptionForm;
import com.echothree.control.user.chain.common.result.EditChainActionSetDescriptionResult;
import com.echothree.control.user.chain.common.spec.ChainActionSetDescriptionSpec;
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
    path = "/Chain/ChainActionSet/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ChainActionSetDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/ChainActionSet/Description", redirect = true),
        @SproutForward(name = "Form", path = "/chain/chainactionset/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ChainActionSetDescriptionSpec, ChainActionSetDescriptionEdit, EditChainActionSetDescriptionForm, EditChainActionSetDescriptionResult> {
    
    @Override
    protected ChainActionSetDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ChainUtil.getHome().getChainActionSetDescriptionSpec();
        
        spec.setChainKindName(findParameter(request, ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName()));
        spec.setChainTypeName(findParameter(request, ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName()));
        spec.setChainName(findParameter(request, ParameterConstants.CHAIN_NAME, actionForm.getChainName()));
        spec.setChainActionSetName(findParameter(request, ParameterConstants.CHAIN_ACTION_SET_NAME, actionForm.getChainActionSetName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ChainActionSetDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ChainUtil.getHome().getChainActionSetDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditChainActionSetDescriptionForm getForm()
            throws NamingException {
        return ChainUtil.getHome().getEditChainActionSetDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditChainActionSetDescriptionResult result, ChainActionSetDescriptionSpec spec, ChainActionSetDescriptionEdit edit) {
        actionForm.setChainKindName(spec.getChainKindName());
        actionForm.setChainTypeName(spec.getChainTypeName());
        actionForm.setChainName(spec.getChainName());
        actionForm.setChainActionSetName(spec.getChainActionSetName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditChainActionSetDescriptionForm commandForm)
            throws Exception {
        return ChainUtil.getHome().editChainActionSetDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName());
        parameters.put(ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName());
        parameters.put(ParameterConstants.CHAIN_NAME, actionForm.getChainName());
        parameters.put(ParameterConstants.CHAIN_ACTION_SET_NAME, actionForm.getChainActionSetName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditChainActionSetDescriptionResult result) {
        request.setAttribute(AttributeConstants.CHAIN_ACTION_SET_DESCRIPTION, result.getChainActionSetDescription());
    }

}
