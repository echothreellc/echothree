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

package com.echothree.model.control.club.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;

public class ClubTransferCaches
        extends BaseTransferCaches {
    
    protected ClubItemTransferCache clubItemTransferCache;
    protected ClubItemTypeTransferCache clubItemTypeTransferCache;
    protected ClubTransferCache clubTransferCache;
    protected ClubDescriptionTransferCache clubDescriptionTransferCache;
    
    /** Creates a new instance of ClubTransferCaches */
    public ClubTransferCaches() {
        super();
    }
    
    public ClubItemTransferCache getClubItemTransferCache() {
        if(clubItemTransferCache == null)
            clubItemTransferCache = CDI.current().select(ClubItemTransferCache.class).get();
        
        return clubItemTransferCache;
    }
    
    public ClubItemTypeTransferCache getClubItemTypeTransferCache() {
        if(clubItemTypeTransferCache == null)
            clubItemTypeTransferCache = CDI.current().select(ClubItemTypeTransferCache.class).get();
        
        return clubItemTypeTransferCache;
    }
    
    public ClubTransferCache getClubTransferCache() {
        if(clubTransferCache == null)
            clubTransferCache = CDI.current().select(ClubTransferCache.class).get();
        
        return clubTransferCache;
    }
    
    public ClubDescriptionTransferCache getClubDescriptionTransferCache() {
        if(clubDescriptionTransferCache == null)
            clubDescriptionTransferCache = CDI.current().select(ClubDescriptionTransferCache.class).get();
        
        return clubDescriptionTransferCache;
    }
    
}
