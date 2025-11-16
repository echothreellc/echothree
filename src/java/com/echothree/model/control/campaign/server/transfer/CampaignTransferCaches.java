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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class CampaignTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    CampaignTransferCache campaignTransferCache;
    
    @Inject
    CampaignDescriptionTransferCache campaignDescriptionTransferCache;
    
    @Inject
    CampaignSourceTransferCache campaignSourceTransferCache;
    
    @Inject
    CampaignSourceDescriptionTransferCache campaignSourceDescriptionTransferCache;
    
    @Inject
    CampaignMediumTransferCache campaignMediumTransferCache;
    
    @Inject
    CampaignMediumDescriptionTransferCache campaignMediumDescriptionTransferCache;
    
    @Inject
    CampaignTermTransferCache campaignTermTransferCache;
    
    @Inject
    CampaignTermDescriptionTransferCache campaignTermDescriptionTransferCache;
    
    @Inject
    CampaignContentTransferCache campaignContentTransferCache;
    
    @Inject
    CampaignContentDescriptionTransferCache campaignContentDescriptionTransferCache;
    
    @Inject
    UserVisitCampaignTransferCache userVisitCampaignTransferCache;

    /** Creates a new instance of CampaignTransferCaches */
    protected CampaignTransferCaches() {
        super();
    }
    
    public CampaignTransferCache getCampaignTransferCache() {
        return campaignTransferCache;
    }

    public CampaignDescriptionTransferCache getCampaignDescriptionTransferCache() {
        return campaignDescriptionTransferCache;
    }

    public CampaignSourceTransferCache getCampaignSourceTransferCache() {
        return campaignSourceTransferCache;
    }

    public CampaignSourceDescriptionTransferCache getCampaignSourceDescriptionTransferCache() {
        return campaignSourceDescriptionTransferCache;
    }

    public CampaignMediumTransferCache getCampaignMediumTransferCache() {
        return campaignMediumTransferCache;
    }

    public CampaignMediumDescriptionTransferCache getCampaignMediumDescriptionTransferCache() {
        return campaignMediumDescriptionTransferCache;
    }

    public CampaignTermTransferCache getCampaignTermTransferCache() {
        return campaignTermTransferCache;
    }

    public CampaignTermDescriptionTransferCache getCampaignTermDescriptionTransferCache() {
        return campaignTermDescriptionTransferCache;
    }

    public CampaignContentTransferCache getCampaignContentTransferCache() {
        return campaignContentTransferCache;
    }

    public CampaignContentDescriptionTransferCache getCampaignContentDescriptionTransferCache() {
        return campaignContentDescriptionTransferCache;
    }

    public UserVisitCampaignTransferCache getUserVisitCampaignTransferCache() {
        return userVisitCampaignTransferCache;
    }

}
