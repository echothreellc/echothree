// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.campaign.server.graphql;

import com.echothree.model.control.campaign.common.workflow.CampaignTermStatusConstants;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.campaign.server.entity.CampaignTerm;
import com.echothree.model.data.campaign.server.entity.CampaignTermDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("campaign term object")
@GraphQLName("CampaignTerm")
public class CampaignTermObject
        extends BaseEntityInstanceObject {

    private final CampaignTerm campaignTerm; // Always Present

    public CampaignTermObject(CampaignTerm campaignTerm) {
        super(campaignTerm.getPrimaryKey());
        
        this.campaignTerm = campaignTerm;
    }

    private CampaignTermDetail campaignTermDetail; // Optional, use getCampaignTermDetail()
    
    private CampaignTermDetail getCampaignTermDetail() {
        if(campaignTermDetail == null) {
            campaignTermDetail = campaignTerm.getLastDetail();
        }
        
        return campaignTermDetail;
    }

    @GraphQLField
    @GraphQLDescription("campaign term name")
    @GraphQLNonNull
    public String getCampaignTermName() {
        return getCampaignTermDetail().getCampaignTermName();
    }

    @GraphQLField
    @GraphQLDescription("value SHA1 hash")
    @GraphQLNonNull
    public String getValueSha1Hash() {
        return getCampaignTermDetail().getValueSha1Hash();
    }

    @GraphQLField
    @GraphQLDescription("value")
    @GraphQLNonNull
    public String getValue() {
        return getCampaignTermDetail().getValue();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getCampaignTermDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getCampaignTermDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return campaignControl.getBestCampaignTermDescription(campaignTerm, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("campaign term status")
    public WorkflowEntityStatusObject getCampaignTermStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, CampaignTermStatusConstants.Workflow_CAMPAIGN_TERM_STATUS);
    }

}
