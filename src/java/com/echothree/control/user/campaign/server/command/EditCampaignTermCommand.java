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
import com.echothree.control.user.campaign.common.edit.CampaignTermEdit;
import com.echothree.control.user.campaign.common.form.EditCampaignTermForm;
import com.echothree.control.user.campaign.common.result.CampaignResultFactory;
import com.echothree.control.user.campaign.common.result.EditCampaignTermResult;
import com.echothree.control.user.campaign.common.spec.CampaignTermSpec;
import com.echothree.model.control.campaign.server.CampaignControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.campaign.server.entity.CampaignTerm;
import com.echothree.model.data.campaign.server.entity.CampaignTermDescription;
import com.echothree.model.data.campaign.server.entity.CampaignTermDetail;
import com.echothree.model.data.campaign.server.value.CampaignTermDescriptionValue;
import com.echothree.model.data.campaign.server.value.CampaignTermDetailValue;
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

public class EditCampaignTermCommand
        extends BaseAbstractEditCommand<CampaignTermSpec, CampaignTermEdit, EditCampaignTermResult, CampaignTerm, CampaignTerm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CampaignTerm.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CampaignTermName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Value", FieldType.STRING, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditCampaignTermCommand */
    public EditCampaignTermCommand(UserVisitPK userVisitPK, EditCampaignTermForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCampaignTermResult getResult() {
        return CampaignResultFactory.getEditCampaignTermResult();
    }

    @Override
    public CampaignTermEdit getEdit() {
        return CampaignEditFactory.getCampaignTermEdit();
    }

    @Override
    public CampaignTerm getEntity(EditCampaignTermResult result) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        CampaignTerm campaignTerm;
        String campaignTermName = spec.getCampaignTermName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            campaignTerm = campaignControl.getCampaignTermByName(campaignTermName);
        } else { // EditMode.UPDATE
            campaignTerm = campaignControl.getCampaignTermByNameForUpdate(campaignTermName);
        }

        if(campaignTerm == null) {
            addExecutionError(ExecutionErrors.UnknownCampaignTermName.name(), campaignTermName);
        }

        return campaignTerm;
    }

    @Override
    public CampaignTerm getLockEntity(CampaignTerm campaignTerm) {
        return campaignTerm;
    }

    @Override
    public void fillInResult(EditCampaignTermResult result, CampaignTerm campaignTerm) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);

        result.setCampaignTerm(campaignControl.getCampaignTermTransfer(getUserVisit(), campaignTerm));
    }

    @Override
    public void doLock(CampaignTermEdit edit, CampaignTerm campaignTerm) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        CampaignTermDescription campaignTermDescription = campaignControl.getCampaignTermDescription(campaignTerm, getPreferredLanguage());
        CampaignTermDetail campaignTermDetail = campaignTerm.getLastDetail();

        edit.setValue(campaignTermDetail.getValue());
        edit.setIsDefault(campaignTermDetail.getIsDefault().toString());
        edit.setSortOrder(campaignTermDetail.getSortOrder().toString());

        if(campaignTermDescription != null) {
            edit.setDescription(campaignTermDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(CampaignTerm campaignTerm) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        String value = edit.getValue();
        CampaignTerm duplicateCampaignTerm = campaignControl.getCampaignTermByValue(value);

        if(duplicateCampaignTerm != null && !campaignTerm.equals(duplicateCampaignTerm)) {
            addExecutionError(ExecutionErrors.DuplicateCampaignTermValue.name(), value);
        }
    }

    @Override
    public void doUpdate(CampaignTerm campaignTerm) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        PartyPK partyPK = getPartyPK();
        CampaignTermDetailValue campaignTermDetailValue = campaignControl.getCampaignTermDetailValueForUpdate(campaignTerm);
        CampaignTermDescription campaignTermDescription = campaignControl.getCampaignTermDescriptionForUpdate(campaignTerm, getPreferredLanguage());
        String description = edit.getDescription();

        campaignTermDetailValue.setValue(edit.getValue());
        campaignTermDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        campaignTermDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        campaignControl.updateCampaignTermFromValue(campaignTermDetailValue, partyPK);

        if(campaignTermDescription == null && description != null) {
            campaignControl.createCampaignTermDescription(campaignTerm, getPreferredLanguage(), description, partyPK);
        } else {
            if(campaignTermDescription != null && description == null) {
                campaignControl.deleteCampaignTermDescription(campaignTermDescription, partyPK);
            } else {
                if(campaignTermDescription != null && description != null) {
                    CampaignTermDescriptionValue campaignTermDescriptionValue = campaignControl.getCampaignTermDescriptionValue(campaignTermDescription);

                    campaignTermDescriptionValue.setDescription(description);
                    campaignControl.updateCampaignTermDescriptionFromValue(campaignTermDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
