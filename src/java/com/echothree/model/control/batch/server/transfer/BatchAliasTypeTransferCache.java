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

package com.echothree.model.control.batch.server.transfer;

import com.echothree.model.control.batch.common.transfer.BatchAliasTypeTransfer;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.data.batch.server.entity.BatchAliasType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class BatchAliasTypeTransferCache
        extends BaseBatchTransferCache<BatchAliasType, BatchAliasTypeTransfer> {

    BatchControl batchControl = Session.getModelController(BatchControl.class);

    /** Creates a new instance of BatchAliasTypeTransferCache */
    public BatchAliasTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public BatchAliasTypeTransfer getTransfer(UserVisit userVisit, BatchAliasType batchAliasType) {
        var batchAliasTypeTransfer = get(batchAliasType);
        
        if(batchAliasTypeTransfer == null) {
            var batchAliasTypeDetail = batchAliasType.getLastDetail();
            var batchType = batchControl.getBatchTypeTransfer(userVisit, batchAliasTypeDetail.getBatchType());
            var batchAliasTypeName = batchAliasTypeDetail.getBatchAliasTypeName();
            var validationPattern = batchAliasTypeDetail.getValidationPattern();
            var isDefault = batchAliasTypeDetail.getIsDefault();
            var sortOrder = batchAliasTypeDetail.getSortOrder();
            var description = batchControl.getBestBatchAliasTypeDescription(batchAliasType, getLanguage(userVisit));
            
            batchAliasTypeTransfer = new BatchAliasTypeTransfer(batchType, batchAliasTypeName, validationPattern, isDefault, sortOrder, description);
            put(userVisit, batchAliasType, batchAliasTypeTransfer);
        }
        
        return batchAliasTypeTransfer;
    }
    
}
