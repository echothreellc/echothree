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
import com.echothree.control.user.party.common.result.GetPartyAliasTypeDescriptionResult;
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
    path = "/Configuration/PartyAliasType/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "PartyAliasTypeDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PartyAliasType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/partyaliastype/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName(final DescriptionDeleteActionForm actionForm) {
        return EntityTypes.PartyAliasTypeDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyTypeName(findParameter(request, ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName()));
        actionForm.setPartyAliasTypeName(findParameter(request, ParameterConstants.PARTY_ALIAS_TYPE_NAME, actionForm.getPartyAliasTypeName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = PartyUtil.getHome().getGetPartyAliasTypeDescriptionForm();
        
        commandForm.setPartyTypeName(actionForm.getPartyTypeName());
        commandForm.setPartyAliasTypeName(actionForm.getPartyAliasTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        var commandResult = PartyUtil.getHome().getPartyAliasTypeDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPartyAliasTypeDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.PARTY_ALIAS_TYPE_DESCRIPTION, result.getPartyAliasTypeDescription());
        }
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = PartyUtil.getHome().getDeletePartyAliasTypeDescriptionForm();

        commandForm.setPartyTypeName(actionForm.getPartyTypeName());
        commandForm.setPartyAliasTypeName(actionForm.getPartyAliasTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return PartyUtil.getHome().deletePartyAliasTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName());
        parameters.put(ParameterConstants.PARTY_ALIAS_TYPE_NAME, actionForm.getPartyAliasTypeName());
    }
    
}
