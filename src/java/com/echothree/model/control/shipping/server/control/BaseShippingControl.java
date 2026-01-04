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

package com.echothree.model.control.shipping.server.control;

import com.echothree.model.control.shipping.server.transfer.ShippingMethodCarrierServiceTransferCache;
import com.echothree.model.control.shipping.server.transfer.ShippingMethodDescriptionTransferCache;
import com.echothree.model.control.shipping.server.transfer.ShippingMethodTransferCache;
import com.echothree.util.server.control.BaseModelControl;
import javax.inject.Inject;

public abstract class BaseShippingControl
        extends BaseModelControl {

    /** Creates a new instance of BaseShippingControl */
    protected BaseShippingControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Shipping Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    ShippingMethodDescriptionTransferCache shippingMethodDescriptionTransferCache;

    @Inject
    ShippingMethodTransferCache shippingMethodTransferCache;

    @Inject
    ShippingMethodCarrierServiceTransferCache shippingMethodCarrierServiceTransferCache;

 }
