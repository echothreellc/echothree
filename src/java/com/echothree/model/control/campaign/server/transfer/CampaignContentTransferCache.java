// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.campaign.common.transfer.CampaignContentTransfer;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.campaign.common.workflow.CampaignContentStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.campaign.server.entity.CampaignContent;
import com.echothree.model.data.campaign.server.entity.CampaignContentDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class CampaignContentTransferCache
        extends BaseCampaignTransferCache<CampaignContent, CampaignContentTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of CampaignContentTransferCache */
    public CampaignContentTransferCache(UserVisit userVisit, CampaignControl campaignControl) {
        super(userVisit, campaignControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(CampaignOptions.CampaignContentIncludeKey));
            setIncludeGuid(options.contains(CampaignOptions.CampaignContentIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public CampaignContentTransfer getCampaignContentTransfer(CampaignContent campaignContent) {
        CampaignContentTransfer campaignContentTransfer = get(campaignContent);

        if(campaignContentTransfer == null) {
            CampaignContentDetail campaignContentDetail = campaignContent.getLastDetail();
            String campaignContentName = campaignContentDetail.getCampaignContentName();
            String valueSha1Hash = campaignContentDetail.getValueSha1Hash();
            String value = campaignContentDetail.getValue();
            Boolean isDefault = campaignContentDetail.getIsDefault();
            Integer sortOrder = campaignContentDetail.getSortOrder();
            String description = campaignControl.getBestCampaignContentDescription(campaignContent, getLanguage());

            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(campaignContent.getPrimaryKey());
            WorkflowEntityStatusTransfer campaignContentStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    CampaignContentStatusConstants.Workflow_CAMPAIGN_CONTENT_STATUS, entityInstance);
            
            campaignContentTransfer = new CampaignContentTransfer(campaignContentName, valueSha1Hash, value, isDefault, sortOrder, description,
                    campaignContentStatusTransfer);
            put(campaignContent, campaignContentTransfer);
        }

        return campaignContentTransfer;
    }

}
