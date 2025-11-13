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

import com.echothree.model.control.campaign.common.transfer.CampaignSourceDescriptionTransfer;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.data.campaign.server.entity.CampaignSourceDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CampaignSourceDescriptionTransferCache
        extends BaseCampaignDescriptionTransferCache<CampaignSourceDescription, CampaignSourceDescriptionTransfer> {
    
    /** Creates a new instance of CampaignSourceDescriptionTransferCache */
    public CampaignSourceDescriptionTransferCache(CampaignControl campaignControl) {
        super(campaignControl);
    }
    
    public CampaignSourceDescriptionTransfer getCampaignSourceDescriptionTransfer(CampaignSourceDescription campaignSourceDescription) {
        var campaignSourceDescriptionTransfer = get(campaignSourceDescription);
        
        if(campaignSourceDescriptionTransfer == null) {
            var campaignSourceTransfer = campaignControl.getCampaignSourceTransfer(userVisit, campaignSourceDescription.getCampaignSource());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, campaignSourceDescription.getLanguage());
            
            campaignSourceDescriptionTransfer = new CampaignSourceDescriptionTransfer(languageTransfer, campaignSourceTransfer, campaignSourceDescription.getDescription());
            put(userVisit, campaignSourceDescription, campaignSourceDescriptionTransfer);
        }
        return campaignSourceDescriptionTransfer;
    }
    
}
