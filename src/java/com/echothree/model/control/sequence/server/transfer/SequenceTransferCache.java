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

package com.echothree.model.control.sequence.server.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SequenceTransferCache
        extends BaseSequenceTransferCache<Sequence, SequenceTransfer> {
    
    /** Creates a new instance of SequenceTransferCache */
    public SequenceTransferCache(UserVisit userVisit, SequenceControl sequenceControl) {
        super(userVisit, sequenceControl);
        
        setIncludeEntityInstance(true);
    }
    
    public SequenceTransfer getSequenceTransfer(Sequence sequence) {
        SequenceTransfer sequenceTransfer = get(sequence);
        
        if(sequenceTransfer == null) {
            SequenceDetail sequenceDetail = sequence.getLastDetail();
            SequenceTypeTransferCache sequenceTypeTransferCache = sequenceControl.getSequenceTransferCaches(userVisit).getSequenceTypeTransferCache();
            SequenceTypeTransfer sequenceType = sequenceTypeTransferCache.getSequenceTypeTransfer(sequenceDetail.getSequenceType());
            String sequenceName = sequenceDetail.getSequenceName();
            String mask = sequenceDetail.getMask();
            Integer chunkSize = sequenceDetail.getChunkSize();
            Boolean isDefault = sequenceDetail.getIsDefault();
            Integer sortOrder = sequenceDetail.getSortOrder();
            String value = sequenceControl.getSequenceValue(sequence).getValue();
            String description = sequenceControl.getBestSequenceDescription(sequence, getLanguage());
            
            sequenceTransfer = new SequenceTransfer(sequenceType, sequenceName, mask, chunkSize, isDefault, sortOrder, value, description);
            put(sequence, sequenceTransfer);
        }
        return sequenceTransfer;
    }
    
}
