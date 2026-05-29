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

import com.echothree.control.user.campaign.common.edit.CampaignEdit;
import com.echothree.control.user.campaign.common.edit.CampaignEditFactory;
import com.echothree.control.user.campaign.common.result.CampaignResultFactory;
import com.echothree.control.user.campaign.common.result.EditCampaignResult;
import com.echothree.control.user.campaign.common.spec.CampaignUniversalSpec;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.campaign.server.logic.CampaignLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.campaign.server.entity.Campaign;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditCampaignCommand
        extends BaseAbstractEditCommand<CampaignUniversalSpec, CampaignEdit, EditCampaignResult, Campaign, Campaign> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Campaign.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CampaignName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Value", FieldType.STRING, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    @Inject
    CampaignControl campaignControl;

    @Inject
    CampaignLogic campaignLogic;

    /** Creates a new instance of EditCampaignCommand */
    public EditCampaignCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        return campaignLogic.getCampaignByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public Campaign getLockEntity(Campaign campaign) {
        return campaign;
    }

    @Override
    public void fillInResult(EditCampaignResult result, Campaign campaign) {
        result.setCampaign(campaignControl.getCampaignTransfer(getUserVisit(), campaign));
    }

    @Override
    public void doLock(CampaignEdit edit, Campaign campaign) {
        var campaignDescription = campaignControl.getCampaignDescription(campaign, getPreferredLanguage());
        var campaignDetail = campaign.getLastDetail();

        edit.setValue(campaignDetail.getValue());
        edit.setIsDefault(campaignDetail.getIsDefault().toString());
        edit.setSortOrder(campaignDetail.getSortOrder().toString());

        if(campaignDescription != null) {
            edit.setDescription(campaignDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Campaign campaign) {
        var value = edit.getValue();
        var duplicateCampaign = campaignControl.getCampaignByValue(value);

        if(duplicateCampaign != null && !campaign.equals(duplicateCampaign)) {
            addExecutionError(ExecutionErrors.DuplicateCampaignValue.name(), value);
        }
    }

    @Override
    public void doUpdate(Campaign campaign) {
        var partyPK = getPartyPK();
        var campaignDetailValue = campaignControl.getCampaignDetailValueForUpdate(campaign);
        var campaignDescription = campaignControl.getCampaignDescriptionForUpdate(campaign, getPreferredLanguage());
        var description = edit.getDescription();

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
                    var campaignDescriptionValue = campaignControl.getCampaignDescriptionValue(campaignDescription);

                    campaignDescriptionValue.setDescription(description);
                    campaignControl.updateCampaignDescriptionFromValue(campaignDescriptionValue, partyPK);
                }
            }
        }
    }

}
