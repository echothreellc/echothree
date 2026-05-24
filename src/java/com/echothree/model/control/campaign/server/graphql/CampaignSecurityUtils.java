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

import com.echothree.control.user.campaign.server.command.GetCampaignCommand;
import com.echothree.control.user.campaign.server.command.GetCampaignContentCommand;
import com.echothree.control.user.campaign.server.command.GetCampaignContentsCommand;
import com.echothree.control.user.campaign.server.command.GetCampaignMediumCommand;
import com.echothree.control.user.campaign.server.command.GetCampaignMediumsCommand;
import com.echothree.control.user.campaign.server.command.GetCampaignSourceCommand;
import com.echothree.control.user.campaign.server.command.GetCampaignSourcesCommand;
import com.echothree.control.user.campaign.server.command.GetCampaignTermCommand;
import com.echothree.control.user.campaign.server.command.GetCampaignTermsCommand;
import com.echothree.control.user.campaign.server.command.GetCampaignsCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentAmountCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentAmountsCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentFixedAmountCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentFixedAmountsCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentPercentCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentPercentsCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentSourceCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentTypeCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentsCommand;
import com.echothree.control.user.filter.server.command.GetFilterCommand;
import com.echothree.control.user.filter.server.command.GetFilterEntranceStepCommand;
import com.echothree.control.user.filter.server.command.GetFilterEntranceStepsCommand;
import com.echothree.control.user.filter.server.command.GetFilterKindCommand;
import com.echothree.control.user.filter.server.command.GetFilterStepCommand;
import com.echothree.control.user.filter.server.command.GetFilterStepDestinationCommand;
import com.echothree.control.user.filter.server.command.GetFilterStepDestinationsCommand;
import com.echothree.control.user.filter.server.command.GetFilterStepElementCommand;
import com.echothree.control.user.filter.server.command.GetFilterStepElementsCommand;
import com.echothree.control.user.filter.server.command.GetFilterStepsCommand;
import com.echothree.control.user.filter.server.command.GetFilterTypeCommand;
import com.echothree.control.user.filter.server.command.GetFilterTypesCommand;
import com.echothree.control.user.filter.server.command.GetFiltersCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface CampaignSecurityUtils {

    static boolean getHasCampaignAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetCampaignCommand.class);
    }

    static boolean getHasCampaignsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetCampaignsCommand.class);
    }

    static boolean getHasCampaignSourceAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetCampaignSourceCommand.class);
    }

    static boolean getHasCampaignSourcesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetCampaignSourcesCommand.class);
    }

    static boolean getHasCampaignMediumAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetCampaignMediumCommand.class);
    }

    static boolean getHasCampaignMediumsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetCampaignMediumsCommand.class);
    }

    static boolean getHasCampaignTermAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetCampaignTermCommand.class);
    }

    static boolean getHasCampaignTermsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetCampaignTermsCommand.class);
    }

    static boolean getHasCampaignContentAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetCampaignContentCommand.class);
    }

    static boolean getHasCampaignContentsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetCampaignContentsCommand.class);
    }

}
