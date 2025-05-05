// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
        return new CreateSalesOrderBatchCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSalesOrderBatch(UserVisitPK userVisitPK, GetSalesOrderBatchForm form) {
        return new GetSalesOrderBatchCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSalesOrderBatches(UserVisitPK userVisitPK, GetSalesOrderBatchesForm form) {
        return new GetSalesOrderBatchesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSalesOrderBatchStatusChoices(UserVisitPK userVisitPK, GetSalesOrderBatchStatusChoicesForm form) {
        return new GetSalesOrderBatchStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setSalesOrderBatchStatus(UserVisitPK userVisitPK, SetSalesOrderBatchStatusForm form) {
        return new SetSalesOrderBatchStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSalesOrderBatch(UserVisitPK userVisitPK, EditSalesOrderBatchForm form) {
        return new EditSalesOrderBatchCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSalesOrderBatch(UserVisitPK userVisitPK, DeleteSalesOrderBatchForm form) {
        return new DeleteSalesOrderBatchCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Sales Orders
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrder(UserVisitPK userVisitPK, CreateSalesOrderForm form) {
        return new CreateSalesOrderCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSalesOrderStatusChoices(UserVisitPK userVisitPK, GetSalesOrderStatusChoicesForm form) {
        return new GetSalesOrderStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setSalesOrderStatus(UserVisitPK userVisitPK, SetSalesOrderStatusForm form) {
        return new SetSalesOrderStatusCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sales Order Payment Preferences
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrderPaymentPreference(UserVisitPK userVisitPK, CreateSalesOrderPaymentPreferenceForm form) {
        return new CreateSalesOrderPaymentPreferenceCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sales Order Times
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrderTime(UserVisitPK userVisitPK, CreateSalesOrderTimeForm form) {
        return new CreateSalesOrderTimeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSalesOrderTime(UserVisitPK userVisitPK, GetSalesOrderTimeForm form) {
        return new GetSalesOrderTimeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSalesOrderTimes(UserVisitPK userVisitPK, GetSalesOrderTimesForm form) {
        return new GetSalesOrderTimesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSalesOrderTime(UserVisitPK userVisitPK, EditSalesOrderTimeForm form) {
        return new EditSalesOrderTimeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSalesOrderTime(UserVisitPK userVisitPK, DeleteSalesOrderTimeForm form) {
        return new DeleteSalesOrderTimeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Sales Order Lines
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSalesOrderLine(UserVisitPK userVisitPK, CreateSalesOrderLineForm form) {
        return new CreateSalesOrderLineCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Sales Order Shipment Groups
    // -------------------------------------------------------------------------

    @Override
    public CommandResult editSalesOrderShipmentGroup(UserVisitPK userVisitPK, EditSalesOrderShipmentGroupForm form) {
        return new EditSalesOrderShipmentGroupCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Sales Order Line Times
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrderLineTime(UserVisitPK userVisitPK, CreateSalesOrderLineTimeForm form) {
        return new CreateSalesOrderLineTimeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSalesOrderLineTime(UserVisitPK userVisitPK, GetSalesOrderLineTimeForm form) {
        return new GetSalesOrderLineTimeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSalesOrderLineTimes(UserVisitPK userVisitPK, GetSalesOrderLineTimesForm form) {
        return new GetSalesOrderLineTimesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSalesOrderLineTime(UserVisitPK userVisitPK, EditSalesOrderLineTimeForm form) {
        return new EditSalesOrderLineTimeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSalesOrderLineTime(UserVisitPK userVisitPK, DeleteSalesOrderLineTimeForm form) {
        return new DeleteSalesOrderLineTimeCommand().run(userVisitPK, form);
    }

}
