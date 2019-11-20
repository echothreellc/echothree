// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.sales.common.result.CreateSalesOrderResult;
import com.echothree.cucumber.EmployeePersonas;
import com.echothree.cucumber.LastCommandResult;
import com.echothree.util.common.validation.FieldType;
import cucumber.api.java.en.When;
import javax.naming.NamingException;

public class SalesOrderLineSteps {

    private void createSalesOrderLine(String persona, String orderLineSequence, String itemName, String inventoryConditionName,
            String unitOfMeasureTypeName, String quantity, String unitAmount, String taxable, String description,
            String cancellationPolicyName, String returnPolicyName, String offerName, String useName)
            throws NamingException {
        var salesService = SalesUtil.getHome();
        var createSalesOrderLineForm = salesService.getCreateSalesOrderLineForm();
        var employeePersona = EmployeePersonas.getEmployeePersona(persona);

        createSalesOrderLineForm.setOrderName(employeePersona.lastSalesOrderName);
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
        createSalesOrderLineForm.setOfferName(offerName);
        createSalesOrderLineForm.setUseName(useName);

        var commandResult = salesService.createSalesOrderLine(employeePersona.userVisitPK, createSalesOrderLineForm);

        LastCommandResult.commandResult = commandResult;
        var result = (CreateSalesOrderLineResult)commandResult.getExecutionResult().getResult();

        employeePersona.lastSalesOrderName = commandResult.getHasErrors() ? null : result.getOrderName();
        employeePersona.lastSalesOrderLineSequence = commandResult.getHasErrors() ? null : result.getOrderLineSequence();
    }

    @When("^the employee ([^\"]*) adds ([^\"]*) ([^\"]*) to the sales order$")
    public void theEmployeeAddsToTheSalesOrder(String persona, String quantity, String itemName)
            throws NamingException {
        createSalesOrderLine(persona, null, itemName, null, null, quantity, null, null, null, null, null, null, null);
    }

}
