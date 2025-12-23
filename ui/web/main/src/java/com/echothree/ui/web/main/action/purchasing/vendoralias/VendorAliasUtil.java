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

package com.echothree.ui.web.main.action.purchasing.vendoralias;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetPartyAliasResult;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.GetVendorResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
        var commandForm = VendorUtil.getHome().getGetVendorForm();

        commandForm.setPartyName(partyName);

        var commandResult = VendorUtil.getHome().getVendor(MainBaseAction.getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetVendorResult)executionResult.getResult();
            var vendor = result.getVendor();

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
        var commandForm = PartyUtil.getHome().getGetPartyAliasForm();

        commandForm.setPartyName(partyName);
        commandForm.setPartyAliasTypeName(partyAliasTypeName);

        var commandResult = PartyUtil.getHome().getPartyAlias(MainBaseAction.getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyAliasResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PARTY_ALIAS, result.getPartyAlias());
    }

}
