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

package com.echothree.control.user.campaign.common;

import com.echothree.control.user.campaign.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface CampaignService
        extends CampaignForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Campaigns
    // --------------------------------------------------------------------------------
    
    CommandResult createCampaign(UserVisitPK userVisitPK, CreateCampaignForm form);
    
    CommandResult getCampaignChoices(UserVisitPK userVisitPK, GetCampaignChoicesForm form);
    
    CommandResult getCampaign(UserVisitPK userVisitPK, GetCampaignForm form);
    
    CommandResult getCampaigns(UserVisitPK userVisitPK, GetCampaignsForm form);
    
    CommandResult setDefaultCampaign(UserVisitPK userVisitPK, SetDefaultCampaignForm form);
    
    CommandResult getCampaignStatusChoices(UserVisitPK userVisitPK, GetCampaignStatusChoicesForm form);
    
    CommandResult setCampaignStatus(UserVisitPK userVisitPK, SetCampaignStatusForm form);
    
    CommandResult editCampaign(UserVisitPK userVisitPK, EditCampaignForm form);
    
    CommandResult deleteCampaign(UserVisitPK userVisitPK, DeleteCampaignForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createCampaignDescription(UserVisitPK userVisitPK, CreateCampaignDescriptionForm form);
    
    CommandResult getCampaignDescription(UserVisitPK userVisitPK, GetCampaignDescriptionForm form);
    
    CommandResult getCampaignDescriptions(UserVisitPK userVisitPK, GetCampaignDescriptionsForm form);
    
    CommandResult editCampaignDescription(UserVisitPK userVisitPK, EditCampaignDescriptionForm form);
    
    CommandResult deleteCampaignDescription(UserVisitPK userVisitPK, DeleteCampaignDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Sources
    // --------------------------------------------------------------------------------
    
    CommandResult createCampaignSource(UserVisitPK userVisitPK, CreateCampaignSourceForm form);
    
    CommandResult getCampaignSourceChoices(UserVisitPK userVisitPK, GetCampaignSourceChoicesForm form);
    
    CommandResult getCampaignSource(UserVisitPK userVisitPK, GetCampaignSourceForm form);
    
    CommandResult getCampaignSources(UserVisitPK userVisitPK, GetCampaignSourcesForm form);
    
    CommandResult setDefaultCampaignSource(UserVisitPK userVisitPK, SetDefaultCampaignSourceForm form);
    
    CommandResult getCampaignSourceStatusChoices(UserVisitPK userVisitPK, GetCampaignSourceStatusChoicesForm form);
    
    CommandResult setCampaignSourceStatus(UserVisitPK userVisitPK, SetCampaignSourceStatusForm form);
    
    CommandResult editCampaignSource(UserVisitPK userVisitPK, EditCampaignSourceForm form);
    
    CommandResult deleteCampaignSource(UserVisitPK userVisitPK, DeleteCampaignSourceForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Source Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createCampaignSourceDescription(UserVisitPK userVisitPK, CreateCampaignSourceDescriptionForm form);
    
    CommandResult getCampaignSourceDescription(UserVisitPK userVisitPK, GetCampaignSourceDescriptionForm form);
    
    CommandResult getCampaignSourceDescriptions(UserVisitPK userVisitPK, GetCampaignSourceDescriptionsForm form);
    
    CommandResult editCampaignSourceDescription(UserVisitPK userVisitPK, EditCampaignSourceDescriptionForm form);
    
    CommandResult deleteCampaignSourceDescription(UserVisitPK userVisitPK, DeleteCampaignSourceDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Mediums
    // --------------------------------------------------------------------------------
    
    CommandResult createCampaignMedium(UserVisitPK userVisitPK, CreateCampaignMediumForm form);
    
    CommandResult getCampaignMediumChoices(UserVisitPK userVisitPK, GetCampaignMediumChoicesForm form);
    
    CommandResult getCampaignMedium(UserVisitPK userVisitPK, GetCampaignMediumForm form);
    
    CommandResult getCampaignMediums(UserVisitPK userVisitPK, GetCampaignMediumsForm form);
    
    CommandResult setDefaultCampaignMedium(UserVisitPK userVisitPK, SetDefaultCampaignMediumForm form);
    
    CommandResult getCampaignMediumStatusChoices(UserVisitPK userVisitPK, GetCampaignMediumStatusChoicesForm form);
    
    CommandResult setCampaignMediumStatus(UserVisitPK userVisitPK, SetCampaignMediumStatusForm form);
    
    CommandResult editCampaignMedium(UserVisitPK userVisitPK, EditCampaignMediumForm form);
    
    CommandResult deleteCampaignMedium(UserVisitPK userVisitPK, DeleteCampaignMediumForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Medium Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createCampaignMediumDescription(UserVisitPK userVisitPK, CreateCampaignMediumDescriptionForm form);
    
    CommandResult getCampaignMediumDescription(UserVisitPK userVisitPK, GetCampaignMediumDescriptionForm form);
    
    CommandResult getCampaignMediumDescriptions(UserVisitPK userVisitPK, GetCampaignMediumDescriptionsForm form);
    
    CommandResult editCampaignMediumDescription(UserVisitPK userVisitPK, EditCampaignMediumDescriptionForm form);
    
    CommandResult deleteCampaignMediumDescription(UserVisitPK userVisitPK, DeleteCampaignMediumDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Terms
    // --------------------------------------------------------------------------------
    
    CommandResult createCampaignTerm(UserVisitPK userVisitPK, CreateCampaignTermForm form);
    
    CommandResult getCampaignTermChoices(UserVisitPK userVisitPK, GetCampaignTermChoicesForm form);
    
    CommandResult getCampaignTerm(UserVisitPK userVisitPK, GetCampaignTermForm form);
    
    CommandResult getCampaignTerms(UserVisitPK userVisitPK, GetCampaignTermsForm form);
    
    CommandResult setDefaultCampaignTerm(UserVisitPK userVisitPK, SetDefaultCampaignTermForm form);
    
    CommandResult getCampaignTermStatusChoices(UserVisitPK userVisitPK, GetCampaignTermStatusChoicesForm form);
    
    CommandResult setCampaignTermStatus(UserVisitPK userVisitPK, SetCampaignTermStatusForm form);
    
    CommandResult editCampaignTerm(UserVisitPK userVisitPK, EditCampaignTermForm form);
    
    CommandResult deleteCampaignTerm(UserVisitPK userVisitPK, DeleteCampaignTermForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Term Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createCampaignTermDescription(UserVisitPK userVisitPK, CreateCampaignTermDescriptionForm form);
    
    CommandResult getCampaignTermDescription(UserVisitPK userVisitPK, GetCampaignTermDescriptionForm form);
    
    CommandResult getCampaignTermDescriptions(UserVisitPK userVisitPK, GetCampaignTermDescriptionsForm form);
    
    CommandResult editCampaignTermDescription(UserVisitPK userVisitPK, EditCampaignTermDescriptionForm form);
    
    CommandResult deleteCampaignTermDescription(UserVisitPK userVisitPK, DeleteCampaignTermDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Contents
    // --------------------------------------------------------------------------------
    
    CommandResult createCampaignContent(UserVisitPK userVisitPK, CreateCampaignContentForm form);
    
    CommandResult getCampaignContentChoices(UserVisitPK userVisitPK, GetCampaignContentChoicesForm form);
    
    CommandResult getCampaignContent(UserVisitPK userVisitPK, GetCampaignContentForm form);
    
    CommandResult getCampaignContents(UserVisitPK userVisitPK, GetCampaignContentsForm form);
    
    CommandResult setDefaultCampaignContent(UserVisitPK userVisitPK, SetDefaultCampaignContentForm form);
    
    CommandResult getCampaignContentStatusChoices(UserVisitPK userVisitPK, GetCampaignContentStatusChoicesForm form);
    
    CommandResult setCampaignContentStatus(UserVisitPK userVisitPK, SetCampaignContentStatusForm form);
    
    CommandResult editCampaignContent(UserVisitPK userVisitPK, EditCampaignContentForm form);
    
    CommandResult deleteCampaignContent(UserVisitPK userVisitPK, DeleteCampaignContentForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Content Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createCampaignContentDescription(UserVisitPK userVisitPK, CreateCampaignContentDescriptionForm form);
    
    CommandResult getCampaignContentDescription(UserVisitPK userVisitPK, GetCampaignContentDescriptionForm form);
    
    CommandResult getCampaignContentDescriptions(UserVisitPK userVisitPK, GetCampaignContentDescriptionsForm form);
    
    CommandResult editCampaignContentDescription(UserVisitPK userVisitPK, EditCampaignContentDescriptionForm form);
    
    CommandResult deleteCampaignContentDescription(UserVisitPK userVisitPK, DeleteCampaignContentDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   User Visit Tracks
    // --------------------------------------------------------------------------------
    
    CommandResult createUserVisitCampaign(UserVisitPK userVisitPK, CreateUserVisitCampaignForm form);
    
}
