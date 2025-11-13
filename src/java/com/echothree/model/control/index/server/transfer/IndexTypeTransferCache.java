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

import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.index.common.IndexOptions;
import com.echothree.model.control.index.common.transfer.IndexTypeTransfer;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class IndexTypeTransferCache
        extends BaseIndexTransferCache<IndexType, IndexTypeTransfer> {

    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);

    /** Creates a new instance of IndexTypeTransferCache */
    public IndexTypeTransferCache(IndexControl indexControl) {
        super(indexControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(IndexOptions.IndexTypeIncludeUuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public IndexTypeTransfer getIndexTypeTransfer(IndexType indexType) {
        var indexTypeTransfer = get(indexType);

        if(indexTypeTransfer == null) {
            var indexTypeDetail = indexType.getLastDetail();
            var indexTypeName = indexTypeDetail.getIndexTypeName();
            var entityType = indexTypeDetail.getEntityType();
            var entityTypeTransfer = entityType == null ? null : entityTypeControl.getEntityTypeTransfer(userVisit, entityType);
            var isDefault = indexTypeDetail.getIsDefault();
            var sortOrder = indexTypeDetail.getSortOrder();
            var description = indexControl.getBestIndexTypeDescription(indexType, getLanguage(userVisit));

            indexTypeTransfer = new IndexTypeTransfer(indexTypeName, entityTypeTransfer, isDefault, sortOrder, description);
            put(userVisit, indexType, indexTypeTransfer);
        }

        return indexTypeTransfer;
    }

}
