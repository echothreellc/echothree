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
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationReasonTypeEdit;
import com.echothree.control.user.cancellationpolicy.common.form.EditCancellationReasonTypeForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationReasonTypeSpec;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
public class EditCancellationReasonTypeCommand
        extends BaseEditCommand<CancellationReasonTypeSpec, CancellationReasonTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationReasonType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditCancellationReasonTypeCommand */
    public EditCancellationReasonTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var result = CancellationPolicyResultFactory.getEditCancellationReasonTypeResult();
        var cancellationKindName = spec.getCancellationKindName();
        var cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);
        
        if(cancellationKind != null) {
            var cancellationReasonName = spec.getCancellationReasonName();
            var cancellationReason = cancellationPolicyControl.getCancellationReasonByName(cancellationKind, cancellationReasonName);
            
            if(cancellationReason != null) {
                var cancellationTypeName = spec.getCancellationTypeName();
                var cancellationType = cancellationPolicyControl.getCancellationTypeByName(cancellationKind, cancellationTypeName);
                
                if(cancellationType != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        var cancellationReasonType = cancellationPolicyControl.getCancellationReasonType(cancellationReason, cancellationType);
                        
                        if(cancellationReasonType != null) {
                            result.setCancellationReasonType(cancellationPolicyControl.getCancellationReasonTypeTransfer(getUserVisit(), cancellationReasonType));
                            
                            if(lockEntity(cancellationReason)) {
                                var edit = CancellationPolicyEditFactory.getCancellationReasonTypeEdit();
                                
                                result.setEdit(edit);
                                edit.setIsDefault(cancellationReasonType.getIsDefault().toString());
                                edit.setSortOrder(cancellationReasonType.getSortOrder().toString());
                                
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(cancellationReasonType));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCancellationReasonType.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var cancellationReasonTypeValue = cancellationPolicyControl.getCancellationReasonTypeValueForUpdate(cancellationReason, cancellationType);
                        
                        if(cancellationReasonTypeValue != null) {
                            if(lockEntityForUpdate(cancellationReason)) {
                                try {
                                    cancellationReasonTypeValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    cancellationReasonTypeValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    cancellationPolicyControl.updateCancellationReasonTypeFromValue(cancellationReasonTypeValue, getPartyPK());
                                } finally {
                                    unlockEntity(cancellationReason);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCancellationReasonType.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCancellationTypeName.name(), cancellationTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCancellationReasonName.name(), cancellationReasonName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
        }
        
        return result;
    }
    
}
