// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.campaign.common.edit.CampaignEdit;
import com.echothree.control.user.campaign.common.edit.CampaignEditFactory;
import com.echothree.control.user.campaign.common.form.EditCampaignForm;
import com.echothree.control.user.campaign.common.result.CampaignResultFactory;
import com.echothree.control.user.campaign.common.result.EditCampaignResult;
import com.echothree.control.user.campaign.common.spec.CampaignSpec;
import com.echothree.model.control.campaign.server.CampaignControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.campaign.server.entity.Campaign;
import com.echothree.model.data.campaign.server.entity.CampaignDescription;
import com.echothree.model.data.campaign.server.entity.CampaignDetail;
import com.echothree.model.data.campaign.server.value.CampaignDescriptionValue;
import com.echothree.model.data.campaign.server.value.CampaignDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditCampaignCommand
        extends BaseAbstractEditCommand<CampaignSpec, CampaignEdit, EditCampaignResult, Campaign, Campaign> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Campaign.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CampaignName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Value", FieldType.STRING, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditCampaignCommand */
    public EditCampaignCommand(UserVisitPK userVisitPK, EditCampaignForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCampaignResult getResult() {
        return CampaignResultFactory.getEditCampaignResult();
    }

    @Override
    public CampaignEdit getEdit() {
        return CampaignEditFactory.getCampaignEdit();
    }

    @Override
    public Campaign getEntity(EditCampaignResult result) {
        CampaignControl campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        Campaign campaign;
        String campaignName = spec.getCampaignName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            campaign = campaignControl.getCampaignByName(campaignName);
        } else { // EditMode.UPDATE
            campaign = campaignControl.getCampaignByNameForUpdate(campaignName);
        }

        if(campaign == null) {
            addExecutionError(ExecutionErrors.UnknownCampaignName.name(), campaignName);
        }

        return campaign;
    }

    @Override
    public Campaign getLockEntity(Campaign campaign) {
        return campaign;
    }

    @Override
    public void fillInResult(EditCampaignResult result, Campaign campaign) {
        CampaignControl campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);

        result.setCampaign(campaignControl.getCampaignTransfer(getUserVisit(), campaign));
    }

    @Override
    public void doLock(CampaignEdit edit, Campaign campaign) {
        CampaignControl campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        CampaignDescription campaignDescription = campaignControl.getCampaignDescription(campaign, getPreferredLanguage());
        CampaignDetail campaignDetail = campaign.getLastDetail();

        edit.setValue(campaignDetail.getValue());
        edit.setIsDefault(campaignDetail.getIsDefault().toString());
        edit.setSortOrder(campaignDetail.getSortOrder().toString());

        if(campaignDescription != null) {
            edit.setDescription(campaignDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Campaign campaign) {
        CampaignControl campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        String value = edit.getValue();
        Campaign duplicateCampaign = campaignControl.getCampaignByValue(value);

        if(duplicateCampaign != null && !campaign.equals(duplicateCampaign)) {
            addExecutionError(ExecutionErrors.DuplicateCampaignValue.name(), value);
        }
    }

    @Override
    public void doUpdate(Campaign campaign) {
        CampaignControl campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        PartyPK partyPK = getPartyPK();
        CampaignDetailValue campaignDetailValue = campaignControl.getCampaignDetailValueForUpdate(campaign);
        CampaignDescription campaignDescription = campaignControl.getCampaignDescriptionForUpdate(campaign, getPreferredLanguage());
        String description = edit.getDescription();

        campaignDetailValue.setValue(edit.getValue());
        campaignDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        campaignDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        campaignControl.updateCampaignFromValue(campaignDetailValue, partyPK);

        if(campaignDescription == null && description != null) {
            campaignControl.createCampaignDescription(campaign, getPreferredLanguage(), description, partyPK);
        } else {
            if(campaignDescription != null && description == null) {
                campaignControl.deleteCampaignDescription(campaignDescription, partyPK);
            } else {
                if(campaignDescription != null && description != null) {
                    CampaignDescriptionValue campaignDescriptionValue = campaignControl.getCampaignDescriptionValue(campaignDescription);

                    campaignDescriptionValue.setDescription(description);
                    campaignControl.updateCampaignDescriptionFromValue(campaignDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
