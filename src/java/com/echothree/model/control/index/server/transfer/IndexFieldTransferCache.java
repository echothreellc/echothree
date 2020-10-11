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

package com.echothree.model.control.index.server.transfer;

import com.echothree.model.control.index.common.transfer.IndexFieldTransfer;
import com.echothree.model.control.index.common.transfer.IndexTypeTransfer;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.data.index.server.entity.IndexField;
import com.echothree.model.data.index.server.entity.IndexFieldDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class IndexFieldTransferCache
        extends BaseIndexTransferCache<IndexField, IndexFieldTransfer> {
    
    /** Creates a new instance of IndexFieldTransferCache */
    public IndexFieldTransferCache(UserVisit userVisit, IndexControl indexControl) {
        super(userVisit, indexControl);
        
        setIncludeEntityInstance(true);
    }
    
    public IndexFieldTransfer getIndexFieldTransfer(IndexField indexField) {
        IndexFieldTransfer indexFieldTransfer = get(indexField);
        
        if(indexFieldTransfer == null) {
            IndexFieldDetail indexFieldDetail = indexField.getLastDetail();
            IndexTypeTransfer indexTypeTransfer = indexControl.getIndexTypeTransfer(userVisit, indexFieldDetail.getIndexType());
            String indexFieldName = indexFieldDetail.getIndexFieldName();
            Boolean isDefault = indexFieldDetail.getIsDefault();
            Integer sortOrder = indexFieldDetail.getSortOrder();
            String description = indexControl.getBestIndexFieldDescription(indexField, getLanguage());
            
            indexFieldTransfer = new IndexFieldTransfer(indexTypeTransfer, indexFieldName, isDefault, sortOrder, description);
            put(indexField, indexFieldTransfer);
        }
        
        return indexFieldTransfer;
    }
    
}
