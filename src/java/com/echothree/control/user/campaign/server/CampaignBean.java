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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateCampaignCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignChoices(UserVisitPK userVisitPK, GetCampaignChoicesForm form) {
        return CDI.current().select(GetCampaignChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaign(UserVisitPK userVisitPK, GetCampaignForm form) {
        return CDI.current().select(GetCampaignCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaigns(UserVisitPK userVisitPK, GetCampaignsForm form) {
        return CDI.current().select(GetCampaignsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCampaign(UserVisitPK userVisitPK, SetDefaultCampaignForm form) {
        return CDI.current().select(SetDefaultCampaignCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignStatusChoices(UserVisitPK userVisitPK, GetCampaignStatusChoicesForm form) {
        return CDI.current().select(GetCampaignStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCampaignStatus(UserVisitPK userVisitPK, SetCampaignStatusForm form) {
        return CDI.current().select(SetCampaignStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaign(UserVisitPK userVisitPK, EditCampaignForm form) {
        return CDI.current().select(EditCampaignCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaign(UserVisitPK userVisitPK, DeleteCampaignForm form) {
        return CDI.current().select(DeleteCampaignCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignDescription(UserVisitPK userVisitPK, CreateCampaignDescriptionForm form) {
        return CDI.current().select(CreateCampaignDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignDescription(UserVisitPK userVisitPK, GetCampaignDescriptionForm form) {
        return CDI.current().select(GetCampaignDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignDescriptions(UserVisitPK userVisitPK, GetCampaignDescriptionsForm form) {
        return CDI.current().select(GetCampaignDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignDescription(UserVisitPK userVisitPK, EditCampaignDescriptionForm form) {
        return CDI.current().select(EditCampaignDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignDescription(UserVisitPK userVisitPK, DeleteCampaignDescriptionForm form) {
        return CDI.current().select(DeleteCampaignDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Sources
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignSource(UserVisitPK userVisitPK, CreateCampaignSourceForm form) {
        return CDI.current().select(CreateCampaignSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignSourceChoices(UserVisitPK userVisitPK, GetCampaignSourceChoicesForm form) {
        return CDI.current().select(GetCampaignSourceChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignSource(UserVisitPK userVisitPK, GetCampaignSourceForm form) {
        return CDI.current().select(GetCampaignSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignSources(UserVisitPK userVisitPK, GetCampaignSourcesForm form) {
        return CDI.current().select(GetCampaignSourcesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCampaignSource(UserVisitPK userVisitPK, SetDefaultCampaignSourceForm form) {
        return CDI.current().select(SetDefaultCampaignSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignSourceStatusChoices(UserVisitPK userVisitPK, GetCampaignSourceStatusChoicesForm form) {
        return CDI.current().select(GetCampaignSourceStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCampaignSourceStatus(UserVisitPK userVisitPK, SetCampaignSourceStatusForm form) {
        return CDI.current().select(SetCampaignSourceStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignSource(UserVisitPK userVisitPK, EditCampaignSourceForm form) {
        return CDI.current().select(EditCampaignSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignSource(UserVisitPK userVisitPK, DeleteCampaignSourceForm form) {
        return CDI.current().select(DeleteCampaignSourceCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Source Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignSourceDescription(UserVisitPK userVisitPK, CreateCampaignSourceDescriptionForm form) {
        return CDI.current().select(CreateCampaignSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignSourceDescription(UserVisitPK userVisitPK, GetCampaignSourceDescriptionForm form) {
        return CDI.current().select(GetCampaignSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignSourceDescriptions(UserVisitPK userVisitPK, GetCampaignSourceDescriptionsForm form) {
        return CDI.current().select(GetCampaignSourceDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignSourceDescription(UserVisitPK userVisitPK, EditCampaignSourceDescriptionForm form) {
        return CDI.current().select(EditCampaignSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignSourceDescription(UserVisitPK userVisitPK, DeleteCampaignSourceDescriptionForm form) {
        return CDI.current().select(DeleteCampaignSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Mediums
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignMedium(UserVisitPK userVisitPK, CreateCampaignMediumForm form) {
        return CDI.current().select(CreateCampaignMediumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignMediumChoices(UserVisitPK userVisitPK, GetCampaignMediumChoicesForm form) {
        return CDI.current().select(GetCampaignMediumChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignMedium(UserVisitPK userVisitPK, GetCampaignMediumForm form) {
        return CDI.current().select(GetCampaignMediumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignMediums(UserVisitPK userVisitPK, GetCampaignMediumsForm form) {
        return CDI.current().select(GetCampaignMediumsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCampaignMedium(UserVisitPK userVisitPK, SetDefaultCampaignMediumForm form) {
        return CDI.current().select(SetDefaultCampaignMediumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignMediumStatusChoices(UserVisitPK userVisitPK, GetCampaignMediumStatusChoicesForm form) {
        return CDI.current().select(GetCampaignMediumStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCampaignMediumStatus(UserVisitPK userVisitPK, SetCampaignMediumStatusForm form) {
        return CDI.current().select(SetCampaignMediumStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignMedium(UserVisitPK userVisitPK, EditCampaignMediumForm form) {
        return CDI.current().select(EditCampaignMediumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignMedium(UserVisitPK userVisitPK, DeleteCampaignMediumForm form) {
        return CDI.current().select(DeleteCampaignMediumCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Medium Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignMediumDescription(UserVisitPK userVisitPK, CreateCampaignMediumDescriptionForm form) {
        return CDI.current().select(CreateCampaignMediumDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignMediumDescription(UserVisitPK userVisitPK, GetCampaignMediumDescriptionForm form) {
        return CDI.current().select(GetCampaignMediumDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignMediumDescriptions(UserVisitPK userVisitPK, GetCampaignMediumDescriptionsForm form) {
        return CDI.current().select(GetCampaignMediumDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignMediumDescription(UserVisitPK userVisitPK, EditCampaignMediumDescriptionForm form) {
        return CDI.current().select(EditCampaignMediumDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignMediumDescription(UserVisitPK userVisitPK, DeleteCampaignMediumDescriptionForm form) {
        return CDI.current().select(DeleteCampaignMediumDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Terms
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignTerm(UserVisitPK userVisitPK, CreateCampaignTermForm form) {
        return CDI.current().select(CreateCampaignTermCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignTermChoices(UserVisitPK userVisitPK, GetCampaignTermChoicesForm form) {
        return CDI.current().select(GetCampaignTermChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignTerm(UserVisitPK userVisitPK, GetCampaignTermForm form) {
        return CDI.current().select(GetCampaignTermCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignTerms(UserVisitPK userVisitPK, GetCampaignTermsForm form) {
        return CDI.current().select(GetCampaignTermsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCampaignTerm(UserVisitPK userVisitPK, SetDefaultCampaignTermForm form) {
        return CDI.current().select(SetDefaultCampaignTermCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignTermStatusChoices(UserVisitPK userVisitPK, GetCampaignTermStatusChoicesForm form) {
        return CDI.current().select(GetCampaignTermStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCampaignTermStatus(UserVisitPK userVisitPK, SetCampaignTermStatusForm form) {
        return CDI.current().select(SetCampaignTermStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignTerm(UserVisitPK userVisitPK, EditCampaignTermForm form) {
        return CDI.current().select(EditCampaignTermCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignTerm(UserVisitPK userVisitPK, DeleteCampaignTermForm form) {
        return CDI.current().select(DeleteCampaignTermCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Term Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignTermDescription(UserVisitPK userVisitPK, CreateCampaignTermDescriptionForm form) {
        return CDI.current().select(CreateCampaignTermDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignTermDescription(UserVisitPK userVisitPK, GetCampaignTermDescriptionForm form) {
        return CDI.current().select(GetCampaignTermDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignTermDescriptions(UserVisitPK userVisitPK, GetCampaignTermDescriptionsForm form) {
        return CDI.current().select(GetCampaignTermDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignTermDescription(UserVisitPK userVisitPK, EditCampaignTermDescriptionForm form) {
        return CDI.current().select(EditCampaignTermDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignTermDescription(UserVisitPK userVisitPK, DeleteCampaignTermDescriptionForm form) {
        return CDI.current().select(DeleteCampaignTermDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Contents
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignContent(UserVisitPK userVisitPK, CreateCampaignContentForm form) {
        return CDI.current().select(CreateCampaignContentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignContentChoices(UserVisitPK userVisitPK, GetCampaignContentChoicesForm form) {
        return CDI.current().select(GetCampaignContentChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignContent(UserVisitPK userVisitPK, GetCampaignContentForm form) {
        return CDI.current().select(GetCampaignContentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignContents(UserVisitPK userVisitPK, GetCampaignContentsForm form) {
        return CDI.current().select(GetCampaignContentsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCampaignContent(UserVisitPK userVisitPK, SetDefaultCampaignContentForm form) {
        return CDI.current().select(SetDefaultCampaignContentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignContentStatusChoices(UserVisitPK userVisitPK, GetCampaignContentStatusChoicesForm form) {
        return CDI.current().select(GetCampaignContentStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCampaignContentStatus(UserVisitPK userVisitPK, SetCampaignContentStatusForm form) {
        return CDI.current().select(SetCampaignContentStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignContent(UserVisitPK userVisitPK, EditCampaignContentForm form) {
        return CDI.current().select(EditCampaignContentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignContent(UserVisitPK userVisitPK, DeleteCampaignContentForm form) {
        return CDI.current().select(DeleteCampaignContentCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Content Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCampaignContentDescription(UserVisitPK userVisitPK, CreateCampaignContentDescriptionForm form) {
        return CDI.current().select(CreateCampaignContentDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignContentDescription(UserVisitPK userVisitPK, GetCampaignContentDescriptionForm form) {
        return CDI.current().select(GetCampaignContentDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCampaignContentDescriptions(UserVisitPK userVisitPK, GetCampaignContentDescriptionsForm form) {
        return CDI.current().select(GetCampaignContentDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCampaignContentDescription(UserVisitPK userVisitPK, EditCampaignContentDescriptionForm form) {
        return CDI.current().select(EditCampaignContentDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCampaignContentDescription(UserVisitPK userVisitPK, DeleteCampaignContentDescriptionForm form) {
        return CDI.current().select(DeleteCampaignContentDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   User Visit Campaigns
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserVisitCampaign(UserVisitPK userVisitPK, CreateUserVisitCampaignForm form) {
        return CDI.current().select(CreateUserVisitCampaignCommand.class).get().run(userVisitPK, form);
    }
    
}
