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

package com.echothree.model.control.sequence.server.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceEncoderTypeTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.SequenceEncoderType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SequenceEncoderTypeTransferCache
        extends BaseSequenceTransferCache<SequenceEncoderType, SequenceEncoderTypeTransfer> {
    
    /** Creates a new instance of SequenceEncoderTypeTransferCache */
    public SequenceEncoderTypeTransferCache(SequenceControl sequenceControl) {
        super(sequenceControl);
    }
    
    public SequenceEncoderTypeTransfer getSequenceEncoderTypeTransfer(SequenceEncoderType sequenceEncoderType) {
        var sequenceEncoderTypeTransfer = get(sequenceEncoderType);
        
        if(sequenceEncoderTypeTransfer == null) {
            var sequenceEncoderTypeName = sequenceEncoderType.getSequenceEncoderTypeName();
            var isDefault = sequenceEncoderType.getIsDefault();
            var sortOrder = sequenceEncoderType.getSortOrder();
            var description = sequenceControl.getBestSequenceEncoderTypeDescription(sequenceEncoderType, getLanguage(userVisit));
            
            sequenceEncoderTypeTransfer = new SequenceEncoderTypeTransfer(sequenceEncoderTypeName, isDefault, sortOrder, description);
            put(userVisit, sequenceEncoderType, sequenceEncoderTypeTransfer);
        }
        return sequenceEncoderTypeTransfer;
    }
    
}
