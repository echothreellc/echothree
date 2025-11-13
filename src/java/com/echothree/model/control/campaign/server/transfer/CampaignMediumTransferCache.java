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
import com.echothree.model.control.campaign.common.transfer.CampaignMediumTransfer;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.campaign.common.workflow.CampaignMediumStatusConstants;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.campaign.server.entity.CampaignMedium;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CampaignMediumTransferCache
        extends BaseCampaignTransferCache<CampaignMedium, CampaignMediumTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of CampaignMediumTransferCache */
    public CampaignMediumTransferCache(CampaignControl campaignControl) {
        super(campaignControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(CampaignOptions.CampaignMediumIncludeUuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public CampaignMediumTransfer getCampaignMediumTransfer(UserVisit userVisit, CampaignMedium campaignMedium) {
        var campaignMediumTransfer = get(campaignMedium);

        if(campaignMediumTransfer == null) {
            var campaignMediumDetail = campaignMedium.getLastDetail();
            var campaignMediumName = campaignMediumDetail.getCampaignMediumName();
            var valueSha1Hash = campaignMediumDetail.getValueSha1Hash();
            var value = campaignMediumDetail.getValue();
            var isDefault = campaignMediumDetail.getIsDefault();
            var sortOrder = campaignMediumDetail.getSortOrder();
            var description = campaignControl.getBestCampaignMediumDescription(campaignMedium, getLanguage(userVisit));

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignMedium.getPrimaryKey());
            var campaignMediumStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    CampaignMediumStatusConstants.Workflow_CAMPAIGN_MEDIUM_STATUS, entityInstance);
            
            campaignMediumTransfer = new CampaignMediumTransfer(campaignMediumName, valueSha1Hash, value, isDefault, sortOrder, description,
                    campaignMediumStatusTransfer);
            put(userVisit, campaignMedium, campaignMediumTransfer);
        }

        return campaignMediumTransfer;
    }

}
