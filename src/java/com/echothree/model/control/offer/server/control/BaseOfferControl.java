// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.offer.server.control;

import com.echothree.model.control.offer.server.transfer.OfferChainTypeTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferCustomerTypeTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferItemPriceTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferItemTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferNameElementDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferNameElementTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferUseTransferCache;
import com.echothree.model.control.offer.server.transfer.SourceTransferCache;
import com.echothree.model.control.offer.server.transfer.UseDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.UseNameElementDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.UseNameElementTransferCache;
import com.echothree.model.control.offer.server.transfer.UseTransferCache;
import com.echothree.model.control.offer.server.transfer.UseTypeDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.UseTypeTransferCache;
import com.echothree.util.server.control.BaseModelControl;
import javax.inject.Inject;

public class BaseOfferControl
        extends BaseModelControl {

    /** Creates a new instance of BaseOfferControl */
    protected BaseOfferControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Offer Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    OfferTransferCache offerTransferCache;

    @Inject
    OfferDescriptionTransferCache offerDescriptionTransferCache;

    @Inject
    OfferUseTransferCache offerUseTransferCache;

    @Inject
    SourceTransferCache sourceTransferCache;

    @Inject
    OfferItemPriceTransferCache offerItemPriceTransferCache;

    @Inject
    OfferItemTransferCache offerItemTransferCache;

    @Inject
    OfferNameElementDescriptionTransferCache offerNameElementDescriptionTransferCache;

    @Inject
    OfferNameElementTransferCache offerNameElementTransferCache;

    @Inject
    UseNameElementDescriptionTransferCache useNameElementDescriptionTransferCache;

    @Inject
    UseNameElementTransferCache useNameElementTransferCache;

    @Inject
    UseDescriptionTransferCache useDescriptionTransferCache;

    @Inject
    UseTransferCache useTransferCache;

    @Inject
    UseTypeDescriptionTransferCache useTypeDescriptionTransferCache;

    @Inject
    UseTypeTransferCache useTypeTransferCache;

    @Inject
    OfferCustomerTypeTransferCache offerCustomerTypeTransferCache;

    @Inject
    OfferChainTypeTransferCache offerChainTypeTransferCache;

}
