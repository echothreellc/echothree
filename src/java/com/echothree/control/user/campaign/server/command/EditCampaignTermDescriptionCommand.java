// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.campaign.common.edit.CampaignTermDescriptionEdit;
import com.echothree.control.user.campaign.common.form.EditCampaignTermDescriptionForm;
import com.echothree.control.user.campaign.common.result.CampaignResultFactory;
import com.echothree.control.user.campaign.common.result.EditCampaignTermDescriptionResult;
import com.echothree.control.user.campaign.common.spec.CampaignTermDescriptionSpec;
import com.echothree.model.control.campaign.server.CampaignControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.campaign.server.entity.CampaignTerm;
import com.echothree.model.data.campaign.server.entity.CampaignTermDescription;
import com.echothree.model.data.campaign.server.value.CampaignTermDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditCampaignTermDescriptionCommand
        extends BaseAbstractEditCommand<CampaignTermDescriptionSpec, CampaignTermDescriptionEdit, EditCampaignTermDescriptionResult, CampaignTermDescription, CampaignTerm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CampaignTerm.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CampaignTermName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditCampaignTermDescriptionCommand */
    public EditCampaignTermDescriptionCommand(UserVisitPK userVisitPK, EditCampaignTermDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCampaignTermDescriptionResult getResult() {
        return CampaignResultFactory.getEditCampaignTermDescriptionResult();
    }

    @Override
    public CampaignTermDescriptionEdit getEdit() {
        return CampaignEditFactory.getCampaignTermDescriptionEdit();
    }

    @Override
    public CampaignTermDescription getEntity(EditCampaignTermDescriptionResult result) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        CampaignTermDescription campaignTermDescription = null;
        String campaignTermName = spec.getCampaignTermName();
        CampaignTerm campaignTerm = campaignControl.getCampaignTermByName(campaignTermName);

        if(campaignTerm != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    campaignTermDescription = campaignControl.getCampaignTermDescription(campaignTerm, language);
                } else { // EditMode.UPDATE
                    campaignTermDescription = campaignControl.getCampaignTermDescriptionForUpdate(campaignTerm, language);
                }

                if(campaignTermDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownCampaignTermDescription.name(), campaignTermName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCampaignTermName.name(), campaignTermName);
        }

        return campaignTermDescription;
    }

    @Override
    public CampaignTerm getLockEntity(CampaignTermDescription campaignTermDescription) {
        return campaignTermDescription.getCampaignTerm();
    }

    @Override
    public void fillInResult(EditCampaignTermDescriptionResult result, CampaignTermDescription campaignTermDescription) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);

        result.setCampaignTermDescription(campaignControl.getCampaignTermDescriptionTransfer(getUserVisit(), campaignTermDescription));
    }

    @Override
    public void doLock(CampaignTermDescriptionEdit edit, CampaignTermDescription campaignTermDescription) {
        edit.setDescription(campaignTermDescription.getDescription());
    }

    @Override
    public void doUpdate(CampaignTermDescription campaignTermDescription) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        CampaignTermDescriptionValue campaignTermDescriptionValue = campaignControl.getCampaignTermDescriptionValue(campaignTermDescription);
        campaignTermDescriptionValue.setDescription(edit.getDescription());

        campaignControl.updateCampaignTermDescriptionFromValue(campaignTermDescriptionValue, getPartyPK());
    }
    
}
