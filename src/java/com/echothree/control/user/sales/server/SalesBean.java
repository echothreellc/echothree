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

package com.echothree.control.user.sales.server;

import com.echothree.control.user.sales.common.SalesRemote;
import com.echothree.control.user.sales.common.form.*;
import com.echothree.control.user.sales.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class SalesBean
        extends SalesFormsImpl
        implements SalesRemote, SalesLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "SalesBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Sales Order Batches
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrderBatch(UserVisitPK userVisitPK, CreateSalesOrderBatchForm form) {
        return new CreateSalesOrderBatchCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSalesOrderBatch(UserVisitPK userVisitPK, GetSalesOrderBatchForm form) {
        return new GetSalesOrderBatchCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSalesOrderBatches(UserVisitPK userVisitPK, GetSalesOrderBatchesForm form) {
        return new GetSalesOrderBatchesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSalesOrderBatchStatusChoices(UserVisitPK userVisitPK, GetSalesOrderBatchStatusChoicesForm form) {
        return new GetSalesOrderBatchStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setSalesOrderBatchStatus(UserVisitPK userVisitPK, SetSalesOrderBatchStatusForm form) {
        return new SetSalesOrderBatchStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSalesOrderBatch(UserVisitPK userVisitPK, EditSalesOrderBatchForm form) {
        return new EditSalesOrderBatchCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSalesOrderBatch(UserVisitPK userVisitPK, DeleteSalesOrderBatchForm form) {
        return new DeleteSalesOrderBatchCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Sales Orders
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrder(UserVisitPK userVisitPK, CreateSalesOrderForm form) {
        return new CreateSalesOrderCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSalesOrderStatusChoices(UserVisitPK userVisitPK, GetSalesOrderStatusChoicesForm form) {
        return new GetSalesOrderStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setSalesOrderStatus(UserVisitPK userVisitPK, SetSalesOrderStatusForm form) {
        return new SetSalesOrderStatusCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Sales Order Payment Preferences
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrderPaymentPreference(UserVisitPK userVisitPK, CreateSalesOrderPaymentPreferenceForm form) {
        return new CreateSalesOrderPaymentPreferenceCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Sales Order Times
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrderTime(UserVisitPK userVisitPK, CreateSalesOrderTimeForm form) {
        return new CreateSalesOrderTimeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSalesOrderTime(UserVisitPK userVisitPK, GetSalesOrderTimeForm form) {
        return new GetSalesOrderTimeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSalesOrderTimes(UserVisitPK userVisitPK, GetSalesOrderTimesForm form) {
        return new GetSalesOrderTimesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSalesOrderTime(UserVisitPK userVisitPK, EditSalesOrderTimeForm form) {
        return new EditSalesOrderTimeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSalesOrderTime(UserVisitPK userVisitPK, DeleteSalesOrderTimeForm form) {
        return new DeleteSalesOrderTimeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Sales Order Lines
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrderLine(UserVisitPK userVisitPK, CreateSalesOrderLineForm form) {
        return new CreateSalesOrderLineCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Sales Order Line Times
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrderLineTime(UserVisitPK userVisitPK, CreateSalesOrderLineTimeForm form) {
        return new CreateSalesOrderLineTimeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSalesOrderLineTime(UserVisitPK userVisitPK, GetSalesOrderLineTimeForm form) {
        return new GetSalesOrderLineTimeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSalesOrderLineTimes(UserVisitPK userVisitPK, GetSalesOrderLineTimesForm form) {
        return new GetSalesOrderLineTimesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSalesOrderLineTime(UserVisitPK userVisitPK, EditSalesOrderLineTimeForm form) {
        return new EditSalesOrderLineTimeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSalesOrderLineTime(UserVisitPK userVisitPK, DeleteSalesOrderLineTimeForm form) {
        return new DeleteSalesOrderLineTimeCommand(userVisitPK, form).run();
    }

}
