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

package com.echothree.model.control.offer.server.transfer;

import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.offer.common.OfferOptions;
import com.echothree.model.control.offer.common.OfferProperties;
import com.echothree.model.control.offer.common.transfer.OfferItemTransfer;
import com.echothree.model.control.offer.common.transfer.OfferTransfer;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.transfer.ListWrapperBuilder;
import java.util.Set;

public class OfferItemTransferCache
        extends BaseOfferTransferCache<OfferItem, OfferItemTransfer> {
    
    ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
    OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
    OfferItemControl offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

    boolean includeOfferItemPrices;

    TransferProperties transferProperties;
    boolean filterOffer;
    boolean filterItem;
    boolean filterOfferItemPrices;
    boolean filterEntityInstance;

    /** Creates a new instance of OfferItemTransferCache */
    public OfferItemTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeOfferItemPrices = options.contains(OfferOptions.OfferItemIncludeOfferItemPrices);
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(OfferItemTransfer.class);
            
            if(properties != null) {
                filterOffer = !properties.contains(OfferProperties.OFFER);
                filterItem = !properties.contains(OfferProperties.ITEM);
                filterOfferItemPrices = !properties.contains(OfferProperties.OFFER_ITEM_PRICES);
                filterEntityInstance = !properties.contains(OfferProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public OfferItemTransfer getOfferItemTransfer(OfferItem offerItem) {
        OfferItemTransfer offerItemTransfer = get(offerItem);
        
        if(offerItemTransfer == null) {
            OfferTransfer offer = filterOffer ? null : offerControl.getOfferTransfer(userVisit, offerItem.getOffer());
            ItemTransfer item = filterItem ? null : itemControl.getItemTransfer(userVisit, offerItem.getItem());
            
            offerItemTransfer = new OfferItemTransfer(offer, item);
            put(offerItem, offerItemTransfer);

            if(includeOfferItemPrices) {
                offerItemTransfer.setOfferItemPrices(ListWrapperBuilder.getInstance().filter(transferProperties,
                        offerItemControl.getOfferItemPriceTransfersByOfferItem(userVisit, offerItem)));
            }
        }
        
        return offerItemTransfer;
    }
    
}
