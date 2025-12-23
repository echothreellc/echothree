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
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationPolicyReasonEdit;
import com.echothree.control.user.cancellationpolicy.common.form.EditCancellationPolicyReasonForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationPolicyReasonSpec;
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
public class EditCancellationPolicyReasonCommand
        extends BaseEditCommand<CancellationPolicyReasonSpec, CancellationPolicyReasonEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationPolicyReason.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationReasonName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditCancellationPolicyReasonCommand */
    public EditCancellationPolicyReasonCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var result = CancellationPolicyResultFactory.getEditCancellationPolicyReasonResult();
        var cancellationPolicyName = spec.getCancellationPolicyName();
        var cancellationKindName = spec.getCancellationKindName();
        var cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);
        
        if(cancellationKind != null) {
            var cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);
            
            if(cancellationPolicy != null) {
                var cancellationReasonName = spec.getCancellationReasonName();
                var cancellationReason = cancellationPolicyControl.getCancellationReasonByName(cancellationKind, cancellationReasonName);
                
                if(cancellationReason != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        var cancellationPolicyReason = cancellationPolicyControl.getCancellationPolicyReason(cancellationPolicy, cancellationReason);
                        
                        if(cancellationPolicyReason != null) {
                            result.setCancellationPolicyReason(cancellationPolicyControl.getCancellationPolicyReasonTransfer(getUserVisit(), cancellationPolicyReason));
                            
                            if(lockEntity(cancellationPolicy)) {
                                var edit = CancellationPolicyEditFactory.getCancellationPolicyReasonEdit();
                                
                                result.setEdit(edit);
                                edit.setIsDefault(cancellationPolicyReason.getIsDefault().toString());
                                edit.setSortOrder(cancellationPolicyReason.getSortOrder().toString());
                                
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(cancellationPolicyReason));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCancellationPolicyReason.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var cancellationPolicyReasonValue = cancellationPolicyControl.getCancellationPolicyReasonValueForUpdate(cancellationPolicy, cancellationReason);
                        
                        if(cancellationPolicyReasonValue != null) {
                            if(lockEntityForUpdate(cancellationPolicy)) {
                                try {
                                    cancellationPolicyReasonValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    cancellationPolicyReasonValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    cancellationPolicyControl.updateCancellationPolicyReasonFromValue(cancellationPolicyReasonValue, getPartyPK());
                                } finally {
                                    unlockEntity(cancellationPolicy);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCancellationPolicyReason.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCancellationReasonName.name(), cancellationReasonName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationPolicyName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
        }
        
        return result;
    }
    
}
