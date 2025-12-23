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

package com.echothree.control.user.sales.common;

import com.echothree.control.user.sales.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface SalesService
        extends SalesForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Sales Order Batches
    // -------------------------------------------------------------------------
    
    CommandResult createSalesOrderBatch(UserVisitPK userVisitPK, CreateSalesOrderBatchForm form);
    
    CommandResult getSalesOrderBatch(UserVisitPK userVisitPK, GetSalesOrderBatchForm form);

    CommandResult getSalesOrderBatches(UserVisitPK userVisitPK, GetSalesOrderBatchesForm form);
    
    CommandResult getSalesOrderBatchStatusChoices(UserVisitPK userVisitPK, GetSalesOrderBatchStatusChoicesForm form);
    
    CommandResult setSalesOrderBatchStatus(UserVisitPK userVisitPK, SetSalesOrderBatchStatusForm form);
    
    CommandResult editSalesOrderBatch(UserVisitPK userVisitPK, EditSalesOrderBatchForm form);

    CommandResult deleteSalesOrderBatch(UserVisitPK userVisitPK, DeleteSalesOrderBatchForm form);

    // -------------------------------------------------------------------------
    //   Sales Orders
    // -------------------------------------------------------------------------
    
    CommandResult createSalesOrder(UserVisitPK userVisitPK, CreateSalesOrderForm form);
    
    CommandResult getSalesOrderStatusChoices(UserVisitPK userVisitPK, GetSalesOrderStatusChoicesForm form);
    
    CommandResult setSalesOrderStatus(UserVisitPK userVisitPK, SetSalesOrderStatusForm form);
    
    // -------------------------------------------------------------------------
    //   Sales Order Payment Preferences
    // -------------------------------------------------------------------------
    
    CommandResult createSalesOrderPaymentPreference(UserVisitPK userVisitPK, CreateSalesOrderPaymentPreferenceForm form);
    
    // -------------------------------------------------------------------------
    //   Sales Order Times
    // -------------------------------------------------------------------------
    
    CommandResult createSalesOrderTime(UserVisitPK userVisitPK, CreateSalesOrderTimeForm form);
    
    CommandResult getSalesOrderTime(UserVisitPK userVisitPK, GetSalesOrderTimeForm form);

    CommandResult getSalesOrderTimes(UserVisitPK userVisitPK, GetSalesOrderTimesForm form);
    
    CommandResult editSalesOrderTime(UserVisitPK userVisitPK, EditSalesOrderTimeForm form);

    CommandResult deleteSalesOrderTime(UserVisitPK userVisitPK, DeleteSalesOrderTimeForm form);

    // -------------------------------------------------------------------------
    //   Sales Order Shipment Groups
    // -------------------------------------------------------------------------

    public CommandResult editSalesOrderShipmentGroup(UserVisitPK userVisitPK, EditSalesOrderShipmentGroupForm form);

    // -------------------------------------------------------------------------
    //   Sales Order Lines
    // -------------------------------------------------------------------------
    
    CommandResult createSalesOrderLine(UserVisitPK userVisitPK, CreateSalesOrderLineForm form);
    
    // -------------------------------------------------------------------------
    //   Sales Order Line Times
    // -------------------------------------------------------------------------
    
    CommandResult createSalesOrderLineTime(UserVisitPK userVisitPK, CreateSalesOrderLineTimeForm form);
    
    CommandResult getSalesOrderLineTime(UserVisitPK userVisitPK, GetSalesOrderLineTimeForm form);

    CommandResult getSalesOrderLineTimes(UserVisitPK userVisitPK, GetSalesOrderLineTimesForm form);
    
    CommandResult editSalesOrderLineTime(UserVisitPK userVisitPK, EditSalesOrderLineTimeForm form);

    CommandResult deleteSalesOrderLineTime(UserVisitPK userVisitPK, DeleteSalesOrderLineTimeForm form);

}
