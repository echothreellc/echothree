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

package com.echothree.model.control.sequence.server.transfer;

import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class SequenceTransferCaches
        extends BaseTransferCaches {
    
    protected SequenceControl sequenceControl;
    
    protected SequenceEncoderTypeTransferCache sequenceEncoderTypeTransferCache;
    protected SequenceChecksumTypeTransferCache sequenceChecksumTypeTransferCache;
    protected SequenceTypeTransferCache sequenceTypeTransferCache;
    protected SequenceTypeDescriptionTransferCache sequenceTypeDescriptionTransferCache;
    protected SequenceTransferCache sequenceTransferCache;
    protected SequenceDescriptionTransferCache sequenceDescriptionTransferCache;
    
    /** Creates a new instance of SequenceTransferCaches */
    public SequenceTransferCaches(UserVisit userVisit, SequenceControl sequenceControl) {
        super(userVisit);
        
        this.sequenceControl = sequenceControl;
    }
    
    public SequenceEncoderTypeTransferCache getSequenceEncoderTypeTransferCache() {
        if(sequenceEncoderTypeTransferCache == null)
            sequenceEncoderTypeTransferCache = new SequenceEncoderTypeTransferCache(userVisit, sequenceControl);
        
        return sequenceEncoderTypeTransferCache;
    }
    
    public SequenceChecksumTypeTransferCache getSequenceChecksumTypeTransferCache() {
        if(sequenceChecksumTypeTransferCache == null)
            sequenceChecksumTypeTransferCache = new SequenceChecksumTypeTransferCache(userVisit, sequenceControl);
        
        return sequenceChecksumTypeTransferCache;
    }
    
    public SequenceTypeTransferCache getSequenceTypeTransferCache() {
        if(sequenceTypeTransferCache == null)
            sequenceTypeTransferCache = new SequenceTypeTransferCache(userVisit, sequenceControl);
        
        return sequenceTypeTransferCache;
    }
    
    public SequenceTypeDescriptionTransferCache getSequenceTypeDescriptionTransferCache() {
        if(sequenceTypeDescriptionTransferCache == null)
            sequenceTypeDescriptionTransferCache = new SequenceTypeDescriptionTransferCache(userVisit, sequenceControl);
        
        return sequenceTypeDescriptionTransferCache;
    }
    
    public SequenceTransferCache getSequenceTransferCache() {
        if(sequenceTransferCache == null)
            sequenceTransferCache = new SequenceTransferCache(userVisit, sequenceControl);
        
        return sequenceTransferCache;
    }
    
    public SequenceDescriptionTransferCache getSequenceDescriptionTransferCache() {
        if(sequenceDescriptionTransferCache == null)
            sequenceDescriptionTransferCache = new SequenceDescriptionTransferCache(userVisit, sequenceControl);
        
        return sequenceDescriptionTransferCache;
    }
    
}
