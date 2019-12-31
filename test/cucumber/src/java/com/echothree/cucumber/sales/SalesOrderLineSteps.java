// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.cucumber.BasePersona;
import com.echothree.cucumber.EmployeePersonas;
import com.echothree.cucumber.LastCommandResult;
import cucumber.api.java.en.When;
import javax.naming.NamingException;

public class SalesOrderLineSteps {

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

    @When("^the employee ([^\"]*) adds ([^\"]*) ([^\"]*) to the sales order$")
    public void theEmployeeAddsToTheSalesOrder(String persona, String quantity, String itemName)
            throws NamingException {
        var employeePersona = EmployeePersonas.getEmployeePersona(persona);

        createSalesOrderLine(employeePersona, employeePersona.lastSalesOrderName, null, itemName, null, null, quantity, null, null, null, null, null, null);
    }

    @When("^the employee ([^\"]*) adds ([^\"]*) ([^\"]*) to a new sales order$")
    public void theEmployeeAddsToANewSalesOrder(String persona, String quantity, String itemName)
            throws NamingException {
        var employeePersona = EmployeePersonas.getEmployeePersona(persona);

        createSalesOrderLine(employeePersona, null, null, itemName, null, null, quantity, null, null, null, null, null, null);
    }

}
