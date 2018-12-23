// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.campaign.server.CampaignControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.campaign.common.workflow.CampaignMediumStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.campaign.server.entity.CampaignMedium;
import com.echothree.model.data.campaign.server.entity.CampaignMediumDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class CampaignMediumTransferCache
        extends BaseCampaignTransferCache<CampaignMedium, CampaignMediumTransfer> {

    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of CampaignMediumTransferCache */
    public CampaignMediumTransferCache(UserVisit userVisit, CampaignControl campaignControl) {
        super(userVisit, campaignControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(CampaignOptions.CampaignMediumIncludeKey));
            setIncludeGuid(options.contains(CampaignOptions.CampaignMediumIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public CampaignMediumTransfer getCampaignMediumTransfer(CampaignMedium campaignMedium) {
        CampaignMediumTransfer campaignMediumTransfer = get(campaignMedium);

        if(campaignMediumTransfer == null) {
            CampaignMediumDetail campaignMediumDetail = campaignMedium.getLastDetail();
            String campaignMediumName = campaignMediumDetail.getCampaignMediumName();
            String valueSha1Hash = campaignMediumDetail.getValueSha1Hash();
            String value = campaignMediumDetail.getValue();
            Boolean isDefault = campaignMediumDetail.getIsDefault();
            Integer sortOrder = campaignMediumDetail.getSortOrder();
            String description = campaignControl.getBestCampaignMediumDescription(campaignMedium, getLanguage());

            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(campaignMedium.getPrimaryKey());
            WorkflowEntityStatusTransfer campaignMediumStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    CampaignMediumStatusConstants.Workflow_CAMPAIGN_MEDIUM_STATUS, entityInstance);
            
            campaignMediumTransfer = new CampaignMediumTransfer(campaignMediumName, valueSha1Hash, value, isDefault, sortOrder, description,
                    campaignMediumStatusTransfer);
            put(campaignMedium, campaignMediumTransfer);
        }

        return campaignMediumTransfer;
    }

}
