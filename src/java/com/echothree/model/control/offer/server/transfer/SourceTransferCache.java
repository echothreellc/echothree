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

package com.echothree.model.control.offer.server.transfer;

import com.echothree.model.control.offer.common.transfer.SourceTransfer;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class SourceTransferCache
        extends BaseOfferTransferCache<Source, SourceTransfer> {

    OfferUseControl offerUseControl = Session.getModelController(OfferUseControl.class);

    /** Creates a new instance of SourceTransferCache */
    public SourceTransferCache() {
        super();
    }
    
    public SourceTransfer getSourceTransfer(Source source) {
        var sourceTransfer = get(source);
        
        if(sourceTransfer == null) {
            var sourceDetail = source.getLastDetail();
            var sourceName = sourceDetail.getSourceName();
            var offerUseTransfer = offerUseControl.getOfferUseTransfer(userVisit, sourceDetail.getOfferUse());
            var isDefault = sourceDetail.getIsDefault();
            var sortOrder = sourceDetail.getSortOrder();
            
            sourceTransfer = new SourceTransfer(sourceName, offerUseTransfer, isDefault, sortOrder);
            put(userVisit, source, sourceTransfer);
        }
        
        return sourceTransfer;
    }
    
}
