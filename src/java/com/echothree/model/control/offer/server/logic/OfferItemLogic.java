// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.offer.server.logic;

import com.echothree.model.control.offer.common.exception.UnknownOfferItemException;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class OfferItemLogic
        extends BaseLogic {

    private OfferItemLogic() {
        super();
    }

    private static class OfferItemLogicHolder {
        static OfferItemLogic instance = new OfferItemLogic();
    }

    public static OfferItemLogic getInstance() {
        return OfferItemLogicHolder.instance;
    }

    public OfferItem getOfferItem(final ExecutionErrorAccumulator eea, final Offer offer, final Item item) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        OfferItem offerItem = offerControl.getOfferItem(offer, item);

        if(offerItem == null) {
            handleExecutionError(UnknownOfferItemException.class, eea, ExecutionErrors.UnknownOfferItem.name(), offer.getLastDetail().getOfferName(), item.getLastDetail().getItemName());
        }

        return offerItem;
    }

}
