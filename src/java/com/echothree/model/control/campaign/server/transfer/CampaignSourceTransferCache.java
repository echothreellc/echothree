// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.campaign.common.transfer.CampaignSourceTransfer;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.campaign.common.workflow.CampaignSourceStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.campaign.server.entity.CampaignSource;
import com.echothree.model.data.campaign.server.entity.CampaignSourceDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class CampaignSourceTransferCache
        extends BaseCampaignTransferCache<CampaignSource, CampaignSourceTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of CampaignSourceTransferCache */
    public CampaignSourceTransferCache(UserVisit userVisit, CampaignControl campaignControl) {
        super(userVisit, campaignControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(CampaignOptions.CampaignSourceIncludeKey));
            setIncludeGuid(options.contains(CampaignOptions.CampaignSourceIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public CampaignSourceTransfer getCampaignSourceTransfer(CampaignSource campaignSource) {
        CampaignSourceTransfer campaignSourceTransfer = get(campaignSource);

        if(campaignSourceTransfer == null) {
            CampaignSourceDetail campaignSourceDetail = campaignSource.getLastDetail();
            String campaignSourceName = campaignSourceDetail.getCampaignSourceName();
            String valueSha1Hash = campaignSourceDetail.getValueSha1Hash();
            String value = campaignSourceDetail.getValue();
            Boolean isDefault = campaignSourceDetail.getIsDefault();
            Integer sortOrder = campaignSourceDetail.getSortOrder();
            String description = campaignControl.getBestCampaignSourceDescription(campaignSource, getLanguage());

            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(campaignSource.getPrimaryKey());
            WorkflowEntityStatusTransfer campaignSourceStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    CampaignSourceStatusConstants.Workflow_CAMPAIGN_SOURCE_STATUS, entityInstance);
            
            campaignSourceTransfer = new CampaignSourceTransfer(campaignSourceName, valueSha1Hash, value, isDefault, sortOrder, description,
                    campaignSourceStatusTransfer);
            put(campaignSource, campaignSourceTransfer);
        }

        return campaignSourceTransfer;
    }

}
