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

import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.result.GetWarehouseResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetPartyAliasResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

public class WarehouseAliasUtil {

    private WarehouseAliasUtil() {
        super();
    }

    private static class WarehouseAliasUtilHolder {
        static WarehouseAliasUtil instance = new WarehouseAliasUtil();
    }

    public static WarehouseAliasUtil getInstance() {
        return WarehouseAliasUtilHolder.instance;
    }

    public void setupWarehouse(HttpServletRequest request, String partyName)
            throws NamingException {
        var commandForm = WarehouseUtil.getHome().getGetWarehouseForm();

        commandForm.setPartyName(partyName);

        var commandResult = WarehouseUtil.getHome().getWarehouse(MainBaseAction.getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetWarehouseResult)executionResult.getResult();
            var warehouse = result.getWarehouse();

            if(warehouse != null) {
                request.setAttribute(AttributeConstants.WAREHOUSE, warehouse);
            }
        }
    }

    public void setupWarehouse(HttpServletRequest request)
            throws NamingException {
        setupWarehouse(request, request.getParameter(ParameterConstants.PARTY_NAME));
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
