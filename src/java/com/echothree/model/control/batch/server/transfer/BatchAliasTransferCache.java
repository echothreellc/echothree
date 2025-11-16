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

import com.echothree.model.control.batch.common.transfer.BatchAliasTransfer;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.data.batch.server.entity.BatchAlias;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class BatchAliasTransferCache
        extends BaseBatchTransferCache<BatchAlias, BatchAliasTransfer> {

    BatchControl batchControl = Session.getModelController(BatchControl.class);

    /** Creates a new instance of BatchAliasTransferCache */
    public BatchAliasTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public BatchAliasTransfer getTransfer(UserVisit userVisit, BatchAlias batchAlias) {
        var batchAliasTransfer = get(batchAlias);
        
        if(batchAliasTransfer == null) {
            var batchAliasType = batchControl.getBatchAliasTypeTransfer(userVisit, batchAlias.getBatchAliasType());
            var alias = batchAlias.getAlias();
            
            batchAliasTransfer = new BatchAliasTransfer(batchAliasType, alias);
            put(userVisit, batchAlias, batchAliasTransfer);
        }
        
        return batchAliasTransfer;
    }
    
}
