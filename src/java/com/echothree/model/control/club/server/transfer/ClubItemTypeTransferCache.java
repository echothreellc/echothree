// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.club.common.transfer.ClubItemTypeTransfer;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.data.club.server.entity.ClubItemType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ClubItemTypeTransferCache
        extends BaseClubTransferCache<ClubItemType, ClubItemTypeTransfer> {
    
    /** Creates a new instance of ClubItemTypeTransferCache */
    public ClubItemTypeTransferCache(UserVisit userVisit, ClubControl clubControl) {
        super(userVisit, clubControl);
    }
    
    public ClubItemTypeTransfer getClubItemTypeTransfer(ClubItemType clubItemType) {
        ClubItemTypeTransfer clubItemTypeTransfer = get(clubItemType);
        
        if(clubItemTypeTransfer == null) {
            String clubItemTypeName = clubItemType.getClubItemTypeName();
            Boolean isDefault = clubItemType.getIsDefault();
            Integer sortOrder = clubItemType.getSortOrder();
            String description = clubControl.getBestClubItemTypeDescription(clubItemType, getLanguage());
            
            clubItemTypeTransfer = new ClubItemTypeTransfer(clubItemTypeName, isDefault, sortOrder, description);
            put(clubItemType, clubItemTypeTransfer);
        }
        return clubItemTypeTransfer;
    }
    
}
