// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.associate.server.transfer;

import com.echothree.model.control.associate.common.transfer.AssociateProgramTransfer;
import com.echothree.model.control.associate.common.transfer.AssociateTransfer;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.associate.server.entity.Associate;
import com.echothree.model.data.associate.server.entity.AssociateDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class AssociateTransferCache
        extends BaseAssociateTransferCache<Associate, AssociateTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of AssociateTransferCache */
    public AssociateTransferCache(UserVisit userVisit, AssociateControl associateControl) {
        super(userVisit, associateControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public AssociateTransfer getTransfer(Associate associate) {
        AssociateTransfer associateTransfer = get(associate);
        
        if(associateTransfer == null) {
            AssociateDetail associateDetail = associate.getLastDetail();
            AssociateProgramTransfer associateProgram = associateControl.getAssociateProgramTransfer(userVisit, associateDetail.getAssociateProgram());
            String associateName = associateDetail.getAssociateName();
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, associateDetail.getParty());
            String description = associateDetail.getDescription();
            MimeTypeTransfer summaryMimeType = coreControl.getMimeTypeTransfer(userVisit, associateDetail.getSummaryMimeType());
            String summary = associateDetail.getSummary();
            
            associateTransfer = new AssociateTransfer(associateProgram, associateName, party, description, summaryMimeType, summary);
            put(associate, associateTransfer);
        }
        return associateTransfer;
    }
    
}
