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

package com.echothree.ui.web.main.action.configuration.partyaliastype;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.edit.PartyAliasTypeDescriptionEdit;
import com.echothree.control.user.party.common.form.EditPartyAliasTypeDescriptionForm;
import com.echothree.control.user.party.common.result.EditPartyAliasTypeDescriptionResult;
import com.echothree.control.user.party.common.spec.PartyAliasTypeDescriptionSpec;
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
    path = "/Configuration/PartyAliasType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "PartyAliasTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PartyAliasType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/partyaliastype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, PartyAliasTypeDescriptionSpec, PartyAliasTypeDescriptionEdit, EditPartyAliasTypeDescriptionForm, EditPartyAliasTypeDescriptionResult> {
    
    @Override
    protected PartyAliasTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = PartyUtil.getHome().getPartyAliasTypeDescriptionSpec();
        
        spec.setPartyTypeName(findParameter(request, ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName()));
        spec.setPartyAliasTypeName(findParameter(request, ParameterConstants.PARTY_ALIAS_TYPE_NAME, actionForm.getPartyAliasTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected PartyAliasTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = PartyUtil.getHome().getPartyAliasTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditPartyAliasTypeDescriptionForm getForm()
            throws NamingException {
        return PartyUtil.getHome().getEditPartyAliasTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditPartyAliasTypeDescriptionResult result, PartyAliasTypeDescriptionSpec spec, PartyAliasTypeDescriptionEdit edit) {
        actionForm.setPartyTypeName(spec.getPartyTypeName());
        actionForm.setPartyAliasTypeName(spec.getPartyAliasTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyAliasTypeDescriptionForm commandForm)
            throws Exception {
        return PartyUtil.getHome().editPartyAliasTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName());
        parameters.put(ParameterConstants.PARTY_ALIAS_TYPE_NAME, actionForm.getPartyAliasTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditPartyAliasTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.PARTY_ALIAS_TYPE_DESCRIPTION, result.getPartyAliasTypeDescription());
    }

}
