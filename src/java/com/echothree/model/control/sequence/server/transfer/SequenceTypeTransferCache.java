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

import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SequenceTypeTransferCache
        extends BaseSequenceTransferCache<SequenceType, SequenceTypeTransfer> {

    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);

    /** Creates a new instance of SequenceTypeTransferCache */
    protected SequenceTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public SequenceTypeTransfer getSequenceTypeTransfer(UserVisit userVisit, SequenceType sequenceType) {
        var sequenceTypeTransfer = get(sequenceType);
        
        if(sequenceTypeTransfer == null) {
            var sequenceTypeDetail = sequenceType.getLastDetail();
            var sequenceTypeName = sequenceTypeDetail.getSequenceTypeName();
            var prefix = sequenceTypeDetail.getPrefix();
            var suffix = sequenceTypeDetail.getSuffix();
            var sequenceEncoderTypeTransferCache = sequenceControl.getSequenceTransferCaches().getSequenceEncoderTypeTransferCache();
            var sequenceEncoderType = sequenceEncoderTypeTransferCache.getSequenceEncoderTypeTransfer(userVisit, sequenceTypeDetail.getSequenceEncoderType());
            var sequenceChecksumTypeTransferCache = sequenceControl.getSequenceTransferCaches().getSequenceChecksumTypeTransferCache();
            var sequenceChecksumType = sequenceChecksumTypeTransferCache.getSequenceChecksumTypeTransfer(userVisit, sequenceTypeDetail.getSequenceChecksumType());
            var chunkSize = sequenceTypeDetail.getChunkSize();
            var isDefault = sequenceTypeDetail.getIsDefault();
            var sortOrder = sequenceTypeDetail.getSortOrder();
            var description = sequenceControl.getBestSequenceTypeDescription(sequenceType, getLanguage(userVisit));
            
            sequenceTypeTransfer = new SequenceTypeTransfer(sequenceTypeName, prefix, suffix, sequenceEncoderType,
                    sequenceChecksumType, chunkSize, isDefault, sortOrder, description);
            put(userVisit, sequenceType, sequenceTypeTransfer);
        }
        return sequenceTypeTransfer;
    }
    
}
