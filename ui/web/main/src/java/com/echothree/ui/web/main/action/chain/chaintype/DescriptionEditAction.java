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

package com.echothree.ui.web.main.action.chain.chaintype;

import com.echothree.control.user.chain.common.ChainUtil;
import com.echothree.control.user.chain.common.edit.ChainTypeDescriptionEdit;
import com.echothree.control.user.chain.common.form.EditChainTypeDescriptionForm;
import com.echothree.control.user.chain.common.result.EditChainTypeDescriptionResult;
import com.echothree.control.user.chain.common.spec.ChainTypeDescriptionSpec;
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
    path = "/Chain/ChainType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ChainTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/ChainType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/chain/chaintype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ChainTypeDescriptionSpec, ChainTypeDescriptionEdit, EditChainTypeDescriptionForm, EditChainTypeDescriptionResult> {
    
    @Override
    protected ChainTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ChainUtil.getHome().getChainTypeDescriptionSpec();
        
        spec.setChainKindName(findParameter(request, ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName()));
        spec.setChainTypeName(findParameter(request, ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ChainTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ChainUtil.getHome().getChainTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditChainTypeDescriptionForm getForm()
            throws NamingException {
        return ChainUtil.getHome().getEditChainTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditChainTypeDescriptionResult result, ChainTypeDescriptionSpec spec, ChainTypeDescriptionEdit edit) {
        actionForm.setChainKindName(spec.getChainKindName());
        actionForm.setChainTypeName(spec.getChainTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditChainTypeDescriptionForm commandForm)
            throws Exception {
        return ChainUtil.getHome().editChainTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CHAIN_KIND_NAME, actionForm.getChainKindName());
        parameters.put(ParameterConstants.CHAIN_TYPE_NAME, actionForm.getChainTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditChainTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.CHAIN_TYPE_DESCRIPTION, result.getChainTypeDescription());
    }

}
