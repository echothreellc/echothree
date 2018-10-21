// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.campaign.remote.transfer;

import com.echothree.model.control.user.remote.transfer.UserVisitTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class UserVisitCampaignTransfer
        extends BaseTransfer {
    
    private UserVisitTransfer userVisit;
    private Integer userVisitCampaignSequence;
    private Long unformattedTime;
    private String time;
    private CampaignTransfer campaign;
    private CampaignSourceTransfer campaignSource;
    private CampaignMediumTransfer campaignMedium;
    private CampaignTermTransfer campaignTerm;
    private CampaignContentTransfer campaignContent;
    
    /** Creates a new instance of UserVisitCampaignTransfer */
    public UserVisitCampaignTransfer(UserVisitTransfer userVisit, Integer userVisitCampaignSequence, Long unformattedTime, String time,
            CampaignTransfer campaign, CampaignSourceTransfer campaignSource, CampaignMediumTransfer campaignMedium, CampaignTermTransfer campaignTerm,
            CampaignContentTransfer campaignContent) {
        this.userVisit = userVisit;
        this.userVisitCampaignSequence = userVisitCampaignSequence;
        this.unformattedTime = unformattedTime;
        this.time = time;
        this.campaign = campaign;
        this.campaignSource = campaignSource;
        this.campaignMedium = campaignMedium;
        this.campaignTerm = campaignTerm;
        this.campaignContent = campaignContent;
    }

    /**
     * @return the userVisit
     */
    public UserVisitTransfer getUserVisit() {
        return userVisit;
    }

    /**
     * @param userVisit the userVisit to set
     */
    public void setUserVisit(UserVisitTransfer userVisit) {
        this.userVisit = userVisit;
    }

    /**
     * @return the userVisitCampaignSequence
     */
    public Integer getUserVisitCampaignSequence() {
        return userVisitCampaignSequence;
    }

    /**
     * @param userVisitCampaignSequence the userVisitCampaignSequence to set
     */
    public void setUserVisitCampaignSequence(Integer userVisitCampaignSequence) {
        this.userVisitCampaignSequence = userVisitCampaignSequence;
    }

    /**
     * @return the unformattedTime
     */
    public Long getUnformattedTime() {
        return unformattedTime;
    }

    /**
     * @param unformattedTime the unformattedTime to set
     */
    public void setUnformattedTime(Long unformattedTime) {
        this.unformattedTime = unformattedTime;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the campaign
     */
    public CampaignTransfer getCampaign() {
        return campaign;
    }

    /**
     * @param campaign the campaign to set
     */
    public void setCampaign(CampaignTransfer campaign) {
        this.campaign = campaign;
    }

    /**
     * @return the campaignSource
     */
    public CampaignSourceTransfer getCampaignSource() {
        return campaignSource;
    }

    /**
     * @param campaignSource the campaignSource to set
     */
    public void setCampaignSource(CampaignSourceTransfer campaignSource) {
        this.campaignSource = campaignSource;
    }

    /**
     * @return the campaignMedium
     */
    public CampaignMediumTransfer getCampaignMedium() {
        return campaignMedium;
    }

    /**
     * @param campaignMedium the campaignMedium to set
     */
    public void setCampaignMedium(CampaignMediumTransfer campaignMedium) {
        this.campaignMedium = campaignMedium;
    }

    /**
     * @return the campaignTerm
     */
    public CampaignTermTransfer getCampaignTerm() {
        return campaignTerm;
    }

    /**
     * @param campaignTerm the campaignTerm to set
     */
    public void setCampaignTerm(CampaignTermTransfer campaignTerm) {
        this.campaignTerm = campaignTerm;
    }

    /**
     * @return the campaignContent
     */
    public CampaignContentTransfer getCampaignContent() {
        return campaignContent;
    }

    /**
     * @param campaignContent the campaignContent to set
     */
    public void setCampaignContent(CampaignContentTransfer campaignContent) {
        this.campaignContent = campaignContent;
    }
    
}
