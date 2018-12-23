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

import com.echothree.control.user.campaign.common.edit.CampaignContentEdit;
import com.echothree.control.user.campaign.common.edit.CampaignEditFactory;
import com.echothree.control.user.campaign.common.form.EditCampaignContentForm;
import com.echothree.control.user.campaign.common.result.CampaignResultFactory;
import com.echothree.control.user.campaign.common.result.EditCampaignContentResult;
import com.echothree.control.user.campaign.common.spec.CampaignContentSpec;
import com.echothree.model.control.campaign.server.CampaignControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.campaign.server.entity.CampaignContent;
import com.echothree.model.data.campaign.server.entity.CampaignContentDescription;
import com.echothree.model.data.campaign.server.entity.CampaignContentDetail;
import com.echothree.model.data.campaign.server.value.CampaignContentDescriptionValue;
import com.echothree.model.data.campaign.server.value.CampaignContentDetailValue;
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

public class EditCampaignContentCommand
        extends BaseAbstractEditCommand<CampaignContentSpec, CampaignContentEdit, EditCampaignContentResult, CampaignContent, CampaignContent> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CampaignContent.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CampaignContentName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Value", FieldType.STRING, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditCampaignContentCommand */
    public EditCampaignContentCommand(UserVisitPK userVisitPK, EditCampaignContentForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCampaignContentResult getResult() {
        return CampaignResultFactory.getEditCampaignContentResult();
    }

    @Override
    public CampaignContentEdit getEdit() {
        return CampaignEditFactory.getCampaignContentEdit();
    }

    @Override
    public CampaignContent getEntity(EditCampaignContentResult result) {
        CampaignControl campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        CampaignContent campaignContent;
        String campaignContentName = spec.getCampaignContentName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            campaignContent = campaignControl.getCampaignContentByName(campaignContentName);
        } else { // EditMode.UPDATE
            campaignContent = campaignControl.getCampaignContentByNameForUpdate(campaignContentName);
        }

        if(campaignContent == null) {
            addExecutionError(ExecutionErrors.UnknownCampaignContentName.name(), campaignContentName);
        }

        return campaignContent;
    }

    @Override
    public CampaignContent getLockEntity(CampaignContent campaignContent) {
        return campaignContent;
    }

    @Override
    public void fillInResult(EditCampaignContentResult result, CampaignContent campaignContent) {
        CampaignControl campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);

        result.setCampaignContent(campaignControl.getCampaignContentTransfer(getUserVisit(), campaignContent));
    }

    @Override
    public void doLock(CampaignContentEdit edit, CampaignContent campaignContent) {
        CampaignControl campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        CampaignContentDescription campaignContentDescription = campaignControl.getCampaignContentDescription(campaignContent, getPreferredLanguage());
        CampaignContentDetail campaignContentDetail = campaignContent.getLastDetail();

        edit.setValue(campaignContentDetail.getValue());
        edit.setIsDefault(campaignContentDetail.getIsDefault().toString());
        edit.setSortOrder(campaignContentDetail.getSortOrder().toString());

        if(campaignContentDescription != null) {
            edit.setDescription(campaignContentDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(CampaignContent campaignContent) {
        CampaignControl campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        String value = edit.getValue();
        CampaignContent duplicateCampaignContent = campaignControl.getCampaignContentByValue(value);

        if(duplicateCampaignContent != null && !campaignContent.equals(duplicateCampaignContent)) {
            addExecutionError(ExecutionErrors.DuplicateCampaignContentValue.name(), value);
        }
    }

    @Override
    public void doUpdate(CampaignContent campaignContent) {
        CampaignControl campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        PartyPK partyPK = getPartyPK();
        CampaignContentDetailValue campaignContentDetailValue = campaignControl.getCampaignContentDetailValueForUpdate(campaignContent);
        CampaignContentDescription campaignContentDescription = campaignControl.getCampaignContentDescriptionForUpdate(campaignContent, getPreferredLanguage());
        String description = edit.getDescription();

        campaignContentDetailValue.setValue(edit.getValue());
        campaignContentDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        campaignContentDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        campaignControl.updateCampaignContentFromValue(campaignContentDetailValue, partyPK);

        if(campaignContentDescription == null && description != null) {
            campaignControl.createCampaignContentDescription(campaignContent, getPreferredLanguage(), description, partyPK);
        } else {
            if(campaignContentDescription != null && description == null) {
                campaignControl.deleteCampaignContentDescription(campaignContentDescription, partyPK);
            } else {
                if(campaignContentDescription != null && description != null) {
                    CampaignContentDescriptionValue campaignContentDescriptionValue = campaignControl.getCampaignContentDescriptionValue(campaignContentDescription);

                    campaignContentDescriptionValue.setDescription(description);
                    campaignControl.updateCampaignContentDescriptionFromValue(campaignContentDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
