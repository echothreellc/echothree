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

package com.echothree.ui.web.main.action.warehouse.warehousealias;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.edit.PartyAliasEdit;
import com.echothree.control.user.party.common.form.EditPartyAliasForm;
import com.echothree.control.user.party.common.result.EditPartyAliasResult;
import com.echothree.control.user.party.common.spec.PartyAliasSpec;
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
    path = "/Warehouse/WarehouseAlias/Edit",
    mappingClass = SecureActionMapping.class,
    name = "WarehouseAliasEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Warehouse/WarehouseAlias/Main", redirect = true),
        @SproutForward(name = "Form", path = "/warehouse/warehousealias/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PartyAliasSpec, PartyAliasEdit, EditPartyAliasForm, EditPartyAliasResult> {
    
    @Override
    protected PartyAliasSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = PartyUtil.getHome().getPartyAliasSpec();

        spec.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        spec.setPartyAliasTypeName(findParameter(request, ParameterConstants.PARTY_ALIAS_TYPE_NAME, actionForm.getPartyAliasTypeName()));

        return spec;
    }
    
    @Override
    protected PartyAliasEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = PartyUtil.getHome().getPartyAliasEdit();

        edit.setAlias(actionForm.getAlias());

        return edit;
    }
    
    @Override
    protected EditPartyAliasForm getForm()
            throws NamingException {
        return PartyUtil.getHome().getEditPartyAliasForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPartyAliasResult result, PartyAliasSpec spec, PartyAliasEdit edit) {
        actionForm.setPartyName(request.getParameter(ParameterConstants.PARTY_NAME));
        actionForm.setPartyAliasTypeName(spec.getPartyAliasTypeName());
        actionForm.setAlias(edit.getAlias());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyAliasForm commandForm)
            throws Exception {
        var commandResult = PartyUtil.getHome().editPartyAlias(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditPartyAliasResult)executionResult.getResult();
        var partyAlias = result.getPartyAlias();

        if(partyAlias != null) {
            request.setAttribute(AttributeConstants.PARTY_ALIAS, partyAlias);
        }
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
    @Override
    public void setupTransfer(EditActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        WarehouseAliasUtil.getInstance().setupWarehouse(request, actionForm.getPartyName());
    }
    
}