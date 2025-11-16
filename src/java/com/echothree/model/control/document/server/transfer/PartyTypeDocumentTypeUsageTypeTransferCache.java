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

package com.echothree.model.control.document.server.transfer;

import com.echothree.model.control.document.common.transfer.PartyTypeDocumentTypeUsageTypeTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.document.server.entity.PartyTypeDocumentTypeUsageType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyTypeDocumentTypeUsageTypeTransferCache
        extends BaseDocumentTransferCache<PartyTypeDocumentTypeUsageType, PartyTypeDocumentTypeUsageTypeTransfer> {

    DocumentControl documentControl = Session.getModelController(DocumentControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);

    /** Creates a new instance of PartyTypeDocumentTypeUsageTypeTransferCache */
    public PartyTypeDocumentTypeUsageTypeTransferCache() {
        super();
    }
    
    public PartyTypeDocumentTypeUsageTypeTransfer getPartyTypeDocumentTypeUsageTypeTransfer(UserVisit userVisit, PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType) {
        var partyTypeDocumentTypeUsageTypeTransfer = get(partyTypeDocumentTypeUsageType);
        
        if(partyTypeDocumentTypeUsageTypeTransfer == null) {
            var partyTypeTransfer = partyControl.getPartyTypeTransfer(userVisit, partyTypeDocumentTypeUsageType.getPartyType());
            var documentTypeUsageTypeTransfer = documentControl.getDocumentTypeUsageTypeTransfer(userVisit, partyTypeDocumentTypeUsageType.getDocumentTypeUsageType());
            var isDefault = partyTypeDocumentTypeUsageType.getIsDefault();
            var sortOrder = partyTypeDocumentTypeUsageType.getSortOrder();
            
            partyTypeDocumentTypeUsageTypeTransfer = new PartyTypeDocumentTypeUsageTypeTransfer(partyTypeTransfer, documentTypeUsageTypeTransfer, isDefault, sortOrder);
            put(userVisit, partyTypeDocumentTypeUsageType, partyTypeDocumentTypeUsageTypeTransfer);
        }
        
        return partyTypeDocumentTypeUsageTypeTransfer;
    }
    
}
