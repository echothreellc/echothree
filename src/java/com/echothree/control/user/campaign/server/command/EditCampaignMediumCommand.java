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
import com.echothree.control.user.campaign.common.edit.CampaignMediumEdit;
import com.echothree.control.user.campaign.common.form.EditCampaignMediumForm;
import com.echothree.control.user.campaign.common.result.CampaignResultFactory;
import com.echothree.control.user.campaign.common.result.EditCampaignMediumResult;
import com.echothree.control.user.campaign.common.spec.CampaignMediumSpec;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.campaign.server.entity.CampaignMedium;
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

public class EditCampaignMediumCommand
        extends BaseAbstractEditCommand<CampaignMediumSpec, CampaignMediumEdit, EditCampaignMediumResult, CampaignMedium, CampaignMedium> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CampaignMedium.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CampaignMediumName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Value", FieldType.STRING, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCampaignMediumCommand */
    public EditCampaignMediumCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCampaignMediumResult getResult() {
        return CampaignResultFactory.getEditCampaignMediumResult();
    }

    @Override
    public CampaignMediumEdit getEdit() {
        return CampaignEditFactory.getCampaignMediumEdit();
    }

    @Override
    public CampaignMedium getEntity(EditCampaignMediumResult result) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        CampaignMedium campaignMedium;
        var campaignMediumName = spec.getCampaignMediumName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            campaignMedium = campaignControl.getCampaignMediumByName(campaignMediumName);
        } else { // EditMode.UPDATE
            campaignMedium = campaignControl.getCampaignMediumByNameForUpdate(campaignMediumName);
        }

        if(campaignMedium == null) {
            addExecutionError(ExecutionErrors.UnknownCampaignMediumName.name(), campaignMediumName);
        }

        return campaignMedium;
    }

    @Override
    public CampaignMedium getLockEntity(CampaignMedium campaignMedium) {
        return campaignMedium;
    }

    @Override
    public void fillInResult(EditCampaignMediumResult result, CampaignMedium campaignMedium) {
        var campaignControl = Session.getModelController(CampaignControl.class);

        result.setCampaignMedium(campaignControl.getCampaignMediumTransfer(getUserVisit(), campaignMedium));
    }

    @Override
    public void doLock(CampaignMediumEdit edit, CampaignMedium campaignMedium) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var campaignMediumDescription = campaignControl.getCampaignMediumDescription(campaignMedium, getPreferredLanguage());
        var campaignMediumDetail = campaignMedium.getLastDetail();

        edit.setValue(campaignMediumDetail.getValue());
        edit.setIsDefault(campaignMediumDetail.getIsDefault().toString());
        edit.setSortOrder(campaignMediumDetail.getSortOrder().toString());

        if(campaignMediumDescription != null) {
            edit.setDescription(campaignMediumDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(CampaignMedium campaignMedium) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var value = edit.getValue();
        var duplicateCampaignMedium = campaignControl.getCampaignMediumByValue(value);

        if(duplicateCampaignMedium != null && !campaignMedium.equals(duplicateCampaignMedium)) {
            addExecutionError(ExecutionErrors.DuplicateCampaignMediumValue.name(), value);
        }
    }

    @Override
    public void doUpdate(CampaignMedium campaignMedium) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var partyPK = getPartyPK();
        var campaignMediumDetailValue = campaignControl.getCampaignMediumDetailValueForUpdate(campaignMedium);
        var campaignMediumDescription = campaignControl.getCampaignMediumDescriptionForUpdate(campaignMedium, getPreferredLanguage());
        var description = edit.getDescription();

        campaignMediumDetailValue.setValue(edit.getValue());
        campaignMediumDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        campaignMediumDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        campaignControl.updateCampaignMediumFromValue(campaignMediumDetailValue, partyPK);

        if(campaignMediumDescription == null && description != null) {
            campaignControl.createCampaignMediumDescription(campaignMedium, getPreferredLanguage(), description, partyPK);
        } else {
            if(campaignMediumDescription != null && description == null) {
                campaignControl.deleteCampaignMediumDescription(campaignMediumDescription, partyPK);
            } else {
                if(campaignMediumDescription != null && description != null) {
                    var campaignMediumDescriptionValue = campaignControl.getCampaignMediumDescriptionValue(campaignMediumDescription);

                    campaignMediumDescriptionValue.setDescription(description);
                    campaignControl.updateCampaignMediumDescriptionFromValue(campaignMediumDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
