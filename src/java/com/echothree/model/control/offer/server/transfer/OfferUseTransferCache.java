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

import com.echothree.model.control.offer.common.transfer.OfferTransfer;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.offer.common.transfer.UseTransfer;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.OfferUseDetail;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class OfferUseTransferCache
        extends BaseOfferTransferCache<OfferUse, OfferUseTransfer> {
    
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    
    /** Creates a new instance of OfferUseTransferCache */
    public OfferUseTransferCache(UserVisit userVisit, OfferControl offerControl) {
        super(userVisit, offerControl);
        
        setIncludeEntityInstance(true);
    }
    
    public OfferUseTransfer getOfferUseTransfer(OfferUse offerUse) {
        OfferUseTransfer offerUseTransfer = get(offerUse);
        
        if(offerUseTransfer == null) {
            OfferUseDetail offerUseDetail = offerUse.getLastDetail();
            OfferTransfer offerTransfer = offerControl.getOfferTransfer(userVisit, offerUseDetail.getOffer());
            UseTransfer useTransfer = offerControl.getUseTransfer(userVisit, offerUseDetail.getUse());
            Sequence salesOrderSequence = offerUseDetail.getSalesOrderSequence();
            SequenceTransfer salesOrderSequenceTransfer = salesOrderSequence == null? null:sequenceControl.getSequenceTransfer(userVisit, salesOrderSequence);
            
            offerUseTransfer = new OfferUseTransfer(offerTransfer, useTransfer, salesOrderSequenceTransfer);
            put(offerUse, offerUseTransfer);
        }
        
        return offerUseTransfer;
    }
    
}
