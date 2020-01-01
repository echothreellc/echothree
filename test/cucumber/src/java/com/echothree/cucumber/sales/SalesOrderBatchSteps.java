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
import com.echothree.control.user.sales.common.result.CreateSalesOrderBatchResult;
import com.echothree.control.user.sales.common.result.CreateSalesOrderResult;
import com.echothree.cucumber.BasePersona;
import com.echothree.cucumber.EmployeePersonas;
import com.echothree.cucumber.LastCommandResult;
import cucumber.api.java.en.When;
import javax.naming.NamingException;

public class SalesOrderBatchSteps {

    private void createSalesOrderBatch(BasePersona persona, String currencyIsoName, String paymentMethodName, String count, String amount)
            throws NamingException {
        var salesService = SalesUtil.getHome();
        var createSalesOrderBatchForm = salesService.getCreateSalesOrderBatchForm();

        createSalesOrderBatchForm.setCurrencyIsoName(currencyIsoName);
        createSalesOrderBatchForm.setPaymentMethodName(paymentMethodName);
        createSalesOrderBatchForm.setCount(count);
        createSalesOrderBatchForm.setAmount(amount);

        var commandResult = salesService.createSalesOrderBatch(persona.userVisitPK, createSalesOrderBatchForm);

        LastCommandResult.commandResult = commandResult;
        var result = (CreateSalesOrderBatchResult)commandResult.getExecutionResult().getResult();

        persona.lastSalesOrderBatchName = commandResult.getHasErrors() ? null : result.getBatchName();
    }

    private void deleteSalesOrderBatch(BasePersona persona, String batchName)
            throws NamingException {
        var salesService = SalesUtil.getHome();
        var deleteSalesOrderBatchForm = salesService.getDeleteSalesOrderBatchForm();

        deleteSalesOrderBatchForm.setBatchName(batchName);

        var commandResult = salesService.deleteSalesOrderBatch(persona.userVisitPK, deleteSalesOrderBatchForm);

        LastCommandResult.commandResult = commandResult;
    }

    @When("^the employee ([^\"]*) adds a new sales order batch with the currency ([^\"]*) and payment method ([^\"]*)$")
    public void theEmployeeAddsANewSalesOrderBatch(String persona, String currencyIsoName, String paymentMethodName)
            throws NamingException {
        var employeePersona = EmployeePersonas.getEmployeePersona(persona);

        createSalesOrderBatch(employeePersona, currencyIsoName, paymentMethodName, null, null);
    }

    @When("^the employee ([^\"]*) deletes the last sales order batch added$")
    public void theEmployeeDeletesTheLastSalesOrderBatchAdded(String persona)
            throws NamingException {
        var employeePersona = EmployeePersonas.getEmployeePersona(persona);

        deleteSalesOrderBatch(employeePersona, employeePersona.lastSalesOrderBatchName);
    }

}