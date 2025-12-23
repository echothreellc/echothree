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

package com.echothree.model.control.offer.server.transfer;

import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OfferUseTransferCache
        extends BaseOfferTransferCache<OfferUse, OfferUseTransfer> {

    OfferControl offerControl = Session.getModelController(OfferControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    UseControl useControl = Session.getModelController(UseControl.class);

    /** Creates a new instance of OfferUseTransferCache */
    protected OfferUseTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public OfferUseTransfer getOfferUseTransfer(UserVisit userVisit, OfferUse offerUse) {
        var offerUseTransfer = get(offerUse);
        
        if(offerUseTransfer == null) {
            var offerUseDetail = offerUse.getLastDetail();
            var offerTransfer = offerControl.getOfferTransfer(userVisit, offerUseDetail.getOffer());
            var useTransfer = useControl.getUseTransfer(userVisit, offerUseDetail.getUse());
            var salesOrderSequence = offerUseDetail.getSalesOrderSequence();
            var salesOrderSequenceTransfer = salesOrderSequence == null? null:sequenceControl.getSequenceTransfer(userVisit, salesOrderSequence);
            
            offerUseTransfer = new OfferUseTransfer(offerTransfer, useTransfer, salesOrderSequenceTransfer);
            put(userVisit, offerUse, offerUseTransfer);
        }
        
        return offerUseTransfer;
    }
    
}
