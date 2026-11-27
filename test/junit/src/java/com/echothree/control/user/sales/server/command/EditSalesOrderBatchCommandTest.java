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

package com.echothree.control.user.sales.server.command;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.order.server.control.OrderBatchControl;
import com.echothree.model.control.sales.server.control.SalesOrderBatchControl;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.common.pk.SymbolPositionPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.value.CurrencyValue;
import com.echothree.model.data.batch.common.pk.BatchPK;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.order.server.entity.OrderBatch;
import com.echothree.model.data.order.server.value.OrderBatchValue;
import com.echothree.model.data.payment.common.pk.PaymentMethodPK;
import com.echothree.model.data.payment.common.pk.PaymentMethodTypePK;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethodDetail;
import com.echothree.model.data.payment.server.value.PaymentMethodDetailValue;
import com.echothree.model.data.payment.server.value.PaymentMethodValue;
import com.echothree.model.data.sales.server.entity.SalesOrderBatch;
import com.echothree.model.data.sales.server.value.SalesOrderBatchValue;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.Validator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
class EditSalesOrderBatchCommandTest {

    @Test
    void lockedAmountRoundTripsThroughUpdateValidation() {
        var batchCurrency = new Currency(new CurrencyValue(new CurrencyPK(1L), "USD", "$", new SymbolPositionPK(1L),
                false, false, false, false, ",", 3, ".", 2, 4, 2, 4, 2, "-", true, 0), EntityPermission.READ_ONLY);
        var batchPK = new BatchPK(1L);
        var paymentMethodPK = new PaymentMethodPK(1L);
        var command = new EditSalesOrderBatchCommand() {
            void lockAndValidate() {
                orderBatchControl = new OrderBatchControl() {
                    @Override
                    public OrderBatch getOrderBatch(Batch batch) {
                        return new OrderBatch(new OrderBatchValue(batchPK, batchCurrency.getPrimaryKey(), 1L, 1234L, 0L, Long.MAX_VALUE),
                                EntityPermission.READ_ONLY) {
                            @Override
                            public Currency getCurrency() {
                                return batchCurrency;
                            }
                        };
                    }
                };
                salesOrderBatchControl = new SalesOrderBatchControl() {
                    @Override
                    public SalesOrderBatch getSalesOrderBatch(Batch batch) {
                        var salesOrderBatchValue = new SalesOrderBatchValue(batchPK, paymentMethodPK, 0L, Long.MAX_VALUE);
                        return new SalesOrderBatch(salesOrderBatchValue, EntityPermission.READ_ONLY) {
                            @Override
                            public PaymentMethod getPaymentMethod() {
                                return new PaymentMethod(new PaymentMethodValue(paymentMethodPK, null, null), EntityPermission.READ_ONLY) {
                                    @Override
                                    public PaymentMethodDetail getLastDetail() {
                                        var paymentMethodDetailValue = new PaymentMethodDetailValue(paymentMethodPK, "VISA",
                                                new PaymentMethodTypePK(1L), null, null, null, false, 0, 0L, Long.MAX_VALUE);
                                        return new PaymentMethodDetail(paymentMethodDetailValue, EntityPermission.READ_ONLY);
                                    }
                                };
                            }
                        };
                    }
                };

                edit = getEdit();
                doLock(edit, null);
                assertEquals("12.34", edit.getAmount());

                var validator = new Validator(this) {
                    @Override
                    public AccountingControl getAccountingControl() {
                        return new AccountingControl() {
                            @Override
                            public Currency getCurrencyByIsoName(String currencyIsoName) {
                                assertEquals("USD", currencyIsoName);
                                return batchCurrency;
                            }
                        };
                    }
                };
                assertFalse(validateEdit(validator).getHasErrors());
                assertEquals("1234", edit.getAmount());
            }
        };
        command.lockAndValidate();
    }

}
