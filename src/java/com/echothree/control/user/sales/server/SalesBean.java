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

package com.echothree.control.user.sales.server;

import com.echothree.control.user.sales.common.SalesRemote;
import com.echothree.control.user.sales.common.form.*;
import com.echothree.control.user.sales.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateSalesOrderBatchCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSalesOrderBatch(UserVisitPK userVisitPK, GetSalesOrderBatchForm form) {
        return CDI.current().select(GetSalesOrderBatchCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSalesOrderBatches(UserVisitPK userVisitPK, GetSalesOrderBatchesForm form) {
        return CDI.current().select(GetSalesOrderBatchesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSalesOrderBatchStatusChoices(UserVisitPK userVisitPK, GetSalesOrderBatchStatusChoicesForm form) {
        return CDI.current().select(GetSalesOrderBatchStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setSalesOrderBatchStatus(UserVisitPK userVisitPK, SetSalesOrderBatchStatusForm form) {
        return CDI.current().select(SetSalesOrderBatchStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSalesOrderBatch(UserVisitPK userVisitPK, EditSalesOrderBatchForm form) {
        return CDI.current().select(EditSalesOrderBatchCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSalesOrderBatch(UserVisitPK userVisitPK, DeleteSalesOrderBatchForm form) {
        return CDI.current().select(DeleteSalesOrderBatchCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Sales Orders
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrder(UserVisitPK userVisitPK, CreateSalesOrderForm form) {
        return CDI.current().select(CreateSalesOrderCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSalesOrderStatusChoices(UserVisitPK userVisitPK, GetSalesOrderStatusChoicesForm form) {
        return CDI.current().select(GetSalesOrderStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setSalesOrderStatus(UserVisitPK userVisitPK, SetSalesOrderStatusForm form) {
        return CDI.current().select(SetSalesOrderStatusCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sales Order Payment Preferences
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrderPaymentPreference(UserVisitPK userVisitPK, CreateSalesOrderPaymentPreferenceForm form) {
        return CDI.current().select(CreateSalesOrderPaymentPreferenceCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sales Order Times
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrderTime(UserVisitPK userVisitPK, CreateSalesOrderTimeForm form) {
        return CDI.current().select(CreateSalesOrderTimeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSalesOrderTime(UserVisitPK userVisitPK, GetSalesOrderTimeForm form) {
        return CDI.current().select(GetSalesOrderTimeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSalesOrderTimes(UserVisitPK userVisitPK, GetSalesOrderTimesForm form) {
        return CDI.current().select(GetSalesOrderTimesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSalesOrderTime(UserVisitPK userVisitPK, EditSalesOrderTimeForm form) {
        return CDI.current().select(EditSalesOrderTimeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSalesOrderTime(UserVisitPK userVisitPK, DeleteSalesOrderTimeForm form) {
        return CDI.current().select(DeleteSalesOrderTimeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Sales Order Lines
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSalesOrderLine(UserVisitPK userVisitPK, CreateSalesOrderLineForm form) {
        return CDI.current().select(CreateSalesOrderLineCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Sales Order Shipment Groups
    // -------------------------------------------------------------------------

    @Override
    public CommandResult editSalesOrderShipmentGroup(UserVisitPK userVisitPK, EditSalesOrderShipmentGroupForm form) {
        return CDI.current().select(EditSalesOrderShipmentGroupCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Sales Order Line Times
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSalesOrderLineTime(UserVisitPK userVisitPK, CreateSalesOrderLineTimeForm form) {
        return CDI.current().select(CreateSalesOrderLineTimeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSalesOrderLineTime(UserVisitPK userVisitPK, GetSalesOrderLineTimeForm form) {
        return CDI.current().select(GetSalesOrderLineTimeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSalesOrderLineTimes(UserVisitPK userVisitPK, GetSalesOrderLineTimesForm form) {
        return CDI.current().select(GetSalesOrderLineTimesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSalesOrderLineTime(UserVisitPK userVisitPK, EditSalesOrderLineTimeForm form) {
        return CDI.current().select(EditSalesOrderLineTimeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSalesOrderLineTime(UserVisitPK userVisitPK, DeleteSalesOrderLineTimeForm form) {
        return CDI.current().select(DeleteSalesOrderLineTimeCommand.class).get().run(userVisitPK, form);
    }

}
