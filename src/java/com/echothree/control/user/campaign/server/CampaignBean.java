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

package com.echothree.control.user.campaign.server;

import com.echothree.control.user.campaign.common.CampaignRemote;
import com.echothree.control.user.campaign.common.form.*;
import com.echothree.control.user.campaign.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class CampaignBean
        extends CampaignFormsImpl
        implements CampaignRemote, CampaignLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "CampaignBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Campaigns
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaign(UserVisitPK userVisitPK, CreateCampaignForm form) {
        return new CreateCampaignCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignChoices(UserVisitPK userVisitPK, GetCampaignChoicesForm form) {
        return new GetCampaignChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaign(UserVisitPK userVisitPK, GetCampaignForm form) {
        return new GetCampaignCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaigns(UserVisitPK userVisitPK, GetCampaignsForm form) {
        return new GetCampaignsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCampaign(UserVisitPK userVisitPK, SetDefaultCampaignForm form) {
        return new SetDefaultCampaignCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignStatusChoices(UserVisitPK userVisitPK, GetCampaignStatusChoicesForm form) {
        return new GetCampaignStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setCampaignStatus(UserVisitPK userVisitPK, SetCampaignStatusForm form) {
        return new SetCampaignStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCampaign(UserVisitPK userVisitPK, EditCampaignForm form) {
        return new EditCampaignCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCampaign(UserVisitPK userVisitPK, DeleteCampaignForm form) {
        return new DeleteCampaignCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignDescription(UserVisitPK userVisitPK, CreateCampaignDescriptionForm form) {
        return new CreateCampaignDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignDescription(UserVisitPK userVisitPK, GetCampaignDescriptionForm form) {
        return new GetCampaignDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignDescriptions(UserVisitPK userVisitPK, GetCampaignDescriptionsForm form) {
        return new GetCampaignDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCampaignDescription(UserVisitPK userVisitPK, EditCampaignDescriptionForm form) {
        return new EditCampaignDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCampaignDescription(UserVisitPK userVisitPK, DeleteCampaignDescriptionForm form) {
        return new DeleteCampaignDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Sources
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignSource(UserVisitPK userVisitPK, CreateCampaignSourceForm form) {
        return new CreateCampaignSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignSourceChoices(UserVisitPK userVisitPK, GetCampaignSourceChoicesForm form) {
        return new GetCampaignSourceChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignSource(UserVisitPK userVisitPK, GetCampaignSourceForm form) {
        return new GetCampaignSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignSources(UserVisitPK userVisitPK, GetCampaignSourcesForm form) {
        return new GetCampaignSourcesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCampaignSource(UserVisitPK userVisitPK, SetDefaultCampaignSourceForm form) {
        return new SetDefaultCampaignSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignSourceStatusChoices(UserVisitPK userVisitPK, GetCampaignSourceStatusChoicesForm form) {
        return new GetCampaignSourceStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setCampaignSourceStatus(UserVisitPK userVisitPK, SetCampaignSourceStatusForm form) {
        return new SetCampaignSourceStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCampaignSource(UserVisitPK userVisitPK, EditCampaignSourceForm form) {
        return new EditCampaignSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCampaignSource(UserVisitPK userVisitPK, DeleteCampaignSourceForm form) {
        return new DeleteCampaignSourceCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Source Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignSourceDescription(UserVisitPK userVisitPK, CreateCampaignSourceDescriptionForm form) {
        return new CreateCampaignSourceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignSourceDescription(UserVisitPK userVisitPK, GetCampaignSourceDescriptionForm form) {
        return new GetCampaignSourceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignSourceDescriptions(UserVisitPK userVisitPK, GetCampaignSourceDescriptionsForm form) {
        return new GetCampaignSourceDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCampaignSourceDescription(UserVisitPK userVisitPK, EditCampaignSourceDescriptionForm form) {
        return new EditCampaignSourceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCampaignSourceDescription(UserVisitPK userVisitPK, DeleteCampaignSourceDescriptionForm form) {
        return new DeleteCampaignSourceDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Mediums
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignMedium(UserVisitPK userVisitPK, CreateCampaignMediumForm form) {
        return new CreateCampaignMediumCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignMediumChoices(UserVisitPK userVisitPK, GetCampaignMediumChoicesForm form) {
        return new GetCampaignMediumChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignMedium(UserVisitPK userVisitPK, GetCampaignMediumForm form) {
        return new GetCampaignMediumCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignMediums(UserVisitPK userVisitPK, GetCampaignMediumsForm form) {
        return new GetCampaignMediumsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCampaignMedium(UserVisitPK userVisitPK, SetDefaultCampaignMediumForm form) {
        return new SetDefaultCampaignMediumCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignMediumStatusChoices(UserVisitPK userVisitPK, GetCampaignMediumStatusChoicesForm form) {
        return new GetCampaignMediumStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setCampaignMediumStatus(UserVisitPK userVisitPK, SetCampaignMediumStatusForm form) {
        return new SetCampaignMediumStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCampaignMedium(UserVisitPK userVisitPK, EditCampaignMediumForm form) {
        return new EditCampaignMediumCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCampaignMedium(UserVisitPK userVisitPK, DeleteCampaignMediumForm form) {
        return new DeleteCampaignMediumCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Medium Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignMediumDescription(UserVisitPK userVisitPK, CreateCampaignMediumDescriptionForm form) {
        return new CreateCampaignMediumDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignMediumDescription(UserVisitPK userVisitPK, GetCampaignMediumDescriptionForm form) {
        return new GetCampaignMediumDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignMediumDescriptions(UserVisitPK userVisitPK, GetCampaignMediumDescriptionsForm form) {
        return new GetCampaignMediumDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCampaignMediumDescription(UserVisitPK userVisitPK, EditCampaignMediumDescriptionForm form) {
        return new EditCampaignMediumDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCampaignMediumDescription(UserVisitPK userVisitPK, DeleteCampaignMediumDescriptionForm form) {
        return new DeleteCampaignMediumDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Terms
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignTerm(UserVisitPK userVisitPK, CreateCampaignTermForm form) {
        return new CreateCampaignTermCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignTermChoices(UserVisitPK userVisitPK, GetCampaignTermChoicesForm form) {
        return new GetCampaignTermChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignTerm(UserVisitPK userVisitPK, GetCampaignTermForm form) {
        return new GetCampaignTermCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignTerms(UserVisitPK userVisitPK, GetCampaignTermsForm form) {
        return new GetCampaignTermsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCampaignTerm(UserVisitPK userVisitPK, SetDefaultCampaignTermForm form) {
        return new SetDefaultCampaignTermCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignTermStatusChoices(UserVisitPK userVisitPK, GetCampaignTermStatusChoicesForm form) {
        return new GetCampaignTermStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setCampaignTermStatus(UserVisitPK userVisitPK, SetCampaignTermStatusForm form) {
        return new SetCampaignTermStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCampaignTerm(UserVisitPK userVisitPK, EditCampaignTermForm form) {
        return new EditCampaignTermCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCampaignTerm(UserVisitPK userVisitPK, DeleteCampaignTermForm form) {
        return new DeleteCampaignTermCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Term Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignTermDescription(UserVisitPK userVisitPK, CreateCampaignTermDescriptionForm form) {
        return new CreateCampaignTermDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignTermDescription(UserVisitPK userVisitPK, GetCampaignTermDescriptionForm form) {
        return new GetCampaignTermDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignTermDescriptions(UserVisitPK userVisitPK, GetCampaignTermDescriptionsForm form) {
        return new GetCampaignTermDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCampaignTermDescription(UserVisitPK userVisitPK, EditCampaignTermDescriptionForm form) {
        return new EditCampaignTermDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCampaignTermDescription(UserVisitPK userVisitPK, DeleteCampaignTermDescriptionForm form) {
        return new DeleteCampaignTermDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Contents
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignContent(UserVisitPK userVisitPK, CreateCampaignContentForm form) {
        return new CreateCampaignContentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignContentChoices(UserVisitPK userVisitPK, GetCampaignContentChoicesForm form) {
        return new GetCampaignContentChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignContent(UserVisitPK userVisitPK, GetCampaignContentForm form) {
        return new GetCampaignContentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignContents(UserVisitPK userVisitPK, GetCampaignContentsForm form) {
        return new GetCampaignContentsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCampaignContent(UserVisitPK userVisitPK, SetDefaultCampaignContentForm form) {
        return new SetDefaultCampaignContentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignContentStatusChoices(UserVisitPK userVisitPK, GetCampaignContentStatusChoicesForm form) {
        return new GetCampaignContentStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setCampaignContentStatus(UserVisitPK userVisitPK, SetCampaignContentStatusForm form) {
        return new SetCampaignContentStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCampaignContent(UserVisitPK userVisitPK, EditCampaignContentForm form) {
        return new EditCampaignContentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCampaignContent(UserVisitPK userVisitPK, DeleteCampaignContentForm form) {
        return new DeleteCampaignContentCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Content Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignContentDescription(UserVisitPK userVisitPK, CreateCampaignContentDescriptionForm form) {
        return new CreateCampaignContentDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignContentDescription(UserVisitPK userVisitPK, GetCampaignContentDescriptionForm form) {
        return new GetCampaignContentDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCampaignContentDescriptions(UserVisitPK userVisitPK, GetCampaignContentDescriptionsForm form) {
        return new GetCampaignContentDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCampaignContentDescription(UserVisitPK userVisitPK, EditCampaignContentDescriptionForm form) {
        return new EditCampaignContentDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCampaignContentDescription(UserVisitPK userVisitPK, DeleteCampaignContentDescriptionForm form) {
        return new DeleteCampaignContentDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   User Visit Tracks
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserVisitCampaign(UserVisitPK userVisitPK, CreateUserVisitCampaignForm form) {
        return new CreateUserVisitCampaignCommand(userVisitPK, form).run();
    }
    
}
