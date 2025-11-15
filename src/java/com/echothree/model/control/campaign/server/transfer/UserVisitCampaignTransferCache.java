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

import com.echothree.model.control.campaign.common.transfer.UserVisitCampaignTransfer;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.campaign.server.entity.UserVisitCampaign;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class UserVisitCampaignTransferCache
        extends BaseCampaignTransferCache<UserVisitCampaign, UserVisitCampaignTransfer> {

    CampaignControl campaignControl = Session.getModelController(CampaignControl.class);
    UserControl userControl = Session.getModelController(UserControl.class);
    
    /** Creates a new instance of UserVisitCampaignTransferCache */
    public UserVisitCampaignTransferCache() {
        super();
    }

    public UserVisitCampaignTransfer getUserVisitCampaignTransfer(UserVisit userVisit, UserVisitCampaign userVisitCampaign) {
        var userVisitCampaignTransfer = get(userVisitCampaign);

        if(userVisitCampaignTransfer == null) {
            var userVisitTransfer = userControl.getUserVisitTransfer(userVisit, userVisit);
            var userVisitCampaignSequence = userVisitCampaign.getUserVisitCampaignSequence();
            var unformattedTime = userVisitCampaign.getTime();
            var time = formatTypicalDateTime(userVisit, unformattedTime);
            var campaign = userVisitCampaign.getCampaign();
            var campaignTransfer = campaign == null ? null : campaignControl.getCampaignTransfer(userVisit, campaign);
            var campaignSource = userVisitCampaign.getCampaignSource();
            var campaignSourceTransfer = campaignSource == null ? null : campaignControl.getCampaignSourceTransfer(userVisit, campaignSource);
            var campaignMedium = userVisitCampaign.getCampaignMedium();
            var campaignMediumTransfer = campaignMedium == null ? null : campaignControl.getCampaignMediumTransfer(userVisit, campaignMedium);
            var campaignTerm = userVisitCampaign.getCampaignTerm();
            var campaignTermTransfer = campaignTerm == null ? null : campaignControl.getCampaignTermTransfer(userVisit, campaignTerm);
            var campaignContent = userVisitCampaign.getCampaignContent();
            var campaignContentTransfer = campaignContent == null ? null : campaignControl.getCampaignContentTransfer(userVisit, campaignContent);

            userVisitCampaignTransfer = new UserVisitCampaignTransfer(userVisitTransfer, userVisitCampaignSequence, unformattedTime, time, campaignTransfer,
                    campaignSourceTransfer, campaignMediumTransfer, campaignTermTransfer, campaignContentTransfer);
            put(userVisit, userVisitCampaign, userVisitCampaignTransfer);
        }

        return userVisitCampaignTransfer;
    }

}
