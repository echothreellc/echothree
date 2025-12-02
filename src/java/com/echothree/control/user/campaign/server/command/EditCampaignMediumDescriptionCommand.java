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

import com.echothree.control.user.campaign.common.edit.CampaignEditFactory;
import com.echothree.control.user.campaign.common.edit.CampaignMediumDescriptionEdit;
import com.echothree.control.user.campaign.common.form.EditCampaignMediumDescriptionForm;
import com.echothree.control.user.campaign.common.result.CampaignResultFactory;
import com.echothree.control.user.campaign.common.result.EditCampaignMediumDescriptionResult;
import com.echothree.control.user.campaign.common.spec.CampaignMediumDescriptionSpec;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.campaign.server.entity.CampaignMedium;
import com.echothree.model.data.campaign.server.entity.CampaignMediumDescription;
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
public class EditCampaignMediumDescriptionCommand
        extends BaseAbstractEditCommand<CampaignMediumDescriptionSpec, CampaignMediumDescriptionEdit, EditCampaignMediumDescriptionResult, CampaignMediumDescription, CampaignMedium> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CampaignMedium.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CampaignMediumName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCampaignMediumDescriptionCommand */
    public EditCampaignMediumDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCampaignMediumDescriptionResult getResult() {
        return CampaignResultFactory.getEditCampaignMediumDescriptionResult();
    }

    @Override
    public CampaignMediumDescriptionEdit getEdit() {
        return CampaignEditFactory.getCampaignMediumDescriptionEdit();
    }

    @Override
    public CampaignMediumDescription getEntity(EditCampaignMediumDescriptionResult result) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        CampaignMediumDescription campaignMediumDescription = null;
        var campaignMediumName = spec.getCampaignMediumName();
        var campaignMedium = campaignControl.getCampaignMediumByName(campaignMediumName);

        if(campaignMedium != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    campaignMediumDescription = campaignControl.getCampaignMediumDescription(campaignMedium, language);
                } else { // EditMode.UPDATE
                    campaignMediumDescription = campaignControl.getCampaignMediumDescriptionForUpdate(campaignMedium, language);
                }

                if(campaignMediumDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownCampaignMediumDescription.name(), campaignMediumName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCampaignMediumName.name(), campaignMediumName);
        }

        return campaignMediumDescription;
    }

    @Override
    public CampaignMedium getLockEntity(CampaignMediumDescription campaignMediumDescription) {
        return campaignMediumDescription.getCampaignMedium();
    }

    @Override
    public void fillInResult(EditCampaignMediumDescriptionResult result, CampaignMediumDescription campaignMediumDescription) {
        var campaignControl = Session.getModelController(CampaignControl.class);

        result.setCampaignMediumDescription(campaignControl.getCampaignMediumDescriptionTransfer(getUserVisit(), campaignMediumDescription));
    }

    @Override
    public void doLock(CampaignMediumDescriptionEdit edit, CampaignMediumDescription campaignMediumDescription) {
        edit.setDescription(campaignMediumDescription.getDescription());
    }

    @Override
    public void doUpdate(CampaignMediumDescription campaignMediumDescription) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var campaignMediumDescriptionValue = campaignControl.getCampaignMediumDescriptionValue(campaignMediumDescription);
        campaignMediumDescriptionValue.setDescription(edit.getDescription());

        campaignControl.updateCampaignMediumDescriptionFromValue(campaignMediumDescriptionValue, getPartyPK());
    }
    
}
