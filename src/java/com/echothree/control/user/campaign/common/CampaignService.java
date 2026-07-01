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
import com.echothree.control.user.campaign.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface CampaignService
        extends CampaignForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Campaigns
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateCampaignResult> createCampaign(UserVisitPK userVisitPK, CreateCampaignForm form);
    
    CommandResult<GetCampaignChoicesResult> getCampaignChoices(UserVisitPK userVisitPK, GetCampaignChoicesForm form);
    
    CommandResult<GetCampaignResult> getCampaign(UserVisitPK userVisitPK, GetCampaignForm form);
    
    CommandResult<GetCampaignsResult> getCampaigns(UserVisitPK userVisitPK, GetCampaignsForm form);
    
    CommandResult<VoidResult> setDefaultCampaign(UserVisitPK userVisitPK, SetDefaultCampaignForm form);
    
    CommandResult<GetCampaignStatusChoicesResult> getCampaignStatusChoices(UserVisitPK userVisitPK, GetCampaignStatusChoicesForm form);
    
    CommandResult<VoidResult> setCampaignStatus(UserVisitPK userVisitPK, SetCampaignStatusForm form);
    
    CommandResult<EditCampaignResult> editCampaign(UserVisitPK userVisitPK, EditCampaignForm form);
    
    CommandResult<VoidResult> deleteCampaign(UserVisitPK userVisitPK, DeleteCampaignForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCampaignDescription(UserVisitPK userVisitPK, CreateCampaignDescriptionForm form);
    
    CommandResult<GetCampaignDescriptionResult> getCampaignDescription(UserVisitPK userVisitPK, GetCampaignDescriptionForm form);
    
    CommandResult<GetCampaignDescriptionsResult> getCampaignDescriptions(UserVisitPK userVisitPK, GetCampaignDescriptionsForm form);
    
    CommandResult<EditCampaignDescriptionResult> editCampaignDescription(UserVisitPK userVisitPK, EditCampaignDescriptionForm form);
    
    CommandResult<VoidResult> deleteCampaignDescription(UserVisitPK userVisitPK, DeleteCampaignDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Sources
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateCampaignSourceResult> createCampaignSource(UserVisitPK userVisitPK, CreateCampaignSourceForm form);
    
    CommandResult<GetCampaignSourceChoicesResult> getCampaignSourceChoices(UserVisitPK userVisitPK, GetCampaignSourceChoicesForm form);
    
    CommandResult<GetCampaignSourceResult> getCampaignSource(UserVisitPK userVisitPK, GetCampaignSourceForm form);
    
    CommandResult<GetCampaignSourcesResult> getCampaignSources(UserVisitPK userVisitPK, GetCampaignSourcesForm form);
    
    CommandResult<VoidResult> setDefaultCampaignSource(UserVisitPK userVisitPK, SetDefaultCampaignSourceForm form);
    
    CommandResult<GetCampaignSourceStatusChoicesResult> getCampaignSourceStatusChoices(UserVisitPK userVisitPK, GetCampaignSourceStatusChoicesForm form);
    
    CommandResult<VoidResult> setCampaignSourceStatus(UserVisitPK userVisitPK, SetCampaignSourceStatusForm form);
    
    CommandResult<EditCampaignSourceResult> editCampaignSource(UserVisitPK userVisitPK, EditCampaignSourceForm form);
    
    CommandResult<VoidResult> deleteCampaignSource(UserVisitPK userVisitPK, DeleteCampaignSourceForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Source Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCampaignSourceDescription(UserVisitPK userVisitPK, CreateCampaignSourceDescriptionForm form);
    
    CommandResult<GetCampaignSourceDescriptionResult> getCampaignSourceDescription(UserVisitPK userVisitPK, GetCampaignSourceDescriptionForm form);
    
    CommandResult<GetCampaignSourceDescriptionsResult> getCampaignSourceDescriptions(UserVisitPK userVisitPK, GetCampaignSourceDescriptionsForm form);
    
    CommandResult<EditCampaignSourceDescriptionResult> editCampaignSourceDescription(UserVisitPK userVisitPK, EditCampaignSourceDescriptionForm form);
    
    CommandResult<VoidResult> deleteCampaignSourceDescription(UserVisitPK userVisitPK, DeleteCampaignSourceDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Mediums
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateCampaignMediumResult> createCampaignMedium(UserVisitPK userVisitPK, CreateCampaignMediumForm form);
    
    CommandResult<GetCampaignMediumChoicesResult> getCampaignMediumChoices(UserVisitPK userVisitPK, GetCampaignMediumChoicesForm form);
    
    CommandResult<GetCampaignMediumResult> getCampaignMedium(UserVisitPK userVisitPK, GetCampaignMediumForm form);
    
    CommandResult<GetCampaignMediumsResult> getCampaignMediums(UserVisitPK userVisitPK, GetCampaignMediumsForm form);
    
    CommandResult<VoidResult> setDefaultCampaignMedium(UserVisitPK userVisitPK, SetDefaultCampaignMediumForm form);
    
    CommandResult<GetCampaignMediumStatusChoicesResult> getCampaignMediumStatusChoices(UserVisitPK userVisitPK, GetCampaignMediumStatusChoicesForm form);
    
    CommandResult<VoidResult> setCampaignMediumStatus(UserVisitPK userVisitPK, SetCampaignMediumStatusForm form);
    
    CommandResult<EditCampaignMediumResult> editCampaignMedium(UserVisitPK userVisitPK, EditCampaignMediumForm form);
    
    CommandResult<VoidResult> deleteCampaignMedium(UserVisitPK userVisitPK, DeleteCampaignMediumForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Medium Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCampaignMediumDescription(UserVisitPK userVisitPK, CreateCampaignMediumDescriptionForm form);
    
    CommandResult<GetCampaignMediumDescriptionResult> getCampaignMediumDescription(UserVisitPK userVisitPK, GetCampaignMediumDescriptionForm form);
    
    CommandResult<GetCampaignMediumDescriptionsResult> getCampaignMediumDescriptions(UserVisitPK userVisitPK, GetCampaignMediumDescriptionsForm form);
    
    CommandResult<EditCampaignMediumDescriptionResult> editCampaignMediumDescription(UserVisitPK userVisitPK, EditCampaignMediumDescriptionForm form);
    
    CommandResult<VoidResult> deleteCampaignMediumDescription(UserVisitPK userVisitPK, DeleteCampaignMediumDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Terms
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateCampaignTermResult> createCampaignTerm(UserVisitPK userVisitPK, CreateCampaignTermForm form);
    
    CommandResult<GetCampaignTermChoicesResult> getCampaignTermChoices(UserVisitPK userVisitPK, GetCampaignTermChoicesForm form);
    
    CommandResult<GetCampaignTermResult> getCampaignTerm(UserVisitPK userVisitPK, GetCampaignTermForm form);
    
    CommandResult<GetCampaignTermsResult> getCampaignTerms(UserVisitPK userVisitPK, GetCampaignTermsForm form);
    
    CommandResult<VoidResult> setDefaultCampaignTerm(UserVisitPK userVisitPK, SetDefaultCampaignTermForm form);
    
    CommandResult<GetCampaignTermStatusChoicesResult> getCampaignTermStatusChoices(UserVisitPK userVisitPK, GetCampaignTermStatusChoicesForm form);
    
    CommandResult<VoidResult> setCampaignTermStatus(UserVisitPK userVisitPK, SetCampaignTermStatusForm form);
    
    CommandResult<EditCampaignTermResult> editCampaignTerm(UserVisitPK userVisitPK, EditCampaignTermForm form);
    
    CommandResult<VoidResult> deleteCampaignTerm(UserVisitPK userVisitPK, DeleteCampaignTermForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Term Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCampaignTermDescription(UserVisitPK userVisitPK, CreateCampaignTermDescriptionForm form);
    
    CommandResult<GetCampaignTermDescriptionResult> getCampaignTermDescription(UserVisitPK userVisitPK, GetCampaignTermDescriptionForm form);
    
    CommandResult<GetCampaignTermDescriptionsResult> getCampaignTermDescriptions(UserVisitPK userVisitPK, GetCampaignTermDescriptionsForm form);
    
    CommandResult<EditCampaignTermDescriptionResult> editCampaignTermDescription(UserVisitPK userVisitPK, EditCampaignTermDescriptionForm form);
    
    CommandResult<VoidResult> deleteCampaignTermDescription(UserVisitPK userVisitPK, DeleteCampaignTermDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Contents
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateCampaignContentResult> createCampaignContent(UserVisitPK userVisitPK, CreateCampaignContentForm form);
    
    CommandResult<GetCampaignContentChoicesResult> getCampaignContentChoices(UserVisitPK userVisitPK, GetCampaignContentChoicesForm form);
    
    CommandResult<GetCampaignContentResult> getCampaignContent(UserVisitPK userVisitPK, GetCampaignContentForm form);
    
    CommandResult<GetCampaignContentsResult> getCampaignContents(UserVisitPK userVisitPK, GetCampaignContentsForm form);
    
    CommandResult<VoidResult> setDefaultCampaignContent(UserVisitPK userVisitPK, SetDefaultCampaignContentForm form);
    
    CommandResult<GetCampaignContentStatusChoicesResult> getCampaignContentStatusChoices(UserVisitPK userVisitPK, GetCampaignContentStatusChoicesForm form);
    
    CommandResult<VoidResult> setCampaignContentStatus(UserVisitPK userVisitPK, SetCampaignContentStatusForm form);
    
    CommandResult<EditCampaignContentResult> editCampaignContent(UserVisitPK userVisitPK, EditCampaignContentForm form);
    
    CommandResult<VoidResult> deleteCampaignContent(UserVisitPK userVisitPK, DeleteCampaignContentForm form);
    
    // --------------------------------------------------------------------------------
    //   Campaign Content Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCampaignContentDescription(UserVisitPK userVisitPK, CreateCampaignContentDescriptionForm form);
    
    CommandResult<GetCampaignContentDescriptionResult> getCampaignContentDescription(UserVisitPK userVisitPK, GetCampaignContentDescriptionForm form);
    
    CommandResult<GetCampaignContentDescriptionsResult> getCampaignContentDescriptions(UserVisitPK userVisitPK, GetCampaignContentDescriptionsForm form);
    
    CommandResult<EditCampaignContentDescriptionResult> editCampaignContentDescription(UserVisitPK userVisitPK, EditCampaignContentDescriptionForm form);
    
    CommandResult<VoidResult> deleteCampaignContentDescription(UserVisitPK userVisitPK, DeleteCampaignContentDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   User Visit Tracks
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUserVisitCampaign(UserVisitPK userVisitPK, CreateUserVisitCampaignForm form);
    
}
