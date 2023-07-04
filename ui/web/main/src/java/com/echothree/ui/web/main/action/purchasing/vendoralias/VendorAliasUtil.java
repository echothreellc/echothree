// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.purchasing.vendoralias;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetPartyAliasForm;
import com.echothree.control.user.party.common.result.GetPartyAliasResult;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.form.GetVendorForm;
import com.echothree.control.user.vendor.common.result.GetVendorResult;
import com.echothree.model.control.vendor.common.transfer.VendorTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

public class VendorAliasUtil {

    private VendorAliasUtil() {
        super();
    }

    private static class VendorAliasUtilHolder {
        static VendorAliasUtil instance = new VendorAliasUtil();
    }

    public static VendorAliasUtil getInstance() {
        return VendorAliasUtilHolder.instance;
    }

    public void setupVendor(HttpServletRequest request, String partyName)
            throws NamingException {
        GetVendorForm commandForm = VendorUtil.getHome().getGetVendorForm();

        commandForm.setPartyName(partyName);

        CommandResult commandResult = VendorUtil.getHome().getVendor(MainBaseAction.getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetVendorResult result = (GetVendorResult)executionResult.getResult();
            VendorTransfer vendor = result.getVendor();

            if(vendor != null) {
                request.setAttribute(AttributeConstants.VENDOR, vendor);
            }
        }
    }

    public void setupVendor(HttpServletRequest request)
            throws NamingException {
        setupVendor(request, request.getParameter(ParameterConstants.PARTY_NAME));
    }

    public void setupPartyAliasTransfer(HttpServletRequest request, String partyName, String partyAliasTypeName)
            throws NamingException {
        GetPartyAliasForm commandForm = PartyUtil.getHome().getGetPartyAliasForm();

        commandForm.setPartyName(partyName);
        commandForm.setPartyAliasTypeName(partyAliasTypeName);

        CommandResult commandResult = PartyUtil.getHome().getPartyAlias(MainBaseAction.getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPartyAliasResult result = (GetPartyAliasResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PARTY_ALIAS, result.getPartyAlias());
    }

}
