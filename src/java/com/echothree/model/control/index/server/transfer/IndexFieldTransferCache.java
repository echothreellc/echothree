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

import com.echothree.model.control.index.common.transfer.IndexFieldTransfer;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.data.index.server.entity.IndexField;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class IndexFieldTransferCache
        extends BaseIndexTransferCache<IndexField, IndexFieldTransfer> {

    IndexControl indexControl = Session.getModelController(IndexControl.class);

    /** Creates a new instance of IndexFieldTransferCache */
    public IndexFieldTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public IndexFieldTransfer getIndexFieldTransfer(UserVisit userVisit, IndexField indexField) {
        var indexFieldTransfer = get(indexField);
        
        if(indexFieldTransfer == null) {
            var indexFieldDetail = indexField.getLastDetail();
            var indexTypeTransfer = indexControl.getIndexTypeTransfer(userVisit, indexFieldDetail.getIndexType());
            var indexFieldName = indexFieldDetail.getIndexFieldName();
            var isDefault = indexFieldDetail.getIsDefault();
            var sortOrder = indexFieldDetail.getSortOrder();
            var description = indexControl.getBestIndexFieldDescription(indexField, getLanguage(userVisit));
            
            indexFieldTransfer = new IndexFieldTransfer(indexTypeTransfer, indexFieldName, isDefault, sortOrder, description);
            put(userVisit, indexField, indexFieldTransfer);
        }
        
        return indexFieldTransfer;
    }
    
}
