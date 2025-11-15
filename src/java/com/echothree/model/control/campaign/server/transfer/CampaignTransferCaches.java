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

public class CampaignTransferCaches
        extends BaseTransferCaches {
    
    protected CampaignTransferCache campaignTransferCache;
    protected CampaignDescriptionTransferCache campaignDescriptionTransferCache;
    protected CampaignSourceTransferCache campaignSourceTransferCache;
    protected CampaignSourceDescriptionTransferCache campaignSourceDescriptionTransferCache;
    protected CampaignMediumTransferCache campaignMediumTransferCache;
    protected CampaignMediumDescriptionTransferCache campaignMediumDescriptionTransferCache;
    protected CampaignTermTransferCache campaignTermTransferCache;
    protected CampaignTermDescriptionTransferCache campaignTermDescriptionTransferCache;
    protected CampaignContentTransferCache campaignContentTransferCache;
    protected CampaignContentDescriptionTransferCache campaignContentDescriptionTransferCache;
    protected UserVisitCampaignTransferCache userVisitCampaignTransferCache;
    
    /** Creates a new instance of CampaignTransferCaches */
    public CampaignTransferCaches() {
        super();
    }
    
    public CampaignTransferCache getCampaignTransferCache() {
        if(campaignTransferCache == null) {
            campaignTransferCache = new CampaignTransferCache();
        }

        return campaignTransferCache;
    }

    public CampaignDescriptionTransferCache getCampaignDescriptionTransferCache() {
        if(campaignDescriptionTransferCache == null) {
            campaignDescriptionTransferCache = new CampaignDescriptionTransferCache();
        }

        return campaignDescriptionTransferCache;
    }

    public CampaignSourceTransferCache getCampaignSourceTransferCache() {
        if(campaignSourceTransferCache == null) {
            campaignSourceTransferCache = new CampaignSourceTransferCache();
        }

        return campaignSourceTransferCache;
    }

    public CampaignSourceDescriptionTransferCache getCampaignSourceDescriptionTransferCache() {
        if(campaignSourceDescriptionTransferCache == null) {
            campaignSourceDescriptionTransferCache = new CampaignSourceDescriptionTransferCache();
        }

        return campaignSourceDescriptionTransferCache;
    }

    public CampaignMediumTransferCache getCampaignMediumTransferCache() {
        if(campaignMediumTransferCache == null) {
            campaignMediumTransferCache = new CampaignMediumTransferCache();
        }

        return campaignMediumTransferCache;
    }

    public CampaignMediumDescriptionTransferCache getCampaignMediumDescriptionTransferCache() {
        if(campaignMediumDescriptionTransferCache == null) {
            campaignMediumDescriptionTransferCache = new CampaignMediumDescriptionTransferCache();
        }

        return campaignMediumDescriptionTransferCache;
    }

    public CampaignTermTransferCache getCampaignTermTransferCache() {
        if(campaignTermTransferCache == null) {
            campaignTermTransferCache = new CampaignTermTransferCache();
        }

        return campaignTermTransferCache;
    }

    public CampaignTermDescriptionTransferCache getCampaignTermDescriptionTransferCache() {
        if(campaignTermDescriptionTransferCache == null) {
            campaignTermDescriptionTransferCache = new CampaignTermDescriptionTransferCache();
        }

        return campaignTermDescriptionTransferCache;
    }

    public CampaignContentTransferCache getCampaignContentTransferCache() {
        if(campaignContentTransferCache == null) {
            campaignContentTransferCache = new CampaignContentTransferCache();
        }

        return campaignContentTransferCache;
    }

    public CampaignContentDescriptionTransferCache getCampaignContentDescriptionTransferCache() {
        if(campaignContentDescriptionTransferCache == null) {
            campaignContentDescriptionTransferCache = new CampaignContentDescriptionTransferCache();
        }

        return campaignContentDescriptionTransferCache;
    }

    public UserVisitCampaignTransferCache getUserVisitCampaignTransferCache() {
        if(userVisitCampaignTransferCache == null) {
            userVisitCampaignTransferCache = new UserVisitCampaignTransferCache();
        }

        return userVisitCampaignTransferCache;
    }

}
