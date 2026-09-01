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

package com.echothree.control.user.cancellationpolicy.server.command;

import com.echothree.control.user.cancellationpolicy.common.edit.CancellationPolicyEditFactory;
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationReasonEdit;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.result.EditCancellationReasonResult;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationReasonSpec;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationKindLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReason;
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
public class EditCancellationReasonCommand
        extends BaseAbstractEditCommand<CancellationReasonSpec, CancellationReasonEdit, EditCancellationReasonResult,
                CancellationReason, CancellationReason> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationReason.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationReasonName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CancellationReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    CancellationPolicyControl cancellationPolicyControl;

    @Inject
    CancellationKindLogic cancellationKindLogic;
    
    /** Creates a new instance of EditCancellationReasonCommand */
    public EditCancellationReasonCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCancellationReasonResult getResult() {
        return CancellationPolicyResultFactory.getEditCancellationReasonResult();
    }

    @Override
    public CancellationReasonEdit getEdit() {
        return CancellationPolicyEditFactory.getCancellationReasonEdit();
    }

    @Override
    public CancellationReason getEntity(EditCancellationReasonResult result) {
        CancellationReason cancellationReason = null;
        var cancellationKindName = spec.getCancellationKindName();
        var cancellationKind = cancellationKindLogic.getCancellationKindByName(this, cancellationKindName);

        if(!hasExecutionErrors()) {
            var cancellationReasonName = spec.getCancellationReasonName();

            cancellationReason = cancellationPolicyControl.getCancellationReasonByName(cancellationKind, cancellationReasonName,
                    editModeToEntityPermission(editMode));

            if(cancellationReason == null) {
                    addExecutionError(ExecutionErrors.UnknownCancellationReasonName.name(), cancellationKindName, cancellationReasonName);
            }
        }

        return cancellationReason;
    }

    @Override
    public CancellationReason getLockEntity(CancellationReason cancellationReason) {
        return cancellationReason;
    }

    @Override
    public void fillInResult(EditCancellationReasonResult result, CancellationReason cancellationReason) {
        result.setCancellationReason(cancellationPolicyControl.getCancellationReasonTransfer(getUserVisit(), cancellationReason));
    }

    @Override
    public void doLock(CancellationReasonEdit edit, CancellationReason cancellationReason) {
        var cancellationReasonDescription = cancellationPolicyControl.getCancellationReasonDescription(cancellationReason, getPreferredLanguage());
        var cancellationReasonDetail = cancellationReason.getLastDetail();

        edit.setCancellationReasonName(cancellationReasonDetail.getCancellationReasonName());
        edit.setIsDefault(cancellationReasonDetail.getIsDefault().toString());
        edit.setSortOrder(cancellationReasonDetail.getSortOrder().toString());

        if(cancellationReasonDescription != null) {
            edit.setDescription(cancellationReasonDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(CancellationReason cancellationReason) {
        var cancellationKind = cancellationReason.getLastDetail().getCancellationKind();
        var cancellationReasonName = edit.getCancellationReasonName();
        var duplicateCancellationReason = cancellationPolicyControl.getCancellationReasonByName(cancellationKind, cancellationReasonName);

        if(duplicateCancellationReason != null && !cancellationReason.equals(duplicateCancellationReason)) {
            addExecutionError(ExecutionErrors.DuplicateCancellationReasonName.name(), cancellationReasonName);
        }
    }

    @Override
    public void doUpdate(CancellationReason cancellationReason) {
        var partyPK = getPartyPK();
        var cancellationReasonDetailValue = cancellationPolicyControl.getCancellationReasonDetailValueForUpdate(cancellationReason);
        var cancellationReasonDescription = cancellationPolicyControl.getCancellationReasonDescriptionForUpdate(cancellationReason, getPreferredLanguage());
        var description = edit.getDescription();

        cancellationReasonDetailValue.setCancellationReasonName(edit.getCancellationReasonName());
        cancellationReasonDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        cancellationReasonDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        cancellationPolicyControl.updateCancellationReasonFromValue(cancellationReasonDetailValue, partyPK);

        if(cancellationReasonDescription == null && description != null) {
            cancellationPolicyControl.createCancellationReasonDescription(cancellationReason, getPreferredLanguage(), description, partyPK);
        } else if(cancellationReasonDescription != null && description == null) {
            cancellationPolicyControl.deleteCancellationReasonDescription(cancellationReasonDescription, partyPK);
        } else if(cancellationReasonDescription != null && description != null) {
            var cancellationReasonDescriptionValue = cancellationPolicyControl.getCancellationReasonDescriptionValue(cancellationReasonDescription);

            cancellationReasonDescriptionValue.setDescription(description);
            cancellationPolicyControl.updateCancellationReasonDescriptionFromValue(cancellationReasonDescriptionValue, partyPK);
        }
    }
    
}
