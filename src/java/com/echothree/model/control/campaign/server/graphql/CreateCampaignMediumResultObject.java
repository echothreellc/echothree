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

import com.echothree.control.user.campaign.common.result.CreateCampaignMediumResult;
import com.echothree.model.control.graphql.server.graphql.MutationResultWithIdObject;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

@GraphQLDescription("create campaign medium result object")
@GraphQLName("CreateCampaignMediumResult")
public class CreateCampaignMediumResultObject
        extends MutationResultWithIdObject {

    String campaignMediumName;

    public void setCreateCampaignMediumResult(final CreateCampaignMediumResult result) {
        setEntityInstanceFromEntityRef(result.getEntityRef());

        this.campaignMediumName = result.getCampaignMediumName();
    }

    @GraphQLField
    @GraphQLDescription("campaign medium name")
    public String getCampaignMediumName() {
        return campaignMediumName;
    }

}
