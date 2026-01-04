// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.index.common.transfer.IndexFieldDescriptionTransfer;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.data.index.server.entity.IndexFieldDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class IndexFieldDescriptionTransferCache
        extends BaseIndexDescriptionTransferCache<IndexFieldDescription, IndexFieldDescriptionTransfer> {

    IndexControl indexControl = Session.getModelController(IndexControl.class);

    /** Creates a new instance of IndexFieldDescriptionTransferCache */
    protected IndexFieldDescriptionTransferCache() {
        super();
    }
    
    public IndexFieldDescriptionTransfer getIndexFieldDescriptionTransfer(UserVisit userVisit, IndexFieldDescription indexFieldDescription) {
        var indexFieldDescriptionTransfer = get(indexFieldDescription);
        
        if(indexFieldDescriptionTransfer == null) {
            var indexFieldTransfer = indexControl.getIndexFieldTransfer(userVisit, indexFieldDescription.getIndexField());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, indexFieldDescription.getLanguage());
            
            indexFieldDescriptionTransfer = new IndexFieldDescriptionTransfer(languageTransfer, indexFieldTransfer, indexFieldDescription.getDescription());
            put(userVisit, indexFieldDescription, indexFieldDescriptionTransfer);
        }
        
        return indexFieldDescriptionTransfer;
    }
    
}
