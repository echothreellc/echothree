// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.campaign.common.workflow.CampaignStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.campaign.server.entity.Campaign;
import com.echothree.model.data.campaign.server.entity.CampaignDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class CampaignTransferCache
        extends BaseCampaignTransferCache<Campaign, CampaignTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of CampaignTransferCache */
    public CampaignTransferCache(UserVisit userVisit, CampaignControl campaignControl) {
        super(userVisit, campaignControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(CampaignOptions.CampaignIncludeKey));
            setIncludeGuid(options.contains(CampaignOptions.CampaignIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public CampaignTransfer getCampaignTransfer(Campaign campaign) {
        CampaignTransfer campaignTransfer = get(campaign);

        if(campaignTransfer == null) {
            CampaignDetail campaignDetail = campaign.getLastDetail();
            String campaignName = campaignDetail.getCampaignName();
            String valueSha1Hash = campaignDetail.getValueSha1Hash();
            String value = campaignDetail.getValue();
            Boolean isDefault = campaignDetail.getIsDefault();
            Integer sortOrder = campaignDetail.getSortOrder();
            String description = campaignControl.getBestCampaignDescription(campaign, getLanguage());

            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(campaign.getPrimaryKey());
            WorkflowEntityStatusTransfer campaignStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    CampaignStatusConstants.Workflow_CAMPAIGN_STATUS, entityInstance);
            
            campaignTransfer = new CampaignTransfer(campaignName, valueSha1Hash, value, isDefault, sortOrder, description,
                    campaignStatusTransfer);
            put(campaign, campaignTransfer);
        }

        return campaignTransfer;
    }

}
