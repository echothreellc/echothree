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

package com.echothree.control.user.cancellationpolicy.server.command;

import com.echothree.control.user.cancellationpolicy.common.edit.CancellationPolicyEditFactory;
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationTypeEdit;
import com.echothree.control.user.cancellationpolicy.common.form.EditCancellationTypeForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationTypeSpec;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditCancellationTypeCommand
        extends BaseEditCommand<CancellationTypeSpec, CancellationTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCancellationTypeCommand */
    public EditCancellationTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var result = CancellationPolicyResultFactory.getEditCancellationTypeResult();
        var cancellationKindName = spec.getCancellationKindName();
        var cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);
        
        if(cancellationKind != null) {
            if(editMode.equals(EditMode.LOCK)) {
                var cancellationTypeName = spec.getCancellationTypeName();
                var cancellationType = cancellationPolicyControl.getCancellationTypeByName(cancellationKind, cancellationTypeName);
                
                if(cancellationType != null) {
                    result.setCancellationType(cancellationPolicyControl.getCancellationTypeTransfer(getUserVisit(), cancellationType));
                    
                    if(lockEntity(cancellationType)) {
                        var cancellationTypeDescription = cancellationPolicyControl.getCancellationTypeDescription(cancellationType, getPreferredLanguage());
                        var edit = CancellationPolicyEditFactory.getCancellationTypeEdit();
                        var cancellationTypeDetail = cancellationType.getLastDetail();
                        var cancellationSequence = cancellationTypeDetail.getCancellationSequence();
                        
                        result.setEdit(edit);
                        edit.setCancellationTypeName(cancellationTypeDetail.getCancellationTypeName());
                        edit.setCancellationSequenceName(cancellationSequence == null? null: cancellationSequence.getLastDetail().getSequenceName());
                        edit.setIsDefault(cancellationTypeDetail.getIsDefault().toString());
                        edit.setSortOrder(cancellationTypeDetail.getSortOrder().toString());
                        
                        if(cancellationTypeDescription != null)
                            edit.setDescription(cancellationTypeDescription.getDescription());
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(cancellationType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownCancellationTypeName.name(), cancellationTypeName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                var cancellationTypeName = spec.getCancellationTypeName();
                var cancellationType = cancellationPolicyControl.getCancellationTypeByNameForUpdate(cancellationKind, cancellationTypeName);
                
                if(cancellationType != null) {
                    cancellationTypeName = edit.getCancellationTypeName();
                    var duplicateCancellationType = cancellationPolicyControl.getCancellationTypeByName(cancellationKind, cancellationTypeName);
                    
                    if(duplicateCancellationType == null || cancellationType.equals(duplicateCancellationType)) {
                        var sequenceControl = Session.getModelController(SequenceControl.class);
                        var cancellationSequenceName = edit.getCancellationSequenceName();
                        Sequence cancellationSequence = null;
                        
                        if(cancellationSequenceName != null) {
                            cancellationSequence = sequenceControl.getSequenceByName(cancellationKind.getLastDetail().getCancellationSequenceType(),
                                    cancellationSequenceName);
                        }
                        
                        if(cancellationSequenceName == null || cancellationSequence != null) {
                            if(lockEntityForUpdate(cancellationType)) {
                                try {
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
                                } finally {
                                    unlockEntity(cancellationType);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCancellationSequenceName.name(), cancellationSequenceName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateCancellationTypeName.name(), cancellationTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCancellationTypeName.name(), cancellationTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
        }
        
        return result;
    }
    
}
