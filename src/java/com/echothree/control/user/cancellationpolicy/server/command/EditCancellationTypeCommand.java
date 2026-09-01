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
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationTypeEdit;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.result.EditCancellationTypeResult;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationTypeSpec;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationKindLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationType;
import com.echothree.model.data.sequence.server.entity.Sequence;
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
public class EditCancellationTypeCommand
        extends BaseAbstractEditCommand<CancellationTypeSpec, CancellationTypeEdit, EditCancellationTypeResult, CancellationType, CancellationType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationType.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CancellationTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    CancellationPolicyControl cancellationPolicyControl;

    @Inject
    CancellationKindLogic cancellationKindLogic;

    @Inject
    SequenceControl sequenceControl;

    /** Creates a new instance of EditCancellationTypeCommand */
    public EditCancellationTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCancellationTypeResult getResult() {
        return CancellationPolicyResultFactory.getEditCancellationTypeResult();
    }

    @Override
    public CancellationTypeEdit getEdit() {
        return CancellationPolicyEditFactory.getCancellationTypeEdit();
    }

    @Override
    public CancellationType getEntity(EditCancellationTypeResult result) {
        CancellationType cancellationType = null;
        var cancellationKindName = spec.getCancellationKindName();
        var cancellationKind = cancellationKindLogic.getCancellationKindByName(this, cancellationKindName);

        if(!hasExecutionErrors()) {
            var cancellationTypeName = spec.getCancellationTypeName();

            cancellationType = cancellationPolicyControl.getCancellationTypeByName(cancellationKind, cancellationTypeName,
                    editModeToEntityPermission(editMode));

            if(cancellationType == null) {
                addExecutionError(ExecutionErrors.UnknownCancellationTypeName.name(), cancellationKindName, cancellationTypeName);
            }
        }

        return cancellationType;
    }

    @Override
    public CancellationType getLockEntity(CancellationType cancellationType) {
        return cancellationType;
    }

    @Override
    public void fillInResult(EditCancellationTypeResult result, CancellationType cancellationType) {
        result.setCancellationType(cancellationPolicyControl.getCancellationTypeTransfer(getUserVisit(), cancellationType));
    }

    @Override
    public void doLock(CancellationTypeEdit edit, CancellationType cancellationType) {
        var cancellationTypeDescription = cancellationPolicyControl.getCancellationTypeDescription(cancellationType, getPreferredLanguage());
        var cancellationTypeDetail = cancellationType.getLastDetail();
        var cancellationSequence = cancellationTypeDetail.getCancellationSequence();

        edit.setCancellationTypeName(cancellationTypeDetail.getCancellationTypeName());
        edit.setCancellationSequenceName(cancellationSequence == null? null: cancellationSequence.getLastDetail().getSequenceName());
        edit.setIsDefault(cancellationTypeDetail.getIsDefault().toString());
        edit.setSortOrder(cancellationTypeDetail.getSortOrder().toString());

        if(cancellationTypeDescription != null) {
            edit.setDescription(cancellationTypeDescription.getDescription());
        }
    }

    Sequence cancellationSequence;

    @Override
    public void canUpdate(CancellationType cancellationType) {
        var cancellationKind = cancellationType.getLastDetail().getCancellationKind();
        var cancellationTypeName = edit.getCancellationTypeName();
        var duplicateCancellationType = cancellationPolicyControl.getCancellationTypeByName(cancellationKind, cancellationTypeName);

        if(duplicateCancellationType == null || cancellationType.equals(duplicateCancellationType)) {
            var cancellationSequenceName = edit.getCancellationSequenceName();

            if(cancellationSequenceName != null) {
                cancellationSequence = sequenceControl.getSequenceByName(cancellationKind.getLastDetail().getCancellationSequenceType(),
                        cancellationSequenceName);
            }

            if(cancellationSequenceName != null && cancellationSequence == null) {
                addExecutionError(ExecutionErrors.UnknownCancellationSequenceName.name(), cancellationSequenceName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateCancellationTypeName.name(), cancellationTypeName);
        }
    }

    @Override
    public void doUpdate(CancellationType cancellationType) {
        var partyPK = getPartyPK();
        var cancellationTypeDetailValue = cancellationPolicyControl.getCancellationTypeDetailValueForUpdate(cancellationType);
        var cancellationTypeDescription = cancellationPolicyControl.getCancellationTypeDescriptionForUpdate(cancellationType, getPreferredLanguage());
        var description = edit.getDescription();

        cancellationTypeDetailValue.setCancellationTypeName(edit.getCancellationTypeName());
        cancellationTypeDetailValue.setCancellationSequencePK(cancellationSequence == null? null: cancellationSequence.getPrimaryKey());
        cancellationTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        cancellationTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        cancellationPolicyControl.updateCancellationTypeFromValue(cancellationTypeDetailValue, partyPK);

        if(cancellationTypeDescription == null && description != null) {
            cancellationPolicyControl.createCancellationTypeDescription(cancellationType, getPreferredLanguage(), description, partyPK);
        } else if(cancellationTypeDescription != null && description == null) {
            cancellationPolicyControl.deleteCancellationTypeDescription(cancellationTypeDescription, partyPK);
        } else if(cancellationTypeDescription != null && description != null) {
            var cancellationTypeDescriptionValue = cancellationPolicyControl.getCancellationTypeDescriptionValue(cancellationTypeDescription);

            cancellationTypeDescriptionValue.setDescription(description);
            cancellationPolicyControl.updateCancellationTypeDescriptionFromValue(cancellationTypeDescriptionValue, partyPK);
        }
    }
    
}
