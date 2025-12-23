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

package com.echothree.model.control.order.server.control;

import com.echothree.model.control.order.server.transfer.OrderAdjustmentTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderAdjustmentTypeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderAliasTransferCache;
import com.echothree.model.control.order.server.transfer.OrderAliasTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderAliasTypeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderLineAdjustmentTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderLineAdjustmentTypeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderLineTimeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderPaymentPreferenceTransferCache;
import com.echothree.model.control.order.server.transfer.OrderPriorityDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderPriorityTransferCache;
import com.echothree.model.control.order.server.transfer.OrderRoleTransferCache;
import com.echothree.model.control.order.server.transfer.OrderRoleTypeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderShipmentGroupTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTimeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTimeTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTimeTypeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTypeTransferCache;
import com.echothree.util.server.control.BaseModelControl;
import javax.inject.Inject;

public class BaseOrderControl
        extends BaseModelControl {

    /** Creates a new instance of OrderControl */
    protected BaseOrderControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Order Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    OrderTypeTransferCache orderTypeTransferCache;

    @Inject
    OrderTypeDescriptionTransferCache orderTypeDescriptionTransferCache;

    @Inject
    OrderTimeTypeTransferCache orderTimeTypeTransferCache;

    @Inject
    OrderTimeTypeDescriptionTransferCache orderTimeTypeDescriptionTransferCache;

    @Inject
    OrderPaymentPreferenceTransferCache orderPaymentPreferenceTransferCache;

    @Inject
    OrderShipmentGroupTransferCache orderShipmentGroupTransferCache;

    @Inject
    OrderTimeTransferCache orderTimeTransferCache;

    @Inject
    OrderLineTimeTransferCache orderLineTimeTransferCache;

    @Inject
    OrderAdjustmentTypeTransferCache orderAdjustmentTypeTransferCache;

    @Inject
    OrderAdjustmentTypeDescriptionTransferCache orderAdjustmentTypeDescriptionTransferCache;

    @Inject
    OrderLineAdjustmentTypeTransferCache orderLineAdjustmentTypeTransferCache;

    @Inject
    OrderLineAdjustmentTypeDescriptionTransferCache orderLineAdjustmentTypeDescriptionTransferCache;

    @Inject
    OrderRoleTypeTransferCache orderRoleTypeTransferCache;

    @Inject
    OrderRoleTransferCache orderRoleTransferCache;

    @Inject
    OrderAliasTypeTransferCache orderAliasTypeTransferCache;

    @Inject
    OrderAliasTypeDescriptionTransferCache orderAliasTypeDescriptionTransferCache;

    @Inject
    OrderAliasTransferCache orderAliasTransferCache;

    @Inject
    OrderPriorityTransferCache orderPriorityTransferCache;

    @Inject
    OrderPriorityDescriptionTransferCache orderPriorityDescriptionTransferCache;
    
}
