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

import com.echothree.model.control.campaign.common.CampaignOptions;
import com.echothree.model.control.campaign.common.transfer.CampaignTransfer;
import com.echothree.model.control.campaign.common.workflow.CampaignStatusConstants;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.campaign.server.entity.Campaign;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CampaignTransferCache
        extends BaseCampaignTransferCache<Campaign, CampaignTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of CampaignTransferCache */
    public CampaignTransferCache(UserVisit userVisit, CampaignControl campaignControl) {
        super(userVisit, campaignControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(CampaignOptions.CampaignIncludeUuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public CampaignTransfer getCampaignTransfer(Campaign campaign) {
        var campaignTransfer = get(campaign);

        if(campaignTransfer == null) {
            var campaignDetail = campaign.getLastDetail();
            var campaignName = campaignDetail.getCampaignName();
            var valueSha1Hash = campaignDetail.getValueSha1Hash();
            var value = campaignDetail.getValue();
            var isDefault = campaignDetail.getIsDefault();
            var sortOrder = campaignDetail.getSortOrder();
            var description = campaignControl.getBestCampaignDescription(campaign, getLanguage(userVisit));

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaign.getPrimaryKey());
            var campaignStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    CampaignStatusConstants.Workflow_CAMPAIGN_STATUS, entityInstance);
            
            campaignTransfer = new CampaignTransfer(campaignName, valueSha1Hash, value, isDefault, sortOrder, description,
                    campaignStatusTransfer);
            put(userVisit, campaign, campaignTransfer);
        }

        return campaignTransfer;
    }

}
