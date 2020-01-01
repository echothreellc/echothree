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

package com.echothree.model.control.sales.server.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.batch.common.transfer.BatchTypeTransfer;
import com.echothree.model.control.batch.server.BatchControl;
import com.echothree.model.control.batch.server.transfer.GenericBatchTransferCache;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTransfer;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.control.sales.common.transfer.SalesOrderBatchTransfer;
import com.echothree.model.control.sales.server.SalesControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.batch.server.entity.BatchDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.order.server.entity.OrderBatch;
import com.echothree.model.data.sales.server.entity.SalesOrderBatch;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class SalesOrderBatchTransferCache
        extends GenericBatchTransferCache<SalesOrderBatchTransfer> {
    
    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    PaymentControl paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);
    OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
    SalesControl salesControl;
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of SalesOrderBatchTransferCache */
    public SalesOrderBatchTransferCache(UserVisit userVisit, SalesControl salesControl) {
        super(userVisit, (BatchControl)Session.getModelController(BatchControl.class));

        this.salesControl = salesControl;
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public SalesOrderBatchTransfer getTransfer(Batch batch) {
        SalesOrderBatchTransfer salesOrderBatchTransfer = get(batch);
        
        if(salesOrderBatchTransfer == null) {
            BatchDetail batchDetail = batch.getLastDetail();
            OrderBatch orderBatch = orderControl.getOrderBatch(batch);
            SalesOrderBatch salesOrderBatch = salesControl.getSalesOrderBatch(batch);
            BatchTypeTransfer batchTypeTransfer = batchControl.getBatchTypeTransfer(userVisit, batchDetail.getBatchType());
            String batchName = batchDetail.getBatchName();
            Currency currency = orderBatch.getCurrency();
            CurrencyTransfer currencyTransfer = accountingControl.getCurrencyTransfer(userVisit, currency);
            Long count = orderBatch.getCount();
            PaymentMethodTransfer paymentMethodTransfer = paymentControl.getPaymentMethodTransfer(userVisit, salesOrderBatch.getPaymentMethod());
            String amount = AmountUtils.getInstance().formatPriceLine(currency, orderBatch.getAmount());
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(batch.getPrimaryKey());
            
            salesOrderBatchTransfer = new SalesOrderBatchTransfer(batchTypeTransfer, batchName, currencyTransfer, paymentMethodTransfer, count, amount,
                    getBatchStatus(batch, entityInstance));
            put(batch, salesOrderBatchTransfer, entityInstance);
            
            handleOptions(batch, salesOrderBatchTransfer);
        }
        
        return salesOrderBatchTransfer;
    }
    
}
