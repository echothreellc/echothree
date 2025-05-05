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
        return new CreateCampaignCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignChoices(UserVisitPK userVisitPK, GetCampaignChoicesForm form) {
        return new GetCampaignChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaign(UserVisitPK userVisitPK, GetCampaignForm form) {
        return new GetCampaignCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaigns(UserVisitPK userVisitPK, GetCampaignsForm form) {
        return new GetCampaignsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCampaign(UserVisitPK userVisitPK, SetDefaultCampaignForm form) {
        return new SetDefaultCampaignCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignStatusChoices(UserVisitPK userVisitPK, GetCampaignStatusChoicesForm form) {
        return new GetCampaignStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCampaignStatus(UserVisitPK userVisitPK, SetCampaignStatusForm form) {
        return new SetCampaignStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaign(UserVisitPK userVisitPK, EditCampaignForm form) {
        return new EditCampaignCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaign(UserVisitPK userVisitPK, DeleteCampaignForm form) {
        return new DeleteCampaignCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignDescription(UserVisitPK userVisitPK, CreateCampaignDescriptionForm form) {
        return new CreateCampaignDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignDescription(UserVisitPK userVisitPK, GetCampaignDescriptionForm form) {
        return new GetCampaignDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignDescriptions(UserVisitPK userVisitPK, GetCampaignDescriptionsForm form) {
        return new GetCampaignDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignDescription(UserVisitPK userVisitPK, EditCampaignDescriptionForm form) {
        return new EditCampaignDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignDescription(UserVisitPK userVisitPK, DeleteCampaignDescriptionForm form) {
        return new DeleteCampaignDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Sources
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignSource(UserVisitPK userVisitPK, CreateCampaignSourceForm form) {
        return new CreateCampaignSourceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignSourceChoices(UserVisitPK userVisitPK, GetCampaignSourceChoicesForm form) {
        return new GetCampaignSourceChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignSource(UserVisitPK userVisitPK, GetCampaignSourceForm form) {
        return new GetCampaignSourceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignSources(UserVisitPK userVisitPK, GetCampaignSourcesForm form) {
        return new GetCampaignSourcesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCampaignSource(UserVisitPK userVisitPK, SetDefaultCampaignSourceForm form) {
        return new SetDefaultCampaignSourceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignSourceStatusChoices(UserVisitPK userVisitPK, GetCampaignSourceStatusChoicesForm form) {
        return new GetCampaignSourceStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCampaignSourceStatus(UserVisitPK userVisitPK, SetCampaignSourceStatusForm form) {
        return new SetCampaignSourceStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignSource(UserVisitPK userVisitPK, EditCampaignSourceForm form) {
        return new EditCampaignSourceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignSource(UserVisitPK userVisitPK, DeleteCampaignSourceForm form) {
        return new DeleteCampaignSourceCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Source Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignSourceDescription(UserVisitPK userVisitPK, CreateCampaignSourceDescriptionForm form) {
        return new CreateCampaignSourceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignSourceDescription(UserVisitPK userVisitPK, GetCampaignSourceDescriptionForm form) {
        return new GetCampaignSourceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignSourceDescriptions(UserVisitPK userVisitPK, GetCampaignSourceDescriptionsForm form) {
        return new GetCampaignSourceDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignSourceDescription(UserVisitPK userVisitPK, EditCampaignSourceDescriptionForm form) {
        return new EditCampaignSourceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignSourceDescription(UserVisitPK userVisitPK, DeleteCampaignSourceDescriptionForm form) {
        return new DeleteCampaignSourceDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Mediums
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignMedium(UserVisitPK userVisitPK, CreateCampaignMediumForm form) {
        return new CreateCampaignMediumCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignMediumChoices(UserVisitPK userVisitPK, GetCampaignMediumChoicesForm form) {
        return new GetCampaignMediumChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignMedium(UserVisitPK userVisitPK, GetCampaignMediumForm form) {
        return new GetCampaignMediumCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignMediums(UserVisitPK userVisitPK, GetCampaignMediumsForm form) {
        return new GetCampaignMediumsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCampaignMedium(UserVisitPK userVisitPK, SetDefaultCampaignMediumForm form) {
        return new SetDefaultCampaignMediumCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignMediumStatusChoices(UserVisitPK userVisitPK, GetCampaignMediumStatusChoicesForm form) {
        return new GetCampaignMediumStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCampaignMediumStatus(UserVisitPK userVisitPK, SetCampaignMediumStatusForm form) {
        return new SetCampaignMediumStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignMedium(UserVisitPK userVisitPK, EditCampaignMediumForm form) {
        return new EditCampaignMediumCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignMedium(UserVisitPK userVisitPK, DeleteCampaignMediumForm form) {
        return new DeleteCampaignMediumCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Medium Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignMediumDescription(UserVisitPK userVisitPK, CreateCampaignMediumDescriptionForm form) {
        return new CreateCampaignMediumDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignMediumDescription(UserVisitPK userVisitPK, GetCampaignMediumDescriptionForm form) {
        return new GetCampaignMediumDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignMediumDescriptions(UserVisitPK userVisitPK, GetCampaignMediumDescriptionsForm form) {
        return new GetCampaignMediumDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignMediumDescription(UserVisitPK userVisitPK, EditCampaignMediumDescriptionForm form) {
        return new EditCampaignMediumDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignMediumDescription(UserVisitPK userVisitPK, DeleteCampaignMediumDescriptionForm form) {
        return new DeleteCampaignMediumDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Terms
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignTerm(UserVisitPK userVisitPK, CreateCampaignTermForm form) {
        return new CreateCampaignTermCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignTermChoices(UserVisitPK userVisitPK, GetCampaignTermChoicesForm form) {
        return new GetCampaignTermChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignTerm(UserVisitPK userVisitPK, GetCampaignTermForm form) {
        return new GetCampaignTermCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignTerms(UserVisitPK userVisitPK, GetCampaignTermsForm form) {
        return new GetCampaignTermsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCampaignTerm(UserVisitPK userVisitPK, SetDefaultCampaignTermForm form) {
        return new SetDefaultCampaignTermCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignTermStatusChoices(UserVisitPK userVisitPK, GetCampaignTermStatusChoicesForm form) {
        return new GetCampaignTermStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCampaignTermStatus(UserVisitPK userVisitPK, SetCampaignTermStatusForm form) {
        return new SetCampaignTermStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignTerm(UserVisitPK userVisitPK, EditCampaignTermForm form) {
        return new EditCampaignTermCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignTerm(UserVisitPK userVisitPK, DeleteCampaignTermForm form) {
        return new DeleteCampaignTermCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Term Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignTermDescription(UserVisitPK userVisitPK, CreateCampaignTermDescriptionForm form) {
        return new CreateCampaignTermDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignTermDescription(UserVisitPK userVisitPK, GetCampaignTermDescriptionForm form) {
        return new GetCampaignTermDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignTermDescriptions(UserVisitPK userVisitPK, GetCampaignTermDescriptionsForm form) {
        return new GetCampaignTermDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignTermDescription(UserVisitPK userVisitPK, EditCampaignTermDescriptionForm form) {
        return new EditCampaignTermDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignTermDescription(UserVisitPK userVisitPK, DeleteCampaignTermDescriptionForm form) {
        return new DeleteCampaignTermDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Contents
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignContent(UserVisitPK userVisitPK, CreateCampaignContentForm form) {
        return new CreateCampaignContentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignContentChoices(UserVisitPK userVisitPK, GetCampaignContentChoicesForm form) {
        return new GetCampaignContentChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignContent(UserVisitPK userVisitPK, GetCampaignContentForm form) {
        return new GetCampaignContentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignContents(UserVisitPK userVisitPK, GetCampaignContentsForm form) {
        return new GetCampaignContentsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCampaignContent(UserVisitPK userVisitPK, SetDefaultCampaignContentForm form) {
        return new SetDefaultCampaignContentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignContentStatusChoices(UserVisitPK userVisitPK, GetCampaignContentStatusChoicesForm form) {
        return new GetCampaignContentStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCampaignContentStatus(UserVisitPK userVisitPK, SetCampaignContentStatusForm form) {
        return new SetCampaignContentStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignContent(UserVisitPK userVisitPK, EditCampaignContentForm form) {
        return new EditCampaignContentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignContent(UserVisitPK userVisitPK, DeleteCampaignContentForm form) {
        return new DeleteCampaignContentCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Content Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignContentDescription(UserVisitPK userVisitPK, CreateCampaignContentDescriptionForm form) {
        return new CreateCampaignContentDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignContentDescription(UserVisitPK userVisitPK, GetCampaignContentDescriptionForm form) {
        return new GetCampaignContentDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignContentDescriptions(UserVisitPK userVisitPK, GetCampaignContentDescriptionsForm form) {
        return new GetCampaignContentDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignContentDescription(UserVisitPK userVisitPK, EditCampaignContentDescriptionForm form) {
        return new EditCampaignContentDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignContentDescription(UserVisitPK userVisitPK, DeleteCampaignContentDescriptionForm form) {
        return new DeleteCampaignContentDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   User Visit Campaigns
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserVisitCampaign(UserVisitPK userVisitPK, CreateUserVisitCampaignForm form) {
        return new CreateUserVisitCampaignCommand().run(userVisitPK, form);
    }
    
}
