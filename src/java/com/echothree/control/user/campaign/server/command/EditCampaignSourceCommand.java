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
import com.echothree.control.user.campaign.common.edit.CampaignSourceEdit;
import com.echothree.control.user.campaign.common.form.EditCampaignSourceForm;
import com.echothree.control.user.campaign.common.result.CampaignResultFactory;
import com.echothree.control.user.campaign.common.result.EditCampaignSourceResult;
import com.echothree.control.user.campaign.common.spec.CampaignSourceSpec;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.campaign.server.entity.CampaignSource;
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
public class EditCampaignSourceCommand
        extends BaseAbstractEditCommand<CampaignSourceSpec, CampaignSourceEdit, EditCampaignSourceResult, CampaignSource, CampaignSource> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CampaignSource.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CampaignSourceName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Value", FieldType.STRING, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCampaignSourceCommand */
    public EditCampaignSourceCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCampaignSourceResult getResult() {
        return CampaignResultFactory.getEditCampaignSourceResult();
    }

    @Override
    public CampaignSourceEdit getEdit() {
        return CampaignEditFactory.getCampaignSourceEdit();
    }

    @Override
    public CampaignSource getEntity(EditCampaignSourceResult result) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        CampaignSource campaignSource;
        var campaignSourceName = spec.getCampaignSourceName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            campaignSource = campaignControl.getCampaignSourceByName(campaignSourceName);
        } else { // EditMode.UPDATE
            campaignSource = campaignControl.getCampaignSourceByNameForUpdate(campaignSourceName);
        }

        if(campaignSource == null) {
            addExecutionError(ExecutionErrors.UnknownCampaignSourceName.name(), campaignSourceName);
        }

        return campaignSource;
    }

    @Override
    public CampaignSource getLockEntity(CampaignSource campaignSource) {
        return campaignSource;
    }

    @Override
    public void fillInResult(EditCampaignSourceResult result, CampaignSource campaignSource) {
        var campaignControl = Session.getModelController(CampaignControl.class);

        result.setCampaignSource(campaignControl.getCampaignSourceTransfer(getUserVisit(), campaignSource));
    }

    @Override
    public void doLock(CampaignSourceEdit edit, CampaignSource campaignSource) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var campaignSourceDescription = campaignControl.getCampaignSourceDescription(campaignSource, getPreferredLanguage());
        var campaignSourceDetail = campaignSource.getLastDetail();

        edit.setValue(campaignSourceDetail.getValue());
        edit.setIsDefault(campaignSourceDetail.getIsDefault().toString());
        edit.setSortOrder(campaignSourceDetail.getSortOrder().toString());

        if(campaignSourceDescription != null) {
            edit.setDescription(campaignSourceDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(CampaignSource campaignSource) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var value = edit.getValue();
        var duplicateCampaignSource = campaignControl.getCampaignSourceByValue(value);

        if(duplicateCampaignSource != null && !campaignSource.equals(duplicateCampaignSource)) {
            addExecutionError(ExecutionErrors.DuplicateCampaignSourceValue.name(), value);
        }
    }

    @Override
    public void doUpdate(CampaignSource campaignSource) {
        var campaignControl = Session.getModelController(CampaignControl.class);
        var partyPK = getPartyPK();
        var campaignSourceDetailValue = campaignControl.getCampaignSourceDetailValueForUpdate(campaignSource);
        var campaignSourceDescription = campaignControl.getCampaignSourceDescriptionForUpdate(campaignSource, getPreferredLanguage());
        var description = edit.getDescription();

        campaignSourceDetailValue.setValue(edit.getValue());
        campaignSourceDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        campaignSourceDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        campaignControl.updateCampaignSourceFromValue(campaignSourceDetailValue, partyPK);

        if(campaignSourceDescription == null && description != null) {
            campaignControl.createCampaignSourceDescription(campaignSource, getPreferredLanguage(), description, partyPK);
        } else {
            if(campaignSourceDescription != null && description == null) {
                campaignControl.deleteCampaignSourceDescription(campaignSourceDescription, partyPK);
            } else {
                if(campaignSourceDescription != null && description != null) {
                    var campaignSourceDescriptionValue = campaignControl.getCampaignSourceDescriptionValue(campaignSourceDescription);

                    campaignSourceDescriptionValue.setDescription(description);
                    campaignControl.updateCampaignSourceDescriptionFromValue(campaignSourceDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
