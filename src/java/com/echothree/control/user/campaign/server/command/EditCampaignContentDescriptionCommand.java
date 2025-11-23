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

import com.echothree.control.user.campaign.common.edit.CampaignContentDescriptionEdit;
import com.echothree.control.user.campaign.common.edit.CampaignEditFactory;
import com.echothree.control.user.campaign.common.form.EditCampaignContentDescriptionForm;
import com.echothree.control.user.campaign.common.result.CampaignResultFactory;
import com.echothree.control.user.campaign.common.result.EditCampaignContentDescriptionResult;
import com.echothree.control.user.campaign.common.spec.CampaignContentDescriptionSpec;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.campaign.server.entity.CampaignContent;
import com.echothree.model.data.campaign.server.entity.CampaignContentDescription;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditCampaignContentDescriptionCommand
        extends BaseAbstractEditCommand<CampaignContentDescriptionSpec, CampaignContentDescriptionEdit, EditCampaignContentDescriptionResult, CampaignContentDescription, CampaignContent> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CampaignContent.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CampaignContentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCampaignContentDescriptionCommand */
    public EditCampaignContentDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCampaignContentDescriptionResult getResult() {
        return CampaignResultFactory.getEditCampaignContentDescriptionResult();
    }

    @Override
    public CampaignContentDescriptionEdit getEdit() {
        return CampaignEditFactory.getCampaignContentDescriptionEdit();
    }

    @Override
    public CampaignContentDescription getEntity(EditCampaignContentDescriptionResult result) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        CampaignContentDescription campaignContentDescription = null;
        var campaignContentName = spec.getCampaignContentName();
        var campaignContent = campaignControl.getCampaignContentByName(campaignContentName);

        if(campaignContent != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    campaignContentDescription = campaignControl.getCampaignContentDescription(campaignContent, language);
                } else { // EditMode.UPDATE
                    campaignContentDescription = campaignControl.getCampaignContentDescriptionForUpdate(campaignContent, language);
                }

                if(campaignContentDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownCampaignContentDescription.name(), campaignContentName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCampaignContentName.name(), campaignContentName);
        }

        return campaignContentDescription;
    }

    @Override
    public CampaignContent getLockEntity(CampaignContentDescription campaignContentDescription) {
        return campaignContentDescription.getCampaignContent();
    }

    @Override
    public void fillInResult(EditCampaignContentDescriptionResult result, CampaignContentDescription campaignContentDescription) {
        var campaignControl = Session.getModelController(CampaignControl.class);

        result.setCampaignContentDescription(campaignControl.getCampaignContentDescriptionTransfer(getUserVisit(), campaignContentDescription));
    }

    @Override
    public void doLock(CampaignContentDescriptionEdit edit, CampaignContentDescription campaignContentDescription) {
        edit.setDescription(campaignContentDescription.getDescription());
    }

    @Override
    public void doUpdate(CampaignContentDescription campaignContentDescription) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var campaignContentDescriptionValue = campaignControl.getCampaignContentDescriptionValue(campaignContentDescription);
        campaignContentDescriptionValue.setDescription(edit.getDescription());

        campaignControl.updateCampaignContentDescriptionFromValue(campaignContentDescriptionValue, getPartyPK());
    }
    
}
