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
import com.echothree.control.user.sales.common.result.CreateSalesOrderBatchResult;
import com.echothree.cucumber.util.persona.BasePersona;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import javax.naming.NamingException;

public class SalesOrderBatchSteps implements En {

    public SalesOrderBatchSteps() {
        When("^the user adds a new sales order batch with the currency ([^\"]*) and payment method ([^\"]*)$",
                (String currencyIsoName, String paymentMethodName) -> {
                    var persona = CurrentPersona.persona;

                    createSalesOrderBatch(persona, currencyIsoName, paymentMethodName, null, null);
                });

        When("^the user deletes the last sales order batch added$",
                () -> {
                    var persona = CurrentPersona.persona;

                    deleteSalesOrderBatch(persona, persona.lastSalesOrderBatchName);
                });
    }

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

}
