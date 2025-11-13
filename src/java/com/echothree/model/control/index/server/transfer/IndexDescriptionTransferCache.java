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

import com.echothree.model.control.index.common.transfer.IndexDescriptionTransfer;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.data.index.server.entity.IndexDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class IndexDescriptionTransferCache
        extends BaseIndexDescriptionTransferCache<IndexDescription, IndexDescriptionTransfer> {
    
    /** Creates a new instance of IndexDescriptionTransferCache */
    public IndexDescriptionTransferCache(IndexControl indexControl) {
        super(indexControl);
    }
    
    public IndexDescriptionTransfer getIndexDescriptionTransfer(UserVisit userVisit, IndexDescription indexDescription) {
        var indexDescriptionTransfer = get(indexDescription);
        
        if(indexDescriptionTransfer == null) {
            var indexTransfer = indexControl.getIndexTransfer(userVisit, indexDescription.getIndex());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, indexDescription.getLanguage());
            
            indexDescriptionTransfer = new IndexDescriptionTransfer(languageTransfer, indexTransfer, indexDescription.getDescription());
            put(userVisit, indexDescription, indexDescriptionTransfer);
        }
        return indexDescriptionTransfer;
    }
    
}
