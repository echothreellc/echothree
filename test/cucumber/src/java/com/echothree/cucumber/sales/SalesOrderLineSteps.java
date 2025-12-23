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
import com.echothree.control.user.sales.common.result.CreateSalesOrderLineResult;
import com.echothree.cucumber.util.persona.BasePersona;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import javax.naming.NamingException;

public class SalesOrderLineSteps implements En {

    public SalesOrderLineSteps() {
        When("^the user adds ([^\"]*) ([^\"]*) to the sales order$",
                (String quantity, String itemName) -> {
                    var persona = CurrentPersona.persona;

                    createSalesOrderLine(persona, persona.lastSalesOrderName, null, itemName, null, null, quantity, null, null, null, null, null, null);
                });

        When("^the user adds ([^\"]*) ([^\"]*) to a new sales order$",
                (String quantity, String itemName) -> {
                    var persona = CurrentPersona.persona;

                    createSalesOrderLine(persona, null, null, itemName, null, null, quantity, null, null, null, null, null, null);
                });
    }

    private void createSalesOrderLine(BasePersona persona, String salesOrderName, String orderLineSequence, String itemName,
            String inventoryConditionName, String unitOfMeasureTypeName, String quantity, String unitAmount, String taxable,
            String description, String cancellationPolicyName, String returnPolicyName, String sourceName)
            throws NamingException {
        var salesService = SalesUtil.getHome();
        var createSalesOrderLineForm = salesService.getCreateSalesOrderLineForm();

        createSalesOrderLineForm.setOrderName(salesOrderName);
        createSalesOrderLineForm.setOrderLineSequence(orderLineSequence);
        createSalesOrderLineForm.setItemName(itemName);
        createSalesOrderLineForm.setInventoryConditionName(inventoryConditionName);
        createSalesOrderLineForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
        createSalesOrderLineForm.setQuantity(quantity);
        createSalesOrderLineForm.setUnitAmount(unitAmount);
        createSalesOrderLineForm.setTaxable(taxable);
        createSalesOrderLineForm.setDescription(description);
        createSalesOrderLineForm.setCancellationPolicyName(cancellationPolicyName);
        createSalesOrderLineForm.setReturnPolicyName(returnPolicyName);
        createSalesOrderLineForm.setSourceName(sourceName);

        var commandResult = salesService.createSalesOrderLine(persona.userVisitPK, createSalesOrderLineForm);

        LastCommandResult.commandResult = commandResult;
        var result = (CreateSalesOrderLineResult)commandResult.getExecutionResult().getResult();

        persona.lastSalesOrderName = commandResult.getHasErrors() ? null : result.getOrderName();
        persona.lastSalesOrderLineSequence = commandResult.getHasErrors() ? null : result.getOrderLineSequence();
    }

}
