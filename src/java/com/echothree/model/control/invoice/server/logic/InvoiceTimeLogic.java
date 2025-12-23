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

package com.echothree.model.control.invoice.server.logic;

import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.Invoice;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class InvoiceTimeLogic {

    protected InvoiceTimeLogic() {
        super();
    }

    public static InvoiceTimeLogic getInstance() {
        return CDI.current().select(InvoiceTimeLogic.class).get();
    }

    private String getInvoiceTypeName(InvoiceType invoiceType) {
        return invoiceType.getLastDetail().getInvoiceTypeName();
    }

    public void createOrUpdateInvoiceTimeIfNotNull(final ExecutionErrorAccumulator ema, final Invoice invoice, final String invoiceTimeTypeName, final Long time,
            final BasePK partyPK) {
        if(time != null) {
            createOrUpdateInvoiceTime(ema, invoice, invoiceTimeTypeName, time, partyPK);
        }
    }

    public void createOrUpdateInvoiceTime(final ExecutionErrorAccumulator ema, final Invoice invoice, final String invoiceTimeTypeName, final Long time,
            final BasePK partyPK) {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var invoiceDetail = invoice.getLastDetail();
        var invoiceType = invoiceDetail.getInvoiceType();
        var invoiceTimeType = invoiceControl.getInvoiceTimeTypeByName(invoiceType, invoiceTimeTypeName);

        if(invoiceTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownInvoiceTimeTypeName.name(), getInvoiceTypeName(invoiceType), invoiceTimeTypeName);
            }
        } else {
            var invoiceTimeValue = invoiceControl.getInvoiceTimeValueForUpdate(invoice, invoiceTimeType);

            if(invoiceTimeValue == null) {
                invoiceControl.createInvoiceTime(invoice, invoiceTimeType, time, partyPK);
            } else {
                invoiceTimeValue.setTime(time);
                invoiceControl.updateInvoiceTimeFromValue(invoiceTimeValue, partyPK);
            }
        }
    }

    public Long getInvoiceTime(final ExecutionErrorAccumulator ema, final Invoice invoice, final String invoiceTimeTypeName) {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var invoiceDetail = invoice.getLastDetail();
        var invoiceType = invoiceDetail.getInvoiceType();
        var invoiceTimeType = invoiceControl.getInvoiceTimeTypeByName(invoiceType, invoiceTimeTypeName);
        Long result = null;

        if(invoiceTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownInvoiceTimeTypeName.name(), getInvoiceTypeName(invoiceType), invoiceTimeTypeName);
            }
        } else {
            var invoiceTime = invoiceControl.getInvoiceTime(invoice, invoiceTimeType);

            if(invoiceTime == null) {
                if(ema != null) {
                    ema.addExecutionError(ExecutionErrors.UnknownInvoiceTime.name(), getInvoiceTypeName(invoiceType), invoiceDetail.getInvoiceName(), invoiceTimeTypeName);
                }
            } else {
                result = invoiceTime.getTime();
            }
        }

        return result;
    }

    public void deleteInvoiceTime(final ExecutionErrorAccumulator ema, final Invoice invoice, final String invoiceTimeTypeName, final BasePK deletedBy) {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var invoiceDetail = invoice.getLastDetail();
        var invoiceType = invoiceDetail.getInvoiceType();
        var invoiceTimeType = invoiceControl.getInvoiceTimeTypeByName(invoiceType, invoiceTimeTypeName);

        if(invoiceTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownInvoiceTimeTypeName.name(), getInvoiceTypeName(invoiceType), invoiceTimeTypeName);
            }
        } else {
            var invoiceTime = invoiceControl.getInvoiceTimeForUpdate(invoice, invoiceTimeType);

            if(invoiceTime == null) {
                if(ema != null) {
                    ema.addExecutionError(ExecutionErrors.UnknownInvoiceTime.name(), getInvoiceTypeName(invoiceType), invoiceDetail.getInvoiceName(), invoiceTimeTypeName);
                }
            } else {
                invoiceControl.deleteInvoiceTime(invoiceTime, deletedBy);
            }
        }
    }

}
