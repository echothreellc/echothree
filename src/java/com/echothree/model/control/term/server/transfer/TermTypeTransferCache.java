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

package com.echothree.model.control.term.server.transfer;

import com.echothree.model.control.term.common.transfer.TermTypeTransfer;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.term.server.entity.TermType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TermTypeTransferCache
        extends BaseTermTransferCache<TermType, TermTypeTransfer> {

    TermControl termControl = Session.getModelController(TermControl.class);

    /** Creates a new instance of TermTypeTransferCache */
    public TermTypeTransferCache() {
        super();
    }
    
    public TermTypeTransfer getTermTypeTransfer(UserVisit userVisit, TermType termType) {
        var termTypeTransfer = get(termType);
        
        if(termTypeTransfer == null) {
            var termTypeName = termType.getTermTypeName();
            var isDefault = termType.getIsDefault();
            var sortOrder = termType.getSortOrder();
            var description = termControl.getBestTermTypeDescription(termType, getLanguage(userVisit));
            
            termTypeTransfer = new TermTypeTransfer(termTypeName, isDefault, sortOrder, description);
            put(userVisit, termType, termTypeTransfer);
        }
        
        return termTypeTransfer;
    }
    
}
