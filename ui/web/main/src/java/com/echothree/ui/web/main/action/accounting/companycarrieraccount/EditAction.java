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

package com.echothree.ui.web.main.action.accounting.companycarrieraccount;

import com.echothree.control.user.carrier.common.CarrierUtil;
import com.echothree.control.user.carrier.common.edit.PartyCarrierAccountEdit;
import com.echothree.control.user.carrier.common.form.EditPartyCarrierAccountForm;
import com.echothree.control.user.carrier.common.result.EditPartyCarrierAccountResult;
import com.echothree.control.user.carrier.common.spec.PartyCarrierAccountSpec;
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
    path = "/Accounting/CompanyCarrierAccount/Edit",
    mappingClass = SecureActionMapping.class,
    name = "CompanyCarrierAccountEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/CompanyCarrierAccount/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/companycarrieraccount/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PartyCarrierAccountSpec, PartyCarrierAccountEdit, EditPartyCarrierAccountForm, EditPartyCarrierAccountResult> {

    @Override
    protected PartyCarrierAccountSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CarrierUtil.getHome().getPartyCarrierAccountSpec();

        spec.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        spec.setCarrierName(findParameter(request, ParameterConstants.CARRIER_NAME, actionForm.getCarrierName()));

        actionForm.setPartyName(spec.getPartyName());

        return spec;
    }

    @Override
    protected PartyCarrierAccountEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CarrierUtil.getHome().getPartyCarrierAccountEdit();

        edit.setAccount(actionForm.getAccount());
        edit.setAlwaysUseThirdPartyBilling(actionForm.getAlwaysUseThirdPartyBilling().toString());

        return edit;
    }

    @Override
    protected EditPartyCarrierAccountForm getForm()
            throws NamingException {
        return CarrierUtil.getHome().getEditPartyCarrierAccountForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPartyCarrierAccountResult result, PartyCarrierAccountSpec spec, PartyCarrierAccountEdit edit)
            throws NamingException {
        actionForm.setPartyName(spec.getPartyName());
        actionForm.setCarrierName(spec.getCarrierName());
        actionForm.setAccount(edit.getAccount());
        actionForm.setAlwaysUseThirdPartyBilling(Boolean.valueOf(edit.getAlwaysUseThirdPartyBilling()));
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyCarrierAccountForm commandForm)
            throws Exception {
        return CarrierUtil.getHome().editPartyCarrierAccount(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditPartyCarrierAccountResult result)
            throws NamingException {
        request.setAttribute(AttributeConstants.PARTY_CARRIER_ACCOUNT, result.getPartyCarrierAccount());
        BaseCompanyCarrierAccountAction.setupCompany(request, actionForm.getPartyName());
    }
    
}