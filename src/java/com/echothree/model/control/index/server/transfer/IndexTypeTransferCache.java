// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.core.remote.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.index.common.IndexOptions;
import com.echothree.model.control.index.remote.transfer.IndexTypeTransfer;
import com.echothree.model.control.index.server.IndexControl;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.model.data.index.server.entity.IndexTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class IndexTypeTransferCache
        extends BaseIndexTransferCache<IndexType, IndexTypeTransfer> {

    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);

    /** Creates a new instance of IndexTypeTransferCache */
    public IndexTypeTransferCache(UserVisit userVisit, IndexControl indexControl) {
        super(userVisit, indexControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(IndexOptions.IndexTypeIncludeKey));
            setIncludeGuid(options.contains(IndexOptions.IndexTypeIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public IndexTypeTransfer getIndexTypeTransfer(IndexType indexType) {
        IndexTypeTransfer indexTypeTransfer = get(indexType);

        if(indexTypeTransfer == null) {
            IndexTypeDetail indexTypeDetail = indexType.getLastDetail();
            String indexTypeName = indexTypeDetail.getIndexTypeName();
            EntityType entityType = indexTypeDetail.getEntityType();
            EntityTypeTransfer entityTypeTransfer = entityType == null ? null : coreControl.getEntityTypeTransfer(userVisit, entityType);
            Boolean isDefault = indexTypeDetail.getIsDefault();
            Integer sortOrder = indexTypeDetail.getSortOrder();
            String description = indexControl.getBestIndexTypeDescription(indexType, getLanguage());

            indexTypeTransfer = new IndexTypeTransfer(indexTypeName, entityTypeTransfer, isDefault, sortOrder, description);
            put(indexType, indexTypeTransfer);
        }

        return indexTypeTransfer;
    }

}
