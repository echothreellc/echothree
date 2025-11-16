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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ClubTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    ClubItemTransferCache clubItemTransferCache;
    
    @Inject
    ClubItemTypeTransferCache clubItemTypeTransferCache;
    
    @Inject
    ClubTransferCache clubTransferCache;
    
    @Inject
    ClubDescriptionTransferCache clubDescriptionTransferCache;

    /** Creates a new instance of ClubTransferCaches */
    protected ClubTransferCaches() {
        super();
    }
    
    public ClubItemTransferCache getClubItemTransferCache() {
        return clubItemTransferCache;
    }
    
    public ClubItemTypeTransferCache getClubItemTypeTransferCache() {
        return clubItemTypeTransferCache;
    }
    
    public ClubTransferCache getClubTransferCache() {
        return clubTransferCache;
    }
    
    public ClubDescriptionTransferCache getClubDescriptionTransferCache() {
        return clubDescriptionTransferCache;
    }
    
}
