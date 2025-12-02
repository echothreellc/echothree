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

package com.echothree.control.user.campaign.server.command;

import com.echothree.control.user.campaign.common.edit.CampaignDescriptionEdit;
import com.echothree.control.user.campaign.common.edit.CampaignEditFactory;
import com.echothree.control.user.campaign.common.form.EditCampaignDescriptionForm;
import com.echothree.control.user.campaign.common.result.CampaignResultFactory;
import com.echothree.control.user.campaign.common.result.EditCampaignDescriptionResult;
import com.echothree.control.user.campaign.common.spec.CampaignDescriptionSpec;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.campaign.server.entity.Campaign;
import com.echothree.model.data.campaign.server.entity.CampaignDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditCampaignDescriptionCommand
        extends BaseAbstractEditCommand<CampaignDescriptionSpec, CampaignDescriptionEdit, EditCampaignDescriptionResult, CampaignDescription, Campaign> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Campaign.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CampaignName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCampaignDescriptionCommand */
    public EditCampaignDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCampaignDescriptionResult getResult() {
        return CampaignResultFactory.getEditCampaignDescriptionResult();
    }

    @Override
    public CampaignDescriptionEdit getEdit() {
        return CampaignEditFactory.getCampaignDescriptionEdit();
    }

    @Override
    public CampaignDescription getEntity(EditCampaignDescriptionResult result) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        CampaignDescription campaignDescription = null;
        var campaignName = spec.getCampaignName();
        var campaign = campaignControl.getCampaignByName(campaignName);

        if(campaign != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    campaignDescription = campaignControl.getCampaignDescription(campaign, language);
                } else { // EditMode.UPDATE
                    campaignDescription = campaignControl.getCampaignDescriptionForUpdate(campaign, language);
                }

                if(campaignDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownCampaignDescription.name(), campaignName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCampaignName.name(), campaignName);
        }

        return campaignDescription;
    }

    @Override
    public Campaign getLockEntity(CampaignDescription campaignDescription) {
        return campaignDescription.getCampaign();
    }

    @Override
    public void fillInResult(EditCampaignDescriptionResult result, CampaignDescription campaignDescription) {
        var campaignControl = Session.getModelController(CampaignControl.class);

        result.setCampaignDescription(campaignControl.getCampaignDescriptionTransfer(getUserVisit(), campaignDescription));
    }

    @Override
    public void doLock(CampaignDescriptionEdit edit, CampaignDescription campaignDescription) {
        edit.setDescription(campaignDescription.getDescription());
    }

    @Override
    public void doUpdate(CampaignDescription campaignDescription) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var campaignDescriptionValue = campaignControl.getCampaignDescriptionValue(campaignDescription);
        campaignDescriptionValue.setDescription(edit.getDescription());

        campaignControl.updateCampaignDescriptionFromValue(campaignDescriptionValue, getPartyPK());
    }
    
}
