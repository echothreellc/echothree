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

import com.echothree.model.control.campaign.common.workflow.CampaignContentStatusConstants;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.campaign.server.entity.CampaignContent;
import com.echothree.model.data.campaign.server.entity.CampaignContentDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("campaign content object")
@GraphQLName("CampaignContent")
public class CampaignContentObject
        extends BaseEntityInstanceObject {
    
    private final CampaignContent campaignContent; // Always Present
    
    public CampaignContentObject(CampaignContent campaignContent) {
        super(campaignContent.getPrimaryKey());
        
        this.campaignContent = campaignContent;
    }

    private CampaignContentDetail campaignContentDetail; // Optional, use getCampaignContentDetail()
    
    private CampaignContentDetail getCampaignContentDetail() {
        if(campaignContentDetail == null) {
            campaignContentDetail = campaignContent.getLastDetail();
        }
        
        return campaignContentDetail;
    }

    @GraphQLField
    @GraphQLDescription("campaign content name")
    @GraphQLNonNull
    public String getCampaignContentName() {
        return getCampaignContentDetail().getCampaignContentName();
    }

    @GraphQLField
    @GraphQLDescription("value SHA1 hash")
    @GraphQLNonNull
    public String getValueSha1Hash() {
        return getCampaignContentDetail().getValueSha1Hash();
    }

    @GraphQLField
    @GraphQLDescription("value")
    @GraphQLNonNull
    public String getValue() {
        return getCampaignContentDetail().getValue();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getCampaignContentDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getCampaignContentDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return campaignControl.getBestCampaignContentDescription(campaignContent, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("campaign content status")
    public WorkflowEntityStatusObject getCampaignContentStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, CampaignContentStatusConstants.Workflow_CAMPAIGN_CONTENT_STATUS);
    }

}
