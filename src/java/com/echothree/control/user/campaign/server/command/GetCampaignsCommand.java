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

package com.echothree.control.user.campaign.server.command;

import com.echothree.control.user.campaign.common.form.GetCampaignsForm;
import com.echothree.control.user.campaign.common.result.CampaignResultFactory;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.campaign.server.entity.Campaign;
import com.echothree.model.data.campaign.server.factory.CampaignFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCampaignsCommand
        extends BasePaginatedMultipleEntitiesCommand<Campaign, GetCampaignsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Campaign.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    @Inject
    CampaignControl campaignControl;

    /** Creates a new instance of GetCampaignsCommand */
    public GetCampaignsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected void handleForm() {
        // No form fields to handle
    }

    @Override
    protected Long getTotalEntities() {
        return campaignControl.countCampaigns();
    }

    @Override
    protected Collection<Campaign> getEntities() {
        return campaignControl.getCampaigns();
    }

    @Override
    protected BaseResult getResult(Collection<Campaign> entities) {
        var result = CampaignResultFactory.getGetCampaignsResult();

        if(entities != null) {
            if(session.hasLimit(CampaignFactory.class)) {
                result.setCampaignCount(getTotalEntities());
            }

            result.setCampaigns(campaignControl.getCampaignTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
