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

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.graphql.server.graphql.TimeObject;
import com.echothree.model.data.campaign.server.entity.UserVisitCampaign;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("user visit campaign object")
@GraphQLName("UserVisitCampaign")
public class UserVisitCampaignObject
        extends BaseObject {

    private final UserVisitCampaign userVisitCampaign; // Always Present

    public UserVisitCampaignObject(UserVisitCampaign userVisitCampaign) {
        this.userVisitCampaign = userVisitCampaign;
    }

    @GraphQLField
    @GraphQLDescription("user visit campaign sequence")
    @GraphQLNonNull
    public int getUserVisitCampaignSequence() {
        return userVisitCampaign.getUserVisitCampaignSequence();
    }

    @GraphQLField
    @GraphQLDescription("time")
    @GraphQLNonNull
    public TimeObject getTime(final DataFetchingEnvironment env) {
        return new TimeObject(userVisitCampaign.getTime());
    }

    @GraphQLField
    @GraphQLDescription("campaign")
    public CampaignObject getCampaign(final DataFetchingEnvironment env) {
        var campaign = userVisitCampaign.getCampaign();
        return campaign == null ? null :
                CampaignSecurityUtils.getHasCampaignAccess(env) ? new CampaignObject(userVisitCampaign.getCampaign()) : null;
    }

    @GraphQLField
    @GraphQLDescription("campaign source")
    public CampaignSourceObject getCampaignSource(final DataFetchingEnvironment env) {
        var campaignSource = userVisitCampaign.getCampaignSource();
        return campaignSource == null ? null :
                CampaignSecurityUtils.getHasCampaignSourceAccess(env) ? new CampaignSourceObject(userVisitCampaign.getCampaignSource()) : null;
    }

    @GraphQLField
    @GraphQLDescription("campaign medium")
    public CampaignMediumObject getCampaignMedium(final DataFetchingEnvironment env) {
        var campaignMedium = userVisitCampaign.getCampaignMedium();
        return campaignMedium == null ? null :
                CampaignSecurityUtils.getHasCampaignMediumAccess(env) ? new CampaignMediumObject(userVisitCampaign.getCampaignMedium()) : null;
    }

    @GraphQLField
    @GraphQLDescription("campaign content")
    public CampaignTermObject getCampaignTerm(final DataFetchingEnvironment env) {
        var campaignTerm = userVisitCampaign.getCampaignTerm();
        return campaignTerm == null ? null :
                CampaignSecurityUtils.getHasCampaignTermAccess(env) ? new CampaignTermObject(userVisitCampaign.getCampaignTerm()) : null;
    }

    @GraphQLField
    @GraphQLDescription("campaign content")
    public CampaignContentObject getCampaignContent(final DataFetchingEnvironment env) {
        var campaignContent = userVisitCampaign.getCampaignContent();
        return campaignContent == null ? null :
                CampaignSecurityUtils.getHasCampaignContentAccess(env) ? new CampaignContentObject(userVisitCampaign.getCampaignContent()) : null;
    }

}
