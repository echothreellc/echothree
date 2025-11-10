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

package com.echothree.model.control.index.server.transfer;

import com.echothree.model.control.index.common.transfer.IndexTypeDescriptionTransfer;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.data.index.server.entity.IndexTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class IndexTypeDescriptionTransferCache
        extends BaseIndexDescriptionTransferCache<IndexTypeDescription, IndexTypeDescriptionTransfer> {
    
    /** Creates a new instance of IndexTypeDescriptionTransferCache */
    public IndexTypeDescriptionTransferCache(UserVisit userVisit, IndexControl indexControl) {
        super(userVisit, indexControl);
    }
    
    public IndexTypeDescriptionTransfer getIndexTypeDescriptionTransfer(IndexTypeDescription indexTypeDescription) {
        var indexTypeDescriptionTransfer = get(indexTypeDescription);
        
        if(indexTypeDescriptionTransfer == null) {
            var indexTypeTransfer = indexControl.getIndexTypeTransfer(userVisit, indexTypeDescription.getIndexType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, indexTypeDescription.getLanguage());
            
            indexTypeDescriptionTransfer = new IndexTypeDescriptionTransfer(languageTransfer, indexTypeTransfer, indexTypeDescription.getDescription());
            put(userVisit, indexTypeDescription, indexTypeDescriptionTransfer);
        }
        return indexTypeDescriptionTransfer;
    }
    
}
