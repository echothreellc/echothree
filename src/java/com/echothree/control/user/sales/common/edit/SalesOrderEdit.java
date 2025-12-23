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

package com.echothree.control.user.sales.common.edit;

import com.echothree.control.user.accounting.common.spec.CurrencySpec;
import com.echothree.control.user.offer.common.spec.SourceSpec;
import com.echothree.control.user.sales.common.spec.SalesOrderBatchSpec;
import com.echothree.control.user.shipment.common.spec.FreeOnBoardSpec;
import com.echothree.control.user.term.common.spec.TermSpec;
import com.echothree.util.common.form.BaseEdit;

public interface SalesOrderEdit
        extends BaseEdit, SalesOrderBatchSpec, SourceSpec, CurrencySpec, TermSpec, FreeOnBoardSpec {
    
    String getBillToPartyName();
    void setBillToPartyName(String billToPartyName);

    String getOrderPriorityName();
    void setOrderPriorityName(String orderPriorityName);

    String getHoldUntilComplete();
    void setHoldUntilComplete(String holdUntilComplete);

    String getAllowBackorders();
    void setAllowBackorders(String allowBackorders);

    String getAllowSubstitutions();
    void setAllowSubstitutions(String allowSubstitutions);

    String getAllowCombiningShipments();
    void setAllowCombiningShipments(String allowCombiningShipments);

    String getReference();
    void setReference(String reference);

    String getTaxable();
    void setTaxable(String taxable);

}
