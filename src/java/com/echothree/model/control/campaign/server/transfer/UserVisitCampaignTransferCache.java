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

package com.echothree.model.control.campaign.server.transfer;

import com.echothree.model.control.campaign.common.transfer.CampaignContentTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignMediumTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignSourceTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignTermTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignTransfer;
import com.echothree.model.control.campaign.common.transfer.UserVisitCampaignTransfer;
import com.echothree.model.control.campaign.server.CampaignControl;
import com.echothree.model.control.user.common.transfer.UserVisitTransfer;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.campaign.server.entity.Campaign;
import com.echothree.model.data.campaign.server.entity.CampaignContent;
import com.echothree.model.data.campaign.server.entity.CampaignMedium;
import com.echothree.model.data.campaign.server.entity.CampaignSource;
import com.echothree.model.data.campaign.server.entity.CampaignTerm;
import com.echothree.model.data.campaign.server.entity.UserVisitCampaign;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class UserVisitCampaignTransferCache
        extends BaseCampaignTransferCache<UserVisitCampaign, UserVisitCampaignTransfer> {

    UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
    
    /** Creates a new instance of UserVisitCampaignTransferCache */
    public UserVisitCampaignTransferCache(UserVisit userVisit, CampaignControl campaignControl) {
        super(userVisit, campaignControl);
    }

    public UserVisitCampaignTransfer getUserVisitCampaignTransfer(UserVisitCampaign userVisitCampaign) {
        UserVisitCampaignTransfer userVisitCampaignTransfer = get(userVisitCampaign);

        if(userVisitCampaignTransfer == null) {
            UserVisitTransfer userVisitTransfer = userControl.getUserVisitTransfer(userVisit, userVisit);
            Integer userVisitCampaignSequence = userVisitCampaign.getUserVisitCampaignSequence();
            Long unformattedTime = userVisitCampaign.getTime();
            String time = formatTypicalDateTime(unformattedTime);
            Campaign campaign = userVisitCampaign.getCampaign();
            CampaignTransfer campaignTransfer = campaign == null ? null : campaignControl.getCampaignTransfer(userVisit, campaign);
            CampaignSource campaignSource = userVisitCampaign.getCampaignSource();
            CampaignSourceTransfer campaignSourceTransfer = campaignSource == null ? null : campaignControl.getCampaignSourceTransfer(userVisit, campaignSource);
            CampaignMedium campaignMedium = userVisitCampaign.getCampaignMedium();
            CampaignMediumTransfer campaignMediumTransfer = campaignMedium == null ? null : campaignControl.getCampaignMediumTransfer(userVisit, campaignMedium);
            CampaignTerm campaignTerm = userVisitCampaign.getCampaignTerm();
            CampaignTermTransfer campaignTermTransfer = campaignTerm == null ? null : campaignControl.getCampaignTermTransfer(userVisit, campaignTerm);
            CampaignContent campaignContent = userVisitCampaign.getCampaignContent();
            CampaignContentTransfer campaignContentTransfer = campaignContent == null ? null : campaignControl.getCampaignContentTransfer(userVisit, campaignContent);

            userVisitCampaignTransfer = new UserVisitCampaignTransfer(userVisitTransfer, userVisitCampaignSequence, unformattedTime, time, campaignTransfer,
                    campaignSourceTransfer, campaignMediumTransfer, campaignTermTransfer, campaignContentTransfer);
            put(userVisitCampaign, userVisitCampaignTransfer);
        }

        return userVisitCampaignTransfer;
    }

}
