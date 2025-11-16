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
import com.echothree.model.control.campaign.common.transfer.CampaignSourceTransfer;
import com.echothree.model.control.campaign.common.workflow.CampaignSourceStatusConstants;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.campaign.server.entity.CampaignSource;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CampaignSourceTransferCache
        extends BaseCampaignTransferCache<CampaignSource, CampaignSourceTransfer> {

    CampaignControl campaignControl = Session.getModelController(CampaignControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of CampaignSourceTransferCache */
    protected CampaignSourceTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(CampaignOptions.CampaignSourceIncludeUuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public CampaignSourceTransfer getCampaignSourceTransfer(UserVisit userVisit, CampaignSource campaignSource) {
        var campaignSourceTransfer = get(campaignSource);

        if(campaignSourceTransfer == null) {
            var campaignSourceDetail = campaignSource.getLastDetail();
            var campaignSourceName = campaignSourceDetail.getCampaignSourceName();
            var valueSha1Hash = campaignSourceDetail.getValueSha1Hash();
            var value = campaignSourceDetail.getValue();
            var isDefault = campaignSourceDetail.getIsDefault();
            var sortOrder = campaignSourceDetail.getSortOrder();
            var description = campaignControl.getBestCampaignSourceDescription(campaignSource, getLanguage(userVisit));

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignSource.getPrimaryKey());
            var campaignSourceStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    CampaignSourceStatusConstants.Workflow_CAMPAIGN_SOURCE_STATUS, entityInstance);
            
            campaignSourceTransfer = new CampaignSourceTransfer(campaignSourceName, valueSha1Hash, value, isDefault, sortOrder, description,
                    campaignSourceStatusTransfer);
            put(userVisit, campaignSource, campaignSourceTransfer);
        }

        return campaignSourceTransfer;
    }

}
