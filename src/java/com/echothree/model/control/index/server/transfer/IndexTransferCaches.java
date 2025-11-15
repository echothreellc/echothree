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

import com.echothree.util.server.transfer.BaseTransferCaches;

public class IndexTransferCaches
        extends BaseTransferCaches {
    
    protected IndexTypeTransferCache indexTypeTransferCache;
    protected IndexTypeDescriptionTransferCache indexTypeDescriptionTransferCache;
    protected IndexFieldTransferCache indexFieldTransferCache;
    protected IndexFieldDescriptionTransferCache indexFieldDescriptionTransferCache;
    protected IndexTransferCache indexTransferCache;
    protected IndexDescriptionTransferCache indexDescriptionTransferCache;
    
    /** Creates a new instance of IndexTransferCaches */
    public IndexTransferCaches() {
        super();
    }
    
    public IndexTypeTransferCache getIndexTypeTransferCache() {
        if(indexTypeTransferCache == null) {
            indexTypeTransferCache = new IndexTypeTransferCache();
        }

        return indexTypeTransferCache;
    }

    public IndexTypeDescriptionTransferCache getIndexTypeDescriptionTransferCache() {
        if(indexTypeDescriptionTransferCache == null) {
            indexTypeDescriptionTransferCache = new IndexTypeDescriptionTransferCache();
        }

        return indexTypeDescriptionTransferCache;
    }

    public IndexFieldTransferCache getIndexFieldTransferCache() {
        if(indexFieldTransferCache == null) {
            indexFieldTransferCache = new IndexFieldTransferCache();
        }

        return indexFieldTransferCache;
    }

    public IndexFieldDescriptionTransferCache getIndexFieldDescriptionTransferCache() {
        if(indexFieldDescriptionTransferCache == null) {
            indexFieldDescriptionTransferCache = new IndexFieldDescriptionTransferCache();
        }

        return indexFieldDescriptionTransferCache;
    }

    public IndexTransferCache getIndexTransferCache() {
        if(indexTransferCache == null) {
            indexTransferCache = new IndexTransferCache();
        }

        return indexTransferCache;
    }

    public IndexDescriptionTransferCache getIndexDescriptionTransferCache() {
        if(indexDescriptionTransferCache == null) {
            indexDescriptionTransferCache = new IndexDescriptionTransferCache();
        }

        return indexDescriptionTransferCache;
    }

}
