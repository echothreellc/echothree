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

package com.echothree.model.control.campaign.server.transfer;

import com.echothree.model.control.campaign.common.transfer.CampaignDescriptionTransfer;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.data.campaign.server.entity.CampaignDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CampaignDescriptionTransferCache
        extends BaseCampaignDescriptionTransferCache<CampaignDescription, CampaignDescriptionTransfer> {
    
    /** Creates a new instance of CampaignDescriptionTransferCache */
    public CampaignDescriptionTransferCache(UserVisit userVisit, CampaignControl campaignControl) {
        super(userVisit, campaignControl);
    }
    
    public CampaignDescriptionTransfer getCampaignDescriptionTransfer(CampaignDescription campaignDescription) {
        var campaignDescriptionTransfer = get(campaignDescription);
        
        if(campaignDescriptionTransfer == null) {
            var campaignTransfer = campaignControl.getCampaignTransfer(userVisit, campaignDescription.getCampaign());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, campaignDescription.getLanguage());
            
            campaignDescriptionTransfer = new CampaignDescriptionTransfer(languageTransfer, campaignTransfer, campaignDescription.getDescription());
            put(userVisit, campaignDescription, campaignDescriptionTransfer);
        }
        return campaignDescriptionTransfer;
    }
    
}
