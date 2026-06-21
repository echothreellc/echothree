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
import com.echothree.control.user.sales.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface SalesService
        extends SalesForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Sales Order Batches
    // -------------------------------------------------------------------------
    
    CommandResult<CreateSalesOrderBatchResult> createSalesOrderBatch(UserVisitPK userVisitPK, CreateSalesOrderBatchForm form);
    
    CommandResult<GetSalesOrderBatchResult> getSalesOrderBatch(UserVisitPK userVisitPK, GetSalesOrderBatchForm form);

    CommandResult<GetSalesOrderBatchesResult> getSalesOrderBatches(UserVisitPK userVisitPK, GetSalesOrderBatchesForm form);
    
    CommandResult<GetSalesOrderBatchStatusChoicesResult> getSalesOrderBatchStatusChoices(UserVisitPK userVisitPK, GetSalesOrderBatchStatusChoicesForm form);
    
    CommandResult<VoidResult> setSalesOrderBatchStatus(UserVisitPK userVisitPK, SetSalesOrderBatchStatusForm form);
    
    CommandResult<EditSalesOrderBatchResult> editSalesOrderBatch(UserVisitPK userVisitPK, EditSalesOrderBatchForm form);

    CommandResult<VoidResult> deleteSalesOrderBatch(UserVisitPK userVisitPK, DeleteSalesOrderBatchForm form);

    // -------------------------------------------------------------------------
    //   Sales Orders
    // -------------------------------------------------------------------------
    
    CommandResult<CreateSalesOrderResult> createSalesOrder(UserVisitPK userVisitPK, CreateSalesOrderForm form);
    
    CommandResult<GetSalesOrderStatusChoicesResult> getSalesOrderStatusChoices(UserVisitPK userVisitPK, GetSalesOrderStatusChoicesForm form);
    
    CommandResult<VoidResult> setSalesOrderStatus(UserVisitPK userVisitPK, SetSalesOrderStatusForm form);
    
    // -------------------------------------------------------------------------
    //   Sales Order Payment Preferences
    // -------------------------------------------------------------------------
    
    CommandResult<CreateSalesOrderPaymentPreferenceResult> createSalesOrderPaymentPreference(UserVisitPK userVisitPK, CreateSalesOrderPaymentPreferenceForm form);
    
    // -------------------------------------------------------------------------
    //   Sales Order Times
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createSalesOrderTime(UserVisitPK userVisitPK, CreateSalesOrderTimeForm form);
    
    CommandResult<GetSalesOrderTimeResult> getSalesOrderTime(UserVisitPK userVisitPK, GetSalesOrderTimeForm form);

    CommandResult<GetSalesOrderTimesResult> getSalesOrderTimes(UserVisitPK userVisitPK, GetSalesOrderTimesForm form);
    
    CommandResult<EditSalesOrderTimeResult> editSalesOrderTime(UserVisitPK userVisitPK, EditSalesOrderTimeForm form);

    CommandResult<VoidResult> deleteSalesOrderTime(UserVisitPK userVisitPK, DeleteSalesOrderTimeForm form);

    // -------------------------------------------------------------------------
    //   Sales Order Shipment Groups
    // -------------------------------------------------------------------------

    public CommandResult<EditSalesOrderShipmentGroupResult> editSalesOrderShipmentGroup(UserVisitPK userVisitPK, EditSalesOrderShipmentGroupForm form);

    // -------------------------------------------------------------------------
    //   Sales Order Lines
    // -------------------------------------------------------------------------
    
    CommandResult<CreateSalesOrderLineResult> createSalesOrderLine(UserVisitPK userVisitPK, CreateSalesOrderLineForm form);
    
    // -------------------------------------------------------------------------
    //   Sales Order Line Times
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createSalesOrderLineTime(UserVisitPK userVisitPK, CreateSalesOrderLineTimeForm form);
    
    CommandResult<GetSalesOrderLineTimeResult> getSalesOrderLineTime(UserVisitPK userVisitPK, GetSalesOrderLineTimeForm form);

    CommandResult<GetSalesOrderLineTimesResult> getSalesOrderLineTimes(UserVisitPK userVisitPK, GetSalesOrderLineTimesForm form);
    
    CommandResult<EditSalesOrderLineTimeResult> editSalesOrderLineTime(UserVisitPK userVisitPK, EditSalesOrderLineTimeForm form);

    CommandResult<VoidResult> deleteSalesOrderLineTime(UserVisitPK userVisitPK, DeleteSalesOrderLineTimeForm form);

}
