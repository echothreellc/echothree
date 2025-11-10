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

import com.echothree.model.control.offer.common.transfer.UseNameElementTransfer;
import com.echothree.model.control.offer.server.control.UseNameElementControl;
import com.echothree.model.data.offer.server.entity.UseNameElement;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class UseNameElementTransferCache
        extends BaseOfferTransferCache<UseNameElement, UseNameElementTransfer> {

    UseNameElementControl useNameElementControl = Session.getModelController(UseNameElementControl.class);

    /** Creates a new instance of UseNameElementTransferCache */
    public UseNameElementTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    public UseNameElementTransfer getUseNameElementTransfer(UseNameElement useNameElement) {
        var useNameElementTransfer = get(useNameElement);
        
        if(useNameElementTransfer == null) {
            var useNameElementDetail = useNameElement.getLastDetail();
            var useNameElementName = useNameElementDetail.getUseNameElementName();
            var offset = useNameElementDetail.getOffset();
            var length = useNameElementDetail.getLength();
            var validationPattern = useNameElementDetail.getValidationPattern();
            var description = useNameElementControl.getBestUseNameElementDescription(useNameElement, getLanguage(userVisit));
            
            useNameElementTransfer = new UseNameElementTransfer(useNameElementName, offset, length, validationPattern, description);
            put(userVisit, useNameElement, useNameElementTransfer);
        }
        
        return useNameElementTransfer;
    }
    
}
