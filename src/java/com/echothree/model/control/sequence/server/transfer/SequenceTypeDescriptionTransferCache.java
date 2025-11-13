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

import com.echothree.model.control.sequence.common.transfer.SequenceTypeDescriptionTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.SequenceTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SequenceTypeDescriptionTransferCache
        extends BaseSequenceDescriptionTransferCache<SequenceTypeDescription, SequenceTypeDescriptionTransfer> {
    
    /** Creates a new instance of SequenceTypeDescriptionTransferCache */
    public SequenceTypeDescriptionTransferCache(SequenceControl sequenceControl) {
        super(sequenceControl);
    }
    
    public SequenceTypeDescriptionTransfer getSequenceTypeDescriptionTransfer(SequenceTypeDescription sequenceTypeDescription) {
        var sequenceTypeDescriptionTransfer = get(sequenceTypeDescription);
        
        if(sequenceTypeDescriptionTransfer == null) {
            var sequenceTypeTransferCache = sequenceControl.getSequenceTransferCaches(userVisit).getSequenceTypeTransferCache();
            var sequenceTypeTransfer = sequenceTypeTransferCache.getSequenceTypeTransfer(sequenceTypeDescription.getSequenceType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, sequenceTypeDescription.getLanguage());
            
            sequenceTypeDescriptionTransfer = new SequenceTypeDescriptionTransfer(languageTransfer, sequenceTypeTransfer, sequenceTypeDescription.getDescription());
            put(userVisit, sequenceTypeDescription, sequenceTypeDescriptionTransfer);
        }
        
        return sequenceTypeDescriptionTransfer;
    }
    
}
