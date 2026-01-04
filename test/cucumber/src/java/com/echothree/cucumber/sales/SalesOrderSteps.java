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

package com.echothree.cucumber.sales;

import com.echothree.control.user.sales.common.SalesUtil;
import com.echothree.control.user.sales.common.result.CreateSalesOrderResult;
import com.echothree.cucumber.util.persona.BasePersona;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import javax.naming.NamingException;

public class SalesOrderSteps implements En {

    public SalesOrderSteps() {
        When("^the user adds a new sales order$",
                () -> {
                    var persona = CurrentPersona.persona;

                    createSalesOrder(persona, null, null, null, null, null, null,null, null, null, null, null, null, null);
                });
    }

    private void createSalesOrder(BasePersona persona, String batchName, String sourceName, String currencyIsoName, String termName,
            String billToPartyName, String orderPriorityName, String holdUntilComplete, String allowBackorders, String allowSubstitutions,
            String allowCombiningShipments, String reference, String taxable, String workflowEntranceName)
            throws NamingException {
        var salesService = SalesUtil.getHome();
        var createSalesOrderForm = salesService.getCreateSalesOrderForm();

        createSalesOrderForm.setBatchName(batchName);
        createSalesOrderForm.setSourceName(sourceName);
        createSalesOrderForm.setCurrencyIsoName(currencyIsoName);
        createSalesOrderForm.setTermName(termName);
        createSalesOrderForm.setBillToPartyName(billToPartyName);
        createSalesOrderForm.setOrderPriorityName(orderPriorityName);
        createSalesOrderForm.setHoldUntilComplete(holdUntilComplete);
        createSalesOrderForm.setAllowBackorders(allowBackorders);
        createSalesOrderForm.setAllowSubstitutions(allowSubstitutions);
        createSalesOrderForm.setAllowCombiningShipments(allowCombiningShipments);
        createSalesOrderForm.setReference(reference);
        createSalesOrderForm.setTaxable(taxable);
        createSalesOrderForm.setWorkflowEntranceName(workflowEntranceName);

        var commandResult = salesService.createSalesOrder(persona.userVisitPK, createSalesOrderForm);

        LastCommandResult.commandResult = commandResult;
        var result = (CreateSalesOrderResult)commandResult.getExecutionResult().getResult();

        persona.lastSalesOrderName = commandResult.getHasErrors() ? null : result.getOrderName();
    }

}
