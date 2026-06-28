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

import com.echothree.model.control.invoice.common.exception.UnknownInvoiceLineTypeNameException;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceLineType;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InvoiceLineTypeLogic
        extends BaseLogic {

    @Inject
    InvoiceControl invoiceControl;

    protected InvoiceLineTypeLogic() {
        super();
    }

    public InvoiceLineType getInvoiceLineTypeByName(final ExecutionErrorAccumulator eea, final InvoiceType invoiceType, final String invoiceLineTypeName) {
        var invoiceLineType = invoiceControl.getInvoiceLineTypeByName(invoiceType, invoiceLineTypeName);

        if(invoiceLineType == null) {
            handleExecutionError(UnknownInvoiceLineTypeNameException.class, eea, ExecutionErrors.UnknownInvoiceLineTypeName.name(), invoiceLineTypeName);
        }

        return invoiceLineType;
    }

}
