// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.associate.server.entity.AssociateProgram;
import com.echothree.model.data.associate.server.entity.AssociateProgramDetail;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.PercentUtils;

public class AssociateProgramTransferCache
        extends BaseAssociateTransferCache<AssociateProgram, AssociateProgramTransfer> {
    
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    
    /** Creates a new instance of AssociateProgramTransferCache */
    public AssociateProgramTransferCache(UserVisit userVisit, AssociateControl associateControl) {
        super(userVisit, associateControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public AssociateProgramTransfer getTransfer(AssociateProgram associateProgram) {
        AssociateProgramTransfer associateProgramTransfer = get(associateProgram);
        
        if(associateProgramTransfer == null) {
            AssociateProgramDetail associateProgramDetail = associateProgram.getLastDetail();
            String associateProgramName = associateProgramDetail.getAssociateProgramName();
            Sequence associateSequence = associateProgramDetail.getAssociateSequence();
            SequenceTransfer associateSequenceTransfer = associateSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, associateSequence);
            Sequence associatePartyContactMechanismSequence = associateProgramDetail.getAssociatePartyContactMechanismSequence();
            SequenceTransfer associatePartyContactMechanismSequenceTransfer = associatePartyContactMechanismSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, associatePartyContactMechanismSequence);
            Sequence associateReferralSequence = associateProgramDetail.getAssociateReferralSequence();
            SequenceTransfer associateReferralSequenceTransfer = associateReferralSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, associateReferralSequence);
            String itemIndirectSalePercent = PercentUtils.getInstance().formatFractionalPercent(associateProgramDetail.getItemIndirectSalePercent());
            String itemDirectSalePercent = PercentUtils.getInstance().formatFractionalPercent(associateProgramDetail.getItemDirectSalePercent());
            Boolean isDefault = associateProgramDetail.getIsDefault();
            Integer sortOrder = associateProgramDetail.getSortOrder();
            String description = associateControl.getBestAssociateProgramDescription(associateProgram, getLanguage());
            
            associateProgramTransfer = new AssociateProgramTransfer(associateProgramName, associateSequenceTransfer,
                    associatePartyContactMechanismSequenceTransfer, associateReferralSequenceTransfer, itemIndirectSalePercent,
                    itemDirectSalePercent, isDefault, sortOrder, description);
            put(associateProgram, associateProgramTransfer);
        }
        return associateProgramTransfer;
    }
    
}
